package org.catrobat.catroid.devices.mindstorms.nxt;

import org.catrobat.catroid.devices.mindstorms.MindstormsReply;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/devices/mindstorms/nxt/NXTReply.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class NXTReply extends MindstormsReply {
    public static final String INSUFFICIENT_REPLY_LENGTH_EXCEPTION_MESSAGE = "Invalid NXT Reply! Message length is smaller than MIN_REPLY_MESSAGE_LENGTH.";
    public static final String INVALID_COMMAND_BYTE_EXCEPTION_MESSAGE = "Invalid NXT Reply! data[1] must be member of CommandByte.";
    public static final String INVALID_FIRST_BYTE_EXCEPTION_MESSAGE = "Invalid NXT Reply! First Byte must be REPLY_COMMAND.";
    public static final int MIN_REPLY_MESSAGE_LENGTH = 3;
    public static final byte NO_ERROR = 0;
    public static final String TAG = "NXTReply";

    @Override // org.catrobat.catroid.devices.mindstorms.MindstormsReply
    public boolean hasError() {
        return getStatusByte() != 0;
    }

    @Override // org.catrobat.catroid.devices.mindstorms.MindstormsReply
    public byte getStatusByte() {
        return this.data[2];
    }

    @Override // org.catrobat.catroid.devices.mindstorms.MindstormsReply
    public byte getCommandByte() {
        return this.data[1];
    }

    public NXTReply(byte[] data) throws NXTException {
        super(data);
        if (data.length < 3) {
            throw new NXTException(INSUFFICIENT_REPLY_LENGTH_EXCEPTION_MESSAGE);
        }
        if (!CommandByte.isMember(data[1])) {
            throw new NXTException(INVALID_COMMAND_BYTE_EXCEPTION_MESSAGE);
        }
        if (data[0] != CommandType.REPLY_COMMAND.getByte()) {
            throw new NXTException(INVALID_FIRST_BYTE_EXCEPTION_MESSAGE);
        }
    }
}
