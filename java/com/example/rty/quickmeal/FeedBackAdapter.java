package com.example.rty.quickmeal;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;


public class FeedBackAdapter extends RecyclerView.Adapter<FeedBackAdapter.FeedbackViewHolder> {
    private List<Feedback> feedbacktList;

    public FeedBackAdapter(List<Feedback> feedbacktList) {
        this.feedbacktList=feedbacktList;
    }

    @Override
    public FeedbackViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.feedback_list, parent, false);

        return new FeedbackViewHolder(v);

    }

    // ------- Show feedback inside Recyclerview -------- 

    @Override
    public void onBindViewHolder(FeedbackViewHolder holder, int position) {

        Feedback c =feedbacktList.get(position);
        String username = c.getUserName();
        String userFeedback = c.getUserFeedback();
        holder.userName.setText(username);
        holder.userFeedback.setText(userFeedback);
    }

    @Override
    public int getItemCount() {
        return feedbacktList.size();
    }
    
    //--------- Get the data feilds inside Recyclerview using ViewHolder -----------
    public class FeedbackViewHolder extends RecyclerView.ViewHolder {

        View mView;
        public TextView userName;
        public TextView userFeedback;
        public FeedbackViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            userName = (TextView) itemView.findViewById(R.id.username);
            userFeedback = (TextView) itemView.findViewById(R.id.userfeedback);
        }
    }
}
