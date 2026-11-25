package com.thoughtworks.xstream.core;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/core/BaseException.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public abstract class BaseException extends RuntimeException {
    protected BaseException(String str) {
        super(str);
    }

    protected BaseException(String str, Throwable th) {
        super(str, th);
    }
}
