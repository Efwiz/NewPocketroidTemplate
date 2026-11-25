package com.thoughtworks.xstream.converters.extended;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/converters/extended/StackTraceElementFactory15.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
class StackTraceElementFactory15 extends StackTraceElementFactory {
    StackTraceElementFactory15() {
    }

    @Override // com.thoughtworks.xstream.converters.extended.StackTraceElementFactory
    protected StackTraceElement create(String str, String str2, String str3, int i) {
        return new StackTraceElement(str, str2, str3, i);
    }
}
