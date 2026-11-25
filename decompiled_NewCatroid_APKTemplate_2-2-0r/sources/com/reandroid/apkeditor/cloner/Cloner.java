package com.reandroid.apkeditor.cloner;

import com.reandroid.apkeditor.APKEditor;
import com.reandroid.apkeditor.CommandExecutor;
import java.io.IOException;

/* JADX INFO: loaded from: classes10.dex */
public class Cloner extends CommandExecutor<ClonerOptions> {
    public Cloner(ClonerOptions clonerOptions) {
        super(clonerOptions, "[CLONE] ");
    }

    @Override // com.reandroid.apkeditor.CommandExecutor
    public void runCommand() throws IOException {
        logWarn("This feature not implemented, follow updates on: " + APKEditor.getRepo());
    }
}
