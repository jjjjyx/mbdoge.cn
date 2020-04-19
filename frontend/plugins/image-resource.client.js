import Vue from 'vue'
import Upload from '@/components/upload/upload'
import ImageSelect from '@/components/image-select/image-select-dialog'
import config from '~/config'


export default function ({$axios, redirect}, inject) {
    const UploadConstructor = Vue.extend(Upload)
    const ImageSelectDialogConstructor = Vue.extend(ImageSelect)

    let imageSelectInstance = new ImageSelectDialogConstructor({
        data: {}
        // store
    })
    imageSelectInstance.$mount()

    let uploadInstance = new UploadConstructor({
        data: {
            action: config.uploadAction
        },
        $axios
    })
    uploadInstance.$mount()

    document.body.appendChild(uploadInstance.$el)
    document.body.appendChild(imageSelectInstance.$el)

    inject('uploadFiles', uploadInstance.uploadFiles)
    inject('openImageSelectModal', imageSelectInstance.open)
}
