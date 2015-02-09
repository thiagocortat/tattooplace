package infnet.tattooplace.fragments;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

import infnet.tattooplace.R;
import infnet.tattooplace.activities.DetailActivity;
import infnet.tattooplace.adapter.WallAdapter;
import infnet.tattooplace.models.ParseProxyObject;
import infnet.tattooplace.models.Tatto;


/**
 * A simple {@link android.app.Fragment} subclass.
 */
public class MyWallFragment extends WallFragment implements AdapterView.OnItemClickListener {


    public void getContentList(){
        ParseQuery<Tatto> query = new ParseQuery<Tatto>(Tatto.class);
        query.whereEqualTo("author", ParseUser.getCurrentUser());
        query.findInBackground(new FindCallback<Tatto>() {
            @Override
            public void done(List<Tatto> tattos, ParseException e) {
                if (e == null) {
                    mAdapter = new WallAdapter(getActivity(), tattos);
                    gridView.setAdapter(mAdapter);
                }
            }
        });
    }
}
