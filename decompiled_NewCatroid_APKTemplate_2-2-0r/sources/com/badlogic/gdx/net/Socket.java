package com.badlogic.gdx.net;

import com.badlogic.gdx.utils.Disposable;
import java.io.InputStream;
import java.io.OutputStream;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/net/Socket.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public interface Socket extends Disposable {
    InputStream getInputStream();

    OutputStream getOutputStream();

    String getRemoteAddress();

    boolean isConnected();
}
