import request from '@/utils/request'

// 查询许可列表
export function listPermission(query) {
  return request({
    url: '/vote/permission/list',
    method: 'get',
    params: query
  })
}

// 查询许可详细
export function getPermission(id) {
  return request({
    url: '/vote/permission/' + id,
    method: 'get'
  })
}

// 新增许可
export function addPermission(data) {
  return request({
    url: '/vote/permission',
    method: 'post',
    data: data
  })
}

// 修改许可
export function updatePermission(data) {
  return request({
    url: '/vote/permission',
    method: 'put',
    data: data
  })
}

// 删除许可
export function delPermission(id) {
  return request({
    url: '/vote/permission/' + id,
    method: 'delete'
  })
}

// 导出许可
export function exportPermission(query) {
  return request({
    url: '/vote/permission/export',
    method: 'get',
    params: query
  })
}
