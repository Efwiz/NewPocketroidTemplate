package org.jetbrains.kotlin.fir.declarations;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.expressions.FirAnnotationCall;
import org.jetbrains.kotlin.fir.expressions.FirArgumentList;
import org.jetbrains.kotlin.fir.expressions.impl.FirResolvedArgumentList;
import org.jetbrains.kotlin.fir.references.FirNamedReference;
import org.jetbrains.kotlin.fir.references.FirResolvedNamedReference;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.visitors.FirDefaultVisitor;

/* JADX INFO: compiled from: GeneratedDeclarationValidation.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u0010\u0012\u0004\u0012\u00020\u0002\u0012\u0006\u0012\u0004\u0018\u00010\u00030\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J\u001a\u0010\u0005\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u00072\b\u0010\b\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010\t\u001a\u00020\u00022\u0006\u0010\n\u001a\u00020\u000b2\b\u0010\b\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010\f\u001a\u00020\u00022\u0006\u0010\r\u001a\u00020\u000e2\b\u0010\b\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010\u000f\u001a\u00020\u00022\u0006\u0010\u0010\u001a\u00020\u00112\b\u0010\b\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010\u0012\u001a\u00020\u00022\u0006\u0010\u0013\u001a\u00020\u00142\b\u0010\b\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010\u0015\u001a\u00020\u00022\u0006\u0010\u0016\u001a\u00020\u00172\b\u0010\b\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010\u0018\u001a\u00020\u00022\u0006\u0010\u0019\u001a\u00020\u001a2\b\u0010\b\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010\u001b\u001a\u00020\u00022\u0006\u0010\u001c\u001a\u00020\u001d2\b\u0010\b\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010\u001e\u001a\u00020\u00022\u0006\u0010\u001f\u001a\u00020 2\b\u0010\b\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010!\u001a\u00020\u00022\u0006\u0010\"\u001a\u00020#2\b\u0010\b\u001a\u0004\u0018\u00010\u0003H\u0016¨\u0006$"}, d2 = {"Lorg/jetbrains/kotlin/fir/declarations/FirGeneratedElementsValidator;", "Lorg/jetbrains/kotlin/fir/visitors/FirDefaultVisitor;", "", "", "()V", "visitAnnotation", "annotation", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "data", "visitAnnotationCall", "annotationCall", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotationCall;", "visitArgumentList", "argumentList", "Lorg/jetbrains/kotlin/fir/expressions/FirArgumentList;", "visitDeclarationStatus", "declarationStatus", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationStatus;", "visitElement", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/fir/FirElement;", "visitNamedReference", "namedReference", "Lorg/jetbrains/kotlin/fir/references/FirNamedReference;", "visitRegularClass", "regularClass", "Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;", "visitResolvedTypeRef", "resolvedTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirResolvedTypeRef;", "visitTypeParameterRef", "typeParameterRef", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeParameterRef;", "visitTypeRef", "typeRef", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirGeneratedElementsValidator extends FirDefaultVisitor<Unit, Object> {
    public static final FirGeneratedElementsValidator INSTANCE = new FirGeneratedElementsValidator();

    private FirGeneratedElementsValidator() {
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirDefaultVisitor, org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Object visitAnnotation(FirAnnotation firAnnotation, Object obj) {
        m6799visitAnnotation(firAnnotation, obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Object visitAnnotationCall(FirAnnotationCall firAnnotationCall, Object obj) {
        m6800visitAnnotationCall(firAnnotationCall, obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Object visitArgumentList(FirArgumentList firArgumentList, Object obj) {
        m6801visitArgumentList(firArgumentList, obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Object visitDeclarationStatus(FirDeclarationStatus firDeclarationStatus, Object obj) {
        m6802visitDeclarationStatus(firDeclarationStatus, obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Object visitElement(FirElement firElement, Object obj) {
        m6803visitElement(firElement, obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirDefaultVisitor, org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Object visitNamedReference(FirNamedReference firNamedReference, Object obj) {
        m6804visitNamedReference(firNamedReference, obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirDefaultVisitor, org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Object visitRegularClass(FirRegularClass firRegularClass, Object obj) {
        m6805visitRegularClass(firRegularClass, obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirDefaultVisitor, org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Object visitResolvedTypeRef(FirResolvedTypeRef firResolvedTypeRef, Object obj) {
        m6806visitResolvedTypeRef(firResolvedTypeRef, obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Object visitTypeParameterRef(FirTypeParameterRef firTypeParameterRef, Object obj) {
        m6807visitTypeParameterRef(firTypeParameterRef, obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirDefaultVisitor, org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Object visitTypeRef(FirTypeRef firTypeRef, Object obj) {
        m6808visitTypeRef(firTypeRef, obj);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitElement, reason: collision with other method in class */
    public void m6803visitElement(FirElement element, Object data) {
        Intrinsics.checkNotNullParameter(element, "element");
        element.acceptChildren(this, null);
    }

    /* JADX INFO: renamed from: visitAnnotation, reason: collision with other method in class */
    public void m6799visitAnnotation(FirAnnotation annotation, Object data) {
        Intrinsics.checkNotNullParameter(annotation, "annotation");
        annotation.acceptChildren(this, null);
    }

    /* JADX INFO: renamed from: visitAnnotationCall, reason: collision with other method in class */
    public void m6800visitAnnotationCall(FirAnnotationCall annotationCall, Object data) {
        Intrinsics.checkNotNullParameter(annotationCall, "annotationCall");
        annotationCall.acceptChildren(this, null);
    }

    /* JADX INFO: renamed from: visitRegularClass, reason: collision with other method in class */
    public void m6805visitRegularClass(FirRegularClass regularClass, Object data) {
        Intrinsics.checkNotNullParameter(regularClass, "regularClass");
        regularClass.acceptChildren(this, null);
    }

    /* JADX INFO: renamed from: visitArgumentList, reason: collision with other method in class */
    public void m6801visitArgumentList(FirArgumentList argumentList, Object data) {
        Intrinsics.checkNotNullParameter(argumentList, "argumentList");
        if (!(argumentList instanceof FirResolvedArgumentList)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        argumentList.acceptChildren(this, null);
    }

    /* JADX INFO: renamed from: visitNamedReference, reason: collision with other method in class */
    public void m6804visitNamedReference(FirNamedReference namedReference, Object data) {
        Intrinsics.checkNotNullParameter(namedReference, "namedReference");
        if (!(namedReference instanceof FirResolvedNamedReference)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        namedReference.acceptChildren(this, null);
    }

    /* JADX INFO: renamed from: visitTypeRef, reason: collision with other method in class */
    public void m6808visitTypeRef(FirTypeRef typeRef, Object data) {
        Intrinsics.checkNotNullParameter(typeRef, "typeRef");
        typeRef.acceptChildren(this, null);
    }

    /* JADX INFO: renamed from: visitResolvedTypeRef, reason: collision with other method in class */
    public void m6806visitResolvedTypeRef(FirResolvedTypeRef resolvedTypeRef, Object data) {
        Intrinsics.checkNotNullParameter(resolvedTypeRef, "resolvedTypeRef");
        Iterator<T> it = resolvedTypeRef.getAnnotations().iterator();
        while (it.hasNext()) {
            ((FirAnnotation) it.next()).accept(this, null);
        }
    }

    /* JADX INFO: renamed from: visitDeclarationStatus, reason: collision with other method in class */
    public void m6802visitDeclarationStatus(FirDeclarationStatus declarationStatus, Object data) {
        Intrinsics.checkNotNullParameter(declarationStatus, "declarationStatus");
        if (!(declarationStatus instanceof FirResolvedDeclarationStatus)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX INFO: renamed from: visitTypeParameterRef, reason: collision with other method in class */
    public void m6807visitTypeParameterRef(FirTypeParameterRef typeParameterRef, Object data) {
        Intrinsics.checkNotNullParameter(typeParameterRef, "typeParameterRef");
        ((FirTypeParameter) typeParameterRef.getSymbol().getFir()).accept(this, null);
    }
}
