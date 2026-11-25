package org.catrobat.catroid.devices.mindstorms.ev3;

import org.catrobat.catroid.devices.mindstorms.MindstormsException;
import org.catrobat.catroid.devices.mindstorms.MindstormsReply;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiLiteralUtil;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/devices/mindstorms/ev3/EV3Reply.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class EV3Reply extends MindstormsReply {
    public static final int MIN_REPLY_MESSAGE_LENGTH = 3;
    public static final byte NO_ERROR = 2;
    public static final String TAG = "EV3Reply";

    @Override // org.catrobat.catroid.devices.mindstorms.MindstormsReply
    public byte getCommandByte() {
        return (byte) 0;
    }

    @Override // org.catrobat.catroid.devices.mindstorms.MindstormsReply
    public boolean hasError() {
        return getStatusByte() != 2;
    }

    @Override // org.catrobat.catroid.devices.mindstorms.MindstormsReply
    public byte getStatusByte() {
        return this.data[2];
    }

    public boolean isValid(int commandCounter) {
        return this.data[0] == ((byte) (commandCounter & 255)) && this.data[1] == ((commandCounter & 65280) >> 8) && !hasError();
    }

    public EV3Reply(byte[] data) throws MindstormsException {
        super(data);
        if (data.length < 3) {
            throw new MindstormsException("Invalid EV3 Reply");
        }
    }

    public String toHexString(EV3Reply reply) {
        byte[] data = reply.getData();
        StringBuilder sb = new StringBuilder(PsiLiteralUtil.HEX_PREFIX);
        if (data.length == 0) {
            return "null";
        }
        for (byte b : data) {
            sb.append(Integer.toHexString(b & 255));
            sb.append('_');
        }
        return sb.toString();
    }
}
