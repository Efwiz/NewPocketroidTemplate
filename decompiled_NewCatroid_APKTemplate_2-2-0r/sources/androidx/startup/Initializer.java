package androidx.startup;

import android.content.Context;
import java.util.List;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/startup-runtime-1.1.1.aar:classes.jar:androidx/startup/Initializer.class
  classes.dex
 */
/* JADX INFO: loaded from: classes.dex */
public interface Initializer<T> {
    T create(Context context);

    List<Class<? extends Initializer<?>>> dependencies();
}
