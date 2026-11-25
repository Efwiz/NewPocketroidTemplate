package org.jetbrains.kotlin.load.java.structure.impl.classFiles;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import org.jetbrains.org.objectweb.asm.ClassReader;
import org.jetbrains.org.objectweb.asm.ClassVisitor;

/* JADX INFO: compiled from: Other.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0012\n\u0000\u001a\u000e\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003¨\u0006\u0004"}, d2 = {"isNotTopLevelClass", "", "classContent", "", "resolution.common.jvm"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class OtherKt {
    public static final boolean isNotTopLevelClass(byte[] classContent) {
        Intrinsics.checkNotNullParameter(classContent, "classContent");
        final Ref.BooleanRef booleanRef = new Ref.BooleanRef();
        new ClassReader(classContent).accept(new ClassVisitor() { // from class: org.jetbrains.kotlin.load.java.structure.impl.classFiles.OtherKt.isNotTopLevelClass.1
            private String internalName;

            {
                super(589824);
            }

            @Override // org.jetbrains.org.objectweb.asm.ClassVisitor
            public void visit(int version, int access, String name2, String signature, String superName, String[] interfaces) {
                this.internalName = name2;
            }

            @Override // org.jetbrains.org.objectweb.asm.ClassVisitor
            public void visitInnerClass(String name2, String outerName, String innerName, int access) {
                if (Intrinsics.areEqual(name2, this.internalName)) {
                    if (innerName == null || Intrinsics.areEqual(name2, outerName + '$' + innerName)) {
                        booleanRef.element = true;
                    }
                }
            }
        }, 7);
        return booleanRef.element;
    }
}
