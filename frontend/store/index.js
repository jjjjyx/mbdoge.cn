'use strict'

// import config from '~/config'
// import axios from 'axios'

const state = () => ({
    locales: ['en', 'zh-CN'],
    locale: 'en'
})

const getters = {

}

// actions
const actions = {
    async nuxtServerInit ({ commit, dispatch }, { req }) {
        console.log('nuxt init')
        console.log('process.server = ', process.server)
        // if (req.headers.cookie) {
            // try {
            //
            //     const ret = await this.$axios.$get('/auth')
            //     commit('user/USER_SET_INFO', data)
            //     dispatch('app/generateRoutes')
            // } catch (e) {
            //     // console.log(e.response)
            //     console.log('尚未登录')
            // }
        // }
        // await dispatch('user/checkUser')
    },

}
const mutations = {
    SET_LANG (state, locale) {
        if (state.locales.includes(locale)) {
            state.locale = locale
        }
    }
}

export default {
    state,
    getters,
    actions,
    mutations
}
