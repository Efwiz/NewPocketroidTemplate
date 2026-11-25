package org.jetbrains.kotlin.incremental;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.io.File;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: JavaClassesTrackerImpl.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J\t\u0010\f\u001a\u00020\u0005HÆ\u0003J\u001d\u0010\r\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001J\t\u0010\u0013\u001a\u00020\u0014HÖ\u0001R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/incremental/SerializedJavaClassWithSource;", "", FirebaseAnalytics.Param.SOURCE, "Ljava/io/File;", "proto", "Lorg/jetbrains/kotlin/incremental/SerializedJavaClass;", "(Ljava/io/File;Lorg/jetbrains/kotlin/incremental/SerializedJavaClass;)V", "getProto", "()Lorg/jetbrains/kotlin/incremental/SerializedJavaClass;", "getSource", "()Ljava/io/File;", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", "kotlin-build-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class SerializedJavaClassWithSource {
    private final SerializedJavaClass proto;
    private final File source;

    public static /* synthetic */ SerializedJavaClassWithSource copy$default(SerializedJavaClassWithSource serializedJavaClassWithSource, File file, SerializedJavaClass serializedJavaClass, int i, Object obj) {
        if ((i & 1) != 0) {
            file = serializedJavaClassWithSource.source;
        }
        if ((i & 2) != 0) {
            serializedJavaClass = serializedJavaClassWithSource.proto;
        }
        return serializedJavaClassWithSource.copy(file, serializedJavaClass);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final File getSource() {
        return this.source;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final SerializedJavaClass getProto() {
        return this.proto;
    }

    public final SerializedJavaClassWithSource copy(File source, SerializedJavaClass proto) {
        Intrinsics.checkNotNullParameter(source, "source");
        Intrinsics.checkNotNullParameter(proto, "proto");
        return new SerializedJavaClassWithSource(source, proto);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof SerializedJavaClassWithSource)) {
            return false;
        }
        SerializedJavaClassWithSource serializedJavaClassWithSource = (SerializedJavaClassWithSource) other;
        return Intrinsics.areEqual(this.source, serializedJavaClassWithSource.source) && Intrinsics.areEqual(this.proto, serializedJavaClassWithSource.proto);
    }

    public int hashCode() {
        return (this.source.hashCode() * 31) + this.proto.hashCode();
    }

    public String toString() {
        return "SerializedJavaClassWithSource(source=" + this.source + ", proto=" + this.proto + ')';
    }

    public SerializedJavaClassWithSource(File source, SerializedJavaClass proto) {
        Intrinsics.checkNotNullParameter(source, "source");
        Intrinsics.checkNotNullParameter(proto, "proto");
        this.source = source;
        this.proto = proto;
    }

    public final File getSource() {
        return this.source;
    }

    public final SerializedJavaClass getProto() {
        return this.proto;
    }
}
