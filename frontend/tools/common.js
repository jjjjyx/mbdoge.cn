// import isDate from 'lodash/isDate'
// import 'webcrypto-shim'
// import asmcrypto from 'asmcrypto.js'
import { Pbkdf2HmacSha512 } from 'asmcrypto.js/dist_es8/pbkdf2/pbkdf2-hmac-sha512.js'
// /**
//  * 时间格式化
//  * @param format
//  * @returns
//  */
// /* eslint-disable no-extend-native */
// Date.prototype.format = function (format) {
//     let o = {
//         'M+': this.getMonth() + 1, // month
//         'd+': this.getDate(), // day
//         'h+': this.getHours(), // hour
//         'm+': this.getMinutes(), // minute
//         's+': this.getSeconds(), // second
//         'q+': Math.floor((this.getMonth() + 3) / 3), // quarter
//         'S': this.getMilliseconds() // millisecond
//     }
//     if (/(y+)/.test(format)) {
//         format = format.replace(RegExp.$1, (this.getFullYear() + '').substr(4 - RegExp.$1.length))
//     }
//     for (let k in o) {
//         if (new RegExp('(' + k + ')').test(format)) {
//             format = format.replace(RegExp.$1, RegExp.$1.length === 1 ? o[k] : ('00' + o[k]).substr(('' + o[k]).length))
//         }
//     }
//     return format
// }

const CTRL_MASK = 0x1000
const ALT_MASK = 0x2000
const SHIFT_MASK = 0x4000

export function getMetaKeyCode (e) {
    let metaKeyCode = 0
    if (e.ctrlKey || e.metaKey) {
        metaKeyCode |= CTRL_MASK
    }
    if (e.altKey) {
        metaKeyCode |= ALT_MASK
    }
    if (e.shiftKey) {
        metaKeyCode |= SHIFT_MASK
    }
    metaKeyCode |= e.keyCode
    return metaKeyCode
}

export function hyphenToHump (str) {
    return str.replace(/-(\w)/g, (all, letter) => letter.toUpperCase())
}

const defaultDatePattern = 'yyyy/MM/dd hh:mm'

// export function dateFormat (timeInMs, pattern = defaultDatePattern) {
//     if (timeInMs instanceof Date) {
//         return timeInMs.format(pattern) || '-'
//     } else if (!isNaN(timeInMs)) {
//         return new Date(timeInMs * 1000).format(pattern)
//     } else {
//         return new Date(timeInMs).format(pattern) || '-'
//     }
// }

/**
 *  see https://github.com/fy0/Icarus/blob/master/src/tools/user.js#L34-L56
 */
const salt = '1U$&6gpq$mFnCdKcrJjYmW@8%2Xy0WGT'
const _passwordResultToText = function (keyBuffer, saltUint8, iterations) {
    const keyArray = Array.from(new Uint8Array(keyBuffer)) // key as byte array
    const saltArray = Array.from(new Uint8Array(saltUint8)) // salt as byte array

    const iterHex = ('000000' + iterations.toString(16)).slice(-6) // iter’n count as hex
    const iterArray = iterHex.match(/.{2}/g).map(byte => parseInt(byte, 16)) // iter’ns as byte array

    const compositeArray = [].concat(saltArray, iterArray, keyArray) // combined array
    const compositeStr = compositeArray.map(byte => String.fromCharCode(byte)).join('') // combined as string
    const compositeBase64 = btoa('v01' + compositeStr) // encode as base64

    return compositeBase64 // return composite key
}
// const passwordHashAsmCrypto =

let passwordHashAsmCrypto = function (password, iterations = 4e4) {
    return new Promise((resolve, reject) => {
        let enc = new TextEncoder()
        const pwUtf8 = enc.encode(password) // encode pw as UTF-8
        const saltUint8 = enc.encode(salt)
        let keyBuffer = Pbkdf2HmacSha512(pwUtf8, saltUint8, iterations, 32)
        resolve(_passwordResultToText(keyBuffer, saltUint8, iterations))
    })
}

export const passwordHash = passwordHashAsmCrypto

export function shieldIP (ip) {
    if (!ip) return '-'
    let arr = ip.split('.')
    arr.splice(1, 2)
    return arr.join('.*.*.')
}

// export function getColor (key) {
//     if (key === '-') return '#000'
//     let bgColor = murmurhash.v3(key).toString(16).slice(0, 6)
//     return '#' + bgColor
// }
//

export const delay = function (promise = Promise.resolve(), delayTime = 2000) {
    let time = delayTime || 2000
    let offset = Math.random() * 500 + 200

    let p1 = new Promise(function (resolve, reject) {
        let setTime = ~~(Math.random() < 0.5 ? time + offset : time - offset)
        console.debug('time-end = ', setTime)
        setTimeout(resolve, setTime)
    })
    let arr = [p1, promise]

    return Promise.all(arr).then(([t, data]) => data)
    // return promise
}

export function paramsSerializer (params) {
    let searchParams = new URLSearchParams()
    for (let [k, v] of Object.entries(params)) {
        let key = k // Crazy.encrypt(k, secret)
        if (Array.isArray(v)) {
            v.forEach((it) => searchParams.append(key, it))
        } else {
            searchParams.append(key, v)
        }
    }
    return searchParams.toString()
}
