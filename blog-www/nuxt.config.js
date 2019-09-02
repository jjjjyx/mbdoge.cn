const webpack = require('webpack')
const path = require('path')

module.exports = {
    mode: 'universal',
    transition: {
        name: 'fade-transform',
        mode: 'out-in'
    },
    // transition: 'fade-transform',
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
    router: {
        middleware: ['permission']
    },
    /*
     ** Customize the progress-bar color
     */
    loading: { color: '#fff' },
    /*
     ** Global CSS
     */
    css: [
        'normalize.css/normalize.css',
        'element-ui/lib/theme-chalk/index.css',
        '@/assets/global.css'
    ],
    /*
     ** Plugins to load before mounting the App
     */
    plugins: [
        '@/plugins/element-ui',
        {
            src: '@/plugins/globalStyle',
            ssr: false
        }
    ],
    /*
     ** Nuxt.js dev-modules
     */
    devModules: [],
    /*
     ** Nuxt.js modules
     */
    modules: [
        '@nuxtjs/router',
        '@nuxtjs/style-resources'
    ],
    styleResources: {
        scss: [
            // '@/assets/sass/_var.scss',
            '@/assets/sass/base.scss'
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
        // loaders: {
        //     css: {},
        //     cssModules: {
        //         localIdentName: '[local]_[hash:base64:4]'
        //     },
        // },
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
