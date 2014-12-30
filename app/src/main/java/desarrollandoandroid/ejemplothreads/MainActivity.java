package desarrollandoandroid.ejemplothreads;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;


public class MainActivity extends ActionBarActivity {

    private Button mImageButton, mProgressButton;
    private ImageView mImageView;
    ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mImageButton = (Button) findViewById(R.id.buttonImage);
        mImageView = (ImageView) findViewById(R.id.imageView);
        mProgressButton = (Button) findViewById(R.id.buttonProgress);

        mImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });
        mProgressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchBarDialog();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Cargamos la imagen desde internet en el hilo de interfaz de usuario de nuestra aplicación.
     */
    public void uploadImage() {
        new Thread(new Runnable() {
            // Definiremos el código para ejecutar en este hilo.
            private Bitmap loadImageFromNetwork(String url){
                try {
                    Bitmap mBitmap = BitmapFactory.decodeStream((InputStream) new URL(url).getContent());
                    return mBitmap;
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
            public void run() {
                // Ejecutamos el código definido.
                String url = "http://esphoto500x500.mnstatic.com/banco-de-arena-en-zanzibar_5805011.jpg";
                final Bitmap mBitmap = loadImageFromNetwork(url);
                mImageView.post(new Runnable() {
                    public void run() {
                        mImageView.setImageBitmap(mBitmap);
                    }
                });
            }
        }).start();
    }
    public void launchBarDialog() {
        mProgressDialog = new ProgressDialog(MainActivity.this);
        mProgressDialog.setTitle("Simulando descarga ...");
        mProgressDialog.setMessage("Descarga en progreso ...");
        mProgressDialog.setProgressStyle(mProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.setProgress(0);
        mProgressDialog.setMax(100);
        mProgressDialog.show();

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int j=1; j<=20; j++){
                    try{
                        Thread.sleep(1000);
                        mProgressDialog.incrementProgressBy(100/20);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
                mProgressDialog.dismiss();
            }
        }).start();

    }
}




