<template>
  <div>
    <el-menu :default-active="this.$route.path" mode="horizontal" router>
      <el-menu-item index="/search">课程检索</el-menu-item>
      <el-menu-item index="/program">培养方案</el-menu-item>
    </el-menu>

    <div>
      <transition name="fade" mode="out-in">
        <router-view/>
      </transition>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Home'
}
</script>

<style scoped>
.fade-enter-active, .fade-leave-active {
  transition: all .5s ease;
}
.fade-enter, .fade-leave-to{
  opacity: 0;
}
</style>
