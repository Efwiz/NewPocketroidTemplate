package org.jf.baksmali;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.beust.jcommander.ParametersDelegate;
import com.huawei.hms.ml.language.common.utils.LanguageCodeUtil;
import java.io.File;
import java.io.IOException;
import java.util.List;
import org.jf.dexlib2.analysis.CustomInlineMethodResolver;
import org.jf.dexlib2.analysis.InlineMethodResolver;
import org.jf.dexlib2.dexbacked.DexBackedOdexFile;
import org.jf.util.jcommander.ExtendedParameter;
import org.jf.util.jcommander.ExtendedParameters;

/* JADX INFO: loaded from: classes6.dex */
@ExtendedParameters(commandAliases = {LanguageCodeUtil.DE, "x"}, commandName = "deodex")
@Parameters(commandDescription = "Deodexes an odex/oat file")
public class DeodexCommand extends DisassembleCommand {

    @ParametersDelegate
    protected AnalysisArguments.CheckPackagePrivateArgument checkPackagePrivateArgument;

    @ExtendedParameter(argumentNames = {"file"})
    @Parameter(description = "Specify a file containing a custom inline method table to use. See the \"deodexerant\" tool in the smali github repository to dump the inline method table from a device that uses dalvik.", names = {"--inline-table", "--inline", "--it"})
    private String inlineTable;

    @Override // org.jf.baksmali.DisassembleCommand
    protected boolean needsClassPath() {
        return true;
    }

    @Override // org.jf.baksmali.DisassembleCommand
    protected boolean showDeodexWarning() {
        return false;
    }

    public DeodexCommand(List<JCommander> list) {
        super(list);
        this.checkPackagePrivateArgument = new AnalysisArguments.CheckPackagePrivateArgument();
    }

    @Override // org.jf.baksmali.DisassembleCommand
    protected BaksmaliOptions getOptions() {
        BaksmaliOptions options = super.getOptions();
        options.deodex = true;
        if (this.dexFile instanceof DexBackedOdexFile) {
            if (this.inlineTable == null) {
                options.inlineResolver = InlineMethodResolver.createInlineMethodResolver(((DexBackedOdexFile) this.dexFile).getOdexVersion());
            } else {
                File file = new File(this.inlineTable);
                if (!file.exists()) {
                    System.err.println(String.format("Could not find file: %s", this.inlineTable));
                    System.exit(-1);
                }
                try {
                    options.inlineResolver = new CustomInlineMethodResolver(options.classPath, file);
                } catch (IOException e) {
                    System.err.println(String.format("Error while reading file: %s", file));
                    e.printStackTrace(System.err);
                    System.exit(-1);
                }
            }
        }
        return options;
    }

    @Override // org.jf.baksmali.DisassembleCommand
    protected boolean shouldCheckPackagePrivateAccess() {
        return this.checkPackagePrivateArgument.checkPackagePrivateAccess;
    }
}
