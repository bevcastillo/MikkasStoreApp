package com.example.mikkasstoreapp.Tabs;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.mikkasstoreapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CompletedPaymentsTabFragment extends Fragment {


    public CompletedPaymentsTabFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_completed_payments_tab, container, false);

        return v;
    }

}
