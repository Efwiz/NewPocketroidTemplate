package org.jetbrains.kotlin.fir.java;

import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jdt.internal.compiler.util.SuffixConstants;
import org.jetbrains.kotlin.descriptors.Visibilities;
import org.jetbrains.kotlin.descriptors.Visibility;
import org.jetbrains.kotlin.descriptors.java.JavaVisibilities;
import org.jetbrains.kotlin.fir.ClassMembersKt;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.FirVisibilityChecker;
import org.jetbrains.kotlin.fir.FirVisibilityCheckerKt;
import org.jetbrains.kotlin.fir.UtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirFile;
import org.jetbrains.kotlin.fir.declarations.synthetic.FirSyntheticPropertyAccessor;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.resolve.SupertypeSupplier;
import org.jetbrains.kotlin.fir.resolve.SupertypeUtilsKt;
import org.jetbrains.kotlin.fir.resolve.calls.FirSimpleSyntheticPropertySymbol;
import org.jetbrains.kotlin.fir.symbols.ConeClassLikeLookupTag;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirPropertyAccessorSymbol;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: FirJavaVisibilityChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J(\u0010\u0003\u001a\u00020\u00042\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u00062\n\u0010\u0007\u001a\u0006\u0012\u0002\b\u00030\u00062\u0006\u0010\b\u001a\u00020\tH\u0014JT\u0010\n\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\t2\n\u0010\f\u001a\u0006\u0012\u0002\b\u00030\u00062\u0006\u0010\r\u001a\u00020\u000e2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00110\u00102\b\u0010\u0012\u001a\u0004\u0018\u00010\u00132\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00042\u0006\u0010\u0017\u001a\u00020\u0018H\u0014J\"\u0010\u0019\u001a\u00020\u0004*\u00020\u001a2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00110\u00102\u0006\u0010\u0014\u001a\u00020\u0015H\u0002J\u0018\u0010\u001b\u001a\u00020\u0004*\u0006\u0012\u0002\b\u00030\u00062\u0006\u0010\u001c\u001a\u00020\u001dH\u0002¨\u0006\u001e"}, d2 = {"Lorg/jetbrains/kotlin/fir/java/FirJavaVisibilityChecker;", "Lorg/jetbrains/kotlin/fir/FirVisibilityChecker;", "()V", "platformOverrideVisibilityCheck", "", "candidateInDerivedClass", "Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", "symbolInBaseClass", "visibilityInBaseClass", "Lorg/jetbrains/kotlin/descriptors/Visibility;", "platformVisibilityCheck", "declarationVisibility", NativeSymbol.TYPE_NAME, "useSiteFile", "Lorg/jetbrains/kotlin/fir/declarations/FirFile;", "containingDeclarations", "", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "dispatchReceiver", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "isCallToPropertySetter", "supertypeSupplier", "Lorg/jetbrains/kotlin/fir/resolve/SupertypeSupplier;", "isCalledFromSubclass", "Lorg/jetbrains/kotlin/fir/resolve/calls/FirSimpleSyntheticPropertySymbol;", "isInPackage", "expected", "Lorg/jetbrains/kotlin/name/FqName;", SuffixConstants.EXTENSION_java}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirJavaVisibilityChecker extends FirVisibilityChecker {
    public static final FirJavaVisibilityChecker INSTANCE = new FirJavaVisibilityChecker();

    private FirJavaVisibilityChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.FirVisibilityChecker
    protected boolean platformVisibilityCheck(Visibility declarationVisibility, FirBasedSymbol<?> symbol, FirFile useSiteFile, List<? extends FirDeclaration> containingDeclarations, FirExpression dispatchReceiver, FirSession session, boolean isCallToPropertySetter, SupertypeSupplier supertypeSupplier) {
        Intrinsics.checkNotNullParameter(declarationVisibility, "declarationVisibility");
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        Intrinsics.checkNotNullParameter(useSiteFile, "useSiteFile");
        Intrinsics.checkNotNullParameter(containingDeclarations, "containingDeclarations");
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(supertypeSupplier, "supertypeSupplier");
        if (Intrinsics.areEqual(declarationVisibility, JavaVisibilities.ProtectedAndPackage.INSTANCE) ? true : Intrinsics.areEqual(declarationVisibility, JavaVisibilities.ProtectedStaticVisibility.INSTANCE)) {
            if (Intrinsics.areEqual(packageFqName(symbol), UtilsKt.getPackageFqName(useSiteFile))) {
                return true;
            }
            ConeClassLikeLookupTag ownerLookupTag = FirVisibilityCheckerKt.getOwnerLookupTag(symbol);
            if (ownerLookupTag == null) {
                return false;
            }
            if (canSeeProtectedMemberOf(symbol, containingDeclarations, !((symbol instanceof FirCallableSymbol) && ((FirCallableSymbol) symbol).getRawStatus().isStatic()) ? dispatchReceiver : null, ownerLookupTag, session, FirVisibilityCheckerKt.isVariableOrNamedFunction(symbol), symbol.getFir() instanceof FirSyntheticPropertyAccessor, supertypeSupplier)) {
                return true;
            }
            if (!isCallToPropertySetter || !(symbol instanceof FirSimpleSyntheticPropertySymbol)) {
                return false;
            }
            FirSimpleSyntheticPropertySymbol firSimpleSyntheticPropertySymbol = (FirSimpleSyntheticPropertySymbol) symbol;
            FirPropertyAccessorSymbol setterSymbol = firSimpleSyntheticPropertySymbol.getSetterSymbol();
            return Intrinsics.areEqual(setterSymbol != null ? setterSymbol.getResolvedStatus().getVisibility() : null, Visibilities.Public.INSTANCE) && isCalledFromSubclass(firSimpleSyntheticPropertySymbol, containingDeclarations, session);
        }
        if (Intrinsics.areEqual(declarationVisibility, JavaVisibilities.PackageVisibility.INSTANCE)) {
            return isInPackage(symbol, UtilsKt.getPackageFqName(useSiteFile));
        }
        return true;
    }

    private final boolean isCalledFromSubclass(FirSimpleSyntheticPropertySymbol firSimpleSyntheticPropertySymbol, List<? extends FirDeclaration> list, FirSession firSession) {
        ConeClassLikeLookupTag coneClassLikeLookupTagContainingClassLookupTag = ClassMembersKt.containingClassLookupTag(firSimpleSyntheticPropertySymbol);
        if (coneClassLikeLookupTagContainingClassLookupTag == null) {
            return false;
        }
        List<? extends FirDeclaration> list2 = list;
        if ((list2 instanceof Collection) && list2.isEmpty()) {
            return false;
        }
        for (FirDeclaration firDeclaration : list2) {
            if ((firDeclaration instanceof FirClass) && SupertypeUtilsKt.isSubclassOf$default((FirClass) firDeclaration, coneClassLikeLookupTagContainingClassLookupTag, firSession, false, null, false, 24, null)) {
                return true;
            }
        }
        return false;
    }

    @Override // org.jetbrains.kotlin.fir.FirVisibilityChecker
    protected boolean platformOverrideVisibilityCheck(FirBasedSymbol<?> candidateInDerivedClass, FirBasedSymbol<?> symbolInBaseClass, Visibility visibilityInBaseClass) {
        Intrinsics.checkNotNullParameter(candidateInDerivedClass, "candidateInDerivedClass");
        Intrinsics.checkNotNullParameter(symbolInBaseClass, "symbolInBaseClass");
        Intrinsics.checkNotNullParameter(visibilityInBaseClass, "visibilityInBaseClass");
        if (!(Intrinsics.areEqual(visibilityInBaseClass, JavaVisibilities.ProtectedAndPackage.INSTANCE) ? true : Intrinsics.areEqual(visibilityInBaseClass, JavaVisibilities.ProtectedStaticVisibility.INSTANCE)) && Intrinsics.areEqual(visibilityInBaseClass, JavaVisibilities.PackageVisibility.INSTANCE)) {
            return isInPackage(symbolInBaseClass, packageFqName(candidateInDerivedClass));
        }
        return true;
    }

    private final boolean isInPackage(FirBasedSymbol<?> firBasedSymbol, FqName fqName) {
        ClassId classId;
        if (!Intrinsics.areEqual(packageFqName(firBasedSymbol), fqName)) {
            if (firBasedSymbol.getFir() instanceof FirSyntheticPropertyAccessor) {
                ConeClassLikeLookupTag ownerLookupTag = FirVisibilityCheckerKt.getOwnerLookupTag(firBasedSymbol);
                if (Intrinsics.areEqual((ownerLookupTag == null || (classId = ownerLookupTag.getClassId()) == null) ? null : classId.getPackageFqName(), fqName)) {
                }
            }
            return false;
        }
        return true;
    }
}
