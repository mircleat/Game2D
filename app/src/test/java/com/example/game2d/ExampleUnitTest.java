package com.example.game2d;

import org.junit.Rule;
import org.junit.Test;
import static org.junit.Assert.*;
//
import android.content.Context;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;


import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

//@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
public class ExampleUnitTest {
    @Mock
    ChalkActivity testObj;

     /*@Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }*/


    // -------------------------------- BEGIN --------------------------------
    // ChalkActivity.java
    @Test
    public void randomizeOrderTest() {
        int[] original = new int[] { 0, 1, 2, 3 };
        ChalkActivity testObj = new ChalkActivity();
        int[] created = testObj.randomizeOrders();
        for (int i = 0; i < 4; i++) {
            System.out.println(created[i]);
        }
        assertEquals(original, testObj.randomizeOrders());
    }


}