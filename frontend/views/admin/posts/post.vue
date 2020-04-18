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
                    <el-select v-model="tableFilter.status" style="width: 120px" @change="fetchData">
                        <el-option value="all" label="不限"></el-option>
                        <el-option v-for="(v, k) in ArticleStatus" :key="k" :value="k" :label="v.label"></el-option>
                    </el-select>
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
                <el-table-column v-slot="{row}" label="封面" width="120">
                    <article-cover-col :attribute="row.attribute"></article-cover-col>
                </el-table-column>
                <el-table-column v-slot="{row}" label="文章" min-width="260">
                    <article-name-col v-bind="row"></article-name-col>
                </el-table-column>
                <el-table-column label="分类" v-slot="{row}" width="120">
                    <font-icon :type="row.category.icon" class="" size="16"></font-icon>
                    <span>{{row.category.name}}</span>
                </el-table-column>
                <el-table-column label="标签" v-slot="{row}" width="170">
                    <article-tags-col :id="row.id" :tags="row.tags"></article-tags-col>
                </el-table-column>
                <el-table-column label="作者" v-slot="{row}" width="80">
                    <article-author-col :user="row.user"></article-author-col>
                </el-table-column>
                <el-table-column label="状态" v-slot="{row}" width="190">
                    <article-status-col v-bind="row"></article-status-col>
                </el-table-column>
                <el-table-column label="统计" v-slot="{row}" width="140">
                    <article-statistics-col :like-count="row.likeCount" :comment-count="row.commentCount" :eye-count="row.eyeCount"></article-statistics-col>
                </el-table-column>
                <el-table-column label="其他" v-slot="{row}" width="130px">
                    <article-other-col :attribute="row.attribute" :comment-status="row.commentStatus"></article-other-col>
                </el-table-column>
                <el-table-column
                    fixed="right"
                    label="操作"
                    width="180" v-slot="{row}">
                    <article-action-col v-bind="row" @action-success="fetchData"></article-action-col>
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
import {ArticleStatus} from "@/tools/enum";
import {convertOriginData, tableMixin} from "@/mixins/table-mixin";
import ArticleNameCol from "@/views/components/col/article-name-col";
import ArticleTagsCol from "@/views/components/col/article-tags-col";
import ArticleStatusCol from "@/views/components/col/article-status-col";
import ArticleAuthorCol from "@/views/components/col/article-author-col";
import ArticleStatisticsCol from "@/views/components/col/article-statistics-col";
import ArticleOtherCol from "@/views/components/col/article-other-col";
import ArticleCoverCol from "@/views/components/col/article-cover-col";
import ArticleActionCol from "@/views/components/col/article-action-col";

export default {
    components: {
        ArticleActionCol,
        ArticleCoverCol,
        ArticleOtherCol,
        ArticleStatisticsCol, ArticleAuthorCol, ArticleStatusCol, ArticleTagsCol, ArticleNameCol},
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
                keyword: '',
                status: 'all'
            },
            ArticleStatus
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

                if (this.tableFilter.status && this.tableFilter.status !== 'all') {
                    params.status = this.tableFilter.status
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
