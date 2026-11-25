package org.catrobat.catroid.utils.lunoscript;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/lunoscript/ReturnSignal.class
  classes14.dex
 */
/* JADX INFO: compiled from: LunoExceptions.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00060\u0001j\u0002`\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\b"}, d2 = {"Lorg/catrobat/catroid/utils/lunoscript/ReturnSignal;", "Ljava/lang/RuntimeException;", "Lkotlin/RuntimeException;", "value", "Lorg/catrobat/catroid/utils/lunoscript/LunoValue;", "(Lorg/catrobat/catroid/utils/lunoscript/LunoValue;)V", "getValue", "()Lorg/catrobat/catroid/utils/lunoscript/LunoValue;", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ReturnSignal extends RuntimeException {
    private final LunoValue value;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ReturnSignal(LunoValue value) {
        super(null, null, false, false);
        Intrinsics.checkNotNullParameter(value, "value");
        this.value = value;
    }

    public final LunoValue getValue() {
        return this.value;
    }
}
