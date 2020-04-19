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
            <div :class="$style.imageGridWarp">
                <!--<el-image-->
                <!--    style="width: 100px; height: 100px"-->
                <!--    :src="previewSrcList[0]"-->
                <!--    :preview-src-list="previewSrcList">-->
                <!--</el-image>-->
                <!--infinite-scroll-distance="300" infinite-scroll-delay="1000"-->
                <div :class="$style.imageGrid" v-infinite-scroll="fetchData" >
                    <div :class="$style.imageItem" v-for="(item, index) in imageData" :key="index">
                        <el-image :src="imageDomain + item.key + imgStyle" :alt="item.key" :previewSrcList="previewSrcList"></el-image>
                        <font-icon :class="$style.check" type="el-icon-success" size="20"></font-icon>

                    </div>
                </div>
            </div>
        </el-card>

        <form class="h5-uploader-form" action="javascript:void(0);" @change="handleUploadChange"
              style="position: absolute; opacity: 0; top: -999px; left: 0; width: 0; height: 0; cursor: pointer;">
            <input title="点击选择文件" id="h5Input0" ref="h5Input0" multiple accept="image/*" type="file"
                   name="html5uploader"
                   style="position:absolute;opacity:0;top:0;left:0;width:100%;height:100%;cursor:pointer;">
        </form>
    </div>
</template>

<script>
import UploadTest from '@/views/components/upload-test'
import {convertOriginData, tableMixin} from '@/mixins/table-mixin'
import {$curdStyle} from '@/tools/style'
import {ImagesSpace} from '@/tools/enum'
import config from '@/config'
export default {
    layout: 'admin',
	name: "media",
    mixins: [tableMixin],
    components: {UploadTest},
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
            imageDomain: config.imageDomain,
            ImagesSpace,
            imgStyle: '?imageView2/1/w/180/h/180/q/75|watermark/2/text/ampqanl4/font/Y291cmllciBuZXc=/fontsize/240/fill/I0ZERkRGRA==/dissolve/84/gravity/SouthWest/dx/10/dy/10|imageslim',
            imageData: [],
            loading: false,
            tableFilter: {
                space: 'default',
                marker: '',
                eof: ''
            }
        }
    },
    computed: {
        $curdStyle,
        previewSrcList () {
            return this.imageData.map((item) => {
                return config.imageDomain + item.key
            }).filter(Boolean)
        }
    },
    methods: {
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
            // this.fetchData()
        },
        handleUpload () {
            this.$refs.h5Input0.click()
        },
        handleUploadChange (e) {
            // const h = this.$createElement;
            this.$uploadFiles(e.target.files, {space: this.tableFilter.space})
        }
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
.imageGridWarp {
    flex: 1;
    position: relative;
}
.imageGrid {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    overflow-y: scroll;
    user-select: none;
}
.imageItem {
    position: relative;
    float: left;
    width: 170px;
    margin: .1rem;
    overflow: hidden;
    transition: background-color .15s ease-in;
    padding: 10px;
    border: 1px solid transparent;
    &:hover {
        background-color: rgba($primary, .2);
        //transform: scale(1.05);
    }
    &:hover .check {
        display: block;
        opacity: .4;
    }
    .imgConn {
        height: 150px;
        display: flex;
        justify-content: center;
        align-items: center;
        overflow: hidden;

        img {
            //width: 100%;
            height: 100%;
            box-shadow: 0 0 6px 0 rgba(0, 0, 0, 0.2);
            user-select: none;
        }
    }



    .check {
        position: absolute;
        top: 5px;
        left: 5px;
        height: 21px;
        width: 21px;
        display: none;
        cursor: pointer;
    }
}
</style>
