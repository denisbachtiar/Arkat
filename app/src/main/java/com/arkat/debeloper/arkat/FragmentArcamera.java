package com.arkat.debeloper.arkat;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.debeloper.arkat.UnityPlayerActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentArcamera extends Fragment {

    private Button button;



    public FragmentArcamera() {
        // Required empty public constructor
    }

    Context context;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("AR Camera");
        View rootView = inflater.inflate(R.layout.fragment_arcamera, container, false);
        context = rootView.getContext();
        button = (Button) rootView.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), UnityPlayerActivity.class);
                startActivity(i);
            }
        });
        return rootView;
        // Inflate the layout for this fragment

    }

}
