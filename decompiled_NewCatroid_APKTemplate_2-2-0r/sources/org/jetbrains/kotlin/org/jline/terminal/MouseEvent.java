package org.jetbrains.kotlin.org.jline.terminal;

import java.util.EnumSet;

/* JADX INFO: loaded from: classes4.dex */
public class MouseEvent {
    private final Button button;
    private final EnumSet<Modifier> modifiers;
    private final Type type;
    private final int x;
    private final int y;

    public enum Button {
        NoButton,
        Button1,
        Button2,
        Button3,
        WheelUp,
        WheelDown
    }

    public enum Modifier {
        Shift,
        Alt,
        Control
    }

    public enum Type {
        Released,
        Pressed,
        Wheel,
        Moved,
        Dragged
    }

    public MouseEvent(Type type, Button button, EnumSet<Modifier> enumSet, int i, int i2) {
        this.type = type;
        this.button = button;
        this.modifiers = enumSet;
        this.x = i;
        this.y = i2;
    }

    public Type getType() {
        return this.type;
    }

    public Button getButton() {
        return this.button;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public String toString() {
        return "MouseEvent[type=" + this.type + ", button=" + this.button + ", modifiers=" + this.modifiers + ", x=" + this.x + ", y=" + this.y + ']';
    }
}
