## Garage Management 

> Chạy mysql (phải chạy trước Service do đang chưa fix được lỗi viết trong cùng 1 docker-compose file).
> Port expose ra là port 3308, có thể dùng kết nối để xem db 
```
cd mysql-script
docker-compose up -d
```


> Chạy các Service 
```
// quay lại thư mục gốc nếu đang ở mysql-script 
cd .. 
docker-compose up -d  
```

> API Docs 

Thay đổi trong Environment Postman cho đúng:

- Các service sẽ vào qua API Gateway có port 8765 + tên service 

![image](https://github.com/ftvdexcz/Garage-Management/assets/60183306/047545f5-4c08-4377-9453-8480064569b2)

Ví dụ endpoint /accessory của accessory-service sẽ có URI: ``` localhost:8765/accessory-service/accessory ```

- Đăng nhập lấy token xong thay đổi trong này không thì bị lỗi 401, có 3 role admin, customer, support 