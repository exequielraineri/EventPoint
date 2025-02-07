package com.exeraineri.eventpoint.client.ui.event;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.exeraineri.eventpoint.client.data.service.ICategoryService;
import com.exeraineri.eventpoint.client.data.service.IEventService;
import com.exeraineri.eventpoint.client.data.service.implementation.CategoryServiceImpl;
import com.exeraineri.eventpoint.client.data.service.implementation.EventServiceImpl;
import com.exeraineri.eventpoint.client.domain.model.Category;
import com.exeraineri.eventpoint.client.domain.model.Event;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RequiresApi(api = Build.VERSION_CODES.O)
public class EventViewModel extends ViewModel {

    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);
    private MutableLiveData<List<Event>> events = new MutableLiveData<>();
    private MutableLiveData<List<Category>> categories = new MutableLiveData<>();
    private final IEventService eventService = new EventServiceImpl();
    private final ICategoryService categoryService = new CategoryServiceImpl();
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();


    public EventViewModel() {
        loadEvents();
        loadCategories();
    }

    private void loadCategories() {
        executorService.execute(() -> {
            List<Category> categoriesList = categoryService.getAllCategories();
            categories.postValue(categoriesList);
        });
    }

    private void loadEvents() {
        isLoading.postValue(true);

        executorService.execute(() -> {
            List<Event> eventsList = eventService.getAllEvents();
            events.postValue(eventsList);
            isLoading.postValue(false);
        });

    }

    public LiveData<List<Event>> getAllEvents() {
        return events;
    }


    public MutableLiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public LiveData<List<Category>> getCategories() {
        return categories;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        executorService.shutdown();
    }
}
