package org.jetbrains.kotlin.com.intellij.openapi.command.impl;

import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import java.util.Iterator;
import java.util.List;
import name.antonsmirnov.firmata.FormatHelper;
import org.jetbrains.kotlin.com.intellij.openapi.application.ApplicationManager;
import org.jetbrains.kotlin.com.intellij.openapi.command.CommandEvent;
import org.jetbrains.kotlin.com.intellij.openapi.command.CommandListener;
import org.jetbrains.kotlin.com.intellij.openapi.command.CommandProcessorEx;
import org.jetbrains.kotlin.com.intellij.openapi.command.CommandToken;
import org.jetbrains.kotlin.com.intellij.openapi.command.UndoConfirmationPolicy;
import org.jetbrains.kotlin.com.intellij.openapi.editor.Document;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;
import org.jetbrains.kotlin.com.intellij.util.containers.Stack;
import org.jetbrains.kotlin.com.intellij.util.messages.MessageBus;

/* JADX INFO: loaded from: classes3.dex */
public class CoreCommandProcessor extends CommandProcessorEx {
    private final CommandListener eventPublisher;
    protected CommandDescriptor myCurrentCommand;
    private final Stack<CommandDescriptor> myInterruptedCommands = new Stack<>();
    private final List<CommandListener> myListeners = ContainerUtil.createLockFreeCopyOnWriteList();
    private int myUndoTransparentCount;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        switch (i) {
            case 2:
            case 4:
            case 6:
            case 8:
            case 11:
                objArr[0] = "command";
                break;
            case 3:
            case 5:
            case 7:
            case 9:
            case 10:
                objArr[0] = "undoConfirmationPolicy";
                break;
            case 12:
            case 13:
                objArr[0] = ServiceSpecificExtraArgs.CastExtraArgs.LISTENER;
                break;
            case 14:
            case 17:
                objArr[0] = "action";
                break;
            case 15:
                objArr[0] = "docs";
                break;
            case 16:
                objArr[0] = "files";
                break;
            default:
                objArr[0] = "runnable";
                break;
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/command/impl/CoreCommandProcessor";
        switch (i) {
            case 10:
                objArr[2] = "startCommand";
                break;
            case 11:
                objArr[2] = "finishCommand";
                break;
            case 12:
                objArr[2] = "addCommandListener";
                break;
            case 13:
                objArr[2] = "removeCommandListener";
                break;
            case 14:
                objArr[2] = "runUndoTransparentAction";
                break;
            case 15:
                objArr[2] = "addAffectedDocuments";
                break;
            case 16:
                objArr[2] = "addAffectedFiles";
                break;
            case 17:
                objArr[2] = "allowMergeGlobalCommands";
                break;
            default:
                objArr[2] = "executeCommand";
                break;
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    private static class CommandDescriptor implements CommandToken {
        public final Runnable myCommand;
        public final Document myDocument;
        public Object myGroupId;
        public String myName;
        public final Project myProject;
        final boolean myShouldRecordActionForActiveDocument;
        final UndoConfirmationPolicy myUndoConfirmationPolicy;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            Object[] objArr = new Object[3];
            if (i != 1) {
                objArr[0] = "command";
            } else {
                objArr[0] = "undoConfirmationPolicy";
            }
            objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/command/impl/CoreCommandProcessor$CommandDescriptor";
            objArr[2] = "<init>";
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        CommandDescriptor(Runnable runnable, Project project, String str, Object obj, UndoConfirmationPolicy undoConfirmationPolicy, boolean z, Document document) {
            if (runnable == null) {
                $$$reportNull$$$0(0);
            }
            if (undoConfirmationPolicy == null) {
                $$$reportNull$$$0(1);
            }
            this.myCommand = runnable;
            this.myProject = project;
            this.myName = str;
            this.myGroupId = obj;
            this.myUndoConfirmationPolicy = undoConfirmationPolicy;
            this.myShouldRecordActionForActiveDocument = z;
            this.myDocument = document;
        }

        public String toString() {
            return FormatHelper.QUOTE + this.myName + "', group: '" + this.myGroupId + FormatHelper.QUOTE;
        }
    }

    public CoreCommandProcessor() {
        MessageBus messageBus = ApplicationManager.getApplication().getMessageBus();
        messageBus.simpleConnect().subscribe(CommandListener.TOPIC, new CommandListener() { // from class: org.jetbrains.kotlin.com.intellij.openapi.command.impl.CoreCommandProcessor.1
            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                Object[] objArr = new Object[3];
                objArr[0] = "event";
                objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/command/impl/CoreCommandProcessor$1";
                if (i == 1) {
                    objArr[2] = "beforeCommandFinished";
                } else if (i != 2) {
                    objArr[2] = "commandStarted";
                } else {
                    objArr[2] = "commandFinished";
                }
                throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
            }

            @Override // org.jetbrains.kotlin.com.intellij.openapi.command.CommandListener
            public void commandStarted(CommandEvent commandEvent) {
                if (commandEvent == null) {
                    $$$reportNull$$$0(0);
                }
                Iterator it = CoreCommandProcessor.this.myListeners.iterator();
                while (it.hasNext()) {
                    try {
                        ((CommandListener) it.next()).commandStarted(commandEvent);
                    } catch (Throwable th) {
                        CommandLog.LOG.error(th);
                    }
                }
            }

            @Override // org.jetbrains.kotlin.com.intellij.openapi.command.CommandListener
            public void beforeCommandFinished(CommandEvent commandEvent) {
                if (commandEvent == null) {
                    $$$reportNull$$$0(1);
                }
                Iterator it = CoreCommandProcessor.this.myListeners.iterator();
                while (it.hasNext()) {
                    try {
                        ((CommandListener) it.next()).beforeCommandFinished(commandEvent);
                    } catch (Throwable th) {
                        CommandLog.LOG.error(th);
                    }
                }
            }

            @Override // org.jetbrains.kotlin.com.intellij.openapi.command.CommandListener
            public void commandFinished(CommandEvent commandEvent) {
                if (commandEvent == null) {
                    $$$reportNull$$$0(2);
                }
                Iterator it = CoreCommandProcessor.this.myListeners.iterator();
                while (it.hasNext()) {
                    try {
                        ((CommandListener) it.next()).commandFinished(commandEvent);
                    } catch (Throwable th) {
                        CommandLog.LOG.error(th);
                    }
                }
            }
        });
        this.eventPublisher = (CommandListener) messageBus.syncPublisher(CommandListener.TOPIC);
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.command.CommandProcessor
    public void executeCommand(Project project, Runnable runnable, String str, Object obj) {
        if (runnable == null) {
            $$$reportNull$$$0(0);
        }
        executeCommand(project, runnable, str, obj, UndoConfirmationPolicy.DEFAULT);
    }

    public void executeCommand(Project project, Runnable runnable, String str, Object obj, UndoConfirmationPolicy undoConfirmationPolicy) {
        if (runnable == null) {
            $$$reportNull$$$0(2);
        }
        if (undoConfirmationPolicy == null) {
            $$$reportNull$$$0(3);
        }
        executeCommand(project, runnable, str, obj, undoConfirmationPolicy, null);
    }

    public void executeCommand(Project project, Runnable runnable, String str, Object obj, UndoConfirmationPolicy undoConfirmationPolicy, Document document) {
        if (runnable == null) {
            $$$reportNull$$$0(4);
        }
        if (undoConfirmationPolicy == null) {
            $$$reportNull$$$0(5);
        }
        executeCommand(project, runnable, str, obj, undoConfirmationPolicy, true, document);
    }

    public void executeCommand(Project project, Runnable runnable, String str, Object obj, UndoConfirmationPolicy undoConfirmationPolicy, boolean z, Document document) {
        if (runnable == null) {
            $$$reportNull$$$0(8);
        }
        if (undoConfirmationPolicy == null) {
            $$$reportNull$$$0(9);
        }
        ApplicationManager.getApplication().assertIsWriteThread();
        if (CommandLog.LOG.isDebugEnabled()) {
            CommandLog.LOG.debug("executeCommand: " + runnable + ", name = " + str + ", groupId = " + obj + ", in command = " + (this.myCurrentCommand != null) + ", in transparent action = " + isUndoTransparentActionInProgress());
        }
        if (project != null && project.isDisposed()) {
            CommandLog.LOG.error("Project " + project + " already disposed");
            return;
        }
        if (this.myCurrentCommand != null) {
            runnable.run();
            return;
        }
        CommandDescriptor commandDescriptor = new CommandDescriptor(runnable, project, str, obj, undoConfirmationPolicy, z, document);
        try {
            this.myCurrentCommand = commandDescriptor;
            fireCommandStarted();
            runnable.run();
            th = null;
        } catch (Throwable th) {
            th = th;
        }
        finishCommand(commandDescriptor, th);
    }

    public void finishCommand(CommandToken commandToken, Throwable th) {
        if (commandToken == null) {
            $$$reportNull$$$0(11);
        }
        ApplicationManager.getApplication().assertIsWriteThread();
        CommandLog.LOG.assertTrue(this.myCurrentCommand != null, "no current command in progress");
        fireCommandFinished();
    }

    private void fireCommandFinished() {
        ApplicationManager.getApplication().assertIsWriteThread();
        CommandDescriptor commandDescriptor = this.myCurrentCommand;
        CommandEvent commandEvent = new CommandEvent(this, commandDescriptor.myCommand, commandDescriptor.myName, commandDescriptor.myGroupId, commandDescriptor.myProject, commandDescriptor.myUndoConfirmationPolicy, commandDescriptor.myShouldRecordActionForActiveDocument, commandDescriptor.myDocument);
        CommandListener commandListener = this.eventPublisher;
        try {
            commandListener.beforeCommandFinished(commandEvent);
        } finally {
            this.myCurrentCommand = null;
            commandListener.commandFinished(commandEvent);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.command.CommandProcessor
    public Runnable getCurrentCommand() {
        CommandDescriptor commandDescriptor = this.myCurrentCommand;
        if (commandDescriptor != null) {
            return commandDescriptor.myCommand;
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.command.CommandProcessor
    public boolean isUndoTransparentActionInProgress() {
        return this.myUndoTransparentCount > 0;
    }

    private void fireCommandStarted() {
        ApplicationManager.getApplication().assertIsWriteThread();
        CommandDescriptor commandDescriptor = this.myCurrentCommand;
        this.eventPublisher.commandStarted(new CommandEvent(this, commandDescriptor.myCommand, commandDescriptor.myName, commandDescriptor.myGroupId, commandDescriptor.myProject, commandDescriptor.myUndoConfirmationPolicy, commandDescriptor.myShouldRecordActionForActiveDocument, commandDescriptor.myDocument));
    }
}
