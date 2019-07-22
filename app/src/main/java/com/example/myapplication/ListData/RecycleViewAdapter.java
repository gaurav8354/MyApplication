package com.example.myapplication.ListData;
import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.myapplication.Database.SqlHelper;
import com.example.myapplication.R;
import java.util.ArrayList;
public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewViewHolder>{
    ArrayList<ArrayListLoad> ar;
    LayoutInflater layoutInflater;
    Context mcx;

    public RecycleViewAdapter(Context context, ArrayList<ArrayListLoad> arrayListLoad) {
       // this.layoutInflater = layoutInflater;
        this.ar=arrayListLoad;
        this.mcx=context;
    }

    @NonNull
    @Override
    public RecycleViewViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
           View view= LayoutInflater.from(mcx).inflate(R.layout.recyclelist,viewGroup,false);
           RecycleViewViewHolder recycleViewViewHolder=new RecycleViewViewHolder(view);
        return recycleViewViewHolder;
    }



    @Override
    public void onBindViewHolder(@NonNull RecycleViewViewHolder recycleViewViewHolder, int i) {
        recycleViewViewHolder.tv_no.setText(ar.get(i).no);
        recycleViewViewHolder.tv_name.setText(ar.get(i).name);
        recycleViewViewHolder.tv_email.setText(ar.get(i).email);
        recycleViewViewHolder.tv_pass.setText(ar.get(i).password);

    }

    @Override
    public int getItemCount() {
       return ar.size();
    }
}
