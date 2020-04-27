import Vue from 'vue'
import GlobalStyle from '@/assets/global.module.scss?module'

Vue.mixin({
    computed: {
        $globalStyle () {
            return GlobalStyle
        }
    }
})
