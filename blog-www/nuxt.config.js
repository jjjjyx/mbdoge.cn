const webpack = require('webpack')
const path = require('path')

module.exports = {
    mode: 'universal',
    /*
     ** Headers of the page
     */
    head: {
        title: process.env.npm_package_name || '',
        meta: [
            { charset: 'utf-8' },
            { name: 'viewport', content: 'width=device-width, initial-scale=1' },
            { hid: 'description', name: 'description', content: process.env.npm_package_description || '' }
        ],
        link: [
            { rel: 'icon', type: 'image/x-icon', href: '/favicon.ico' }
        ]
    },
    /*
     ** Customize the progress-bar color
     */
    loading: { color: '#fff' },
    /*
     ** Global CSS
     */
    css: [
        'element-ui/lib/theme-chalk/index.css'
    ],
    /*
     ** Plugins to load before mounting the App
     */
    plugins: [
        '@/plugins/element-ui'
    ],
    /*
     ** Nuxt.js dev-modules
     */
    devModules: [],
    /*
     ** Nuxt.js modules
     */
    modules: [
        '@nuxtjs/style-resources'
    ],
    styleResources: {
        scss: [
            '@/assets/sass/_var.scss'
        ]
    },
    /*
     ** Build configuration
     */
    build: {
        transpile: [/^element-ui/],
        postcss: {
            plugins: {
                autoprefixer: {}
            }
        },
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
        extend (config, ctx) {
        }
    }
}
