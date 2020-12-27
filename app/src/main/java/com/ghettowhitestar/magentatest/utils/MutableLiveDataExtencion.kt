package com.ghettowhitestar.magentatest.utils

import androidx.lifecycle.MutableLiveData

/**
 * Добавляет элемент в список для наблюдения за ним в MutableLiveData
 * @param items Список
 */
fun <T> MutableLiveData<MutableList<T>>.add(items: List<T>?) {
    items?.let {
        val updatedItems = mutableListOf<T>().apply {
            addAll(this@add.value ?: mutableListOf())
            addAll(items)
        }
        this.value = updatedItems
    }
}

/**
 * Удаляет элемент в список для наблюдения за ним в MutableLiveData
 * @param items Список
 */
fun <T> MutableLiveData<MutableList<T>>.delete(items: List<T>?) {
    items?.let {
        val updatedItems = mutableListOf<T>().apply {
            addAll(this@delete.value ?: mutableListOf())
            removeAll(items)
        }
        this.value = updatedItems
    }
}
