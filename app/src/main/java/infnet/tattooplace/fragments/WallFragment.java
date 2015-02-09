package infnet.tattooplace.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.List;

import infnet.tattooplace.R;
import infnet.tattooplace.activities.DetailActivity;
import infnet.tattooplace.adapter.WallAdapter;
import infnet.tattooplace.models.ParseProxyObject;
import infnet.tattooplace.models.Tatto;


/**
 * A simple {@link Fragment} subclass.
 */
public class WallFragment extends Fragment implements AdapterView.OnItemClickListener {

    protected GridView gridView;
    protected WallAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_wall, container, false);

        gridView = (GridView) v.findViewById(R.id.gridView);
        gridView.setOnItemClickListener(this);


        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getContentList();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Tatto tatto = (Tatto) parent.getItemAtPosition(position);
        // --- Sending ---
        ParseProxyObject ppo = new ParseProxyObject(tatto);

        Intent it = new Intent(getActivity(), DetailActivity.class);
        it.putExtra("tatto", ppo);
        startActivity(it);
    }

    public void getContentList(){
        ParseQuery<Tatto> query = new ParseQuery<Tatto>(Tatto.class);
        query.findInBackground(new FindCallback<Tatto>() {
            @Override
            public void done(List<Tatto> tattos, ParseException e) {
                if (e == null){
                    mAdapter = new WallAdapter(getActivity(), tattos);
                    gridView.setAdapter(mAdapter);
                }
            }
        });
    }
}
