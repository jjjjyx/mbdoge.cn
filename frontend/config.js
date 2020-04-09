'use strict'
import merge from 'lodash/merge'

const isDev = process.env.NODE_ENV === 'development'
const CONFIG = {
    projectName: '',
    baseURL: '',
    tokenKey: 'Authorization',
    tokenPrefix: 'Bearer ',
    isDev,
    copyright: '--',
    title: 'mbdoge.cn',
    version: '2.0.20190818',
    apiEncryptEnable: true,
    secret: 'xxx'
}

try {
    let pri = require('./private.js')
    merge(CONFIG, pri.default)
} catch (e) {
    // console.info('Failed to load private configuration!')
}

export default CONFIG
