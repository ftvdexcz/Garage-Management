'use stricts';

/**********************************************/
const token = localStorage.getItem('token');
const tbody = document.querySelector('tbody');
const userInfo = document.querySelector('#user-info');
const switchInput = document.getElementById('mySwitch');

let _status = 'false';
let id;
/**********************************************/

switchInput.addEventListener('change', async function (event) {
  if (event.target.checked) {
    console.log('Hoàn thành');
    document.querySelector('div[status-switch]').textContent =
      'Trạng thái (Hoàn thành)';

    _status = 'true';

    await fetchData(
      1,
      7,
      `${CAR_REPAIR_API}/customer?customerId=${id}&status=${_status}`,
      template
    );
  } else {
    console.log('Chưa hoàn thành');
    document.querySelector('div[status-switch]').textContent =
      'Trạng thái (Chưa hoàn thành)';

    _status = 'false';

    await fetchData(
      1,
      7,
      `${CAR_REPAIR_API}/customer?customerId=${id}&status=${_status}`,
      template
    );
  }
});

const detailCB = (item) => {
  console.log(item);

  const id = item.querySelector('td[repair-id]').textContent;

  const url = `${DETAIL_CAR_REPAIR_DIRECT_HTML}?id=${encodeURIComponent(id)}`;

  redirect(url);
};

const template = (data) => {
  var html = '';
  $.each(data, function (index, item) {
    html += `
    <tr>
    <td stt>${index + 1}</td>
    <td repair-id>${item.id}</td>
    <td date>${item.date.split('T')[0]}</td>
    <td plate>${item.plate}</td>
    <td plate class="${item.status ? 'done' : 'pending'}">${
      item.status ? 'Hoàn thành' : 'Chưa hoàn thành'
    }</td>
    <td note>${item.note ? item.note : ''}</td>
    
    <td>
      <button id="btn-update-item" onclick="detailCB(this.closest('tr'))">
      <ion-icon name="create-outline"></ion-icon>
      
    </td>
    </tr>
      `;
  });
  return html;
};

const main = async () => {
  const userData = await checkRole('CUSTOMER');
  console.log(userData);

  userInfo.textContent = `Xin chào, ${userData.fullname}`;

  id = userData.id;

  await fetchData(
    1,
    7,
    `${CAR_REPAIR_API}/customer?customerId=${id}&status=${_status}`,
    template
  );
};

main();
