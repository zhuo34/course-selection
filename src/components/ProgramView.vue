<template>
    <div>
      <el-row  type="flex" justify="center" >
        <el-col :span="2"></el-col>
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
            <el-button type="danger" @click="delTableRow(scope.$index, 'requiredCourse')">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      </el-row>

      <el-row type="flex" justify="center">
        <el-col :span="2"></el-col>
        <el-col :span="11">选修课程</el-col>
        <el-col :span="2">已选学分:{{selectiveCredit}}</el-col>
        <el-col :span="1">
          <el-button v-popover:tablepop0 type="primary" round>添加</el-button>
          <el-popover ref="tablepop0" placement="bottom-start" trigger="click">
            <search-view :showCourseTable="false"></search-view>
          </el-popover>
        </el-col>
      </el-row>

      <el-table :data="selectiveCourse" border style="width: 80%; margin: auto" height="200">
        <el-table-column prop="id" label="课程号" width="180" align="center"></el-table-column>
        <el-table-column prop="name" label="课程名称"  width="180" align="center"></el-table-column>
        <el-table-column prop="credit" label="学分" align="center"></el-table-column>
        <el-table-column prop="state" label="操作" align="center">
          <template slot-scope="scope">
            <el-button type="danger" @click="delTableRow(scope.$index, 'selectiveCourse')">删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <el-row type="flex" justify="center">
        <el-col :span="2"></el-col>
        <el-col :span="11">公共课程</el-col>
        <el-col :span="2">已选学分:{{commonCredit}}</el-col>
        <el-col :span="1">
          <el-button v-popover:tablepop1 type="primary" round>添加</el-button>
          <el-popover ref="tablepop1" placement="bottom-start" trigger="click">
            <search-view :showCourseTable="false"></search-view>
          </el-popover>
        </el-col>
      </el-row>

      <el-table :data="commonCourse" border style="width: 80%; margin: auto" height="200">
        <el-table-column prop="id" label="课程号" width="180" align="center"></el-table-column>
        <el-table-column prop="name" label="课程名称"  width="180" align="center"></el-table-column>
        <el-table-column prop="credit" label="学分" align="center"></el-table-column>
        <el-table-column prop="state" label="操作" align="center">
          <template slot-scope="scope">
            <el-button type="danger" @click="delTableRow(scope.$index, 'commonCourse')">删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <el-row type="flex" justify="center">
        <el-button type="success" @click="submit" plain icon="el-icon-check" size="medium">提交</el-button>
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
      requiredCourse: [{
        id: '21120261',
        name: '软件工程',
        credit: 2.5
      }, {
        id: '21120262',
        name: '软件工程',
        credit: 2.5
      }, {
        id: '21120263',
        name: '软件工程',
        credit: 2.5
      }, {
        id: '21120264',
        name: '软件工程',
        credit: 2.5
      }],
      selectiveCourse: [{
        id: '21120261',
        name: '软件工程',
        credit: 2.5
      }, {
        id: '21120262',
        name: '软件工程',
        credit: 2.5
      }, {
        id: '21120263',
        name: '软件工程',
        credit: 2.5
      }, {
        id: '21120264',
        name: '软件工程',
        credit: 2.5
      }],
      commonCourse: [{
        id: '21120261',
        name: '软件工程',
        credit: 2.5
      }, {
        id: '21120262',
        name: '软件工程',
        credit: 2.5
      }, {
        id: '21120263',
        name: '软件工程',
        credit: 2.5
      }, {
        id: '21120264',
        name: '软件工程',
        credit: 2.5
      }],
      courseList: [{
        id: '21120261',
        name: '软件工程',
        credit: 2.5
      }, {
        id: '21120262',
        name: '软件工程',
        credit: 2.5
      }, {
        id: '21120263',
        name: '软件工程',
        credit: 2.5
      }, {
        id: '21120264',
        name: '软件工程',
        credit: 2.5
      }]
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
    delTableRow: function (index, tableName) {
      if (tableName === 'requiredCourse') {
        this.requiredCourse.splice(index, 1)
      } else if (tableName === 'selectiveCourse') {
        this.selectiveCourse.splice(index, 1)
      } else if (tableName === 'commonCourse') {
        this.commonCourse.splice(index, 1)
      }
    },
    addCourse: function (index, tableName) {
    }
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
