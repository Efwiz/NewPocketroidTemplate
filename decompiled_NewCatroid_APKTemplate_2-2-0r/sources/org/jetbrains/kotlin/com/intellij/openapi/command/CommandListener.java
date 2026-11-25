package org.jetbrains.kotlin.com.intellij.openapi.command;

import java.util.EventListener;
import org.jetbrains.kotlin.com.intellij.util.messages.Topic;

/* JADX INFO: loaded from: classes3.dex */
public interface CommandListener extends EventListener {
    public static final Topic<CommandListener> TOPIC = new Topic<>(CommandListener.class, Topic.BroadcastDirection.TO_DIRECT_CHILDREN, true);

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        objArr[0] = "event";
        objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/command/CommandListener";
        if (i == 1) {
            objArr[2] = "beforeCommandFinished";
        } else if (i != 2) {
            objArr[2] = "commandStarted";
        } else {
            objArr[2] = "commandFinished";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    default void beforeCommandFinished(CommandEvent commandEvent) {
        if (commandEvent == null) {
            $$$reportNull$$$0(1);
        }
    }

    default void commandFinished(CommandEvent commandEvent) {
        if (commandEvent == null) {
            $$$reportNull$$$0(2);
        }
    }

    default void commandStarted(CommandEvent commandEvent) {
        if (commandEvent == null) {
            $$$reportNull$$$0(0);
        }
    }
}
