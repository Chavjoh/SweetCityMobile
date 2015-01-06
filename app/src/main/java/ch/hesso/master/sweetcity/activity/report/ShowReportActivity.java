package ch.hesso.master.sweetcity.activity.report;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.api.client.util.Joiner;

import java.text.SimpleDateFormat;

import ch.hesso.master.sweetcity.R;
import ch.hesso.master.sweetcity.data.CurrentReportList;
import ch.hesso.master.sweetcity.model.Report;
import ch.hesso.master.sweetcity.model.Tag;
import ch.hesso.master.sweetcity.utils.PictureUtils;
import ch.hesso.master.sweetcity.task.GetReportPictureAsyncTask;
import ch.hesso.master.sweetcity.callback.PictureUploadCallbackImpl;

public class ShowReportActivity extends Activity {

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_report);

        this.imageView = (ImageView)findViewById(R.id.showReportImage);

        TextView user = (TextView)findViewById(R.id.showReportUser);
        TextView date = (TextView)findViewById(R.id.showReportDate);
        TextView votes = (TextView)findViewById(R.id.showReportVotes);
        TextView tags = (TextView)findViewById(R.id.showReportTags);

        Integer position = getIntent().getIntExtra("report", 0);
        Report report = CurrentReportList.getInstance().get(position);

        user.setText(report.getUser().getPseudo());
        date.setText(new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(report.getSubmitDate().getValue()));
        votes.setText(String.valueOf(report.getVote()));

        StringBuilder sbTags = new StringBuilder();
        if (report.getListTag() != null)
            for (Tag tag : report.getListTag())
                sbTags.append((sbTags.length() > 0 ? ", " : "") + tag.getName());
        tags.setText(sbTags.length() == 0 ? "-" : sbTags.toString());

        new GetReportPictureAsyncTask(this, new PictureUploadCallbackImpl(this), PictureUtils.Key.fromString(report.getImage())).execute();
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

    public void setPicture(Bitmap picture) {
        this.imageView.setImageBitmap(picture);
    }
}
