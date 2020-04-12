import moment from 'moment'

moment.locale('zh-cn');
// 统一时间格式化， 统一时区，不管访问者是什么语言，都已zh-CN 的时区以及格式进行格式化


/**
 * 长时间格式 完整时间格式
 * @param date Date or Number (包含毫秒)
 */
export function datetimeFormat(date = Date.now()) {
    return moment(date).format('lll');
}

/**
 * 日期格式
 * @param date Date or Number (包含毫秒)
 */
export function dateFormat(date = Date.now()) {
    return moment(date).format('l');
}

/**
 * 时间格式
 * @param date Date or Number (包含毫秒)
 */
export function timeFormat(date = Date.now()) {
    return moment(date).format('LT');
}
