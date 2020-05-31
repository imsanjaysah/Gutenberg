package com.sanjay.gutenberg.ui.book_list

import android.content.Intent
import android.view.View
import android.widget.EditText
import androidx.annotation.DrawableRes
import androidx.test.core.app.ApplicationProvider
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import androidx.test.platform.app.InstrumentationRegistry
import com.sanjay.gutenberg.GutenbergApplication
import com.sanjay.gutenberg.R
import com.sanjay.gutenberg.data.repository.GutenbergRepository
import com.sanjay.gutenberg.injection.DaggerAppComponent
import com.sanjay.gutenberg.injection.module.AppModule
import com.sanjay.gutenberg.injection.module.TestRepositoryModule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.Description
import org.hamcrest.Matchers.not
import org.hamcrest.TypeSafeMatcher
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations


@ExperimentalCoroutinesApi
@MediumTest
@RunWith(AndroidJUnit4::class)
class BooksListActivityTest {

    @Mock
    private lateinit var repository: GutenbergRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        val instrumentation = InstrumentationRegistry.getInstrumentation()
        val app = instrumentation.targetContext.applicationContext as GutenbergApplication

        val testComponent = DaggerAppComponent.builder()
            .appModule(AppModule(app))
            .repositoryModule(TestRepositoryModule())
            .build()
        app.appComponent = testComponent
    }

    @Test
    fun booksList_DisplayedInUi() = runBlockingTest {
        val intent =
            Intent(ApplicationProvider.getApplicationContext(), BooksListActivity::class.java)
                .putExtra("extra_category", "Drama")

        launchActivity<BooksListActivity>(intent)
        onView(withText("BOOK1"))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        onView(withText("A1"))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun searchEditText_DisplayClearButton() = runBlockingTest {
        val intent =
            Intent(ApplicationProvider.getApplicationContext(), BooksListActivity::class.java)
                .putExtra("extra_category", "Drama")

        launchActivity<BooksListActivity>(intent)

        //Before entering text clear button should not be visible
        onView(withId(R.id.et_search_books)).check(matches(isDisplayed())).check(
            matches(
                not(withRightDrawable(R.drawable.ic_clear))
            )
        )

        //Enter text
        onView(withId(R.id.et_search_books)).perform(typeText("A"))

        //Clear button should be visible
        onView(withId(R.id.et_search_books)).check(matches(isDisplayed())).check(
            matches(
                withRightDrawable(R.drawable.ic_clear)
            )
        )
    }

}

fun withRightDrawable(@DrawableRes id: Int) = object : TypeSafeMatcher<View>() {
    override fun describeTo(description: Description) {
    }

    override fun matchesSafely(view: View): Boolean {

        val drawable = (view as EditText).compoundDrawables[2]

        return drawable != null
    }
}
