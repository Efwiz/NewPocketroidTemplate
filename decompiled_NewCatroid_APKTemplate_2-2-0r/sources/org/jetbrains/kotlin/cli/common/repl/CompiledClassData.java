package org.jetbrains.kotlin.cli.common.repl;

import java.io.Serializable;
import java.util.Arrays;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: ReplApi.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0012\n\u0002\b\t\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\b\u0086\b\u0018\u0000 \u00152\u00020\u0001:\u0001\u0015B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J\t\u0010\f\u001a\u00020\u0005HÆ\u0003J\u001d\u0010\r\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u0096\u0002J\b\u0010\u0012\u001a\u00020\u0013H\u0016J\t\u0010\u0014\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/cli/common/repl/CompiledClassData;", "Ljava/io/Serializable;", "path", "", "bytes", "", "(Ljava/lang/String;[B)V", "getBytes", "()[B", "getPath", "()Ljava/lang/String;", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "toString", "Companion", "cli-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class CompiledClassData implements Serializable {
    private static final long serialVersionUID = 8228357578L;
    private final byte[] bytes;
    private final String path;

    public static /* synthetic */ CompiledClassData copy$default(CompiledClassData compiledClassData, String str, byte[] bArr, int i, Object obj) {
        if ((i & 1) != 0) {
            str = compiledClassData.path;
        }
        if ((i & 2) != 0) {
            bArr = compiledClassData.bytes;
        }
        return compiledClassData.copy(str, bArr);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final String getPath() {
        return this.path;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final byte[] getBytes() {
        return this.bytes;
    }

    public final CompiledClassData copy(String path, byte[] bytes) {
        Intrinsics.checkNotNullParameter(path, "path");
        Intrinsics.checkNotNullParameter(bytes, "bytes");
        return new CompiledClassData(path, bytes);
    }

    public String toString() {
        return "CompiledClassData(path=" + this.path + ", bytes=" + Arrays.toString(this.bytes) + ')';
    }

    public CompiledClassData(String path, byte[] bytes) {
        Intrinsics.checkNotNullParameter(path, "path");
        Intrinsics.checkNotNullParameter(bytes, "bytes");
        this.path = path;
        this.bytes = bytes;
    }

    public final byte[] getBytes() {
        return this.bytes;
    }

    public final String getPath() {
        return this.path;
    }

    public boolean equals(Object other) {
        CompiledClassData compiledClassData = other instanceof CompiledClassData ? (CompiledClassData) other : null;
        return compiledClassData != null && Intrinsics.areEqual(this.path, compiledClassData.path) && Arrays.equals(this.bytes, compiledClassData.bytes);
    }

    public int hashCode() {
        return this.path.hashCode() + Arrays.hashCode(this.bytes);
    }
}
