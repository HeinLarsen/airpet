import axios from 'axios'

export default axios.create({
  baseURL: "http://localhost:8080/demo",
  headers: {
    "Content-type": "application/json",
  }
});