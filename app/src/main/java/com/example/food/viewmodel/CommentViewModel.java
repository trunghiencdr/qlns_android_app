package com.example.food.viewmodel;

import android.annotation.SuppressLint;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.food.Domain.Comment;
import com.example.food.network.repository.CommentRepository;

import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

public class CommentViewModel extends ViewModel {

    private CommentRepository commentRepository;
    private MutableLiveData<List<Comment>> comments;
    private MutableLiveData<Boolean> saveCommentSuccess;
    private MutableLiveData<Comment> commentOfOrder;


    public CommentViewModel(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
        comments = new MutableLiveData<>();
        commentOfOrder = new MutableLiveData<>();
        saveCommentSuccess = new MutableLiveData<>();
    }

    public MutableLiveData<List<Comment>> getComments() {
        return comments;
    }

    @SuppressLint("CheckResult")
    public void callGetCommentOfProudct(int productId) {
        commentRepository.getCommentsOfProduct(productId)
                .subscribeOn(Schedulers.io())
                .subscribe(response -> {
                    if (response.isSuccessful()) {
                        comments.postValue(response.body());
                    } else {
                        comments.postValue(null);
                    }
                });
    }

    @SuppressLint("CheckResult")
    public void callGetCommentOfOrder(int orderId) {
        commentRepository.getCommentsOfOrder(orderId)
                .subscribeOn(Schedulers.io())
                .subscribe(response -> {
                    if (response.isSuccessful()) {
                        commentOfOrder.postValue(response.body());
                    } else {
                        commentOfOrder.postValue(null);
                    }
                });
    }



    @SuppressLint("CheckResult")
    public void callSaveComment(Comment comment) {
        commentRepository.saveComment(comment)
                .subscribeOn(Schedulers.io())
                .subscribe(response -> {
            if (response.isSuccessful()) {
                saveCommentSuccess.postValue(true);
            } else {
                comments.postValue(null);
            }
        });
    }
}
