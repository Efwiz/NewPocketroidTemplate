package org.catrobat.catroid.content.actions;

import java.util.List;
import kotlin.Metadata;
import org.catrobat.catroid.common.ParameterizedData;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.UserList;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/ParameterizedAssertAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: ParameterizedAssertAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018H\u0016J\u0018\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001cH\u0002J\u001c\u0010\u001e\u001a\u00020\u001a2\b\u0010\u001b\u001a\u0004\u0018\u00010\u001c2\b\u0010\u001d\u001a\u0004\u0018\u00010\u001cH\u0002R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001c\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014¨\u0006\u001f"}, d2 = {"Lorg/catrobat/catroid/content/actions/ParameterizedAssertAction;", "Lorg/catrobat/catroid/content/actions/AssertAction;", "()V", "actualFormula", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getActualFormula", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setActualFormula", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "expectedList", "Lorg/catrobat/catroid/formulaeditor/UserList;", "getExpectedList", "()Lorg/catrobat/catroid/formulaeditor/UserList;", "setExpectedList", "(Lorg/catrobat/catroid/formulaeditor/UserList;)V", "parameterizedData", "Lorg/catrobat/catroid/common/ParameterizedData;", "getParameterizedData", "()Lorg/catrobat/catroid/common/ParameterizedData;", "setParameterizedData", "(Lorg/catrobat/catroid/common/ParameterizedData;)V", "act", "", "delta", "", "formattedAssertEqualsError", "", "actual", "", "expected", "formattedSuccessMessage", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ParameterizedAssertAction extends AssertAction {
    private Formula actualFormula;
    private UserList expectedList;
    private ParameterizedData parameterizedData;

    public ParameterizedAssertAction() {
        setAssertTitle("\nParameterizedAssertError\n");
    }

    public final Formula getActualFormula() {
        return this.actualFormula;
    }

    public final void setActualFormula(Formula formula) {
        this.actualFormula = formula;
    }

    public final UserList getExpectedList() {
        return this.expectedList;
    }

    public final void setExpectedList(UserList userList) {
        this.expectedList = userList;
    }

    public final ParameterizedData getParameterizedData() {
        return this.parameterizedData;
    }

    public final void setParameterizedData(ParameterizedData parameterizedData) {
        this.parameterizedData = parameterizedData;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Action
    public boolean act(float delta) {
        List<Object> value;
        List<Object> value2;
        List<Object> value3;
        ParameterizedData parameterizedData = this.parameterizedData;
        int size = 0;
        if (parameterizedData != null) {
            UserList userList = this.expectedList;
            parameterizedData.setListSize((userList == null || (value3 = userList.getValue()) == null) ? 0 : value3.size());
        }
        Formula formula = this.actualFormula;
        if (formula == null) {
            failWith("Actual is null");
            return false;
        }
        if (this.expectedList == null) {
            failWith("Expected is null");
            return false;
        }
        Object obj = null;
        String strValueOf = String.valueOf(formula != null ? formula.interpretObject(getScope()) : null);
        UserList userList2 = this.expectedList;
        if (userList2 != null && (value2 = userList2.getValue()) != null) {
            ParameterizedData parameterizedData2 = this.parameterizedData;
            obj = value2.get(parameterizedData2 != null ? parameterizedData2.getCurrentPosition() : 0);
        }
        if (obj == null) {
            obj = "null";
        }
        ParameterizedData parameterizedData3 = this.parameterizedData;
        if (parameterizedData3 != null) {
            if (!equalValues(strValueOf, obj.toString())) {
                parameterizedData3.getFailMessages().append("\n" + parameterizedData3.getCurrentParameters() + "\n").append(formattedAssertEqualsError(strValueOf, obj));
            } else {
                parameterizedData3.getSuccessMessages().append("\n" + parameterizedData3.getCurrentParameters() + "\n").append(formattedSuccessMessage(strValueOf, obj));
            }
            parameterizedData3.setCurrentPosition(parameterizedData3.getCurrentPosition() + 1);
            parameterizedData3.setCurrentParameters("");
            if (parameterizedData3.getFailMessages().length() > 0) {
                int currentPosition = parameterizedData3.getCurrentPosition();
                UserList userList3 = this.expectedList;
                if (userList3 != null && (value = userList3.getValue()) != null) {
                    size = value.size();
                }
                if (currentPosition >= size) {
                    failWith("Failed Tests:\n" + ((Object) parameterizedData3.getFailMessages()) + "\n\nSucceeded Tests:\n" + ((Object) parameterizedData3.getSuccessMessages()));
                }
            }
        }
        return true;
    }

    private final String formattedAssertEqualsError(Object actual, Object expected) {
        return "expected: <" + expected + ">\nactual:   <" + actual + ">\ndeviation: " + generateIndicator(actual, expected) + "\n";
    }

    private final String formattedSuccessMessage(Object actual, Object expected) {
        return actual + " == " + expected + "\n";
    }
}
