package org.jetbrains.kotlin.resolve.diagnostics;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import org.jetbrains.kotlin.cli.common.arguments.DevModeOverwritingStrategies;
import org.jetbrains.kotlin.com.intellij.openapi.util.Condition;
import org.jetbrains.kotlin.com.intellij.openapi.util.ModificationTracker;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.util.containers.FilteringIterator;
import org.jetbrains.kotlin.diagnostics.Diagnostic;
import org.jetbrains.kotlin.diagnostics.DiagnosticSink;

/* JADX INFO: loaded from: classes6.dex */
public class DiagnosticsWithSuppression implements Diagnostics {
    private final Collection<Diagnostic> diagnostics;
    private final DiagnosticsElementsCache elementsCache;
    private final KotlinSuppressCache suppressCache;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 2 || i == 4 || i == 7) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 2 || i == 4 || i == 7) ? 2 : 3];
        switch (i) {
            case 1:
                objArr[0] = "diagnostics";
                break;
            case 2:
            case 4:
            case 7:
                objArr[0] = "org/jetbrains/kotlin/resolve/diagnostics/DiagnosticsWithSuppression";
                break;
            case 3:
                objArr[0] = "psiElement";
                break;
            case 5:
            case 6:
                objArr[0] = "callback";
                break;
            default:
                objArr[0] = "suppressCache";
                break;
        }
        if (i == 2) {
            objArr[1] = DevModeOverwritingStrategies.ALL;
        } else if (i == 4) {
            objArr[1] = "forElement";
        } else if (i != 7) {
            objArr[1] = "org/jetbrains/kotlin/resolve/diagnostics/DiagnosticsWithSuppression";
        } else {
            objArr[1] = "getDiagnostics";
        }
        switch (i) {
            case 2:
            case 4:
            case 7:
                break;
            case 3:
                objArr[2] = "forElement";
                break;
            case 5:
                objArr[2] = "setCallback";
                break;
            case 6:
                objArr[2] = "setCallbackIfNotSet";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 4 && i != 7) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    @Override // org.jetbrains.kotlin.resolve.diagnostics.Diagnostics
    public void resetCallback() {
    }

    @Override // org.jetbrains.kotlin.resolve.diagnostics.Diagnostics
    public void setCallback(DiagnosticSink.DiagnosticsCallback diagnosticsCallback) {
        if (diagnosticsCallback == null) {
            $$$reportNull$$$0(5);
        }
    }

    @Override // org.jetbrains.kotlin.resolve.diagnostics.Diagnostics
    public boolean setCallbackIfNotSet(DiagnosticSink.DiagnosticsCallback diagnosticsCallback) {
        if (diagnosticsCallback != null) {
            return false;
        }
        $$$reportNull$$$0(6);
        return false;
    }

    public DiagnosticsWithSuppression(KotlinSuppressCache kotlinSuppressCache, Collection<Diagnostic> collection) {
        if (kotlinSuppressCache == null) {
            $$$reportNull$$$0(0);
        }
        if (collection == null) {
            $$$reportNull$$$0(1);
        }
        this.diagnostics = collection;
        this.suppressCache = kotlinSuppressCache;
        this.elementsCache = new DiagnosticsElementsCache(this, kotlinSuppressCache.getFilter());
    }

    @Override // org.jetbrains.kotlin.resolve.diagnostics.Diagnostics
    public Diagnostics noSuppression() {
        return new SimpleDiagnostics(this.diagnostics);
    }

    @Override // org.jetbrains.kotlin.resolve.diagnostics.Diagnostics, org.jetbrains.kotlin.diagnostics.GenericDiagnostics, java.lang.Iterable
    public Iterator<Diagnostic> iterator() {
        Iterator<Diagnostic> it = this.diagnostics.iterator();
        final Function1<Diagnostic, Boolean> filter = this.suppressCache.getFilter();
        filter.getClass();
        return new FilteringIterator(it, new Condition() { // from class: org.jetbrains.kotlin.resolve.diagnostics.DiagnosticsWithSuppression$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Condition
            public final boolean value(Object obj) {
                return ((Boolean) filter.invoke((Diagnostic) obj)).booleanValue();
            }
        });
    }

    @Override // org.jetbrains.kotlin.resolve.diagnostics.Diagnostics, org.jetbrains.kotlin.diagnostics.GenericDiagnostics
    public Collection<Diagnostic> all() {
        List listFilter = CollectionsKt.filter(this.diagnostics, this.suppressCache.getFilter());
        if (listFilter == null) {
            $$$reportNull$$$0(2);
        }
        return listFilter;
    }

    @Override // org.jetbrains.kotlin.resolve.diagnostics.Diagnostics
    public Collection<Diagnostic> forElement(PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(3);
        }
        Collection<Diagnostic> diagnostics = this.elementsCache.getDiagnostics(psiElement);
        if (diagnostics == null) {
            $$$reportNull$$$0(4);
        }
        return diagnostics;
    }

    @Override // org.jetbrains.kotlin.resolve.diagnostics.Diagnostics, org.jetbrains.kotlin.diagnostics.GenericDiagnostics
    public boolean isEmpty() {
        return all().isEmpty();
    }

    @Override // org.jetbrains.kotlin.resolve.diagnostics.Diagnostics
    public ModificationTracker getModificationTracker() {
        throw new IllegalStateException("Trying to obtain modification tracker for readonly DiagnosticsWithSuppression.");
    }

    public Collection<Diagnostic> getDiagnostics() {
        Collection<Diagnostic> collection = this.diagnostics;
        if (collection == null) {
            $$$reportNull$$$0(7);
        }
        return collection;
    }
}
