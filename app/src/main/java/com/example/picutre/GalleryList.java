package com.example.picutre;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class GalleryList extends AppCompatActivity {

    private static final int REQUEST_PERMISSION = 100;
    private RecyclerView recyclerView;
    private FolderAdapter adapter;
    private List<FolderItem> folderItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_gallery_list);

        recyclerView = findViewById(R.id.recylcerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        folderItems = new ArrayList<>();

        loadGalleryFolders();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void loadGalleryFolders() {
        ContentResolver contentResolver = getContentResolver();
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.DATE_TAKEN
        };
        //String orderBy = MediaStore.Images.Media.DATE_TAKEN + " DESC";

        Cursor cursor = contentResolver.query(uri, projection, null, null, null);
        if (cursor != null) {
            Map<String, Integer> folderCountMap = new HashMap<>();
            Map<String, String> folderMap = new LinkedHashMap<>();
            //Map<String, Long> folderDateMap = new HashMap<>();

            while (cursor.moveToNext()) {
                String folderName = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME));
                String firstImagePath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
                //long dateTaken = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_TAKEN));
                if (!folderMap.containsKey(folderName)) {
                    folderMap.put(folderName, firstImagePath);
                    //folderDateMap.put(folderName, dateTaken);
                }

                //각 폴더별 사진 수 세기
                if(folderCountMap.containsKey(folderName)) {
                    int count = folderCountMap.get(folderName);
                    folderCountMap.put(folderName, count+1);
                }else {
                    folderCountMap.put(folderName, 1);
                    folderMap.put(folderName, firstImagePath);
                }

            }
            cursor.close();

            //for (Map.Entry<String, String> entry : folderMap.entrySet()) {
            //    folderItems.add(new FolderItem(entry.getKey(), entry.getValue()));
            //}

            for(Map.Entry<String, Integer> entry : folderCountMap.entrySet()) {
                String folderName = entry.getKey();
                int count = entry.getValue();
                String firstImagePath = folderMap.get(folderName);
                folderItems.add(new FolderItem(folderName, firstImagePath, count));
            }

            adapter = new FolderAdapter(folderItems);
            recyclerView.setAdapter(adapter);
        }
    }
}