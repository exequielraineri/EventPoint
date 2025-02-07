package com.exeraineri.eventpoint.client.ui.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.exeraineri.eventpoint.client.R;
import com.exeraineri.eventpoint.client.databinding.ItemCardStringBinding;

import java.util.ArrayList;
import java.util.List;

public class StringAdapter extends RecyclerView.Adapter<StringAdapter.ViewHolder> {

    List<String> data = new ArrayList<>();

    public StringAdapter(List<String> data) {
        this.data = data;
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public StringAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.item_card_string,
                        parent,
                        false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull StringAdapter.ViewHolder holder, int position) {
        holder.bind(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemCardStringBinding binding;

        public ViewHolder(@NonNull ItemCardStringBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(String data) {
            binding.setData(data);
            binding.executePendingBindings();
        }
    }
}
