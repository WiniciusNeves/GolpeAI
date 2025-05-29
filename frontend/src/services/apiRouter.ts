import axios from "axios";

const apiRouter = axios.create({
    baseURL: "http://localhost:8080/api/",
});


export default apiRouter;
