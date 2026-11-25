package org.jetbrains.kotlin.fir.scopes.impl;

import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.MutablePropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationDataRegistry;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.ConeFlexibleType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeSimpleKotlinType;
import org.jetbrains.kotlin.fir.types.ConeTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.name.ClassId;

/* JADX INFO: compiled from: FirDelegatedMemberScope.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00006\n\u0000\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u001a\u001a\u0010\u000f\u001a\u00020\u0004*\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0004\u001a\n\u0010\u0014\u001a\u00020\u0004*\u00020\u0015\"\u0014\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\"3\u0010\u0005\u001a\u0004\u0018\u00010\u0004*\u00020\u00062\b\u0010\u0003\u001a\u0004\u0018\u00010\u00048F@FX\u0086\u008e\u0002¢\u0006\u0012\n\u0004\b\u000b\u0010\f\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\n\"\u001b\u0010\u0005\u001a\u0004\u0018\u00010\u0004*\u0006\u0012\u0002\b\u00030\r8F¢\u0006\u0006\u001a\u0004\b\u0007\u0010\u000e¨\u0006\u0016"}, d2 = {"PUBLIC_METHOD_NAMES_IN_ANY", "", "", "<set-?>", "", "multipleDelegatesWithTheSameSignature", "Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;", "getMultipleDelegatesWithTheSameSignature", "(Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;)Ljava/lang/Boolean;", "setMultipleDelegatesWithTheSameSignature", "(Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;Ljava/lang/Boolean;)V", "multipleDelegatesWithTheSameSignature$delegate", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationDataRegistry$DeclarationDataAccessor;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;", "(Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;)Ljava/lang/Boolean;", "hasTypeOf", "Lorg/jetbrains/kotlin/fir/declarations/FirValueParameter;", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "allowNullable", "isPublicInAny", "Lorg/jetbrains/kotlin/fir/declarations/FirSimpleFunction;", "providers"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FirDelegatedMemberScopeKt {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinPackage(FirDelegatedMemberScopeKt.class, "providers"), "multipleDelegatesWithTheSameSignature", "getMultipleDelegatesWithTheSameSignature(Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;)Ljava/lang/Boolean;"))};
    private static final FirDeclarationDataRegistry.DeclarationDataAccessor multipleDelegatesWithTheSameSignature$delegate = FirDeclarationDataRegistry.INSTANCE.data(MultipleDelegatesWithTheSameSignatureKey.INSTANCE);
    private static final Set<String> PUBLIC_METHOD_NAMES_IN_ANY = SetsKt.setOf((Object[]) new String[]{Namer.EQUALS_METHOD_NAME, "hashCode", "toString"});

    public static final Boolean getMultipleDelegatesWithTheSameSignature(FirCallableDeclaration firCallableDeclaration) {
        Intrinsics.checkNotNullParameter(firCallableDeclaration, "<this>");
        return (Boolean) multipleDelegatesWithTheSameSignature$delegate.getValue(firCallableDeclaration, $$delegatedProperties[0]);
    }

    public static final void setMultipleDelegatesWithTheSameSignature(FirCallableDeclaration firCallableDeclaration, Boolean bool) {
        Intrinsics.checkNotNullParameter(firCallableDeclaration, "<this>");
        multipleDelegatesWithTheSameSignature$delegate.setValue(firCallableDeclaration, $$delegatedProperties[0], bool);
    }

    public static final Boolean getMultipleDelegatesWithTheSameSignature(FirCallableSymbol<?> firCallableSymbol) {
        Intrinsics.checkNotNullParameter(firCallableSymbol, "<this>");
        return getMultipleDelegatesWithTheSameSignature((FirCallableDeclaration) firCallableSymbol.getFir());
    }

    /* JADX WARN: Code restructure failed: missing block: B:12:0x0038, code lost:
    
        if (r0.equals("hashCode") != false) goto L23;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x0064, code lost:
    
        if (r0.equals("toString") != false) goto L23;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x006e, code lost:
    
        return r5.getValueParameters().isEmpty();
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final boolean isPublicInAny(final org.jetbrains.kotlin.fir.declarations.FirSimpleFunction r5) {
        /*
            java.lang.String r0 = "<this>"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r5, r0)
            java.util.Set<java.lang.String> r0 = org.jetbrains.kotlin.fir.scopes.impl.FirDelegatedMemberScopeKt.PUBLIC_METHOD_NAMES_IN_ANY
            org.jetbrains.kotlin.name.Name r1 = r5.getName()
            java.lang.String r1 = r1.asString()
            boolean r0 = r0.contains(r1)
            r1 = 0
            if (r0 != 0) goto L17
            return r1
        L17:
            org.jetbrains.kotlin.name.Name r0 = r5.getName()
            java.lang.String r0 = r0.asString()
            int r2 = r0.hashCode()
            r3 = -1776922004(0xffffffff9616526c, float:-1.2142911E-25)
            if (r2 == r3) goto L5e
            r3 = -1295482945(0xffffffffb2c87fbf, float:-2.3341157E-8)
            if (r2 == r3) goto L3b
            r1 = 147696667(0x8cdac1b, float:1.23784505E-33)
            if (r2 != r1) goto L6f
            java.lang.String r1 = "hashCode"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L6f
            goto L66
        L3b:
            java.lang.String r2 = "equals"
            boolean r0 = r0.equals(r2)
            if (r0 == 0) goto L6f
            java.util.List r5 = r5.getValueParameters()
            java.lang.Object r5 = kotlin.collections.CollectionsKt.singleOrNull(r5)
            org.jetbrains.kotlin.fir.declarations.FirValueParameter r5 = (org.jetbrains.kotlin.fir.declarations.FirValueParameter) r5
            if (r5 == 0) goto L6e
            org.jetbrains.kotlin.name.StandardClassIds r0 = org.jetbrains.kotlin.name.StandardClassIds.INSTANCE
            org.jetbrains.kotlin.name.ClassId r0 = r0.getAny()
            r2 = 1
            boolean r5 = hasTypeOf(r5, r0, r2)
            if (r5 != r2) goto L6e
            r1 = r2
            goto L6e
        L5e:
            java.lang.String r1 = "toString"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L6f
        L66:
            java.util.List r5 = r5.getValueParameters()
            boolean r1 = r5.isEmpty()
        L6e:
            return r1
        L6f:
            org.jetbrains.kotlin.utils.exceptions.KotlinIllegalArgumentExceptionWithAttachments r0 = new org.jetbrains.kotlin.utils.exceptions.KotlinIllegalArgumentExceptionWithAttachments
            java.lang.String r1 = "Unexpected method name"
            r2 = 0
            r0.<init>(r1, r2)
            r1 = r0
            org.jetbrains.kotlin.utils.exceptions.KotlinExceptionWithAttachments r1 = (org.jetbrains.kotlin.utils.exceptions.KotlinExceptionWithAttachments) r1
            org.jetbrains.kotlin.utils.exceptions.ExceptionAttachmentBuilder r2 = new org.jetbrains.kotlin.utils.exceptions.ExceptionAttachmentBuilder
            r2.<init>()
            org.jetbrains.kotlin.name.Name r3 = r5.getName()
            org.jetbrains.kotlin.fir.scopes.impl.FirDelegatedMemberScopeKt$isPublicInAny$1$1 r4 = new org.jetbrains.kotlin.fir.scopes.impl.FirDelegatedMemberScopeKt$isPublicInAny$1$1
            r4.<init>()
            kotlin.jvm.functions.Function1 r4 = (kotlin.jvm.functions.Function1) r4
            java.lang.String r5 = "methodName"
            r2.withEntry(r5, r3, r4)
            java.lang.String r5 = r2.buildString()
            java.lang.String r2 = "info.txt"
            r1.withAttachment(r2, r5)
            java.lang.Throwable r0 = (java.lang.Throwable) r0
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.jetbrains.kotlin.fir.scopes.impl.FirDelegatedMemberScopeKt.isPublicInAny(org.jetbrains.kotlin.fir.declarations.FirSimpleFunction):boolean");
    }

    public static final boolean hasTypeOf(FirValueParameter firValueParameter, ClassId classId, boolean z) {
        ConeClassLikeType coneClassLikeType;
        Intrinsics.checkNotNullParameter(firValueParameter, "<this>");
        Intrinsics.checkNotNullParameter(classId, "classId");
        ConeKotlinType coneType = FirTypeUtilsKt.getConeType(firValueParameter.getReturnTypeRef());
        if (coneType instanceof ConeClassLikeType) {
            coneClassLikeType = (ConeClassLikeType) coneType;
        } else {
            if (!(coneType instanceof ConeFlexibleType)) {
                return false;
            }
            ConeSimpleKotlinType upperBound = ((ConeFlexibleType) coneType).getUpperBound();
            coneClassLikeType = upperBound instanceof ConeClassLikeType ? (ConeClassLikeType) upperBound : null;
            if (coneClassLikeType == null) {
                return false;
            }
        }
        if (!ConeTypeUtilsKt.isMarkedNullable(coneClassLikeType) || z) {
            return Intrinsics.areEqual(coneClassLikeType.getLookupTag().getClassId(), classId);
        }
        return false;
    }
}
