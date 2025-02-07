package com.exeraineri.eventpoint.client.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.exeraineri.eventpoint.client.R;
import com.exeraineri.eventpoint.client.databinding.FragmentLoginBinding;
import com.exeraineri.eventpoint.client.ui.MainActivity;


public class LoginFragment extends Fragment {

    private FragmentLoginBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initUI();
        initListener();
        initObserver();

    }

    private void initListener() {
        binding.tvRegister.setOnClickListener(v -> {
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.frameLayoutAuthentication, new RegisterFragment())
                    .commit();
        });

        binding.btnCloseAuth.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
            getActivity().finish();
        });
    }


    private void initUI() {
    }

    private void initObserver() {
    }
}