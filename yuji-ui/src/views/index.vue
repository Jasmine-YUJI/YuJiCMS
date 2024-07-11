<template>
  <div class="dashboard-editor-container">
    <el-row :gutter="10">
      <el-col :span="24">
        <user-info></user-info>
      </el-col>
    </el-row>
    <el-row :gutter="10">
      <el-col :span="24">
        <shortcut></shortcut>
      </el-col>
    </el-row>
    <el-row :gutter="10">
      <el-col :span="24">
        <server-info></server-info>
      </el-col>
    </el-row>

  </div>
</template>

<script>
import SysUserInfo from '@/views/system/dashboard/userInfo'
import SysShortcut from '@/views/system/dashboard/shortcut'
import ServerInfo from '@/views/system/dashboard/serverInfo'


export default {
  name: 'Index',
  components: {
    'user-info': SysUserInfo,
    'shortcut': SysShortcut,
    'server-info': ServerInfo,
  },
  data() {
  },
  methods: {
  }
}
</script>

<style lang="scss" scoped>
.dashboard-editor-container {
  padding: 32px;
  background-color: rgb(240, 242, 245);
  position: relative;

  .chart-wrapper {
    background: #fff;
    padding: 16px 16px 0;
    margin-bottom: 32px;
  }
}

@media (max-width:1024px) {
  .chart-wrapper {
    padding: 8px;
  }
}
</style>
