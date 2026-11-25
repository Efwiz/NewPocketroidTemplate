package org.jetbrains.kotlin.fir;

import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.descriptors.Visibilities;
import org.jetbrains.kotlin.descriptors.Visibility;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin;
import org.jetbrains.kotlin.fir.declarations.FirFile;
import org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.declarations.utils.FirDeclarationUtilKt;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirExpressionUtilKt;
import org.jetbrains.kotlin.fir.expressions.FirPropertyAccessExpression;
import org.jetbrains.kotlin.fir.references.FirReference;
import org.jetbrains.kotlin.fir.references.FirSuperReference;
import org.jetbrains.kotlin.fir.references.FirThisReference;
import org.jetbrains.kotlin.fir.resolve.LookupTagUtilsKt;
import org.jetbrains.kotlin.fir.resolve.SupertypeSupplier;
import org.jetbrains.kotlin.fir.resolve.SupertypeUtilsKt;
import org.jetbrains.kotlin.fir.resolve.TypeExpansionUtilsKt;
import org.jetbrains.kotlin.fir.symbols.ConeClassLikeLookupTag;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeStarProjection;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.TypeComponentsKt;
import org.jetbrains.kotlin.fir.types.TypeConstructionUtilsKt;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.types.AbstractTypeChecker;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: FirVisibilityChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0086\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b&\u0018\u00002\u00020\u0001:\u00017B\u0005¢\u0006\u0002\u0010\u0002JD\u0010\u0003\u001a\u00020\u00042\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010\n\u001a\u00020\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\r2\u0006\u0010\u000e\u001a\u00020\u00042\u0006\u0010\u000f\u001a\u00020\u0010H\u0002JB\u0010\u0011\u001a\u00020\u00042\u0006\u0010\u0012\u001a\u00020\u00132\b\u0010\f\u001a\u0004\u0018\u00010\r2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u000e\u001a\u00020\u00042\u0006\u0010\u0014\u001a\u00020\u00042\u0006\u0010\u0015\u001a\u00020\u0016H\u0002JT\u0010\u0011\u001a\u00020\u00042\n\u0010\u0017\u001a\u0006\u0012\u0002\b\u00030\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b2\b\u0010\f\u001a\u0004\u0018\u00010\r2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u000e\u001a\u00020\u00042\u0006\u0010\u0014\u001a\u00020\u00042\u0006\u0010\u0015\u001a\u00020\u0016H\u0004J2\u0010\u0018\u001a\u00020\u00042\b\u0010\f\u001a\u0004\u0018\u00010\r2\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0014\u001a\u00020\u00042\u0006\u0010\u000f\u001a\u00020\u0010H\u0002J,\u0010\u0019\u001a\u00020\u00042\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u001c\u001a\u00020\u001d2\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\t0\bJJ\u0010\u001f\u001a\u00020\u00042\u0006\u0010\u001a\u001a\u00020 2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u001c\u001a\u00020\u001d2\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\t0\b2\b\u0010\f\u001a\u0004\u0018\u00010\r2\b\b\u0002\u0010!\u001a\u00020\u00042\u0006\u0010\u0015\u001a\u00020\u0016H\u0002J`\u0010\"\u001a\u00020\u00042\u0006\u0010\u001a\u001a\u00020 2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u001c\u001a\u00020\u001d2\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\t0\b2\b\u0010\f\u001a\u0004\u0018\u00010\r2\b\b\u0002\u0010!\u001a\u00020\u00042\n\b\u0002\u0010#\u001a\u0004\u0018\u00010$2\b\b\u0002\u0010%\u001a\u00020\u00042\b\b\u0002\u0010\u0015\u001a\u00020\u0016J\"\u0010&\u001a\u00020\u00042\u0006\u0010'\u001a\u00020(2\n\u0010)\u001a\u0006\u0012\u0002\b\u00030\u00062\u0006\u0010*\u001a\u00020 J\u0016\u0010&\u001a\u00020\u00042\u0006\u0010+\u001a\u00020 2\u0006\u0010*\u001a\u00020 J(\u0010,\u001a\u00020\u00042\n\u0010+\u001a\u0006\u0012\u0002\b\u00030\u00062\n\u0010-\u001a\u0006\u0012\u0002\b\u00030\u00062\u0006\u0010.\u001a\u00020/H$JT\u00100\u001a\u00020\u00042\u0006\u00101\u001a\u00020/2\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u00062\u0006\u0010\u001c\u001a\u00020\u001d2\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\t0\b2\b\u0010\f\u001a\u0004\u0018\u00010\r2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010!\u001a\u00020\u00042\u0006\u0010\u0015\u001a\u00020\u0016H$J\f\u00102\u001a\u00020\u0004*\u000203H\u0002J\u0018\u00104\u001a\u0004\u0018\u00010\u000b*\u0004\u0018\u00010\r2\u0006\u0010\u000f\u001a\u00020\u0010H\u0002J\u0016\u00104\u001a\u0004\u0018\u00010\u000b*\u00020\u000b2\u0006\u0010\u000f\u001a\u00020\u0010H\u0002J\u0010\u00105\u001a\u000206*\u0006\u0012\u0002\b\u00030\u0006H\u0004¨\u00068"}, d2 = {"Lorg/jetbrains/kotlin/fir/FirVisibilityChecker;", "Lorg/jetbrains/kotlin/fir/FirSessionComponent;", "()V", "canSeePrivateMemberOf", "", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", "containingDeclarationOfUseSite", "", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "ownerLookupTag", "Lorg/jetbrains/kotlin/fir/symbols/ConeClassLikeLookupTag;", "dispatchReceiver", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "isVariableOrNamedFunction", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "canSeeProtectedMemberOf", "containingUseSiteClass", "Lorg/jetbrains/kotlin/fir/declarations/FirClass;", "isSyntheticProperty", "supertypeSupplier", "Lorg/jetbrains/kotlin/fir/resolve/SupertypeSupplier;", "usedSymbol", "doesReceiverFitForProtectedVisibility", "isClassLikeVisible", "declaration", "Lorg/jetbrains/kotlin/fir/declarations/FirClassLikeDeclaration;", "useSiteFile", "Lorg/jetbrains/kotlin/fir/declarations/FirFile;", "containingDeclarations", "isSpecificDeclarationVisible", "Lorg/jetbrains/kotlin/fir/declarations/FirMemberDeclaration;", "isCallToPropertySetter", "isVisible", "staticQualifierClassForCallable", "Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;", "skipCheckForContainingClassVisibility", "isVisibleForOverriding", "derivedClassModuleData", "Lorg/jetbrains/kotlin/fir/FirModuleData;", "symbolFromDerivedClass", "candidateInBaseClass", "candidateInDerivedClass", "platformOverrideVisibilityCheck", "symbolInBaseClass", "visibilityInBaseClass", "Lorg/jetbrains/kotlin/descriptors/Visibility;", "platformVisibilityCheck", "declarationVisibility", "isAllowedToBeAccessedFromOutside", "Lorg/jetbrains/kotlin/fir/declarations/FirSimpleFunction;", "ownerIfCompanion", "packageFqName", "Lorg/jetbrains/kotlin/name/FqName;", "Default", "providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FirVisibilityChecker implements FirSessionComponent {
    protected abstract boolean platformOverrideVisibilityCheck(FirBasedSymbol<?> candidateInDerivedClass, FirBasedSymbol<?> symbolInBaseClass, Visibility visibilityInBaseClass);

    protected abstract boolean platformVisibilityCheck(Visibility declarationVisibility, FirBasedSymbol<?> symbol, FirFile useSiteFile, List<? extends FirDeclaration> containingDeclarations, FirExpression dispatchReceiver, FirSession session, boolean isCallToPropertySetter, SupertypeSupplier supertypeSupplier);

    /* JADX INFO: compiled from: FirVisibilityChecker.kt */
    @Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J(\u0010\u0003\u001a\u00020\u00042\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u00062\n\u0010\u0007\u001a\u0006\u0012\u0002\b\u00030\u00062\u0006\u0010\b\u001a\u00020\tH\u0014JT\u0010\n\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\t2\n\u0010\f\u001a\u0006\u0012\u0002\b\u00030\u00062\u0006\u0010\r\u001a\u00020\u000e2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00110\u00102\b\u0010\u0012\u001a\u0004\u0018\u00010\u00132\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00042\u0006\u0010\u0017\u001a\u00020\u0018H\u0014¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/fir/FirVisibilityChecker$Default;", "Lorg/jetbrains/kotlin/fir/FirVisibilityChecker;", "()V", "platformOverrideVisibilityCheck", "", "candidateInDerivedClass", "Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", "symbolInBaseClass", "visibilityInBaseClass", "Lorg/jetbrains/kotlin/descriptors/Visibility;", "platformVisibilityCheck", "declarationVisibility", NativeSymbol.TYPE_NAME, "useSiteFile", "Lorg/jetbrains/kotlin/fir/declarations/FirFile;", "containingDeclarations", "", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "dispatchReceiver", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "isCallToPropertySetter", "supertypeSupplier", "Lorg/jetbrains/kotlin/fir/resolve/SupertypeSupplier;", "providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Default extends FirVisibilityChecker {
        public static final Default INSTANCE = new Default();

        @Override // org.jetbrains.kotlin.fir.FirVisibilityChecker
        protected boolean platformOverrideVisibilityCheck(FirBasedSymbol<?> candidateInDerivedClass, FirBasedSymbol<?> symbolInBaseClass, Visibility visibilityInBaseClass) {
            Intrinsics.checkNotNullParameter(candidateInDerivedClass, "candidateInDerivedClass");
            Intrinsics.checkNotNullParameter(symbolInBaseClass, "symbolInBaseClass");
            Intrinsics.checkNotNullParameter(visibilityInBaseClass, "visibilityInBaseClass");
            return true;
        }

        @Override // org.jetbrains.kotlin.fir.FirVisibilityChecker
        protected boolean platformVisibilityCheck(Visibility declarationVisibility, FirBasedSymbol<?> symbol, FirFile useSiteFile, List<? extends FirDeclaration> containingDeclarations, FirExpression dispatchReceiver, FirSession session, boolean isCallToPropertySetter, SupertypeSupplier supertypeSupplier) {
            Intrinsics.checkNotNullParameter(declarationVisibility, "declarationVisibility");
            Intrinsics.checkNotNullParameter(symbol, "symbol");
            Intrinsics.checkNotNullParameter(useSiteFile, "useSiteFile");
            Intrinsics.checkNotNullParameter(containingDeclarations, "containingDeclarations");
            Intrinsics.checkNotNullParameter(session, "session");
            Intrinsics.checkNotNullParameter(supertypeSupplier, "supertypeSupplier");
            return true;
        }

        private Default() {
        }
    }

    public final boolean isClassLikeVisible(FirClassLikeDeclaration declaration, FirSession session, FirFile useSiteFile, List<? extends FirDeclaration> containingDeclarations) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(useSiteFile, "useSiteFile");
        Intrinsics.checkNotNullParameter(containingDeclarations, "containingDeclarations");
        return isVisible(declaration, session, useSiteFile, containingDeclarations, null, false, null, false, SupertypeSupplier.Default.INSTANCE);
    }

    public static /* synthetic */ boolean isVisible$default(FirVisibilityChecker firVisibilityChecker, FirMemberDeclaration firMemberDeclaration, FirSession firSession, FirFile firFile, List list, FirExpression firExpression, boolean z, FirRegularClass firRegularClass, boolean z2, SupertypeSupplier supertypeSupplier, int i, Object obj) {
        if (obj == null) {
            return firVisibilityChecker.isVisible(firMemberDeclaration, firSession, firFile, list, firExpression, (i & 32) != 0 ? false : z, (i & 64) != 0 ? null : firRegularClass, (i & 128) != 0 ? false : z2, (i & 256) != 0 ? SupertypeSupplier.Default.INSTANCE : supertypeSupplier);
        }
        throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: isVisible");
    }

    public final boolean isVisible(FirMemberDeclaration declaration, FirSession session, FirFile useSiteFile, List<? extends FirDeclaration> containingDeclarations, FirExpression dispatchReceiver, boolean isCallToPropertySetter, FirRegularClass staticQualifierClassForCallable, boolean skipCheckForContainingClassVisibility, SupertypeSupplier supertypeSupplier) {
        FirMemberDeclaration firMemberDeclaration;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(useSiteFile, "useSiteFile");
        Intrinsics.checkNotNullParameter(containingDeclarations, "containingDeclarations");
        Intrinsics.checkNotNullParameter(supertypeSupplier, "supertypeSupplier");
        if (declaration instanceof FirCallableDeclaration) {
            FirCallableDeclaration firCallableDeclaration = (FirCallableDeclaration) declaration;
            while (ClassMembersKt.isSubstitutionOrIntersectionOverride(firCallableDeclaration)) {
                FirCallableDeclaration originalForSubstitutionOverrideAttr = (ClassMembersKt.isSubstitutionOverride(firCallableDeclaration) || (firCallableDeclaration.getOrigin() instanceof FirDeclarationOrigin.Synthetic)) ? ClassMembersKt.getOriginalForSubstitutionOverrideAttr(firCallableDeclaration) : null;
                if (originalForSubstitutionOverrideAttr == null) {
                    originalForSubstitutionOverrideAttr = ClassMembersKt.isIntersectionOverride(firCallableDeclaration) ? ClassMembersKt.getOriginalForIntersectionOverrideAttr(firCallableDeclaration) : null;
                }
                if (originalForSubstitutionOverrideAttr == null) {
                    break;
                }
                firCallableDeclaration = originalForSubstitutionOverrideAttr;
            }
            firMemberDeclaration = firCallableDeclaration;
        } else {
            firMemberDeclaration = declaration;
        }
        boolean z = false;
        if (!isSpecificDeclarationVisible(firMemberDeclaration, session, useSiteFile, containingDeclarations, dispatchReceiver, isCallToPropertySetter, supertypeSupplier)) {
            return false;
        }
        if (skipCheckForContainingClassVisibility) {
            return true;
        }
        if (staticQualifierClassForCallable != null) {
            return isSpecificDeclarationVisible(staticQualifierClassForCallable, session, useSiteFile, containingDeclarations, null, isCallToPropertySetter, supertypeSupplier);
        }
        Sequence<FirClassLikeDeclaration> sequenceParentDeclarationSequence = FirVisibilityCheckerKt.parentDeclarationSequence(declaration, session, dispatchReceiver, containingDeclarations, supertypeSupplier);
        if (sequenceParentDeclarationSequence == null) {
            return true;
        }
        Iterator<FirClassLikeDeclaration> it = sequenceParentDeclarationSequence.iterator();
        while (it.hasNext()) {
            if (!isSpecificDeclarationVisible(it.next(), session, useSiteFile, containingDeclarations, null, isCallToPropertySetter, supertypeSupplier)) {
                return z;
            }
        }
        z = true;
        return z;
    }

    public final boolean isVisibleForOverriding(FirMemberDeclaration candidateInDerivedClass, FirMemberDeclaration candidateInBaseClass) {
        Intrinsics.checkNotNullParameter(candidateInDerivedClass, "candidateInDerivedClass");
        Intrinsics.checkNotNullParameter(candidateInBaseClass, "candidateInBaseClass");
        return isVisibleForOverriding(candidateInDerivedClass.getModuleData(), candidateInDerivedClass.getSymbol(), candidateInBaseClass);
    }

    /* JADX WARN: Code restructure failed: missing block: B:12:0x0038, code lost:
    
        if ((r1 != null && r1.isInFriendModule(r13)) != false) goto L15;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private final boolean isSpecificDeclarationVisible(org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration r13, org.jetbrains.kotlin.fir.FirSession r14, org.jetbrains.kotlin.fir.declarations.FirFile r15, java.util.List<? extends org.jetbrains.kotlin.fir.declarations.FirDeclaration> r16, org.jetbrains.kotlin.fir.expressions.FirExpression r17, boolean r18, org.jetbrains.kotlin.fir.resolve.SupertypeSupplier r19) {
        /*
            Method dump skipped, instruction units count: 249
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.jetbrains.kotlin.fir.FirVisibilityChecker.isSpecificDeclarationVisible(org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.FirSession, org.jetbrains.kotlin.fir.declarations.FirFile, java.util.List, org.jetbrains.kotlin.fir.expressions.FirExpression, boolean, org.jetbrains.kotlin.fir.resolve.SupertypeSupplier):boolean");
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final boolean canSeePrivateMemberOf(FirBasedSymbol<?> symbol, List<? extends FirDeclaration> containingDeclarationOfUseSite, ConeClassLikeLookupTag ownerLookupTag, FirExpression dispatchReceiver, boolean isVariableOrNamedFunction, FirSession session) {
        FirCallableDeclaration propertyIfAccessor;
        FirCallableDeclaration propertyIfBackingField;
        ConeClassLikeLookupTag coneClassLikeLookupTagDispatchReceiverClassLookupTagOrNull;
        FirClassLikeSymbol<?> symbol2;
        ConeClassLikeLookupTag coneClassLikeLookupTagOwnerIfCompanion = ownerIfCompanion(ownerLookupTag, session);
        if (coneClassLikeLookupTagOwnerIfCompanion != null) {
            return canSeePrivateMemberOf(symbol, containingDeclarationOfUseSite, coneClassLikeLookupTagOwnerIfCompanion, dispatchReceiver, isVariableOrNamedFunction, session);
        }
        if (dispatchReceiver != null && (!(symbol instanceof FirCallableSymbol) || !((FirCallableSymbol) symbol).getRawStatus().isStatic())) {
            FirDeclaration fir = symbol.getFir();
            FirCallableDeclaration firCallableDeclaration = fir instanceof FirCallableDeclaration ? (FirCallableDeclaration) fir : null;
            if (firCallableDeclaration == null || (propertyIfAccessor = ClassMembersKt.getPropertyIfAccessor(firCallableDeclaration)) == null || (propertyIfBackingField = ClassMembersKt.getPropertyIfBackingField(propertyIfAccessor)) == null || (coneClassLikeLookupTagDispatchReceiverClassLookupTagOrNull = ClassMembersKt.dispatchReceiverClassLookupTagOrNull(propertyIfBackingField)) == null || (symbol2 = LookupTagUtilsKt.toSymbol(coneClassLikeLookupTagDispatchReceiverClassLookupTagOrNull, session)) == null) {
                return true;
            }
            ConeClassLikeLookupTag lookupTag = symbol2.getLookupTag();
            ConeKotlinType coneType = FirTypeUtilsKt.getConeType(dispatchReceiver.getTypeRef());
            int size = ((FirClassLikeDeclaration) symbol2.getFir()).getTypeParameters().size();
            ConeStarProjection[] coneStarProjectionArr = new ConeStarProjection[size];
            for (int i = 0; i < size; i++) {
                coneStarProjectionArr[i] = ConeStarProjection.INSTANCE;
            }
            if (!Intrinsics.areEqual(lookupTag, LookupTagUtilsKt.findClassRepresentation(coneType, TypeConstructionUtilsKt.constructClassType$default(lookupTag, coneStarProjectionArr, true, null, 4, null), session))) {
                return false;
            }
            if (Intrinsics.areEqual(((FirMemberDeclaration) fir).getStatus().getVisibility(), Visibilities.PrivateToThis.INSTANCE)) {
                FirReference reference = FirExpressionUtilKt.toReference(dispatchReceiver);
                if (!(reference instanceof FirThisReference) || !Intrinsics.areEqual(((FirThisReference) reference).getBoundSymbol(), symbol2)) {
                    return false;
                }
            }
        }
        for (FirDeclaration firDeclaration : containingDeclarationOfUseSite) {
            if ((firDeclaration instanceof FirClass) && Intrinsics.areEqual(((FirClass) firDeclaration).getSymbol().getLookupTag(), ownerLookupTag)) {
                return true;
            }
        }
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final ConeClassLikeLookupTag ownerIfCompanion(ConeClassLikeLookupTag coneClassLikeLookupTag, FirSession firSession) {
        ClassId outerClassId;
        FirRegularClass firRegularClass;
        if (coneClassLikeLookupTag.getClassId().isLocal() || (outerClassId = coneClassLikeLookupTag.getClassId().getOuterClassId()) == null) {
            return null;
        }
        FirClassLikeSymbol<?> symbol = LookupTagUtilsKt.toSymbol(coneClassLikeLookupTag, firSession);
        FirRegularClassSymbol firRegularClassSymbol = symbol instanceof FirRegularClassSymbol ? (FirRegularClassSymbol) symbol : null;
        if (firRegularClassSymbol == null || (firRegularClass = (FirRegularClass) firRegularClassSymbol.getFir()) == null || !firRegularClass.getStatus().isCompanion()) {
            return null;
        }
        return TypeConstructionUtilsKt.toLookupTag(outerClassId);
    }

    private final boolean canSeeProtectedMemberOf(FirClass containingUseSiteClass, FirExpression dispatchReceiver, ConeClassLikeLookupTag ownerLookupTag, FirSession session, boolean isVariableOrNamedFunction, boolean isSyntheticProperty, SupertypeSupplier supertypeSupplier) {
        ConeClassLikeLookupTag coneClassLikeLookupTagOwnerIfCompanion;
        if (dispatchReceiver != null && (coneClassLikeLookupTagOwnerIfCompanion = ownerIfCompanion(dispatchReceiver, session)) != null && SupertypeUtilsKt.isSubclassOf$default(containingUseSiteClass, coneClassLikeLookupTagOwnerIfCompanion, session, false, supertypeSupplier, false, 16, null)) {
            return true;
        }
        if (!SupertypeUtilsKt.isSubclassOf$default(containingUseSiteClass, ownerLookupTag, session, false, supertypeSupplier, false, 16, null)) {
            return false;
        }
        if (isVariableOrNamedFunction) {
            return doesReceiverFitForProtectedVisibility(dispatchReceiver, containingUseSiteClass, ownerLookupTag, isSyntheticProperty, session);
        }
        return true;
    }

    private final boolean doesReceiverFitForProtectedVisibility(FirExpression dispatchReceiver, FirClass containingUseSiteClass, ConeClassLikeLookupTag ownerLookupTag, boolean isSyntheticProperty, FirSession session) {
        if (dispatchReceiver == null) {
            return true;
        }
        ConeKotlinType coneType = FirTypeUtilsKt.getConeType(dispatchReceiver.getTypeRef());
        if (dispatchReceiver instanceof FirPropertyAccessExpression) {
            FirPropertyAccessExpression firPropertyAccessExpression = (FirPropertyAccessExpression) dispatchReceiver;
            if (firPropertyAccessExpression.getCalleeReference() instanceof FirSuperReference) {
                coneType = FirTypeUtilsKt.getConeType(firPropertyAccessExpression.getDispatchReceiver().getTypeRef());
            }
        }
        if (AbstractTypeChecker.isSubtypeOf$default(AbstractTypeChecker.INSTANCE, TypeComponentsKt.getTypeContext(session).newTypeCheckerState(false, false), (KotlinTypeMarker) TypeExpansionUtilsKt.fullyExpandedType(coneType, session), (KotlinTypeMarker) TypeConstructionUtilsKt.constructStarProjectedType$default(containingUseSiteClass.getSymbol(), 0, false, 3, null), false, 8, (Object) null)) {
            return true;
        }
        if (!isSyntheticProperty) {
            return false;
        }
        if (FirLanguageSettingsComponentKt.getLanguageVersionSettings(session).supportsFeature(LanguageFeature.ImproveReportingDiagnosticsOnProtectedMembersOfBaseClass)) {
            return Intrinsics.areEqual(FirDeclarationUtilKt.getClassId(containingUseSiteClass).getPackageFqName(), ownerLookupTag.getClassId().getPackageFqName());
        }
        return true;
    }

    private final ConeClassLikeLookupTag ownerIfCompanion(FirExpression firExpression, FirSession firSession) {
        ConeClassLikeLookupTag lookupTag;
        FirTypeRef typeRef;
        ConeKotlinType coneType = (firExpression == null || (typeRef = firExpression.getTypeRef()) == null) ? null : FirTypeUtilsKt.getConeType(typeRef);
        ConeClassLikeType coneClassLikeType = coneType instanceof ConeClassLikeType ? (ConeClassLikeType) coneType : null;
        if (coneClassLikeType == null || (lookupTag = coneClassLikeType.getLookupTag()) == null) {
            return null;
        }
        return ownerIfCompanion(lookupTag, firSession);
    }

    private final boolean isAllowedToBeAccessedFromOutside(FirSimpleFunction firSimpleFunction) {
        FirSimpleFunction firSimpleFunction2 = firSimpleFunction;
        if (!Intrinsics.areEqual(firSimpleFunction2.getOrigin(), FirDeclarationOrigin.Library.INSTANCE) && !Intrinsics.areEqual(firSimpleFunction2.getOrigin(), FirDeclarationOrigin.Java.Library.INSTANCE)) {
            return false;
        }
        String strAsString = firSimpleFunction.getSymbol().getCallableId().getPackageName().asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        String strAsString2 = firSimpleFunction.getName().asString();
        Intrinsics.checkNotNullExpressionValue(strAsString2, "asString(...)");
        if (Intrinsics.areEqual(strAsString, "kotlin.jvm.internal.unsafe")) {
            return Intrinsics.areEqual(strAsString2, "monitorEnter") || Intrinsics.areEqual(strAsString2, "monitorExit");
        }
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    protected final boolean canSeeProtectedMemberOf(FirBasedSymbol<?> usedSymbol, List<? extends FirDeclaration> containingDeclarationOfUseSite, FirExpression dispatchReceiver, ConeClassLikeLookupTag ownerLookupTag, FirSession session, boolean isVariableOrNamedFunction, boolean isSyntheticProperty, SupertypeSupplier supertypeSupplier) {
        Intrinsics.checkNotNullParameter(usedSymbol, "usedSymbol");
        Intrinsics.checkNotNullParameter(containingDeclarationOfUseSite, "containingDeclarationOfUseSite");
        Intrinsics.checkNotNullParameter(ownerLookupTag, "ownerLookupTag");
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(supertypeSupplier, "supertypeSupplier");
        if (canSeePrivateMemberOf(usedSymbol, containingDeclarationOfUseSite, ownerLookupTag, dispatchReceiver, isVariableOrNamedFunction, session)) {
            return true;
        }
        for (FirDeclaration firDeclaration : containingDeclarationOfUseSite) {
            if (firDeclaration instanceof FirClass) {
                if (canSeeProtectedMemberOf((FirClass) ((FirClass) firDeclaration).getSymbol().getFir(), dispatchReceiver, ownerLookupTag, session, isVariableOrNamedFunction, isSyntheticProperty, supertypeSupplier)) {
                    return true;
                }
            } else if ((firDeclaration instanceof FirFile) && isSyntheticProperty && FirLanguageSettingsComponentKt.getLanguageVersionSettings(session).supportsFeature(LanguageFeature.ImproveReportingDiagnosticsOnProtectedMembersOfBaseClass) && Intrinsics.areEqual(UtilsKt.getPackageFqName((FirFile) firDeclaration), ownerLookupTag.getClassId().getPackageFqName())) {
                return true;
            }
        }
        return false;
    }

    protected final FqName packageFqName(FirBasedSymbol<?> firBasedSymbol) {
        Intrinsics.checkNotNullParameter(firBasedSymbol, "<this>");
        if (firBasedSymbol instanceof FirClassLikeSymbol) {
            FqName packageFqName = ((FirClassLikeSymbol) firBasedSymbol).getClassId().getPackageFqName();
            Intrinsics.checkNotNullExpressionValue(packageFqName, "getPackageFqName(...)");
            return packageFqName;
        }
        if (firBasedSymbol instanceof FirCallableSymbol) {
            return ((FirCallableSymbol) firBasedSymbol).getCallableId().getPackageName();
        }
        throw new IllegalStateException(("No package fq name for " + firBasedSymbol).toString());
    }

    public final boolean isVisibleForOverriding(FirModuleData derivedClassModuleData, FirBasedSymbol<?> symbolFromDerivedClass, FirMemberDeclaration candidateInBaseClass) {
        Intrinsics.checkNotNullParameter(derivedClassModuleData, "derivedClassModuleData");
        Intrinsics.checkNotNullParameter(symbolFromDerivedClass, "symbolFromDerivedClass");
        Intrinsics.checkNotNullParameter(candidateInBaseClass, "candidateInBaseClass");
        Visibility visibility = candidateInBaseClass.getStatus().getVisibility();
        if (Intrinsics.areEqual(visibility, Visibilities.Internal.INSTANCE)) {
            if (!Intrinsics.areEqual(candidateInBaseClass.getModuleData(), derivedClassModuleData)) {
                FirModuleVisibilityChecker moduleVisibilityChecker = FirVisibilityCheckerKt.getModuleVisibilityChecker(derivedClassModuleData.getSession());
                if (!(moduleVisibilityChecker != null && moduleVisibilityChecker.isInFriendModule(candidateInBaseClass))) {
                    return false;
                }
            }
        } else {
            if (Intrinsics.areEqual(visibility, Visibilities.Private.INSTANCE) ? true : Intrinsics.areEqual(visibility, Visibilities.PrivateToThis.INSTANCE)) {
                return false;
            }
            if (!Intrinsics.areEqual(visibility, Visibilities.Protected.INSTANCE)) {
                return platformOverrideVisibilityCheck(symbolFromDerivedClass, candidateInBaseClass.getSymbol(), candidateInBaseClass.getStatus().getVisibility());
            }
        }
        return true;
    }
}
