package org.antlr.runtime.tree;

/* JADX INFO: loaded from: classes12.dex */
public interface PositionTrackingStream<T> {
    T getKnownPositionElement(boolean z);

    boolean hasPositionInformation(T t);
}
