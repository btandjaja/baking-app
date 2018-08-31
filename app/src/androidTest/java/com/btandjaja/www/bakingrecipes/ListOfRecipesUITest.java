package com.btandjaja.www.bakingrecipes;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class ListOfRecipesUITest {
    public static final String FIRST_RECIPE = "Nutella Pie";

    @Rule
    public ActivityTestRule<ListOfRecipesActivity> mActivityTestRule = new ActivityTestRule<>(ListOfRecipesActivity.class);

    @Test
    public void appStartHasRelativeLayout() {
        // check if ListOfRecipesUI loads
        onView(withId(R.id.rl_list_of_recipes)).check(matches(isDisplayed()));
    }

    @Test
    public void recyclerView_displayed() {
        // check if the recyclerView exist
        onView(withId(R.id.rv_recipe_list)).check(matches(isDisplayed()));
    }

    @Test
    public void recyclerView_ListOfRecipesActivity_FirstRecipe_Phone() {
        int position = 0;
        // check responsive recyclerView
        onView(withId(R.id.rv_recipe_list)).perform(RecyclerViewActions
                .actionOnItemAtPosition(position, click()));
        // select the first recipe and match recipe name
        onView(withId(R.id.tv_recipe_title)).check(matches(withText(FIRST_RECIPE)));
        // check if steps recyclerView is displayed
        onView(withId(R.id.rv_recipe_instruction_list)).check(matches(isDisplayed()));
    }
}
