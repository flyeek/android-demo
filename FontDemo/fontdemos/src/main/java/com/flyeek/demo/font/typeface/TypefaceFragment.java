package com.flyeek.demo.font.typeface;


import android.app.Fragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.flyeek.demo.font.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TypefaceFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TypefaceFragment extends Fragment {
    public static final String STYLE_NORMAL = "normal";
    public static final String STYLE_BOLD = "bold";
    public static final String STYLE_ITALIC = "italic";
    public static final String STYLE_BOLD_ITALIC = "bold-italic";

    public static final String FONTFAMILY_BUILDIN = "Roboto(sans)";
    public static final String FONTFAMILY_CUSTOM = "Noto(serif)";

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment TypefaceFragment.
     */
    public static TypefaceFragment newInstance() {
        TypefaceFragment fragment = new TypefaceFragment();
        return fragment;
    }

    public TypefaceFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_typeface, container, false);

        Button showButton = (Button) rootView.findViewById(R.id.typeface_show);
        showButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTypefaceInfo();
            }
        });

        return rootView;
    }

    private void showTypefaceInfo() {
        TextView sampleBuildinTxtView = (TextView) getActivity().findViewById(R.id
                .typeface_sample_buildin);
        TextView infoBuildinTxtView = (TextView) getActivity().findViewById(R.id
                .typeface_info_buildin);
        showTypefaceSample(sampleBuildinTxtView, infoBuildinTxtView, FONTFAMILY_BUILDIN);

        CustomTypefaceTextView sampleCustomTxtView = (CustomTypefaceTextView) getActivity().findViewById(R.id
                .typeface_sample_custom);
        TextView infoCustomTxtView = (TextView) getActivity().findViewById(R.id
                .typeface_info_custom);
        showTypefaceSample(sampleCustomTxtView, infoCustomTxtView, FONTFAMILY_CUSTOM);
    }

    private void showTypefaceSample(final TextView sampleTxtView,
                                    final TextView infoTxtView, String fontfamily) {
        // TODO refine string concatenation
        String infoString = "";
        infoString += "font family: " + fontfamily + "\n";
        // Get text style string, must be one of "normal" "bold" "italic" "bold-italic".
        String textStyleString;
        switch (sampleTxtView.getTypeface().getStyle()) {
            case Typeface.NORMAL:
                textStyleString = STYLE_NORMAL;
                break;
            case Typeface.BOLD:
                textStyleString = STYLE_BOLD;
                break;
            case Typeface.ITALIC:
                textStyleString = STYLE_ITALIC;
                break;
            case Typeface.BOLD_ITALIC:
                textStyleString = STYLE_BOLD_ITALIC;
                break;
            default:
                textStyleString = "";
                break;
        }
        infoString += "text style: " + textStyleString;

        infoTxtView.setText(infoString);
    }

}
