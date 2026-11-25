package org.jetbrains.kotlin.org.jline.terminal.impl;

import java.util.EnumSet;
import java.util.function.IntSupplier;
import org.jetbrains.kotlin.org.jline.terminal.MouseEvent;
import org.jetbrains.kotlin.org.jline.terminal.Terminal;
import org.jetbrains.kotlin.org.jline.utils.InfoCmp;

/* JADX INFO: loaded from: classes4.dex */
public class MouseSupport {
    public static boolean hasMouseSupport(Terminal terminal) {
        return terminal.getStringCapability(InfoCmp.Capability.key_mouse) != null;
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.org.jline.terminal.impl.MouseSupport$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$org$jline$terminal$Terminal$MouseTracking;

        static {
            int[] iArr = new int[Terminal.MouseTracking.values().length];
            $SwitchMap$org$jline$terminal$Terminal$MouseTracking = iArr;
            try {
                iArr[Terminal.MouseTracking.Off.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$org$jline$terminal$Terminal$MouseTracking[Terminal.MouseTracking.Normal.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$org$jline$terminal$Terminal$MouseTracking[Terminal.MouseTracking.Button.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$org$jline$terminal$Terminal$MouseTracking[Terminal.MouseTracking.Any.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    public static boolean trackMouse(Terminal terminal, Terminal.MouseTracking mouseTracking) {
        if (!hasMouseSupport(terminal)) {
            return false;
        }
        int i = AnonymousClass1.$SwitchMap$org$jline$terminal$Terminal$MouseTracking[mouseTracking.ordinal()];
        if (i == 1) {
            terminal.writer().write("\u001b[?1000l");
        } else if (i == 2) {
            terminal.writer().write("\u001b[?1005h\u001b[?1000h");
        } else if (i == 3) {
            terminal.writer().write("\u001b[?1005h\u001b[?1002h");
        } else if (i == 4) {
            terminal.writer().write("\u001b[?1005h\u001b[?1003h");
        }
        terminal.flush();
        return true;
    }

    public static MouseEvent readMouse(IntSupplier intSupplier, MouseEvent mouseEvent) {
        MouseEvent.Button button;
        MouseEvent.Type type;
        MouseEvent.Button button2;
        MouseEvent.Type type2;
        int asInt = intSupplier.getAsInt() - 32;
        int asInt2 = intSupplier.getAsInt() - 33;
        int asInt3 = intSupplier.getAsInt() - 33;
        EnumSet enumSetNoneOf = EnumSet.noneOf(MouseEvent.Modifier.class);
        if ((asInt & 4) == 4) {
            enumSetNoneOf.add(MouseEvent.Modifier.Shift);
        }
        if ((asInt & 8) == 8) {
            enumSetNoneOf.add(MouseEvent.Modifier.Alt);
        }
        if ((asInt & 16) == 16) {
            enumSetNoneOf.add(MouseEvent.Modifier.Control);
        }
        if ((asInt & 64) == 64) {
            type2 = MouseEvent.Type.Wheel;
            button2 = (asInt & 1) == 1 ? MouseEvent.Button.WheelDown : MouseEvent.Button.WheelUp;
        } else {
            int i = asInt & 3;
            if (i == 0) {
                button = MouseEvent.Button.Button1;
                type = mouseEvent.getButton() == button ? MouseEvent.Type.Dragged : MouseEvent.Type.Pressed;
            } else if (i == 1) {
                button = MouseEvent.Button.Button2;
                type = mouseEvent.getButton() == button ? MouseEvent.Type.Dragged : MouseEvent.Type.Pressed;
            } else if (i == 2) {
                button = MouseEvent.Button.Button3;
                type = mouseEvent.getButton() == button ? MouseEvent.Type.Dragged : MouseEvent.Type.Pressed;
            } else if (mouseEvent.getType() == MouseEvent.Type.Pressed || mouseEvent.getType() == MouseEvent.Type.Dragged) {
                button = mouseEvent.getButton();
                type = MouseEvent.Type.Released;
            } else {
                button = MouseEvent.Button.NoButton;
                type = MouseEvent.Type.Moved;
            }
            button2 = button;
            type2 = type;
        }
        return new MouseEvent(type2, button2, enumSetNoneOf, asInt2, asInt3);
    }
}
