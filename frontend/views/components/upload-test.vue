<template>
    <div>
        七牛上传测试 demo 页面

        <p>
            测试全局上传组件
        </p>
        <input type="file" @change="handlerUpload">
        <button @click="handlerUpload">上传</button>
    </div>
</template>

<script>
export default {
	name: "upload-test",
    data () {
	    return {
	        files: []
        }
    },
    methods: {
        async handlerUpload (e) {
            // const file = e.target.files[0]
            // console.log(file)

            const token = await this.$axios.$get('/images/token',{
                params: {
                    space: 'xxx'
                }
            })
            // const data = {
            //     token,
            //     file,
            //     'x:space': 'xxx',
            //     'x:remark': file.name
            // }
            // const formData = new FormData()
            // formData.append("token", token)
            // formData.append("file", file)
            // formData.append("x:space", 'xxx')
            // formData.append("x:remark", file.name)

            try {
                this.$uploadFiles(e.target.files, {token, space: 'xxx',})
            } catch (e) {
                console.log(e)
            } finally {
            }
        }
    }
}
</script>

<style scoped>

</style>
