package org.jf.baksmali;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.jf.dexlib2.DexFileFactory;
import org.jf.dexlib2.Opcodes;
import org.jf.util.jcommander.Command;
import org.jf.util.jcommander.ExtendedParameter;
import org.jf.util.jcommander.ExtendedParameters;

/* JADX INFO: loaded from: classes6.dex */
@ExtendedParameters(commandAliases = {"d"}, commandName = "dex")
@Parameters(commandDescription = "Lists the dex files in an apk/oat file.")
public class ListDexCommand extends Command {

    @Parameter(description = "Show usage information", help = true, names = {"-h", "-?", "--help"})
    private boolean help;

    @ExtendedParameter(argumentNames = {"file"})
    @Parameter(description = "An apk or oat file.")
    private List<String> inputList;

    public ListDexCommand(List<JCommander> list) {
        super(list);
        this.inputList = new ArrayList();
    }

    @Override // org.jf.util.jcommander.Command
    public void run() {
        List<String> list;
        if (this.help || (list = this.inputList) == null || list.isEmpty()) {
            usage();
            return;
        }
        if (this.inputList.size() > 1) {
            System.err.println("Too many files specified");
            usage();
            return;
        }
        String str = this.inputList.get(0);
        File file = new File(str);
        if (!file.exists()) {
            System.err.println(String.format("Could not find the file: %s", str));
            System.exit(-1);
        }
        try {
            Iterator<String> it = DexFileFactory.loadDexContainer(file, Opcodes.getDefault()).getDexEntryNames().iterator();
            while (it.hasNext()) {
                System.out.println(it.next());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
