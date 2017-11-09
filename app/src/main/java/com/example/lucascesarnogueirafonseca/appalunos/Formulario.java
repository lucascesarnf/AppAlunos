package com.example.lucascesarnogueirafonseca.appalunos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Formulario extends AppCompatActivity {
    //Atributos de componentes de tela
private Button botao;
private  FormularioHelper helper;
    private Aluno alunoParaSerAlterado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Ligação da tela ao seu controlador:
        setContentView(R.layout.formulario);
        //Cria o objeto helper
        helper = new FormularioHelper(this);

        //Busca o aluno a ser alterado
        alunoParaSerAlterado = (Aluno) getIntent().getSerializableExtra("ALUNO_SELECIONADO");
        if(alunoParaSerAlterado!= null){
            //Atualiza a tela com os dados
            helper.setAluno(alunoParaSerAlterado);
        }

        botao = (Button) findViewById(R.id.button);
        botao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Aluno aluno;
                if(alunoParaSerAlterado!= null){
                    //Atualiza a tela com os dados

                 aluno = helper.getUpdatedAluno(alunoParaSerAlterado);

                }else{

                 aluno = helper.getAluno();

                }
                //Cria objeto DAO para iniciar conexao com DB
                AlunoDAO dao = new AlunoDAO(Formulario.this);

                if(aluno.getId() == null){
                    //Chamada do metodo de cadastro do Aluno
                    dao.cadastrar(aluno);
                }else{
                     dao.alterar(aluno);
                }
                //Encerramento da conexao com o bd
                dao.close();

                Toast.makeText(Formulario.this, aluno.getNome(), Toast.LENGTH_LONG).show();

                //Encerrando a activity
                finish();

                Log.i("CADASTRO_ALUNO", "Formulario encerrado");
            }
          });
        }
}
