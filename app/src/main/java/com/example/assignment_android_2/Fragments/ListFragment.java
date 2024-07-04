package com.example.assignment_android_2.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.assignment_android_2.Adapters.RecordAdapter;
import com.example.assignment_android_2.Data.RecordList;
import com.example.assignment_android_2.Data.SharePreferencesManager;
import com.example.assignment_android_2.Interfaces.Callback_ListItemClicked;
import com.example.assignment_android_2.R;
import com.google.gson.Gson;


public class ListFragment extends Fragment {

    private RecyclerView records_LST;
    private RecordList recordList;
    private final String KEY_RECORDS_SPM = "recordList";

    private Callback_ListItemClicked callbackListItemClicked;

    public ListFragment() {
        // Required empty public constructor
    }

    public void setCallbackListItemClicked(Callback_ListItemClicked callbackListItemClicked) {
        this.callbackListItemClicked = callbackListItemClicked;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_list, container, false);
        findViews(v);
        initListData();
        initViews();
        return v;
    }

    private void initViews() {
        RecordAdapter recordAdapter = new RecordAdapter(recordList.getRecordsTop10(), (lat, lon) -> {
            if (callbackListItemClicked != null) {
                callbackListItemClicked.listItemClicked(lat, lon);
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        records_LST.setLayoutManager(linearLayoutManager);
        records_LST.setAdapter(recordAdapter);
    }

    private void findViews(View v) {
        records_LST = v.findViewById(R.id.records_LST);
    }

    private void initListData() {
        Gson gson = new Gson();
        // Getting data
        String recordListAsJson = SharePreferencesManager
                .getInstance()
                .getString(KEY_RECORDS_SPM, "");
        recordList = gson.fromJson(recordListAsJson, RecordList.class);
        if (recordList == null) {
            recordList = new RecordList();
        }
    }
}