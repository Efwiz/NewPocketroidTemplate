package com.reandroid.apkeditor;

import com.reandroid.apk.xmlencoder.EncodeException;
import com.reandroid.apkeditor.compile.BuildOptions;
import com.reandroid.apkeditor.decompile.DecompileOptions;
import com.reandroid.apkeditor.info.InfoOptions;
import com.reandroid.apkeditor.merge.MergerOptions;
import com.reandroid.apkeditor.protect.ProtectorOptions;
import com.reandroid.apkeditor.refactor.RefactorOptions;
import com.reandroid.arsc.ARSCLib;
import com.reandroid.arsc.coder.xml.XmlEncodeException;
import com.reandroid.jcommand.CommandHelpBuilder;
import com.reandroid.jcommand.CommandParser;
import com.reandroid.jcommand.annotations.MainCommand;
import com.reandroid.jcommand.annotations.OnOptionSelected;
import com.reandroid.jcommand.annotations.OtherOption;
import com.reandroid.jcommand.exceptions.CommandException;

/* JADX INFO: loaded from: classes10.dex */
@MainCommand(headers = {"title_app_name_and_version", "title_app_repo", "title_app_description"}, options = {DecompileOptions.class, BuildOptions.class, MergerOptions.class, RefactorOptions.class, ProtectorOptions.class, InfoOptions.class})
public class Main {
    private boolean mEmptyOption;
    private int mExitCode;
    private Options mOptions;

    private Main() {
    }

    public static void main(String[] strArr) {
        System.exit(execute(strArr));
    }

    public static int execute(String[] strArr) {
        return new Main().run(strArr);
    }

    @OtherOption(description = "Displays this help and exit", names = {"-h", "-help"})
    void onMainHelp() {
        this.mExitCode = 2;
        CommandHelpBuilder commandHelpBuilder = new CommandHelpBuilder(ResourceStrings.INSTANCE, Main.class);
        commandHelpBuilder.setFooters("", "help_main_footer", "<command> -h", "");
        System.err.println(commandHelpBuilder.build());
    }

    @OtherOption(description = "Displays version", names = {"-v", "-version"})
    void onPrintVersion() {
        this.mExitCode = 2;
        System.err.println(APKEditor.getName() + " version " + APKEditor.getVersion() + ", " + ARSCLib.getName() + " version " + ARSCLib.getVersion());
    }

    @OnOptionSelected
    void onOption(Object obj, boolean z) {
        this.mOptions = (Options) obj;
        this.mEmptyOption = z;
    }

    private int run(String[] strArr) {
        this.mOptions = null;
        this.mEmptyOption = false;
        this.mExitCode = 2;
        try {
            new CommandParser(Main.class).parse(this, strArr);
            Options options = this.mOptions;
            if (options == null) {
                return this.mExitCode;
            }
            if (this.mEmptyOption) {
                System.err.println(ResourceStrings.INSTANCE.getString("empty_command_option_exception"));
                return this.mExitCode;
            }
            try {
                options.validate();
                if (this.mOptions.help) {
                    System.err.println(this.mOptions.getHelp());
                    return this.mExitCode;
                }
                this.mExitCode = 1;
                try {
                    this.mOptions.runCommand();
                    this.mExitCode = 0;
                } catch (EncodeException e) {
                    e = e;
                    System.err.flush();
                    System.err.println("\nERROR:\n" + e.getMessage());
                } catch (XmlEncodeException e2) {
                    e = e2;
                    System.err.flush();
                    System.err.println("\nERROR:\n" + e.getMessage());
                } catch (CommandException e3) {
                    System.err.flush();
                    System.err.println(e3.getMessage(ResourceStrings.INSTANCE));
                } catch (Exception e4) {
                    System.err.flush();
                    System.err.println("\nERROR:");
                    e4.printStackTrace(System.err);
                }
                return this.mExitCode;
            } catch (CommandException e5) {
                System.err.flush();
                System.err.println(e5.getMessage(ResourceStrings.INSTANCE));
                return this.mExitCode;
            }
        } catch (CommandException e6) {
            System.err.flush();
            System.err.println(e6.getMessage(ResourceStrings.INSTANCE));
            return this.mExitCode;
        }
    }
}
