package com.example.lucascesarnogueirafonseca.appalunos;

/**
 * Created by lucascesarnogueirafonseca on 08/11/17.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class AlunoDAO extends SQLiteOpenHelper{

    private  static final int VERSAO = 1;
    private  static final String TABELA = "Aluno";
    private  static final String DATABASE = "MPAlunos";

    private static final String TAG = "CADASTRO_ALUNO";

    public AlunoDAO(Context context){
        super(context, DATABASE, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        //Definição do comando DDL a ser executado
        String ddl = "CREATE TABLE IF NOT EXISTS " + TABELA + "( "
                + "id INTEGER PRIMARY KEY, "
                + "nome TEXT, telefone TEXT, endereco TEXT, "
                + "email TEXT, foto TEXT, "
                + "nota REAL);";

        //Executa comando SQLite
        db.execSQL(ddl);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
         //Comando de destruição da tabela
        String sql = "DROP TABLE IF EXIST "+ TABELA;

        //Executa o comando
        db.execSQL(sql);

    //Chama o método de construçao da base de dados
        onCreate(db);
    }

    public void cadastrar(Aluno aluno){
        SQLiteDatabase db = getWritableDatabase();
        //Objetivo para armazenar os valores dos campos
        ContentValues values = new ContentValues();

        //Definição de valores dos campos de tabela
        values.put("nome", aluno.getNome());
        values.put("telefone", aluno.getTelefone());
        values.put("endereco", aluno.getEndereco());
        values.put("email", aluno.getEmail());
        values.put("foto", aluno.getFoto());
        values.put("nota", aluno.getNota());

        //Inserir dados do Aluno no BD
        db.insert(TABELA, null, values);
        Log.i(TAG, "Aluno cadastrado: " +aluno.getNome());

    }

    public List<Aluno> listar(){
        //Definição da coleção de alunos
        List<Aluno> lista = new ArrayList<Aluno>();

        //Definição da instrução SQL
        String sql = "SELECT * FROM "+TABELA+" ORDER BY nome";

        //Objeto que recebe os registros do banco de dados
        Cursor cursor = getReadableDatabase().rawQuery(sql, null);
        try{

            while(cursor.moveToNext()){
                //Cria nova referencia para Aluno
                Aluno aluno = new Aluno();
                //Carregar os atributos de Aluno com dados do BD
                aluno.setId(cursor.getLong(0));
                aluno.setNome(cursor.getString(1));
                aluno.setTelefone(cursor.getString(2));
                aluno.setEndereco(cursor.getString(3));
                aluno.setEmail(cursor.getString(4));
                aluno.setFoto(cursor.getString(5));
                aluno.setNota(cursor.getDouble(6));

                //Adicionar novo Aluno a lista
                lista.add(aluno);
            }

        }catch (SQLException e){
            Log.e(TAG, e.getMessage());
        }finally {
            cursor.close();
        }
        return lista;


    }

    public void deletar(Aluno aluno){
        //Definição de array de parametros
        String[] args = {aluno.getId().toString()};

        //Exclusao do aluno
        getWritableDatabase().delete(TABELA, "id=?",args);

        Log.i(TAG,"Aluno deletado: "+aluno.getNome());

    }

    public void alterar(Aluno aluno){

        ContentValues values =  new ContentValues();

        //Definição de valores dos campos de tabela
        values.put("nome", aluno.getNome());
        values.put("telefone", aluno.getTelefone());
        values.put("endereco", aluno.getEndereco());
        values.put("email", aluno.getEmail());
        values.put("foto", aluno.getFoto());
        values.put("nota", aluno.getNota());

        //Colecao de valores de parametro do SQL
        String[] args = {aluno.getId().toString()};

        //Alterar dados do Aluno no BD
        getWritableDatabase().update(TABELA, values, "id=?",args);
        Log.i(TAG, "Aluno alterado: "+aluno.getNome());
    }

}
