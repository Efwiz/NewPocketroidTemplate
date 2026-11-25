package org.jf.baksmali;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import java.util.List;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.jf.util.jcommander.Command;
import org.jf.util.jcommander.ExtendedCommands;
import org.jf.util.jcommander.ExtendedParameters;

/* JADX INFO: loaded from: classes6.dex */
@ExtendedParameters(commandAliases = {"l"}, commandName = SchemaSymbols.ATTVAL_LIST)
@Parameters(commandDescription = "Lists various objects in a dex file.")
public class ListCommand extends Command {

    @Parameter(description = "Show usage information", help = true, names = {"-h", "-?", "--help"})
    private boolean help;

    public ListCommand(List<JCommander> list) {
        super(list);
    }

    @Override // org.jf.util.jcommander.Command
    protected void setupCommand(JCommander jCommander) {
        List<JCommander> commandHierarchy = getCommandHierarchy();
        ExtendedCommands.addExtendedCommand(jCommander, new ListStringsCommand(commandHierarchy));
        ExtendedCommands.addExtendedCommand(jCommander, new ListMethodsCommand(commandHierarchy));
        ExtendedCommands.addExtendedCommand(jCommander, new ListFieldsCommand(commandHierarchy));
        ExtendedCommands.addExtendedCommand(jCommander, new ListTypesCommand(commandHierarchy));
        ExtendedCommands.addExtendedCommand(jCommander, new ListClassesCommand(commandHierarchy));
        ExtendedCommands.addExtendedCommand(jCommander, new ListDexCommand(commandHierarchy));
        ExtendedCommands.addExtendedCommand(jCommander, new ListVtablesCommand(commandHierarchy));
        ExtendedCommands.addExtendedCommand(jCommander, new ListFieldOffsetsCommand(commandHierarchy));
        ExtendedCommands.addExtendedCommand(jCommander, new ListDependenciesCommand(commandHierarchy));
        ExtendedCommands.addExtendedCommand(jCommander, new ListHelpCommand(commandHierarchy));
        ExtendedCommands.addExtendedCommand(jCommander, new ListHelpCommand.ListHlepCommand(commandHierarchy));
    }

    @Override // org.jf.util.jcommander.Command
    public void run() {
        JCommander jCommander = getJCommander();
        if (this.help || jCommander.getParsedCommand() == null) {
            usage();
        } else {
            ((Command) jCommander.getCommands().get(jCommander.getParsedCommand()).getObjects().get(0)).run();
        }
    }
}
