package com.example.csc_330_navigation;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


/**
 * A fragment representing a list of Items.
 */
public class ItemFragment extends Fragment implements MyItemRecyclerViewAdapter.AdapterDelegate {
    private MyItemRecyclerViewAdapter myItemRecyclerViewAdapter;
    private List<NoteType> nValues;

    // add get method to pull notes from we service
    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;

    View fragmentView;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ItemFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static ItemFragment newInstance(int columnCount) {
        ItemFragment fragment = new ItemFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       final  View view = inflater.inflate(R.layout.fragment_item_list, container, false);
        view.findViewById(R.id.createNotes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_itemFragment_to_loginFragment);
            }
        });

        view.findViewById(R.id.aboutPage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_itemFragment_to_uploadFragment);
            }
        });

        // Set the adapter
        if (view.findViewById(R.id.list) instanceof RecyclerView) {
            Context context = view.getContext();
            final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.list);
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            NoteModel noteModel = NoteModel.getSharedInstance();
            myItemRecyclerViewAdapter = new MyItemRecyclerViewAdapter(new ArrayList<NoteType>());
            myItemRecyclerViewAdapter.delegate = this;
            recyclerView.setAdapter(myItemRecyclerViewAdapter);
            noteModel.getNotes(new NoteModel.GetNotCompletionHandler() {
                @Override
                public void getNote(List<NoteType> noteTypesList) {
                    myItemRecyclerViewAdapter.setNotes(noteTypesList);
                    myItemRecyclerViewAdapter.notifyDataSetChanged();
                }
            });
        }
        fragmentView = view;
        return view;
    }

    @Override
    public void didSelectRow(int index) {
        NoteModel noteModel = NoteModel.getSharedInstance();
        NoteType note = noteModel.getNote(index); // gets index of note
        // use this to get title and description
        Bundle bundle = new Bundle();
        bundle.putString("action", "update");
        bundle.putString("title",note.title);
        bundle.putString("description", note.content);
        bundle.putInt("id", note.noteId);
        //TODO: pass title and description back to createNote fragment use bundle
        Navigation.findNavController(fragmentView).navigate(R.id.action_itemFragment_to_loginFragment, bundle);

        int j = 5;
    }
}