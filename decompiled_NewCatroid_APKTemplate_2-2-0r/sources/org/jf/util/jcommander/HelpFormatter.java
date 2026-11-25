package org.jf.util.jcommander;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterDescription;
import com.beust.jcommander.Parameters;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jf.util.StringUtils;
import org.jf.util.WrappedIndentingWriter;
import org.jf.util.collection.Iterables;
import org.jf.util.collection.ListUtil;

/* JADX INFO: loaded from: classes6.dex */
public class HelpFormatter {
    private int width = 80;

    public HelpFormatter width(int i) {
        this.width = i;
        return this;
    }

    private static ExtendedParameters getExtendedParameters(JCommander jCommander) {
        ExtendedParameters extendedParameters = (ExtendedParameters) jCommander.getObjects().get(0).getClass().getAnnotation(ExtendedParameters.class);
        if (extendedParameters != null) {
            return extendedParameters;
        }
        throw new IllegalStateException("All commands should have an ExtendedParameters annotation");
    }

    private static List<String> getCommandAliases(JCommander jCommander) {
        return ListUtil.newArrayList(getExtendedParameters(jCommander).commandAliases());
    }

    private static boolean includeParametersInUsage(JCommander jCommander) {
        return getExtendedParameters(jCommander).includeParametersInUsage();
    }

    private static String getPostfixDescription(JCommander jCommander) {
        return getExtendedParameters(jCommander).postfixDescription();
    }

    private int getParameterArity(ParameterDescription parameterDescription) {
        if (parameterDescription.getParameter().arity() > 0) {
            return parameterDescription.getParameter().arity();
        }
        Class<?> type = parameterDescription.getParameterized().getType();
        return (type == Boolean.TYPE || type == Boolean.class) ? 0 : 1;
    }

    private List<ParameterDescription> getSortedParameters(JCommander jCommander) {
        ArrayList arrayListNewArrayList = ListUtil.newArrayList((Collection) jCommander.getParameters());
        final Pattern patternCompile = Pattern.compile("^-*(.*)$");
        Collections.sort(arrayListNewArrayList, new Comparator<ParameterDescription>() { // from class: org.jf.util.jcommander.HelpFormatter.1
            @Override // java.util.Comparator
            public int compare(ParameterDescription parameterDescription, ParameterDescription parameterDescription2) {
                Matcher matcher = patternCompile.matcher(parameterDescription.getParameter().names()[0]);
                if (matcher.matches()) {
                    String strGroup = matcher.group(1);
                    Matcher matcher2 = patternCompile.matcher(parameterDescription2.getParameter().names()[0]);
                    if (!matcher2.matches()) {
                        throw new IllegalStateException();
                    }
                    return strGroup.compareTo(matcher2.group(1));
                }
                throw new IllegalStateException();
            }
        });
        return arrayListNewArrayList;
    }

    public String format(JCommander... jCommanderArr) {
        return format(Arrays.asList(jCommanderArr));
    }

    /* JADX WARN: Code duplicated, block: B:88:0x01e8  */
    public String format(List<JCommander> list) {
        String string;
        try {
            StringWriter stringWriter = new StringWriter();
            int i = this.width;
            WrappedIndentingWriter wrappedIndentingWriter = new WrappedIndentingWriter(stringWriter, i - 5, i);
            JCommander jCommander = (JCommander) Iterables.getLast(list);
            wrappedIndentingWriter.write("usage:");
            wrappedIndentingWriter.indent(2);
            for (JCommander jCommander2 : list) {
                wrappedIndentingWriter.write(" ");
                wrappedIndentingWriter.write(ExtendedCommands.commandName(jCommander2));
            }
            int i2 = 0;
            if (includeParametersInUsage(jCommander)) {
                for (ParameterDescription parameterDescription : jCommander.getParameters()) {
                    if (!parameterDescription.getParameter().hidden()) {
                        wrappedIndentingWriter.write(" [");
                        wrappedIndentingWriter.write(parameterDescription.getParameter().getParameter().names()[0]);
                        wrappedIndentingWriter.write("]");
                    }
                }
            } else if (!jCommander.getParameters().isEmpty()) {
                wrappedIndentingWriter.write(" [<options>]");
            }
            if (!jCommander.getCommands().isEmpty()) {
                wrappedIndentingWriter.write(" [<command [<args>]]");
            }
            if (jCommander.getMainParameter() != null) {
                String[] strArrParameterArgumentNames = ExtendedCommands.parameterArgumentNames(jCommander.getMainParameter());
                if (strArrParameterArgumentNames.length == 0) {
                    wrappedIndentingWriter.write(" <args>");
                } else {
                    String str = strArrParameterArgumentNames[0];
                    boolean z = (str.startsWith("<") || str.startsWith("[")) ? false : true;
                    wrappedIndentingWriter.write(" ");
                    if (z) {
                        wrappedIndentingWriter.write("<");
                    }
                    wrappedIndentingWriter.write(strArrParameterArgumentNames[0]);
                    if (z) {
                        wrappedIndentingWriter.write(">");
                    }
                }
            }
            wrappedIndentingWriter.deindent(2);
            String commandDescription = ExtendedCommands.getCommandDescription(jCommander);
            if (commandDescription != null) {
                wrappedIndentingWriter.write("\n");
                wrappedIndentingWriter.write(commandDescription);
            }
            int i3 = 4;
            if (!jCommander.getParameters().isEmpty() || jCommander.getMainParameter() != null) {
                wrappedIndentingWriter.write("\n\nOptions:");
                wrappedIndentingWriter.indent(2);
                Iterator<ParameterDescription> it = getSortedParameters(jCommander).iterator();
                while (it.hasNext()) {
                    ParameterDescription next = it.next();
                    if (!next.getParameter().hidden()) {
                        wrappedIndentingWriter.write("\n");
                        wrappedIndentingWriter.indent(i3);
                        if (!next.getNames().isEmpty()) {
                            wrappedIndentingWriter.write(StringUtils.join(",", next.getParameter().names()));
                        }
                        if (getParameterArity(next) > 0) {
                            String[] strArrParameterArgumentNames2 = ExtendedCommands.parameterArgumentNames(next);
                            while (i2 < getParameterArity(next)) {
                                wrappedIndentingWriter.write(" ");
                                if (i2 < strArrParameterArgumentNames2.length) {
                                    wrappedIndentingWriter.write("<");
                                    wrappedIndentingWriter.write(strArrParameterArgumentNames2[i2]);
                                    wrappedIndentingWriter.write(">");
                                } else {
                                    wrappedIndentingWriter.write("<arg>");
                                }
                                i2++;
                            }
                        }
                        if (next.getDescription() != null && !next.getDescription().isEmpty()) {
                            wrappedIndentingWriter.write(" - ");
                            wrappedIndentingWriter.write(next.getDescription());
                        }
                        if (next.getDefault() != null) {
                            if (next.getParameterized().getType() == Boolean.class || next.getParameterized().getType() == Boolean.TYPE) {
                                if (((Boolean) next.getDefault()).booleanValue()) {
                                    string = "True";
                                } else {
                                    string = null;
                                }
                            } else if (List.class.isAssignableFrom(next.getParameterized().getType()) && ((List) next.getDefault()).isEmpty()) {
                                string = null;
                            } else {
                                string = next.getDefault().toString();
                            }
                            if (string != null) {
                                wrappedIndentingWriter.write(" (default: ");
                                wrappedIndentingWriter.write(string);
                                wrappedIndentingWriter.write(Constant.AFTER_QUTO);
                            }
                        }
                        wrappedIndentingWriter.deindent(4);
                    }
                    i2 = 0;
                    i3 = 4;
                }
                if (jCommander.getMainParameter() != null) {
                    String[] strArrParameterArgumentNames3 = ExtendedCommands.parameterArgumentNames(jCommander.getMainParameter());
                    wrappedIndentingWriter.write("\n");
                    wrappedIndentingWriter.indent(4);
                    if (strArrParameterArgumentNames3.length > 0) {
                        wrappedIndentingWriter.write("<");
                        wrappedIndentingWriter.write(strArrParameterArgumentNames3[0]);
                        wrappedIndentingWriter.write(">");
                    } else {
                        wrappedIndentingWriter.write("<args>");
                    }
                    if (jCommander.getMainParameterDescription() != null) {
                        wrappedIndentingWriter.write(" - ");
                        wrappedIndentingWriter.write(jCommander.getMainParameterDescription());
                    }
                    wrappedIndentingWriter.deindent(4);
                }
                wrappedIndentingWriter.deindent(2);
            }
            if (!jCommander.getCommands().isEmpty()) {
                wrappedIndentingWriter.write("\n\nCommands:");
                wrappedIndentingWriter.indent(2);
                ArrayList<Map.Entry> arrayListNewArrayList = ListUtil.newArrayList((Collection) jCommander.getCommands().entrySet());
                Collections.sort(arrayListNewArrayList, new Comparator<Map.Entry<String, JCommander>>() { // from class: org.jf.util.jcommander.HelpFormatter.2
                    @Override // java.util.Comparator
                    public int compare(Map.Entry<String, JCommander> entry, Map.Entry<String, JCommander> entry2) {
                        return entry.getKey().compareTo(entry2.getKey());
                    }
                });
                for (Map.Entry entry : arrayListNewArrayList) {
                    String str2 = (String) entry.getKey();
                    JCommander jCommander3 = (JCommander) entry.getValue();
                    if (!((Parameters) jCommander3.getObjects().get(0).getClass().getAnnotation(Parameters.class)).hidden()) {
                        wrappedIndentingWriter.write("\n");
                        wrappedIndentingWriter.indent(4);
                        wrappedIndentingWriter.write(str2);
                        List<String> commandAliases = getCommandAliases(jCommander3);
                        if (!commandAliases.isEmpty()) {
                            wrappedIndentingWriter.write("(");
                            wrappedIndentingWriter.write(StringUtils.join(",", commandAliases));
                            wrappedIndentingWriter.write(Constant.AFTER_QUTO);
                        }
                        String commandDescription2 = jCommander.getCommandDescription(str2);
                        if (commandDescription2 != null) {
                            wrappedIndentingWriter.write(" - ");
                            wrappedIndentingWriter.write(commandDescription2);
                        }
                        wrappedIndentingWriter.deindent(4);
                    }
                }
                wrappedIndentingWriter.deindent(2);
            }
            String postfixDescription = getPostfixDescription(jCommander);
            if (!postfixDescription.isEmpty()) {
                wrappedIndentingWriter.write("\n\n");
                wrappedIndentingWriter.write(postfixDescription);
            }
            wrappedIndentingWriter.flush();
            return stringWriter.getBuffer().toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
