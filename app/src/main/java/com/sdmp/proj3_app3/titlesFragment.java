package com.sdmp.proj3_app3;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.ListFragment;

public class titlesFragment extends ListFragment
{
    static final String OLD_POSITION = "old_item" ;
    private ListSelectionListener listSeListener = null;
    private static final String TAG = "TitlesFragment";
    Integer mOldPosition = null ;
    public interface ListSelectionListener
    {
        void onListItemSelection(int index);
    }

    @Override
    public void onListItemClick(@NonNull ListView l, @NonNull View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        getListView().setItemChecked(position,true);
        listSeListener.onListItemSelection(position);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listSeListener = (ListSelectionListener) context;
        }
        catch(ClassCastException e)
        {
            throw new ClassCastException(context.toString()
                    + " must implement OnListItemSelection");
        }
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, getClass().getSimpleName() + ":entered onCreate()");
        super.onCreate(savedInstanceState);
    }

    // UB:  Notice that the superclass's method does an OK job of inflating the
    //      container layout.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(TAG, getClass().getSimpleName() + ":entered onCreateView()");
        if (savedInstanceState != null) {
            int oldPosition = savedInstanceState.getInt(OLD_POSITION) ;
            Log.i(TAG, "OLD_POSITION = " + oldPosition) ;
            mOldPosition = oldPosition ;
        }
        else {
            mOldPosition = null ;
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setListAdapter(new ArrayAdapter<String>(getActivity(), R.layout.phonename, MainActivity.phoneNames));

        getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
    }

    @Override
    public void onStart() {
        Log.i(TAG, getClass().getSimpleName() + ":entered onStart()");
        super.onStart();
        if (mOldPosition != null) {
            int oldPosition = mOldPosition ;
            getListView().setSelection(oldPosition) ;
            // Inform the QuoteViewerActivity that the item in position pos was selected
            listSeListener.onListItemSelection(oldPosition);
        }
    }

    @Override
    public void onResume() {
        Log.i(TAG, getClass().getSimpleName() + ":entered onResume()");
        super.onResume();
    }
}

