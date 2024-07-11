<template>
  <div class="menu-perms-container" v-loading="loading">
    <el-row class="mb10">
      <el-col>
        <el-button plain type="success" icon="el-icon-edit" size="mini" @click="handleSave">{{ $t("Common.Save") }}</el-button>
        <el-button plain type="primary" icon="el-icon-check" size="mini" @click="handleSelectAll">{{ this.selectAll ? $t('Common.CheckInverse') : $t('Common.CheckAll') }}</el-button>
        <el-button plain type="primary" icon="el-icon-bottom-right" size="mini" @click="handleExpandAll">{{ this.expandAll ? $t('Common.Collapse') : $t('Common.Expand') }}</el-button>
      </el-col>
    </el-row>
    <el-row>
      <el-col>
        <el-card shadow="never">
          <el-tree
            class="tree-border"
            :data="menuTreeData"
            default-expand-all
            show-checkbox
            ref="menu"
            node-key="id"
            empty-text="加载中，请稍候"
            :props="defaultProps">
          </el-tree>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>
<script>
import { updateRoleMenu } from "@/api/system/role";
import {roleMenuTreeselect } from "@/api/system/menu";

export default {
  name: "MenuPermission",
  props: {
    owner: {
      type: Number,
      require: false,
      default: ""
    }
  },
  watch: {
    owner: {
      handler(newV, oldV) {
        if (newV && newV != '') {
          this.form.owner = newV;
          this.loadData();
        }
      }
    },
  },
  mounted() {
    this.loadData();
  },
  data() {
    return {
      loading: true,
      openPermissionDialog: false,
      selectAll: false,
      expandAll: true,
      menuTreeData: [],
      perms: [],
      form: {},
      defaultProps: {
        children: 'children',
        label: 'label'
      }
    };
  },
  methods: {
    loadData() {
      this.loading = true;
      const roleId = this.owner;
      const roleMenu = this.getRoleMenuTreeselect(roleId);
      this.$nextTick(() => {
        roleMenu.then(res => {
          let checkedKeys = res.checkedKeys
          checkedKeys.forEach((v) => {
            this.$nextTick(()=>{
              this.$refs.menu.setChecked(v, true ,false);
              this.loading = false;
            })
          })
        });

      });
    },
    /** 根据角色ID查询菜单树结构 */
    getRoleMenuTreeselect(roleId) {
      return roleMenuTreeselect(roleId).then(response => {
        this.menuTreeData = response.menus;
        return response;
      });
    },
    handleSelectAll() {
      this.$refs.menu.setCheckedNodes(!this.selectAll ? this.menuTreeData : []);
      this.selectAll = !this.selectAll;
    },
    handleExpandAll() {
      let treeList = this.menuTreeData;
      for (let i = 0; i < treeList.length; i++) {
        this.$refs.menu.store.nodesMap[treeList[i].id].expanded = !this.expandAll;
      }
      this.expandAll = !this.expandAll;
    },
    // 所有菜单节点数据
    getMenuAllCheckedKeys() {
      // 目前被选中的菜单节点
      let checkedKeys = this.$refs.menu.getCheckedKeys();
      // 半选中的菜单节点
      let halfCheckedKeys = this.$refs.menu.getHalfCheckedKeys();
      checkedKeys.unshift.apply(checkedKeys, halfCheckedKeys);
      return checkedKeys;
    },
    handleSave() {
      let menuIds = this.getMenuAllCheckedKeys();
      const data = {
        roleId: this.owner,
        menuIds: menuIds
      };
      updateRoleMenu(data).then(response => {
        this.$modal.notifySuccess(this.$t('Common.SaveSuccess'));
        this.openPermissionDialog = false;
      });
    }
  }
};
</script>
