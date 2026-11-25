package com.koushikdutta.async.http.server;

import android.text.TextUtils;
import com.beust.jcommander.Parameters;
import com.google.common.net.HttpHeaders;
import com.huawei.hms.framework.common.ContainerUtils;
import com.koushikdutta.async.AsyncServer;
import com.koushikdutta.async.AsyncSocket;
import com.koushikdutta.async.ByteBufferList;
import com.koushikdutta.async.DataSink;
import com.koushikdutta.async.Util;
import com.koushikdutta.async.callback.CompletedCallback;
import com.koushikdutta.async.callback.DataCallback;
import com.koushikdutta.async.callback.WritableCallback;
import com.koushikdutta.async.http.AsyncHttpResponse;
import com.koushikdutta.async.http.Headers;
import com.koushikdutta.async.http.HttpUtil;
import com.koushikdutta.async.http.Protocol;
import com.koushikdutta.async.http.filter.ChunkedOutputFilter;
import com.koushikdutta.async.util.StreamUtility;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Locale;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes10.dex */
public class AsyncHttpServerResponseImpl implements AsyncHttpServerResponse {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    CompletedCallback closedCallback;
    boolean ended;
    boolean mEnded;
    AsyncHttpServerRequestImpl mRequest;
    DataSink mSink;
    AsyncSocket mSocket;
    WritableCallback writable;
    private Headers mRawHeaders = new Headers();
    private long mContentLength = -1;
    boolean headWritten = false;
    int code = 200;

    protected void report(Exception exc) {
    }

    @Override // com.koushikdutta.async.http.server.AsyncHttpServerResponse
    public Headers getHeaders() {
        return this.mRawHeaders;
    }

    @Override // com.koushikdutta.async.http.server.AsyncHttpServerResponse
    public AsyncSocket getSocket() {
        return this.mSocket;
    }

    AsyncHttpServerResponseImpl(AsyncSocket asyncSocket, AsyncHttpServerRequestImpl asyncHttpServerRequestImpl) {
        this.mSocket = asyncSocket;
        this.mRequest = asyncHttpServerRequestImpl;
        if (HttpUtil.isKeepAlive(Protocol.HTTP_1_1, asyncHttpServerRequestImpl.getHeaders())) {
            this.mRawHeaders.set("Connection", HttpHeaders.KEEP_ALIVE);
        }
    }

    @Override // com.koushikdutta.async.DataSink
    public void write(ByteBufferList byteBufferList) {
        DataSink dataSink;
        if (!this.headWritten) {
            initFirstWrite();
        }
        if (byteBufferList.remaining() == 0 || (dataSink = this.mSink) == null) {
            return;
        }
        dataSink.write(byteBufferList);
    }

    void initFirstWrite() {
        if (this.headWritten) {
            return;
        }
        final boolean z = true;
        this.headWritten = true;
        String str = this.mRawHeaders.get("Transfer-Encoding");
        if ("".equals(str)) {
            this.mRawHeaders.removeAll("Transfer-Encoding");
        }
        boolean z2 = ("Chunked".equalsIgnoreCase(str) || str == null) && !"close".equalsIgnoreCase(this.mRawHeaders.get("Connection"));
        if (this.mContentLength < 0) {
            String str2 = this.mRawHeaders.get("Content-Length");
            if (!TextUtils.isEmpty(str2)) {
                this.mContentLength = Long.valueOf(str2).longValue();
            }
        }
        if (this.mContentLength >= 0 || !z2) {
            z = false;
        } else {
            this.mRawHeaders.set("Transfer-Encoding", "Chunked");
        }
        Util.writeAll(this.mSocket, this.mRawHeaders.toPrefixString(String.format(Locale.ENGLISH, "HTTP/1.1 %s %s", Integer.valueOf(this.code), AsyncHttpServer.getResponseCodeDescription(this.code))).getBytes(), new CompletedCallback() { // from class: com.koushikdutta.async.http.server.AsyncHttpServerResponseImpl.1
            @Override // com.koushikdutta.async.callback.CompletedCallback
            public void onCompleted(Exception exc) {
                if (exc != null) {
                    AsyncHttpServerResponseImpl.this.report(exc);
                    return;
                }
                if (z) {
                    ChunkedOutputFilter chunkedOutputFilter = new ChunkedOutputFilter(AsyncHttpServerResponseImpl.this.mSocket);
                    chunkedOutputFilter.setMaxBuffer(0);
                    AsyncHttpServerResponseImpl.this.mSink = chunkedOutputFilter;
                } else {
                    AsyncHttpServerResponseImpl asyncHttpServerResponseImpl = AsyncHttpServerResponseImpl.this;
                    asyncHttpServerResponseImpl.mSink = asyncHttpServerResponseImpl.mSocket;
                }
                AsyncHttpServerResponseImpl.this.mSink.setClosedCallback(AsyncHttpServerResponseImpl.this.closedCallback);
                AsyncHttpServerResponseImpl.this.closedCallback = null;
                AsyncHttpServerResponseImpl.this.mSink.setWriteableCallback(AsyncHttpServerResponseImpl.this.writable);
                AsyncHttpServerResponseImpl.this.writable = null;
                if (AsyncHttpServerResponseImpl.this.ended) {
                    AsyncHttpServerResponseImpl.this.end();
                } else {
                    AsyncHttpServerResponseImpl.this.getServer().post(new Runnable() { // from class: com.koushikdutta.async.http.server.AsyncHttpServerResponseImpl.1.1
                        @Override // java.lang.Runnable
                        public void run() {
                            WritableCallback writeableCallback = AsyncHttpServerResponseImpl.this.getWriteableCallback();
                            if (writeableCallback != null) {
                                writeableCallback.onWriteable();
                            }
                        }
                    });
                }
            }
        });
    }

    @Override // com.koushikdutta.async.DataSink
    public void setWriteableCallback(WritableCallback writableCallback) {
        DataSink dataSink = this.mSink;
        if (dataSink != null) {
            dataSink.setWriteableCallback(writableCallback);
        } else {
            this.writable = writableCallback;
        }
    }

    @Override // com.koushikdutta.async.DataSink
    public WritableCallback getWriteableCallback() {
        DataSink dataSink = this.mSink;
        if (dataSink != null) {
            return dataSink.getWriteableCallback();
        }
        return this.writable;
    }

    @Override // com.koushikdutta.async.http.server.AsyncHttpServerResponse, com.koushikdutta.async.DataSink
    public void end() {
        if (this.ended) {
            return;
        }
        this.ended = true;
        boolean z = this.headWritten;
        if (z && this.mSink == null) {
            return;
        }
        if (!z) {
            this.mRawHeaders.remove("Transfer-Encoding");
        }
        DataSink dataSink = this.mSink;
        if (dataSink instanceof ChunkedOutputFilter) {
            ((ChunkedOutputFilter) dataSink).setMaxBuffer(Integer.MAX_VALUE);
            this.mSink.write(new ByteBufferList());
            onEnd();
        } else {
            if (!this.headWritten) {
                if (!this.mRequest.getMethod().equalsIgnoreCase("HEAD")) {
                    send("text/html", "");
                    return;
                } else {
                    writeHead();
                    onEnd();
                    return;
                }
            }
            onEnd();
        }
    }

    @Override // com.koushikdutta.async.http.server.AsyncHttpServerResponse
    public void writeHead() {
        initFirstWrite();
    }

    @Override // com.koushikdutta.async.http.server.AsyncHttpServerResponse
    public void setContentType(String str) {
        this.mRawHeaders.set("Content-Type", str);
    }

    @Override // com.koushikdutta.async.http.server.AsyncHttpServerResponse
    public void send(String str, byte[] bArr) {
        this.mContentLength = bArr.length;
        this.mRawHeaders.set("Content-Length", Integer.toString(bArr.length));
        this.mRawHeaders.set("Content-Type", str);
        Util.writeAll(this, bArr, new CompletedCallback() { // from class: com.koushikdutta.async.http.server.AsyncHttpServerResponseImpl.2
            @Override // com.koushikdutta.async.callback.CompletedCallback
            public void onCompleted(Exception exc) {
                AsyncHttpServerResponseImpl.this.onEnd();
            }
        });
    }

    @Override // com.koushikdutta.async.http.server.AsyncHttpServerResponse
    public void send(String str, String str2) {
        try {
            send(str, str2.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new AssertionError(e);
        }
    }

    protected void onEnd() {
        this.mEnded = true;
    }

    @Override // com.koushikdutta.async.http.server.AsyncHttpServerResponse
    public void send(String str) {
        String str2 = this.mRawHeaders.get("Content-Type");
        if (str2 == null) {
            str2 = "text/html; charset=utf-8";
        }
        send(str2, str);
    }

    @Override // com.koushikdutta.async.http.server.AsyncHttpServerResponse
    public void send(JSONObject jSONObject) {
        send("application/json; charset=utf-8", jSONObject.toString());
    }

    @Override // com.koushikdutta.async.http.server.AsyncHttpServerResponse
    public void sendStream(final InputStream inputStream, long j) {
        long j2 = j - 1;
        String str = this.mRequest.getHeaders().get("Range");
        long j3 = 0;
        if (str != null) {
            String[] strArrSplit = str.split(ContainerUtils.KEY_VALUE_DELIMITER);
            if (strArrSplit.length != 2 || !"bytes".equals(strArrSplit[0])) {
                code(416);
                end();
                return;
            }
            String[] strArrSplit2 = strArrSplit[1].split(Parameters.DEFAULT_OPTION_PREFIXES);
            try {
                if (strArrSplit2.length > 2) {
                    throw new MalformedRangeException();
                }
                j3 = TextUtils.isEmpty(strArrSplit2[0]) ? 0L : Long.parseLong(strArrSplit2[0]);
                if (strArrSplit2.length == 2 && !TextUtils.isEmpty(strArrSplit2[1])) {
                    j2 = Long.parseLong(strArrSplit2[1]);
                }
                code(206);
                getHeaders().set("Content-Range", String.format(Locale.ENGLISH, "bytes %d-%d/%d", Long.valueOf(j3), Long.valueOf(j2), Long.valueOf(j)));
            } catch (Exception unused) {
                code(416);
                end();
                return;
            }
        }
        try {
            if (j3 != inputStream.skip(j3)) {
                throw new StreamSkipException("skip failed to skip requested amount");
            }
            long j4 = (j2 - j3) + 1;
            this.mContentLength = j4;
            this.mRawHeaders.set("Content-Length", String.valueOf(j4));
            this.mRawHeaders.set("Accept-Ranges", "bytes");
            if (this.mRequest.getMethod().equals("HEAD")) {
                writeHead();
                onEnd();
            } else {
                Util.pump(inputStream, this.mContentLength, this, new CompletedCallback() { // from class: com.koushikdutta.async.http.server.AsyncHttpServerResponseImpl.3
                    @Override // com.koushikdutta.async.callback.CompletedCallback
                    public void onCompleted(Exception exc) {
                        StreamUtility.closeQuietly(inputStream);
                        AsyncHttpServerResponseImpl.this.onEnd();
                    }
                });
            }
        } catch (Exception unused2) {
            code(500);
            end();
        }
    }

    @Override // com.koushikdutta.async.http.server.AsyncHttpServerResponse
    public void sendFile(File file) {
        try {
            if (this.mRawHeaders.get("Content-Type") == null) {
                this.mRawHeaders.set("Content-Type", AsyncHttpServer.getContentType(file.getAbsolutePath()));
            }
            sendStream(new BufferedInputStream(new FileInputStream(file), 64000), file.length());
        } catch (FileNotFoundException unused) {
            code(404);
            end();
        }
    }

    @Override // com.koushikdutta.async.http.server.AsyncHttpServerResponse
    public void proxy(final AsyncHttpResponse asyncHttpResponse) {
        code(asyncHttpResponse.code());
        asyncHttpResponse.headers().removeAll("Transfer-Encoding");
        asyncHttpResponse.headers().removeAll("Content-Encoding");
        asyncHttpResponse.headers().removeAll("Connection");
        getHeaders().addAll(asyncHttpResponse.headers());
        asyncHttpResponse.headers().set("Connection", "close");
        Util.pump(asyncHttpResponse, this, new CompletedCallback() { // from class: com.koushikdutta.async.http.server.AsyncHttpServerResponseImpl.4
            @Override // com.koushikdutta.async.callback.CompletedCallback
            public void onCompleted(Exception exc) {
                asyncHttpResponse.setEndCallback(new CompletedCallback.NullCompletedCallback());
                asyncHttpResponse.setDataCallback(new DataCallback.NullDataCallback());
                AsyncHttpServerResponseImpl.this.end();
            }
        });
    }

    @Override // com.koushikdutta.async.http.server.AsyncHttpServerResponse
    public AsyncHttpServerResponse code(int i) {
        this.code = i;
        return this;
    }

    @Override // com.koushikdutta.async.http.server.AsyncHttpServerResponse
    public int code() {
        return this.code;
    }

    @Override // com.koushikdutta.async.http.server.AsyncHttpServerResponse
    public void redirect(String str) {
        code(302);
        this.mRawHeaders.set("Location", str);
        end();
    }

    @Override // com.koushikdutta.async.http.server.AsyncHttpServerResponse, com.koushikdutta.async.callback.CompletedCallback
    public void onCompleted(Exception exc) {
        end();
    }

    @Override // com.koushikdutta.async.DataSink
    public boolean isOpen() {
        DataSink dataSink = this.mSink;
        if (dataSink != null) {
            return dataSink.isOpen();
        }
        return this.mSocket.isOpen();
    }

    @Override // com.koushikdutta.async.DataSink
    public void setClosedCallback(CompletedCallback completedCallback) {
        DataSink dataSink = this.mSink;
        if (dataSink != null) {
            dataSink.setClosedCallback(completedCallback);
        } else {
            this.closedCallback = completedCallback;
        }
    }

    @Override // com.koushikdutta.async.DataSink
    public CompletedCallback getClosedCallback() {
        DataSink dataSink = this.mSink;
        if (dataSink != null) {
            return dataSink.getClosedCallback();
        }
        return this.closedCallback;
    }

    @Override // com.koushikdutta.async.DataSink
    public AsyncServer getServer() {
        return this.mSocket.getServer();
    }

    public String toString() {
        if (this.mRawHeaders == null) {
            return super.toString();
        }
        return this.mRawHeaders.toPrefixString(String.format(Locale.ENGLISH, "HTTP/1.1 %s %s", Integer.valueOf(this.code), AsyncHttpServer.getResponseCodeDescription(this.code)));
    }
}
