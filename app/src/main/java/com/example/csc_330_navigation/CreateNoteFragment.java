package com.example.csc_330_navigation;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreateNoteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateNoteFragment extends Fragment {
    private static final String  TAG = "CreateNoteFragment";
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static  Context context;
    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private CreateNoteFragment createNoteFragment = this;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CreateNoteFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CreateNoteFragment newInstance(String param1, String param2) {
        CreateNoteFragment fragment = new CreateNoteFragment();
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
        final View fragmentView  = inflater.inflate(R.layout.fragment_login, container, false);
        fragmentView.findViewById(R.id.viewNotes).setEnabled(false);
        fragmentView.findViewById(R.id.viewNotes).setVisibility(View.INVISIBLE);

         String action = "create";
        if (getArguments() != null && getArguments().getString("action") != null) {
            action =  getArguments().getString("action");
            fragmentView.findViewById(R.id.viewNotes).setVisibility(View.VISIBLE);
            fragmentView.findViewById(R.id.viewNotes).setEnabled(true);
            EditText uploadTitle = fragmentView.findViewById(R.id.topicTextView);
            EditText uploadDescription = fragmentView.findViewById(R.id.note);
            String title = getArguments().getString("title");
            String noteDescription = getArguments().getString("description");
            int id = getArguments().getInt("id");
            uploadTitle.setText(title);
            uploadDescription.setText(noteDescription);

        }


        final String finalAction = action;
        fragmentView.findViewById(R.id.submitNote).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (finalAction.equals("create")) {

                    NoteModel noteModel = NoteModel.getSharedInstance();
                    //TODO: refer to postNote method so that when you create a note It gets sent to the post man
                    EditText noteView = fragmentView.findViewById(R.id.note);
                    EditText topicView = fragmentView.findViewById(R.id.topicTextView);
                    NoteType noteType = new NoteType(topicView.getText().toString(), noteView.getText().toString());
                    noteModel.postNotes(noteType, new NoteModel.PostNoteCompletionHandler() {
                        @Override
                        public void postNote() {
                            Navigation.findNavController(fragmentView).navigate(R.id.action_loginFragment_to_itemFragment);
                        }
                    });
                }
                else {
                    NoteModel noteModel = NoteModel.getSharedInstance();
                    EditText uploadTitle = fragmentView.findViewById(R.id.topicTextView);
                    EditText uploadDescription = fragmentView.findViewById(R.id.note);
                    String title = uploadTitle.getText().toString();
                    String noteDescription = uploadDescription.getText().toString();
                    assert getArguments() != null;
                    int id = getArguments().getInt("id");
                    NoteType noteType = new NoteType(title,noteDescription, id);


                    noteModel.patchNotes(noteType, new NoteModel.PatchNoteCompletionHandler() {
                        @Override
                        public void patchNote() {
                            Navigation.findNavController(fragmentView).navigate(R.id.action_loginFragment_to_itemFragment);
                        }
                    });
                }

            }
        });


        fragmentView.findViewById(R.id.viewNotes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NoteModel noteModel = NoteModel.getSharedInstance();
                assert getArguments() != null;
                int id = getArguments().getInt("id");

                noteModel.deleteNotes(id, new NoteModel.DeleteCompletionHandler() {
                    @Override
                    public void deleteNote() {
                        Navigation.findNavController(fragmentView).navigate(R.id.action_loginFragment_to_itemFragment);
                    }
                });

            }
        });
        return fragmentView;
    }


    }


