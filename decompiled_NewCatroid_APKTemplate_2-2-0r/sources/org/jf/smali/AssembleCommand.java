package org.jf.smali;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.beust.jcommander.validators.PositiveInteger;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import org.jf.dexlib2.extra.DexMarker;
import org.jf.util.jcommander.Command;
import org.jf.util.jcommander.ExtendedParameter;
import org.jf.util.jcommander.ExtendedParameters;

/* JADX INFO: loaded from: classes6.dex */
@ExtendedParameters(commandAliases = {"ass", "as", "a"}, commandName = "assemble")
@Parameters(commandDescription = "Assembles smali files into a dex file.")
public class AssembleCommand extends Command {

    @Parameter(description = "Allows the odex opcodes that dalvik doesn't reject to be assembled.", names = {"--allow-odex-opcodes", "--allow-odex", "--ao"})
    private boolean allowOdexOpcodes;

    @ExtendedParameter(argumentNames = {"api"})
    @Parameter(description = "The numeric api level to use while assembling.", names = {"-a", "--api"})
    private int apiLevel;

    @Parameter(description = "Show usage information for this command.", help = true, names = {"-h", "-?", "--help"})
    private boolean help;

    @ExtendedParameter(argumentNames = {"[<file>|<dir>]+"})
    @Parameter(description = "Assembles the given files. If a directory is specified, it will be recursively searched for any files with a .smali prefix")
    private List<String> input;

    @ExtendedParameter(argumentNames = {"n"})
    @Parameter(description = "The number of threads to use. Defaults to the number of cores available.", names = {"-j", "--jobs"}, validateWith = PositiveInteger.class)
    private int jobs;

    @ExtendedParameter(argumentNames = {"file"})
    @Parameter(description = "The name/path of file containing line separated markers", names = {"-markers", "--markers"})
    private String markers;

    @ExtendedParameter(argumentNames = {"file"})
    @Parameter(description = "The name/path of the dex file to write.", names = {"-o", "--output"})
    private String output;

    @Parameter(description = "Generate verbose error messages.", names = {"--verbose"})
    private boolean verbose;

    public AssembleCommand(List<JCommander> list) {
        super(list);
        this.jobs = Runtime.getRuntime().availableProcessors();
        this.apiLevel = 15;
        this.output = "out.dex";
        this.verbose = false;
    }

    @Override // org.jf.util.jcommander.Command
    public void run() {
        List<String> list;
        if (this.help || (list = this.input) == null || list.isEmpty()) {
            usage();
            return;
        }
        try {
            Smali.assemble(getOptions(), this.input);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String getMarkers() {
        String str = this.markers;
        if (str == null) {
            Iterator<String> it = this.input.iterator();
            while (it.hasNext()) {
                File file = new File(new File(it.next()), DexMarker.FILE_NAME);
                if (file.isFile()) {
                    return file.getAbsolutePath();
                }
            }
        } else if (str.trim().length() == 0) {
            return null;
        }
        return this.markers;
    }

    protected SmaliOptions getOptions() {
        SmaliOptions smaliOptions = new SmaliOptions();
        smaliOptions.jobs = this.jobs;
        smaliOptions.apiLevel = this.apiLevel;
        smaliOptions.outputDexFile = this.output;
        smaliOptions.allowOdexOpcodes = this.allowOdexOpcodes;
        smaliOptions.verboseErrors = this.verbose;
        smaliOptions.markersListFile = getMarkers();
        return smaliOptions;
    }
}
