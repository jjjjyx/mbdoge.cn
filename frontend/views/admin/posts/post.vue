<template>
    <div>
        <div :class="$globalStyle.pageHeader">
            <div :class="$globalStyle.title">
                {{$t('admin.post.title')}}
            </div>
            <div :class="$globalStyle.content">
            </div>
        </div>

        <!---->
        <el-card shadow="never" :class="[$style.main, $curdStyle.curdCard]">
            <div :class="$curdStyle.curdHeader">
                <div :class="$curdStyle.curdOptionConn">
                    <!-- 搜索标签 标题-->
                    <el-input style="width: 200px;" @keyup.enter.native="handleFilter"
                              :placeholder="$t('admin.post.input.search')"></el-input>
                    <!-- 按文章状态筛选-->
                    <!-- 按发布时间 -->
                    <!-- 按分类 -->
                    <el-button class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">
                        {{$t('admin.post.button.search')}}
                    </el-button>

                    <el-button class="filter-item" type="success" icon="el-icon-edit" @click="handleClickCreatePost">
                        {{$t('admin.post.button.new')}}
                    </el-button>
                </div>
                <div :class="$curdStyle.curdTableInfo">
                    共 {{tableParams.total}} 篇文章
                </div>
                <el-button :loading="loading" icon="el-icon-refresh" size="small" @click="handlerClickRefresh"
                           circle></el-button>
            </div>

            <!--<el-table></el-table>-->
            <el-pagination
                :class="$curdStyle.curdPagination"
                :page-size="tableParams.pageSize"
                :pager-count="11"
                :current-page="tableParams.currPage"
                layout="prev, pager, next"
                :total="tableParams.total"
                @current-change="handleChangePage">
            </el-pagination>
        </el-card>
    </div>
</template>

<script>
import {$curdStyle} from '~/tools/style'
import {tableMixin} from "@/mixins/table-mixin";
export default {
    layout: 'admin',
    name: 'post',
    mixins: [tableMixin],
    data() {
        return {
            loading: false,
            params: {
                currPage: 1,
                pageSize: 20,
                total: 0,
                order: '',
                defaultOrder: {}
            }
        }
    },
    computed: {
        $curdStyle
    },
    methods: {
        async fetchData () {
            const data = await this.$axios.$get('/article')
            this.setTableData(data)
        },
        handlerClickRefresh() {
        },
        handleClickCreatePost() {
            this.$router.push({name: 'new-post'})
        },
    },
    mounted() {
        console.log(this.$curdStyle)
    }
}
</script>

<style module lang="scss">
.main {
}


</style>
