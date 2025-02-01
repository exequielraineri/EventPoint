package com.exeraineri.eventpoint.client.ui.activity;

import android.os.Bundle;
import android.view.Window;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;
import androidx.fragment.app.Fragment;

import com.exeraineri.eventpoint.client.R;
import com.exeraineri.eventpoint.client.databinding.ActivityMainBinding;
import com.exeraineri.eventpoint.client.ui.fragment.EventsFragment;
import com.exeraineri.eventpoint.client.ui.fragment.HomeFragment;
import com.exeraineri.eventpoint.client.ui.fragment.ProfileFragment;

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
            if (itemId== R.id.home){
                replaceFragment(new HomeFragment());
            } else if (itemId==R.id.events) {
                replaceFragment(new EventsFragment());
            }else {
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