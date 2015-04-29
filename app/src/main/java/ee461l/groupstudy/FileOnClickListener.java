package ee461l.groupstudy;

import android.content.Context;
import android.view.View;

/**
 * Created by britne on 4/27/15.
 */
public class FileOnClickListener implements View.OnClickListener {

    private Context context;
    private String fileContents;

    public FileOnClickListener(Context context, String fileContents) {
        this.context = context;
        this.fileContents = fileContents;
    }

    @Override
    public void onClick(View v) {
        OpenFile.viewFile(fileContents);
    }
}
