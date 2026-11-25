package name.antonsmirnov.firmata;

/* JADX INFO: loaded from: classes12.dex */
public class FormatHelper {
    private static final String HEX_PREFIX = "0x";
    public static final String QUOTE = "'";
    public static final String SPACE = " ";

    public String formatBinary(int i) {
        StringBuilder sb = new StringBuilder();
        formatBinary(i, sb);
        return sb.toString();
    }

    protected void formatBinary(int i, StringBuilder sb) {
        sb.append("0x");
        sb.append(Integer.toHexString(i).toUpperCase());
    }

    public String formatBinaryData(int[] iArr, String str, String str2, String str3) {
        StringBuilder sb = new StringBuilder(str2);
        for (int i = 0; i < iArr.length; i++) {
            formatBinary(iArr[i], sb);
            if (i < iArr.length - 1) {
                sb.append(str);
            }
        }
        sb.append(str3);
        return sb.toString();
    }

    public String formatBinaryData(int[] iArr) {
        return formatBinaryData(iArr, " ", QUOTE, QUOTE);
    }

    public String formatBinaryData(byte[] bArr, String str, String str2, String str3) {
        int[] iArr = new int[bArr.length];
        for (int i = 0; i < bArr.length; i++) {
            iArr[i] = bArr[i] & 255;
        }
        return formatBinaryData(iArr, str, str2, str3);
    }

    public String formatBinaryData(byte[] bArr) {
        return formatBinaryData(bArr, " ", QUOTE, QUOTE);
    }
}
