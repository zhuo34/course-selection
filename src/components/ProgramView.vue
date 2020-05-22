<template>
    <div>
      <el-table :data="tableData" border="true" style="width: 80%" height="250">
        <el-table-column prop="date" label="日期" width="180"></el-table-column>
        <el-table-column prop="name" label="姓名"  width="180"></el-table-column>
        <el-table-column prop="address" label="地址"></el-table-column>
        <el-table-column prop="state" label="">
          <template slot-scope="scope">
            <el-button type="info" @click="delTableRow(scope.$index)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <p>{{message}}</p>
    </div>
</template>

<script>
export default {
  name: "ProgramView",
  data() {
    return {
      tableData: [{
        date: '2016-05-02',
        name: '王小虎',
        address: '上海市普陀区金沙江路 1518 弄'
      }, {
        date: '2016-05-04',
        name: '王小虎',
        address: '上海市普陀区金沙江路 1517 弄'
      }, {
        date: '2016-05-01',
        name: '王小虎',
        address: '上海市普陀区金沙江路 1519 弄'
      }, {
        date: '2016-05-03',
        name: '王小虎',
        address: '上海市普陀区金沙江路 1516 弄'
      }],
      message: 'aaa'
    }
  },
  methods: {
    delTableRow: function(index){
      this.tableData.splice(index, 1)
      this.message = 'delete finished.'
    }
  }
}
</script>

<style scoped>

</style>
