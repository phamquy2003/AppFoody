package com.henrryd.appfoody2.View.Fragments;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.henrryd.appfoody2.Controller.Interfaces.DialogListener;
import com.henrryd.appfoody2.Dialog.AlertDialogg;
import com.henrryd.appfoody2.LoginActivity;
import com.henrryd.appfoody2.MainActivity;
import com.henrryd.appfoody2.Model.ChiNhanhQuanAnModel;
import com.henrryd.appfoody2.Model.MonAnModel;
import com.henrryd.appfoody2.Model.Quan;
import com.henrryd.appfoody2.Model.QuanAnModel;
import com.henrryd.appfoody2.Model.ThemThucDonModel;
import com.henrryd.appfoody2.Model.ThucDonModel;
import com.henrryd.appfoody2.Model.TienIchModel;
import com.henrryd.appfoody2.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.henrryd.appfoody2.other.DataLocalManager;
import com.henrryd.appfoody2.other.MyApplication;

public class ThemQuanAnActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    final int RESULT_IMG1 = 111;
    final int RESULT_IMG2 = 112;
    final int RESULT_IMG3 = 113;
    final int RESULT_IMG4 = 114;
    final int RESULT_IMG5 = 115;
    final int RESULT_IMGTHUCDON = 116;

    String gioMoCua, gioDongCua,khuvuc;
    private Button btnGioMoCua;
    private Button btnGioDongCua;
    Button btnThemChiNhanh, btnThemQuanAn;
    Spinner spinnerKhuVuc;
    List<ThucDonModel> thucDonModelList;
    List<String> khuVucList, thucDonList;
    List<String> selectedTienIchList;
    List<String> chiNhanhList;
    List<ThemThucDonModel> themThucDonModelList;
    List<Bitmap> hinhDaChup;
    List<Uri> hinhQuanAn;
    ArrayAdapter<String> adapterKhuVuc;
    ImageView imgTam, imgHinhQuan1, imgHinhQuan2, imgHinhQuan3, imgHinhQuan4, imgHinhQuan5, btnLogoutAdmin;
    LinearLayout khungTienIch, khungChiNhanh, khungChuaChiNhanh, khungChuaThucDon;
    RadioGroup rdgTrangThai;
    EditText edTenQuanAn, edGiaToiDa, edGiaToiThieu;
    String maQuanAn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_themquanan);
        DataLocalManager.init(this);

        btnGioMoCua = findViewById(R.id.btnGioMoCua);
        btnGioDongCua = findViewById(R.id.btnGioDongCua);
        btnThemQuanAn = findViewById(R.id.btnThemQuanAn);

        spinnerKhuVuc = findViewById(R.id.spinnerKhuVuc);

        khungTienIch = findViewById(R.id.khungTienIch);
        khungChuaChiNhanh = findViewById(R.id.khungChuaChiNhanh);
        khungChuaThucDon = findViewById(R.id.khungChuaThucDon);
        imgHinhQuan1 = findViewById(R.id.imgHinhQuan1);
        imgHinhQuan2 = findViewById(R.id.imgHinhQuan2);
        imgHinhQuan3 = findViewById(R.id.imgHinhQuan3);
        imgHinhQuan4 = findViewById(R.id.imgHinhQuan4);
        imgHinhQuan5 = findViewById(R.id.imgHinhQuan5);
        rdgTrangThai = findViewById(R.id.rdgTrangThai);
        edTenQuanAn = findViewById(R.id.edTenQuanAn);
//        edGiaToiDa = findViewById(R.id.edGiaToiDa);
//        edGiaToiThieu = findViewById(R.id.edGiaToiThieu);
        btnLogoutAdmin = findViewById(R.id.btnLogoutAdmin);


        thucDonModelList = new ArrayList<>();
        khuVucList = new ArrayList<>();
        thucDonList = new ArrayList<>();
        selectedTienIchList = new ArrayList<>();
        chiNhanhList = new ArrayList<>();
        themThucDonModelList = new ArrayList<>();
        hinhDaChup = new ArrayList<>();
        hinhQuanAn = new ArrayList<>();


        CloneChiNhanh();
        CloneThucDon();

        LayDanhSachKhuVuc();
        LayDanhSachTienIch();


        adapterKhuVuc = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, khuVucList);
        spinnerKhuVuc.setAdapter(adapterKhuVuc);
        adapterKhuVuc.notifyDataSetChanged();


        btnGioMoCua.setOnClickListener(this);
        btnGioDongCua.setOnClickListener(this);
        btnThemQuanAn.setOnClickListener(this);
        spinnerKhuVuc.setOnItemSelectedListener(this);


        imgHinhQuan1.setOnClickListener(this);
        imgHinhQuan2.setOnClickListener(this);
        imgHinhQuan3.setOnClickListener(this);
        imgHinhQuan4.setOnClickListener(this);
        imgHinhQuan5.setOnClickListener(this);
        btnLogoutAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logOut();
            }
        });


    }
    private void logOut() {
        // Hiển thị dialog hỏi người dùng có muốn đăng xuất không
        AlertDialogg alertDialogg = new AlertDialogg(ThemQuanAnActivity.this,
                "Thông báo",
                "Bạn có chắc chắn muốn đăng xuất?",
                R.drawable.ic_logo_f2);
        alertDialogg.setDialogListener(new DialogListener() {
            @Override
            public void dialogPositive() {
                // Xử lý khi người dùng chọn đồng ý đăng xuất
                Log.d("MainActivity", "Logging out");
                DataLocalManager.remove_user();
                Intent it = new Intent(ThemQuanAnActivity.this, LoginActivity.class);
                MyApplication.User = null;
                startActivity(it);
                finish();
            }
        });
        alertDialogg.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case RESULT_IMG1:
                if (RESULT_OK == resultCode) {
                    Uri uri = data.getData();
                    imgHinhQuan1.setImageURI(uri);
                    hinhQuanAn.add(uri);
                }
                break;
            case RESULT_IMG2:
                if (RESULT_OK == resultCode) {
                    Uri uri = data.getData();
                    imgHinhQuan2.setImageURI(uri);
                    hinhQuanAn.add(uri);
                }
                break;
            case RESULT_IMG3:
                if (RESULT_OK == resultCode) {
                    Uri uri = data.getData();
                    imgHinhQuan3.setImageURI(uri);
                    hinhQuanAn.add(uri);
                }
                break;
            case RESULT_IMG4:
                if (RESULT_OK == resultCode) {
                    Uri uri = data.getData();
                    imgHinhQuan4.setImageURI(uri);
                    hinhQuanAn.add(uri);
                }
                break;
            case RESULT_IMG5:
                if (RESULT_OK == resultCode) {
                    Uri uri = data.getData();
                    imgHinhQuan5.setImageURI(uri);
                    hinhQuanAn.add(uri);
                }
                break;
            case RESULT_IMGTHUCDON:
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                imgTam.setImageBitmap(bitmap);
                hinhDaChup.add(bitmap);
                break;
        }


    }

    private void LayDanhSachThucDon(final ArrayAdapter<String> adapterThucDon) {
        FirebaseDatabase.getInstance().getReference().child("thucdons").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ThucDonModel thucDonModel = new ThucDonModel();
                    String key = snapshot.getKey();
                    String value = snapshot.getValue(String.class);

                    thucDonModel.setTenthucdon(value);
                    thucDonModel.setMathucdon(key);

                    thucDonModelList.add(thucDonModel);
                    thucDonList.add(value);
                }
                adapterThucDon.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void LayDanhSachKhuVuc() {
        FirebaseDatabase.getInstance().getReference().child("khuvucs").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String tenKhuVuc = snapshot.getKey();
                    khuVucList.add(tenKhuVuc);
                }
                adapterKhuVuc.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void LayDanhSachTienIch() {
        FirebaseDatabase.getInstance().getReference().child("quanlytienichs").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String maTienIch = snapshot.getKey();
                    TienIchModel tienIchModel = snapshot.getValue(TienIchModel.class);
                    tienIchModel.setMaTienIch(maTienIch);

                    CheckBox checkBox = new CheckBox(ThemQuanAnActivity.this);
                    checkBox.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            String maTienIch = buttonView.getTag().toString();
                            if (isChecked) {
                                selectedTienIchList.add(maTienIch);

                            } else {
                                selectedTienIchList.remove(maTienIch);
                            }
                        }
                    });
                    checkBox.setText(tienIchModel.getTentienich());
                    checkBox.setTag(maTienIch);
                    khungTienIch.addView(checkBox);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void CloneChiNhanh() {
        final View view = LayoutInflater.from(ThemQuanAnActivity.this).inflate(R.layout.layout_clone_chinhanh, null);
        Button btnThemChiNhanh = view.findViewById(R.id.btnThemChiNhanh);
        final Button btnXoaChiNhanh = view.findViewById(R.id.btnXoaChiNhanh);

        btnThemChiNhanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText edTenChiNhanh = view.findViewById(R.id.edTenChiNhanh);
                String tenChiNhanh = edTenChiNhanh.getText().toString();

                v.setVisibility(View.GONE);
                btnXoaChiNhanh.setVisibility(View.VISIBLE);
                btnXoaChiNhanh.setTag(tenChiNhanh);


                chiNhanhList.add(tenChiNhanh);

//                view.setVisibility(View.GONE);
//                btnXoaChiNhanh.setVisibility(View.VISIBLE);
//
//                chiNhanhList.add(edTenChiNhanh.getText().toString());
                CloneChiNhanh();
            }
        });
        btnXoaChiNhanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenChiNhanh = v.getTag().toString();
                chiNhanhList.remove(tenChiNhanh);
                khungChuaChiNhanh.removeView(view);
            }
        });


        khungChuaChiNhanh.addView(view);
    }

    private void CloneThucDon() {
        View view = LayoutInflater.from(ThemQuanAnActivity.this).inflate(R.layout.layout_clone_thucdon, null);
        final Spinner spinnerThucDon = view.findViewById(R.id.spinnerThucDon);
        Button btnThemThucDon = view.findViewById(R.id.btnThemThucDon);
        EditText edTenMon = view.findViewById(R.id.edTenMon);
        EditText edGiaTien = view.findViewById(R.id.edGiaTien);
        ImageView imageChupHinh = view.findViewById(R.id.imgChupHinh);
        imgTam = imageChupHinh;

        ArrayAdapter<String> adapterThucDon = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, thucDonList);
        spinnerThucDon.setAdapter(adapterThucDon);
        adapterThucDon.notifyDataSetChanged();
        if (thucDonList.size() == 0) {
            LayDanhSachThucDon(adapterThucDon);

        }
        imageChupHinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, RESULT_IMGTHUCDON);
            }
        });


        btnThemThucDon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setVisibility(View.GONE);
                long thoigian = Calendar.getInstance().getTimeInMillis();
                String tenhinh = String.valueOf(thoigian) + "jpg";
                int position = spinnerThucDon.getSelectedItemPosition();
                String maThucDon = thucDonModelList.get(position).getMathucdon();

                MonAnModel monAnModel = new MonAnModel();
                monAnModel.setTenmon(edTenMon.getText().toString());
                monAnModel.setGiatien(Long.parseLong(edGiaTien.getText().toString()));
                monAnModel.setHinhanh(tenhinh);

                ThemThucDonModel themThucDonModel = new ThemThucDonModel();
                themThucDonModel.setMathucdon(maThucDon);
                themThucDonModel.setMonAnModel(monAnModel);

                themThucDonModelList.add(themThucDonModel);
                CloneThucDon();
            }
        });


        khungChuaThucDon.addView(view);
    }

    @Override
    public void onClick(final View v) {
        Calendar calendar = Calendar.getInstance();
        int gio = calendar.get(Calendar.HOUR_OF_DAY);
        int phut = calendar.get(Calendar.MINUTE);
        if (v.getId() == R.id.btnGioDongCua) {
            TimePickerDialog timePickerDialog = new TimePickerDialog(ThemQuanAnActivity.this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    gioDongCua = hourOfDay + ":" + minute;
                    ((Button) v).setText(gioDongCua);
                }
            }, gio, phut, true);
            timePickerDialog.show();
            // để lai nhu hoi truoc de het loi thu ik
        } else if (v.getId() == R.id.btnGioMoCua) {
            TimePickerDialog moCuatimePickerDialog = new TimePickerDialog(ThemQuanAnActivity.this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    gioMoCua = hourOfDay + ":" + minute;
                    ((Button) v).setText(gioMoCua);


                }
            }, gio, phut, true);
            moCuatimePickerDialog.show();
        } else if (v.getId() == R.id.imgHinhQuan1) {
            ChonHinhTuGallary(RESULT_IMG1);


        } else if (v.getId() == R.id.imgHinhQuan2) {
            ChonHinhTuGallary(RESULT_IMG2);


        } else if (v.getId() == R.id.imgHinhQuan3) {
            ChonHinhTuGallary(RESULT_IMG3);


        } else if (v.getId() == R.id.imgHinhQuan4) {
            ChonHinhTuGallary(RESULT_IMG4);


        } else if (v.getId() == R.id.imgHinhQuan5) {
            ChonHinhTuGallary(RESULT_IMG5);


        } else if (v.getId() == R.id.btnThemQuanAn) {
            ThemQuanAn();
        }
    }



    private void ThemQuanAn() {
        // Lấy giá trị từ các trường nhập liệu
        String tenQuanAn = edTenQuanAn.getText().toString().trim();


// Xác định trạng thái giao hàng
        boolean giaoHang;
        int idRadioSelected = rdgTrangThai.getCheckedRadioButtonId();
        if (idRadioSelected == R.id.edGiaoHang) {
            giaoHang = true;
        } else {
            giaoHang = false;
        }

// Tạo mã quán ăn mới
        // Tham chiếu đến nút "quanans" trong RTDB

        DatabaseReference nodeRoot = FirebaseDatabase.getInstance().getReference();
        DatabaseReference quanansRef = nodeRoot.child("quanans");
         maQuanAn = quanansRef.push().getKey()  ;

        nodeRoot.child("khuvucs").child(khuvuc).push().setValue(maQuanAn);
        for (String  chinhanh: chiNhanhList){
            String urlGaoCoding = "https://maps.googleapis.com/maps/api/geocode/json?address="+chinhanh.replace("","%20")+"&key=AIzaSyBVd2D3evAh1Ip_f5nuN1P6ad-14G3Ns0g";
            DownLoadToaDo downLoadToaDo = new DownLoadToaDo();
            downLoadToaDo.execute(urlGaoCoding);

        }


// Tạo một đối tượng QuanAnModel với dữ liệu mới
        Quan quanAnModel = new Quan();

        quanAnModel.setTenquanan(tenQuanAn);
        quanAnModel.setGiaohang(giaoHang);
        quanAnModel.setGiodongcua(gioDongCua);
        quanAnModel.setGiomocua(gioMoCua);
        quanAnModel.setTienich(selectedTienIchList);
        quanAnModel.setLuotthich(0L);

        QuanAnModel quanan = new QuanAnModel();

        quanan.setTenquanan(tenQuanAn);
        quanan.setGiaohang(giaoHang);
        quanan.setGiodongcua(gioDongCua);
        quanan.setGiomocua(gioMoCua);
        quanan.setTienich(selectedTienIchList);
        quanan.setLuotthich(0L);
// Thêm dữ liệu mới vào RTDB
        String newQuanAnKey = quanansRef.push().getKey();
        if (newQuanAnKey != null) {
            quanansRef.child(maQuanAn).setValue(quanAnModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(ThemQuanAnActivity.this, "Thêm quán ăn thành công", Toast.LENGTH_LONG).show();
                }
            });


        }


//       quanansRef.child(quanansRef.push().getKey()).setValue(quanAnModel).addOnSuccessListener(new OnSuccessListener<Void>() {
//           @Override
//           public void onSuccess(Void unused) {
//               Toast.makeText(ThemQuanAnActivity.this,"ok",Toast.LENGTH_LONG).show();
//           }
//
//
//       });
        // Lấy reference tới Firebase Firestore, Firebase Storage và Firebase Realtime Database
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        DatabaseReference realtimeRef = FirebaseDatabase.getInstance().getReference("hinhanhquanans").child(maQuanAn);


// Lặp qua mảng hinhQuanAn
        for (Uri hinhquan : hinhQuanAn) {
            // Thêm phần đuôi .jpg vào tên tệp
            String fileName = hinhquan.getLastPathSegment() + ".jpg";
            StorageReference storageRef = storage.getReference().child("hinhanh/" + fileName);

            storageRef.putFile(hinhquan)
                    .addOnSuccessListener(taskSnapshot -> storageRef.getDownloadUrl().addOnSuccessListener(downloadUri -> {
                        Map<String, Object> data = new HashMap<>();
                        data.put("imageUrl", downloadUri.toString());
                        data.put("maQuanAn", maQuanAn);

                        db.collection("hinhanh").add(data)
                                .addOnSuccessListener(documentReference -> realtimeRef.child(documentReference.getId()).setValue(fileName)
                                        .addOnSuccessListener(aVoid -> Log.d("FirebaseUpload", "Uploaded image: " + downloadUri.toString()))
                                        .addOnFailureListener(e -> Log.e("FirebaseUpload", "Error uploading image name: " + e.getMessage())))
                                .addOnFailureListener(e -> Log.e("FirebaseUpload", "Error uploading image: " + e.getMessage()));
                    }))
                    .addOnFailureListener(e -> Log.e("FirebaseUpload", "Error uploading image: " + e.getMessage()));
        }


        // Lặp qua danh sách các món ăn và lưu thông tin vào Realtime Database
        for (int i = 0; i < themThucDonModelList.size(); i++) {
            ThemThucDonModel themThucDon = themThucDonModelList.get(i);
            MonAnModel monAn = themThucDon.getMonAnModel();
            String maThucDon = themThucDon.getMathucdon();
            String hinhAnh = monAn.getHinhanh();

            nodeRoot.child("thucdonquanans").child(maQuanAn).child(maThucDon).push().setValue(monAn);

            Bitmap bitmap = hinhDaChup.get(i);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte[] data = baos.toByteArray();

            storage.getReference().child("hinhanh/" + hinhAnh).putBytes(data);
        }
//            @Override
//            public void onSuccess(Void unused) {
//                Toast.makeText(ThemQuanAnActivity.this, "Thêm quán ăn thành công", Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    private void ChonHinhTuGallary(int requestCode) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Chọn hình..."), requestCode);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.spinnerKhuVuc) {
            khuvuc = khuVucList.get(position);

        }


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    class DownLoadToaDo extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... strings) {
            StringBuilder stringBuilder = new StringBuilder();

            try {
                URL url = new URL(strings[0]) ;
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.connect();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader  = new BufferedReader(new InputStreamReader(inputStream));
                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line+"\n");
                }



            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return stringBuilder.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray results = jsonObject.getJSONArray("results");
                for (int i = 0; i<results.length();i++){
                    JSONObject object = results.getJSONObject(i);
                    JSONObject geometry = object.getJSONObject("geometry");
                    String address = object.getString("formatted_address");
                    JSONObject location = geometry.getJSONObject("location");

                    double latitude = (double) location.get("lat");
                    double longtitude = (double) location.get("lng");

                    ChiNhanhQuanAnModel chiNhanhQuanAnModel = new ChiNhanhQuanAnModel();
                    chiNhanhQuanAnModel.setDiachi(address);
                    chiNhanhQuanAnModel.setLatitude(latitude);
                    chiNhanhQuanAnModel.setLongitude(longtitude);

                    FirebaseDatabase.getInstance().getReference().child("chinhanhquanans").child(maQuanAn).push().setValue(chiNhanhQuanAnModel);

                }
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

        }
    }


}
