package org.catrobat.catroid.content.actions;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import kotlin.Metadata;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.content.eventids.UserDefinedBrickEventId;
import org.catrobat.catroid.formulaeditor.UserVariable;
import org.catrobat.catroid.userbrick.UserDefinedBrickInput;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/UserDefinedBrickAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: UserDefinedBrickAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\n\u0010\u0016\u001a\u0004\u0018\u00010\u0017H\u0016J\u000e\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00190\u0010H\u0002J\u0016\u0010\u001a\u001a\u00020\u001b2\u000e\u0010\u001c\u001a\n\u0012\u0004\u0012\u00020\u0011\u0018\u00010\u0010R(\u0010\u0003\u001a\u0004\u0018\u00010\u00042\b\u0010\u0003\u001a\u0004\u0018\u00010\u0004@FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\"\u0010\u000f\u001a\n\u0012\u0004\u0012\u00020\u0011\u0018\u00010\u0010X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015¨\u0006\u001d"}, d2 = {"Lorg/catrobat/catroid/content/actions/UserDefinedBrickAction;", "Lorg/catrobat/catroid/content/actions/SingleSpriteEventAction;", "()V", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", "userDefinedBrickID", "Ljava/util/UUID;", "getUserDefinedBrickID", "()Ljava/util/UUID;", "setUserDefinedBrickID", "(Ljava/util/UUID;)V", "userDefinedBrickInputs", "", "Lorg/catrobat/catroid/userbrick/UserDefinedBrickInput;", "getUserDefinedBrickInputs", "()Ljava/util/List;", "setUserDefinedBrickInputs", "(Ljava/util/List;)V", "getEventId", "Lorg/catrobat/catroid/content/eventids/UserDefinedBrickEventId;", "getInterpretedInputs", "", "setInputs", "", "inputs", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class UserDefinedBrickAction extends SingleSpriteEventAction {
    private Scope scope;
    private UUID userDefinedBrickID;
    private List<UserDefinedBrickInput> userDefinedBrickInputs;

    public final Scope getScope() {
        return this.scope;
    }

    public final void setScope(Scope scope) {
        this.scope = scope;
        super.setSprite(scope != null ? scope.getSprite() : null);
    }

    public final UUID getUserDefinedBrickID() {
        return this.userDefinedBrickID;
    }

    public final void setUserDefinedBrickID(UUID uuid) {
        this.userDefinedBrickID = uuid;
    }

    public final List<UserDefinedBrickInput> getUserDefinedBrickInputs() {
        return this.userDefinedBrickInputs;
    }

    public final void setUserDefinedBrickInputs(List<UserDefinedBrickInput> list) {
        this.userDefinedBrickInputs = list;
    }

    public final void setInputs(List<UserDefinedBrickInput> inputs) {
        this.userDefinedBrickInputs = new ArrayList();
        if (inputs != null) {
            for (UserDefinedBrickInput userDefinedBrickInput : inputs) {
                List<UserDefinedBrickInput> list = this.userDefinedBrickInputs;
                if (list != null) {
                    list.add(new UserDefinedBrickInput(userDefinedBrickInput));
                }
            }
        }
    }

    private final List<Object> getInterpretedInputs() {
        ArrayList arrayList = new ArrayList();
        List<UserDefinedBrickInput> list = this.userDefinedBrickInputs;
        if (list != null) {
            for (UserDefinedBrickInput userDefinedBrickInput : list) {
                arrayList.add(new UserVariable(userDefinedBrickInput.getName(), userDefinedBrickInput.getValue().interpretObject(this.scope)));
            }
        }
        return arrayList;
    }

    @Override // org.catrobat.catroid.content.actions.EventAction
    public UserDefinedBrickEventId getEventId() {
        UUID uuid = this.userDefinedBrickID;
        if (uuid != null) {
            return new UserDefinedBrickEventId(uuid, getInterpretedInputs());
        }
        return null;
    }
}
