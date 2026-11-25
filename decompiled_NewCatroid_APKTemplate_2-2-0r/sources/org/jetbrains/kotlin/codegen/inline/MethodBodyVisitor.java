package org.jetbrains.kotlin.codegen.inline;

import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.antlr.stringtemplate.language.ASTExpr;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;
import org.jetbrains.org.objectweb.asm.AnnotationVisitor;
import org.jetbrains.org.objectweb.asm.Attribute;
import org.jetbrains.org.objectweb.asm.MethodVisitor;
import org.jetbrains.org.objectweb.asm.TypePath;

/* JADX INFO: compiled from: MethodBodyVisitor.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0018\u0002\n\u0000\b\u0016\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0001¢\u0006\u0002\u0010\u0003J\u0018\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0016J\u001a\u0010\n\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\b\u001a\u00020\tH\u0016J\n\u0010\u000e\u001a\u0004\u0018\u00010\u000bH\u0016J\u0010\u0010\u000f\u001a\u00020\u00052\u0006\u0010\u0010\u001a\u00020\u0011H\u0016J\b\u0010\u0012\u001a\u00020\u0005H\u0016J\b\u0010\u0013\u001a\u00020\u0005H\u0016J\u0018\u0010\u0014\u001a\u00020\u00052\u0006\u0010\u0015\u001a\u00020\u00072\u0006\u0010\u0016\u001a\u00020\u0007H\u0016J\u0018\u0010\u0017\u001a\u00020\u00052\u0006\u0010\u0018\u001a\u00020\r2\u0006\u0010\u0019\u001a\u00020\u0007H\u0016J\"\u0010\u001a\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\u001b\u001a\u00020\u00072\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\b\u001a\u00020\tH\u0016J,\u0010\u001c\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\u001d\u001a\u00020\u00072\b\u0010\u001e\u001a\u0004\u0018\u00010\u001f2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\b\u001a\u00020\tH\u0016¨\u0006 "}, d2 = {"Lorg/jetbrains/kotlin/codegen/inline/MethodBodyVisitor;", "Lorg/jetbrains/org/objectweb/asm/MethodVisitor;", JvmAnnotationNames.METADATA_VERSION_FIELD_NAME, "(Lorg/jetbrains/org/objectweb/asm/MethodVisitor;)V", "visitAnnotableParameterCount", "", "parameterCount", "", "visible", "", "visitAnnotation", "Lorg/jetbrains/org/objectweb/asm/AnnotationVisitor;", "desc", "", "visitAnnotationDefault", "visitAttribute", ASTExpr.DEFAULT_ATTRIBUTE_NAME_DEPRECATED, "Lorg/jetbrains/org/objectweb/asm/Attribute;", "visitCode", "visitEnd", "visitMaxs", "maxStack", "maxLocals", "visitParameter", "name", "access", "visitParameterAnnotation", "parameter", "visitTypeAnnotation", "typeRef", "typePath", "Lorg/jetbrains/org/objectweb/asm/TypePath;", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public class MethodBodyVisitor extends MethodVisitor {
    @Override // org.jetbrains.org.objectweb.asm.MethodVisitor
    public void visitAnnotableParameterCount(int parameterCount, boolean visible) {
    }

    @Override // org.jetbrains.org.objectweb.asm.MethodVisitor
    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        Intrinsics.checkNotNullParameter(desc, "desc");
        return null;
    }

    @Override // org.jetbrains.org.objectweb.asm.MethodVisitor
    public AnnotationVisitor visitAnnotationDefault() {
        return null;
    }

    @Override // org.jetbrains.org.objectweb.asm.MethodVisitor
    public void visitAttribute(Attribute attr) {
        Intrinsics.checkNotNullParameter(attr, "attr");
    }

    @Override // org.jetbrains.org.objectweb.asm.MethodVisitor
    public void visitCode() {
    }

    @Override // org.jetbrains.org.objectweb.asm.MethodVisitor
    public void visitEnd() {
    }

    @Override // org.jetbrains.org.objectweb.asm.MethodVisitor
    public void visitMaxs(int maxStack, int maxLocals) {
    }

    @Override // org.jetbrains.org.objectweb.asm.MethodVisitor
    public void visitParameter(String name2, int access) {
        Intrinsics.checkNotNullParameter(name2, "name");
    }

    @Override // org.jetbrains.org.objectweb.asm.MethodVisitor
    public AnnotationVisitor visitParameterAnnotation(int parameter, String desc, boolean visible) {
        Intrinsics.checkNotNullParameter(desc, "desc");
        return null;
    }

    @Override // org.jetbrains.org.objectweb.asm.MethodVisitor
    public AnnotationVisitor visitTypeAnnotation(int typeRef, TypePath typePath, String desc, boolean visible) {
        Intrinsics.checkNotNullParameter(desc, "desc");
        return null;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MethodBodyVisitor(MethodVisitor mv) {
        super(589824, mv);
        Intrinsics.checkNotNullParameter(mv, "mv");
    }
}
