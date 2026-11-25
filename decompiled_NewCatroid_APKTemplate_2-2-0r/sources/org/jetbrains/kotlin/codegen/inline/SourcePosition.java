package org.jetbrains.kotlin.codegen.inline;

import com.huawei.hms.ml.language.common.utils.Constant;
import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: SMAP.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0086\b\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005¢\u0006\u0002\u0010\u0007J\t\u0010\r\u001a\u00020\u0003HÆ\u0003J\t\u0010\u000e\u001a\u00020\u0005HÆ\u0003J\t\u0010\u000f\u001a\u00020\u0005HÆ\u0003J'\u0010\u0010\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u0011\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0014\u001a\u00020\u0003HÖ\u0001J\t\u0010\u0015\u001a\u00020\u0005HÖ\u0001R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0006\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\t¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/codegen/inline/SourcePosition;", "", Constant.LINE, "", "file", "", "path", "(ILjava/lang/String;Ljava/lang/String;)V", "getFile", "()Ljava/lang/String;", "getLine", "()I", "getPath", "component1", "component2", "component3", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "toString", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class SourcePosition {
    private final String file;
    private final int line;
    private final String path;

    public static /* synthetic */ SourcePosition copy$default(SourcePosition sourcePosition, int i, String str, String str2, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            i = sourcePosition.line;
        }
        if ((i2 & 2) != 0) {
            str = sourcePosition.file;
        }
        if ((i2 & 4) != 0) {
            str2 = sourcePosition.path;
        }
        return sourcePosition.copy(i, str, str2);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final int getLine() {
        return this.line;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final String getFile() {
        return this.file;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final String getPath() {
        return this.path;
    }

    public final SourcePosition copy(int line, String file, String path) {
        Intrinsics.checkNotNullParameter(file, "file");
        Intrinsics.checkNotNullParameter(path, "path");
        return new SourcePosition(line, file, path);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof SourcePosition)) {
            return false;
        }
        SourcePosition sourcePosition = (SourcePosition) other;
        return this.line == sourcePosition.line && Intrinsics.areEqual(this.file, sourcePosition.file) && Intrinsics.areEqual(this.path, sourcePosition.path);
    }

    public int hashCode() {
        return (((Integer.hashCode(this.line) * 31) + this.file.hashCode()) * 31) + this.path.hashCode();
    }

    public String toString() {
        return "SourcePosition(line=" + this.line + ", file=" + this.file + ", path=" + this.path + ')';
    }

    public SourcePosition(int i, String file, String path) {
        Intrinsics.checkNotNullParameter(file, "file");
        Intrinsics.checkNotNullParameter(path, "path");
        this.line = i;
        this.file = file;
        this.path = path;
    }

    public final String getFile() {
        return this.file;
    }

    public final int getLine() {
        return this.line;
    }

    public final String getPath() {
        return this.path;
    }
}
