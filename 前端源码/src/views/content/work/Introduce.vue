<template>

    <el-main>
    <p style="font: 18px large;">题目描述:&nbsp;{{this.detail.desc}}</p><br>
    <p style="font: 14px Base">输入样例:<br><br>{{this.detail.example_input}}<br><br>样例输出:<br><br>{{this.detail.example_output}}</p><br>

    <!-- 自定义样例 -->
    <p style="font: 14px Base">自定义输入样例:<br><br>
        <el-input placeholder="拜托按上头的样例格式输入, 这破功能经不住考验" v-model="customized_input" :disabled="disabled" clearable></el-input><br><br>
        自定义样例输出:<br><br>{{this.customized_output}}
    </p><br>

    <p style="font: 16px Medium">
        提示:&nbsp;{{this.detail.tips}}
    </p><br>

    <p style="font: 14px Base;">
        内存限制:&nbsp;{{this.detail.memory_limit}}MB&nbsp;&nbsp;&nbsp;&nbsp;
        时间限制:&nbsp;{{this.detail.time_limit}}ms    
    </p>
      
    </el-main>
 
</template>

<script>
export default {
    name: 'Introduce',

    watch: {
        customized_input(){
          //console.log(this.detail.num);
          this.solve(this.detail.num);
        }
    },

    data() {
        return {
            detail:{
                num: '',
                memory_limit: '',
                time_limit: '',
                desc: '',
                example_input: '',
                example_output: '',
                tips: '',
                customized: '',               
            },
            disabled: true,
            customized_input: '',
            customized_output: ''
        };
    },

    mounted() {
        let info = {num: this.$route.params.num};
        this.$axios.post('/getDetail', info).then(response=>{
            //console.log(res.data);
            let result = response.data;
            if(result.code === 1001){
              this.$router.push({name: 'repository'});
              this.$message.error('请求参数错误');
              return;
            } else if(result.code === 2008){
              this.$router.push({name: 'repository'});
              this.$message.error('请求资源不存在');
              return;
            }
            if(result.data.customized === 1){
              this.disabled = false;
            }
            this.detail = result.data;
            window.sessionStorage.setItem("memory_limit", this.detail.memory_limit);
        });
    },


    methods: {
        isNumber(value){
          return (typeof value === 'number' && !isNaN(value));
        },
        solve(num){
          switch(num){
            case 0:{
              let val = Number(this.customized_input);
              if(!this.isNumber(val)){
                this.customized_output = '输入不合规范';
                return;
              }
              let res = this.customized_input.split("");
              if(val < 0){
                res.reverse();
                res.pop();     
                res.unshift("-");
                console.log(res);
              } else {
                res.reverse();
              }
              this.customized_output = res.join("");
              return;
            }
          }
        }
    },
};
</script>

<style lang="scss" scoped>

</style>