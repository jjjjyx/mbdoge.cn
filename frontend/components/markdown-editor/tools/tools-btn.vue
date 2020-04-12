<template>
    <li :title="$t('editor.tools.btn.'+name)">
        <a href="javascript:void(0);" tabindex="-1" :data-command="name" v-on="$listeners">
            <font-icon :type="icon"></font-icon>
        </a>
    </li>
</template>

<script>
export default {
	name: "tools-btn",
    props: {
        icon: String,
        name: String,
        custom: Boolean
    }
}
</script>
