package com.beust.jcommander;

import com.beust.jcommander.converters.IParameterSplitter;
import com.beust.jcommander.converters.NoConverter;
import com.beust.jcommander.converters.StringConverter;
import com.beust.jcommander.internal.Console;
import com.beust.jcommander.internal.DefaultConsole;
import com.beust.jcommander.internal.DefaultConverterFactory;
import com.beust.jcommander.internal.JDK6Console;
import com.beust.jcommander.internal.Lists;
import com.beust.jcommander.internal.Maps;
import com.beust.jcommander.internal.Nullable;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.antlr.stringtemplate.language.ASTExpr;
import org.jetbrains.kotlin.cli.common.arguments.PreprocessCommandLineArgumentsKt;

/* JADX INFO: loaded from: classes8.dex */
public class JCommander {
    private static LinkedList<IStringConverterFactory> CONVERTER_FACTORIES = null;
    public static final String DEBUG_PROPERTY = "jcommander.debug";
    private static Console m_console;
    private java.util.ResourceBundle m_bundle;
    private IDefaultProvider m_defaultProvider;
    private Map<FuzzyMap.IKey, ParameterDescription> m_descriptions;
    private boolean m_helpWasSpecified;
    private Parameter m_mainParameterAnnotation;
    private ParameterDescription m_mainParameterDescription;
    private Object m_mainParameterObject;
    private String m_parsedAlias;
    private String m_parsedCommand;
    private ProgramName m_programName;
    private List<Object> m_objects = Lists.newArrayList();
    private boolean m_firstTimeMainParameter = true;
    private Parameterized m_mainParameter = null;
    private Map<Parameterized, ParameterDescription> m_requiredFields = Maps.newHashMap();
    private Map<Parameterized, ParameterDescription> m_fields = Maps.newHashMap();
    private Map<ProgramName, JCommander> m_commands = Maps.newLinkedHashMap();
    private Map<FuzzyMap.IKey, ProgramName> aliasMap = Maps.newLinkedHashMap();
    private Comparator<? super ParameterDescription> m_parameterDescriptionComparator = new Comparator<ParameterDescription>() { // from class: com.beust.jcommander.JCommander.1
        @Override // java.util.Comparator
        public int compare(ParameterDescription parameterDescription, ParameterDescription parameterDescription2) {
            return parameterDescription.getLongestName().compareTo(parameterDescription2.getLongestName());
        }
    };
    private int m_columnSize = 79;
    private List<String> m_unknownArgs = Lists.newArrayList();
    private boolean m_acceptUnknownOptions = false;
    private boolean m_allowParameterOverwriting = false;
    private final IVariableArity DEFAULT_VARIABLE_ARITY = new DefaultVariableArity();
    private int m_verbose = 0;
    private boolean m_caseSensitiveOptions = true;
    private boolean m_allowAbbreviatedOptions = false;

    private static String pluralize(int i, String str, String str2) {
        return i == 1 ? str : str2;
    }

    static {
        LinkedList<IStringConverterFactory> linkedListNewLinkedList = Lists.newLinkedList();
        CONVERTER_FACTORIES = linkedListNewLinkedList;
        linkedListNewLinkedList.addFirst(new DefaultConverterFactory());
    }

    public JCommander() {
    }

    public JCommander(Object obj) {
        addObject(obj);
        createDescriptions();
    }

    public JCommander(Object obj, @Nullable java.util.ResourceBundle resourceBundle) {
        addObject(obj);
        setDescriptionsBundle(resourceBundle);
    }

    public JCommander(Object obj, java.util.ResourceBundle resourceBundle, String... strArr) {
        addObject(obj);
        setDescriptionsBundle(resourceBundle);
        parse(strArr);
    }

    public JCommander(Object obj, String... strArr) {
        addObject(obj);
        parse(strArr);
    }

    public static Console getConsole() {
        if (m_console == null) {
            try {
                m_console = new JDK6Console(System.class.getDeclaredMethod("console", new Class[0]).invoke(null, new Object[0]));
            } catch (Throwable unused) {
                m_console = new DefaultConsole();
            }
        }
        return m_console;
    }

    public final void addObject(Object obj) {
        if (obj instanceof Iterable) {
            Iterator it = ((Iterable) obj).iterator();
            while (it.hasNext()) {
                this.m_objects.add(it.next());
            }
            return;
        }
        if (obj.getClass().isArray()) {
            for (Object obj2 : (Object[]) obj) {
                this.m_objects.add(obj2);
            }
            return;
        }
        this.m_objects.add(obj);
    }

    public final void setDescriptionsBundle(java.util.ResourceBundle resourceBundle) {
        this.m_bundle = resourceBundle;
    }

    public void parse(String... strArr) {
        parse(true, strArr);
    }

    public void parseWithoutValidation(String... strArr) {
        parse(false, strArr);
    }

    private void parse(boolean z, String... strArr) {
        p("Parsing \"" + ((CharSequence) join(strArr).append("\"\n  with:").append((CharSequence) join(this.m_objects.toArray()))));
        if (this.m_descriptions == null) {
            createDescriptions();
        }
        initializeDefaultValues();
        parseValues(expandArgs(strArr), z);
        if (z) {
            validateOptions();
        }
    }

    private StringBuilder join(Object[] objArr) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < objArr.length; i++) {
            if (i > 0) {
                sb.append(" ");
            }
            sb.append(objArr[i]);
        }
        return sb;
    }

    private void initializeDefaultValues() {
        if (this.m_defaultProvider != null) {
            Iterator<ParameterDescription> it = this.m_descriptions.values().iterator();
            while (it.hasNext()) {
                initializeDefaultValue(it.next());
            }
            Iterator<Map.Entry<ProgramName, JCommander>> it2 = this.m_commands.entrySet().iterator();
            while (it2.hasNext()) {
                it2.next().getValue().initializeDefaultValues();
            }
        }
    }

    private void validateOptions() {
        if (this.m_helpWasSpecified) {
            return;
        }
        if (!this.m_requiredFields.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            Iterator<ParameterDescription> it = this.m_requiredFields.values().iterator();
            while (it.hasNext()) {
                sb.append(it.next().getNames()).append(" ");
            }
            throw new ParameterException("The following " + pluralize(this.m_requiredFields.size(), "option is required: ", "options are required: ") + ((Object) sb));
        }
        ParameterDescription parameterDescription = this.m_mainParameterDescription;
        if (parameterDescription != null && parameterDescription.getParameter().required() && !this.m_mainParameterDescription.isAssigned()) {
            throw new ParameterException("Main parameters are required (\"" + this.m_mainParameterDescription.getDescription() + "\")");
        }
    }

    private String[] expandArgs(String[] strArr) {
        List listNewArrayList = Lists.newArrayList();
        for (String str : strArr) {
            if (str.startsWith(PreprocessCommandLineArgumentsKt.ARGFILE_ARGUMENT)) {
                listNewArrayList.addAll(readFile(str.substring(1)));
            } else {
                listNewArrayList.addAll(expandDynamicArg(str));
            }
        }
        List listNewArrayList2 = Lists.newArrayList();
        for (int i = 0; i < listNewArrayList.size(); i++) {
            String str2 = (String) listNewArrayList.get(i);
            String[] strArr2 = (String[]) listNewArrayList.toArray(new String[0]);
            if (isOption(strArr2, str2)) {
                String separatorFor = getSeparatorFor(strArr2, str2);
                if (!" ".equals(separatorFor)) {
                    for (String str3 : str2.split("[" + separatorFor + "]", 2)) {
                        listNewArrayList2.add(str3);
                    }
                } else {
                    listNewArrayList2.add(str2);
                }
            } else {
                listNewArrayList2.add(str2);
            }
        }
        return (String[]) listNewArrayList2.toArray(new String[listNewArrayList2.size()]);
    }

    private List<String> expandDynamicArg(String str) {
        for (ParameterDescription parameterDescription : this.m_descriptions.values()) {
            if (parameterDescription.isDynamicParameter()) {
                for (String str2 : parameterDescription.getParameter().names()) {
                    if (str.startsWith(str2) && !str.equals(str2)) {
                        return Arrays.asList(str2, str.substring(str2.length()));
                    }
                }
            }
        }
        return Arrays.asList(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isOption(String[] strArr, String str) {
        return str.length() > 0 && getOptionPrefixes(strArr, str).indexOf(str.charAt(0)) >= 0;
    }

    private ParameterDescription getPrefixDescriptionFor(String str) {
        for (Map.Entry<FuzzyMap.IKey, ParameterDescription> entry : this.m_descriptions.entrySet()) {
            if (str.startsWith(entry.getKey().getName())) {
                return entry.getValue();
            }
        }
        return null;
    }

    private ParameterDescription getDescriptionFor(String[] strArr, String str) {
        ParameterDescription prefixDescriptionFor = getPrefixDescriptionFor(str);
        if (prefixDescriptionFor != null) {
            return prefixDescriptionFor;
        }
        for (String str2 : strArr) {
            ParameterDescription prefixDescriptionFor2 = getPrefixDescriptionFor(str);
            if (prefixDescriptionFor2 != null) {
                prefixDescriptionFor = prefixDescriptionFor2;
            }
            if (str2.equals(str)) {
                return prefixDescriptionFor;
            }
        }
        throw new ParameterException("Unknown parameter: " + str);
    }

    private String getSeparatorFor(String[] strArr, String str) {
        Parameters parameters;
        ParameterDescription descriptionFor = getDescriptionFor(strArr, str);
        if (descriptionFor != null && (parameters = (Parameters) descriptionFor.getObject().getClass().getAnnotation(Parameters.class)) != null) {
            return parameters.separators();
        }
        return " ";
    }

    private String getOptionPrefixes(String[] strArr, String str) {
        Parameters parameters;
        ParameterDescription descriptionFor = getDescriptionFor(strArr, str);
        if (descriptionFor != null && (parameters = (Parameters) descriptionFor.getObject().getClass().getAnnotation(Parameters.class)) != null) {
            return parameters.optionPrefixes();
        }
        StringBuilder sb = new StringBuilder();
        Iterator<Object> it = this.m_objects.iterator();
        while (it.hasNext()) {
            Parameters parameters2 = (Parameters) it.next().getClass().getAnnotation(Parameters.class);
            if (parameters2 != null && !Parameters.DEFAULT_OPTION_PREFIXES.equals(parameters2.optionPrefixes())) {
                sb.append(parameters2.optionPrefixes());
            }
        }
        return !Strings.isStringEmpty(sb.toString()) ? sb.toString() : Parameters.DEFAULT_OPTION_PREFIXES;
    }

    private static List<String> readFile(String str) {
        List<String> listNewArrayList = Lists.newArrayList();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(str));
            while (true) {
                String line = bufferedReader.readLine();
                if (line != null) {
                    if (line.length() > 0 && !line.trim().startsWith("#")) {
                        listNewArrayList.add(line);
                    }
                } else {
                    bufferedReader.close();
                    return listNewArrayList;
                }
            }
        } catch (IOException e) {
            throw new ParameterException("Could not read file " + str + ": " + e);
        }
    }

    private static String trim(String str) {
        String strTrim = str.trim();
        return (strTrim.startsWith("\"") && strTrim.endsWith("\"") && strTrim.length() > 1) ? strTrim.substring(1, strTrim.length() - 1) : strTrim;
    }

    private void createDescriptions() {
        this.m_descriptions = Maps.newHashMap();
        Iterator<Object> it = this.m_objects.iterator();
        while (it.hasNext()) {
            addDescription(it.next());
        }
    }

    private void addDescription(Object obj) {
        obj.getClass();
        Iterator<Parameterized> it = Parameterized.parseArg(obj).iterator();
        while (it.hasNext()) {
            Parameterized next = it.next();
            WrappedParameter wrappedParameter = next.getWrappedParameter();
            int i = 0;
            if (wrappedParameter != null && wrappedParameter.getParameter() != null) {
                Parameter parameter = wrappedParameter.getParameter();
                if (parameter.names().length == 0) {
                    p("Found main parameter:" + next);
                    if (this.m_mainParameter != null) {
                        throw new ParameterException("Only one @Parameter with no names attribute is allowed, found:" + this.m_mainParameter + " and " + next);
                    }
                    this.m_mainParameter = next;
                    this.m_mainParameterObject = obj;
                    this.m_mainParameterAnnotation = parameter;
                    this.m_mainParameterDescription = new ParameterDescription(obj, parameter, next, this.m_bundle, this);
                } else {
                    ParameterDescription parameterDescription = new ParameterDescription(obj, parameter, next, this.m_bundle, this);
                    String[] strArrNames = parameter.names();
                    int length = strArrNames.length;
                    while (i < length) {
                        String str = strArrNames[i];
                        if (this.m_descriptions.containsKey(new StringKey(str))) {
                            throw new ParameterException("Found the option " + str + " multiple times");
                        }
                        p("Adding description for " + str);
                        this.m_fields.put(next, parameterDescription);
                        this.m_descriptions.put(new StringKey(str), parameterDescription);
                        if (parameter.required()) {
                            this.m_requiredFields.put(next, parameterDescription);
                        }
                        i++;
                    }
                }
            } else if (next.getDelegateAnnotation() != null) {
                Object obj2 = next.get(obj);
                if (obj2 == null) {
                    throw new ParameterException("Delegate field '" + next.getName() + "' cannot be null.");
                }
                addDescription(obj2);
            } else if (wrappedParameter != null && wrappedParameter.getDynamicParameter() != null) {
                DynamicParameter dynamicParameter = wrappedParameter.getDynamicParameter();
                String[] strArrNames2 = dynamicParameter.names();
                int length2 = strArrNames2.length;
                while (i < length2) {
                    String str2 = strArrNames2[i];
                    if (this.m_descriptions.containsKey(str2)) {
                        throw new ParameterException("Found the option " + str2 + " multiple times");
                    }
                    p("Adding description for " + str2);
                    Iterator<Parameterized> it2 = it;
                    int i2 = length2;
                    ParameterDescription parameterDescription2 = new ParameterDescription(obj, dynamicParameter, next, this.m_bundle, this);
                    this.m_fields.put(next, parameterDescription2);
                    this.m_descriptions.put(new StringKey(str2), parameterDescription2);
                    if (dynamicParameter.required()) {
                        this.m_requiredFields.put(next, parameterDescription2);
                    }
                    i++;
                    length2 = i2;
                    it = it2;
                }
            }
            it = it;
        }
    }

    private void initializeDefaultValue(ParameterDescription parameterDescription) {
        for (String str : parameterDescription.getParameter().names()) {
            String defaultValueFor = this.m_defaultProvider.getDefaultValueFor(str);
            if (defaultValueFor != null) {
                p("Initializing " + str + " with default value:" + defaultValueFor);
                parameterDescription.addValue(defaultValueFor, true);
                return;
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:56:0x0135  */
    private void parseValues(String[] strArr, boolean z) {
        Object objConvertValue;
        int iProcessFixedArity;
        int i = 0;
        boolean z2 = false;
        boolean z3 = false;
        while (true) {
            int i2 = 1;
            if (i >= strArr.length || z2) {
                break;
            }
            String str = strArr[i];
            String strTrim = trim(str);
            strArr[i] = strTrim;
            p("Parsing arg: " + strTrim);
            JCommander jCommanderFindCommandByAlias = findCommandByAlias(str);
            if (!z3 && !"--".equals(strTrim) && isOption(strArr, strTrim) && jCommanderFindCommandByAlias == null) {
                ParameterDescription parameterDescriptionFindParameterDescription = findParameterDescription(strTrim);
                if (parameterDescriptionFindParameterDescription != null) {
                    if (parameterDescriptionFindParameterDescription.getParameter().password()) {
                        parameterDescriptionFindParameterDescription.addValue(new String(readPassword(parameterDescriptionFindParameterDescription.getDescription(), parameterDescriptionFindParameterDescription.getParameter().echoInput())));
                        this.m_requiredFields.remove(parameterDescriptionFindParameterDescription.getParameterized());
                    } else {
                        if (parameterDescriptionFindParameterDescription.getParameter().variableArity()) {
                            iProcessFixedArity = processVariableArity(strArr, i, parameterDescriptionFindParameterDescription);
                        } else {
                            Class<?> type = parameterDescriptionFindParameterDescription.getParameterized().getType();
                            if ((type == Boolean.TYPE || type == Boolean.class) && parameterDescriptionFindParameterDescription.getParameter().arity() == -1) {
                                parameterDescriptionFindParameterDescription.addValue("true");
                                this.m_requiredFields.remove(parameterDescriptionFindParameterDescription.getParameterized());
                                iProcessFixedArity = 1;
                            } else {
                                iProcessFixedArity = processFixedArity(strArr, i, parameterDescriptionFindParameterDescription, type);
                            }
                            if (parameterDescriptionFindParameterDescription.isHelp()) {
                                this.m_helpWasSpecified = true;
                            }
                        }
                        i2 = iProcessFixedArity;
                    }
                } else if (this.m_acceptUnknownOptions) {
                    this.m_unknownArgs.add(str);
                    i++;
                    while (i < strArr.length && !isOption(strArr, strArr[i])) {
                        this.m_unknownArgs.add(strArr[i]);
                        i++;
                    }
                    i2 = 0;
                } else {
                    throw new ParameterException("Unknown option: " + str);
                }
            } else if (Strings.isStringEmpty(str)) {
                continue;
            } else {
                if ("--".equals(str)) {
                    i++;
                    strTrim = trim(strArr[i]);
                    z3 = true;
                }
                if (this.m_commands.isEmpty()) {
                    List<?> mainParameter = getMainParameter(str);
                    if (this.m_mainParameter.getGenericType() instanceof ParameterizedType) {
                        Type type2 = ((ParameterizedType) this.m_mainParameter.getGenericType()).getActualTypeArguments()[0];
                        if (type2 instanceof Class) {
                            objConvertValue = convertValue(this.m_mainParameter, (Class) type2, strTrim);
                        } else {
                            objConvertValue = strTrim;
                        }
                    } else {
                        objConvertValue = strTrim;
                    }
                    ParameterDescription.validateParameter(this.m_mainParameterDescription, this.m_mainParameterAnnotation.validateWith(), "Default", strTrim);
                    this.m_mainParameterDescription.setAssigned(true);
                    mainParameter.add(objConvertValue);
                } else {
                    if (jCommanderFindCommandByAlias == null && z) {
                        throw new MissingCommandException("Expected a command, got " + str);
                    }
                    if (jCommanderFindCommandByAlias != null) {
                        this.m_parsedCommand = jCommanderFindCommandByAlias.m_programName.m_name;
                        this.m_parsedAlias = str;
                        jCommanderFindCommandByAlias.parse(subArray(strArr, i + 1));
                        z2 = true;
                    }
                }
            }
            i += i2;
        }
        for (ParameterDescription parameterDescription : this.m_descriptions.values()) {
            if (parameterDescription.isAssigned()) {
                this.m_fields.get(parameterDescription.getParameterized()).setAssigned(true);
            }
        }
    }

    private class DefaultVariableArity implements IVariableArity {
        private DefaultVariableArity() {
        }

        @Override // com.beust.jcommander.IVariableArity
        public int processVariableArity(String str, String[] strArr) {
            int i = 0;
            while (i < strArr.length && !JCommander.this.isOption(strArr, strArr[i])) {
                i++;
            }
            return i;
        }
    }

    private int processVariableArity(String[] strArr, int i, ParameterDescription parameterDescription) {
        IVariableArity iVariableArity;
        Object object = parameterDescription.getObject();
        if (!(object instanceof IVariableArity)) {
            iVariableArity = this.DEFAULT_VARIABLE_ARITY;
        } else {
            iVariableArity = (IVariableArity) object;
        }
        List listNewArrayList = Lists.newArrayList();
        for (int i2 = i + 1; i2 < strArr.length; i2++) {
            listNewArrayList.add(strArr[i2]);
        }
        return processFixedArity(strArr, i, parameterDescription, List.class, iVariableArity.processVariableArity(parameterDescription.getParameter().names()[0], (String[]) listNewArrayList.toArray(new String[0])));
    }

    private int processFixedArity(String[] strArr, int i, ParameterDescription parameterDescription, Class<?> cls) {
        int iArity = parameterDescription.getParameter().arity();
        if (iArity == -1) {
            iArity = 1;
        }
        return processFixedArity(strArr, i, parameterDescription, cls, iArity);
    }

    private int processFixedArity(String[] strArr, int i, ParameterDescription parameterDescription, Class<?> cls, int i2) {
        String str = strArr[i];
        if (i2 == 0 && (Boolean.class.isAssignableFrom(cls) || Boolean.TYPE.isAssignableFrom(cls))) {
            parameterDescription.addValue("true");
            this.m_requiredFields.remove(parameterDescription.getParameterized());
        } else if (i < strArr.length - 1) {
            boolean zEquals = "--".equals(strArr[i + 1]);
            if (i + i2 >= strArr.length) {
                throw new ParameterException("Expected " + i2 + " values after " + str);
            }
            for (int i3 = 1; i3 <= i2; i3++) {
                parameterDescription.addValue(trim(strArr[i + i3 + (zEquals ? 1 : 0)]));
                this.m_requiredFields.remove(parameterDescription.getParameterized());
            }
        } else {
            throw new ParameterException("Expected a value after parameter " + str);
        }
        return i2 + 1;
    }

    private char[] readPassword(String str, boolean z) {
        getConsole().print(str + ": ");
        return getConsole().readPassword(z);
    }

    private String[] subArray(String[] strArr, int i) {
        int length = strArr.length - i;
        String[] strArr2 = new String[length];
        System.arraycopy(strArr, i, strArr2, 0, length);
        return strArr2;
    }

    private List<?> getMainParameter(String str) {
        Parameterized parameterized = this.m_mainParameter;
        if (parameterized == null) {
            throw new ParameterException("Was passed main parameter '" + str + "' but no main parameter was defined");
        }
        List<?> listNewArrayList = (List) parameterized.get(this.m_mainParameterObject);
        if (listNewArrayList == null) {
            listNewArrayList = Lists.newArrayList();
            if (!List.class.isAssignableFrom(this.m_mainParameter.getType())) {
                throw new ParameterException("Main parameter field " + this.m_mainParameter + " needs to be of type List, not " + this.m_mainParameter.getType());
            }
            this.m_mainParameter.set(this.m_mainParameterObject, listNewArrayList);
        }
        if (this.m_firstTimeMainParameter) {
            listNewArrayList.clear();
            this.m_firstTimeMainParameter = false;
        }
        return listNewArrayList;
    }

    public String getMainParameterDescription() {
        if (this.m_descriptions == null) {
            createDescriptions();
        }
        Parameter parameter = this.m_mainParameterAnnotation;
        if (parameter != null) {
            return parameter.description();
        }
        return null;
    }

    public void setProgramName(String str) {
        setProgramName(str, new String[0]);
    }

    public void setProgramName(String str, String... strArr) {
        this.m_programName = new ProgramName(str, Arrays.asList(strArr));
    }

    public void usage(String str) {
        StringBuilder sb = new StringBuilder();
        usage(str, sb);
        getConsole().println(sb.toString());
    }

    public void usage(String str, StringBuilder sb) {
        usage(str, sb, "");
    }

    public void usage(String str, StringBuilder sb, String str2) {
        String commandDescription = getCommandDescription(str);
        JCommander jCommanderFindCommandByAlias = findCommandByAlias(str);
        if (commandDescription != null) {
            sb.append(str2).append(commandDescription);
            sb.append("\n");
        }
        jCommanderFindCommandByAlias.usage(sb, str2);
    }

    public String getCommandDescription(String str) {
        java.util.ResourceBundle bundle;
        JCommander jCommanderFindCommandByAlias = findCommandByAlias(str);
        if (jCommanderFindCommandByAlias == null) {
            throw new ParameterException("Asking description for unknown command: " + str);
        }
        Parameters parameters = (Parameters) jCommanderFindCommandByAlias.getObjects().get(0).getClass().getAnnotation(Parameters.class);
        if (parameters == null) {
            return null;
        }
        String strCommandDescription = parameters.commandDescription();
        String strResourceBundle = parameters.resourceBundle();
        if (!"".equals(strResourceBundle)) {
            bundle = java.util.ResourceBundle.getBundle(strResourceBundle, Locale.getDefault());
        } else {
            bundle = this.m_bundle;
        }
        return bundle != null ? getI18nString(bundle, parameters.commandDescriptionKey(), parameters.commandDescription()) : strCommandDescription;
    }

    private String getI18nString(java.util.ResourceBundle resourceBundle, String str, String str2) {
        String string = resourceBundle != null ? resourceBundle.getString(str) : null;
        return string != null ? string : str2;
    }

    public void usage() {
        StringBuilder sb = new StringBuilder();
        usage(sb);
        getConsole().println(sb.toString());
    }

    public void usage(StringBuilder sb) {
        usage(sb, "");
    }

    public void usage(StringBuilder sb, String str) {
        if (this.m_descriptions == null) {
            createDescriptions();
        }
        boolean zIsEmpty = this.m_commands.isEmpty();
        ProgramName programName = this.m_programName;
        sb.append(str).append("Usage: " + (programName != null ? programName.getDisplayName() : "<main class>") + " [options]");
        if (!zIsEmpty) {
            sb.append(str).append(" [command] [command options]");
        }
        if (this.m_mainParameterDescription != null) {
            sb.append(" " + this.m_mainParameterDescription.getDescription());
        }
        sb.append("\n");
        List<ParameterDescription> listNewArrayList = Lists.newArrayList();
        int i = 0;
        for (ParameterDescription parameterDescription : this.m_fields.values()) {
            if (!parameterDescription.getParameter().hidden()) {
                listNewArrayList.add(parameterDescription);
                int length = parameterDescription.getNames().length() + 2;
                if (length > i) {
                    i = length;
                }
            }
        }
        Collections.sort(listNewArrayList, getParameterDescriptionComparator());
        if (listNewArrayList.size() > 0) {
            sb.append(str).append("  Options:\n");
        }
        for (ParameterDescription parameterDescription2 : listNewArrayList) {
            WrappedParameter parameter = parameterDescription2.getParameter();
            sb.append(str).append("  " + (parameter.required() ? "* " : "  ") + parameterDescription2.getNames() + "\n" + str + s(6));
            int length2 = str.length();
            wrapDescription(sb, length2 + 6, parameterDescription2.getDescription());
            Object obj = parameterDescription2.getDefault();
            if (parameterDescription2.isDynamicParameter()) {
                sb.append("\n" + s(length2 + 7)).append("Syntax: " + parameter.names()[0] + ASTExpr.DEFAULT_MAP_KEY_NAME + parameter.getAssignment() + "value");
            }
            if (obj != null) {
                String string = Strings.isStringEmpty(obj.toString()) ? "<empty string>" : obj.toString();
                StringBuilder sbAppend = sb.append("\n" + s(length2 + 7));
                StringBuilder sb2 = new StringBuilder("Default: ");
                if (parameter.password()) {
                    string = "********";
                }
                sbAppend.append(sb2.append(string).toString());
            }
            Class<?> type = parameterDescription2.getParameterized().getType();
            if (type.isEnum()) {
                sb.append("\n" + s(length2 + 7)).append("Possible Values: " + EnumSet.allOf(type));
            }
            sb.append("\n");
        }
        if (zIsEmpty) {
            return;
        }
        sb.append("  Commands:\n");
        for (Map.Entry<ProgramName, JCommander> entry : this.m_commands.entrySet()) {
            if (!((Parameters) entry.getValue().getObjects().get(0).getClass().getAnnotation(Parameters.class)).hidden()) {
                ProgramName key = entry.getKey();
                sb.append(str).append("    " + key.getDisplayName());
                usage(key.getName(), sb, "      ");
                sb.append("\n");
            }
        }
    }

    private Comparator<? super ParameterDescription> getParameterDescriptionComparator() {
        return this.m_parameterDescriptionComparator;
    }

    public void setParameterDescriptionComparator(Comparator<? super ParameterDescription> comparator) {
        this.m_parameterDescriptionComparator = comparator;
    }

    public void setColumnSize(int i) {
        this.m_columnSize = i;
    }

    public int getColumnSize() {
        return this.m_columnSize;
    }

    private void wrapDescription(StringBuilder sb, int i, String str) {
        int columnSize = getColumnSize();
        int length = i;
        for (String str2 : str.split(" ")) {
            if (str2.length() > columnSize || str2.length() + length <= columnSize) {
                sb.append(" ").append(str2);
                length += str2.length() + 1;
            } else {
                sb.append("\n").append(s(i + 1)).append(str2);
                length = i;
            }
        }
    }

    public List<ParameterDescription> getParameters() {
        return new ArrayList(this.m_fields.values());
    }

    public ParameterDescription getMainParameter() {
        return this.m_mainParameterDescription;
    }

    private void p(String str) {
        if (this.m_verbose > 0 || System.getProperty(DEBUG_PROPERTY) != null) {
            getConsole().println("[JCommander] " + str);
        }
    }

    public void setDefaultProvider(IDefaultProvider iDefaultProvider) {
        this.m_defaultProvider = iDefaultProvider;
        Iterator<Map.Entry<ProgramName, JCommander>> it = this.m_commands.entrySet().iterator();
        while (it.hasNext()) {
            it.next().getValue().setDefaultProvider(iDefaultProvider);
        }
    }

    public void addConverterFactory(IStringConverterFactory iStringConverterFactory) {
        CONVERTER_FACTORIES.addFirst(iStringConverterFactory);
    }

    public <T> Class<? extends IStringConverter<T>> findConverter(Class<T> cls) {
        Iterator<IStringConverterFactory> it = CONVERTER_FACTORIES.iterator();
        while (it.hasNext()) {
            Class<? extends IStringConverter<T>> converter = it.next().getConverter(cls);
            if (converter != null) {
                return converter;
            }
        }
        return null;
    }

    public Object convertValue(ParameterDescription parameterDescription, String str) {
        return convertValue(parameterDescription.getParameterized(), parameterDescription.getParameterized().getType(), str);
    }

    /* JADX WARN: Code duplicated, block: B:25:0x004c  */
    /* JADX WARN: Multi-variable type inference failed */
    public Object convertValue(Parameterized parameterized, Class cls, String str) {
        Parameter parameter = parameterized.getParameter();
        if (parameter == null) {
            return str;
        }
        Class clsConverter = parameter.converter();
        boolean z = parameter.listConverter() != NoConverter.class;
        if (clsConverter == null || clsConverter == NoConverter.class) {
            clsConverter = cls.isEnum() ? cls : findConverter(cls);
        }
        if (clsConverter == null) {
            Type typeFindFieldGenericType = parameterized.findFieldGenericType();
            Class<StringConverter> clsFindConverter = typeFindFieldGenericType != null ? findConverter((Class) typeFindFieldGenericType) : StringConverter.class;
            if (clsFindConverter == null) {
                clsConverter = (Class) typeFindFieldGenericType;
                if (!Enum.class.isAssignableFrom(clsConverter)) {
                    clsConverter = clsFindConverter;
                }
            } else {
                clsConverter = clsFindConverter;
            }
        }
        try {
            String[] strArrNames = parameter.names();
            String str2 = strArrNames.length > 0 ? strArrNames[0] : "[Main class]";
            if (clsConverter != null && clsConverter.isEnum()) {
                try {
                    try {
                        return Enum.valueOf(clsConverter, str);
                    } catch (IllegalArgumentException unused) {
                        throw new ParameterException("Invalid value for " + str2 + " parameter. Allowed values:" + EnumSet.allOf(clsConverter));
                    }
                } catch (IllegalArgumentException unused2) {
                    return Enum.valueOf(clsConverter, str.toUpperCase());
                } catch (Exception unused3) {
                    throw new ParameterException("Invalid value for " + str2 + " parameter. Allowed values:" + EnumSet.allOf(clsConverter));
                }
            }
            IStringConverter<?> iStringConverterInstantiateConverter = instantiateConverter(str2, clsConverter);
            if (cls.isAssignableFrom(List.class) && (parameterized.getGenericType() instanceof ParameterizedType)) {
                return z ? instantiateConverter(str2, parameter.listConverter()).convert(str) : convertToList(str, iStringConverterInstantiateConverter, parameter.splitter());
            }
            return iStringConverterInstantiateConverter.convert(str);
        } catch (IllegalAccessException e) {
            throw new ParameterException(e);
        } catch (InstantiationException e2) {
            throw new ParameterException(e2);
        } catch (InvocationTargetException e3) {
            throw new ParameterException(e3);
        }
    }

    private Object convertToList(String str, IStringConverter<?> iStringConverter, Class<? extends IParameterSplitter> cls) throws IllegalAccessException, InstantiationException {
        IParameterSplitter iParameterSplitterNewInstance = cls.newInstance();
        List listNewArrayList = Lists.newArrayList();
        Iterator<String> it = iParameterSplitterNewInstance.split(str).iterator();
        while (it.hasNext()) {
            listNewArrayList.add(iStringConverter.convert(it.next()));
        }
        return listNewArrayList;
    }

    private IStringConverter<?> instantiateConverter(String str, Class<? extends IStringConverter<?>> cls) throws IllegalAccessException, InstantiationException, IllegalArgumentException, InvocationTargetException {
        Object objNewInstance;
        Constructor<?> constructor = null;
        Constructor<?> constructor2 = null;
        for (Constructor<?> constructor3 : cls.getDeclaredConstructors()) {
            Class<?>[] parameterTypes = constructor3.getParameterTypes();
            if (parameterTypes.length == 1 && parameterTypes[0].equals(String.class)) {
                constructor = constructor3;
            } else if (parameterTypes.length == 0) {
                constructor2 = constructor3;
            }
        }
        if (constructor != null) {
            objNewInstance = constructor.newInstance(str);
        } else {
            if (constructor2 == null) {
                return null;
            }
            objNewInstance = constructor2.newInstance(new Object[0]);
        }
        return (IStringConverter) objNewInstance;
    }

    public void addCommand(String str, Object obj) {
        addCommand(str, obj, new String[0]);
    }

    public void addCommand(Object obj) {
        Parameters parameters = (Parameters) obj.getClass().getAnnotation(Parameters.class);
        if (parameters != null && parameters.commandNames().length > 0) {
            for (String str : parameters.commandNames()) {
                addCommand(str, obj);
            }
            return;
        }
        throw new ParameterException("Trying to add command " + obj.getClass().getName() + " without specifying its names in @Parameters");
    }

    public void addCommand(String str, Object obj, String... strArr) {
        JCommander jCommander = new JCommander(obj);
        jCommander.setProgramName(str, strArr);
        jCommander.setDefaultProvider(this.m_defaultProvider);
        jCommander.setAcceptUnknownOptions(this.m_acceptUnknownOptions);
        ProgramName programName = jCommander.m_programName;
        this.m_commands.put(programName, jCommander);
        this.aliasMap.put(new StringKey(str), programName);
        for (String str2 : strArr) {
            StringKey stringKey = new StringKey(str2);
            if (!stringKey.equals(str)) {
                ProgramName programName2 = this.aliasMap.get(stringKey);
                if (programName2 != null && !programName2.equals(programName)) {
                    throw new ParameterException("Cannot set alias " + stringKey + " for " + str + " command because it has already been defined for " + programName2.m_name + " command");
                }
                this.aliasMap.put(stringKey, programName);
            }
        }
    }

    public Map<String, JCommander> getCommands() {
        Map<String, JCommander> mapNewLinkedHashMap = Maps.newLinkedHashMap();
        for (Map.Entry<ProgramName, JCommander> entry : this.m_commands.entrySet()) {
            mapNewLinkedHashMap.put(entry.getKey().m_name, entry.getValue());
        }
        return mapNewLinkedHashMap;
    }

    public String getParsedCommand() {
        return this.m_parsedCommand;
    }

    public String getParsedAlias() {
        return this.m_parsedAlias;
    }

    private String s(int i) {
        StringBuilder sb = new StringBuilder();
        for (int i2 = 0; i2 < i; i2++) {
            sb.append(" ");
        }
        return sb.toString();
    }

    public List<Object> getObjects() {
        return this.m_objects;
    }

    private ParameterDescription findParameterDescription(String str) {
        return (ParameterDescription) FuzzyMap.findInMap(this.m_descriptions, new StringKey(str), this.m_caseSensitiveOptions, this.m_allowAbbreviatedOptions);
    }

    private JCommander findCommand(ProgramName programName) {
        return (JCommander) FuzzyMap.findInMap(this.m_commands, programName, this.m_caseSensitiveOptions, this.m_allowAbbreviatedOptions);
    }

    private ProgramName findProgramName(String str) {
        return (ProgramName) FuzzyMap.findInMap(this.aliasMap, new StringKey(str), this.m_caseSensitiveOptions, this.m_allowAbbreviatedOptions);
    }

    private JCommander findCommandByAlias(String str) {
        ProgramName programNameFindProgramName = findProgramName(str);
        if (programNameFindProgramName == null) {
            return null;
        }
        JCommander jCommanderFindCommand = findCommand(programNameFindProgramName);
        if (jCommanderFindCommand != null) {
            return jCommanderFindCommand;
        }
        throw new IllegalStateException("There appears to be inconsistency in the internal command database.  This is likely a bug. Please report.");
    }

    private static final class ProgramName implements FuzzyMap.IKey {
        private final List<String> m_aliases;
        private final String m_name;

        ProgramName(String str, List<String> list) {
            this.m_name = str;
            this.m_aliases = list;
        }

        @Override // com.beust.jcommander.FuzzyMap.IKey
        public String getName() {
            return this.m_name;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public String getDisplayName() {
            StringBuilder sb = new StringBuilder();
            sb.append(this.m_name);
            if (!this.m_aliases.isEmpty()) {
                sb.append("(");
                Iterator<String> it = this.m_aliases.iterator();
                while (it.hasNext()) {
                    sb.append(it.next());
                    if (it.hasNext()) {
                        sb.append(",");
                    }
                }
                sb.append(Constant.AFTER_QUTO);
            }
            return sb.toString();
        }

        public int hashCode() {
            String str = this.m_name;
            return 31 + (str == null ? 0 : str.hashCode());
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            ProgramName programName = (ProgramName) obj;
            String str = this.m_name;
            if (str == null) {
                if (programName.m_name != null) {
                    return false;
                }
            } else if (!str.equals(programName.m_name)) {
                return false;
            }
            return true;
        }

        public String toString() {
            return getDisplayName();
        }
    }

    public void setVerbose(int i) {
        this.m_verbose = i;
    }

    public void setCaseSensitiveOptions(boolean z) {
        this.m_caseSensitiveOptions = z;
    }

    public void setAllowAbbreviatedOptions(boolean z) {
        this.m_allowAbbreviatedOptions = z;
    }

    public void setAcceptUnknownOptions(boolean z) {
        this.m_acceptUnknownOptions = z;
    }

    public List<String> getUnknownOptions() {
        return this.m_unknownArgs;
    }

    public void setAllowParameterOverwriting(boolean z) {
        this.m_allowParameterOverwriting = z;
    }

    public boolean isParameterOverwritingAllowed() {
        return this.m_allowParameterOverwriting;
    }
}
