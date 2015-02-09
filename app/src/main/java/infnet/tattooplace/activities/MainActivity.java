package infnet.tattooplace.activities;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.parse.ParseUser;

import infnet.tattooplace.R;
import infnet.tattooplace.fragments.WallFragment;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser == null) {
            loadLoginView();
        }

        FragmentManager manager = getFragmentManager();
        Fragment fragment = manager.findFragmentById(R.id.frameLayout);

        if (fragment == null) {
            fragment = new WallFragment();
            manager.beginTransaction().add(R.id.frameLayout, fragment)
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_meal_list, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_refresh: {
                updateMealList();
                break;
            }
            case R.id.action_favorites: {
                showFavorites();
                break;
            }
            case R.id.action_new: {
                newMeal();
                break;
            }
            case R.id.menu_title:{
                myMeal();
                break;
            }
            case R.id.menuLogout: {
                ParseUser.logOut();
                loadLoginView();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void myMeal() {
        Intent i = new Intent(this, MyWallActivity.class);
        startActivity(i);
    }

    private void updateMealList() {
        FragmentManager manager = getFragmentManager();
        WallFragment fragment = (WallFragment) manager.findFragmentById(R.id.frameLayout);
        fragment.getContentList();

    }

    private void showFavorites() {
//        favoritesAdapter.loadObjects();
//        setListAdapter(favoritesAdapter);
    }

    private void newMeal() {
        Intent i = new Intent(this, AddTattoActivity.class);
        startActivityForResult(i, 0);
    }

    private void loadLoginView() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            // If a new post has been added, update
            // the list of posts
            updateMealList();
        }
    }
}
