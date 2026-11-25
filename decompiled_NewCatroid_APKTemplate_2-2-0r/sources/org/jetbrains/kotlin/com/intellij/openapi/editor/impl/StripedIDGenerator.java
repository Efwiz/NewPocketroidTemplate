package org.jetbrains.kotlin.com.intellij.openapi.editor.impl;

import java.util.concurrent.atomic.AtomicLong;

/* JADX INFO: loaded from: classes3.dex */
public class StripedIDGenerator {
    private final AtomicLong nextChunkStart = new AtomicLong();
    private final ThreadLocal<Object> localCounter = new ThreadLocal<>();
}
