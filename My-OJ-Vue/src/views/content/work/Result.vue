<template>
<div>
    <el-main v-if="after_judge">
        <p style="font: 18px large">{{this.content.title}}</p><br>

        <p style="font: 14px Base">{{this.content.key1}}<br><br>{{this.content.val1}}</p><br>
        <p style="font: 14px Base">{{this.content.key2}}<br><br>{{this.content.val2}}</p><br>
        <p style="font: 14px Base">{{this.content.key3}}<br><br>{{this.content.val3}}</p><br>
        <p style="font: 14px Base">{{this.content.key4}}<br><br>{{this.content.val4}}</p><br>

        <el-button type="primary" style="margin-left: -4px" @click="checkHistory">查看历史代码</el-button>
        <!-- <el-drawer title="上次提交代码" size="44%" :visible.sync="drawer" >
            <div style="padding-left: 20px">
                <span>{{this.content.code}}</span>
            </div>
        </el-drawer> -->
    </el-main>

    <el-main v-else-if="before_signin">
        <p style="font: 20px Extra large">尚未登录</p><br>
    </el-main>

    <el-main v-else>
        <p style="font: 20px Extra large">暂无结果</p><br>
    </el-main>
</div>
</template>

<script>
export default {
    name: 'Result',

    data() {
        return {
            //drawer: false,
            content: {
                title: '',
                key1: '',
                key2: '',
                key3: '',
                key4: '',
                val1: '',
                val2: '',
                val3: '',
                val4: '',
                code: ''
            },
            result: {
                status: -1,
                passNum: '',
                realAns: '',
                curAns: '',
                initTime: '',
                destroyTime: '',
                timeLimit: '',
                memoryLimit: '',
                duration: '',
                srcData: '',
                averageTime: '',
                memoryUsage: '',
                compileInfo: ''
            },
            after_judge: false,
            before_signin: false,
        };
    },

    mounted() {
        if(window.sessionStorage.getItem("loginUsername") === null){
            this.before_signin = true;
            return;
        }
        if(this.$route.params.result !== undefined){
            //console.log("hahaha")
            this.after_judge = true;
            this.result = this.$route.params.result;
            
            //console.log(this.result);
            
            let code = this.$route.params.code;
            // 用正则表达式将字符串中的换行空格全换为html的格式，便于展示
            code = code.replace(/\n/g, "<br>");
            code = code.replace(/\t/g, "&nbsp;&nbsp;&nbsp;&nbsp;");
            code = code.replace(/    /g, "&nbsp;&nbsp;&nbsp;&nbsp;");
            //console.log(code);
            this.content.code = code;

            //更新result库
            //console.log(this.result.status);
            if(this.result.status !== -1){
                this.setKey(this.result.status);
                this.setVal(this.result.status);
                this.storeRes();
            }
        } else{ //从sql中拿
            this.getRes();
            return;
        }
        
        
    },

    methods: {
        checkHistory(){
            this.$notify({
                title: '上次提交代码',
                message: this.content.code,
                duration: 0,
                dangerouslyUseHTMLString: true,
                offset: 300,
            });
        },

        storeRes(){
            let info = {account: window.sessionStorage.getItem("loginAccount"),
                        num: this.$route.params.num, title: this.content.title,
                        key1: this.content.key1, key2: this.content.key2,
                        key3: this.content.key3, key4: this.content.key4,
                        val1: this.content.val1, val2: this.content.val2,
                        val3: this.content.val3, val4: this.content.val4,
                        code: this.content.code};
            this.$axios.post("/storeRes", info).then(response => {
                if(response.data.code === 200){
                    this.$message({
                        type: 'success',
                        message: '历史记录完毕',
                    });
                }  
            })
        },
        getRes(){
            let info = {account: window.sessionStorage.getItem("loginAccount"), num: this.$route.params.num};
            this.$axios.post("/getRes", info).then(response => {
                if(response.data.code !== 200){
                    return;
                }
                let data = response.data.data;
                this.content.key1 = data.key1;
                this.content.key2 = data.key2;
                this.content.key3 = data.key3;
                this.content.key4 = data.key4;
                this.content.val1 = data.val1;
                this.content.val2 = data.val2;
                this.content.val3 = data.val3;
                this.content.val4 = data.val4;
                this.content.title = data.title;
                this.content.code = data.code;
                this.after_judge = true;
            });       
        },
        setKey(num){
            switch(num){
                case 1:{
                    this.content.title = '成功通过 OwO'; 
                    this.content.key1 = "通过样例:";
                    this.content.key2 = "平均用时:";
                    this.content.key3 = "内存使用:";
                    this.content.key4 = "创建容器时间:"
                    break;
                }
                case 2:{
                    this.content.title = '编译失败 QAQ'; 
                    this.content.key1 = "通过样例:";
                    this.content.key2 = "错误信息:";
                    this.content.key3 = "容器创建时间:";
                    this.content.key4 = "容器销毁时间:"
                    break;
                } 
                case 3:{
                    this.content.title = '解答错误 TAT';
                    this.content.key1 = "通过样例:";
                    this.content.key2 = "当前样例:";
                    this.content.key3 = "正确答案:";
                    this.content.key4 = "当前答案:"
                    break;
                } 
                case 4:{
                    this.content.title = '运行超时 =A='; 
                    this.content.key1 = "通过样例:";
                    this.content.key2 = "当前样例:";
                    this.content.key3 = "时间限制:";
                    this.content.key4 = "当前用时:"
                    break;
                }
                case 5:{
                    this.content.title = '超出内存限制 Orz'; 
                    this.content.key1 = "通过样例:";
                    this.content.key2 = "平均用时:";
                    this.content.key3 = "内存限制:";
                    this.content.key4 = "当前使用内存:"
                    break;
                }
            }
        },
        setVal(num){
            switch(num){
                case 1:{
                    this.content.val1 = this.result.passNum;
                    this.content.val2 = this.result.averageTime + "ms";
                    this.content.val3 = this.result.memoryUsage;
                    this.content.val4 = this.result.initTime;
                    break;
                }
                case 2:{
                    this.content.val1 = this.result.passNum;
                    this.content.val2 = this.result.compileInfo;
                    this.content.val3 = this.result.initTime;
                    this.content.val4 = this.result.destroyTime;
                    break;
                } 
                case 3:{
                    this.content.val1 = this.result.passNum;
                    this.content.val2 = this.result.srcData;
                    this.content.val3 = this.result.realAns;
                    this.content.val4 = this.result.curAns;
                    break;
                } 
                case 4:{
                    this.content.val1 = this.result.passNum;
                    this.content.val2 = this.result.srcData;
                    this.content.val3 = this.result.timeLimit + "ms";
                    this.content.val4 = this.result.duration + "ms";
                    break;
                }
                case 5:{
                    this.content.val1 = this.result.passNum;
                    this.content.val2 = this.result.averageTime + "ms";
                    this.content.val3 = this.result.memoryLimit;
                    this.content.val4 = this.result.memoryUsage;
                    break;
                }
            }
        }
    },
};
</script>

<style lang="scss" scoped>

</style>