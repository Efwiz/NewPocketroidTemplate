package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import kotlin.Metadata;
import kotlin.text.StringsKt;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.utils.ErrorLog;
import org.catrobat.catroid.utils.GlobalShaderManager;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/ShaderAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: ShaderAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0014R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001c\u0010\u000f\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0006\"\u0004\b\u0011\u0010\b¨\u0006\u0016"}, d2 = {"Lorg/catrobat/catroid/content/actions/ShaderAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", "formula", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getFormula", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setFormula", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", "vertex", "getVertex", "setVertex", ConfigConstants.CONFIG_KEY_UPDATE, "", "percent", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ShaderAction extends TemporalAction {
    private Formula formula;
    private Scope scope;
    private Formula vertex;

    public final Scope getScope() {
        return this.scope;
    }

    public final void setScope(Scope scope) {
        this.scope = scope;
    }

    public final Formula getFormula() {
        return this.formula;
    }

    public final void setFormula(Formula formula) {
        this.formula = formula;
    }

    public final Formula getVertex() {
        return this.vertex;
    }

    public final void setVertex(Formula formula) {
        this.vertex = formula;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        String strTrimIndent;
        String strInterpretString;
        Formula formula = this.formula;
        if (formula == null || (strInterpretString = formula.interpretString(this.scope)) == null || (strTrimIndent = StringsKt.trimIndent(strInterpretString)) == null) {
            strTrimIndent = "        // Код для вашего фрагментного шейдера\n#ifdef GL_ES\nprecision mediump float;\n#endif\n\nvarying vec2 v_texCoords;\nuniform sampler2D u_texture;\n\nvoid main() {\n    // 1. Пытаемся получить цвет из текстуры FBO\n    vec4 sceneColor = texture2D(u_texture, v_texCoords);\n\n    // 2. Генерируем \"контрольный\" цвет-градиент, который не зависит от текстуры.\n    // Он доказывает, что сам шейдер и его координаты работают.\n    vec4 gradientColor = vec4(v_texCoords.x, v_texCoords.y, 0.0, 1.0);\n\n    // 3. Смешиваем их. 50% от сцены, 50% от градиента.\n    gl_FragColor = mix(sceneColor, gradientColor, 0.5);\n}";
        }
        Formula formula2 = this.vertex;
        String strInterpretString2 = formula2 != null ? formula2.interpretString(this.scope) : null;
        if (strInterpretString2 == null) {
            strInterpretString2 = "attribute vec4 a_position;\nattribute vec2 a_texCoord0;\nuniform mat4 u_projTrans; // <--- ЭТО ВАЖНО\nvarying vec2 v_texCoords;\n\nvoid main() {\n    v_texCoords = a_texCoord0;\n    gl_Position = u_projTrans * a_position;\n}";
        }
        String customShader = GlobalShaderManager.INSTANCE.setCustomShader(strTrimIndent, strInterpretString2);
        if (customShader != null) {
            ErrorLog.INSTANCE.log(customShader);
        }
    }
}
