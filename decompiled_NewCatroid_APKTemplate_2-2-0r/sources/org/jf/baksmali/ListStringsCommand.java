package org.jf.baksmali;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameters;
import java.util.List;
import org.jf.util.jcommander.ExtendedParameters;

/* JADX INFO: loaded from: classes6.dex */
@ExtendedParameters(commandAliases = {"string", "str", "s"}, commandName = "strings")
@Parameters(commandDescription = "Lists the strings in a dex file's string table.")
public class ListStringsCommand extends ListReferencesCommand {
    public ListStringsCommand(List<JCommander> list) {
        super(list, 0);
    }
}
