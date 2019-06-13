package br.net.listacompras;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ProdutosAdapter extends BaseAdapter {
    private List<Produto> produtos;
    private Activity activity;

    ProdutosAdapter(List<Produto> produtos, Activity activity) {
        this.produtos = produtos;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return produtos.size();
    }

    @Override
    public Object getItem(int position) {
        return produtos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        @SuppressLint("ViewHolder") View view = activity.getLayoutInflater().inflate(R.layout.item_lista, parent, false);
        Produto produto = produtos.get(position);

        //Instanciar objetos do xml;
        TextView nome = view.findViewById(R.id.li_nome);
        TextView quantidade = view.findViewById(R.id.li_quantidade);

        //Atribuir atributos nesses objetos;
        nome.setText(produto.getNome());
        quantidade.setText("Quantidade: " + produto.getQuantidade());

        return view;
    }
}