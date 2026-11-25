package com.reandroid.jcommand;

import com.reandroid.jcommand.annotations.ChoiceArg;
import com.reandroid.jcommand.annotations.CommandOptions;
import com.reandroid.jcommand.annotations.LastArgs;
import com.reandroid.jcommand.annotations.OptionArg;
import com.reandroid.jcommand.utils.CommandUtil;
import com.reandroid.jcommand.utils.ReflectionUtil;
import com.reandroid.jcommand.utils.SpreadSheet;
import com.reandroid.jcommand.utils.TwoColumnTable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/* JADX INFO: loaded from: classes11.dex */
public class SubCommandHelpBuilder extends HelpBuilder {
    private final List<ChoiceArg> choiceArgList;
    private final CommandOptions commandOptions;
    private final LastArgs lastArgs;
    private final List<OptionArg> optionArgList;

    public SubCommandHelpBuilder(CommandStringResource commandStringResource, Class<?> cls) {
        super(commandStringResource);
        this.commandOptions = (CommandOptions) cls.getAnnotation(CommandOptions.class);
        this.optionArgList = ReflectionUtil.listOptionArgs(cls);
        this.choiceArgList = ReflectionUtil.listChoiceArgs(cls);
        this.lastArgs = ReflectionUtil.getLastArgs(cls);
    }

    public SubCommandHelpBuilder(Class<?> cls) {
        this(null, cls);
    }

    @Override // com.reandroid.jcommand.HelpBuilder
    public SpreadSheet buildTable() {
        reset();
        TwoColumnTable table = getTable();
        appendDescription();
        appendUsage();
        appendOptionArgs(getOptionArgList(), CommandStrings.title_options);
        appendChoiceArgs();
        appendOptionArgs(getFlagList(), CommandStrings.title_flags);
        appendExamples();
        table.addSeparator();
        return table.buildTable();
    }

    private void appendOptionArgs(List<OptionArg> list, String str) {
        if (list.isEmpty()) {
            return;
        }
        sortOptionArgList(list);
        CommandStringResource stringResource = getStringResource();
        TwoColumnTable table = getTable();
        table.addSeparator();
        table.addMergedRow(stringResource.getString(str));
        int size = list.size();
        for (int i = 0; i < size; i++) {
            OptionArg optionArg = list.get(i);
            StringBuilder sb = new StringBuilder();
            sb.append(optionArg.name());
            for (String str2 : optionArg.alternates()) {
                sb.append(" | ");
                sb.append(str2);
            }
            table.addRow(sb.toString(), stringResource.getString(optionArg.description()));
        }
    }

    private void appendChoiceArgs() {
        List<ChoiceArg> list = this.choiceArgList;
        if (list.isEmpty()) {
            return;
        }
        CommandStringResource stringResource = getStringResource();
        TwoColumnTable table = getTable();
        if (this.optionArgList.isEmpty()) {
            table.addMergedRow(stringResource.getString(CommandStrings.title_options));
        }
        int size = list.size();
        for (int i = 0; i < size; i++) {
            ChoiceArg choiceArg = list.get(i);
            StringBuilder sb = new StringBuilder();
            sb.append(choiceArg.name());
            for (String str : choiceArg.alternates()) {
                sb.append(" | ");
                sb.append(str);
            }
            table.addRow(sb.toString(), stringResource.getString(choiceArg.description()) + "\n" + CommandUtil.asString(choiceArg.values()));
        }
    }

    private void appendExamples() {
        String[] strArrExamples;
        int length;
        CommandOptions commandOptions = this.commandOptions;
        if (commandOptions == null || (length = (strArrExamples = commandOptions.examples()).length) == 0) {
            return;
        }
        CommandStringResource stringResource = getStringResource();
        TwoColumnTable table = getTable();
        table.addSeparator();
        table.addMergedRow(stringResource.getString(CommandStrings.title_example));
        int i = 0;
        while (i < length) {
            int i2 = i + 1;
            table.addMergedRowTabbed(i2 + ")  " + stringResource.getString(strArrExamples[i]));
            i = i2;
        }
    }

    private void appendUsage() {
        CommandOptions commandOptions = this.commandOptions;
        if (commandOptions == null || CommandUtil.isEmpty(commandOptions.usage())) {
            return;
        }
        CommandStringResource stringResource = getStringResource();
        TwoColumnTable table = getTable();
        table.addSeparator();
        table.addMergedRow(stringResource.getString(CommandStrings.title_usage));
        table.addMergedRowTabbed(stringResource.getString(commandOptions.usage()));
        table.addSeparator();
    }

    private void appendDescription() {
        CommandStringResource stringResource = getStringResource();
        TwoColumnTable table = getTable();
        table.addSeparator();
        CommandOptions commandOptions = this.commandOptions;
        if (commandOptions == null) {
            return;
        }
        table.addMergedRow(stringResource.getString(commandOptions.description()));
        table.addSeparator();
    }

    private List<OptionArg> getOptionArgList() {
        ArrayList arrayList = new ArrayList();
        for (OptionArg optionArg : this.optionArgList) {
            if (!optionArg.flag()) {
                arrayList.add(optionArg);
            }
        }
        return arrayList;
    }

    private List<OptionArg> getFlagList() {
        ArrayList arrayList = new ArrayList();
        for (OptionArg optionArg : this.optionArgList) {
            if (optionArg.flag()) {
                arrayList.add(optionArg);
            }
        }
        return arrayList;
    }

    private static void sortOptionArgList(List<OptionArg> list) {
        list.sort(new Comparator<OptionArg>() { // from class: com.reandroid.jcommand.SubCommandHelpBuilder.1
            @Override // java.util.Comparator
            public int compare(OptionArg optionArg, OptionArg optionArg2) {
                return optionArg.name().compareTo(optionArg2.name());
            }
        });
    }
}
