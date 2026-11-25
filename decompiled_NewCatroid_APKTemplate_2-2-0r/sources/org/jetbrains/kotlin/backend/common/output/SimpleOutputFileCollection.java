package org.jetbrains.kotlin.backend.common.output;

import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: output.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u0001B\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0005J\u000e\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u0016J\u0012\u0010\u0007\u001a\u0004\u0018\u00010\u00042\u0006\u0010\b\u001a\u00020\tH\u0016R\u0014\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/output/SimpleOutputFileCollection;", "Lorg/jetbrains/kotlin/backend/common/output/OutputFileCollection;", "outputFiles", "", "Lorg/jetbrains/kotlin/backend/common/output/OutputFile;", "(Ljava/util/List;)V", "asList", "get", "relativePath", "", "backend-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SimpleOutputFileCollection implements OutputFileCollection {
    private final List<OutputFile> outputFiles;

    /* JADX WARN: Multi-variable type inference failed */
    public SimpleOutputFileCollection(List<? extends OutputFile> outputFiles) {
        Intrinsics.checkNotNullParameter(outputFiles, "outputFiles");
        this.outputFiles = outputFiles;
    }

    @Override // org.jetbrains.kotlin.backend.common.output.OutputFileCollection
    public OutputFile get(String relativePath) {
        Object next;
        Intrinsics.checkNotNullParameter(relativePath, "relativePath");
        Iterator<T> it = this.outputFiles.iterator();
        while (it.hasNext()) {
            next = it.next();
            if (Intrinsics.areEqual(((OutputFile) next).getRelativePath(), relativePath)) {
                return (OutputFile) next;
            }
        }
        next = null;
        return (OutputFile) next;
    }

    @Override // org.jetbrains.kotlin.backend.common.output.OutputFileCollection
    public List<OutputFile> asList() {
        return this.outputFiles;
    }
}
