package io.netty.util;

import io.netty.util.AbstractConstant;
import java.util.concurrent.atomic.AtomicLong;

/* JADX INFO: loaded from: classes11.dex */
public abstract class AbstractConstant<T extends AbstractConstant<T>> implements Constant<T> {
    private static final AtomicLong uniqueIdGenerator = new AtomicLong();

    /* JADX INFO: renamed from: id, reason: collision with root package name */
    private final int f283id;

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    private final String f284name;
    private final long uniquifier = uniqueIdGenerator.getAndIncrement();

    protected AbstractConstant(int i, String str) {
        this.f283id = i;
        this.f284name = str;
    }

    @Override // io.netty.util.Constant
    public final String name() {
        return this.f284name;
    }

    @Override // io.netty.util.Constant
    public final int id() {
        return this.f283id;
    }

    public final String toString() {
        return name();
    }

    public final int hashCode() {
        return super.hashCode();
    }

    public final boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override // java.lang.Comparable
    public final int compareTo(T t) {
        if (this == t) {
            return 0;
        }
        int iHashCode = hashCode() - t.hashCode();
        if (iHashCode != 0) {
            return iHashCode;
        }
        long j = this.uniquifier;
        long j2 = t.uniquifier;
        if (j < j2) {
            return -1;
        }
        if (j > j2) {
            return 1;
        }
        throw new Error("failed to compare two different constants");
    }
}
