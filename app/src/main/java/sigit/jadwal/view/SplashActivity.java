package sigit.jadwal.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import sigit.jadwal.R;

public class SplashActivity extends AppCompatActivity implements Animation.AnimationListener{
    protected boolean _active = true;
    protected int _splashTime = 3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        StartAnimations();//Menjalankan Method Start Animasi
        Thread splashThread = new Thread() {
            //Timer SplashActivity
            public void run() {
                try{
                    int waited = 0;
                    while(_active && (waited < _splashTime)) {
                        sleep(100);
                        if(_active) {
                            waited += 100;
                        }
                    }
                } catch(InterruptedException e) {
                    // do nothing
                } finally {
                    Intent newIntent=new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(newIntent);
                    finish();
                }
            }
        };
        splashThread.start();
    }

    private void StartAnimations() {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.zoom_in);
        anim.reset();
        ImageView iv = (ImageView) findViewById(R.id.imageSplash);
        iv.clearAnimation();
        iv.startAnimation(anim);
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
