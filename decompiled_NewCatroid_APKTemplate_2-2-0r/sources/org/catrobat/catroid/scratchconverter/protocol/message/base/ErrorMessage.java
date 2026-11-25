package org.catrobat.catroid.scratchconverter.protocol.message.base;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/scratchconverter/protocol/message/base/ErrorMessage.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class ErrorMessage extends BaseMessage {
    private final String message;

    public ErrorMessage(final String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
