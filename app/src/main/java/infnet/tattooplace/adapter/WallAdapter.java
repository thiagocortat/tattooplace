package infnet.tattooplace.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import infnet.tattooplace.R;
import infnet.tattooplace.models.Tatto;
import infnet.tattooplace.views.SquareImageView;

public class WallAdapter extends ArrayAdapter<Tatto> {

    private static final String TAG = "WallAdapter";

    private List<Tatto> tattos;

    public WallAdapter(Context context, List<Tatto> produtos) {
        super(context, android.R.layout.simple_list_item_1, produtos);
        this.tattos = produtos;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewgroup) {

        Activity activity = (Activity) getContext();
        ViewHolder viewHolder;
        if (view == null) {
            view = activity.getLayoutInflater().inflate(R.layout.adapter_item_wall, null);

            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) view.getTag();
        }

        Tatto tatto = tattos.get(i);


        Picasso.with(activity).load(tatto.getPhotoFile().getUrl()).into(viewHolder.imagem);

        viewHolder.titulo.setText(tatto.getTitle());

        return view;
    }

    private class ViewHolder {

        SquareImageView imagem;
        TextView titulo;

        public ViewHolder(View view){

            imagem = (SquareImageView) view.findViewById(R.id.imagem);
            titulo = (TextView) view.findViewById(R.id.title);

        }
    }


}