import axios from "axios";

// 创建axios实例
const service = axios.create({
  baseURL: 'http://172.16.102.135:9001',
  timeout: 20000
})

export default service