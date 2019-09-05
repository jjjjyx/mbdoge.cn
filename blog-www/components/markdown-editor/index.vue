<template>
    <div :class="$style.markdownEditor">
        <textarea ref="el"></textarea>
    </div>
</template>

<script>
import 'SimpleMDE/dist/simplemde.min.css'
export default {
    name: 'markdown-editor',
    methods: {
        async initialize () {
            const SimpleMDE = await import(/* webpackChunkName: "SimpleMDE" */'SimpleMDE').then((m) => m.default || m)

            const configs = {
                element: this.$refs.el,
                status: false,
                toolbar: false,
            }

            this.$md = new SimpleMDE(configs)
        }
    },
    mounted () {
        this.initialize()
    },
}
</script>

<style module lang="scss">
.markdownEditor {
    height: 100%;
    textarea {
        opacity: 0;
    }
    :global(.CodeMirror) {
        height: 100%;
        border: none;
    }
}
</style>
