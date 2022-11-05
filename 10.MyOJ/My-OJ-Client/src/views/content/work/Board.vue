<template>
    <div>
        <h3>Drawing Borad</h3><br><br>
        
        <el-row :gutter="20">
                  
            <!-- 绘画区 -->
            <el-col :span="10">
                <el-input type="text" v-model="painting.title" placeholder="若要上传作品，请输入画作标题"></el-input><br><br>
                <div class="grid-content bg-purple">  
                
                    <el-input
                        type="textarea"
                        :rows="21"
                        placeholder="开始绘制...<h1 style='color: blue'>hahaha</h1>"
                        v-model="painting.content"
                        id="drawingArea">
                    </el-input><br><br>

                    <el-button icon="el-icon-edit" @click="draw">Draw</el-button>
                    <el-button icon="el-icon-s-promotion" @click="submit" :loading="submiting">Submit</el-button>
                    <el-button style="float: right;" @click="clean">清空</el-button>
                
                </div>
                
            </el-col>

            <!-- 热门作品展示 -->
            <el-col :span="7" v-for="item in this.paintings.slice(this.index, this.index+4)" :key="item.num">
                <el-card shadow="hover" class="box-card">
                    <div slot="header" class="clearfix">
                        <span style="font-size: 18px">{{item.title}}</span>
                        
                        <el-button icon="el-icon-document" style="float: right" @click="copy(item.content)"></el-button>
                        <el-button icon="el-icon-magic-stick" style="float: right" @click="show(item.content)"></el-button>
                    </div>
                    <div class="text item">
                        {{item.desc}}
                    </div><br>
                    <div class="bottom clearfix">
                        <time class="time">
                            <el-button icon="el-icon-thumb" @click="thumb()" size="small">点赞 {{item.thumb}}</el-button>
                        </time>
                        <time class="time" style="float: right; margin-top: 7px">From {{item.from}}</time>                    
                    </div>
                </el-card><br><br>
                
            </el-col>&nbsp;&nbsp;
            <el-button @click="flush">查看更多</el-button>
            
        </el-row>



        
    </div>
</template>

<script>
export default {
    name: 'Board',

    data() {
        return {

            submiting: false,

            painting: {
                title: '',
                desc: '',
                content: '',
                from: '',
            },
            // 新建一张表存画
            paintings: [],
            index: 0
        };
    },

    mounted() {
        this.$axios.get('/getPaintings').then(response => {
            let result = response.data;
            if(result.code != 200){
                this.$message.error("请求数据失败");
                return;
            }
            this.paintings = result.data;
        })
    },

    methods: {

        flush(){
            if(this.index+4 >= this.paintings.length){
                this.$message('已经到底辽')
                this.index = 0;
                return;
            }
            this.index = this.index+4;
        },

        clean(){
            this.painting.content = '';
            this.$message({
                type: 'success',
                message: '清除成功'
            });
        },

        draw() {
            this.$alert(this.painting.content, 'Your Painting', {
                dangerouslyUseHTMLString: true
            });
        },

        show(val){
            this.$alert(val, 'the Painting', {
                dangerouslyUseHTMLString: true
            });
        },

        copy(val){
            this.painting.content = val;
            this.$message({
                type: 'success',
                message: '复制成功'
            });
        },

        submit(){
            this.submiting = true;
            if(window.sessionStorage.getItem("loginUsername") == null){
              this.$message({
                type: 'warning',
                message: '请先登录'
              });
              this.submiting = false;
              return;
            }
            if(this.painting.title == ''){
                this.$message('标题未填写');
                this.submiting = false;
                return;
            }

            let user = window.sessionStorage.getItem("loginUsername");

            // 弹窗完善简介
            this.$prompt('作品简介(可为空)', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
            }).then(({ value }) => {
                if(value != null){
                    this.painting.desc = value;
                }
                this.$message({
                    type: 'success',
                    message: '你的简介是: ' + value
                });
                this.painting.from = window.sessionStorage.getItem("loginUsername");

                console.log(this.painting.title);

                let info = {title: this.painting.title, desc: this.painting.desc, from: user, content: this.painting.content};
                this.$axios.post("/submitPainting", info).then(response => {
                    let result = response.data;
                    if(result.code != 200){
                        this.$message.error('上传失败');
                        this.submiting = false;
                        return;
                    }
                    this.$message({
                        type: 'success',
                        message: '上传成功'
                    })
                    this.$router.push({name: 'dispatch', params: {flag: 'board'}});
                })
            }).catch(() => {
                this.$message({
                    type: 'info',
                    message: '取消上传'
                });      
                this.submiting = false; 
            });

            
        },
    },
};
</script>

<style lang="scss" scoped>

</style>