package org.jetbrains.kotlin.diagnostics;

import java.util.Arrays;
import java.util.Collection;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.diagnostics.UnboundDiagnostic;
import org.jetbrains.kotlin.diagnostics.rendering.DiagnosticRenderer;

/* JADX INFO: compiled from: DiagnosticFactory.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0010\u0002\n\u0002\b\u0004\b&\u0018\u0000 \u001c*\b\b\u0000\u0010\u0001*\u00020\u00022\u00020\u0003:\u0001\u001cB\u000f\b\u0014\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006B\u0019\b\u0004\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\b\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\tJ\u0013\u0010\u0015\u001a\u00028\u00002\u0006\u0010\u0016\u001a\u00020\u0002¢\u0006\u0002\u0010\u0017J\u0014\u0010\u0018\u001a\u00020\u00192\f\u0010\n\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u000bJ\u000e\u0010\u001a\u001a\u00020\u00192\u0006\u0010\u0010\u001a\u00020\bJ\b\u0010\u001b\u001a\u00020\bH\u0016R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u000e¢\u0006\u0002\n\u0000R\"\u0010\n\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u000bX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR\u0014\u0010\u0010\u001a\u00020\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012R\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014¨\u0006\u001d"}, d2 = {"Lorg/jetbrains/kotlin/diagnostics/DiagnosticFactory;", "D", "Lorg/jetbrains/kotlin/diagnostics/UnboundDiagnostic;", "", "severity", "Lorg/jetbrains/kotlin/diagnostics/Severity;", "(Lorg/jetbrains/kotlin/diagnostics/Severity;)V", "_name", "", "(Ljava/lang/String;Lorg/jetbrains/kotlin/diagnostics/Severity;)V", "defaultRenderer", "Lorg/jetbrains/kotlin/diagnostics/rendering/DiagnosticRenderer;", "getDefaultRenderer", "()Lorg/jetbrains/kotlin/diagnostics/rendering/DiagnosticRenderer;", "setDefaultRenderer", "(Lorg/jetbrains/kotlin/diagnostics/rendering/DiagnosticRenderer;)V", "name", "getName", "()Ljava/lang/String;", "getSeverity", "()Lorg/jetbrains/kotlin/diagnostics/Severity;", "cast", "diagnostic", "(Lorg/jetbrains/kotlin/diagnostics/UnboundDiagnostic;)Lorg/jetbrains/kotlin/diagnostics/UnboundDiagnostic;", "initDefaultRenderer", "", "initializeName", "toString", "Companion", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class DiagnosticFactory<D extends UnboundDiagnostic> {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private String _name;
    private DiagnosticRenderer<? super D> defaultRenderer;
    private final Severity severity;

    protected DiagnosticFactory(String str, Severity severity) {
        Intrinsics.checkNotNullParameter(severity, "severity");
        this._name = str;
        this.severity = severity;
    }

    public Severity getSeverity() {
        return this.severity;
    }

    public String getName() {
        String str = this._name;
        Intrinsics.checkNotNull(str);
        return str;
    }

    public final void initializeName(String name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        this._name = name2;
    }

    public DiagnosticRenderer<D> getDefaultRenderer() {
        return this.defaultRenderer;
    }

    public void setDefaultRenderer(DiagnosticRenderer<? super D> diagnosticRenderer) {
        this.defaultRenderer = diagnosticRenderer;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    protected DiagnosticFactory(Severity severity) {
        this(null, severity);
        Intrinsics.checkNotNullParameter(severity, "severity");
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void initDefaultRenderer(DiagnosticRenderer<?> defaultRenderer) {
        setDefaultRenderer(defaultRenderer);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final D cast(UnboundDiagnostic diagnostic) {
        Intrinsics.checkNotNullParameter(diagnostic, "diagnostic");
        if (diagnostic.getFactory() == this) {
            return diagnostic;
        }
        throw new IllegalArgumentException(("Factory mismatch: expected " + this + " but was " + diagnostic.getFactory()).toString());
    }

    public String toString() {
        String str = this._name;
        return str == null ? "<Anonymous DiagnosticFactory>" : str;
    }

    /* JADX INFO: compiled from: DiagnosticFactory.kt */
    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002JC\u0010\u0003\u001a\u0002H\u0004\"\b\b\u0001\u0010\u0004*\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00052\"\u0010\u0007\u001a\u0012\u0012\u000e\b\u0001\u0012\n\u0012\u0006\b\u0001\u0012\u0002H\u00040\t0\b\"\n\u0012\u0006\b\u0001\u0012\u0002H\u00040\tH\u0007¢\u0006\u0002\u0010\nJ3\u0010\u0003\u001a\u0002H\u0004\"\b\b\u0001\u0010\u0004*\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00052\u0014\u0010\u0007\u001a\u0010\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u0002H\u00040\t0\u000b¢\u0006\u0002\u0010\f¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/diagnostics/DiagnosticFactory$Companion;", "", "()V", "cast", "D", "Lorg/jetbrains/kotlin/diagnostics/UnboundDiagnostic;", "diagnostic", "factories", "", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticFactory;", "(Lorg/jetbrains/kotlin/diagnostics/UnboundDiagnostic;[Lorg/jetbrains/kotlin/diagnostics/DiagnosticFactory;)Lorg/jetbrains/kotlin/diagnostics/UnboundDiagnostic;", "", "(Lorg/jetbrains/kotlin/diagnostics/UnboundDiagnostic;Ljava/util/Collection;)Lorg/jetbrains/kotlin/diagnostics/UnboundDiagnostic;", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @SafeVarargs
        public final <D extends UnboundDiagnostic> D cast(UnboundDiagnostic diagnostic, DiagnosticFactory<? extends D>... factories) {
            Intrinsics.checkNotNullParameter(diagnostic, "diagnostic");
            Intrinsics.checkNotNullParameter(factories, "factories");
            return (D) cast(diagnostic, CollectionsKt.listOf(Arrays.copyOf(factories, factories.length)));
        }

        public final <D extends UnboundDiagnostic> D cast(UnboundDiagnostic diagnostic, Collection<? extends DiagnosticFactory<? extends D>> factories) {
            Intrinsics.checkNotNullParameter(diagnostic, "diagnostic");
            Intrinsics.checkNotNullParameter(factories, "factories");
            for (DiagnosticFactory<? extends D> diagnosticFactory : factories) {
                if (diagnostic.getFactory() == diagnosticFactory) {
                    return (D) diagnosticFactory.cast(diagnostic);
                }
            }
            throw new IllegalArgumentException("Factory mismatch: expected one of " + factories + " but was " + diagnostic.getFactory());
        }
    }
}
