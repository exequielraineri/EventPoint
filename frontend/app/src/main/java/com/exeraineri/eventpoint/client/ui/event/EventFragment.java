package com.exeraineri.eventpoint.client.ui.event;

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

import com.exeraineri.eventpoint.client.R;
import com.exeraineri.eventpoint.client.databinding.FragmentEventsBinding;
import com.exeraineri.eventpoint.client.domain.model.Category;
import com.exeraineri.eventpoint.client.ui.adapter.EventAdapter;
import com.exeraineri.eventpoint.client.ui.adapter.StringAdapter;

import java.util.ArrayList;
import java.util.stream.Collectors;

@RequiresApi(api = Build.VERSION_CODES.O)
public class EventFragment extends Fragment {

    FragmentEventsBinding binding;
    EventViewModel eventViewModel;
    EventAdapter eventAdapter;
    StringAdapter stringAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentEventsBinding.inflate(inflater, container, false);
        eventViewModel = new ViewModelProvider(requireActivity()).get(EventViewModel.class);
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

    private void initUI() {
        eventAdapter = new EventAdapter(new ArrayList<>());
        stringAdapter = new StringAdapter(new ArrayList<>());

        binding.rvEvents.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvEvents.setAdapter(eventAdapter);

        binding.rvCategories.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.rvCategories.setAdapter(stringAdapter);
    }

    private void initObserver() {
        eventViewModel.getAllEvents().observe(getViewLifecycleOwner(), events -> {
            eventAdapter.setEvents(events);
        });

        eventViewModel.getIsLoading().observe(getViewLifecycleOwner(), isLoading -> {
            binding.progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        });


        eventViewModel.getCategories().observe(getViewLifecycleOwner(), categories -> {
            stringAdapter.setData(categories.stream()
                    .map(Category::getName)
                    .collect(Collectors.toList()));
        });

    }

    private void initListener() {
        binding.btnAddEvent.setOnClickListener(v -> {
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.frameLayout, new AddEventFragment())
                    .commit();
        });
    }


}