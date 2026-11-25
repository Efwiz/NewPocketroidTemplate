package com.google.firebase.remoteconfig.interop;

import com.google.firebase.remoteconfig.interop.rollouts.RolloutsStateSubscriber;

/* JADX INFO: loaded from: classes10.dex */
public interface FirebaseRemoteConfigInterop {
    void registerRolloutsStateSubscriber(String str, RolloutsStateSubscriber rolloutsStateSubscriber);
}
