<template>
    <div>
        <h1 class="h3 mb-3">话题详情</h1><br>
        <el-row :gutter="12" v-if="this.got">
            <el-col :span="11" style="margin-right: 44px">
                <el-descriptions  :column="2" size="medium" border>
                    <!-- <template slot="title" v-if="this.isFrom">
                        <el-button type="primary" size="small">修改</el-button>
                        <el-button type="primary" size="small">删除</el-button>
                    </template> -->

                    <el-descriptions-item>
                        <template slot="label">
                            <i class="el-icon-user"></i> 发起者
                        </template>
                        <span>{{this.topic.from}}</span>
                    </el-descriptions-item>


                    <el-descriptions-item>
                        <template slot="label">
                            <i class="el-icon-message"></i> 联系方式
                        </template>
                        {{this.topic.contact}}
                    </el-descriptions-item>


                    <el-descriptions-item>
                        <template slot="label">
                            <i class="el-icon-eleme"></i> 标题
                        </template>
                        {{this.topic.title}}
                    </el-descriptions-item>

                    


                    <el-descriptions-item>
                        <template slot="label">
                            <i class="el-icon-tickets"></i> 标签
                        </template>
                        <el-tag size="small">{{this.topic.tag}}</el-tag>
                    </el-descriptions-item>

                    

                    <el-descriptions-item>
                        <template slot="label">
                            <i class="el-icon-cherry"></i> 简述
                        </template>
                        {{this.topic.desc}}
                    </el-descriptions-item>

                </el-descriptions><br>

                <el-descriptions direction="vertical" :column="2" border>
                    <el-descriptions-item label="话题内容">
                        {{this.topic.content}}
                    </el-descriptions-item>
                </el-descriptions><br>
                
            </el-col>


            <el-col :span="11" >
                <div id="scroll">
                
                    <el-collapse v-model="activeName" @change="handleChange" accordion>
           
                        <el-collapse-item v-for="item in this.comments" :key="item.floor" :name="item.floor">
                            <template slot="title">&nbsp;&nbsp;&nbsp;&nbsp;
                                <span>{{item.content}}</span>
                                
                                
                                
                            </template>
                            
                            <div v-for="i in item.replies" :key="i.floor" class="response">
                                <span>{{i.from}}: {{i.content}}</span>   
                            </div>
                            <time class="time" style="float: right;">"{{item.from}}的楼层"</time>
                        </el-collapse-item>
                        



                    </el-collapse>
                
                </div><br>
                <div>
                    <el-form :inline="true" class="demo-form-inline">
                        <el-form-item>
                            <el-input v-model="reply.content" placeholder="发表评论"></el-input>
                        </el-form-item>
                        
                        <el-form-item>
                            <el-button type="primary" :loading="repling" @click="submitReply">{{this.replyBtnStatus}}</el-button>
                        </el-form-item>
                    </el-form>
                </div> 
            </el-col>         
 
        </el-row>
    </div>
</template>

<script>
export default {
    name: 'Topic',

    data() {
        return {
            topic: {
                num: -1,
                title: '',
                desc: '',
                content: '',
                tag: '',
                from: '',
                contact: '',
            },
            got: false,
            isFrom: false,
            activeName: '',
            reply: {
                topic: '',
                floor: '',
                to: 0,
                from: '',
                content: '',
            },
            repling: false,
            replyBtnStatus: 'Reply',
            comments: []
        };
    },

    mounted() {
        if(this.$route.params.floor !== undefined){
            this.activeName = this.$route.params.floor;
            this.reply.to = this.activeName;
        }
        this.reply.topic = this.$route.params.num;
        this.$axios.post("/getTopic", this.reply.topic).then(response => {
            let result = response.data;
            if(result.code !== 200){
                this.$message.error("话题信息请求错误");
                this.$router.push('/main/discuss');
                return;
            }
            this.topic = result.data;
            this.got = true;
            if(result.data.from === window.sessionStorage.getItem("loginUsername")){
                this.isFrom = true;
            }
        });

        this.$axios.post("/getReply", this.reply.topic).then(response => {
            let result = response.data;
            if(result.code !== 200){
                this.$message.error("评论请求错误");
                return;
            }
            this.comments = result.data;
        })
    },

    methods: {
        handleChange(val) {
            if(val === ''){
                this.reply.to = 0;
                return;
            }          
            this.reply.to = val;
            //console.log(this.reply.to);
        },

        submitReply(){
            if(this.reply.content === ''){
                this.$message("请勿回复空内容");
                return;
            }

            if(window.sessionStorage.getItem("loginUsername") == null){
              this.$message({
                type: 'warning',
                message: '请先登录'
              });
              return;
            }

            this.reply.topic = this.topic.num;
            this.reply.from = window.sessionStorage.getItem("loginUsername");
            
            let info = {topic: this.reply.topic, from: this.reply.from, to: this.reply.to, content: this.reply.content};

            this.repling = true;
            this.replyBtnStatus = 'Repling';

            this.$axios.post('/reply', info).then(response => {
                let result = response.data;
                if(result.code !== 200){
                    this.$message.error('发表评论失败, 请重试')
                    return;
                }
                this.$message({
                    type: 'success',
                    message: '回复成功'
                })
                this.$router.push({name: 'dispatch', params: {num: this.topic.num, replyFloor: this.reply.to}});
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
    height: 520px;  
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