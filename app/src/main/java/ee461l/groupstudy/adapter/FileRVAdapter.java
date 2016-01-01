package ee461l.groupstudy.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseFile;

import java.util.List;

import ee461l.groupstudy.R;

/**
 * Created by britne on 1/1/16.
 */
public class FileRVAdapter extends RecyclerView.Adapter<FileRVAdapter.ViewHolder> {

    private Context context;
    private List<ParseFile> files;

    public FileRVAdapter(Context context, List<ParseFile> files) {
        this.context = context;
        this.files = files;
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View view;
        public TextView fileName;
        public ImageView filePic;
        public CardView card;

        public ViewHolder(View v) {
            super(v);
            view = v;
            card = (CardView) v.findViewById(R.id.card_view);
            fileName = (TextView) v.findViewById(R.id.file_name);
            filePic = (ImageView) v.findViewById(R.id.file_pic);
        }
    }

    @Override
    public FileRVAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.file_item, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.fileName.setText(files.get(position).getName());
        //holder.filePic.setImageBitmap(something);

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // download/open file
            }
        });
    }

    @Override
    public int getItemCount() {
        return files.size();
    }
}