import axios from "axios";

const api = axios.create({
  baseURL: "http://localhost:8080/api", // porta do Spring Boot
  timeout: 8000,
});

export default api;