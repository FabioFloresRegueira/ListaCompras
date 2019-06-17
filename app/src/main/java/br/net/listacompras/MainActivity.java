package br.net.listacompras;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView lvProdutos;
    private ProdutosAdapter adapter;
    private List<Produto> produtos;
    private ListaComprasDB db;
    private double valorTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton btnAdd = findViewById(R.id.btnAdd);
        ImageButton btnTotal = findViewById(R.id.btnTotal);
        lvProdutos = findViewById(R.id.lvProdutos);

        listarProdutos();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AdicionarActivity.class);
                startActivity(intent);
            }
        });

        btnTotal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calcularValorTotal();
                mostrarValorTotal();
            }
        });

        lvProdutos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //TODO: Marcar item como comprado.
            }
        });

        lvProdutos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                try{
                    Produto produto = (Produto) adapter.getItem(position);
                    Intent intent = new Intent(view.getContext(), EditarActivity.class);
                    intent.putExtra("id", produto.getId());
                    intent.putExtra("nome", produto.getNome());
                    intent.putExtra("quantidade", produto.getQuantidade());
                    intent.putExtra("preco", produto.getPreco());
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
                return true;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        listarProdutos();
    }

    public void listarProdutos() {
        this.db = new ListaComprasDB(getBaseContext());
        this.produtos = db.getProdutos();
        this.adapter = new ProdutosAdapter(this.produtos, this);
        this.lvProdutos.setAdapter(this.adapter);
    }

    public void calcularValorTotal() {
        this.valorTotal = 0;
        for (Produto produto: produtos) {
            valorTotal = valorTotal + (produto.getPreco()/100);
        }
    }

    public void mostrarValorTotal() {
        String valorTotalFormatado = NumberFormat.getCurrencyInstance().format(valorTotal);
        final AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
        dlgAlert.setTitle("Valor Total");
        dlgAlert.setMessage("Total: " + valorTotalFormatado);
        dlgAlert.setPositiveButton("Fechar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dlgAlert.create().show();
    }
}
