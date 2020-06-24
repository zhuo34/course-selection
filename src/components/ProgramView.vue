<template>
    <div>
      <el-row  type="flex" justify="center" >
        <el-col :span="2">
          <el-button v-if="!isFinished"
                     v-popover:tablepop0 type="primary" round>添加课程</el-button>
          <el-popover ref="tablepop0" placement="bottom-start" trigger="click">
            <search-view :isProgramView="true" @addCourse="addNewCourse"></search-view>
          </el-popover>
        </el-col>
        <el-col :span="11">必修课程</el-col>
        <el-col :span="2">已选学分:{{requiredCredit}}</el-col>
        <el-col :span="1"></el-col>
      </el-row>

      <el-row>
      <el-table :data="requiredCourse" border style="width: 80%; margin: auto" height="200">
        <el-table-column prop="id" label="课程号" width="180" align="center"></el-table-column>
        <el-table-column prop="name" label="课程名称"  width="180" align="center"></el-table-column>
        <el-table-column prop="credit" label="学分" align="center"></el-table-column>
        <el-table-column label="操作" align="center">
          <template slot-scope="scope">
            <el-button v-if="!isFinished"
                       type="danger" @click="delTableRow(scope.$index, 'requiredCourse')"
                       :disabled="true">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      </el-row>

      <el-row type="flex" justify="center">
        <el-col :span="2"></el-col>
        <el-col :span="11">选修课程</el-col>
        <el-col :span="2">已选学分:{{selectiveCredit}}</el-col>
        <el-col :span="1"></el-col>
      </el-row>

      <el-table :data="selectiveCourse" border style="width: 80%; margin: auto" height="200">
        <el-table-column prop="id" label="课程号" width="180" align="center"></el-table-column>
        <el-table-column prop="name" label="课程名称"  width="180" align="center"></el-table-column>
        <el-table-column prop="credit" label="学分" align="center"></el-table-column>
        <el-table-column prop="state" label="操作" align="center">
          <template slot-scope="scope">
            <el-button v-if="!isFinished"
                       type="danger" @click="delTableRow(scope.$index, 'selectiveCourse')">删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <el-row type="flex" justify="center">
        <el-col :span="2"></el-col>
        <el-col :span="11">公共课程</el-col>
        <el-col :span="2">已选学分:{{commonCredit}}</el-col>
        <el-col :span="1"></el-col>
      </el-row>

      <el-table :data="commonCourse" border style="width: 80%; margin: auto" height="200">
        <el-table-column prop="id" label="课程号" width="180" align="center"></el-table-column>
        <el-table-column prop="name" label="课程名称"  width="180" align="center"></el-table-column>
        <el-table-column prop="credit" label="学分" align="center"></el-table-column>
        <el-table-column prop="state" label="操作" align="center">
          <template slot-scope="scope">
            <el-button v-if="!isFinished"
                       type="danger" @click="delTableRow(scope.$index, 'commonCourse')">删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <el-row type="flex" justify="center">
        <el-col :span="2">
          <el-button v-if="!isFinished" type="primary" @click="clickSave"
                     plain icon="el-icon-s-order" size="medium">保存</el-button>
        </el-col>
        <el-col :span="2"></el-col>
        <el-col :span="2">
          <el-button v-if="!isFinished" type="success" @click="clickSubmit" plain
                     icon="el-icon-check" size="medium">提交</el-button>
        </el-col>
      </el-row>
    </div>
</template>

<script>
import SearchView from './SearchView'
export default {
  name: 'ProgramView',
  components: {SearchView},
  data () {
    return {
      stuId: '3170756898',
      isFinished: false,
      totCreditStand: 0.0,
      selCreditStand: 0.0,
      comCreditStand: 0.0,
      requiredCDel: [],
      selectiveCDel: [],
      commonCDel: [],
      requiredCourse: [],
      selectiveCourse: [],
      commonCourse: [],
      courseList: []
    }
  },
  computed: {
    requiredCredit: function () {
      let sum = 0
      for (let i = 0; i < this.requiredCourse.length; i++) {
        sum = sum + this.requiredCourse[i].credit
      }
      return sum
    },
    selectiveCredit: function () {
      let su = 0
      for (let i = 0; i < this.selectiveCourse.length; i++) {
        su = su + this.selectiveCourse[i].credit
      }
      return su
    },
    commonCredit: function () {
      let s = 0
      for (let i = 0; i < this.commonCourse.length; i++) {
        s = s + this.commonCourse[i].credit
      }
      return s
    }
  },
  methods: {
    accessProgram () {
      // let ret = [{
      //   id: '21120261',
      //   name: '软件工程',
      //   credit: 2.5,
      //   state: 'normal'
      // }, {
      //   id: '21120262',
      //   name: '软件工程',
      //   credit: 2.5,
      //   state: 'normal'
      // }, {
      //   id: '21120263',
      //   name: '软件工程',
      //   credit: 2.5,
      //   state: 'normal'
      // }, {
      //   id: '21120264',
      //   name: '软件工程',
      //   credit: 2.5,
      //   state: 'normal'
      // }]
      this.$axios.get('/get-program', {
        params: {stuId: this.stuId}})
        .then(successResponse => {
          this.requiredCourse = []
          this.selectiveCourse = []
          this.commonCourse = []
          this.requiredCDel = []
          this.selectiveCDel = []
          this.commonCDel = []
          let allCourses = successResponse.data.courses
          this.isFinished = successResponse.data.isFinished
          this.isFinished = false
          this.totCreditStand = successResponse.data.minTotalCredit
          this.selCreditStand = successResponse.data.minOptionalCredit
          this.comCreditStand = successResponse.data.minPublicCredit
          for (let i = 0; i < allCourses.length; i++) {
            if (allCourses[i].type === 0) {
              // required
              this.requiredCourse.push({id: allCourses[i].id, name: allCourses[i].name, credit: allCourses[i].credit, state: 'normal'})
            } else if (allCourses[i].type === 1) {
              // selective
              this.selectiveCourse.push({id: allCourses[i].id, name: allCourses[i].name, credit: allCourses[i].credit, state: 'normal'})
            } else {
              // common
              this.commonCourse.push({id: allCourses[i].id, name: allCourses[i].name, credit: allCourses[i].credit, state: 'normal'})
            }
          }
        })
    },
    delTableRow: function (index, tableName) {
      // if (tableName === 'requiredCourse') {
      //   this.requiredCourse.splice(index, 1)
      // } else if (tableName === 'selectiveCourse') {
      //   this.selectiveCourse.splice(index, 1)
      // } else if (tableName === 'commonCourse') {
      //   this.commonCourse.splice(index, 1)
      // }
      if (tableName === 'requiredCourse') {
        if (this.requiredCourse[index].state === 'normal') {
          this.requiredCourse[index].state = 'deleted'
          this.requiredCDel.push(this.requiredCourse.splice(index, 1)[0])
        } else {
          this.requiredCourse.splice(index, 1)
        }
      } else if (tableName === 'selectiveCourse') {
        if (this.selectiveCourse[index].state === 'normal') {
          this.selectiveCourse[index].state = 'deleted'
          this.selectiveCDel.push(this.selectiveCourse.splice(index, 1)[0])
        } else {
          this.selectiveCourse.splice(index, 1)
        }
      } else if (tableName === 'commonCourse') {
        if (this.commonCourse[index].state === 'normal') {
          this.commonCourse[index].state = 'deleted'
          this.commonCDel.push(this.commonCourse.splice(index, 1)[0])
        } else {
          this.commonCourse.splice(index, 1)
        }
      }
    },
    addNewCourse (courseInfo) {
      let cType = courseInfo.type
      let cList = this.selectiveCourse
      let cDel = this.selectiveCDel
      if (!cType) {
        cList = this.commonCourse
        cDel = this.commonCDel
      }
      // add
      let key = courseInfo.id
      for (let i = 0; i < cDel.length; i++) {
        if (key === cDel[i].id) {
          cDel[i].state = 'normal'
          cList.push(cDel.splice(i, 1)[0])
          // this.$alert('添加成功！', '选课系统', {
          //   confirmButtonText: '我知道了',
          //   callback: action => {}
          // })
          this.$message({
            message: '添加成功！',
            type: 'success'
          });
          return
        }
      }
      let repeated = false
      for (let i = 0; i < cList.length; i++) {
        if (key === cList[i].id) {
          repeated = true
          break
        }
      }
      if (repeated) {
        // this.$alert('培养方案中不能添加重复的课程！', '选课系统', {
        //   confirmButtonText: '我知道了',
        //   callback: action => {}
        // })
        this.$message({
          message: '培养方案中不能添加重复的课程！',
          type: 'error'
        });
      } else {
        courseInfo['state'] = 'new'
        delete courseInfo.type
        cList.push(courseInfo)
        // this.$alert('添加成功！', '选课系统', {
        //   confirmButtonText: '我知道了',
        //   callback: action => {}
        // })
        this.$message({
          message: '添加成功！',
          type: 'success'
        });
      }
    },
    clickSave () {
      this.submit(0)
    },
    clickSubmit () {
      // judge if credit is enough
      if (this.requiredCredit + this.selectiveCredit + this.commonCredit >= this.totCreditStand && this.selectiveCredit >= this.selCreditStand && this.commonCredit >= this.comCreditStand) {
        this.$confirm('提交之后将不能修改培养方案，是否提交？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.submit(1)
        }).catch(() => {})
      } else {
        let msg = ''
        let offset = 0.0
        if (this.selectiveCredit < this.selCreditStand) {
          msg = '选修课程已选分数'
          offset = this.selCreditStand - this.selectiveCredit
        } else if (this.commonCredit < this.comCreditStand) {
          msg = '公共课程已选分数'
          offset = this.comCreditStand - this.commonCredit
        } else {
          msg = '所有课程总分数'
          offset = this.totCreditStand - this.requiredCredit - this.selectiveCredit - this.commonCredit
        }
        this.$alert(msg + '不满足要求，差' + offset.toString() + '分。', '选课系统', {
          confirmButtonText: '确定',
          callback: action => {}
        })
      }
    },
    submit (isSubmit) {
      let deletedAll = this.requiredCDel.concat(this.selectiveCDel)
      deletedAll = deletedAll.concat(this.commonCDel)
      let deletedId = []
      for (let i = 0; i < deletedAll.length; i++) {
        deletedId.push(deletedAll[i].id)
      }
      let newAll = this.selectiveCourse.concat(this.commonCourse)
      let newId = []
      for (let i = 0; i < newAll.length; i++) {
        if (newAll[i].state === 'new') {
          newId.push(newAll[i].id)
        }
      }
      console.log(isSubmit)
      this.$axios.post('/submit-program', {stuId: this.stuId, isSubmit: isSubmit, insert: newId, delete: deletedId})
        .then(successResponse => {
          console.log('submit success')
          let msg = '保存成功！'
          if (isSubmit) {
            msg = '提交成功！'
          }
          this.$alert(msg, '选课系统', {
            confirmButtonText: '确定',
            callback: action => {}
          })
          if (isSubmit) {
            this.accessProgram()
          }
        })
        .catch(failResponse => {
          console.log('fail')
        })
        .finally(() => this)
      // restore state
      for (let i = 0; i < this.selectiveCourse.length; i++) {
        this.selectiveCourse[i].state = 'normal'
      }
      for (let i = 0; i < this.commonCourse.length; i++) {
        this.commonCourse[i].state = 'normal'
      }
      this.commonCDel = []
      this.selectiveCDel = []
      this.requiredCDel = []
    }
  },
  mounted () {
    this.accessProgram()
  }
}
</script>

<style scoped>

  .el-row{
    margin-bottom: 20px;
    margin-top: 20px;
    padding: auto;
    font-size: 20px;
  }

</style>
