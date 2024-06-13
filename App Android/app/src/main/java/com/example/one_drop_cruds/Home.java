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

import com.example.one_drop_cruds.entities.user.FichaMedicaUsuario;
import com.example.one_drop_cruds.entities.user.LoguedUserDetails;
import com.example.one_drop_cruds.request.AuthRequests;
import com.example.one_drop_cruds.request.FileRequest;
import com.example.one_drop_cruds.utils.BackendUrl;
import com.example.one_drop_cruds.utils.RetrofitHelper;
import com.example.one_drop_cruds.utils.SharedPrefManager; // Importa la clase SharedPrefManager
import com.example.one_drop_cruds.utils.ToastHelper;
import com.example.one_drop_cruds.utils.UserSessionManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Home extends AppCompatActivity {
    UserSessionManager userSessionManager;
    SharedPrefManager sharedPrefManager;
    TextView textView_welcome;
    WebView webview;
    String baseUrl = new BackendUrl().getBackendUrl();
    String token;
    LoguedUserDetails loguedUser;
    AuthRequests authRequest;
    ToastHelper toastHelper;
    FileRequest fileRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        webview = (WebView) findViewById(R.id.web_tip);
        webview.loadUrl("https://davidcosta92.github.io/noticias_one_drop/");
        textView_welcome = findViewById(R.id.textView_welcome);

        // user sessions
        userSessionManager = new UserSessionManager(Home.this);
        sharedPrefManager = new SharedPrefManager(Home.this, "oneDrop_shared_preferences");
        token = sharedPrefManager.getUserToken();
        getLoguedUserDetails(token);

        toastHelper= new ToastHelper(Home.this);

        // request, Crea helper con el jwt, inicializa retrofit y crea RecordsRequest, para hacer solicitudes
        authRequest = new RetrofitHelper(token).getRetrofitHelperWithToken().create(AuthRequests.class);
        fileRequest = new RetrofitHelper(token).getRetrofitHelperWithToken().create(FileRequest.class);

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

    private void getLoguedUserDetails(String token){
        HttpLoggingInterceptor getDetailsUserInterceptor = new HttpLoggingInterceptor();
        getDetailsUserInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(getDetailsUserInterceptor);

        // todo el interceptor para agregar el token al header
        httpClient.addInterceptor(chain -> {
            Request originalRequest = chain.request();
            Request.Builder builder = originalRequest.newBuilder();

            if (token!= null &&!token.isEmpty()) {
                builder.header("Authorization", "Bearer " + token);
            }
            return chain.proceed(builder.build());
        }); //todo el interceptor para agregar el token al header

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl+"/auth/userDetails/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
        AuthRequests authRequest = retrofit.create(AuthRequests.class);
        Call<LoguedUserDetails> call = authRequest.getUserDetailsRequest();
        call.enqueue(new Callback<LoguedUserDetails>() {
            @Override
            public void onResponse(Call<LoguedUserDetails> call, Response<LoguedUserDetails> response) {
                if(response.isSuccessful() && response.body() != null){
                    // Obtener datos del usuario de la resp y guardarlo en shared pref como un objeto json
                    loguedUser = response.body();
                    getFichaMedicaUsuario();
                    sharedPrefManager.setLoguedUser(response.body());
                    textView_welcome.setText("Bienvenido, " + response.body().getUsername());
                } else if (response.code()==400){
                    sharedPrefManager.clearLoguedUser();
                }
            }
            @Override
            public void onFailure(Call<LoguedUserDetails> call, Throwable t) {
                sharedPrefManager.clearLoguedUser();
            }
        });
    }
    private void getFichaMedicaUsuario(){
        Call<FichaMedicaUsuario> call = authRequest.getFichaMedicaUsuario(loguedUser.getId());
        call.enqueue(new Callback<FichaMedicaUsuario>() {
            @Override
            public void onResponse(Call<FichaMedicaUsuario> call, Response<FichaMedicaUsuario> response) {
                if(response.isSuccessful() && response.body() != null){
                    sharedPrefManager.setFichaMedicaUser(response.body());// Obtener datos de ficha medica y guardarlo en shared
                    System.out.println("******* FICHA MEDICA *******************");
                } else if (response.code()==400){
                    System.out.println(" FICHA MEDICA response.code()==400 SI NO ESTA CARGADA LA FICHA, SE DEBERIA REDIRIGIR A ACTIVIY DE CARGA DE FICHA MEDICA ***");
                    System.out.println(response.body());
                    // TODO SI NO ESTA CARGADA LA FICHA, SE DEBERIA REDIRIGIR A ACTIVIY DE CARGA DE FICHA MEDICA
                    // TODO SI NO ESTA CARGADA LA FICHA, SE DEBERIA REDIRIGIR A ACTIVIY DE CARGA DE FICHA MEDICA
                    // TODO SI NO ESTA CARGADA LA FICHA, SE DEBERIA REDIRIGIR A ACTIVIY DE CARGA DE FICHA MEDICA
                    System.out.println(" FICHA MEDICA response.code()==400 SI NO ESTA CARGADA LA FICHA, SE DEBERIA REDIRIGIR A ACTIVIY DE CARGA DE FICHA MEDICA ***");
                }
            }
            @Override
            public void onFailure(Call<FichaMedicaUsuario> call, Throwable t) {
                System.out.println("******* FICHA MEDICA Throwable t****************");
                System.out.println( t);
                // TODO SI NO ESTA CARGADA LA FICHA, SE DEBERIA REDIRIGIR A ACTIVIY DE CARGA DE FICHA MEDICA
                // TODO SI NO ESTA CARGADA LA FICHA, SE DEBERIA REDIRIGIR A ACTIVIY DE CARGA DE FICHA MEDICA
                // TODO SI NO ESTA CARGADA LA FICHA, SE DEBERIA REDIRIGIR A ACTIVIY DE CARGA DE FICHA MEDICA
                System.out.println("******* FICHA MEDICA Throwable t*******************");
            }
        });
    }

    public void aRegistrarGlucemia(View v) {
        Intent siguiente = new Intent(this, RegGlyActivity.class);
        startActivity(siguiente);
    }

    public void toWeight(View v) {
        Intent weight = new Intent(this, RegWeightActivity.class);
        startActivity(weight);
    }

    public void toProfile(View v) {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }

    public void toContact(View v) {
        Intent pressure = new Intent(this, ContactoActivity.class);
        startActivity(pressure);
    }

    public void toPressure(View v) {
        Intent pressure = new Intent(this, RegPressureActivity.class);
        startActivity(pressure);
    }
    public void toServices(View v) {
        Intent services = new Intent(this, services.class);
        startActivity(services);
    }

    public void btn_export_data(View v) {
        Call<ResponseBody> call = fileRequest.getFullResume(loguedUser.getId());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    savePdfToFile(response.body().bytes());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                toastHelper.showLong("Ocurrio un error creando el archivo! "+t.getMessage());
            }
        });
    }
    public void savePdfToFile(byte[] pdfBytes) {
        String fileName = "resume_" + loguedUser.getId() + ".pdf";
        File downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS); // Obtener el directorio de descargas
        File pdfFile = new File(downloadsDir, fileName);// Crear el archivo en el directorio de descargas
        try {
            FileOutputStream fos = new FileOutputStream(pdfFile);
            fos.write(pdfBytes);
            fos.close();
            toastHelper.showLong("Se creo el PDF correctamente, revisa las Descargas");
        } catch (IOException e) {
            e.printStackTrace();
            toastHelper.showLong("Ocurrio un error creando el archivo! "+e.getMessage());
        }
    }

    public void shareFileWhatsApp(Uri uri) {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.setType("application/pdf"); // para PDFs
        shareIntent.setPackage("com.whatsapp");
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        Log.i("TAG", "***** COMPARTIENDO POR WHATSAPP => URI **");
        Log.i("TAG", "*" + uri.toString());
        Log.i("TAG", "***** COMPARTIENDO POR WHATSAPP => URI **");

        try {
            startActivity(Intent.createChooser(shareIntent, "Compartir a través de"));
        } catch (Exception ex) {
            Log.e("TAG", "***** COMPARTIENDO POR WHATSAPP => Exception *" + ex.getCause().toString() + "**");
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
    public void openWebUrl(View view) {
        Uri uri = Uri.parse("https://www.onedrop.com");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }
}