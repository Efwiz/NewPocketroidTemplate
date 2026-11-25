package org.jetbrains.kotlin.org.fusesource.jansi;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import org.jetbrains.kotlin.backend.common.serialization.mangle.MangleConstant;

/* JADX INFO: loaded from: classes4.dex */
public class AnsiOutputStream extends FilterOutputStream {
    protected static final int ATTRIBUTE_BLINK_FAST = 6;
    protected static final int ATTRIBUTE_BLINK_OFF = 25;
    protected static final int ATTRIBUTE_BLINK_SLOW = 5;
    protected static final int ATTRIBUTE_CONCEAL_OFF = 28;
    protected static final int ATTRIBUTE_CONCEAL_ON = 8;
    protected static final int ATTRIBUTE_INTENSITY_BOLD = 1;
    protected static final int ATTRIBUTE_INTENSITY_FAINT = 2;
    protected static final int ATTRIBUTE_INTENSITY_NORMAL = 22;
    protected static final int ATTRIBUTE_ITALIC = 3;
    protected static final int ATTRIBUTE_NEGATIVE_OFF = 27;
    protected static final int ATTRIBUTE_NEGATIVE_ON = 7;

    @Deprecated
    protected static final int ATTRIBUTE_NEGATIVE_Off = 27;
    protected static final int ATTRIBUTE_UNDERLINE = 4;
    protected static final int ATTRIBUTE_UNDERLINE_DOUBLE = 21;
    protected static final int ATTRIBUTE_UNDERLINE_OFF = 24;
    private static final int BEL = 7;
    protected static final int BLACK = 0;
    protected static final int BLUE = 4;
    protected static final int CYAN = 6;
    protected static final int ERASE_LINE = 2;
    protected static final int ERASE_LINE_TO_BEGINING = 1;
    protected static final int ERASE_LINE_TO_END = 0;
    protected static final int ERASE_SCREEN = 2;
    protected static final int ERASE_SCREEN_TO_BEGINING = 1;
    protected static final int ERASE_SCREEN_TO_END = 0;
    private static final int FIRST_ESC_CHAR = 27;
    protected static final int GREEN = 2;
    private static final int LOOKING_FOR_FIRST_ESC_CHAR = 0;
    private static final int LOOKING_FOR_INT_ARG_END = 4;
    private static final int LOOKING_FOR_NEXT_ARG = 2;
    private static final int LOOKING_FOR_OSC_COMMAND = 5;
    private static final int LOOKING_FOR_OSC_COMMAND_END = 6;
    private static final int LOOKING_FOR_OSC_PARAM = 7;
    private static final int LOOKING_FOR_SECOND_ESC_CHAR = 1;
    private static final int LOOKING_FOR_ST = 8;
    private static final int LOOKING_FOR_STR_ARG_END = 3;
    protected static final int MAGENTA = 5;
    private static final int MAX_ESCAPE_SEQUENCE_LENGTH = 100;
    protected static final int RED = 1;
    public static final byte[] RESET_CODE;

    @Deprecated
    public static final byte[] REST_CODE;
    private static final int SECOND_ESC_CHAR = 91;
    private static final int SECOND_OSC_CHAR = 93;
    private static final int SECOND_ST_CHAR = 92;
    protected static final int WHITE = 7;
    protected static final int YELLOW = 3;
    private final byte[] buffer;
    private final ArrayList<Object> options;
    private int pos;
    private int startOfValue;
    int state;

    protected void processAttributeRest() throws IOException {
    }

    protected void processChangeIconName(String str) {
    }

    protected void processChangeWindowTitle(String str) {
    }

    protected void processCursorDown(int i) throws IOException {
    }

    protected void processCursorLeft(int i) throws IOException {
    }

    protected void processCursorTo(int i, int i2) throws IOException {
    }

    protected void processCursorToColumn(int i) throws IOException {
    }

    protected void processCursorUp(int i) throws IOException {
    }

    protected void processCursorUpLine(int i) throws IOException {
    }

    protected void processDefaultBackgroundColor() throws IOException {
    }

    protected void processDefaultTextColor() throws IOException {
    }

    protected void processDeleteLine(int i) throws IOException {
    }

    protected void processEraseLine(int i) throws IOException {
    }

    protected void processEraseScreen(int i) throws IOException {
    }

    protected void processInsertLine(int i) throws IOException {
    }

    protected void processRestoreCursorPosition() throws IOException {
    }

    protected void processSaveCursorPosition() throws IOException {
    }

    protected void processScrollDown(int i) throws IOException {
    }

    protected void processScrollUp(int i) throws IOException {
    }

    protected void processSetAttribute(int i) throws IOException {
    }

    protected void processSetBackgroundColor(int i, boolean z) throws IOException {
    }

    protected void processSetBackgroundColorExt(int i) throws IOException {
    }

    protected void processSetBackgroundColorExt(int i, int i2, int i3) throws IOException {
    }

    protected void processSetForegroundColor(int i, boolean z) throws IOException {
    }

    protected void processSetForegroundColorExt(int i) throws IOException {
    }

    protected void processSetForegroundColorExt(int i, int i2, int i3) throws IOException {
    }

    protected void processUnknownExtension(ArrayList<Object> arrayList, int i) {
    }

    protected void processUnknownOperatingSystemCommand(int i, String str) {
    }

    static {
        byte[] bytes = "\u001b[0m".getBytes();
        RESET_CODE = bytes;
        REST_CODE = bytes;
    }

    public AnsiOutputStream(OutputStream outputStream) {
        super(outputStream);
        this.buffer = new byte[100];
        this.pos = 0;
        this.options = new ArrayList<>();
        this.state = 0;
    }

    @Override // java.io.FilterOutputStream, java.io.OutputStream
    public synchronized void write(int i) throws IOException {
        switch (this.state) {
            case 0:
                if (i == 27) {
                    byte[] bArr = this.buffer;
                    int i2 = this.pos;
                    this.pos = i2 + 1;
                    bArr[i2] = (byte) i;
                    this.state = 1;
                } else {
                    this.out.write(i);
                }
                break;
            case 1:
                byte[] bArr2 = this.buffer;
                int i3 = this.pos;
                this.pos = i3 + 1;
                bArr2[i3] = (byte) i;
                if (i == 91) {
                    this.state = 2;
                } else if (i == 93) {
                    this.state = 5;
                } else {
                    reset(false);
                }
                break;
            case 2:
                byte[] bArr3 = this.buffer;
                int i4 = this.pos;
                this.pos = i4 + 1;
                bArr3[i4] = (byte) i;
                if (34 == i) {
                    this.startOfValue = i4;
                    this.state = 3;
                } else if (48 <= i && i <= 57) {
                    this.startOfValue = i4;
                    this.state = 4;
                } else if (59 == i) {
                    this.options.add(null);
                } else if (63 == i) {
                    this.options.add(Character.valueOf(MangleConstant.Q_MARK));
                } else if (61 == i) {
                    this.options.add('=');
                } else {
                    reset(processEscapeCommand(this.options, i));
                }
                break;
            case 3:
                byte[] bArr4 = this.buffer;
                int i5 = this.pos;
                this.pos = i5 + 1;
                bArr4[i5] = (byte) i;
                if (34 != i) {
                    byte[] bArr5 = this.buffer;
                    int i6 = this.startOfValue;
                    this.options.add(new String(bArr5, i6, (this.pos - 1) - i6, Charset.defaultCharset()));
                    if (i == 59) {
                        this.state = 2;
                    } else {
                        reset(processEscapeCommand(this.options, i));
                    }
                }
                break;
            case 4:
                byte[] bArr6 = this.buffer;
                int i7 = this.pos;
                this.pos = i7 + 1;
                bArr6[i7] = (byte) i;
                if (48 > i || i > 57) {
                    byte[] bArr7 = this.buffer;
                    int i8 = this.startOfValue;
                    this.options.add(new Integer(new String(bArr7, i8, (this.pos - 1) - i8, Charset.defaultCharset())));
                    if (i == 59) {
                        this.state = 2;
                    } else {
                        reset(processEscapeCommand(this.options, i));
                    }
                }
                break;
            case 5:
                byte[] bArr8 = this.buffer;
                int i9 = this.pos;
                this.pos = i9 + 1;
                bArr8[i9] = (byte) i;
                if (48 <= i && i <= 57) {
                    this.startOfValue = i9;
                    this.state = 6;
                } else {
                    reset(false);
                }
                break;
            case 6:
                byte[] bArr9 = this.buffer;
                int i10 = this.pos;
                this.pos = i10 + 1;
                bArr9[i10] = (byte) i;
                if (59 == i) {
                    byte[] bArr10 = this.buffer;
                    int i11 = this.startOfValue;
                    this.options.add(new Integer(new String(bArr10, i11, (this.pos - 1) - i11, Charset.defaultCharset())));
                    this.startOfValue = this.pos;
                    this.state = 7;
                } else if (48 > i || i > 57) {
                    reset(false);
                }
                break;
            case 7:
                byte[] bArr11 = this.buffer;
                int i12 = this.pos;
                this.pos = i12 + 1;
                bArr11[i12] = (byte) i;
                if (7 == i) {
                    byte[] bArr12 = this.buffer;
                    int i13 = this.startOfValue;
                    this.options.add(new String(bArr12, i13, (this.pos - 1) - i13, Charset.defaultCharset()));
                    reset(processOperatingSystemCommand(this.options));
                } else if (27 == i) {
                    this.state = 8;
                }
                break;
            case 8:
                byte[] bArr13 = this.buffer;
                int i14 = this.pos;
                this.pos = i14 + 1;
                bArr13[i14] = (byte) i;
                if (92 == i) {
                    byte[] bArr14 = this.buffer;
                    int i15 = this.startOfValue;
                    this.options.add(new String(bArr14, i15, (this.pos - 2) - i15, Charset.defaultCharset()));
                    reset(processOperatingSystemCommand(this.options));
                } else {
                    this.state = 7;
                }
                break;
        }
        if (this.pos >= this.buffer.length) {
            reset(false);
        }
    }

    private void reset(boolean z) throws IOException {
        if (!z) {
            this.out.write(this.buffer, 0, this.pos);
        }
        this.pos = 0;
        this.startOfValue = 0;
        this.options.clear();
        this.state = 0;
    }

    private int getNextOptionInt(Iterator<Object> it) throws IOException {
        while (it.hasNext()) {
            Object next = it.next();
            if (next != null) {
                return ((Integer) next).intValue();
            }
        }
        throw new IllegalArgumentException();
    }

    private boolean processEscapeCommand(ArrayList<Object> arrayList, int i) throws IOException {
        try {
            if (i == 83) {
                processScrollUp(optionInt(arrayList, 0, 1));
                return true;
            }
            if (i != 84) {
                if (i != 102) {
                    if (i == 109) {
                        for (Object obj : arrayList) {
                            if (obj != null && obj.getClass() != Integer.class) {
                                throw new IllegalArgumentException();
                            }
                        }
                        Iterator<Object> it = arrayList.iterator();
                        int i2 = 0;
                        while (it.hasNext()) {
                            Object next = it.next();
                            if (next != null) {
                                i2++;
                                int iIntValue = ((Integer) next).intValue();
                                if (30 <= iIntValue && iIntValue <= 37) {
                                    processSetForegroundColor(iIntValue - 30);
                                } else if (40 <= iIntValue && iIntValue <= 47) {
                                    processSetBackgroundColor(iIntValue - 40);
                                } else if (90 <= iIntValue && iIntValue <= 97) {
                                    processSetForegroundColor(iIntValue - 90, true);
                                } else if (100 <= iIntValue && iIntValue <= 107) {
                                    processSetBackgroundColor(iIntValue - 100, true);
                                } else if (iIntValue == 38 || iIntValue == 48) {
                                    int nextOptionInt = getNextOptionInt(it);
                                    if (nextOptionInt == 2) {
                                        int nextOptionInt2 = getNextOptionInt(it);
                                        int nextOptionInt3 = getNextOptionInt(it);
                                        int nextOptionInt4 = getNextOptionInt(it);
                                        if (nextOptionInt2 < 0 || nextOptionInt2 > 255 || nextOptionInt3 < 0 || nextOptionInt3 > 255 || nextOptionInt4 < 0 || nextOptionInt4 > 255) {
                                            throw new IllegalArgumentException();
                                        }
                                        if (iIntValue == 38) {
                                            processSetForegroundColorExt(nextOptionInt2, nextOptionInt3, nextOptionInt4);
                                        } else {
                                            processSetBackgroundColorExt(nextOptionInt2, nextOptionInt3, nextOptionInt4);
                                        }
                                    } else if (nextOptionInt == 5) {
                                        int nextOptionInt5 = getNextOptionInt(it);
                                        if (nextOptionInt5 < 0 || nextOptionInt5 > 255) {
                                            throw new IllegalArgumentException();
                                        }
                                        if (iIntValue == 38) {
                                            processSetForegroundColorExt(nextOptionInt5);
                                        } else {
                                            processSetBackgroundColorExt(nextOptionInt5);
                                        }
                                    } else {
                                        throw new IllegalArgumentException();
                                    }
                                } else if (iIntValue == 0) {
                                    processAttributeRest();
                                } else if (iIntValue == 39) {
                                    processDefaultTextColor();
                                } else if (iIntValue == 49) {
                                    processDefaultBackgroundColor();
                                } else {
                                    processSetAttribute(iIntValue);
                                }
                            }
                        }
                        if (i2 == 0) {
                            processAttributeRest();
                        }
                        return true;
                    }
                    if (i == 115) {
                        processSaveCursorPosition();
                        return true;
                    }
                    if (i != 117) {
                        switch (i) {
                            case 65:
                                processCursorUp(optionInt(arrayList, 0, 1));
                                return true;
                            case 66:
                                processCursorDown(optionInt(arrayList, 0, 1));
                                return true;
                            case 67:
                                processCursorRight(optionInt(arrayList, 0, 1));
                                return true;
                            case 68:
                                processCursorLeft(optionInt(arrayList, 0, 1));
                                return true;
                            case 69:
                                processCursorDownLine(optionInt(arrayList, 0, 1));
                                return true;
                            case 70:
                                processCursorUpLine(optionInt(arrayList, 0, 1));
                                return true;
                            case 71:
                                processCursorToColumn(optionInt(arrayList, 0));
                                return true;
                            case 72:
                                break;
                            default:
                                switch (i) {
                                    case 74:
                                        processEraseScreen(optionInt(arrayList, 0, 0));
                                        return true;
                                    case 75:
                                        processEraseLine(optionInt(arrayList, 0, 0));
                                        return true;
                                    case 76:
                                        processInsertLine(optionInt(arrayList, 0, 1));
                                        return true;
                                    case 77:
                                        processDeleteLine(optionInt(arrayList, 0, 1));
                                        return true;
                                    default:
                                        if (97 <= i && 122 <= i) {
                                            processUnknownExtension(arrayList, i);
                                            return true;
                                        }
                                        if (65 > i || 90 > i) {
                                            return false;
                                        }
                                        processUnknownExtension(arrayList, i);
                                        return true;
                                }
                        }
                    } else {
                        processRestoreCursorPosition();
                        return true;
                    }
                }
                processCursorTo(optionInt(arrayList, 0, 1), optionInt(arrayList, 1, 1));
                return true;
            }
            processScrollDown(optionInt(arrayList, 0, 1));
            return true;
        } catch (IllegalArgumentException unused) {
            return false;
        }
    }

    private boolean processOperatingSystemCommand(ArrayList<Object> arrayList) throws IOException {
        int iOptionInt = optionInt(arrayList, 0);
        String str = (String) arrayList.get(1);
        try {
            if (iOptionInt == 0) {
                processChangeIconNameAndWindowTitle(str);
                return true;
            }
            if (iOptionInt == 1) {
                processChangeIconName(str);
                return true;
            }
            if (iOptionInt == 2) {
                processChangeWindowTitle(str);
                return true;
            }
            processUnknownOperatingSystemCommand(iOptionInt, str);
            return true;
        } catch (IllegalArgumentException unused) {
            return false;
        }
    }

    protected void processSetForegroundColor(int i) throws IOException {
        processSetForegroundColor(i, false);
    }

    protected void processSetBackgroundColor(int i) throws IOException {
        processSetBackgroundColor(i, false);
    }

    protected void processCursorDownLine(int i) throws IOException {
        for (int i2 = 0; i2 < i; i2++) {
            this.out.write(10);
        }
    }

    protected void processCursorRight(int i) throws IOException {
        for (int i2 = 0; i2 < i; i2++) {
            this.out.write(32);
        }
    }

    protected void processChangeIconNameAndWindowTitle(String str) {
        processChangeIconName(str);
        processChangeWindowTitle(str);
    }

    private int optionInt(ArrayList<Object> arrayList, int i) {
        if (arrayList.size() <= i) {
            throw new IllegalArgumentException();
        }
        Object obj = arrayList.get(i);
        if (obj == null) {
            throw new IllegalArgumentException();
        }
        if (!obj.getClass().equals(Integer.class)) {
            throw new IllegalArgumentException();
        }
        return ((Integer) obj).intValue();
    }

    private int optionInt(ArrayList<Object> arrayList, int i, int i2) {
        Object obj;
        return (arrayList.size() <= i || (obj = arrayList.get(i)) == null) ? i2 : ((Integer) obj).intValue();
    }

    @Override // java.io.FilterOutputStream, java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        write(RESET_CODE);
        flush();
        super.close();
    }
}
