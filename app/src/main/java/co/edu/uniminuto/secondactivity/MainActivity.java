package co.edu.uniminuto.secondactivity;

//Importaciones de las librerias
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //Declaración de variables de los componentes
    private EditText etTask; //ingreso de tareas
    private Button btnAdd;//boton para agregar tareas
    private ListView listTask;//lista para mostrar tareas
    private ArrayList<String> arrayList;//Arreglo para guardar las tareas
    private ArrayAdapter<String> adapter;//Adaptador para mostrar las tareas en la lista
    private static final int EDIT_TASK_REQUEST =1;//Solicitar editar tarea
    private int position;//guarda la posiión



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Configurar Edge-to-Edge
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        //Ajuste de padding
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initObject();
        //Generar evento click
        this.btnAdd.setOnClickListener(this::addTask);
        this.listTask.setOnItemClickListener(this::setOnItemClick);
    }
    // Metodo para agregar una nueva tarea a la lista
    private void addTask(View view){
        String task =this.etTask.getText().toString().trim();
        if(!task.isEmpty()){
            this.arrayList.add(task);
            this.adapter.notifyDataSetChanged();
            this.etTask.setText("");
        }else{
            //Mostrar mensaje si el campo esta vacio
            Toast.makeText(this, "Introduzca una tarea", Toast.LENGTH_SHORT).show();
        }
    }
    //inicializa componentes de la interfaz
    private void initObject(){
        this.etTask = findViewById(R.id.etTask);
        this.btnAdd = findViewById(R.id.btnAd);
        this.listTask = findViewById(R.id.lisTask);
        this.arrayList = new ArrayList<>();//Inicializa el arreglo
        this.adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayList);
        this.listTask.setAdapter(this.adapter);// Asigna el adaptador a la lista
    }
    //Manejo de el click en la lista
    private void setOnItemClick(AdapterView<?> adapterView, View view, int position, long l)
    {  this.position = position;
            // nos saca la otra ventana SecondActivity
        Intent intent = new Intent(MainActivity.this, SecondActivity.class);
        intent.putExtra("position", position);  // Envía la posición como entero
        intent.putExtra("TASK_NAME", arrayList.get(position));//envio de texto de la tarea
         // startActivity(intent);
        //  Toast.makeText(this, "tarea seleccionada" +  arrayList.get(position), Toast.LENGTH_SHORT).show();
        //Iniciar actividad esperadno un resultado
        startActivityForResult(intent, EDIT_TASK_REQUEST);
    }
    //Metodo para recibir el resultado de la actividad
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Verifica que es el resultado correcto
        if (requestCode == EDIT_TASK_REQUEST && resultCode == RESULT_OK && data != null) {
            int pos = data.getIntExtra("position", -1);
            if (pos != -1) {
                if (data.hasExtra("delete")) {
                    arrayList.remove(pos); // Eliminar tarea
                } else {
                    String updatedTask = data.getStringExtra("updatedTask");
                    arrayList.set(pos, updatedTask); // Actualizar tarea
                }
                adapter.notifyDataSetChanged();
            }
        }
    }

}




