package org.apache.xerces.impl.msg;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import kotlin.text.Typography;
import org.apache.xerces.util.MessageFormatter;
import org.jetbrains.kotlin.backend.common.serialization.mangle.MangleConstant;

/* JADX INFO: loaded from: classes13.dex */
public class XMLMessageFormatter implements MessageFormatter {
    public static final String XMLNS_DOMAIN = "http://www.w3.org/TR/1999/REC-xml-names-19990114";
    public static final String XML_DOMAIN = "http://www.w3.org/TR/1998/REC-xml-19980210";
    private Locale fLocale = null;
    private ResourceBundle fResourceBundle = null;

    @Override // org.apache.xerces.util.MessageFormatter
    public String formatMessage(Locale locale, String str, Object[] objArr) throws MissingResourceException {
        if (locale == null) {
            locale = Locale.getDefault();
        }
        if (locale != this.fLocale) {
            this.fResourceBundle = ResourceBundle.getBundle("org.apache.xerces.impl.msg.XMLMessages", locale);
            this.fLocale = locale;
        }
        try {
            String string = this.fResourceBundle.getString(str);
            if (objArr != null) {
                try {
                    string = MessageFormat.format(string, objArr);
                } catch (Exception unused) {
                    string = this.fResourceBundle.getString("FormatFailed") + " " + this.fResourceBundle.getString(str);
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
            throw new MissingResourceException(str, this.fResourceBundle.getString("BadMessageKey"), str);
        }
    }
}
