package com.reandroid.xml;

import com.huawei.hms.framework.common.ContainerUtils;
import com.reandroid.apk.XmlHelper;
import com.reandroid.utils.ObjectsUtil;
import java.io.Closeable;
import java.io.IOException;
import name.antonsmirnov.firmata.FormatHelper;
import org.catrobat.catroid.ui.SpriteActivity;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes11.dex */
public class XMLUtil {
    public static final String FEATURE_INDENT_OUTPUT = ObjectsUtil.of(XmlHelper.FEATURE_INDENT);
    public static String[] EVENT_TYPES = {"START_DOCUMENT", "END_DOCUMENT", "START_TAG", "END_TAG", SpriteActivity.EXTRA_TEXT, "CDSECT", "ENTITY_REF", "IGNORABLE_WHITESPACE", "PROCESSING_INSTRUCTION", "COMMENT", "DOCDECL"};

    public static String decodeEntityRef(String str) {
        if (str == null || str.length() == 0) {
            return "";
        }
        if ("lt".equals(str)) {
            return "<";
        }
        if ("gt".equals(str)) {
            return ">";
        }
        if ("amp".equals(str)) {
            return ContainerUtils.FIELD_DELIMITER;
        }
        if ("quot".equals(str)) {
            return "\"";
        }
        if ("apos".equals(str)) {
            return FormatHelper.QUOTE;
        }
        if (str.charAt(0) != '#') {
            return str;
        }
        try {
            return String.valueOf((char) Integer.parseInt(str.substring(1)));
        } catch (NumberFormatException unused) {
            return str;
        }
    }

    public static String splitName(String str) {
        if (str == null) {
            return null;
        }
        int iLastIndexOf = str.lastIndexOf(58);
        if (iLastIndexOf >= 0) {
            str = str.substring(iLastIndexOf + 1);
        }
        String strTrim = str.trim();
        if (strTrim.length() == 0) {
            return null;
        }
        return strTrim;
    }

    public static String splitPrefix(String str) {
        int iIndexOf;
        if (str != null && (iIndexOf = str.indexOf(58)) > 0) {
            return str.substring(0, iIndexOf);
        }
        return null;
    }

    public static int ensureStartTag(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        int eventType = xmlPullParser.getEventType();
        while (eventType != 2 && eventType != 1) {
            eventType = xmlPullParser.next();
        }
        return eventType;
    }

    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    public static String escapeXmlChars(String str) {
        if (str == null) {
            return null;
        }
        return (str.indexOf(38) >= 0 || str.indexOf(60) >= 0 || str.indexOf(62) >= 0) ? str.replaceAll("&amp;", ContainerUtils.FIELD_DELIMITER).replaceAll("&lt;", "<").replaceAll("&gt;", ">").replaceAll(ContainerUtils.FIELD_DELIMITER, "&amp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;") : str;
    }

    public static String toEventName(int i) {
        String[] strArr = EVENT_TYPES;
        if (i < 0 || i >= strArr.length) {
            return String.valueOf(i);
        }
        return strArr[i];
    }

    public static boolean getFeatureSafe(XmlPullParser xmlPullParser, String str, boolean z) {
        try {
            return xmlPullParser.getFeature(str);
        } catch (Throwable unused) {
            return z;
        }
    }

    public static void setFeatureSafe(XmlPullParser xmlPullParser, String str, boolean z) {
        try {
            xmlPullParser.setFeature(str, z);
        } catch (Throwable unused) {
        }
    }

    public static void setFeatureSafe(XmlSerializer xmlSerializer, String str, boolean z) {
        try {
            xmlSerializer.setFeature(str, z);
        } catch (Throwable unused) {
        }
    }

    public static void close(XmlSerializer xmlSerializer) {
        if (xmlSerializer != null) {
            try {
                xmlSerializer.flush();
            } catch (IOException unused) {
            }
        }
        if (xmlSerializer instanceof Closeable) {
            try {
                ((Closeable) xmlSerializer).close();
            } catch (IOException unused2) {
            }
        }
    }
}
