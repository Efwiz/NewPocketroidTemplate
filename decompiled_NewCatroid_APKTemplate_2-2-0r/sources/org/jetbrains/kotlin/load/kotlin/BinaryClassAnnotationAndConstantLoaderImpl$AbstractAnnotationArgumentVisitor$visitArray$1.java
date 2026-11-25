package org.jetbrains.kotlin.load.kotlin;

import com.reandroid.archive.PathTree;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.constants.AnnotationValue;
import org.jetbrains.kotlin.resolve.constants.ClassLiteralValue;
import org.jetbrains.kotlin.resolve.constants.ConstantValue;
import org.jetbrains.kotlin.resolve.constants.EnumValue;
import org.jetbrains.kotlin.resolve.constants.KClassValue;

/* JADX INFO: compiled from: BinaryClassAnnotationAndConstantLoaderImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000?\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\u0012\u0010\u0006\u001a\u00020\u00072\b\u0010\b\u001a\u0004\u0018\u00010\tH\u0016J\u0012\u0010\n\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\f\u001a\u00020\rH\u0016J\u0010\u0010\u000e\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u000fH\u0016J\b\u0010\u0010\u001a\u00020\u0007H\u0016J\u0018\u0010\u0011\u001a\u00020\u00072\u0006\u0010\u0012\u001a\u00020\r2\u0006\u0010\u0013\u001a\u00020\u0014H\u0016R&\u0010\u0002\u001a\u001a\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00040\u0003j\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0004`\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0015"}, d2 = {"org/jetbrains/kotlin/load/kotlin/BinaryClassAnnotationAndConstantLoaderImpl$AbstractAnnotationArgumentVisitor$visitArray$1", "Lorg/jetbrains/kotlin/load/kotlin/KotlinJvmBinaryClass$AnnotationArrayArgumentVisitor;", PathTree.NAME_elements, "Ljava/util/ArrayList;", "Lorg/jetbrains/kotlin/resolve/constants/ConstantValue;", "Lkotlin/collections/ArrayList;", "visit", "", "value", "", "visitAnnotation", "Lorg/jetbrains/kotlin/load/kotlin/KotlinJvmBinaryClass$AnnotationArgumentVisitor;", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "visitClassLiteral", "Lorg/jetbrains/kotlin/resolve/constants/ClassLiteralValue;", "visitEnd", "visitEnum", "enumClassId", "enumEntryName", "Lorg/jetbrains/kotlin/name/Name;", "descriptors.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class BinaryClassAnnotationAndConstantLoaderImpl$AbstractAnnotationArgumentVisitor$visitArray$1 implements KotlinJvmBinaryClass.AnnotationArrayArgumentVisitor {
    final /* synthetic */ Name $name;
    private final ArrayList<ConstantValue<?>> elements = new ArrayList<>();
    final /* synthetic */ BinaryClassAnnotationAndConstantLoaderImpl this$0;
    final /* synthetic */ BinaryClassAnnotationAndConstantLoaderImpl.AbstractAnnotationArgumentVisitor this$1;

    BinaryClassAnnotationAndConstantLoaderImpl$AbstractAnnotationArgumentVisitor$visitArray$1(BinaryClassAnnotationAndConstantLoaderImpl binaryClassAnnotationAndConstantLoaderImpl, Name name2, BinaryClassAnnotationAndConstantLoaderImpl.AbstractAnnotationArgumentVisitor abstractAnnotationArgumentVisitor) {
        this.this$0 = binaryClassAnnotationAndConstantLoaderImpl;
        this.$name = name2;
        this.this$1 = abstractAnnotationArgumentVisitor;
    }

    @Override // org.jetbrains.kotlin.load.kotlin.KotlinJvmBinaryClass.AnnotationArrayArgumentVisitor
    public void visit(Object value) {
        this.elements.add(this.this$0.createConstant(this.$name, value));
    }

    @Override // org.jetbrains.kotlin.load.kotlin.KotlinJvmBinaryClass.AnnotationArrayArgumentVisitor
    public void visitEnum(ClassId enumClassId, Name enumEntryName) {
        Intrinsics.checkNotNullParameter(enumClassId, "enumClassId");
        Intrinsics.checkNotNullParameter(enumEntryName, "enumEntryName");
        this.elements.add(new EnumValue(enumClassId, enumEntryName));
    }

    @Override // org.jetbrains.kotlin.load.kotlin.KotlinJvmBinaryClass.AnnotationArrayArgumentVisitor
    public void visitClassLiteral(ClassLiteralValue value) {
        Intrinsics.checkNotNullParameter(value, "value");
        this.elements.add(new KClassValue(value));
    }

    @Override // org.jetbrains.kotlin.load.kotlin.KotlinJvmBinaryClass.AnnotationArrayArgumentVisitor
    public KotlinJvmBinaryClass.AnnotationArgumentVisitor visitAnnotation(ClassId classId) {
        Intrinsics.checkNotNullParameter(classId, "classId");
        final ArrayList arrayList = new ArrayList();
        BinaryClassAnnotationAndConstantLoaderImpl binaryClassAnnotationAndConstantLoaderImpl = this.this$0;
        SourceElement NO_SOURCE = SourceElement.NO_SOURCE;
        Intrinsics.checkNotNullExpressionValue(NO_SOURCE, "NO_SOURCE");
        final KotlinJvmBinaryClass.AnnotationArgumentVisitor annotationArgumentVisitorLoadAnnotation = binaryClassAnnotationAndConstantLoaderImpl.loadAnnotation(classId, NO_SOURCE, arrayList);
        Intrinsics.checkNotNull(annotationArgumentVisitorLoadAnnotation);
        return new KotlinJvmBinaryClass.AnnotationArgumentVisitor(this, arrayList) { // from class: org.jetbrains.kotlin.load.kotlin.BinaryClassAnnotationAndConstantLoaderImpl$AbstractAnnotationArgumentVisitor$visitArray$1$visitAnnotation$1
            private final /* synthetic */ KotlinJvmBinaryClass.AnnotationArgumentVisitor $$delegate_0;
            final /* synthetic */ ArrayList<AnnotationDescriptor> $list;
            final /* synthetic */ BinaryClassAnnotationAndConstantLoaderImpl$AbstractAnnotationArgumentVisitor$visitArray$1 this$0;

            @Override // org.jetbrains.kotlin.load.kotlin.KotlinJvmBinaryClass.AnnotationArgumentVisitor
            public void visit(Name name2, Object value) {
                this.$$delegate_0.visit(name2, value);
            }

            @Override // org.jetbrains.kotlin.load.kotlin.KotlinJvmBinaryClass.AnnotationArgumentVisitor
            public KotlinJvmBinaryClass.AnnotationArgumentVisitor visitAnnotation(Name name2, ClassId classId2) {
                Intrinsics.checkNotNullParameter(classId2, "classId");
                return this.$$delegate_0.visitAnnotation(name2, classId2);
            }

            @Override // org.jetbrains.kotlin.load.kotlin.KotlinJvmBinaryClass.AnnotationArgumentVisitor
            public KotlinJvmBinaryClass.AnnotationArrayArgumentVisitor visitArray(Name name2) {
                return this.$$delegate_0.visitArray(name2);
            }

            @Override // org.jetbrains.kotlin.load.kotlin.KotlinJvmBinaryClass.AnnotationArgumentVisitor
            public void visitClassLiteral(Name name2, ClassLiteralValue value) {
                Intrinsics.checkNotNullParameter(value, "value");
                this.$$delegate_0.visitClassLiteral(name2, value);
            }

            @Override // org.jetbrains.kotlin.load.kotlin.KotlinJvmBinaryClass.AnnotationArgumentVisitor
            public void visitEnum(Name name2, ClassId enumClassId, Name enumEntryName) {
                Intrinsics.checkNotNullParameter(enumClassId, "enumClassId");
                Intrinsics.checkNotNullParameter(enumEntryName, "enumEntryName");
                this.$$delegate_0.visitEnum(name2, enumClassId, enumEntryName);
            }

            {
                this.this$0 = this;
                this.$list = arrayList;
                this.$$delegate_0 = this.$visitor;
            }

            @Override // org.jetbrains.kotlin.load.kotlin.KotlinJvmBinaryClass.AnnotationArgumentVisitor
            public void visitEnd() {
                this.$visitor.visitEnd();
                this.this$0.elements.add(new AnnotationValue((AnnotationDescriptor) CollectionsKt.single((List) this.$list)));
            }
        };
    }

    @Override // org.jetbrains.kotlin.load.kotlin.KotlinJvmBinaryClass.AnnotationArrayArgumentVisitor
    public void visitEnd() {
        this.this$1.visitArrayValue(this.$name, this.elements);
    }
}
