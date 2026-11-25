package org.jetbrains.kotlin.incremental.classpathDiff;

import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ClassFile.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u001b\u0010\t\u001a\u00020\n8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\r\u0010\u000e\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/incremental/classpathDiff/ClassFileWithContents;", "", "classFile", "Lorg/jetbrains/kotlin/incremental/classpathDiff/ClassFile;", "contents", "", "(Lorg/jetbrains/kotlin/incremental/classpathDiff/ClassFile;[B)V", "getClassFile", "()Lorg/jetbrains/kotlin/incremental/classpathDiff/ClassFile;", "classInfo", "Lorg/jetbrains/kotlin/incremental/classpathDiff/BasicClassInfo;", "getClassInfo", "()Lorg/jetbrains/kotlin/incremental/classpathDiff/BasicClassInfo;", "classInfo$delegate", "Lkotlin/Lazy;", "getContents", "()[B", "incremental-compilation-impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ClassFileWithContents {
    private final ClassFile classFile;

    /* JADX INFO: renamed from: classInfo$delegate, reason: from kotlin metadata */
    private final Lazy classInfo;
    private final byte[] contents;

    public ClassFileWithContents(ClassFile classFile, byte[] contents) {
        Intrinsics.checkNotNullParameter(classFile, "classFile");
        Intrinsics.checkNotNullParameter(contents, "contents");
        this.classFile = classFile;
        this.contents = contents;
        this.classInfo = LazyKt.lazy(new Function0<BasicClassInfo>() { // from class: org.jetbrains.kotlin.incremental.classpathDiff.ClassFileWithContents$classInfo$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final BasicClassInfo invoke() {
                return BasicClassInfo.INSTANCE.compute(this.this$0.getContents());
            }
        });
    }

    public final ClassFile getClassFile() {
        return this.classFile;
    }

    public final byte[] getContents() {
        return this.contents;
    }

    public final BasicClassInfo getClassInfo() {
        return (BasicClassInfo) this.classInfo.getValue();
    }
}
