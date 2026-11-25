package com.koushikdutta.async.http;

import android.text.TextUtils;
import com.koushikdutta.async.AsyncSocket;
import com.koushikdutta.async.BufferedDataSink;
import com.koushikdutta.async.DataEmitter;
import com.koushikdutta.async.DataSink;
import com.koushikdutta.async.LineEmitter;
import com.koushikdutta.async.Util;
import com.koushikdutta.async.callback.CompletedCallback;
import com.koushikdutta.async.http.body.AsyncHttpRequestBody;
import com.koushikdutta.async.http.filter.ChunkedOutputFilter;
import java.io.IOException;

/* JADX INFO: loaded from: classes10.dex */
public class HttpTransportMiddleware extends SimpleMiddleware {
    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    @Override // com.koushikdutta.async.http.SimpleMiddleware, com.koushikdutta.async.http.AsyncHttpClientMiddleware
    public boolean exchangeHeaders(final AsyncHttpClientMiddleware.OnExchangeHeaderData onExchangeHeaderData) {
        final BufferedDataSink bufferedDataSink;
        DataSink dataSink;
        Protocol protocol = Protocol.get(onExchangeHeaderData.protocol);
        if (protocol != null && protocol != Protocol.HTTP_1_0 && protocol != Protocol.HTTP_1_1) {
            return super.exchangeHeaders(onExchangeHeaderData);
        }
        AsyncHttpRequest asyncHttpRequest = onExchangeHeaderData.request;
        AsyncHttpRequestBody body = onExchangeHeaderData.request.getBody();
        if (body != null) {
            if (body.length() >= 0) {
                asyncHttpRequest.getHeaders().set("Content-Length", String.valueOf(body.length()));
                onExchangeHeaderData.response.sink(onExchangeHeaderData.socket);
            } else if ("close".equals(asyncHttpRequest.getHeaders().get("Connection"))) {
                onExchangeHeaderData.response.sink(onExchangeHeaderData.socket);
            } else {
                asyncHttpRequest.getHeaders().set("Transfer-Encoding", "Chunked");
                onExchangeHeaderData.response.sink(new ChunkedOutputFilter(onExchangeHeaderData.socket));
            }
        }
        String prefixString = asyncHttpRequest.getHeaders().toPrefixString(asyncHttpRequest.getRequestLine().toString());
        byte[] bytes = prefixString.getBytes();
        if (body != null && body.length() >= 0 && body.length() + bytes.length < 1024) {
            BufferedDataSink bufferedDataSink2 = new BufferedDataSink(onExchangeHeaderData.response.sink());
            bufferedDataSink2.forceBuffering(true);
            onExchangeHeaderData.response.sink(bufferedDataSink2);
            bufferedDataSink = bufferedDataSink2;
            dataSink = bufferedDataSink2;
        } else {
            bufferedDataSink = null;
            dataSink = onExchangeHeaderData.socket;
        }
        asyncHttpRequest.logv("\n" + prefixString);
        final CompletedCallback completedCallback = onExchangeHeaderData.sendHeadersCallback;
        Util.writeAll(dataSink, bytes, new CompletedCallback() { // from class: com.koushikdutta.async.http.HttpTransportMiddleware.1
            @Override // com.koushikdutta.async.callback.CompletedCallback
            public void onCompleted(Exception exc) {
                Util.end(completedCallback, exc);
                BufferedDataSink bufferedDataSink3 = bufferedDataSink;
                if (bufferedDataSink3 != null) {
                    bufferedDataSink3.forceBuffering(false);
                    bufferedDataSink.setMaxBuffer(0);
                }
            }
        });
        LineEmitter.StringCallback stringCallback = new LineEmitter.StringCallback() { // from class: com.koushikdutta.async.http.HttpTransportMiddleware.2
            Headers mRawHeaders = new Headers();
            String statusLine;

            @Override // com.koushikdutta.async.LineEmitter.StringCallback
            public void onStringAvailable(String str) {
                DataEmitter bodyDecoder;
                try {
                    String strTrim = str.trim();
                    if (this.statusLine == null) {
                        this.statusLine = strTrim;
                        return;
                    }
                    if (!TextUtils.isEmpty(strTrim)) {
                        this.mRawHeaders.addLine(strTrim);
                        return;
                    }
                    String[] strArrSplit = this.statusLine.split(" ", 3);
                    if (strArrSplit.length < 2) {
                        throw new Exception(new IOException("Not HTTP"));
                    }
                    onExchangeHeaderData.response.headers(this.mRawHeaders);
                    String str2 = strArrSplit[0];
                    onExchangeHeaderData.response.protocol(str2);
                    onExchangeHeaderData.response.code(Integer.parseInt(strArrSplit[1]));
                    onExchangeHeaderData.response.message(strArrSplit.length == 3 ? strArrSplit[2] : "");
                    onExchangeHeaderData.receiveHeadersCallback.onCompleted(null);
                    AsyncSocket asyncSocketSocket = onExchangeHeaderData.response.socket();
                    if (asyncSocketSocket == null) {
                        return;
                    }
                    if ("HEAD".equalsIgnoreCase(onExchangeHeaderData.request.getMethod())) {
                        bodyDecoder = HttpUtil.EndEmitter.create(asyncSocketSocket.getServer(), null);
                    } else {
                        bodyDecoder = HttpUtil.getBodyDecoder(asyncSocketSocket, Protocol.get(str2), this.mRawHeaders, false);
                    }
                    onExchangeHeaderData.response.emitter(bodyDecoder);
                } catch (Exception e) {
                    onExchangeHeaderData.receiveHeadersCallback.onCompleted(e);
                }
            }
        };
        LineEmitter lineEmitter = new LineEmitter();
        onExchangeHeaderData.socket.setDataCallback(lineEmitter);
        lineEmitter.setLineCallback(stringCallback);
        return true;
    }

    @Override // com.koushikdutta.async.http.SimpleMiddleware, com.koushikdutta.async.http.AsyncHttpClientMiddleware
    public void onRequestSent(AsyncHttpClientMiddleware.OnRequestSentData onRequestSentData) {
        Protocol protocol = Protocol.get(onRequestSentData.protocol);
        if ((protocol == null || protocol == Protocol.HTTP_1_0 || protocol == Protocol.HTTP_1_1) && (onRequestSentData.response.sink() instanceof ChunkedOutputFilter)) {
            onRequestSentData.response.sink().end();
        }
    }
}
