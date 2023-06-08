'use stricts';

/**********************************************/
const token = localStorage.getItem('token');
const tbody = document.querySelector('tbody');
const userInfo = document.querySelector('#user-info');
const btnUpdate = document.querySelector('#btn-update');
const btnAdd = document.querySelector('#btn-add');

idInput = document.querySelector('#employee_id');
fullnameInput = document.querySelector('#employee_fullname');
usernameInput = document.querySelector('#employee_username');
phoneInput = document.querySelector('#employee_phone');
emailInput = document.querySelector('#employee_email');
addressInput = document.querySelector('#employee_address');
roleInput = document.querySelector('#employee_role');
searchInput = document.querySelector('#search-input');
/**********************************************/
searchInput.addEventListener('input', async function () {
  await fetchData(1, 10, `${USER_API}?role=1,2&name=${this.value}`, template);
});

btnAdd.addEventListener('click', async () => {
  const data = {
    username: document.querySelector('#employee_username_add').value,
    fullname: document.querySelector('#employee_fullname_add').value,
    password: document.querySelector('#employee_password_add').value,
    phone: document.querySelector('#employee_phone_add').value,
    email: document.querySelector('#employee_email_add').value,
    address: document.querySelector('#employee_address_add').value,
    role: +document.querySelector('#employee_role_add').value,
  };

  console.log(data);

  try {
    const r = await call_api('POST', `${USER_SERVICE}/user`, data, {
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
    username: usernameInput.value,
    fullname: fullnameInput.value,
    phone: phoneInput.value,
    email: emailInput.value,
    address: addressInput.value,
    role: +roleInput.value,
  };

  console.log(data);

  try {
    const r = await call_api(
      'PATCH',
      `${USER_SERVICE}/user/${idInput.value}`,
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

  const id = item.querySelector('td[employee-id]').textContent;
  const fullname = item.querySelector('td[fullname]').textContent;
  const username = item.querySelector('td[username]').textContent;
  const phone = item.querySelector('td[phone]').textContent;
  const email = item.querySelector('td[email]').textContent;
  const address = item.querySelector('td[address]').textContent;
  let role = item.querySelector('td[role]').textContent;

  if (role === 'SUPPORT') role = 2;
  else if (role === 'ADMIN') role = 1;

  idInput.value = id;
  fullnameInput.value = fullname;
  usernameInput.value = username;
  phoneInput.value = phone;
  emailInput.value = email;
  addressInput.value = address;
  roleInput.value = +role;
};

const deleteCB = async (item) => {
  var result = confirm('Xóa nhân viên ?');
  if (result) {
    const id = item.querySelector('td[employee-id]').textContent;

    try {
      await call_api(
        'DELETE',
        `${USER_SERVICE}/user/${id}`,
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
    <td employee-id>${item.id}</td>
    <td fullname>${item.fullname}</td>
    <td username>${item.username}</td>
    <td phone>${item.phone}</td>
    <td email>${item.email}</td>
    <td address>${item.address}</td>
    <td role>${item.role}</td>
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
  const userData = await checkRole('ADMIN');
  console.log(userData);

  userInfo.textContent = `Xin chào, ${userData.fullname}`;

  await fetchData(1, 10, `${USER_API}?role=1,2`, template);
};

main();
