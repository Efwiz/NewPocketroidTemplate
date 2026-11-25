package org.jetbrains.kotlin.backend.common.output;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.io.File;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;

/* JADX INFO: compiled from: output.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0010\u0012\n\u0002\b\u0003\u0018\u00002\u00020\u0001B#\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\u0006¢\u0006\u0002\u0010\bJ\b\u0010\r\u001a\u00020\u000eH\u0016J\b\u0010\u000f\u001a\u00020\u0006H\u0016J\b\u0010\u0010\u001a\u00020\u0006H\u0016R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\u00020\u0006X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u001a\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/output/SimpleOutputFile;", "Lorg/jetbrains/kotlin/backend/common/output/OutputFile;", "sourceFiles", "", "Ljava/io/File;", "relativePath", "", FirebaseAnalytics.Param.CONTENT, "(Ljava/util/List;Ljava/lang/String;Ljava/lang/String;)V", "getRelativePath", "()Ljava/lang/String;", "getSourceFiles", "()Ljava/util/List;", "asByteArray", "", "asText", "toString", "backend-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SimpleOutputFile implements OutputFile {
    private final String content;
    private final String relativePath;
    private final List<File> sourceFiles;

    /* JADX WARN: Multi-variable type inference failed */
    public SimpleOutputFile(List<? extends File> sourceFiles, String relativePath, String content) {
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
    /* JADX INFO: renamed from: asByteArray */
    public byte[] getContent() {
        byte[] bytes = this.content.getBytes(Charsets.UTF_8);
        Intrinsics.checkNotNullExpressionValue(bytes, "getBytes(...)");
        return bytes;
    }

    @Override // org.jetbrains.kotlin.backend.common.output.OutputFile
    /* JADX INFO: renamed from: asText, reason: from getter */
    public String getContent() {
        return this.content;
    }

    public String toString() {
        return getRelativePath() + " (compiled from " + getSourceFiles() + ')';
    }
}
