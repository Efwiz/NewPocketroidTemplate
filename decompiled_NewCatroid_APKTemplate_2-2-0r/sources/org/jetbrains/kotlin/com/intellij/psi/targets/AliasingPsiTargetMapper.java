package org.jetbrains.kotlin.com.intellij.psi.targets;

import java.util.Set;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.ExtensionPointName;
import org.jetbrains.kotlin.com.intellij.pom.PomTarget;

/* JADX INFO: loaded from: classes3.dex */
public interface AliasingPsiTargetMapper {
    public static final ExtensionPointName<AliasingPsiTargetMapper> EP_NAME = ExtensionPointName.create("org.jetbrains.kotlin.com.intellij.aliasingPsiTargetMapper");

    Set<AliasingPsiTarget> getTargets(PomTarget pomTarget);
}
