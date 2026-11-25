package com.badlogic.gdx.net;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/net/SocketHints.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class SocketHints {
    public int connectTimeout = 5000;
    public int performancePrefConnectionTime = 0;
    public int performancePrefLatency = 1;
    public int performancePrefBandwidth = 0;
    public int trafficClass = 20;
    public boolean keepAlive = true;
    public boolean tcpNoDelay = true;
    public int sendBufferSize = 4096;
    public int receiveBufferSize = 4096;
    public boolean linger = false;
    public int lingerDuration = 0;
    public int socketTimeout = 0;
}
