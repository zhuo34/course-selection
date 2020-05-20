<template>
      <el-container class="container">
        <el-header>
          <h1> {{ question.a }} + {{ question.b }} =  </h1>
        </el-header>
        <el-main>
          <el-form :model="question" :rules="questionRules" label-position="right"
                  label-width="0px" v-loading="loading">
            <el-form-item prop="ans">
              <el-input type="number" v-model="question.ans" auto-complete="off"></el-input>
            </el-form-item>
            <el-form-item style="width: 100%">
              <el-button type="primary" @click.native.prevent="submitClick" style="width: 100%">提交</el-button>
            </el-form-item>
          </el-form>
        </el-main>
      </el-container>
</template>

<script>
import axios from 'axios'

export default {
  name: 'HelloWorld',
  data () {
    return {
      questionRules: {
        ans: [{required: true, message: '请输入答案', trigger: 'blur'}]
      },
      question: {
        a: 1,
        b: 1,
        ans: 0
      },
      loading: false
    }
  },
  methods: {
    submitClick: function () {
      var _this = this
      this.loading = true
      axios.post('/check', {
        a: this.question.a,
        b: this.question.b,
        c: this.question.ans
      }).then((response) => {
        _this.loading = false
        var json = response.data
        if (json.isRight == '0') {
          _this.$alert('正确!', '结果')
          _this.question.a = parseInt(json.a)
          _this.question.b = parseInt(json.b)
        } else {
          _this.$alert('错误!', '结果')
        }
      })
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h1, h2 {
  font-weight: normal;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}
.container {
  border-radius: 15px;
  background-clip: padding-box;
  border: 1px solid #eee;
  margin-left: 25%;
  margin-right: 25%;
  box-shadow: 0 0 25px #cac6c6;
}
</style>
