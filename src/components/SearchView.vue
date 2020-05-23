<template>
  <div>
    <el-row type="flex" justify="space-between">
      <el-button v-popover:tablepop type="primary" round>显示课表</el-button>

      <el-popover ref="tablepop" placement="bottom-start" trigger="click">
        <el-table :data="courseTable" style="width: 100%" border :span-method="combineCell">
          <el-table-column prop="time" label="时间"/>
          <el-table-column prop="mon" label="星期一"/>
          <el-table-column prop="tue" label="星期二"/>
          <el-table-column prop="wed" label="星期三"/>
          <el-table-column prop="thu" label="星期四"/>
          <el-table-column prop="fri" label="星期五"/>
          <el-table-column prop="sat" label="星期六"/>
          <el-table-column prop="sun" label="星期日"/>
        </el-table>
        <el-table :data="myCourses" style="width: 100%" border>
          <el-table-column prop="state" label="状态"/>
          <el-table-column prop="id" label="课程号"/>
          <el-table-column prop="teacher" label="教师"/>
          <el-table-column prop="time" label="上课时间"/>
          <el-table-column prop="place" label="上课地点"/>
          <el-table-column prop="op" label="操作">
            <template slot-scope="scope">
              <el-button type="danger"
                @click="dropCourse(scope.$index)">退选</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-popover>

      <el-select v-model="searchTitle1" placeholder="请选择">
        <el-option v-for="item in searchTitleOptions" :key="item"
                   :label="item" :value="item"></el-option>
      </el-select>
      <el-input class="inline-input" v-model="searchInfo1"
                prefix-icon="el-icon-search" clearable placeholder="请输入内容"/>

      <el-select v-model="searchTitle2" placeholder="请选择">
        <el-option v-for="item in searchTitleOptions" :key="item"
                   :label="item" :value="item"></el-option>
      </el-select>
      <el-input class="inline-input" v-model="searchInfo2"
                prefix-icon="el-icon-search" clearable placeholder="请输入内容"/>

      <el-button type="primary">查询</el-button>

    </el-row>

    <el-table :data="searchResults" style="width: 100%" :row-key="getRowKeys"
              :expand-row-keys="expands" @expand-change="expandSelect">

      <el-table-column type="expand">
        <el-table :data="chosenCourseDetails" style="width: 100%"
                  :row-class-name="whichCourseSelected">
          <el-table-column prop="teacher" sortable label="教师"/>
          <el-table-column prop="courseTime" sortable label="上课时间"/>
          <el-table-column prop="coursePlace" sortable label="上课地点"/>
          <el-table-column prop="examTime" sortable label="考试时间"/>
          <el-table-column prop="remainNum" sortable label="余量"/>
          <el-table-column prop="totalNum" sortable label="容量"/>
          <el-table-column prop="chosenNum" sortable label="待定人数"/>
          <el-table-column prop="op" label="操作">
            <template slot-scope="scope">
              <el-button
                :type="chosenCourseDetails[scope.$index].chosen ? 'danger' : 'primary'"
                @click="modifyChosen(scope.$index)">
                {{chosenCourseDetails[scope.$index].chosen ? '退选' : '选课'}}</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-table-column>

      <el-table-column label="课程ID" prop="id"/>
      <el-table-column label="课程名称" prop="name"/>
      <el-table-column label="选课状态" prop="state"/>
    </el-table>
  </div>
</template>

<script>
export default {
  name: 'SearchView',
  data () {
    return {
      searchTitleOptions: [
        '课程名称', '课程代码', '教师姓名', '课程类别',
        '上课时间', '上课地点', '学期'
      ],
      searchTitle1: '课程名称',
      searchTitle2: '课程名称',
      searchInfo1: '',
      searchInfo2: '',
      expands: [],
      searchResults: [{
        id: '21120261',
        name: '软件工程',
        state: '未选'
      }, {
        id: '21120262',
        name: '软件工程',
        state: '未选'
      }, {
        id: '21120263',
        name: '软件工程',
        state: '未选'
      }, {
        id: '21120264',
        name: '软件工程',
        state: '未选'
      }],
      chosenCourseDetails: [{
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
      }],
      courseTable: [
        {
          time: '1',
          mon: '',
          tue: '',
          wed: '',
          thu: '',
          fri: '',
          sat: '',
          sun: ''
        }, {
          time: '2',
          mon: '',
          tue: '',
          wed: '',
          thu: '',
          fri: '',
          sat: '',
          sun: ''
        }, {
          time: '3',
          mon: '',
          tue: '',
          wed: '',
          thu: '',
          fri: '',
          sat: '',
          sun: ''
        }, {
          time: '4',
          mon: '',
          tue: '',
          wed: '',
          thu: '',
          fri: '',
          sat: '',
          sun: ''
        }, {
          time: '5',
          mon: '',
          tue: '',
          wed: '',
          thu: '',
          fri: '',
          sat: '',
          sun: ''
        }, {
          time: '6',
          mon: '',
          tue: '',
          wed: '',
          thu: '',
          fri: '',
          sat: '',
          sun: ''
        }, {
          time: '7',
          mon: '',
          tue: '',
          wed: '',
          thu: '',
          fri: '',
          sat: '',
          sun: ''
        }, {
          time: '8',
          mon: '',
          tue: '',
          wed: '',
          thu: '',
          fri: '',
          sat: '',
          sun: ''
        }, {
          time: '9',
          mon: '',
          tue: '',
          wed: '',
          thu: '',
          fri: '',
          sat: '',
          sun: ''
        }, {
          time: '10',
          mon: '',
          tue: '',
          wed: '',
          thu: '',
          fri: '',
          sat: '',
          sun: ''
        }, {
          time: '11',
          mon: '软件工程',
          tue: '',
          wed: '',
          thu: '',
          fri: '',
          sat: '',
          sun: ''
        }, {
          time: '12',
          mon: '软件工程',
          tue: '',
          wed: '',
          thu: '',
          fri: '',
          sat: '',
          sun: ''
        }, {
          time: '13',
          mon: '软件工程',
          tue: '',
          wed: '',
          thu: '',
          fri: '',
          sat: '',
          sun: ''
        }
      ],
      myCourses: [{
        state: '已选上',
        id: '21120264',
        teacher: '刘玉生',
        time: '周一第1,2节',
        place: '玉泉曹光彪二期-104(多)'
      }, {
        state: '筛选中',
        id: '21120264',
        teacher: '刘玉生',
        time: '周一第1,2节',
        place: '玉泉曹光彪二期-104(多)'
      }]
    }
  },
  methods: {
    getRowKeys (row) {
      return row.id
    },
    whichCourseSelected ({rowIndex}) {
      if (this.chosenCourseDetails[rowIndex].chosen) {
        return 'chosen-row'
      } else {
        return ''
      }
    },
    expandSelect (row, expandedRows) {
      this.expands = []
      if (expandedRows.length > 0 && row) {
        this.expands.push(row.id)
      }
    },
    modifyChosen (index) {
      this.chosenCourseDetails[index].chosen = !this.chosenCourseDetails[index].chosen
    },
    dropCourse (index) {

    },
    combineCell ({row, column, rowIndex, columnIndex}) {
      if (rowIndex > 0 && row[column.property] === this.courseTable[rowIndex - 1][column.property]) {
        return {
          rowspan: 0,
          colspan: 0
        }
      } else {
        let rows = 1
        for (let i = rowIndex; i < this.courseTable[i + 1][column.property];) {
          rows = rows + 1
        }
        return {
          rowspan: rows,
          colspan: 1
        }
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
.el-popover{
  width: 1300px;
  height: 500px;
  overflow: auto;
}
</style>
