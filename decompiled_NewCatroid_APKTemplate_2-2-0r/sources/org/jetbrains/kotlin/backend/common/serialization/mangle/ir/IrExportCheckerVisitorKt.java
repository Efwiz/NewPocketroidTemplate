package org.jetbrains.kotlin.backend.common.serialization.mangle.ir;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.name.SpecialNames;

/* JADX INFO: compiled from: IrExportCheckerVisitor.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\"\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0000\u0010\u0003¨\u0006\u0004"}, d2 = {"isAnonymous", "", "Lorg/jetbrains/kotlin/name/Name;", "(Lorg/jetbrains/kotlin/name/Name;)Z", "ir.serialization.common"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class IrExportCheckerVisitorKt {
    public static final boolean isAnonymous(Name name2) {
        Intrinsics.checkNotNullParameter(name2, "<this>");
        return name2.isSpecial() && (Intrinsics.areEqual(name2, SpecialNames.ANONYMOUS) || Intrinsics.areEqual(name2, SpecialNames.NO_NAME_PROVIDED));
    }
}
