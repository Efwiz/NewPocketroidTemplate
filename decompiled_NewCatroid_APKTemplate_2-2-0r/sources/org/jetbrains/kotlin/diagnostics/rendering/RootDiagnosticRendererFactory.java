package org.jetbrains.kotlin.diagnostics.rendering;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.concurrent.locks.ReentrantLock;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.diagnostics.KtDiagnostic;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticRenderer;

/* JADX INFO: compiled from: DiagnosticRendererFactory.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0011\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0096\u0002J\u000e\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u0001R\u001e\u0010\u0003\u001a\u0012\u0012\u0004\u0012\u00020\u00010\u0004j\b\u0012\u0004\u0012\u00020\u0001`\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/diagnostics/rendering/RootDiagnosticRendererFactory;", "Lorg/jetbrains/kotlin/diagnostics/rendering/DiagnosticRendererFactory;", "()V", "factories", "Ljava/util/LinkedHashSet;", "Lkotlin/collections/LinkedHashSet;", "lock", "Ljava/util/concurrent/locks/ReentrantLock;", "invoke", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticRenderer;", "diagnostic", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnostic;", "registerFactory", "", "factory", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class RootDiagnosticRendererFactory implements DiagnosticRendererFactory {
    public static final RootDiagnosticRendererFactory INSTANCE = new RootDiagnosticRendererFactory();
    private static final LinkedHashSet<DiagnosticRendererFactory> factories = new LinkedHashSet<>();
    private static final ReentrantLock lock = new ReentrantLock();

    private RootDiagnosticRendererFactory() {
    }

    @Override // org.jetbrains.kotlin.diagnostics.rendering.DiagnosticRendererFactory
    public KtDiagnosticRenderer invoke(KtDiagnostic diagnostic) {
        Intrinsics.checkNotNullParameter(diagnostic, "diagnostic");
        ReentrantLock reentrantLock = lock;
        reentrantLock.lock();
        try {
            Iterator<DiagnosticRendererFactory> it = factories.iterator();
            while (it.hasNext()) {
                KtDiagnosticRenderer ktDiagnosticRendererInvoke = it.next().invoke(diagnostic);
                if (ktDiagnosticRendererInvoke != null) {
                    return ktDiagnosticRendererInvoke;
                }
            }
            return diagnostic.getFactory().getKtRenderer();
        } finally {
            reentrantLock.unlock();
        }
    }

    public final void registerFactory(DiagnosticRendererFactory factory) {
        Intrinsics.checkNotNullParameter(factory, "factory");
        ReentrantLock reentrantLock = lock;
        reentrantLock.lock();
        try {
            factories.add(factory);
        } finally {
            reentrantLock.unlock();
        }
    }
}
