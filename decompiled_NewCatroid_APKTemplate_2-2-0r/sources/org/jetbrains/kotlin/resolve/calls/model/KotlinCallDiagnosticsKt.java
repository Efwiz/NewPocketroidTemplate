package org.jetbrains.kotlin.resolve.calls.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.resolve.calls.inference.model.ConstraintSystemError;
import org.jetbrains.kotlin.resolve.calls.inference.model.NewConstraintWarning;

/* JADX INFO: compiled from: KotlinCallDiagnostics.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000 \n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\u001e\n\u0002\b\u0002\u001a\n\u0010\u0005\u001a\u00020\u0006*\u00020\u0001\u001a\u0016\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00060\b*\b\u0012\u0004\u0012\u00020\u00010\t\u001a\u0016\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00020\b*\b\u0012\u0004\u0012\u00020\u00020\b\"\u0017\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004¨\u0006\u000b"}, d2 = {"constraintSystemError", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/ConstraintSystemError;", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallDiagnostic;", "getConstraintSystemError", "(Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallDiagnostic;)Lorg/jetbrains/kotlin/resolve/calls/inference/model/ConstraintSystemError;", "asDiagnostic", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinConstraintSystemDiagnostic;", "asDiagnostics", "", "", "filterErrorDiagnostics", "resolution"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class KotlinCallDiagnosticsKt {
    public static final ConstraintSystemError getConstraintSystemError(KotlinCallDiagnostic kotlinCallDiagnostic) {
        Intrinsics.checkNotNullParameter(kotlinCallDiagnostic, "<this>");
        KotlinConstraintSystemDiagnostic kotlinConstraintSystemDiagnostic = kotlinCallDiagnostic instanceof KotlinConstraintSystemDiagnostic ? (KotlinConstraintSystemDiagnostic) kotlinCallDiagnostic : null;
        if (kotlinConstraintSystemDiagnostic != null) {
            return kotlinConstraintSystemDiagnostic.getError();
        }
        return null;
    }

    public static final KotlinConstraintSystemDiagnostic asDiagnostic(ConstraintSystemError constraintSystemError) {
        Intrinsics.checkNotNullParameter(constraintSystemError, "<this>");
        return new KotlinConstraintSystemDiagnostic(constraintSystemError);
    }

    public static final List<KotlinConstraintSystemDiagnostic> asDiagnostics(Collection<? extends ConstraintSystemError> collection) {
        Intrinsics.checkNotNullParameter(collection, "<this>");
        Collection<? extends ConstraintSystemError> collection2 = collection;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(collection2, 10));
        Iterator<T> it = collection2.iterator();
        while (it.hasNext()) {
            arrayList.add(asDiagnostic((ConstraintSystemError) it.next()));
        }
        return arrayList;
    }

    public static final List<KotlinCallDiagnostic> filterErrorDiagnostics(List<? extends KotlinCallDiagnostic> list) {
        Intrinsics.checkNotNullParameter(list, "<this>");
        ArrayList arrayList = new ArrayList();
        for (Object obj : list) {
            KotlinCallDiagnostic kotlinCallDiagnostic = (KotlinCallDiagnostic) obj;
            if (!(kotlinCallDiagnostic instanceof KotlinConstraintSystemDiagnostic) || !(((KotlinConstraintSystemDiagnostic) kotlinCallDiagnostic).getError() instanceof NewConstraintWarning)) {
                arrayList.add(obj);
            }
        }
        return arrayList;
    }
}
