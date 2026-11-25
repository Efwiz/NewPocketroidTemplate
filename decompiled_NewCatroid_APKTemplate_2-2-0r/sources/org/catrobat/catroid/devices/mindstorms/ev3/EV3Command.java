package org.catrobat.catroid.devices.mindstorms.ev3;

import java.io.ByteArrayOutputStream;
import org.catrobat.catroid.devices.mindstorms.MindstormsCommand;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/devices/mindstorms/ev3/EV3Command.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class EV3Command implements MindstormsCommand {
    private final ByteArrayOutputStream commandData;

    public EV3Command(short commandCounter, EV3CommandType commandType, int globalVars, int localVars, EV3CommandByte.EV3CommandOpCode commandByte) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        this.commandData = byteArrayOutputStream;
        byteArrayOutputStream.write((byte) (commandCounter & 255));
        byteArrayOutputStream.write((byte) ((commandCounter & 65280) >> 8));
        byteArrayOutputStream.write(commandType.getByte());
        byteArrayOutputStream.write((byte) (globalVars & 255));
        byteArrayOutputStream.write((byte) ((localVars << 2) | (globalVars >> 8)));
        byteArrayOutputStream.write(commandByte.getByte());
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

    public void append(EV3CommandByte.EV3CommandByteCode commandCode) {
        append(EV3CommandByte.EV3CommandParamFormat.PARAM_FORMAT_SHORT, commandCode.getByte());
    }

    public void append(EV3CommandByte.EV3CommandVariableScope variableScope, int bytesToReserve) {
        append((byte) (variableScope.getByte() | EV3CommandByte.EV3CommandParamFormat.PARAM_FORMAT_SHORT.getByte() | EV3CommandByte.EV3CommandParamByteCode.PARAM_TYPE_VARIABLE.getByte() | (bytesToReserve & EV3CommandByte.EV3CommandParamByteCode.PARAM_SHORT_MAX.getByte()) | EV3CommandByte.EV3CommandParamByteCode.PARAM_SHORT_SIGN_POSITIVE.getByte()));
    }

    public void append(EV3CommandByte.EV3CommandParamFormat paramFormat, int data) {
        byte b;
        if (paramFormat == EV3CommandByte.EV3CommandParamFormat.PARAM_FORMAT_SHORT) {
            int i = EV3CommandByte.EV3CommandParamFormat.PARAM_FORMAT_SHORT.getByte() | EV3CommandByte.EV3CommandParamByteCode.PARAM_TYPE_CONSTANT.getByte() | (EV3CommandByte.EV3CommandParamByteCode.PARAM_SHORT_MAX.getByte() & data);
            if (data >= 0) {
                b = EV3CommandByte.EV3CommandParamByteCode.PARAM_SHORT_SIGN_POSITIVE.getByte();
            } else {
                b = EV3CommandByte.EV3CommandParamByteCode.PARAM_SHORT_SIGN_NEGATIVE.getByte();
            }
            append((byte) (i | b));
            return;
        }
        if ((data >= 0 && data <= 127) || (data < 0 && data <= 255)) {
            append((byte) (EV3CommandByte.EV3CommandParamFormat.PARAM_FORMAT_LONG.getByte() | EV3CommandByte.EV3CommandParamByteCode.PARAM_TYPE_CONSTANT.getByte() | EV3CommandByte.EV3CommandParamByteCode.PARAM_FOLLOW_ONE_BYTE.getByte()));
            append((byte) (255 & data));
        } else {
            append((byte) (EV3CommandByte.EV3CommandParamFormat.PARAM_FORMAT_LONG.getByte() | EV3CommandByte.EV3CommandParamByteCode.PARAM_TYPE_CONSTANT.getByte() | EV3CommandByte.EV3CommandParamByteCode.PARAM_FOLLOW_TWO_BYTE.getByte()));
            append((byte) (data & 255));
            append((byte) ((data & 65280) >> 8));
        }
    }

    @Override // org.catrobat.catroid.devices.mindstorms.MindstormsCommand
    public int getLength() {
        return this.commandData.size();
    }

    @Override // org.catrobat.catroid.devices.mindstorms.MindstormsCommand
    public byte[] getRawCommand() {
        return this.commandData.toByteArray();
    }
}
