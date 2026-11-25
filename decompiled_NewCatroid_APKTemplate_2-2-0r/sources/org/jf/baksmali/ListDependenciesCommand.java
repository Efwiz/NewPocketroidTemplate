package org.jf.baksmali;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.jf.dexlib2.Opcodes;
import org.jf.dexlib2.dexbacked.DexBackedDexFile;
import org.jf.dexlib2.dexbacked.DexBackedOdexFile;
import org.jf.dexlib2.dexbacked.OatFile;
import org.jf.util.jcommander.Command;
import org.jf.util.jcommander.ExtendedParameter;
import org.jf.util.jcommander.ExtendedParameters;

/* JADX INFO: loaded from: classes6.dex */
@ExtendedParameters(commandAliases = {"deps", "dep"}, commandName = "dependencies")
@Parameters(commandDescription = "Lists the stored dependencies in an odex/oat file.")
public class ListDependenciesCommand extends Command {

    @Parameter(description = "Show usage information", help = true, names = {"-h", "-?", "--help"})
    private boolean help;

    @ExtendedParameter(argumentNames = {"file"})
    @Parameter(description = "An oat/odex file")
    private List<String> inputList;

    public ListDependenciesCommand(List<JCommander> list) {
        super(list);
        this.inputList = new ArrayList();
    }

    @Override // org.jf.util.jcommander.Command
    public void run() {
        List<String> list;
        BufferedInputStream bufferedInputStream;
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
        try {
            bufferedInputStream = new BufferedInputStream(new FileInputStream(str));
        } catch (FileNotFoundException unused) {
            System.err.println("Could not find file: " + str);
            System.exit(-1);
            bufferedInputStream = null;
        }
        try {
            try {
                Iterator<String> it = OatFile.fromInputStream(bufferedInputStream).getBootClassPath().iterator();
                while (it.hasNext()) {
                    System.out.println(it.next());
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (DexBackedDexFile.NotADexFile | DexBackedOdexFile.NotAnOdexFile unused2) {
                System.err.println(str + " is not an odex or oat file.");
                System.exit(-1);
            }
        } catch (IOException e2) {
            throw new RuntimeException(e2);
        } catch (OatFile.NotAnOatFileException unused3) {
            Iterator<String> it2 = DexBackedOdexFile.fromInputStream(Opcodes.getDefault(), (InputStream) bufferedInputStream).getDependencies().iterator();
            while (it2.hasNext()) {
                System.out.println(it2.next());
            }
        }
    }
}
