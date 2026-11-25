package org.jetbrains.kotlin.fir.scopes;

import androidx.exifinterface.media.ExifInterface;
import com.reandroid.arsc.chunk.OverlayablePolicy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.Visibilities;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.FirSessionComponent;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.resolve.substitution.ConeSubstitutor;
import org.jetbrains.kotlin.fir.resolve.transformers.ReturnTypeCalculator;
import org.jetbrains.kotlin.fir.scopes.impl.AbstractFirOverrideScopeKt;
import org.jetbrains.kotlin.fir.scopes.impl.FirAbstractOverrideCheckerKt;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.fir.types.ConeFlexibleType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.TypeComponentsKt;
import org.jetbrains.kotlin.types.AbstractTypeChecker;
import org.jetbrains.kotlin.types.TypeCheckerState;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;

/* JADX INFO: compiled from: FirOverrideService.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001f\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u0018\n\u0002\b\u0002\u0018\u00002\u00020\u0001:\u0001 B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004JJ\u0010\u0007\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\n0\t0\b\"\f\b\u0000\u0010\n*\u0006\u0012\u0002\b\u00030\u000b2\f\u0010\f\u001a\b\u0012\u0004\u0012\u0002H\n0\t2\u0012\u0010\r\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\n0\t0\u000e2\u0006\u0010\u000f\u001a\u00020\u0010J<\u0010\u0011\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\n0\t0\u0012\"\f\b\u0000\u0010\n*\u0006\u0012\u0002\b\u00030\u000b2\u0012\u0010\u0013\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\n0\t0\u00122\u0006\u0010\u0014\u001a\u00020\u0015J#\u0010\u0016\u001a\u0004\u0018\u00010\u0017*\u0006\u0012\u0002\b\u00030\u00182\n\u0010\u0019\u001a\u0006\u0012\u0002\b\u00030\u0018H\u0002¢\u0006\u0002\u0010\u001aJ \u0010\u001b\u001a\u00020\u001c\"\u0004\b\u0000\u0010\u001d*\b\u0012\u0004\u0012\u0002H\u001d0\b2\u0006\u0010\u001e\u001a\u00020\u001fH\u0002R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006!"}, d2 = {"Lorg/jetbrains/kotlin/fir/scopes/FirOverrideService;", "Lorg/jetbrains/kotlin/fir/FirSessionComponent;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "(Lorg/jetbrains/kotlin/fir/FirSession;)V", "getSession", "()Lorg/jetbrains/kotlin/fir/FirSession;", "extractBothWaysOverridable", "", "Lorg/jetbrains/kotlin/fir/scopes/MemberWithBaseScope;", "D", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;", "overrider", "members", "", "overrideChecker", "Lorg/jetbrains/kotlin/fir/scopes/FirOverrideChecker;", "selectMostSpecificMembers", "", "overridables", "returnTypeCalculator", "Lorg/jetbrains/kotlin/fir/resolve/transformers/ReturnTypeCalculator;", "compareTo", "", "Lorg/jetbrains/kotlin/fir/scopes/FirOverrideService$MemberWithBaseScopeAndReturnType;", "other", "(Lorg/jetbrains/kotlin/fir/scopes/FirOverrideService$MemberWithBaseScopeAndReturnType;Lorg/jetbrains/kotlin/fir/scopes/FirOverrideService$MemberWithBaseScopeAndReturnType;)Ljava/lang/Integer;", "removeFlagged", "", ExifInterface.LONGITUDE_EAST, OverlayablePolicy.NAME_flags, "", "MemberWithBaseScopeAndReturnType", "providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirOverrideService implements FirSessionComponent {
    private final FirSession session;

    public FirOverrideService(FirSession session) {
        Intrinsics.checkNotNullParameter(session, "session");
        this.session = session;
    }

    public final FirSession getSession() {
        return this.session;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final <D extends FirCallableSymbol<?>> List<MemberWithBaseScope<D>> extractBothWaysOverridable(MemberWithBaseScope<? extends D> overrider, Collection<MemberWithBaseScope<D>> members, FirOverrideChecker overrideChecker) {
        Intrinsics.checkNotNullParameter(overrider, "overrider");
        Intrinsics.checkNotNullParameter(members, "members");
        Intrinsics.checkNotNullParameter(overrideChecker, "overrideChecker");
        ArrayList arrayList = new ArrayList();
        arrayList.add(overrider);
        Iterator<MemberWithBaseScope<D>> it = members.iterator();
        FirCallableDeclaration firCallableDeclaration = (FirCallableDeclaration) overrider.getMember().getFir();
        while (it.hasNext()) {
            MemberWithBaseScope<D> next = it.next();
            if (Intrinsics.areEqual(next, overrider)) {
                it.remove();
            } else if (AbstractFirOverrideScopeKt.similarFunctionsOrBothProperties(overrideChecker, firCallableDeclaration, (FirCallableDeclaration) next.getMember().getFir())) {
                arrayList.add(next);
                it.remove();
            }
        }
        return arrayList;
    }

    /* JADX WARN: Code duplicated, block: B:19:0x005a A[PHI: r7
      0x005a: PHI (r7v2 boolean) = (r7v1 boolean), (r7v4 boolean) binds: [B:13:0x004d, B:17:0x0056] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Multi-variable type inference failed */
    public final <D extends FirCallableSymbol<?>> List<MemberWithBaseScope<D>> selectMostSpecificMembers(List<? extends MemberWithBaseScope<? extends D>> overridables, ReturnTypeCalculator returnTypeCalculator) {
        boolean z;
        Intrinsics.checkNotNullParameter(overridables, "overridables");
        Intrinsics.checkNotNullParameter(returnTypeCalculator, "returnTypeCalculator");
        if (overridables.isEmpty()) {
            throw new IllegalArgumentException("Should have at least one overridable symbol".toString());
        }
        if (overridables.size() == 1) {
            return overridables;
        }
        ArrayList arrayList = new ArrayList(2);
        Iterator it = overridables.iterator();
        while (it.hasNext()) {
            MemberWithBaseScopeAndReturnType<?> memberWithBaseScopeAndReturnType = new MemberWithBaseScopeAndReturnType<>((MemberWithBaseScope) it.next(), returnTypeCalculator);
            int size = arrayList.size();
            boolean[] zArr = new boolean[size];
            boolean z2 = false;
            for (int i = 0; i < size; i++) {
                Integer numCompareTo = compareTo((MemberWithBaseScopeAndReturnType) arrayList.get(i), memberWithBaseScopeAndReturnType);
                if (numCompareTo == null) {
                    z = false;
                } else {
                    int iIntValue = numCompareTo.intValue();
                    if (iIntValue >= 0) {
                        z2 = true;
                    }
                    if (iIntValue < 0) {
                        z = true;
                    } else {
                        z = false;
                    }
                }
                zArr[i] = z;
            }
            removeFlagged(arrayList, zArr);
            if (!z2) {
                arrayList.add(memberWithBaseScopeAndReturnType);
            }
        }
        ArrayList arrayList2 = arrayList;
        ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList2, 10));
        Iterator it2 = arrayList2.iterator();
        while (it2.hasNext()) {
            arrayList3.add(((MemberWithBaseScopeAndReturnType) it2.next()).getMemberWithBaseScope());
        }
        return arrayList3;
    }

    private final <E> void removeFlagged(List<E> list, boolean[] zArr) {
        int length = zArr.length;
        int i = 0;
        for (int i2 = 0; i2 < length; i2++) {
            if (!zArr[i2]) {
                list.set(i, list.get(i2));
                i++;
            }
        }
        while (list.size() > i) {
            CollectionsKt.removeLast(list);
        }
    }

    /* JADX INFO: compiled from: FirOverrideService.kt */
    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0002\u0018\u0000*\u000e\b\u0000\u0010\u0001 \u0001*\u0006\u0012\u0002\b\u00030\u00022\u00020\u0003B\u001b\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bR\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0013\u0010\u000b\u001a\u0004\u0018\u00010\f¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/fir/scopes/FirOverrideService$MemberWithBaseScopeAndReturnType;", "D", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;", "", "memberWithBaseScope", "Lorg/jetbrains/kotlin/fir/scopes/MemberWithBaseScope;", "returnTypeCalculator", "Lorg/jetbrains/kotlin/fir/resolve/transformers/ReturnTypeCalculator;", "(Lorg/jetbrains/kotlin/fir/scopes/MemberWithBaseScope;Lorg/jetbrains/kotlin/fir/resolve/transformers/ReturnTypeCalculator;)V", "getMemberWithBaseScope", "()Lorg/jetbrains/kotlin/fir/scopes/MemberWithBaseScope;", "returnType", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "getReturnType", "()Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class MemberWithBaseScopeAndReturnType<D extends FirCallableSymbol<?>> {
        private final MemberWithBaseScope<D> memberWithBaseScope;
        private final ConeKotlinType returnType;

        /* JADX WARN: Multi-variable type inference failed */
        public MemberWithBaseScopeAndReturnType(MemberWithBaseScope<? extends D> memberWithBaseScope, ReturnTypeCalculator returnTypeCalculator) {
            Intrinsics.checkNotNullParameter(memberWithBaseScope, "memberWithBaseScope");
            Intrinsics.checkNotNullParameter(returnTypeCalculator, "returnTypeCalculator");
            this.memberWithBaseScope = memberWithBaseScope;
            FirResolvedTypeRef firResolvedTypeRefTryCalculateReturnTypeOrNull = returnTypeCalculator.tryCalculateReturnTypeOrNull((FirCallableDeclaration) memberWithBaseScope.getMember().getFir());
            this.returnType = firResolvedTypeRefTryCalculateReturnTypeOrNull != null ? firResolvedTypeRefTryCalculateReturnTypeOrNull.getType() : null;
        }

        public final MemberWithBaseScope<D> getMemberWithBaseScope() {
            return this.memberWithBaseScope;
        }

        public final ConeKotlinType getReturnType() {
            return this.returnType;
        }
    }

    private static final Integer compareTo$merge(boolean z, boolean z2, int i) {
        if (z == z2) {
            return Integer.valueOf(i);
        }
        if (!z || i < 0) {
            return (!z2 || i > 0) ? null : -1;
        }
        return 1;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final Integer compareTo(MemberWithBaseScopeAndReturnType<?> memberWithBaseScopeAndReturnType, MemberWithBaseScopeAndReturnType<?> memberWithBaseScopeAndReturnType2) {
        ConeKotlinType returnType;
        ConeKotlinType coneKotlinTypeSubstituteOrSelf;
        ConeKotlinType returnType2;
        FirCallableDeclaration firCallableDeclaration = (FirCallableDeclaration) memberWithBaseScopeAndReturnType.getMemberWithBaseScope().getMember().getFir();
        FirCallableDeclaration firCallableDeclaration2 = (FirCallableDeclaration) memberWithBaseScopeAndReturnType2.getMemberWithBaseScope().getMember().getFir();
        Integer numCompare = Visibilities.INSTANCE.compare(firCallableDeclaration.getStatus().getVisibility(), firCallableDeclaration2.getStatus().getVisibility());
        int iIntValue = numCompare != null ? numCompare.intValue() : 0;
        ConeSubstitutor coneSubstitutorBuildSubstitutorForOverridesCheck = FirAbstractOverrideCheckerKt.buildSubstitutorForOverridesCheck(firCallableDeclaration, firCallableDeclaration2, this.session);
        if (coneSubstitutorBuildSubstitutorForOverridesCheck == null || (returnType = memberWithBaseScopeAndReturnType.getReturnType()) == null || (coneKotlinTypeSubstituteOrSelf = coneSubstitutorBuildSubstitutorForOverridesCheck.substituteOrSelf(returnType)) == null || (returnType2 = memberWithBaseScopeAndReturnType2.getReturnType()) == null) {
            return null;
        }
        TypeCheckerState typeCheckerStateNewTypeCheckerState = TypeComponentsKt.getTypeContext(this.session).newTypeCheckerState(false, false);
        ConeKotlinType coneKotlinType = coneKotlinTypeSubstituteOrSelf;
        ConeKotlinType coneKotlinType2 = returnType2;
        boolean zIsSubtypeOf$default = AbstractTypeChecker.isSubtypeOf$default(AbstractTypeChecker.INSTANCE, typeCheckerStateNewTypeCheckerState, (KotlinTypeMarker) coneKotlinType, (KotlinTypeMarker) coneKotlinType2, false, 8, (Object) null);
        boolean zIsSubtypeOf$default2 = AbstractTypeChecker.isSubtypeOf$default(AbstractTypeChecker.INSTANCE, typeCheckerStateNewTypeCheckerState, (KotlinTypeMarker) coneKotlinType2, (KotlinTypeMarker) coneKotlinType, false, 8, (Object) null);
        int iIntValue2 = 1;
        if (zIsSubtypeOf$default && zIsSubtypeOf$default2) {
            Integer numCompareTo$merge = compareTo$merge(!(coneKotlinTypeSubstituteOrSelf instanceof ConeFlexibleType), !(returnType2 instanceof ConeFlexibleType), iIntValue);
            if (numCompareTo$merge == null) {
                return null;
            }
            iIntValue2 = numCompareTo$merge.intValue();
        } else if (!zIsSubtypeOf$default || iIntValue < 0) {
            if (zIsSubtypeOf$default2 && iIntValue <= 0) {
                iIntValue2 = -1;
            }
            return null;
        }
        if (firCallableDeclaration instanceof FirSimpleFunction) {
            if (!(firCallableDeclaration2 instanceof FirSimpleFunction)) {
                throw new IllegalArgumentException(("b is " + firCallableDeclaration2.getClass()).toString());
            }
            return Integer.valueOf(iIntValue2);
        }
        if (firCallableDeclaration instanceof FirProperty) {
            if (!(firCallableDeclaration2 instanceof FirProperty)) {
                throw new IllegalArgumentException(("b is " + firCallableDeclaration2.getClass()).toString());
            }
            FirProperty firProperty = (FirProperty) firCallableDeclaration;
            if (firProperty.getIsVar() && !zIsSubtypeOf$default) {
                return null;
            }
            FirProperty firProperty2 = (FirProperty) firCallableDeclaration2;
            if (!firProperty2.getIsVar() || zIsSubtypeOf$default2) {
                return compareTo$merge(firProperty.getIsVar(), firProperty2.getIsVar(), iIntValue2);
            }
            return null;
        }
        throw new IllegalArgumentException("Unexpected callable: " + firCallableDeclaration.getClass());
    }
}
