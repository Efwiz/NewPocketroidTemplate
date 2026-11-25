package org.jf.baksmali;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameters;
import com.reandroid.arsc.container.SpecTypePair;
import java.util.List;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jf.util.jcommander.ExtendedParameters;

/* JADX INFO: loaded from: classes6.dex */
@ExtendedParameters(commandAliases = {ModuleXmlParser.TYPE, "t"}, commandName = SpecTypePair.NAME_types)
@Parameters(commandDescription = "Lists the type ids in a dex file's type table.")
public class ListTypesCommand extends ListReferencesCommand {
    public ListTypesCommand(List<JCommander> list) {
        super(list, 1);
    }
}
