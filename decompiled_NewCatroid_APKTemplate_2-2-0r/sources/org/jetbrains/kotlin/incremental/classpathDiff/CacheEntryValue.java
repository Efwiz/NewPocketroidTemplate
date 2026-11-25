package org.jetbrains.kotlin.incremental.classpathDiff;

import java.lang.ref.SoftReference;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.implementation.auxiliary.TypeProxy;

/* JADX INFO: compiled from: InMemoryCacheWithEviction.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\b\u0003\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u0002B\u0017\b\u0016\u0012\u0006\u0010\u0003\u001a\u00028\u0000\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006B)\b\u0002\u0012\b\u0010\u0007\u001a\u0004\u0018\u00018\u0000\u0012\u000e\u0010\b\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\t\u0012\u0006\u0010\n\u001a\u00020\u0005¢\u0006\u0002\u0010\u000bJ\r\u0010\r\u001a\u0004\u0018\u00018\u0000¢\u0006\u0002\u0010\u000eJ\u0006\u0010\n\u001a\u00020\u0005J\u001b\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0003\u001a\u00028\u00002\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0006\u0010\u0011\u001a\u00020\u0010J\u0006\u0010\u0012\u001a\u00020\u0013J\u0006\u0010\u0014\u001a\u00020\u0013R\u000e\u0010\n\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u0016\u0010\b\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\u0007\u001a\u0004\u0018\u00018\u0000X\u0082\u000e¢\u0006\u0004\n\u0002\u0010\f¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/incremental/classpathDiff/CacheEntryValue;", "VALUE", "", "value", "currentTimePeriod", "", "(Ljava/lang/Object;I)V", "strongRef", "softRef", "Ljava/lang/ref/SoftReference;", "lastUsed", "(Ljava/lang/Object;Ljava/lang/ref/SoftReference;I)V", TypeProxy.SilentConstruction.Appender.JAVA_LANG_OBJECT_DESCRIPTOR, "get", "()Ljava/lang/Object;", "setStrongReference", "", "updateToSoftReference", "valueIsAStrongReference", "", "valueWasGarbageCollected", "incremental-compilation-impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class CacheEntryValue<VALUE> {
    private int lastUsed;
    private SoftReference<VALUE> softRef;
    private VALUE strongRef;

    private CacheEntryValue(VALUE value, SoftReference<VALUE> softReference, int i) {
        this.strongRef = value;
        this.softRef = softReference;
        this.lastUsed = i;
    }

    public CacheEntryValue(VALUE value, int i) {
        this(value, null, i);
    }

    public final VALUE get() {
        VALUE value = this.strongRef;
        if (value != null) {
            return value;
        }
        SoftReference<VALUE> softReference = this.softRef;
        Intrinsics.checkNotNull(softReference);
        return softReference.get();
    }

    public final void setStrongReference(VALUE value, int currentTimePeriod) {
        this.strongRef = value;
        this.softRef = null;
        this.lastUsed = currentTimePeriod;
    }

    public final void updateToSoftReference() {
        if (this.strongRef != null) {
            this.softRef = new SoftReference<>(this.strongRef);
            this.strongRef = null;
        }
    }

    public final boolean valueIsAStrongReference() {
        return this.strongRef != null;
    }

    public final boolean valueWasGarbageCollected() {
        return get() == null;
    }

    /* JADX INFO: renamed from: lastUsed, reason: from getter */
    public final int getLastUsed() {
        return this.lastUsed;
    }
}
