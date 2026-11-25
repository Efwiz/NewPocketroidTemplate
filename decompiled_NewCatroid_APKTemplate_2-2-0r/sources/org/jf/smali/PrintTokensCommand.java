package org.jf.smali;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import java.io.IOException;
import java.util.List;
import org.jf.util.jcommander.Command;
import org.jf.util.jcommander.ExtendedParameter;
import org.jf.util.jcommander.ExtendedParameters;

/* JADX INFO: loaded from: classes6.dex */
@ExtendedParameters(commandName = "tokens")
@Parameters(commandDescription = "Lexes the given smali file(s) and prints the tokens.", hidden = true)
public class PrintTokensCommand extends Command {

    @ExtendedParameter(argumentNames = {"api"})
    @Parameter(description = "The numeric api level to use while assembling.", names = {"-a", "--api"})
    private int apiLevel;

    @Parameter(description = "Show usage information for this command.", help = true, names = {"-h", "-?", "--help"})
    private boolean help;

    @ExtendedParameter(argumentNames = {"[<file>|<dir>]+"})
    @Parameter(description = "Assembles the given files. If a directory is specified, it will be recursively searched for any files with a .smali prefix")
    private List<String> input;

    public PrintTokensCommand(List<JCommander> list) {
        super(list);
        this.apiLevel = 15;
    }

    @Override // org.jf.util.jcommander.Command
    public void run() {
        List<String> list;
        if (this.help || (list = this.input) == null || list.isEmpty()) {
            usage();
            return;
        }
        try {
            Smali.printTokens(getOptions(), this.input);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected SmaliOptions getOptions() {
        SmaliOptions smaliOptions = new SmaliOptions();
        smaliOptions.apiLevel = this.apiLevel;
        return smaliOptions;
    }
}
