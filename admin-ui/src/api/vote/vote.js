import request from '@/utils/request'

// 查询投票列表
export function listVote(query) {
  return request({
    url: '/vote/vote/list',
    method: 'get',
    params: query
  })
}

// 查询投票详细
export function getVote(id) {
  return request({
    url: '/vote/vote/' + id,
    method: 'get'
  })
}

// 新增投票
export function addVote(data) {
  return request({
    url: '/vote/vote',
    method: 'post',
    data: data
  })
}

// 修改投票
export function updateVote(data) {
  return request({
    url: '/vote/vote',
    method: 'put',
    data: data
  })
}

// 删除投票
export function delVote(id) {
  return request({
    url: '/vote/vote/' + id,
    method: 'delete'
  })
}

/**
 * 获取总投票数和参与人数
 * @returns {AxiosPromise}
 */
export function statisticsVote() {
  return request({
    url: '/vote/vote/statisticsVote',
    method: 'get'
  })
}

/**
 * 获取前5用户总投票数
 * @returns {AxiosPromise}
 */
export function getDataList() {
  return request({
    url: '/vote/vote/getDataList',
    method: 'get'
  })
}

// 查询所有投票列表
export function voteList() {
  return request({
    url: '/vote/vote/voteList',
    method: 'get'
  })
}

/**
 * 根据投票时间范围内查询投票列表
 * @param createTime
 * @returns {AxiosPromise}
 */
export function selectVoteListByTime(data) {
  return request({
    url: '/vote/vote/selectVoteListByTime',
    method: 'get',
    params: data
  })
}

export function getUserByVoteRange(id) {
  return request({
    url: '/vote/vote/getUserByVoteRange/' + id,
    method: 'get'
  })
}
