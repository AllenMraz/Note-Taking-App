package com.example.notetakingapp


import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.lifecycle.Lifecycle
import androidx.test.annotation.UiThreadTest
import androidx.test.runner.AndroidJUnit4
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


class UiTest {
    @get:Rule
    val composeTestRule = createComposeRule()


    @Test
    fun test_add_new_note() {
        composeTestRule.setContent {
            Surface(modifier = Modifier.Companion.fillMaxWidth()) {
                NoteTakingApp()
            }

        }
        composeTestRule.onNodeWithContentDescription("Add Note").performClick()

        composeTestRule.onNodeWithText("Note Title*").performTextInput("Test Title")
        composeTestRule.onNodeWithText("Note Content*").performTextInput("Test Content")


        composeTestRule.onNodeWithText("Save").performClick()



        composeTestRule.onNodeWithText("Test Title").assertIsDisplayed()
    }
}