package org.jetbrains.kotlin.com.intellij.openapi.editor;

import java.util.Comparator;
import org.jetbrains.kotlin.com.intellij.openapi.util.Segment;
import org.jetbrains.kotlin.com.intellij.openapi.util.UserDataHolder;

/* JADX INFO: loaded from: classes3.dex */
public interface RangeMarker extends Segment, UserDataHolder {
    public static final Comparator<? super RangeMarker> BY_START_OFFSET = BY_START_OFFSET_THEN_END_OFFSET;

    void dispose();

    @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Segment
    int getEndOffset();

    @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Segment
    int getStartOffset();

    boolean isGreedyToLeft();

    boolean isGreedyToRight();

    boolean isValid();
}
