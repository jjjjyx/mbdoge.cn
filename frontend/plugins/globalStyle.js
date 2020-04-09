import Vue from 'vue'
import GlobalStyle from '~/assets/sass/global.module.scss?module'

Vue.mixin({
    computed: {
        $globalStyle () {
            return GlobalStyle
        }
    }
})
