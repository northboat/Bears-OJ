<template>
    <div>
        <el-main>
            <p style="font: 18px large;">击剑区</p>
            <div id="scroll">
                
                <el-collapse v-model="activeName" @change="handleChange" accordion>
        
                    <!-- <el-collapse-item v-for="item in this.comments" :key="item.floor" :name="item.floor">
                        <template slot="title">&nbsp;&nbsp;&nbsp;&nbsp;
                            <span>{{item.content}}</span>    
                        </template>
                        
                        <div v-for="i in item.replies" :key="i.floor" class="response">
                            <span>{{i.from}}: {{i.content}}</span>   
                        </div>
                        <time class="time" style="float: right;">"{{item.from}}的楼层"</time>
                    </el-collapse-item> -->
                    
                    <el-collapse-item v-for="item in this.comments" :key="item.floor" :name="item.floor">
                        <template slot="title">&nbsp;&nbsp;&nbsp;&nbsp;
                            <span>{{item.content}}</span>                           
                        </template>
                        
                        <div class="response" v-for="i in item.comments" :key="i.floor">
                            <span>{{i.from}}: {{i.content}}</span>   
                        </div>
                        <time class="time" style="float: right;">"{{item.from}}的楼层"</time>
                    </el-collapse-item>


                </el-collapse>
            
            </div><br>
            <div>
                <el-form :inline="true" class="demo-form-inline">
                    <el-form-item>
                        <el-input v-model="comment.content" placeholder="发表见解"></el-input>
                    </el-form-item>
                    
                    <el-form-item>
                        <el-button type="primary" :loading="commenting" @click="submitComment">评论</el-button>
                    </el-form-item>
                </el-form>
            </div> 
        </el-main>
    </div>
</template>

<script>
export default {
    name: 'Comment',

    data() {
        return {
            comments: [],
            activeName: 0,
            commenting: false,
            comment: {
                question: '',
                from: '',
                to: '0',
                content: '',
            }
        };
    },

    mounted() {
        if(this.$route.params.floor !== undefined){
            //console.log('hahaha');
            this.activeName = this.$route.params.floor;
            this.comment.to = this.activeName;
        }
        this.comment.question = this.$route.params.num;
        let info = {question: this.comment.question}
        //console.log(info);
        this.$axios.post("/getComments", info).then(response => {
            let result = response.data;
            if(result.code != 200){
                this.$message.error("请求评论失败");
                return;
            }
            this.comments = result.data;
        })
    },

    methods: {
        handleChange(val) {
            if(val === ''){
                this.comment.to = 0;
                return;
            }          
            this.comment.to = val;
            //console.log(val);
        },
        submitComment(){
            if(window.sessionStorage.getItem("loginUsername") == null){
                this.$message("请先登录");
                return;
            }
            this.commenting = true;
            this.comment.from = window.sessionStorage.getItem("loginUsername");
            let info = {question: this.comment.question, from: this.comment.from, to: this.comment.to, content: this.comment.content};

            this.$axios.post("/comment", info).then(response => {
                let result = response.data;
                if(result.code != 200){
                    this.$message("回复超出长度限制");
                    return;
                }
                this.$message({
                    type: 'success',
                    message: "评论成功"
                })
                this.$router.push({name: 'dispatch', params: {num: this.comment.question, commentFloor: this.comment.to}})
            })
        }
    },
};
</script>

<style lang="scss" scoped>
  #scroll{
    border-radius: 24px;
    -webkit-overflow-scrolling: touch;
    overflow-y: scroll;//overflow-x 横屏滚动 overflow-y 竖屏滚动
    overflow-x: hidden;
    white-space: nowrap;
    height: 400px;
    width: 90%;
    padding-right: 2px;
  }

  .response{
      padding-left: 14px;
      margin-top: 4px;
  }

  .time {
    font-size: 13px;
    color: #999;
  }
</style>