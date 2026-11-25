package com.koushikdutta.async.http.server;

import android.net.Uri;
import com.koushikdutta.async.AsyncServer;
import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.AsyncHttpRequest;
import com.koushikdutta.async.http.AsyncHttpResponse;
import com.koushikdutta.async.http.callback.HttpConnectCallback;

/* JADX INFO: loaded from: classes10.dex */
public class AsyncProxyServer extends AsyncHttpServer {
    AsyncHttpClient proxyClient;

    @Override // com.koushikdutta.async.http.server.AsyncHttpServer
    protected boolean onRequest(AsyncHttpServerRequest asyncHttpServerRequest, AsyncHttpServerResponse asyncHttpServerResponse) {
        return true;
    }

    public AsyncProxyServer(AsyncServer asyncServer) {
        this.proxyClient = new AsyncHttpClient(asyncServer);
    }

    /* JADX WARN: Code duplicated, block: B:17:0x0040  */
    @Override // com.koushikdutta.async.http.server.AsyncHttpServer
    protected void onRequest(HttpServerRequestCallback httpServerRequestCallback, AsyncHttpServerRequest asyncHttpServerRequest, final AsyncHttpServerResponse asyncHttpServerResponse) {
        int i;
        Uri uri;
        super.onRequest(httpServerRequestCallback, asyncHttpServerRequest, asyncHttpServerResponse);
        if (httpServerRequestCallback != null) {
            return;
        }
        try {
            try {
                uri = Uri.parse(asyncHttpServerRequest.getPath());
                if (uri.getScheme() == null) {
                    throw new Exception("no host or full uri provided");
                }
                this.proxyClient.execute(new AsyncHttpRequest(uri, asyncHttpServerRequest.getMethod(), asyncHttpServerRequest.getHeaders()), new HttpConnectCallback() { // from class: com.koushikdutta.async.http.server.AsyncProxyServer.1
                    @Override // com.koushikdutta.async.http.callback.HttpConnectCallback
                    public void onConnectCompleted(Exception exc, AsyncHttpResponse asyncHttpResponse) {
                        if (exc != null) {
                            asyncHttpServerResponse.code(500);
                            asyncHttpServerResponse.send(exc.getMessage());
                        } else {
                            asyncHttpServerResponse.proxy(asyncHttpResponse);
                        }
                    }
                });
                return;
            } catch (Exception unused) {
                String str = asyncHttpServerRequest.getHeaders().get("Host");
                if (str != null) {
                    String[] strArrSplit = str.split(":", 2);
                    if (strArrSplit.length == 2) {
                        str = strArrSplit[0];
                        i = Integer.parseInt(strArrSplit[1]);
                    } else {
                        i = 80;
                    }
                } else {
                    i = 80;
                }
                uri = Uri.parse("http://" + str + ":" + i + asyncHttpServerRequest.getPath());
            }
        } catch (Exception e) {
            asyncHttpServerResponse.code(500);
            asyncHttpServerResponse.send(e.getMessage());
        }
        asyncHttpServerResponse.code(500);
        asyncHttpServerResponse.send(e.getMessage());
    }
}
