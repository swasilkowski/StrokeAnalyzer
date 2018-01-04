package com.example.asus.strokeanalyzer.View.Form;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.asus.strokeanalyzer.Model.Form.Answer.Answer;
import com.example.asus.strokeanalyzer.Model.Form.Answer.NumericAnswer;
import com.example.asus.strokeanalyzer.Model.Form.Answer.TextAnswer;
import com.example.asus.strokeanalyzer.Model.Form.Answer.TrueFalseAnswer;
import com.example.asus.strokeanalyzer.R;
import com.example.asus.strokeanalyzer.View.Helpers.ClickListener;
import com.example.asus.strokeanalyzer.View.Helpers.RecyclerTouchListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//sorce:https://medium.com/@ruut_j/a-recyclerview-with-multiple-item-types-bce7fbd1d30e

/**
 * Created by Asus on 03.12.2017.
 */

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.ViewHolder> {
    private List<Question> questions;
    private List<Answer> answers;
    private final Context context;


    public abstract class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }

        public abstract void bindType(Question question);
        public abstract void setAnswer();
    }

    public class ViewHolderDescriptiveQ extends ViewHolder {
        private final TextView question;
        private final EditText answer;//!!!!!!!!!!!!!!!!!!!!! TO DO !!!!!!!!!!
        private Question questionObject;

        public ViewHolderDescriptiveQ(View view) {
            super(view);

            question = (TextView) view.findViewById(R.id.questionTextD);
            answer = (EditText) view.findViewById(R.id.answerText);
        }

        @Override
        public void bindType(Question question) {
            questionObject = question;
            this.question.setText(((DescriptiveQ)question).getText());
            this.answer.setText(((DescriptiveQ)questionObject).getAnswer());

            answer.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    ((DescriptiveQ)questionObject).setAnswer(answer.getText().toString());
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
        }

        @Override
        public void setAnswer()
        {
            ((DescriptiveQ)questionObject).setAnswer(answer.getText().toString());
        }

    }

    public class ViewHolderNumericQ extends ViewHolder {
        private final TextView question;
        private final EditText answer;//!!!!!!!!!!!!!!!!!!!!! TO DO !!!!!!!!!!
        private Question questionObject;

        public ViewHolderNumericQ(View view) {
            super(view);

            question = (TextView) view.findViewById(R.id.questionTextN);
            answer = (EditText) view.findViewById(R.id.answerData);
        }

        @Override
        public void bindType(Question question) {
            questionObject = question;
            this.question.setText(((NumericQ)question).getText());
            this.answer.setText(String.valueOf(((NumericQ)questionObject).getAnswer()));

            answer.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    ((NumericQ)questionObject).setAnswer(Double.parseDouble(answer.getText().toString()));
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
        }

        @Override
        public void setAnswer()
        {
            ((NumericQ)questionObject).setAnswer(Double.parseDouble(answer.getText().toString()));
        }

    }

    public class ViewHolderTrueFalseQ extends ViewHolder {
        private final TextView question;
        private final CheckBox answer;//!!!!!!!!!!!!!!!!!!!!! TO DO !!!!!!!!!!
        private Question questionObject;

        public ViewHolderTrueFalseQ(View view) {
            super(view);

            question = (TextView) view.findViewById(R.id.questionTextTF);
            answer = (CheckBox) view.findViewById(R.id.answerBox);
        }

        @Override
        public void bindType(Question question) {
            questionObject = question;
            this.question.setText(((TrueFalseQ)question).getText());
            this.answer.setChecked(((TrueFalseQ)questionObject).getAnswer());

            answer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

                    ((TrueFalseQ)questionObject).setAnswer(answer.isChecked());
                }
            });
        }

        @Override
        public void setAnswer()
        {
            ((TrueFalseQ)questionObject).setAnswer(answer.isChecked());
        }
    }

    public class ViewHolderBulletedQ extends ViewHolder {
        private final TextView question;
        private final ListView answers;
        private BulletedAnswerAdapter aAdapter;
        private Question questionObject;
        private int answerID;

        public ViewHolderBulletedQ(View view) {
            super(view);

            question = (TextView) view.findViewById(R.id.questionTextB);
            answers = (ListView) view.findViewById(R.id.answer_recyclerview);
        }

        @Override
        public void bindType(Question question) {
            questionObject = question;
            this.question.setText(((BulletedQ)question).getText());

            // Set the adapter
            if (answers instanceof ListView) {
                Context context = answers.getContext(); //????????????

                //get patients list from database
                //namesList = dbh.getNameList(rankingID);

                aAdapter = new BulletedAnswerAdapter(((BulletedQ)question).getAnswers(),((BulletedQ)questionObject).getAnswer(),context);
                answers.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);

               // answers.setLayoutManager(new LinearLayoutManager(context));
            /*recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.addItemDecoration(new DividerItem(context, LinearLayoutManager.VERTICAL));
            ItemTouchHelper.Callback callback =
                    new SwipeHelperCallback(nAdapter);
            ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
            touchHelper.attachToRecyclerView(recyclerView);*/
                answers.setAdapter(aAdapter);
                //answers.setSelection(((BulletedQ)questionObject).getAnswer());


                //--------------TODO_--------NOOOOOOOOOOW
                answers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                        answerID = ((BulletedQ)questionObject).getAnswers().get(i).getId();
                        ((BulletedQ)questionObject).setAnswer(answerID);
                        aAdapter.SetAnswerID(answerID);
                        aAdapter.color(view,answerID);
                        //answers.setSelection(answerID);
                    }
                });

                //answers.performItemClick(answers.getChildAt(0),0,answers.getItemIdAtPosition(0));

            }
            //-------------------------------------------------------------
        }

        @Override
        public void setAnswer() {
            ((BulletedQ)questionObject).setAnswer(answerID);
        }
    }

/*    public class ViewHolder extends RecyclerView.ViewHolder {
       *//* public TextView name;
        public TextView number;
        public final View mView;*//*

        public ViewHolder(View view) {
            super(view);
           *//* mView = view;

            name = (TextView) view.findViewById(R.id.patientNameSurname);
            number = (TextView) view.findViewById(R.id.patientID);*//*
        }
    }*/

    public QuestionAdapter(final List<Question> questions, List<Answer> answers, Context context) {
        this.questions = questions;
        this.context = context;
        this.answers = answers;
    }

    @Override
    public int getItemViewType(int position) {
        return questions.get(position).getListItemType();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = null;
        switch (viewType) {
            case Question.BULLETED:
                view = LayoutInflater
                        .from(parent.getContext())
                        .inflate(R.layout.bulleted_question, parent, false);
                return new ViewHolderBulletedQ(view);
            case Question.DESCRIPTIVE:
                view = LayoutInflater
                        .from(parent.getContext())
                        .inflate(R.layout.descriptive_question, parent, false);
                return new ViewHolderDescriptiveQ(view);
            case Question.TRUEFALSE:
                view = LayoutInflater
                        .from(parent.getContext())
                        .inflate(R.layout.true_false_question, parent, false);
                return new ViewHolderTrueFalseQ(view);
            case Question.NUMERIC:
                view = LayoutInflater
                        .from(parent.getContext())
                        .inflate(R.layout.numeric_question, parent, false);
                return new ViewHolderNumericQ(view);

        }
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Question question = questions.get(position);
        holder.bindType(question);

        //final Question patient = questions.get(position);
/*
        holder.name.setText(patient.Name + " " + patient.Surname);
        holder.number.setText(patient.PatientNumber);*/

    }


    @Override
    public int getItemCount() {
        return questions.size();
    }

    public List<Answer> returnAnswers()
    {
        List<Answer> answers = new ArrayList<>();
        for(Question q:questions)
        {
            Answer answer = new Answer(-1);//no answer
            if(q instanceof DescriptiveQ)
            {
                answer = new TextAnswer(((DescriptiveQ) q).getID());
                ((TextAnswer)answer).Value= ((DescriptiveQ) q).getAnswer();
            }
            else if (q instanceof TrueFalseQ)
            {
                answer = new TrueFalseAnswer(((TrueFalseQ) q).getId());
                ((TrueFalseAnswer)answer).Value = ((TrueFalseQ) q).getAnswer();
            }
            else if (q instanceof BulletedQ)
            {
                answer = new NumericAnswer(((BulletedQ) q).getID());
                ((NumericAnswer)answer).Value = ((BulletedQ) q).getAnswer();//SAMO ID __________TO DO_________
            }
            else if (q instanceof NumericQ)
            {
                answer = new NumericAnswer(((NumericQ) q).getID());
                ((NumericAnswer) answer).Value = ((NumericQ) q).getAnswer();
            }
            answers.add(answer);
        }
        return answers;
    }



    /*    // Remove a RecyclerView item containing a specified Data object
    public void remove(Name data) {
        int position = namesList.indexOf(data);
        namesList.remove(position);
        notifyItemRemoved(position);
    }*/
}
