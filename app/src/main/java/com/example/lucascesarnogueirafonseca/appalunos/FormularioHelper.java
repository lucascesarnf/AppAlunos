package com.example.lucascesarnogueirafonseca.appalunos;

import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;

/**
 * Created by lucascesarnogueirafonseca on 08/11/17.
 */

public class FormularioHelper {

    private EditText nome;
    private EditText telefone;
    private EditText endereco;
    private EditText site;
    private EditText email;
    private SeekBar nota;
    private ImageView foto;

    private Aluno aluno;

    public FormularioHelper(Formulario activity){

        //Associação de campos da tela a atributos de controle
        nome = (EditText) activity.findViewById(R.id.edName);
        telefone = (EditText) activity.findViewById(R.id.edPhone);
        endereco = (EditText) activity.findViewById(R.id.edEnd);
        email = (EditText) activity.findViewById(R.id.edMail);
        nota = (SeekBar) activity.findViewById(R.id.nota);
        foto = (ImageView) activity.findViewById(R.id.foto);

    }

    public Aluno getAluno(){

        //Cria Aluno
        aluno = new Aluno();
        aluno.setNome(nome.getText().toString());
        aluno.setTelefone(telefone.getText().toString());
        aluno.setEndereco(endereco.getText().toString());
        aluno.setEmail(email.getText().toString());
        aluno.setNota(Double.valueOf(nota.getProgress()));

        return aluno;
    }

}
