package com.example.fotos;

import android.Manifest;
import android.app.AlertDialog;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CAMERA_PERMISSION = 100;
    private Uri photoURI;
    private ActivityResultLauncher<Uri> takePictureLauncher;
    private String currentPhotoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initCameraLauncher();

        Button btnFoto = findViewById(R.id.btnFoto);
        btnFoto.setOnClickListener(v -> checkCameraPermission());
    }

    private void initCameraLauncher() {
        takePictureLauncher = registerForActivityResult(
                new ActivityResultContracts.TakePicture(),
                result -> {
                    if (result) {
                        ImageView imageView = findViewById(R.id.imageView);
                        imageView.setImageURI(photoURI);
                        pedirNombreParaFoto(); // 👈 después de mostrarla
                    } else {
                        Toast.makeText(this, "No se capturó imagen", Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }

    private void checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    REQUEST_CAMERA_PERMISSION);
        } else {
            launchCamera();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                launchCamera();
            } else {
                Toast.makeText(this, "Permiso de cámara denegado", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void launchCamera() {
        try {
            File photoFile = createImageFile();
            photoURI = FileProvider.getUriForFile(
                    this,
                    "com.example.fotos.fileprovider",
                    photoFile);
            takePictureLauncher.launch(photoURI);
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error al crear archivo de imagen", Toast.LENGTH_SHORT).show();
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void pedirNombreParaFoto() {
        final EditText editText = new EditText(this);
        editText.setHint("Nombre para la foto");

        new AlertDialog.Builder(this)
                .setTitle("¿Guardar la foto?")
                .setMessage("Si te gusta la foto, ingresa un nombre para guardarla.\nSi no, presiona 'Descartar'.")
                .setView(editText)
                .setPositiveButton("Guardar", (dialog, which) -> {
                    String nuevoNombre = editText.getText().toString().trim();
                    if (!nuevoNombre.isEmpty()) {
                        renombrarFoto(nuevoNombre);
                    } else {
                        Toast.makeText(this, "Nombre vacío, no se guardó", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Descartar", (dialog, which) -> descartarFoto())
                .setCancelable(false)
                .show();
    }

    private void descartarFoto() {
        File file = new File(currentPhotoPath);
        if (file.exists() && file.delete()) {
            Toast.makeText(this, "Foto descartada", Toast.LENGTH_SHORT).show();
            ImageView imageView = findViewById(R.id.imageView);
            imageView.setImageDrawable(null); // limpiar imagen
        } else {
            Toast.makeText(this, "No se pudo eliminar la foto", Toast.LENGTH_SHORT).show();
        }
    }

    private void renombrarFoto(String nuevoNombre) {
        File originalFile = new File(currentPhotoPath);
        File nuevoArchivo = new File(originalFile.getParent(), nuevoNombre + ".jpg");

        if (originalFile.renameTo(nuevoArchivo)) {
            currentPhotoPath = nuevoArchivo.getAbsolutePath();
            Toast.makeText(this, "Foto guardada como: " + nuevoNombre + ".jpg", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Error al renombrar la foto", Toast.LENGTH_SHORT).show();
        }
    }


}
