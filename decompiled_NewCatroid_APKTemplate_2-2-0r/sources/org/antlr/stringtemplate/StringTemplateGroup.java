package org.antlr.stringtemplate;

import com.huawei.hms.ml.language.common.utils.Constant;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import name.antonsmirnov.firmata.FormatHelper;
import org.antlr.stringtemplate.language.GroupLexer;
import org.antlr.stringtemplate.language.GroupParser;
import org.apache.commons.lang3.SystemProperties;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.jetbrains.kotlin.name.JvmNames;

/* JADX INFO: loaded from: classes12.dex */
public class StringTemplateGroup {
    public static StringTemplateErrorListener DEFAULT_ERROR_LISTENER;
    protected static final StringTemplate NOT_FOUND_ST;
    static /* synthetic */ Class class$java$io$Writer;
    static /* synthetic */ Class class$org$antlr$stringtemplate$language$AngleBracketTemplateLexer;
    static /* synthetic */ Class class$org$antlr$stringtemplate$language$DefaultTemplateLexer;
    protected static Class defaultTemplateLexerClass;
    private static StringTemplateGroupLoader groupLoader;
    protected static Map nameToGroupMap;
    protected static Map nameToInterfaceMap;
    protected Map attributeRenderers;
    protected boolean debugTemplateOutput;
    String fileCharEncoding;
    protected List interfaces;
    protected long lastCheckedDisk;
    protected StringTemplateErrorListener listener;
    protected Map maps;

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    protected String f426name;
    protected Set noDebugStartStopStrings;
    protected int refreshIntervalInSeconds;
    protected String rootDir;
    protected StringTemplateGroup superGroup;
    protected Class templateLexerClass;
    protected Map templates;
    protected boolean templatesDefinedInGroupFile;
    protected Class userSpecifiedWriter;

    static {
        Class clsClass$ = class$org$antlr$stringtemplate$language$DefaultTemplateLexer;
        if (clsClass$ == null) {
            clsClass$ = class$("org.antlr.stringtemplate.language.DefaultTemplateLexer");
            class$org$antlr$stringtemplate$language$DefaultTemplateLexer = clsClass$;
        }
        defaultTemplateLexerClass = clsClass$;
        nameToGroupMap = Collections.synchronizedMap(new HashMap());
        nameToInterfaceMap = Collections.synchronizedMap(new HashMap());
        groupLoader = null;
        DEFAULT_ERROR_LISTENER = new StringTemplateErrorListener() { // from class: org.antlr.stringtemplate.StringTemplateGroup.1
            @Override // org.antlr.stringtemplate.StringTemplateErrorListener
            public void error(String str, Throwable th) {
                System.err.println(str);
                if (th != null) {
                    th.printStackTrace(System.err);
                }
            }

            @Override // org.antlr.stringtemplate.StringTemplateErrorListener
            public void warning(String str) {
                System.out.println(str);
            }
        };
        NOT_FOUND_ST = new StringTemplate();
    }

    static /* synthetic */ Class class$(String str) throws Throwable {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError().initCause(e);
        }
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public StringTemplateGroup(String str, String str2) throws Throwable {
        Class clsClass$ = class$org$antlr$stringtemplate$language$DefaultTemplateLexer;
        if (clsClass$ == null) {
            clsClass$ = class$("org.antlr.stringtemplate.language.DefaultTemplateLexer");
            class$org$antlr$stringtemplate$language$DefaultTemplateLexer = clsClass$;
        }
        this(str, str2, clsClass$);
    }

    public StringTemplateGroup(String str, String str2, Class cls) {
        this.templates = new HashMap();
        this.maps = new HashMap();
        this.templateLexerClass = null;
        this.rootDir = null;
        this.superGroup = null;
        this.interfaces = null;
        this.templatesDefinedInGroupFile = false;
        this.debugTemplateOutput = false;
        this.listener = DEFAULT_ERROR_LISTENER;
        this.refreshIntervalInSeconds = 2147483;
        this.lastCheckedDisk = 0L;
        this.fileCharEncoding = System.getProperty(SystemProperties.FILE_ENCODING);
        this.f426name = str;
        this.rootDir = str2;
        this.lastCheckedDisk = System.currentTimeMillis();
        nameToGroupMap.put(str, this);
        this.templateLexerClass = cls;
    }

    public StringTemplateGroup(String str) {
        this(str, (String) null, (Class) null);
    }

    public StringTemplateGroup(String str, Class cls) {
        this(str, (String) null, cls);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public StringTemplateGroup(Reader reader) throws Throwable {
        Class clsClass$ = class$org$antlr$stringtemplate$language$AngleBracketTemplateLexer;
        if (clsClass$ == null) {
            clsClass$ = class$("org.antlr.stringtemplate.language.AngleBracketTemplateLexer");
            class$org$antlr$stringtemplate$language$AngleBracketTemplateLexer = clsClass$;
        }
        this(reader, clsClass$, DEFAULT_ERROR_LISTENER, null);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public StringTemplateGroup(Reader reader, StringTemplateErrorListener stringTemplateErrorListener) throws Throwable {
        Class clsClass$ = class$org$antlr$stringtemplate$language$AngleBracketTemplateLexer;
        if (clsClass$ == null) {
            clsClass$ = class$("org.antlr.stringtemplate.language.AngleBracketTemplateLexer");
            class$org$antlr$stringtemplate$language$AngleBracketTemplateLexer = clsClass$;
        }
        this(reader, clsClass$, stringTemplateErrorListener, null);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public StringTemplateGroup(Reader reader, Class cls) {
        this(reader, cls, null, null);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public StringTemplateGroup(Reader reader, Class cls, StringTemplateErrorListener stringTemplateErrorListener) {
        this(reader, cls, stringTemplateErrorListener, null);
    }

    public StringTemplateGroup(Reader reader, Class cls, StringTemplateErrorListener stringTemplateErrorListener, StringTemplateGroup stringTemplateGroup) throws Throwable {
        this.templates = new HashMap();
        this.maps = new HashMap();
        this.templateLexerClass = null;
        this.rootDir = null;
        this.superGroup = null;
        this.interfaces = null;
        this.templatesDefinedInGroupFile = false;
        this.debugTemplateOutput = false;
        this.listener = DEFAULT_ERROR_LISTENER;
        this.refreshIntervalInSeconds = 2147483;
        this.lastCheckedDisk = 0L;
        this.fileCharEncoding = System.getProperty(SystemProperties.FILE_ENCODING);
        this.templatesDefinedInGroupFile = true;
        if (cls == null && (cls = class$org$antlr$stringtemplate$language$AngleBracketTemplateLexer) == null) {
            cls = class$("org.antlr.stringtemplate.language.AngleBracketTemplateLexer");
            class$org$antlr$stringtemplate$language$AngleBracketTemplateLexer = cls;
        }
        this.templateLexerClass = cls;
        if (stringTemplateErrorListener != null) {
            this.listener = stringTemplateErrorListener;
        }
        setSuperGroup(stringTemplateGroup);
        parseGroup(reader);
        nameToGroupMap.put(this.f426name, this);
        verifyInterfaceImplementations();
    }

    public Class getTemplateLexerClass() {
        Class cls = this.templateLexerClass;
        return cls != null ? cls : defaultTemplateLexerClass;
    }

    public String getName() {
        return this.f426name;
    }

    public void setName(String str) {
        this.f426name = str;
    }

    public void setSuperGroup(StringTemplateGroup stringTemplateGroup) {
        this.superGroup = stringTemplateGroup;
    }

    public void setSuperGroup(String str) {
        StringTemplateGroup stringTemplateGroup = (StringTemplateGroup) nameToGroupMap.get(str);
        if (stringTemplateGroup != null) {
            setSuperGroup(stringTemplateGroup);
            return;
        }
        StringTemplateGroup stringTemplateGroupLoadGroup = loadGroup(str, this.templateLexerClass, null);
        if (stringTemplateGroupLoadGroup != null) {
            nameToGroupMap.put(str, stringTemplateGroupLoadGroup);
            setSuperGroup(stringTemplateGroupLoadGroup);
        } else if (groupLoader == null) {
            this.listener.error("no group loader registered", null);
        }
    }

    public void implementInterface(StringTemplateGroupInterface stringTemplateGroupInterface) {
        if (this.interfaces == null) {
            this.interfaces = new ArrayList();
        }
        this.interfaces.add(stringTemplateGroupInterface);
    }

    public void implementInterface(String str) {
        StringTemplateGroupInterface stringTemplateGroupInterface = (StringTemplateGroupInterface) nameToInterfaceMap.get(str);
        if (stringTemplateGroupInterface != null) {
            implementInterface(stringTemplateGroupInterface);
            return;
        }
        StringTemplateGroupInterface stringTemplateGroupInterfaceLoadInterface = loadInterface(str);
        if (stringTemplateGroupInterfaceLoadInterface != null) {
            nameToInterfaceMap.put(str, stringTemplateGroupInterfaceLoadInterface);
            implementInterface(stringTemplateGroupInterfaceLoadInterface);
        } else if (groupLoader == null) {
            this.listener.error("no group loader registered", null);
        }
    }

    public StringTemplateGroup getSuperGroup() {
        return this.superGroup;
    }

    public String getGroupHierarchyStackString() {
        LinkedList linkedList = new LinkedList();
        for (StringTemplateGroup stringTemplateGroup = this; stringTemplateGroup != null; stringTemplateGroup = stringTemplateGroup.superGroup) {
            linkedList.add(0, stringTemplateGroup.f426name);
        }
        return linkedList.toString().replaceAll(",", "");
    }

    public String getRootDir() {
        return this.rootDir;
    }

    public void setRootDir(String str) {
        this.rootDir = str;
    }

    public StringTemplate createStringTemplate() {
        return new StringTemplate();
    }

    protected StringTemplate getInstanceOf(StringTemplate stringTemplate, String str) throws IllegalArgumentException {
        StringTemplate stringTemplateLookupTemplate = lookupTemplate(stringTemplate, str);
        if (stringTemplateLookupTemplate != null) {
            return stringTemplateLookupTemplate.getInstanceOf();
        }
        return null;
    }

    public StringTemplate getInstanceOf(String str) {
        return getInstanceOf((StringTemplate) null, str);
    }

    public StringTemplate getInstanceOf(String str, Map map) {
        StringTemplate instanceOf = getInstanceOf(str);
        instanceOf.attributes = map;
        return instanceOf;
    }

    public StringTemplate getEmbeddedInstanceOf(StringTemplate stringTemplate, String str) throws IllegalArgumentException {
        StringTemplate instanceOf;
        if (str.startsWith("super.")) {
            instanceOf = stringTemplate.getNativeGroup().getInstanceOf(stringTemplate, str);
        } else {
            instanceOf = getInstanceOf(stringTemplate, str);
        }
        instanceOf.setGroup(this);
        instanceOf.setEnclosingInstance(stringTemplate);
        return instanceOf;
    }

    public synchronized StringTemplate lookupTemplate(StringTemplate stringTemplate, String str) throws IllegalArgumentException {
        StringTemplateGroup stringTemplateGroup;
        if (str.startsWith("super.")) {
            if (this.superGroup != null) {
                return this.superGroup.lookupTemplate(stringTemplate, str.substring(str.indexOf(46) + 1, str.length()));
            }
            throw new IllegalArgumentException(new StringBuffer().append(getName()).append(" has no super group; invalid template: ").append(str).toString());
        }
        checkRefreshInterval();
        StringTemplate instanceOf = (StringTemplate) this.templates.get(str);
        if (instanceOf == null) {
            if (!this.templatesDefinedInGroupFile) {
                instanceOf = loadTemplateFromBeneathRootDirOrCLASSPATH(getFileNameFromTemplateName(str));
            }
            if (instanceOf == null && (stringTemplateGroup = this.superGroup) != null && (instanceOf = stringTemplateGroup.getInstanceOf(str)) != null) {
                instanceOf.setGroup(this);
            }
            if (instanceOf != null) {
                this.templates.put(str, instanceOf);
            } else {
                this.templates.put(str, NOT_FOUND_ST);
                String string = "";
                if (stringTemplate != null) {
                    string = new StringBuffer("; context is ").append(stringTemplate.getEnclosingInstanceStackString()).toString();
                }
                throw new IllegalArgumentException(new StringBuffer("Can't find template ").append(getFileNameFromTemplateName(str)).append(new StringBuffer().append(string).append("; group hierarchy is ").append(getGroupHierarchyStackString()).toString()).toString());
            }
        } else if (instanceOf == NOT_FOUND_ST) {
            return null;
        }
        return instanceOf;
    }

    public StringTemplate lookupTemplate(String str) {
        return lookupTemplate(null, str);
    }

    protected void checkRefreshInterval() {
        if (this.templatesDefinedInGroupFile) {
            return;
        }
        if (this.refreshIntervalInSeconds == 0 || System.currentTimeMillis() - this.lastCheckedDisk >= this.refreshIntervalInSeconds * 1000) {
            this.templates.clear();
            this.lastCheckedDisk = System.currentTimeMillis();
        }
    }

    protected StringTemplate loadTemplate(String str, BufferedReader bufferedReader) throws IOException {
        String property = System.getProperty(SystemProperties.LINE_SEPARATOR);
        StringBuffer stringBuffer = new StringBuffer(300);
        while (true) {
            String line = bufferedReader.readLine();
            if (line == null) {
                break;
            }
            stringBuffer.append(line);
            stringBuffer.append(property);
        }
        String strTrim = stringBuffer.toString().trim();
        if (strTrim.length() == 0) {
            error(new StringBuffer("no text in template '").append(str).append(FormatHelper.QUOTE).toString());
            return null;
        }
        return defineTemplate(str, strTrim);
    }

    protected StringTemplate loadTemplateFromBeneathRootDirOrCLASSPATH(String str) throws Throwable {
        BufferedReader bufferedReader;
        StringBuffer stringBuffer;
        String templateNameFromFileName = getTemplateNameFromFileName(str);
        if (this.rootDir != null) {
            return loadTemplate(templateNameFromFileName, new StringBuffer().append(this.rootDir).append("/").append(str).toString());
        }
        InputStream resourceAsStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(str);
        if (resourceAsStream == null) {
            resourceAsStream = getClass().getClassLoader().getResourceAsStream(str);
        }
        BufferedReader bufferedReader2 = null;
        stringTemplateLoadTemplate = null;
        stringTemplateLoadTemplate = null;
        StringTemplate stringTemplateLoadTemplate = null;
        try {
            if (resourceAsStream == null) {
                return null;
            }
            try {
                bufferedReader = new BufferedReader(getInputStreamReader(resourceAsStream));
                try {
                    stringTemplateLoadTemplate = loadTemplate(templateNameFromFileName, bufferedReader);
                    try {
                        bufferedReader.close();
                    } catch (IOException e) {
                        e = e;
                        stringBuffer = new StringBuffer("Cannot close template file: ");
                        error(stringBuffer.append(str).toString(), e);
                    }
                } catch (IOException e2) {
                    e = e2;
                    error(new StringBuffer("Problem reading template file: ").append(str).toString(), e);
                    if (bufferedReader != null) {
                        try {
                            bufferedReader.close();
                        } catch (IOException e3) {
                            e = e3;
                            stringBuffer = new StringBuffer("Cannot close template file: ");
                            error(stringBuffer.append(str).toString(), e);
                        }
                    }
                }
            } catch (IOException e4) {
                e = e4;
                bufferedReader = null;
            } catch (Throwable th) {
                th = th;
            }
            return stringTemplateLoadTemplate;
        } catch (Throwable th2) {
            th = th2;
            bufferedReader2 = bufferedReader;
        }
        if (bufferedReader2 != null) {
            try {
                bufferedReader2.close();
            } catch (IOException e5) {
                error(new StringBuffer("Cannot close template file: ").append(str).toString(), e5);
            }
        }
        throw th;
    }

    public String getFileNameFromTemplateName(String str) {
        return new StringBuffer().append(str).append(".st").toString();
    }

    public String getTemplateNameFromFileName(String str) {
        int iLastIndexOf = str.lastIndexOf(".st");
        return iLastIndexOf >= 0 ? str.substring(0, iLastIndexOf) : str;
    }

    protected StringTemplate loadTemplate(String str, String str2) {
        StringTemplate stringTemplate;
        BufferedReader bufferedReader = null;
        StringTemplate stringTemplateLoadTemplate = null;
        try {
            BufferedReader bufferedReader2 = new BufferedReader(getInputStreamReader(new FileInputStream(str2)));
            try {
                stringTemplateLoadTemplate = loadTemplate(str, bufferedReader2);
                bufferedReader2.close();
                return stringTemplateLoadTemplate;
            } catch (IOException unused) {
                stringTemplate = stringTemplateLoadTemplate;
                bufferedReader = bufferedReader2;
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (IOException unused2) {
                        error(new StringBuffer("Cannot close template file: ").append(str2).toString());
                    }
                }
                return stringTemplate;
            }
        } catch (IOException unused3) {
            stringTemplate = null;
        }
    }

    protected InputStreamReader getInputStreamReader(InputStream inputStream) {
        try {
            return new InputStreamReader(inputStream, this.fileCharEncoding);
        } catch (UnsupportedEncodingException unused) {
            error(new StringBuffer("Invalid file character encoding: ").append(this.fileCharEncoding).toString());
            return null;
        }
    }

    public String getFileCharEncoding() {
        return this.fileCharEncoding;
    }

    public void setFileCharEncoding(String str) {
        this.fileCharEncoding = str;
    }

    public synchronized StringTemplate defineTemplate(String str, String str2) {
        StringTemplate stringTemplateCreateStringTemplate;
        if (str != null) {
            if (str.indexOf(46) >= 0) {
                throw new IllegalArgumentException("cannot have '.' in template names");
            }
        }
        stringTemplateCreateStringTemplate = createStringTemplate();
        stringTemplateCreateStringTemplate.setName(str);
        stringTemplateCreateStringTemplate.setGroup(this);
        stringTemplateCreateStringTemplate.setNativeGroup(this);
        stringTemplateCreateStringTemplate.setTemplate(str2);
        stringTemplateCreateStringTemplate.setErrorListener(this.listener);
        this.templates.put(str, stringTemplateCreateStringTemplate);
        return stringTemplateCreateStringTemplate;
    }

    public StringTemplate defineRegionTemplate(String str, String str2, String str3, int i) {
        StringTemplate stringTemplateDefineTemplate = defineTemplate(getMangledRegionName(str, str2), str3);
        stringTemplateDefineTemplate.setIsRegion(true);
        stringTemplateDefineTemplate.setRegionDefType(i);
        return stringTemplateDefineTemplate;
    }

    public StringTemplate defineRegionTemplate(StringTemplate stringTemplate, String str, String str2, int i) {
        StringTemplate stringTemplateDefineRegionTemplate = defineRegionTemplate(stringTemplate.getOutermostName(), str, str2, i);
        stringTemplate.getOutermostEnclosingInstance().addRegionName(str);
        return stringTemplateDefineRegionTemplate;
    }

    public StringTemplate defineImplicitRegionTemplate(StringTemplate stringTemplate, String str) {
        return defineRegionTemplate(stringTemplate, str, "", 1);
    }

    public String getMangledRegionName(String str, String str2) {
        return new StringBuffer("region__").append(str).append(JvmNames.MULTIFILE_PART_NAME_DELIMITER).append(str2).toString();
    }

    public String getUnMangledTemplateName(String str) {
        return str.substring("region__".length(), str.lastIndexOf(JvmNames.MULTIFILE_PART_NAME_DELIMITER));
    }

    public synchronized StringTemplate defineTemplateAlias(String str, String str2) {
        StringTemplate templateDefinition = getTemplateDefinition(str2);
        if (templateDefinition == null) {
            error(new StringBuffer("cannot alias ").append(str).append(" to undefined template: ").append(str2).toString());
            return null;
        }
        this.templates.put(str, templateDefinition);
        return templateDefinition;
    }

    public synchronized boolean isDefinedInThisGroup(String str) {
        StringTemplate stringTemplate = (StringTemplate) this.templates.get(str);
        if (stringTemplate != null) {
            return (stringTemplate.isRegion() && stringTemplate.getRegionDefType() == 1) ? false : true;
        }
        return false;
    }

    public synchronized StringTemplate getTemplateDefinition(String str) {
        return (StringTemplate) this.templates.get(str);
    }

    public boolean isDefined(String str) {
        try {
            return lookupTemplate(str) != null;
        } catch (IllegalArgumentException unused) {
            return false;
        }
    }

    protected void parseGroup(Reader reader) {
        String name2;
        try {
            new GroupParser(new GroupLexer(reader)).group(this);
        } catch (Exception e) {
            if (getName() == null) {
                name2 = "<unknown>";
            } else {
                name2 = getName();
            }
            error(new StringBuffer("problem parsing group ").append(name2).append(": ").append(e).toString(), e);
        }
    }

    protected void verifyInterfaceImplementations() {
        int i = 0;
        while (true) {
            List list = this.interfaces;
            if (list == null || i >= list.size()) {
                return;
            }
            StringTemplateGroupInterface stringTemplateGroupInterface = (StringTemplateGroupInterface) this.interfaces.get(i);
            List missingTemplates = stringTemplateGroupInterface.getMissingTemplates(this);
            List mismatchedTemplates = stringTemplateGroupInterface.getMismatchedTemplates(this);
            if (missingTemplates != null) {
                error(new StringBuffer("group ").append(getName()).append(" does not satisfy interface ").append(stringTemplateGroupInterface.getName()).append(": missing templates ").append(missingTemplates).toString());
            }
            if (mismatchedTemplates != null) {
                error(new StringBuffer("group ").append(getName()).append(" does not satisfy interface ").append(stringTemplateGroupInterface.getName()).append(": mismatched arguments on these templates ").append(mismatchedTemplates).toString());
            }
            i++;
        }
    }

    public int getRefreshInterval() {
        return this.refreshIntervalInSeconds;
    }

    public void setRefreshInterval(int i) {
        this.refreshIntervalInSeconds = i;
    }

    public void setErrorListener(StringTemplateErrorListener stringTemplateErrorListener) {
        this.listener = stringTemplateErrorListener;
    }

    public StringTemplateErrorListener getErrorListener() {
        return this.listener;
    }

    public void setStringTemplateWriter(Class cls) {
        this.userSpecifiedWriter = cls;
    }

    public StringTemplateWriter getStringTemplateWriter(Writer writer) throws Throwable {
        StringTemplateWriter stringTemplateWriter;
        Class cls = this.userSpecifiedWriter;
        if (cls != null) {
            try {
                Class<?>[] clsArr = new Class[1];
                Class<?> clsClass$ = class$java$io$Writer;
                if (clsClass$ == null) {
                    clsClass$ = class$("java.io.Writer");
                    class$java$io$Writer = clsClass$;
                }
                clsArr[0] = clsClass$;
                stringTemplateWriter = (StringTemplateWriter) cls.getConstructor(clsArr).newInstance(writer);
            } catch (Exception e) {
                error("problems getting StringTemplateWriter", e);
                stringTemplateWriter = null;
            }
        } else {
            stringTemplateWriter = null;
        }
        return stringTemplateWriter == null ? new AutoIndentWriter(writer) : stringTemplateWriter;
    }

    public void setAttributeRenderers(Map map) {
        this.attributeRenderers = map;
    }

    public void registerRenderer(Class cls, Object obj) {
        if (this.attributeRenderers == null) {
            this.attributeRenderers = Collections.synchronizedMap(new HashMap());
        }
        this.attributeRenderers.put(cls, obj);
    }

    public AttributeRenderer getAttributeRenderer(Class cls) {
        StringTemplateGroup stringTemplateGroup;
        Map map = this.attributeRenderers;
        if (map == null) {
            StringTemplateGroup stringTemplateGroup2 = this.superGroup;
            if (stringTemplateGroup2 == null) {
                return null;
            }
            return stringTemplateGroup2.getAttributeRenderer(cls);
        }
        AttributeRenderer attributeRenderer = (AttributeRenderer) map.get(cls);
        return (attributeRenderer != null || (stringTemplateGroup = this.superGroup) == null) ? attributeRenderer : stringTemplateGroup.getAttributeRenderer(cls);
    }

    public Map getMap(String str) {
        StringTemplateGroup stringTemplateGroup;
        Map map = this.maps;
        if (map == null) {
            StringTemplateGroup stringTemplateGroup2 = this.superGroup;
            if (stringTemplateGroup2 == null) {
                return null;
            }
            return stringTemplateGroup2.getMap(str);
        }
        Map map2 = (Map) map.get(str);
        return (map2 != null || (stringTemplateGroup = this.superGroup) == null) ? map2 : stringTemplateGroup.getMap(str);
    }

    public void defineMap(String str, Map map) {
        this.maps.put(str, map);
    }

    public static void registerDefaultLexer(Class cls) {
        defaultTemplateLexerClass = cls;
    }

    public static void registerGroupLoader(StringTemplateGroupLoader stringTemplateGroupLoader) {
        groupLoader = stringTemplateGroupLoader;
    }

    public static StringTemplateGroup loadGroup(String str) {
        return loadGroup(str, null, null);
    }

    public static StringTemplateGroup loadGroup(String str, StringTemplateGroup stringTemplateGroup) {
        return loadGroup(str, null, stringTemplateGroup);
    }

    public static StringTemplateGroup loadGroup(String str, Class cls, StringTemplateGroup stringTemplateGroup) {
        StringTemplateGroupLoader stringTemplateGroupLoader = groupLoader;
        if (stringTemplateGroupLoader != null) {
            return stringTemplateGroupLoader.loadGroup(str, cls, stringTemplateGroup);
        }
        return null;
    }

    public static StringTemplateGroupInterface loadInterface(String str) {
        StringTemplateGroupLoader stringTemplateGroupLoader = groupLoader;
        if (stringTemplateGroupLoader != null) {
            return stringTemplateGroupLoader.loadInterface(str);
        }
        return null;
    }

    public void error(String str) {
        error(str, null);
    }

    public void error(String str, Exception exc) {
        StringTemplateErrorListener stringTemplateErrorListener = this.listener;
        if (stringTemplateErrorListener != null) {
            stringTemplateErrorListener.error(str, exc);
            return;
        }
        System.err.println(new StringBuffer("StringTemplate: ").append(str).toString());
        if (exc != null) {
            exc.printStackTrace();
        }
    }

    public synchronized Set getTemplateNames() {
        return this.templates.keySet();
    }

    public void emitDebugStartStopStrings(boolean z) {
        this.debugTemplateOutput = z;
    }

    public void doNotEmitDebugStringsForTemplate(String str) {
        if (this.noDebugStartStopStrings == null) {
            this.noDebugStartStopStrings = new HashSet();
        }
        this.noDebugStartStopStrings.add(str);
    }

    public void emitTemplateStartDebugString(StringTemplate stringTemplate, StringTemplateWriter stringTemplateWriter) throws IOException {
        String string;
        Set set = this.noDebugStartStopStrings;
        if (set == null || !set.contains(stringTemplate.getName())) {
            if (!stringTemplate.getName().startsWith(PsiKeyword.IF) && !stringTemplate.getName().startsWith(PsiKeyword.ELSE)) {
                if (stringTemplate.getNativeGroup() != null) {
                    string = new StringBuffer().append(stringTemplate.getNativeGroup().getName()).append(".").toString();
                } else {
                    string = new StringBuffer().append(stringTemplate.getGroup().getName()).append(".").toString();
                }
            } else {
                string = "";
            }
            stringTemplateWriter.write(new StringBuffer("<").append(string).append(stringTemplate.getName()).append(">").toString());
        }
    }

    public void emitTemplateStopDebugString(StringTemplate stringTemplate, StringTemplateWriter stringTemplateWriter) throws IOException {
        String string;
        Set set = this.noDebugStartStopStrings;
        if (set == null || !set.contains(stringTemplate.getName())) {
            if (!stringTemplate.getName().startsWith(PsiKeyword.IF) && !stringTemplate.getName().startsWith(PsiKeyword.ELSE)) {
                if (stringTemplate.getNativeGroup() != null) {
                    string = new StringBuffer().append(stringTemplate.getNativeGroup().getName()).append(".").toString();
                } else {
                    string = new StringBuffer().append(stringTemplate.getGroup().getName()).append(".").toString();
                }
            } else {
                string = "";
            }
            stringTemplateWriter.write(new StringBuffer("</").append(string).append(stringTemplate.getName()).append(">").toString());
        }
    }

    public String toString() {
        return toString(true);
    }

    public String toString(boolean z) {
        StringBuffer stringBuffer = new StringBuffer();
        ArrayList<String> arrayList = new ArrayList(this.templates.keySet());
        Collections.sort(arrayList);
        stringBuffer.append(new StringBuffer("group ").append(getName()).append(";\n").toString());
        StringTemplate stringTemplate = new StringTemplate("$args;separator=\",\"$");
        for (String str : arrayList) {
            StringTemplate stringTemplate2 = (StringTemplate) this.templates.get(str);
            if (stringTemplate2 != NOT_FOUND_ST) {
                stringTemplate = stringTemplate.getInstanceOf();
                stringTemplate.setAttribute("args", stringTemplate2.getFormalArguments());
                stringBuffer.append(new StringBuffer().append(str).append("(").append(stringTemplate).append(Constant.AFTER_QUTO).toString());
                if (z) {
                    stringBuffer.append(" ::= <<");
                    stringBuffer.append(stringTemplate2.getTemplate());
                    stringBuffer.append(">>\n");
                } else {
                    stringBuffer.append('\n');
                }
            }
        }
        return stringBuffer.toString();
    }
}
