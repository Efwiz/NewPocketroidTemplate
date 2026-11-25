package org.jf.baksmali;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.List;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.jf.dexlib2.dexbacked.DexBackedDexFile;
import org.jf.dexlib2.dexbacked.raw.util.DexAnnotator;
import org.jf.util.ConsoleUtil;
import org.jf.util.jcommander.ExtendedParameters;

/* JADX INFO: loaded from: classes6.dex */
@ExtendedParameters(commandAliases = {"du"}, commandName = ArchiveStreamFactory.DUMP)
@Parameters(commandDescription = "Prints an annotated hex dump for the given dex file")
public class DumpCommand extends DexInputCommand {

    @Parameter(description = "Show usage information for this command.", help = true, names = {"-h", "-?", "--help"})
    private boolean help;

    public DumpCommand(List<JCommander> list) {
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
        try {
            dump(this.dexFile, System.out);
        } catch (IOException e) {
            System.err.println("There was an error while dumping the dex file");
            e.printStackTrace(System.err);
        }
    }

    public static void dump(DexBackedDexFile dexBackedDexFile, OutputStream outputStream) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
        try {
            int consoleWidth = ConsoleUtil.getConsoleWidth();
            if (consoleWidth <= 0) {
                consoleWidth = 120;
            }
            new DexAnnotator(dexBackedDexFile, consoleWidth).writeAnnotations(bufferedWriter);
        } finally {
            bufferedWriter.close();
        }
    }
}
