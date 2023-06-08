'use stricts';

const token = localStorage.getItem('token');
const tbody = document.querySelector('tbody');
const userInfo = document.querySelector('#user-info');

const template = (data) => {
  var html = '';
  $.each(data, function (index, item) {
    html += `
    <tr>
    <td stt>${index + 1}</td>
    <td employee-id>${item.employee_id}</td>
    <td fullname>${item.fullname}</td>
    <td phone>${item.phone}</td>
    <td email>${item.email}</td>
    <td address>${item.address}</td>
    <td role>${item.salary} VNĐ</td>
  
    </tr>
      `;
  });
  return html;
};

document.querySelector('#btn-salary').addEventListener('click', async () => {
  const startdate = document.querySelector('#startdate').value;
  const enddate = document.querySelector('#enddate').value;

  const dataSource = await call_api(
    'POST',
    `${CAR_REPAIR_SERVICE}/salary`,
    {
      start_date: startdate,
      end_date: enddate,
    },
    {
      Authorization: `Bearer ${token}`,
    }
  );

  console.log(dataSource.data);

  $('#pagination-container').pagination({
    dataSource: dataSource.data,
    pageSize: 7,
    callback: function (data, pagination) {
      // template method of yourself
      var html = template(data);
      $('#data-container').html(html);
    },
  });
});

const main = async () => {
  const userData = await checkRole('ADMIN');
  console.log(userData);

  userInfo.textContent = `Xin chào, ${userData.fullname}`;
  id = userData.id;
};

main();
