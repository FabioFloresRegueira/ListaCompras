package br.net.listacompras;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class AdicionarActivity extends AppCompatActivity {

    private EditText txtNome;
    private EditText txtQuantidade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar);

        ImageButton btnVoltar = findViewById(R.id.btnVoltarMain);
        Button btnAdd = findViewById(R.id.btnAddProduto);
        txtNome = findViewById(R.id.txtNome);
        txtQuantidade = findViewById(R.id.txtQuantidade);

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Salvar dados do produto.
            }
        });

    }
}
