package org.jf.util.jcommander;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterDescription;
import com.beust.jcommander.Parameterized;
import com.beust.jcommander.Parameters;
import java.lang.reflect.Field;

/* JADX INFO: loaded from: classes6.dex */
public class ExtendedCommands {
    static final /* synthetic */ boolean $assertionsDisabled = false;

    private static ExtendedParameters getExtendedParameters(Object obj) {
        ExtendedParameters extendedParameters = (ExtendedParameters) obj.getClass().getAnnotation(ExtendedParameters.class);
        if (extendedParameters != null) {
            return extendedParameters;
        }
        throw new IllegalStateException("All extended commands should have an ExtendedParameters annotation: " + obj.getClass().getCanonicalName());
    }

    public static String commandName(JCommander jCommander) {
        return getExtendedParameters(jCommander.getObjects().get(0)).commandName();
    }

    public static String commandName(Object obj) {
        return getExtendedParameters(obj).commandName();
    }

    public static String[] commandAliases(JCommander jCommander) {
        return commandAliases(jCommander.getObjects().get(0));
    }

    public static String[] commandAliases(Object obj) {
        return getExtendedParameters(obj).commandAliases();
    }

    public static boolean includeParametersInUsage(JCommander jCommander) {
        return includeParametersInUsage(jCommander.getObjects().get(0));
    }

    public static boolean includeParametersInUsage(Object obj) {
        return getExtendedParameters(obj).includeParametersInUsage();
    }

    public static String postfixDescription(JCommander jCommander) {
        return postfixDescription(jCommander.getObjects().get(0));
    }

    public static String postfixDescription(Object obj) {
        return getExtendedParameters(obj).postfixDescription();
    }

    public static void addExtendedCommand(JCommander jCommander, Command command) {
        jCommander.addCommand(commandName(command), command, commandAliases(command));
        command.setupCommand(command.getJCommander());
    }

    public static String[] parameterArgumentNames(ParameterDescription parameterDescription) {
        Field declaredField;
        Parameterized parameterized = parameterDescription.getParameterized();
        Class<?> superclass = parameterDescription.getObject().getClass();
        while (true) {
            if (superclass == Object.class) {
                declaredField = null;
                break;
            }
            try {
                declaredField = superclass.getDeclaredField(parameterized.getName());
                break;
            } catch (NoSuchFieldException unused) {
                superclass = superclass.getSuperclass();
            }
        }
        ExtendedParameter extendedParameter = (ExtendedParameter) declaredField.getAnnotation(ExtendedParameter.class);
        if (extendedParameter != null) {
            return extendedParameter.argumentNames();
        }
        return new String[0];
    }

    public static JCommander getSubcommand(JCommander jCommander, String str) {
        if (jCommander.getCommands().containsKey(str)) {
            return jCommander.getCommands().get(str);
        }
        for (JCommander jCommander2 : jCommander.getCommands().values()) {
            for (String str2 : commandAliases(jCommander2)) {
                if (str.equals(str2)) {
                    return jCommander2;
                }
            }
        }
        return null;
    }

    public static String getCommandDescription(JCommander jCommander) {
        Parameters parameters = (Parameters) jCommander.getObjects().get(0).getClass().getAnnotation(Parameters.class);
        if (parameters == null) {
            return null;
        }
        return parameters.commandDescription();
    }
}
