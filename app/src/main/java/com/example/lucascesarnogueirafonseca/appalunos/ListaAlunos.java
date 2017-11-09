package com.example.lucascesarnogueirafonseca.appalunos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ListaAlunos extends AppCompatActivity {

    //Constantes:

    private final String TAG = "CADASTRO_ALUNO";
    private final String ALUNOS_KEY = "LISTA";

    //Atributos de tela
    private EditText edNome;
    private Button botao;
    private ListView lvListagem;

    //Coleção de alunos
    private List<Aluno> listaAlunos;

    //Array Adapter converte listas e vetores em View
    private ArrayAdapter<Aluno> adapter;

    //Definição do layout de exibição da listagem

    private int adapterLayout = android.R.layout.simple_list_item_1;

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        //Verifica o item do menu selecionado
        switch (item.getItemId()){
            //Verifica se foi selecionado o item NOVO
            case R.id.menu_novo:
               //Toast.makeText(ListaAlunos.this, "Você clicou em novo", Toast.LENGTH_LONG).show();
                //Cria o especialista em mudança de telas
                Intent intent = new Intent(ListaAlunos.this, Formulario.class);
                startActivity(intent);
                return false;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        //Definição do objeto Inflater
        MenuInflater inflater = this.getMenuInflater();

        //Inflar um XML em um menu vazio
        inflater.inflate(R.menu.menu_principal, menu);

        //Exibe o menu
        return true;
    }

/*
    @Override
    protected void onSaveInstanceState(Bundle outState){
      //Inclusão da lista de alunos no objeto Bundle.MAP
        outState.putStringArrayList(ALUNOS_KEY,(ArrayList<Aluno>) listaAlunos);
        //Persistencia do objeto bundle
        super.onSaveInstanceState(outState);
        //Mensagem no LOG
        Log.i(TAG, "onSaveInstanceState(): "+ listaAlunos);

    }
    @Override
    protected void  onRestoreInstanceState(Bundle savedInstanceState){
        //Recupera o estado do objeto Bundle
        super.onRestoreInstanceState(savedInstanceState);
        //Carrega lista de alunos do Bundle.Map
        listaAlunos = savedInstanceState.getStringArrayList(ALUNOS_KEY);
        //Mensagem no log :
        Log.i(TAG, "onSaveRestoreState():" + listaAlunos);
    }
*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Ligação da tela ao seu controlador:
        setContentView(R.layout.activity_lista_alunos);

        //Ligação dos componentes de tela aos atributos da activity
        lvListagem = (ListView) findViewById(R.id.lvListagem);

        //Metodo que escuta o evento de click SIMPLES
        lvListagem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(ListaAlunos.this, "Aluno: "+listaAlunos.get(position),Toast.LENGTH_LONG).show();
            }
        });

        //Metodo que escuta o evento de click LONGO
        lvListagem.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(ListaAlunos.this, "Aluno: "+listaAlunos.get(position)+"[selecionado]",Toast.LENGTH_LONG).show();
                //True:Não executa o click simples/ False: executa o click simples
                return true;
            }
        });
    }

    private void carregarLista(){

        AlunoDAO dao = new AlunoDAO(this);
        listaAlunos = dao.listar();
        dao.close();

        adapter = new ArrayAdapter<Aluno>(this,adapterLayout,listaAlunos);
        lvListagem.setAdapter(adapter);

    }

    @Override
    protected void onResume(){
        super.onResume();
        this.carregarLista();
    }

}
