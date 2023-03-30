import request from "@/utils/request";

export function findPage(params) {
  return request({
    url: '/user-role/page',
    method: 'get',
    params
  })
}

export function create(data) {
  return request({
    url: '/user-role',
    method: 'post',
    data
  })
}

export function remove(params) {
  return request({
    url: '/user-role',
    method: 'delete',
    params
  })
}
