package infnet.tattooplace.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.squareup.picasso.Picasso;

import butterknife.InjectView;
import infnet.tattooplace.R;
import infnet.tattooplace.models.ParseProxyObject;
import infnet.tattooplace.models.Tatto;


public class DetailActivity extends BaseActivity{


    @InjectView(R.id.imagemProduto) protected ImageView imageView;
    @InjectView(R.id.titulo)        protected TextView titulo;
    @InjectView(R.id.preco)         protected TextView preco;
    @InjectView(R.id.adicionar)     protected Button adicionar;
//    @InjectView(R.id.recyclerView)  protected RecyclerView mRecyclerView;

    protected ParseProxyObject proxyObject;
//    protected RecyclerView.LayoutManager mLayoutManager;
//    protected ImagemAdapter mAdapter;

    @Override
    public void onBeforeInjectViews(Bundle savedInstanceState) {
        Bundle args = getIntent().getExtras();
        if (args != null){
            if (args.containsKey("tatto"))
                proxyObject = (ParseProxyObject) args.getSerializable("tatto");
        }
    }

    @Override
    public int getLayoutResource() { return R.layout.activity_detail; }

    @Override
    public void onAfterInjectViews(Bundle savedInstanceState) {
        titulo.setText(proxyObject.getString("title"));

        String url  = proxyObject.getString("photo");
        Picasso.with(this).load(url).skipMemoryCache().into(imageView);

    }

    private void setImagemProduto(String path){

//        Uri uri = Uri.fromFile(new File(path));
//        Picasso.with(this).load(uri).skipMemoryCache().into(imagemProduto, new Callback() {
//            @Override
//            public void onSuccess() {
//            }
//
//            @Override
//            public void onError() {}
//        });


    }
}
