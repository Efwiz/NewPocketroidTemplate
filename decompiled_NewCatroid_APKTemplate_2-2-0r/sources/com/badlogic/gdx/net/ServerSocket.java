package com.badlogic.gdx.net;

import com.badlogic.gdx.Net;
import com.badlogic.gdx.utils.Disposable;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/net/ServerSocket.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public interface ServerSocket extends Disposable {
    Socket accept(SocketHints socketHints);

    Net.Protocol getProtocol();
}
