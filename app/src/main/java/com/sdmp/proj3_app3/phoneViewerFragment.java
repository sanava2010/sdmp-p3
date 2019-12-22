package com.sdmp.proj3_app3;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

public class phoneViewerFragment extends Fragment
{
    private ImageView phoneView = null;
    private int currentPos = -1;
    private int phoneArrLen;
    private static final String TAG = "PhoneViewerFragment";

    int getShownIndex() {
        return currentPos;
    }

    // Show the Quote string at position newIndex
    void showPhoneAtPos(int pos) {
        if (pos < 0 || pos>phoneArrLen)
            return;
        currentPos = pos;
        phoneView.setImageResource(MainActivity.phones[pos]);
        Log.i(TAG, getClass().getSimpleName() + ":entered showPhoneatpos"+pos);
    }

    @Override
    public void onAttach(Context activity) {
        Log.i(TAG, getClass().getSimpleName() + ":entered onAttach()");
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, getClass().getSimpleName() + ":entered onCreate()");
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Log.i(TAG, getClass().getSimpleName() + ":entered onActivityCreated()");
        phoneView= (ImageView) getActivity().findViewById(R.id.phoneImageView);
        phoneArrLen = MainActivity.phones.length;

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Toolbar myTB=getActivity().findViewById(R.id.mytb);
        //((AppCompatActivity)getActivity()).setSupportActionBar(myTB);

        return inflater.inflate(R.layout.phoneview,container,false);
    }
}
