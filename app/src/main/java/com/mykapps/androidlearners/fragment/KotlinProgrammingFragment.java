package com.mykapps.androidlearners.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mykapps.androidlearners.R;
import com.mykapps.androidlearners.adapter.TopicAdapter;
import com.mykapps.androidlearners.data.Contract;
import com.mykapps.androidlearners.data.JsonDataList;
import com.mykapps.androidlearners.data.JsonLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class KotlinProgrammingFragment extends Fragment implements LoaderManager.LoaderCallbacks<String> {

    private static final String TAG = "JavaProgrammingFragment";
    private static int ADDRESSLOADER_ID = 99;
    /**
     * RecyclerView , custom List (List<JsonDataList>) variables created
     */
    private RecyclerView kotlinProgrammingRecyclerview;
    private RecyclerView.Adapter topicAdapter;
    private List<JsonDataList> jsonDataListsMain;
    private int gridCount;
    private Bundle bundle;
    private String placeholderString = "kotlin";

    public KotlinProgrammingFragment() {
        // Required empty public constructor
    }

    public static int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int scalingFactor = 180;
        int noOfColumns = (int) (dpWidth / scalingFactor);
        return noOfColumns;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_kotlin_programming, container, false);

    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        /**
         * Accessing Movie DB Key from the Gradle File
         * combined MoviedDBKey with base url will get popular movie information
         */
        kotlinProgrammingRecyclerview = getActivity().findViewById(R.id.kotlin_programming_recyclerview);
        kotlinProgrammingRecyclerview.setHasFixedSize(true);
        gridCount = calculateNoOfColumns(getActivity());
        kotlinProgrammingRecyclerview.setLayoutManager(new GridLayoutManager(getContext(), gridCount));
        jsonDataListsMain = new ArrayList<>();
        /**
         * Fetching JSON Data from the AsyncTask Loader
         * AsyncTask implemented in JsonAsyncTask Class
         */
        bundle = new Bundle();
        bundle.putString("url", Contract.KOTLIN_JSON_URL);
        getLoaderManager().initLoader(ADDRESSLOADER_ID, bundle, this);

    }

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {

        if (id == 99) {

            return new JsonLoader(getContext(), args.getString("url"));
        }
        return null;

    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {

        if (data != null) {

            try {
                JSONArray array = new JSONArray(data);

                for (int i = 0; i < array.length(); i++) {

                    JSONObject innerJsonObject = array.getJSONObject(i);
                    String topicID = innerJsonObject.getString("id");
                    String topicTitle = innerJsonObject.getString("title");
                    String topicImage = innerJsonObject.getString("image");
                    String topicVideoUrl = innerJsonObject.getString("video");
                    String topicLikesCount = innerJsonObject.getString("likes");
                    String topicCommentsCount = innerJsonObject.getString("comments");
                    JsonDataList jsonDataList = new JsonDataList(topicID, topicTitle, topicImage, topicVideoUrl, topicLikesCount, topicCommentsCount, placeholderString);
                    //dataBeforeInsertCheck(topicID, topicTitle, topicLikesCount, topicCommentsCount);
                    jsonDataListsMain.add(jsonDataList);
                    topicAdapter = new TopicAdapter(jsonDataListsMain, getContext());
                    kotlinProgrammingRecyclerview.setAdapter(topicAdapter);

                }


            } catch (JSONException e) {
                Log.d("onLoadFinished", "JSON parsing failed。 JSONException=" + e);
            }


        } else {
            Log.d("onLoadFinished", "onLoadFinished error!");
        }

    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

        getLoaderManager().restartLoader(ADDRESSLOADER_ID, null, this);
    }

/*
    private void dataBeforeInsertCheck(int insertTopicID, String insertTopicTitle, int insertLikesCount, int insertCommentsCount) {

        Uri dataUriCheck = Contract.EntryInfo.CONTENT_URI;
        dataUriCheck = dataUriCheck.buildUpon().appendPath(String.valueOf(insertTopicID)).build();
        Cursor cursorDataInsertCheck = getActivity().getContentResolver().query(dataUriCheck,
                null,
                null,
                null,
                null);
        if (cursorDataInsertCheck.getCount() == 0) {
            dataInsert(insertTopicID, insertTopicTitle, insertLikesCount, insertCommentsCount);
        }

    }*/

/*
    private void dataInsert(int insertTopicID, String insertTopicTitle, int insertLikesCount, int insertCommentsCount) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(Contract.EntryInfo.COLUMN_ID, insertTopicID);
        contentValues.put(Contract.EntryInfo.COLUMN_TITLE, insertTopicTitle);
        contentValues.put(Contract.EntryInfo.COLUMN_LIKES_COUNT, insertLikesCount);
        contentValues.put(Contract.EntryInfo.COLUMN_COMMENTS_COUNT, insertCommentsCount);
        Uri uri = getActivity().getContentResolver().insert(Contract.EntryInfo.CONTENT_URI, contentValues);

        if (uri != null) {
            Toast.makeText(getContext(), insertTopicTitle + "Details Added to data base", Toast.LENGTH_LONG).show();
        }


    }
    */

}
