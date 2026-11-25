package com.reandroid.apkeditor.protect;

import com.reandroid.apkeditor.Options;
import com.reandroid.jcommand.annotations.CommandOptions;
import com.reandroid.jcommand.annotations.OptionArg;
import java.io.File;
import java.util.HashSet;
import java.util.Set;

/* JADX INFO: loaded from: classes10.dex */
@CommandOptions(alternates = {"protect"}, description = "protect_description", examples = {"protect_example_1"}, name = "p")
public class ProtectorOptions extends Options {

    @OptionArg(description = "protect_keep_type", name = "-keep-type")
    public final Set<String> keepTypes = new HashSet();

    @OptionArg(description = "protect_skip_manifest", flag = true, name = "-skip-manifest")
    public boolean skipManifest;

    @Override // com.reandroid.apkeditor.Options
    public void validateValues() {
        super.validateValues();
        addDefaultKeepTypes();
    }

    private void addDefaultKeepTypes() {
        Set<String> set = this.keepTypes;
        if (set.isEmpty()) {
            set.add("font");
        }
    }

    @Override // com.reandroid.apkeditor.Options
    public Protector newCommandExecutor() {
        return new Protector(this);
    }

    @Override // com.reandroid.apkeditor.Options
    public void validateInput(boolean z, boolean z2) {
        super.validateInput(true, false);
    }

    @Override // com.reandroid.apkeditor.Options
    public void validateOutput(boolean z) {
        super.validateOutput(true);
    }

    @Override // com.reandroid.apkeditor.Options
    public File generateOutputFromInput(File file) {
        return generateOutputFromInput(file, "_protected.apk");
    }
}
