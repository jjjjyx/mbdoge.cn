import Crazy from '~/tools/crazy'
import config from "~/config";

const {apiEncryptEnable, secret} = config
// 对服务端的
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
}
