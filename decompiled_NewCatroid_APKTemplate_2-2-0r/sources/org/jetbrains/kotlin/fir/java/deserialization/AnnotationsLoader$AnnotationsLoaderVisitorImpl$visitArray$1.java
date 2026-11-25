package org.jetbrains.kotlin.fir.java.deserialization;

import com.reandroid.archive.PathTree;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jdt.internal.compiler.util.SuffixConstants;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.expressions.FirArgumentUtilKt;
import org.jetbrains.kotlin.fir.expressions.FirClassReferenceExpression;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.builder.FirArgumentListBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirArrayLiteralBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirGetClassCallBuilder;
import org.jetbrains.kotlin.fir.types.ArrayUtilsKt;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.builder.FirResolvedTypeRefBuilder;
import org.jetbrains.kotlin.load.kotlin.KotlinJvmBinaryClass;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.constants.ClassLiteralValue;

/* JADX INFO: compiled from: AnnotationsLoader.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000;\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\u0012\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\bH\u0016J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0016J\u0010\u0010\r\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u000eH\u0016J\b\u0010\u000f\u001a\u00020\u0006H\u0016J\u0018\u0010\u0010\u001a\u00020\u00062\u0006\u0010\u0011\u001a\u00020\f2\u0006\u0010\u0012\u001a\u00020\u0013H\u0016R\u0014\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0014"}, d2 = {"org/jetbrains/kotlin/fir/java/deserialization/AnnotationsLoader$AnnotationsLoaderVisitorImpl$visitArray$1", "Lorg/jetbrains/kotlin/load/kotlin/KotlinJvmBinaryClass$AnnotationArrayArgumentVisitor;", PathTree.NAME_elements, "", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "visit", "", "value", "", "visitAnnotation", "Lorg/jetbrains/kotlin/load/kotlin/KotlinJvmBinaryClass$AnnotationArgumentVisitor;", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "visitClassLiteral", "Lorg/jetbrains/kotlin/resolve/constants/ClassLiteralValue;", "visitEnd", "visitEnum", "enumClassId", "enumEntryName", "Lorg/jetbrains/kotlin/name/Name;", SuffixConstants.EXTENSION_java}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class AnnotationsLoader$AnnotationsLoaderVisitorImpl$visitArray$1 implements KotlinJvmBinaryClass.AnnotationArrayArgumentVisitor {
    final /* synthetic */ Name $name;
    private final List<FirExpression> elements = new ArrayList();
    final /* synthetic */ AnnotationsLoader.AnnotationsLoaderVisitorImpl this$0;
    final /* synthetic */ AnnotationsLoader this$1;

    AnnotationsLoader$AnnotationsLoaderVisitorImpl$visitArray$1(AnnotationsLoader.AnnotationsLoaderVisitorImpl annotationsLoaderVisitorImpl, AnnotationsLoader annotationsLoader, Name name2) {
        this.this$0 = annotationsLoaderVisitorImpl;
        this.this$1 = annotationsLoader;
        this.$name = name2;
    }

    @Override // org.jetbrains.kotlin.load.kotlin.KotlinJvmBinaryClass.AnnotationArrayArgumentVisitor
    public void visit(Object value) {
        this.elements.add(this.this$0.createConstant(value));
    }

    @Override // org.jetbrains.kotlin.load.kotlin.KotlinJvmBinaryClass.AnnotationArrayArgumentVisitor
    public void visitEnum(ClassId enumClassId, Name enumEntryName) {
        Intrinsics.checkNotNullParameter(enumClassId, "enumClassId");
        Intrinsics.checkNotNullParameter(enumEntryName, "enumEntryName");
        this.elements.add(this.this$0.getEnumEntryReferenceCreator().invoke(enumClassId, enumEntryName));
    }

    @Override // org.jetbrains.kotlin.load.kotlin.KotlinJvmBinaryClass.AnnotationArrayArgumentVisitor
    public void visitClassLiteral(ClassLiteralValue value) {
        Intrinsics.checkNotNullParameter(value, "value");
        List<FirExpression> list = this.elements;
        AnnotationsLoader.AnnotationsLoaderVisitorImpl annotationsLoaderVisitorImpl = this.this$0;
        FirGetClassCallBuilder firGetClassCallBuilder = new FirGetClassCallBuilder();
        FirClassReferenceExpression firClassReferenceExpression = annotationsLoaderVisitorImpl.toFirClassReferenceExpression(value);
        firGetClassCallBuilder.setArgumentList(FirArgumentUtilKt.buildUnaryArgumentList(firClassReferenceExpression));
        firGetClassCallBuilder.setTypeRef(firClassReferenceExpression.getTypeRef());
        list.add(firGetClassCallBuilder.mo6809build());
    }

    @Override // org.jetbrains.kotlin.load.kotlin.KotlinJvmBinaryClass.AnnotationArrayArgumentVisitor
    public KotlinJvmBinaryClass.AnnotationArgumentVisitor visitAnnotation(ClassId classId) {
        Intrinsics.checkNotNullParameter(classId, "classId");
        final ArrayList arrayList = new ArrayList();
        final KotlinJvmBinaryClass.AnnotationArgumentVisitor annotationArgumentVisitorLoadAnnotation = this.this$1.loadAnnotation(classId, arrayList, this.this$0.getEnumEntryReferenceCreator());
        return new KotlinJvmBinaryClass.AnnotationArgumentVisitor(this, arrayList) { // from class: org.jetbrains.kotlin.fir.java.deserialization.AnnotationsLoader$AnnotationsLoaderVisitorImpl$visitArray$1$visitAnnotation$1
            private final /* synthetic */ KotlinJvmBinaryClass.AnnotationArgumentVisitor $$delegate_0;
            final /* synthetic */ List<FirAnnotation> $list;
            final /* synthetic */ AnnotationsLoader$AnnotationsLoaderVisitorImpl$visitArray$1 this$0;

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
                this.this$0.elements.add(CollectionsKt.single((List) this.$list));
            }
        };
    }

    @Override // org.jetbrains.kotlin.load.kotlin.KotlinJvmBinaryClass.AnnotationArrayArgumentVisitor
    public void visitEnd() {
        FirTypeRef typeRef;
        ConeKotlinType coneType;
        ConeKotlinType coneKotlinTypeCreateOutArrayType$default;
        AnnotationsLoader.AnnotationsLoaderVisitorImpl annotationsLoaderVisitorImpl = this.this$0;
        Name name2 = this.$name;
        FirArrayLiteralBuilder firArrayLiteralBuilder = new FirArrayLiteralBuilder();
        FirTypeRef firTypeRefGuessArrayTypeIfNeeded = annotationsLoaderVisitorImpl.guessArrayTypeIfNeeded(name2, this.elements);
        if (firTypeRefGuessArrayTypeIfNeeded != null) {
            firArrayLiteralBuilder.setTypeRef(firTypeRefGuessArrayTypeIfNeeded);
        } else {
            FirExpression firExpression = (FirExpression) CollectionsKt.firstOrNull((List) this.elements);
            if (firExpression != null && (typeRef = firExpression.getTypeRef()) != null && (coneType = FirTypeUtilsKt.getConeType(typeRef)) != null && (coneKotlinTypeCreateOutArrayType$default = ArrayUtilsKt.createOutArrayType$default(coneType, false, false, 3, null)) != null) {
                FirResolvedTypeRefBuilder firResolvedTypeRefBuilder = new FirResolvedTypeRefBuilder();
                firResolvedTypeRefBuilder.setType(coneKotlinTypeCreateOutArrayType$default);
                firArrayLiteralBuilder.setTypeRef(firResolvedTypeRefBuilder.mo6809build());
            }
        }
        FirArgumentListBuilder firArgumentListBuilder = new FirArgumentListBuilder();
        CollectionsKt.addAll(firArgumentListBuilder.getArguments(), this.elements);
        firArrayLiteralBuilder.setArgumentList(firArgumentListBuilder.build());
        Unit unit = Unit.INSTANCE;
        annotationsLoaderVisitorImpl.visitExpression(name2, firArrayLiteralBuilder.mo6809build());
    }
}
