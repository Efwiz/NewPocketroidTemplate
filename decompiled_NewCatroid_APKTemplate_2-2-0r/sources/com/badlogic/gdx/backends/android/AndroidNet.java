package com.badlogic.gdx.backends.android;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.net.NetJavaImpl;
import com.badlogic.gdx.net.NetJavaServerSocketImpl;
import com.badlogic.gdx.net.NetJavaSocketImpl;
import com.badlogic.gdx.net.ServerSocket;
import com.badlogic.gdx.net.ServerSocketHints;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.net.SocketHints;

/* JADX INFO: loaded from: classes8.dex */
public class AndroidNet implements Net {

    /* JADX INFO: renamed from: app, reason: collision with root package name */
    final AndroidApplicationBase f91app;
    NetJavaImpl netJavaImpl;

    public AndroidNet(AndroidApplicationBase androidApplicationBase, AndroidApplicationConfiguration androidApplicationConfiguration) {
        this.f91app = androidApplicationBase;
        this.netJavaImpl = new NetJavaImpl(androidApplicationConfiguration.maxNetThreads);
    }

    @Override // com.badlogic.gdx.Net
    public void sendHttpRequest(Net.HttpRequest httpRequest, Net.HttpResponseListener httpResponseListener) {
        this.netJavaImpl.sendHttpRequest(httpRequest, httpResponseListener);
    }

    @Override // com.badlogic.gdx.Net
    public void cancelHttpRequest(Net.HttpRequest httpRequest) {
        this.netJavaImpl.cancelHttpRequest(httpRequest);
    }

    @Override // com.badlogic.gdx.Net
    public boolean isHttpRequestPending(Net.HttpRequest httpRequest) {
        return this.netJavaImpl.isHttpRequestPending(httpRequest);
    }

    @Override // com.badlogic.gdx.Net
    public ServerSocket newServerSocket(Net.Protocol protocol, String str, int i, ServerSocketHints serverSocketHints) {
        return new NetJavaServerSocketImpl(protocol, str, i, serverSocketHints);
    }

    @Override // com.badlogic.gdx.Net
    public ServerSocket newServerSocket(Net.Protocol protocol, int i, ServerSocketHints serverSocketHints) {
        return new NetJavaServerSocketImpl(protocol, i, serverSocketHints);
    }

    @Override // com.badlogic.gdx.Net
    public Socket newClientSocket(Net.Protocol protocol, String str, int i, SocketHints socketHints) {
        return new NetJavaSocketImpl(protocol, str, i, socketHints);
    }

    @Override // com.badlogic.gdx.Net
    public boolean openURI(String str) {
        try {
            Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(str));
            if (!(this.f91app.getContext() instanceof Activity)) {
                intent.addFlags(268435456);
            }
            this.f91app.startActivity(intent);
            return true;
        } catch (ActivityNotFoundException unused) {
            return false;
        }
    }
}
