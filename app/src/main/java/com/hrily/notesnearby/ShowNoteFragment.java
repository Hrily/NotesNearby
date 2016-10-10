package com.hrily.notesnearby;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ShowNoteFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ShowNoteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShowNoteFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String TITLE = "title";
    private static final String DESC = "desc";
    private static final String LAT = "lat";
    private static final String LNG = "lng";

    private String title;
    private String desc;
    private double lat, lng;

    private TextView Title, Desc, LatLng;
    private Button GoBack;

    private OnFragmentInteractionListener mListener;

    public ShowNoteFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param title Parameter 1.
     * @param desc Parameter 2.
     * @return A new instance of fragment ShowNoteFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ShowNoteFragment newInstance(String title, String desc, double lat, double lng) {
        ShowNoteFragment fragment = new ShowNoteFragment();
        Bundle args = new Bundle();
        args.putString(TITLE, title);
        args.putString(DESC, desc);
        args.putDouble(LAT, lat);
        args.putDouble(LNG, lng);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        title = "";
        desc = "";
        lat = 0;
        lng = 0;
        if (getArguments() != null) {
            title = getArguments().getString(TITLE);
            desc = getArguments().getString(DESC);
            lat = getArguments().getDouble(LAT);
            lng = getArguments().getDouble(LNG);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_show_note, container, false);
        Title = (TextView) rootView.findViewById(R.id.show_note_title);
        Desc = (TextView) rootView.findViewById(R.id.show_note_desc);
        LatLng = (TextView) rootView.findViewById(R.id.show_note_latlng);
        GoBack = (Button) rootView.findViewById(R.id.show_note_go_back);
        GoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new MapFragment();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, fragment);
                ft.commit();
            }
        });
        if(title.length()>0){
            Title.setText(title);
            Desc.setText(desc);
            LatLng.setText("@ "+String.valueOf(lat)+" , "+String.valueOf(lng));
        }
        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
