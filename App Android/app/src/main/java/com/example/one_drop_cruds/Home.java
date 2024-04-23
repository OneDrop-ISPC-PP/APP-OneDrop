package com.example.one_drop_cruds;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.one_drop_cruds.utils.FilesManager;
import com.example.one_drop_cruds.utils.SharedPrefManager; // Importa la clase SharedPrefManager

import java.io.File;

public class Home extends AppCompatActivity {
    SharedPrefManager sharedPrefManager;
    TextView textView_welcome;
    FilesManager filesManager;
    WebView webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        webview = (WebView) findViewById(R.id.web_tip);
        webview.loadUrl("https://davidcosta92.github.io/noticias_one_drop/");


        textView_welcome = findViewById(R.id.textView_welcome);
        sharedPrefManager = new SharedPrefManager(getApplicationContext(), "oneDrop_shared_preferences");
        String loggedUsername = sharedPrefManager.getLoguedUsername();

        if (loggedUsername == null) {
            // Si el usuario no ha iniciado sesión, redirige a la actividad de inicio de sesión
            Intent loginIntent = new Intent(this, UserLoginActivity.class);
            startActivity(loginIntent);
            finish(); // Cierra la actividad actual
        } else {
            textView_welcome.setText("Bienvenido, " + loggedUsername + " !");
        }

        filesManager = new FilesManager(getApplicationContext(), this);
        this.askForPermissionsStorage();

        // Agrega la funcionalidad para cerrar sesión
        Button logoutButton = findViewById(R.id.button_logout);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Muestra un cuadro de diálogo de confirmación
                new AlertDialog.Builder(Home.this)
                        .setTitle("Cerrar Sesión")
                        .setMessage("¿Estás seguro que deseas cerrar sesión?")
                        .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Llama al método clearLoguedUser de SharedPrefManager para cerrar la sesión
                                sharedPrefManager.clearLoguedUser();

                                // Redirige al usuario a la actividad de inicio de sesión
                                Intent loginIntent = new Intent(Home.this, UserLoginActivity.class);
                                startActivity(loginIntent);
                                finish(); // Cierra la actividad actual
                            }
                        })
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // No hacer nada, simplemente cierra el cuadro de diálogo
                            }
                        })
                        .show();
            }
        });
    }

    public void aRegistrarGlucemia(View v) {
        Intent siguiente = new Intent(this, RegGlyActivity.class);
        startActivity(siguiente);
    }

    public void aRegistrarAnalisis(View v){
        Intent siguiente = new Intent(this, RegistoAnalisis.class);
        startActivity(siguiente);
    }

    public void toWeight(View v) {
        Intent weight = new Intent(this, RegWeightActivity.class);
        startActivity(weight);
    }

    public void toProfile(View v) {
        Intent pressure = new Intent(this, ProfileActivity.class);
        startActivity(pressure);
    }

    public void toContact(View v) {
        Intent pressure = new Intent(this, ContactoActivity.class);
        startActivity(pressure);
    }

    public void toPressure(View v) {
        Intent pressure = new Intent(this, RegPressureActivity.class);
        startActivity(pressure);
    }

    public void btn_export_data(View v) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.onedrop); // solo para enviar el logo de oneDrop a PDF
        Uri uri = filesManager.exportPdfFileReport(bitmap);
        if (uri != null) {
            Toast.makeText(this, "Se creó el PDF correctamente", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Error en la creación del PDF", Toast.LENGTH_LONG).show();
        }
    }

    public void shareFileWhatsApp(Uri uri) {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.setType("application/pdf"); // para PDFs
        shareIntent.setPackage("com.whatsapp");
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        Log.i("TAG", "************* COMPARTIENDO POR WHATSAPP => URI ****");
        Log.i("TAG", "***" + uri.toString());
        Log.i("TAG", "************* COMPARTIENDO POR WHATSAPP => URI ****");

        try {
            startActivity(Intent.createChooser(shareIntent, "Compartir a través de"));
        } catch (Exception ex) {
            Log.e("TAG", "************* COMPARTIENDO POR WHATSAPP => Exception ****" + ex.getCause().toString() + "***");
        }
    }

    public void askForPermissionsStorage() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            Log.i("TAG", "API MAYOR A 23");
            if (ContextCompat.checkSelfPermission(Home.this, WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                    && ContextCompat.checkSelfPermission(Home.this, READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                Log.i("TAG", "PERMISOS AUTORIZADOS!!!!!");
            } else {
                Log.i("TAG", "Permisos estaban rechazados... se los pido nuevamente");
                if (ActivityCompat.shouldShowRequestPermissionRationale(Home.this, WRITE_EXTERNAL_STORAGE)
                        && ActivityCompat.shouldShowRequestPermissionRationale(Home.this, READ_EXTERNAL_STORAGE)) {
                    Log.i("TAG", "El usuario previamente rechazó los permisos");
                } else {
                    Log.i("TAG", "LOS PERMISOS NO ESTÁN AUTORIZADOS PERO NO FUERON RECHAZADOS NUNCA..");
                }
                Log.i("TAG", "-------------- VOY A PEDIR LOS PERMISOS -------------");
                ActivityCompat.requestPermissions(Home.this, new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, 200);
                Log.i("TAG", "-------------- VOY A PEDIR LOS PERMISOS -------------");
            }
        }
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 200) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                Log.i("TAG", "Permis autoo (onRequestPermissionsResult)!!!!!");
                Toast.makeText(Home.this, "Permis autoo (onRequestPermissionsResult)!", Toast.LENGTH_LONG).show();
            } else {
                Log.i("TAG", "El usuario previamente rechazó los permisos (onRequestPermissionsResult)");
                if (ActivityCompat.shouldShowRequestPermissionRationale(Home.this, WRITE_EXTERNAL_STORAGE)
                        && ActivityCompat.shouldShowRequestPermissionRationale(Home.this, READ_EXTERNAL_STORAGE)) {
                    new AlertDialog.Builder(this).setMessage("Debes aceptar permisos para usar la aplicación")
                            .setPositiveButton("Intentar nuevamente", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Log.i("TAG", "Solicitando permisos nuevamente en el cuadro de diálogo");
                                    ActivityCompat.requestPermissions(Home.this, new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, 200);
                                }
                            })
                            .setNegativeButton("No, gracias", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Log.i("TAG", "Los permisos siguen sin estar aceptados");
                                }
                            }).show();
                } else {
                    Log.i("TAG", "DEBES HABILITAR LOS PERMISOS MANUALMENTE");
                    Toast.makeText(Home.this, "DEBES HABILITAR LOS PERMISOS MANUALMENTE", Toast.LENGTH_LONG).show();
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
