<template>
    <div>
        <h1 class="h3 mb-3">{{this.$route.params.tag}} Discussing</h1><br>
        <el-row :gutter="12" v-if="this.got">
            <el-col :span="12" v-for="item in this.topics" :key="item.num">

                <el-card shadow="hover" class="box-card">
                    <div slot="header" class="clearfix">
                        <span style="font-size: 18px">{{item.title}}</span>
                        <router-link style="float: right;" :to="{name: 'topic', params:{num: item.num}}">
                            <span style="color: pink">查看详情</span>
                        </router-link>
                    </div>
                    <div class="text item">
                        {{item.desc}}
                    </div>
                    <div class="bottom clearfix">
                        <time class="time">{{item.tag}}</time>
                        <time class="time" style="float: right;">From {{item.from}}</time>                    
                    </div>
                </el-card>

            </el-col>            
        </el-row>
        
        <el-row class="fixedButton">
            <el-button icon="el-icon-search" circle></el-button>
            <router-link :to="{name: 'write'}"><el-button type="primary" icon="el-icon-edit" circle></el-button></router-link>
            <el-button type="success" icon="el-icon-check" circle></el-button>
            <el-button type="info" icon="el-icon-message" circle></el-button>
            <el-button type="warning" icon="el-icon-star-off" circle></el-button>
            <el-button type="danger" icon="el-icon-delete" circle></el-button>
        </el-row>

    </div>
</template>

<script>
export default {
    name: 'Discuss',

    data() {
        return {
            topics: [],
            got: false,
        };
    },

    mounted() {
        let info = {tag: this.$route.params.tag};
        //console.log(info);
        this.$axios.post('/getTopics', info).then(response => {
            let result = response.data;
            if(result.code != 200){
                this.$message.error("请求数据失败");
                return;
            }
            this.topics = result.data;
            this.got = true;
        })
    },

    updated(){
        //console.log("hahaha");
        if(!this.got){
            this.$message.error('请求数据失败');
        }
    },
    methods: {
        
    },
};
</script>

<style lang="scss" scoped>
  .el-row {
    margin-bottom: 20px;
    &:last-child {
      margin-bottom: 0;
    }
  }
  .el-col {
    border-radius: 4px;
    margin-bottom: 20px;
  }

  .router-link-active{
    text-decoration: none;
  }
  
  a{
    text-decoration: none;
  }

  a:hover{
    text-decoration: none;
  }

  .text {
    font-size: 15px;
  }

  .time {
    font-size: 13px;
    color: #999;
  }

  .item {
    margin-bottom: 19px;
  }

  .clearfix:before,
  .clearfix:after {
    display: table;
    content: "";
  }
  .clearfix:after {
    clear: both
  }

  .fixedButton {
    position: fixed;
    bottom: 7%;
    right: 1.5%; 
  }

</style>