package fr.airweb.fiboapp.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import fr.airweb.fiboapp.R;

public class FiboAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<String> mDataset;
    private Context mContext;

    //Here there are 2 ViewHolders one for the regular fibonacci values and progressbar
    // Ici, il y a 2 ViewHolders: un pour les valeurs régulières de fibonacci et progressbar

    //Flags to check the View needed
    // Drapeaux pour vérifier la vue nécessaire

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder

    // Fournit une référence aux vues pour chaque élément de données
    // Les éléments de données complexes peuvent nécessiter plus d'une vue par élément, et
    // vous donnez accès à toutes les vues d'un élément de données dans un détenteur de vue

    //This viewholder is for regular fibonacci values
    // Ce viewholder concerne les valeurs de fibonacci habituelles.
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        TextView fiboTextView;
        private MyViewHolder(View v) {
            super(v);
            this.fiboTextView = itemView.findViewById(R.id.fiboTextView);
        }
    }

    //This viewholder is for progressbar
    // Ce viewholder est pour progressbar
    private class LoadingViewHolder extends RecyclerView.ViewHolder {

        ProgressBar progressBar;

        private LoadingViewHolder(View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressBar);
        }
    }

    // A suitable constructor (depending the kind of dataset) in this case ArrayList<String>
    // Un constructeur approprié (selon le type de jeu de données) dans ce cas, ArrayList <String>
    public FiboAdapter(ArrayList<String> myDataset, Context context) {
        mDataset = myDataset;
        mContext=context;
    }

    // Create new views (invoked by the layout manager)
    // Créer de nouvelles vues (invoquées par le gestionnaire de disposition)
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                      int viewType) {
        // create a new view for the fibonacci item if view is for simple items (We get this from getItemViewType()).
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent, false);
            return new MyViewHolder(view);
        }
        // create a new view for the progressbar item if view is for simple items (We get this from getItemViewType()).
        else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false);
            return new LoadingViewHolder(view);
        }
    }

    // Replace the contents of a view (invoked by the layout manager)
    // Remplacer le contenu d'une vue (appelée par le layout manager)
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        if (viewHolder instanceof MyViewHolder) {

            populateItemRows((MyViewHolder) viewHolder, position);
        } else if (viewHolder instanceof LoadingViewHolder) {
            showLoadingView((LoadingViewHolder) viewHolder, position);
        }

    }

    // Return the size of your dataset (invoked by the layout manager)
    // Retourne la taille de votre jeu de données (appelé par le layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    @Override
    public int getItemViewType(int position) {

        return mDataset.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    private void showLoadingView(LoadingViewHolder viewHolder, int position) {
        //ProgressBar would be displayed
        //no need to any thing else

        // ProgressBar serait affiché
        // pas besoin d'autre chose
    }

    //populate
    private void populateItemRows(MyViewHolder viewHolder, int position) {

        viewHolder.fiboTextView.setText(mDataset.get(position));

        //litte experiment to reduce the text size based on downwards scroll
        // petite expérience pour réduire la taille du texte en fonction du défilement vers le bas
        float textSize=(mContext.getResources().getDimensionPixelSize(R.dimen.text_size_large)-position >1)?
                mContext.getResources().getDimensionPixelSize(R.dimen.text_size_large)-position :1.0f;

        //And then finally set the text
        viewHolder.fiboTextView.setTextSize(textSize);

    }
}