package org.jetbrains.kotlin.incremental.classpathDiff;

import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ClassFile.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0012\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u001b\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\u0002\u0010\u0007J\u0006\u0010\f\u001a\u00020\rR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/incremental/classpathDiff/ClassFileWithContentsProvider;", "", "classFile", "Lorg/jetbrains/kotlin/incremental/classpathDiff/ClassFile;", "contentsProvider", "Lkotlin/Function0;", "", "(Lorg/jetbrains/kotlin/incremental/classpathDiff/ClassFile;Lkotlin/jvm/functions/Function0;)V", "getClassFile", "()Lorg/jetbrains/kotlin/incremental/classpathDiff/ClassFile;", "getContentsProvider", "()Lkotlin/jvm/functions/Function0;", "loadContents", "Lorg/jetbrains/kotlin/incremental/classpathDiff/ClassFileWithContents;", "incremental-compilation-impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ClassFileWithContentsProvider {
    private final ClassFile classFile;
    private final Function0<byte[]> contentsProvider;

    public ClassFileWithContentsProvider(ClassFile classFile, Function0<byte[]> contentsProvider) {
        Intrinsics.checkNotNullParameter(classFile, "classFile");
        Intrinsics.checkNotNullParameter(contentsProvider, "contentsProvider");
        this.classFile = classFile;
        this.contentsProvider = contentsProvider;
    }

    public final ClassFile getClassFile() {
        return this.classFile;
    }

    public final Function0<byte[]> getContentsProvider() {
        return this.contentsProvider;
    }

    public final ClassFileWithContents loadContents() {
        return new ClassFileWithContents(this.classFile, this.contentsProvider.invoke());
    }
}
