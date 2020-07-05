package com.example.mapactivity;

import androidx.annotation.MainThread;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.location.GeofencingClient;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button button;
    EditText text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 권한 체크
        // TedPermission 사용
        // https://gun0912.tistory.com/61
        checkTedPermission();

        // 입력 버튼
        button = findViewById(R.id.button);
        // 입력 텍스트
        text = findViewById(R.id.textbox);

        // 지오코딩(GeoCoding) : 주소, 지명 => 위도, 경도 좌표로 변환
        final Geocoder geocoder = new Geocoder(this);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Address> list = null;

                // 장소 입력 후 버튼 클릭
                String str = text.getText().toString();

                try {
                    list = geocoder.getFromLocationName(str, 10);
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.d("location_info!!", "주소 변환 오류 발생");
                }

                // 리스트가 null이 아니면
                if (list != null) {
                    // list 사이즈가 0이면
                    if(list.size() == 0) {
                        Toast.makeText(MainActivity.this, "해당되는 주소 정보가 없습니다.", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        // Address 객체에 list에 저장된 값을 꺼냄
                        Address addr = list.get(0);

                        // 위도와 경도, 위치명을 저장
                        double latitude = addr.getLatitude();
                        double longitude = addr.getLongitude();
                        String title = addr.getAddressLine(0);

                        Log.d("location_info!!", title + " latitude : " + latitude + " longitude : " + longitude);

                        // 토스트 메시지 띄워주기
                        Toast.makeText(MainActivity.this, "latitude : " + latitude + "\n longitude : " + longitude, Toast.LENGTH_SHORT).show();

                        // 인텐트에 위도, 경도, 장소 이름 값을 담은 후 MapsActivity 실행
                        Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                        intent.putExtra("lat", latitude);
                        intent.putExtra("lon", longitude);
                        intent.putExtra("title", title);

                        startActivity(intent);
                    }
                }
            }
        });
    }

    private void checkTedPermission() {
        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                Toast.makeText(MainActivity.this, "Permission Granted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                Toast.makeText(MainActivity.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
            }
        };

        TedPermission.with(this)
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)
                .check();
    }
}