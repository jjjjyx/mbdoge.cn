import axios from 'axios'
import { Message, MessageBox, Notification } from 'element-ui'
import Crazy from './crazy'
import config from '~/config'

const { baseURL, tokenKey, tokenPrefix } = config

let MESSAGE_LEVEL = { 0: 'success', 1: 'error', 2: 'warning', 3: 'info' }
const secret = 'Process finished with exit code 0'

const instance = axios.create({
    baseURL,
    headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/x-www-form-urlencoded'
    },
    withCredentials: true,
    transformRequest: [function (data) {
        // Do whatever you want to transform the data
        if (data) {
            if (data instanceof FormData) {
                return data
            }
            let fd = new FormData()
            if (data && typeof data === 'object') {
                for (let [k, v] of Object.entries(data)) {
                    if (Array.isArray(v)) {
                        v.forEach((it) => fd.append(k, it))
                    } else {
                        fd.append(k, v)
                    }
                }
            }
            return fd
        }
        return data
    }],
    paramsSerializer (params) {
        let searchParams = new URLSearchParams()
        for (let [k, v] of Object.entries(params)) {
            let key = k // Crazy.encrypt(k, secret)
            if (Array.isArray(v)) {
                v.forEach((it) => searchParams.append(key, it))
            } else {
                searchParams.append(key, v)
            }
        }
        return searchParams.toString()
    }
})


instance.interceptors.request.use(
    config => {
        return config
    },
    error => {
        // do something with request error
        // console.log(error) // for debug
        return Promise.reject(error)
    }
)
instance.interceptors.response.use(
    /**
     * If you want to get http information such as headers or status
     * Please return  response => response
     */

    /**
     * Determine the request status by custom code
     * Here is just an example
     * You can also judge the status by HTTP Status Code
     */
    response => {
        let data = response.data
        if (response.headers['content-type'].indexOf('application/json') >= 0) {
            // return JSON.parse(Crazy.decrypt(data, secret))
            return data
        }
        return response
    },
    async error => {
        if (axios.isCancel(error)) {
            Notification.warning({
                title: '提示',
                message: error.message
            })
        } else if (!error.response) {
            // 连接服务器失败
            // 或者跨域了
            console.log(error)
            MessageBox.alert('网络异常，稍后重试', '错误', {
                lockScroll: true,
                center: true,
                showClose: false,
                confirmButtonText: '点击刷新页面',
                type: 'error',
                callback: () => {
                    location.reload(true)
                }
            })
            return Promise.reject(error)
        }

        let statusCode = error.response.status
        if (statusCode === 401) {
            clearToken()
        } else if (statusCode === 403) {
            // 无权访问
            document.write('禁止访问')
            return
        }
        // if (!(error.response.config.url.indexOf('auth') && error.response.config.method === 'get')) {
        let data = error.response.data
        // console.group(error.response.config.url + ': error')
        // console.log(error.response)
        // console.log(data)
        // data = JSON.parse(Crazy.decrypt(data, secret))
        // console.log(data)
        // console.groupEnd()
        console.log(data)
        if (data && data.level !== undefined) {
            let level = MESSAGE_LEVEL[data.level]
            Message.closeAll()
            Message({
                message: data.msg,
                type: level,
                duration: 5 * 1000
            })
        }
        // }
        return Promise.reject(error)
    }
)

// const
export function setToken (t) {
    // localStorage.setItem(tokenKey, t)
    // let Cookie = require('js-cookie')
    // Cookie.set(tokenKey, t)
}

export function clearToken () {
    // localStorage.removeItem(tokenKey)
    // let Cookie = require('js-cookie')
    // Cookie.remove(tokenKey)
}

export default instance
