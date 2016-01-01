package ee461l.groupstudy;

import android.content.Context;
import android.view.View;

/**
 * Created by britne on 4/27/15.
 */
public class FileOnClickListener implements View.OnClickListener {

    private Context context;

    public FileOnClickListener(Context context) {
        this.context = context;
        //this.file = file;
    }

    @Override
    public void onClick(View v) {
        //OpenFileAsyncTask ofat = new OpenFileAsyncTask(context, );
        //ofat.execute();
    }
}
