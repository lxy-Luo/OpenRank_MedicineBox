import request from "@/utils/request";

export function getAllDevice(params){
    return request({
        url:'/device/selectPage',
        method:'get',
        params
    })
}

export function deleteDeviceById(id){
    return request({
        url:'/device/delete/'+id,
        method:'delete'
    })
}

export function deleteBatch(data){
    return request({
        url:'/device/delete/batch',
        method:'delete',
        data
    })
}

export function addDevice(data){
    return request({
        url:'/device/add',
        method:'post',
        data
    })
}


export function updateDevice(data){
    return request({
        url:'/device/update',
        method:'put',
        data
    })
}

export function getDeviceById(id){
    return request({
        url:'/pc/device/selectById/'+id,
        method:'get'
    })
}