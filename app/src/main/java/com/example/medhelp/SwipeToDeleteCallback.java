package com.example.medhelp;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medhelp.Adapter.CartAdapter;

public class SwipeToDeleteCallback extends ItemTouchHelper.Callback {
    private final CartAdapter adapter;
    public SwipeToDeleteCallback(CartAdapter adapter){
        this.adapter = adapter;
    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        return makeMovementFlags(0,ItemTouchHelper.RIGHT); // Allow right swipe only
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        int position = viewHolder.getOldPosition();
        adapter.deleteItem(position); // Implement a delete method in your CartAdapter
    }
}
