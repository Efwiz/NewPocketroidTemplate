package org.jf.baksmali;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jf.util.ConsoleUtil;
import org.jf.util.StringWrapper;
import org.jf.util.jcommander.Command;
import org.jf.util.jcommander.ExtendedCommands;
import org.jf.util.jcommander.ExtendedParameter;
import org.jf.util.jcommander.ExtendedParameters;
import org.jf.util.jcommander.HelpFormatter;

/* JADX INFO: loaded from: classes6.dex */
@ExtendedParameters(commandAliases = {"h"}, commandName = "help")
@Parameters(commandDescription = "Shows usage information")
public class HelpCommand extends Command {

    @ExtendedParameter(argumentNames = {"commands"})
    @Parameter(description = "If specified, show the detailed usage information for the given commands")
    private List<String> commands;

    public HelpCommand(List<JCommander> list) {
        super(list);
        this.commands = new ArrayList();
    }

    @Override // org.jf.util.jcommander.Command
    public void run() {
        JCommander jCommander = this.commandAncestors.get(this.commandAncestors.size() - 1);
        List<String> list = this.commands;
        if (list == null || list.isEmpty()) {
            System.out.println(new HelpFormatter().width(ConsoleUtil.getConsoleWidth()).format(this.commandAncestors));
            return;
        }
        boolean z = false;
        for (String str : this.commands) {
            if (str.equals("register-info")) {
                Iterator<String> it = StringWrapper.wrapStringOnBreaks("The --register-info parameter will cause baksmali to generate comments before and after every instruction containing register type information about some subset of registers. This parameter accepts a comma-separated listof values specifying which registers and how much information to include.\n    ALL: all pre- and post-instruction registers\n    ALLPRE: all pre-instruction registers\n    ALLPOST: all post-instruction registers\n    ARGS: any pre-instruction registers used as arguments to the instruction\n    DEST: the post-instruction register used as the output of the instruction\n    MERGE: any pre-instruction register that has been merged from multiple incoming code paths\n    FULLMERGE: an extended version of MERGE that also includes a list of all the register types from incoming code paths that were merged", ConsoleUtil.getConsoleWidth()).iterator();
                while (it.hasNext()) {
                    System.out.println(it.next());
                }
            } else if (str.equals("input")) {
                Iterator<String> it2 = StringWrapper.wrapStringOnBreaks("Apks and oat files can contain multiple dex files. In order to specify a particular dex file, the basic syntax is to treat the apk/oat file as a directory. For example, to load the \"classes2.dex\" entry from \"app.apk\", you can use \"app.apk/classes2.dex\".\n\nFor ease of use, you can also specify a partial path to the dex file to load. For example, to load a entry named \"/system/framework/framework.jar:classes2.dex\" from \"framework.oat\", you can use any of the following:\n\"framework.oat/classes2.dex\"\n\"framework.oat/framework.jar:classes2.dex\"\n\"framework.oat/framework/framework.jar:classes2.dex\"\n\"framework.oat/system/framework/framework.jar:classes2.dex\"\n\nIn some rare cases, an oat file could have entries that can't be differentiated with the above syntax. For example \"/blah/blah.dex\" and \"blah/blah.dex\". In this case, the \"blah.oat/blah/blah.dex\" would match both entries and generate an error. To get around this, you can add double quotes around the entry name to specify an exact entry name. E.g. blah.oat/\"/blah/blah.dex\" or blah.oat/\"blah/blah.dex\" respectively.", ConsoleUtil.getConsoleWidth()).iterator();
                while (it2.hasNext()) {
                    System.out.println(it2.next());
                }
            } else if (str.equals(ModuleXmlParser.CLASSPATH)) {
                Iterator<String> it3 = StringWrapper.wrapStringOnBreaks("When deodexing odex/oat files or when using the --register-info option, baksmali needs to load all classes from the framework files on the device in order to fully understand the class hierarchy. There are several options that control how baksmali finds and loads the classpath entries.\n\nL+ devices (ART):\nWhen deodexing or disassembling a file from an L+ device using ART, you generally just need to specify the path to the boot.oat file via the --bootclasspath/-b parameter. On pre-N devices, the boot.oat file is self-contained and no other files are needed. In N, boot.oat was split into multiple files. In this case, the other files should be in the same directory as the boot.oat file, but you still only need to specify the boot.oat file in the --bootclasspath/-b option. The other files will be automatically loaded from the same directory.\n\nPre-L devices (dalvik):\nWhen deodexing odex files from a pre-L device using dalvik, you generally just need to specify the path to a directory containing the framework files from the device via the --classpath-dir/-d option. odex files contain a list of framework files they depend on and baksmali will search for these dependencies in the directory that you specify.\n\nDex files don't contain a list of dependencies like odex files, so when disassembling a dex file using the --register-info option, and using the framework files from a pre-L device, baksmali will attempt to use a reasonable default list of classpath files based on the api level set via the -a option. If this default list is incorrect, you can override the classpath using the --bootclasspath/-b option. This option accepts a colon separated list of classpath entries. Each entry can be specified in a few different ways.\n - A simple filename like \"framework.jar\"\n - A device path like \"/system/framework/framework.jar\"\n - A local relative or absolute path like \"/tmp/framework/framework.jar\"\nWhen using the first or second formats, you should also specify the directory containing the framework files via the --classpath-dir/-d option. When using the third format, this option is not needed.\nIt's worth noting that the second format matches the format used by Android for the BOOTCLASSPATH environment variable, so you can simply grab the value of that variable from the device and use it as-is.\n\nExamples:\n  For an M device:\n    adb pull /system/framework/arm/boot.oat /tmp/boot.oat\n    baksmali deodex blah.oat -b /tmp/boot.oat\n  For an N+ device:\n    adb pull /system/framework/arm /tmp/framework\n    baksmali deodex blah.oat -b /tmp/framework/boot.oat\n  For a pre-L device:\n    adb pull /system/framework /tmp/framework\n    baksmali deodex blah.odex -d /tmp/framework\n  Using the BOOTCLASSPATH on a pre-L device:\n    adb pull /system/framework /tmp/framework\n    export BOOTCLASSPATH=`adb shell \"echo \\\\$BOOTCLASPATH\"`\n    baksmali disassemble --register-info ARGS,DEST blah.apk -b $BOOTCLASSPATH -d /tmp/framework", ConsoleUtil.getConsoleWidth()).iterator();
                while (it3.hasNext()) {
                    System.out.println(it3.next());
                }
            } else {
                JCommander subcommand = ExtendedCommands.getSubcommand(jCommander, str);
                if (subcommand == null) {
                    System.err.println("No such command: " + str);
                } else {
                    System.out.println(new HelpFormatter().width(ConsoleUtil.getConsoleWidth()).format(((Command) subcommand.getObjects().get(0)).getCommandHierarchy()));
                }
            }
            z = true;
        }
        if (z) {
            return;
        }
        System.out.println(new HelpFormatter().width(ConsoleUtil.getConsoleWidth()).format(this.commandAncestors));
    }

    @ExtendedParameters(commandName = "hlep")
    @Parameters(hidden = true)
    public static class HlepCommand extends HelpCommand {
        public HlepCommand(List<JCommander> list) {
            super(list);
        }
    }
}
