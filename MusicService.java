public class MusicService extends Service {
  private static final String TAG = "MyService";
  MediaPlayer player;
    @Override
    public IBinder onBind(Intent intent) {
    return null;
    }

  @Override
  public void onCreate() {
        Toast.makeText(this, "Music Service Created", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onCreate");
        player = MediaPlayer.create(this, R.raw.braincandy);
        player.setLooping(false); // Set looping
  }
  @Override
  public void onDestroy() {
        Toast.makeText(this, "My Service Stopped", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onDestroy");
        player.stop();
        player.reset();
        // Release media instance to system
        player.release();
  }
  @Override
  public void onStart(Intent intent, int startid) {
        Toast.makeText(this, "My Service Started", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onStart");
        player.start();
  }
}

// Add a declaration for the service in <application> of AndroidManifest.xml. This is necessary for any newly created background services.
//<service android:enabled="true" android:name="MusicService" />
// Run the application. Now the player will keep on playing music even if the main activity is minimized, because the background service, which is responsible for playing music, is
//still running.
