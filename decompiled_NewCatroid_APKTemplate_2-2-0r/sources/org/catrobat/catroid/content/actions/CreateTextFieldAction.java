package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.Action;
import java.io.File;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.content.Project;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.UserVariable;
import org.catrobat.catroid.stage.StageActivity;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/CreateTextFieldAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: CreateTextFieldAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u000b\n\u0002\b!\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010G\u001a\u00020\u00102\u0006\u0010H\u001a\u00020IH\u0016J\b\u0010J\u001a\u00020KH\u0016J\b\u0010L\u001a\u00020KH\u0016J\b\u0010M\u001a\u00020KH\u0002R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0006\"\u0004\b\u000b\u0010\bR\u001c\u0010\f\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u0006\"\u0004\b\u000e\u0010\bR\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u001c\u0010\u0011\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0006\"\u0004\b\u0013\u0010\bR\u001c\u0010\u0014\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u0006\"\u0004\b\u0016\u0010\bR\u001c\u0010\u0017\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u0006\"\u0004\b\u0019\u0010\bR\u001c\u0010\u001a\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001b\u0010\u0006\"\u0004\b\u001c\u0010\bR\u001c\u0010\u001d\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001e\u0010\u0006\"\u0004\b\u001f\u0010\bR\u001c\u0010 \u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b!\u0010\u0006\"\u0004\b\"\u0010\bR\u001c\u0010#\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b#\u0010\u0006\"\u0004\b$\u0010\bR\u001c\u0010%\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b&\u0010\u0006\"\u0004\b'\u0010\bR\u001c\u0010(\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b)\u0010\u0006\"\u0004\b*\u0010\bR\u001c\u0010+\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b,\u0010\u0006\"\u0004\b-\u0010\bR\u001c\u0010.\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b/\u0010\u0006\"\u0004\b0\u0010\bR\u001c\u00101\u001a\u0004\u0018\u000102X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b3\u00104\"\u0004\b5\u00106R\u000e\u00107\u001a\u00020\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u001c\u00108\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b9\u0010\u0006\"\u0004\b:\u0010\bR\u001c\u0010;\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b<\u0010\u0006\"\u0004\b=\u0010\bR\u001c\u0010>\u001a\u0004\u0018\u00010?X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b@\u0010A\"\u0004\bB\u0010CR\u001c\u0010D\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bE\u0010\u0006\"\u0004\bF\u0010\b¨\u0006N"}, d2 = {"Lorg/catrobat/catroid/content/actions/CreateTextFieldAction;", "Lcom/badlogic/gdx/scenes/scene2d/Action;", "()V", "alignment_f", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getAlignment_f", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setAlignment_f", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "bg_color", "getBg_color", "setBg_color", "corner_radius", "getCorner_radius", "setCorner_radius", "finished", "", "font_path", "getFont_path", "setFont_path", "height", "getHeight", "setHeight", "hint_color", "getHint_color", "setHint_color", "hint_text", "getHint_text", "setHint_text", "initialText", "getInitialText", "setInitialText", "input_type", "getInput_type", "setInput_type", "is_password", "set_password", "max_length", "getMax_length", "setMax_length", "name", "getName", "setName", "posX", "getPosX", "setPosX", "posY", "getPosY", "setPosY", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", "started", "text_color", "getText_color", "setText_color", "text_size", "getText_size", "setText_size", "variable", "Lorg/catrobat/catroid/formulaeditor/UserVariable;", "getVariable", "()Lorg/catrobat/catroid/formulaeditor/UserVariable;", "setVariable", "(Lorg/catrobat/catroid/formulaeditor/UserVariable;)V", "width", "getWidth", "setWidth", "act", "delta", "", "reset", "", "restart", "runAsyncCreate", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CreateTextFieldAction extends Action {
    private Formula alignment_f;
    private Formula bg_color;
    private Formula corner_radius;
    private volatile boolean finished;
    private Formula font_path;
    private Formula height;
    private Formula hint_color;
    private Formula hint_text;
    private Formula initialText;
    private Formula input_type;
    private Formula is_password;
    private Formula max_length;
    private Formula name;
    private Formula posX;
    private Formula posY;
    private Scope scope;
    private boolean started;
    private Formula text_color;
    private Formula text_size;
    private UserVariable variable;
    private Formula width;

    public final Scope getScope() {
        return this.scope;
    }

    public final void setScope(Scope scope) {
        this.scope = scope;
    }

    public final Formula getName() {
        return this.name;
    }

    public final void setName(Formula formula) {
        this.name = formula;
    }

    public final UserVariable getVariable() {
        return this.variable;
    }

    public final void setVariable(UserVariable userVariable) {
        this.variable = userVariable;
    }

    public final Formula getPosX() {
        return this.posX;
    }

    public final void setPosX(Formula formula) {
        this.posX = formula;
    }

    public final Formula getPosY() {
        return this.posY;
    }

    public final void setPosY(Formula formula) {
        this.posY = formula;
    }

    public final Formula getWidth() {
        return this.width;
    }

    public final void setWidth(Formula formula) {
        this.width = formula;
    }

    public final Formula getHeight() {
        return this.height;
    }

    public final void setHeight(Formula formula) {
        this.height = formula;
    }

    public final Formula getInitialText() {
        return this.initialText;
    }

    public final void setInitialText(Formula formula) {
        this.initialText = formula;
    }

    public final Formula getText_size() {
        return this.text_size;
    }

    public final void setText_size(Formula formula) {
        this.text_size = formula;
    }

    public final Formula getText_color() {
        return this.text_color;
    }

    public final void setText_color(Formula formula) {
        this.text_color = formula;
    }

    public final Formula getBg_color() {
        return this.bg_color;
    }

    public final void setBg_color(Formula formula) {
        this.bg_color = formula;
    }

    public final Formula getHint_text() {
        return this.hint_text;
    }

    public final void setHint_text(Formula formula) {
        this.hint_text = formula;
    }

    public final Formula getHint_color() {
        return this.hint_color;
    }

    public final void setHint_color(Formula formula) {
        this.hint_color = formula;
    }

    public final Formula getAlignment_f() {
        return this.alignment_f;
    }

    public final void setAlignment_f(Formula formula) {
        this.alignment_f = formula;
    }

    public final Formula getFont_path() {
        return this.font_path;
    }

    public final void setFont_path(Formula formula) {
        this.font_path = formula;
    }

    public final Formula getInput_type() {
        return this.input_type;
    }

    public final void setInput_type(Formula formula) {
        this.input_type = formula;
    }

    /* JADX INFO: renamed from: is_password, reason: from getter */
    public final Formula getIs_password() {
        return this.is_password;
    }

    public final void set_password(Formula formula) {
        this.is_password = formula;
    }

    public final Formula getMax_length() {
        return this.max_length;
    }

    public final void setMax_length(Formula formula) {
        this.max_length = formula;
    }

    public final Formula getCorner_radius() {
        return this.corner_radius;
    }

    public final void setCorner_radius(Formula formula) {
        this.corner_radius = formula;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Action
    public boolean act(float delta) {
        if (!this.started) {
            this.started = true;
            runAsyncCreate();
        }
        return this.finished;
    }

    private final void runAsyncCreate() {
        String strInterpretString;
        String strInterpretString2;
        String strInterpretString3;
        String strInterpretString4;
        File file;
        WeakReference<StageActivity> weakReference = StageActivity.activeStageActivity;
        String absolutePath = null;
        StageActivity stageActivity = weakReference != null ? weakReference.get() : null;
        if (stageActivity == null) {
            this.finished = true;
            return;
        }
        Formula formula = this.name;
        String strInterpretString5 = formula != null ? formula.interpretString(this.scope) : null;
        String str = strInterpretString5 == null ? "" : strInterpretString5;
        Formula formula2 = this.posX;
        Integer numInterpretInteger = formula2 != null ? formula2.interpretInteger(this.scope) : null;
        final int iIntValue = numInterpretInteger == null ? 0 : numInterpretInteger.intValue();
        Formula formula3 = this.posY;
        Integer numInterpretInteger2 = formula3 != null ? formula3.interpretInteger(this.scope) : null;
        final int iIntValue2 = numInterpretInteger2 == null ? 0 : numInterpretInteger2.intValue();
        Formula formula4 = this.width;
        Integer numInterpretInteger3 = formula4 != null ? formula4.interpretInteger(this.scope) : null;
        final int iIntValue3 = numInterpretInteger3 == null ? 0 : numInterpretInteger3.intValue();
        Formula formula5 = this.height;
        Integer numInterpretInteger4 = formula5 != null ? formula5.interpretInteger(this.scope) : null;
        final int iIntValue4 = numInterpretInteger4 == null ? 0 : numInterpretInteger4.intValue();
        Formula formula6 = this.initialText;
        String strInterpretString6 = formula6 != null ? formula6.interpretString(this.scope) : null;
        String str2 = strInterpretString6 == null ? "" : strInterpretString6;
        if (str.length() == 0) {
            this.finished = true;
            return;
        }
        final HashMap map = new HashMap();
        HashMap map2 = map;
        Formula formula7 = this.text_size;
        String strInterpretString7 = formula7 != null ? formula7.interpretString(this.scope) : null;
        if (strInterpretString7 == null) {
            strInterpretString7 = "22";
        }
        map2.put(StageActivity.STYLE_TEXT_SIZE, strInterpretString7);
        Formula formula8 = this.text_color;
        String strInterpretString8 = formula8 != null ? formula8.interpretString(this.scope) : null;
        if (strInterpretString8 == null) {
            strInterpretString8 = "#FFFFFF";
        }
        map2.put(StageActivity.STYLE_TEXT_COLOR, strInterpretString8);
        Formula formula9 = this.bg_color;
        String strInterpretString9 = formula9 != null ? formula9.interpretString(this.scope) : null;
        if (strInterpretString9 == null) {
            strInterpretString9 = "#88000000";
        }
        map2.put(StageActivity.STYLE_BACKGROUND_COLOR, strInterpretString9);
        Formula formula10 = this.hint_text;
        String strInterpretString10 = formula10 != null ? formula10.interpretString(this.scope) : null;
        if (strInterpretString10 == null) {
            strInterpretString10 = "Enter value...";
        }
        map2.put(StageActivity.STYLE_HINT_TEXT, strInterpretString10);
        Formula formula11 = this.hint_color;
        String strInterpretString11 = formula11 != null ? formula11.interpretString(this.scope) : null;
        if (strInterpretString11 == null) {
            strInterpretString11 = "#CCCCCC";
        }
        map2.put(StageActivity.STYLE_HINT_TEXT_COLOR, strInterpretString11);
        Formula formula12 = this.alignment_f;
        String strInterpretString12 = formula12 != null ? formula12.interpretString(this.scope) : null;
        if (strInterpretString12 == null) {
            strInterpretString12 = "left";
        }
        map2.put(StageActivity.STYLE_TEXT_ALIGNMENT, strInterpretString12);
        Formula formula13 = this.font_path;
        if (formula13 != null && (strInterpretString4 = formula13.interpretString(this.scope)) != null) {
            Scope scope = this.scope;
            Intrinsics.checkNotNull(scope);
            Project project = scope.getProject();
            if (project != null && (file = project.getFile(strInterpretString4)) != null) {
                absolutePath = file.getAbsolutePath();
            }
            if (absolutePath != null) {
                Intrinsics.checkNotNull(absolutePath);
                strInterpretString4 = absolutePath;
            }
            map2.put(StageActivity.STYLE_FONT_PATH, strInterpretString4);
        }
        Formula formula14 = this.input_type;
        if (formula14 != null && (strInterpretString3 = formula14.interpretString(this.scope)) != null) {
            map2.put(StageActivity.STYLE_INPUT_TYPE, strInterpretString3);
        }
        Formula formula15 = this.max_length;
        if (formula15 != null && (strInterpretString2 = formula15.interpretString(this.scope)) != null) {
            map2.put(StageActivity.STYLE_MAX_LENGTH, strInterpretString2);
        }
        Formula formula16 = this.corner_radius;
        if (formula16 != null && (strInterpretString = formula16.interpretString(this.scope)) != null) {
            map2.put(StageActivity.STYLE_CORNER_RADIUS, strInterpretString);
        }
        Formula formula17 = this.is_password;
        map2.put(StageActivity.STYLE_IS_PASSWORD, String.valueOf(formula17 != null ? formula17.interpretBoolean(this.scope) : false));
        final StageActivity stageActivity2 = stageActivity;
        final String str3 = str;
        final String str4 = str2;
        stageActivity.runOnUiThread(new Runnable() { // from class: org.catrobat.catroid.content.actions.CreateTextFieldAction$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                CreateTextFieldAction.runAsyncCreate$lambda$4(stageActivity2, str3, this, str4, iIntValue, iIntValue2, iIntValue3, iIntValue4, map);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void runAsyncCreate$lambda$4(StageActivity stageActivity, String nameT, CreateTextFieldAction this$0, String initText, int i, int i2, int i3, int i4, HashMap customStyles) {
        Intrinsics.checkNotNullParameter(nameT, "$nameT");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(initText, "$initText");
        Intrinsics.checkNotNullParameter(customStyles, "$customStyles");
        try {
            stageActivity.createInputField(nameT, this$0.variable, initText, i, i2, i3, i4, customStyles);
        } finally {
            this$0.finished = true;
        }
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Action
    public void restart() {
        super.restart();
        this.started = false;
        this.finished = false;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Action, com.badlogic.gdx.utils.Pool.Poolable
    public void reset() {
        super.reset();
        this.started = false;
        this.finished = false;
    }
}
