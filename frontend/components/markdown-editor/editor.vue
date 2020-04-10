<template>

</template>

<script>
import 'SimpleMDE/dist/simplemde.min.css'

/**
 * 迟早我会完全的吧这个编辑器抄过来！！！！！！！
 */
export default {
    name: 'editor',
    data () {
        return {
            ready: false,
            $md: null
        }
    },
    methods: {
        async initialize () {
            const SimpleMDE = await import(/* webpackChunkName: "SimpleMDE" */'SimpleMDE').then((m) => m.default || m)

            const configs = {
                element: this.$refs.el,
                status: false,
                toolbar: [],
                autoDownloadFontAwesome: false,
                spellChecker: false // 禁用拼写检查
            }

            this.$md = new SimpleMDE(configs)

            // fix:
            this.$md.toolbarElements = {
                fullscreen: {
                    className: ''
                }
            }
            this.ready = true

        },
        $undo () {
            if (!this.ready) return
            this.$md.undo()
        },
        $redo () {
            if (!this.ready) return
            this.$md.redo()
        },
        $insetImage () {

        },
        $insetTable () {

        },
        $adjunct () {

        },
        $insetCode () {

        },
        $help () {

        },
        $toggleFullScreen () {

        },
        $preview () {

        },
        $save () {

        },
    },
    mounted () {
        this.initialize()
    },
}
</script>

<style module lang="scss">

</style>
