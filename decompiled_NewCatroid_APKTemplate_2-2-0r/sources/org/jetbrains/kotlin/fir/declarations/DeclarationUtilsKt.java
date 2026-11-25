package org.jetbrains.kotlin.fir.declarations;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.resolve.TypeExpansionUtilsKt;
import org.jetbrains.kotlin.fir.scopes.impl.FirAbstractImportingScopeKt;
import org.jetbrains.kotlin.fir.scopes.impl.FirDeclaredMemberScopeProviderKt;
import org.jetbrains.kotlin.fir.scopes.impl.ImportedFromObjectOrStaticData;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirAnonymousObjectSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassifierSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirConstructorSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirEnumEntrySymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirTypeAliasSymbol;
import org.jetbrains.kotlin.fir.types.ConeBuiltinTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.TypeUtilsKt;
import org.jetbrains.kotlin.util.OperatorNameConventions;
import org.jetbrains.kotlin.util.PrivateForInline;

/* JADX INFO: compiled from: declarationUtils.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000d\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\u001a\u0010\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\r*\u00020\u000f\u001a\u0014\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00100\r*\u0006\u0012\u0002\b\u00030\u0011\u001a\u0018\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00140\u0013*\u00020\u000f2\u0006\u0010\u0015\u001a\u00020\u0016\u001a\u0014\u0010\u0017\u001a\u00020\u0001*\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001aH\u0002\u001a\u001b\u0010\u001b\u001a\u0004\u0018\u00010\u001c*\u0006\u0012\u0002\b\u00030\u001d2\u0006\u0010\u001e\u001a\u00020\u0016H\u0086\u0010\u001a\u0014\u0010\u001f\u001a\u0004\u0018\u00010\u001c*\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0016\u001a\u0016\u0010 \u001a\u00020\u0001*\u0006\u0012\u0002\b\u00030\t2\u0006\u0010\u0015\u001a\u00020\u0016\u001a\u0012\u0010!\u001a\u00020\u0001*\u00020\"2\u0006\u0010\u0015\u001a\u00020\u0016\u001a\n\u0010#\u001a\u00020\u0001*\u00020\u001c\u001a\u0016\u0010$\u001a\u00020\u0001*\u0006\u0012\u0002\b\u00030\t2\u0006\u0010\u0015\u001a\u00020\u0016\u001a\u0012\u0010%\u001a\u00020\u0001*\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001a\u001a\u0014\u0010&\u001a\u0004\u0018\u00010\u0014*\u00020\u000f2\u0006\u0010\u0015\u001a\u00020\u0016\"\u001b\u0010\u0000\u001a\u00020\u0001*\u00020\u00028F¢\u0006\f\u0012\u0004\b\u0003\u0010\u0004\u001a\u0004\b\u0000\u0010\u0005\"\u001f\u0010\u0000\u001a\u00020\u0001*\u00020\u00068Æ\u0002X\u0087\u0004¢\u0006\f\u0012\u0004\b\u0003\u0010\u0007\u001a\u0004\b\u0000\u0010\b\" \u0010\u0000\u001a\u00020\u0001*\u0006\u0012\u0002\b\u00030\t8Æ\u0002¢\u0006\f\u0012\u0004\b\u0003\u0010\n\u001a\u0004\b\u0000\u0010\u000b¨\u0006'"}, d2 = {"isJavaOrEnhancement", "", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "isJavaOrEnhancement$annotations", "(Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;)V", "(Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;)Z", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;", "(Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;)V", "(Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;)Z", "Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", "(Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;)V", "(Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;)Z", "collectEnumEntries", "", "Lorg/jetbrains/kotlin/fir/declarations/FirEnumEntry;", "Lorg/jetbrains/kotlin/fir/declarations/FirClass;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirEnumEntrySymbol;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassSymbol;", "constructors", "", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirConstructorSymbol;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "containsDefaultValue", "Lorg/jetbrains/kotlin/fir/declarations/FirFunction;", "index", "", "fullyExpandedClass", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirRegularClassSymbol;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassLikeSymbol;", "useSiteSession", "getConstructedClass", "isAnnotationConstructor", "isEquals", "Lorg/jetbrains/kotlin/fir/declarations/FirSimpleFunction;", "isInlineOrValueClass", "isPrimaryConstructorOfInlineOrValueClass", "itOrExpectHasDefaultParameterValue", "primaryConstructorIfAny", "providers"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class DeclarationUtilsKt {
    public static /* synthetic */ void isJavaOrEnhancement$annotations(FirDeclaration firDeclaration) {
    }

    @PrivateForInline
    public static /* synthetic */ void isJavaOrEnhancement$annotations(FirDeclarationOrigin firDeclarationOrigin) {
    }

    public static /* synthetic */ void isJavaOrEnhancement$annotations(FirBasedSymbol firBasedSymbol) {
    }

    public static final List<FirConstructorSymbol> constructors(FirClass firClass, FirSession session) {
        Intrinsics.checkNotNullParameter(firClass, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        final ArrayList arrayList = new ArrayList();
        FirDeclaredMemberScopeProviderKt.declaredMemberScope(session, firClass, (FirResolvePhase) null).processDeclaredConstructors(new Function1<FirConstructorSymbol, Unit>() { // from class: org.jetbrains.kotlin.fir.declarations.DeclarationUtilsKt.constructors.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(FirConstructorSymbol firConstructorSymbol) {
                invoke2(firConstructorSymbol);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(FirConstructorSymbol it) {
                Intrinsics.checkNotNullParameter(it, "it");
                arrayList.add(it);
            }
        });
        return arrayList;
    }

    public static final FirConstructorSymbol primaryConstructorIfAny(FirClass firClass, FirSession session) {
        Object next;
        Intrinsics.checkNotNullParameter(firClass, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        Iterator<T> it = constructors(firClass, session).iterator();
        while (it.hasNext()) {
            next = it.next();
            if (((FirConstructorSymbol) next).isPrimary()) {
                return (FirConstructorSymbol) next;
            }
        }
        next = null;
        return (FirConstructorSymbol) next;
    }

    public static final Collection<FirEnumEntry> collectEnumEntries(FirClass firClass) {
        Intrinsics.checkNotNullParameter(firClass, "<this>");
        firClass.getClassKind();
        ClassKind classKind = ClassKind.ENUM_CLASS;
        List<FirDeclaration> declarations = firClass.getDeclarations();
        ArrayList arrayList = new ArrayList();
        for (Object obj : declarations) {
            if (obj instanceof FirEnumEntry) {
                arrayList.add(obj);
            }
        }
        return arrayList;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final Collection<FirEnumEntrySymbol> collectEnumEntries(FirClassSymbol<?> firClassSymbol) {
        Intrinsics.checkNotNullParameter(firClassSymbol, "<this>");
        Collection<FirEnumEntry> collectionCollectEnumEntries = collectEnumEntries((FirClass) firClassSymbol.getFir());
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(collectionCollectEnumEntries, 10));
        Iterator<T> it = collectionCollectEnumEntries.iterator();
        while (it.hasNext()) {
            arrayList.add(((FirEnumEntry) it.next()).getSymbol());
        }
        return arrayList;
    }

    public static final FirRegularClassSymbol fullyExpandedClass(FirClassLikeSymbol<?> firClassLikeSymbol, FirSession useSiteSession) {
        Intrinsics.checkNotNullParameter(firClassLikeSymbol, "<this>");
        Intrinsics.checkNotNullParameter(useSiteSession, "useSiteSession");
        while (!(firClassLikeSymbol instanceof FirRegularClassSymbol)) {
            if (!(firClassLikeSymbol instanceof FirAnonymousObjectSymbol)) {
                if (!(firClassLikeSymbol instanceof FirTypeAliasSymbol)) {
                    throw new NoWhenBranchMatchedException();
                }
                FirResolvedTypeRef resolvedExpandedTypeRef = ((FirTypeAliasSymbol) firClassLikeSymbol).getResolvedExpandedTypeRef();
                FirResolvedTypeRef firResolvedTypeRef = resolvedExpandedTypeRef instanceof FirResolvedTypeRef ? resolvedExpandedTypeRef : null;
                ConeKotlinType type = firResolvedTypeRef != null ? firResolvedTypeRef.getType() : null;
                if (!(type instanceof ConeClassLikeType)) {
                    type = null;
                }
                ConeClassLikeType coneClassLikeType = (ConeClassLikeType) type;
                if (coneClassLikeType == null || (firClassLikeSymbol = TypeUtilsKt.toSymbol(coneClassLikeType, useSiteSession)) == null) {
                }
            }
            return null;
        }
        return (FirRegularClassSymbol) firClassLikeSymbol;
    }

    public static final boolean isAnnotationConstructor(FirBasedSymbol<?> firBasedSymbol, FirSession session) {
        Intrinsics.checkNotNullParameter(firBasedSymbol, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        if (!(firBasedSymbol instanceof FirConstructorSymbol)) {
            return false;
        }
        FirRegularClassSymbol constructedClass = getConstructedClass((FirConstructorSymbol) firBasedSymbol, session);
        return (constructedClass != null ? constructedClass.getClassKind() : null) == ClassKind.ANNOTATION_CLASS;
    }

    public static final boolean isPrimaryConstructorOfInlineOrValueClass(FirBasedSymbol<?> firBasedSymbol, FirSession session) {
        FirConstructorSymbol firConstructorSymbol;
        FirRegularClassSymbol constructedClass;
        Intrinsics.checkNotNullParameter(firBasedSymbol, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        return (firBasedSymbol instanceof FirConstructorSymbol) && (constructedClass = getConstructedClass((firConstructorSymbol = (FirConstructorSymbol) firBasedSymbol), session)) != null && isInlineOrValueClass(constructedClass) && firConstructorSymbol.isPrimary();
    }

    public static final FirRegularClassSymbol getConstructedClass(FirConstructorSymbol firConstructorSymbol, FirSession session) {
        Intrinsics.checkNotNullParameter(firConstructorSymbol, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        FirClassifierSymbol<?> symbol = TypeUtilsKt.toSymbol(TypeExpansionUtilsKt.fullyExpandedType(FirTypeUtilsKt.getConeType(firConstructorSymbol.getResolvedReturnTypeRef()), session), session);
        if (symbol instanceof FirRegularClassSymbol) {
            return (FirRegularClassSymbol) symbol;
        }
        return null;
    }

    public static final boolean isInlineOrValueClass(FirRegularClassSymbol firRegularClassSymbol) {
        Intrinsics.checkNotNullParameter(firRegularClassSymbol, "<this>");
        if (firRegularClassSymbol.getClassKind() != ClassKind.CLASS) {
            return false;
        }
        return firRegularClassSymbol.getRawStatus().isInline();
    }

    public static final boolean isJavaOrEnhancement(FirDeclarationOrigin firDeclarationOrigin) {
        Intrinsics.checkNotNullParameter(firDeclarationOrigin, "<this>");
        return (firDeclarationOrigin instanceof FirDeclarationOrigin.Java) || Intrinsics.areEqual(firDeclarationOrigin, FirDeclarationOrigin.Enhancement.INSTANCE);
    }

    public static final boolean isJavaOrEnhancement(FirDeclaration firDeclaration) {
        ImportedFromObjectOrStaticData importedFromObjectOrStaticData;
        FirCallableDeclaration original;
        Intrinsics.checkNotNullParameter(firDeclaration, "<this>");
        FirDeclarationOrigin origin = firDeclaration.getOrigin();
        if ((origin instanceof FirDeclarationOrigin.Java) || Intrinsics.areEqual(origin, FirDeclarationOrigin.Enhancement.INSTANCE)) {
            return true;
        }
        FirCallableDeclaration firCallableDeclaration = firDeclaration instanceof FirCallableDeclaration ? (FirCallableDeclaration) firDeclaration : null;
        return (firCallableDeclaration == null || (importedFromObjectOrStaticData = FirAbstractImportingScopeKt.getImportedFromObjectOrStaticData(firCallableDeclaration)) == null || (original = importedFromObjectOrStaticData.getOriginal()) == null || !isJavaOrEnhancement(original)) ? false : true;
    }

    public static final boolean isJavaOrEnhancement(FirBasedSymbol<?> firBasedSymbol) {
        ImportedFromObjectOrStaticData importedFromObjectOrStaticData;
        FirCallableDeclaration original;
        Intrinsics.checkNotNullParameter(firBasedSymbol, "<this>");
        FirDeclarationOrigin origin = firBasedSymbol.getOrigin();
        if ((origin instanceof FirDeclarationOrigin.Java) || Intrinsics.areEqual(origin, FirDeclarationOrigin.Enhancement.INSTANCE)) {
            return true;
        }
        FirDeclaration fir = firBasedSymbol.getFir();
        FirCallableDeclaration firCallableDeclaration = fir instanceof FirCallableDeclaration ? (FirCallableDeclaration) fir : null;
        return (firCallableDeclaration == null || (importedFromObjectOrStaticData = FirAbstractImportingScopeKt.getImportedFromObjectOrStaticData(firCallableDeclaration)) == null || (original = importedFromObjectOrStaticData.getOriginal()) == null || !isJavaOrEnhancement(original)) ? false : true;
    }

    private static final boolean containsDefaultValue(FirFunction firFunction, int i) {
        return firFunction.getValueParameters().get(i).getDefaultValue() != null;
    }

    public static final boolean itOrExpectHasDefaultParameterValue(FirFunction firFunction, int i) {
        FirFunction firFunction2;
        Intrinsics.checkNotNullParameter(firFunction, "<this>");
        if (containsDefaultValue(firFunction, i)) {
            return true;
        }
        FirFunctionSymbol<?> singleExpectForActualOrNull = ExpectActualAttributesKt.getSingleExpectForActualOrNull((FirFunctionSymbol<?>) firFunction.getSymbol());
        return (singleExpectForActualOrNull == null || (firFunction2 = (FirFunction) singleExpectForActualOrNull.getFir()) == null || !containsDefaultValue(firFunction2, i)) ? false : true;
    }

    public static final boolean isEquals(FirSimpleFunction firSimpleFunction, FirSession session) {
        Intrinsics.checkNotNullParameter(firSimpleFunction, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        if (Intrinsics.areEqual(firSimpleFunction.getName(), OperatorNameConventions.EQUALS) && firSimpleFunction.getValueParameters().size() == 1 && firSimpleFunction.getContextReceivers().isEmpty() && firSimpleFunction.getReceiverParameter() == null) {
            return ConeBuiltinTypeUtilsKt.isNullableAny(TypeExpansionUtilsKt.fullyExpandedType(FirTypeUtilsKt.getConeType(((FirValueParameter) CollectionsKt.first((List) firSimpleFunction.getValueParameters())).getReturnTypeRef()), session));
        }
        return false;
    }
}
