package org.catrobat.catroid.utils.lunoscript;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/lunoscript/LunoApi.class
  classes14.dex
 */
/* JADX INFO: compiled from: LunoApi.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u0004J\u000e\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nJ\u0006\u0010\u000b\u001a\u00020\b¨\u0006\f"}, d2 = {"Lorg/catrobat/catroid/utils/lunoscript/LunoApi;", "", "()V", "addTwoNumbers", "", "a", JvmAnnotationNames.SERIALIZED_IR_BYTES_FIELD_NAME, "printGreeting", "", "name", "", "thisIsAPrivateFunction", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class LunoApi {
    public static final LunoApi INSTANCE = new LunoApi();

    public final double addTwoNumbers(double a, double b) {
        return a + b;
    }

    public final void thisIsAPrivateFunction() {
    }

    private LunoApi() {
    }

    public final void printGreeting(String name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        System.out.println((Object) ("Hello, " + name2 + "!"));
    }
}
