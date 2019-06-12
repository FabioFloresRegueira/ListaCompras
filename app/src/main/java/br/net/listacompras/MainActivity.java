package br.net.listacompras;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    private ListView lvProdutos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton btnAdd = findViewById(R.id.btnAdd);
        lvProdutos = findViewById(R.id.lvProdutos);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AdicionarActivity.class);
                startActivity(intent);
            }
        });
    }
}
