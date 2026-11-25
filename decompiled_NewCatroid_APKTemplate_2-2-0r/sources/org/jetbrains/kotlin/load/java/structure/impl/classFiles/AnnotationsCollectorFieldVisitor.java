package org.jetbrains.kotlin.load.java.structure.impl.classFiles;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.load.java.structure.JavaType;
import org.jetbrains.kotlin.load.java.structure.MutableJavaAnnotationOwner;
import org.jetbrains.org.objectweb.asm.AnnotationVisitor;
import org.jetbrains.org.objectweb.asm.FieldVisitor;
import org.jetbrains.org.objectweb.asm.TypePath;
import org.jetbrains.org.objectweb.asm.TypeReference;

/* JADX INFO: compiled from: Annotations.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0000\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0018\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016J.\u0010\u000f\u001a\u0004\u0018\u00010\n2\u0006\u0010\u0010\u001a\u00020\u00112\b\u0010\u0012\u001a\u0004\u0018\u00010\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/load/java/structure/impl/classFiles/AnnotationsCollectorFieldVisitor;", "Lorg/jetbrains/org/objectweb/asm/FieldVisitor;", "field", "Lorg/jetbrains/kotlin/load/java/structure/impl/classFiles/BinaryJavaField;", "context", "Lorg/jetbrains/kotlin/load/java/structure/impl/classFiles/ClassifierResolutionContext;", "signatureParser", "Lorg/jetbrains/kotlin/load/java/structure/impl/classFiles/BinaryClassSignatureParser;", "(Lorg/jetbrains/kotlin/load/java/structure/impl/classFiles/BinaryJavaField;Lorg/jetbrains/kotlin/load/java/structure/impl/classFiles/ClassifierResolutionContext;Lorg/jetbrains/kotlin/load/java/structure/impl/classFiles/BinaryClassSignatureParser;)V", "visitAnnotation", "Lorg/jetbrains/org/objectweb/asm/AnnotationVisitor;", "desc", "", "visible", "", "visitTypeAnnotation", "typeRef", "", "typePath", "Lorg/jetbrains/org/objectweb/asm/TypePath;", "descriptor", "resolution.common.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class AnnotationsCollectorFieldVisitor extends FieldVisitor {
    private final ClassifierResolutionContext context;
    private final BinaryJavaField field;
    private final BinaryClassSignatureParser signatureParser;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AnnotationsCollectorFieldVisitor(BinaryJavaField field, ClassifierResolutionContext context, BinaryClassSignatureParser signatureParser) {
        super(589824);
        Intrinsics.checkNotNullParameter(field, "field");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(signatureParser, "signatureParser");
        this.field = field;
        this.context = context;
        this.signatureParser = signatureParser;
    }

    @Override // org.jetbrains.org.objectweb.asm.FieldVisitor
    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        Intrinsics.checkNotNullParameter(desc, "desc");
        return BinaryJavaAnnotation.Companion.addAnnotation$default(BinaryJavaAnnotation.INSTANCE, this.field, desc, this.context, this.signatureParser, false, 16, null);
    }

    @Override // org.jetbrains.org.objectweb.asm.FieldVisitor
    public AnnotationVisitor visitTypeAnnotation(int typeRef, TypePath typePath, String descriptor, boolean visible) {
        if (descriptor == null) {
            return null;
        }
        TypeReference typeReference = new TypeReference(typeRef);
        JavaType javaTypeComputeTargetType$resolution_common_jvm = typePath != null ? BinaryJavaAnnotation.INSTANCE.computeTargetType$resolution_common_jvm(this.field.getType(), typePath) : this.field.getType();
        if ((javaTypeComputeTargetType$resolution_common_jvm instanceof MutableJavaAnnotationOwner) && typeReference.getSort() == 19) {
            return BinaryJavaAnnotation.INSTANCE.addAnnotation((MutableJavaAnnotationOwner) javaTypeComputeTargetType$resolution_common_jvm, descriptor, this.context, this.signatureParser, true);
        }
        return null;
    }
}
