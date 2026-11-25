package org.catrobat.catroid.content.actions;

import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.formulaeditor.UserList;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/AssertUserListAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: AssertUserListAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0016J \u0010\u0016\u001a\u00020\r2\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001aH\u0002J\b\u0010\u001c\u001a\u00020\u001dH\u0002R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0006\"\u0004\b\u000b\u0010\bR\u001a\u0010\f\u001a\u00020\rX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011¨\u0006\u001e"}, d2 = {"Lorg/catrobat/catroid/content/actions/AssertUserListAction;", "Lorg/catrobat/catroid/content/actions/AssertAction;", "()V", "actualUserList", "Lorg/catrobat/catroid/formulaeditor/UserList;", "getActualUserList", "()Lorg/catrobat/catroid/formulaeditor/UserList;", "setActualUserList", "(Lorg/catrobat/catroid/formulaeditor/UserList;)V", "expectedUserList", "getExpectedUserList", "setExpectedUserList", CrashHianalyticsData.MESSAGE, "", "getMessage", "()Ljava/lang/String;", "setMessage", "(Ljava/lang/String;)V", "act", "", "delta", "", "formattedAssertError", "listPosition", "", "actual", "", "expected", "validateLists", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class AssertUserListAction extends AssertAction {
    private UserList actualUserList;
    private UserList expectedUserList;
    private String message = "";

    public final UserList getActualUserList() {
        return this.actualUserList;
    }

    public final void setActualUserList(UserList userList) {
        this.actualUserList = userList;
    }

    public final UserList getExpectedUserList() {
        return this.expectedUserList;
    }

    public final void setExpectedUserList(UserList userList) {
        this.expectedUserList = userList;
    }

    public final String getMessage() {
        return this.message;
    }

    public final void setMessage(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.message = str;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Action
    public boolean act(float delta) {
        setAssertTitle("\nAssertUserListError\n");
        this.message = "";
        if (this.actualUserList == null) {
            failWith("Actual list is null");
            return false;
        }
        if (this.expectedUserList == null) {
            failWith("Expected list is null");
            return false;
        }
        validateLists();
        if (this.message.length() == 0) {
            return true;
        }
        failWith(this.message);
        return false;
    }

    private final void validateLists() {
        UserList userList = this.actualUserList;
        Intrinsics.checkNotNull(userList);
        List<Object> value = userList.getValue();
        UserList userList2 = this.expectedUserList;
        Intrinsics.checkNotNull(userList2);
        List<Object> value2 = userList2.getValue();
        if (value.size() != value2.size()) {
            this.message = "The number of list elements are not equal\nexpected: " + value2.size() + " element/s\nactual:   " + value.size() + " element/s\n";
        }
        int iMin = Math.min(value2.size() - 1, value.size() - 1);
        if (iMin < 0) {
            return;
        }
        int i = 0;
        while (true) {
            if (!equalValues(value.get(i).toString(), value2.get(i).toString())) {
                String str = this.message;
                Object obj = value.get(i);
                Intrinsics.checkNotNullExpressionValue(obj, "get(...)");
                Object obj2 = value2.get(i);
                Intrinsics.checkNotNullExpressionValue(obj2, "get(...)");
                this.message = str + formattedAssertError(i, obj, obj2);
            }
            if (i == iMin) {
                return;
            } else {
                i++;
            }
        }
    }

    private final String formattedAssertError(int listPosition, Object actual, Object expected) {
        return "position: " + (listPosition + 1) + "\nexpected: <" + expected + ">\nactual:   <" + actual + ">\ndeviation: " + generateIndicator(actual, expected) + "\n\n";
    }
}
