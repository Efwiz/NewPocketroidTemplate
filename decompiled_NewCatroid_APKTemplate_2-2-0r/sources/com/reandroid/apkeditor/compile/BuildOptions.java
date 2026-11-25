package com.reandroid.apkeditor.compile;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.reandroid.apkeditor.Options;
import com.reandroid.apkeditor.OptionsWithFramework;
import com.reandroid.app.AndroidManifest;
import com.reandroid.arsc.chunk.TableBlock;
import com.reandroid.jcommand.annotations.ChoiceArg;
import com.reandroid.jcommand.annotations.CommandOptions;
import com.reandroid.jcommand.annotations.OptionArg;
import com.reandroid.jcommand.exceptions.CommandException;
import java.io.File;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;

/* JADX INFO: loaded from: classes10.dex */
@CommandOptions(alternates = {JsonPOJOBuilder.DEFAULT_BUILD_METHOD}, description = "build_description", examples = {"build_example_1", "build_example_2", "build_example_3", "build_example_4"}, name = JvmAnnotationNames.SERIALIZED_IR_BYTES_FIELD_NAME)
public class BuildOptions extends OptionsWithFramework {

    @OptionArg(description = "build_no_cache", flag = true, name = "-no-cache")
    public boolean noCache;

    @OptionArg(description = "res_dir_name", name = "-res-dir")
    public String resDirName;

    @OptionArg(description = "signatures_path", name = "-sig")
    public File signaturesDirectory;

    @ChoiceArg(description = "build_types", name = "-t", values = {"xml", Options.TYPE_JSON, Options.TYPE_RAW, Options.TYPE_SIG})
    public String type;

    @OptionArg(description = "validate_resources_dir", flag = true, name = "-vrd")
    public boolean validateResDir;

    @Override // com.reandroid.apkeditor.Options
    public Builder newCommandExecutor() {
        return new Builder(this);
    }

    @Override // com.reandroid.apkeditor.Options
    public void validateInput(boolean z, boolean z2) {
        boolean zEquals = Options.TYPE_SIG.equals(this.type);
        super.validateInput(zEquals, !zEquals);
        evaluateInputDirectoryType();
        validateSignaturesDirectory();
    }

    private void evaluateInputDirectoryType() {
        String str;
        if (this.type != null) {
            return;
        }
        File file = this.inputFile;
        if (isRawInputDirectory(file)) {
            str = Options.TYPE_RAW;
        } else if (isJsonInputDirectory(file)) {
            this.inputFile = getJsonInDir(this.inputFile);
            str = Options.TYPE_JSON;
        } else if (isXmlInputDirectory(file)) {
            str = "xml";
        } else if (this.signaturesDirectory != null) {
            str = Options.TYPE_SIG;
        } else {
            throw new CommandException("unknown_build_directory", file);
        }
        this.type = str;
    }

    @Override // com.reandroid.apkeditor.Options
    public void validateOutput(boolean z) {
        super.validateOutput(true);
    }

    private void validateSignaturesDirectory() {
        if (Options.TYPE_SIG.equals(this.type)) {
            File file = this.signaturesDirectory;
            if (file == null) {
                throw new CommandException("missing_sig_directory", new Object[0]);
            }
            validateInputFile(file, false, true);
            return;
        }
        if (this.signaturesDirectory != null) {
            throw new CommandException("invalid_sig_parameter_combination", new Object[0]);
        }
    }

    @Override // com.reandroid.apkeditor.Options
    public File generateOutputFromInput(File file) {
        return generateOutputFromInput(file, "_out.apk");
    }

    private static boolean isRawInputDirectory(File file) {
        File file2 = new File(file, AndroidManifest.FILE_NAME_BIN);
        if (!file2.isFile()) {
            file2 = new File(file, TableBlock.FILE_NAME);
        }
        return file2.isFile();
    }

    private static boolean isXmlInputDirectory(File file) {
        return new File(file, AndroidManifest.FILE_NAME).isFile();
    }

    private static boolean isJsonInputDirectory(File file) {
        if (isModuleDir(file)) {
            return true;
        }
        File[] fileArrListFiles = file.listFiles();
        if (fileArrListFiles == null) {
            return false;
        }
        for (File file2 : fileArrListFiles) {
            if (isModuleDir(file2)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isModuleDir(File file) {
        if (file.isDirectory()) {
            return new File(file, AndroidManifest.FILE_NAME_JSON).isFile();
        }
        return false;
    }

    private static File getJsonInDir(File file) {
        if (isModuleDir(file)) {
            return file;
        }
        File[] fileArrListFiles = file.listFiles();
        if (fileArrListFiles == null || fileArrListFiles.length == 0) {
            throw new CommandException("Empty directory: %s", file);
        }
        for (File file2 : fileArrListFiles) {
            if (isModuleDir(file2)) {
                return file2;
            }
        }
        throw new CommandException("Invalid directory: '%s', missing file \"uncompressed-files.json\"", file);
    }
}
