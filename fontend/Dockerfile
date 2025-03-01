# Sử dụng Node.js 20.18
FROM node:20.18

# Đặt thư mục làm việc trong container
WORKDIR /app

# Copy package.json và cài đặt dependencies
COPY package.json package-lock.json ./
RUN npm install

# Copy toàn bộ code frontend vào container
COPY . .

# Build frontend
RUN npm run build

# Chạy server
CMD ["npm", "run", "preview"]