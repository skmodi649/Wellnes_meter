package com.example.wellnesmeter;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link pulse#newInstance} factory method to
 * create an instance of this fragment.
 */
public class pulse extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    Random r;

    public pulse() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment pulse.
     */
    // TODO: Rename and change types and number of parameters
    public static pulse newInstance(String param1, String param2) {
        pulse fragment = new pulse();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        r = new Random();
        readHealthData();
    }


    private List<HealthSample> healthSampleList = new ArrayList<>();
    private void readHealthData() {
        String data = "user";
        int output = r.nextInt((3 - 1) + 1) + 1;
        data = data.concat(String.valueOf(output));
        InputStream is;
        if (data.equals("user1")) {
            is = getResources().openRawResource(R.raw.user1);
        } else if (data.equals("user2")) {
            is = getResources().openRawResource(R.raw.user2);
        } else {
            is = getResources().openRawResource(R.raw.user3);
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(
                is, Charset.forName("UTF-8"))
        );
        // Using Buffered Reader to get the single line of text from the .csv file
        String line = "";
        try {
            // step over headers
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                // Split by ','
                String[] tokens = line.split(",");

                //Reading the data
                HealthSample sample = new HealthSample();
                sample.setDay(tokens[0]);
                sample.setTemp(Integer.parseInt(tokens[1]));
                sample.setPulse(Integer.parseInt(tokens[2]));
                sample.setDiast(Integer.parseInt(tokens[3]));
                sample.setSyst(Integer.parseInt(tokens[4]));

                healthSampleList.add(sample);

            }
        } catch (IOException e) {
            Log.wtf("MyActivity", "Error reading data file on line" + line, e);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pulse, container, false);
        GraphView graph = (GraphView)view.findViewById(R.id.graph);

        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0, healthSampleList.get(0).getPulse()),
                new DataPoint(1, healthSampleList.get(1).getPulse()),
                new DataPoint(2, healthSampleList.get(2).getPulse()),
                //    new DataPoint(3, healthSampleList.get(4).getTemp()),
                new DataPoint(4, healthSampleList.get(3).getPulse())
        });
        graph.addSeries(series);
        return view;
    }
}