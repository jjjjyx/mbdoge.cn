const webpack = require('webpack')
const path = require('path')

export default {
    mode: 'universal',
    transition: {
        name: 'fade-transform',
        mode: 'out-in'
    },
    /*
    ** Headers of the page
    */
    head: {
        title: process.env.npm_package_name || '',
        meta: [
            {charset: 'utf-8'},
            {name: 'viewport', content: 'width=device-width, initial-scale=1'},
            {hid: 'description', name: 'description', content: process.env.npm_package_description || ''}
        ],
        link: [
            {rel: 'icon', type: 'image/x-icon', href: '/favicon.ico'}
        ],
        script: [
            // async: true, defer: true
            {src: '//at.alicdn.com/t/font_1385233_mj4u5amekwc.js',}
        ],
    },

    /*
    ** Customize the progress-bar color
    */
    loading: {color: '#fff'},
    /*
     ** Global CSS
     */
    css: [
        'normalize.css/normalize.css',
        'element-ui/lib/theme-chalk/index.css',
        '~/assets/global.css'
    ],
    /*
     ** Plugins to load before mounting the App
     */
    plugins: [
        '~/plugins/element-ui',
        '~/plugins/components',
        '~/plugins/globalStyle',
        '~/plugins/imageSelect.client.js',
        '~/plugins/axios.server.js',
        '~/plugins/axios.client.js'
    ],
    /*
    ** Nuxt.js dev-modules
    */
    buildModules: [
        '@nuxt/typescript-build',
    ],
    /*
    ** Nuxt.js modules
    */
    modules: [
        '@nuxtjs/router',
        '@nuxtjs/style-resources',
        '@nuxtjs/proxy',
        '@nuxtjs/axios',
        '@nuxtjs/auth'
    ],
    axios: {
        proxy: true,
        // debug: true,
        prefix: '/api/v2',
    },
    proxy: {
        '/api/v2': 'http://127.0.0.1:5658/'
    },

    styleResources: {
        scss: [
            // '@/assets/sass/_var.scss',
            '@/assets/sass/base.scss'
        ]
    },
    router: {
        // middleware: ['auth']
    },
    auth: {
        // Options
        strategies: {
            local:{
                endpoints: {
                    login: { url: '/auth', method: 'post', propertyName: false },
                    logout: { url: '/user/logout', method: 'post' },
                    user: { url: '/auth', method: 'get', propertyName: false }
                }
            },
            autoFetchUser: false
        },
        redirect: {
            login: '/6tw9sQs/login',
            logout: '/',
            home: '/6tw9sQs'
        },

        // rewriteRedirects: false,
        scopeKey: 'roles'
    },
    /*
    ** Build configuration
    */
    build: {
        transpile: [/^element-ui/],
        /*
        ** You can extend webpack config here
        */
        plugins: [
            new webpack.ProvidePlugin({
                '_': path.resolve(__dirname, './tools/lodash.js')
            }),
            new webpack.ProvidePlugin({
                '$': path.resolve(__dirname, './tools/_merge.js')
            })
        ],
        extend(config, ctx) {
        }
    }
}
