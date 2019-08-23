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
        //initialiser le recyclerView
        recyclerView = findViewById(R.id.fibo_recycler_view);

        //this setting to improve performance
        // ce paramètre pour améliorer les performances
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        // utilise un linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //populate initial data
        //remplir les données initiales
        int n = 10;
        for(int i=1;i<=n;i++) {
            fiboArrayList.add(MathFunctions.fibos(i));
        }

        // specification of an Adapter
        //spécification d'un Adapter
        mAdapter = new FiboAdapter(fiboArrayList,this);
        recyclerView.setAdapter(mAdapter);

        //to understand scroll events, because of this we can populate the consecutive list in the recycler view
        // pour comprendre les événements de défilement, pour cette raison, nous pouvons renseigner la liste consécutive dans la recycler view
        initScrollListener();
    }

    private void initScrollListener() {

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            //here each time the recycler view is moved the scrollstate is changed and it will signal you a event notification. We don't need it at this moment
            // ici chaque fois que la vue du recycleur est déplacée, l'état de défilement est modifié et il vous signalera une notification d'événement. Nous n'en avons pas besoin en ce moment
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            //Here the implementation goes as such that, if the user scroll to the end of the list, the progress bar is displayed and next batch of elements is requested, in this case the next of fibonacci series
            // Ici, l'implémentation est telle que, si l'utilisateur fait défiler l'écran jusqu'à la fin de la liste, la barre de progression s'affiche et le prochain lot d'éléments est demandé, dans ce cas le suivant de la série fibonacci
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                //dx is horizontal scroll and dy is vertical scroll
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                //if is list is already loaded isLoading is false
                // si sa liste est déjà chargée isLoading is false
                if (!isLoading) {
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == fiboArrayList.size() - 1) {
                        //bottom of list!, so load more elements and set is loading true
                        // bas de la liste !, alors chargez plus d'éléments et set is loading true
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

        //Here computation may take time do a Handler Delay is added.
        // Ici, le calcul peut prendre du temps et Handler Delay est ajouté.
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                //on load that we need to remove the progress bar
                // en charge que nous devons supprimer la barre de progression
                fiboArrayList.remove(fiboArrayList.size() - 1);
                int scrollPosition = fiboArrayList.size();

                //notify adapter that the progress bar removed
                // informe l'adaptateur que la barre de progression a été supprimée
                mAdapter.notifyItemRemoved(scrollPosition);

                //scroll position
                int currentSize = scrollPosition;

                //next set/batch limit of values for fibonacci
                // prochain ensemble / limite de valeurs pour fibonacci
                int nextLimit = currentSize + 10;

                //parse through each value
                // analyser chaque valeur
                while (currentSize - 1 < nextLimit) {
                    fiboArrayList.add(MathFunctions.fibos(currentSize));
                    currentSize++;
                }

                //notify the adapter for the changes
                mAdapter.notifyDataSetChanged();
                isLoading = false;
            }
        }, 1000);


    }
}
