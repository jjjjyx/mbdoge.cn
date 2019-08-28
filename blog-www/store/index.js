'use strict'

import config from '~/config'
import axios from 'axios'

const { baseURL, tokenKey, tokenPrefix } = config

const state = () => ({
    aa: "test--11111"
})

const getters = {

}

// actions
const actions = {
    async nuxtServerInit ({ commit, dispatch }, { req }) {
        // console.log(route)
        // console.log(Object.keys(a))
        // console.log('2222222222222',a.app.router.options.routes)
        if (req.headers.cookie) {
            try {
                let { data } = await axios.get(baseURL + '/api/v1/auth', {
                    headers: {
                        Cookie: req.headers.cookie
                    }
                })
                commit('user/USER_SET_INFO', data)
                dispatch('app/generateRoutes')
            } catch (e) {
                console.log('尚未登录')
            }
        }
        // await dispatch('user/checkUser')
    },

}
const mutations = {

}

export default {
    state,
    getters,
    actions,
    mutations
}
