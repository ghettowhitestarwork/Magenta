package com.ghettowhitestar.magentatest

import com.ghettowhitestar.magentatest.api.PicsumApi
import org.junit.Assert
import org.junit.Test

class ConstantUnitTest {
    @Test
    fun testConstants() {
        Assert.assertTrue(PicsumApi.BASE_URL == "https://picsum.photos/")
    }
}