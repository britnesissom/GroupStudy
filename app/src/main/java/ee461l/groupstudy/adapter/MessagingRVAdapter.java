package ee461l.groupstudy.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import ee461l.groupstudy.R;
import ee461l.groupstudy.models.Message;

/**
 * Created by britne on 5/3/15.
 */
public class MessagingRVAdapter extends RecyclerView.Adapter<MessagingRVAdapter.ViewHolder> {

    private Context context;
    private String username;
    private List<Message> messages;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View view;
        public TextView message;

        public ViewHolder(View v) {
            super(v);
            view = v;
            message = (TextView) v.findViewById(R.id.message);
        }
    }

    public MessagingRVAdapter(Context context, String username, List<Message> messages) {
        this.context = context;
        this.username = username;
        this.messages = messages;
    }

    @Override
    public MessagingRVAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_item, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.message.setText(messages.get(position).getAuthor() + ": " + messages.get(position).getMessageText());

        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) holder.message.getLayoutParams();
        //Check whether message is mine and align to right
        if(messages.get(position).getAuthor().equals(username)) {
            holder.message.setBackgroundResource(R.drawable.yourself_message_border);
            lp.leftMargin = (int) context.getResources().getDimension(R.dimen.message_left_margin);
            lp.rightMargin = (int) context.getResources().getDimension(R.dimen.message_side_margin);
        }
        //If not mine then it is from teammate and align to left
        else {
            holder.message.setBackgroundResource(R.drawable.teammates_message_border);
            lp.rightMargin = (int) context.getResources().getDimension(R.dimen.message_right_margin);
            lp.leftMargin = (int) context.getResources().getDimension(R.dimen.message_side_margin);
        }

        holder.message.setLayoutParams(lp);
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }
}
