import axios from '~/tools/api'
import { passwordHash } from '~/tools/common'

class UserApi {
    static login (username, p) {
        return passwordHash(p).then((password) => {
            // let data = new FormData()
            // data.append('username', username)
            // data.append('password', password)
            return axios.post('/api/v1/auth', { username, password })
        }).then((e) => {
            return e
        })
    }

    // static Gq79OrdECi8lwe4EY0Jh (username, p) {
    //     return passwordHash(p).then((password) => {
    //         let data = new FormData()
    //         data.append('username', username)
    //         data.append('password', password)
    //         return axios.post('/api/v1/auth/Gq79OrdECi8lwe4EY0Jh', data)
    //     })
    // }
    static auth () {
        return axios.get('/api/v1/auth')
    }

    static logout () {
        // todo 退出登录删除 cookie
        return axios.post('/api/v1/auth/logout')
    }
}

export default UserApi
