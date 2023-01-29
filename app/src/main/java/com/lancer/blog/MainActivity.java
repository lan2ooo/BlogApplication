package com.lancer.blog;

import com.goody.base.activity.BaseActivity;
import com.goody.base.utils.BaseUtils;
import com.lancer.blog.databinding.ActivityMainBinding;
import com.lancer.blog.library.PermissionXActivity;

public class MainActivity extends BaseActivity<ActivityMainBinding> {
    @Override
    protected void initActivity() {
        binding.PermissionX.setOnClickListener(v -> BaseUtils.startActivity(context, PermissionXActivity.class));
    }
}