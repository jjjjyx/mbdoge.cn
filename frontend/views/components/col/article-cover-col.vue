<template>
    <div :class="$style.col">
        <el-image
            style="width: 100px; height: 100px"
            :src="url"
            :preview-src-list="srcList">
            <div slot="error" :class="$style.imageSlot">
                <i class="el-icon-picture-outline"></i>
                <span>暂无封面</span>
            </div>
        </el-image>
    </div>
</template>

<script>
export default {
	name: "article-cover-col",
    props: {
        attribute: {
            type: Object,
            default () {
                return {}
            }
        }
    },
    computed: {
        srcList () {
            return this.attribute ? this.attribute.covers : []
        },
        url () {
            return this.attribute ? this.attribute.covers[0] : ''
        }
    },

}
</script>

<style module lang="scss">
.col {
    width: 150px;
}
.imageSlot {
    display: flex;
    align-items: center;
    justify-content: center;
    flex-direction: column;

    width: 100%;
    height: 100%;
    i {
        font-size: 24px;
    }
}

</style>
