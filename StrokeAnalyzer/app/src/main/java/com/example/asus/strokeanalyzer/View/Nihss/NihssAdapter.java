package com.example.asus.strokeanalyzer.View.Nihss;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.asus.strokeanalyzer.Model.Analyzers.NihssAnalyzer;
import com.example.asus.strokeanalyzer.Model.NihssExamination;
import com.example.asus.strokeanalyzer.R;

import java.util.List;

/**
 * Created by Asus on 17.12.2017.
 */

public class NihssAdapter extends RecyclerView.Adapter<NihssAdapter.ViewHolder>  {

    private List<NihssExamination> examinations;
    // private final Context context;


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView date;
        public TextView sum;
        public final View mView;

        public ViewHolder(View view) {
            super(view);
            mView = view;

            date = (TextView) view.findViewById(R.id.examinationDate);
            sum = (TextView) view.findViewById(R.id.pointsSum);
        }
    }

    public NihssAdapter(final List<NihssExamination> examinations, Context context) {
        this.examinations = examinations;
        //this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.nihss_row, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(NihssAdapter.ViewHolder holder, int position) {

        final NihssExamination examination = examinations.get(position);

        holder.date.setText(examination.Date.toString());
       //_____________TO DO________________ holder.sum.setText(NihssAnalyzer.CountNihssSum());
        holder.sum.setText(0);

    }


    @Override
    public int getItemCount() {
        return examinations.size();
    }
}
