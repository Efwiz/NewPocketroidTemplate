package org.jetbrains.kotlin.com.intellij.util.diff;

import org.jetbrains.kotlin.com.intellij.openapi.util.registry.Registry;

/* JADX INFO: loaded from: classes3.dex */
public class FilesTooBigForDiffException extends Exception {
    public static final int DELTA_THRESHOLD_SIZE = Registry.intValue("diff.delta.threshold.size");

    public FilesTooBigForDiffException() {
        super("Can not calculate diff. File is too big and there are too many changes.");
    }
}
