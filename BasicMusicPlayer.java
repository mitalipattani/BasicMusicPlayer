//Modify the source code. We do not need extra data files or strings. When clicking on the buttons, the main thread of application will // not do any real operation; instead, it will pass intents to the background service to trigger related actions.

private static final String TAG = "MyPlayer";
private static ImageButton playerButton, stopButton, resetButton;
@Override
public void onCreate(Bundle savedInstanceState) {
super.onCreate(savedInstanceState);
setContentView(R.layout.main);
Log.d(TAG, "after layout");

// Get the button from the view
playerButton = (ImageButton) findViewById(R.id.play);
playerButton.setOnClickListener(this);
stopButton = (ImageButton) findViewById(R.id.stop);
stopButton.setOnClickListener(this);
resetButton = (ImageButton) findViewById(R.id.reset);
resetButton.setOnClickListener(this);
}

public void onClick(View v) {
switch (v.getId()) {
    case R.id.play:
      startService(new Intent(this, MusicService.class));
      playerButton.setImageResource(R.drawable.pause);
      break;
    case R.id.stop:
      stopService(new Intent(this, MusicService.class));
      playerButton.setImageResource(R.drawable.play);
      break;
    case R.id.reset:
      stopService(new Intent(this, MusicService.class));
      playerButton.setImageResource(R.drawable.play);
      break;
    default:
     break;
 }
}
//Next task is to Create the background service called MusicService.java. Its superclass is android.app.Service.
