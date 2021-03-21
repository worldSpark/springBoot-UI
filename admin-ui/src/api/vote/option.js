import request from '@/utils/request'

// 查询候选项列表
export function listOption(query) {
  return request({
    url: '/vote/option/list',
    method: 'get',
    params: query
  })
}

// 查询候选项详细
export function getOption(id) {
  return request({
    url: '/vote/option/' + id,
    method: 'get'
  })
}

// 新增候选项
export function addOption(data) {
  return request({
    url: '/vote/option',
    method: 'post',
    data: data
  })
}

// 修改候选项
export function updateOption(data) {
  return request({
    url: '/vote/option',
    method: 'put',
    data: data
  })
}

// 删除候选项
export function delOption(id) {
  return request({
    url: '/vote/option/' + id,
    method: 'delete'
  })
}

// 导出候选项
export function exportOption(query) {
  return request({
    url: '/vote/option/export',
    method: 'get',
    params: query
  })
}

//获取投票列表集合
export function treeselect(query) {
  return request({
    url: '/vote/option/treeselect',
    method: 'get',
    params: query
  })
}
//根据用户id查询用户详情
export function getUserInfoByVote(userId) {
  return request({
    url: '/vote/option/getUserInfoByVote/'+userId,
    method: 'get'
  })
}

//根据投票id查询用户投票集合
export function getUserListByVote(voteId,optionId) {
  return request({
    url: '/vote/option/getUserListByVote/'+voteId+'/'+optionId,
    method: 'get'
  })
}

/**
 * 根据投票id获取候选项
 * @param query
 * @returns {AxiosPromise}
 */
export function voteOptionSelects(query) {
  return request({
    url: '/vote/option/voteOptionSelects/'+query,
    method: 'get',
  })
}

export function addUserVoteByList(data) {
  return request({
    url: '/vote/userVote/addUserVoteByList',
    method: 'post',
    data:data
  })
}

