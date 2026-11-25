package org.jetbrains.kotlin.fir;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import org.jetbrains.kotlin.util.AbstractArrayMapOwner;
import org.jetbrains.kotlin.util.NullableArrayMapAccessor;

/* JADX INFO: compiled from: FirImportTrackerComponent.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\u001a\u001e\u0010\u0007\u001a\u00020\b*\u00020\u00012\b\u0010\t\u001a\u0004\u0018\u00010\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\n\"!\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00020\u00028FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0005\u0010\u0006\u001a\u0004\b\u0003\u0010\u0004¨\u0006\f"}, d2 = {"importTracker", "Lorg/jetbrains/kotlin/fir/FirImportTrackerComponent;", "Lorg/jetbrains/kotlin/fir/FirSession;", "getImportTracker", "(Lorg/jetbrains/kotlin/fir/FirSession;)Lorg/jetbrains/kotlin/fir/FirImportTrackerComponent;", "importTracker$delegate", "Lorg/jetbrains/kotlin/util/NullableArrayMapAccessor;", "reportImportDirectives", "", "filePath", "", "importedFqName", "semantics"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FirImportTrackerComponentKt {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinPackage(FirImportTrackerComponentKt.class, "semantics"), "importTracker", "getImportTracker(Lorg/jetbrains/kotlin/fir/FirSession;)Lorg/jetbrains/kotlin/fir/FirImportTrackerComponent;"))};
    private static final NullableArrayMapAccessor importTracker$delegate = FirSession.INSTANCE.generateNullableAccessor(Reflection.getOrCreateKotlinClass(FirImportTrackerComponent.class));

    public static final FirImportTrackerComponent getImportTracker(FirSession firSession) {
        Intrinsics.checkNotNullParameter(firSession, "<this>");
        return (FirImportTrackerComponent) importTracker$delegate.getValue((AbstractArrayMapOwner) firSession, $$delegatedProperties[0]);
    }

    public static final void reportImportDirectives(FirImportTrackerComponent firImportTrackerComponent, String str, String str2) {
        Intrinsics.checkNotNullParameter(firImportTrackerComponent, "<this>");
        if (str == null || str2 == null) {
            return;
        }
        firImportTrackerComponent.report(str, str2);
    }
}
