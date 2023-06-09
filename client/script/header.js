document.querySelector('.header').innerHTML = `<div class="logo">
<div class="brandName">Dashboard</div>
</div>
<div class="action">
<div class="items">
  <div class="item">
    <a href="admin-accessory.html">
      <i class="pi pi-home"></i>
      Quản lý linh kiện
    </a>
  </div>
  <div class="item">
    <a href="admin-service.html">
      <i class="pi pi-home"></i>
      Quản lý dịch vụ
    </a>
  </div>
  <div class="item">
    <a href="admin-customer.html">
      <i class="pi pi-home"></i>
      Quản lý khách hàng
    </a>
  </div>
  <div class="item">
    <a href="admin-employee.html">
      <i class="pi pi-home"></i>
      Quản lý nhân viên 
    </a>
  </div>
  <div class="item">
    <a href="admin-supplier.html">
      <i class="pi pi-home"></i>
      Quản lý nhà cung cấp
    </a>
  </div>
  <div class="item">
    <a href="admin-car-repair.html">
      <i class="pi pi-home"></i>
      Quản lý lịch sửa
    </a>
  </div>
  <div class="item">
    <a href="employee-salary.html">
      <i class="pi pi-home"></i>
      Tính công nhân viên
    </a>
  </div>
  <div class="item">
    <a href="analys-revenue-customer.html">
      <i class="pi pi-home"></i>
      TK doanh thu khách hàng
    </a>
  </div>
</div>
</div>`;

document.querySelector('#logout-btn').addEventListener('click', async (e) => {
  e.preventDefault();

  try {
    const data = await call_api(
      'POST',
      LOGOUT_API,
      {},
      {
        Authorization: `Bearer ${localStorage.getItem('token')}`,
      }
    );

    localStorage.setItem('token', data); // set new token

    await new Promise((resolve) => {
      setTimeout(resolve, 2000);
    });

    redirect(SIGNIN_HTML);
  } catch (err) {
    console.error(err);
    redirect(ERROR_500_HTML);
  }
});
