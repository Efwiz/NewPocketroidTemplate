package org.jf.baksmali;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.beust.jcommander.ParametersDelegate;
import java.io.IOException;
import java.util.List;
import org.jf.baksmali.formatter.BaksmaliFormatter;
import org.jf.dexlib2.analysis.ClassProto;
import org.jf.dexlib2.dexbacked.DexBackedClassDef;
import org.jf.dexlib2.iface.reference.FieldReference;
import org.jf.util.SparseArray;
import org.jf.util.jcommander.ExtendedParameters;

/* JADX INFO: loaded from: classes6.dex */
@ExtendedParameters(commandAliases = {"fieldoffset", "fo"}, commandName = "fieldoffsets")
@Parameters(commandDescription = "Lists the instance field offsets for classes in a dex file.")
public class ListFieldOffsetsCommand extends DexInputCommand {

    @ParametersDelegate
    private AnalysisArguments analysisArguments;

    @Parameter(description = "Show usage information", help = true, names = {"-h", "-?", "--help"})
    private boolean help;

    public ListFieldOffsetsCommand(List<JCommander> list) {
        super(list);
        this.analysisArguments = new AnalysisArguments();
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
        BaksmaliOptions options = getOptions();
        BaksmaliFormatter baksmaliFormatter = new BaksmaliFormatter();
        try {
            for (DexBackedClassDef dexBackedClassDef : this.dexFile.getClasses()) {
                SparseArray<FieldReference> instanceFields = ((ClassProto) options.classPath.getClass(dexBackedClassDef)).getInstanceFields();
                System.out.write(("Class " + baksmaliFormatter.getType(dexBackedClassDef.getType()) + " : " + instanceFields.size() + " instance fields\n").getBytes());
                for (int i = 0; i < instanceFields.size(); i++) {
                    System.out.write((instanceFields.keyAt(i) + ":" + instanceFields.valueAt(i).getType() + " " + instanceFields.valueAt(i).getName() + "\n").getBytes());
                }
                System.out.write("\n".getBytes());
            }
            System.out.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private BaksmaliOptions getOptions() {
        if (this.dexFile == null) {
            throw new IllegalStateException("You must call loadDexFile first");
        }
        BaksmaliOptions baksmaliOptions = new BaksmaliOptions();
        baksmaliOptions.apiLevel = this.apiLevel;
        try {
            baksmaliOptions.classPath = this.analysisArguments.loadClassPathForDexFile(this.inputFile.getAbsoluteFile().getParentFile(), this.dexEntry, false);
        } catch (Exception e) {
            System.err.println("Error occurred while loading class path files.");
            e.printStackTrace(System.err);
            System.exit(-1);
        }
        return baksmaliOptions;
    }
}
