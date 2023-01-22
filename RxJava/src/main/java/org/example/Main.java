package org.example;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

import java.util.concurrent.TimeUnit;

import static java.lang.System.out;
public class Main {
    public static void main(String[] args) {
        Observable<String> observable = Observable.fromArray("This is a long sentence".split(" "));

        observable
                .delay(2000, TimeUnit.MILLISECONDS)
                .observeOn(Schedulers.io()) // this is where observable runs its operators.
                .subscribeOn(Schedulers.computation()) // this is where observer does subscribe related logic.
                .subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                out.println("on Subscribe" + Thread.currentThread().getName());
            }

            @Override
            public void onNext(@NonNull String s) {
                out.println("onNext, item: " +s + "thread name: " + Thread.currentThread().getName());
            }

            @Override
            public void onError(@NonNull Throwable e) {
                out.println("onError, item: " +Thread.currentThread().getName());
            }

            @Override
            public void onComplete() {
                out.println("onComplete, item: " +Thread.currentThread().getName());
            }
        });
        out.println("fist log: " + Thread.currentThread().getName());
        try {
            Thread.sleep(5_000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        out.println("Thread name: " + Thread.currentThread().getName());
    }
}