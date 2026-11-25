package org.jetbrains.kotlin.fir.resolve.transformers;

import androidx.exifinterface.media.ExifInterface;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.ArrayDeque;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtFakeSourceElementKind;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.fir.UtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.types.ArrayUtilsKt;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeKotlinTypeProjectionOut;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.builder.FirResolvedTypeRefBuilder;

/* JADX INFO: compiled from: TransformUtils.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010!\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a;\u0010\u0000\u001a\u0002H\u0001\"\u0004\b\u0000\u0010\u00012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u0006\u0010\u0005\u001a\u00020\u00042\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u0002H\u00010\u0007H\u0086\bø\u0001\u0000¢\u0006\u0002\u0010\b\u001a1\u0010\t\u001a\u0002H\u0001\"\u0004\b\u0000\u0010\u00012\n\u0010\n\u001a\u0006\u0012\u0002\b\u00030\u000b2\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u0002H\u00010\u0007H\u0086\bø\u0001\u0000¢\u0006\u0002\u0010\f\u001a\f\u0010\r\u001a\u00020\u000e*\u00020\u000fH\u0000\u001a\f\u0010\u0010\u001a\u00020\u000e*\u00020\u0011H\u0000\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\u0012"}, d2 = {"withClassDeclarationCleanup", ExifInterface.GPS_DIRECTION_TRUE, "classDeclarations", "Lkotlin/collections/ArrayDeque;", "Lorg/jetbrains/kotlin/fir/declarations/FirClass;", "topClassDeclaration", "l", "Lkotlin/Function0;", "(Lkotlin/collections/ArrayDeque;Lorg/jetbrains/kotlin/fir/declarations/FirClass;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "withScopeCleanup", "scopes", "", "(Ljava/util/List;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "transformTypeToArrayType", "", "Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;", "transformVarargTypeToArrayType", "Lorg/jetbrains/kotlin/fir/declarations/FirValueParameter;", "resolve"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class TransformUtilsKt {
    public static final void transformVarargTypeToArrayType(FirValueParameter firValueParameter) {
        Intrinsics.checkNotNullParameter(firValueParameter, "<this>");
        if (firValueParameter.getIsVararg()) {
            transformTypeToArrayType(firValueParameter);
        }
    }

    public static final void transformTypeToArrayType(FirCallableDeclaration firCallableDeclaration) {
        KtSourceElement source;
        Intrinsics.checkNotNullParameter(firCallableDeclaration, "<this>");
        FirTypeRef returnTypeRef = firCallableDeclaration.getReturnTypeRef();
        if (!(returnTypeRef instanceof FirResolvedTypeRef)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        FirResolvedTypeRef firResolvedTypeRef = (FirResolvedTypeRef) returnTypeRef;
        if (firResolvedTypeRef.getDelegatedTypeRef() instanceof FirResolvedTypeRef) {
            FirTypeRef delegatedTypeRef = firResolvedTypeRef.getDelegatedTypeRef();
            if (Intrinsics.areEqual((delegatedTypeRef == null || (source = delegatedTypeRef.getSource()) == null) ? null : source.getKind(), KtFakeSourceElementKind.ArrayTypeFromVarargParameter.INSTANCE)) {
                return;
            }
        }
        ConeKotlinType coneType = FirTypeUtilsKt.getConeType(returnTypeRef);
        FirResolvedTypeRefBuilder firResolvedTypeRefBuilder = new FirResolvedTypeRefBuilder();
        firResolvedTypeRefBuilder.setSource(returnTypeRef.getSource());
        firResolvedTypeRefBuilder.setType(ArrayUtilsKt.createArrayType$default(new ConeKotlinTypeProjectionOut(coneType), false, false, 3, null));
        CollectionsKt.addAll(firResolvedTypeRefBuilder.getAnnotations(), returnTypeRef.getAnnotations());
        firResolvedTypeRefBuilder.setDelegatedTypeRef(UtilsKt.copyWithNewSourceKind(returnTypeRef, KtFakeSourceElementKind.ArrayTypeFromVarargParameter.INSTANCE));
        firCallableDeclaration.replaceReturnTypeRef(firResolvedTypeRefBuilder.mo6809build());
    }

    public static final <T> T withScopeCleanup(List<?> scopes, Function0<? extends T> l) {
        Intrinsics.checkNotNullParameter(scopes, "scopes");
        Intrinsics.checkNotNullParameter(l, "l");
        int size = scopes.size();
        int i = 0;
        try {
            return l.invoke();
        } finally {
            InlineMarker.finallyStart(1);
            int size2 = scopes.size() - size;
            while (i < size2) {
                scopes.remove(scopes.size() - 1);
                i++;
            }
            InlineMarker.finallyEnd(1);
        }
    }

    public static final <T> T withClassDeclarationCleanup(ArrayDeque<FirClass> classDeclarations, FirClass topClassDeclaration, Function0<? extends T> l) {
        Intrinsics.checkNotNullParameter(classDeclarations, "classDeclarations");
        Intrinsics.checkNotNullParameter(topClassDeclaration, "topClassDeclaration");
        Intrinsics.checkNotNullParameter(l, "l");
        classDeclarations.addLast(topClassDeclaration);
        try {
            return l.invoke();
        } finally {
            InlineMarker.finallyStart(1);
            classDeclarations.removeLast();
            InlineMarker.finallyEnd(1);
        }
    }
}
