package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.Action;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.bytebuddy.implementation.MethodDelegation;
import org.catrobat.catroid.common.ParameterizedData;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.formulaeditor.UserList;
import org.catrobat.catroid.formulaeditor.UserVariable;
import org.catrobat.catroid.stage.StageActivity;
import org.catrobat.catroid.stage.TestResult;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/RepeatParameterizedAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: RepeatParameterizedAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010!\u001a\u00020\u00062\u0006\u0010\"\u001a\u00020#H\u0014J\u0010\u0010$\u001a\u00020%2\u0006\u0010&\u001a\u00020\u0004H\u0002J\b\u0010'\u001a\u00020\u0004H\u0002J\b\u0010(\u001a\u00020\u0006H\u0002J\b\u0010)\u001a\u00020%H\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u001c\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR,\u0010\r\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u00110\u000f0\u000eX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R\u001a\u0010\u0016\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u001aR\u001c\u0010\u001b\u001a\u0004\u0018\u00010\u001cX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001d\u0010\u001e\"\u0004\b\u001f\u0010 ¨\u0006*"}, d2 = {"Lorg/catrobat/catroid/content/actions/RepeatParameterizedAction;", "Lorg/catrobat/catroid/content/actions/LoopAction;", "()V", "assertTitle", "", "isCurrentLoopInitialized", "", "parameterizedData", "Lorg/catrobat/catroid/common/ParameterizedData;", "getParameterizedData", "()Lorg/catrobat/catroid/common/ParameterizedData;", "setParameterizedData", "(Lorg/catrobat/catroid/common/ParameterizedData;)V", "parameters", "", "Lkotlin/Pair;", "Lorg/catrobat/catroid/formulaeditor/UserList;", "Lorg/catrobat/catroid/formulaeditor/UserVariable;", "getParameters", "()Ljava/util/List;", "setParameters", "(Ljava/util/List;)V", "position", "getPosition", "()Ljava/lang/String;", "setPosition", "(Ljava/lang/String;)V", "sprite", "Lorg/catrobat/catroid/content/Sprite;", "getSprite", "()Lorg/catrobat/catroid/content/Sprite;", "setSprite", "(Lorg/catrobat/catroid/content/Sprite;)V", MethodDelegation.ImplementationDelegate.FIELD_NAME_PREFIX, "delta", "", "fail", "", "error", "formattedPosition", "initParameter", "restart", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class RepeatParameterizedAction extends LoopAction {
    private boolean isCurrentLoopInitialized;
    private ParameterizedData parameterizedData;
    private Sprite sprite;
    private String assertTitle = "ParameterizedInitialisationError";
    private String position = "";
    private List<? extends Pair<? extends UserList, ? extends UserVariable>> parameters = CollectionsKt.emptyList();

    public final String getPosition() {
        return this.position;
    }

    public final void setPosition(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.position = str;
    }

    public final Sprite getSprite() {
        return this.sprite;
    }

    public final void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public final ParameterizedData getParameterizedData() {
        return this.parameterizedData;
    }

    public final void setParameterizedData(ParameterizedData parameterizedData) {
        this.parameterizedData = parameterizedData;
    }

    public final List<Pair<UserList, UserVariable>> getParameters() {
        return this.parameters;
    }

    public final void setParameters(List<? extends Pair<? extends UserList, ? extends UserVariable>> list) {
        Intrinsics.checkNotNullParameter(list, "<set-?>");
        this.parameters = list;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.RepeatAction, com.badlogic.gdx.scenes.scene2d.actions.DelegateAction
    protected boolean delegate(float delta) {
        List<? extends Pair<? extends UserList, ? extends UserVariable>> list = this.parameters;
        if (list == null || list.isEmpty()) {
            fail("Input was not selected");
            return true;
        }
        if (!this.isCurrentLoopInitialized) {
            ParameterizedData parameterizedData = this.parameterizedData;
            int currentPosition = parameterizedData != null ? parameterizedData.getCurrentPosition() : 1;
            ParameterizedData parameterizedData2 = this.parameterizedData;
            if (currentPosition >= (parameterizedData2 != null ? parameterizedData2.getListSize() : 0) || initParameter()) {
                return true;
            }
            setCurrentTime(0.0f);
            this.isCurrentLoopInitialized = true;
        }
        setCurrentTime(getCurrentTime() + delta);
        if (this.action.act(delta) && !isLoopDelayNeeded()) {
            ParameterizedData parameterizedData3 = this.parameterizedData;
            int currentPosition2 = parameterizedData3 != null ? parameterizedData3.getCurrentPosition() : 1;
            ParameterizedData parameterizedData4 = this.parameterizedData;
            if (currentPosition2 >= (parameterizedData4 != null ? parameterizedData4.getListSize() : 0)) {
                return true;
            }
            this.isCurrentLoopInitialized = false;
            Action action = this.action;
            if (action != null) {
                action.restart();
            }
        }
        return false;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.RepeatAction, com.badlogic.gdx.scenes.scene2d.actions.DelegateAction, com.badlogic.gdx.scenes.scene2d.Action
    public void restart() {
        this.isCurrentLoopInitialized = false;
        ParameterizedData parameterizedData = this.parameterizedData;
        if (parameterizedData != null) {
            parameterizedData.reset();
        }
        super.restart();
    }

    private final void fail(String error) {
        StageActivity.finishTestWithResult(new TestResult(formattedPosition() + "\n\n" + this.assertTitle + "\n" + error, TestResult.STAGE_ACTIVITY_TEST_FAIL));
    }

    private final boolean initParameter() {
        ParameterizedData parameterizedData = this.parameterizedData;
        if (parameterizedData == null) {
            return true;
        }
        parameterizedData.setCurrentParameters("[" + (parameterizedData.getCurrentPosition() + 1) + "] ");
        for (Pair<? extends UserList, ? extends UserVariable> pair : this.parameters) {
            UserList userListComponent1 = pair.component1();
            UserVariable userVariableComponent2 = pair.component2();
            List<Object> value = userListComponent1.getValue();
            if (value.size() <= parameterizedData.getCurrentPosition()) {
                fail("Input list is missing elements\nFailed Tests:\n" + ((Object) parameterizedData.getFailMessages()) + "\n\nSucceeded Tests:\n" + ((Object) parameterizedData.getSuccessMessages()));
                return true;
            }
            userVariableComponent2.setValue(value.get(parameterizedData.getCurrentPosition()));
            parameterizedData.setCurrentParameters(parameterizedData.getCurrentParameters() + userVariableComponent2.getName() + " = " + userVariableComponent2.getValue() + " | ");
        }
        parameterizedData.setCurrentParameters(StringsKt.removeSuffix(parameterizedData.getCurrentParameters(), (CharSequence) " | "));
        return false;
    }

    private final String formattedPosition() {
        Sprite sprite = this.sprite;
        return "on sprite " + (sprite != null ? sprite.getName() : null) + "\n" + this.position;
    }
}
