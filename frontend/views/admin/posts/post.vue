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
                    <el-input style="width: 200px;" @keyup.enter.native="handleFilter"  v-model="tableFilter.keyword"
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
                <el-button :loading="fetchDataLoading" icon="el-icon-refresh" size="small" @click="fetchData"
                           circle></el-button>
            </div>
            <el-table ref="table"
                      v-loading="fetchDataLoading"
                      :data="tableData"
                      :default-sort="tableParams.defaultOrder"
                      stripe

                      @sort-change="handleSortChange">
                <!--:show-header="false"-->
                <el-table-column type="selection" width="55"></el-table-column>
                <!--  标题，封面，作者，状态，分类，标签，点赞评论查看数，文章统计（字数，啥的） -->
                <el-table-column v-slot="{row}">
                    <article-name-col v-bind="row"></article-name-col>
                </el-table-column>
                <el-table-column label="分类" v-slot="{row}" width="120">
                    <font-icon :type="row.category.icon" class="" size="16"></font-icon>
                    <span>{{row.category.name}}</span>
                </el-table-column>
                <el-table-column label="标签" v-slot="{row}" width="180">
                    <article-tags-col :id="row.id" :tags="row.tags"></article-tags-col>
                </el-table-column>
                <el-table-column label="状态" v-slot="{row}" width="190">
                    <article-status-col :status="row.status"></article-status-col>
                </el-table-column>
                <el-table-column label="作者" v-slot="{row}" width="80">
                    <article-author-col :user="row.user"></article-author-col>
                </el-table-column>
                <el-table-column label="统计">

                </el-table-column>
            </el-table>
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
import {convertOriginData, tableMixin} from "@/mixins/table-mixin";
import ArticleNameCol from "@/views/components/col/article-name-col";
import ArticleTagsCol from "@/views/components/col/article-tags-col";
import ArticleStatusCol from "@/views/components/col/article-status-col";
import ArticleAuthorCol from "@/views/components/col/article-author-col";
export default {
    components: {ArticleAuthorCol, ArticleStatusCol, ArticleTagsCol, ArticleNameCol},
    layout: 'admin',
    name: 'post',
    async asyncData(ctx) {
        const data = await ctx.$axios.$get('/article')
        data.content.forEach((item) => {
            item.tags = item.tags.split('#').filter(Boolean)
        })
        return convertOriginData(data)
    },
    mixins: [tableMixin],
    data() {
        return {
            tableFilter: {
                keyword: ''
            }
        }
    },
    computed: {
        $curdStyle
    },
    methods: {
        beforeDataItem (item) {
            item.tags = item.tags.split('#').filter(Boolean)
        },
        async fetchData () {
            try {
                const params = {
                }
                if (this.tableFilter.keyword) {
                    params.keyword = this.tableFilter.keyword
                }
                const data = await this.$axios.$get('/article', { params })
                this.setTableData(data)
            } catch (e) {
            }
        },
        handleClickCreatePost() {
            this.$router.push({name: 'new-post'})
        }
    },
    mounted() {
    }
}
</script>

<style module lang="scss">
.main {
}


</style>
