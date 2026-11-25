package org.catrobat.catroid.content.actions;

import android.util.Log;
import com.badlogic.gdx.scenes.scene2d.Action;
import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.formulaeditor.UserVariable;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/TryCatchFinallyAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: TryCatchFinallyAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001:\u0001\u001dB\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001aH\u0016J\b\u0010\u001b\u001a\u00020\u001cH\u0016R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001c\u0010\u000f\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0006\"\u0004\b\u0011\u0010\bR\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u000e¢\u0006\u0002\n\u0000R\u001c\u0010\u0014\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u0006\"\u0004\b\u0016\u0010\b¨\u0006\u001e"}, d2 = {"Lorg/catrobat/catroid/content/actions/TryCatchFinallyAction;", "Lcom/badlogic/gdx/scenes/scene2d/Action;", "()V", "catchSequence", "Lorg/catrobat/catroid/content/actions/ScriptSequenceAction;", "getCatchSequence", "()Lorg/catrobat/catroid/content/actions/ScriptSequenceAction;", "setCatchSequence", "(Lorg/catrobat/catroid/content/actions/ScriptSequenceAction;)V", "errorVariable", "Lorg/catrobat/catroid/formulaeditor/UserVariable;", "getErrorVariable", "()Lorg/catrobat/catroid/formulaeditor/UserVariable;", "setErrorVariable", "(Lorg/catrobat/catroid/formulaeditor/UserVariable;)V", "finallySequence", "getFinallySequence", "setFinallySequence", "state", "Lorg/catrobat/catroid/content/actions/TryCatchFinallyAction$State;", "trySequence", "getTrySequence", "setTrySequence", "act", "", "delta", "", "restart", "", "State", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class TryCatchFinallyAction extends Action {
    private ScriptSequenceAction catchSequence;
    private UserVariable errorVariable;
    private ScriptSequenceAction finallySequence;
    private State state = State.READY;
    private ScriptSequenceAction trySequence;

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/TryCatchFinallyAction$State.class
      classes14.dex
     */
    /* JADX INFO: compiled from: TryCatchFinallyAction.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0007\b\u0082\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007¨\u0006\b"}, d2 = {"Lorg/catrobat/catroid/content/actions/TryCatchFinallyAction$State;", "", "(Ljava/lang/String;I)V", "READY", "TRYING", "CATCHING", "FINALLY", "DONE", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private enum State {
        READY,
        TRYING,
        CATCHING,
        FINALLY,
        DONE;

        private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

        public static EnumEntries<State> getEntries() {
            return $ENTRIES;
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/TryCatchFinallyAction$WhenMappings.class
      classes14.dex
     */
    /* JADX INFO: compiled from: TryCatchFinallyAction.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[State.values().length];
            try {
                iArr[State.READY.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[State.TRYING.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[State.CATCHING.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[State.FINALLY.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[State.DONE.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public final ScriptSequenceAction getTrySequence() {
        return this.trySequence;
    }

    public final void setTrySequence(ScriptSequenceAction scriptSequenceAction) {
        this.trySequence = scriptSequenceAction;
    }

    public final ScriptSequenceAction getCatchSequence() {
        return this.catchSequence;
    }

    public final void setCatchSequence(ScriptSequenceAction scriptSequenceAction) {
        this.catchSequence = scriptSequenceAction;
    }

    public final ScriptSequenceAction getFinallySequence() {
        return this.finallySequence;
    }

    public final void setFinallySequence(ScriptSequenceAction scriptSequenceAction) {
        this.finallySequence = scriptSequenceAction;
    }

    public final UserVariable getErrorVariable() {
        return this.errorVariable;
    }

    public final void setErrorVariable(UserVariable userVariable) {
        this.errorVariable = userVariable;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Action
    public boolean act(float delta) {
        while (true) {
            int i = WhenMappings.$EnumSwitchMapping$0[this.state.ordinal()];
            if (i == 1) {
                this.state = State.TRYING;
            } else if (i == 2) {
                try {
                    ScriptSequenceAction scriptSequenceAction = this.trySequence;
                    if (scriptSequenceAction == null || !scriptSequenceAction.act(delta)) {
                        return false;
                    }
                    this.state = State.FINALLY;
                } catch (Exception e) {
                    Log.w("TryCatchFinallyAction", "Exception caught in TRY block: " + e.getMessage());
                    UserVariable userVariable = this.errorVariable;
                    if (userVariable != null) {
                        String message = e.getMessage();
                        if (message == null) {
                            message = "An unknown error occurred";
                        }
                        userVariable.setValue(message);
                    }
                    this.state = State.CATCHING;
                }
            } else if (i == 3) {
                ScriptSequenceAction scriptSequenceAction2 = this.catchSequence;
                if (scriptSequenceAction2 != null) {
                    Intrinsics.checkNotNull(scriptSequenceAction2);
                    if (scriptSequenceAction2.getActions().size != 0) {
                        ScriptSequenceAction scriptSequenceAction3 = this.catchSequence;
                        if (scriptSequenceAction3 == null || !scriptSequenceAction3.act(delta)) {
                            return false;
                        }
                        this.state = State.FINALLY;
                    }
                }
                this.state = State.FINALLY;
            } else {
                if (i == 4) {
                    ScriptSequenceAction scriptSequenceAction4 = this.finallySequence;
                    if (scriptSequenceAction4 != null) {
                        Intrinsics.checkNotNull(scriptSequenceAction4);
                        if (scriptSequenceAction4.getActions().size != 0) {
                            ScriptSequenceAction scriptSequenceAction5 = this.finallySequence;
                            if (scriptSequenceAction5 == null || !scriptSequenceAction5.act(delta)) {
                                return false;
                            }
                            this.state = State.DONE;
                            return true;
                        }
                    }
                    this.state = State.DONE;
                    return true;
                }
                if (i == 5) {
                    return true;
                }
            }
        }
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Action
    public void restart() {
        this.state = State.READY;
        ScriptSequenceAction scriptSequenceAction = this.trySequence;
        if (scriptSequenceAction != null) {
            scriptSequenceAction.restart();
        }
        ScriptSequenceAction scriptSequenceAction2 = this.catchSequence;
        if (scriptSequenceAction2 != null) {
            scriptSequenceAction2.restart();
        }
        ScriptSequenceAction scriptSequenceAction3 = this.finallySequence;
        if (scriptSequenceAction3 != null) {
            scriptSequenceAction3.restart();
        }
        super.restart();
    }
}
