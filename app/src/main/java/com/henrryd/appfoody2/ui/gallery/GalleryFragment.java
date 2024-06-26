package com.henrryd.appfoody2.ui.gallery;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.henrryd.appfoody2.R;

import java.util.HashMap;
import java.util.Map;

public class GalleryFragment extends Fragment {

    EditText giomocua, giodongcua, luotthich, hinhquanan, tenquanan;
    RadioGroup radioGroupGiaoHang;
    Button btnThemQuanAn;

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gallery, container, false);

        giomocua = view.findViewById(R.id.edGioMoCua);
        giodongcua = view.findViewById(R.id.edGioDongCua);
        luotthich = view.findViewById(R.id.edLuotThich);
        hinhquanan = view.findViewById(R.id.edHinhQuanAn);
        tenquanan = view.findViewById(R.id.edTenQuanAn); // Thêm input cho tên quán ăn
        radioGroupGiaoHang = view.findViewById(R.id.rbgGiaoHang);
        btnThemQuanAn = view.findViewById(R.id.btnThemQuanAn);

        btnThemQuanAn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertData();
            }
        });

        return view;
    }

    private void insertData() {
        final String imageUrl = hinhquanan.getText().toString(); // Lấy URL hình ảnh từ EditText

        // Lấy giá trị của RadioButton Giao hàng
        boolean isGiaohang = false;
        int selectedId = radioGroupGiaoHang.getCheckedRadioButtonId();
        if (selectedId != -1) {
            RadioButton radioButton = getView().findViewById(selectedId);
            if (radioButton.getText().toString().equalsIgnoreCase("true")) {
                isGiaohang = true;
            }
        }

        String luotThichStr = luotthich.getText().toString();
        Long luotThichLong = 0L; // Giá trị mặc định

        try {
            luotThichLong = Long.parseLong(luotThichStr);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            // Xử lý khi không thể chuyển đổi thành công, ví dụ như thông báo lỗi
        }

        Map<String, Object> map = new HashMap<>();
        map.put("giaohang", isGiaohang);
        map.put("giomocua", giomocua.getText().toString());
        map.put("giodongcua", giodongcua.getText().toString());
        map.put("luotthich", luotThichLong);
        map.put("tenquanan", tenquanan.getText().toString()); // Thêm tên quán ăn

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("quanans");
        String key = databaseReference.push().getKey();

        databaseReference.child(key).setValue(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        // Thêm liên kết hình ảnh vào nút con của quán ăn
                        addImageLink(key, imageUrl); // Truyền imageUrl động
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "Thêm quán ăn thất bại!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void addImageLink(String key, String imageUrl) {
        DatabaseReference imageReference = FirebaseDatabase.getInstance().getReference().child("hinhanhquanans").child(key);
        Map<String, Object> imageMap = new HashMap<>();
        imageMap.put("image", imageUrl);

        imageReference.setValue(imageMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getContext(), "Thêm liên kết hình ảnh thành công!", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "Thêm liên kết hình ảnh thất bại!", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
