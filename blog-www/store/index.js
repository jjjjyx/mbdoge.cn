'use strict'

import config from '~/config'
import axios from 'axios'
const { baseURL, tokenKey, tokenPrefix } = config

const state = () => ({
    aa: "test--11111"
})

// const defaultUser = cloneDeep(state)
const getters = {

}

// actions
const actions = {
    async nuxtServerInit ({ commit, dispatch }, { req }) {

        if (req.headers.cookie) {

            try {
                let { data } = await axios.get(baseURL + '/api/v1/auth', {
                    headers: {
                        Cookie: req.headers.cookie
                    }
                })
                commit('user/USER_SET_INFO', data)
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
