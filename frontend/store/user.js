'use strict'


import {passwordHash} from '~/tools/common'

const state = () => ({

})


const getters = {
    // // 是否登录
    // isLogin: state => state.username !== '' && (+new Date() - state.validateTime) < 60 * 60 * 1000, // 小于一小时
    // // 是否超时
    // isTimeOut: state => (+new Date() - state.validateTime) < 60 * 60 * 1000
}

// actions
const actions = {
    auth({commit, state}) {
        // return this.$axios.$get('/auth')

    },

    // logout({ commit, state }) {
    //     return this.$axios.$post('/auth')
    // },

    async login({commit, state, dispatch}, {username, password}) {
        const hashPass = await passwordHash(password)
        try {
            const resp = await this.$auth.login({
                data: {
                    username, password: hashPass
                }
            })
        } catch (e) {
            console.log(e)
        }

    },

    // user logout
    logout() {
        return this.$auth.logout()
    },
    // async checkUser({commit, state}) {
    //     let user = await userApi.auth()
    //     commit('USER_SET_INFO', user)
    //     return user
    // },
    // mergeUser({commit}, obj) {
    //     commit('MERGEUSER', obj)
    // }
}
const mutations = {
}

export default {
    namespaced: true,
    state,
    getters,
    actions,
    mutations
}
