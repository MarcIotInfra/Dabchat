package com.example.main.di.Module;


import com.example.main.MainActivity;
import com.example.common.Common.di.Scopes.MainActivityScope;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class AppActivityModule {
    @ContributesAndroidInjector(modules = {MainActivityModule.class, FragmentModule.class})
    @MainActivityScope
    abstract MainActivity bindMainActivity();

}