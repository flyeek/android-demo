package com.flyeek.dev.demo;


import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BlankFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BlankFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BlankFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BlankFragment newInstance(String param1, String param2) {
        BlankFragment fragment = new BlankFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public BlankFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("flyeek", "Fragment, onCreate, savedInstanceState is null = " + (savedInstanceState
                == null));
        if (savedInstanceState != null) {
            Log.d("flyeek", "Fragment, onCreate, savedInstanceState params = " + savedInstanceState
                    .getString("key"));
        }

        Bundle b = getArguments();

        if (b != null) {
            mParam1 = b.getString(ARG_PARAM1);
            mParam2 = b.getString(ARG_PARAM2);
            Log.d("flyeek", "Fragment, onCreate, argument = " + b.getString(ARG_PARAM1));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("flyeek", "fragment onCreateView, param1 is null = " + (mParam1 == null));
        TextView textView = new TextView(getActivity());
        textView.setText(R.string.hello_blank_fragment);
        return textView;
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        Log.d("flyeek", "Fragment, onSaveInstanceState");
        outState.putString("key", "test");
        super.onSaveInstanceState(outState);
    }

}
