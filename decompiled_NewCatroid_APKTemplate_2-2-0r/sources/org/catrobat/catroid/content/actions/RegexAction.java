package org.catrobat.catroid.content.actions;

import android.util.Log;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.MatchResult;
import kotlin.text.Regex;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.UserList;
import org.catrobat.catroid.utils.ErrorLog;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/RegexAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: RegexAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001bH\u0014R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001c\u0010\u000f\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0006\"\u0004\b\u0011\u0010\bR\u001c\u0010\u0012\u001a\u0004\u0018\u00010\u0013X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017¨\u0006\u001c"}, d2 = {"Lorg/catrobat/catroid/content/actions/RegexAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", "regex_f", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getRegex_f", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setRegex_f", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", "text_f", "getText_f", "setText_f", "userlist", "Lorg/catrobat/catroid/formulaeditor/UserList;", "getUserlist", "()Lorg/catrobat/catroid/formulaeditor/UserList;", "setUserlist", "(Lorg/catrobat/catroid/formulaeditor/UserList;)V", ConfigConstants.CONFIG_KEY_UPDATE, "", "percent", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class RegexAction extends TemporalAction {
    private Formula regex_f;
    private Scope scope;
    private Formula text_f;
    private UserList userlist;

    public final Scope getScope() {
        return this.scope;
    }

    public final void setScope(Scope scope) {
        this.scope = scope;
    }

    public final Formula getText_f() {
        return this.text_f;
    }

    public final void setText_f(Formula formula) {
        this.text_f = formula;
    }

    public final Formula getRegex_f() {
        return this.regex_f;
    }

    public final void setRegex_f(Formula formula) {
        this.regex_f = formula;
    }

    public final UserList getUserlist() {
        return this.userlist;
    }

    public final void setUserlist(UserList userList) {
        this.userlist = userList;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        Formula formula = this.text_f;
        String strInterpretString = formula != null ? formula.interpretString(this.scope) : null;
        if (strInterpretString == null) {
            strInterpretString = "";
        }
        Formula formula2 = this.regex_f;
        String strInterpretString2 = formula2 != null ? formula2.interpretString(this.scope) : null;
        String str = strInterpretString2 != null ? strInterpretString2 : "";
        UserList userList = this.userlist;
        if (userList != null) {
            Intrinsics.checkNotNull(userList);
            userList.reset();
            try {
                for (MatchResult matchResult : Regex.findAll$default(new Regex(str), strInterpretString, 0, 2, null)) {
                    UserList userList2 = this.userlist;
                    Intrinsics.checkNotNull(userList2);
                    userList2.addListItem(matchResult.getValue());
                }
                return;
            } catch (Exception e) {
                ErrorLog errorLog = ErrorLog.INSTANCE;
                String message = e.getMessage();
                if (message == null) {
                    message = "**message not provided :(**";
                }
                errorLog.log(message);
                Log.e("RegexAction", "Ошибка при обработке регулярного выражения: " + e.getMessage());
                return;
            }
        }
        Log.e("RegexAction", "UserList не установлен!");
    }
}
