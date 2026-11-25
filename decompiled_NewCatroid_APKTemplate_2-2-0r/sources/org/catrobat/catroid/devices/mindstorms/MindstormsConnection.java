package org.catrobat.catroid.devices.mindstorms;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/devices/mindstorms/MindstormsConnection.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public interface MindstormsConnection {
    void disconnect();

    short getCommandCounter();

    void incCommandCounter();

    void init() throws MindstormsException;

    boolean isConnected();

    void send(MindstormsCommand command) throws MindstormsException;

    byte[] sendAndReceive(MindstormsCommand command) throws MindstormsException;
}
