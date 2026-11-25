package org.jetbrains.kotlin.cli.jvm.compiler.jarfs;

import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ZipImplementation.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\r\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\u0007\u0018\u00002\u00020\u0001:\u0001\u0018B5\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u0006\u0010\u0007\u001a\u00020\u0005\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u0005¢\u0006\u0002\u0010\u000bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\n\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\rR\u0011\u0010\u0011\u001a\u00020\u00128F¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0013R\u0011\u0010\u0007\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\rR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0011\u0010\u0006\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\r¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/cli/jvm/compiler/jarfs/ZipEntryDescription;", "", "relativePath", "", "compressedSize", "", "uncompressedSize", "offsetInFile", "compressionKind", "Lorg/jetbrains/kotlin/cli/jvm/compiler/jarfs/ZipEntryDescription$CompressionKind;", "fileNameSize", "(Ljava/lang/CharSequence;IIILorg/jetbrains/kotlin/cli/jvm/compiler/jarfs/ZipEntryDescription$CompressionKind;I)V", "getCompressedSize", "()I", "getCompressionKind", "()Lorg/jetbrains/kotlin/cli/jvm/compiler/jarfs/ZipEntryDescription$CompressionKind;", "getFileNameSize", "isDirectory", "", "()Z", "getOffsetInFile", "getRelativePath", "()Ljava/lang/CharSequence;", "getUncompressedSize", "CompressionKind", "cli-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ZipEntryDescription {
    private final int compressedSize;
    private final CompressionKind compressionKind;
    private final int fileNameSize;
    private final int offsetInFile;
    private final CharSequence relativePath;
    private final int uncompressedSize;

    /* JADX INFO: compiled from: ZipImplementation.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0004\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004¨\u0006\u0005"}, d2 = {"Lorg/jetbrains/kotlin/cli/jvm/compiler/jarfs/ZipEntryDescription$CompressionKind;", "", "(Ljava/lang/String;I)V", "PLAIN", "DEFLATE", "cli-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public enum CompressionKind {
        PLAIN,
        DEFLATE;

        private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

        public static EnumEntries<CompressionKind> getEntries() {
            return $ENTRIES;
        }
    }

    public ZipEntryDescription(CharSequence relativePath, int i, int i2, int i3, CompressionKind compressionKind, int i4) {
        Intrinsics.checkNotNullParameter(relativePath, "relativePath");
        Intrinsics.checkNotNullParameter(compressionKind, "compressionKind");
        this.relativePath = relativePath;
        this.compressedSize = i;
        this.uncompressedSize = i2;
        this.offsetInFile = i3;
        this.compressionKind = compressionKind;
        this.fileNameSize = i4;
    }

    public final CharSequence getRelativePath() {
        return this.relativePath;
    }

    public final int getCompressedSize() {
        return this.compressedSize;
    }

    public final int getUncompressedSize() {
        return this.uncompressedSize;
    }

    public final int getOffsetInFile() {
        return this.offsetInFile;
    }

    public final CompressionKind getCompressionKind() {
        return this.compressionKind;
    }

    public final int getFileNameSize() {
        return this.fileNameSize;
    }

    public final boolean isDirectory() {
        return this.uncompressedSize == 0;
    }
}
