package ppblivecoding5october2017.myapplication;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private SQLiteDatabase DB;
    private Button insert, update, delete, select;
    EditText nama, lokasi, id;
    private SQLiteOpenHelper openDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        id = (EditText) findViewById(R.id.textId);
        lokasi = (EditText) findViewById(R.id.textLokasi);
        nama = (EditText) findViewById(R.id.textNama);

        View.OnClickListener operasi = new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(view.getId() == R.id.insertButton){
                    Toast ea;
                    ContentValues data =  new ContentValues();
                    data.put("id",id.getText().toString());
                    data.put("nama",nama.getText().toString());
                    data.put("lokasi",lokasi.getText().toString());
                    DB.insert("tanaman", null, data);
                    simpan();


                }
                else if(view.getId() == R.id.updateButton){
                    Cursor cursor = DB.rawQuery("select * from tanaman where id='"+id.getText().toString()+"'", null);
                    if(cursor.getCount() == 0){
                        select(1);
                    }
                    else{
                        ContentValues data =  new ContentValues();
//                    data.put("id",id.getText().toString());
                        data.put("nama",nama.getText().toString());
                        data.put("lokasi",lokasi.getText().toString());
                        DB.update("tanaman", data, "id='"+id.getText().toString()+"'",null);
                        update();
                    }

                }
                else if(view.getId() == R.id.deleteButton){
                    DB.delete("tanaman", "id='"+id.getText().toString()+"'", null);
                    delete();
                }
                else if(view.getId() == R.id.selectButton){
                    Cursor cursor = DB.rawQuery("select * from tanaman where id='"+id.getText().toString()+"'", null);
                    if(cursor.getCount() > 0){
                        cursor.moveToFirst();
                        id.setText(cursor.getString(cursor.getColumnIndex("id")));
                        nama.setText(cursor.getString(cursor.getColumnIndex("nama")));
                        lokasi.setText(cursor.getString(cursor.getColumnIndex("lokasi")));
                        select(0);
                    }
                    else{
                        select(1);
                    }
                }

            }


        };

        insert = (Button) findViewById(R.id.insertButton);
        insert.setOnClickListener(operasi);
        update = (Button) findViewById(R.id.updateButton);
        update.setOnClickListener(operasi);
        delete = (Button) findViewById(R.id.deleteButton);
        delete.setOnClickListener(operasi);
        select = (Button) findViewById(R.id.selectButton);
        select.setOnClickListener(operasi);

        openDB = new SQLiteOpenHelper(this, "db.sql", null, 1) {
            @Override
            public void onCreate(SQLiteDatabase sqLiteDatabase) {

            }

            @Override
            public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

            }
        };
        DB = openDB.getWritableDatabase();
        DB.execSQL("create table if not exists tanaman(id integer primary key, nama TEXT, lokasi TEXT);");





    }
    private void simpan(){
        Toast.makeText(this,"Berhasil insert",Toast.LENGTH_LONG).show();
    }
    private void update(){
        Toast.makeText(this,"Berhasil update",Toast.LENGTH_LONG).show();
    }
    private void delete(){
        Toast.makeText(this,"Berhasil delete",Toast.LENGTH_LONG).show();
    }
    private void select(int i){
        if(i == 0) Toast.makeText(this,"Berhasil select",Toast.LENGTH_LONG).show();
        else Toast.makeText(this,"data not found",Toast.LENGTH_LONG).show();

    }


}
