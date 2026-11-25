package org.jetbrains.kotlin.load.kotlin;

import com.google.firebase.analytics.FirebaseAnalytics;
import kotlin.Metadata;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.load.kotlin.header.KotlinClassHeader;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.constants.ClassLiteralValue;

/* JADX INFO: compiled from: KotlinJvmBinaryClass.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\bf\u0018\u00002\u00020\u0001:\u0005\u0018\u0019\u001a\u001b\u001cJ\u001a\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u0015H&J\u001a\u0010\u0016\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00172\b\u0010\u0014\u001a\u0004\u0018\u00010\u0015H&R\u0012\u0010\u0002\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R\u0012\u0010\u0006\u001a\u00020\u0007X¦\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u0016\u0010\n\u001a\u0004\u0018\u00010\u000b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\rR\u0012\u0010\u000e\u001a\u00020\u000bX¦\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\rø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u001dÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/load/kotlin/KotlinJvmBinaryClass;", "", "classHeader", "Lorg/jetbrains/kotlin/load/kotlin/header/KotlinClassHeader;", "getClassHeader", "()Lorg/jetbrains/kotlin/load/kotlin/header/KotlinClassHeader;", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "getClassId", "()Lorg/jetbrains/kotlin/name/ClassId;", "containingLibrary", "", "getContainingLibrary", "()Ljava/lang/String;", FirebaseAnalytics.Param.LOCATION, "getLocation", "loadClassAnnotations", "", "visitor", "Lorg/jetbrains/kotlin/load/kotlin/KotlinJvmBinaryClass$AnnotationVisitor;", "cachedContents", "", "visitMembers", "Lorg/jetbrains/kotlin/load/kotlin/KotlinJvmBinaryClass$MemberVisitor;", "AnnotationArgumentVisitor", "AnnotationArrayArgumentVisitor", "AnnotationVisitor", "MemberVisitor", "MethodAnnotationVisitor", "deserialization.common.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface KotlinJvmBinaryClass {

    /* JADX INFO: compiled from: KotlinJvmBinaryClass.kt */
    @Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\bf\u0018\u00002\u00020\u0001J\u001c\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u00052\b\u0010\u0006\u001a\u0004\u0018\u00010\u0001H&J\u001c\u0010\u0007\u001a\u0004\u0018\u00010\u00002\b\u0010\u0004\u001a\u0004\u0018\u00010\u00052\u0006\u0010\b\u001a\u00020\tH&J\u0014\u0010\n\u001a\u0004\u0018\u00010\u000b2\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005H&J\u001a\u0010\f\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0006\u001a\u00020\rH&J\b\u0010\u000e\u001a\u00020\u0003H&J\"\u0010\u000f\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0010\u001a\u00020\t2\u0006\u0010\u0011\u001a\u00020\u0005H&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0012À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/load/kotlin/KotlinJvmBinaryClass$AnnotationArgumentVisitor;", "", "visit", "", "name", "Lorg/jetbrains/kotlin/name/Name;", "value", "visitAnnotation", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "visitArray", "Lorg/jetbrains/kotlin/load/kotlin/KotlinJvmBinaryClass$AnnotationArrayArgumentVisitor;", "visitClassLiteral", "Lorg/jetbrains/kotlin/resolve/constants/ClassLiteralValue;", "visitEnd", "visitEnum", "enumClassId", "enumEntryName", "deserialization.common.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public interface AnnotationArgumentVisitor {
        void visit(Name name2, Object value);

        AnnotationArgumentVisitor visitAnnotation(Name name2, ClassId classId);

        AnnotationArrayArgumentVisitor visitArray(Name name2);

        void visitClassLiteral(Name name2, ClassLiteralValue value);

        void visitEnd();

        void visitEnum(Name name2, ClassId enumClassId, Name enumEntryName);
    }

    /* JADX INFO: compiled from: KotlinJvmBinaryClass.kt */
    @Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0012\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u0001H&J\u0012\u0010\u0005\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0007\u001a\u00020\bH&J\u0010\u0010\t\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\nH&J\b\u0010\u000b\u001a\u00020\u0003H&J\u0018\u0010\f\u001a\u00020\u00032\u0006\u0010\r\u001a\u00020\b2\u0006\u0010\u000e\u001a\u00020\u000fH&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0010À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/load/kotlin/KotlinJvmBinaryClass$AnnotationArrayArgumentVisitor;", "", "visit", "", "value", "visitAnnotation", "Lorg/jetbrains/kotlin/load/kotlin/KotlinJvmBinaryClass$AnnotationArgumentVisitor;", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "visitClassLiteral", "Lorg/jetbrains/kotlin/resolve/constants/ClassLiteralValue;", "visitEnd", "visitEnum", "enumClassId", "enumEntryName", "Lorg/jetbrains/kotlin/name/Name;", "deserialization.common.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public interface AnnotationArrayArgumentVisitor {
        void visit(Object value);

        AnnotationArgumentVisitor visitAnnotation(ClassId classId);

        void visitClassLiteral(ClassLiteralValue value);

        void visitEnd();

        void visitEnum(ClassId enumClassId, Name enumEntryName);
    }

    /* JADX INFO: compiled from: KotlinJvmBinaryClass.kt */
    @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u001a\u0010\u0002\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H&J\b\u0010\b\u001a\u00020\tH&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\nÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/load/kotlin/KotlinJvmBinaryClass$AnnotationVisitor;", "", "visitAnnotation", "Lorg/jetbrains/kotlin/load/kotlin/KotlinJvmBinaryClass$AnnotationArgumentVisitor;", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/descriptors/SourceElement;", "visitEnd", "", "deserialization.common.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public interface AnnotationVisitor {
        AnnotationArgumentVisitor visitAnnotation(ClassId classId, SourceElement source);

        void visitEnd();
    }

    /* JADX INFO: compiled from: KotlinJvmBinaryClass.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J$\u0010\u0002\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\b\u0010\b\u001a\u0004\u0018\u00010\u0001H&J\u001a\u0010\t\u001a\u0004\u0018\u00010\n2\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u000bÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/load/kotlin/KotlinJvmBinaryClass$MemberVisitor;", "", "visitField", "Lorg/jetbrains/kotlin/load/kotlin/KotlinJvmBinaryClass$AnnotationVisitor;", "name", "Lorg/jetbrains/kotlin/name/Name;", "desc", "", "initializer", "visitMethod", "Lorg/jetbrains/kotlin/load/kotlin/KotlinJvmBinaryClass$MethodAnnotationVisitor;", "deserialization.common.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public interface MemberVisitor {
        AnnotationVisitor visitField(Name name2, String desc, Object initializer);

        MethodAnnotationVisitor visitMethod(Name name2, String desc);
    }

    /* JADX INFO: compiled from: KotlinJvmBinaryClass.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\n\u0010\u0002\u001a\u0004\u0018\u00010\u0003H&J\"\u0010\u0004\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u000bÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/load/kotlin/KotlinJvmBinaryClass$MethodAnnotationVisitor;", "Lorg/jetbrains/kotlin/load/kotlin/KotlinJvmBinaryClass$AnnotationVisitor;", "visitAnnotationMemberDefaultValue", "Lorg/jetbrains/kotlin/load/kotlin/KotlinJvmBinaryClass$AnnotationArgumentVisitor;", "visitParameterAnnotation", "index", "", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/descriptors/SourceElement;", "deserialization.common.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public interface MethodAnnotationVisitor extends AnnotationVisitor {
        AnnotationArgumentVisitor visitAnnotationMemberDefaultValue();

        AnnotationArgumentVisitor visitParameterAnnotation(int index, ClassId classId, SourceElement source);
    }

    KotlinClassHeader getClassHeader();

    ClassId getClassId();

    default String getContainingLibrary() {
        return null;
    }

    String getLocation();

    void loadClassAnnotations(AnnotationVisitor visitor, byte[] cachedContents);

    void visitMembers(MemberVisitor visitor, byte[] cachedContents);
}
