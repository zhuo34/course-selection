<template>
    <div>
      <el-row  type="flex" justify="center">
        <p>必修课程  已选学分:</p>
        <p>{{requiredCredit}}</p>
      </el-row>

      <el-table :data="requiredCourse" border style="width: 80%" height="250">
        <el-table-column prop="id" label="课程号" width="180"></el-table-column>
        <el-table-column prop="name" label="课程名称"  width="180"></el-table-column>
        <el-table-column prop="credit" label="学分"></el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button type="info" @click="delTableRow(scope.$index, 'requiredCourse')">删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <el-row type="flex" justify="center">
        <p>选修课程 已选学分：</p>
        <p>{{selectiveCredit}}</p>
        <el-button v-popover:tablepop type="primary" round>添加课程</el-button>
        <el-popover ref="tablepop" placement="bottom-start" trigger="click">
          <search-view :showCourseTable="false"></search-view>
        </el-popover>
      </el-row>

      <el-table :data="selectiveCourse" border style="width: 80%" height="250">
        <el-table-column prop="id" label="课程号" width="180"></el-table-column>
        <el-table-column prop="name" label="课程名称"  width="180"></el-table-column>
        <el-table-column prop="credit" label="学分"></el-table-column>
        <el-table-column prop="state" label="操作">
          <template slot-scope="scope">
            <el-button type="info" @click="delTableRow(scope.$index, 'selectiveCourse')">删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <el-row type="flex" justify="center">
        <p>公共课程 已选学分：</p>
        <p>{{commonCredit}}</p>
      </el-row>

      <el-table :data="commonCourse" border style="width: 80%" height="250">
        <el-table-column prop="id" label="课程号" width="180"></el-table-column>
        <el-table-column prop="name" label="课程名称"  width="180"></el-table-column>
        <el-table-column prop="credit" label="学分"></el-table-column>
        <el-table-column prop="state" label="操作">
          <template slot-scope="scope">
            <el-button type="info" @click="delTableRow(scope.$index, 'commonCourse')">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
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

</style>
