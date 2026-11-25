package org.jf.baksmali;

import com.android.dx.rop.code.RegisterSpec;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.beust.jcommander.ParametersDelegate;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import org.jf.dexlib2.AccessFlags;
import org.jf.dexlib2.analysis.ClassProto;
import org.jf.dexlib2.dexbacked.DexBackedClassDef;
import org.jf.dexlib2.iface.Method;
import org.jf.util.jcommander.ExtendedParameter;
import org.jf.util.jcommander.ExtendedParameters;

/* JADX INFO: loaded from: classes6.dex */
@ExtendedParameters(commandAliases = {"vtable", RegisterSpec.PREFIX}, commandName = "vtables")
@Parameters(commandDescription = "Lists the virtual method tables for classes in a dex file.")
public class ListVtablesCommand extends DexInputCommand {

    @ParametersDelegate
    private AnalysisArguments analysisArguments;

    @ParametersDelegate
    private AnalysisArguments.CheckPackagePrivateArgument checkPackagePrivateArgument;

    @ExtendedParameter(argumentNames = {"classes"})
    @Parameter(description = "A comma separated list of classes. Only print the vtable for these classes", names = {"--classes"})
    private List<String> classes;

    @Parameter(description = "Show usage information", help = true, names = {"-h", "-?", "--help"})
    private boolean help;

    @Parameter(description = "Uses a classpath for the given oat version, regardless of the actual oat version. This can be used, e.g. to list vtables from a dex file, as if they were in an oat file of the given version.", names = {"--override-oat-version"})
    private int oatVersion;

    public ListVtablesCommand(List<JCommander> list) {
        super(list);
        this.analysisArguments = new AnalysisArguments();
        this.checkPackagePrivateArgument = new AnalysisArguments.CheckPackagePrivateArgument();
        this.classes = null;
        this.oatVersion = 0;
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
        if (options == null) {
            return;
        }
        try {
            List<String> list = this.classes;
            if (list != null && !list.isEmpty()) {
                Iterator<String> it = this.classes.iterator();
                while (it.hasNext()) {
                    listClassVtable((ClassProto) options.classPath.getClass(it.next()));
                }
                return;
            }
            for (DexBackedClassDef dexBackedClassDef : this.dexFile.getClasses()) {
                if (!AccessFlags.INTERFACE.isSet(dexBackedClassDef.getAccessFlags())) {
                    listClassVtable((ClassProto) options.classPath.getClass(dexBackedClassDef));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void listClassVtable(ClassProto classProto) throws IOException {
        List<Method> vtable = classProto.getVtable();
        System.out.write(("Class " + classProto.getType() + " extends " + classProto.getSuperclass() + " : " + vtable.size() + " methods\n").getBytes());
        for (int i = 0; i < vtable.size(); i++) {
            Method method = vtable.get(i);
            String str = i + ":" + method.getDefiningClass() + "->" + method.getName() + "(";
            Iterator<? extends CharSequence> it = method.getParameterTypes().iterator();
            while (it.hasNext()) {
                str = str + ((Object) it.next());
            }
            System.out.write((str + Constant.AFTER_QUTO + method.getReturnType() + "\n").getBytes());
        }
        System.out.write("\n".getBytes());
    }

    protected BaksmaliOptions getOptions() {
        if (this.dexFile == null) {
            throw new IllegalStateException("You must call loadDexFile first");
        }
        BaksmaliOptions baksmaliOptions = new BaksmaliOptions();
        baksmaliOptions.apiLevel = this.apiLevel;
        try {
            baksmaliOptions.classPath = this.analysisArguments.loadClassPathForDexFile(this.inputFile.getAbsoluteFile().getParentFile(), this.dexEntry, this.checkPackagePrivateArgument.checkPackagePrivateAccess, this.oatVersion);
            return baksmaliOptions;
        } catch (Exception e) {
            System.err.println("Error occurred while loading class path files.");
            e.printStackTrace(System.err);
            return null;
        }
    }
}
