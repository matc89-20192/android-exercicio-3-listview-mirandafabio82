package matc89.exercicio3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Tarefa> tarefas = new ArrayList<Tarefa>();
    private ArrayAdapter adapter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listView);

        atualizarListView();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tarefas.remove(position);
                atualizarListView();
            }
        });

    }


    public void adicionar(View view) {

        try {
            EditText campoPrioridade = (EditText) findViewById(R.id.editPrioridade);
            int prioridade = Integer.parseInt(campoPrioridade.getText().toString());

            EditText campoDescricao = (EditText) findViewById(R.id.editDescricao);
            String descricao = campoDescricao.getText().toString();

            if (verifyPrioridade(prioridade) && searchDescricao(descricao)) {

                EditText editTextDescricao = (EditText) findViewById(R.id.editDescricao);
                EditText editTextPrioridade = (EditText) findViewById(R.id.editPrioridade);

                tarefas.add(new Tarefa(editTextDescricao.getText().toString(), Integer.parseInt(editTextPrioridade.getText().toString())));

                atualizarListView();
            }
        } catch (NumberFormatException e) { }
    }

    private boolean verifyPrioridade(int prioridade) {

        if((prioridade < 1 || prioridade > 10)) {
            Toast.makeText(getApplicationContext(),"A prioridade deve estar entre 1 e 10.", Toast.LENGTH_SHORT).show();
            return false;
        } else {

            return true;
        }
    }

    private boolean searchDescricao(String descricao) {

        for (int i = 0; i < tarefas.size(); i++) {

            if(tarefas.get(i).getDescricao().equals(descricao)) {

                Toast.makeText(getApplicationContext(),"Tarefa já cadastrada.", Toast.LENGTH_SHORT).show();

                return false;
            }
        }

        return true;
    }

    public void remover(View view) {

        tarefas.remove(0);

        atualizarListView();
    }

    private void releaseButtonRemove(){

        Button remove = (Button) findViewById(R.id.buttonRemover);

        remove.setEnabled(verifySize());

    }

    private void atualizarListView(){

        Collections.sort(tarefas);

        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_2, android.R.id.text1, tarefas) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                TextView text2 = (TextView) view.findViewById(android.R.id.text2);

                text1.setText(tarefas.get(position).getDescricao());
                text2.setText("Prioridade: " + String.valueOf(tarefas.get(position).getPrioridade()));
                return view;
            }
        };

        listView.setAdapter(adapter);

        releaseButtonRemove();
    }

    private boolean verifySize(){
        if (tarefas.size() == 0) {
            return false;
        } else {
            return true;
        }
    }
}
