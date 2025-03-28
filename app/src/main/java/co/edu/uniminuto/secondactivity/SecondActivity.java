package co.edu.uniminuto.secondactivity;
//Importar
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import android.widget.Button;
import android.widget.EditText;
import android.view.View;

public class SecondActivity extends AppCompatActivity {
    /// declaracion de los componentes para interacción del user
    private TextView textViewTask;//muestra el titulo
    private EditText editTextTask;//editar la task
    private Button btnSave, btnDeclinar;//botones para editar y eliminar
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_op);///Se vincula con el diseño xml

        // Inicializar la UI
        initUI();

        // Obtener los datos de la actividad 1
        loadTaskData();

        // Se configura los eventos de los botones Editar y Declinar
        btnSave.setOnClickListener(this::saveTask);// guardar
        btnDeclinar.setOnClickListener(this::deleteTask);//eliminar
    }

    // Metodo para inicializar los componentes de la interfaz
    private void initUI() {

        textViewTask = findViewById(R.id.textViewTask);
        editTextTask = findViewById(R.id.namTask);
        btnSave = findViewById(R.id.btnSave);
        btnDeclinar = findViewById(R.id.btnDeclinar);
    }
    // Metodo para recibir y mostrar la tarea seleccionada
    private void loadTaskData() {
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("TASK_NAME") && intent.hasExtra("position")) {
            String taskName = intent.getStringExtra("TASK_NAME");  // obtiene el nombre de la tarea
            position = intent.getIntExtra("position", -1); // se guarda la posicion de la lista del elemento seleccionado
            editTextTask.setText(taskName);
        }
    }
/// //Metodo para guardar
    public void saveTask(View view) {
        String updatedTask = editTextTask.getText().toString().trim();
        if (!updatedTask.isEmpty()) {
            Intent intent = new Intent();
            intent.putExtra("updatedTask", updatedTask);
            intent.putExtra("position", position);
            setResult(RESULT_OK, intent);
            finish();
        }
    }
/// Metodo para eliminar
    public void deleteTask(View view) {
        Intent intent = new Intent();
        intent.putExtra("delete", true);
        intent.putExtra("position", position);
        setResult(RESULT_OK, intent);
        finish();
    }
} //// :)
