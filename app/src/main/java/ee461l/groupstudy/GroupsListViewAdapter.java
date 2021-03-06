package ee461l.groupstudy;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import java.io.File;
import java.util.List;

import ee461l.groupstudyendpoints.groupstudyEndpoint.model.Groups;

/**
 * Created by britne on 4/24/15.
 *
 * Displays user's list of groups on home page
 */
public class GroupsListViewAdapter extends BaseAdapter {
    private List<Groups> groups;
    private Context context;
    private int layoutResourceId;
    private String groupName;
    private String username;

    static class ViewHolder {
        Button groupButton;
    }

    public GroupsListViewAdapter(Context context, int layoutResourceId, List<Groups> groups,
                                 String username) {
        this.context = context;
        this.groups = groups;
        this.layoutResourceId = layoutResourceId;
        this.username = username;
    }

    @Override
    public int getCount() {
        return groups.size();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder v;

        if (convertView == null) {
            // inflate the layout
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(layoutResourceId, parent, false);

            // well set up the ViewHolder
            v = new ViewHolder();
            v.groupButton = (Button) convertView.findViewById(R.id.groupButton);

            // store the holder with the view.
            convertView.setTag(v);
        }
        else {
            // we've just avoided calling findViewById() on resource every time
            // just use the viewHolder
            v = (ViewHolder) convertView.getTag();
        }

        Groups group = getItem(position);
        Log.d("GroupsListView", "position: " + position);

        // assign values if the object is not null
        if(group != null) {
            // get the Button from the ViewHolder and then set the text (group name)
            // and tag (item ID) values
            v.groupButton.setText(group.getGroupName());
            groupName = group.getGroupName();
            v.groupButton.setTag(group.getId());

            v.groupButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("GroupsListView", "group name in onClick: " + groupName);
                    Intent intent = new Intent(context, NavDrawerGroups.class);
                    intent.putExtra("groupName", getItem(position).getGroupName());
                    intent.putExtra("username", username);
                    context.startActivity(intent);
                }
            });
        }

        return convertView;
    }

    @Override
    public Groups getItem(int position) {
        return groups.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
