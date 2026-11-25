package com.reandroid.apkeditor;

import com.reandroid.arsc.ARSCLib;
import com.reandroid.jcommand.CommandHelpBuilder;
import com.reandroid.jcommand.OptionStringBuilder;
import com.reandroid.jcommand.SubCommandHelpBuilder;
import com.reandroid.jcommand.SubCommandParser;
import com.reandroid.jcommand.annotations.OptionArg;
import com.reandroid.jcommand.exceptions.CommandException;
import java.io.File;
import java.io.IOException;

/* JADX INFO: loaded from: classes10.dex */
public class Options {
    public static final int PRINT_WIDTH = 80;
    public static final String TYPE_JSON = "json";
    public static final String TYPE_RAW = "raw";
    public static final String TYPE_SIG = "sig";
    public static final String TYPE_TEXT = "text";
    public static final String TYPE_XML = "xml";

    @OptionArg(description = "force_delete", flag = true, name = "-f")
    public boolean force;

    @OptionArg(alternates = {"-help", "--help"}, description = "help_description", flag = true, name = "-h")
    public boolean help = false;

    @OptionArg(description = "input_path", name = "-i")
    public File inputFile;
    private boolean mValidated;

    @OptionArg(description = "output_path", name = "-o")
    public File outputFile;
    public String type;

    public File generateOutputFromInput(File file) {
        return null;
    }

    public String getHelp() {
        SubCommandHelpBuilder subCommandHelpBuilder = new SubCommandHelpBuilder(ResourceStrings.INSTANCE, getClass());
        subCommandHelpBuilder.setMaxWidth(80);
        subCommandHelpBuilder.setColumnSeparator("   ");
        return subCommandHelpBuilder.build();
    }

    public void parse(String[] strArr) {
        SubCommandParser.parse(this, strArr);
        validate();
    }

    public void runCommand() throws IOException {
        CommandExecutor<?> commandExecutorNewCommandExecutor = newCommandExecutor();
        commandExecutorNewCommandExecutor.logMessage(toString());
        commandExecutorNewCommandExecutor.runCommand();
    }

    public CommandExecutor<?> newCommandExecutor() {
        throw new RuntimeException("Method not implemented");
    }

    public void validate() {
        if (this.help || this.mValidated) {
            return;
        }
        this.mValidated = true;
        validateValues();
    }

    public void validateValues() {
        validateInput(true, false);
        validateOutput(true);
    }

    public void validateInput(boolean z, boolean z2) {
        File file = this.inputFile;
        if (file == null) {
            throw new CommandException("missing_input_file", new Object[0]);
        }
        validateInputFile(file, z, z2);
    }

    public void validateInputFile(File file, boolean z, boolean z2) {
        if (z) {
            if (file.isFile()) {
                return;
            }
            if (!z2) {
                throw new CommandException("no_such_file", file);
            }
        }
        if (z2) {
            if (!file.isDirectory()) {
                throw new CommandException("no_such_directory", file);
            }
        } else if (!file.exists()) {
            throw new CommandException("no_such_file_or_directory", file);
        }
    }

    public void validateOutput(boolean z) {
        File fileGenerateOutputFromInput = this.outputFile;
        if (fileGenerateOutputFromInput == null) {
            fileGenerateOutputFromInput = generateOutputFromInput(this.inputFile);
            this.outputFile = fileGenerateOutputFromInput;
        }
        if (fileGenerateOutputFromInput == null || !fileGenerateOutputFromInput.exists()) {
            return;
        }
        if (z != fileGenerateOutputFromInput.isFile()) {
            if (fileGenerateOutputFromInput.isFile()) {
                throw new CommandException("path_is_file_expect_directory", fileGenerateOutputFromInput);
            }
            throw new CommandException("path_is_directory_expect_file", fileGenerateOutputFromInput);
        }
        if (!this.force) {
            throw new CommandException("path_already_exists", fileGenerateOutputFromInput);
        }
    }

    public File generateOutputFromInput(File file, String str) {
        int iLastIndexOf;
        String name2 = file.getName();
        if (file.isFile() && (iLastIndexOf = name2.lastIndexOf(46)) > 0) {
            name2 = name2.substring(0, iLastIndexOf);
        }
        String str2 = name2 + str;
        File parentFile = file.getParentFile();
        if (parentFile == null) {
            return new File(str2);
        }
        return new File(parentFile, str2);
    }

    public String toString() {
        OptionStringBuilder optionStringBuilder = new OptionStringBuilder(this);
        optionStringBuilder.setMaxWidth(80);
        optionStringBuilder.setTab2("      ");
        return "Using: " + APKEditor.getName() + " version " + APKEditor.getVersion() + ", " + ARSCLib.getName() + " version " + ARSCLib.getVersion() + "\n" + optionStringBuilder.buildTable();
    }

    public static String getHelp(Class<?> cls) {
        CommandHelpBuilder commandHelpBuilder = new CommandHelpBuilder(ResourceStrings.INSTANCE, cls);
        commandHelpBuilder.setMaxWidth(80);
        return commandHelpBuilder.build();
    }
}
