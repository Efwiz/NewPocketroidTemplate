package com.reandroid.apkeditor.cloner;

import com.reandroid.apkeditor.Options;
import com.reandroid.jcommand.annotations.CommandOptions;
import com.reandroid.jcommand.annotations.OptionArg;

/* JADX INFO: loaded from: classes10.dex */
@CommandOptions(alternates = {"clone"}, description = "Clones application (NOT Implemented)", examples = {"[Basic]\n  java -jar APKEditor.jar p -i input.apk -o output.apk"}, name = "c")
public class ClonerOptions extends Options {

    @OptionArg(description = "Application icon. File path of app icon(s).", name = "-app-icon")
    public String appIcon;

    @OptionArg(description = "Application name", name = "-app-name")
    public String appName;

    @OptionArg(description = "Do not rename authorities as per package. \n  *Applies only when option -package used.", name = "-keep-auth")
    public boolean keepAuth;

    @OptionArg(description = "Package name", name = "-package")
    public String packageName;

    @Override // com.reandroid.apkeditor.Options
    public Cloner newCommandExecutor() {
        return new Cloner(this);
    }
}
