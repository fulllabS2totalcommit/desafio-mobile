import axios from 'axios';


const api= axios.create(
    {
        baseURL:"https://desafio.mobfiq.com.br/"
    }
);


export default api;