package com.android.tools.r8.tracereferences;

import com.android.tools.r8.DiagnosticsHandler;
import com.android.tools.r8.diagnostic.DefinitionContext;
import com.android.tools.r8.references.ClassReference;
import com.android.tools.r8.references.FieldReference;
import com.android.tools.r8.references.MethodReference;
import com.android.tools.r8.references.PackageReference;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public interface TraceReferencesConsumer {

    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public interface AccessFlags {
        boolean isPrivate();

        boolean isProtected();

        boolean isPublic();

        boolean isStatic();
    }

    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public interface ClassAccessFlags extends AccessFlags {
        boolean isEnum();

        boolean isInterface();
    }

    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public interface FieldAccessFlags extends AccessFlags {
    }

    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static class ForwardingConsumer implements TraceReferencesConsumer {
        private static final ForwardingConsumer b = new ForwardingConsumer(null);
        private final TraceReferencesConsumer a;

        public ForwardingConsumer(TraceReferencesConsumer traceReferencesConsumer) {
            this.a = traceReferencesConsumer;
        }

        @Override // com.android.tools.r8.tracereferences.TraceReferencesConsumer
        public void acceptField(TracedField tracedField, DiagnosticsHandler diagnosticsHandler) {
            TraceReferencesConsumer traceReferencesConsumer = this.a;
            if (traceReferencesConsumer != null) {
                traceReferencesConsumer.acceptField(tracedField, diagnosticsHandler);
            }
        }

        @Override // com.android.tools.r8.tracereferences.TraceReferencesConsumer
        public void acceptMethod(TracedMethod tracedMethod, DiagnosticsHandler diagnosticsHandler) {
            TraceReferencesConsumer traceReferencesConsumer = this.a;
            if (traceReferencesConsumer != null) {
                traceReferencesConsumer.acceptMethod(tracedMethod, diagnosticsHandler);
            }
        }

        @Override // com.android.tools.r8.tracereferences.TraceReferencesConsumer
        public void acceptPackage(PackageReference packageReference, DiagnosticsHandler diagnosticsHandler) {
            TraceReferencesConsumer traceReferencesConsumer = this.a;
            if (traceReferencesConsumer != null) {
                traceReferencesConsumer.acceptPackage(packageReference, diagnosticsHandler);
            }
        }

        @Override // com.android.tools.r8.tracereferences.TraceReferencesConsumer
        public void acceptType(TracedClass tracedClass, DiagnosticsHandler diagnosticsHandler) {
            TraceReferencesConsumer traceReferencesConsumer = this.a;
            if (traceReferencesConsumer != null) {
                traceReferencesConsumer.acceptType(tracedClass, diagnosticsHandler);
            }
        }

        @Override // com.android.tools.r8.tracereferences.TraceReferencesConsumer
        public void finished(DiagnosticsHandler diagnosticsHandler) {
            TraceReferencesConsumer traceReferencesConsumer = this.a;
            if (traceReferencesConsumer != null) {
                traceReferencesConsumer.finished(diagnosticsHandler);
            }
        }
    }

    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public interface MethodAccessFlags extends AccessFlags {
    }

    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public interface TracedClass extends TracedReference<ClassReference, ClassAccessFlags> {
    }

    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public interface TracedField extends TracedReference<FieldReference, FieldAccessFlags> {
    }

    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public interface TracedMethod extends TracedReference<MethodReference, MethodAccessFlags> {
    }

    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public interface TracedReference<T, F> {
        F getAccessFlags();

        T getReference();

        DefinitionContext getReferencedFromContext();

        boolean isMissingDefinition();
    }

    static TraceReferencesConsumer emptyConsumer() {
        return ForwardingConsumer.b;
    }

    void acceptField(TracedField tracedField, DiagnosticsHandler diagnosticsHandler);

    void acceptMethod(TracedMethod tracedMethod, DiagnosticsHandler diagnosticsHandler);

    default void acceptPackage(PackageReference packageReference, DiagnosticsHandler diagnosticsHandler) {
    }

    void acceptType(TracedClass tracedClass, DiagnosticsHandler diagnosticsHandler);

    default void finished(DiagnosticsHandler diagnosticsHandler) {
    }
}
