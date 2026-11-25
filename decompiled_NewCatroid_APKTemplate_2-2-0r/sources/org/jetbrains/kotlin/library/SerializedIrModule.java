package org.jetbrains.kotlin.library;

import java.util.Collection;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: KotlinLibraryWriter.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0005R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/library/SerializedIrModule;", "", "files", "", "Lorg/jetbrains/kotlin/library/SerializedIrFile;", "(Ljava/util/Collection;)V", "getFiles", "()Ljava/util/Collection;", "kotlin-util-klib"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class SerializedIrModule {
    private final Collection<SerializedIrFile> files;

    public SerializedIrModule(Collection<SerializedIrFile> files) {
        Intrinsics.checkNotNullParameter(files, "files");
        this.files = files;
    }

    public final Collection<SerializedIrFile> getFiles() {
        return this.files;
    }
}
