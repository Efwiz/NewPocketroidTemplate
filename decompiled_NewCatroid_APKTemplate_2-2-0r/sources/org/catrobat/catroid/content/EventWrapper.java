package org.catrobat.catroid.content;

import com.badlogic.gdx.scenes.scene2d.Event;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.content.eventids.EventId;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/EventWrapper.class
  classes14.dex
 */
/* JADX INFO: compiled from: EventWrapper.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0015\u0010\r\u001a\u00020\u00052\u0006\u0010\u000e\u001a\u00020\u000bH\u0000¢\u0006\u0002\b\u000fJ\u0006\u0010\u0010\u001a\u00020\u0005J\u000e\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u000e\u001a\u00020\u000bR\u0014\u0010\u0002\u001a\u00020\u0003X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u001e\u0010\t\u001a\u0012\u0012\u0004\u0012\u00020\u000b\u0012\u0006\u0012\u0004\u0018\u00010\f\u0018\u00010\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0013"}, d2 = {"Lorg/catrobat/catroid/content/EventWrapper;", "Lcom/badlogic/gdx/scenes/scene2d/Event;", "eventId", "Lorg/catrobat/catroid/content/eventids/EventId;", "wait", "", "(Lorg/catrobat/catroid/content/eventids/EventId;Z)V", "getEventId$catroid_apktemplateTemplateRelease", "()Lorg/catrobat/catroid/content/eventids/EventId;", "spriteWaitList", "", "Lorg/catrobat/catroid/content/Sprite;", "", "addSpriteToWaitList", "sprite", "addSpriteToWaitList$catroid_apktemplateTemplateRelease", "isWaitingForSprite", "notify", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class EventWrapper extends Event {
    private final EventId eventId;
    private Map<Sprite, Integer> spriteWaitList;
    private final boolean wait;

    public EventWrapper(EventId eventId, boolean z) {
        Intrinsics.checkNotNullParameter(eventId, "eventId");
        this.eventId = eventId;
        this.wait = z;
    }

    /* JADX INFO: renamed from: getEventId$catroid_apktemplateTemplateRelease, reason: from getter */
    public final EventId getEventId() {
        return this.eventId;
    }

    public final void notify(Sprite sprite) {
        Intrinsics.checkNotNullParameter(sprite, "sprite");
        Map<Sprite, Integer> map = this.spriteWaitList;
        if (map != null) {
            if (map.containsKey(sprite)) {
                Integer num = map.get(sprite);
                map.put(sprite, num != null ? Integer.valueOf(num.intValue() - 1) : null);
            }
            Integer num2 = map.get(sprite);
            if (num2 != null && num2.intValue() == 0) {
                map.remove(sprite);
            }
        }
    }

    public final boolean addSpriteToWaitList$catroid_apktemplateTemplateRelease(Sprite sprite) {
        Intrinsics.checkNotNullParameter(sprite, "sprite");
        boolean z = this.wait;
        if (z) {
            if (this.spriteWaitList == null) {
                this.spriteWaitList = new LinkedHashMap();
            }
            Map<Sprite, Integer> map = this.spriteWaitList;
            Intrinsics.checkNotNull(map);
            Integer num = map.get(sprite);
            map.put(sprite, Integer.valueOf((num != null ? num.intValue() : 0) + 1));
        }
        return z;
    }

    public final boolean isWaitingForSprite() {
        Map<Sprite, Integer> map = this.spriteWaitList;
        return map != null && (map.isEmpty() ^ true);
    }
}
