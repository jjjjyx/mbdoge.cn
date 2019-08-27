'use strict'


import cloneDeep from 'lodash/cloneDeep'
import userApi from '~/api/userApi'
// import store from '../index'

const state = () => ({
    'id': 0,
    'username': '',
    'nickname': '',
    'displayName': '-',
    'avatar': '',
    'url': '',
    'email': '',
    'status': 0,
    'createdAt': new Date(),
    'updatedAt': new Date(),
    'nextExpireTime': null,
    'expire': -1,
    'authorities': [],
    'validateTime': 0
})

const defaultUser = cloneDeep(state)
const getters = {
    // 是否登录
    isLogin: state => state.username !== '' && (+new Date() - state.validateTime) < 60 * 60 * 1000, // 小于一小时
    // 是否超时
    isTimeOut: state => (+new Date() - state.validateTime) < 60 * 60 * 1000
}

// actions
const actions = {
    async login ({ commit, state }, { username, password }) {
        // commit("USER_SET_INFO",user);
        await userApi.login(username, password)
        let user = await userApi.auth()
        commit('USER_SET_INFO', user)
        return user
    },
    async checkUser ({ commit, state }) {
        let user = await userApi.auth()
        commit('USER_SET_INFO', user)
        return user
    },
    mergeUser ({ commit }, obj) {
        commit('MERGEUSER', obj)
    }
}
const mutations = {
    USER_SET_INFO (state, user) {
        for (let key in state) {
            if (state.hasOwnProperty(key)) {
                state[key] = user[key]
            }
        }
        state.validateTime = Date.now()
        // state.user = user
    },
    USER_SIGNOUT (state) {
        // state = null
        for (let key in state) {
            if (state.hasOwnProperty(key)) {
                state[key] = defaultUser[key]
            }
        }
        // 清除本地所有的vuex 记录
    }
    // MERGEUSER (state, obj) {
    //     merge(state, obj)
    // }
}

export default {
    namespaced: true,
    state,
    getters,
    actions,
    mutations
}
