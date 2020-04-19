<template>
    <div :class="$style.col">
        <div :class="$style.nameRow">
            <el-tooltip class="item" effect="dark" :content="info.name" placement="top">
                <span :class="$style.name" class="text-truncate">{{info.name}}</span>
            </el-tooltip>

            <span :class="[$style.status, $style[info.status.key]]">
                <span v-if="!info.msg">{{info.status.label}}</span>
                <el-tooltip v-else :content="info.msg">
                    <span >{{info.status.label}}</span>
                </el-tooltip>
            </span>
        </div>
        <div>
            space: {{info.space}}
        </div>
    </div>
</template>

<script>
import {UploadStatus} from './constant'
export default {
	name: 'file-name-col',
    props: {
	    info: Object
    },
    computed: {
    },
    methods: {
    }
}
</script>

<style module lang="scss">
.col {

}
.nameRow {
    display: flex;
    align-items: center;
    line-height: 1;
    .name {
        flex: 1;
        font-size: 1rem;
    }
}
.status {
    display: none;
    &.fulfil {

    }
    &.ready {

    }
    &.uploading {

    }
    &.cancel {
        display: block;
        color: $--color-info;
        font-style: italic;
    }
    &.error {
        display: block;
        color: red;
    }
}
</style>
