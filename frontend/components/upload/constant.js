
export const accept = 'image/gif,image/jpeg,image/jpg,image/png'

export const UploadStatus = Object.freeze({
    'FULFIL': {
        label: '上传完成',
        key: 'fulfil'
    },
    'READY': {
        label: '准备',
        key: 'ready'
    },
    'UPLOADING': {
        label: '上传中',
        key: 'uploading'
    },
    'CANCEL': {
        label: '已取消',
        key: 'cancel'
    },
    'ERROR': {
        label: '上传失败',
        key: 'error'
    }
})
