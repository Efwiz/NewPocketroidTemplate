package org.jetbrains.kotlin.backend.jvm.extensions;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.ClassBuilder;
import org.jetbrains.kotlin.codegen.DelegatingClassBuilder;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.JvmDeclarationOrigin;
import org.jetbrains.org.objectweb.asm.AnnotationVisitor;
import org.jetbrains.org.objectweb.asm.FieldVisitor;
import org.jetbrains.org.objectweb.asm.MethodVisitor;
import org.jetbrains.org.objectweb.asm.RecordComponentVisitor;
import org.mozilla.javascript.ES6Iterator;

/* JADX INFO: compiled from: ClassBuilderExtensionAdapter.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000l\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006JQ\u0010\u0007\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\f2\u0006\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\u0011\u001a\u00020\u000f2\u000e\u0010\u0012\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u000f0\u0013H\u0016¢\u0006\u0002\u0010\u0014J\u0010\u0010\u0015\u001a\u00020\b2\u0006\u0010\u0016\u001a\u00020\u0017H\u0016J\b\u0010\u0018\u001a\u00020\u0005H\u0014J\u0018\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u000f2\u0006\u0010\u001c\u001a\u00020\u0017H\u0016J<\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\t\u001a\u00020\u001f2\u0006\u0010\r\u001a\u00020\f2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u001b\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u000f2\b\u0010 \u001a\u0004\u0018\u00010!H\u0016JI\u0010\"\u001a\u00020#2\u0006\u0010\t\u001a\u00020\u001f2\u0006\u0010\r\u001a\u00020\f2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u001b\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u000f2\u0010\u0010$\u001a\f\u0012\u0006\b\u0001\u0012\u00020\u000f\u0018\u00010\u0013H\u0016¢\u0006\u0002\u0010%J\"\u0010&\u001a\u00020'2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u001b\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u000fH\u0016J,\u0010(\u001a\u00020\b2\u0006\u0010\u000e\u001a\u00020\u000f2\b\u0010)\u001a\u0004\u0018\u00010\u000f2\b\u0010*\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\r\u001a\u00020\fH\u0016J$\u0010+\u001a\u00020\b2\u0006\u0010,\u001a\u00020\u000f2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\b\u0010\u001b\u001a\u0004\u0018\u00010\u000fH\u0016J\u001a\u0010-\u001a\u00020\b2\u0006\u0010\u000e\u001a\u00020\u000f2\b\u0010.\u001a\u0004\u0018\u00010\u000fH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006/"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/extensions/DelegatingClassBuilderAdapter;", "Lorg/jetbrains/kotlin/codegen/DelegatingClassBuilder;", "generator", "Lorg/jetbrains/kotlin/backend/jvm/extensions/ClassGenerator;", "originalClassBuilder", "Lorg/jetbrains/kotlin/codegen/ClassBuilder;", "(Lorg/jetbrains/kotlin/backend/jvm/extensions/ClassGenerator;Lorg/jetbrains/kotlin/codegen/ClassBuilder;)V", "defineClass", "", "origin", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "version", "", "access", "name", "", "signature", "superName", Namer.METADATA_SUPERTYPES, "", "(Lcom/intellij/psi/PsiElement;IILjava/lang/String;Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;)V", ES6Iterator.DONE_PROPERTY, "generateSmapCopyToAnnotation", "", "getDelegate", "newAnnotation", "Lorg/jetbrains/org/objectweb/asm/AnnotationVisitor;", "desc", "visible", "newField", "Lorg/jetbrains/org/objectweb/asm/FieldVisitor;", "Lorg/jetbrains/kotlin/resolve/jvm/diagnostics/JvmDeclarationOrigin;", "value", "", "newMethod", "Lorg/jetbrains/org/objectweb/asm/MethodVisitor;", "exceptions", "(Lorg/jetbrains/kotlin/resolve/jvm/diagnostics/JvmDeclarationOrigin;ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;)Lorg/jetbrains/org/objectweb/asm/MethodVisitor;", "newRecordComponent", "Lorg/jetbrains/org/objectweb/asm/RecordComponentVisitor;", "visitInnerClass", "outerName", "innerName", "visitOuterClass", "owner", "visitSource", "debug", "backend.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class DelegatingClassBuilderAdapter extends DelegatingClassBuilder {
    private final ClassGenerator generator;
    private final ClassBuilder originalClassBuilder;

    public DelegatingClassBuilderAdapter(ClassGenerator generator, ClassBuilder originalClassBuilder) {
        Intrinsics.checkNotNullParameter(generator, "generator");
        Intrinsics.checkNotNullParameter(originalClassBuilder, "originalClassBuilder");
        this.generator = generator;
        this.originalClassBuilder = originalClassBuilder;
    }

    @Override // org.jetbrains.kotlin.codegen.DelegatingClassBuilder
    /* JADX INFO: renamed from: getDelegate, reason: from getter */
    protected ClassBuilder get_delegate() {
        return this.originalClassBuilder;
    }

    @Override // org.jetbrains.kotlin.codegen.DelegatingClassBuilder, org.jetbrains.kotlin.codegen.ClassBuilder
    public void defineClass(PsiElement psiElement, int i, int i2, String name2, String str, String superName, String[] interfaces) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(superName, "superName");
        Intrinsics.checkNotNullParameter(interfaces, "interfaces");
        this.generator.defineClass(i, i2, name2, str, superName, interfaces);
    }

    @Override // org.jetbrains.kotlin.codegen.DelegatingClassBuilder, org.jetbrains.kotlin.codegen.ClassBuilder
    public FieldVisitor newField(JvmDeclarationOrigin origin, int access, String name2, String desc, String signature, Object value) {
        Intrinsics.checkNotNullParameter(origin, "origin");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(desc, "desc");
        ClassGenerator classGenerator = this.generator;
        JvmIrDeclarationOrigin jvmIrDeclarationOrigin = origin instanceof JvmIrDeclarationOrigin ? (JvmIrDeclarationOrigin) origin : null;
        IrDeclaration declaration = jvmIrDeclarationOrigin != null ? jvmIrDeclarationOrigin.getDeclaration() : null;
        return classGenerator.newField((IrField) (declaration instanceof IrField ? declaration : null), access, name2, desc, signature, value);
    }

    @Override // org.jetbrains.kotlin.codegen.DelegatingClassBuilder, org.jetbrains.kotlin.codegen.ClassBuilder
    public MethodVisitor newMethod(JvmDeclarationOrigin origin, int access, String name2, String desc, String signature, String[] exceptions) {
        Intrinsics.checkNotNullParameter(origin, "origin");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(desc, "desc");
        ClassGenerator classGenerator = this.generator;
        JvmIrDeclarationOrigin jvmIrDeclarationOrigin = origin instanceof JvmIrDeclarationOrigin ? (JvmIrDeclarationOrigin) origin : null;
        IrDeclaration declaration = jvmIrDeclarationOrigin != null ? jvmIrDeclarationOrigin.getDeclaration() : null;
        return classGenerator.newMethod((IrFunction) (declaration instanceof IrFunction ? declaration : null), access, name2, desc, signature, exceptions);
    }

    @Override // org.jetbrains.kotlin.codegen.DelegatingClassBuilder, org.jetbrains.kotlin.codegen.ClassBuilder
    public RecordComponentVisitor newRecordComponent(String name2, String desc, String signature) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(desc, "desc");
        return this.generator.newRecordComponent(name2, desc, signature);
    }

    @Override // org.jetbrains.kotlin.codegen.DelegatingClassBuilder, org.jetbrains.kotlin.codegen.ClassBuilder
    public AnnotationVisitor newAnnotation(String desc, boolean visible) {
        Intrinsics.checkNotNullParameter(desc, "desc");
        return this.generator.visitAnnotation(desc, visible);
    }

    @Override // org.jetbrains.kotlin.codegen.DelegatingClassBuilder, org.jetbrains.kotlin.codegen.ClassBuilder
    public void visitOuterClass(String owner, String name2, String desc) {
        Intrinsics.checkNotNullParameter(owner, "owner");
        this.generator.visitEnclosingMethod(owner, name2, desc);
    }

    @Override // org.jetbrains.kotlin.codegen.DelegatingClassBuilder, org.jetbrains.kotlin.codegen.ClassBuilder
    public void visitInnerClass(String name2, String outerName, String innerName, int access) {
        Intrinsics.checkNotNullParameter(name2, "name");
        this.generator.visitInnerClass(name2, outerName, innerName, access);
    }

    @Override // org.jetbrains.kotlin.codegen.DelegatingClassBuilder, org.jetbrains.kotlin.codegen.ClassBuilder
    public void visitSource(String name2, String debug) {
        Intrinsics.checkNotNullParameter(name2, "name");
        this.generator.visitSource(name2, debug);
    }

    @Override // org.jetbrains.kotlin.codegen.DelegatingClassBuilder, org.jetbrains.kotlin.codegen.ClassBuilder
    public void done(boolean generateSmapCopyToAnnotation) {
        this.generator.done(generateSmapCopyToAnnotation);
    }
}
