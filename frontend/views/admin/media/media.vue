<template>
    <div>
        <div :class="$globalStyle.pageHeader">
            <div :class="$globalStyle.title">
                {{$t('admin.media.title')}}
            </div>
            <div :class="$globalStyle.content">
                七牛图片管理
            </div>
        </div>
        <upload-test></upload-test>
    </div>
</template>

<script>
import UploadTest from "@/views/components/upload-test";
export default {
    components: {UploadTest},
    layout: 'admin',
	name: "media"
}
</script>

<style module lang="scss">

</style>
