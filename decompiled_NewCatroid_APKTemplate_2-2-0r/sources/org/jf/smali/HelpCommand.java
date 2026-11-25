package org.jf.smali;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import java.util.List;
import org.jf.util.ConsoleUtil;
import org.jf.util.jcommander.Command;
import org.jf.util.jcommander.ExtendedCommands;
import org.jf.util.jcommander.ExtendedParameter;
import org.jf.util.jcommander.ExtendedParameters;
import org.jf.util.jcommander.HelpFormatter;

/* JADX INFO: loaded from: classes6.dex */
@ExtendedParameters(commandAliases = {"h"}, commandName = "help")
@Parameters(commandDescription = "Shows usage information")
public class HelpCommand extends Command {

    @ExtendedParameter(argumentNames = {"commands"})
    @Parameter(description = "If specified, show the detailed usage information for the given commands")
    private List<String> commands;

    public HelpCommand(List<JCommander> list) {
        super(list);
    }

    @Override // org.jf.util.jcommander.Command
    public void run() {
        JCommander jCommander = this.commandAncestors.get(this.commandAncestors.size() - 1);
        List<String> list = this.commands;
        if (list == null || list.isEmpty()) {
            System.out.println(new HelpFormatter().width(ConsoleUtil.getConsoleWidth()).format(this.commandAncestors));
            return;
        }
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
    public static class HlepCommand extends HelpCommand {
        public HlepCommand(List<JCommander> list) {
            super(list);
        }
    }
}
