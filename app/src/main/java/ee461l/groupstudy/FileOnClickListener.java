package ee461l.groupstudy;

import android.content.Context;
import android.view.View;

import ee461l.groupstudyendpoints.groupstudyEndpoint.model.FilesEntity;

/**
 * Created by britne on 4/27/15.
 */
public class FileOnClickListener implements View.OnClickListener {

    private Context context;
    private FilesEntity file;

    public FileOnClickListener(Context context, FilesEntity file) {
        this.context = context;
        this.file = file;
    }

    @Override
    public void onClick(View v) {
        OpenFileAsyncTask ofat = new OpenFileAsyncTask(context, file);
        ofat.execute();
    }
}
