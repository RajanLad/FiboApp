package fr.airweb.fiboapp;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

import fr.airweb.fiboapp.Adapters.FiboAdapter;
import fr.airweb.fiboapp.OperationalStuff.MathFunctions;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    ArrayList<String> fiboArrayList = new ArrayList<>();
    boolean isLoading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialize the recyclerView
        recyclerView = findViewById(R.id.fibo_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //populate initial data
        int n = 10;
        for(int i=1;i<=n;i++) {
            fiboArrayList.add(MathFunctions.fibos(i));
            Toast.makeText(this, ""+MathFunctions.fibos(i), Toast.LENGTH_SHORT).show();
        }

        // specify an adapter (see also next example)
        mAdapter = new FiboAdapter(fiboArrayList,this);
        recyclerView.setAdapter(mAdapter);

        initScrollListener();
    }

    private void initScrollListener() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                if (!isLoading) {
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == fiboArrayList.size() - 1) {
                        //bottom of list!
                        loadMore();
                        isLoading = true;
                    }
                }
            }
        });


    }

    private void loadMore() {
        fiboArrayList.add(null);
        mAdapter.notifyItemInserted(fiboArrayList.size() - 1);


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                fiboArrayList.remove(fiboArrayList.size() - 1);
                int scrollPosition = fiboArrayList.size();
                mAdapter.notifyItemRemoved(scrollPosition);
                int currentSize = scrollPosition;
                int nextLimit = currentSize + 10;

                while (currentSize - 1 < nextLimit) {
                    fiboArrayList.add(MathFunctions.fibos(currentSize));
                    currentSize++;
                }

                mAdapter.notifyDataSetChanged();
                isLoading = false;
            }
        }, 2000);


    }
}
