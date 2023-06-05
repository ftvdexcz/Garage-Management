## Garage Management

> Chạy Service và Mysql (Port mysql expose đang để là 3307, username=longdq, password=123456)

```
docker-compose up -d
```

> API Docs

Thay đổi trong Environment Postman cho đúng:

- Các service sẽ vào qua API Gateway có port 8765 + tên service

![image](https://github.com/ftvdexcz/Garage-Management/assets/60183306/047545f5-4c08-4377-9453-8480064569b2)

Ví dụ endpoint /accessory của accessory-service sẽ có URI: `localhost:8765/accessory-service/accessory`

- Đăng nhập lấy token xong thay đổi trong này không thì bị lỗi 401, có 3 role admin, customer, support
