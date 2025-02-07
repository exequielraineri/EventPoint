package com.exeraineri.eventpoint.client.ui.home;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.exeraineri.eventpoint.client.databinding.FragmentHomeBinding;
import com.exeraineri.eventpoint.client.ui.adapter.EventAdapter;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    FragmentHomeBinding binding;
    InterestEventAdapter interestEventAdapter;
    EventAdapter eventAdapter;
    HomeViewModel homeViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        homeViewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
        return binding.getRoot();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUI();
        initListener();
        initObserver();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void initObserver() {
        homeViewModel.getInterestEvents().observe(getViewLifecycleOwner(), events -> {
            interestEventAdapter.setEvents(events);
        });

        homeViewModel.getProxEvents().observe(getViewLifecycleOwner(), events -> {
            eventAdapter.setEvents(events);
        });
    }

    private void initListener() {
    }

    private void initUI() {
        interestEventAdapter = new InterestEventAdapter(new ArrayList<>());
        eventAdapter = new EventAdapter(new ArrayList<>());

        binding.rvEvents.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.rvEvents.setAdapter(interestEventAdapter);

        binding.rvProxEvents.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvProxEvents.setAdapter(eventAdapter);

    }
}