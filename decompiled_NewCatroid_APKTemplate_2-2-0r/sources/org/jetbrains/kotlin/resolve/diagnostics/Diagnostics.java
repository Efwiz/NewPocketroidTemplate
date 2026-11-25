package org.jetbrains.kotlin.resolve.diagnostics;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.arguments.DevModeOverwritingStrategies;
import org.jetbrains.kotlin.com.intellij.openapi.util.ModificationTracker;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.diagnostics.Diagnostic;
import org.jetbrains.kotlin.diagnostics.DiagnosticSink;
import org.jetbrains.kotlin.diagnostics.GenericDiagnostics;

/* JADX INFO: compiled from: Diagnostics.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u001e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010(\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bf\u0018\u0000 \u00172\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001\u0017J\u000e\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00020\bH&J\u0016\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00020\b2\u0006\u0010\n\u001a\u00020\u000bH&J\b\u0010\f\u001a\u00020\rH\u0016J\u000f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00020\u000fH\u0096\u0002J\b\u0010\u0010\u001a\u00020\u0000H&J\b\u0010\u0011\u001a\u00020\u0012H\u0016J\u0010\u0010\u0013\u001a\u00020\u00122\u0006\u0010\u0014\u001a\u00020\u0015H\u0016J\u0010\u0010\u0016\u001a\u00020\r2\u0006\u0010\u0014\u001a\u00020\u0015H\u0016R\u0014\u0010\u0003\u001a\u00020\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0018À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/resolve/diagnostics/Diagnostics;", "Lorg/jetbrains/kotlin/diagnostics/GenericDiagnostics;", "Lorg/jetbrains/kotlin/diagnostics/Diagnostic;", "modificationTracker", "Lorg/jetbrains/kotlin/com/intellij/openapi/util/ModificationTracker;", "getModificationTracker", "()Lcom/intellij/openapi/util/ModificationTracker;", DevModeOverwritingStrategies.ALL, "", "forElement", "psiElement", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "isEmpty", "", "iterator", "", "noSuppression", "resetCallback", "", "setCallback", "callback", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticSink$DiagnosticsCallback;", "setCallbackIfNotSet", "Companion", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface Diagnostics extends GenericDiagnostics<Diagnostic> {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = Companion.$$INSTANCE;

    @Override // org.jetbrains.kotlin.diagnostics.GenericDiagnostics
    Collection<Diagnostic> all();

    Collection<Diagnostic> forElement(PsiElement psiElement);

    Diagnostics noSuppression();

    default void resetCallback() {
    }

    default boolean setCallbackIfNotSet(DiagnosticSink.DiagnosticsCallback callback) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        return false;
    }

    default ModificationTracker getModificationTracker() {
        throw new IllegalStateException("Trying to obtain modification tracker for Diagnostics object of class " + getClass());
    }

    @Override // org.jetbrains.kotlin.diagnostics.GenericDiagnostics
    default boolean isEmpty() {
        return all().isEmpty();
    }

    @Override // org.jetbrains.kotlin.diagnostics.GenericDiagnostics, java.lang.Iterable
    default Iterator<Diagnostic> iterator() {
        return all().iterator();
    }

    default void setCallback(DiagnosticSink.DiagnosticsCallback callback) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        setCallbackIfNotSet(callback);
    }

    /* JADX INFO: compiled from: Diagnostics.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/resolve/diagnostics/Diagnostics$Companion;", "", "()V", "EMPTY", "Lorg/jetbrains/kotlin/resolve/diagnostics/Diagnostics;", "getEMPTY", "()Lorg/jetbrains/kotlin/resolve/diagnostics/Diagnostics;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        static final /* synthetic */ Companion $$INSTANCE = new Companion();
        private static final Diagnostics EMPTY = new Diagnostics() { // from class: org.jetbrains.kotlin.resolve.diagnostics.Diagnostics$Companion$EMPTY$1
            private final ModificationTracker modificationTracker;

            {
                ModificationTracker NEVER_CHANGED = ModificationTracker.NEVER_CHANGED;
                Intrinsics.checkNotNullExpressionValue(NEVER_CHANGED, "NEVER_CHANGED");
                this.modificationTracker = NEVER_CHANGED;
            }

            @Override // org.jetbrains.kotlin.resolve.diagnostics.Diagnostics
            public Diagnostics noSuppression() {
                return this;
            }

            @Override // org.jetbrains.kotlin.resolve.diagnostics.Diagnostics
            public ModificationTracker getModificationTracker() {
                return this.modificationTracker;
            }

            @Override // org.jetbrains.kotlin.resolve.diagnostics.Diagnostics, org.jetbrains.kotlin.diagnostics.GenericDiagnostics
            public List<Diagnostic> all() {
                return CollectionsKt.emptyList();
            }

            @Override // org.jetbrains.kotlin.resolve.diagnostics.Diagnostics
            public List<Diagnostic> forElement(PsiElement psiElement) {
                Intrinsics.checkNotNullParameter(psiElement, "psiElement");
                return CollectionsKt.emptyList();
            }
        };

        private Companion() {
        }

        public final Diagnostics getEMPTY() {
            return EMPTY;
        }
    }
}
