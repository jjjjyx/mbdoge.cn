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
                    <el-input style="width: 200px;" @keyup.enter.native="handleFilter" v-model="tableFilter.keyword"
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
                <el-button :loading="fetchDataLoading" icon="el-icon-refresh" size="small" @click="fetchData"
                           circle></el-button>
            </div>

            <el-table ref="table"
                      v-loading="fetchDataLoading"
                      :data="tableData"
                      :default-sort="tableParams.defaultOrder"
                      stripe
                      @sort-change="handleSortChange">
                <el-table-column type="selection" width="55"></el-table-column>
                <el-table-column label="标签" v-slot="{row}">
                    <category-name-col :name="row.name" :icon="row.icon"></category-name-col>
                </el-table-column>
                <el-table-column label="说明" prop="description"></el-table-column>
                <el-table-column label="操作" v-slot="{row}">
                    <el-button  size="mini" :disabled="!row.id" @click="handlerEditTarget(row)">修改</el-button>
                    <el-popconfirm
                        confirmButtonText='删除'
                        cancelButtonText='不用了'
                        icon="el-icon-info"
                        iconColor="red"
                        @onConfirm="handlerDeleteTarget(row)"
                        :title="'确认删除分类 ' + row.name + '？'"
                    >
                        <el-button  size="mini" type="danger" :loading="deleteLoading" :disabled="!row.id" slot="reference">删除</el-button>
                    </el-popconfirm>
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
        <el-drawer
            :visible.sync="editVisible"
            direction="rtl" :custom-class="$curdStyle.curdDrawer"
            size="50%" ref="drawer">
            <span slot="title">编辑分类</span>
            <el-form :model="editTarget" label-width="80px" v-if="editTarget" :class="$curdStyle.curdEditForm">
                <el-form-item label="分类名称" >
                    <el-input v-model="editTarget.name" autocomplete="off"></el-input>
                </el-form-item>

                <el-form-item label="分类图标" >
                    <el-input v-model="editTarget.icon" autocomplete="off"></el-input>
                    <font-icon  :type="editTarget.icon" size="30" class="mt-1"></font-icon>
                </el-form-item>
                <el-form-item label="说明" >
                    <el-input
                        type="textarea"
                        :rows="3"
                        maxlength="150"
                        placeholder="请输入内容"
                        v-model="editTarget.description">
                    </el-input>
                </el-form-item>
            </el-form>
            <div :class="$curdStyle.curdEditFormFooter">
                <el-button @click="handlerCancelEdit">取 消</el-button>
                <el-button type="primary" @click="handleConfirmEditDrawer" :disabled="!editTargetIsModify" :loading="editLoading">{{ editLoading ? '提交中 ...' : '确 定' }}</el-button>
            </div>
        </el-drawer>

        <el-drawer
            :visible.sync="createVisible"
            direction="rtl" :custom-class="$curdStyle.curdDrawer"
            size="50%" ref="drawer">
            <span slot="title">新建分类</span>
            <el-form :model="newCategoryData" label-width="80px" :class="$curdStyle.curdEditForm">
                <el-form-item label="分类名称" >
                    <el-input v-model="newCategoryData.name" autocomplete="off"></el-input>
                </el-form-item>

                <el-form-item label="分类图标" >
                    <el-input v-model="newCategoryData.icon" autocomplete="off"></el-input>
                    <font-icon  :type="newCategoryData.icon" size="30" class="mt-1"></font-icon>
                </el-form-item>
                <el-form-item label="说明" >
                    <el-input
                        type="textarea"
                        :rows="3"
                        maxlength="150"
                        placeholder="请输入内容"
                        v-model="newCategoryData.description">
                    </el-input>
                </el-form-item>
            </el-form>
            <div :class="$curdStyle.curdEditFormFooter">
                <el-button @click="handlerCancelCreate">取 消</el-button>
                <el-button type="primary" @click="handleConfirmCreate" :disabled="createLoading" :loading="createLoading">{{ createLoading ? '提交中 ...' : '确 定' }}</el-button>
            </div>
        </el-drawer>

    </div>
</template>

<script>
import {$curdStyle} from "~/tools/style";
import {tableMixin, convertOriginData} from "@/mixins/table-mixin";
import CategoryNameCol from "@/views/components/col/category-name-col";

export default {
    layout: 'admin',
    async asyncData(ctx) {
        const data = await ctx.$axios.$get('/category')
        return convertOriginData(data)
    },
    name: 'category',
    // meta: {
    //     title: 'Category',
    //     icon: 'el-icon-collection-tag'
    // },
    mixins: [tableMixin],
    data() {
        return {
            createVisible: false,
            createLoading: false,
            // a! 表单验证好麻烦的说， 先不做了，有时间慢慢雕琢
            newCategoryData: {
                icon: '',
                name: '',
                description: ''
            },
            tableFilter: {
                keyword: ''
            }
        }
    },
    components: {CategoryNameCol},
    computed: {
        $curdStyle
    },
    methods: {
        async handleConfirmEditDrawer () {
            if (this.editLoading) {
                return
            }
            this.editLoading = true

            const id = this.editTarget.id
            try {
                const ret = await this.$axios.$put(`/category/${id}`, this.editTarget)
                Object.assign(this.editTargetOrigin, ret)
                this.editVisible = false
            } catch (e) {
            } finally {
                this.editLoading = false
            }
        },
        async handlerDeleteTarget (target) {
            if (this.deleteLoading) {
                return
            }
            this.deleteLoading = true
            const id = target.id
            try {
                await this.$axios.$delete(`/category/${id}`)
                await this.fetchData()
            } catch (e) {
            } finally {
                this.deleteLoading = false
            }
        },
        async fetchData () {
            try {
                const params = {}
                if (this.tableFilter.keyword) {
                    params.keyword = this.tableFilter.keyword
                }
                const data = await this.$axios.$get('/category', { params })
                this.setTableData(data)
            } catch (e) {
            }
        },
        handlerCancelCreate () {
            this.createVisible = false
            this.createLoading = false
        },
        async handleConfirmCreate () {
            this.createLoading = true
            try {
                await this.$axios.$post(`/category`, this.newCategoryData)
                this.fetchData().then()
                this.createVisible = false
            } catch (e) {
            } finally {
                this.createLoading = false
            }

        },
        handleClickCreateCategory() {
            this.createVisible = true
        }
    }
}
</script>

<style module lang="scss">

</style>
