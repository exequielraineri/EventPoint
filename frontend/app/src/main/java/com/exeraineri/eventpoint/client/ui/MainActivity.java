package com.exeraineri.eventpoint.client.ui;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.exeraineri.eventpoint.client.R;
import com.exeraineri.eventpoint.client.databinding.ActivityMainBinding;
import com.exeraineri.eventpoint.client.ui.event.EventFragment;
import com.exeraineri.eventpoint.client.ui.home.HomeFragment;
import com.exeraineri.eventpoint.client.ui.profile.ProfileFragment;

@RequiresApi(api = Build.VERSION_CODES.O)
public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new HomeFragment());
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.home) {
                replaceFragment(new HomeFragment());
            } else if (itemId == R.id.events) {
                replaceFragment(new EventFragment());
            } else {
                replaceFragment(new ProfileFragment());
            }
            return true;
        });

    }

    private void replaceFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayout, fragment)
                .commit();
    }

}