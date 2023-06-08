'use stricts';

/**********************************************/
const token = localStorage.getItem('token');
const signinBtn = document.querySelector('#signin-btn');
const usernameInput = document.querySelector('#username');
const passwordInput = document.querySelector('#password');
const loginMessage = document.querySelector('#login-message');
const log_err = document.querySelector('#log-err');
/**********************************************/
if (token) {
  (async () => {
    try {
      const result = await verify_token(token);
      console.log(result);

      console.log('token verified');

      const role = result.data.role;
      console.log('role: ' + role);

      switch (role) {
        case 'ADMIN':
          redirect(ADMIN_DASHBOARD_HTML);
          break;

        case 'SUPPORT':
          // redirect(ADMIN_DASHBOARD_HTML);
          break;

        case 'CUSTOMER':
          // redirect(ADMIN_DASHBOARD_HTML);
          break;

        default:
          break;
      }
    } catch (error) {
      // error.logging();
    }
  })();
}

signinBtn.addEventListener('click', async (e) => {
  try {
    e.preventDefault();
    loginMessage.style.display = 'none';

    username = usernameInput.value.trim();
    password = passwordInput.value;

    if (username == '') throw new Error('Tên tài khoản không được để trống');

    if (password == '') throw new Error('Mật khẩu không được để trống');

    const jsonObj = await call_api('POST', SIGNIN_API, {
      username,
      password,
    });

    console.log(jsonObj);

    const data = jsonObj.data;
    const role = data.role;
    const token = data.token;

    localStorage.setItem('token', token);

    switch (role) {
      case 'ADMIN':
        redirect(ADMIN_DASHBOARD_HTML);
        break;

      case 'SUPPORT':
        // redirect(ADMIN_DASHBOARD_HTML);
        break;

      case 'CUSTOMER':
        // redirect(ADMIN_DASHBOARD_HTML);
        break;

      default:
        break;
    }
  } catch (err) {
    console.error(err);

    loginMessage.style.display = 'block';
    // log_err.textContent = err.message;
    log_err.textContent = 'Sai thông tin đăng nhập';
  }
});
