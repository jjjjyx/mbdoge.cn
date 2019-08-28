// import Cookies from 'js-cookie'
// import { constantRoutes } from '@/router'
import { constantRoutes } from '@/router'

const state = () => ({
    sidebar: {
        collapsed: false,
        withoutAnimation: false,
        // backgroundColor: 'black',
        // backgroundImage: 'http://image.cdn.mbdoge.cn/sidebar-1.jpg',
        // image: true,
    },
    visitedViews: [],
    cachedViews: [],

    // see https://github.com/PanJiaChen/vue-element-admin/blob/master/src/store/modules/permission.js
    routes: [],
    addRoutes: [],

    showFooter: true,
    showTagsView: true,
    showSettings: false,
    fixedHeader: false,
    device: 'desktop',

    breadcrumbs: []
})

const mutations = {
    SET_BREADCRUMBS (state, breadcrumbs) {
        state.breadcrumbs = breadcrumbs
    },
    SET_ROUTES: (state, routes) => {
        state.addRoutes = routes
        // state.routes = _.cloneDeep(constantRoutes.concat(routes))
        state.routes = constantRoutes.concat(routes)
    },
    TOGGLE_SIDEBAR: state => {
        state.sidebar.collapsed = !state.sidebar.collapsed
        state.sidebar.withoutAnimation = false
    },
    CLOSE_SIDEBAR: (state, withoutAnimation) => {
        state.sidebar.collapsed = true
        state.sidebar.withoutAnimation = withoutAnimation
    },
    TOGGLE_DEVICE: (state, device) => {
        state.device = device
    },
    CHANGE_SETTING: (state, { key, value }) => {
        if (state.hasOwnProperty(key)) {
            state[key] = value
        }
    },
    ADD_VISITED_VIEW: (state, view) => {
        if (state.visitedViews.some(v => v.path === view.path)) return
        // let o = Object.assign({}, view, {
        //     title: view.meta.title || 'no-name'
        // })
        let o = {
            fullPath: view.fullPath,
            hash: view.hash,
            meta: _.cloneDeep(view.meta),
            name: view.name,
            params: view.params,
            path: view.path,
            query: view.query,
            title: view.meta.title || 'no-name'
        }
        state.visitedViews.push(o)
    },
    ADD_CACHED_VIEW: (state, view) => {
        if (state.cachedViews.includes(view.name)) return
        if (!view.meta.noCache) {
            state.cachedViews.push(view.name)
        }
    },

    DEL_VISITED_VIEW: (state, view) => {
        for (const [i, v] of state.visitedViews.entries()) {
            if (v.path === view.path) {
                state.visitedViews.splice(i, 1)
                break
            }
        }
    },
    DEL_CACHED_VIEW: (state, view) => {
        for (const i of state.cachedViews) {
            if (i === view.name) {
                const index = state.cachedViews.indexOf(i)
                state.cachedViews.splice(index, 1)
                break
            }
        }
    },

    DEL_OTHERS_VISITED_VIEWS: (state, view) => {
        state.visitedViews = state.visitedViews.filter(v => {
            return v.meta.affix || v.path === view.path
        })
    },
    DEL_OTHERS_CACHED_VIEWS: (state, view) => {
        for (const i of state.cachedViews) {
            if (i === view.name) {
                const index = state.cachedViews.indexOf(i)
                state.cachedViews = state.cachedViews.slice(index, index + 1)
                break
            }
        }
    },

    DEL_ALL_VISITED_VIEWS: state => {
        // keep affix tags
        const affixTags = state.visitedViews.filter(tag => tag.meta.affix)
        state.visitedViews = affixTags
    },
    DEL_ALL_CACHED_VIEWS: state => {
        state.cachedViews = []
    },

    UPDATE_VISITED_VIEW: (state, view) => {
        for (let v of state.visitedViews) {
            if (v.path === view.path) {
                let o = {
                    fullPath: view.fullPath,
                    hash: view.hash,
                    meta: _.cloneDeep(view.meta),
                    name: view.name,
                    params: view.params,
                    path: view.path,
                    query: view.query,
                    title: view.meta.title || 'no-name'
                }
                Object.assign(v, o)
                break
            }
        }
    }
}

const actions = {
    generateRoutes ({ commit }, roles) {
        return new Promise(resolve => {
            let accessedRoutes = []
            // if (roles.includes('admin')) {
            //     accessedRoutes = asyncRoutes || []
            // } else {
            //     accessedRoutes = filterAsyncRoutes(asyncRoutes, roles)
            // }
            commit('SET_ROUTES', accessedRoutes)
            resolve(accessedRoutes)
        })
    },
    toggleSideBar ({ commit }) {
        commit('TOGGLE_SIDEBAR')
    },
    closeSideBar ({ commit }, { withoutAnimation }) {
        commit('CLOSE_SIDEBAR', withoutAnimation)
    },
    toggleDevice ({ commit }, device) {
        commit('TOGGLE_DEVICE', device)
    },
    changeSetting ({ commit }, data) {
        commit('CHANGE_SETTING', data)
    },
    addView ({ dispatch }, view) {
        dispatch('addVisitedView', view)
        dispatch('addCachedView', view)
    },
    addVisitedView ({ commit }, view) {
        commit('ADD_VISITED_VIEW', view)
    },
    addCachedView ({ commit }, view) {
        commit('ADD_CACHED_VIEW', view)
    },

    delView ({ dispatch, state }, view) {
        return new Promise(resolve => {
            dispatch('delVisitedView', view)
            dispatch('delCachedView', view)
            resolve({
                visitedViews: [...state.visitedViews],
                cachedViews: [...state.cachedViews]
            })
        })
    },
    delVisitedView ({ commit, state }, view) {
        return new Promise(resolve => {
            commit('DEL_VISITED_VIEW', view)
            resolve([...state.visitedViews])
        })
    },
    delCachedView ({ commit, state }, view) {
        return new Promise(resolve => {
            commit('DEL_CACHED_VIEW', view)
            resolve([...state.cachedViews])
        })
    },

    delOthersViews ({ dispatch, state }, view) {
        return new Promise(resolve => {
            dispatch('delOthersVisitedViews', view)
            dispatch('delOthersCachedViews', view)
            resolve({
                visitedViews: [...state.visitedViews],
                cachedViews: [...state.cachedViews]
            })
        })
    },
    delOthersVisitedViews ({ commit, state }, view) {
        return new Promise(resolve => {
            commit('DEL_OTHERS_VISITED_VIEWS', view)
            resolve([...state.visitedViews])
        })
    },
    delOthersCachedViews ({ commit, state }, view) {
        return new Promise(resolve => {
            commit('DEL_OTHERS_CACHED_VIEWS', view)
            resolve([...state.cachedViews])
        })
    },

    delAllViews ({ dispatch, state }, view) {
        return new Promise(resolve => {
            dispatch('delAllVisitedViews', view)
            dispatch('delAllCachedViews', view)
            resolve({
                visitedViews: [...state.visitedViews],
                cachedViews: [...state.cachedViews]
            })
        })
    },
    delAllVisitedViews ({ commit, state }) {
        return new Promise(resolve => {
            commit('DEL_ALL_VISITED_VIEWS')
            resolve([...state.visitedViews])
        })
    },
    delAllCachedViews ({ commit, state }) {
        return new Promise(resolve => {
            commit('DEL_ALL_CACHED_VIEWS')
            resolve([...state.cachedViews])
        })
    },

    updateVisitedView ({ commit }, view) {
        commit('UPDATE_VISITED_VIEW', view)
    }
}

export default {
    namespaced: true,
    state,
    mutations,
    actions
}
