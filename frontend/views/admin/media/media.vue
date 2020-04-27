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
        <el-card shadow="never" :class="[$curdStyle.curdCard, $style.card]">
            <div :class="$curdStyle.curdHeader">
                <div :class="$curdStyle.curdOptionConn">
                    <!--<el-input style="width: 200px;" @keyup.enter.native="handleFilter" v-model="tableFilter.keyword"-->
                    <!--          :placeholder="$t('admin.category.input.search')"></el-input>-->
                    <el-select v-model="tableFilter.space" style="width: 120px" @change="handlerChangeSpace" placeholder="选择空间">
                        <el-option value="" label="所有空间"></el-option>
                        <el-option v-for="(v, k) in ImagesSpace" :key="k" :value="k" :label="v.label"></el-option>
                    </el-select>
                    <el-button class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">
                        {{$t('admin.media.button.search')}}
                    </el-button>
                    <el-tooltip content="上传图片到当前空间">
                        <el-button class="filter-item" type="primary" plain icon="el-icon-upload" @click="handleUpload">
                            {{$t('admin.media.button.upload')}}
                        </el-button>
                    </el-tooltip>
                </div>
                <div :class="$curdStyle.curdTableInfo">
                    共 {{tableParams.total}} 个图片
                </div>
            </div>
            <!---->
            <contextmenu ref="menu" @on-click="handlerClickMenu" @before-open="handlerBeforeOpenContextmenu">
                <contextmenu-item name="view" label="查看" :disabled="!hasSelected"></contextmenu-item>
                <contextmenu-item-divided></contextmenu-item-divided>
                <contextmenu-item name="copyLink" label="复制链接"></contextmenu-item>
                <contextmenu-item name="copyMarkdownLink" label="复制Markdown链接"></contextmenu-item>
                <!--移动到 xxx -->
                <contextmenu-item-divided></contextmenu-item-divided>
                <contextmenu-item v-for="(v, k) in ImagesSpace" :name="'move_' + k" :label="'移动到\''+ v.label + '\''" :key="k" :disabled="k === tableFilter.space"></contextmenu-item>
                <contextmenu-item-divided></contextmenu-item-divided>
                <contextmenu-item name="remove" label="删除"></contextmenu-item>
            </contextmenu>
            <images-grid :image-data="imageData" v-contextmenu:menu></images-grid>
        </el-card>

        <form class="h5-uploader-form" action="javascript:void(0);" @change="handleUploadChange"
              style="position: absolute; opacity: 0; top: -999px; left: 0; width: 0; height: 0; cursor: pointer;">
            <input title="点击选择文件" id="h5Input0" ref="h5Input0" multiple accept="image/*" type="file"
                   name="html5uploader"
                   style="position:absolute;opacity:0;top:0;left:0;width:100%;height:100%;cursor:pointer;">
        </form>
        <photo-swipe ref="photoswipe"></photo-swipe>
    </div>
</template>

<script>
import config from '@/config'
import {$curdStyle, $mediaStyle} from '@/tools/style'
import {ImagesSpace} from '@/tools/enum'
import {convertOriginData, tableMixin} from '@/mixins/table-mixin'

import UploadTest from '@/views/components/upload-test'
import ImagesGrid from '@/views/components/images-grid'
import PhotoSwipe from '@/views/components/photo-swipe'

export default {
    layout: 'admin',
	name: "media",
    mixins: [tableMixin],
    components: {PhotoSwipe, UploadTest, ImagesGrid},
    async asyncData(ctx) {
        const data = await ctx.$axios.$get('/images', {
            params: {
                space: 'default',
                marker: '',
                size: 100
            }
        })

        return {
            imageData: data.items,
            tableFilter: {
                space: 'default',
                marker: data.marker,
                eof: data.eof
            }
        }
    },
    data () {
        return {
            ImagesSpace,
            imageData: [],
            loading: false,
            // 右键点击的目标
            contextmenuTarget: null,
            frameSelected: [],
            // 框选的目标
            tableFilter: {
                space: 'default',
                marker: '',
                eof: ''
            }
        }
    },
    computed: {
        $curdStyle,
        $mediaStyle,
        previewSrcList () {
            return this.imageData.map((item) => {
                return {
                    src: config.imageDomain + item.key,
                }
            }).filter((item) => item.src)
        },
        hasSelected () {
            return this.contextmenuTarget || this.frameSelected.length >= 1
        },
        selected () {
            return this.contextmenuTarget || this.frameSelected.length[0]
        }
    },
    methods: {
        handlerBeforeOpenContextmenu (e) {
            // const target =
            this.contextmenuTarget = e.path.find((t) => t.matches && t.matches('div[role="imageItem"]'))
            this.frameSelected = document.querySelectorAll('.' + this.$mediaStyle.active)
        },
        handlerClickMenu (name, e) {
            console.log(name, e)
            // 获取到点击的目标
            // const {target} = e
            // const target = e.path.find((t) => t.matches && t.matches('div[role="imageItem"]'))

            switch (name) {
                case 'view': // 显示
                    // 查看当前的选择的对象必须存在
                    this.handlerViewImages()
                    break
            }
        },
        async fetchData () {
            if (this.tableFilter.eof) {
                return
            }
            if (this.loading) {
                return
            }
            this.loading = true

            try {
                const params = {
                    space: this.tableFilter.space,
                    marker: this.tableFilter.marker,
                    size: 100
                }
                const data = await this.$axios.$get('/images', {params})
                this.imageData.push(...data.items)
                this.tableFilter.marker = data.marker
                this.tableFilter.eof = data.eof
            } catch (e) {
                console.log(e)
            } finally {
                this.loading = false
            }
        },
        handlerChangeSpace () {
            this.tableFilter.eof = false
            this.tableFilter.marker = ''
            this.imageData = []
            this.fetchData()
        },
        handleUpload () {
            this.$refs.h5Input0.click()
        },
        handleUploadChange (e) {
            // const h = this.$createElement;
            this.$uploadFiles(e.target.files, {space: this.tableFilter.space})
        },
        async handlerViewImages() {
            if (!this.hasSelected) {
                return
            }
            const index = this.contextmenuTarget.getAttribute('data-index')
            // 查询信息
            const keys = this.imageData.map((item) => item.key)
            const data = await this.$axios.$post('/images/info', { keys })
            // 获取当前视口的图片
            // const imageData = this.previewSrcList
            // console.log(imageData)
            // 这该死的插件必须要高宽，七牛云又不给高宽 ，有高宽，但是只能单张单张获取
            this.$refs.photoswipe.open(parseInt(index, 10), data)
        }
    },
    destroyed () {
        // this.$refs.photoswipe.$el.parentNode.removeChild(this.$refs.photoswipe.$el)
    },
    mounted () {
        // document.body.appendChild(this.$refs.photoswipe.$el)
    }
}
</script>

<style module lang="scss">
.card {
    flex: 1;
    display: flex;
    flex-direction: column;
    :global(.el-card__body) {
        display: flex;
        flex-direction: column;
        flex: 1;
        height: 100%;
    }
}

</style>
