package org.jf.baksmali;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import java.util.List;
import org.jf.util.ConsoleUtil;
import org.jf.util.collection.Iterables;
import org.jf.util.jcommander.Command;
import org.jf.util.jcommander.ExtendedCommands;
import org.jf.util.jcommander.ExtendedParameter;
import org.jf.util.jcommander.ExtendedParameters;
import org.jf.util.jcommander.HelpFormatter;

/* JADX INFO: loaded from: classes6.dex */
@ExtendedParameters(commandAliases = {"h"}, commandName = "help")
@Parameters(commandDescription = "Shows usage information")
public class ListHelpCommand extends Command {

    @ExtendedParameter(argumentNames = {"commands"})
    @Parameter(description = "If specified, show the detailed usage information for the given commands")
    private List<String> commands;

    public ListHelpCommand(List<JCommander> list) {
        super(list);
    }

    @Override // org.jf.util.jcommander.Command
    public void run() {
        List<String> list = this.commands;
        if (list == null || list.isEmpty()) {
            System.out.println(new HelpFormatter().width(ConsoleUtil.getConsoleWidth()).format(this.commandAncestors));
            return;
        }
        JCommander jCommander = (JCommander) Iterables.getLast(this.commandAncestors);
        boolean z = false;
        for (String str : this.commands) {
            JCommander subcommand = ExtendedCommands.getSubcommand(jCommander, str);
            if (subcommand == null) {
                System.err.println("No such command: " + str);
            } else {
                System.out.println(new HelpFormatter().width(ConsoleUtil.getConsoleWidth()).format(((Command) subcommand.getObjects().get(0)).getCommandHierarchy()));
                z = true;
            }
        }
        if (z) {
            return;
        }
        System.out.println(new HelpFormatter().width(ConsoleUtil.getConsoleWidth()).format(this.commandAncestors));
    }

    @ExtendedParameters(commandName = "hlep")
    @Parameters(hidden = true)
    public static class ListHlepCommand extends ListHelpCommand {
        public ListHlepCommand(List<JCommander> list) {
            super(list);
        }
    }
}
