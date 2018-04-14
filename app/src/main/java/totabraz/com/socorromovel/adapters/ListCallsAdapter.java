package totabraz.com.socorromovel.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import totabraz.com.socorromovel.R;
import totabraz.com.socorromovel.domain.EmergencyNumber;

/**
 * Created by totabraz on 12/04/18.
 */

public class ListCallsAdapter extends RecyclerView.Adapter<ListCallsAdapter.ViewHolder> {
    private ArrayList<EmergencyNumber> numbers;
    private Activity activity;

    public ListCallsAdapter(Activity activity, ArrayList<EmergencyNumber> numbers){
        this.numbers = numbers;

        this.activity = activity;

    }




    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_emergency_numbers, parent, false);
        return new ViewHolder(itemView, parent );
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if (position == numbers.size() - 1) {
            holder.viewBorderBottom.setVisibility(View.GONE);
        }
        holder.tvName.setText(numbers.get(position).getName());
        holder.tvNumber.setText(numbers.get(position).getPhone());
        holder.llCallArea.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.fromParts("tel", numbers.get(position).getPhone(), null));
                    activity.startActivity(intent);

            }
        });


    }

    @Override
    public int getItemCount() {
        return this.numbers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        View viewBorderBottom;
        TextView tvName;
        TextView tvNumber;
        LinearLayout llCallArea;
        public ViewHolder(View holder, ViewGroup parent) {
            super(holder);
            tvName = holder.findViewById(R.id.tvName);
            tvNumber = holder.findViewById(R.id.tvNumber);
            viewBorderBottom = holder.findViewById(R.id.borderBottom);
            llCallArea = holder.findViewById(R.id.llCallArea);

        }
    }
}
