import request from "@/utils/request";

export function getAllUser(params){
    return request({
        url:'/user/selectPage',
        method:'get',
        params
    })
}

export function deleteUserById(id){
    return request({
        url:'/user/delete/'+id,
        method:'delete'
    })
}

export function deleteBatch(data){
    return request({
        url:'/user/delete/batch',
        method:'delete',
        data
    })
}

export function addUser(data){
    return request({
        url:'/user/add',
        method:'post',
        data
    })
}


export function updateUser(data){
    return request({
        url:'/user/update',
        method:'put',
        data
    })
}

export function getUserById(id){
    return request({
        url:'/pc/user/selectById/'+id,
        method:'get'
    })
}