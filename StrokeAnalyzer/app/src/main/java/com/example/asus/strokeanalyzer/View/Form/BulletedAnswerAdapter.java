package com.example.asus.strokeanalyzer.View.Form;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.asus.strokeanalyzer.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * Created by Asus on 04.12.2017.
 */

public class BulletedAnswerAdapter  extends BaseAdapter {

    private List<BulletedAnswer> answers;
    private Map<Integer, TextView> buttons;
    private final Context context;
    private Integer answerID;

    @Override
    public int getCount() {
        return answers.size();
    }

    @Override
    public Object getItem(int i) {
        return answers.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public void SetAnswerID(int id)
    {
        answerID = id;
    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.answer_row, viewGroup, false);
        }

        BulletedAnswer answer  = (BulletedAnswer)getItem(i);
        final TextView t = (TextView)  view.findViewById(R.id.answerTextB);
        t.setText(answer.getText());
        color(t,answer.getId());
        buttons.put(answer.getId(),t);
        //
      /*  final BulletedAnswer answer  = (BulletedAnswer)getItem(i);
        final Button b = (Button) view.findViewById(R.id.answerTextB);
        b.setBackgroundResource(R.drawable.selected_button);
        b.setText(answer.getText());
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //chooseAnswer(b, answer.getId());
                b.setSelected(true);
            }
        });*/

        return view;
    }

    public void color(View view,int id)
    {
        if(id==answerID)
        {
            view.setBackgroundColor(context.getColor(R.color.colorAccent));
            //ustaw pokolorowanie
            // view.setSelected(true);
        }
        else
        {
            view.setBackgroundColor((context.getColor(R.color.colorPrimary)));
        }
    }


/*    public class ViewHolder extends RecyclerView.ViewHolder {
        public Button answer;

        public ViewHolder(View view) {
            super(view);

            answer = (Button) view.findViewById(R.id.answerTextB);
        }
    }*/

    public BulletedAnswerAdapter(final List<BulletedAnswer> answers, int pickedAnswer, Context context) {
        this.answers = answers;
        this.context = context;
        this.buttons = new Hashtable<>();
        answerID=pickedAnswer;
    }

  /*  @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.answer_row, parent, false);

        return new ViewHolder(itemView);
    }*/

/*    @Override
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
    }*/

    public void chooseAnswer(View view, int id)
    {

        view.setBackgroundColor(view.getResources().getColor(R.color.colorAccent));
            //kolorowanie elementu
           // ListPoint elem = productsList.get(position);
           // elem.setisBought(val);
           // notifyItemChanged(position);
    }

    public void clearColors()
    {
            for(Integer ans: new ArrayList<Integer>(buttons.keySet()))
            {
                color(buttons.get(ans),ans);
            }
    }


    /*@Override
    public int getItemCount() {
        return answers.size();
    }*/

    /*    // Remove a RecyclerView item containing a specified Data object
    public void remove(Name data) {
        int position = namesList.indexOf(data);
        namesList.remove(position);
        notifyItemRemoved(position);
    }*/

}
