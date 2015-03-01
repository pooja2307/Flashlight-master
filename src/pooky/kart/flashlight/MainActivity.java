package pooky.kart.flashlight;

import android.content.pm.PackageManager;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

	public boolean isLightOn;
	
	public Camera camera;
	public ImageButton button;
	
	
	@Override
	protected void onStop(){
		super.onStop();
		if (camera !=null){
			camera.release();
		}
	}
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
        PackageManager pm= getApplicationContext().getPackageManager();
	if (!  pm.hasSystemFeature(PackageManager.FEATURE_CAMERA)){
		Log.e("err", "Device has no camera!");
		return;
		}
	try{
		
		camera=Camera.open();
camera.setPreviewTexture(new SurfaceTexture(0));
		final Parameters param=camera.getParameters();
		button =(ImageButton) findViewById(R.id.imageButton);	
		button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			if(isLightOn){
				param.setFlashMode(Parameters.FLASH_MODE_OFF);
				camera.setParameters(param);
				camera.stopPreview();
				Toast.makeText(getApplicationContext(), "Flash stopped", Toast.LENGTH_SHORT).show();

				isLightOn=false;
			}else{

				param.setFlashMode(Parameters.FLASH_MODE_TORCH);
				camera.setParameters(param);
				camera.startPreview();
				Toast.makeText(getApplicationContext(), "Flash started", Toast.LENGTH_SHORT).show();

				isLightOn=true;
			}
				
				
			}
		});
	}catch(Exception e){
		Toast.makeText(this, "Your device does not support flashlight", Toast.LENGTH_LONG).show();
	}
	
	
	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
