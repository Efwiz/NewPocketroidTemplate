package org.jetbrains.kotlin.com.intellij.openapi.command;

import java.util.EventObject;
import org.jetbrains.kotlin.com.intellij.openapi.editor.Document;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;

/* JADX INFO: loaded from: classes3.dex */
public class CommandEvent extends EventObject {
    private final Runnable myCommand;
    private final Object myCommandGroupId;
    private final String myCommandName;
    private final Document myDocument;
    private final Project myProject;
    private final boolean myShouldRecordActionForActiveDocument;
    private final UndoConfirmationPolicy myUndoConfirmationPolicy;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 6 || i == 7 || i == 8) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 6 || i == 7 || i == 8) ? 2 : 3];
        switch (i) {
            case 1:
            case 4:
                objArr[0] = "command";
                break;
            case 2:
            case 5:
                objArr[0] = "undoConfirmationPolicy";
                break;
            case 3:
            default:
                objArr[0] = "processor";
                break;
            case 6:
            case 7:
            case 8:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/command/CommandEvent";
                break;
        }
        if (i == 6) {
            objArr[1] = "getCommandProcessor";
        } else if (i == 7) {
            objArr[1] = "getCommand";
        } else if (i != 8) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/command/CommandEvent";
        } else {
            objArr[1] = "getUndoConfirmationPolicy";
        }
        if (i != 6 && i != 7 && i != 8) {
            objArr[2] = "<init>";
        }
        String str2 = String.format(str, objArr);
        if (i != 6 && i != 7 && i != 8) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CommandEvent(CommandProcessor commandProcessor, Runnable runnable, String str, Object obj, Project project, UndoConfirmationPolicy undoConfirmationPolicy, boolean z, Document document) {
        super(commandProcessor);
        if (commandProcessor == null) {
            $$$reportNull$$$0(3);
        }
        if (runnable == null) {
            $$$reportNull$$$0(4);
        }
        if (undoConfirmationPolicy == null) {
            $$$reportNull$$$0(5);
        }
        this.myCommand = runnable;
        this.myCommandName = str;
        this.myCommandGroupId = obj;
        this.myProject = project;
        this.myUndoConfirmationPolicy = undoConfirmationPolicy;
        this.myShouldRecordActionForActiveDocument = z;
        this.myDocument = document;
    }
}
