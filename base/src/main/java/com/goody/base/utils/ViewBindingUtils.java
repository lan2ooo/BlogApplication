package com.goody.base.utils;


import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewbinding.ViewBinding;

import java.lang.reflect.Method;

/**
 * user：利用反射获取ViewBinding   -keep class  包名.databinding.* {*;}
 */
public class ViewBindingUtils {

    public static <Binding extends ViewBinding> Binding create(Class<?> clazz, LayoutInflater inflater) {
        return create(clazz, inflater, null);
    }

    public static <Binding extends ViewBinding> Binding create(Class<?> clazz, LayoutInflater inflater, ViewGroup root) {
        return create(clazz, inflater, root, false);
    }

    @SuppressWarnings("unchecked")
    @NonNull
    public static <Binding extends ViewBinding> Binding create(Class<?> clazz, LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        Class<?> bindingClass = ClassUtils.getViewBindingClass(clazz) ;
        Binding binding = null;
        if (bindingClass != null) {
            try {
                Method method = bindingClass.getMethod("inflate", LayoutInflater.class, ViewGroup.class, boolean.class);
                binding = (Binding) method.invoke(null, inflater, root, attachToRoot);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (binding==null) throw new NullPointerException(clazz.getName());
        return binding;
    }

}