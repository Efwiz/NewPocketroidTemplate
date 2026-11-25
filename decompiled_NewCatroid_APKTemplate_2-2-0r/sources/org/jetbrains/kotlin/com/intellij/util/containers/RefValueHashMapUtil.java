package org.jetbrains.kotlin.com.intellij.util.containers;

import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;

/* JADX INFO: loaded from: classes3.dex */
public class RefValueHashMapUtil {
    public static IncorrectOperationException pointlessContainsKey() {
        return new IncorrectOperationException("containsKey() makes no sense for weak/soft map because GC can clear the value any moment now");
    }

    public static IncorrectOperationException pointlessContainsValue() {
        return new IncorrectOperationException("containsValue() makes no sense for weak/soft map because GC can clear the key any moment now");
    }
}
