package com.koushikdutta.async;

/* JADX INFO: loaded from: classes10.dex */
public interface AsyncSocket extends DataEmitter, DataSink {
    @Override // com.koushikdutta.async.DataEmitter, com.koushikdutta.async.DataSink
    AsyncServer getServer();
}
