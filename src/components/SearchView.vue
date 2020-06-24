<template>
  <div v-loading="searchLoading || myCourseLoading">
    <!--显示课表的按钮以及弹出的课表-->
    <el-row type="flex" justify="space-around" class="func-row">
      <el-button v-if="!isProgramView" v-popover:tablepop
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
          <el-table-column prop="isOn" label="状态" align="center">
            <template slot-scope="scope">
              <span v-if="scope.row.isOn"
                    style="color: blue">已选上</span>
              <span v-else
                    style="color: red">筛选中</span>
            </template>
          </el-table-column>
          <el-table-column prop="cid" label="课程号" align="center"/>
          <el-table-column prop="tname" label="教师" align="center"/>
          <el-table-column prop="printTime" label="上课时间" align="center"/>
          <el-table-column prop="place" label="上课地点" align="center"/>
          <el-table-column prop="op" label="操作" align="center">
            <template slot-scope="scope">
              <el-button type="danger"
                @click="dropCourse(scope.$index)">退选</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-popover>

      <!--Search info 1-->
      <el-select class="search-option" v-model="searchTitle1"
                 placeholder="搜索条件" @change="searchTitleChange(1)">
        <el-option v-for="item in searchTitleOptions" :key="item.label"
                   v-if="searchTitle2 !== item.value"
                   :label="item.label" :value="item.value"></el-option>
      </el-select>
      <el-input v-if="searchTitle1 !== 'courseTime'"
                class="search-input" v-model="searchInfo1"
                prefix-icon="el-icon-search" clearable placeholder="请输入内容"/>
      <el-select v-else class="search-input" v-model="searchInfo1" placeholder="请选择上课时间">
        <el-option v-for="item in courseTimeOptions" :key="item"
                   :label="item" :value="translateTime(item)"></el-option>
      </el-select>

      <!--Search info 2-->
      <el-select class="search-option" v-model="searchTitle2"
                 placeholder="搜索条件" @change="searchTitleChange(2)">
        <el-option v-for="item in searchTitleOptions" :key="item.label"
                   v-if="searchTitle1 !== item.value"
                   :label="item.label" :value="item.value"></el-option>
      </el-select>
      <el-input v-if="searchTitle2 !== 'courseTime'"
                class="search-input" v-model="searchInfo2"
                prefix-icon="el-icon-search" clearable placeholder="请输入内容"/>
      <el-select v-else class="search-input" v-model="searchInfo2" placeholder="请选择上课时间">
        <el-option v-for="item in courseTimeOptions" :key="item"
                   :label="item" :value="translateTime(item)"></el-option>
      </el-select>

      <el-button type="primary" @click="accessSearchResults">查询</el-button>

    </el-row>

    <!--下方的课程列表-->
    <el-table :data="searchResults" style="width: 100%" :row-key="getRowKeys"
              :expand-row-keys="expands" @expand-change="expandSelect"
              :row-class-name="courseSelected" @row-click="rowClick" :key="key_update">
      <!--课程展开信息-->
      <el-table-column type="expand" align="center" v-if="!isProgramView">
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

      <!--课程标题信息-->
      <el-table-column label="课程ID" prop="id" align="center"/>
      <el-table-column label="课程名称" prop="name" align="center"/>
      <el-table-column label="学分" prop="credit" align="center"/>
      <el-table-column v-if="!isProgramView"
                       label="选课状态" prop="chosen" align="center">
        <template slot-scope="scope">
          <b v-if="scope.row.chosen">已选</b>
          <span v-else>未选</span>
        </template>
      </el-table-column>
      <el-table-column v-else prop="op" label="操作" align="center">
        <template slot-scope="scope">
          <el-button
            :disabled="true"
            type="info"
            @click="programAddC(scope.$index)">添加</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
function SearchStruct () {
  this.courseName = ''
  this.courseID = ''
  this.teacherName = ''
  this.courseTime = ''
}

export default {
  props: {
    isProgramView: {
      default: false
    }
  },
  name: 'SearchView',
  data () {
    return {
      stuId: '3170756898',
      stuDepartment: 'CS',
      searchLoading: false,
      detailLoading: false,
      myCourseLoading: false,
      searchTitle1: '',
      searchTitle2: '',
      searchInfo1: '',
      searchInfo2: '',
      expands: [],
      searchResults: [],
      chosenCourseDetails: [],
      courseTable: [],
      myCourses: [],
      key_update: 0,
      searchTitleOptions: [
        { label: '课程名称', value: 'courseName' },
        { label: '课程代码', value: 'courseID' },
        { label: '教师姓名', value: 'teacherName' },
        { label: '上课时间', value: 'courseTime' }
      ],
      courseTimeOptions: []
    }
  },
  methods: {
    getRowKeys (row) {
      return row.id
    },
    courseSelected ({rowIndex}) {
      if (this.isProgramView) {
        return ''
      } else if (this.searchResults[rowIndex].chosen) {
        return 'chosen-row'
      } else {
        return ''
      }
    },
    whichCourseSelected ({rowIndex}) {
      if (this.isProgramView) {
        return ''
      } else if (this.chosenCourseDetails[rowIndex].chosen) {
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
      if (this.isProgramView) return
      this.expands = []
      if (expandedRows.length > 0 && row) {
        this.expands.push(row.id)
        this.accessCourseDetail(row)
      }
    },
    rowClick (row, event, column) {
      if (this.isProgramView) return
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
    searchTitleChange (id) {
      if (id === 1) this.searchInfo1 = ''
      else if (id === 2) this.searchInfo2 = ''
    },
    translateTime (timeStr) {
      let time = []
      time = timeStr.split(' ')
      let dayDic = {'星期一': '0|', '星期二': '1|', '星期三': '2|', '星期四': '3|', '星期五': '4|', '星期六': '5|', '星期日': '6|'}
      let timeDic = {'第1,2节': '0|2', '第3,4节': '1|2', '第3,4,5节': '1|3', '第6,7,8节': '2|3', '第7,8节': '2|2', '第9,10节': '3|2', '第11,12节': '4|2', '第11,12,13节': '4|3'}
      return dayDic[time[0]] + timeDic[time[1]]
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
      let info = new SearchStruct()
      if (this.searchTitle1) {
        info[this.searchTitle1] = this.searchInfo1
      }
      if (this.searchTitle2) {
        info[this.searchTitle2] = this.searchInfo2
      }
      // console.log(info)

      this.searchLoading = true
      if (this.isProgramView) {
        // 从培养方案里面访问搜索引擎
        this.$axios.get('/search-courses', {
          params: {
            stuId: this.stuId,
            courseId: info.courseID,
            courseName: info.courseName,
            tName: info.teacherName,
            cTime: info.courseTime
          }})
          .then(successResponse => {
            this.searchResults = []
            let cnt = 0
            for (let item of successResponse.data) {
              this.searchResults.push({
                id: item.courseId,
                name: item.courseName,
                chosen: item.isSelected > 0,
                credit: item.credits.toFixed(1),
                department: cnt % 2 === 0? 'hhh': this.stuDepartment,
                required: cnt % 2 === 0
              })
              cnt++
            }
            this.searchLoading = false
          })
          .catch(failResponse => {
            console.log('search courses in program: fail')
            this.searchLoading = false
          })
          .finally(() => this)
      } else {
        // 直接访问搜索引擎
        this.$axios.get('/search-courses', {
          params: {
            stuId: this.stuId,
            courseId: info.courseID,
            courseName: info.courseName,
            tName: info.teacherName,
            cTime: info.courseTime
          }})
          .then(successResponse => {
            this.searchResults = []
            for (let item of successResponse.data) {
              this.searchResults.push({
                id: item.courseId,
                name: item.courseName,
                chosen: item.isSelected > 0,
                credit: item.credits.toFixed(1)
              })
            }
            this.searchLoading = false
          })
          .catch(failResponse => {
            console.log('search courses: fail')
            this.searchLoading = false
          })
          .finally(() => this)
      }
    },
    accessMyCourses () {
      this.myCourseLoading = true
      this.myCourses = []

      this.$axios.get('/get-courses', {
        params: {stuId: this.stuId}})
        .then(successResponse => {
          this.myCourses = successResponse.data
          this.updateCourseTable(successResponse.data)
          this.myCourseLoading = false
        })
        .catch(failResponse => {
          console.log('fail')
          this.myCourseLoading = false
        })
        .finally(() => this)
      // let ret = [{
      //   filtering: false,
      //   id: '21120264',
      //   teacher: '刘玉生',
      //   time: '周一第1,2节',
      //   place: '玉泉曹光彪二期-104(多)'
      // }, {
      //   filtering: true,
      //   id: '21120264',
      //   teacher: '刘玉生',
      //   time: '周一第1,2节',
      //   place: '玉泉曹光彪二期-104(多)'
      // }]
      // this.updateCourseTable()
      // this.myCourses = ret
      // this.myCourseLoading = false
    },
    updateCourseTable (chartData) {
      let ret = []
      for (let i = 0; i < 13; i++) {
        ret.push({time: i + 1, mon: '', tue: '', wed: '', thu: '', fri: '', sat: '', sun: ''})
      }
      let time = []
      let name = ''
      for (let i = 0; i < chartData.length; i++) {
        // go through courses
        time = chartData[i].time
        name = chartData[i].cname
        for (let j = 0; j < time.length; j++) {
          // go through times
          // weekday, begin, duration
          let idx = 0
          switch (time[j].begin) {
            case 0:
              idx = 0
              break
            case 1:
              idx = 2
              break
            case 2:
              idx = 5
              break
            case 3:
              idx = 8
              break
            case 4:
              idx = 10
              break
          }
          for (let k = 0; k < time[j].duration; k++) {
            this.stupidAssign(ret[idx + k], time[j].weekday, name)
          }
        }
      }
      // ret[10].mon = '软件工程'
      // ret[11].mon = '软件工程'
      // ret[12].mon = '软件工程'
      this.courseTable = ret
    },
    stupidAssign (chartRow, weekday, name) {
      switch (weekday) {
        case 0:
          chartRow.mon = name
          break
        case 1:
          chartRow.tue = name
          break
        case 2:
          chartRow.wed = name
          break
        case 3:
          chartRow.thu = name
          break
        case 4:
          chartRow.fri = name
          break
        case 5:
          chartRow.sat = name
          break
        case 6:
          chartRow.sun = name
          break
      }
    },
    accessCourseDetail (row) {
      this.detailLoading = true
      this.chosenCourseDetails = []
      this.key_update = Math.random()
      // let ret = []
      // console.log({'courseId': row.id, 'stuId': this.stuId})
      this.$axios.post('/get-classes', {courseId: row.id, stuId: this.stuId})
        .then(successResponse => {
          // console.log('successResponse.data')
          // console.log(successResponse.data)
          this.chosenCourseDetails = successResponse.data
          this.detailLoading = false
          this.key_update = Math.random()
          // this.chosenCourseDetails = successResponse.data
          // this.detailLoading = false
        })
        .catch(failResponse => {
          console.log('fail')
        })
        .finally(() => this)
      // this.chosenCourseDetails = ret
      // console.log('this.chosenCourseDetails')
      // console.log(this.chosenCourseDetails)
    },
    programAddC (index) {
      this.$emit('addCourse', {id: this.searchResults[index].id, name: this.searchResults[index].name, credit: parseFloat(this.searchResults[index].credit)})
    }
  },
  mounted () {
    this.accessSearchResults()
    this.accessMyCourses()

    // init courseTimeOptions
    let days = ['一', '二', '三', '四', '五', '六', '日']
    let times = ['1,2', '3,4', '3,4,5', '6,7,8', '7,8', '9,10', '11,12', '11,12,13']
    for (let day of days) {
      for (let time of times) {
        this.courseTimeOptions.push('星期' + day + ' 第' + time + '节')
      }
    }
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
