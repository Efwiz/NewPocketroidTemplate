package org.catrobat.catroid.retrofit.converters;

import kotlin.Metadata;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/retrofit/converters/BooleanConverter.class
  classes14.dex
 */
/* JADX INFO: compiled from: BooleanConverter.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u0010\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\u0004H\u0007¨\u0006\t"}, d2 = {"Lorg/catrobat/catroid/retrofit/converters/BooleanConverter;", "", "()V", "booleanToInteger", "", "condition", "", "integerToBoolean", "value", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class BooleanConverter {
    public final int booleanToInteger(boolean condition) {
        return condition ? 1 : 0;
    }

    public final boolean integerToBoolean(int value) {
        return value != 0;
    }
}
