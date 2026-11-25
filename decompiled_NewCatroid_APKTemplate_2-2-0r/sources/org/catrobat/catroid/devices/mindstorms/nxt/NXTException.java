package org.catrobat.catroid.devices.mindstorms.nxt;

import org.catrobat.catroid.devices.mindstorms.MindstormsException;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/devices/mindstorms/nxt/NXTException.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class NXTException extends MindstormsException {
    private final NXTError.ErrorCode error;

    public NXTError.ErrorCode getError() {
        return this.error;
    }

    public NXTException(NXTError.ErrorCode error, CommandByte commandByte) {
        super(String.format("NXTException: Error caused by  '%s' on command '%s'", error.name(), commandByte.name()));
        this.error = error;
    }

    public NXTException(String message) {
        super(message);
        this.error = null;
    }
}
