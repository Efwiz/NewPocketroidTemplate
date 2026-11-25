package org.apache.xerces.jaxp.validation;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import kotlin.text.Typography;
import org.jetbrains.kotlin.backend.common.serialization.mangle.MangleConstant;

/* JADX INFO: loaded from: classes13.dex */
final class JAXPValidationMessageFormatter {
    JAXPValidationMessageFormatter() {
    }

    public static String formatMessage(Locale locale, String str, Object[] objArr) throws MissingResourceException {
        if (locale == null) {
            locale = Locale.getDefault();
        }
        ResourceBundle bundle = ResourceBundle.getBundle("org.apache.xerces.impl.msg.JAXPValidationMessages", locale);
        try {
            String string = bundle.getString(str);
            if (objArr != null) {
                try {
                    string = MessageFormat.format(string, objArr);
                } catch (Exception unused) {
                    string = bundle.getString("FormatFailed") + " " + bundle.getString(str);
                }
            }
            if (string != null) {
                return string;
            }
            if (objArr.length <= 0) {
                return str;
            }
            StringBuffer stringBuffer = new StringBuffer(str);
            stringBuffer.append(MangleConstant.Q_MARK);
            for (int i = 0; i < objArr.length; i++) {
                if (i > 0) {
                    stringBuffer.append(Typography.amp);
                }
                stringBuffer.append(String.valueOf(objArr[i]));
            }
            return str;
        } catch (MissingResourceException unused2) {
            throw new MissingResourceException(str, bundle.getString("BadMessageKey"), str);
        }
    }
}
