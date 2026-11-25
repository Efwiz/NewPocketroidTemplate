package org.catrobat.catroid.content.actions;

import android.content.Context;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.UserList;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/SplitAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: SplitAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001c\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u001c0\u001b2\u0006\u0010\u001d\u001a\u00020\u001c2\u0006\u0010\u001e\u001a\u00020\u001cJ\u0010\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\"H\u0014R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u001c\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u001c\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u001c\u0010\u0011\u001a\u0004\u0018\u00010\fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u000e\"\u0004\b\u0013\u0010\u0010R\u001c\u0010\u0014\u001a\u0004\u0018\u00010\u0015X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0017\"\u0004\b\u0018\u0010\u0019¨\u0006#"}, d2 = {"Lorg/catrobat/catroid/content/actions/SplitAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", "contextt", "Landroid/content/Context;", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", "simbol", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getSimbol", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setSimbol", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "string", "getString", "setString", "userList", "Lorg/catrobat/catroid/formulaeditor/UserList;", "getUserList", "()Lorg/catrobat/catroid/formulaeditor/UserList;", "setUserList", "(Lorg/catrobat/catroid/formulaeditor/UserList;)V", "splitString", "", "", "input", "delimiter", ConfigConstants.CONFIG_KEY_UPDATE, "", "percent", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SplitAction extends TemporalAction {
    private Context contextt;
    private Scope scope;
    private Formula simbol;
    private Formula string;
    private UserList userList;

    public final Scope getScope() {
        return this.scope;
    }

    public final void setScope(Scope scope) {
        this.scope = scope;
    }

    public final Formula getString() {
        return this.string;
    }

    public final void setString(Formula formula) {
        this.string = formula;
    }

    public final Formula getSimbol() {
        return this.simbol;
    }

    public final void setSimbol(Formula formula) {
        this.simbol = formula;
    }

    public final UserList getUserList() {
        return this.userList;
    }

    public final void setUserList(UserList userList) {
        this.userList = userList;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        UserList userList = this.userList;
        if (userList == null) {
            return;
        }
        if (userList != null) {
            userList.reset();
        }
        Formula formula = this.string;
        String strInterpretString = formula != null ? formula.interpretString(this.scope) : null;
        if (strInterpretString == null) {
            strInterpretString = "";
        }
        Formula formula2 = this.simbol;
        String strInterpretString2 = formula2 != null ? formula2.interpretString(this.scope) : null;
        if (strInterpretString2 == null) {
            strInterpretString2 = ",";
        }
        for (String str : splitString(strInterpretString, strInterpretString2)) {
            UserList userList2 = this.userList;
            if (userList2 != null) {
                userList2.addListItem(str);
            }
        }
    }

    public final List<String> splitString(String input, String delimiter) {
        Intrinsics.checkNotNullParameter(input, "input");
        Intrinsics.checkNotNullParameter(delimiter, "delimiter");
        List listSplit$default = StringsKt.split$default((CharSequence) input, new String[]{delimiter}, false, 0, 6, (Object) null);
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listSplit$default, 10));
        Iterator it = listSplit$default.iterator();
        while (it.hasNext()) {
            arrayList.add(StringsKt.trim((CharSequence) it.next()).toString());
        }
        ArrayList arrayList2 = new ArrayList();
        for (Object obj : arrayList) {
            if (((String) obj).length() > 0) {
                arrayList2.add(obj);
            }
        }
        return arrayList2;
    }
}
