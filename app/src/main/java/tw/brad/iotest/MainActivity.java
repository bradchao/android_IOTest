package tw.brad.iotest;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity {
    private TextView info;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private File sdroot, app1root, app2root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        info = (TextView)findViewById(R.id.textInfo);

        sp = getSharedPreferences("game.data", MODE_PRIVATE);
//        sp = getPreferences(MODE_PRIVATE);
        editor = sp.edit();

        String state =
                Environment.getExternalStorageState();
        Log.d("brad", state);

        if (isExternalStorageReadable()){
            Log.d("brad", "r");
        }
        if (isExternalStorageWritable()){
            Log.d("brad", "w");
        }

        sdroot = Environment.getExternalStorageDirectory();
        app1root = new File(sdroot, "brad");
        app2root = new File(sdroot, "Android/data/" + getPackageName());
        if (!app1root.exists()){
            if (app1root.mkdirs()){
                Log.d("brad", "Create dir1 OK");
            }else{
                Log.d("brad", "Create dir1 Fail");
            }
        }else{
            Log.d("brad", "app1root exist");
        }
        if (!app2root.exists()){
            if (app2root.mkdirs()){
                Log.d("brad", "Create dir2 OK");
            }else{
                Log.d("brad", "Create dir2 Fail");
            }
        }else{
            Log.d("brad", "app2root exist");
        }
    }

    public void test1(View v){
        editor.putString("user", "Brad");
        editor.putInt("stage", 5);
        editor.putBoolean("sound", true);
        editor.commit();
        Toast.makeText(this, "Save OK", Toast.LENGTH_SHORT).show();

    }
    public void test2(View v){
        int stage = sp.getInt("stage",0);
        String user = sp.getString("user","nobody");
        info.setText(user + ":" + stage);
    }
    public void test3(View v){
        try {
            FileOutputStream fout = openFileOutput("file1.txt", MODE_PRIVATE);
            fout.write("Hello, WOrld\nHello, Brad\n".getBytes());
            fout.flush();
            fout.close();
            Toast.makeText(this, "Save2 OK", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "Exception:" + e.toString(), Toast.LENGTH_SHORT).show();
        }
    }
    public void test4(View v){
        info.setText("");
        try {
            FileInputStream fin = openFileInput("file1.txt");
            int c;
            while ((c = fin.read()) != -1){
                info.append("" + (char)c);
            }
            fin.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void test5(View v){

    }
    public void test6(View v){
    }
    /* Checks if external storage is available for read and write */
    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /* Checks if external storage is available to at least read */
    public static boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }
}
