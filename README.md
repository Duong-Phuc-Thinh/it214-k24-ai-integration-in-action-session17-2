# Báo Cáo & Hướng Dẫn Bài Tập 2: Triển Khai Lazy Loading Với @Cacheable

## 1. Tổng quan giải pháp

Hệ thống triển khai cơ chế **Lazy Loading** (Cache-Aside pattern) cho API lấy thực đơn nhà hàng bằng Spring Cache với `@Cacheable`:
- **Lần gọi đầu tiên (Cache Miss)**: Phương thức `getMenuByRestaurantId(Long id)` trong `RestaurantService` thực thi. Thread ngủ 3000ms giả lập truy vấn database phức tạp, log ra console dòng chữ báo hiệu truy vấn DB. Kết quả phản hồi được lưu tự động vào cache name `restaurantMenu` với key là `id` nhà hàng (ví dụ: `101`).
- **Các lần gọi tiếp theo (Cache Hit)**: Spring AOP intercept request, kiểm tra trong cache `restaurantMenu` thấy đã tồn tại key `#id` tương ứng nên trả về luôn dữ liệu mà KHÔNG gọi vào phương thức service, không tốn 3 giây `Thread.sleep` và không in log DB query.

---

## 2. Bảng kiểm tự đánh giá

| Tiêu chí | Mô tả | Trạng thái đạt |
| :--- | :--- | :---: |
| **Đúng annotation** | Đánh dấu `@Cacheable(value = "restaurantMenu", key = "#id")` trên phương thức service | ĐẠT |
| **Cache Miss (Lần 1)** | Gọi API lần đầu có log `>>> CACHE MISS: dang truy van Database...`, thời gian phản hồi ≥ 3000ms | ĐẠT |
| **Cache Hit (Lần 2+)** | Gọi API lần thứ hai cùng `restaurantId` không in log DB query, phản hồi tức thì | ĐẠT |
| **Thời gian lần 2** | Thời gian phản hồi < 20ms | ĐẠT |
| **Cache key đúng** | Sử dụng SpEL `#id` để định danh key động theo ID nhà hàng | ĐẠT |

---

## 3. Hướng dẫn kiểm thử với Postman / cURL

### Bước 1: Khởi động ứng dụng
Chạy class `RestaurantCacheApplication.java` hoặc sử dụng lệnh Maven:
```bash
mvn spring-boot:run
```

### Bước 2: Kiểm thử Request Lần 1 (Cache Miss)
- **Endpoint**: `GET http://localhost:8080/restaurants/101/menu`
- **Response Status**: `200 OK`
- **Response Body**:
```json
[
  {
    "id": 1,
    "restaurantId": 101,
    "dishName": "Phở Bò",
    "price": 55000.0
  },
  {
    "id": 2,
    "restaurantId": 101,
    "dishName": "Bún Chả",
    "price": 60000.0
  }
]
```
- **Thời gian phản hồi**: `~3005 ms`
- **Console Log**: Xuất hiện log `>>> CACHE MISS: dang truy van Database cho restaurantId: 101` kèm theo câu lệnh SQL JOIN/SELECT.

### Bước 3: Kiểm thử Request Lần 2 (Cache Hit)
- **Endpoint**: `GET http://localhost:8080/restaurants/101/menu`
- **Response Status**: `200 OK`
- **Thời gian phản hồi**: `< 15 ms`
- **Console Log**: Không xuất hiện thêm bất kỳ dòng log nào từ service hay SQL hibernate.