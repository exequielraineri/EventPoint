package com.exeraineri.eventpoint.client.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.exeraineri.eventpoint.client.R;
import com.exeraineri.eventpoint.client.databinding.ActivityAuthenticationBinding;
import com.exeraineri.eventpoint.client.ui.auth.LoginFragment;

public class AuthenticationActivity extends AppCompatActivity {

    ActivityAuthenticationBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAuthenticationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new LoginFragment());
    }


    private void replaceFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayoutAuthentication, fragment)
                .commit();
    }
}