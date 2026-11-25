package org.jetbrains.kotlin.konan.library.impl;

import java.io.IOException;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.konan.file.File;
import org.jetbrains.kotlin.konan.library.TargetedKotlinLibraryLayout;

/* JADX INFO: compiled from: BitcodeWriterImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0016\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u000e\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/konan/library/impl/TargetedWriterImpl;", "", "targetLayout", "Lorg/jetbrains/kotlin/konan/library/TargetedKotlinLibraryLayout;", "(Lorg/jetbrains/kotlin/konan/library/TargetedKotlinLibraryLayout;)V", "getTargetLayout", "()Lorg/jetbrains/kotlin/konan/library/TargetedKotlinLibraryLayout;", "addIncludedBinary", "", "library", "", "kotlin-native-utils"}, k = 1, mv = {1, 5, 1}, xi = 48)
public class TargetedWriterImpl {
    private final TargetedKotlinLibraryLayout targetLayout;

    public TargetedWriterImpl(TargetedKotlinLibraryLayout targetLayout) {
        Intrinsics.checkNotNullParameter(targetLayout, "targetLayout");
        this.targetLayout = targetLayout;
        targetLayout.getTargetDir().mkdirs();
        targetLayout.getIncludedDir().mkdirs();
    }

    public final TargetedKotlinLibraryLayout getTargetLayout() {
        return this.targetLayout;
    }

    public final void addIncludedBinary(String library) throws IOException {
        Intrinsics.checkNotNullParameter(library, "library");
        new File(library).copyTo(new File(this.targetLayout.getIncludedDir(), new File(library).getName()));
    }
}
