/**
 * https://github.com/nuxt/nuxt.js/issues/2936
 * I set webpack.config.js on the project root for WebStorm.
 That file is invalid for Nuxt and it is valid only for WebStorm.
 */
module.exports = {
    resolve: {
        // for WebStorm
        alias: {
            '@': path.resolve(__dirname),
            '~': path.resolve(__dirname)
        }
    }
};
