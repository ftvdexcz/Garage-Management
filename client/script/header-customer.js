document.querySelector('.header').innerHTML = `<div class="logo">
<div class="brandName">Khách hàng</div>
</div>
<div class="action">
<div class="items">
  <div class="item">
    <a href="create-car-repair-appointment.html">
      <i class="pi pi-home"></i>
      Đặt lịch online
    </a>
  </div>
  <div class="item">
    <a href="customer-car-repair.html">
      <i class="pi pi-home"></i>
      Lịch sử
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
