'use strict'

import isBoolean from 'lodash/isBoolean'

const state = () => ({
    // user: null
    collapsed: false,
    fixedHeader: false,
    backgroundColor: 'black',
    backgroundImage: 'http://image.cdn.mbdoge.cn/sidebar-1.jpg',
    image: true,
    color: 'red',
    breadcrumbs: []
})
// 可能会有权限的需求
const getters = {
    // menus: state => state.menus
}

// actions
const actions = {
    // userSetInfo ({ commit, state },user){
    //     commit("USER_SET_INFO",user);
    // },
    toggleSidebarMini ({ commit }) {
        commit('TOGGLE_SIDEBAR_MINI')
    },
    toggleFixedHeader ({ commit }) {
        commit('TOGGLE_FIXED_HEADER')
    }
}
const mutations = {
    SET_BREADCRUMBS (state, breadcrumbs) {
        state.breadcrumbs = breadcrumbs
    },
    TOGGLE_FIXED_HEADER (state, obj) {
        if (isBoolean(obj)) {
            state.fixedHeader = obj
        } else {
            state.fixedHeader = !state.fixedHeader
        }
    },
    TOGGLE_SIDEBAR_MINI (state, obj) {
        if (isBoolean(obj)) {
            state.collapsed = obj
        } else {
            state.collapsed = !state.collapsed
        }
    },
    TOGGLE_SIDEBAR_IMAGE (state, obj) {
        if (isBoolean(obj)) {
            state.image = obj
        } else {
            state.image = !state.image
        }
    },
    switchColor (state, color) {
        state.color = color
    },
    switchBackgroundColor (state, color) {
        state.backgroundColor = color
    },
    switchBackgroundImage (state, color) {
        state.backgroundImage = color
    },
    SET_REMOTE_DOMAIN_ICON (state, domain) {
        state.remoteDomainSite = domain
    }
}

export default {
    state,
    getters,
    actions,
    mutations
}
