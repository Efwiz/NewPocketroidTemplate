package org.jf.baksmali;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.jf.util.jcommander.Command;
import org.jf.util.jcommander.ExtendedCommands;
import org.jf.util.jcommander.ExtendedParameters;

/* JADX INFO: loaded from: classes6.dex */
@ExtendedParameters(commandName = "baksmali", includeParametersInUsage = true, postfixDescription = "See baksmali help <command> for more information about a specific command")
public class Main extends Command {
    public static final String VERSION = loadVersion();

    @Parameter(description = "Show usage information", help = true, names = {"--help", "-h", "-?"})
    private boolean help;
    private JCommander jc;

    @Parameter(description = "Print the version of baksmali and then exit", help = true, names = {"--version", "-v"})
    public boolean version;

    @Override // org.jf.util.jcommander.Command
    public void run() {
    }

    public Main() {
        super(new ArrayList());
    }

    @Override // org.jf.util.jcommander.Command
    protected JCommander getJCommander() {
        return this.jc;
    }

    public static void main(String[] strArr) {
        Main main = new Main();
        JCommander jCommander = new JCommander(main);
        main.jc = jCommander;
        jCommander.setProgramName("baksmali");
        List<JCommander> commandHierarchy = main.getCommandHierarchy();
        ExtendedCommands.addExtendedCommand(jCommander, new DisassembleCommand(commandHierarchy));
        ExtendedCommands.addExtendedCommand(jCommander, new DeodexCommand(commandHierarchy));
        ExtendedCommands.addExtendedCommand(jCommander, new DumpCommand(commandHierarchy));
        ExtendedCommands.addExtendedCommand(jCommander, new HelpCommand(commandHierarchy));
        ExtendedCommands.addExtendedCommand(jCommander, new HelpCommand.HlepCommand(commandHierarchy));
        ExtendedCommands.addExtendedCommand(jCommander, new ListCommand(commandHierarchy));
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
        System.out.println("baksmali " + VERSION + " (http://smali.org)");
        System.out.println("Copyright (C) 2010 Ben Gruver (JesusFreke@JesusFreke.com)");
        System.out.println("BSD license (http://www.opensource.org/licenses/bsd-license.php)");
        System.exit(0);
    }

    private static String loadVersion() {
        InputStream resourceAsStream = Baksmali.class.getClassLoader().getResourceAsStream("baksmali.properties");
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
