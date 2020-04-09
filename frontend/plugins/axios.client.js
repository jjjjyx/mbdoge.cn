import Crazy from '~/tools/crazy'
import {Message, MessageBox, Notification} from "element-ui";
import config from "~/config";

const MESSAGE_LEVEL = {0: 'success', 1: 'error', 2: 'warning', 3: 'info'}
const {apiEncryptEnable, secret} = config
// 对客户端的错误进行一些提示
export default function ({$axios, redirect}, inject) {
    $axios.onResponse(response => {
        let data = response.data
        if (response.headers['content-type'].indexOf('application/json') >= 0) {
            if (apiEncryptEnable) {
                response.data = JSON.parse(Crazy.decrypt(data, secret))
            }
        }
        return response
    })
    $axios.onResponseError(error => {
        if ($axios.isCancel(error)) {
            Notification.warning({
                title: '提示',
                message: error.message
            })
        } else if (!error.response) {
            // 连接服务器失败
            // 或者跨域了
            MessageBox.alert('网络异常，稍后重试', '错误', {
                lockScroll: true,
                center: true,
                showClose: false,
                confirmButtonText: '点击刷新页面',
                type: 'error',
                callback: () => {
                    location.reload()
                }
            });

            return Promise.reject(error)
        }

        let statusCode = error.response.status
        // 如何清除用户信息是个问题
        if (statusCode === 401) {
            // 删除用户信息
            $axios.setToken(false)
            // Token.removeToken()
            // router.replace({ name: 'login', query: { '_': Date.now() } })
        } else if (statusCode === 403) {
            // 无权访问
            document.write('禁止访问')
            return
        }
        let data = error.response.data

        if (apiEncryptEnable) {
            data = JSON.parse(Crazy.decrypt(data, secret))
        }

        if (data && data.level !== undefined) {
            Message.closeAll()
            let level = MESSAGE_LEVEL[data.level]

            Message({
                message: data.msg,
                type: level,
                duration: 5 * 1000
            })
        }

        return Promise.reject(error)
    })
}
