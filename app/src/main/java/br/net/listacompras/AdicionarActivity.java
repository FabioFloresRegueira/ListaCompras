package br.net.listacompras;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class AdicionarActivity extends AppCompatActivity {

    private EditText txtNome;
    private EditText txtQuantidade;
    private EditText txtPreco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar);

        ImageButton btnVoltar = findViewById(R.id.btnVoltarMain);
        Button btnAdd = findViewById(R.id.btnAddProduto);
        txtNome = findViewById(R.id.txtNome);
        txtQuantidade = findViewById(R.id.txtQuantidade);
        txtPreco = findViewById(R.id.txtPreco);
        txtQuantidade.setText("1");
        txtPreco.setText("0.00");

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (verificarCampos()) {
                    ListaComprasDB db = new ListaComprasDB(getBaseContext());
                    double preco = Double.parseDouble(txtPreco.getText().toString()) * Double.parseDouble(txtQuantidade.getText().toString());
                    Produto produto = new Produto(txtNome.getText().toString(), Integer.parseInt(txtQuantidade.getText().toString()), (preco*100));
                    db.setProduto(produto);
                    finish();
                }
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
