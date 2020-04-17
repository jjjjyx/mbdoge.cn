<template>
    <div :class="$style.container">
        <div>
            <h1 :class="$style.title">
                blog-www {{title}}
            </h1>
            <h2 :class="$style.subtitle">
                My first-class Nuxt.js project
            </h2>
            <div :class="$style.links">
                <el-button @click="test">11</el-button>
                <el-radio v-model="radio" label="1">备选项</el-radio>
                <nuxt-link to="/6tw9sQs" no-prefetch>后台</nuxt-link>
                <nuxt-link to="/test" no-prefetch>test</nuxt-link>
            </div>
        </div>
    </div>
</template>

<script>

import api from '~/tools/api'

export default {
    auth: false,
    head() {
        return {title: this.$t('home.title')}
    },
    async asyncData(ctx) {
        // try {
        //     console.log(ctx)
        //     let { data } = await api.get(`https://my-api/posts`)
        // } catch (e) {
        //     console.log(e)
        // }
        return {title: '123123'}
    },
    components: {},
    data() {
        return {
            radio: false
        }
    },
    methods: {
        test() {
            // console.log(this.$globalStyle)
            // console.log(this.$style)
            // console.log(111)
            // try {
            //     api.post('/asd')
            // } finally {
            // }
            // this.title = "12312312312312312"

        }
    },
}
</script>

<style module lang="scss">
.container {
    margin: 0 auto;
    min-height: 100vh;
    display: flex;
    justify-content: center;
    align-items: center;
    text-align: center;
}

.title {
    font-family: 'Quicksand', 'Source Sans Pro', -apple-system, BlinkMacSystemFont,
    'Segoe UI', Roboto, 'Helvetica Neue', Arial, sans-serif;
    display: block;
    font-weight: 300;
    font-size: 100px;
    color: #35495e;
    letter-spacing: 1px;
}

.subtitle {
    font-weight: 300;
    font-size: 42px;
    color: #526488;
    word-spacing: 5px;
    padding-bottom: 15px;
}

.links {
    padding-top: 15px;
}
</style>
