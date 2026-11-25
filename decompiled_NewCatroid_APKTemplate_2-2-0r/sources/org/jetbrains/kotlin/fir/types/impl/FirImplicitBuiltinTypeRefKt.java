package org.jetbrains.kotlin.fir.types.impl;

import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.KtSourceElement;

/* JADX INFO: compiled from: FirImplicitBuiltinTypeRef.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0014\u0010\u0000\u001a\u00020\u0001*\u00020\u00012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003¨\u0006\u0004"}, d2 = {"withNewSource", "Lorg/jetbrains/kotlin/fir/types/impl/FirImplicitBuiltinTypeRef;", "newSource", "Lorg/jetbrains/kotlin/KtSourceElement;", Constants.TYPE_TREE}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FirImplicitBuiltinTypeRefKt {
    public static final FirImplicitBuiltinTypeRef withNewSource(FirImplicitBuiltinTypeRef firImplicitBuiltinTypeRef, KtSourceElement ktSourceElement) {
        Intrinsics.checkNotNullParameter(firImplicitBuiltinTypeRef, "<this>");
        KtSourceElement source = firImplicitBuiltinTypeRef.getSource();
        if (source == null) {
            return firImplicitBuiltinTypeRef;
        }
        if (Intrinsics.areEqual(source.getKind(), ktSourceElement != null ? ktSourceElement.getKind() : null)) {
            return firImplicitBuiltinTypeRef;
        }
        if (firImplicitBuiltinTypeRef instanceof FirImplicitUnitTypeRef) {
            return new FirImplicitUnitTypeRef(ktSourceElement);
        }
        if (firImplicitBuiltinTypeRef instanceof FirImplicitAnyTypeRef) {
            return new FirImplicitAnyTypeRef(ktSourceElement);
        }
        if (firImplicitBuiltinTypeRef instanceof FirImplicitNullableAnyTypeRef) {
            return new FirImplicitNullableAnyTypeRef(ktSourceElement);
        }
        if (firImplicitBuiltinTypeRef instanceof FirImplicitEnumTypeRef) {
            return new FirImplicitEnumTypeRef(ktSourceElement);
        }
        if (firImplicitBuiltinTypeRef instanceof FirImplicitAnnotationTypeRef) {
            return new FirImplicitAnnotationTypeRef(ktSourceElement);
        }
        if (firImplicitBuiltinTypeRef instanceof FirImplicitBooleanTypeRef) {
            return new FirImplicitBooleanTypeRef(ktSourceElement);
        }
        if (firImplicitBuiltinTypeRef instanceof FirImplicitByteTypeRef) {
            return new FirImplicitByteTypeRef(ktSourceElement);
        }
        if (firImplicitBuiltinTypeRef instanceof FirImplicitNumberTypeRef) {
            return new FirImplicitNumberTypeRef(ktSourceElement);
        }
        if (firImplicitBuiltinTypeRef instanceof FirImplicitShortTypeRef) {
            return new FirImplicitShortTypeRef(ktSourceElement);
        }
        if (firImplicitBuiltinTypeRef instanceof FirImplicitIntTypeRef) {
            return new FirImplicitIntTypeRef(ktSourceElement);
        }
        if (firImplicitBuiltinTypeRef instanceof FirImplicitLongTypeRef) {
            return new FirImplicitLongTypeRef(ktSourceElement);
        }
        if (firImplicitBuiltinTypeRef instanceof FirImplicitDoubleTypeRef) {
            return new FirImplicitDoubleTypeRef(ktSourceElement);
        }
        if (firImplicitBuiltinTypeRef instanceof FirImplicitFloatTypeRef) {
            return new FirImplicitFloatTypeRef(ktSourceElement);
        }
        if (firImplicitBuiltinTypeRef instanceof FirImplicitUIntTypeRef) {
            return new FirImplicitUIntTypeRef(ktSourceElement);
        }
        if (firImplicitBuiltinTypeRef instanceof FirImplicitULongTypeRef) {
            return new FirImplicitULongTypeRef(ktSourceElement);
        }
        if (firImplicitBuiltinTypeRef instanceof FirImplicitNothingTypeRef) {
            return new FirImplicitNothingTypeRef(ktSourceElement);
        }
        if (firImplicitBuiltinTypeRef instanceof FirImplicitNullableNothingTypeRef) {
            return new FirImplicitNullableNothingTypeRef(ktSourceElement);
        }
        if (firImplicitBuiltinTypeRef instanceof FirImplicitCharTypeRef) {
            return new FirImplicitCharTypeRef(ktSourceElement);
        }
        if (firImplicitBuiltinTypeRef instanceof FirImplicitStringTypeRef) {
            return new FirImplicitStringTypeRef(ktSourceElement);
        }
        if (firImplicitBuiltinTypeRef instanceof FirImplicitThrowableTypeRef) {
            return new FirImplicitThrowableTypeRef(ktSourceElement);
        }
        if (firImplicitBuiltinTypeRef instanceof FirImplicitCharSequenceTypeRef) {
            return new FirImplicitCharSequenceTypeRef(ktSourceElement);
        }
        if (firImplicitBuiltinTypeRef instanceof FirImplicitCharIteratorTypeRef) {
            return new FirImplicitCharIteratorTypeRef(ktSourceElement);
        }
        if (firImplicitBuiltinTypeRef instanceof FirImplicitKPropertyTypeRef) {
            return new FirImplicitKPropertyTypeRef(ktSourceElement, firImplicitBuiltinTypeRef.getType().getTypeArguments()[0]);
        }
        if (firImplicitBuiltinTypeRef instanceof FirImplicitKProperty0TypeRef) {
            return new FirImplicitKProperty0TypeRef(ktSourceElement, firImplicitBuiltinTypeRef.getType().getTypeArguments()[0]);
        }
        if (firImplicitBuiltinTypeRef instanceof FirImplicitKMutableProperty0TypeRef) {
            return new FirImplicitKMutableProperty0TypeRef(ktSourceElement, firImplicitBuiltinTypeRef.getType().getTypeArguments()[0]);
        }
        if (firImplicitBuiltinTypeRef instanceof FirImplicitKProperty1TypeRef) {
            return new FirImplicitKProperty1TypeRef(ktSourceElement, firImplicitBuiltinTypeRef.getType().getTypeArguments()[0], firImplicitBuiltinTypeRef.getType().getTypeArguments()[1]);
        }
        if (firImplicitBuiltinTypeRef instanceof FirImplicitKMutableProperty1TypeRef) {
            return new FirImplicitKMutableProperty1TypeRef(ktSourceElement, firImplicitBuiltinTypeRef.getType().getTypeArguments()[0], firImplicitBuiltinTypeRef.getType().getTypeArguments()[1]);
        }
        if (firImplicitBuiltinTypeRef instanceof FirImplicitKProperty2TypeRef) {
            return new FirImplicitKProperty2TypeRef(ktSourceElement, firImplicitBuiltinTypeRef.getType().getTypeArguments()[0], firImplicitBuiltinTypeRef.getType().getTypeArguments()[1], firImplicitBuiltinTypeRef.getType().getTypeArguments()[2]);
        }
        if (firImplicitBuiltinTypeRef instanceof FirImplicitKMutableProperty2TypeRef) {
            return new FirImplicitKMutableProperty2TypeRef(ktSourceElement, firImplicitBuiltinTypeRef.getType().getTypeArguments()[0], firImplicitBuiltinTypeRef.getType().getTypeArguments()[1], firImplicitBuiltinTypeRef.getType().getTypeArguments()[2]);
        }
        if (firImplicitBuiltinTypeRef instanceof FirImplicitUByteTypeRef) {
            return new FirImplicitUByteTypeRef(ktSourceElement);
        }
        if (firImplicitBuiltinTypeRef instanceof FirImplicitUShortTypeRef) {
            return new FirImplicitUShortTypeRef(ktSourceElement);
        }
        throw new NoWhenBranchMatchedException();
    }
}
