package com.exeraineri.eventpoint.client.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.exeraineri.eventpoint.client.R;
import com.exeraineri.eventpoint.client.databinding.ItemCardEventBinding;
import com.exeraineri.eventpoint.client.domain.model.Event;

import java.util.ArrayList;
import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {

    private List<Event> events = new ArrayList<>();

    public EventAdapter(List<Event> events) {
        this.events = events;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public EventAdapter.EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_card_event, parent, false);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventAdapter.EventViewHolder holder, int position) {
        holder.bind(events.get(position));
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public class EventViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ItemCardEventBinding binding;

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemCardEventBinding.bind(itemView);
            itemView.setOnClickListener(this);
        }

        public void bind(Event event) {
            binding.setEvent(event);
            Glide.with(binding.getRoot())
                    .load(binding.getEvent().getImage())
                    .into(binding.ivEvent);
            binding.executePendingBindings();
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(v.getContext(), binding.getEvent().getTitle(), Toast.LENGTH_SHORT).show();
        }
    }
}
