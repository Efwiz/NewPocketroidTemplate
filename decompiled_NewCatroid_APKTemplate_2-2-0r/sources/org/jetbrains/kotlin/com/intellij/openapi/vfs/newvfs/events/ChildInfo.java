package org.jetbrains.kotlin.com.intellij.openapi.vfs.newvfs.events;

import java.util.Comparator;
import java.util.function.Function;

/* JADX INFO: loaded from: classes3.dex */
public interface ChildInfo {
    public static final ChildInfo[] EMPTY_ARRAY = new ChildInfo[0];
    public static final Comparator<ChildInfo> BY_ID = Comparator.comparing(new Function() { // from class: org.jetbrains.kotlin.com.intellij.openapi.vfs.newvfs.events.ChildInfo$$ExternalSyntheticLambda0
        @Override // java.util.function.Function
        public final Object apply(Object obj) {
            return Integer.valueOf(((ChildInfo) obj).getId());
        }
    });

    int getId();
}
