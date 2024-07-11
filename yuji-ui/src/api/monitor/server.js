import request from '@/utils/request'

// 获取服务信息
export function getServer() {
  return request({
    url: '/monitor/server',
    method: 'get'
  })
}

//首页获取系统信息
export function getDashboardServerInfo() {
  return request({
    url: '/monitor/server/dashboard',
    method: 'get'
  })
}
