package com.example.crud;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText id,name,model;
    Button create,read,update,delete,email;
    DBHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        id=findViewById(R.id.id);
        name=findViewById(R.id.name);
        model=findViewById(R.id.model);
        create=findViewById(R.id.create);
        read=findViewById(R.id.read);
        update=findViewById(R.id.update);
        delete=findViewById(R.id.delete);
        email=findViewById(R.id.email);
        db=new DBHelper(this);
        create.setOnClickListener(v ->{
            name.setVisibility(View.VISIBLE);
            model.setVisibility(View.VISIBLE);
            id.setVisibility(View.INVISIBLE);
            if(name.getText().toString().length()!=0 && model.getText().toString().length()!=0){

                Boolean checkInsertData=
                        db.addData(name.getText().toString(),model.getText().toString());
                if(checkInsertData){
                    Toast.makeText(MainActivity.this,"New car added",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(MainActivity.this,"Something went wrong",Toast.LENGTH_LONG).show();
                }
            }

            else{
                Toast.makeText(MainActivity.this,"Field's can't be empty",Toast.LENGTH_LONG).show();
            }
            name.setText("");
            model.setText("");
        });

        read.setOnClickListener(v ->{


            id.setVisibility(View.INVISIBLE);
            Cursor res=db.getData();
            if(res.getCount()==0)
            {
                Toast.makeText(MainActivity.this,"No Entry",Toast.LENGTH_LONG).show();
                return;
            }
            StringBuffer buffer = new StringBuffer();
            while(res.moveToNext())
            {
                buffer.append("Id:"+res.getString(0)+"\n");
                buffer.append("Name:"+res.getString(1)+"\n");
                buffer.append("model:"+res.getString(2)+"\n");
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setCancelable(true);
            builder.setTitle("Car Details");
            builder.setMessage(buffer.toString());
            builder.show();

        });


        update.setOnClickListener(v ->{
            name.setVisibility(View.VISIBLE);
            model.setVisibility(View.VISIBLE);
            if(id.getVisibility()==View.VISIBLE){
                if(id.getText().toString().length()!=0 && name.getText().toString().length()!=0 && model.getText().toString().length()!=0)
                {
                    Boolean check=
                            db.updateData(Integer.parseInt(id.getText().toString()),name.getText().toString(),model.getText().toString());
                    if(check)
                    {
                        Toast.makeText(MainActivity.this,"Details updated",Toast.LENGTH_LONG).show();
                        id.setText("");
                        id.setVisibility(View.INVISIBLE);
                        name.setText("");
                        model.setText("");
                    }else{
                        Toast.makeText(MainActivity.this,"No Car",Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(MainActivity.this,"Field's cant be empty",Toast.LENGTH_SHORT).show();
                }
            }else{
                id.setVisibility(View.VISIBLE);
                Toast.makeText(MainActivity.this,"Enter the id",Toast.LENGTH_SHORT).show();
            }

        });


        delete.setOnClickListener(v ->{
            name.setVisibility(View.INVISIBLE);
            model.setVisibility(View.INVISIBLE);
            if(id.getVisibility()==View.VISIBLE){
                if(id.getText().toString().length()!=0 )
                {
                    Boolean check=
                            db.deletecar(Integer.parseInt(id.getText().toString()));
                    if(check)
                    {
                        Toast.makeText(MainActivity.this,"Car Deleted",Toast.LENGTH_LONG).show();
                        id.setText("");
                        id.setVisibility(View.INVISIBLE);

                    }else{
                        Toast.makeText(MainActivity.this,"No Car delete",Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(MainActivity.this,"Field's cant be empty",Toast.LENGTH_SHORT).show();
                }
            }else{
                id.setVisibility(View.VISIBLE);
                Toast.makeText(MainActivity.this,"Enter the id",Toast.LENGTH_SHORT).show();
            }

        });

        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String res=email.getText().toString();
                Intent intent= new Intent(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_EMAIL,res);
                intent.setType("message/rfc882");
                startActivity(Intent.createChooser(intent,"Choose an email client"));
            }
        });
    }
}