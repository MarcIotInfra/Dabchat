package com.example.main.ui;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import dagger.android.support.AndroidSupportInjection;
import dagger.android.support.DaggerFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.main.R;
import com.example.main.interfaces.LoginContract;
import com.example.main.interfaces.MainActivityController;
import com.example.main.model.NetworkResponse;
import com.example.main.model.User;

import javax.inject.Inject;


public class LoginFragment extends DaggerFragment {

    private NavController navController;

    @Inject
    LoginContract.iLoginViewModel viewModel;
    @Inject
    MainActivityController mainActivityController;

    private EditText username;
    private EditText password;
    private Button login;
    private Button register;
    private ProgressBar pbar;


    public LoginFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.login_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        navController = Navigation.findNavController(getActivity(), R.id.main_nav);
        initUI();
        mainActivityController.hideNavBar();
        viewModel.getCurrentUser().observe(this, user -> {
            if(user != null) {
                enableButtonClick(true);
                stopInProgress();
                LoginRequestFinished();
            }
        });
        login.setOnClickListener(v -> {
            showInProgress();
            viewModel.Login(username.getText().toString(), password.getText().toString()).observe(getViewLifecycleOwner(), s -> {
                stopInProgress();
                if(s == NetworkResponse.SUCCESS){
                    displayToast(getString(R.string.LoginSuccess));
                } else if (s == NetworkResponse.FAIL){
                    displayToast(getString(R.string.LoginFail));
                } else {
                    displayToast(getString(R.string.ServiceUnavailable));
                }
            });
        });

        register.setOnClickListener(v -> navController.navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment()));
    }


    @Override
    public void onResume() {
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onResume();
    }


    private void initUI(){
        password = getView().findViewById(R.id.passwordLogin);
        username = getView().findViewById(R.id.usernameLogin);
        login = getView().findViewById(R.id.loginbutton);
        register = getView().findViewById(R.id.loginRegisterBut);
        pbar = getView().findViewById(R.id.loginProgress);
    }

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
    }

    public void displayToast(String message) {
        Toast.makeText(this.getContext(), message, Toast.LENGTH_SHORT).show();
    }

    public void enableButtonClick(boolean bool) {
        login.setEnabled(bool);
    }

    public void showInProgress() {
        pbar.setVisibility(ProgressBar.VISIBLE);
    }

    public void stopInProgress() {
        pbar.setVisibility(ProgressBar.INVISIBLE);
    }

    public void LoginRequestFinished() {
        navController.navigate(LoginFragmentDirections.actionLoginFragmentToUploadFragment());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
