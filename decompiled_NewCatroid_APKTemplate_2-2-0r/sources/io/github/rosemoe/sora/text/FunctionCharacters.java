package io.github.rosemoe.sora.text;

/* JADX INFO: loaded from: classes11.dex */
public class FunctionCharacters {
    private static final String[] names = {"NUL", "SOH", "STX", "ETX", "EOT", "ENQ", "ACK", "BEL", "BS", "HT", "LF", "VT", "FF", "CR", "SO", "SI", "DLE", "DC1", "DC2", "DC3", "DC4", "NAK", "SYN", "ETB", "CAN", "EM", "SUB", "ESC", "FS", "GS", "RS", "US", "SP"};

    public static boolean isFunctionCharacter(char c) {
        return c < ' ' || c == 127;
    }

    public static boolean isEditorFunctionChar(char c) {
        return c != '\t' && isFunctionCharacter(c);
    }

    public static String getNameForFunctionCharacter(char c) {
        if (c < ' ') {
            return names[c];
        }
        if (c == 127) {
            return "DEL";
        }
        return "UNK";
    }
}
