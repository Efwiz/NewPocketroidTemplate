package org.catrobat.catroid.io;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/io/XStreamFieldKeyOrder.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface XStreamFieldKeyOrder {
    String[] value();
}
