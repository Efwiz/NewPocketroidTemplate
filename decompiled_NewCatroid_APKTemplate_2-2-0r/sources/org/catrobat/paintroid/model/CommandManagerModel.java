package org.catrobat.paintroid.model;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.paintroid.command.Command;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: CommandManagerModel.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u001b\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005¢\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J\u000f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005HÆ\u0003J#\u0010\r\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005HÆ\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001J\t\u0010\u0013\u001a\u00020\u0014HÖ\u0001R\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0015"}, d2 = {"Lorg/catrobat/paintroid/model/CommandManagerModel;", "", "initialCommand", "Lorg/catrobat/paintroid/command/Command;", "commands", "", "(Lorg/catrobat/paintroid/command/Command;Ljava/util/List;)V", "getCommands", "()Ljava/util/List;", "getInitialCommand", "()Lorg/catrobat/paintroid/command/Command;", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final /* data */ class CommandManagerModel {
    private final List<Command> commands;
    private final Command initialCommand;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ CommandManagerModel copy$default(CommandManagerModel commandManagerModel, Command command, List list, int i, Object obj) {
        if ((i & 1) != 0) {
            command = commandManagerModel.initialCommand;
        }
        if ((i & 2) != 0) {
            list = commandManagerModel.commands;
        }
        return commandManagerModel.copy(command, list);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final Command getInitialCommand() {
        return this.initialCommand;
    }

    public final List<Command> component2() {
        return this.commands;
    }

    public final CommandManagerModel copy(Command initialCommand, List<Command> commands) {
        Intrinsics.checkNotNullParameter(initialCommand, "initialCommand");
        Intrinsics.checkNotNullParameter(commands, "commands");
        return new CommandManagerModel(initialCommand, commands);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof CommandManagerModel)) {
            return false;
        }
        CommandManagerModel commandManagerModel = (CommandManagerModel) other;
        return Intrinsics.areEqual(this.initialCommand, commandManagerModel.initialCommand) && Intrinsics.areEqual(this.commands, commandManagerModel.commands);
    }

    public int hashCode() {
        return (this.initialCommand.hashCode() * 31) + this.commands.hashCode();
    }

    public String toString() {
        return "CommandManagerModel(initialCommand=" + this.initialCommand + ", commands=" + this.commands + ')';
    }

    public CommandManagerModel(Command initialCommand, List<Command> commands) {
        Intrinsics.checkNotNullParameter(initialCommand, "initialCommand");
        Intrinsics.checkNotNullParameter(commands, "commands");
        this.initialCommand = initialCommand;
        this.commands = commands;
    }

    public final List<Command> getCommands() {
        return this.commands;
    }

    public final Command getInitialCommand() {
        return this.initialCommand;
    }
}
