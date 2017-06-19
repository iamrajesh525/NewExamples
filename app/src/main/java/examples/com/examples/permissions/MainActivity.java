package examples.com.examples.permissions;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

    PermissionHelper permissionHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button= (Button) findViewById(R.id.button);

       button.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               permissionHelper = new PermissionHelper(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA}, 100);
               permissionHelper.request(new PermissionHelper.PermissionCallback() {
                   @Override
                   public void onPermissionGranted() {
                       //called when all permissions are granted
                       Log.d("", "onPermissionGranted() called");
                   }

                   @Override
                   public void onPermissionDenied() {
                       //called when one or all permisions are denied
                       Log.d("", "onPermissionDenied() called");
                   }

                   @Override
                   public void onPermissionDeniedBySystem() {
                       //called when user cheked dont show check box and denied
                       Log.d("", "onPermissionDeniedBySystem() called");
                       permissionHelper.openAppDetailsActivity();
                   }
               });
           }
       });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (permissionHelper != null) {
            permissionHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
