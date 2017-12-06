package com.example.asus.strokeanalyzer.View.Form;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.asus.strokeanalyzer.R;

import java.util.List;

/**
 * Created by Asus on 04.12.2017.
 */

public class BulletedAnswerAdapter  extends RecyclerView.Adapter<BulletedAnswerAdapter.ViewHolder>  {

    private List<BulletedAnswer> answers;
    private final Context context;


    public class ViewHolder extends RecyclerView.ViewHolder {
        public Button answer;

        public ViewHolder(View view) {
            super(view);

            answer = (Button) view.findViewById(R.id.answerTextB);
        }
    }

    public BulletedAnswerAdapter(final List<BulletedAnswer> answers, Context context) {
        this.answers = answers;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.answer_row, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final BulletedAnswerAdapter.ViewHolder holder, int position) {

        final BulletedAnswer answer = answers.get(position);

        holder.answer.setText(answer.getText());
        holder.answer.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                chooseAnswer(holder.answer, answer.getId());
            }
        });
    }

    public void chooseAnswer(View view, int id)
    {
        //
    }

    @Override
    public int getItemCount() {
        return answers.size();
    }

    /*    // Remove a RecyclerView item containing a specified Data object
    public void remove(Name data) {
        int position = namesList.indexOf(data);
        namesList.remove(position);
        notifyItemRemoved(position);
    }*/

}
