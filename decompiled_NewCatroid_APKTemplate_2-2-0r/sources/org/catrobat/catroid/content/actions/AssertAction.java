package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.stage.StageActivity;
import org.catrobat.catroid.stage.TestResult;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/AssertAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: AssertAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\u0010\r\n\u0000\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00042\u0006\u0010\u0015\u001a\u00020\u0004H\u0004J\u0010\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0004H\u0004J\b\u0010\u0019\u001a\u00020\u0004H\u0002J\u0018\u0010\u001a\u001a\u00020\u00042\u0006\u0010\u0014\u001a\u00020\u001b2\u0006\u0010\u0015\u001a\u00020\u001bH\u0004J\u001c\u0010\u001c\u001a\u00020\u001d2\b\u0010\u0014\u001a\u0004\u0018\u00010\u001e2\b\u0010\u0015\u001a\u0004\u0018\u00010\u001eH\u0002R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0006\"\u0004\b\u000b\u0010\bR\u001c\u0010\f\u001a\u0004\u0018\u00010\rX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011¨\u0006\u001f"}, d2 = {"Lorg/catrobat/catroid/content/actions/AssertAction;", "Lcom/badlogic/gdx/scenes/scene2d/Action;", "()V", "assertTitle", "", "getAssertTitle", "()Ljava/lang/String;", "setAssertTitle", "(Ljava/lang/String;)V", "position", "getPosition", "setPosition", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", "equalValues", "", "actual", "expected", "failWith", "", CrashHianalyticsData.MESSAGE, "formattedPosition", "generateIndicator", "", "indexOfDifference", "", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class AssertAction extends Action {
    private String assertTitle = "\nAssertError\n";
    private String position;
    private Scope scope;

    public final String getPosition() {
        return this.position;
    }

    public final void setPosition(String str) {
        this.position = str;
    }

    public final Scope getScope() {
        return this.scope;
    }

    public final void setScope(Scope scope) {
        this.scope = scope;
    }

    public final String getAssertTitle() {
        return this.assertTitle;
    }

    public final void setAssertTitle(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.assertTitle = str;
    }

    protected final void failWith(String message) {
        Intrinsics.checkNotNullParameter(message, "message");
        StageActivity.finishTestWithResult(new TestResult(formattedPosition() + this.assertTitle + message, TestResult.STAGE_ACTIVITY_TEST_FAIL));
    }

    protected final boolean equalValues(String actual, String expected) {
        Intrinsics.checkNotNullParameter(actual, "actual");
        Intrinsics.checkNotNullParameter(expected, "expected");
        try {
            return Intrinsics.areEqual(actual, expected) || Double.parseDouble(actual) == Double.parseDouble(expected);
        } catch (NumberFormatException unused) {
            return false;
        }
    }

    private final int indexOfDifference(CharSequence actual, CharSequence expected) {
        int i = 0;
        if (actual != null && expected != null) {
            while (i < actual.length() && i < expected.length() && actual.charAt(i) == expected.charAt(i)) {
                i++;
            }
        }
        return i;
    }

    protected final String generateIndicator(Object actual, Object expected) {
        Intrinsics.checkNotNullParameter(actual, "actual");
        Intrinsics.checkNotNullParameter(expected, "expected");
        return StringsKt.replace$default(new String(new char[indexOfDifference(actual.toString(), expected.toString())]), (char) 0, '-', false, 4, (Object) null) + "^";
    }

    private final String formattedPosition() {
        Sprite sprite;
        Scope scope = this.scope;
        return "on sprite \"" + ((scope == null || (sprite = scope.getSprite()) == null) ? null : sprite.getName()) + "\"\n" + this.position + "\n";
    }
}
