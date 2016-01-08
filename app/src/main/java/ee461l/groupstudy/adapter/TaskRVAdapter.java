package ee461l.groupstudy.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ee461l.groupstudy.R;
import ee461l.groupstudy.models.Task;

/**
 * Created by britne on 1/1/16.
 */
public class TaskRVAdapter extends RecyclerView.Adapter<TaskRVAdapter.ViewHolder> {

    private Context context;
    private List<Task> tasks;

    public TaskRVAdapter(Context context, List<Task> tasks) {
        this.context = context;
        this.tasks = tasks;
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View view;
        public TextView taskInfo;
        public TextView taskLocation;
        public TextView taskDate;
        public TextView taskTime;

        public ViewHolder(View v) {
            super(v);
            view = v;
            taskInfo = (TextView) v.findViewById(R.id.task_info);
            taskDate = (TextView) v.findViewById(R.id.task_date);
            taskLocation = (TextView) v.findViewById(R.id.task_location);
            taskTime = (TextView) v.findViewById(R.id.task_time);
        }
    }

    @Override
    public TaskRVAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.taskInfo.setText(tasks.get(position).getDescription());
        holder.taskLocation.setText(tasks.get(position).getLocation());
        holder.taskDate.setText(tasks.get(position).getDate());
        holder.taskTime.setText(tasks.get(position).getTime());
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }
}
