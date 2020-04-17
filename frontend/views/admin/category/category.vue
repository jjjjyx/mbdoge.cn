<template>
    <div>
        <div :class="$globalStyle.pageHeader">
            <div :class="$globalStyle.title">
                {{$t('admin.category.title')}}
            </div>
            <div :class="$globalStyle.content">
            </div>
        </div>
        <el-card shadow="never" :class="[$curdStyle.curdCard]">
            <div :class="$curdStyle.curdHeader">
                <div :class="$curdStyle.curdOptionConn">
                    <!-- 搜索标签 名称-->
                    <el-input style="width: 200px;" @keyup.enter.native="handleFilter"
                              :placeholder="$t('admin.category.input.search')"></el-input>

                    <el-button class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">
                        {{$t('admin.category.button.search')}}
                    </el-button>

                    <el-button class="filter-item" type="success" icon="el-icon-edit"
                               @click="handleClickCreateCategory">{{$t('admin.category.button.new')}}
                    </el-button>
                </div>
                <div :class="$curdStyle.curdTableInfo">
                    共 {{tableParams.total}} 个分类
                </div>
                <el-button :loading="loading" icon="el-icon-refresh" size="small" @click="handlerClickRefresh"
                           circle></el-button>
            </div>

            <el-table ref="table"
                      v-loading="fetchDataLoading"
                      :data="tableData"
                      :default-sort="tableParams.defaultOrder"
                      stripe
                      @sort-change="handleSortUser">
                <el-table-column type="selection" width="55"></el-table-column>
                <el-table-column label="标签" v-slot="{row}">
                    <category-name-col :name="row.name" :icon="row.icon"></category-name-col>
                </el-table-column>
                <el-table-column label="说明" prop="description"></el-table-column>
                <el-table-column label="操作" v-slot="{row}">
                    <el-button  size="mini" :disabled="!row.id">修改</el-button>
                    <el-button  size="mini" type="danger" :disabled="!row.id">删除</el-button>
                </el-table-column>
            </el-table>
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
import {$curdStyle} from "~/tools/style";
// import {fetchDataMixin} from "@/mixins/fetch-data-mixin";
import {tableMixin, convertOriginData} from "@/mixins/table-mixin";
import CategoryNameCol from "@/views/components/col/category-name-col";

export default {
    components: {CategoryNameCol},
    layout: 'admin',
    name: 'category',
    // meta: {
    //     title: 'Category',
    //     icon: 'el-icon-collection-tag'
    // },
    mixins: [tableMixin],
    data() {
        return {
            loading: false,
        }
    },
    async asyncData(ctx) {
        const data = await ctx.$axios.$get('/category')
        return convertOriginData(data)
    },
    computed: {
        $curdStyle
    },
    methods: {
        async fetchData () {
            const data = await this.$axios.$get('/category')
            this.setTableData(data)
        },
        handleClickCreateCategory() {
        },
        handlerClickRefresh() {
        }
    }
}
</script>

<style module lang="scss">

</style>
