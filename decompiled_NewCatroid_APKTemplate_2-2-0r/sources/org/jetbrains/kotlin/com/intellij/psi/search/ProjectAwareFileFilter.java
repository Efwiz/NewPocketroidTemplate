package org.jetbrains.kotlin.com.intellij.psi.search;

import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFileFilter;

/* JADX INFO: loaded from: classes3.dex */
public interface ProjectAwareFileFilter extends VirtualFileFilter {
    Project getProject();
}
