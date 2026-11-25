package kotlin.reflect.jvm.internal.impl.load.kotlin;

import kotlin.reflect.jvm.internal.impl.descriptors.SourceElement;
import kotlin.reflect.jvm.internal.impl.load.kotlin.header.KotlinClassHeader;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.constants.ClassLiteralValue;

/* JADX INFO: compiled from: KotlinJvmBinaryClass.kt */
/* JADX INFO: loaded from: classes12.dex */
public interface KotlinJvmBinaryClass {

    /* JADX INFO: compiled from: KotlinJvmBinaryClass.kt */
    public interface AnnotationArgumentVisitor {
        void visit(Name name2, Object obj);

        AnnotationArgumentVisitor visitAnnotation(Name name2, ClassId classId);

        AnnotationArrayArgumentVisitor visitArray(Name name2);

        void visitClassLiteral(Name name2, ClassLiteralValue classLiteralValue);

        void visitEnd();

        void visitEnum(Name name2, ClassId classId, Name name3);
    }

    /* JADX INFO: compiled from: KotlinJvmBinaryClass.kt */
    public interface AnnotationArrayArgumentVisitor {
        void visit(Object obj);

        AnnotationArgumentVisitor visitAnnotation(ClassId classId);

        void visitClassLiteral(ClassLiteralValue classLiteralValue);

        void visitEnd();

        void visitEnum(ClassId classId, Name name2);
    }

    /* JADX INFO: compiled from: KotlinJvmBinaryClass.kt */
    public interface AnnotationVisitor {
        AnnotationArgumentVisitor visitAnnotation(ClassId classId, SourceElement sourceElement);

        void visitEnd();
    }

    /* JADX INFO: compiled from: KotlinJvmBinaryClass.kt */
    public interface MemberVisitor {
        AnnotationVisitor visitField(Name name2, String str, Object obj);

        MethodAnnotationVisitor visitMethod(Name name2, String str);
    }

    /* JADX INFO: compiled from: KotlinJvmBinaryClass.kt */
    public interface MethodAnnotationVisitor extends AnnotationVisitor {
        AnnotationArgumentVisitor visitParameterAnnotation(int i, ClassId classId, SourceElement sourceElement);
    }

    KotlinClassHeader getClassHeader();

    ClassId getClassId();

    String getLocation();

    void loadClassAnnotations(AnnotationVisitor annotationVisitor, byte[] bArr);

    void visitMembers(MemberVisitor memberVisitor, byte[] bArr);
}
