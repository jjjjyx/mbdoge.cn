<template>
    <div>
        <div v-if="showPercentage">
            <el-progress :percentage="info.percentage" :color="progressColor" :show-text="false"></el-progress>
            <div :class="$style.size">
                <span> {{info.loaded}}/{{info.total}} (Kb)</span>
                <span>{{info.percentage.toFixed(0)}} %</span>
            </div>
        </div>
        <div v-else >
            <span class="text-success"><i class="el-icon-success"></i>上传已完成</span>
        </div>
    </div>
</template>

<script>
import {UploadStatus} from '@/components/upload/constant'

export default {
	name: 'file-percentage-col',
    props: {
        info: Object
    },
    computed: {
        progressColor () {
            switch (this.info.status) {
                case UploadStatus.UPLOADING:
                    return '#409eff'
                case UploadStatus.ERROR:
                    return '#f56c6c'
                case UploadStatus.CANCEL:
                    return '#909399'
                case UploadStatus.FULFIL:
                    return '#67c23a'
            }
        },
        showPercentage () {
            return this.info.status !== UploadStatus.FULFIL
        }
    },
}
</script>

<style module lang="scss">
.size {
    display: flex;
    justify-content: space-between;
    color: $--color-info;
}
</style>
