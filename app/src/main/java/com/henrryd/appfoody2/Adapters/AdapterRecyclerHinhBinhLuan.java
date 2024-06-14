package com.henrryd.appfoody2.Adapters;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.henrryd.appfoody2.R;


import java.util.ArrayList;
import java.util.List;
public class AdapterRecyclerHinhBinhLuan extends RecyclerView.Adapter<AdapterRecyclerHinhBinhLuan.ViewHolderHinhBinhLuan> {
    Context context;
    int resource;
    List<Bitmap> bitmapList;
    List<Bitmap> listHinh;

    public AdapterRecyclerHinhBinhLuan(Context context, int layout, List<Bitmap> listHinh){
        this.context = context;
        this.resource = resource;
        this.listHinh = listHinh;
        bitmapList = new ArrayList<>();



        }
        public class ViewHolderHinhBinhLuan extends RecyclerView.ViewHolder{
                ImageView imageHinhBinhLuon;
                TextView txtSoHinhBinhLuan;
                FrameLayout khungSoHinhBinhLuan;
                public ViewHolderHinhBinhLuan (View itemView) {
                    super(itemView);

                    imageHinhBinhLuon = (ImageView) itemView.findViewById(R.id.imageBinhLuan);
                    txtSoHinhBinhLuan = (TextView) itemView.findViewById(R.id.txtSoHinhBinhLuan);
                    khungSoHinhBinhLuan = (FrameLayout) itemView.findViewById(R.id.khungSoHinhBinhLuan);
                }
        }
            @Override
            public AdapterRecyclerHinhBinhLuan.ViewHolderHinhBinhLuan onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);
                ViewHolderHinhBinhLuan viewHolderHinhBinhLuan = new ViewHolderHinhBinhLuan(view);

                return null;
            }
            @Override
            public void onBindViewHolder(AdapterRecyclerHinhBinhLuan.ViewHolderHinhBinhLuan holder, int position) {
                holder.imageHinhBinhLuon.setImageBitmap(listHinh.get(position));
                if(position == 3){
                    int sohinhconlai = listHinh.size()-4;
                    if(sohinhconlai > 0 ){
                        holder.khungSoHinhBinhLuan.setVisibility(View.VISIBLE);
                        holder.txtSoHinhBinhLuan.setText("+" + sohinhconlai);
                    }

                }
            }

            @Override
                public int getItemCount () {
                    return 4;
                }
}
