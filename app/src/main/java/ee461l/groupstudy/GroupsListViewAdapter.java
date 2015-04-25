package ee461l.groupstudy;

import android.content.Context;
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

    public GroupsListViewAdapter(Context context, List<Groups> groups) {
        this.context = context;
        this.groups = groups;
    }

    @Override
    public int getCount() {
        return groups.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v;

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.home_page_groups_list_item, null);

        //load Button for single file
        Button groupButton = (Button) v.findViewById(R.id.groupButton);
        groupButton.setText(groups.get(position).getGroupName());

        //when button is clicked, open file in document viewer program
        groupButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                //open fragment for group
            }
        });
        return v;
    }

    @Override
    public Object getItem(int position) {
        return groups.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
