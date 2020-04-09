import { AES_CBC } from 'asmcrypto.js/dist_es8/aes/cbc.js'
import { Sha512 } from 'asmcrypto.js/dist_es8/hash/sha512/sha512'
import * as asmCryptoUtils from 'asmcrypto.js/dist_es8/other/utils'
import { IllegalArgumentError } from 'asmcrypto.js/dist_es8/other/errors'

function getKey (key) {
    // 错误的key
    if (typeof key !== 'string') {
        throw new IllegalArgumentError('illegal key')
    }
    const sha512 = new Sha512()
    sha512.HASH_SIZE = 16
    let data = asmCryptoUtils.string_to_bytes(key)
    return sha512.process(data).finish().result
}

class Crazy {
    static encrypt (str, key) {
        // 未提交对象
        if (str === undefined || typeof str !== 'string') {
            throw new IllegalArgumentError('illegal str')
        }

        const p = asmCryptoUtils.string_to_bytes(str, true)
        let result = AES_CBC.encrypt(p, getKey(key))
        return asmCryptoUtils.bytes_to_base64(result)
    }
    static decrypt (str, key) {
        // 未提交对象
        if (str === undefined || typeof str !== 'string') {
            throw new IllegalArgumentError('illegal str')
        }
        key = getKey(key)
        let result = asmCryptoUtils.base64_to_bytes(str)
        let origin = AES_CBC.decrypt(result, key)

        return asmCryptoUtils.bytes_to_string(origin, true)
    }
}

export default Crazy
