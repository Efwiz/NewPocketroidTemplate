package org.catrobat.catroid.content.actions;

import java.util.List;
import kotlin.Metadata;
import org.catrobat.catroid.common.LookData;
import org.catrobat.catroid.content.Look;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.eventids.EventId;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/SetRelativeLookAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: SetRelativeLookAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\n\u0010\t\u001a\u0004\u0018\u00010\nH\u0016R\u0018\u0010\u0003\u001a\u00020\u0004X¦\u000e¢\u0006\f\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\b¨\u0006\u000b"}, d2 = {"Lorg/catrobat/catroid/content/actions/SetRelativeLookAction;", "Lorg/catrobat/catroid/content/actions/SetLookByIndexAction;", "()V", "change", "", "getChange", "()I", "setChange", "(I)V", "getEventId", "Lorg/catrobat/catroid/content/eventids/EventId;", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class SetRelativeLookAction extends SetLookByIndexAction {
    public abstract int getChange();

    public abstract void setChange(int i);

    @Override // org.catrobat.catroid.content.actions.SetLookByIndexAction, org.catrobat.catroid.content.actions.SetLookAction, org.catrobat.catroid.content.actions.EventAction
    public EventId getEventId() {
        int iIndexOf;
        Look look;
        Sprite sprite = getSprite();
        LookData lookData = null;
        List<LookData> lookList = sprite != null ? sprite.getLookList() : null;
        Sprite sprite2 = getSprite();
        Look look2 = sprite2 != null ? sprite2.look : null;
        Sprite sprite3 = getSprite();
        if (sprite3 != null && (look = sprite3.look) != null) {
            lookData = look.getLookData();
        }
        if (look2 != null && lookList != null && look2.getLookListIndexBeforeLookRequest() > -1) {
            iIndexOf = ((look2.getLookListIndexBeforeLookRequest() + getChange()) + lookList.size()) % lookList.size();
        } else {
            iIndexOf = (lookData == null || lookList == null || !lookList.contains(lookData)) ? 0 : ((lookList.indexOf(lookData) + getChange()) + lookList.size()) % lookList.size();
        }
        setFormula(new Formula(Integer.valueOf(iIndexOf + 1)));
        if (look2 != null) {
            look2.setLookListIndexBeforeLookRequest(-1);
        }
        return super.getEventId();
    }
}
