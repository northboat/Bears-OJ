<template>
    <div>
        
    </div>
</template>

<script>
export default {
    name: 'Dispatch',

    data() {
        return {
            
        };
    },

    mounted() {
        //若在结果界面判题，通过这里作为跳板刷新结果界面
        if(this.$route.params.result !== undefined){
            this.$router.push({name: 'result', params: {num: this.$route.params.num, result: this.$route.params.result, code: this.$route.params.code}});
            //console.log(this.$route.params.result);
            return;
        }
        // 侧边栏仓库请求 
        else if(this.$route.params.repoTag != undefined){
            this.$router.push({name: 'repository', params: {tag: this.$route.params.repoTag}});
            return;
        } 
        // 侧边栏讨论请求
        else if(this.$route.params.discTag != undefined){
            this.$router.push({name: 'discuss', params: {tag: this.$route.params.discTag}});
            return;
        } 
        // 题目评论后跳转
        else if(this.$route.params.commentFloor != undefined){
            this.$router.push({name: 'comment', params: {num: this.$route.params.num, floor: this.$route.params.commentFloor}});
            return;
        } 
        // 话题评论后跳转
        else if(this.$route.params.replyFloor != undefined){
            this.$router.push({name: 'topic', params: {num: this.$route.params.num, floor: this.$route.params.replyFloor}});
            return;
        }
        // 发布新绘画后跳转 
        else if(this.$route.params.flag != undefined){
            this.$router.push({name: 'board'});
            return;
        }
        //个人页面删除后跳转
        else if(this.$route.params.delete != undefined){
            this.$router.push({name: 'profile'});
            return;
        }
        this.$router.push({name: 'hello'});
    },

    methods: {
        
    },
};
</script>

<style lang="scss" scoped>

</style>