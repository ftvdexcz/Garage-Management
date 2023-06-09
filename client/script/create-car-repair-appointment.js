'use stricts';

/**********************************************/
const token = localStorage.getItem('token');
const userInfo = document.querySelector('#user-info');
const selectService = document.querySelector('.service-options-container');
const dateInput = document.getElementById('dateInput');
const btnAdd = document.querySelector('#btn-add-car-repair-appoiment');
/**********************************************/

let id;

btnAdd.addEventListener('click', async () => {
  const plate = document.getElementById('plate').value;
  const date = dateInput.value;

  let services = document.querySelectorAll(
    '.service-options-container input[type="checkbox"]:checked'
  );

  const _services = [];
  services = services.forEach((checkbox) => {
    _services.push(checkbox.getAttribute('id'));
  });

  const data = {
    date: date,
    plate: plate,
    services: _services,
    customer_id: id,
  };

  console.log(data);

  try {
    await call_api('POST', `${CAR_REPAIR_API}/appointment`, data, {
      Authorization: `Bearer ${token}`,
    });

    alert('Đặt lịch thành công');

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

const main = async () => {
  const userData = await checkRole('CUSTOMER');
  console.log(userData);

  userInfo.textContent = `Xin chào, ${userData.fullname}`;

  id = userData.id;

  initDate();
  await parseService();
};

main();
