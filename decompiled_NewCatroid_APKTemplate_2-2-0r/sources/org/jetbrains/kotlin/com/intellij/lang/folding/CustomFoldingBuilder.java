package org.jetbrains.kotlin.com.intellij.lang.folding;

import java.util.Set;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.openapi.project.PossiblyDumbAware;
import org.jetbrains.kotlin.com.intellij.openapi.util.registry.Registry;
import org.jetbrains.kotlin.com.intellij.openapi.util.registry.RegistryValue;

/* JADX INFO: loaded from: classes3.dex */
public abstract class CustomFoldingBuilder extends FoldingBuilderEx implements PossiblyDumbAware {
    private static final RegistryValue myMaxLookupDepth = Registry.get("custom.folding.max.lookup.depth");
    private static final ThreadLocal<Set<ASTNode>> ourCustomRegionElements = new ThreadLocal<>();

    @Override // org.jetbrains.kotlin.com.intellij.openapi.project.PossiblyDumbAware
    public boolean isDumbAware() {
        return true;
    }
}
