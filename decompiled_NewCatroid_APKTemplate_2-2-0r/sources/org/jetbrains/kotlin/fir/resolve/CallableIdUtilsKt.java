package org.jetbrains.kotlin.fir.resolve;

import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProvider;
import org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProviderKt;
import org.jetbrains.kotlin.fir.symbols.ConeClassifierLookupTag;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassifierSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeLookupTagBasedType;
import org.jetbrains.kotlin.fir.types.ConeSimpleKotlinType;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.name.CallableId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.StandardClassIds;
import org.mozilla.javascript.ES6Iterator;

/* JADX INFO: compiled from: CallableIdUtils.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0005\u001a\u0014\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004\u001a\n\u0010\u0005\u001a\u00020\u0006*\u00020\u0007\u001a\n\u0010\b\u001a\u00020\u0006*\u00020\u0007\u001a\n\u0010\t\u001a\u00020\u0006*\u00020\u0007\u001a\n\u0010\n\u001a\u00020\u0006*\u00020\u0007\u001a\n\u0010\u000b\u001a\u00020\u0006*\u00020\u0007¨\u0006\f"}, d2 = {"fqName", "Lorg/jetbrains/kotlin/name/FqName;", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "isInvoke", "", "Lorg/jetbrains/kotlin/name/CallableId;", "isIterator", "isIteratorHasNext", "isIteratorNext", "isKFunctionInvoke", "semantics"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class CallableIdUtilsKt {
    public static final boolean isInvoke(CallableId callableId) {
        FqName className;
        String strAsString;
        Intrinsics.checkNotNullParameter(callableId, "<this>");
        if (isKFunctionInvoke(callableId)) {
            return true;
        }
        return Intrinsics.areEqual(callableId.getCallableName().asString(), "invoke") && (className = callableId.getClassName()) != null && (strAsString = className.asString()) != null && StringsKt.startsWith$default(strAsString, "Function", false, 2, (Object) null) && Intrinsics.areEqual(callableId.getPackageName(), StandardClassIds.INSTANCE.getBASE_KOTLIN_PACKAGE());
    }

    public static final boolean isKFunctionInvoke(CallableId callableId) {
        FqName className;
        String strAsString;
        Intrinsics.checkNotNullParameter(callableId, "<this>");
        return Intrinsics.areEqual(callableId.getCallableName().asString(), "invoke") && (className = callableId.getClassName()) != null && (strAsString = className.asString()) != null && StringsKt.startsWith$default(strAsString, StandardNames.K_FUNCTION_PREFIX, false, 2, (Object) null) && Intrinsics.areEqual(callableId.getPackageName().asString(), "kotlin.reflect");
    }

    public static final boolean isIteratorNext(CallableId callableId) {
        FqName className;
        String strAsString;
        Intrinsics.checkNotNullParameter(callableId, "<this>");
        return Intrinsics.areEqual(callableId.getCallableName().asString(), ES6Iterator.NEXT_METHOD) && (className = callableId.getClassName()) != null && (strAsString = className.asString()) != null && StringsKt.endsWith$default(strAsString, "Iterator", false, 2, (Object) null) && Intrinsics.areEqual(callableId.getPackageName().asString(), "kotlin.collections");
    }

    public static final boolean isIteratorHasNext(CallableId callableId) {
        FqName className;
        String strAsString;
        Intrinsics.checkNotNullParameter(callableId, "<this>");
        return Intrinsics.areEqual(callableId.getCallableName().asString(), "hasNext") && (className = callableId.getClassName()) != null && (strAsString = className.asString()) != null && StringsKt.endsWith$default(strAsString, "Iterator", false, 2, (Object) null) && Intrinsics.areEqual(callableId.getPackageName().asString(), "kotlin.collections");
    }

    public static final boolean isIterator(CallableId callableId) {
        Intrinsics.checkNotNullParameter(callableId, "<this>");
        if (!Intrinsics.areEqual(callableId.getCallableName().asString(), "iterator")) {
            return false;
        }
        String[] strArr = {"kotlin.collections", "kotlin.ranges"};
        String strAsString = callableId.getPackageName().asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        return ArraysKt.contains(strArr, strAsString);
    }

    public static final FqName fqName(FirAnnotation firAnnotation, FirSession session) {
        FirRegularClassSymbol firRegularClassSymbol;
        ConeClassifierLookupTag lookupTag;
        Intrinsics.checkNotNullParameter(firAnnotation, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        FirSymbolProvider symbolProvider = FirSymbolProviderKt.getSymbolProvider(session);
        FirTypeRef annotationTypeRef = firAnnotation.getAnnotationTypeRef();
        FirResolvedTypeRef firResolvedTypeRef = annotationTypeRef instanceof FirResolvedTypeRef ? (FirResolvedTypeRef) annotationTypeRef : null;
        ConeKotlinType type = firResolvedTypeRef != null ? firResolvedTypeRef.getType() : null;
        if (!(type instanceof ConeSimpleKotlinType)) {
            type = null;
        }
        ConeSimpleKotlinType coneSimpleKotlinType = (ConeSimpleKotlinType) type;
        ConeSimpleKotlinType coneSimpleKotlinTypeFullyExpandedType = coneSimpleKotlinType != null ? TypeExpansionUtilsKt.fullyExpandedType(coneSimpleKotlinType, symbolProvider.getSession()) : null;
        ConeLookupTagBasedType coneLookupTagBasedType = coneSimpleKotlinTypeFullyExpandedType instanceof ConeLookupTagBasedType ? (ConeLookupTagBasedType) coneSimpleKotlinTypeFullyExpandedType : null;
        if (coneLookupTagBasedType == null || (lookupTag = coneLookupTagBasedType.getLookupTag()) == null) {
            firRegularClassSymbol = null;
        } else {
            FirClassifierSymbol<?> symbolByLookupTag = LookupTagUtilsKt.getSymbolByLookupTag(symbolProvider, lookupTag);
            if (!(symbolByLookupTag instanceof FirRegularClassSymbol)) {
                symbolByLookupTag = null;
            }
            firRegularClassSymbol = (FirRegularClassSymbol) symbolByLookupTag;
        }
        FirRegularClassSymbol firRegularClassSymbol2 = firRegularClassSymbol;
        if (firRegularClassSymbol2 == null) {
            return null;
        }
        return firRegularClassSymbol2.getClassId().asSingleFqName();
    }
}
