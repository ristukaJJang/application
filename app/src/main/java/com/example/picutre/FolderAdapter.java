package com.example.picutre;

import static android.provider.MediaStore.MediaColumns.BUCKET_DISPLAY_NAME;
import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;

import java.io.File;
import java.util.List;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class FolderAdapter extends RecyclerView.Adapter<FolderAdapter.FolderViewHolder> {

    private List<FolderItem> folderItems;
    private Context context;

    public FolderAdapter(List<FolderItem> folderItems ) {
        this.folderItems = folderItems;
    }

    @NonNull
    @Override
    public FolderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.listview, parent, false);
        //FolderAdapter.FolderViewHolder viewHolder = new FolderAdapter.FolderViewHolder(view);

        return new FolderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FolderAdapter.FolderViewHolder holder, int position) {
        FolderItem folderItem = folderItems.get(position);
        holder.folderName.setText(folderItem.getFolderName());
        holder.count.setText(String.valueOf(folderItem.getCount()));
        Glide.with(context).load(folderItem.getFirstImagePath()).into(holder.folderImage);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, LoadingScreen.class); //로딩 스크린으로 화면 이동
            String folderPath = new File(folderItem.getFirstImagePath()).getParent(); // 이미지 경로에서 폴더 경로 추출
            intent.putExtra("folderPath", folderPath);
            Log.d(TAG, "folderPath : " +  folderPath);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return folderItems.size();
    }

    static class FolderViewHolder extends RecyclerView.ViewHolder {
        TextView folderName;
        ImageView folderImage;
        TextView count;

        public FolderViewHolder(@NonNull View itemView) {
            super(itemView);
            folderName = itemView.findViewById(R.id.name);
            folderImage = itemView.findViewById(R.id.imageview);
            count = itemView.findViewById(R.id.count);

        }


    }
}
