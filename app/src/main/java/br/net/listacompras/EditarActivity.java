package br.net.listacompras;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class EditarActivity extends AppCompatActivity {

    private EditText txtNome;
    private EditText txtQuantidade;
    private EditText txtPreco;
    private long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar);

        ImageButton btnVoltar = findViewById(R.id.btnVoltarMainEDT);
        Button btnSalvar = findViewById(R.id.btnSalvar);
        Button btnExcluir = findViewById(R.id.btnExcluir);
        txtNome = findViewById(R.id.txtNomeEDT);
        txtQuantidade = findViewById(R.id.txtQuantidadeEDT);
        txtPreco = findViewById(R.id.txtPrecoEDT);

        this.id = getIntent().getExtras().getLong("id");
        txtNome.setText(getIntent().getExtras().getString("nome"));
        txtQuantidade.setText(getIntent().getExtras().getInt("quantidade")+"");
        double precoUnitario = (getIntent().getExtras().getDouble("preco"))/(getIntent().getExtras().getInt("quantidade"));
        txtPreco.setText(precoUnitario/100+"");


        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (verificarCampos()) {
                    ListaComprasDB db = new ListaComprasDB(getBaseContext());
                    double preco = Double.parseDouble(txtPreco.getText().toString()) * Double.parseDouble(txtQuantidade.getText().toString());
                    Produto produto = new Produto(txtNome.getText().toString(),Integer.parseInt(txtQuantidade.getText().toString()), (preco*100), false);
                    db.atualizarProduto(id, produto);
                    finish();
                }
            }
        });

        btnExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListaComprasDB db = new ListaComprasDB(getBaseContext());
                db.removerProduto(id);
                finish();
            }
        });

    }

    private boolean verificarCampos() {
        String nome = txtNome.getText().toString();
        String quantidade = txtQuantidade.getText().toString();
        String preco = txtPreco.getText().toString();
        // -- NOME --
        if (TextUtils.isEmpty(nome)) {
            txtNome.setError("O nome do produto deve ser preenchido.");
            return false;
        }
        // -- QUANTIDADE --
        if (TextUtils.isEmpty(quantidade)) {
            txtQuantidade.setError("A quantidade do produto deve ser preenchida.");
            return false;
        }
        if (Integer.parseInt(quantidade) < 1) {
            txtQuantidade.setText("1");
        } else if (Integer.parseInt(quantidade) > 99) {
            txtQuantidade.setText("99");
        }
        // -- PRECO --
        if (TextUtils.isEmpty(preco)) {
            txtPreco.setError("O preço unitário do produto deve ser preenchido.");
            return false;
        }
        return true;
    }
}
