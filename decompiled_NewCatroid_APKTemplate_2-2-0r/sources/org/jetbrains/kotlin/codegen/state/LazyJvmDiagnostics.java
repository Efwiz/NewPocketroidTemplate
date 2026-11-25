package org.jetbrains.kotlin.codegen.state;

import com.reandroid.dex.sections.Marker;
import java.util.Collection;
import java.util.Iterator;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.implementation.MethodDelegation;
import org.jetbrains.kotlin.cli.common.arguments.DevModeOverwritingStrategies;
import org.jetbrains.kotlin.com.intellij.openapi.util.ModificationTracker;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.diagnostics.Diagnostic;
import org.jetbrains.kotlin.resolve.diagnostics.Diagnostics;

/* JADX INFO: compiled from: GenerationState.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010(\n\u0002\b\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00010\u0003¢\u0006\u0002\u0010\u0004J\u000e\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000fH\u0016J\u0016\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f2\u0006\u0010\u0012\u001a\u00020\u0013H\u0016J\b\u0010\u0014\u001a\u00020\u0015H\u0016J\u000f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00100\u0017H\u0096\u0002J\b\u0010\u0018\u001a\u00020\u0001H\u0016R\u001b\u0010\u0005\u001a\u00020\u00018BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\u0006\u0010\u0007R\u0014\u0010\n\u001a\u00020\u000b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\r¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/codegen/state/LazyJvmDiagnostics;", "Lorg/jetbrains/kotlin/resolve/diagnostics/Diagnostics;", "compute", "Lkotlin/Function0;", "(Lkotlin/jvm/functions/Function0;)V", MethodDelegation.ImplementationDelegate.FIELD_NAME_PREFIX, "getDelegate", "()Lorg/jetbrains/kotlin/resolve/diagnostics/Diagnostics;", "delegate$delegate", "Lkotlin/Lazy;", "modificationTracker", "Lorg/jetbrains/kotlin/com/intellij/openapi/util/ModificationTracker;", "getModificationTracker", "()Lcom/intellij/openapi/util/ModificationTracker;", DevModeOverwritingStrategies.ALL, "", "Lorg/jetbrains/kotlin/diagnostics/Diagnostic;", "forElement", "psiElement", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "isEmpty", "", "iterator", "", "noSuppression", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
final class LazyJvmDiagnostics implements Diagnostics {

    /* JADX INFO: renamed from: delegate$delegate, reason: from kotlin metadata */
    private final Lazy delegate;

    public LazyJvmDiagnostics(Function0<? extends Diagnostics> compute) {
        Intrinsics.checkNotNullParameter(compute, "compute");
        this.delegate = LazyKt.lazy(LazyThreadSafetyMode.SYNCHRONIZED, (Function0) compute);
    }

    private final Diagnostics getDelegate() {
        return (Diagnostics) this.delegate.getValue();
    }

    @Override // org.jetbrains.kotlin.resolve.diagnostics.Diagnostics
    public ModificationTracker getModificationTracker() {
        return getDelegate().getModificationTracker();
    }

    @Override // org.jetbrains.kotlin.resolve.diagnostics.Diagnostics, org.jetbrains.kotlin.diagnostics.GenericDiagnostics
    public Collection<Diagnostic> all() {
        return getDelegate().all();
    }

    @Override // org.jetbrains.kotlin.resolve.diagnostics.Diagnostics
    public Collection<Diagnostic> forElement(PsiElement psiElement) {
        Intrinsics.checkNotNullParameter(psiElement, "psiElement");
        return getDelegate().forElement(psiElement);
    }

    @Override // org.jetbrains.kotlin.resolve.diagnostics.Diagnostics, org.jetbrains.kotlin.diagnostics.GenericDiagnostics
    public boolean isEmpty() {
        return getDelegate().isEmpty();
    }

    @Override // org.jetbrains.kotlin.resolve.diagnostics.Diagnostics
    public Diagnostics noSuppression() {
        return getDelegate().noSuppression();
    }

    @Override // org.jetbrains.kotlin.resolve.diagnostics.Diagnostics, org.jetbrains.kotlin.diagnostics.GenericDiagnostics, java.lang.Iterable
    public Iterator<Diagnostic> iterator() {
        return getDelegate().iterator();
    }
}
