package com.reandroid.apkeditor.refactor;

import com.reandroid.apkeditor.Options;
import com.reandroid.jcommand.annotations.CommandOptions;
import com.reandroid.jcommand.annotations.OptionArg;
import java.io.File;

/* JADX INFO: loaded from: classes10.dex */
@CommandOptions(alternates = {"refactor"}, description = "refactor_description", examples = {"refactor_example_1"}, name = "x")
public class RefactorOptions extends Options {

    @OptionArg(description = "clean_meta", flag = true, name = "-clean-meta")
    public boolean cleanMeta;

    @OptionArg(description = "refactor_fix_types", flag = true, name = "-fix-types")
    public boolean fixTypeNames;

    @OptionArg(description = "refactor_public_xml", name = "-public-xml")
    public File publicXml;

    @Override // com.reandroid.apkeditor.Options
    public Refactor newCommandExecutor() {
        return new Refactor(this);
    }

    @Override // com.reandroid.apkeditor.Options
    public void validateValues() {
        super.validateValues();
        validatePublicXml();
    }

    private void validatePublicXml() {
        File file = this.publicXml;
        if (file != null) {
            validateInputFile(file, true, false);
        }
    }

    @Override // com.reandroid.apkeditor.Options
    public File generateOutputFromInput(File file) {
        return generateOutputFromInput(file, "_refactored.apk");
    }
}
