<template>
  <div class="app-container">
    <el-tabs v-model="activeName" @tab-click="handleTabClick">
      <el-tab-pane :label="$t('System.Permission.MenuPriv')" name="Menu">
        <permission-menu
          v-if="this.activeName=='Menu'"
          :owner="permsOwner">
        </permission-menu>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>
<script>
import MenuPermission from './menuPerms';

export default {
  name: "PermisisonTab",
  components: {
    'permission-menu': MenuPermission,
  },
  props: {
    owner: {
      type: Number,
      default: ""
    }
  },
  watch: {
    owner(newV, oldV) {
      this.permsOwner = newV;
    }
  },
  data () {
    return {
      activeName: 'Menu',
      permsOwner: this.owner
    };
  },
  methods: {
    handleTabClick (tab, event) {
    }
  }
};
</script>
