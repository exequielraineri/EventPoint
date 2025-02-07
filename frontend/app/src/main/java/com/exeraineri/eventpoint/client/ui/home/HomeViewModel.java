package com.exeraineri.eventpoint.client.ui.home;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.exeraineri.eventpoint.client.data.service.IEventService;
import com.exeraineri.eventpoint.client.data.service.implementation.EventServiceImpl;
import com.exeraineri.eventpoint.client.domain.model.Event;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RequiresApi(api = Build.VERSION_CODES.O)
public class HomeViewModel extends ViewModel {
    private MutableLiveData<List<Event>> interestEvents = new MutableLiveData<>();
    private MutableLiveData<List<Event>> proxEvents = new MutableLiveData<>();

    private ExecutorService executorService = Executors.newSingleThreadExecutor();
    private IEventService eventService = new EventServiceImpl();

    public HomeViewModel() {
        loadInterestEvents();
        loadProxEvents();
    }

    private void loadProxEvents() {
        executorService.execute(() -> {
            List<Event> eventsList = eventService.getAllEvents();
            proxEvents.postValue(eventsList);
        });
    }

    private void loadInterestEvents() {
        executorService.execute(() -> {
            List<Event> eventsList = eventService.getAllEvents();
            interestEvents.postValue(eventsList);
        });
    }

    public MutableLiveData<List<Event>> getInterestEvents() {
        return interestEvents;
    }

    public MutableLiveData<List<Event>> getProxEvents() {
        return proxEvents;
    }
}
