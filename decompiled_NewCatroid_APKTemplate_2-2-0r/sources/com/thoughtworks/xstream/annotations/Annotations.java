package com.thoughtworks.xstream.annotations;

import com.thoughtworks.xstream.XStream;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/annotations/Annotations.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
@Deprecated
public class Annotations {
    private Annotations() {
    }

    @Deprecated
    public static synchronized void configureAliases(XStream xStream, Class<?>... clsArr) {
        xStream.processAnnotations(clsArr);
    }
}
