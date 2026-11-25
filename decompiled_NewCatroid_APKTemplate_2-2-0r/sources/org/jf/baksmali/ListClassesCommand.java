package org.jf.baksmali;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import java.util.Iterator;
import java.util.List;
import org.jf.baksmali.formatter.BaksmaliFormatter;
import org.jf.dexlib2.dexbacked.DexBackedClassDef;
import org.jf.util.jcommander.ExtendedParameters;

/* JADX INFO: loaded from: classes6.dex */
@ExtendedParameters(commandAliases = {"class", "c"}, commandName = "classes")
@Parameters(commandDescription = "Lists the classes in a dex file.")
public class ListClassesCommand extends DexInputCommand {

    @Parameter(description = "Show usage information", help = true, names = {"-h", "-?", "--help"})
    private boolean help;

    public ListClassesCommand(List<JCommander> list) {
        super(list);
    }

    @Override // org.jf.util.jcommander.Command
    public void run() {
        if (this.help || this.inputList == null || this.inputList.isEmpty()) {
            usage();
            return;
        }
        if (this.inputList.size() > 1) {
            System.err.println("Too many files specified");
            usage();
            return;
        }
        loadDexFile(this.inputList.get(0));
        BaksmaliFormatter baksmaliFormatter = new BaksmaliFormatter();
        Iterator<? extends DexBackedClassDef> it = this.dexFile.getClasses().iterator();
        while (it.hasNext()) {
            System.out.println(baksmaliFormatter.getType(it.next().getType()));
        }
    }
}
