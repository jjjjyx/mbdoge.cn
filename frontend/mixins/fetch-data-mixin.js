

import {delay} from "~/tools/common";
const none = () => {}

export const fetchDataMixin = {
    computed: {
        fetchDataLoading: {
            get () {
                return this.$store.state.app.reloadDataLoading
            },
            set (value) {
                return this.$store.commit('app/SET_RELOAD_DATA_LOADING', value)
            }
        }
    },
    methods: {
        fetchData () {}
    },
    activated () {
        // console.log('activated = ', this.$options.name, this.$options.__file, this.__refreshOn)
        if (!this.__refreshOn) {
            this.$events.on('refresh', this.fetchData)
            this.$events.fire('hook:activated', this)
            this.__refreshOn = true
        }
    },
    deactivated () {
        this.$events.off('refresh', this.fetchData)
        this.__refreshOn = false
    },
    destroyed () {
        this.$events.off('refresh', this.fetchData)
        this.__refreshOn = false
    },
    created () {
        const originFetchDataFun = this.fetchData || none
        this.fetchData = async (...a) => {
            this.fetchDataLoading = true
            try {
                // 让点击更有感觉
                await delay(originFetchDataFun(...a), 700)
            } catch (e) {
            } finally {
                this.fetchDataLoading = false
            }
        }
        this.__refreshOn = true
        this.$events.on('refresh', this.fetchData)
    }
}
