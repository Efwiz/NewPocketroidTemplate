package com.koushikdutta.async.http.server;

import com.koushikdutta.async.DataEmitter;
import com.koushikdutta.async.DataSink;
import com.koushikdutta.async.Util;
import com.koushikdutta.async.callback.CompletedCallback;
import com.koushikdutta.async.callback.DataCallback;
import com.koushikdutta.async.http.AsyncHttpRequest;
import com.koushikdutta.async.http.body.AsyncHttpRequestBody;

/* JADX INFO: loaded from: classes10.dex */
public class UnknownRequestBody implements AsyncHttpRequestBody<Void> {
    DataEmitter emitter;
    int length;
    private String mContentType;

    @Override // com.koushikdutta.async.http.body.AsyncHttpRequestBody
    public Void get() {
        return null;
    }

    @Override // com.koushikdutta.async.http.body.AsyncHttpRequestBody
    public boolean readFullyOnRequest() {
        return false;
    }

    public UnknownRequestBody(String str) {
        this.length = -1;
        this.mContentType = str;
    }

    public UnknownRequestBody(DataEmitter dataEmitter, String str, int i) {
        this.mContentType = str;
        this.emitter = dataEmitter;
        this.length = i;
    }

    @Override // com.koushikdutta.async.http.body.AsyncHttpRequestBody
    public void write(AsyncHttpRequest asyncHttpRequest, DataSink dataSink, CompletedCallback completedCallback) {
        Util.pump(this.emitter, dataSink, completedCallback);
        if (this.emitter.isPaused()) {
            this.emitter.resume();
        }
    }

    @Override // com.koushikdutta.async.http.body.AsyncHttpRequestBody
    public String getContentType() {
        return this.mContentType;
    }

    @Override // com.koushikdutta.async.http.body.AsyncHttpRequestBody
    public int length() {
        return this.length;
    }

    @Deprecated
    public void setCallbacks(DataCallback dataCallback, CompletedCallback completedCallback) {
        this.emitter.setEndCallback(completedCallback);
        this.emitter.setDataCallback(dataCallback);
    }

    public DataEmitter getEmitter() {
        return this.emitter;
    }

    @Override // com.koushikdutta.async.http.body.AsyncHttpRequestBody
    public void parse(DataEmitter dataEmitter, CompletedCallback completedCallback) {
        this.emitter = dataEmitter;
        dataEmitter.setEndCallback(completedCallback);
        dataEmitter.setDataCallback(new DataCallback.NullDataCallback());
    }
}
