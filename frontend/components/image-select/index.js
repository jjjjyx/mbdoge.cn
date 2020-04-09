import ImageSelectDialog from './image-select-dialog'

export default function install(Vue, options = {}) {
    if (process.client) {
        const ImageSelectDialogConstructor = Vue.extend(ImageSelectDialog)

        let instance = new ImageSelectDialogConstructor({
            data: options.data,
            // i18n,
            // store
        })
        instance.$mount()

        document.body.appendChild(instance.$el)
        Vue.prototype.$openImageSelectModal = instance.open
    }
}
