package infnet.tattooplace.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import infnet.tattooplace.R;
import infnet.tattooplace.fragments.MyWallFragment;
import infnet.tattooplace.fragments.WallFragment;

public class MyWallActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_wall);

        FragmentManager manager = getFragmentManager();
        Fragment fragment = manager.findFragmentById(R.id.frameLayout);

        if (fragment == null) {
            fragment = new MyWallFragment();
            manager.beginTransaction().add(R.id.frameLayout, fragment)
                    .commit();
        }
    }


}
