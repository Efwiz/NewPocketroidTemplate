package com.google.firebase.database.connection;

/* JADX INFO: loaded from: classes10.dex */
public interface ListenHashProvider {
    CompoundHash getCompoundHash();

    String getSimpleHash();

    boolean shouldIncludeCompoundHash();
}
