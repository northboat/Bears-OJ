<template>
    <div>
        <h3>List from Leetcode</h3><br>
        <div class="row">

            <div class="col-12 col-xl-12">
                <div class="card">
                    <div class="card-header">
                        <h5 class="card-title">LeetCode的题单单</h5>
                        <h6 class="card-subtitle text-muted">Use <code>Java/C++/Python</code> to solve the problems.</h6>
                    </div>
                    <table class="table table-striped table-hover">
                        <thead>
                            <tr>
                                <th style="width:40%">题目</th>
                                <th style="width:40%">难度</th>
                                <th style="width:33%">标签</th>
                            </tr>
                        </thead>
                        <tbody v-if="this.got">
                            <tr v-for="item in list" :key="item.num">
                                <td><a v-bind:href="item.href" target="_blank">{{item.title}}</a></td>
                                <td>{{item.level}}</td>
                                <td>{{item.tag}}</td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>

        </div>
    </div>
</template>

<script>
export default {
    name: 'List',

    data() {
        return {
            list:[],
            got: false
        };
    },

    mounted() {
        this.$axios.get("/getList").then(response => {
            let result = response.data;
            if(result.code != 200){
                this.$message.error('请求失败');
                return;
            }
            this.list = result.data;
            this.got = true;
        })
    },

    methods: {
        
    },
};
</script>

<style lang="scss" scoped>
  a{
    text-decoration: none;
  }

  a:hover{
    text-decoration: none;
  }
</style>