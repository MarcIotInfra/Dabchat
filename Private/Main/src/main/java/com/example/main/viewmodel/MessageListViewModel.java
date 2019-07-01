package com.example.main.viewmodel;

import com.example.main.interfaces.ILoginRepository;
import com.example.main.interfaces.IMessageRepository;
import com.example.main.model.Message;
import com.example.main.model.User;

import java.util.List;
import java.util.concurrent.ExecutorService;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MessageListViewModel extends ViewModel {
    private IMessageRepository messageRepository;
    private ILoginRepository loginRepository;
    private ExecutorService executor;
    private User currentUser;


    public MessageListViewModel(IMessageRepository messageRepository, ILoginRepository loginRepository, ExecutorService executor){
        this.messageRepository = messageRepository;
        this.loginRepository = loginRepository;
        this.executor = executor;
        executor.submit(()->{
            currentUser = loginRepository.getLoggedInUser();
        });

    }


    public LiveData<List<Message>> downloadMessages(){
        MutableLiveData<List<Message>> receivedListOfMessages = new MutableLiveData<>();
        executor.submit(() ->{
            Boolean bool = true;
            while(bool) {
                if (currentUser != null) {
                    messageRepository.receiveMessagesByUserID(currentUser.getId(), receivedListOfMessages);
                    bool = false;
                }
            }
        });

        return receivedListOfMessages;
    }

    @Override
    protected void onCleared() {
        closeDB();
        super.onCleared();
    }

    public void closeDB(){
        loginRepository.closeDB();
    }

}