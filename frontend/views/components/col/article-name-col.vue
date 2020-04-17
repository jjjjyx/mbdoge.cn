<template>
    <div>
        {{title}}
    </div>
</template>

<script>
export default {
	name: "article-name-col",
    props: {
	    title: String,
        type: String,
        shortChain: String,
        attribute: {
	        type: Object,
            default () {
	            return {}
            }
        }
    }
}
</script>

<style module lang="scss">

</style>
