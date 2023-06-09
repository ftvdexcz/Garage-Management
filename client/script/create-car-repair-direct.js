'use stricts';

/**********************************************/
const token = localStorage.getItem('token');
const userInfo = document.querySelector('#user-info');
const selectEmployee = document.querySelector('#select-employee');
const selectService = document.querySelector('.service-options-container');
const selectAccessory = document.querySelector('.accessory-options-container');
const dateInput = document.getElementById('dateInput');
const btnAdd = document.querySelector('#btn-add-car-repair-direct');
/**********************************************/

btnAdd.addEventListener('click', async () => {
  const customerName = document.getElementById('customer').value;
  const phone = document.getElementById('phone').value;
  const plate = document.getElementById('plate').value;
  const date = dateInput.value;

  const selectedOptionIndex = selectEmployee.selectedIndex;
  const selectedOption = selectEmployee.options[selectedOptionIndex];
  console.log(selectedOption);

  let services = document.querySelectorAll(
    '.service-options-container input[type="checkbox"]:checked'
  );

  const _services = [];
  services = services.forEach((checkbox) => {
    _services.push(checkbox.getAttribute('id'));
  });

  let accessories = document.querySelectorAll(
    '.accessory-options-container input[type="checkbox"]:checked'
  );

  const _accessories = [];
  accessories.forEach((checkbox) => {
    const id = checkbox.getAttribute('id');
    const num = +document.querySelector(`input[accessory-id="${id}"]`).value;

    _accessories.push(`${id},${num}`);
  });

  const data = {
    customer_name: customerName,
    customer_phone: phone,
    date: date,
    plate: plate,
    employee_id: selectedOption.getAttribute('employee-id'),
    services: _services,
    accessories: _accessories,
  };

  console.log(data);

  try {
    await call_api('POST', `${CAR_REPAIR_API}/direct`, data, {
      Authorization: `Bearer ${token}`,
    });

    alert('Thêm thành công');

    window.location.reload();
  } catch (err) {
    alert(err.message);
  }
});

const initDate = () => {
  const currentDate = new Date();
  const year = currentDate.getFullYear();
  const month = String(currentDate.getMonth() + 1).padStart(2, '0');
  const day = String(currentDate.getDate()).padStart(2, '0');
  const formattedDate = `${year}-${month}-${day}`;
  dateInput.value = formattedDate;
};

const parseEmployee = async () => {
  const r = await call_api('GET', `${USER_API}?size=10000&role=2`, null, {
    Authorization: `Bearer ${token}`,
  });

  const data = r.data.content;
  console.log(data);

  data.forEach((ele, idx) => {
    selectEmployee.insertAdjacentHTML(
      'beforeend',
      `
      <option employee-id=${ele.id}>${ele.fullname}</option>
    `
    );
  });
};

const parseService = async () => {
  const r = await call_api('GET', `${SERVICE_API}?size=10000`, null, {
    Authorization: `Bearer ${token}`,
  });

  const data = r.data.content;
  console.log(data);

  data.forEach((ele, idx) => {
    selectService.insertAdjacentHTML(
      'beforeend',
      `
      <div class="service-options">
        <div class="service-option">
          <input type="checkbox" id="${ele.id}">
          <label for="${ele.id}">${ele.name}</label>
        </div>
      </div>
      `
    );
  });
};

const parseAccessory = async () => {
  const r = await call_api('GET', `${ACCESSORY_API}?size=10000`, null, {
    Authorization: `Bearer ${token}`,
  });

  const data = r.data.content;
  console.log(data);

  data.forEach((ele, idx) => {
    selectAccessory.insertAdjacentHTML(
      'beforeend',
      `
      <div class="accessory-option">
        <input type="checkbox" id="${ele.id}" value="1">
        <label for="${ele.id}">${ele.name}</label>
        <input accessory-id="${ele.id}" type="number" class="quantity-input" min="1" value="1">
      </div>
      `
    );
  });
};

const main = async () => {
  const userData = await checkRole('ADMIN,SUPPORT');
  console.log(userData);

  userInfo.textContent = `Xin chào, ${userData.fullname}`;

  initDate();

  await parseEmployee();
  await parseService();
  await parseAccessory();
};

main();
