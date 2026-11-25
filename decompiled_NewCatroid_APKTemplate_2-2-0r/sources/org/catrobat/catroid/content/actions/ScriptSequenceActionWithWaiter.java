package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.utils.Array;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.content.EventWrapper;
import org.catrobat.catroid.content.Sprite;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/ScriptSequenceActionWithWaiter.class
  classes14.dex
 */
/* JADX INFO: compiled from: ScriptSequenceActionWithWaiter.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001:\u0001\fB\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0001\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u0006\u0010\n\u001a\u00020\u000bR\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\r"}, d2 = {"Lorg/catrobat/catroid/content/actions/ScriptSequenceActionWithWaiter;", "Lorg/catrobat/catroid/content/actions/ScriptSequenceAction;", "thread", "event", "Lorg/catrobat/catroid/content/EventWrapper;", "sprite", "Lorg/catrobat/catroid/content/Sprite;", "(Lorg/catrobat/catroid/content/actions/ScriptSequenceAction;Lorg/catrobat/catroid/content/EventWrapper;Lorg/catrobat/catroid/content/Sprite;)V", "waiter", "Lorg/catrobat/catroid/content/actions/ScriptSequenceActionWithWaiter$Waiter;", "notifyWaiter", "", "Waiter", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ScriptSequenceActionWithWaiter extends ScriptSequenceAction {
    private final Waiter waiter;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ScriptSequenceActionWithWaiter(ScriptSequenceAction thread, EventWrapper event, Sprite sprite) {
        super(thread.script);
        Intrinsics.checkNotNullParameter(thread, "thread");
        Intrinsics.checkNotNullParameter(event, "event");
        Intrinsics.checkNotNullParameter(sprite, "sprite");
        this.waiter = new Waiter(event, sprite);
        Array<Action> actions = thread.getActions();
        Intrinsics.checkNotNullExpressionValue(actions, "getActions(...)");
        Iterator<Action> it = actions.iterator();
        while (it.hasNext()) {
            addAction(it.next());
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/ScriptSequenceActionWithWaiter$Waiter.class
      classes14.dex
     */
    /* JADX INFO: compiled from: ScriptSequenceActionWithWaiter.kt */
    @Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0082\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J\t\u0010\f\u001a\u00020\u0005HÆ\u0003J\u001d\u0010\r\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001J\t\u0010\u0013\u001a\u00020\u0014HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0015"}, d2 = {"Lorg/catrobat/catroid/content/actions/ScriptSequenceActionWithWaiter$Waiter;", "", "event", "Lorg/catrobat/catroid/content/EventWrapper;", "sprite", "Lorg/catrobat/catroid/content/Sprite;", "(Lorg/catrobat/catroid/content/EventWrapper;Lorg/catrobat/catroid/content/Sprite;)V", "getEvent", "()Lorg/catrobat/catroid/content/EventWrapper;", "getSprite", "()Lorg/catrobat/catroid/content/Sprite;", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final /* data */ class Waiter {
        private final EventWrapper event;
        private final Sprite sprite;

        public static /* synthetic */ Waiter copy$default(Waiter waiter, EventWrapper eventWrapper, Sprite sprite, int i, Object obj) {
            if ((i & 1) != 0) {
                eventWrapper = waiter.event;
            }
            if ((i & 2) != 0) {
                sprite = waiter.sprite;
            }
            return waiter.copy(eventWrapper, sprite);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final EventWrapper getEvent() {
            return this.event;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final Sprite getSprite() {
            return this.sprite;
        }

        public final Waiter copy(EventWrapper event, Sprite sprite) {
            Intrinsics.checkNotNullParameter(event, "event");
            Intrinsics.checkNotNullParameter(sprite, "sprite");
            return new Waiter(event, sprite);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof Waiter)) {
                return false;
            }
            Waiter waiter = (Waiter) other;
            return Intrinsics.areEqual(this.event, waiter.event) && Intrinsics.areEqual(this.sprite, waiter.sprite);
        }

        public int hashCode() {
            return (this.event.hashCode() * 31) + this.sprite.hashCode();
        }

        public String toString() {
            return "Waiter(event=" + this.event + ", sprite=" + this.sprite + Constant.AFTER_QUTO;
        }

        public Waiter(EventWrapper event, Sprite sprite) {
            Intrinsics.checkNotNullParameter(event, "event");
            Intrinsics.checkNotNullParameter(sprite, "sprite");
            this.event = event;
            this.sprite = sprite;
        }

        public final EventWrapper getEvent() {
            return this.event;
        }

        public final Sprite getSprite() {
            return this.sprite;
        }
    }

    public final void notifyWaiter() {
        this.waiter.getEvent().notify(this.waiter.getSprite());
    }
}
