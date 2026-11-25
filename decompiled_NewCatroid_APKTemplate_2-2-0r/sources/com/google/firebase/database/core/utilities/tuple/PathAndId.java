package com.google.firebase.database.core.utilities.tuple;

import com.google.firebase.database.core.Path;

/* JADX INFO: loaded from: classes10.dex */
public class PathAndId {

    /* JADX INFO: renamed from: id, reason: collision with root package name */
    private long f180id;
    private Path path;

    public PathAndId(Path path, long j) {
        this.path = path;
        this.f180id = j;
    }

    public Path getPath() {
        return this.path;
    }

    public long getId() {
        return this.f180id;
    }
}
