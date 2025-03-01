# Sử dụng JDK 17 để build ứng dụng
FROM eclipse-temurin:17-jdk AS build

# Đặt thư mục làm việc
WORKDIR /app

# Copy toàn bộ source code vào container
COPY . .

# Kiểm tra nội dung thư mục trước khi build
RUN ls -la /app

# Cài đặt dependencies và build ứng dụng
RUN chmod +x mvnw && ./mvnw clean package -DskipTests

# Chạy bước mới để chỉ lấy file JAR đã build
FROM eclipse-temurin:17-jdk

WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Kiểm tra xem file JAR có tồn tại không
RUN ls -la /app

# Chạy ứng dụng
CMD ["java", "-jar", "app.jar"]