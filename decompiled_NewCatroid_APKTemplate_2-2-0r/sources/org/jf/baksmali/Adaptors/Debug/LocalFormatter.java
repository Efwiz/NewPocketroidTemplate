package org.jf.baksmali.Adaptors.Debug;

import androidx.exifinterface.media.ExifInterface;
import java.io.IOException;
import org.jf.baksmali.formatter.BaksmaliWriter;
import org.jf.dexlib2.immutable.value.ImmutableNullEncodedValue;

/* JADX INFO: loaded from: classes6.dex */
public class LocalFormatter {
    public static void writeLocal(BaksmaliWriter baksmaliWriter, String str, String str2, String str3) throws IOException {
        if (str != null) {
            baksmaliWriter.writeQuotedString(str);
        } else {
            baksmaliWriter.writeEncodedValue(ImmutableNullEncodedValue.INSTANCE);
        }
        baksmaliWriter.write(58);
        if (str2 != null) {
            baksmaliWriter.writeType(str2);
        } else {
            baksmaliWriter.writeType(ExifInterface.GPS_MEASUREMENT_INTERRUPTED);
        }
        if (str3 != null) {
            baksmaliWriter.write(", ");
            baksmaliWriter.writeQuotedString(str3);
        }
    }
}
