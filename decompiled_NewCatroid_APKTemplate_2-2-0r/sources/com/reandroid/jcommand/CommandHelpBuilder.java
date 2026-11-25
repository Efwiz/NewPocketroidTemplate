package com.reandroid.jcommand;

import com.reandroid.jcommand.annotations.CommandOptions;
import com.reandroid.jcommand.annotations.MainCommand;
import com.reandroid.jcommand.annotations.OtherOption;
import com.reandroid.jcommand.utils.ReflectionUtil;
import com.reandroid.jcommand.utils.SpreadSheet;
import com.reandroid.jcommand.utils.TwoColumnTable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import name.antonsmirnov.firmata.FormatHelper;

/* JADX INFO: loaded from: classes11.dex */
public class CommandHelpBuilder extends HelpBuilder {
    private MainCommand mainCommand;
    private final Class<?> mainCommandClass;

    public CommandHelpBuilder(CommandStringResource commandStringResource, Class<?> cls) {
        super(commandStringResource);
        if (cls == null) {
            throw new NullPointerException("Null MainCommand");
        }
        this.mainCommandClass = cls;
    }

    public CommandHelpBuilder(Class<?> cls) {
        this(null, cls);
    }

    @Override // com.reandroid.jcommand.HelpBuilder
    public SpreadSheet buildTable() {
        reset();
        appendLines(false, getMainCommand().headers());
        appendUsageLines();
        appendCommandOptions();
        appendOtherOptions();
        appendFooters();
        TwoColumnTable table = getTable();
        table.addSeparator();
        return table.buildTable();
    }

    public MainCommand getMainCommand() {
        MainCommand mainCommand = this.mainCommand;
        if (mainCommand == null) {
            mainCommand = (MainCommand) this.mainCommandClass.getAnnotation(MainCommand.class);
            if (mainCommand == null) {
                throw new RuntimeException("Class not annotated with MainCommand: '" + this.mainCommandClass + FormatHelper.QUOTE);
            }
            this.mainCommand = mainCommand;
        }
        return mainCommand;
    }

    private void appendCommandOptions() {
        List<CommandOptions> commandOptions = getCommandOptions();
        TwoColumnTable table = getTable();
        table.addMergedRow(getStringResource().getString(CommandStrings.title_commands));
        Iterator<CommandOptions> it = commandOptions.iterator();
        while (it.hasNext()) {
            appendCommandOptions(it.next());
        }
        table.addSeparator();
    }

    private void appendCommandOptions(CommandOptions commandOptions) {
        TwoColumnTable table = getTable();
        StringBuilder sb = new StringBuilder();
        sb.append(commandOptions.name());
        for (String str : commandOptions.alternates()) {
            sb.append(" | ");
            sb.append(str);
        }
        table.addRow(sb.toString(), getStringResource().getString(commandOptions.description()));
    }

    private void appendOtherOptions() {
        List<OtherOption> otherOptions = getOtherOptions();
        if (otherOptions.isEmpty()) {
            return;
        }
        TwoColumnTable table = getTable();
        table.addMergedRow(getStringResource().getString(CommandStrings.title_other_options));
        Iterator<OtherOption> it = otherOptions.iterator();
        while (it.hasNext()) {
            appendOtherOption(it.next());
        }
        table.addSeparator();
    }

    private void appendOtherOption(OtherOption otherOption) {
        TwoColumnTable table = getTable();
        StringBuilder sb = new StringBuilder();
        String[] strArrNames = otherOption.names();
        int length = strArrNames.length;
        for (int i = 0; i < length; i++) {
            if (i != 0) {
                sb.append(" | ");
            }
            sb.append(strArrNames[i]);
        }
        table.addRow(sb.toString(), getStringResource().getString(otherOption.description()));
    }

    private void appendUsageLines() {
        MainCommand mainCommand = getMainCommand();
        if (mainCommand == null) {
            return;
        }
        String[] strArrUsages = mainCommand.usages();
        if (strArrUsages.length == 0) {
            return;
        }
        CommandStringResource stringResource = getStringResource();
        TwoColumnTable table = getTable();
        table.addSeparator();
        table.addMergedRow(stringResource.getString(CommandStrings.title_usage));
        for (String str : strArrUsages) {
            table.addMergedRowTabbed(stringResource.getString(str));
        }
        table.addSeparator();
    }

    public List<CommandOptions> getCommandOptions() {
        ArrayList arrayList = new ArrayList();
        for (Class<?> cls : getMainCommand().options()) {
            CommandOptions commandOptions = (CommandOptions) cls.getAnnotation(CommandOptions.class);
            if (commandOptions == null) {
                throw new IllegalArgumentException("Invalid option class: '" + cls + "', should annotate CommandOptions");
            }
            arrayList.add(commandOptions);
        }
        return arrayList;
    }

    public List<OtherOption> getOtherOptions() {
        return ReflectionUtil.listOtherOptions(this.mainCommandClass);
    }
}
