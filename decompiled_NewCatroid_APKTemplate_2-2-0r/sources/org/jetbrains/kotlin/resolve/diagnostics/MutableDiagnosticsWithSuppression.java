package org.jetbrains.kotlin.resolve.diagnostics;

import com.koushikdutta.async.http.cache.ResponseCacheMiddleware;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.arguments.DevModeOverwritingStrategies;
import org.jetbrains.kotlin.com.intellij.openapi.util.CompositeModificationTracker;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.util.CachedValueProvider;
import org.jetbrains.kotlin.com.intellij.util.CachedValueImpl;
import org.jetbrains.kotlin.diagnostics.Diagnostic;
import org.jetbrains.kotlin.diagnostics.DiagnosticSink;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.org.jline.reader.LineReader;
import org.jetbrains.kotlin.psi.KtAnnotated;
import org.jetbrains.kotlin.psi.KtAnnotationEntry;
import org.jetbrains.kotlin.psi.KtStubbedPsiUtil;

/* JADX INFO: compiled from: MutableDiagnosticsWithSuppression.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000^\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u001e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0001¢\u0006\u0002\u0010\u0005J\u000e\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\f0\u0015H\u0016J\u0006\u0010\u0016\u001a\u00020\u0017J\u0016\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\f0\u00152\u0006\u0010\u0019\u001a\u00020\u001aH\u0016J\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\f0\u001cJ\u0006\u0010\u001d\u001a\u00020\bJ\b\u0010\u001e\u001a\u00020\u0001H\u0016J\u0012\u0010\u001f\u001a\u0004\u0018\u00010\u000f2\u0006\u0010 \u001a\u00020\fH\u0002J\b\u0010!\u001a\u00020\bH\u0002J\u000e\u0010\"\u001a\u00020\u00172\u0006\u0010 \u001a\u00020\fJ\b\u0010#\u001a\u00020\u0017H\u0016J\u0010\u0010$\u001a\u00020%2\u0006\u0010&\u001a\u00020\u000fH\u0016R\u001c\u0010\u0006\u001a\u0010\u0012\f\u0012\n \t*\u0004\u0018\u00010\b0\b0\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\n\u001a\u0012\u0012\u0004\u0012\u00020\f0\u000bj\b\u0012\u0004\u0012\u00020\f`\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u000e\u001a\u0004\u0018\u00010\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0010\u001a\u00020\u0011X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006'"}, d2 = {"Lorg/jetbrains/kotlin/resolve/diagnostics/MutableDiagnosticsWithSuppression;", "Lorg/jetbrains/kotlin/resolve/diagnostics/Diagnostics;", "suppressCache", "Lorg/jetbrains/kotlin/resolve/diagnostics/KotlinSuppressCache;", "delegateDiagnostics", "(Lorg/jetbrains/kotlin/resolve/diagnostics/KotlinSuppressCache;Lorg/jetbrains/kotlin/resolve/diagnostics/Diagnostics;)V", ResponseCacheMiddleware.CACHE, "Lorg/jetbrains/kotlin/com/intellij/util/CachedValueImpl;", "Lorg/jetbrains/kotlin/resolve/diagnostics/DiagnosticsWithSuppression;", JvmProtoBufUtil.PLATFORM_TYPE_ID, "diagnosticList", "Ljava/util/ArrayList;", "Lorg/jetbrains/kotlin/diagnostics/Diagnostic;", "Lkotlin/collections/ArrayList;", "diagnosticsCallback", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticSink$DiagnosticsCallback;", "modificationTracker", "Lorg/jetbrains/kotlin/com/intellij/openapi/util/CompositeModificationTracker;", "getModificationTracker", "()Lcom/intellij/openapi/util/CompositeModificationTracker;", DevModeOverwritingStrategies.ALL, "", LineReader.CLEAR, "", "forElement", "psiElement", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "getOwnDiagnostics", "", "getReadonlyView", "noSuppression", "onTheFlyDiagnosticsCallback", "diagnostic", "readonlyView", "report", "resetCallback", "setCallbackIfNotSet", "", "callback", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class MutableDiagnosticsWithSuppression implements Diagnostics {
    private final CachedValueImpl<DiagnosticsWithSuppression> cache;
    private final Diagnostics delegateDiagnostics;
    private final ArrayList<Diagnostic> diagnosticList;
    private volatile DiagnosticSink.DiagnosticsCallback diagnosticsCallback;
    private final CompositeModificationTracker modificationTracker;
    private final KotlinSuppressCache suppressCache;

    public MutableDiagnosticsWithSuppression(KotlinSuppressCache suppressCache, Diagnostics delegateDiagnostics) {
        Intrinsics.checkNotNullParameter(suppressCache, "suppressCache");
        Intrinsics.checkNotNullParameter(delegateDiagnostics, "delegateDiagnostics");
        this.suppressCache = suppressCache;
        this.delegateDiagnostics = delegateDiagnostics;
        this.diagnosticList = new ArrayList<>();
        this.cache = new CachedValueImpl<>(new CachedValueProvider() { // from class: org.jetbrains.kotlin.resolve.diagnostics.MutableDiagnosticsWithSuppression$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.com.intellij.psi.util.CachedValueProvider
            public final CachedValueProvider.Result compute() {
                return MutableDiagnosticsWithSuppression.cache$lambda$0(this.f$0);
            }
        });
        this.modificationTracker = new CompositeModificationTracker(delegateDiagnostics.getModificationTracker());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final CachedValueProvider.Result cache$lambda$0(MutableDiagnosticsWithSuppression this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        return new CachedValueProvider.Result(new DiagnosticsWithSuppression(this$0.suppressCache, CollectionsKt.plus((Collection) this$0.delegateDiagnostics.noSuppression().all(), (Iterable) this$0.diagnosticList)), this$0.getModificationTracker());
    }

    private final DiagnosticsWithSuppression readonlyView() {
        DiagnosticsWithSuppression value = this.cache.getValue();
        Intrinsics.checkNotNull(value);
        return value;
    }

    @Override // org.jetbrains.kotlin.resolve.diagnostics.Diagnostics
    public CompositeModificationTracker getModificationTracker() {
        return this.modificationTracker;
    }

    @Override // org.jetbrains.kotlin.resolve.diagnostics.Diagnostics, org.jetbrains.kotlin.diagnostics.GenericDiagnostics
    public Collection<Diagnostic> all() {
        Collection<Diagnostic> collectionAll = readonlyView().all();
        Intrinsics.checkNotNullExpressionValue(collectionAll, "all(...)");
        return collectionAll;
    }

    @Override // org.jetbrains.kotlin.resolve.diagnostics.Diagnostics
    public Collection<Diagnostic> forElement(PsiElement psiElement) {
        Intrinsics.checkNotNullParameter(psiElement, "psiElement");
        Collection<Diagnostic> collectionForElement = readonlyView().forElement(psiElement);
        Intrinsics.checkNotNullExpressionValue(collectionForElement, "forElement(...)");
        return collectionForElement;
    }

    @Override // org.jetbrains.kotlin.resolve.diagnostics.Diagnostics
    public Diagnostics noSuppression() {
        Diagnostics diagnosticsNoSuppression = readonlyView().noSuppression();
        Intrinsics.checkNotNullExpressionValue(diagnosticsNoSuppression, "noSuppression(...)");
        return diagnosticsNoSuppression;
    }

    @Override // org.jetbrains.kotlin.resolve.diagnostics.Diagnostics
    public boolean setCallbackIfNotSet(DiagnosticSink.DiagnosticsCallback callback) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        if (this.diagnosticsCallback != null) {
            return false;
        }
        this.diagnosticsCallback = callback;
        this.delegateDiagnostics.setCallbackIfNotSet(callback);
        return true;
    }

    @Override // org.jetbrains.kotlin.resolve.diagnostics.Diagnostics
    public void resetCallback() {
        this.diagnosticsCallback = null;
        this.delegateDiagnostics.resetCallback();
    }

    public final List<Diagnostic> getOwnDiagnostics() {
        return this.diagnosticList;
    }

    public final void report(Diagnostic diagnostic) {
        Intrinsics.checkNotNullParameter(diagnostic, "diagnostic");
        DiagnosticSink.DiagnosticsCallback diagnosticsCallbackOnTheFlyDiagnosticsCallback = onTheFlyDiagnosticsCallback(diagnostic);
        if (diagnosticsCallbackOnTheFlyDiagnosticsCallback != null) {
            diagnosticsCallbackOnTheFlyDiagnosticsCallback.callback(diagnostic);
        }
        this.diagnosticList.add(diagnostic);
        getModificationTracker().incModificationCount();
    }

    private final DiagnosticSink.DiagnosticsCallback onTheFlyDiagnosticsCallback(Diagnostic diagnostic) {
        DiagnosticSink.DiagnosticsCallback diagnosticsCallback = this.diagnosticsCallback;
        if (diagnosticsCallback == null) {
            return null;
        }
        PsiElement psiElement = diagnostic.getPsiElement();
        while (psiElement != null && !(psiElement instanceof PsiFile)) {
            KtAnnotated ktAnnotated = (KtAnnotated) KtStubbedPsiUtil.getPsiOrStubParent(psiElement, KtAnnotated.class, false);
            List<KtAnnotationEntry> annotationEntries = ktAnnotated != null ? ktAnnotated.getAnnotationEntries() : null;
            if (annotationEntries != null && (!annotationEntries.isEmpty())) {
                return null;
            }
            psiElement = ktAnnotated != null ? ktAnnotated.getLightParent() : null;
        }
        if (this.suppressCache.getFilter().invoke(diagnostic).booleanValue()) {
            return diagnosticsCallback;
        }
        return null;
    }

    public final void clear() {
        this.diagnosticList.clear();
        getModificationTracker().incModificationCount();
    }

    public final DiagnosticsWithSuppression getReadonlyView() {
        return readonlyView();
    }
}
