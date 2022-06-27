package com.example.newprojectsetup.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.newprojectsetup.Activity.MainActivity;
import com.example.newprojectsetup.Common.Common;
import com.example.newprojectsetup.Common.LoadingBar;
import com.example.newprojectsetup.R;
import com.example.newprojectsetup.Utli.ConnectivityReceiver;
import com.facebook.shimmer.ShimmerFrameLayout;

public class HomeFragment extends Fragment {
    ShimmerFrameLayout shimmerLayout;
    RelativeLayout rel_recycler, rel_shimmer;
    Handler handler;
    int limit = 3000;
    Common common;
    SwipeRefreshLayout swipe;
    LoadingBar loadingBar;


    public HomeFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initAllId(view);
//        ((MainActivity)getActivity()).setTitles( getString( R.string.app_name) );
        init_swipe_method();

        if (ConnectivityReceiver.isConnected())
        {
            rel_shimmer.setVisibility( View.VISIBLE );
            handler.postDelayed( new Runnable() {
                @Override
                public void run() {
                    if (ConnectivityReceiver.isConnected()) {
                        initLeadsRecycler();
                        initNewLeadRecycler();
                        rel_shimmer.setVisibility( View.GONE );
                        rel_recycler.setVisibility( View.VISIBLE );
                    } else {
                        common.No_internetdailoge();
                    }
                }
            }, limit );

        } else {
            common.No_internetdailoge();
        }

        return view;
    }


    private void initAllId(View view) {
        handler = new Handler();
        common = new Common(  getActivity()) ;
        loadingBar = new LoadingBar(getActivity());
        shimmerLayout = view.findViewById( R.id.shimmerLayout );
        rel_shimmer = view.findViewById( R.id.rel_shimmer );
        rel_recycler = view.findViewById( R.id.rel_recycler );
        swipe=view.findViewById(R.id.swipe);


    }
    private void init_swipe_method() {
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipe.setRefreshing(true);


                initLeadsRecycler();
                initNewLeadRecycler();

                swipe.setRefreshing(false);
            }
        });

        swipe.setProgressBackgroundColorSchemeResource(R.color.white);
        // Set refresh indicator color to red.
        int indicatorColorArr[] = {R.color.red, R.color.green, R.color.orange,R.color.cyan};
        swipe.setColorSchemeResources(indicatorColorArr);
    }
    private void initNewLeadRecycler() {

    }

    private void initLeadsRecycler() {




    }
}  //    @Override
//    public void onResume() {
//        super.onResume();
//        shimmerLayout.startShimmer();
//        rel_recycler.setVisibility( View.GONE );
//        rel_shimmer.setVisibility( View.VISIBLE );
//    }
//
//    @Override
//    public void onPause() {
//        shimmerLayout.stopShimmer();
//        super.onPause();
//        rel_recycler.setVisibility( View.VISIBLE );
//        rel_shimmer.setVisibility( View.GONE );
//    }
