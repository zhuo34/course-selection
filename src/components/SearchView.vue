<template>
  <div v-loading="searchLoading || myCourseLoading">
    <el-row type="flex" justify="space-around" class="func-row">
      <el-button v-if="showCourseTable" v-popover:tablepop
                 type="primary" round>显示课表</el-button>

      <el-popover ref="tablepop" placement="bottom-start" trigger="click">
        <el-table :data="courseTable" style="width: 100%" border
                  :span-method="combineCell" :cell-class-name="hasCourse"
                  row-class-name="course-table-row">
          <el-table-column prop="time" label="时间" align="center"/>
          <el-table-column prop="mon" label="星期一" align="center"/>
          <el-table-column prop="tue" label="星期二" align="center"/>
          <el-table-column prop="wed" label="星期三" align="center"/>
          <el-table-column prop="thu" label="星期四" align="center"/>
          <el-table-column prop="fri" label="星期五" align="center"/>
          <el-table-column prop="sat" label="星期六" align="center"/>
          <el-table-column prop="sun" label="星期日" align="center"/>
        </el-table>
        <el-table :data="myCourses" style="width: 100%" border>
          <el-table-column prop="filtering" label="状态" align="center">
            <template slot-scope="scope">
              <span v-if="scope.row.filtering"
                    style="color: red">筛选中</span>
              <span v-else
                    style="color: blue">已选上</span>
            </template>
          </el-table-column>
          <el-table-column prop="id" label="课程号" align="center"/>
          <el-table-column prop="teacher" label="教师" align="center"/>
          <el-table-column prop="time" label="上课时间" align="center"/>
          <el-table-column prop="place" label="上课地点" align="center"/>
          <el-table-column prop="op" label="操作" align="center">
            <template slot-scope="scope">
              <el-button type="danger"
                @click="dropCourse(scope.$index)">退选</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-popover>

      <el-select class="search-option" v-model="searchTitle1" placeholder="请选择">
        <el-option v-for="item in searchTitleOptions" :key="item"
                   :label="item" :value="item"></el-option>
      </el-select>
      <el-input class="search-input" v-model="searchInfo1"
                prefix-icon="el-icon-search" clearable placeholder="请输入内容"/>

      <el-select class="search-option" v-model="searchTitle2" placeholder="请选择">
        <el-option v-for="item in searchTitleOptions" :key="item"
                   :label="item" :value="item"></el-option>
      </el-select>
      <el-input class="search-input" v-model="searchInfo2"
                prefix-icon="el-icon-search" clearable placeholder="请输入内容"/>

      <el-button type="primary" @click="accessSearchResults">查询</el-button>

    </el-row>

    <el-table :data="searchResults" style="width: 100%" :row-key="getRowKeys"
              :expand-row-keys="expands" @expand-change="expandSelect"
              :row-class-name="courseSelected" @row-click="rowClick">

      <el-table-column type="expand" align="center">
        <el-table :data="chosenCourseDetails" style="width: 100%"
                  :row-class-name="whichCourseSelected" v-loading="detailLoading">
          <el-table-column prop="teacher" sortable label="教师" align="center"/>
          <el-table-column prop="courseTime" sortable label="上课时间" align="center"/>
          <el-table-column prop="coursePlace" sortable label="上课地点" align="center"/>
          <el-table-column prop="examTime" sortable label="考试时间" align="center"/>
          <el-table-column prop="remainNum" sortable label="余量" align="center"/>
          <el-table-column prop="totalNum" sortable label="容量" align="center"/>
          <el-table-column prop="chosenNum" sortable label="待定人数" align="center"/>
          <el-table-column prop="op" label="操作" align="center">
            <template slot-scope="scope">
              <el-button
                :type="chosenCourseDetails[scope.$index].chosen ? 'danger' : 'primary'"
                @click="modifyChosen(scope.$index)">
                {{chosenCourseDetails[scope.$index].chosen ? '退选' : '选课'}}</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-table-column>

      <el-table-column label="课程ID" prop="id" align="center"/>
      <el-table-column label="课程名称" prop="name" align="center"/>
      <el-table-column label="学分" prop="credit" align="center"/>
      <el-table-column label="选课状态" prop="chosen" align="center">
        <template slot-scope="scope">
          <b v-if="scope.row.chosen">已选</b>
          <span v-else>未选</span>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
export default {
  props: {
    showCourseTable: {
      default: true
    }
  },
  name: 'SearchView',
  data () {
    return {
      searchLoading: false,
      detailLoading: false,
      myCourseLoading: false,
      searchTitleOptions: [
        '课程名称', '课程代码', '教师姓名', '课程类别',
        '上课时间', '上课地点', '学期'
      ],
      searchTitle1: '课程名称',
      searchTitle2: '课程名称',
      searchInfo1: '',
      searchInfo2: '',
      expands: [],
      searchResults: [],
      chosenCourseDetails: [],
      courseTable: [],
      myCourses: []
    }
  },
  methods: {
    getRowKeys (row) {
      return row.id
    },
    courseSelected ({rowIndex}) {
      if (this.searchResults[rowIndex].chosen) {
        return 'chosen-row'
      } else {
        return ''
      }
    },
    whichCourseSelected ({rowIndex}) {
      if (this.chosenCourseDetails[rowIndex].chosen) {
        return 'chosen-row'
      } else {
        return ''
      }
    },
    hasCourse ({row, column, rowIndex, columnIndex}) {
      if (columnIndex === 0 || row[column.property] === '') {
        return ''
      } else {
        return 'cell-blue'
      }
    },
    expandSelect (row, expandedRows) {
      this.expands = []
      if (expandedRows.length > 0 && row) {
        this.expands.push(row.id)
        this.accessCourseDetail(row)
      }
    },
    rowClick (row, event, column) {
      let collapse = this.expands.indexOf(row.id) >= 0
      this.expands = []
      if (!collapse) {
        this.expands.push(row.id)
        this.accessCourseDetail(row)
      }
    },
    modifyChosen (index) {
      this.chosenCourseDetails[index].chosen = !this.chosenCourseDetails[index].chosen
    },
    dropCourse (index) {

    },
    combineCell ({row, column, rowIndex, columnIndex}) {
      if (row[column.property] === '') {
        return {
          rowspan: 1,
          colspan: 1
        }
      }
      if (rowIndex > 0 && row[column.property] === this.courseTable[rowIndex - 1][column.property]) {
        return {
          rowspan: 0,
          colspan: 0
        }
      } else {
        let rows = 1
        for (let i = rowIndex; i < this.courseTable.length - 1; i++) {
          if (row[column.property] === this.courseTable[i + 1][column.property]) {
            rows = rows + 1
          }
        }
        return {
          rowspan: rows,
          colspan: 1
        }
      }
    },

    // Data access methods
    // 注意：之后要换成异步操作
    accessSearchResults () {
      this.searchLoading = true

      let ret = [{
        id: '21120261',
        name: '软件工程',
        state: '未选',
        chosen: false,
        credit: 1.0.toFixed(1)
      }, {
        id: '21120262',
        name: '软件工程',
        state: '未选',
        chosen: true,
        credit: 1.5.toFixed(1)
      }, {
        id: '21120263',
        name: '软件工程',
        state: '未选',
        chosen: false,
        credit: 2.0.toFixed(1)
      }, {
        id: '21120264',
        name: '软件工程',
        state: '未选',
        chosen: false,
        credit: 2.5.toFixed(1)
      }]
      this.searchResults = ret

      this.searchLoading = false
    },
    accessMyCourses () {
      this.myCourseLoading = true

      let ret = [{
        filtering: false,
        id: '21120264',
        teacher: '刘玉生',
        time: '周一第1,2节',
        place: '玉泉曹光彪二期-104(多)'
      }, {
        filtering: true,
        id: '21120264',
        teacher: '刘玉生',
        time: '周一第1,2节',
        place: '玉泉曹光彪二期-104(多)'
      }]
      this.updateCourseTable()
      this.myCourses = ret

      this.myCourseLoading = false
    },
    updateCourseTable () {
      // let myCourses = this.myCourses
      let ret = []
      for (let i = 0; i < 13; i++) {
        ret.push({time: i + 1, mon: '', tue: '', wed: '', thu: '', fri: '', sat: '', sun: ''})
      }
      ret[10].mon = '软件工程'
      ret[11].mon = '软件工程'
      ret[12].mon = '软件工程'
      this.courseTable = ret
    },
    accessCourseDetail (row) {
      this.detailLoading = true

      let ret = [{
        teacher: '刘玉生',
        courseTime: '周一第1,2节',
        coursePlace: '玉泉曹光彪二期-104(多)',
        examTime: '2020年6月30日(14:00-16:00)',
        remainNum: -1,
        totalNum: 79,
        chosenNum: 0,
        chosen: false
      }, {
        teacher: '刘玉生',
        courseTime: '周一第1,2节',
        coursePlace: '玉泉曹光彪二期-104(多)',
        examTime: '2020年6月30日(14:00-16:00)',
        remainNum: -1,
        totalNum: 79,
        chosenNum: 0,
        chosen: true
      }, {
        teacher: '刘玉生',
        courseTime: '周一第1,2节',
        coursePlace: '玉泉曹光彪二期-104(多)',
        examTime: '2020年6月30日(14:00-16:00)',
        remainNum: -1,
        totalNum: 79,
        chosenNum: 0,
        chosen: false
      }, {
        teacher: '刘玉生',
        courseTime: '周一第1,2节',
        coursePlace: '玉泉曹光彪二期-104(多)',
        examTime: '2020年6月30日(14:00-16:00)',
        remainNum: -1,
        totalNum: 79,
        chosenNum: 0,
        chosen: false
      }]
      this.chosenCourseDetails = ret

      this.detailLoading = false
    }
  },
  mounted () {
    this.accessSearchResults()
    this.accessMyCourses()
  }
}
</script>

<style>
.demo-table-expand {
  font-size: 0;
}
.demo-table-expand label {
  width: 90px;
  color: #99a9bf;
}
.demo-table-expand .el-form-item {
  margin-right: 0;
  margin-bottom: 0;
  width: 50%;
}
.my-course-table {
  position: absolute;
  right: 0%;
  height: 200px;
}
.el-table .chosen-row {
  background:oldlace;
}
.cell-blue {
  background: powderblue;
}
.el-popover{
  width: 1300px;
  height: 500px;
  overflow: auto;
}
.func-row {
  margin-top: 20px;
  margin-bottom: 20px;
}
.search-option {
  width: 150px;
}
.search-input {
  width: 450px;
}
.el-table__body tr:hover > td {
  background: initial !important;
}
.el-table__body tr:current-row > td {
  background: initial !important;
}
.course-table-row {
  pointer-events: none;
}
</style>
