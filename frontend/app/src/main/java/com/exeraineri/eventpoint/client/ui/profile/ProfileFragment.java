package com.exeraineri.eventpoint.client.ui.profile;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.exeraineri.eventpoint.client.databinding.FragmentProfileBinding;
import com.exeraineri.eventpoint.client.ui.AuthenticationActivity;

public class ProfileFragment extends Fragment {

    FragmentProfileBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initListener();

    }

    private void initListener() {
        binding.btnLogin.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), AuthenticationActivity.class);
            startActivity(intent);
            getActivity().finish();
        });
    }
}