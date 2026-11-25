package org.jetbrains.kotlin.resolve.diagnostics;

import java.util.Collection;
import java.util.function.Supplier;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import org.jetbrains.kotlin.com.intellij.openapi.util.NotNullLazyValue;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.util.containers.MultiMap;
import org.jetbrains.kotlin.diagnostics.Diagnostic;
import org.jetbrains.kotlin.utils.PlatformUtilsKt;

/* JADX INFO: loaded from: classes6.dex */
public class DiagnosticsElementsCache {
    private final Diagnostics diagnostics;
    private final NotNullLazyValue<MultiMap<PsiElement, Diagnostic>> elementToDiagnostic = NotNullLazyValue.atomicLazy(new Supplier() { // from class: org.jetbrains.kotlin.resolve.diagnostics.DiagnosticsElementsCache$$ExternalSyntheticLambda0
        @Override // java.util.function.Supplier
        public final Object get() {
            return this.f$0.m7440x216a4678();
        }
    });
    private final Function1<Diagnostic, Boolean> filter;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 1 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 1 ? 3 : 2];
        if (i != 1) {
            objArr[0] = "psiElement";
        } else {
            objArr[0] = "org/jetbrains/kotlin/resolve/diagnostics/DiagnosticsElementsCache";
        }
        if (i != 1) {
            objArr[1] = "org/jetbrains/kotlin/resolve/diagnostics/DiagnosticsElementsCache";
        } else {
            objArr[1] = "getDiagnostics";
        }
        if (i != 1) {
            objArr[2] = "getDiagnostics";
        }
        String str2 = String.format(str, objArr);
        if (i == 1) {
            throw new IllegalStateException(str2);
        }
    }

    public DiagnosticsElementsCache(Diagnostics diagnostics, Function1<Diagnostic, Boolean> function1) {
        this.diagnostics = diagnostics;
        this.filter = function1;
    }

    /* JADX INFO: renamed from: lambda$new$0$org-jetbrains-kotlin-resolve-diagnostics-DiagnosticsElementsCache, reason: not valid java name */
    /* synthetic */ MultiMap m7440x216a4678() {
        return buildElementToDiagnosticCache(this.diagnostics, this.filter);
    }

    public Collection<Diagnostic> getDiagnostics(PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(0);
        }
        Collection<Diagnostic> collection = this.elementToDiagnostic.getValue().get(psiElement);
        if (collection == null) {
            $$$reportNull$$$0(1);
        }
        return collection;
    }

    private static MultiMap<PsiElement, Diagnostic> buildElementToDiagnosticCache(Diagnostics diagnostics, Function1<Diagnostic, Boolean> function1) {
        MultiMap<PsiElement, Diagnostic> multiMapCreateConcurrentMultiMap = PlatformUtilsKt.createConcurrentMultiMap();
        for (Diagnostic diagnostic : diagnostics) {
            if (diagnostic == null) {
                throw new IllegalStateException("There shouldn't be null diagnostics in the collection: " + CollectionsKt.toList(diagnostics));
            }
            if (function1.invoke(diagnostic).booleanValue()) {
                multiMapCreateConcurrentMultiMap.putValue(diagnostic.getPsiElement(), diagnostic);
            }
        }
        return multiMapCreateConcurrentMultiMap;
    }
}
