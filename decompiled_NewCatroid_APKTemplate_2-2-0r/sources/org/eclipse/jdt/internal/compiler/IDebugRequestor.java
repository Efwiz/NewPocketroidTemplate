package org.eclipse.jdt.internal.compiler;

/* JADX INFO: loaded from: classes14.dex */
public interface IDebugRequestor {
    void acceptDebugResult(CompilationResult compilationResult);

    void activate();

    void deactivate();

    boolean isActive();

    void reset();
}
