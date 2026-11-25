package org.jetbrains.kotlin.resolve.calls.model;

import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ResolvedCallAtoms.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001:\u0001\u0006J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0007À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/model/KotlinDiagnosticsHolder;", "", "addDiagnostic", "", "diagnostic", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallDiagnostic;", "SimpleHolder", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface KotlinDiagnosticsHolder {
    void addDiagnostic(KotlinCallDiagnostic diagnostic);

    /* JADX INFO: compiled from: ResolvedCallAtoms.kt */
    @Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0005H\u0016J\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00050\u000bR\u001e\u0010\u0003\u001a\u0012\u0012\u0004\u0012\u00020\u00050\u0004j\b\u0012\u0004\u0012\u00020\u0005`\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/model/KotlinDiagnosticsHolder$SimpleHolder;", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinDiagnosticsHolder;", "()V", "diagnostics", "Ljava/util/ArrayList;", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallDiagnostic;", "Lkotlin/collections/ArrayList;", "addDiagnostic", "", "diagnostic", "getDiagnostics", "", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class SimpleHolder implements KotlinDiagnosticsHolder {
        private final ArrayList<KotlinCallDiagnostic> diagnostics = new ArrayList<>();

        @Override // org.jetbrains.kotlin.resolve.calls.model.KotlinDiagnosticsHolder
        public void addDiagnostic(KotlinCallDiagnostic diagnostic) {
            Intrinsics.checkNotNullParameter(diagnostic, "diagnostic");
            this.diagnostics.add(diagnostic);
        }

        public final List<KotlinCallDiagnostic> getDiagnostics() {
            return this.diagnostics;
        }
    }
}
