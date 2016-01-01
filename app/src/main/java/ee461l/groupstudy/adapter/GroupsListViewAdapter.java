package ee461l.groupstudy.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import java.util.List;

import ee461l.groupstudy.R;
import ee461l.groupstudy.activities.MainActivity;
import ee461l.groupstudy.fragments.GroupHomePageFragment;

/**
 * Created by britne on 4/24/15.
 *
 * Displays user's list of groups on home page
 */
public class GroupsListViewAdapter extends BaseAdapter {
    private List<String> groups;
    private Context context;
    private int layoutResourceId;
    private String groupName;
    private String username;

    static class ViewHolder {
        Button groupButton;
    }

    public GroupsListViewAdapter(Context context, int layoutResourceId, List<String> groups,
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

        String group = getItem(position);
        Log.d("GroupsListView", "position: " + position);

        // assign values if the object is not null
        if(group != null) {
            // get the Button from the ViewHolder and then set the text (group name)
            // and tag (item ID) values
            v.groupButton.setText(group);
            groupName = group;
            Log.d("GroupAdapter", "group name in adapter: " + groupName);
            v.groupButton.setTag(groupName);

            v.groupButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MainActivity main = new MainActivity();
                    main.sendGroupName(groupName);

                    NavigationView nv = (NavigationView) ((Activity) context).findViewById(R.id
                            .nav_view);
                    nv.getMenu().clear();
                    nv.inflateMenu(R.menu.menu_group_nav);
                    nv.getMenu().getItem(0).setChecked(true);

                    FragmentTransaction transaction = ((FragmentActivity) context)
                            .getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.content_fragment,
                            GroupHomePageFragment.newInstance(groupName, username))
                            .commit();
                }
            });
        }

        return convertView;
    }

    @Override
    public String getItem(int position) {
        return groups.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
