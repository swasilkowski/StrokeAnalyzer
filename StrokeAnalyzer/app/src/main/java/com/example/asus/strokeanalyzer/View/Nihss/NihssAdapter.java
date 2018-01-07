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

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Asus on 17.12.2017.
 */

public class NihssAdapter extends RecyclerView.Adapter<NihssAdapter.ViewHolder>  {

    private List<NihssExamination> examinations;
    // private final Context context;


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView date;
        public TextView time;
        public TextView sum;
        public final View mView;

        public ViewHolder(View view) {
            super(view);
            mView = view;

            date = (TextView) view.findViewById(R.id.examinationDate);
            time = (TextView) view.findViewById(R.id.examinationTime);
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

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        holder.date.setText(dateFormat.format(examination.Date));
        holder.time.setText(timeFormat.format(examination.Date));
        holder.sum.setText(String.valueOf(NihssAnalyzer.CountNihssSum(examination)));

    }


    @Override
    public int getItemCount() {
        return examinations.size();
    }
}
