package com.example.tinder.editinfor;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.tinder.R;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EditInforFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EditInforFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditInforFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private List<Integer> arrayImage = new ArrayList<>();
    private List<ImageView> arrayImg = new ArrayList<>();
    private int currSelect = 0;
    private Button btnAdd1, btnAdd2, btnAdd3, btnAdd4, btnAdd5, btnDelete;

    private OnFragmentInteractionListener mListener;

    public EditInforFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditInforFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EditInforFragment newInstance(String param1, String param2) {
        EditInforFragment fragment = new EditInforFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_infor, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        arrayImg.add((ImageView) view.findViewById(R.id.imageView1));
        arrayImg.add((ImageView) view.findViewById(R.id.imageView2));
        arrayImg.add((ImageView) view.findViewById(R.id.imageView3));
        arrayImg.add((ImageView) view.findViewById(R.id.imageView4));
        arrayImg.add((ImageView) view.findViewById(R.id.imageView5));
        arrayImg.add((ImageView) view.findViewById(R.id.imageView6));

        btnAdd1 = (Button) view.findViewById(R.id.buttonAdd1);
        btnAdd2 = (Button) view.findViewById(R.id.buttonAdd2);
        btnAdd3 = (Button) view.findViewById(R.id.buttonAdd3);
        btnAdd4 = (Button) view.findViewById(R.id.buttonAdd4);
        btnAdd5 = (Button) view.findViewById(R.id.buttonAdd5);
        btnDelete = (Button) view.findViewById(R.id.buttonDelete);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar3);

        arrayImage.add(R.drawable.girl_demo);
        arrayImage.add(R.drawable.girl_3);
        arrayImage.add(R.drawable.girl_2);
        arrayImage.add(R.drawable.girl_demo);
        setImage();

        //click Up back button
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        //select ImageView
        arrayImg.get(0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewImage(0);
            }
        });
        arrayImg.get(1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewImage(1);
            }
        });
        arrayImg.get(2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewImage(2);
            }
        });
        arrayImg.get(3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewImage(3);
            }
        });
        arrayImg.get(4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewImage(4);
            }
        });
        // end Select View
        //click button
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(arrayImage.isEmpty()) {
                    Toast.makeText(getActivity(), "empty", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    arrayImage.remove(currSelect);
                    if(arrayImage.isEmpty()){
                        arrayImg.get(0).setImageResource(0);
                        arrayImg.get(5).setImageResource(0);
                    }else {
                        setImage();
                        currSelect=0;
                    }

                }
            }
        });

    }


    private  void setImage(){
        if(arrayImage.isEmpty()) {
            return;
        }
        else {
            for(int i =0; i < arrayImage.size(); i++){
                arrayImg.get(i).setImageResource(arrayImage.get(i));
            }
            for (int i = arrayImage.size(); i < 5; i++){
                arrayImg.get(i).setImageResource(0);
            }
            arrayImg.get(5).setImageResource(arrayImage.get(0));
        }
    }

    private void viewImage(int index){
        if(arrayImg.get(index).getDrawable() == null)
            Toast.makeText(getActivity(),"image empty", Toast.LENGTH_SHORT).show();
        else {
            arrayImg.get(5).setImageResource(arrayImage.get(index));
            currSelect = index;
        }
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
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
