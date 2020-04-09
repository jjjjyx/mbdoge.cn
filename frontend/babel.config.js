module.exports = {
    presets: [
        '@nuxt/babel-preset-app'
    ],
    'plugins': ['lodash', [
        'component',
        {
            'libraryName': 'element-ui',
            'styleLibraryName': 'theme-chalk'
        }
    ]]
}
