package com.garagemanagement.carrepairservice.service.impl;

import com.garagemanagement.carrepairservice.common.entity.*;
import com.garagemanagement.carrepairservice.common.handler.AppError;
import com.garagemanagement.carrepairservice.common.model.*;
import com.garagemanagement.carrepairservice.common.model.internal.AccessoryDTO;
import com.garagemanagement.carrepairservice.common.model.internal.CreateUserDTO;
import com.garagemanagement.carrepairservice.common.model.internal.RetrieveUserDTO;
import com.garagemanagement.carrepairservice.common.model.internal.ServiceDTO;
import com.garagemanagement.carrepairservice.common.utils.GenerateUUID;
import com.garagemanagement.carrepairservice.internal.InternalServiceImpl;
import com.garagemanagement.carrepairservice.repository.*;
import com.garagemanagement.carrepairservice.service.CarRepairService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.*;

@Service
public class CarRepairServiceImpl implements CarRepairService {
    @Autowired
    CarRepairRepository carRepairRepository;

    @Autowired
    InternalServiceImpl internalService;

    @Autowired
    CarRepository carRepository;

    @Autowired
    ServiceUsedRepository serviceUsedRepository;

    @Autowired
    BillRepository billRepository;

    @Override
    public CarRepair createCarRepair(CarRepairDTO carRepairDTO) {
        if (carRepairDTO.getStatus() == null) {
            carRepairDTO.setStatus(false);
        }

        if (carRepairDTO.getEmployeeId() != null)
            internalService.checkUserRoleById(carRepairDTO.getEmployeeId(), "SUPPORT");

        Optional<Car> car = carRepository.findById(carRepairDTO.getCarId());

        if (car.isEmpty())
            throw AppError.NotFoundError(Car.class.getSimpleName());

        String uuid = GenerateUUID.generateRandomUUID();

        carRepairDTO.setId(uuid);

        CarRepair carRepair = new CarRepair(
                carRepairDTO.getId(),
                carRepairDTO.getStatus(),
                carRepairDTO.getDate(),
                carRepairDTO.getNote(),
                carRepairDTO.getEmployeeId(),
                car.get()
        );

        return carRepairRepository.save(carRepair);
    }

    @Override
    public CarRepair getCarRepairById(String id) {
        Optional<CarRepair> carRepair = carRepairRepository.findById(id);

        if (carRepair.isEmpty())
            throw AppError.NotFoundError(CarRepair.class.getSimpleName());

        return carRepair.get();
    }

    @Override
    public void deleteCarRepairById(String id) {
        Optional<CarRepair> carRepair = carRepairRepository.findById(id);

        if (carRepair.isEmpty())
            throw AppError.NotFoundError(CarRepair.class.getSimpleName());

        carRepairRepository.deleteById(id);
    }

    @Override
    public CarRepair updateCarRepairById(String id, Map<String, Object> fields) {
        Optional<CarRepair> carRepair = carRepairRepository.findById(id);

        if (carRepair.isEmpty())
            throw AppError.NotFoundError(CarRepair.class.getSimpleName());

        fields.forEach((key, value) -> {
            if (key.equals("employee_id")) {
                carRepair.get().setEmployeeId(String.valueOf(value));
            }

            if (!key.equals("id")) {
                Field field = ReflectionUtils.findField(CarRepair.class, key);
                if (field != null) {
                    field.setAccessible(true);
                    ReflectionUtils.setField(field, carRepair.get(), value);
                }
            }
        });

        if (carRepair.get().getEmployeeId() != null)
            internalService.checkUserRoleById(carRepair.get().getEmployeeId(), "SUPPORT");

        Optional<Car> car = carRepository.findById(carRepair.get().getCar().getId());

        if (car.isEmpty())
            throw AppError.NotFoundError(Car.class.getSimpleName());

        System.out.println(carRepair.get());

        return carRepairRepository.save(carRepair.get());
    }

    public CarRepair createAppoinmentCarRepair(CreateAppointmentCarRepairDTO createAppointmentCarRepairDTO) {
        String customerId = createAppointmentCarRepairDTO.getCustomerId();
        internalService.checkUserRoleById(customerId, "CUSTOMER");

        Car _car = null;

        String plate = createAppointmentCarRepairDTO.getPlate();
        if (plate != null && !plate.equals("")) {
            Optional<Car> car = carRepository.findByPlate(plate);

            _car = car.orElseGet(() -> carRepository.save(new Car(
                    GenerateUUID.generateRandomUUID(),
                    plate, customerId
            )));
        }

        String[] services = createAppointmentCarRepairDTO.getServices();

        CarRepair carRepair = new CarRepair(
                GenerateUUID.generateRandomUUID(),
                false,
                createAppointmentCarRepairDTO.getDate(),
                "Đặt lịch online",
                null,
                _car
        );

        List<ServiceDTO> serviceDTOList = new ArrayList<>();

        if (services != null) {
            // check services id valid
            for (String service : services) {
                serviceDTOList.add(internalService.getServiceById(service));
            }
        }

        carRepair.setServiceUseds(new ArrayList<ServiceUsed>());

        for (ServiceDTO serviceDTO : serviceDTOList) {
            carRepair.getServiceUseds().add(
                    new ServiceUsed(
                            GenerateUUID.generateRandomUUID(),
                            serviceDTO.getPrice(),
                            serviceDTO.getId(),
                            carRepair
                    )
            );
        }

        CarRepair createdAppoinmentCarRepair = carRepairRepository.save(carRepair);

        return createdAppoinmentCarRepair;
    }

    public CarRepair createDirectCarRepair(CreateDirectCarRepairDTO createDirectCarRepairDTO) {
        RetrieveUserDTO user = internalService.getUserByPhone(createDirectCarRepairDTO.getCustomerPhone());

        String customerId = null;

        if (user != null) {
            customerId = user.getId();
        }

        if (customerId == null) {
            // create new user save in db
            String uuid = GenerateUUID.generateRandomUUID();
            RetrieveUserDTO createdUser = internalService.createUser(new CreateUserDTO(
                    uuid,
                    uuid,
                    "customer", // set default password
                    createDirectCarRepairDTO.getCustomerName(),
                    createDirectCarRepairDTO.getCustomerPhone(),
                    "",
                    "",
                    3 // customer
            ));
            customerId = createdUser.getId();
        }

        Car _car = null;

        String plate = createDirectCarRepairDTO.getPlate();
        Optional<Car> car = carRepository.findByPlate(plate);

        if (car.isEmpty()) {
            _car = carRepository.save(new Car(
                    GenerateUUID.generateRandomUUID(),
                    plate,
                    customerId
            ));
        } else _car = car.get();

        String employeeId = createDirectCarRepairDTO.getEmployeeId();

        internalService.checkUserRoleById(employeeId, "SUPPORT");

        String[] services = createDirectCarRepairDTO.getServices();
        String[] accessories = createDirectCarRepairDTO.getAccessories();

        CarRepair carRepair = new CarRepair(
                GenerateUUID.generateRandomUUID(),
                false,
                createDirectCarRepairDTO.getDate(),
                "Tạo lịch sửa trực tiếp",
                employeeId,
                _car
        );

        List<ServiceDTO> serviceDTOList = new ArrayList<>();

        if (services != null) {
            // check services id valid
            for (String service : services) {
                serviceDTOList.add(internalService.getServiceById(service));
            }
        }

        carRepair.setServiceUseds(new ArrayList<ServiceUsed>());

        for (ServiceDTO serviceDTO : serviceDTOList) {
            carRepair.getServiceUseds().add(
                    new ServiceUsed(
                            GenerateUUID.generateRandomUUID(),
                            serviceDTO.getPrice(),
                            serviceDTO.getId(),
                            carRepair
                    )
            );
        }

        List<AccessoryUsed> accessoryUseds = new ArrayList<>();

        if (accessories != null) {
            // check accessories id valid
            for (String accessory : accessories) {
                String[] tmp = accessory.split(",");

                AccessoryDTO accessoryDTO = internalService.getAccessoryById(tmp[0]);

                accessoryUseds.add(new AccessoryUsed(
                        GenerateUUID.generateRandomUUID(),
                        Integer.parseInt(tmp[1]),
                        Integer.parseInt(tmp[1]) * accessoryDTO.getPrice(),
                        accessoryDTO.getId(),
                        carRepair
                ));
            }
        }

        carRepair.setAccessoryUseds(new ArrayList<AccessoryUsed>());

        for (AccessoryUsed accessorUsed : accessoryUseds) {
            carRepair.getAccessoryUseds().add(
                    accessorUsed
            );
        }

        return carRepairRepository.save(carRepair);
    }

    public SalaryEmployeeDTO getSalaryEmployeeById(SalaryEmployeeDTO salaryEmployeeDTO) {
        RetrieveUserDTO employee = internalService.checkUserRoleById(salaryEmployeeDTO.getEmployeeId(), "SUPPORT");

        List<CarRepair> carRepairs = carRepairRepository.findByDateBetweenAndEmployeeId(salaryEmployeeDTO.getStartDate(), salaryEmployeeDTO.getEndDate(), employee.getId());

        Double d = (double) 0f;
        // giả sử công thức tính tiền công = 30% của tiền dịch vụ
        for (CarRepair carRepair : carRepairs) {
            List<ServiceUsed> serviceUseds = carRepair.getServiceUseds();

            for (ServiceUsed serviceUsed : serviceUseds) {
                d += (0.3 * serviceUsed.getAmount());
            }
        }

        return new SalaryEmployeeDTO(
                salaryEmployeeDTO.getEmployeeId(),
                employee.getFullname(),
                employee.getPhone(),
                employee.getEmail(),
                employee.getAddress(),
                salaryEmployeeDTO.getStartDate(),
                salaryEmployeeDTO.getEndDate(),
                d
        );
    }

    public Page<CarRepairPaginationDTO> findCarRepairs(List<Boolean> status, String plate, Pageable pageable) {
        return carRepairRepository.findCarRepairs(status, plate, pageable);
    }

    public Page<CarRepairPaginationDTO> findCarRepairsByCustomerId(List<Boolean> status, String customerId, Pageable pageable){
        return carRepairRepository.findCarRepairsByCustomerId(status, customerId, pageable);
    }

    public List<SalaryEmployeeDTO> getSalaryEmployees(SalaryEmployeeDTO salaryEmployeeDTO) {
        List<RetrieveUserDTO> users = internalService.getAllUsers();
        List<SalaryEmployeeDTO> lists = new ArrayList<>();

        for (RetrieveUserDTO user : users) {
            if (user.getRole().equals("SUPPORT")) {
                List<CarRepair> carRepairs = carRepairRepository.findByDateBetweenAndEmployeeId(salaryEmployeeDTO.getStartDate(), salaryEmployeeDTO.getEndDate(), user.getId());

                Double d = (double) 0f;
                // giả sử công thức tính tiền công = 30% của tiền dịch vụ
                for (CarRepair carRepair : carRepairs) {
                    List<ServiceUsed> serviceUseds = carRepair.getServiceUseds();

                    for (ServiceUsed serviceUsed : serviceUseds) {
                        d += (0.3 * serviceUsed.getAmount());
                    }
                }

                lists.add(new SalaryEmployeeDTO(
                        user.getId(),
                        user.getFullname(),
                        user.getPhone(),
                        user.getEmail(),
                        user.getAddress(),
                        salaryEmployeeDTO.getStartDate(),
                        salaryEmployeeDTO.getEndDate(),
                        d
                ));
            }

        }

        return lists;
    }

    public List<RevenueCustomerDTO> getRevenueCustomers(RevenueCustomerDTO revenueCustomerDTO){
        List<RetrieveUserDTO> users = internalService.getAllUsers();
        List<RevenueCustomerDTO> lists = new ArrayList<>();

        for (RetrieveUserDTO user : users) {
            if (user.getRole().equals("CUSTOMER")) {
                List<Agg> carRepairs = carRepairRepository.findCarRepairsByCustomerIdBetweenDate(revenueCustomerDTO.getStartDate(),
                        revenueCustomerDTO.getEndDate(), user.getId());

                Double d = (double) 0f;

                for (Agg carRepair : carRepairs) {
                    Optional<Bill> b = billRepository.findBillByRepairId(carRepair.getRepair_id());

                    if(b.isPresent()){
                        d += b.get().getAmount();
                    }
                }

                lists.add(new RevenueCustomerDTO(
                        user.getId(),
                        user.getFullname(),
                        user.getPhone(),
                        user.getEmail(),
                        user.getAddress(),
                        revenueCustomerDTO.getStartDate(),
                        revenueCustomerDTO.getEndDate(),
                        d
                ));
            }

        }
        Collections.sort(lists, Comparator.comparingDouble(RevenueCustomerDTO::getTotal));

        Collections.reverse(lists);
        return lists;
    }
}
