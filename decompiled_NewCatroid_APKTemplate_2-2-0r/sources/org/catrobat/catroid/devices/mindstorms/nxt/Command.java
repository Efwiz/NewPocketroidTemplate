package org.catrobat.catroid.devices.mindstorms.nxt;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import org.catrobat.catroid.devices.mindstorms.MindstormsCommand;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/devices/mindstorms/nxt/Command.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class Command implements MindstormsCommand {
    private static final String TAG = "Command";
    private ByteArrayOutputStream commandData;

    public Command(CommandType commandType, CommandByte commandByte, boolean reply) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        this.commandData = byteArrayOutputStream;
        if (reply) {
            byteArrayOutputStream.write(commandType.getByte());
        } else {
            byteArrayOutputStream.write((byte) (commandType.getByte() | 128));
        }
        this.commandData.write(commandByte.getByte());
    }

    public void append(byte data) {
        this.commandData.write(data);
    }

    public void append(byte[] data) {
        this.commandData.write(data, 0, data.length);
    }

    public void append(int data) {
        append((byte) (data & 255));
        append((byte) ((data >> 8) & 255));
        append((byte) ((data >> 16) & 255));
        append((byte) ((data >> 24) & 255));
    }

    @Override // org.catrobat.catroid.devices.mindstorms.MindstormsCommand
    public int getLength() {
        return this.commandData.size();
    }

    @Override // org.catrobat.catroid.devices.mindstorms.MindstormsCommand
    public byte[] getRawCommand() {
        return this.commandData.toByteArray();
    }

    public boolean equals(Object obj) {
        if (obj instanceof Command) {
            return Arrays.equals(this.commandData.toByteArray(), ((Command) obj).commandData.toByteArray());
        }
        return false;
    }

    public int hashCode() {
        return super.hashCode() * TAG.hashCode();
    }
}
