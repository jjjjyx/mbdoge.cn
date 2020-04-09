module.exports = {
    root: true,

    env: {
        node: true
    },

    extends: [
        'plugin:vue/essential',
        '@vue/standard'
    ],

    rules: {
        'no-console': 'off',
        'no-debugger': 'off',
        'vue/no-parsing-error': 'off',
        indent: [
            'error',
            4,
            {
                SwitchCase: 1
            }
        ]
    },

    parserOptions: {
        parser: 'babel-eslint'
    },

    globals: {
        '_': true,
        '$': true
    },

    overrides: [
        {
            files: [
                '*.vue'
            ],
            rules: {
                indent: 'off'
            }
        }
    ]
}
