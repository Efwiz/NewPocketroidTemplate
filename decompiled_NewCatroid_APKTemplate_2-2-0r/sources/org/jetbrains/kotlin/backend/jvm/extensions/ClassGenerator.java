package org.jetbrains.kotlin.backend.jvm.extensions;

import kotlin.Metadata;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.org.objectweb.asm.AnnotationVisitor;
import org.jetbrains.org.objectweb.asm.FieldVisitor;
import org.jetbrains.org.objectweb.asm.MethodVisitor;
import org.jetbrains.org.objectweb.asm.RecordComponentVisitor;
import org.mozilla.javascript.ES6Iterator;

/* JADX INFO: compiled from: ClassGeneratorExtension.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\bf\u0018\u00002\u00020\u0001JG\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\b2\u0006\u0010\n\u001a\u00020\b2\u000e\u0010\u000b\u001a\n\u0012\u0006\b\u0001\u0012\u00020\b0\fH&¢\u0006\u0002\u0010\rJ\u0010\u0010\u000e\u001a\u00020\u00032\u0006\u0010\u000f\u001a\u00020\u0010H&J>\u0010\u0011\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u00142\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0015\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\b2\b\u0010\u0016\u001a\u0004\u0018\u00010\u0001H&JK\u0010\u0017\u001a\u00020\u00182\b\u0010\u0013\u001a\u0004\u0018\u00010\u00192\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0015\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\b2\u0010\u0010\u001a\u001a\f\u0012\u0006\b\u0001\u0012\u00020\b\u0018\u00010\fH&¢\u0006\u0002\u0010\u001bJ\"\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0015\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\bH&J\u0018\u0010\u001e\u001a\u00020\u001f2\u0006\u0010\u0015\u001a\u00020\b2\u0006\u0010 \u001a\u00020\u0010H&J$\u0010!\u001a\u00020\u00032\u0006\u0010\"\u001a\u00020\b2\b\u0010\u0007\u001a\u0004\u0018\u00010\b2\b\u0010\u0015\u001a\u0004\u0018\u00010\bH&J,\u0010#\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\b2\b\u0010$\u001a\u0004\u0018\u00010\b2\b\u0010%\u001a\u0004\u0018\u00010\b2\u0006\u0010\u0006\u001a\u00020\u0005H&J\u001a\u0010&\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\b2\b\u0010'\u001a\u0004\u0018\u00010\bH&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006(À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/extensions/ClassGenerator;", "", "defineClass", "", "version", "", "access", "name", "", "signature", "superName", Namer.METADATA_SUPERTYPES, "", "(IILjava/lang/String;Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;)V", ES6Iterator.DONE_PROPERTY, "generateSmapCopyToAnnotation", "", "newField", "Lorg/jetbrains/org/objectweb/asm/FieldVisitor;", "declaration", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "desc", "value", "newMethod", "Lorg/jetbrains/org/objectweb/asm/MethodVisitor;", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "exceptions", "(Lorg/jetbrains/kotlin/ir/declarations/IrFunction;ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;)Lorg/jetbrains/org/objectweb/asm/MethodVisitor;", "newRecordComponent", "Lorg/jetbrains/org/objectweb/asm/RecordComponentVisitor;", "visitAnnotation", "Lorg/jetbrains/org/objectweb/asm/AnnotationVisitor;", "visible", "visitEnclosingMethod", "owner", "visitInnerClass", "outerName", "innerName", "visitSource", "debug", "backend.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface ClassGenerator {
    void defineClass(int version, int access, String name2, String signature, String superName, String[] interfaces);

    void done(boolean generateSmapCopyToAnnotation);

    FieldVisitor newField(IrField declaration, int access, String name2, String desc, String signature, Object value);

    MethodVisitor newMethod(IrFunction declaration, int access, String name2, String desc, String signature, String[] exceptions);

    RecordComponentVisitor newRecordComponent(String name2, String desc, String signature);

    AnnotationVisitor visitAnnotation(String desc, boolean visible);

    void visitEnclosingMethod(String owner, String name2, String desc);

    void visitInnerClass(String name2, String outerName, String innerName, int access);

    void visitSource(String name2, String debug);
}
