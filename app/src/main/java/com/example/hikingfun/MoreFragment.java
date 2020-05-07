package com.example.hikingfun;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import static android.content.Context.MODE_PRIVATE;


public class MoreFragment extends Fragment {
    private Switch mSwitchLight;
    private Switch mSwitchPowerSaving;
    private TextView mTvAbout;

    CameraManager mCameraManager;

    public interface CallBackDarkMode{
        public void SendDarkStatus(boolean darkOrLight);
    }
    CallBackDarkMode callBackDarkMode;
    @Override
    public void onAttach(Activity activity) {
        // TODO Auto-generated method stub
        super.onAttach(activity);
        //当前fragment从activity重写了回调接口  得到接口的实例化对象
        callBackDarkMode =(CallBackDarkMode) getActivity();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_more, container, false);

        mSwitchLight = (Switch) view.findViewById(R.id.switch_light);
        mSwitchPowerSaving = (Switch) view.findViewById(R.id.switch_saving);
        mTvAbout = (TextView) view.findViewById(R.id.about);
        loadPreferences();
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, 0);
        }


        //obtain CameraManager
        mCameraManager = (CameraManager) getContext().getSystemService(Context.CAMERA_SERVICE);
        // Flash Light
        mSwitchLight.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                try {
                    //obtain camera ID
                    String[] ids = mCameraManager.getCameraIdList();
                    for (String id : ids) {
                        CameraCharacteristics c = mCameraManager.getCameraCharacteristics(id);
                        //whether contain flash light
                        Boolean flashAvailable = c.get(CameraCharacteristics.FLASH_INFO_AVAILABLE);
                        Integer lensFacing = c.get(CameraCharacteristics.LENS_FACING);
                        if (flashAvailable != null && flashAvailable
                                && lensFacing != null && lensFacing == CameraCharacteristics.LENS_FACING_BACK) {
                            //open or close flash light
                            mCameraManager.setTorchMode(id, isChecked);

                            if (isChecked)
                                Toast.makeText(getContext(), R.string.light_on, Toast.LENGTH_SHORT).show();
                            else
                                Toast.makeText(getContext(), R.string.light_off, Toast.LENGTH_SHORT).show();
                            break;
                        }
                    }
                } catch (CameraAccessException e) {
                    e.printStackTrace();
                }


            }
        });


        // dark mode
        mSwitchPowerSaving.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                callBackDarkMode.SendDarkStatus(isChecked);
                savePreferences(mSwitchLight.isChecked(), isChecked);
                if (isChecked)
                    Toast.makeText(getContext(), R.string.dark_on, Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getContext(), R.string.dark_off, Toast.LENGTH_SHORT).show();

            }
        });


        // About this app
        mTvAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getContext())
                        .setTitle(R.string.about)
                        .setMessage(R.string.about_msg)
                        .setPositiveButton(R.string.dialog_ok,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(
                                            DialogInterface dialoginterface, int i) {

                                    }
                                }).show();
            }
        });


        return view;
    }

    public void savePreferences(boolean f, boolean p) {
        SharedPreferences pref = getContext().getSharedPreferences("HikingFunMORE", MODE_PRIVATE);
        pref.edit().putBoolean("flash_light", f).commit();
        pref.edit().putBoolean("power_saving", p).commit();
    }

    public void loadPreferences() {
        SharedPreferences pref = getContext().getSharedPreferences("HikingFunMORE", MODE_PRIVATE);
        mSwitchLight.setChecked(pref.getBoolean("flash_light", false));
        mSwitchPowerSaving.setChecked(pref.getBoolean("power_saving", false));

    }
}
