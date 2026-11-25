package org.jetbrains.kotlin.fir;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeTypeUtilsKt;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.util.AbstractArrayMapOwner;
import org.jetbrains.kotlin.util.NullableArrayMapAccessor;

/* JADX INFO: compiled from: FirEnumWhenTrackerComponent.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000 \n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u001e\u0010\u0007\u001a\u00020\b*\u00020\u00012\b\u0010\t\u001a\u0004\u0018\u00010\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\f\"!\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00020\u00028FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0005\u0010\u0006\u001a\u0004\b\u0003\u0010\u0004¨\u0006\r"}, d2 = {"enumWhenTracker", "Lorg/jetbrains/kotlin/fir/FirEnumWhenTrackerComponent;", "Lorg/jetbrains/kotlin/fir/FirSession;", "getEnumWhenTracker", "(Lorg/jetbrains/kotlin/fir/FirSession;)Lorg/jetbrains/kotlin/fir/FirEnumWhenTrackerComponent;", "enumWhenTracker$delegate", "Lorg/jetbrains/kotlin/util/NullableArrayMapAccessor;", "reportEnumUsageInWhen", "", "path", "", "subjectType", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "semantics"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FirEnumWhenTrackerComponentKt {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinPackage(FirEnumWhenTrackerComponentKt.class, "semantics"), "enumWhenTracker", "getEnumWhenTracker(Lorg/jetbrains/kotlin/fir/FirSession;)Lorg/jetbrains/kotlin/fir/FirEnumWhenTrackerComponent;"))};
    private static final NullableArrayMapAccessor enumWhenTracker$delegate = FirSession.INSTANCE.generateNullableAccessor(Reflection.getOrCreateKotlinClass(FirEnumWhenTrackerComponent.class));

    public static final FirEnumWhenTrackerComponent getEnumWhenTracker(FirSession firSession) {
        Intrinsics.checkNotNullParameter(firSession, "<this>");
        return (FirEnumWhenTrackerComponent) enumWhenTracker$delegate.getValue((AbstractArrayMapOwner) firSession, $$delegatedProperties[0]);
    }

    public static final void reportEnumUsageInWhen(FirEnumWhenTrackerComponent firEnumWhenTrackerComponent, String str, ConeKotlinType coneKotlinType) {
        ClassId classId;
        String strAsString;
        String strReplace$default;
        String strReplace$default2;
        Intrinsics.checkNotNullParameter(firEnumWhenTrackerComponent, "<this>");
        if (str == null || coneKotlinType == null || (classId = ConeTypeUtilsKt.getClassId(coneKotlinType)) == null || (strAsString = classId.asString()) == null || (strReplace$default = StringsKt.replace$default(strAsString, ".", "$", false, 4, (Object) null)) == null || (strReplace$default2 = StringsKt.replace$default(strReplace$default, "/", ".", false, 4, (Object) null)) == null) {
            return;
        }
        firEnumWhenTrackerComponent.report(str, strReplace$default2);
    }
}
