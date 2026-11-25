package org.jetbrains.kotlin.backend.jvm.ir;

import com.google.firebase.analytics.FirebaseAnalytics;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.ScopeWithIr;
import org.jetbrains.kotlin.backend.jvm.JvmBackendContext;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: JvmIrBuilder.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000&\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0012\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004\u001a\"\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0006\u001a\u001a\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\t\u001a&\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\n\u001a\u00020\u000b2\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\u0006\u001a\u001a\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\b\u001a\u00020\t¨\u0006\f"}, d2 = {"createJvmIrBuilder", "Lorg/jetbrains/kotlin/backend/jvm/ir/JvmIrBuilder;", "Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "scopeWithIr", "Lorg/jetbrains/kotlin/backend/common/ScopeWithIr;", "startOffset", "", "endOffset", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/ir/IrElement;", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "backend.jvm"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class JvmIrBuilderKt {
    public static /* synthetic */ JvmIrBuilder createJvmIrBuilder$default(JvmBackendContext jvmBackendContext, IrSymbol irSymbol, int i, int i2, int i3, Object obj) {
        if ((i3 & 2) != 0) {
            i = -1;
        }
        if ((i3 & 4) != 0) {
            i2 = -1;
        }
        return createJvmIrBuilder(jvmBackendContext, irSymbol, i, i2);
    }

    public static final JvmIrBuilder createJvmIrBuilder(JvmBackendContext jvmBackendContext, IrSymbol symbol, int i, int i2) {
        Intrinsics.checkNotNullParameter(jvmBackendContext, "<this>");
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        return new JvmIrBuilder(jvmBackendContext, symbol, i, i2);
    }

    public static final JvmIrBuilder createJvmIrBuilder(JvmBackendContext jvmBackendContext, IrSymbol symbol, IrElement source) {
        Intrinsics.checkNotNullParameter(jvmBackendContext, "<this>");
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        Intrinsics.checkNotNullParameter(source, "source");
        return new JvmIrBuilder(jvmBackendContext, symbol, source.getStartOffset(), source.getEndOffset());
    }

    public static final JvmIrBuilder createJvmIrBuilder(JvmBackendContext jvmBackendContext, ScopeWithIr scopeWithIr) {
        Intrinsics.checkNotNullParameter(jvmBackendContext, "<this>");
        Intrinsics.checkNotNullParameter(scopeWithIr, "scopeWithIr");
        return new JvmIrBuilder(jvmBackendContext, scopeWithIr.getScope().getScopeOwnerSymbol(), -1, -1);
    }

    public static final JvmIrBuilder createJvmIrBuilder(JvmBackendContext jvmBackendContext, ScopeWithIr scopeWithIr, int i, int i2) {
        Intrinsics.checkNotNullParameter(jvmBackendContext, "<this>");
        Intrinsics.checkNotNullParameter(scopeWithIr, "scopeWithIr");
        return new JvmIrBuilder(jvmBackendContext, scopeWithIr.getScope().getScopeOwnerSymbol(), i, i2);
    }

    public static final JvmIrBuilder createJvmIrBuilder(JvmBackendContext jvmBackendContext, ScopeWithIr scopeWithIr, IrElement source) {
        Intrinsics.checkNotNullParameter(jvmBackendContext, "<this>");
        Intrinsics.checkNotNullParameter(scopeWithIr, "scopeWithIr");
        Intrinsics.checkNotNullParameter(source, "source");
        return new JvmIrBuilder(jvmBackendContext, scopeWithIr.getScope().getScopeOwnerSymbol(), source.getStartOffset(), source.getEndOffset());
    }
}
