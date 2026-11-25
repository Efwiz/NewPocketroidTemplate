package org.jetbrains.kotlin.ir.backend.js.lower.serialization.ir;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.serialization.GlobalDeclarationTable;
import org.jetbrains.kotlin.backend.common.serialization.IdSignatureClashTracker;
import org.jetbrains.kotlin.ir.IrBuiltIns;

/* JADX INFO: compiled from: JsDeclarationTable.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/serialization/ir/JsGlobalDeclarationTable;", "Lorg/jetbrains/kotlin/backend/common/serialization/GlobalDeclarationTable;", "builtIns", "Lorg/jetbrains/kotlin/ir/IrBuiltIns;", "tracker", "Lorg/jetbrains/kotlin/backend/common/serialization/IdSignatureClashTracker;", "(Lorg/jetbrains/kotlin/ir/IrBuiltIns;Lorg/jetbrains/kotlin/backend/common/serialization/IdSignatureClashTracker;)V", "ir.serialization.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JsGlobalDeclarationTable extends GlobalDeclarationTable {
    public /* synthetic */ JsGlobalDeclarationTable(IrBuiltIns irBuiltIns, JsUniqIdClashTracker jsUniqIdClashTracker, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(irBuiltIns, (i & 2) != 0 ? new JsUniqIdClashTracker() : jsUniqIdClashTracker);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public JsGlobalDeclarationTable(IrBuiltIns builtIns, IdSignatureClashTracker tracker) {
        super(JsManglerIr.INSTANCE, tracker);
        Intrinsics.checkNotNullParameter(builtIns, "builtIns");
        Intrinsics.checkNotNullParameter(tracker, "tracker");
        loadKnownBuiltins(builtIns);
    }
}
