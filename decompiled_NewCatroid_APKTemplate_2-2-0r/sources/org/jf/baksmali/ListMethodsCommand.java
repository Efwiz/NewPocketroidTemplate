package org.jf.baksmali;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameters;
import java.util.List;
import org.jf.util.jcommander.ExtendedParameters;

/* JADX INFO: loaded from: classes6.dex */
@ExtendedParameters(commandAliases = {"method", "m"}, commandName = "methods")
@Parameters(commandDescription = "Lists the methods in a dex file's method table.")
public class ListMethodsCommand extends ListReferencesCommand {
    public ListMethodsCommand(List<JCommander> list) {
        super(list, 3);
    }
}
