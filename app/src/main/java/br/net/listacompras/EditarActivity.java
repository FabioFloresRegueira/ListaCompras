package br.net.listacompras;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class EditarActivity extends AppCompatActivity {

    private EditText txtNome;
    private EditText txtQuantidade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar);

        ImageButton btnVoltar = findViewById(R.id.btnVoltarMainEDT);
        Button btnSalvar = findViewById(R.id.btnSalvar);
        Button btnExcluir = findViewById(R.id.btnExcluir);
        txtNome = findViewById(R.id.txtNomeEDT);
        txtQuantidade = findViewById(R.id.txtQuantidadeEDT);

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Salvar dados alterados.
            }
        });

        btnExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Excluir produto.
            }
        });

    }
}
