package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.stage.StageActivity;
import org.catrobat.catroid.virtualmachine.VirtualMachineManager;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/SendVmInputAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: SendVmInputAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0010H\u0002J\u0010\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0014R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000e¨\u0006\u0016"}, d2 = {"Lorg/catrobat/catroid/content/actions/SendVmInputAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", "inputText", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getInputText", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setInputText", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", "mapSpecialKeys", "", "input", ConfigConstants.CONFIG_KEY_UPDATE, "", "percent", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SendVmInputAction extends TemporalAction {
    private Formula inputText;
    private Scope scope;

    public final Scope getScope() {
        return this.scope;
    }

    public final void setScope(Scope scope) {
        this.scope = scope;
    }

    public final Formula getInputText() {
        return this.inputText;
    }

    public final void setInputText(Formula formula) {
        this.inputText = formula;
    }

    private final String mapSpecialKeys(String input) {
        Pattern patternCompile = Pattern.compile("<CTRL\\+([A-Z])>");
        String upperCase = input.toUpperCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue(upperCase, "toUpperCase(...)");
        Matcher matcher = patternCompile.matcher(upperCase);
        if (matcher.matches()) {
            String strGroup = matcher.group(1);
            Intrinsics.checkNotNull(strGroup);
            return String.valueOf((char) (strGroup.charAt(0) - '@'));
        }
        String upperCase2 = input.toUpperCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue(upperCase2, "toUpperCase(...)");
        switch (upperCase2.hashCode()) {
            case -989472833:
                return !upperCase2.equals("<CTRL+C>") ? input : "\u0003";
            case -989472802:
                return !upperCase2.equals("<CTRL+D>") ? input : "\u0004";
            case -989472120:
                return !upperCase2.equals("<CTRL+Z>") ? input : "\u001a";
            case -948732677:
                return !upperCase2.equals("<BACKSPACE>") ? input : "\b";
            case -534336574:
                return !upperCase2.equals("<ENTER>") ? input : "\n";
            case -167159554:
                return !upperCase2.equals("<RIGHT>") ? input : "\u001b[C";
            case 1871687:
                return !upperCase2.equals("<UP>") ? input : "\u001b[A";
            case 57548741:
                return !upperCase2.equals("<ESC>") ? input : "\u001b";
            case 57978277:
                return !upperCase2.equals("<TAB>") ? input : "\t";
            case 1782988064:
                return !upperCase2.equals("<DOWN>") ? input : "\u001b[B";
            case 1790062171:
                return !upperCase2.equals("<LEFT>") ? input : "\u001b[D";
            default:
                return input;
        }
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        Formula formula = this.inputText;
        String strInterpretString = formula != null ? formula.interpretString(this.scope) : null;
        if (strInterpretString == null) {
            return;
        }
        VirtualMachineManager.INSTANCE.sendInputToVM(StageActivity.DEFAULT_VM_NAME, mapSpecialKeys(strInterpretString));
    }
}
