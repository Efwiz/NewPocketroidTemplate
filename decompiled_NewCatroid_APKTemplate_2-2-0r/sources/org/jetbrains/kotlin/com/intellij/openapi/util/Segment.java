package org.jetbrains.kotlin.com.intellij.openapi.util;

import java.util.Comparator;
import java.util.function.ToIntFunction;

/* JADX INFO: loaded from: classes3.dex */
public interface Segment {
    public static final Segment[] EMPTY_ARRAY = new Segment[0];
    public static final Comparator<Segment> BY_START_OFFSET_THEN_END_OFFSET = Comparator.comparingInt(new ToIntFunction() { // from class: org.jetbrains.kotlin.com.intellij.openapi.util.Segment$$ExternalSyntheticLambda0
        @Override // java.util.function.ToIntFunction
        public final int applyAsInt(Object obj) {
            return ((Segment) obj).getStartOffset();
        }
    }).thenComparingInt(new ToIntFunction() { // from class: org.jetbrains.kotlin.com.intellij.openapi.util.Segment$$ExternalSyntheticLambda1
        @Override // java.util.function.ToIntFunction
        public final int applyAsInt(Object obj) {
            return ((Segment) obj).getEndOffset();
        }
    });

    int getEndOffset();

    int getStartOffset();
}
