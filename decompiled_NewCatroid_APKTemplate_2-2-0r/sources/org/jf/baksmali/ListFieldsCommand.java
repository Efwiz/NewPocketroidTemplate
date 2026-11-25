package org.jf.baksmali;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameters;
import java.util.List;
import org.jf.util.jcommander.ExtendedParameters;

/* JADX INFO: loaded from: classes6.dex */
@ExtendedParameters(commandAliases = {"field", "f"}, commandName = "fields")
@Parameters(commandDescription = "Lists the fields in a dex file's field table.")
public class ListFieldsCommand extends ListReferencesCommand {
    public ListFieldsCommand(List<JCommander> list) {
        super(list, 2);
    }
}
