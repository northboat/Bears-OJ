<template>
    <div>
        <h3>Write Something</h3><br>
        <div class="row">
            <div class="col-12 col-lg-6">

                <!-- 标题输入框 -->
                <div class="card">
                    <div class="card-header">
                        <h5 class="card-title mb-0">Title</h5>
                    </div>
                    <div class="card-body">
                        <input type="text" v-model="topic.title" class="form-control" placeholder="输入你的标题">
                    </div>
                </div>

                <!-- 简介输入框 -->
                <div class="card">
                    <div class="card-header">
                        <h5 class="card-title mb-0">Describe</h5>
                    </div>
                    <div class="card-body">
                        <textarea class="form-control" v-model="topic.desc" rows="2" placeholder="输入你的简介"></textarea>
                    </div>
                </div>


                <!-- 联系方式选择框 -->
                <div class="card">
                    <div class="card-header">
                        <h5 class="card-title mb-0">Contact</h5>
                    </div>
                    <div class="card-body">
                        

                        <div class="form-check form-switch" style="position: relative; left: -24px">
                            <el-radio v-model="radio" label="1">隐藏联系方式</el-radio><br><br>
                            <el-radio v-model="radio" label="2">使用默认邮箱作为联系方式</el-radio><br><br>
                            <el-radio v-model="radio" label="3">其他联系方式</el-radio><br><br>
                            <input v-if="this.radio === '3'" type="text" v-model="otherContact" class="form-control" placeholder="输入其他联系方式">
                        </div>

                    </div>
                </div>

              

                
            </div>

			<div class="col-12 col-lg-6">

                <!-- 标签下拉框 -->
                <div class="card">
                    <div class="card-header">
                        <h5 class="card-title mb-0">Tag</h5>
                    </div>
                    <div class="card-body">
						<select class="form-control mb-3" id="selector">
                            <option selected value="划水闲聊">划水闲聊</option>
                            <option value="Books">书籍推荐</option>
                            <option value="Push">内推人脉</option>
                            <option value="Teams">组队资讯</option>
                            <option value="技术探讨">技术探讨</option>
                            <option value="Bug发掘">Bug发掘</option>
                            <option value="数据结构与算法">数据结构与算法</option>
                        </select>

						<!-- <select multiple class="form-control">
                            <option>Open this select menu</option>
                            <option>One</option>
                            <option>Two</option>
                            <option>Three</option>
                        </select> -->
					</div>
				</div>

				<!-- 内容输入框 -->
                <div class="card">
                    <div class="card-header">
                        <h5 class="card-title mb-0">Content</h5>
                    </div>
                    <div class="card-body">
                        <textarea class="form-control" v-model="topic.content" rows="12" placeholder="输入你的内容"></textarea>
                    </div>
                </div>

                <div style="text-align: center;">
                    <el-button @click="submit()" :loading="submiting">提交</el-button>
                </div>
                

                <!-- <div class="card">
                    <div class="card-header">
                        <h5 class="card-title mb-0">Sizes</h5>
                    </div>
                    <div class="card-body">
                        <input class="form-control form-control-lg mb-3" type="text" placeholder="Large input">
                        <input class="form-control mb-3" type="text" placeholder="Medium input">
                        <input class="form-control form-control-sm" type="text" placeholder="Small input">
                    </div>
                </div>

                
                <div class="card">
                    <div class="card-header">
                        <h5 class="card-title mb-0">Checkboxes</h5>
                    </div>
                    <div class="card-body">
                        <div>
							<label class="form-check">
                                <input class="form-check-input" type="checkbox" value="">
                                <span class="form-check-label">
                                    Option one is this and that&mdash;be sure to include why it's great
                                </span>
                            </label>
							<label class="form-check">
                                <input class="form-check-input" type="checkbox" value="" disabled>
                                <span class="form-check-label">
                                    Option two is disabled
                                </span>
                            </label>
						</div>
						<div>
							<label class="form-check form-check-inline">
                                <input class="form-check-input" type="checkbox" value="option1">
                                    <span class="form-check-label">
                                        1
                                    </span>
                            </label>
							<label class="form-check form-check-inline">
                                <input class="form-check-input" type="checkbox" value="option2">
                                <span class="form-check-label">
                                    2
                                </span>
                            </label>
							<label class="form-check form-check-inline">
                                <input class="form-check-input" type="checkbox" value="option3" disabled>
                                <span class="form-check-label">
                                    3
                                </span>
                            </label>
						</div>
					</div>
				</div> -->
				

							
			</div>
		</div>

    </div>
</template>

<script>
export default {
    name: 'Write',

    data() {
        return {
            radio: '2',
            otherContact: '',

            submiting: false,

            topic:{
                title: '',
                desc: '',
                tag: '',
                from: '',
                contact: '',
                content: '',
            },
        };
    },

    watch:{
        hide(){
            console.log('hahaha');
        }
    },

    mounted() {
        
    },

    methods: {
        

        getTag(){
            let selector = document.getElementById('selector');
            let index = selector.selectedIndex;
            //console.log(index);
            return selector.options[index].value;
            //console.log(this.topic.tag);
        },

        getFrom(){
            return window.sessionStorage.getItem("loginUsername")
        },

        getContact(){
            switch(this.radio){
                case '1': return '匿';
                case '2': return window.sessionStorage.getItem("loginAccount");
                case '3': return this.otherContact;
            }
        },

        submit(){

            this.submiting = true;

            if(window.sessionStorage.getItem("loginAccount") == null){
               
                this.$message('请先登录');
                this.submiting = false;
                return;
            }
            this.topic.tag = this.getTag();
            //console.log(this.topic.tag)
            this.topic.from = this.getFrom();
            //console.log(this.topic.from)
            this.topic.contact = this.getContact();
            //console.log(this.topic.contact)

            if(this.topic.tag == '' || this.topic.from == null 
               || this.topic.contact == '' || this.topic.title == ''){

                this.$message('信息不全, 请重新填写');
                this.submiting = false;
                return;
            }

            if(this.topic.content == ''){
                this.$message('请勿输入空内容');
                this.submiting = false;
                return;
            }

            let info = {title: this.topic.title, desc: this.topic.desc,
                        tag: this.topic.tag, from: this.topic.from,
                        contact: this.topic.contact, content: this.topic.content};
            //console.log(info);

            this.$axios.post("/write", info).then(response => {
                let result = response.data;
                if(result.code !== 200){
                    this.$message.error(result.message);
                    this.submiting = false;
                    return;
                }
                this.$message({
                    type: 'success',
                    message: '发布话题成功'
                })
                this.$router.push({name: 'discuss', params: {tag: "All"}});
            })
        }
    },
};
</script>

<style lang="scss" scoped>

</style>