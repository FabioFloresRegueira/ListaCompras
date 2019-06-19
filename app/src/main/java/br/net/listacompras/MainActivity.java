package br.net.listacompras;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
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

        android.support.v7.widget.Toolbar toolbar= findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);

        ImageButton btnTotal = findViewById(R.id.btnTotal);
        lvProdutos = findViewById(R.id.lvProdutos);

        listarProdutos();

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
                }
            }
        });

        lvProdutos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                ImageView img = view.findViewById(R.id.li_comprado);
                Produto produto = (Produto) adapter.getItem(position);
                if (produto.isComprado()) {
                    produto.setComprado(false);
                    db.comprarProduto(id, produto);
                    img.setVisibility(View.INVISIBLE);
                } else {
                    produto.setComprado(true);
                    db.comprarProduto(id,produto);
                    img.setVisibility(View.VISIBLE);
                }
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.item_add:
                adicionarProduto();
                return true;
            case R.id.item_limpar:
                limparProdutos();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void adicionarProduto() {
        Intent intent = new Intent(this, AdicionarActivity.class);
        startActivity(intent);
    }

    private void limparProdutos() {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        Toast.makeText(getApplicationContext(), "Operação cancelada.", Toast.LENGTH_SHORT).show();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        db.removerTodos();
                        produtos.clear();
                        listarProdutos();
                        Toast.makeText(getApplicationContext(), "Todos os produtos foram deletados.", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };
        AlertDialog.Builder ab = new AlertDialog.Builder(this);
        ab.setMessage("Deseja realmente excluir todos os produtos da lista?")
                .setNegativeButton("Sim", dialogClickListener)
                .setPositiveButton("Não", dialogClickListener)
                .show();
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
