<template>
    <div>
        <el-link size="mini" @click="handlerEditArticle">编辑</el-link>
        <el-divider direction="vertical"></el-divider>
        <!-- 如果是已经发布的显示 下架-->
        <status-btn :status="status"></status-btn>
        <el-divider direction="vertical"></el-divider>
        <!--<el-link size="mini" @click="handlerClickBtnByStatus" type="primary">{{fnText}}</el-link>-->
        <el-link :loading="deleteLoading" size="mini" @click="handlerDeleteArticle" type="danger">删除</el-link>

    </div>
</template>

<script>
export default {
	name: "article-action-col",
    data () {
	    return {
            deleteLoading: false,
            statusLoading: false
        }
    },
    props: {
	    id: Number,
        status: String
    },
    components: {
	    StatusBtn: {
	        functional: true,
            props: {
	            status: String
            },
            render (h, ctx) {
	            let method = () => {}
	            let text = ''
                let type = ''
                const {status} = ctx.props
                switch (status) {
                    case 'DRAFT':
                    case 'PRIVATE':
                    case 'PENDING':
                        text = '发布'
                        method = ctx.parent.publishArticle
                        type = 'success'
                        break
                    case 'DELETE':
                        text = '恢复'
                        method = ctx.parent.restoreArticle
                        type = 'primary'
                        break
                    case 'PUBLISH':
                        text = '撤回'
                        method = ctx.parent.offShelfArticle
                        type = 'warning'
                        break
                }
                return h('el-link', {
                    props: {
                        size: 'mini',
                        type
                    },
                    on: {
                        click: method
                    }
                }, text)
            }
        }
    },
    computed: {
    },
    methods: {
        handlerEditArticle () {
            console.log('跳转到 编辑页面')
        },
        async handlerDeleteArticle () {
            // 直接删除，反正可以找回
            if (this.deleteLoading) {
                return
            }
            this.deleteLoading = true
            const id = this.id

            try {
                await this.$axios.$delete(`/article/${id}`)
                this.$emit('action-success')
            } catch (e) {
            } finally {
                this.deleteLoading = false
            }
        },
        async updateArticleStatus (status) {
            if (this.statusLoading) {
                return
            }
            this.statusLoading = true
            const id = this.id

            try {
                await this.$axios.$patch(`/article/${id}/status`, { status })
                this.$emit('action-success')
            } catch (e) {
            } finally {
                this.statusLoading = false
            }
        },
        async publishArticle() {
            await this.updateArticleStatus('PUBLISH')
        },
        async restoreArticle() {
            await this.updateArticleStatus('PENDING')
        },
        offShelfArticle() {
            this.updateArticleStatus('PENDING')
        }
    }
}
</script>

<style module lang="scss">

</style>
