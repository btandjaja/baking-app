package com.btandjaja.www.bakingrecipes;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.support.test.espresso.contrib.RecyclerViewActions;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class UITest {
    private static final String FIRST_RECIPE = "Nutella Pie";

    @Rule
    public ActivityTestRule<ListOfRecipesActivity> mActivityTestRule = new ActivityTestRule<>(ListOfRecipesActivity.class);

    @Test
    public void appStartHasRelativeLayout() {
        onView(withId(R.id.rl_list_of_recipes)).check(matches(isDisplayed()));
    }

    @Test
    public void recyclerView_ListOfRecipesActivity_FirstRecipe_Phone() {
        int position = 0;
        onView(withId(R.id.rv_recipe_list)).perform(RecyclerViewActions
                .actionOnItemAtPosition(position, click()));
        onView(withId(R.id.tv_recipe_title)).check(matches(withText(FIRST_RECIPE)));
        onView(withId(R.id.rv_recipe_instruction_list)).check(matches(isDisplayed()));
    }

    @Test
    public void recyclerView_DetailActivity_FirstVideo_Phone() {
        int position = 0;
          //from ListOfRecipesActivity recyclerView, select the first viewholder
        onView(withId(R.id.rv_recipe_list)).perform(RecyclerViewActions
                .actionOnItemAtPosition(position, click()));
        // check shortDescription textbox exist
        onView(withId(R.id.rv_recipe_instruction_list)).check(matches(isDisplayed()));

        // if recyclerView exist, select the first viewHolder
        onView(withId(R.id.rv_recipe_instruction_list)).perform(RecyclerViewActions
        .actionOnItemAtPosition(position, click()));

        // exoplayer exist
        onView(withId(R.id.exoplayer_view)).check(matches(isDisplayed()));
    }
}

