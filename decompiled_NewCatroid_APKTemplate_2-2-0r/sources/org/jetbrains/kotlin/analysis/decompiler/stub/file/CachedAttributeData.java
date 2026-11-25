package org.jetbrains.kotlin.analysis.decompiler.stub.file;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.implementation.auxiliary.TypeProxy;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: FileAttributeService.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u000b\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u0000*\u0006\b\u0000\u0010\u0001 \u00012\u00020\u0002B\u0015\u0012\u0006\u0010\u0003\u001a\u00028\u0000\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u000e\u0010\f\u001a\u00028\u0000HÆ\u0003¢\u0006\u0002\u0010\nJ\t\u0010\r\u001a\u00020\u0005HÆ\u0003J(\u0010\u000e\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\b\b\u0002\u0010\u0003\u001a\u00028\u00002\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001¢\u0006\u0002\u0010\u000fJ\u0013\u0010\u0010\u001a\u00020\u00112\b\u0010\u0012\u001a\u0004\u0018\u00010\u0002HÖ\u0003J\t\u0010\u0013\u001a\u00020\u0014HÖ\u0001J\t\u0010\u0015\u001a\u00020\u0016HÖ\u0001R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0013\u0010\u0003\u001a\u00028\u0000¢\u0006\n\n\u0002\u0010\u000b\u001a\u0004\b\t\u0010\n¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/analysis/decompiler/stub/file/CachedAttributeData;", ExifInterface.GPS_DIRECTION_TRUE, "", "value", "timeStamp", "", "(Ljava/lang/Object;J)V", "getTimeStamp", "()J", "getValue", "()Ljava/lang/Object;", TypeProxy.SilentConstruction.Appender.JAVA_LANG_OBJECT_DESCRIPTOR, "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, "(Ljava/lang/Object;J)Lorg/jetbrains/kotlin/analysis/decompiler/stub/file/CachedAttributeData;", Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", "decompiler-to-file-stubs"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class CachedAttributeData<T> {
    private final long timeStamp;
    private final T value;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ CachedAttributeData copy$default(CachedAttributeData cachedAttributeData, Object obj, long j, int i, Object obj2) {
        if ((i & 1) != 0) {
            obj = cachedAttributeData.value;
        }
        if ((i & 2) != 0) {
            j = cachedAttributeData.timeStamp;
        }
        return cachedAttributeData.copy(obj, j);
    }

    public final T component1() {
        return this.value;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final long getTimeStamp() {
        return this.timeStamp;
    }

    public final CachedAttributeData<T> copy(T value, long timeStamp) {
        return new CachedAttributeData<>(value, timeStamp);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof CachedAttributeData)) {
            return false;
        }
        CachedAttributeData cachedAttributeData = (CachedAttributeData) other;
        return Intrinsics.areEqual(this.value, cachedAttributeData.value) && this.timeStamp == cachedAttributeData.timeStamp;
    }

    public int hashCode() {
        T t = this.value;
        return ((t == null ? 0 : t.hashCode()) * 31) + Long.hashCode(this.timeStamp);
    }

    public String toString() {
        return "CachedAttributeData(value=" + this.value + ", timeStamp=" + this.timeStamp + ')';
    }

    public CachedAttributeData(T t, long j) {
        this.value = t;
        this.timeStamp = j;
    }

    public final long getTimeStamp() {
        return this.timeStamp;
    }

    public final T getValue() {
        return this.value;
    }
}
