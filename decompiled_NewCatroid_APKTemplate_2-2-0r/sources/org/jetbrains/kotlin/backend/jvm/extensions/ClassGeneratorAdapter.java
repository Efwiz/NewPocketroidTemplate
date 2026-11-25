package org.jetbrains.kotlin.backend.jvm.extensions;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrUtilsKt;
import org.jetbrains.kotlin.codegen.ClassBuilder;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.org.objectweb.asm.AnnotationVisitor;
import org.jetbrains.org.objectweb.asm.FieldVisitor;
import org.jetbrains.org.objectweb.asm.MethodVisitor;
import org.jetbrains.org.objectweb.asm.RecordComponentVisitor;
import org.mozilla.javascript.ES6Iterator;

/* JADX INFO: compiled from: ClassBuilderExtensionAdapter.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000j\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\b\u0002\u0018\u00002\u00020\u0001B\u0017\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006JG\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\u0010\u001a\u00020\u00112\b\u0010\u0012\u001a\u0004\u0018\u00010\u00112\u0006\u0010\u0013\u001a\u00020\u00112\u000e\u0010\u0014\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00110\u0015H\u0016¢\u0006\u0002\u0010\u0016J\u0010\u0010\u0017\u001a\u00020\f2\u0006\u0010\u0018\u001a\u00020\u0019H\u0016J>\u0010\u001a\u001a\u00020\u001b2\b\u0010\u001c\u001a\u0004\u0018\u00010\u001d2\u0006\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u001e\u001a\u00020\u00112\b\u0010\u0012\u001a\u0004\u0018\u00010\u00112\b\u0010\u001f\u001a\u0004\u0018\u00010 H\u0016JK\u0010!\u001a\u00020\"2\b\u0010\u001c\u001a\u0004\u0018\u00010#2\u0006\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u001e\u001a\u00020\u00112\b\u0010\u0012\u001a\u0004\u0018\u00010\u00112\u0010\u0010$\u001a\f\u0012\u0006\b\u0001\u0012\u00020\u0011\u0018\u00010\u0015H\u0016¢\u0006\u0002\u0010%J\"\u0010&\u001a\u00020'2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u001e\u001a\u00020\u00112\b\u0010\u0012\u001a\u0004\u0018\u00010\u0011H\u0016J\u0018\u0010(\u001a\u00020)2\u0006\u0010\u001e\u001a\u00020\u00112\u0006\u0010*\u001a\u00020\u0019H\u0016J$\u0010+\u001a\u00020\f2\u0006\u0010,\u001a\u00020\u00112\b\u0010\u0010\u001a\u0004\u0018\u00010\u00112\b\u0010\u001e\u001a\u0004\u0018\u00010\u0011H\u0016J,\u0010-\u001a\u00020\f2\u0006\u0010\u0010\u001a\u00020\u00112\b\u0010.\u001a\u0004\u0018\u00010\u00112\b\u0010/\u001a\u0004\u0018\u00010\u00112\u0006\u0010\u000f\u001a\u00020\u000eH\u0016J\u001a\u00100\u001a\u00020\f2\u0006\u0010\u0010\u001a\u00020\u00112\b\u00101\u001a\u0004\u0018\u00010\u0011H\u0016R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u00062"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/extensions/ClassGeneratorAdapter;", "Lorg/jetbrains/kotlin/backend/jvm/extensions/ClassGenerator;", "irClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "builder", "Lorg/jetbrains/kotlin/codegen/ClassBuilder;", "(Lorg/jetbrains/kotlin/ir/declarations/IrClass;Lorg/jetbrains/kotlin/codegen/ClassBuilder;)V", "getBuilder", "()Lorg/jetbrains/kotlin/codegen/ClassBuilder;", "getIrClass", "()Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "defineClass", "", "version", "", "access", "name", "", "signature", "superName", Namer.METADATA_SUPERTYPES, "", "(IILjava/lang/String;Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;)V", ES6Iterator.DONE_PROPERTY, "generateSmapCopyToAnnotation", "", "newField", "Lorg/jetbrains/org/objectweb/asm/FieldVisitor;", "declaration", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "desc", "value", "", "newMethod", "Lorg/jetbrains/org/objectweb/asm/MethodVisitor;", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "exceptions", "(Lorg/jetbrains/kotlin/ir/declarations/IrFunction;ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;)Lorg/jetbrains/org/objectweb/asm/MethodVisitor;", "newRecordComponent", "Lorg/jetbrains/org/objectweb/asm/RecordComponentVisitor;", "visitAnnotation", "Lorg/jetbrains/org/objectweb/asm/AnnotationVisitor;", "visible", "visitEnclosingMethod", "owner", "visitInnerClass", "outerName", "innerName", "visitSource", "debug", "backend.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class ClassGeneratorAdapter implements ClassGenerator {
    private final ClassBuilder builder;
    private final IrClass irClass;

    public ClassGeneratorAdapter(IrClass irClass, ClassBuilder builder) {
        Intrinsics.checkNotNullParameter(builder, "builder");
        this.irClass = irClass;
        this.builder = builder;
    }

    public final ClassBuilder getBuilder() {
        return this.builder;
    }

    public final IrClass getIrClass() {
        return this.irClass;
    }

    @Override // org.jetbrains.kotlin.backend.jvm.extensions.ClassGenerator
    public void defineClass(int version, int access, String name2, String signature, String superName, String[] interfaces) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(superName, "superName");
        Intrinsics.checkNotNullParameter(interfaces, "interfaces");
        ClassBuilder classBuilder = this.builder;
        IrClass irClass = this.irClass;
        classBuilder.defineClass(irClass != null ? JvmIrUtilsKt.getPsiElement(irClass) : null, version, access, name2, signature, superName, interfaces);
    }

    @Override // org.jetbrains.kotlin.backend.jvm.extensions.ClassGenerator
    public FieldVisitor newField(IrField declaration, int access, String name2, String desc, String signature, Object value) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(desc, "desc");
        FieldVisitor fieldVisitorNewField = this.builder.newField(ClassBuilderExtensionAdapterKt.wrapToOrigin(declaration), access, name2, desc, signature, value);
        Intrinsics.checkNotNullExpressionValue(fieldVisitorNewField, "newField(...)");
        return fieldVisitorNewField;
    }

    @Override // org.jetbrains.kotlin.backend.jvm.extensions.ClassGenerator
    public MethodVisitor newMethod(IrFunction declaration, int access, String name2, String desc, String signature, String[] exceptions) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(desc, "desc");
        MethodVisitor methodVisitorNewMethod = this.builder.newMethod(ClassBuilderExtensionAdapterKt.wrapToOrigin(declaration), access, name2, desc, signature, exceptions);
        Intrinsics.checkNotNullExpressionValue(methodVisitorNewMethod, "newMethod(...)");
        return methodVisitorNewMethod;
    }

    @Override // org.jetbrains.kotlin.backend.jvm.extensions.ClassGenerator
    public RecordComponentVisitor newRecordComponent(String name2, String desc, String signature) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(desc, "desc");
        RecordComponentVisitor recordComponentVisitorNewRecordComponent = this.builder.newRecordComponent(name2, desc, signature);
        Intrinsics.checkNotNullExpressionValue(recordComponentVisitorNewRecordComponent, "newRecordComponent(...)");
        return recordComponentVisitorNewRecordComponent;
    }

    @Override // org.jetbrains.kotlin.backend.jvm.extensions.ClassGenerator
    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        Intrinsics.checkNotNullParameter(desc, "desc");
        AnnotationVisitor annotationVisitorNewAnnotation = this.builder.newAnnotation(desc, visible);
        Intrinsics.checkNotNullExpressionValue(annotationVisitorNewAnnotation, "newAnnotation(...)");
        return annotationVisitorNewAnnotation;
    }

    @Override // org.jetbrains.kotlin.backend.jvm.extensions.ClassGenerator
    public void visitInnerClass(String name2, String outerName, String innerName, int access) {
        Intrinsics.checkNotNullParameter(name2, "name");
        this.builder.visitInnerClass(name2, outerName, innerName, access);
    }

    @Override // org.jetbrains.kotlin.backend.jvm.extensions.ClassGenerator
    public void visitEnclosingMethod(String owner, String name2, String desc) {
        Intrinsics.checkNotNullParameter(owner, "owner");
        this.builder.visitOuterClass(owner, name2, desc);
    }

    @Override // org.jetbrains.kotlin.backend.jvm.extensions.ClassGenerator
    public void visitSource(String name2, String debug) {
        Intrinsics.checkNotNullParameter(name2, "name");
        this.builder.visitSource(name2, debug);
    }

    @Override // org.jetbrains.kotlin.backend.jvm.extensions.ClassGenerator
    public void done(boolean generateSmapCopyToAnnotation) {
        this.builder.done(generateSmapCopyToAnnotation);
    }
}
