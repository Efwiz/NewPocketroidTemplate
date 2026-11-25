package org.catrobat.catroid.sensing;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/sensing/ConditionMatcherRunner.class
  classes14.dex
 */
/* JADX INFO: compiled from: ConditionMatcherRunner.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0016\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\nR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\f"}, d2 = {"Lorg/catrobat/catroid/sensing/ConditionMatcherRunner;", "", "matcher", "Lorg/catrobat/catroid/sensing/ConditionMatcher;", "(Lorg/catrobat/catroid/sensing/ConditionMatcher;)V", "getMatcher", "()Lorg/catrobat/catroid/sensing/ConditionMatcher;", "match", "", "width", "", "height", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ConditionMatcherRunner {
    private final ConditionMatcher matcher;

    public ConditionMatcherRunner(ConditionMatcher matcher) {
        Intrinsics.checkNotNullParameter(matcher, "matcher");
        this.matcher = matcher;
    }

    public final ConditionMatcher getMatcher() {
        return this.matcher;
    }

    public final boolean match(int width, int height) {
        for (int i = 0; i < width; i++) {
            for (int i2 = 0; i2 < height; i2++) {
                if (this.matcher.matches(i, i2)) {
                    return true;
                }
            }
        }
        return false;
    }
}
