package ee461l.groupstudy;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import java.io.File;
import java.util.List;

import ee461l.groupstudyendpoints.groupstudyEndpoint.model.FilesEntity;
import ee461l.groupstudyendpoints.groupstudyEndpoint.model.Groups;

/**
 * Created by britne on 3/17/15.
 */
public class FileListViewAdapter extends BaseAdapter {

    private List<FilesEntity> files;
    private Context context;
    private int layoutResourceId;

    static class ViewHolder {
        Button fileButton;
    }

    public FileListViewAdapter(Context context, int layoutResourceId, List<FilesEntity> files) {
        this.context = context;
        this.files = files;
        this.layoutResourceId = layoutResourceId;
    }

    @Override
    public int getCount() {
        return files.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder v;

        if (convertView == null) {
            // inflate the layout
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(layoutResourceId, parent, false);

            // well set up the ViewHolder
            v = new ViewHolder();
            v.fileButton = (Button) convertView.findViewById(R.id.fileButton);

            // store the holder with the view.
            convertView.setTag(v);
        }
        else {
            // we've just avoided calling findViewById() on resource every time
            // just use the viewHolder
            v = (ViewHolder) convertView.getTag();
        }

        FilesEntity file = files.get(position);

        // assign values if the object is not null
        if(file != null) {
            // get the Button from the ViewHolder and then set the text (file name)
            // and tag (item ID) values
            v.fileButton.setText(file.getFileName());
            //groupName = group.getGroupName();
            v.fileButton.setTag(file.getFileName());

            v.fileButton.setOnClickListener(new FileOnClickListener(context, file));
        }

        return convertView;
    }

    @Override
    public Object getItem(int position) {
        return files.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    //create new class (RetrieveFilesFromServer) to save list of files
    //from server under specific group
    //can get group name depending on which group you click on in homepage
    //because it will have an id associated with it

    //FileListViewAdapter is for when we already have the list of files
    //downloaded from the server
}
