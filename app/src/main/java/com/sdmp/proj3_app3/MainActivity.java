package com.sdmp.proj3_app3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements titlesFragment.ListSelectionListener {

    private static final String TOAST_ACTION = "edu.uic.cs478.BroadcastReceiver2.showToast";
    private static final String PERMISSION ="edu.uic.cs478.s19.kaboom";
    public static String[] phoneNames={"Samsung Galaxy Note10","Sony Xperia","Samsung Galaxy S8","Moto G7","Google Pixel","OnePlus 7","OnePlus T"};
    public static int[] phones={R.drawable.phoneh2,R.drawable.phoneh3,R.drawable.phoneh4,R.drawable.phoneh5,R.drawable.phoneh6,R.drawable.phoneh9,R.drawable.phoneh10};
    private FragmentManager fm;
    private FrameLayout namesFrame,phonesFrame,namesFrame1,phonesFrame1;
    private final phoneViewerFragment phoneFragment = new phoneViewerFragment();
    private titlesFragment namesFragment=null;
    private static final String TAG = "Activity";
    private static final int MATCH_PARENT = LinearLayout.LayoutParams.MATCH_PARENT;
    int currentPos=-1;
    static String OLD_ITEM = "old_item" ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newmainactivity);
        Toolbar myTB=findViewById(R.id.mytb);
        setSupportActionBar(myTB);
        getSupportActionBar().setIcon(R.drawable.uic_logo);
        //myTB.setLogo(R.drawable.images);

        Log.i(TAG, getClass().getSimpleName() + ":entered onCreate()");
        currentPos=-1;
        if(!(findViewById(R.id.activity_potrait) != null))
        {
            namesFrame=findViewById(R.id.name_fragment_container);
            phonesFrame=findViewById(R.id.name_fragment_container);
            fm=getSupportFragmentManager();
            FragmentTransaction ft=fm.beginTransaction();
            namesFragment=new titlesFragment();
            ft.replace(R.id.name_fragment_container,namesFragment);
            ft.commit();
        }
        else
        {
            namesFrame=findViewById(R.id.name_fragment_container);
            phonesFrame=findViewById(R.id.phone_fragment_container);
            fm=getSupportFragmentManager();
            FragmentTransaction ft=fm.beginTransaction();
            namesFragment=new titlesFragment();
            ft.replace(R.id.name_fragment_container,namesFragment);
            ft.commit();

        }
        if (savedInstanceState != null) {
            currentPos = savedInstanceState.getInt(OLD_ITEM) ;
            //Toast.makeText(this,"Current pos:"+currentPos,Toast.LENGTH_SHORT).show();
        }

        fm.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener(){
            @Override
            public void onBackStackChanged() {
                setLayout();
                Log.i("MainActivity BackStackChangedListner","BS changed landscape");

            }
        });
    }
    public void setLayout()
    {
        if(findViewById(R.id.activity_land)!=null)
        {
            namesFrame1=findViewById(R.id.name_fragment_container);
            phonesFrame1=findViewById(R.id.phone_fragment_container);
            if (!phoneFragment.isAdded()) {
                namesFrame1.setLayoutParams(new LinearLayout.LayoutParams(
                        MATCH_PARENT, MATCH_PARENT));
                phonesFrame1.setLayoutParams(new LinearLayout.LayoutParams(0,
                        MATCH_PARENT));
                Log.i("MainACtivity BackStackChangedListner","Phone fragment not added");
            } else {
                Log.i("MainActivity BackStackChangedListner", "Phone fragment added");
                namesFrame1.setLayoutParams(new LinearLayout.LayoutParams(0,
                        MATCH_PARENT, 1f));
                phonesFrame1.setLayoutParams(new LinearLayout.LayoutParams(0,MATCH_PARENT, 2f));

            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.i1:
                Log.i("On Menu Selected","Option 1 selected");
                Intent aIntent = new Intent(TOAST_ACTION) ;
                aIntent.putExtra("img",currentPos);
                sendOrderedBroadcast(aIntent, PERMISSION) ;
                return true;
            case R.id.i2:
                finishAndRemoveTask();
                return true;
            default: return super.onOptionsItemSelected(item);

        }

    }

    @Override
    public void onListItemSelection(int index)
    {
        Log.i(TAG, getClass().getSimpleName() + ":entered onItemSelection()");
        if(findViewById(R.id.activity_potrait)!=null)
        {
            if(!phoneFragment.isAdded())
            {
                FragmentTransaction ft2 = fm.beginTransaction();
                ft2.replace(R.id.name_fragment_container,phoneFragment);
                ft2.addToBackStack(null);
                ft2.commit();
                fm.executePendingTransactions();
            }
            if( phoneFragment.getShownIndex() != index)
            {
                if (currentPos!=index)
                {

                    phoneFragment.showPhoneAtPos(index);
                    currentPos=index;

                }
            }
            else
                phoneFragment.showPhoneAtPos(index);


        }
        else
        {

            //Landscape mode
            if(!phoneFragment.isAdded())
            {
                FragmentTransaction ft2 = fm.beginTransaction();
                ft2.replace(R.id.phone_fragment_container,phoneFragment);
                ft2.addToBackStack(null);
                ft2.commit();
                fm.executePendingTransactions();
            }
            if( phoneFragment.getShownIndex() != index)
            {
                if (currentPos!=index)
                {
                    //setLayout();
                    phoneFragment.showPhoneAtPos(index);
                    currentPos=index;
                }
            }
            else
                phoneFragment.showPhoneAtPos(index);

        }
    }

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState) ;
        Log.i(TAG, getClass().getSimpleName() + ":entered onStart()");
        if (currentPos >= 0) {
            outState.putInt(OLD_ITEM, currentPos) ;
        }
        else {
            outState.putInt(OLD_ITEM, -1 ) ;
        }
    }

    @Override
    protected void onStart() {
        Log.i(TAG, getClass().getSimpleName() + ":entered onStart()");
        super.onStart();
        if (currentPos >= 0) {
            Log.i(TAG, getClass().getSimpleName() + ":entered onStart() 1 with pos"+currentPos);

            Log.i(TAG, getClass().getSimpleName() + ":entered onStart() 2 with pos"+currentPos);
            namesFragment.setSelection(currentPos);
            namesFragment.getListView().setItemChecked(currentPos,true);
            if(findViewById(R.id.activity_potrait)!=null)
            {
                FragmentTransaction ft2 = fm.beginTransaction();
                ft2.replace(R.id.name_fragment_container,phoneFragment);
                ft2.addToBackStack(null);
                ft2.commit();
                fm.executePendingTransactions();

            }
            else
            {
                FragmentTransaction ft2 = fm.beginTransaction();
                ft2.replace(R.id.phone_fragment_container,phoneFragment);
                ft2.addToBackStack(null);
                ft2.commit();
                fm.executePendingTransactions();
            }
            phoneFragment.showPhoneAtPos(currentPos);

        }

    }
}
