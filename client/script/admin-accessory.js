'use stricts';

/**********************************************/
const token = localStorage.getItem('token');
const tbody = document.querySelector('tbody');
const userInfo = document.querySelector('#user-info');
const btnUpdate = document.querySelector('#btn-update');
const btnAdd = document.querySelector('#btn-add');
const btnBuy = document.querySelector('#btn-buy');

idInput = document.querySelector('#accessory_id');
nameInput = document.querySelector('#accessory_name');
priceInput = document.querySelector('#accessory_price');
quantityInput = document.querySelector('#accessory_quantity');
suppierIdInput = document.querySelector('#supplier_id');
searchInput = document.querySelector('#search-input');
/**********************************************/

let id;

searchInput.addEventListener('input', async function () {
  await fetchData(1, 10, `${ACCESSORY_API}?name=${this.value}`, template);
});

btnBuy.addEventListener('click', async () => {
  const currentDate = new Date();

  // Lấy thông tin ngày, tháng, năm
  const year = currentDate.getFullYear();
  const month = String(currentDate.getMonth() + 1).padStart(2, '0');
  const day = String(currentDate.getDate()).padStart(2, '0');

  // Định dạng ngày theo format yyyy-mm-dd
  const formattedDate = `${year}-${month}-${day}`;

  console.log(formattedDate); // Kết quả: yyyy-mm-dd

  const data = {
    quantity: +document.querySelector('#accessory_quantity_buy').value,
    amount: +document.querySelector('#accessory_total_buy').value,
    purchased_date: formattedDate,
    employee_id: id,
  };

  console.log(data);

  const accessory_id = document.querySelector('#accessory_id_buy').value;
  try {
    const r = await call_api(
      'POST',
      `${ACCESSORY_SERVICE}/accessory/${accessory_id}/purchase`,
      data,
      {
        Authorization: `Bearer ${token}`,
      }
    );

    console.log(r);
    alert('Nhập thành công');

    await new Promise((resolve) => {
      setTimeout(resolve, 500);
    });

    window.location.reload();
  } catch (err) {
    console.error(err.message);
    alert('Có lỗi xảy ra');
  }
});

btnAdd.addEventListener('click', async () => {
  const data = {
    name: document.querySelector('#accessory_name_add').value,
    price: +document.querySelector('#accessory_price_add').value,
    quantity: +document.querySelector('#accessory_quantity_add').value,
    supplier_id: document.querySelector('#supplier_id_add').value,
  };

  console.log(data);

  try {
    const r = await call_api('POST', `${ACCESSORY_SERVICE}/accessory`, data, {
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
    quantity: +quantityInput.value,
    supplier_id: suppierIdInput.value,
  };

  console.log(data);

  try {
    const r = await call_api(
      'PATCH',
      `${ACCESSORY_SERVICE}/accessory/${idInput.value}`,
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

  const id = item.querySelector('td[accessoy-id]').textContent;
  const name = item.querySelector('td[name]').textContent;
  const price = item.querySelector('td[price]').textContent;
  const quantity = item.querySelector('td[quantity]').textContent;
  const suppier_id = item.querySelector('td[supplier-id]').textContent;

  idInput.value = id;
  nameInput.value = name;
  priceInput.value = +price;
  quantityInput.value = +quantity;
  suppierIdInput.value = suppier_id;
};

const deleteCB = async (item) => {
  var result = confirm('Xóa linh kiện ?');
  if (result) {
    const id = item.querySelector('td[accessoy-id]').textContent;

    try {
      await call_api(
        'DELETE',
        `${ACCESSORY_SERVICE}/accessory/${id}`,
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

const buyCB = async (item) => {
  const quantityInputBuy = document.querySelector('#accessory_quantity_buy');
  quantityInputBuy.addEventListener('input', (e) => {
    console.log('change');
    totalInputBuy.value = +e.target.value * price;
  });

  console.log(item);

  const id = item.querySelector('td[accessoy-id]').textContent;
  const name = item.querySelector('td[name]').textContent;
  const price = item.querySelector('td[price]').textContent;
  const quantity = item.querySelector('td[quantity]').textContent;
  const suppier_id = item.querySelector('td[supplier-id]').textContent;

  const idInputBuy = document.querySelector('#accessory_id_buy');
  const nameInputBuy = document.querySelector('#accessory_name_buy');
  const priceInputBuy = document.querySelector('#accessory_price_buy');

  const suppierIdInputBuy = document.querySelector('#supplier_id_buy');
  const totalInputBuy = document.querySelector('#accessory_total_buy');

  idInputBuy.value = id;
  nameInputBuy.value = name;
  priceInputBuy.value = +price;
  quantityInput.value = +quantity;
  suppierIdInputBuy.value = suppier_id;

  totalInputBuy.value = +quantityInputBuy.value * price;
};

const template = (data) => {
  var html = '';
  $.each(data, function (index, item) {
    html += `
    <tr>
    <td stt>${index + 1}</td>
    <td accessoy-id>${item.id}</td>
    <td name>${item.name}</td>
    <td price>${item.price}</td>
    <td quantity>${item.quantity}</td>
    <td supplier-name>${item.supplier_name}</td>
    <td supplier-id style="display: none">${item.supplier_id}</td>
    <td>
      <button id="btn-update-item" data-toggle="modal" data-target="#updateModal" onclick="updateCB(this.closest('tr'))">
        <ion-icon name="clipboard-outline"></ion-icon>
      </button>
      <button id="btn-delete-item" onclick="deleteCB(this.closest('tr'))">
        <ion-icon name="trash-outline"></ion-icon>
      </button>
      <button id="btn-buy-item" data-toggle="modal" data-target="#buyModal" onclick="buyCB(this.closest('tr'))">
      <ion-icon name="cart-outline"></ion-icon>
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
  id = userData.id;

  await fetchData(1, 10, ACCESSORY_API, template);
};

main();
