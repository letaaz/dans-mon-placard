package com.sem.lamoot.elati.danstonplacard.danstonplacard.view.fragment.ldc;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.sem.lamoot.elati.danstonplacard.danstonplacard.R;
import com.sem.lamoot.elati.danstonplacard.danstonplacard.database.model.ListeCoursesDefaut;

public class LDCFragment extends Fragment implements LDCAdapter.OnItemClickListener {

    public static String ARGS = "ARGUMENTS_LDC_FRAG";
    public static Integer NEW_LDC = 0x999999;
    private Context mContext;
    private RecyclerView ldcDisponiblesRecyclerView, historyLdcRecyclerView;
    private ImageButton btn_hide_show_ldc, btn_hide_show_history;
    private FloatingActionButton btn_create_ldc_fab;

    public static Fragment newInstance(String params) {
        Bundle args = new Bundle();
        args.putString(ARGS, params);
        LDCFragment ldcFragment = new LDCFragment();
        ldcFragment.setArguments(args);
        return ldcFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this.getContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ldc_fragment, container, false);

        // ViewModel : retrieve from DB

        // RecyclerView LDC
        ldcDisponiblesRecyclerView = view.findViewById(R.id.ldc_list_recyclerview);
        LDCAdapter ldcAdapter = new LDCAdapter(mContext, this);
        ldcAdapter.setData(LDCAdapter.generateLDC());
        ldcDisponiblesRecyclerView.setItemAnimator(new DefaultItemAnimator());
        ldcDisponiblesRecyclerView.setAdapter(ldcAdapter);

        RecyclerView.LayoutManager ldcLayoutManager = new LinearLayoutManager(getActivity());
        ldcDisponiblesRecyclerView.setLayoutManager(ldcLayoutManager);
        ldcDisponiblesRecyclerView.setNestedScrollingEnabled(false);

        // RecyclerView hist LDC
        historyLdcRecyclerView = view.findViewById(R.id.history_ldc_recyclerview);
        LDCAdapter historyAdapter = new LDCAdapter(mContext, this);
        historyAdapter.setData(LDCAdapter.generateLDC2());
        historyLdcRecyclerView.setItemAnimator(new DefaultItemAnimator());
        historyLdcRecyclerView.setAdapter(historyAdapter);

        RecyclerView.LayoutManager historyLayoutManager = new LinearLayoutManager(getActivity());
        historyLdcRecyclerView.setLayoutManager(historyLayoutManager);
        historyLdcRecyclerView.setNestedScrollingEnabled(false);

        btn_create_ldc_fab = view.findViewById(R.id.ajout_ldc_fab);
        btn_create_ldc_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Launch the view for adding a product to the current piece
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.root_ldc_frame, LDCEditFragment.newInstance(NEW_LDC));
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        // Hide/Show buttons
        btn_hide_show_ldc = (ImageButton) view.findViewById(R.id.btn_expand_ldc_list);
        btn_hide_show_history = view.findViewById(R.id.btn_expand_history_list);

        RelativeLayout sectionLdc = view.findViewById(R.id.section_ldc_list);
        sectionLdc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ldcDisponiblesRecyclerView.getVisibility() == View.GONE) {
                    btn_hide_show_ldc.setImageDrawable(mContext.getResources().getDrawable(R.drawable.expand_arrow_gray));
                    ldcDisponiblesRecyclerView.setVisibility(View.VISIBLE);
                } else { // Assuming the view is visible
                    btn_hide_show_ldc.setImageDrawable(mContext.getResources().getDrawable(R.drawable.collapse_arrow_gray));
                    ldcDisponiblesRecyclerView.setVisibility(View.GONE);
                }
            }
        });

        RelativeLayout sectionHistory = view.findViewById(R.id.section_history_list);
        sectionHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (historyLdcRecyclerView.getVisibility() == View.GONE) {
                    btn_hide_show_history.setImageDrawable(mContext.getResources().getDrawable(R.drawable.expand_arrow_gray));
                    historyLdcRecyclerView.setVisibility(View.VISIBLE);
                } else { // Assuming the view is visible
                    btn_hide_show_history.setImageDrawable(mContext.getResources().getDrawable(R.drawable.collapse_arrow_gray));
                    historyLdcRecyclerView.setVisibility(View.GONE);
                }
            }
        });
        return view;
    }

    @Override
    public void onItemClickListener(ListeCoursesDefaut ldcDefaut) {
        // Launch the view for product's detail
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.root_ldc_frame, DetailLDCFragment.newInstance(ldcDefaut.getId()));
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}