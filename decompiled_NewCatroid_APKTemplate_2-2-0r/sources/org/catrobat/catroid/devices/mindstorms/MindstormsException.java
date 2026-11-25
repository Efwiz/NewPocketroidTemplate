package org.catrobat.catroid.devices.mindstorms;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/devices/mindstorms/MindstormsException.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class MindstormsException extends Exception {
    private final Exception innerException;

    public MindstormsException(String message) {
        this(null, message);
    }

    public MindstormsException(Exception innerException, String message) {
        super(message);
        this.innerException = innerException;
    }

    public Exception getInnerException() {
        return this.innerException;
    }
}
