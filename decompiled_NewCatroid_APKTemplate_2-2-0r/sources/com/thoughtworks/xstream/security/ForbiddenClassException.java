package com.thoughtworks.xstream.security;

import com.thoughtworks.xstream.XStreamException;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/security/ForbiddenClassException.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class ForbiddenClassException extends XStreamException {
    public ForbiddenClassException(Class cls) {
        super(cls == null ? "null" : cls.getName());
    }
}
