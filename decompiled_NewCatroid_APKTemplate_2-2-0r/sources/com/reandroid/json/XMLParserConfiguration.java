package com.reandroid.json;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes11.dex */
public class XMLParserConfiguration {
    private String cDataTagName;
    private boolean convertNilAttributeToNull;
    private boolean keepStrings;
    private Map<String, XMLXsiTypeConverter<?>> xsiTypeMap;
    public static final XMLParserConfiguration ORIGINAL = new XMLParserConfiguration();
    public static final XMLParserConfiguration KEEP_STRINGS = new XMLParserConfiguration().withKeepStrings(true);

    public XMLParserConfiguration() {
        this.keepStrings = false;
        this.cDataTagName = FirebaseAnalytics.Param.CONTENT;
        this.convertNilAttributeToNull = false;
        this.xsiTypeMap = Collections.emptyMap();
    }

    @Deprecated
    public XMLParserConfiguration(boolean z) {
        this(z, FirebaseAnalytics.Param.CONTENT, false);
    }

    @Deprecated
    public XMLParserConfiguration(String str) {
        this(false, str, false);
    }

    @Deprecated
    public XMLParserConfiguration(boolean z, String str) {
        this.keepStrings = z;
        this.cDataTagName = str;
        this.convertNilAttributeToNull = false;
    }

    @Deprecated
    public XMLParserConfiguration(boolean z, String str, boolean z2) {
        this.keepStrings = z;
        this.cDataTagName = str;
        this.convertNilAttributeToNull = z2;
    }

    private XMLParserConfiguration(boolean z, String str, boolean z2, Map<String, XMLXsiTypeConverter<?>> map) {
        this.keepStrings = z;
        this.cDataTagName = str;
        this.convertNilAttributeToNull = z2;
        this.xsiTypeMap = Collections.unmodifiableMap(map);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX INFO: renamed from: clone, reason: merged with bridge method [inline-methods] */
    public XMLParserConfiguration m2781clone() {
        return new XMLParserConfiguration(this.keepStrings, this.cDataTagName, this.convertNilAttributeToNull, this.xsiTypeMap);
    }

    public boolean isKeepStrings() {
        return this.keepStrings;
    }

    public XMLParserConfiguration withKeepStrings(boolean z) {
        XMLParserConfiguration xMLParserConfigurationM2781clone = m2781clone();
        xMLParserConfigurationM2781clone.keepStrings = z;
        return xMLParserConfigurationM2781clone;
    }

    public String getcDataTagName() {
        return this.cDataTagName;
    }

    public XMLParserConfiguration withcDataTagName(String str) {
        XMLParserConfiguration xMLParserConfigurationM2781clone = m2781clone();
        xMLParserConfigurationM2781clone.cDataTagName = str;
        return xMLParserConfigurationM2781clone;
    }

    public boolean isConvertNilAttributeToNull() {
        return this.convertNilAttributeToNull;
    }

    public XMLParserConfiguration withConvertNilAttributeToNull(boolean z) {
        XMLParserConfiguration xMLParserConfigurationM2781clone = m2781clone();
        xMLParserConfigurationM2781clone.convertNilAttributeToNull = z;
        return xMLParserConfigurationM2781clone;
    }

    public Map<String, XMLXsiTypeConverter<?>> getXsiTypeMap() {
        return this.xsiTypeMap;
    }

    public XMLParserConfiguration withXsiTypeMap(Map<String, XMLXsiTypeConverter<?>> map) {
        XMLParserConfiguration xMLParserConfigurationM2781clone = m2781clone();
        xMLParserConfigurationM2781clone.xsiTypeMap = Collections.unmodifiableMap(new HashMap(map));
        return xMLParserConfigurationM2781clone;
    }
}
