package net.mgsx.gltf.demo.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.utils.StreamUtils;
import java.io.IOException;

/* JADX INFO: loaded from: classes12.dex */
public abstract class SafeHttpResponseListener implements Net.HttpResponseListener {
    protected abstract void handleData(byte[] bArr);

    protected abstract void handleEnd();

    protected abstract void handleError(Throwable th);

    @Override // com.badlogic.gdx.Net.HttpResponseListener
    public void handleHttpResponse(Net.HttpResponse httpResponse) {
        try {
            final byte[] bArrCopyStreamToByteArray = StreamUtils.copyStreamToByteArray(httpResponse.getResultAsStream());
            Gdx.f82app.postRunnable(new Runnable() { // from class: net.mgsx.gltf.demo.util.SafeHttpResponseListener.1
                @Override // java.lang.Runnable
                public void run() {
                    SafeHttpResponseListener.this.handleData(bArrCopyStreamToByteArray);
                    SafeHttpResponseListener.this.handleEnd();
                }
            });
        } catch (IOException e) {
            failed(e);
        }
    }

    @Override // com.badlogic.gdx.Net.HttpResponseListener
    public void failed(final Throwable th) {
        Gdx.f82app.postRunnable(new Runnable() { // from class: net.mgsx.gltf.demo.util.SafeHttpResponseListener.2
            @Override // java.lang.Runnable
            public void run() {
                SafeHttpResponseListener.this.handleError(th);
                SafeHttpResponseListener.this.handleEnd();
            }
        });
    }

    @Override // com.badlogic.gdx.Net.HttpResponseListener
    public void cancelled() {
        Gdx.f82app.postRunnable(new Runnable() { // from class: net.mgsx.gltf.demo.util.SafeHttpResponseListener.3
            @Override // java.lang.Runnable
            public void run() {
                SafeHttpResponseListener.this.handleEnd();
            }
        });
    }
}
