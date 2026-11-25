package org.jetbrains.kotlin.fir.analysis.js.checkers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.Visibilities;
import org.jetbrains.kotlin.descriptors.Visibility;
import org.jetbrains.kotlin.fir.ClassMembersKt;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.analysis.checkers.FirHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.declarations.FirAnnotationUtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirFile;
import org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration;
import org.jetbrains.kotlin.fir.declarations.utils.FirDeclarationUtilKt;
import org.jetbrains.kotlin.fir.resolve.providers.FirProviderKt;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirPropertyAccessorSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirPropertySymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirValueParameterSymbol;
import org.jetbrains.kotlin.fir.types.ConeBuiltinTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.TypeUtilsKt;
import org.jetbrains.kotlin.js.PredefinedAnnotation;
import org.jetbrains.kotlin.js.common.IdentifierPolicyKt;
import org.jetbrains.kotlin.name.JsStandardClassIds;
import org.jetbrains.kotlin.name.Name;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: FirJsHelpers.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000>\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a \u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u00022\n\u0010\u0006\u001a\u0006\u0012\u0002\b\u00030\u00022\u0006\u0010\u0007\u001a\u00020\bH\u0000\u001a\u000e\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\n\u001a\u001a\u0010\f\u001a\u0004\u0018\u00010\r*\u0006\u0012\u0002\b\u00030\u00022\u0006\u0010\u0007\u001a\u00020\bH\u0000\u001a\u0018\u0010\u000e\u001a\u0004\u0018\u00010\n*\u0006\u0012\u0002\b\u00030\u00022\u0006\u0010\u0007\u001a\u00020\b\u001a\u001a\u0010\u000f\u001a\u0004\u0018\u00010\n*\u0006\u0012\u0002\b\u00030\u00022\u0006\u0010\u0007\u001a\u00020\bH\u0000\u001a\u0016\u0010\u0010\u001a\u00020\u0001*\u0006\u0012\u0002\b\u00030\u00022\u0006\u0010\u0007\u001a\u00020\b\u001a\u0016\u0010\u0010\u001a\u00020\u0001*\u0006\u0012\u0002\b\u00030\u00022\u0006\u0010\u0011\u001a\u00020\u0012\u001a\u0016\u0010\u0013\u001a\u00020\u0001*\u0006\u0012\u0002\b\u00030\u00022\u0006\u0010\u0007\u001a\u00020\b\u001a\u0016\u0010\u0014\u001a\u00020\u0001*\u0006\u0012\u0002\b\u00030\u00022\u0006\u0010\u0007\u001a\u00020\b\u001a\u0016\u0010\u0014\u001a\u00020\u0001*\u0006\u0012\u0002\b\u00030\u00022\u0006\u0010\u0011\u001a\u00020\u0012\u001a\u0016\u0010\u0015\u001a\u00020\u0001*\u0006\u0012\u0002\b\u00030\u00022\u0006\u0010\u0007\u001a\u00020\b\u001a\u0016\u0010\u0015\u001a\u00020\u0001*\u0006\u0012\u0002\b\u00030\u00022\u0006\u0010\u0011\u001a\u00020\u0012\u001a\u0016\u0010\u0016\u001a\u00020\u0001*\u0006\u0012\u0002\b\u00030\u00022\u0006\u0010\u0007\u001a\u00020\b\u001a\u0016\u0010\u0016\u001a\u00020\u0001*\u0006\u0012\u0002\b\u00030\u00022\u0006\u0010\u0011\u001a\u00020\u0012\u001a\u0016\u0010\u0017\u001a\u00020\u0001*\u0006\u0012\u0002\b\u00030\u00182\u0006\u0010\u0011\u001a\u00020\u0012\u001a\u0016\u0010\u0019\u001a\u00020\u0001*\u0006\u0012\u0002\b\u00030\u00022\u0006\u0010\u0007\u001a\u00020\b\u001a\u0016\u0010\u0019\u001a\u00020\u0001*\u0006\u0012\u0002\b\u00030\u00022\u0006\u0010\u0011\u001a\u00020\u0012\u001a\u0016\u0010\u001a\u001a\u0004\u0018\u00010\u001b*\u00020\u001c2\u0006\u0010\u0007\u001a\u00020\bH\u0000\"\u001c\u0010\u0000\u001a\u00020\u0001*\u0006\u0012\u0002\b\u00030\u00028BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0000\u0010\u0003\"\u001c\u0010\u0004\u001a\u00020\u0001*\u0006\u0012\u0002\b\u00030\u00028BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0003¨\u0006\u001d"}, d2 = {"isExpect", "", "Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", "(Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;)Z", "isExternal", "getRootClassLikeSymbolOrSelf", NativeSymbol.TYPE_NAME, "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "sanitizeName", "", "name", "getContainingFile", "Lorg/jetbrains/kotlin/fir/declarations/FirFile;", "getJsName", "getStableNameInJavaScript", "isEffectivelyExternal", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "isEffectivelyExternalMember", "isExportedObject", "isNativeInterface", "isNativeObject", "isOverridingExternalWithOptionalParams", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirFunctionSymbol;", "isPredefinedObject", "superClassNotAny", "Lorg/jetbrains/kotlin/fir/types/ConeClassLikeType;", "Lorg/jetbrains/kotlin/fir/declarations/FirClass;", "checkers.js"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FirJsHelpersKt {
    private static final boolean isExternal(FirBasedSymbol<?> firBasedSymbol) {
        if (!(firBasedSymbol instanceof FirCallableSymbol)) {
            if (firBasedSymbol instanceof FirClassSymbol) {
                return ((FirClassLikeSymbol) firBasedSymbol).getRawStatus().isExternal();
            }
            return false;
        }
        return ((FirCallableSymbol) firBasedSymbol).getRawStatus().isExternal();
    }

    public static final boolean isEffectivelyExternal(FirBasedSymbol<?> firBasedSymbol, FirSession session) {
        FirPropertySymbol firPropertySymbol;
        FirPropertyAccessorSymbol getterSymbol;
        FirPropertyAccessorSymbol setterSymbol;
        Intrinsics.checkNotNullParameter(firBasedSymbol, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        if ((firBasedSymbol.getFir() instanceof FirMemberDeclaration) && isExternal(firBasedSymbol)) {
            return true;
        }
        if ((firBasedSymbol instanceof FirPropertyAccessorSymbol) && isEffectivelyExternal(((FirPropertyAccessorSymbol) firBasedSymbol).getPropertySymbol(), session)) {
            return true;
        }
        if ((firBasedSymbol instanceof FirPropertySymbol) && (getterSymbol = (firPropertySymbol = (FirPropertySymbol) firBasedSymbol).getGetterSymbol()) != null && getterSymbol.getRawStatus().isExternal() && (!firPropertySymbol.isVar() || ((setterSymbol = firPropertySymbol.getSetterSymbol()) != null && setterSymbol.getRawStatus().isExternal()))) {
            return true;
        }
        FirClassLikeSymbol<?> containingClassSymbol = FirHelpersKt.getContainingClassSymbol(firBasedSymbol, session);
        return containingClassSymbol != null && isEffectivelyExternal(containingClassSymbol, session);
    }

    public static final boolean isEffectivelyExternalMember(FirBasedSymbol<?> firBasedSymbol, FirSession session) {
        Intrinsics.checkNotNullParameter(firBasedSymbol, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        return (firBasedSymbol.getFir() instanceof FirMemberDeclaration) && isEffectivelyExternal(firBasedSymbol, session);
    }

    public static final boolean isEffectivelyExternal(FirBasedSymbol<?> firBasedSymbol, CheckerContext context) {
        Intrinsics.checkNotNullParameter(firBasedSymbol, "<this>");
        Intrinsics.checkNotNullParameter(context, "context");
        return isEffectivelyExternal(firBasedSymbol, context.getSession());
    }

    public static final boolean isOverridingExternalWithOptionalParams(FirFunctionSymbol<?> firFunctionSymbol, CheckerContext context) {
        List<FirNamedFunctionSymbol> listDirectOverriddenFunctions;
        Intrinsics.checkNotNullParameter(firFunctionSymbol, "<this>");
        Intrinsics.checkNotNullParameter(context, "context");
        FirFunctionSymbol<?> firFunctionSymbol2 = firFunctionSymbol;
        if (!ClassMembersKt.isSubstitutionOrIntersectionOverride(firFunctionSymbol2) && firFunctionSymbol2.getResolvedStatus().getModality() == Modality.ABSTRACT) {
            return false;
        }
        FirNamedFunctionSymbol firNamedFunctionSymbol = firFunctionSymbol instanceof FirNamedFunctionSymbol ? (FirNamedFunctionSymbol) firFunctionSymbol : null;
        if (firNamedFunctionSymbol != null && (listDirectOverriddenFunctions = FirHelpersKt.directOverriddenFunctions(firNamedFunctionSymbol, context)) != null) {
            ArrayList arrayList = new ArrayList();
            for (Object obj : listDirectOverriddenFunctions) {
                if (isEffectivelyExternal((FirNamedFunctionSymbol) obj, context)) {
                    arrayList.add(obj);
                }
            }
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                List<FirValueParameterSymbol> valueParameterSymbols = ((FirNamedFunctionSymbol) it.next()).getValueParameterSymbols();
                if (!(valueParameterSymbols instanceof Collection) || !valueParameterSymbols.isEmpty()) {
                    Iterator<T> it2 = valueParameterSymbols.iterator();
                    while (it2.hasNext()) {
                        if (((FirValueParameterSymbol) it2.next()).getHasDefaultValue()) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public static final String getJsName(FirBasedSymbol<?> firBasedSymbol, FirSession session) {
        Intrinsics.checkNotNullParameter(firBasedSymbol, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        return FirHelpersKt.getAnnotationStringParameter(firBasedSymbol, JsStandardClassIds.Annotations.JsName, session);
    }

    public static final String sanitizeName(String name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        String str = name2;
        if (str.length() == 0) {
            return "_";
        }
        char cFirst = StringsKt.first(str);
        if (!IdentifierPolicyKt.isES5IdentifierStart(cFirst)) {
            cFirst = '_';
        }
        StringBuilder sbAppend = new StringBuilder().append(cFirst);
        String strDrop = StringsKt.drop(name2, 1);
        ArrayList arrayList = new ArrayList(strDrop.length());
        for (int i = 0; i < strDrop.length(); i++) {
            char cCharAt = strDrop.charAt(i);
            if (!IdentifierPolicyKt.isES5IdentifierPart(cCharAt)) {
                cCharAt = '_';
            }
            arrayList.add(Character.valueOf(cCharAt));
        }
        return sbAppend.append(CollectionsKt.joinToString$default(arrayList, "", null, null, 0, null, null, 62, null)).toString();
    }

    public static final boolean isNativeObject(FirBasedSymbol<?> firBasedSymbol, FirSession session) {
        Intrinsics.checkNotNullParameter(firBasedSymbol, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        if (FirHelpersKt.hasAnnotationOrInsideAnnotatedClass(firBasedSymbol, JsStandardClassIds.Annotations.JsNative, session) || isEffectivelyExternal(firBasedSymbol, session)) {
            return true;
        }
        if (firBasedSymbol instanceof FirPropertyAccessorSymbol) {
            return FirHelpersKt.hasAnnotationOrInsideAnnotatedClass(((FirPropertyAccessorSymbol) firBasedSymbol).getPropertySymbol(), JsStandardClassIds.Annotations.JsNative, session);
        }
        return false;
    }

    public static final boolean isNativeInterface(FirBasedSymbol<?> firBasedSymbol, FirSession session) {
        Intrinsics.checkNotNullParameter(firBasedSymbol, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        if (isNativeObject(firBasedSymbol, session)) {
            FirDeclaration fir = firBasedSymbol.getFir();
            FirClass firClass = fir instanceof FirClass ? (FirClass) fir : null;
            if (firClass != null && firClass.getClassKind() == ClassKind.INTERFACE) {
                return true;
            }
        }
        return false;
    }

    private static final boolean isExpect(FirBasedSymbol<?> firBasedSymbol) {
        if (!(firBasedSymbol instanceof FirCallableSymbol)) {
            if (firBasedSymbol instanceof FirClassSymbol) {
                return ((FirClassLikeSymbol) firBasedSymbol).getRawStatus().isExpect();
            }
            return false;
        }
        return ((FirCallableSymbol) firBasedSymbol).getRawStatus().isExpect();
    }

    public static final boolean isPredefinedObject(FirBasedSymbol<?> firBasedSymbol, FirSession session) {
        Intrinsics.checkNotNullParameter(firBasedSymbol, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        if (((firBasedSymbol.getFir() instanceof FirMemberDeclaration) && isExpect(firBasedSymbol)) || isEffectivelyExternalMember(firBasedSymbol, session)) {
            return true;
        }
        for (PredefinedAnnotation predefinedAnnotation : PredefinedAnnotation.values()) {
            if (FirHelpersKt.hasAnnotationOrInsideAnnotatedClass(firBasedSymbol, predefinedAnnotation.getClassId(), session)) {
                return true;
            }
        }
        return false;
    }

    public static final boolean isExportedObject(FirBasedSymbol<?> firBasedSymbol, FirSession session) {
        FirFile containingFile;
        Intrinsics.checkNotNullParameter(firBasedSymbol, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        FirDeclaration fir = firBasedSymbol.getFir();
        if (fir instanceof FirMemberDeclaration) {
            Visibility visibility = ((FirMemberDeclaration) fir).getStatus().getVisibility();
            if (!Intrinsics.areEqual(visibility, Visibilities.Public.INSTANCE) && !Intrinsics.areEqual(visibility, Visibilities.Protected.INSTANCE)) {
                return false;
            }
        }
        if (FirHelpersKt.hasAnnotationOrInsideAnnotatedClass(firBasedSymbol, JsStandardClassIds.Annotations.JsExportIgnore, session)) {
            return false;
        }
        return FirHelpersKt.hasAnnotationOrInsideAnnotatedClass(firBasedSymbol, JsStandardClassIds.Annotations.JsExport, session) || ((containingFile = getContainingFile(firBasedSymbol, session)) != null && FirAnnotationUtilsKt.hasAnnotation((FirDeclaration) containingFile, JsStandardClassIds.Annotations.JsExport, session));
    }

    public static final FirFile getContainingFile(FirBasedSymbol<?> firBasedSymbol, FirSession session) {
        Intrinsics.checkNotNullParameter(firBasedSymbol, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        if (firBasedSymbol instanceof FirCallableSymbol) {
            return FirProviderKt.getFirProvider(session).getFirCallableContainerFile((FirCallableSymbol) firBasedSymbol);
        }
        if (firBasedSymbol instanceof FirClassLikeSymbol) {
            return FirProviderKt.getFirProvider(session).getFirClassifierContainerFileIfAny((FirClassLikeSymbol<?>) firBasedSymbol);
        }
        return null;
    }

    public static final boolean isNativeObject(FirBasedSymbol<?> firBasedSymbol, CheckerContext context) {
        Intrinsics.checkNotNullParameter(firBasedSymbol, "<this>");
        Intrinsics.checkNotNullParameter(context, "context");
        return isNativeObject(firBasedSymbol, context.getSession());
    }

    public static final boolean isNativeInterface(FirBasedSymbol<?> firBasedSymbol, CheckerContext context) {
        Intrinsics.checkNotNullParameter(firBasedSymbol, "<this>");
        Intrinsics.checkNotNullParameter(context, "context");
        return isNativeInterface(firBasedSymbol, context.getSession());
    }

    public static final boolean isPredefinedObject(FirBasedSymbol<?> firBasedSymbol, CheckerContext context) {
        Intrinsics.checkNotNullParameter(firBasedSymbol, "<this>");
        Intrinsics.checkNotNullParameter(context, "context");
        return isPredefinedObject(firBasedSymbol, context.getSession());
    }

    public static final boolean isExportedObject(FirBasedSymbol<?> firBasedSymbol, CheckerContext context) {
        Intrinsics.checkNotNullParameter(firBasedSymbol, "<this>");
        Intrinsics.checkNotNullParameter(context, "context");
        return isExportedObject(firBasedSymbol, context.getSession());
    }

    public static final ConeClassLikeType superClassNotAny(FirClass firClass, FirSession session) {
        Object obj;
        Object next;
        FirClassLikeSymbol<?> symbol;
        Intrinsics.checkNotNullParameter(firClass, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        List<ConeClassLikeType> superConeTypes = FirDeclarationUtilKt.getSuperConeTypes(firClass);
        ArrayList arrayList = new ArrayList();
        for (Object obj2 : superConeTypes) {
            ConeClassLikeType coneClassLikeType = (ConeClassLikeType) obj2;
            if (!ConeBuiltinTypeUtilsKt.isAny(coneClassLikeType) && !ConeBuiltinTypeUtilsKt.isNullableAny(coneClassLikeType)) {
                arrayList.add(obj2);
            }
        }
        Iterator it = arrayList.iterator();
        do {
            obj = null;
            if (it.hasNext()) {
                next = it.next();
                symbol = TypeUtilsKt.toSymbol((ConeClassLikeType) next, session);
            }
            return (ConeClassLikeType) obj;
        } while ((symbol != null ? FirHelpersKt.getClassKind(symbol) : null) != ClassKind.CLASS);
        obj = next;
        return (ConeClassLikeType) obj;
    }

    public static final FirBasedSymbol<?> getRootClassLikeSymbolOrSelf(FirBasedSymbol<?> symbol, FirSession session) {
        FirBasedSymbol<?> rootClassLikeSymbolOrSelf;
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        Intrinsics.checkNotNullParameter(session, "session");
        FirClassLikeSymbol<?> containingClassSymbol = FirHelpersKt.getContainingClassSymbol(symbol, session);
        return (containingClassSymbol == null || (rootClassLikeSymbolOrSelf = getRootClassLikeSymbolOrSelf(containingClassSymbol, session)) == null) ? symbol : rootClassLikeSymbolOrSelf;
    }

    /* JADX WARN: Code duplicated, block: B:16:0x0034  */
    public static final String getStableNameInJavaScript(FirBasedSymbol<?> firBasedSymbol, FirSession session) {
        Modality modality;
        Name nameOrSpecialName;
        Intrinsics.checkNotNullParameter(firBasedSymbol, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        String jsName = getJsName(firBasedSymbol, session);
        if (jsName != null) {
            return jsName;
        }
        boolean z = false;
        boolean z2 = isEffectivelyExternal(firBasedSymbol, session) || isExportedObject(firBasedSymbol, session);
        if (!(firBasedSymbol instanceof FirClassLikeSymbol)) {
            if (firBasedSymbol instanceof FirCallableSymbol) {
                FirClassLikeSymbol<?> containingClassSymbol = FirHelpersKt.getContainingClassSymbol(firBasedSymbol, session);
                if (containingClassSymbol == null) {
                    FirCallableSymbol firCallableSymbol = (FirCallableSymbol) firBasedSymbol;
                    if (!firCallableSymbol.getCallableId().isLocal() && firCallableSymbol.getResolvedStatus().getEffectiveVisibility().getPublicApi()) {
                        z = true;
                    }
                } else {
                    Visibility visibility = ((FirCallableSymbol) firBasedSymbol).getResolvedStatus().getVisibility();
                    if ((visibility instanceof Visibilities.Public) || ((visibility instanceof Visibilities.Protected) && (modality = containingClassSymbol.getResolvedStatus().getModality()) != null && modality != Modality.FINAL && containingClassSymbol.getResolvedStatus().getVisibility().getIsPublicAPI())) {
                        z = true;
                    }
                }
            }
        } else if (!((FirClassLikeSymbol) firBasedSymbol).getClassId().isLocal()) {
            z = true;
        }
        if (!z2 && !z) {
            return null;
        }
        FirDeclaration fir = firBasedSymbol.getFir();
        FirMemberDeclaration firMemberDeclaration = fir instanceof FirMemberDeclaration ? (FirMemberDeclaration) fir : null;
        if (firMemberDeclaration == null || (nameOrSpecialName = FirDeclarationUtilKt.getNameOrSpecialName(firMemberDeclaration)) == null) {
            return null;
        }
        return nameOrSpecialName.getIdentifierOrNullIfSpecial();
    }
}
