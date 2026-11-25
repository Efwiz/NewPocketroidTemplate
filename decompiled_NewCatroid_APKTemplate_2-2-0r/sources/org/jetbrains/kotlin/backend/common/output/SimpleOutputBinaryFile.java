package org.jetbrains.kotlin.backend.common.output;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.io.File;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;

/* JADX INFO: compiled from: output.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0012\n\u0002\b\t\u0018\u00002\u00020\u0001B#\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\b\u0010\u000e\u001a\u00020\bH\u0016J\b\u0010\u000f\u001a\u00020\u0006H\u0016J\b\u0010\u0010\u001a\u00020\u0006H\u0016R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\u00020\u0006X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u001a\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/output/SimpleOutputBinaryFile;", "Lorg/jetbrains/kotlin/backend/common/output/OutputFile;", "sourceFiles", "", "Ljava/io/File;", "relativePath", "", FirebaseAnalytics.Param.CONTENT, "", "(Ljava/util/List;Ljava/lang/String;[B)V", "getRelativePath", "()Ljava/lang/String;", "getSourceFiles", "()Ljava/util/List;", "asByteArray", "asText", "toString", "backend-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SimpleOutputBinaryFile implements OutputFile {
    private final byte[] content;
    private final String relativePath;
    private final List<File> sourceFiles;

    /* JADX WARN: Multi-variable type inference failed */
    public SimpleOutputBinaryFile(List<? extends File> sourceFiles, String relativePath, byte[] content) {
        Intrinsics.checkNotNullParameter(sourceFiles, "sourceFiles");
        Intrinsics.checkNotNullParameter(relativePath, "relativePath");
        Intrinsics.checkNotNullParameter(content, "content");
        this.sourceFiles = sourceFiles;
        this.relativePath = relativePath;
        this.content = content;
    }

    @Override // org.jetbrains.kotlin.backend.common.output.OutputFile
    public List<File> getSourceFiles() {
        return this.sourceFiles;
    }

    @Override // org.jetbrains.kotlin.backend.common.output.OutputFile
    public String getRelativePath() {
        return this.relativePath;
    }

    @Override // org.jetbrains.kotlin.backend.common.output.OutputFile
    /* JADX INFO: renamed from: asByteArray, reason: from getter */
    public byte[] getContent() {
        return this.content;
    }

    @Override // org.jetbrains.kotlin.backend.common.output.OutputFile
    public String asText() {
        return new String(this.content, Charsets.UTF_8);
    }

    public String toString() {
        return getRelativePath() + " (compiled from " + getSourceFiles() + ')';
    }
}
