package com.reandroid.apkeditor.decompile;

import com.reandroid.apkeditor.Options;
import com.reandroid.apkeditor.OptionsWithFramework;
import com.reandroid.jcommand.annotations.ChoiceArg;
import com.reandroid.jcommand.annotations.CommandOptions;
import com.reandroid.jcommand.annotations.OptionArg;
import java.io.File;

/* JADX INFO: loaded from: classes10.dex */
@CommandOptions(alternates = {"decode"}, description = "decode_description", examples = {"decode_example_1", "decode_example_2", "decode_example_3", "decode_example_4", "decode_example_5"}, name = "d", usage = "decode_usage")
public class DecompileOptions extends OptionsWithFramework {

    @OptionArg(description = "raw_dex", flag = true, name = "-dex")
    public boolean dex;

    @OptionArg(description = "dump_dex_markers", flag = true, name = "-dex-markers")
    public boolean dexMarkers;

    @OptionArg(description = "keep_original_res", flag = true, name = "-keep-res-path")
    public boolean keepResPath;

    @OptionArg(description = "no_dex_debug", flag = true, name = "-no-dex-debug")
    public boolean noDexDebug;

    @OptionArg(description = "res_dir_name", name = "-res-dir")
    public String resDirName;

    @OptionArg(description = "signatures_path", name = "-sig")
    public File signaturesDirectory;

    @OptionArg(description = "split_json", flag = true, name = "-split-json")
    public boolean splitJson;

    @ChoiceArg(description = "decode_types", name = "-t", values = {"xml", Options.TYPE_JSON, Options.TYPE_RAW, Options.TYPE_SIG})
    public String type = "xml";

    @OptionArg(description = "validate_resources_dir", flag = true, name = "-vrd")
    public boolean validateResDir;

    @Override // com.reandroid.apkeditor.Options
    public Decompiler newCommandExecutor() {
        return new Decompiler(this);
    }

    @Override // com.reandroid.apkeditor.Options
    public void validateInput(boolean z, boolean z2) {
        super.validateInput(true, false);
    }

    @Override // com.reandroid.apkeditor.Options
    public void validateOutput(boolean z) {
        super.validateOutput(false);
    }

    @Override // com.reandroid.apkeditor.Options
    public File generateOutputFromInput(File file) {
        return generateOutputFromInput(file, "_decompile_" + this.type);
    }
}
