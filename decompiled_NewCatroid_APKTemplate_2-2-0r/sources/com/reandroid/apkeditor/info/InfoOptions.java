package com.reandroid.apkeditor.info;

import com.reandroid.apkeditor.Options;
import com.reandroid.apkeditor.OptionsWithFramework;
import com.reandroid.jcommand.annotations.ChoiceArg;
import com.reandroid.jcommand.annotations.CommandOptions;
import com.reandroid.jcommand.annotations.OptionArg;
import com.reandroid.jcommand.exceptions.CommandException;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.catrobat.catroid.common.Constants;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;

/* JADX INFO: loaded from: classes10.dex */
@CommandOptions(description = "info_description", examples = {"info_example_1", "info_example_2", "info_example_3"}, name = CompilerOptions.INFO)
public class InfoOptions extends OptionsWithFramework {

    @ChoiceArg(description = "info_print_types", name = "-t", values = {"text", Options.TYPE_JSON, "xml"})
    public String type = "text";

    @OptionArg(description = "info_verbose_mode", flag = true, name = "-v")
    public boolean verbose = false;

    @OptionArg(description = "info_package_name", flag = true, name = "-package")
    public boolean packageName = false;

    @OptionArg(description = "info_app_version_code", flag = true, name = "-version-code")
    public boolean versionCode = false;

    @OptionArg(description = "info_app_version_name", flag = true, name = "-version-name")
    public boolean versionName = false;

    @OptionArg(description = "info_min_sdk_version", flag = true, name = "-min-sdk-version")
    public boolean minSdkVersion = false;

    @OptionArg(description = "info_target_sdk_version", flag = true, name = "-target-sdk-version")
    public boolean targetSdkVersion = false;

    @OptionArg(description = "info_app_name", flag = true, name = "-app-name")
    public boolean appName = false;

    @OptionArg(description = "info_app_icon", flag = true, name = "-app-icon")
    public boolean appIcon = false;

    @OptionArg(description = "info_app_icon_round", flag = true, name = "-app-round-icon")
    public boolean appRoundIcon = false;

    @OptionArg(description = "info_permissions", flag = true, name = "-permissions")
    public boolean permissions = false;

    @OptionArg(description = "info_app_class_name", flag = true, name = "-app-class")
    public boolean appClass = false;

    @OptionArg(description = "info_activities", flag = true, name = "-activities")
    public boolean activities = false;

    @OptionArg(description = "info_res", name = "-res")
    public final List<String> resList = new ArrayList();

    @OptionArg(description = "info_resources", flag = true, name = "-resources")
    public boolean resources = false;

    @OptionArg(description = "info_filter_type", name = "-filter-type")
    public final List<String> typeFilterList = new ArrayList();

    @OptionArg(description = "info_dex", flag = true, name = "-dex")
    public boolean dex = false;

    @OptionArg(description = "info_signatures", flag = true, name = "-signatures")
    public boolean signatures = false;

    @OptionArg(description = "info_signatures_base64", flag = true, name = "-signatures-base64")
    public boolean signatures_base64 = false;

    @OptionArg(description = "info_xml_strings", name = "-xmlstrings")
    public List<String> xmlStrings = new ArrayList();

    @OptionArg(description = "info_strings", flag = true, name = "-strings")
    public boolean strings = false;

    @OptionArg(description = "info_xml_tree", name = "-xmltree")
    public final List<String> xmlTree = new ArrayList();

    @OptionArg(description = "info_list_files", flag = true, name = "-list-files")
    public boolean listFiles = false;

    @OptionArg(description = "info_list_xml_files", flag = true, name = "-list-xml-files")
    public boolean listXmlFiles = false;

    @OptionArg(description = "info_configurations", flag = true, name = "-configurations")
    public boolean configurations = false;

    @OptionArg(description = "info_languages", flag = true, name = "-languages")
    public boolean languages = false;

    @OptionArg(description = "info_locales", flag = true, name = "-locales")
    public boolean locales = false;

    @Override // com.reandroid.apkeditor.Options
    public Info newCommandExecutor() {
        return new Info(this);
    }

    @Override // com.reandroid.apkeditor.OptionsWithFramework, com.reandroid.apkeditor.Options
    public void validateValues() {
        super.validateValues();
        initializeDefaults();
    }

    @Override // com.reandroid.apkeditor.Options
    public void validateInput(boolean z, boolean z2) {
        super.validateInput(true, false);
    }

    @Override // com.reandroid.apkeditor.Options
    public void validateOutput(boolean z) {
        super.validateOutput(true);
        validateOutputExtension();
    }

    private void validateOutputExtension() {
        String str;
        File file = this.outputFile;
        if (file == null) {
            return;
        }
        String lowerCase = file.getName().toLowerCase();
        if ("text".equals(this.type)) {
            str = ".text";
            if (!lowerCase.endsWith(".text")) {
                str = Constants.TEXT_FILE_EXTENSION;
            }
        } else {
            str = "." + this.type.toLowerCase();
        }
        if (!lowerCase.endsWith(str)) {
            throw new CommandException("info_invalid_output_extension", str, file);
        }
    }

    private void initializeDefaults() {
        if (isDefault()) {
            this.appName = true;
            this.appIcon = true;
            this.activities = true;
            this.appClass = true;
            this.packageName = true;
            this.versionCode = true;
            this.versionName = true;
            if (this.verbose) {
                this.permissions = true;
            }
        }
    }

    private boolean isDefault() {
        return (this.activities || this.appClass || this.appIcon || this.appName || this.appRoundIcon || this.dex || this.minSdkVersion || this.packageName || this.permissions || this.targetSdkVersion || this.resources || this.signatures || this.signatures_base64 || this.versionCode || this.versionName || this.listFiles || this.listXmlFiles || this.configurations || this.languages || this.locales || this.strings || !this.resList.isEmpty() || !this.typeFilterList.isEmpty() || !this.xmlTree.isEmpty() || !this.xmlStrings.isEmpty()) ? false : true;
    }
}
