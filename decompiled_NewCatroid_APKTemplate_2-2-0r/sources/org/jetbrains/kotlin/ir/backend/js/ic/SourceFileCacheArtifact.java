package org.jetbrains.kotlin.ir.backend.js.ic;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.io.FilesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.JsIrProgramFragment;
import org.jetbrains.kotlin.ir.backend.js.utils.serialization.JsIrAstSerializerKt;

/* JADX INFO: compiled from: IncrementalCacheArtifact.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b0\u0018\u00002\u00020\u0001:\u0003\u0012\u0013\u0014B\u0017\b\u0004\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0014\u0010\f\u001a\u00020\r2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000fJ\b\u0010\u0011\u001a\u00020\rH&R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0019\u0010\u0002\u001a\u00020\u0003ø\u0001\u0000ø\u0001\u0001¢\u0006\n\n\u0002\u0010\u000b\u001a\u0004\b\t\u0010\n\u0082\u0001\u0003\u0015\u0016\u0017\u0082\u0002\u000b\n\u0005\b¡\u001e0\u0001\n\u0002\b!¨\u0006\u0018"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/ic/SourceFileCacheArtifact;", "", "srcFile", "Lorg/jetbrains/kotlin/ir/backend/js/ic/KotlinSourceFile;", "binaryAstFile", "Ljava/io/File;", "(Ljava/lang/String;Ljava/io/File;)V", "getBinaryAstFile", "()Ljava/io/File;", "getSrcFile-zDqpWdY", "()Ljava/lang/String;", "Ljava/lang/String;", "commitBinaryAst", "", "fragments", "", "Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/JsIrProgramFragment;", "commitMetadata", "CommitMetadata", "DoNotChangeMetadata", "RemoveMetadata", "Lorg/jetbrains/kotlin/ir/backend/js/ic/SourceFileCacheArtifact$CommitMetadata;", "Lorg/jetbrains/kotlin/ir/backend/js/ic/SourceFileCacheArtifact$DoNotChangeMetadata;", "Lorg/jetbrains/kotlin/ir/backend/js/ic/SourceFileCacheArtifact$RemoveMetadata;", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class SourceFileCacheArtifact {
    private final File binaryAstFile;
    private final String srcFile;

    public /* synthetic */ SourceFileCacheArtifact(String str, File file, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, file);
    }

    public abstract void commitMetadata();

    private SourceFileCacheArtifact(String srcFile, File binaryAstFile) {
        Intrinsics.checkNotNullParameter(srcFile, "srcFile");
        Intrinsics.checkNotNullParameter(binaryAstFile, "binaryAstFile");
        this.srcFile = srcFile;
        this.binaryAstFile = binaryAstFile;
    }

    public final File getBinaryAstFile() {
        return this.binaryAstFile;
    }

    /* JADX INFO: renamed from: getSrcFile-zDqpWdY, reason: not valid java name and from getter */
    public final String getSrcFile() {
        return this.srcFile;
    }

    public final void commitBinaryAst(List<JsIrProgramFragment> fragments) throws Exception {
        Intrinsics.checkNotNullParameter(fragments, "fragments");
        File parentFile = this.binaryAstFile.getParentFile();
        if (parentFile != null) {
            parentFile.mkdirs();
        }
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(this.binaryAstFile));
        boolean z = false;
        try {
            JsIrAstSerializerKt.serializeTo(fragments, bufferedOutputStream);
            Unit unit = Unit.INSTANCE;
        } catch (Exception e) {
            z = true;
            throw e;
        } finally {
            if (!z) {
                bufferedOutputStream.close();
            }
        }
    }

    /* JADX INFO: compiled from: IncrementalCacheArtifact.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\b\u0010\u0007\u001a\u00020\bH\u0016¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/ic/SourceFileCacheArtifact$DoNotChangeMetadata;", "Lorg/jetbrains/kotlin/ir/backend/js/ic/SourceFileCacheArtifact;", "srcFile", "Lorg/jetbrains/kotlin/ir/backend/js/ic/KotlinSourceFile;", "binaryAstFile", "Ljava/io/File;", "(Ljava/lang/String;Ljava/io/File;Lkotlin/jvm/internal/DefaultConstructorMarker;)V", "commitMetadata", "", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class DoNotChangeMetadata extends SourceFileCacheArtifact {
        public /* synthetic */ DoNotChangeMetadata(String str, File file, DefaultConstructorMarker defaultConstructorMarker) {
            this(str, file);
        }

        @Override // org.jetbrains.kotlin.ir.backend.js.ic.SourceFileCacheArtifact
        public void commitMetadata() {
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        private DoNotChangeMetadata(String srcFile, File binaryAstFile) {
            super(srcFile, binaryAstFile, null);
            Intrinsics.checkNotNullParameter(srcFile, "srcFile");
            Intrinsics.checkNotNullParameter(binaryAstFile, "binaryAstFile");
        }
    }

    /* JADX INFO: compiled from: IncrementalCacheArtifact.kt */
    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\b\u0010\n\u001a\u00020\u000bH\u0016R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/ic/SourceFileCacheArtifact$CommitMetadata;", "Lorg/jetbrains/kotlin/ir/backend/js/ic/SourceFileCacheArtifact;", "srcFile", "Lorg/jetbrains/kotlin/ir/backend/js/ic/KotlinSourceFile;", "binaryAstFile", "Ljava/io/File;", "metadataFile", "encodedMetadata", "", "(Ljava/lang/String;Ljava/io/File;Ljava/io/File;[BLkotlin/jvm/internal/DefaultConstructorMarker;)V", "commitMetadata", "", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class CommitMetadata extends SourceFileCacheArtifact {
        private final byte[] encodedMetadata;
        private final File metadataFile;

        public /* synthetic */ CommitMetadata(String str, File file, File file2, byte[] bArr, DefaultConstructorMarker defaultConstructorMarker) {
            this(str, file, file2, bArr);
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        private CommitMetadata(String srcFile, File binaryAstFile, File metadataFile, byte[] encodedMetadata) {
            super(srcFile, binaryAstFile, null);
            Intrinsics.checkNotNullParameter(srcFile, "srcFile");
            Intrinsics.checkNotNullParameter(binaryAstFile, "binaryAstFile");
            Intrinsics.checkNotNullParameter(metadataFile, "metadataFile");
            Intrinsics.checkNotNullParameter(encodedMetadata, "encodedMetadata");
            this.metadataFile = metadataFile;
            this.encodedMetadata = encodedMetadata;
        }

        @Override // org.jetbrains.kotlin.ir.backend.js.ic.SourceFileCacheArtifact
        public void commitMetadata() {
            File parentFile = this.metadataFile.getParentFile();
            if (parentFile != null) {
                parentFile.mkdirs();
            }
            FilesKt.writeBytes(this.metadataFile, this.encodedMetadata);
        }
    }

    /* JADX INFO: compiled from: IncrementalCacheArtifact.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005¢\u0006\u0002\u0010\u0007J\b\u0010\b\u001a\u00020\tH\u0016R\u000e\u0010\u0006\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/ic/SourceFileCacheArtifact$RemoveMetadata;", "Lorg/jetbrains/kotlin/ir/backend/js/ic/SourceFileCacheArtifact;", "srcFile", "Lorg/jetbrains/kotlin/ir/backend/js/ic/KotlinSourceFile;", "binaryAstFile", "Ljava/io/File;", "metadataFile", "(Ljava/lang/String;Ljava/io/File;Ljava/io/File;Lkotlin/jvm/internal/DefaultConstructorMarker;)V", "commitMetadata", "", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class RemoveMetadata extends SourceFileCacheArtifact {
        private final File metadataFile;

        public /* synthetic */ RemoveMetadata(String str, File file, File file2, DefaultConstructorMarker defaultConstructorMarker) {
            this(str, file, file2);
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        private RemoveMetadata(String srcFile, File binaryAstFile, File metadataFile) {
            super(srcFile, binaryAstFile, null);
            Intrinsics.checkNotNullParameter(srcFile, "srcFile");
            Intrinsics.checkNotNullParameter(binaryAstFile, "binaryAstFile");
            Intrinsics.checkNotNullParameter(metadataFile, "metadataFile");
            this.metadataFile = metadataFile;
        }

        @Override // org.jetbrains.kotlin.ir.backend.js.ic.SourceFileCacheArtifact
        public void commitMetadata() {
            this.metadataFile.delete();
        }
    }
}
