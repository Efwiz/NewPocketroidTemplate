package org.catrobat.catroid.utils;

import java.math.BigDecimal;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.content.Project$$ExternalSyntheticBackportWithForwarding0;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/FormatNumberUtil.class
  classes14.dex
 */
/* JADX INFO: compiled from: FormatNumberUtil.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0004H\u0007¨\u0006\u0006"}, d2 = {"Lorg/catrobat/catroid/utils/FormatNumberUtil;", "", "()V", "cutTrailingZeros", "", "number", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FormatNumberUtil {
    public static final FormatNumberUtil INSTANCE = new FormatNumberUtil();

    private FormatNumberUtil() {
    }

    @JvmStatic
    public static final String cutTrailingZeros(String number) {
        Intrinsics.checkNotNullParameter(number, "number");
        try {
            String str = number;
            int length = str.length() - 1;
            int i = 0;
            boolean z = false;
            while (i <= length) {
                boolean z2 = Intrinsics.compare((int) str.charAt(!z ? i : length), 32) <= 0;
                if (z) {
                    if (!z2) {
                        break;
                    }
                    length--;
                } else if (z2) {
                    i++;
                } else {
                    z = true;
                }
            }
            BigDecimal ZERO = Project$$ExternalSyntheticBackportWithForwarding0.m(new BigDecimal(str.subSequence(i, length + 1).toString()));
            Intrinsics.checkNotNullExpressionValue(ZERO, "stripTrailingZeros(...)");
            if (ZERO.compareTo(BigDecimal.ZERO) == 0) {
                ZERO = BigDecimal.ZERO;
                Intrinsics.checkNotNullExpressionValue(ZERO, "ZERO");
            }
            String plainString = ZERO.toPlainString();
            Intrinsics.checkNotNull(plainString);
            return plainString;
        } catch (NumberFormatException unused) {
            return number;
        }
    }
}
