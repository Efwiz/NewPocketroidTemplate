package org.jetbrains.kotlin.fir;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationUseSiteTarget;
import org.jetbrains.kotlin.fir.declarations.DeclarationUtilsKt;
import org.jetbrains.kotlin.fir.declarations.DeprecationUtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration;
import org.jetbrains.kotlin.fir.diagnostics.ConeDiagnostic;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirResolvedQualifier;
import org.jetbrains.kotlin.fir.expressions.builder.FirResolvedQualifierBuilder;
import org.jetbrains.kotlin.fir.references.impl.FirSimpleNamedReference;
import org.jetbrains.kotlin.fir.resolve.BodyResolveComponents;
import org.jetbrains.kotlin.fir.resolve.BodyResolveComponentsKt;
import org.jetbrains.kotlin.fir.resolve.ResolveUtilsKt;
import org.jetbrains.kotlin.fir.resolve.calls.ConstructorProcessingKt;
import org.jetbrains.kotlin.fir.resolve.diagnostics.ConeDeprecated;
import org.jetbrains.kotlin.fir.scopes.FirContainingNamesAwareScope;
import org.jetbrains.kotlin.fir.scopes.FirScope;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassifierSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.fir.types.FirTypeProjection;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.deprecation.DeprecationInfo;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: QualifiedNameResolution.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000T\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001aF\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\u0010\u0005\u001a\u0004\u0018\u00010\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\b2\n\u0010\t\u001a\u0006\u0012\u0002\b\u00030\n2\u000e\u0010\u000b\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u00032\u0006\u0010\f\u001a\u00020\rH\u0000\u001aL\u0010\u000e\u001a\u0004\u0018\u00010\u000f*\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\b\u0010\u0005\u001a\u0004\u0018\u00010\u00062\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00130\u00032\u000e\u0010\u0014\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u00032\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u0015\u001a\u00020\u0016\u001aF\u0010\u0017\u001a\u0004\u0018\u00010\u000f*\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001a2\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00130\u00032\u000e\u0010\u0014\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u00032\u0006\u0010\u0015\u001a\u00020\u00162\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u0002\u001a<\u0010\u001b\u001a\u0004\u0018\u00010\u000f*\u00020\u00162\u0006\u0010\u0010\u001a\u00020\u00112\b\u0010\u0005\u001a\u0004\u0018\u00010\u00062\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00130\u00032\u000e\u0010\u0014\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u0003\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000¨\u0006\u001c"}, d2 = {"ROOT_PREFIX_FOR_IDE_RESOLUTION_MODE", "", "extractNonFatalDiagnostics", "", "Lorg/jetbrains/kotlin/fir/diagnostics/ConeDiagnostic;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "explicitReceiver", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassLikeSymbol;", "extraNotFatalDiagnostics", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "continueQualifier", "Lorg/jetbrains/kotlin/fir/expressions/FirResolvedQualifier;", "namedReference", "Lorg/jetbrains/kotlin/fir/references/impl/FirSimpleNamedReference;", "typeArguments", "Lorg/jetbrains/kotlin/fir/types/FirTypeProjection;", "nonFatalDiagnosticsFromExpression", "components", "Lorg/jetbrains/kotlin/fir/resolve/BodyResolveComponents;", "continueQualifierInPackage", "Lorg/jetbrains/kotlin/name/FqName;", "name", "Lorg/jetbrains/kotlin/name/Name;", "resolveRootPartOfQualifier", "resolve"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class QualifiedNameResolutionKt {
    public static final String ROOT_PREFIX_FOR_IDE_RESOLUTION_MODE = "_root_ide_package_";

    /* JADX WARN: Multi-variable type inference failed */
    public static final FirResolvedQualifier resolveRootPartOfQualifier(BodyResolveComponents bodyResolveComponents, FirSimpleNamedReference namedReference, KtSourceElement ktSourceElement, List<? extends FirTypeProjection> typeArguments, List<? extends ConeDiagnostic> list) {
        FirRegularClassSymbol firRegularClassSymbolFullyExpandedClass;
        Intrinsics.checkNotNullParameter(bodyResolveComponents, "<this>");
        Intrinsics.checkNotNullParameter(namedReference, "namedReference");
        Intrinsics.checkNotNullParameter(typeArguments, "typeArguments");
        Name name2 = namedReference.getName();
        if (!Intrinsics.areEqual(name2.asString(), "_root_ide_package_")) {
            Iterator<FirScope> it = BodyResolveComponentsKt.createCurrentScopeList(bodyResolveComponents).iterator();
            while (it.hasNext()) {
                FirClassifierSymbol<?> singleVisibleClassifier = ConstructorProcessingKt.getSingleVisibleClassifier(it.next(), bodyResolveComponents.getSession(), bodyResolveComponents, name2);
                if (singleVisibleClassifier != null) {
                    FirClassLikeSymbol firClassLikeSymbol = singleVisibleClassifier instanceof FirClassLikeSymbol ? (FirClassLikeSymbol) singleVisibleClassifier : null;
                    if (firClassLikeSymbol != null && (firRegularClassSymbolFullyExpandedClass = DeclarationUtilsKt.fullyExpandedClass(firClassLikeSymbol, bodyResolveComponents.getSession())) != null && FirVisibilityCheckerKt.getVisibilityChecker(bodyResolveComponents.getSession()).isClassLikeVisible((FirClassLikeDeclaration) firRegularClassSymbolFullyExpandedClass.getFir(), bodyResolveComponents.getSession(), bodyResolveComponents.getFile(), bodyResolveComponents.getContainingDeclarations())) {
                        FirClassLikeSymbol<?> firClassLikeSymbol2 = (FirClassLikeSymbol) singleVisibleClassifier;
                        ClassId classId = firClassLikeSymbol2.getClassId();
                        FirResolvedQualifierBuilder firResolvedQualifierBuilder = new FirResolvedQualifierBuilder();
                        firResolvedQualifierBuilder.setSource(ktSourceElement);
                        FqName packageFqName = classId.getPackageFqName();
                        Intrinsics.checkNotNullExpressionValue(packageFqName, "getPackageFqName(...)");
                        firResolvedQualifierBuilder.setPackageFqName(packageFqName);
                        firResolvedQualifierBuilder.setRelativeClassFqName(classId.getRelativeClassName());
                        firResolvedQualifierBuilder.setSymbol(firClassLikeSymbol2);
                        firResolvedQualifierBuilder.getTypeArguments().addAll(typeArguments);
                        firResolvedQualifierBuilder.getNonFatalDiagnostics().addAll(extractNonFatalDiagnostics(ktSourceElement, null, firClassLikeSymbol2, list, bodyResolveComponents.getSession()));
                        FirResolvedQualifier firResolvedQualifierMo6809build = firResolvedQualifierBuilder.mo6809build();
                        firResolvedQualifierMo6809build.replaceTypeRef(ResolveUtilsKt.typeForQualifier(bodyResolveComponents, firResolvedQualifierMo6809build));
                        return firResolvedQualifierMo6809build;
                    }
                }
            }
            FqName ROOT = FqName.ROOT;
            Intrinsics.checkNotNullExpressionValue(ROOT, "ROOT");
            return continueQualifierInPackage(ROOT, name2, typeArguments, list, bodyResolveComponents, ktSourceElement);
        }
        FirResolvedQualifierBuilder firResolvedQualifierBuilder2 = new FirResolvedQualifierBuilder();
        firResolvedQualifierBuilder2.setSource(ktSourceElement);
        FqName ROOT2 = FqName.ROOT;
        Intrinsics.checkNotNullExpressionValue(ROOT2, "ROOT");
        firResolvedQualifierBuilder2.setPackageFqName(ROOT2);
        List<ConeDiagnostic> nonFatalDiagnostics = firResolvedQualifierBuilder2.getNonFatalDiagnostics();
        if (list == null) {
            list = CollectionsKt.emptyList();
        }
        nonFatalDiagnostics.addAll(list);
        FirResolvedQualifier firResolvedQualifierMo6809build2 = firResolvedQualifierBuilder2.mo6809build();
        firResolvedQualifierMo6809build2.replaceTypeRef(ResolveUtilsKt.typeForQualifier(bodyResolveComponents, firResolvedQualifierMo6809build2));
        return firResolvedQualifierMo6809build2;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final FirResolvedQualifier continueQualifier(FirResolvedQualifier firResolvedQualifier, FirSimpleNamedReference namedReference, KtSourceElement ktSourceElement, List<? extends FirTypeProjection> typeArguments, List<? extends ConeDiagnostic> list, FirSession session, BodyResolveComponents components) {
        FirClassifierSymbol<?> singleVisibleClassifier;
        Intrinsics.checkNotNullParameter(firResolvedQualifier, "<this>");
        Intrinsics.checkNotNullParameter(namedReference, "namedReference");
        Intrinsics.checkNotNullParameter(typeArguments, "typeArguments");
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(components, "components");
        Name name2 = namedReference.getName();
        FirClassLikeSymbol<?> symbol = firResolvedQualifier.getSymbol();
        if (symbol != null) {
            FirClassLikeDeclaration firClassLikeDeclaration = (FirClassLikeDeclaration) symbol.getFir();
            if (!(firClassLikeDeclaration instanceof FirClass)) {
                return null;
            }
            FirClass firClass = (FirClass) firClassLikeDeclaration;
            FirContainingNamesAwareScope nestedClassifierScope = firClass.getScopeProvider().getNestedClassifierScope(firClass, components.getSession(), components.getScopeSession());
            if (nestedClassifierScope == null || (singleVisibleClassifier = ConstructorProcessingKt.getSingleVisibleClassifier(nestedClassifierScope, session, components, name2)) == null) {
                return null;
            }
            if (!(singleVisibleClassifier instanceof FirClassLikeSymbol)) {
                singleVisibleClassifier = null;
            }
            if (singleVisibleClassifier == null) {
                return null;
            }
            FirResolvedQualifierBuilder firResolvedQualifierBuilder = new FirResolvedQualifierBuilder();
            firResolvedQualifierBuilder.setSource(ktSourceElement);
            firResolvedQualifierBuilder.setPackageFqName(firResolvedQualifier.getPackageFqName());
            FqName relativeClassFqName = firResolvedQualifier.getRelativeClassFqName();
            firResolvedQualifierBuilder.setRelativeClassFqName(relativeClassFqName != null ? relativeClassFqName.child(name2) : null);
            FirClassLikeSymbol<?> firClassLikeSymbol = (FirClassLikeSymbol) singleVisibleClassifier;
            firResolvedQualifierBuilder.setSymbol(firClassLikeSymbol);
            firResolvedQualifierBuilder.setFullyQualified(true);
            firResolvedQualifierBuilder.getTypeArguments().clear();
            firResolvedQualifierBuilder.getTypeArguments().addAll(typeArguments);
            firResolvedQualifierBuilder.getTypeArguments().addAll(firResolvedQualifier.getTypeArguments());
            List<ConeDiagnostic> nonFatalDiagnostics = firResolvedQualifierBuilder.getNonFatalDiagnostics();
            if (list == null) {
                list = CollectionsKt.emptyList();
            }
            nonFatalDiagnostics.addAll(list);
            firResolvedQualifierBuilder.getNonFatalDiagnostics().addAll(extractNonFatalDiagnostics(ktSourceElement, null, firClassLikeSymbol, firResolvedQualifier.getNonFatalDiagnostics(), session));
            FirResolvedQualifier firResolvedQualifierMo6809build = firResolvedQualifierBuilder.mo6809build();
            firResolvedQualifierMo6809build.replaceTypeRef(ResolveUtilsKt.typeForQualifier(components, firResolvedQualifierMo6809build));
            return firResolvedQualifierMo6809build;
        }
        return continueQualifierInPackage(firResolvedQualifier.getPackageFqName(), name2, typeArguments, list, components, ktSourceElement);
    }

    private static final FirResolvedQualifier continueQualifierInPackage(FqName fqName, Name name2, List<? extends FirTypeProjection> list, List<? extends ConeDiagnostic> list2, BodyResolveComponents bodyResolveComponents, KtSourceElement ktSourceElement) {
        FqName fqNameChild = fqName.child(name2);
        Intrinsics.checkNotNullExpressionValue(fqNameChild, "child(...)");
        if (bodyResolveComponents.getSymbolProvider().getPackage(fqNameChild) == null) {
            ClassId classId = ClassId.topLevel(fqNameChild);
            Intrinsics.checkNotNullExpressionValue(classId, "topLevel(...)");
            FirClassLikeSymbol<?> classLikeSymbolByClassId = bodyResolveComponents.getSymbolProvider().getClassLikeSymbolByClassId(classId);
            if (classLikeSymbolByClassId == null) {
                return null;
            }
            FirResolvedQualifierBuilder firResolvedQualifierBuilder = new FirResolvedQualifierBuilder();
            firResolvedQualifierBuilder.setSource(ktSourceElement);
            firResolvedQualifierBuilder.setPackageFqName(fqName);
            firResolvedQualifierBuilder.setRelativeClassFqName(classId.getRelativeClassName());
            firResolvedQualifierBuilder.setSymbol(classLikeSymbolByClassId);
            firResolvedQualifierBuilder.getTypeArguments().addAll(list);
            firResolvedQualifierBuilder.getNonFatalDiagnostics().addAll(extractNonFatalDiagnostics(ktSourceElement, null, classLikeSymbolByClassId, list2, bodyResolveComponents.getSession()));
            firResolvedQualifierBuilder.setFullyQualified(true);
            FirResolvedQualifier firResolvedQualifierMo6809build = firResolvedQualifierBuilder.mo6809build();
            firResolvedQualifierMo6809build.replaceTypeRef(ResolveUtilsKt.typeForQualifier(bodyResolveComponents, firResolvedQualifierMo6809build));
            return firResolvedQualifierMo6809build;
        }
        FirResolvedQualifierBuilder firResolvedQualifierBuilder2 = new FirResolvedQualifierBuilder();
        firResolvedQualifierBuilder2.setSource(ktSourceElement);
        firResolvedQualifierBuilder2.setPackageFqName(fqNameChild);
        firResolvedQualifierBuilder2.getTypeArguments().addAll(list);
        List<ConeDiagnostic> nonFatalDiagnostics = firResolvedQualifierBuilder2.getNonFatalDiagnostics();
        if (list2 == null) {
            list2 = CollectionsKt.emptyList();
        }
        nonFatalDiagnostics.addAll(list2);
        FirResolvedQualifier firResolvedQualifierMo6809build2 = firResolvedQualifierBuilder2.mo6809build();
        firResolvedQualifierMo6809build2.replaceTypeRef(ResolveUtilsKt.typeForQualifier(bodyResolveComponents, firResolvedQualifierMo6809build2));
        return firResolvedQualifierMo6809build2;
    }

    public static final List<ConeDiagnostic> extractNonFatalDiagnostics(KtSourceElement ktSourceElement, FirExpression firExpression, FirClassLikeSymbol<?> symbol, List<? extends ConeDiagnostic> list, FirSession session) {
        List<ConeDiagnostic> listEmptyList;
        List<ConeDiagnostic> list2;
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        Intrinsics.checkNotNullParameter(session, "session");
        ArrayList arrayList = null;
        FirResolvedQualifier firResolvedQualifier = firExpression instanceof FirResolvedQualifier ? (FirResolvedQualifier) firExpression : null;
        if (firResolvedQualifier == null || (listEmptyList = firResolvedQualifier.getNonFatalDiagnostics()) == null) {
            listEmptyList = CollectionsKt.emptyList();
        }
        FirClassLikeSymbol<?> firClassLikeSymbol = symbol;
        DeprecationInfo deprecationForCallSite = DeprecationUtilsKt.getDeprecationForCallSite(firClassLikeSymbol, session, new AnnotationUseSiteTarget[0]);
        if (deprecationForCallSite != null) {
            arrayList = new ArrayList();
            arrayList.addAll(listEmptyList);
            arrayList.add(new ConeDeprecated(ktSourceElement, firClassLikeSymbol, deprecationForCallSite));
        }
        if (list != null) {
            List<? extends ConeDiagnostic> list3 = list;
            if (!list3.isEmpty()) {
                if (arrayList == null) {
                    ArrayList arrayList2 = new ArrayList();
                    arrayList2.addAll(listEmptyList);
                    arrayList = arrayList2;
                }
                arrayList.addAll(list3);
            }
        }
        return (arrayList == null || (list2 = CollectionsKt.toList(arrayList)) == null) ? listEmptyList : list2;
    }
}
