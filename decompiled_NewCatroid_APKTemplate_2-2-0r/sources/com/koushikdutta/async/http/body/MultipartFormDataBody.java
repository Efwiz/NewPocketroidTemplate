package com.koushikdutta.async.http.body;

import com.beust.jcommander.Parameters;
import com.huawei.hms.framework.common.ContainerUtils;
import com.koushikdutta.async.ByteBufferList;
import com.koushikdutta.async.DataEmitter;
import com.koushikdutta.async.DataSink;
import com.koushikdutta.async.LineEmitter;
import com.koushikdutta.async.Util;
import com.koushikdutta.async.callback.CompletedCallback;
import com.koushikdutta.async.callback.ContinuationCallback;
import com.koushikdutta.async.callback.DataCallback;
import com.koushikdutta.async.future.Continuation;
import com.koushikdutta.async.http.AsyncHttpRequest;
import com.koushikdutta.async.http.Headers;
import com.koushikdutta.async.http.Multimap;
import com.koushikdutta.async.http.server.BoundaryEmitter;
import java.io.File;
import java.util.ArrayList;
import java.util.UUID;

/* JADX INFO: loaded from: classes10.dex */
public class MultipartFormDataBody extends BoundaryEmitter implements AsyncHttpRequestBody<Multimap> {
    public static final String CONTENT_TYPE = "multipart/form-data";
    String contentType = "multipart/form-data";
    Headers formData;
    ByteBufferList last;
    String lastName;
    LineEmitter liner;
    MultipartCallback mCallback;
    private ArrayList<Part> mParts;
    int totalToWrite;
    int written;

    public interface MultipartCallback {
        void onPart(Part part);
    }

    @Override // com.koushikdutta.async.http.body.AsyncHttpRequestBody
    public boolean readFullyOnRequest() {
        return false;
    }

    @Override // com.koushikdutta.async.http.body.AsyncHttpRequestBody
    public void parse(DataEmitter dataEmitter, CompletedCallback completedCallback) {
        setDataEmitter(dataEmitter);
        setEndCallback(completedCallback);
    }

    void handleLast() {
        if (this.last == null) {
            return;
        }
        if (this.formData == null) {
            this.formData = new Headers();
        }
        this.formData.add(this.lastName, this.last.peekString());
        this.lastName = null;
        this.last = null;
    }

    public String getField(String str) {
        Headers headers = this.formData;
        if (headers == null) {
            return null;
        }
        return headers.get(str);
    }

    @Override // com.koushikdutta.async.http.server.BoundaryEmitter
    protected void onBoundaryEnd() {
        super.onBoundaryEnd();
        handleLast();
    }

    @Override // com.koushikdutta.async.http.server.BoundaryEmitter
    protected void onBoundaryStart() {
        final Headers headers = new Headers();
        LineEmitter lineEmitter = new LineEmitter();
        this.liner = lineEmitter;
        lineEmitter.setLineCallback(new LineEmitter.StringCallback() { // from class: com.koushikdutta.async.http.body.MultipartFormDataBody.1
            @Override // com.koushikdutta.async.LineEmitter.StringCallback
            public void onStringAvailable(String str) {
                if (!"\r".equals(str)) {
                    headers.addLine(str);
                    return;
                }
                MultipartFormDataBody.this.handleLast();
                MultipartFormDataBody.this.liner = null;
                MultipartFormDataBody.this.setDataCallback(null);
                Part part = new Part(headers);
                if (MultipartFormDataBody.this.mCallback != null) {
                    MultipartFormDataBody.this.mCallback.onPart(part);
                }
                if (MultipartFormDataBody.this.getDataCallback() == null) {
                    if (part.isFile()) {
                        MultipartFormDataBody.this.setDataCallback(new DataCallback.NullDataCallback());
                        return;
                    }
                    MultipartFormDataBody.this.lastName = part.getName();
                    MultipartFormDataBody.this.last = new ByteBufferList();
                    MultipartFormDataBody.this.setDataCallback(new DataCallback() { // from class: com.koushikdutta.async.http.body.MultipartFormDataBody.1.1
                        @Override // com.koushikdutta.async.callback.DataCallback
                        public void onDataAvailable(DataEmitter dataEmitter, ByteBufferList byteBufferList) {
                            byteBufferList.get(MultipartFormDataBody.this.last);
                        }
                    });
                }
            }
        });
        setDataCallback(this.liner);
    }

    public MultipartFormDataBody(String[] strArr) {
        for (String str : strArr) {
            String[] strArrSplit = str.split(ContainerUtils.KEY_VALUE_DELIMITER);
            if (strArrSplit.length == 2 && "boundary".equals(strArrSplit[0])) {
                setBoundary(strArrSplit[1]);
                return;
            }
        }
        report(new Exception("No boundary found for multipart/form-data"));
    }

    public void setMultipartCallback(MultipartCallback multipartCallback) {
        this.mCallback = multipartCallback;
    }

    public MultipartCallback getMultipartCallback() {
        return this.mCallback;
    }

    @Override // com.koushikdutta.async.http.body.AsyncHttpRequestBody
    public void write(AsyncHttpRequest asyncHttpRequest, final DataSink dataSink, final CompletedCallback completedCallback) {
        if (this.mParts == null) {
            return;
        }
        Continuation continuation = new Continuation(new CompletedCallback() { // from class: com.koushikdutta.async.http.body.MultipartFormDataBody.2
            @Override // com.koushikdutta.async.callback.CompletedCallback
            public void onCompleted(Exception exc) {
                completedCallback.onCompleted(exc);
            }
        });
        for (final Part part : this.mParts) {
            continuation.add(new ContinuationCallback() { // from class: com.koushikdutta.async.http.body.MultipartFormDataBody.5
                @Override // com.koushikdutta.async.callback.ContinuationCallback
                public void onContinue(Continuation continuation2, CompletedCallback completedCallback2) throws Exception {
                    byte[] bytes = part.getRawHeaders().toPrefixString(MultipartFormDataBody.this.getBoundaryStart()).getBytes();
                    Util.writeAll(dataSink, bytes, completedCallback2);
                    MultipartFormDataBody.this.written += bytes.length;
                }
            }).add(new ContinuationCallback() { // from class: com.koushikdutta.async.http.body.MultipartFormDataBody.4
                @Override // com.koushikdutta.async.callback.ContinuationCallback
                public void onContinue(Continuation continuation2, CompletedCallback completedCallback2) throws Exception {
                    long length = part.length();
                    if (length >= 0) {
                        MultipartFormDataBody multipartFormDataBody = MultipartFormDataBody.this;
                        multipartFormDataBody.written = (int) (((long) multipartFormDataBody.written) + length);
                    }
                    part.write(dataSink, completedCallback2);
                }
            }).add(new ContinuationCallback() { // from class: com.koushikdutta.async.http.body.MultipartFormDataBody.3
                @Override // com.koushikdutta.async.callback.ContinuationCallback
                public void onContinue(Continuation continuation2, CompletedCallback completedCallback2) throws Exception {
                    byte[] bytes = "\r\n".getBytes();
                    Util.writeAll(dataSink, bytes, completedCallback2);
                    MultipartFormDataBody.this.written += bytes.length;
                }
            });
        }
        continuation.add(new ContinuationCallback() { // from class: com.koushikdutta.async.http.body.MultipartFormDataBody.6
            static final /* synthetic */ boolean $assertionsDisabled = false;

            @Override // com.koushikdutta.async.callback.ContinuationCallback
            public void onContinue(Continuation continuation2, CompletedCallback completedCallback2) throws Exception {
                byte[] bytes = MultipartFormDataBody.this.getBoundaryEnd().getBytes();
                Util.writeAll(dataSink, bytes, completedCallback2);
                MultipartFormDataBody.this.written += bytes.length;
            }
        });
        continuation.start();
    }

    @Override // com.koushikdutta.async.http.body.AsyncHttpRequestBody
    public String getContentType() {
        if (getBoundary() == null) {
            setBoundary("----------------------------" + UUID.randomUUID().toString().replace(Parameters.DEFAULT_OPTION_PREFIXES, ""));
        }
        return this.contentType + "; boundary=" + getBoundary();
    }

    @Override // com.koushikdutta.async.http.body.AsyncHttpRequestBody
    public int length() {
        if (getBoundary() == null) {
            setBoundary("----------------------------" + UUID.randomUUID().toString().replace(Parameters.DEFAULT_OPTION_PREFIXES, ""));
        }
        int length = 0;
        for (Part part : this.mParts) {
            String prefixString = part.getRawHeaders().toPrefixString(getBoundaryStart());
            if (part.length() == -1) {
                return -1;
            }
            length = (int) (((long) length) + part.length() + ((long) prefixString.getBytes().length) + ((long) "\r\n".length()));
        }
        int length2 = length + getBoundaryEnd().getBytes().length;
        this.totalToWrite = length2;
        return length2;
    }

    public MultipartFormDataBody() {
    }

    public void setContentType(String str) {
        this.contentType = str;
    }

    public void addFilePart(String str, File file) {
        addPart(new FilePart(str, file));
    }

    public void addStringPart(String str, String str2) {
        addPart(new StringPart(str, str2));
    }

    public void addPart(Part part) {
        if (this.mParts == null) {
            this.mParts = new ArrayList<>();
        }
        this.mParts.add(part);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.koushikdutta.async.http.body.AsyncHttpRequestBody
    public Multimap get() {
        return new Multimap(this.formData.getMultiMap());
    }
}
