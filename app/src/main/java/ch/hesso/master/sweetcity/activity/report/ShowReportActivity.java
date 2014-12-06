package ch.hesso.master.sweetcity.activity.report;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import ch.hesso.master.sweetcity.R;
import ch.hesso.master.sweetcity.data.CurrentReportList;
import ch.hesso.master.sweetcity.model.Report;


public class ShowReportActivity extends Activity {

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_report);

        imageView = (ImageView)findViewById(R.id.showReport);

        Integer position = getIntent().getIntExtra("report", 0);
        Report report = CurrentReportList.getInstance().get(position);

        Bitmap pictureBitmap = BitmapFactory.decodeFile(report.getImage());
        imageView.setImageBitmap(pictureBitmap);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_show_report, menu);
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
}
