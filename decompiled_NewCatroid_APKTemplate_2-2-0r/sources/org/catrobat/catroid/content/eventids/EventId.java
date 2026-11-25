package org.catrobat.catroid.content.eventids;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/eventids/EventId.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class EventId {
    public static final int AFTER_UPDATE = 11;
    public static final int ANY_NFC = 5;
    public static final int APP_MINIMIZED = 16;
    public static final int APP_RESTORED = 17;
    public static final int BACK_PRESSED = 7;
    public static final int BEFORE_UPDATE = 10;
    public static final int FINGER_MOVED_ON_SCREEN = 14;
    public static final int FINGER_MOVED_OVER_SPRITE = 13;
    public static final int MOUSE_BUTTON_CLICKED = 8;
    public static final int MOUSE_WHEEL_SCROLLED = 9;
    public static final int NOTIFICATION_CLICKED = 15;
    public static final int OTHER = 0;
    public static final int PROJECT_EXIT = 6;
    public static final int SPRITE_RELEASED = 12;
    public static final int START = 3;
    public static final int START_AS_CLONE = 4;
    public static final int TAP = 1;
    public static final int TAP_BACKGROUND = 2;
    public static final int USER_CONCAT = 19;
    public static final int WINDOW_RESIZED = 18;
    private final int type;

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/eventids/EventId$EventType.class
      classes14.dex
     */
    @Retention(RetentionPolicy.SOURCE)
    public @interface EventType {
    }

    public EventId(int type) {
        this.type = type;
    }

    protected EventId() {
        this.type = 0;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        return (o instanceof EventId) && this.type == ((EventId) o).type;
    }

    public int hashCode() {
        return this.type;
    }
}
