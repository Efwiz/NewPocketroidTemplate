package org.catrobat.catroid.devices.mindstorms.nxt;

import android.util.SparseArray;
import com.gaurav.avnc.vnc.XKeySym;
import org.catrobat.catroid.devices.mindstorms.MindstormsReply;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/devices/mindstorms/nxt/NXTError.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class NXTError {
    public static final String TAG = "NXTError";

    private NXTError() {
    }

    public static void checkForError(MindstormsReply reply, int expectedLength) throws NXTException {
        if (reply.hasError()) {
            throw new NXTException(ErrorCode.getTypeByValue(reply.getStatusByte()), CommandByte.getTypeByValue(reply.getCommandByte()));
        }
        if (reply.getLength() != expectedLength) {
            throw new NXTException(ErrorCode.WrongNumberOfBytes, CommandByte.getTypeByValue(reply.getCommandByte()));
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/devices/mindstorms/nxt/NXTError$ErrorCode.class
      classes14.dex
     */
    public enum ErrorCode {
        WrongNumberOfBytes(120),
        UnknownErrorCode(121),
        I2CTimeOut(128),
        NoMoreHandles(129),
        NoSpace(130),
        NoMoreFiles(131),
        EndOfFileExpected(132),
        EndOfFile(133),
        NotALinearFile(134),
        FileNotFound(135),
        HandleAlreadyClosed(136),
        NoLinearSpace(137),
        UndefinedFileError(138),
        FileBusy(139),
        NoWriteBuffers(140),
        AppendNotPossible(141),
        FileIsFull(142),
        FileAlreadyExists(143),
        ModuleNotFound(144),
        OutOfBoundary(145),
        IllegalFileName(146),
        IllegalHandle(147),
        PendingCommunication(32),
        MailboxQueueEmpty(64),
        RequestFailed(189),
        UnknownCommand(190),
        InsanePacket(191),
        DataOutOfRange(192),
        CommunicationBusError(221),
        BufferFull(222),
        InvalidChannel(223),
        ChannelBusy(224),
        NoActiveProgram(XKeySym.XK_igrave),
        IllegalSize(XKeySym.XK_iacute),
        InvalidMailboxQueue(XKeySym.XK_icircumflex),
        InvalidField(XKeySym.XK_idiaeresis),
        BadIO(240),
        OutOfMemory(251),
        BadArguments(255);

        private static final SparseArray<ErrorCode> LOOKUP = new SparseArray<>();
        private final int errorCodeValue;

        static {
            for (ErrorCode errorCode : values()) {
                LOOKUP.put(errorCode.errorCodeValue, errorCode);
            }
        }

        ErrorCode(int errorCodeValue) {
            this.errorCodeValue = errorCodeValue;
        }

        public byte getByte() {
            return (byte) this.errorCodeValue;
        }

        public static ErrorCode getTypeByValue(byte value) {
            return LOOKUP.get(value & 255);
        }
    }
}
