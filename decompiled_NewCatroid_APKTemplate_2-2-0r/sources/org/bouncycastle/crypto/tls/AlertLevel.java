package org.bouncycastle.crypto.tls;

import com.huawei.hms.framework.network.grs.GrsBaseInfo;
import com.huawei.hms.ml.language.common.utils.Constant;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;

/* JADX INFO: loaded from: classes13.dex */
public class AlertLevel {
    public static final short fatal = 2;
    public static final short warning = 1;

    public static String getName(short s) {
        if (s != 1) {
            return s != 2 ? GrsBaseInfo.CountryCodeSource.UNKNOWN : "fatal";
        }
        return CompilerOptions.WARNING;
    }

    public static String getText(short s) {
        return getName(s) + "(" + ((int) s) + Constant.AFTER_QUTO;
    }
}
