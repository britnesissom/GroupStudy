package ee461l.groupstudy;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Created by britne on 3/17/15.
 */
public class FileListViewAdapter extends BaseAdapter {

    private List<File> files;
    private Context context;

    public FileListViewAdapter(Context context, List<File> files) {
        this.context = context;
        this.files = files;
    }

    @Override
    public int getCount() {
        return files.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v;

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.file_list_item, null);

        //load Button for single file
        Button fileButton = (Button) v.findViewById(R.id.fileButton);
        fileButton.setText(files.get(position).getName());

        //when button is clicked, open file in document viewer program
        fileButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                //open file in document viewer
            }
        });
        return v;
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
