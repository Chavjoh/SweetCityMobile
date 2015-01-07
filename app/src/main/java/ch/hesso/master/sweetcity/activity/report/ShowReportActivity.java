package ch.hesso.master.sweetcity.activity.report;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import ch.hesso.master.sweetcity.Constants;
import ch.hesso.master.sweetcity.R;
import ch.hesso.master.sweetcity.callback.PictureUploadCallbackImpl;
import ch.hesso.master.sweetcity.data.CurrentReportList;
import ch.hesso.master.sweetcity.model.Report;
import ch.hesso.master.sweetcity.model.Tag;
import ch.hesso.master.sweetcity.task.GetReportPictureAsyncTask;
import ch.hesso.master.sweetcity.utils.LayoutUtils;
import ch.hesso.master.sweetcity.utils.PictureUtils;

public class ShowReportActivity extends Activity {

    private Report report;
    private TextView tvUser;
    private TextView tvDate;
    private TextView tvVotes;
    private TextView tvTags;
    private ImageView ivPicture;
    private Bitmap bitmapPicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_report);

        findViews();

        Integer position = getIntent().getIntExtra("report", 0);
        this.report = CurrentReportList.getInstance().get(position);

        this.tvUser.setText(report.getUser().getPseudo());
        this.tvDate.setText(Constants.DATE_FORMAT.format(report.getSubmitDate().getValue()));
        this.tvVotes.setText(String.valueOf(report.getVote()));

        showTagList();
    }

    @Override
    protected void onResume() {
        super.onResume();

        loadPicture();
    }

    private void loadPicture() {
        if (bitmapPicture == null) {
            new GetReportPictureAsyncTask(
                    this,
                    new PictureUploadCallbackActivity(this),
                    PictureUtils.Key.fromString(report.getImage())
            ).execute();
        } else {
            setPicture(bitmapPicture);
        }
    }

    private void findViews() {
        this.tvUser = LayoutUtils.findView(this, R.id.tv_report_user);
        this.tvDate = LayoutUtils.findView(this, R.id.tv_report_date);
        this.tvVotes = LayoutUtils.findView(this, R.id.tv_report_votes);
        this.tvTags = LayoutUtils.findView(this, R.id.tv_report_tags);
        this.ivPicture = LayoutUtils.findView(this, R.id.iv_report_image);
    }

    private void showTagList() {
        StringBuilder sbTags = new StringBuilder();

        if (this.report.getListTag() != null)
            for (Tag tag : this.report.getListTag())
                sbTags.append((sbTags.length() > 0 ? ", " : "") + tag.getName());

        this.tvTags.setText(sbTags.length() == 0 ? "-" : sbTags.toString());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_show_report, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    public void setPicture(Bitmap picture) {
        this.bitmapPicture = picture;
        this.ivPicture.setImageBitmap(picture);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        savedInstanceState.putParcelable("image", bitmapPicture);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        setPicture((Bitmap) savedInstanceState.getParcelable("image"));
    }

    /**
     * Picture upload callback with display of the picture after loading
     */
    private class PictureUploadCallbackActivity extends PictureUploadCallbackImpl {

        public PictureUploadCallbackActivity(Activity context) {
            super(context);
        }

        @Override
        public void loaded(Bitmap picture) {
            super.loaded(picture);

            if (picture != null) {
                setPicture(picture);
            }
        }
    }
}
