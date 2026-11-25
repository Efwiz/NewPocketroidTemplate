package androidx.core.os;

import androidx.core.util.ObjectsCompat;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/core-1.8.0.aar:classes.jar:androidx/core/os/OperationCanceledException.class
  NewCatroid_APKTemplate_2-2-0r.apk:assets/core.jar:androidx/core/os/OperationCanceledException.class
  classes.dex
 */
/* JADX INFO: loaded from: classes.dex */
public class OperationCanceledException extends RuntimeException {
    public OperationCanceledException() {
        this(null);
    }

    public OperationCanceledException(String str) {
        super(ObjectsCompat.toString(str, "The operation has been canceled."));
    }
}
