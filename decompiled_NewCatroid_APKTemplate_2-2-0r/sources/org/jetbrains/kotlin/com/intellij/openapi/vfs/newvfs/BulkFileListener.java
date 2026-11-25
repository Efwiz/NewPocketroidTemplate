package org.jetbrains.kotlin.com.intellij.openapi.vfs.newvfs;

import java.util.List;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.newvfs.events.VFileEvent;

/* JADX INFO: loaded from: classes3.dex */
public interface BulkFileListener {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        objArr[0] = "events";
        objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/vfs/newvfs/BulkFileListener";
        if (i != 1) {
            objArr[2] = "before";
        } else {
            objArr[2] = "after";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    default void after(List<? extends VFileEvent> list) {
        if (list == null) {
            $$$reportNull$$$0(1);
        }
    }

    default void before(List<? extends VFileEvent> list) {
        if (list == null) {
            $$$reportNull$$$0(0);
        }
    }
}
