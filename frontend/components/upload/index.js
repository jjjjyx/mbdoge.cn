
import Upload from './upload'


export default function install(Vue, options = {}) {

    const UploadConstructor = Vue.extend(Upload)
    let instance = new UploadConstructor({
        data: options.data,
    })
    instance.$mount()
    document.body.appendChild(instance.$el)
    // initDropDom(instance)
    Vue.prototype.$uploadFiles = instance.uploadFiles

}
