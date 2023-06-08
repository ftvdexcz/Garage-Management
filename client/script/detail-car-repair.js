'use stricts';

/**********************************************/
const token = localStorage.getItem('token');
const tbody = document.querySelector('tbody');
const userInfo = document.querySelector('#user-info');
const selectService = document.querySelector('#service-used');
const selectAccessory = document.querySelector('#accessory-used');
/**********************************************/
let totalAmount = 0;
const queryString = window.location.search;
const urlParams = new URLSearchParams(queryString);
const id = urlParams.get('id');
console.log(id);

let _status;

const main = async () => {
  const userData = await checkRole('ADMIN');
  console.log(userData);

  userInfo.textContent = `Xin chào, ${userData.fullname}`;

  let r = await call_api('GET', `${CAR_REPAIR_API}/${id}`, null, {
    Authorization: `Bearer ${token}`,
  });

  let data = r.data;

  _status = data.status;

  console.log(data);

  r = await call_api('GET', `${USER_API}/${data.car.customer_id}`, null, {
    Authorization: `Bearer ${token}`,
  });

  const userdata = r.data;

  console.log(userdata);

  document.querySelector('#customer').placeholder = userdata.fullname;
  document.querySelector('#phone').placeholder = userdata.phone;
  document.querySelector('#plate').placeholder = data.car.plate;
  document.getElementById('dateInput').value = data.date.split('T')[0];

  r = await call_api('GET', `${USER_API}/${data.employee_id}`, null, {
    Authorization: `Bearer ${token}`,
  });

  const employeedata = r.data;

  console.log(employeedata);

  document.querySelector('#employee').placeholder = employeedata.fullname;

  data.accessoryUseds.forEach(async (ele, idx) => {
    r = await call_api(
      'GET',
      `${CAR_REPAIR_SERVICE}/accessory-used/${ele.id}`,
      null,
      {
        Authorization: `Bearer ${token}`,
      }
    );

    let data = r.data;
    const quantity = data.quantity;
    const amount = data.amount;

    totalAmount += amount;

    r = await call_api('GET', `${ACCESSORY_API}/${data.accessory_id}`, null, {
      Authorization: `Bearer ${token}`,
    });

    const accessoryName = r.data.name;

    selectAccessory.insertAdjacentHTML(
      'beforeend',
      `
        <div style="display:flex">
        <p>Tên linh kiện: ${accessoryName}</p>
        <span style="margin: 0px 16px">-</span>
        <p>Sử dụng: ${quantity}</p>
        <span style="margin: 0px 16px">-</span>
        <p>Giá: ${amount} VNĐ</p>
        </div>
      `
    );
  });

  data.serviceUseds.forEach(async (ele, idx) => {
    r = await call_api(
      'GET',
      `${CAR_REPAIR_SERVICE}/service-used/${ele.id}`,
      null,
      {
        Authorization: `Bearer ${token}`,
      }
    );

    let data = r.data;
    const amount = data.amount;
    totalAmount += amount;

    r = await call_api('GET', `${SERVICE_API}/${data.service_id}`, null, {
      Authorization: `Bearer ${token}`,
    });

    const serviceName = r.data.name;

    selectService.insertAdjacentHTML(
      'beforeend',
      `
        <div style="display:flex">
        <p>Tên dịch vụ: ${serviceName}</p>
        <span style="margin: 0px 16px">-</span>
        <p>Giá: ${amount} VNĐ</p>
        </div>
      `
    );
  });
};

(async () => {
  await main();

  // tạm
  await new Promise((resolve) => {
    setTimeout(resolve, 1000);
  });

  document.querySelector('#totalAmount').textContent = `${totalAmount} VNĐ`;

  console.log(_status);
  if (_status) {
    document.querySelector('#btn-payment').style.display = 'none';

    r = await call_api(
      'GET',
      `${CAR_REPAIR_SERVICE}/bill/car-repair/${id}`,
      null,
      {
        Authorization: `Bearer ${token}`,
      }
    );

    document.querySelector('#payment').innerHTML = `
    <p style="font-size: 20px; color: green">Đã thanh toán, Ngày: ${
      r.data.payment_date.split('T')[0]
    }</p>
    
    `;
  } else {
    document
      .querySelector('#btn-payment')
      .addEventListener('click', async () => {
        const currentDate = new Date();

        // Lấy thông tin ngày, tháng, năm
        const year = currentDate.getFullYear();
        const month = String(currentDate.getMonth() + 1).padStart(2, '0');
        const day = String(currentDate.getDate()).padStart(2, '0');

        // Định dạng ngày theo format yyyy-mm-dd
        const formattedDate = `${year}-${month}-${day}`;

        const data = {
          amount: totalAmount,
          payment_date: formattedDate,
          car_repair_id: id,
        };

        try {
          const r = await call_api(
            'POST',
            `${CAR_REPAIR_SERVICE}/bill/payment`,
            data,
            {
              Authorization: `Bearer ${token}`,
            }
          );

          console.log(r);

          alert('Thanh toán thành công');

          window.location.reload();
        } catch (err) {
          alert('Có lỗi xảy ra');
        }
      });
  }
})();
