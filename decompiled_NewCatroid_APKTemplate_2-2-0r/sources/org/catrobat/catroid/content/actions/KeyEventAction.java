package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import com.google.firebase.analytics.FirebaseAnalytics;
import kotlin.Metadata;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.stage.StageActivity;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/KeyEventAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: KeyEventAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0002J\u0010\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018H\u0014R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\u0006\"\u0004\b\n\u0010\bR\u001c\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010¨\u0006\u0019"}, d2 = {"Lorg/catrobat/catroid/content/actions/KeyEventAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", FirebaseAnalytics.Param.CHARACTER, "Lorg/catrobat/catroid/formulaeditor/Formula;", "getCharacter", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setCharacter", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "isDown", "setDown", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", "stringToKeysym", "", "input", "", ConfigConstants.CONFIG_KEY_UPDATE, "", "percent", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KeyEventAction extends TemporalAction {
    private Formula character;
    private Formula isDown;
    private Scope scope;

    public final Scope getScope() {
        return this.scope;
    }

    public final void setScope(Scope scope) {
        this.scope = scope;
    }

    public final Formula getCharacter() {
        return this.character;
    }

    public final void setCharacter(Formula formula) {
        this.character = formula;
    }

    /* JADX INFO: renamed from: isDown, reason: from getter */
    public final Formula getIsDown() {
        return this.isDown;
    }

    public final void setDown(Formula formula) {
        this.isDown = formula;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code duplicated, block: B:295:0x0438  */
    /* JADX WARN: Code duplicated, block: B:354:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Code restructure failed: missing block: B:12:0x003a, code lost:
    
        if (r0.equals("<NUM_DOT>") == false) goto L293;
     */
    /* JADX WARN: Code restructure failed: missing block: B:130:0x01e8, code lost:
    
        if (r0.equals(" ") == false) goto L293;
     */
    /* JADX WARN: Code restructure failed: missing block: B:133:0x01f2, code lost:
    
        if (r0.equals("\n") == false) goto L293;
     */
    /* JADX WARN: Code restructure failed: missing block: B:140:0x020b, code lost:
    
        if (r0.equals("<WIN_L>") == false) goto L293;
     */
    /* JADX WARN: Code restructure failed: missing block: B:143:0x0215, code lost:
    
        if (r0.equals("<SUPER>") == false) goto L293;
     */
    /* JADX WARN: Code restructure failed: missing block: B:146:0x021f, code lost:
    
        if (r0.equals("<INSERT>") == false) goto L293;
     */
    /* JADX WARN: Code restructure failed: missing block: B:148:0x0223, code lost:
    
        return com.gaurav.avnc.vnc.XKeySym.XK_Insert;
     */
    /* JADX WARN: Code restructure failed: missing block: B:150:0x022e, code lost:
    
        if (r0.equals("<SPACE>") == false) goto L293;
     */
    /* JADX WARN: Code restructure failed: missing block: B:152:0x0232, code lost:
    
        return 32;
     */
    /* JADX WARN: Code restructure failed: missing block: B:154:0x023c, code lost:
    
        if (r0.equals("<NUM_MINUS>") == false) goto L293;
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x0044, code lost:
    
        if (r0.equals("<NUM_DIV>") == false) goto L293;
     */
    /* JADX WARN: Code restructure failed: missing block: B:161:0x0255, code lost:
    
        if (r0.equals("<PAUSE>") == false) goto L293;
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x004e, code lost:
    
        if (r0.equals("<NUM_PLUS>") == false) goto L293;
     */
    /* JADX WARN: Code restructure failed: missing block: B:204:0x02f5, code lost:
    
        if (r0.equals("<NUM_/>") == false) goto L293;
     */
    /* JADX WARN: Code restructure failed: missing block: B:206:0x02f9, code lost:
    
        return com.gaurav.avnc.vnc.XKeySym.XK_KP_Divide;
     */
    /* JADX WARN: Code restructure failed: missing block: B:208:0x0304, code lost:
    
        if (r0.equals("<NUM_.>") == false) goto L293;
     */
    /* JADX WARN: Code restructure failed: missing block: B:210:0x0308, code lost:
    
        return com.gaurav.avnc.vnc.XKeySym.XK_KP_Decimal;
     */
    /* JADX WARN: Code restructure failed: missing block: B:212:0x0313, code lost:
    
        if (r0.equals("<NUM_->") == false) goto L293;
     */
    /* JADX WARN: Code restructure failed: missing block: B:214:0x0317, code lost:
    
        return com.gaurav.avnc.vnc.XKeySym.XK_KP_Subtract;
     */
    /* JADX WARN: Code restructure failed: missing block: B:216:0x0322, code lost:
    
        if (r0.equals("<NUM_+>") == false) goto L293;
     */
    /* JADX WARN: Code restructure failed: missing block: B:218:0x0326, code lost:
    
        return com.gaurav.avnc.vnc.XKeySym.XK_KP_Add;
     */
    /* JADX WARN: Code restructure failed: missing block: B:220:0x0331, code lost:
    
        if (r0.equals("<NUM_*>") == false) goto L293;
     */
    /* JADX WARN: Code restructure failed: missing block: B:222:0x0335, code lost:
    
        return com.gaurav.avnc.vnc.XKeySym.XK_KP_Multiply;
     */
    /* JADX WARN: Code restructure failed: missing block: B:228:0x034f, code lost:
    
        if (r0.equals("<ENTER>") == false) goto L293;
     */
    /* JADX WARN: Code restructure failed: missing block: B:230:0x0353, code lost:
    
        return com.gaurav.avnc.vnc.XKeySym.XK_Return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:232:0x035e, code lost:
    
        if (r0.equals("<DELETE>") == false) goto L293;
     */
    /* JADX WARN: Code restructure failed: missing block: B:234:0x0362, code lost:
    
        return 65535;
     */
    /* JADX WARN: Code restructure failed: missing block: B:236:0x036d, code lost:
    
        if (r0.equals("<BREAK>") == false) goto L293;
     */
    /* JADX WARN: Code restructure failed: missing block: B:238:0x0371, code lost:
    
        return 65299;
     */
    /* JADX WARN: Code restructure failed: missing block: B:256:0x03b8, code lost:
    
        if (r0.equals("<COMMAND>") == false) goto L293;
     */
    /* JADX WARN: Code restructure failed: missing block: B:258:0x03bc, code lost:
    
        return com.gaurav.avnc.vnc.XKeySym.XK_Super_L;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x0067, code lost:
    
        if (r0.equals("ESCAPE") == false) goto L293;
     */
    /* JADX WARN: Code restructure failed: missing block: B:260:0x03c7, code lost:
    
        if (r0.equals("<BACKSPACE>") == false) goto L293;
     */
    /* JADX WARN: Code restructure failed: missing block: B:262:0x03cb, code lost:
    
        return com.gaurav.avnc.vnc.XKeySym.XK_BackSpace;
     */
    /* JADX WARN: Code restructure failed: missing block: B:272:0x03f1, code lost:
    
        if (r0.equals("<CAPS_LOCK>") == false) goto L293;
     */
    /* JADX WARN: Code restructure failed: missing block: B:275:0x03fa, code lost:
    
        if (r0.equals("<PRT_SCR>") == false) goto L293;
     */
    /* JADX WARN: Code restructure failed: missing block: B:278:0x0403, code lost:
    
        if (r0.equals("<PAGE_DOWN>") == false) goto L293;
     */
    /* JADX WARN: Code restructure failed: missing block: B:280:0x0406, code lost:
    
        return 65366;
     */
    /* JADX WARN: Code restructure failed: missing block: B:286:0x041d, code lost:
    
        if (r0.equals("<CAPSLOCK>") == false) goto L293;
     */
    /* JADX WARN: Code restructure failed: missing block: B:288:0x0420, code lost:
    
        return com.gaurav.avnc.vnc.XKeySym.XK_Caps_Lock;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x0071, code lost:
    
        if (r0.equals("<PGUP>") == false) goto L293;
     */
    /* JADX WARN: Code restructure failed: missing block: B:290:0x042a, code lost:
    
        if (r0.equals("<PRINTSCREEN>") == false) goto L293;
     */
    /* JADX WARN: Code restructure failed: missing block: B:292:0x042d, code lost:
    
        return com.gaurav.avnc.vnc.XKeySym.XK_Print;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x007b, code lost:
    
        if (r0.equals("<PGDN>") == false) goto L293;
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x00c1, code lost:
    
        if (r0.equals("<BKSP>") == false) goto L293;
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x00cb, code lost:
    
        if (r0.equals("<PAGE_UP>") == false) goto L293;
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x00cf, code lost:
    
        return 65365;
     */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x00da, code lost:
    
        if (r0.equals("<WIN>") == false) goto L293;
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x00f3, code lost:
    
        if (r0.equals("<INS>") == false) goto L293;
     */
    /* JADX WARN: Code restructure failed: missing block: B:67:0x00fd, code lost:
    
        if (r0.equals("<ESC>") == false) goto L293;
     */
    /* JADX WARN: Code restructure failed: missing block: B:69:0x0101, code lost:
    
        return com.gaurav.avnc.vnc.XKeySym.XK_Escape;
     */
    /* JADX WARN: Code restructure failed: missing block: B:87:0x0148, code lost:
    
        if (r0.equals("<DEL>") == false) goto L293;
     */
    /* JADX WARN: Code restructure failed: missing block: B:9:0x0030, code lost:
    
        if (r0.equals("<NUM_MUL>") == false) goto L293;
     */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private final int stringToKeysym(java.lang.String r4) {
        /*
            Method dump skipped, instruction units count: 1398
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.catrobat.catroid.content.actions.KeyEventAction.stringToKeysym(java.lang.String):int");
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        int iStringToKeysym;
        StageActivity stageActivity;
        Formula formula = this.character;
        String strInterpretString = formula != null ? formula.interpretString(this.scope) : null;
        if (strInterpretString == null) {
            strInterpretString = "";
        }
        Formula formula2 = this.isDown;
        boolean zInterpretBoolean = formula2 != null ? formula2.interpretBoolean(this.scope) : true;
        if (strInterpretString.length() == 0 || (iStringToKeysym = stringToKeysym(strInterpretString)) == 0 || (stageActivity = StageActivity.activeStageActivity.get()) == null) {
            return;
        }
        stageActivity.sendVmKeyEvent(iStringToKeysym, zInterpretBoolean);
    }
}
