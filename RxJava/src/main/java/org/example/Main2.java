package org.example;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;

import static java.lang.System.out;

public class Main2 {

    public static void main(String[] args){
        Observable<String> sub = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> emitter) throws Throwable {
                emitter.onNext("First");
                emitter.onNext("second");
                emitter.onError(new Exception("custom error"));
                emitter.onNext("third");
                emitter.onComplete();
            }
        });

        sub.doAfterNext(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Throwable {
                        out.println("this comes after every next");
                    }
                })
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        out.println("subscribed");
                    }

                    @Override
                    public void onNext(@NonNull String s) {
                        out.println("on Next, received: " + s);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        out.println(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        out.println("completed");
                    }
                });
    }
}
