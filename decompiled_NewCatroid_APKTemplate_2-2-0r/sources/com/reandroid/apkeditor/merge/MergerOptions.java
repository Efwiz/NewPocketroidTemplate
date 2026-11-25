package com.reandroid.apkeditor.merge;

import com.reandroid.apkeditor.Options;
import com.reandroid.jcommand.annotations.CommandOptions;
import com.reandroid.jcommand.annotations.OptionArg;
import java.io.File;

/* JADX INFO: loaded from: classes10.dex */
@CommandOptions(alternates = {"merge"}, description = "merge_description", examples = {"merge_example_1"}, name = "m")
public class MergerOptions extends Options {

    @OptionArg(description = "clean_meta", flag = true, name = "-clean-meta")
    public boolean cleanMeta;

    @OptionArg(description = "res_dir_name", name = "-res-dir")
    public String resDirName;

    @OptionArg(description = "validate_modules", flag = true, name = "-validate-modules")
    public boolean validateModules;

    @OptionArg(description = "validate_resources_dir", flag = true, name = "-vrd")
    public boolean validateResDir;

    @Override // com.reandroid.apkeditor.Options
    public Merger newCommandExecutor() {
        return new Merger(this);
    }

    @Override // com.reandroid.apkeditor.Options
    public void validateInput(boolean z, boolean z2) {
        super.validateInput(true, true);
    }

    @Override // com.reandroid.apkeditor.Options
    public File generateOutputFromInput(File file) {
        return generateOutputFromInput(file, "_merged.apk");
    }
}
