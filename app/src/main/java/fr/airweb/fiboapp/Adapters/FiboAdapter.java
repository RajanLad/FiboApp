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

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        TextView fiboTextView;
        private MyViewHolder(View v) {
            super(v);
            this.fiboTextView = itemView.findViewById(R.id.fiboTextView);
        }
    }

    private class LoadingViewHolder extends RecyclerView.ViewHolder {

        ProgressBar progressBar;

        private LoadingViewHolder(View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressBar);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public FiboAdapter(ArrayList<String> myDataset, Context context) {
        mDataset = myDataset;
        mContext=context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                      int viewType) {
        // create a new view
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent, false);
            return new MyViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false);
            return new LoadingViewHolder(view);
        }
    }

    // Replace the contents of a view (invoked by the layout manager)
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

    }

    private void populateItemRows(MyViewHolder viewHolder, int position) {

        viewHolder.fiboTextView.setText(mDataset.get(position));

        //litte experiment
        float textSize=(mContext.getResources().getDimensionPixelSize(R.dimen.text_size_large)-position >1)?
                mContext.getResources().getDimensionPixelSize(R.dimen.text_size_large)-position :1.0f;


        viewHolder.fiboTextView.setTextSize(textSize);

    }
}