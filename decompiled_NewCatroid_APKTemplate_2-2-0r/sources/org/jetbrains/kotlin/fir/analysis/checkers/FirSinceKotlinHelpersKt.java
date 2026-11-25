package org.jetbrains.kotlin.fir.analysis.checkers;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import org.jetbrains.kotlin.config.ApiVersion;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.fir.FirLanguageSettingsComponentKt;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.declarations.FirAnnotationUtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirConstructor;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.declarations.FirTypeAlias;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.expressions.FirConstExpression;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.resolve.LookupTagUtilsKt;
import org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProviderKt;
import org.jetbrains.kotlin.fir.symbols.ConeClassLikeLookupTag;
import org.jetbrains.kotlin.fir.symbols.FirLazyDeclarationResolverKt;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.StandardClassIds;
import org.jetbrains.kotlin.resolve.checkers.OptInNames;

/* JADX INFO: compiled from: FirSinceKotlinHelpers.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000(\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\u001a\u0012\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004\u001a\u0016\u0010\u0005\u001a\u0004\u0018\u00010\u0006*\u00020\u00022\u0006\u0010\u0007\u001a\u00020\bH\u0002\u001a\u001a\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n*\u00020\u00022\u0006\u0010\u0007\u001a\u00020\bH\u0002¨\u0006\f"}, d2 = {"checkSinceKotlinVersionAccessibility", "Lorg/jetbrains/kotlin/fir/analysis/checkers/FirSinceKotlinAccessibility;", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "getOwnSinceKotlinVersion", "Lorg/jetbrains/kotlin/fir/analysis/checkers/FirSinceKotlinValue;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "loadWasExperimentalMarkerClasses", "", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirRegularClassSymbol;", "checkers"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FirSinceKotlinHelpersKt {
    public static final FirSinceKotlinAccessibility checkSinceKotlinVersionAccessibility(FirDeclaration firDeclaration, CheckerContext context) {
        Intrinsics.checkNotNullParameter(firDeclaration, "<this>");
        Intrinsics.checkNotNullParameter(context, "context");
        FirSinceKotlinValue ownSinceKotlinVersion = getOwnSinceKotlinVersion(firDeclaration, context.getSession());
        ApiVersion apiVersion = ownSinceKotlinVersion != null ? ownSinceKotlinVersion.getApiVersion() : null;
        LanguageVersionSettings languageVersionSettings = FirLanguageSettingsComponentKt.getLanguageVersionSettings(context.getSession());
        if (apiVersion == null || apiVersion.compareTo(languageVersionSettings.getApiVersion()) <= 0) {
            return FirSinceKotlinAccessibility.Accessible.INSTANCE;
        }
        List<FirRegularClassSymbol> wasExperimentalMarkerClasses = ownSinceKotlinVersion.getWasExperimentalMarkerClasses();
        if (!wasExperimentalMarkerClasses.isEmpty()) {
            return new FirSinceKotlinAccessibility.NotAccessibleButWasExperimental(apiVersion, wasExperimentalMarkerClasses);
        }
        return new FirSinceKotlinAccessibility.NotAccessible(apiVersion);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static final FirSinceKotlinValue getOwnSinceKotlinVersion(FirDeclaration firDeclaration, FirSession firSession) {
        ConeClassLikeLookupTag lookupTag;
        FirClassLikeSymbol<?> symbol;
        ClassId classId;
        FirClassLikeSymbol<?> classLikeSymbolByClassId;
        Ref.ObjectRef objectRef = new Ref.ObjectRef();
        getOwnSinceKotlinVersion$consider(firDeclaration, firSession, objectRef);
        if ((firDeclaration instanceof FirConstructor) && (classId = ((FirConstructor) firDeclaration).getSymbol().getCallableId().getClassId()) != null && (classLikeSymbolByClassId = FirSymbolProviderKt.getSymbolProvider(firSession).getClassLikeSymbolByClassId(classId)) != null) {
            getOwnSinceKotlinVersion$consider$0(classLikeSymbolByClassId, firSession, objectRef);
        }
        if (firDeclaration instanceof FirTypeAlias) {
            ConeKotlinType coneType = FirTypeUtilsKt.getConeType(((FirTypeAlias) firDeclaration).getExpandedTypeRef());
            ConeClassLikeType coneClassLikeType = coneType instanceof ConeClassLikeType ? (ConeClassLikeType) coneType : null;
            if (coneClassLikeType != null && (lookupTag = coneClassLikeType.getLookupTag()) != null && (symbol = LookupTagUtilsKt.toSymbol(lookupTag, firSession)) != null) {
                getOwnSinceKotlinVersion$consider$0(symbol, firSession, objectRef);
            }
        }
        return (FirSinceKotlinValue) objectRef.element;
    }

    /* JADX WARN: Type inference failed for: r0v12, types: [T, org.jetbrains.kotlin.fir.analysis.checkers.FirSinceKotlinValue] */
    private static final void getOwnSinceKotlinVersion$consider(FirDeclaration firDeclaration, FirSession firSession, Ref.ObjectRef<FirSinceKotlinValue> objectRef) {
        FirAnnotation annotationByClassId = FirAnnotationUtilsKt.getAnnotationByClassId(firDeclaration, StandardClassIds.Annotations.INSTANCE.getSinceKotlin(), firSession);
        FirExpression firExpressionFindArgumentByName = annotationByClassId != null ? FirAnnotationUtilsKt.findArgumentByName(annotationByClassId, StandardClassIds.Annotations.ParameterNames.INSTANCE.getSinceKotlinVersion()) : null;
        FirConstExpression firConstExpression = firExpressionFindArgumentByName instanceof FirConstExpression ? (FirConstExpression) firExpressionFindArgumentByName : null;
        Object value = firConstExpression != null ? firConstExpression.getValue() : null;
        String str = value instanceof String ? (String) value : null;
        ApiVersion apiVersion = str != null ? ApiVersion.INSTANCE.parse(str) : null;
        if (apiVersion != null) {
            if (objectRef.element != null) {
                FirSinceKotlinValue firSinceKotlinValue = objectRef.element;
                Intrinsics.checkNotNull(firSinceKotlinValue);
                if (apiVersion.compareTo(firSinceKotlinValue.getApiVersion()) <= 0) {
                    return;
                }
            }
            objectRef.element = new FirSinceKotlinValue(apiVersion, loadWasExperimentalMarkerClasses(firDeclaration, firSession));
        }
    }

    /* JADX WARN: Type inference failed for: r2v1, types: [org.jetbrains.kotlin.fir.declarations.FirDeclaration] */
    private static final void getOwnSinceKotlinVersion$consider$0(FirClassLikeSymbol<?> firClassLikeSymbol, FirSession firSession, Ref.ObjectRef<FirSinceKotlinValue> objectRef) {
        FirLazyDeclarationResolverKt.lazyResolveToPhase(firClassLikeSymbol, FirResolvePhase.BODY_RESOLVE);
        getOwnSinceKotlinVersion$consider(firClassLikeSymbol.getFir(), firSession, objectRef);
    }

    private static final List<FirRegularClassSymbol> loadWasExperimentalMarkerClasses(FirDeclaration firDeclaration, FirSession firSession) {
        FirExpression firExpressionFindArgumentByName;
        FirAnnotation annotationByClassId = FirAnnotationUtilsKt.getAnnotationByClassId(firDeclaration, OptInNames.INSTANCE.getWAS_EXPERIMENTAL_CLASS_ID(), firSession);
        if (annotationByClassId != null && (firExpressionFindArgumentByName = FirAnnotationUtilsKt.findArgumentByName(annotationByClassId, OptInNames.INSTANCE.getWAS_EXPERIMENTAL_ANNOTATION_CLASS())) != null) {
            return FirAnnotationHelpersKt.extractClassesFromArgument(firExpressionFindArgumentByName, firSession);
        }
        return CollectionsKt.emptyList();
    }
}
