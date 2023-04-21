import request from '@/utils/request'

export function find(params) {
  return request({
    url: '/role',
    method: 'get',
    params
  })
}

export function findPage(params) {
  return request({
    url: '/role/page',
    method: 'get',
    params
  })
}

export function findInactiveRolePage(params) {
  return request({
    url: '/role/inactive/page',
    method: 'get',
    params
  })
}

export function create(data) {
  return request({
    url: '/role',
    method: 'post',
    data
  })
}

export function modify(data) {
  return request({
    url: '/role',
    method: 'put',
    data
  })
}

export function remove(params) {
  return request({
    url: '/role',
    method: 'delete',
    params
  })
}
