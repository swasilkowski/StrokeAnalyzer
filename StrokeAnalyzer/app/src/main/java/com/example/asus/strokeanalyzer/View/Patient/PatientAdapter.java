package com.example.asus.strokeanalyzer.View.Patient;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.asus.strokeanalyzer.Model.Patient;
import com.example.asus.strokeanalyzer.R;

import java.util.List;

/**
 * Created by Asus on 03.12.2017.
 */

public class PatientAdapter extends RecyclerView.Adapter<PatientAdapter.ViewHolder> {

    private List<Patient> patients;
   // private final Context context;


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView number;
        public final View mView;

        public ViewHolder(View view) {
            super(view);
            mView = view;

            name = (TextView) view.findViewById(R.id.patientNameSurname);
            number = (TextView) view.findViewById(R.id.patientID);
        }
    }

    public PatientAdapter(final List<Patient> patients, Context context) {
        this.patients = patients;
        //this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.patient_row, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PatientAdapter.ViewHolder holder, int position) {

        final Patient patient = patients.get(position);

        holder.name.setText(patient.Name + " " + patient.Surname);
        holder.number.setText(Integer.toString(patient.PatientNumber));

    }


    @Override
    public int getItemCount() {
        return patients.size();
    }

    /*    // Remove a RecyclerView item containing a specified Data object
    public void remove(Name data) {
        int position = namesList.indexOf(data);
        namesList.remove(position);
        notifyItemRemoved(position);
    }*/

}
