package org.catrobat.catroid.retrofit.converters;

import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/retrofit/converters/StringListConverter.class
  classes14.dex
 */
/* JADX INFO: compiled from: StringListConverter.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0010!\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u000e\u0010\u0005\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u0006H\u0007J\u0016\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00040\b2\u0006\u0010\t\u001a\u00020\u0004H\u0007¨\u0006\n"}, d2 = {"Lorg/catrobat/catroid/retrofit/converters/StringListConverter;", "", "()V", "stringListToString", "", "stringList", "", "stringToStringList", "", "string", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class StringListConverter {
    public final String stringListToString(List<String> stringList) {
        return StringsKt.replace$default(StringsKt.replace$default(StringsKt.replace$default(String.valueOf(stringList), "[", "", false, 4, (Object) null), "]", "", false, 4, (Object) null), " ", "", false, 4, (Object) null);
    }

    public final List<String> stringToStringList(String string) {
        Intrinsics.checkNotNullParameter(string, "string");
        return CollectionsKt.toMutableList((Collection) StringsKt.split$default((CharSequence) string, new String[]{","}, false, 0, 6, (Object) null));
    }
}
