package org.catrobat.catroid.content.actions;

import android.util.Log;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.common.LookData;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.eventids.EventId;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.InterpretationException;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/SetLookByIndexAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: SetLookByIndexAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\b\u0016\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\n\u0010\u0016\u001a\u0004\u0018\u00010\u0017H\u0016R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR(\u0010\u0011\u001a\u0004\u0018\u00010\u00102\b\u0010\u000f\u001a\u0004\u0018\u00010\u00108V@VX\u0096\u000e¢\u0006\f\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015¨\u0006\u0018"}, d2 = {"Lorg/catrobat/catroid/content/actions/SetLookByIndexAction;", "Lorg/catrobat/catroid/content/actions/SetLookAction;", "()V", "formula", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getFormula", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setFormula", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", "value", "Lorg/catrobat/catroid/content/Sprite;", "sprite", "getSprite", "()Lorg/catrobat/catroid/content/Sprite;", "setSprite", "(Lorg/catrobat/catroid/content/Sprite;)V", "getEventId", "Lorg/catrobat/catroid/content/eventids/EventId;", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class SetLookByIndexAction extends SetLookAction {
    private Formula formula;
    private Scope scope;

    public final Formula getFormula() {
        return this.formula;
    }

    public final void setFormula(Formula formula) {
        this.formula = formula;
    }

    @Override // org.catrobat.catroid.content.actions.SetLookAction
    public Sprite getSprite() {
        return super.getSprite();
    }

    @Override // org.catrobat.catroid.content.actions.SetLookAction
    public void setSprite(Sprite sprite) {
        super.setSprite(sprite);
    }

    public final Scope getScope() {
        return this.scope;
    }

    public final void setScope(Scope scope) {
        this.scope = scope;
    }

    @Override // org.catrobat.catroid.content.actions.SetLookAction, org.catrobat.catroid.content.actions.EventAction
    public EventId getEventId() {
        try {
            if (getSprite() != null) {
                Scope scope = this.scope;
                if ((scope != null ? scope.getSprite() : null) != null) {
                    Scope scope2 = this.scope;
                    if ((scope2 != null ? scope2.getSequence() : null) != null) {
                        Formula formula = this.formula;
                        Integer numInterpretInteger = formula != null ? formula.interpretInteger(this.scope) : null;
                        int iIntValue = numInterpretInteger == null ? 0 : numInterpretInteger.intValue();
                        Sprite sprite = getSprite();
                        Intrinsics.checkNotNull(sprite);
                        List<LookData> lookList = sprite.getLookList();
                        setLookData(lookList != null ? (LookData) CollectionsKt.getOrNull(lookList, iIntValue - 1) : null);
                    }
                }
            }
        } catch (InterpretationException e) {
            Log.d(getClass().getSimpleName(), "Formula Interpretation for look index failed", e);
        }
        return super.getEventId();
    }
}
