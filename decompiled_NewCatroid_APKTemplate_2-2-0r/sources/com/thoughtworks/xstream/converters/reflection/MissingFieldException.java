package com.thoughtworks.xstream.converters.reflection;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/converters/reflection/MissingFieldException.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class MissingFieldException extends ObjectAccessException {
    private final String className;
    private final String fieldName;

    public MissingFieldException(String str, String str2) {
        super("Field not found in class.");
        this.className = str;
        this.fieldName = str2;
        add("field", new StringBuffer().append(str).append(".").append(str2).toString());
    }

    public String getFieldName() {
        return this.fieldName;
    }

    protected String getClassName() {
        return this.className;
    }
}
