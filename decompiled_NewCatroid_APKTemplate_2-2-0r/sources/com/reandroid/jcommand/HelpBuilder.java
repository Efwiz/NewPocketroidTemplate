package com.reandroid.jcommand;

import com.reandroid.jcommand.utils.SpreadSheet;
import com.reandroid.jcommand.utils.TwoColumnTable;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes11.dex */
public abstract class HelpBuilder {
    private String[] footers;
    private final CommandStringResource stringResource;
    private final TwoColumnTable twoColumnTable;

    public abstract SpreadSheet buildTable();

    public HelpBuilder(CommandStringResource commandStringResource) {
        this.stringResource = commandStringResource == null ? defaultStringResource() : commandStringResource;
        this.twoColumnTable = new TwoColumnTable();
    }

    public void setMaxWidth(int i) {
        getTable().setMaxWidth(i);
    }

    public void setTab2(String str) {
        getTable().setTab2(str);
    }

    public void setColumnSeparator(String str) {
        getTable().setColumnSeparator(str);
    }

    public void setDrawBorder(boolean z) {
        getTable().setDrawBorder(z);
    }

    public String[] getFooters() {
        return this.footers;
    }

    public void setFooters(String... strArr) {
        this.footers = strArr;
    }

    public String build() {
        return buildTable().toString();
    }

    public void appendLines(boolean z, String[] strArr) {
        if (strArr == null || strArr.length == 0) {
            return;
        }
        CommandStringResource stringResource = getStringResource();
        TwoColumnTable table = getTable();
        table.addSeparator();
        for (String str : strArr) {
            String string = stringResource.getString(str);
            if (z) {
                table.addMergedRowTabbed(string);
            } else {
                table.addMergedRow(string);
            }
        }
        table.addSeparator();
    }

    protected void appendFooters() {
        String[] footers = getFooters();
        if (footers == null || footers.length == 0) {
            return;
        }
        CommandStringResource stringResource = getStringResource();
        TwoColumnTable table = getTable();
        table.addSeparator();
        for (String str : footers) {
            table.addMergedRow(stringResource.getString(str)).setHorizontalIndent(0);
        }
    }

    public TwoColumnTable getTable() {
        return this.twoColumnTable;
    }

    public CommandStringResource getStringResource() {
        return this.stringResource;
    }

    public void reset() {
        getTable().clear();
    }

    public static CommandStringResource defaultStringResource() {
        final HashMap map = new HashMap();
        map.put(CommandStrings.title_commands, "Commands:");
        map.put(CommandStrings.title_options, "Options:");
        map.put(CommandStrings.title_flags, "Flags:");
        map.put(CommandStrings.title_usage, "Usage:");
        map.put(CommandStrings.title_example, "Examples:");
        return new CommandStringResource() { // from class: com.reandroid.jcommand.HelpBuilder$$ExternalSyntheticLambda0
            @Override // com.reandroid.jcommand.CommandStringResource
            public final String getString(String str) {
                return HelpBuilder.lambda$defaultStringResource$0(map, str);
            }
        };
    }

    static /* synthetic */ String lambda$defaultStringResource$0(Map map, String str) {
        String str2 = (String) map.get(str);
        return str2 == null ? str : str2;
    }
}
