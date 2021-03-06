
package com.openclassrooms.entrevoisins.neighbour_list;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.ui.neighbour_list.ListNeighbourActivity;
import com.openclassrooms.entrevoisins.ui.neighbour_list.NeighbourDetailsActivity;
import com.openclassrooms.entrevoisins.utils.DeleteViewAction;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.openclassrooms.entrevoisins.utils.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.core.IsNull.notNullValue;



/**
 * Test class for list of neighbours
 */
@RunWith(AndroidJUnit4.class)
public class NeighboursListTest {

    // This is fixed
    private static int ITEMS_COUNT = 12;

    private ListNeighbourActivity mActivity;

    @Rule
    public ActivityTestRule<ListNeighbourActivity> mActivityRule =
            new ActivityTestRule(ListNeighbourActivity.class);

    @Before
    public void setUp() {
        mActivity = mActivityRule.getActivity();
        assertThat(mActivity, notNullValue());
    }

    /**
     * On v??rifie que la liste contient au moins un ??l??ment
     */
    @Test
    public void myNeighboursList_shouldNotBeEmpty() {
        onView(allOf(withId(R.id.list_neighbours), isDisplayed()))
                .check(matches(hasMinimumChildCount(1)));
    }

    /**
     * Au clic sur supprimer, la liste compte bien un voisin en moins
     */
    @Test
    public void myNeighboursList_deleteAction_shouldRemoveItem() {
        // V??rifie qu'il y a bien 12 ??l??ments dans la liste
        onView(allOf(withId(R.id.list_neighbours), isDisplayed())).check(withItemCount(ITEMS_COUNT));
        // Clique sur le bouton supprimer du 2??me ??l??ment
        onView(allOf(withId(R.id.list_neighbours), isDisplayed()))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, new DeleteViewAction()));
        // V??rifie que le nombre d'??l??ments passe ?? 11
        onView(allOf(withId(R.id.list_neighbours), isDisplayed())).check(withItemCount(ITEMS_COUNT-1));
    }

    /**
     * Au clic sur un ??l??ment, l'??cran de d??tails se lance
     */
    @Test
    public void onItemClick_shouldStartDetailsActivity() {
        // Initialise Intents et d??marre l'enregistrement des intents
        Intents.init();
        // Effectue un clic sur l'??l??ment ?? la position 0
        onView(allOf(withId(R.id.list_neighbours), isDisplayed())).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        // V??rifie que l'??cran de d??tail est bien lanc??
        intended(hasComponent(NeighbourDetailsActivity.class.getName()));
        Intents.release();
    }

    /**
     * Au d??marrage de l'??cran de d??tails, le textView du nom du voisin en question est bien rempli
     */
    @Test
    public void cardInfoName_shouldDisplayNeighbourName() {
        // R??cup??re le deuxi??me voisin de la liste
        Neighbour neighbour = DI.getNeighbourApiService().getNeighbours().get(1);
        // Effectue un clic sur l'??l??ment ?? la position 1 (2e voisin)
        onView(allOf(withId(R.id.list_neighbours), isDisplayed())).perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        // V??rifie que le textView du nom du voisin contient bien le bon nom
        onView(withId(R.id.card_info_name)).check(matches(withText(neighbour.getName())));
    }

    /**
     * V??rifie que l'onglet Favoris n'affiche que les voisins marqu??s comme favoris
     */
    @Test
    public void favoritesTab_shouldOnlyDisplayFavorites() {
        // Affichage de l'onglet des favoris
        onView(withContentDescription("Favorites")).perform(click());
        // V??rifie que la liste est bien vide
        onView(allOf(withId(R.id.list_neighbours), isDisplayed())).check(withItemCount(0));
        // Affichage de l'onglet de tous les voisins
        onView(withContentDescription("My neighbours")).perform(click());
        // Effectue un clic sur l'??l??ment ?? la position 0 de la liste
        onView(allOf(withId(R.id.list_neighbours), isDisplayed())).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        // Ajoute le voisin aux favoris
        onView(withId(R.id.fab_favorite)).perform(click());
        // Retour ?? la liste des voisins
        pressBack();
        // Affichage de l'onglet des favoris
        onView(withContentDescription("Favorites")).perform(click());
        // La liste de favoris doit contenir un voisin
        onView(allOf(withId(R.id.list_neighbours), isDisplayed())).check(withItemCount(1));
    }
}