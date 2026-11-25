package org.jetbrains.kotlin.incremental.classpathDiff;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.load.kotlin.FileBasedKotlinClass;
import org.jetbrains.org.objectweb.asm.ClassVisitor;

/* JADX INFO: compiled from: BasicClassInfo.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0001¢\u0006\u0002\u0010\u0003J\u0006\u0010\u0006\u001a\u00020\u0005J,\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\n2\b\u0010\f\u001a\u0004\u0018\u00010\n2\u0006\u0010\r\u001a\u00020\u000eH\u0016R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/incremental/classpathDiff/InnerClassesClassVisitor;", "Lorg/jetbrains/org/objectweb/asm/ClassVisitor;", "cv", "(Lorg/jetbrains/org/objectweb/asm/ClassVisitor;)V", "innerClassesInfo", "Lorg/jetbrains/kotlin/load/kotlin/FileBasedKotlinClass$InnerClassesInfo;", "getInnerClassesInfo", "visitInnerClass", "", "name", "", "outerName", "innerName", "access", "", "incremental-compilation-impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class InnerClassesClassVisitor extends ClassVisitor {
    private final FileBasedKotlinClass.InnerClassesInfo innerClassesInfo;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public InnerClassesClassVisitor(ClassVisitor cv) {
        super(589824, cv);
        Intrinsics.checkNotNullParameter(cv, "cv");
        this.innerClassesInfo = new FileBasedKotlinClass.InnerClassesInfo();
    }

    @Override // org.jetbrains.org.objectweb.asm.ClassVisitor
    public void visitInnerClass(String name2, String outerName, String innerName, int access) {
        Intrinsics.checkNotNullParameter(name2, "name");
        this.innerClassesInfo.add(name2, outerName, innerName);
        super.visitInnerClass(name2, outerName, innerName, access);
    }

    public final FileBasedKotlinClass.InnerClassesInfo getInnerClassesInfo() {
        return this.innerClassesInfo;
    }
}
