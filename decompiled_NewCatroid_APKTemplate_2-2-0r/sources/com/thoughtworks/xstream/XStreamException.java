package com.thoughtworks.xstream;

import com.thoughtworks.xstream.core.BaseException;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/XStreamException.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class XStreamException extends BaseException {
    protected XStreamException() {
        this("", null);
    }

    public XStreamException(String str) {
        this(str, null);
    }

    public XStreamException(Throwable th) {
        this("", th);
    }

    public XStreamException(String str, Throwable th) {
        super(str, th);
    }
}
