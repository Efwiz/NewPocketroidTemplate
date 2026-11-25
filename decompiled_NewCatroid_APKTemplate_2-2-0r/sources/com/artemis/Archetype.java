package com.artemis;

/* JADX INFO: loaded from: classes8.dex */
public final class Archetype {
    public final int compositionId;

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    public final String f79name;
    final EntityTransmuter.TransmuteOperation transmuter;

    Archetype(EntityTransmuter.TransmuteOperation transmuteOperation, int i, String str) {
        this.transmuter = transmuteOperation;
        this.compositionId = i;
        this.f79name = str;
    }
}
