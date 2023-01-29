package com.lancer.blog.library;

import android.Manifest;

import androidx.annotation.NonNull;

import com.goody.base.activity.BaseActivity;
import com.goody.base.utils.ToastUtils;
import com.lancer.blog.databinding.ActivityPermissionXactivityBinding;
import com.permissionx.guolindev.PermissionX;
import com.permissionx.guolindev.callback.ExplainReasonCallbackWithBeforeParam;
import com.permissionx.guolindev.callback.RequestCallback;
import com.permissionx.guolindev.request.ExplainScope;

import java.util.List;

public class PermissionXActivity extends BaseActivity<ActivityPermissionXactivityBinding> {

    @Override
    protected void initActivity() {
        binding.back.setOnClickListener(view -> finish());

        binding.camera.setOnClickListener(view -> PermissionX.init(this)
                .permissions(Manifest.permission.CAMERA)
                .request((allGranted, grantedList, deniedList) -> {
                    if (allGranted) {
                        ToastUtils.showToast(context,"成功获取" + grantedList + "权限");
                    } else {
                        ToastUtils.showToast(context,deniedList + "权限被拒绝");
                    }
                }));

        binding.audio.setOnClickListener(view -> PermissionX.init(this)
                .permissions(Manifest.permission.RECORD_AUDIO)
                .onExplainRequestReason((scope, deniedList, beforeRequest) -> scope.showRequestReasonDialog(deniedList, "即将申请的权限是程序必须依赖的权限", "我已明白","取消"))
                .explainReasonBeforeRequest()
                .request((allGranted, grantedList, deniedList) -> {
                    if (allGranted) {
                        ToastUtils.showToast(context,"成功获取" + grantedList + "权限");
                    } else {
                        ToastUtils.showToast(context,deniedList + "权限被拒绝");
                    }
                }));
    }
}