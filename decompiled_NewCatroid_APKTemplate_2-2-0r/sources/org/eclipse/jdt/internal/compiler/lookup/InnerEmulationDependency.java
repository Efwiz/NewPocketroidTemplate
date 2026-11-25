package org.eclipse.jdt.internal.compiler.lookup;

/* JADX INFO: loaded from: classes14.dex */
public class InnerEmulationDependency {
    public BlockScope scope;
    public boolean wasEnclosingInstanceSupplied;

    public InnerEmulationDependency(BlockScope blockScope, boolean z) {
        this.scope = blockScope;
        this.wasEnclosingInstanceSupplied = z;
    }
}
