package mondaini.android.bagulhodoido.activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class LocalActivity extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		TextView tv = new TextView(this);
		tv.setText("Local");
		setContentView(tv);
	}
}
