package org.jf.baksmali;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.beust.jcommander.ParametersDelegate;
import com.beust.jcommander.validators.PositiveInteger;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.jf.dexlib2.util.SyntheticAccessorResolver;
import org.jf.util.ConsoleUtil;
import org.jf.util.StringWrapper;
import org.jf.util.jcommander.ExtendedParameter;
import org.jf.util.jcommander.ExtendedParameters;
import org.xml.sax.SAXException;

/* JADX INFO: loaded from: classes6.dex */
@ExtendedParameters(commandAliases = {"dis", "d"}, commandName = "disassemble")
@Parameters(commandDescription = "Disassembles a dex file.")
public class DisassembleCommand extends DexInputCommand {
    static final /* synthetic */ boolean $assertionsDisabled = false;

    @ExtendedParameter(argumentNames = {"boolean"})
    @Parameter(arity = 1, description = "Generate helper comments for synthetic accessors. True by default, use --accessor-comments=false to disable.", names = {"--accessor-comments", "--ac"})
    private boolean accessorComments;

    @Parameter(description = "Allows odex opcodes to be disassembled, even if the result won't be able to be reassembled.", names = {"--allow-odex-opcodes"})
    private boolean allowOdex;

    @ParametersDelegate
    protected AnalysisArguments analysisArguments;

    @ExtendedParameter(argumentNames = {"classes"})
    @Parameter(description = "A comma separated list of classes. Only disassemble these classes", names = {"--classes"})
    private List<String> classes;

    @Parameter(description = "Add a comment before each instruction with it's code offset within the method.", names = {"--code-offsets", "--offsets", "--off"})
    private boolean codeOffsets;

    @ExtendedParameter(argumentNames = {"boolean"})
    @Parameter(arity = 1, description = "Whether to include debug information in the output (.local, .param, .line, etc.). True by default, use --debug-info=false to disable.", names = {"--debug-info", "--di"})
    private boolean debugInfo;

    @Parameter(description = "Dump markers to file", names = {"--dump-markers", "--markers"})
    private boolean dumpMarkers;

    @Parameter(description = "Show usage information for this command.", help = true, names = {"-h", "-?", "--help"})
    private boolean help;

    @Parameter(description = "Use implicit method and field references (without the class name) for methods and fields from the current class.", names = {"--implicit-references", "--implicit", "--ir"})
    private boolean implicitReferences;

    @ExtendedParameter(argumentNames = {"n"})
    @Parameter(description = "The number of threads to use. Defaults to the number of cores available.", names = {"-j", "--jobs"}, validateWith = PositiveInteger.class)
    private int jobs;

    @Parameter(description = "When disassembling, output the .locals directive with the number of non-parameter registers instead of the .registers directive with the total number of registers.", names = {"-l", "--use-locals"})
    private boolean localsDirective;

    @Parameter(description = "Normalize virtual method references to use the base class where the method is originally declared.", names = {"--normalize-virtual-methods", "--norm", "--nvm"})
    private boolean normalizeVirtualMethods;

    @ExtendedParameter(argumentNames = {"dir"})
    @Parameter(description = "The directory to write the disassembled files to.", names = {"-o", "--output"})
    private String outputDir;

    @ExtendedParameter(argumentNames = {"boolean"})
    @Parameter(arity = 1, description = "Use the pNN syntax for registers that refer to a method parameter on method entry. True by default, use --parameter-registers=false to disable.", names = {"--parameter-registers", "--preg", "--pr"})
    private boolean parameterRegisters;

    @ExtendedParameter(argumentNames = {"register info specifier"})
    @Parameter(description = "Add comments before/after each instruction with information about register types. The value is a comma-separated list of any of ALL, ALLPRE, ALLPOST, ARGS, DEST, MERGE and FULLMERGE. See \"baksmali help register-info\" for more information.", names = {"-r", "--register-info"})
    private List<String> registerInfoTypes;

    @ExtendedParameter(argumentNames = {"resource prefix", "public.xml file"})
    @Parameter(arity = 2, description = "This will attempt to find any resource id references within the bytecode and add a comment with the name of the resource being referenced. The parameter accepts 2 values:an arbitrary resource prefix and the path to a public.xml file. For example: --resolve-resources android.R framework/res/values/public.xml. This option can be specified multiple times to provide resources from multiple packages.", names = {"--resolve-resources", "--rr"})
    private List<String> resourceIdFiles;

    @Parameter(description = "Create label names using a sequential numbering scheme per label type, rather than using the bytecode address.", names = {"--sequential-labels", "--seq", "--sl"})
    private boolean sequentialLabels;

    protected boolean shouldCheckPackagePrivateAccess() {
        return false;
    }

    protected boolean showDeodexWarning() {
        return true;
    }

    public DisassembleCommand(List<JCommander> list) {
        super(list);
        this.analysisArguments = new AnalysisArguments();
        this.debugInfo = true;
        this.codeOffsets = false;
        this.resourceIdFiles = new ArrayList();
        this.jobs = Runtime.getRuntime().availableProcessors();
        this.localsDirective = false;
        this.accessorComments = true;
        this.normalizeVirtualMethods = false;
        this.outputDir = "out";
        this.parameterRegisters = true;
        this.registerInfoTypes = new ArrayList();
        this.sequentialLabels = false;
        this.implicitReferences = false;
        this.allowOdex = false;
        this.classes = null;
        this.dumpMarkers = false;
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
        if (showDeodexWarning() && this.dexFile.supportsOptimizedOpcodes()) {
            StringWrapper.printWrappedString(System.err, "Warning: You are disassembling an odex/oat file without deodexing it. You won't be able to re-assemble the results unless you deodex it. See \"baksmali help deodex\"", ConsoleUtil.getConsoleWidth());
        }
        File file = new File(this.outputDir);
        if (!file.exists() && !file.mkdirs()) {
            System.err.println("Can't create the output directory " + this.outputDir);
            System.exit(-1);
        }
        if (this.analysisArguments.classPathDirectories == null || this.analysisArguments.classPathDirectories.isEmpty()) {
            this.analysisArguments.classPathDirectories = new ArrayList(1);
            this.analysisArguments.classPathDirectories.add(this.inputFile.getAbsoluteFile().getParent());
        }
        if (Baksmali.disassembleDexFile(this.dexFile, file, this.jobs, getOptions(), this.classes)) {
            return;
        }
        System.exit(-1);
    }

    protected boolean needsClassPath() {
        return !this.registerInfoTypes.isEmpty() || this.normalizeVirtualMethods;
    }

    protected BaksmaliOptions getOptions() {
        if (this.dexFile == null) {
            throw new IllegalStateException("You must call loadDexFile first");
        }
        BaksmaliOptions baksmaliOptions = new BaksmaliOptions();
        if (needsClassPath()) {
            try {
                baksmaliOptions.classPath = this.analysisArguments.loadClassPathForDexFile(this.inputFile.getAbsoluteFile().getParentFile(), this.dexEntry, shouldCheckPackagePrivateAccess());
            } catch (Exception e) {
                System.err.println("\n\nError occurred while loading class path files. Aborting.");
                e.printStackTrace(System.err);
                System.exit(-1);
            }
        }
        if (!this.resourceIdFiles.isEmpty()) {
            HashMap map = new HashMap();
            for (int i = 0; i < this.resourceIdFiles.size(); i += 2) {
                String str = this.resourceIdFiles.get(i);
                File file = new File(this.resourceIdFiles.get(i + 1));
                if (!file.exists()) {
                    System.err.println(String.format("Can't find file: %s", file));
                    System.exit(-1);
                }
                map.put(str, file);
            }
            try {
                baksmaliOptions.loadResourceIds(map);
            } catch (IOException e2) {
                System.err.println("Error while loading resource files:");
                e2.printStackTrace(System.err);
                System.exit(-1);
            } catch (SAXException e3) {
                System.err.println("Error while loading resource files:");
                e3.printStackTrace(System.err);
                System.exit(-1);
            }
        }
        baksmaliOptions.parameterRegisters = this.parameterRegisters;
        baksmaliOptions.localsDirective = this.localsDirective;
        baksmaliOptions.sequentialLabels = this.sequentialLabels;
        baksmaliOptions.debugInfo = this.debugInfo;
        baksmaliOptions.codeOffsets = this.codeOffsets;
        baksmaliOptions.accessorComments = this.accessorComments;
        baksmaliOptions.implicitReferences = this.implicitReferences;
        baksmaliOptions.normalizeVirtualMethods = this.normalizeVirtualMethods;
        baksmaliOptions.registerInfo = 0;
        for (String str2 : this.registerInfoTypes) {
            if (str2.equalsIgnoreCase("ALL")) {
                baksmaliOptions.registerInfo |= 1;
            } else if (str2.equalsIgnoreCase("ALLPRE")) {
                baksmaliOptions.registerInfo |= 2;
            } else if (str2.equalsIgnoreCase("ALLPOST")) {
                baksmaliOptions.registerInfo |= 4;
            } else if (str2.equalsIgnoreCase("ARGS")) {
                baksmaliOptions.registerInfo |= 8;
            } else if (str2.equalsIgnoreCase("DEST")) {
                baksmaliOptions.registerInfo |= 16;
            } else if (str2.equalsIgnoreCase("MERGE")) {
                baksmaliOptions.registerInfo |= 32;
            } else if (str2.equalsIgnoreCase("FULLMERGE")) {
                baksmaliOptions.registerInfo |= 64;
            } else {
                System.err.println(String.format("Invalid register info type: %s", str2));
                usage();
                System.exit(-1);
            }
            if ((baksmaliOptions.registerInfo & 64) != 0) {
                baksmaliOptions.registerInfo &= -33;
            }
        }
        if (this.accessorComments) {
            baksmaliOptions.syntheticAccessorResolver = new SyntheticAccessorResolver(this.dexFile.getOpcodes(), this.dexFile.getClasses());
        }
        if (this.allowOdex) {
            baksmaliOptions.allowOdex = true;
        }
        baksmaliOptions.dumpMarkers = this.dumpMarkers;
        return baksmaliOptions;
    }
}
