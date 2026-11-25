package org.jf.smali;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import org.jf.util.collection.ListUtil;
import org.jf.util.jcommander.Command;
import org.jf.util.jcommander.ExtendedCommands;
import org.jf.util.jcommander.ExtendedParameters;

/* JADX INFO: loaded from: classes6.dex */
@ExtendedParameters(commandName = "smali", includeParametersInUsage = true, postfixDescription = "See smali help <command> for more information about a specific command")
public class Main extends Command {
    public static final String VERSION = loadVersion();

    @Parameter(description = "Show usage information", help = true, names = {"-h", "-?", "--help"})
    private boolean help;
    private JCommander jc;

    @Parameter(description = "Print the version of baksmali and then exit", help = true, names = {"-v", "--version"})
    public boolean version;

    @Override // org.jf.util.jcommander.Command
    public void run() {
    }

    @Override // org.jf.util.jcommander.Command
    protected JCommander getJCommander() {
        return this.jc;
    }

    public Main() {
        super(ListUtil.newArrayList());
    }

    public static void main(String[] strArr) {
        Main main = new Main();
        JCommander jCommander = new JCommander(main);
        main.jc = jCommander;
        jCommander.setProgramName("smali");
        List<JCommander> commandHierarchy = main.getCommandHierarchy();
        ExtendedCommands.addExtendedCommand(jCommander, new AssembleCommand(commandHierarchy));
        ExtendedCommands.addExtendedCommand(jCommander, new PrintTokensCommand(commandHierarchy));
        ExtendedCommands.addExtendedCommand(jCommander, new HelpCommand(commandHierarchy));
        ExtendedCommands.addExtendedCommand(jCommander, new HelpCommand.HlepCommand(commandHierarchy));
        jCommander.parse(strArr);
        if (main.version) {
            version();
        }
        if (jCommander.getParsedCommand() == null || main.help) {
            main.usage();
        } else {
            ((Command) jCommander.getCommands().get(jCommander.getParsedCommand()).getObjects().get(0)).run();
        }
    }

    protected static void version() {
        System.out.println("smali " + VERSION + " (http://smali.org)");
        System.out.println("Copyright (C) 2010 Ben Gruver (JesusFreke@JesusFreke.com)");
        System.out.println("BSD license (http://www.opensource.org/licenses/bsd-license.php)");
        System.exit(0);
    }

    private static String loadVersion() {
        InputStream resourceAsStream = Main.class.getClassLoader().getResourceAsStream("smali.properties");
        if (resourceAsStream != null) {
            Properties properties = new Properties();
            try {
                properties.load(resourceAsStream);
                return properties.getProperty("application.version");
            } catch (IOException unused) {
            }
        }
        return "[unknown version]";
    }
}
