package org.catrobat.catroid.scratchconverter.protocol.message.base;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/scratchconverter/protocol/message/base/ClientIDMessage.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class ClientIDMessage extends BaseMessage {
    private final long clientID;

    public ClientIDMessage(final long clientID) {
        this.clientID = clientID;
    }

    public long getClientID() {
        return this.clientID;
    }
}
