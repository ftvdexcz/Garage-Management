'use stricts';

/**********************************************/
const token = localStorage.getItem('token');
const tbody = document.querySelector('tbody');
const userInfo = document.querySelector('#user-info');
const btnAdd = document.querySelector('#btn-add');
const searchInput = document.querySelector('#search-input');
const switchInput = document.getElementById('mySwitch');

let _status = 'false';
/**********************************************/

btnAdd.addEventListener('click', () => {
  redirect(CREATE_CAR_REPAIR_DIRECT_HTML);
});

switchInput.addEventListener('change', async function (event) {
  if (event.target.checked) {
    console.log('Hoàn thành');
    document.querySelector('div[status-switch]').textContent =
      'Trạng thái (Hoàn thành)';

    _status = 'true';

    await fetchData(
      1,
      7,
      `${CAR_REPAIR_API}?plate=${searchInput.value}&status=${_status}`,
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
      `${CAR_REPAIR_API}?plate=${searchInput.value}&status=${_status}`,
      template
    );
  }
});

searchInput.addEventListener('input', async function () {
  await fetchData(
    1,
    7,
    `${CAR_REPAIR_API}?plate=${this.value}&status=${_status}`,
    template
  );
});

btnAdd.addEventListener('click', async () => {});

const detailCB = (item) => {
  console.log(item);

  const id = item.querySelector('td[repair-id]').textContent;

  const url = `${DETAIL_CAR_REPAIR_DIRECT_HTML}?id=${encodeURIComponent(id)}`;

  redirect(url);
};

const deleteCB = async (item) => {
  var result = confirm('Xóa lịch sửa ?');
  if (result) {
    const id = item.querySelector('td[repair-id]').textContent;

    try {
      await call_api(
        'DELETE',
        `${CAR_REPAIR_API}/${id}`,
        {},
        {
          Authorization: `Bearer ${token}`,
        }
      );

      alert('Xóa thành công');

      await new Promise((resolve) => {
        setTimeout(resolve, 500);
      });

      window.location.reload();
    } catch (err) {
      console.error(err.message);
      alert('Có lỗi xảy ra');
    }
  }
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
      </button>
      <button id="btn-delete-item" onclick="deleteCB(this.closest('tr'))">
        <ion-icon name="trash-outline"></ion-icon>
      </button>
    </td>
    </tr>
      `;
  });
  return html;
};

const main = async () => {
  const userData = await checkRole('ADMIN');
  console.log(userData);

  userInfo.textContent = `Xin chào, ${userData.fullname}`;

  await fetchData(1, 7, `${CAR_REPAIR_API}?status=${_status}`, template);
};

main();
