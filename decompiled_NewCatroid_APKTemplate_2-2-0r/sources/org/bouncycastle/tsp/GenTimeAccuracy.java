package org.bouncycastle.tsp;

import org.apache.commons.compress.archivers.tar.TarConstants;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.tsp.Accuracy;

/* JADX INFO: loaded from: classes13.dex */
public class GenTimeAccuracy {
    private Accuracy accuracy;

    public GenTimeAccuracy(Accuracy accuracy) {
        this.accuracy = accuracy;
    }

    private String format(int i) {
        StringBuilder sb;
        if (i < 10) {
            sb = new StringBuilder(TarConstants.VERSION_POSIX);
        } else {
            if (i >= 100) {
                return Integer.toString(i);
            }
            sb = new StringBuilder("0");
        }
        return sb.append(i).toString();
    }

    private int getTimeComponent(ASN1Integer aSN1Integer) {
        if (aSN1Integer != null) {
            return aSN1Integer.getValue().intValue();
        }
        return 0;
    }

    public int getMicros() {
        return getTimeComponent(this.accuracy.getMicros());
    }

    public int getMillis() {
        return getTimeComponent(this.accuracy.getMillis());
    }

    public int getSeconds() {
        return getTimeComponent(this.accuracy.getSeconds());
    }

    public String toString() {
        return getSeconds() + "." + format(getMillis()) + format(getMicros());
    }
}
