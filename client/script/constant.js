// const USER_SERVICE = 'http://127.0.0.1:8765/user-service';
// const ACCESSORY_SERVICE = 'http://127.0.0.1:8765/accessory-service';
// const CAR_REPAIR_SERVICE = 'http://127.0.0.1:8765/car-repair-service';

const USER_SERVICE = 'http://171.241.62.13:8765/user-service';
const ACCESSORY_SERVICE = 'http://171.241.62.13:8765/accessory-service';
const CAR_REPAIR_SERVICE = 'http://171.241.62.13:8765/car-repair-service';

const VERIFY_TOKEN_API = `${USER_SERVICE}/auth/verify-token`;
const LOGOUT_API = `${USER_SERVICE}/auth/logout`;
const SIGNIN_API = `${USER_SERVICE}/auth/login`;
const ADMIN_DASHBOARD_HTML = 'admin-accessory.html';
const CUSTOMER_DASHBOARD_HTML = 'create-car-repair-appointment.html';
const CREATE_CAR_REPAIR_DIRECT_HTML = 'create-car-repair-direct.html';
const DETAIL_CAR_REPAIR_DIRECT_HTML = 'detail-car-repair.html';
const SIGNIN_HTML = 'signin.html';
const ERROR_403_HTML = 'forbidden.html';
const ERROR_500_HTML = 'server-error.html';

const ACCESSORY_API = `${ACCESSORY_SERVICE}/accessory`;
const SERVICE_API = `${ACCESSORY_SERVICE}/service`;
const SUPPLIER_API = `${ACCESSORY_SERVICE}/supplier`;
const USER_API = `${USER_SERVICE}/user`;
const CAR_REPAIR_API = `${CAR_REPAIR_SERVICE}/car-repair`;
