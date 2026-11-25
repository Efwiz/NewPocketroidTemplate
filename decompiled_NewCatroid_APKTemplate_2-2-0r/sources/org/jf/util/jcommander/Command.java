package org.jf.util.jcommander;

import com.beust.jcommander.JCommander;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.jf.util.ConsoleUtil;
import org.jf.util.collection.Iterables;
import org.jf.util.collection.ListUtil;

/* JADX INFO: loaded from: classes6.dex */
public abstract class Command {
    protected final List<JCommander> commandAncestors;

    public abstract void run();

    protected void setupCommand(JCommander jCommander) {
    }

    public Command(List<JCommander> list) {
        this.commandAncestors = list;
    }

    public void usage() {
        System.out.println(new HelpFormatter().width(ConsoleUtil.getConsoleWidth()).format(getCommandHierarchy()));
    }

    protected JCommander getJCommander() {
        return ((JCommander) Iterables.getLast(this.commandAncestors)).getCommands().get(((ExtendedParameters) getClass().getAnnotation(ExtendedParameters.class)).commandName());
    }

    public List<JCommander> getCommandHierarchy() {
        ArrayList arrayListNewArrayList = ListUtil.newArrayList((Collection) this.commandAncestors);
        arrayListNewArrayList.add(getJCommander());
        return arrayListNewArrayList;
    }
}
