'use stricts';

/**********************************************/
const token = localStorage.getItem('token');
const tbody = document.querySelector('tbody');
const userInfo = document.querySelector('#user-info');
const btnUpdate = document.querySelector('#btn-update');
const btnAdd = document.querySelector('#btn-add');

idInput = document.querySelector('#service_id');
nameInput = document.querySelector('#service_name');
priceInput = document.querySelector('#service_price');
noteInput = document.querySelector('#service_note');
searchInput = document.querySelector('#search-input');
/**********************************************/
searchInput.addEventListener('input', async function () {
  await fetchData(1, 10, `${SERVICE_API}?name=${this.value}`, template);
});

btnAdd.addEventListener('click', async () => {
  const data = {
    name: document.querySelector('#service_name_add').value,
    price: +document.querySelector('#service_price_add').value,
    note: document.querySelector('#service_note_add').value,
  };

  console.log(data);

  try {
    const r = await call_api('POST', `${ACCESSORY_SERVICE}/service`, data, {
      Authorization: `Bearer ${token}`,
    });

    console.log(r);
    alert('Thêm thành công');

    await new Promise((resolve) => {
      setTimeout(resolve, 500);
    });

    window.location.reload();
  } catch (err) {
    console.error(err.message);
    alert('Có lỗi xảy ra');
  }
});

btnUpdate.addEventListener('click', async () => {
  const data = {
    name: nameInput.value,
    price: +priceInput.value,
    note: noteInput.value,
  };

  console.log(data);

  try {
    const r = await call_api(
      'PATCH',
      `${ACCESSORY_SERVICE}/service/${idInput.value}`,
      data,
      {
        Authorization: `Bearer ${token}`,
      }
    );

    console.log(r);
    alert('Cập nhật thành công');

    await new Promise((resolve) => {
      setTimeout(resolve, 500);
    });

    window.location.reload();
  } catch (err) {
    console.error(err.message);
    alert('Có lỗi xảy ra');
  }
});

const updateCB = (item) => {
  console.log(item);

  const id = item.querySelector('td[service-id]').textContent;
  const name = item.querySelector('td[name]').textContent;
  const price = item.querySelector('td[price]').textContent;
  const note = item.querySelector('td[note]').textContent;

  idInput.value = id;
  nameInput.value = name;
  priceInput.value = +price;
  noteInput.value = note;
};

const deleteCB = async (item) => {
  var result = confirm('Xóa dịch vụ ?');
  if (result) {
    const id = item.querySelector('td[service-id]').textContent;

    try {
      await call_api(
        'DELETE',
        `${ACCESSORY_SERVICE}/service/${id}`,
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
    <td service-id>${item.id}</td>
    <td name>${item.name}</td>
    <td price>${item.price}</td>
    <td note>${item.note}</td>
    <td>
      <button id="btn-update-item" data-toggle="modal" data-target="#updateModal" onclick="updateCB(this.closest('tr'))">
        <ion-icon name="clipboard-outline"></ion-icon>
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
  const userData = await checkRole('ADMIN,SUPPORT');
  console.log(userData);

  userInfo.textContent = `Xin chào, ${userData.fullname}`;

  await fetchData(1, 10, SERVICE_API, template);
};

main();
