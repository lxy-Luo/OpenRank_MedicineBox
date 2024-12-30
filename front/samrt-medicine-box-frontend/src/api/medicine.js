import request from "@/utils/request";

export function getAllMedicine(params){
    return request({
        url:'/medicine/selectPage',
        method:'get',
        params
    })
}

export function deleteMedicineById(id){
    return request({
        url:'/medicine/delete/'+id,
        method:'delete'
    })
}

export function deleteBatch(data){
    return request({
        url:'/medicine/delete/batch',
        method:'delete',
        data
    })
}

export function addMedicine(data){
    return request({
        url:'/medicine/add',
        method:'post',
        data
    })
}


export function updateMedicine(data){
    return request({
        url:'/medicine/update',
        method:'put',
        data
    })
}

export function getMedicineById(id){
    return request({
        url:'/pc/medicine/selectById/'+id,
        method:'get'
    })
}