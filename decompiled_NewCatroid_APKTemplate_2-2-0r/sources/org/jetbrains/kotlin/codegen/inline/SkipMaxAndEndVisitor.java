package org.jetbrains.kotlin.codegen.inline;

import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;
import org.jetbrains.org.objectweb.asm.MethodVisitor;
import org.jetbrains.org.objectweb.asm.commons.InstructionAdapter;

/* JADX INFO: compiled from: MethodBodyVisitor.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0016\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u0005\u001a\u00020\u0006H\u0016J\u0018\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\tH\u0016¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/codegen/inline/SkipMaxAndEndVisitor;", "Lorg/jetbrains/org/objectweb/asm/commons/InstructionAdapter;", JvmAnnotationNames.METADATA_VERSION_FIELD_NAME, "Lorg/jetbrains/org/objectweb/asm/MethodVisitor;", "(Lorg/jetbrains/org/objectweb/asm/MethodVisitor;)V", "visitEnd", "", "visitMaxs", "maxStack", "", "maxLocals", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public class SkipMaxAndEndVisitor extends InstructionAdapter {
    @Override // org.jetbrains.org.objectweb.asm.MethodVisitor
    public void visitEnd() {
    }

    @Override // org.jetbrains.org.objectweb.asm.MethodVisitor
    public void visitMaxs(int maxStack, int maxLocals) {
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SkipMaxAndEndVisitor(MethodVisitor mv) {
        super(589824, mv);
        Intrinsics.checkNotNullParameter(mv, "mv");
    }
}
