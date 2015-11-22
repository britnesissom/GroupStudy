package ee461l.groupstudy.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import ee461l.groupstudy.R;

/**
 * Created by britne on 5/3/15.
 */
public class MessagingListViewAdapter extends BaseAdapter {

    private Context context;
    private String username;
    private List<String> messages;
    private int layoutResourceId;

    static class ViewHolder {
        TextView message;
    }

    public MessagingListViewAdapter(Context context, String username, List<String> messages,
                                    int layoutResourceId) {
        this.context = context;
        this.username = username;
        this.messages = messages;
        this.layoutResourceId = layoutResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        String messageText = (String) this.getItem(position);

        if(convertView == null)
        {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.message_list_view_item,
                    parent, false);
            holder.message = (TextView) convertView.findViewById(R.id.message);
            convertView.setTag(holder);
        }
        else
            holder = (ViewHolder) convertView.getTag();

        holder.message.setText(messageText);

        //gets user from message
        String user = messageText.substring(0, messageText.indexOf(':'));

        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) holder.message.getLayoutParams();
        //Check whether message is mine and align to right
        if(user.equals(username))
        {
            holder.message.setBackgroundResource(R.drawable.yourself_message_border);
            lp.leftMargin = (int) context.getResources().getDimension(R.dimen.message_left_margin);
            lp.rightMargin = (int) context.getResources().getDimension(R.dimen.message_side_margin);
        }
        //If not mine then it is from teammate and align to left
        else
        {
            holder.message.setBackgroundResource(R.drawable.teammates_message_border);
            lp.rightMargin = (int) context.getResources().getDimension(R.dimen.message_right_margin);
            lp.leftMargin = (int) context.getResources().getDimension(R.dimen.message_side_margin);
        }

        holder.message.setLayoutParams(lp);

        return convertView;
    }

    @Override
    public Object getItem(int position) {
        return messages.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getCount() {
        return messages.size();
    }
}
