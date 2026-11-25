package org.jetbrains.kotlin.fir;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.fir.resolve.calls.AbstractCallInfo;
import org.jetbrains.kotlin.fir.types.ConeErrorType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeTypeProjection;
import org.jetbrains.kotlin.fir.types.ConeTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.FirQualifierPart;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.FirUserTypeRef;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.util.AbstractArrayMapOwner;
import org.jetbrains.kotlin.util.NullableArrayMapAccessor;
import org.jetbrains.kotlin.utils.SmartList;

/* JADX INFO: compiled from: FirLookupTrackerComponent.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000:\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a \u0010\u0007\u001a\u00020\b*\u00020\u00012\u0006\u0010\t\u001a\u00020\n2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\f\u001a\u001a\u0010\u0007\u001a\u00020\b*\u00020\u00012\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000e\u001a\u00020\u000f\u001a*\u0010\u0010\u001a\u00020\b*\u00020\u00012\u0006\u0010\u0011\u001a\u00020\u00122\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\f2\b\u0010\u0013\u001a\u0004\u0018\u00010\u0014\u001a&\u0010\u0015\u001a\u00020\b*\u00020\u00012\u0006\u0010\u0011\u001a\u00020\u00122\b\u0010\u0016\u001a\u0004\u0018\u00010\u00142\b\u0010\u0013\u001a\u0004\u0018\u00010\u0014\"!\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00020\u00028FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0005\u0010\u0006\u001a\u0004\b\u0003\u0010\u0004¨\u0006\u0017"}, d2 = {"lookupTracker", "Lorg/jetbrains/kotlin/fir/FirLookupTrackerComponent;", "Lorg/jetbrains/kotlin/fir/FirSession;", "getLookupTracker", "(Lorg/jetbrains/kotlin/fir/FirSession;)Lorg/jetbrains/kotlin/fir/FirLookupTrackerComponent;", "lookupTracker$delegate", "Lorg/jetbrains/kotlin/util/NullableArrayMapAccessor;", "recordCallLookup", "", "callInfo", "Lorg/jetbrains/kotlin/fir/resolve/calls/AbstractCallInfo;", "inScopes", "", "", "inType", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "recordTypeLookup", "typeRef", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "fileSource", "Lorg/jetbrains/kotlin/KtSourceElement;", "recordTypeResolveAsLookup", FirebaseAnalytics.Param.SOURCE, "semantics"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FirLookupTrackerComponentKt {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinPackage(FirLookupTrackerComponentKt.class, "semantics"), "lookupTracker", "getLookupTracker(Lorg/jetbrains/kotlin/fir/FirSession;)Lorg/jetbrains/kotlin/fir/FirLookupTrackerComponent;"))};
    private static final NullableArrayMapAccessor lookupTracker$delegate = FirSession.INSTANCE.generateNullableAccessor(Reflection.getOrCreateKotlinClass(FirLookupTrackerComponent.class));

    public static final void recordCallLookup(FirLookupTrackerComponent firLookupTrackerComponent, AbstractCallInfo callInfo, ConeKotlinType inType) {
        Name shortClassName;
        Intrinsics.checkNotNullParameter(firLookupTrackerComponent, "<this>");
        Intrinsics.checkNotNullParameter(callInfo, "callInfo");
        Intrinsics.checkNotNullParameter(inType, "inType");
        ClassId classId = ConeTypeUtilsKt.getClassId(inType);
        if (classId == null || !classId.isLocal()) {
            SmartList smartList = new SmartList(StringsKt.replace$default(ConeTypeUtilsKt.renderForDebugging(inType), '/', '.', false, 4, (Object) null));
            ClassId classId2 = ConeTypeUtilsKt.getClassId(inType);
            if (Intrinsics.areEqual((classId2 == null || (shortClassName = classId2.getShortClassName()) == null) ? null : shortClassName.asString(), "Companion")) {
                ClassId classId3 = ConeTypeUtilsKt.getClassId(inType);
                Intrinsics.checkNotNull(classId3);
                ClassId outerClassId = classId3.getOuterClassId();
                Intrinsics.checkNotNull(outerClassId);
                String strAsString = outerClassId.asString();
                Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
                smartList.add(StringsKt.replace$default(strAsString, '/', '.', false, 4, (Object) null));
            }
            firLookupTrackerComponent.recordLookup(callInfo.getName(), smartList, callInfo.getCallSite().getSource(), callInfo.getContainingFile().getSource());
        }
    }

    public static final void recordCallLookup(FirLookupTrackerComponent firLookupTrackerComponent, AbstractCallInfo callInfo, List<String> inScopes) {
        Intrinsics.checkNotNullParameter(firLookupTrackerComponent, "<this>");
        Intrinsics.checkNotNullParameter(callInfo, "callInfo");
        Intrinsics.checkNotNullParameter(inScopes, "inScopes");
        firLookupTrackerComponent.recordLookup(callInfo.getName(), inScopes, callInfo.getCallSite().getSource(), callInfo.getContainingFile().getSource());
    }

    public static final void recordTypeLookup(FirLookupTrackerComponent firLookupTrackerComponent, FirTypeRef typeRef, List<String> inScopes, KtSourceElement ktSourceElement) {
        Intrinsics.checkNotNullParameter(firLookupTrackerComponent, "<this>");
        Intrinsics.checkNotNullParameter(typeRef, "typeRef");
        Intrinsics.checkNotNullParameter(inScopes, "inScopes");
        if (typeRef instanceof FirUserTypeRef) {
            firLookupTrackerComponent.recordLookup(((FirQualifierPart) CollectionsKt.first((List) ((FirUserTypeRef) typeRef).getQualifier())).getName(), inScopes, typeRef.getSource(), ktSourceElement);
        }
    }

    public static final void recordTypeResolveAsLookup(FirLookupTrackerComponent firLookupTrackerComponent, FirTypeRef typeRef, KtSourceElement ktSourceElement, KtSourceElement ktSourceElement2) {
        Intrinsics.checkNotNullParameter(firLookupTrackerComponent, "<this>");
        Intrinsics.checkNotNullParameter(typeRef, "typeRef");
        if (typeRef instanceof FirResolvedTypeRef) {
            if (ktSourceElement == null && ktSourceElement2 == null) {
                return;
            }
            recordTypeResolveAsLookup$recordIfValid(firLookupTrackerComponent, ktSourceElement, ktSourceElement2, ((FirResolvedTypeRef) typeRef).getType());
        }
    }

    private static final void recordTypeResolveAsLookup$recordIfValid(FirLookupTrackerComponent firLookupTrackerComponent, KtSourceElement ktSourceElement, KtSourceElement ktSourceElement2, ConeKotlinType coneKotlinType) {
        if (coneKotlinType instanceof ConeErrorType) {
            return;
        }
        ClassId classId = ConeTypeUtilsKt.getClassId(coneKotlinType);
        if (classId != null && !classId.isLocal()) {
            if (!Intrinsics.areEqual(classId.getShortClassName().asString(), "Companion")) {
                Name shortClassName = classId.getShortClassName();
                Intrinsics.checkNotNullExpressionValue(shortClassName, "getShortClassName(...)");
                String strAsString = classId.getPackageFqName().asString();
                Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
                firLookupTrackerComponent.recordLookup(shortClassName, strAsString, ktSourceElement, ktSourceElement2);
            } else {
                ClassId outerClassId = classId.getOuterClassId();
                Intrinsics.checkNotNull(outerClassId);
                Name shortClassName2 = outerClassId.getShortClassName();
                Intrinsics.checkNotNullExpressionValue(shortClassName2, "getShortClassName(...)");
                ClassId outerClassId2 = classId.getOuterClassId();
                Intrinsics.checkNotNull(outerClassId2);
                String strAsString2 = outerClassId2.getPackageFqName().asString();
                Intrinsics.checkNotNullExpressionValue(strAsString2, "asString(...)");
                firLookupTrackerComponent.recordLookup(shortClassName2, strAsString2, ktSourceElement, ktSourceElement2);
            }
        }
        for (ConeTypeProjection coneTypeProjection : coneKotlinType.getTypeArguments()) {
            if (coneTypeProjection instanceof ConeKotlinType) {
                recordTypeResolveAsLookup$recordIfValid(firLookupTrackerComponent, ktSourceElement, ktSourceElement2, (ConeKotlinType) coneTypeProjection);
            }
        }
    }

    public static final FirLookupTrackerComponent getLookupTracker(FirSession firSession) {
        Intrinsics.checkNotNullParameter(firSession, "<this>");
        return (FirLookupTrackerComponent) lookupTracker$delegate.getValue((AbstractArrayMapOwner) firSession, $$delegatedProperties[0]);
    }
}
