package org.catrobat.catroid.devices.mindstorms;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/devices/mindstorms/MindstormsReply.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public abstract class MindstormsReply {
    protected byte[] data;

    public abstract byte getCommandByte();

    public abstract byte getStatusByte();

    public abstract boolean hasError();

    public MindstormsReply(byte[] data) {
        this.data = (byte[]) data.clone();
    }

    public int getLength() {
        return this.data.length;
    }

    public byte[] getData() {
        return (byte[]) this.data.clone();
    }

    public byte[] getData(int offset, int length) {
        if (offset > this.data.length - length) {
            return null;
        }
        byte[] bArr = new byte[length];
        for (int i = 0; i < length; i++) {
            bArr[i] = this.data[i + offset];
        }
        return bArr;
    }

    public byte getByte(int number) {
        return this.data[number];
    }

    public int getShort(int offset) {
        byte[] bArr = this.data;
        return (short) (((bArr[offset + 1] & 255) << 8) | (bArr[offset] & 255));
    }

    public int getInt(int offset) {
        byte[] bArr = this.data;
        return ((bArr[offset + 3] & 255) << 24) | (bArr[offset] & 255) | ((bArr[offset + 1] & 255) << 8) | ((bArr[offset + 2] & 255) << 16);
    }
}
