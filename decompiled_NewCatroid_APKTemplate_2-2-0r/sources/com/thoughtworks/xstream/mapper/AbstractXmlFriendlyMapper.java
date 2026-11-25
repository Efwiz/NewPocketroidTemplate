package com.thoughtworks.xstream.mapper;

import org.jetbrains.kotlin.name.JvmNames;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/mapper/AbstractXmlFriendlyMapper.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class AbstractXmlFriendlyMapper extends MapperWrapper {
    private char dollarReplacementInClass;
    private String dollarReplacementInField;
    private String noPackagePrefix;
    private String underscoreReplacementInField;

    protected AbstractXmlFriendlyMapper(Mapper mapper) {
        super(mapper);
        this.dollarReplacementInClass = '-';
        this.dollarReplacementInField = "_DOLLAR_";
        this.underscoreReplacementInField = JvmNames.MULTIFILE_PART_NAME_DELIMITER;
        this.noPackagePrefix = "default";
    }

    protected String escapeClassName(String str) {
        String strReplace = str.replace('$', this.dollarReplacementInClass);
        return strReplace.charAt(0) == this.dollarReplacementInClass ? new StringBuffer().append(this.noPackagePrefix).append(strReplace).toString() : strReplace;
    }

    protected String unescapeClassName(String str) {
        if (str.startsWith(new StringBuffer().append(this.noPackagePrefix).append(this.dollarReplacementInClass).toString())) {
            str = str.substring(this.noPackagePrefix.length());
        }
        return str.replace(this.dollarReplacementInClass, '$');
    }

    protected String escapeFieldName(String str) {
        StringBuffer stringBuffer = new StringBuffer();
        int length = str.length();
        for (int i = 0; i < length; i++) {
            char cCharAt = str.charAt(i);
            if (cCharAt == '$') {
                stringBuffer.append(this.dollarReplacementInField);
            } else if (cCharAt == '_') {
                stringBuffer.append(this.underscoreReplacementInField);
            } else {
                stringBuffer.append(cCharAt);
            }
        }
        return stringBuffer.toString();
    }

    protected String unescapeFieldName(String str) {
        StringBuffer stringBuffer = new StringBuffer();
        int length = str.length();
        int length2 = 0;
        while (length2 < length) {
            char cCharAt = str.charAt(length2);
            if (stringFoundAt(str, length2, this.underscoreReplacementInField)) {
                length2 += this.underscoreReplacementInField.length() - 1;
                stringBuffer.append('_');
            } else if (stringFoundAt(str, length2, this.dollarReplacementInField)) {
                length2 += this.dollarReplacementInField.length() - 1;
                stringBuffer.append('$');
            } else {
                stringBuffer.append(cCharAt);
            }
            length2++;
        }
        return stringBuffer.toString();
    }

    private boolean stringFoundAt(String str, int i, String str2) {
        return str.length() >= str2.length() + i && str.substring(i, str2.length() + i).equals(str2);
    }
}
