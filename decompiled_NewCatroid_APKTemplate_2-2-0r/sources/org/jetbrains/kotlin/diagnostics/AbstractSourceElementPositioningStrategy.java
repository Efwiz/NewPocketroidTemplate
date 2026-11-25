package org.jetbrains.kotlin.diagnostics;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.AbstractKtSourceElement;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;

/* JADX INFO: compiled from: AbstractSourceElementPositioningStrategy.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b&\u0018\u0000 \f2\u00020\u0001:\u0001\fB\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H&J\u0016\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010\n\u001a\u00020\u000bH&¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/diagnostics/AbstractSourceElementPositioningStrategy;", "", "()V", "isValid", "", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/AbstractKtSourceElement;", "markDiagnostic", "", "Lorg/jetbrains/kotlin/com/intellij/openapi/util/TextRange;", "diagnostic", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnostic;", "Companion", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class AbstractSourceElementPositioningStrategy {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static AbstractSourceElementPositioningStrategy DEFAULT = new OffsetsOnlyPositioningStrategy();

    @JvmStatic
    public static final void setDefault(AbstractSourceElementPositioningStrategy abstractSourceElementPositioningStrategy) {
        INSTANCE.setDefault(abstractSourceElementPositioningStrategy);
    }

    public abstract boolean isValid(AbstractKtSourceElement element);

    public abstract List<TextRange> markDiagnostic(KtDiagnostic diagnostic);

    /* JADX INFO: compiled from: AbstractSourceElementPositioningStrategy.kt */
    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u0004H\u0007R\u001e\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0004@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/diagnostics/AbstractSourceElementPositioningStrategy$Companion;", "", "()V", "<set-?>", "Lorg/jetbrains/kotlin/diagnostics/AbstractSourceElementPositioningStrategy;", "DEFAULT", "getDEFAULT", "()Lorg/jetbrains/kotlin/diagnostics/AbstractSourceElementPositioningStrategy;", "setDefault", "", "default", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @JvmStatic
        public final void setDefault(AbstractSourceElementPositioningStrategy abstractSourceElementPositioningStrategy) {
            Intrinsics.checkNotNullParameter(abstractSourceElementPositioningStrategy, "default");
            AbstractSourceElementPositioningStrategy.DEFAULT = abstractSourceElementPositioningStrategy;
        }

        public final AbstractSourceElementPositioningStrategy getDEFAULT() {
            return AbstractSourceElementPositioningStrategy.DEFAULT;
        }
    }
}
