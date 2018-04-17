package totabraz.com.socorromovel.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import totabraz.com.socorromovel.R;
import totabraz.com.socorromovel.domain.Smartphone;
import totabraz.com.socorromovel.utils.SysUtils;

/**
 * Created by totabraz on 14/04/18.
 */

public class MyPhoneAdapter extends RecyclerView.Adapter<MyPhoneAdapter.ViewHolder> {
    private ArrayList<Smartphone> smartphones;
    private Activity context;


    public MyPhoneAdapter(Activity context, ArrayList<Smartphone> smartphones) {
        this.context = context;
        this.smartphones = smartphones;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_my_phone, parent, false);
        return new ViewHolder(itemView, parent);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if (position == smartphones.size() - 1) {
            holder.viewBorderBottom.setVisibility(View.GONE);
        }
        if(smartphones.get(position).getStatus().equals(SysUtils.PHONE_STOLED)){
            holder.llCardArea.setBackgroundColor(context.getResources().getColor(R.color.statusStoled));
        }
        holder.tvImei.setText(smartphones.get(position).getImei());
        holder.tvImei.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(context, android.R.style.Theme_Material_Dialog_Alert);
                } else {
                    builder = new AlertDialog.Builder(context);
                }
                builder.setTitle("Deseja realmente adicionar como roubado?")
                        .setNegativeButton("Não", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child(SysUtils.FB_REPORTED_PHONES).child(mAuth.getCurrentUser().getUid()).child(smartphones.get(position).getImei());
                                mDatabase.setValue(smartphones.get(0).getImei());
                            }
                        })

                        .setIcon(R.drawable.ic_warning)
                        .show();
            }
        });
        holder.ivDeletePhone.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog.Builder builder;
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            builder = new AlertDialog.Builder(context, android.R.style.Theme_Material_Dialog_Alert);
                        } else {
                            builder = new AlertDialog.Builder(context);
                        }
                        builder.setTitle("Deseja realmente deletar?")
                                .setNegativeButton("Não", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                })
                                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        FirebaseAuth mAuth = FirebaseAuth.getInstance();
                                        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child(SysUtils.FB_USERS);
                                        mDatabase.child(mAuth.getCurrentUser().getUid()).child(SysUtils.FB_MY_PHONES).child(smartphones.get(position).getImei()).removeValue();
                                    }
                                })

                                .setIcon(R.drawable.ic_warning)
                                .show();

                        // mDatabase.childe(smartphones.get(position).getId()).setValue(null);

                    }
                }
        );
    }


    @Override
    public int getItemCount() {
        return smartphones.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        View viewBorderBottom;
        TextView tvImei;
        ImageView ivDeletePhone;
        LinearLayout llCardArea;

        public ViewHolder(View holder, ViewGroup parent) {
            super(holder);
            llCardArea = holder.findViewById(R.id.llCardArea);
            viewBorderBottom = holder.findViewById(R.id.borderBottom);
            tvImei = holder.findViewById(R.id.tvImei);
            ivDeletePhone = holder.findViewById(R.id.ivDeletePhone);
        }
    }

}
