package org.jetbrains.kotlin.com.intellij.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* JADX INFO: loaded from: classes3.dex */
public final class SharedProcessingContext {
    private final Map<Object, Object> myMap = new ConcurrentHashMap();
}
