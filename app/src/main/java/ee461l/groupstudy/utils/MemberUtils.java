package ee461l.groupstudy.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import ee461l.groupstudy.R;
import ee461l.groupstudy.async.AddMemberAsyncTask;
import ee461l.groupstudy.async.DeleteMemberAsyncTask;
import ee461l.groupstudy.models.Group;

/**
 * Created by britne on 1/11/16.
 */
public class MemberUtils {

    private Context context;
    private Group group;

    public MemberUtils(Context context, Group group) {
        this.context = context;
        this.group = group;
    }

    public void addMember() {

        LayoutInflater li = LayoutInflater.from(context);
        View promptsView = li.inflate(R.layout.add_delete_member_layout, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

        final EditText userInput = (EditText) promptsView
                .findViewById(R.id.memberInput);

        // set dialog message
        alertDialogBuilder
                .setTitle("Add a new member to your group")
                .setCancelable(false)
                .setPositiveButton("ADD",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //add member here
                                String memberToAdd = userInput.getText().toString();

                                AddMemberAsyncTask amat = new AddMemberAsyncTask(context,
                                        group);
                                amat.execute(memberToAdd);

                            }
                        })
                .setNegativeButton("CANCEL",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public void deleteMember() {
        LayoutInflater li = LayoutInflater.from(context);
        View promptsView = li.inflate(R.layout.add_delete_member_layout, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

        final EditText userInput = (EditText) promptsView
                .findViewById(R.id.memberInput);

        // set dialog message
        alertDialogBuilder
                .setTitle("Delete a member from your group")
                .setCancelable(false)
                .setPositiveButton("REMOVE",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                String memberToRemove = userInput.getText().toString();

                                //delete member here
                                DeleteMemberAsyncTask dmat = new DeleteMemberAsyncTask(context,
                                        group);
                                dmat.execute(memberToRemove);
                            }
                        })
                .setNegativeButton("CANCEL",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
