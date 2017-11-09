package com.example.lucascesarnogueirafonseca.appalunos;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
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

    //Aluno selecionado no click longo da ListView
    private Aluno alunoSelecionado = null;

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


        //Informa que a ListView tem Menu de Contexto
        registerForContextMenu(lvListagem);

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

                //Marca o aluno como selecionado na ListView
                alunoSelecionado = (Aluno) adapter.getItem(position);

                Toast.makeText(ListaAlunos.this, "Aluno: "+listaAlunos.get(position)+"[selecionado]",Toast.LENGTH_SHORT).show();
                //True:Não executa o click simples/ False: executa o click simples
                return false;
            }
        });
    }

    private void excluirAluno(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Você quer deletar o aluno "+alunoSelecionado.getNome());
        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                AlunoDAO dao = new AlunoDAO(ListaAlunos.this);
                dao.deletar(alunoSelecionado);
                dao.close();
                carregarLista();
                alunoSelecionado = null;
            }
        });
    builder.setNegativeButton("Não", null);
        AlertDialog dialog = builder.create();
        dialog.setTitle("Confirmar operação");
        dialog.show();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu,view,menuInfo);

        getMenuInflater().inflate(R.menu.menu_contexto, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.menuDeletar:
                excluirAluno();
                break;
            default:
                break;
        }
        return  super.onContextItemSelected(item);
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
