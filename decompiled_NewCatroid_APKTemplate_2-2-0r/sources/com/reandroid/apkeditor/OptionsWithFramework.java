package com.reandroid.apkeditor;

import com.reandroid.jcommand.annotations.OptionArg;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes10.dex */
public class OptionsWithFramework extends Options {

    @OptionArg(description = "framework_version_number", name = "-framework-version")
    public Integer frameworkVersion;

    @OptionArg(description = "path_of_framework", name = "-framework")
    public final List<File> frameworks = new ArrayList();

    @Override // com.reandroid.apkeditor.Options
    public void validateValues() {
        super.validateValues();
        validateFrameworkFiles();
    }

    public File[] getFrameworks() {
        return (File[]) this.frameworks.toArray(new File[0]);
    }

    public void validateFrameworkFiles() {
        Iterator<File> it = this.frameworks.iterator();
        while (it.hasNext()) {
            validateInputFile(it.next(), true, false);
        }
    }
}
