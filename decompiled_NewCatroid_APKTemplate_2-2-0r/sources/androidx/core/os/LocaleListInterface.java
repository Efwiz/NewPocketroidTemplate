package androidx.core.os;

import java.util.Locale;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/core-1.8.0.aar:classes.jar:androidx/core/os/LocaleListInterface.class
  NewCatroid_APKTemplate_2-2-0r.apk:assets/core.jar:androidx/core/os/LocaleListInterface.class
  classes.dex
 */
/* JADX INFO: loaded from: classes.dex */
interface LocaleListInterface {
    Locale get(int i);

    Locale getFirstMatch(String[] strArr);

    Object getLocaleList();

    int indexOf(Locale locale);

    boolean isEmpty();

    int size();

    String toLanguageTags();
}
