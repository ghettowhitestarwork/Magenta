package com.ghettowhitestar.magentatest.paginator

class NetworkResponceState <out T>(
    val status: Status,
    val data: T?,
    val message:String?
){
    companion object{

        fun <T> success(data:T?): NetworkResponceState<T>{
            return NetworkResponceState(Status.SUCCESS, data, null)
        }

        fun <T> error(msg:String, data:T?): NetworkResponceState<T>{
            return NetworkResponceState(Status.ERROR, data, msg)
        }

        fun <T> loading(data:T?): NetworkResponceState<T>{
            return NetworkResponceState(Status.LOADING, data, null)
        }

    }
}