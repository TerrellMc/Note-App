package com.example.csc_330_navigation;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NoteModel {
    private List<NoteType> noteList = new ArrayList<>();
    private static  NoteModel sharedInstance = null;
    private Context context;

    public NoteModel(){
        noteList.add(new NoteType("good", "ha"));
        noteList.add(new NoteType("bad", "bla"));
        noteList.add(new NoteType("ok", "goo"));
        noteList.add(new NoteType("cool", "new"));
    }

    public interface PostNoteCompletionHandler {
        void postNote();
    }

    public interface GetNotCompletionHandler{
        void getNote(List<NoteType> noteTypesList);
    }

    private NoteModel(Context ctx) {
        this.context = ctx;
    }

    static synchronized public NoteModel getSharedInstance(Context ctx) {
        if (sharedInstance == null) {
            sharedInstance = new NoteModel(ctx);
        }
        return sharedInstance;
    }

    public int getCount(){
        return noteList.size();
    }

    //TODO: add getNotes method to pull notes from web service
    public void getNotes(final GetNotCompletionHandler getNotCompletionHandler){
        ServiceClient.getInstance().get(new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Gson gson = new Gson();
                NoteResponseObject noteResponseObject = gson.fromJson(response.toString(),NoteResponseObject.class);
                getNotCompletionHandler.getNote(noteResponseObject.data);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }

    //TODO: add postNotes method to post notes to web service
    public void postNotes(NoteType noteType, final PostNoteCompletionHandler postNoteCompletionHandler){
        ServiceClient.getInstance().post(noteType, new Response.Listener<JSONObject>() {
          @Override
          public void onResponse(JSONObject response) {
            postNoteCompletionHandler.postNote();
          }
      }, new Response.ErrorListener() {
          @Override
          public void onErrorResponse(VolleyError error) {

          }
      });
    }
    //TODO: add patchNotes method to edit notes that exist in web service




    //TODO: add methods to get notes from service client, edit notes from service client, post notes to service client, and delete notes from service client


}
