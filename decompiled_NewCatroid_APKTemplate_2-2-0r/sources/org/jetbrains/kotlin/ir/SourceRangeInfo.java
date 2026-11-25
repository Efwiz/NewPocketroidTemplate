package org.jetbrains.kotlin.ir;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: IrFileEntry.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0018\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0086\b\u0018\u00002\u00020\u0001B=\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u0006\u0010\u0007\u001a\u00020\u0005\u0012\u0006\u0010\b\u001a\u00020\u0005\u0012\u0006\u0010\t\u001a\u00020\u0005\u0012\u0006\u0010\n\u001a\u00020\u0005¢\u0006\u0002\u0010\u000bJ\t\u0010\u0015\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0016\u001a\u00020\u0005HÆ\u0003J\t\u0010\u0017\u001a\u00020\u0005HÆ\u0003J\t\u0010\u0018\u001a\u00020\u0005HÆ\u0003J\t\u0010\u0019\u001a\u00020\u0005HÆ\u0003J\t\u0010\u001a\u001a\u00020\u0005HÆ\u0003J\t\u0010\u001b\u001a\u00020\u0005HÆ\u0003JO\u0010\u001c\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00052\b\b\u0002\u0010\u0007\u001a\u00020\u00052\b\b\u0002\u0010\b\u001a\u00020\u00052\b\b\u0002\u0010\t\u001a\u00020\u00052\b\b\u0002\u0010\n\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u001d\u001a\u00020\u001e2\b\u0010\u001f\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010 \u001a\u00020\u0005HÖ\u0001J\t\u0010!\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\n\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\t\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\rR\u0011\u0010\b\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\rR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0007\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\rR\u0011\u0010\u0006\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\rR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\r¨\u0006\""}, d2 = {"Lorg/jetbrains/kotlin/ir/SourceRangeInfo;", "", "filePath", "", "startOffset", "", "startLineNumber", "startColumnNumber", "endOffset", "endLineNumber", "endColumnNumber", "(Ljava/lang/String;IIIIII)V", "getEndColumnNumber", "()I", "getEndLineNumber", "getEndOffset", "getFilePath", "()Ljava/lang/String;", "getStartColumnNumber", "getStartLineNumber", "getStartOffset", "component1", "component2", "component3", "component4", "component5", "component6", "component7", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "toString", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class SourceRangeInfo {
    private final int endColumnNumber;
    private final int endLineNumber;
    private final int endOffset;
    private final String filePath;
    private final int startColumnNumber;
    private final int startLineNumber;
    private final int startOffset;

    public static /* synthetic */ SourceRangeInfo copy$default(SourceRangeInfo sourceRangeInfo, String str, int i, int i2, int i3, int i4, int i5, int i6, int i7, Object obj) {
        if ((i7 & 1) != 0) {
            str = sourceRangeInfo.filePath;
        }
        if ((i7 & 2) != 0) {
            i = sourceRangeInfo.startOffset;
        }
        int i8 = i;
        if ((i7 & 4) != 0) {
            i2 = sourceRangeInfo.startLineNumber;
        }
        int i9 = i2;
        if ((i7 & 8) != 0) {
            i3 = sourceRangeInfo.startColumnNumber;
        }
        int i10 = i3;
        if ((i7 & 16) != 0) {
            i4 = sourceRangeInfo.endOffset;
        }
        int i11 = i4;
        if ((i7 & 32) != 0) {
            i5 = sourceRangeInfo.endLineNumber;
        }
        int i12 = i5;
        if ((i7 & 64) != 0) {
            i6 = sourceRangeInfo.endColumnNumber;
        }
        return sourceRangeInfo.copy(str, i8, i9, i10, i11, i12, i6);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final String getFilePath() {
        return this.filePath;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final int getStartOffset() {
        return this.startOffset;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final int getStartLineNumber() {
        return this.startLineNumber;
    }

    /* JADX INFO: renamed from: component4, reason: from getter */
    public final int getStartColumnNumber() {
        return this.startColumnNumber;
    }

    /* JADX INFO: renamed from: component5, reason: from getter */
    public final int getEndOffset() {
        return this.endOffset;
    }

    /* JADX INFO: renamed from: component6, reason: from getter */
    public final int getEndLineNumber() {
        return this.endLineNumber;
    }

    /* JADX INFO: renamed from: component7, reason: from getter */
    public final int getEndColumnNumber() {
        return this.endColumnNumber;
    }

    public final SourceRangeInfo copy(String filePath, int startOffset, int startLineNumber, int startColumnNumber, int endOffset, int endLineNumber, int endColumnNumber) {
        Intrinsics.checkNotNullParameter(filePath, "filePath");
        return new SourceRangeInfo(filePath, startOffset, startLineNumber, startColumnNumber, endOffset, endLineNumber, endColumnNumber);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof SourceRangeInfo)) {
            return false;
        }
        SourceRangeInfo sourceRangeInfo = (SourceRangeInfo) other;
        return Intrinsics.areEqual(this.filePath, sourceRangeInfo.filePath) && this.startOffset == sourceRangeInfo.startOffset && this.startLineNumber == sourceRangeInfo.startLineNumber && this.startColumnNumber == sourceRangeInfo.startColumnNumber && this.endOffset == sourceRangeInfo.endOffset && this.endLineNumber == sourceRangeInfo.endLineNumber && this.endColumnNumber == sourceRangeInfo.endColumnNumber;
    }

    public int hashCode() {
        return (((((((((((this.filePath.hashCode() * 31) + Integer.hashCode(this.startOffset)) * 31) + Integer.hashCode(this.startLineNumber)) * 31) + Integer.hashCode(this.startColumnNumber)) * 31) + Integer.hashCode(this.endOffset)) * 31) + Integer.hashCode(this.endLineNumber)) * 31) + Integer.hashCode(this.endColumnNumber);
    }

    public String toString() {
        return "SourceRangeInfo(filePath=" + this.filePath + ", startOffset=" + this.startOffset + ", startLineNumber=" + this.startLineNumber + ", startColumnNumber=" + this.startColumnNumber + ", endOffset=" + this.endOffset + ", endLineNumber=" + this.endLineNumber + ", endColumnNumber=" + this.endColumnNumber + ')';
    }

    public SourceRangeInfo(String filePath, int i, int i2, int i3, int i4, int i5, int i6) {
        Intrinsics.checkNotNullParameter(filePath, "filePath");
        this.filePath = filePath;
        this.startOffset = i;
        this.startLineNumber = i2;
        this.startColumnNumber = i3;
        this.endOffset = i4;
        this.endLineNumber = i5;
        this.endColumnNumber = i6;
    }

    public final String getFilePath() {
        return this.filePath;
    }

    public final int getStartOffset() {
        return this.startOffset;
    }

    public final int getStartLineNumber() {
        return this.startLineNumber;
    }

    public final int getStartColumnNumber() {
        return this.startColumnNumber;
    }

    public final int getEndOffset() {
        return this.endOffset;
    }

    public final int getEndLineNumber() {
        return this.endLineNumber;
    }

    public final int getEndColumnNumber() {
        return this.endColumnNumber;
    }
}
