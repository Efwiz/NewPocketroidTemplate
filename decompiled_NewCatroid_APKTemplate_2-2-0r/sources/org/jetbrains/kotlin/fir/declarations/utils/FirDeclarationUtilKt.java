package org.jetbrains.kotlin.fir.declarations.utils;

import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.fir.declarations.FirAnonymousInitializer;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin;
import org.jetbrains.kotlin.fir.declarations.FirField;
import org.jetbrains.kotlin.fir.declarations.FirFile;
import org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.declarations.FirTypeAlias;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirValueParameterSymbol;
import org.jetbrains.kotlin.fir.types.ConeBuiltinTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.util.OperatorNameConventions;

/* JADX INFO: compiled from: FirDeclarationUtil.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000Z\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\"\u001b\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005\"\u0015\u0010\u0006\u001a\u00020\u0007*\u00020\b8F¢\u0006\u0006\u001a\u0004\b\t\u0010\n\"\u001b\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\u0001*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\r\u0010\u0005\"\u0017\u0010\u000e\u001a\u0004\u0018\u00010\u000f*\u00020\u00108F¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012\"\u0015\u0010\u0013\u001a\u00020\u0014*\u00020\u00158F¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0016\"\u0016\u0010\u0017\u001a\u00020\u0014*\u00020\u00188Æ\u0002¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0019\"\u0016\u0010\u001a\u001a\u00020\u0014*\u00020\u00188Æ\u0002¢\u0006\u0006\u001a\u0004\b\u001a\u0010\u0019\"\u0015\u0010\u001b\u001a\u00020\u0014*\u00020\u001c8F¢\u0006\u0006\u001a\u0004\b\u001b\u0010\u001d\"\u0015\u0010\u001e\u001a\u00020\u0014*\u00020\u00188F¢\u0006\u0006\u001a\u0004\b\u001e\u0010\u0019\"\u0016\u0010\u001f\u001a\u00020\u0014*\u00020\u00188Æ\u0002¢\u0006\u0006\u001a\u0004\b\u001f\u0010\u0019\"\u0016\u0010 \u001a\u00020\u0014*\u00020\u00188Æ\u0002¢\u0006\u0006\u001a\u0004\b \u0010\u0019\"\u0015\u0010!\u001a\u00020\"*\u00020#8F¢\u0006\u0006\u001a\u0004\b$\u0010%\"\u001b\u0010&\u001a\b\u0012\u0004\u0012\u00020\u000f0\u0001*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b'\u0010\u0005¨\u0006("}, d2 = {"anonymousInitializers", "", "Lorg/jetbrains/kotlin/fir/declarations/FirAnonymousInitializer;", "Lorg/jetbrains/kotlin/fir/declarations/FirClass;", "getAnonymousInitializers", "(Lorg/jetbrains/kotlin/fir/declarations/FirClass;)Ljava/util/List;", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "Lorg/jetbrains/kotlin/fir/declarations/FirClassLikeDeclaration;", "getClassId", "(Lorg/jetbrains/kotlin/fir/declarations/FirClassLikeDeclaration;)Lorg/jetbrains/kotlin/name/ClassId;", "delegateFields", "Lorg/jetbrains/kotlin/fir/declarations/FirField;", "getDelegateFields", "expandedConeType", "Lorg/jetbrains/kotlin/fir/types/ConeClassLikeType;", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeAlias;", "getExpandedConeType", "(Lorg/jetbrains/kotlin/fir/declarations/FirTypeAlias;)Lorg/jetbrains/kotlin/fir/types/ConeClassLikeType;", "isExtension", "", "Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;", "(Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;)Z", "isFromLibrary", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "(Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;)Z", "isJava", "isMethodOfAny", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirNamedFunctionSymbol;", "(Lorg/jetbrains/kotlin/fir/symbols/impl/FirNamedFunctionSymbol;)Z", "isNonLocal", "isPrecompiled", "isSynthetic", "nameOrSpecialName", "Lorg/jetbrains/kotlin/name/Name;", "Lorg/jetbrains/kotlin/fir/declarations/FirMemberDeclaration;", "getNameOrSpecialName", "(Lorg/jetbrains/kotlin/fir/declarations/FirMemberDeclaration;)Lorg/jetbrains/kotlin/name/Name;", "superConeTypes", "getSuperConeTypes", Constants.TYPE_TREE}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FirDeclarationUtilKt {
    public static final ConeClassLikeType getExpandedConeType(FirTypeAlias firTypeAlias) {
        Intrinsics.checkNotNullParameter(firTypeAlias, "<this>");
        FirTypeRef expandedTypeRef = firTypeAlias.getExpandedTypeRef();
        FirResolvedTypeRef firResolvedTypeRef = expandedTypeRef instanceof FirResolvedTypeRef ? (FirResolvedTypeRef) expandedTypeRef : null;
        ConeKotlinType type = firResolvedTypeRef != null ? firResolvedTypeRef.getType() : null;
        return (ConeClassLikeType) (type instanceof ConeClassLikeType ? type : null);
    }

    public static final ClassId getClassId(FirClassLikeDeclaration firClassLikeDeclaration) {
        Intrinsics.checkNotNullParameter(firClassLikeDeclaration, "<this>");
        return firClassLikeDeclaration.getSymbol().getClassId();
    }

    public static final List<ConeClassLikeType> getSuperConeTypes(FirClass firClass) {
        Intrinsics.checkNotNullParameter(firClass, "<this>");
        List<FirTypeRef> superTypeRefs = firClass.getSuperTypeRefs();
        ArrayList arrayList = new ArrayList();
        for (FirTypeRef firTypeRef : superTypeRefs) {
            FirResolvedTypeRef firResolvedTypeRef = firTypeRef instanceof FirResolvedTypeRef ? (FirResolvedTypeRef) firTypeRef : null;
            ConeKotlinType type = firResolvedTypeRef != null ? firResolvedTypeRef.getType() : null;
            ConeClassLikeType coneClassLikeType = (ConeClassLikeType) (type instanceof ConeClassLikeType ? type : null);
            if (coneClassLikeType != null) {
                arrayList.add(coneClassLikeType);
            }
        }
        return arrayList;
    }

    public static final List<FirAnonymousInitializer> getAnonymousInitializers(FirClass firClass) {
        Intrinsics.checkNotNullParameter(firClass, "<this>");
        List<FirDeclaration> declarations = firClass.getDeclarations();
        ArrayList arrayList = new ArrayList();
        for (Object obj : declarations) {
            if (obj instanceof FirAnonymousInitializer) {
                arrayList.add(obj);
            }
        }
        return arrayList;
    }

    public static final List<FirField> getDelegateFields(FirClass firClass) {
        Intrinsics.checkNotNullParameter(firClass, "<this>");
        List<FirDeclaration> declarations = firClass.getDeclarations();
        ArrayList arrayList = new ArrayList();
        for (Object obj : declarations) {
            if (obj instanceof FirField) {
                arrayList.add(obj);
            }
        }
        ArrayList arrayList2 = new ArrayList();
        for (Object obj2 : arrayList) {
            if (((FirField) obj2).getOrigin() instanceof FirDeclarationOrigin.Synthetic) {
                arrayList2.add(obj2);
            }
        }
        return arrayList2;
    }

    public static final boolean isJava(FirDeclaration firDeclaration) {
        Intrinsics.checkNotNullParameter(firDeclaration, "<this>");
        return firDeclaration.getOrigin() instanceof FirDeclarationOrigin.Java;
    }

    public static final boolean isFromLibrary(FirDeclaration firDeclaration) {
        Intrinsics.checkNotNullParameter(firDeclaration, "<this>");
        return Intrinsics.areEqual(firDeclaration.getOrigin(), FirDeclarationOrigin.Library.INSTANCE) || Intrinsics.areEqual(firDeclaration.getOrigin(), FirDeclarationOrigin.Java.Library.INSTANCE);
    }

    public static final boolean isPrecompiled(FirDeclaration firDeclaration) {
        Intrinsics.checkNotNullParameter(firDeclaration, "<this>");
        return Intrinsics.areEqual(firDeclaration.getOrigin(), FirDeclarationOrigin.Precompiled.INSTANCE);
    }

    public static final boolean isSynthetic(FirDeclaration firDeclaration) {
        Intrinsics.checkNotNullParameter(firDeclaration, "<this>");
        return firDeclaration.getOrigin() instanceof FirDeclarationOrigin.Synthetic;
    }

    public static final boolean isNonLocal(FirDeclaration firDeclaration) {
        Intrinsics.checkNotNullParameter(firDeclaration, "<this>");
        if (firDeclaration instanceof FirFile) {
            return true;
        }
        if (firDeclaration instanceof FirCallableDeclaration) {
            if (!((FirCallableDeclaration) firDeclaration).getSymbol().getCallableId().isLocal()) {
                return true;
            }
        } else if ((firDeclaration instanceof FirClassLikeDeclaration) && !((FirClassLikeDeclaration) firDeclaration).getSymbol().getClassId().isLocal()) {
            return true;
        }
        return false;
    }

    public static final boolean isExtension(FirCallableDeclaration firCallableDeclaration) {
        Intrinsics.checkNotNullParameter(firCallableDeclaration, "<this>");
        return firCallableDeclaration.getReceiverParameter() != null;
    }

    public static final Name getNameOrSpecialName(FirMemberDeclaration firMemberDeclaration) {
        Intrinsics.checkNotNullParameter(firMemberDeclaration, "<this>");
        if (firMemberDeclaration instanceof FirCallableDeclaration) {
            return ((FirCallableDeclaration) firMemberDeclaration).getSymbol().getCallableId().getCallableName();
        }
        if (firMemberDeclaration instanceof FirClass) {
            Name shortClassName = getClassId((FirClassLikeDeclaration) firMemberDeclaration).getShortClassName();
            Intrinsics.checkNotNullExpressionValue(shortClassName, "getShortClassName(...)");
            return shortClassName;
        }
        if (firMemberDeclaration instanceof FirTypeAlias) {
            return ((FirTypeAlias) firMemberDeclaration).getName();
        }
        throw new NoWhenBranchMatchedException();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final boolean isMethodOfAny(FirNamedFunctionSymbol firNamedFunctionSymbol) {
        ConeKotlinType resolvedReturnType;
        Intrinsics.checkNotNullParameter(firNamedFunctionSymbol, "<this>");
        if (firNamedFunctionSymbol.getReceiverParameter() != null || !firNamedFunctionSymbol.getResolvedContextReceivers().isEmpty()) {
            return false;
        }
        Name name2 = firNamedFunctionSymbol.getName();
        if (Intrinsics.areEqual(name2, OperatorNameConventions.EQUALS)) {
            FirValueParameterSymbol firValueParameterSymbol = (FirValueParameterSymbol) CollectionsKt.singleOrNull((List) firNamedFunctionSymbol.getValueParameterSymbols());
            return (firValueParameterSymbol == null || (resolvedReturnType = firValueParameterSymbol.getResolvedReturnType()) == null || !ConeBuiltinTypeUtilsKt.isNullableAny(resolvedReturnType)) ? false : true;
        }
        if (Intrinsics.areEqual(name2, OperatorNameConventions.HASH_CODE) ? true : Intrinsics.areEqual(name2, OperatorNameConventions.TO_STRING)) {
            return ((FirSimpleFunction) firNamedFunctionSymbol.getFir()).getValueParameters().isEmpty();
        }
        return false;
    }
}
