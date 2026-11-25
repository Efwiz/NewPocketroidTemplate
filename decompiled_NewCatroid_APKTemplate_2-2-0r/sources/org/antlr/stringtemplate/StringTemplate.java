package org.antlr.stringtemplate;

import antlr.CharScanner;
import antlr.CommonAST;
import antlr.CommonToken;
import antlr.RecognitionException;
import antlr.TokenStreamException;
import antlr.collections.AST;
import antlr.collections.ASTEnumeration;
import com.huawei.hms.framework.common.ContainerUtils;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.StringTokenizer;
import name.antonsmirnov.firmata.FormatHelper;
import org.antlr.stringtemplate.language.ASTExpr;
import org.antlr.stringtemplate.language.ActionLexer;
import org.antlr.stringtemplate.language.ActionParser;
import org.antlr.stringtemplate.language.ConditionalExpr;
import org.antlr.stringtemplate.language.Expr;
import org.antlr.stringtemplate.language.FormalArgument;
import org.antlr.stringtemplate.language.NewlineRef;
import org.antlr.stringtemplate.language.StringTemplateAST;
import org.antlr.stringtemplate.language.TemplateParser;

/* JADX INFO: loaded from: classes12.dex */
public class StringTemplate {
    public static final String ANONYMOUS_ST_NAME = "anonymous";
    public static final int REGION_EMBEDDED = 2;
    public static final int REGION_EXPLICIT = 3;
    public static final int REGION_IMPLICIT = 1;
    public static final String VERSION = "3.2.1";
    static /* synthetic */ Class class$java$io$Reader = null;
    static /* synthetic */ Class class$org$antlr$stringtemplate$StringTemplate = null;
    static /* synthetic */ Class class$org$antlr$stringtemplate$StringTemplate$STAttributeList = null;
    public static StringTemplateGroup defaultGroup = new StringTemplateGroup("defaultGroup", ".");
    static boolean lintMode = false;
    private static int templateCounter;
    protected Map argumentContext;
    protected StringTemplateAST argumentsAST;
    protected Map attributeRenderers;
    protected Map attributes;
    protected List chunks;
    protected StringTemplate enclosingInstance;
    protected LinkedHashMap formalArguments;
    protected StringTemplateGroup group;
    protected int groupFileLine;
    protected boolean isRegion;
    StringTemplateErrorListener listener;

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    protected String f425name;
    protected StringTemplateGroup nativeGroup;
    protected int numberOfDefaultArgumentValues;
    protected boolean passThroughAttributes;
    protected String pattern;
    protected List referencedAttributes;
    protected int regionDefType;
    protected Set regions;
    protected int templateID;

    public static final class Aggregate {
        protected HashMap properties = new HashMap();

        protected void put(String str, Object obj) {
            this.properties.put(str, obj);
        }

        public Object get(String str) {
            return this.properties.get(str);
        }

        public String toString() {
            return this.properties.toString();
        }
    }

    public static final class STAttributeList extends ArrayList {
        public STAttributeList(int i) {
            super(i);
        }

        public STAttributeList() {
        }
    }

    private static synchronized int getNextTemplateCounter() {
        int i;
        i = templateCounter + 1;
        templateCounter = i;
        return i;
    }

    public static void resetTemplateCounter() {
        templateCounter = 0;
    }

    public StringTemplate() {
        this.referencedAttributes = null;
        this.f425name = ANONYMOUS_ST_NAME;
        this.templateID = getNextTemplateCounter();
        this.enclosingInstance = null;
        this.argumentContext = null;
        this.argumentsAST = null;
        this.formalArguments = FormalArgument.UNKNOWN;
        this.numberOfDefaultArgumentValues = 0;
        this.passThroughAttributes = false;
        this.listener = null;
        this.group = defaultGroup;
    }

    public StringTemplate(String str) {
        this((StringTemplateGroup) null, str);
    }

    public StringTemplate(String str, Class cls) throws Throwable {
        this();
        setGroup(new StringTemplateGroup("defaultGroup", cls));
        setTemplate(str);
    }

    public StringTemplate(StringTemplateGroup stringTemplateGroup, String str) throws Throwable {
        this();
        if (stringTemplateGroup != null) {
            setGroup(stringTemplateGroup);
        }
        setTemplate(str);
    }

    public StringTemplate(StringTemplateGroup stringTemplateGroup, String str, HashMap map) {
        this(stringTemplateGroup, str);
        this.attributes = map;
    }

    protected void dup(StringTemplate stringTemplate, StringTemplate stringTemplate2) {
        stringTemplate2.attributeRenderers = stringTemplate.attributeRenderers;
        stringTemplate2.pattern = stringTemplate.pattern;
        stringTemplate2.chunks = stringTemplate.chunks;
        stringTemplate2.formalArguments = stringTemplate.formalArguments;
        stringTemplate2.numberOfDefaultArgumentValues = stringTemplate.numberOfDefaultArgumentValues;
        stringTemplate2.f425name = stringTemplate.f425name;
        stringTemplate2.group = stringTemplate.group;
        stringTemplate2.nativeGroup = stringTemplate.nativeGroup;
        stringTemplate2.listener = stringTemplate.listener;
        stringTemplate2.regions = stringTemplate.regions;
        stringTemplate2.isRegion = stringTemplate.isRegion;
        stringTemplate2.regionDefType = stringTemplate.regionDefType;
    }

    public StringTemplate getInstanceOf() {
        StringTemplate stringTemplateCreateStringTemplate;
        StringTemplateGroup stringTemplateGroup = this.nativeGroup;
        if (stringTemplateGroup != null) {
            stringTemplateCreateStringTemplate = stringTemplateGroup.createStringTemplate();
        } else {
            stringTemplateCreateStringTemplate = this.group.createStringTemplate();
        }
        dup(this, stringTemplateCreateStringTemplate);
        return stringTemplateCreateStringTemplate;
    }

    public StringTemplate getEnclosingInstance() {
        return this.enclosingInstance;
    }

    public StringTemplate getOutermostEnclosingInstance() {
        StringTemplate stringTemplate = this.enclosingInstance;
        return stringTemplate != null ? stringTemplate.getOutermostEnclosingInstance() : this;
    }

    public void setEnclosingInstance(StringTemplate stringTemplate) {
        if (this == stringTemplate) {
            throw new IllegalArgumentException(new StringBuffer("cannot embed template ").append(getName()).append(" in itself").toString());
        }
        this.enclosingInstance = stringTemplate;
    }

    public Map getArgumentContext() {
        return this.argumentContext;
    }

    public void setArgumentContext(Map map) {
        this.argumentContext = map;
    }

    public StringTemplateAST getArgumentsAST() {
        return this.argumentsAST;
    }

    public void setArgumentsAST(StringTemplateAST stringTemplateAST) {
        this.argumentsAST = stringTemplateAST;
    }

    public String getName() {
        return this.f425name;
    }

    public String getOutermostName() {
        StringTemplate stringTemplate = this.enclosingInstance;
        if (stringTemplate != null) {
            return stringTemplate.getOutermostName();
        }
        return getName();
    }

    public void setName(String str) {
        this.f425name = str;
    }

    public StringTemplateGroup getGroup() {
        return this.group;
    }

    public void setGroup(StringTemplateGroup stringTemplateGroup) {
        this.group = stringTemplateGroup;
    }

    public StringTemplateGroup getNativeGroup() {
        return this.nativeGroup;
    }

    public void setNativeGroup(StringTemplateGroup stringTemplateGroup) {
        this.nativeGroup = stringTemplateGroup;
    }

    public int getGroupFileLine() {
        StringTemplate stringTemplate = this.enclosingInstance;
        if (stringTemplate != null) {
            return stringTemplate.getGroupFileLine();
        }
        return this.groupFileLine;
    }

    public void setGroupFileLine(int i) {
        this.groupFileLine = i;
    }

    public void setTemplate(String str) throws Throwable {
        this.pattern = str;
        breakTemplateIntoChunks();
    }

    public String getTemplate() {
        return this.pattern;
    }

    public void setErrorListener(StringTemplateErrorListener stringTemplateErrorListener) {
        this.listener = stringTemplateErrorListener;
    }

    public StringTemplateErrorListener getErrorListener() {
        StringTemplateErrorListener stringTemplateErrorListener = this.listener;
        return stringTemplateErrorListener == null ? this.group.getErrorListener() : stringTemplateErrorListener;
    }

    public void reset() {
        this.attributes = new HashMap();
    }

    public void setPredefinedAttributes() {
        inLintMode();
    }

    public void removeAttribute(String str) {
        Map map = this.attributes;
        if (map != null) {
            map.remove(str);
        }
    }

    public void setAttribute(String str, Object obj) {
        STAttributeList sTAttributeList;
        STAttributeList sTAttributeList2;
        if (obj == null || str == null) {
            return;
        }
        if (str.indexOf(46) >= 0) {
            throw new IllegalArgumentException("cannot have '.' in attribute names");
        }
        if (this.attributes == null) {
            this.attributes = new HashMap();
        }
        if (obj instanceof StringTemplate) {
            ((StringTemplate) obj).setEnclosingInstance(this);
        } else {
            obj = ASTExpr.convertArrayToList(obj);
        }
        Object obj2 = this.attributes.get(str);
        if (obj2 == null) {
            rawSetAttribute(this.attributes, str, obj);
            return;
        }
        Class<?> cls = obj2.getClass();
        Class<?> clsClass$ = class$org$antlr$stringtemplate$StringTemplate$STAttributeList;
        if (clsClass$ == null) {
            clsClass$ = class$("org.antlr.stringtemplate.StringTemplate$STAttributeList");
            class$org$antlr$stringtemplate$StringTemplate$STAttributeList = clsClass$;
        }
        if (cls == clsClass$) {
            sTAttributeList2 = (STAttributeList) obj2;
        } else {
            if (obj2 instanceof List) {
                List list = (List) obj2;
                sTAttributeList = new STAttributeList(list.size());
                sTAttributeList.addAll(list);
                rawSetAttribute(this.attributes, str, sTAttributeList);
            } else {
                sTAttributeList = new STAttributeList();
                rawSetAttribute(this.attributes, str, sTAttributeList);
                sTAttributeList.add(obj2);
            }
            sTAttributeList2 = sTAttributeList;
        }
        if (!(obj instanceof List)) {
            sTAttributeList2.add(obj);
        } else if (sTAttributeList2 != obj) {
            sTAttributeList2.addAll((List) obj);
        }
    }

    static /* synthetic */ Class class$(String str) throws Throwable {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError().initCause(e);
        }
    }

    public void setAttribute(String str, int i) {
        setAttribute(str, new Integer(i));
    }

    public void setAttribute(String str, Object obj, Object obj2) {
        setAttribute(str, new Object[]{obj, obj2});
    }

    public void setAttribute(String str, Object obj, Object obj2, Object obj3) {
        setAttribute(str, new Object[]{obj, obj2, obj3});
    }

    public void setAttribute(String str, Object obj, Object obj2, Object obj3, Object obj4) {
        setAttribute(str, new Object[]{obj, obj2, obj3, obj4});
    }

    public void setAttribute(String str, Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
        setAttribute(str, new Object[]{obj, obj2, obj3, obj4, obj5});
    }

    protected void setAttribute(String str, Object[] objArr) {
        List arrayList = new ArrayList();
        String aggregateAttributeSpec = parseAggregateAttributeSpec(str, arrayList);
        if (objArr == null || arrayList.size() == 0) {
            throw new IllegalArgumentException(new StringBuffer("missing properties or values for '").append(str).append(FormatHelper.QUOTE).toString());
        }
        if (objArr.length != arrayList.size()) {
            throw new IllegalArgumentException(new StringBuffer("number of properties in '").append(str).append("' != number of values").toString());
        }
        Aggregate aggregate = new Aggregate();
        for (int i = 0; i < objArr.length; i++) {
            Object objConvertArrayToList = objArr[i];
            if (objConvertArrayToList instanceof StringTemplate) {
                ((StringTemplate) objConvertArrayToList).setEnclosingInstance(this);
            } else {
                objConvertArrayToList = ASTExpr.convertArrayToList(objConvertArrayToList);
            }
            aggregate.put((String) arrayList.get(i), objConvertArrayToList);
        }
        setAttribute(aggregateAttributeSpec, aggregate);
    }

    protected String parseAggregateAttributeSpec(String str, List list) {
        int iIndexOf = str.indexOf(46);
        if (iIndexOf <= 0) {
            throw new IllegalArgumentException(new StringBuffer("invalid aggregate attribute format: ").append(str).toString());
        }
        String strSubstring = str.substring(0, iIndexOf);
        StringTokenizer stringTokenizer = new StringTokenizer(str.substring(iIndexOf + 1, str.length()), "{,}", true);
        if (stringTokenizer.hasMoreTokens() && stringTokenizer.nextToken().trim().equals("{")) {
            list.add(stringTokenizer.nextToken().trim());
            String strTrim = stringTokenizer.nextToken().trim();
            while (strTrim.equals(",")) {
                list.add(stringTokenizer.nextToken().trim());
                strTrim = stringTokenizer.nextToken().trim();
            }
            if (strTrim.equals("}")) {
                return strSubstring;
            }
        }
        throw new IllegalArgumentException(new StringBuffer("invalid aggregate attribute format: ").append(str).toString());
    }

    protected void rawSetAttribute(Map map, String str, Object obj) {
        if (this.formalArguments != FormalArgument.UNKNOWN && getFormalArgument(str) == null) {
            throw new NoSuchElementException(new StringBuffer("no such attribute: ").append(str).append(" in template context ").append(getEnclosingInstanceStackString()).toString());
        }
        if (obj == null) {
            return;
        }
        map.put(str, obj);
    }

    public void rawSetArgumentAttribute(StringTemplate stringTemplate, Map map, String str, Object obj) {
        if (stringTemplate.formalArguments != FormalArgument.UNKNOWN && stringTemplate.getFormalArgument(str) == null) {
            throw new NoSuchElementException(new StringBuffer("template ").append(stringTemplate.getName()).append(" has no such attribute: ").append(str).append(" in template context ").append(getEnclosingInstanceStackString()).toString());
        }
        if (obj == null) {
            return;
        }
        map.put(str, obj);
    }

    public Object getAttribute(String str) {
        Object obj = get(this, str);
        if (obj == null) {
            checkNullAttributeAgainstFormalArguments(this, str);
        }
        return obj;
    }

    public int write(StringTemplateWriter stringTemplateWriter) throws IOException {
        List list;
        int i;
        int i2;
        int i3;
        if (this.group.debugTemplateOutput) {
            this.group.emitTemplateStartDebugString(this, stringTemplateWriter);
        }
        setPredefinedAttributes();
        setDefaultArgumentValues();
        int i4 = 0;
        int i5 = 0;
        boolean z = true;
        while (true) {
            List list2 = this.chunks;
            if (list2 == null || i4 >= list2.size()) {
                break;
            }
            int iWrite = ((Expr) this.chunks.get(i4)).write(this, stringTemplateWriter);
            if (iWrite > 0 || i4 != 0 || (i = i4 + 1) >= this.chunks.size() || !(this.chunks.get(i) instanceof NewlineRef)) {
                if (iWrite <= 0 && (i2 = i4 - 1) >= 0 && (this.chunks.get(i2) instanceof NewlineRef) && (i3 = i4 + 1) < this.chunks.size() && (this.chunks.get(i3) instanceof NewlineRef)) {
                    i4 = i3;
                }
                if (iWrite != -1) {
                    i5 += iWrite;
                    z = false;
                }
                i = i4;
            }
            i4 = i + 1;
        }
        if (this.group.debugTemplateOutput) {
            this.group.emitTemplateStopDebugString(this, stringTemplateWriter);
        }
        if (lintMode) {
            checkForTrouble();
        }
        if (!z || (list = this.chunks) == null || list.size() <= 0) {
            return i5;
        }
        return -1;
    }

    public Object get(StringTemplate stringTemplate, String str) {
        StringTemplate stringTemplate2;
        Map argumentContext;
        if (stringTemplate == null) {
            return null;
        }
        if (lintMode) {
            stringTemplate.trackAttributeReference(str);
        }
        Map map = stringTemplate.attributes;
        Object obj = map != null ? map.get(str) : null;
        if (obj == null && (argumentContext = stringTemplate.getArgumentContext()) != null) {
            obj = argumentContext.get(str);
        }
        if (obj == null && !stringTemplate.passThroughAttributes && stringTemplate.getFormalArgument(str) != null) {
            return null;
        }
        if (obj != null || (stringTemplate2 = stringTemplate.enclosingInstance) == null) {
            return (obj == null && stringTemplate.enclosingInstance == null) ? stringTemplate.group.getMap(str) : obj;
        }
        return get(stringTemplate2, str);
    }

    protected void breakTemplateIntoChunks() throws Throwable {
        String string;
        if (this.pattern == null) {
            return;
        }
        try {
            Class templateLexerClass = this.group.getTemplateLexerClass();
            Class<?>[] clsArr = new Class[2];
            Class<?> clsClass$ = class$org$antlr$stringtemplate$StringTemplate;
            if (clsClass$ == null) {
                clsClass$ = class$("org.antlr.stringtemplate.StringTemplate");
                class$org$antlr$stringtemplate$StringTemplate = clsClass$;
            }
            clsArr[0] = clsClass$;
            Class<?> clsClass$2 = class$java$io$Reader;
            if (clsClass$2 == null) {
                clsClass$2 = class$("java.io.Reader");
                class$java$io$Reader = clsClass$2;
            }
            clsArr[1] = clsClass$2;
            CharScanner charScanner = (CharScanner) templateLexerClass.getConstructor(clsArr).newInstance(this, new StringReader(this.pattern));
            charScanner.setTokenObjectClass("org.antlr.stringtemplate.language.ChunkToken");
            new TemplateParser(charScanner).template(this);
        } catch (Exception e) {
            String outermostName = getOutermostName();
            if (getName() == null) {
                string = "<unknown>";
            } else {
                string = getName();
            }
            if (outermostName != null && !string.equals(outermostName)) {
                string = new StringBuffer().append(string).append(" nested in ").append(outermostName).toString();
            }
            error(new StringBuffer("problem parsing template '").append(string).append(FormatHelper.QUOTE).toString(), e);
        }
    }

    public ASTExpr parseAction(String str) {
        ActionLexer actionLexer = new ActionLexer(new StringReader(str.toString()));
        ActionParser actionParser = new ActionParser(actionLexer, this);
        actionParser.setASTNodeClass("org.antlr.stringtemplate.language.StringTemplateAST");
        actionLexer.setTokenObjectClass("org.antlr.stringtemplate.language.StringTemplateToken");
        ASTExpr aSTExpr = null;
        try {
            Map mapAction = actionParser.action();
            AST ast = actionParser.getAST();
            if (ast != null) {
                if (ast.getType() == 8) {
                    aSTExpr = new ConditionalExpr(this, ast);
                } else {
                    aSTExpr = new ASTExpr(this, ast, mapAction);
                }
            }
        } catch (RecognitionException e) {
            error(new StringBuffer("Can't parse chunk: ").append(str.toString()).toString(), e);
        } catch (TokenStreamException e2) {
            error(new StringBuffer("Can't parse chunk: ").append(str.toString()).toString(), e2);
        }
        return aSTExpr;
    }

    public int getTemplateID() {
        return this.templateID;
    }

    public Map getAttributes() {
        return this.attributes;
    }

    public List getChunks() {
        return this.chunks;
    }

    public void addChunk(Expr expr) {
        if (this.chunks == null) {
            this.chunks = new ArrayList();
        }
        this.chunks.add(expr);
    }

    public void setAttributes(Map map) {
        this.attributes = map;
    }

    public Map getFormalArguments() {
        return this.formalArguments;
    }

    public void setFormalArguments(LinkedHashMap linkedHashMap) {
        this.formalArguments = linkedHashMap;
    }

    public void setDefaultArgumentValues() {
        if (this.numberOfDefaultArgumentValues == 0) {
            return;
        }
        if (this.argumentContext == null) {
            this.argumentContext = new HashMap();
        }
        if (this.formalArguments != FormalArgument.UNKNOWN) {
            for (String str : this.formalArguments.keySet()) {
                FormalArgument formalArgument = (FormalArgument) this.formalArguments.get(str);
                if (formalArgument.defaultValueST != null && getAttribute(str) == null) {
                    Object objEvaluateExpression = formalArgument.defaultValueST;
                    if (formalArgument.defaultValueST.chunks.size() == 1) {
                        Object obj = formalArgument.defaultValueST.chunks.get(0);
                        if (obj instanceof ASTExpr) {
                            ASTExpr aSTExpr = (ASTExpr) obj;
                            if (aSTExpr.getAST().getType() == 9) {
                                objEvaluateExpression = aSTExpr.evaluateExpression(this, aSTExpr.getAST());
                            }
                        }
                    }
                    this.argumentContext.put(str, objEvaluateExpression);
                }
            }
        }
    }

    public FormalArgument lookupFormalArgument(String str) {
        StringTemplate stringTemplate;
        FormalArgument formalArgument = getFormalArgument(str);
        return (formalArgument != null || (stringTemplate = this.enclosingInstance) == null) ? formalArgument : stringTemplate.lookupFormalArgument(str);
    }

    public FormalArgument getFormalArgument(String str) {
        return (FormalArgument) this.formalArguments.get(str);
    }

    public void defineEmptyFormalArgumentList() {
        setFormalArguments(new LinkedHashMap());
    }

    public void defineFormalArgument(String str) {
        defineFormalArgument(str, null);
    }

    public void defineFormalArguments(List list) {
        if (list == null) {
            return;
        }
        for (int i = 0; i < list.size(); i++) {
            defineFormalArgument((String) list.get(i));
        }
    }

    public void defineFormalArgument(String str, StringTemplate stringTemplate) {
        if (stringTemplate != null) {
            this.numberOfDefaultArgumentValues++;
        }
        FormalArgument formalArgument = new FormalArgument(str, stringTemplate);
        if (this.formalArguments == FormalArgument.UNKNOWN) {
            this.formalArguments = new LinkedHashMap();
        }
        this.formalArguments.put(str, formalArgument);
    }

    public void setPassThroughAttributes(boolean z) {
        this.passThroughAttributes = z;
    }

    public void setAttributeRenderers(Map map) {
        this.attributeRenderers = map;
    }

    public void registerRenderer(Class cls, AttributeRenderer attributeRenderer) {
        if (this.attributeRenderers == null) {
            this.attributeRenderers = new HashMap();
        }
        this.attributeRenderers.put(cls, attributeRenderer);
    }

    public AttributeRenderer getAttributeRenderer(Class cls) {
        Map map = this.attributeRenderers;
        AttributeRenderer attributeRenderer = map != null ? (AttributeRenderer) map.get(cls) : null;
        if (attributeRenderer != null) {
            return attributeRenderer;
        }
        StringTemplate stringTemplate = this.enclosingInstance;
        if (stringTemplate != null) {
            return stringTemplate.getAttributeRenderer(cls);
        }
        return this.group.getAttributeRenderer(cls);
    }

    public void error(String str) {
        error(str, null);
    }

    public void warning(String str) {
        if (getErrorListener() != null) {
            getErrorListener().warning(str);
        } else {
            System.err.println(new StringBuffer("StringTemplate: warning: ").append(str).toString());
        }
    }

    public void error(String str, Throwable th) {
        if (getErrorListener() != null) {
            getErrorListener().error(str, th);
            return;
        }
        if (th != null) {
            System.err.println(new StringBuffer("StringTemplate: error: ").append(str).append(": ").append(th.toString()).toString());
            if (th instanceof InvocationTargetException) {
                th = ((InvocationTargetException) th).getTargetException();
            }
            th.printStackTrace(System.err);
            return;
        }
        System.err.println(new StringBuffer("StringTemplate: error: ").append(str).toString());
    }

    public static void setLintMode(boolean z) {
        lintMode = z;
    }

    public static boolean inLintMode() {
        return lintMode;
    }

    protected void trackAttributeReference(String str) {
        if (this.referencedAttributes == null) {
            this.referencedAttributes = new ArrayList();
        }
        this.referencedAttributes.add(str);
    }

    public static boolean isRecursiveEnclosingInstance(StringTemplate stringTemplate) {
        if (stringTemplate == null) {
            return false;
        }
        StringTemplate stringTemplate2 = stringTemplate.enclosingInstance;
        if (stringTemplate2 == stringTemplate) {
            return true;
        }
        while (stringTemplate2 != null) {
            if (stringTemplate2 == stringTemplate) {
                return true;
            }
            stringTemplate2 = stringTemplate2.enclosingInstance;
        }
        return false;
    }

    public String getEnclosingInstanceStackTrace() {
        StringBuffer stringBuffer = new StringBuffer();
        HashSet hashSet = new HashSet();
        for (StringTemplate stringTemplate = this; stringTemplate != null; stringTemplate = stringTemplate.enclosingInstance) {
            if (hashSet.contains(stringTemplate)) {
                stringBuffer.append(stringTemplate.getTemplateDeclaratorString());
                stringBuffer.append(" (start of recursive cycle)\n...");
                break;
            }
            hashSet.add(stringTemplate);
            stringBuffer.append(stringTemplate.getTemplateDeclaratorString());
            if (stringTemplate.attributes != null) {
                stringBuffer.append(", attributes=[");
                int i = 0;
                for (String str : stringTemplate.attributes.keySet()) {
                    if (i > 0) {
                        stringBuffer.append(", ");
                    }
                    i++;
                    stringBuffer.append(str);
                    Object obj = stringTemplate.attributes.get(str);
                    if (obj instanceof StringTemplate) {
                        StringTemplate stringTemplate2 = (StringTemplate) obj;
                        stringBuffer.append("=<");
                        stringBuffer.append(stringTemplate2.getName());
                        stringBuffer.append("()@");
                        stringBuffer.append(String.valueOf(stringTemplate2.getTemplateID()));
                        stringBuffer.append(">");
                    } else if (obj instanceof List) {
                        stringBuffer.append("=List[..");
                        List list = (List) obj;
                        int i2 = 0;
                        for (int i3 = 0; i3 < list.size(); i3++) {
                            Object obj2 = list.get(i3);
                            if (obj2 instanceof StringTemplate) {
                                if (i2 > 0) {
                                    stringBuffer.append(", ");
                                }
                                i2++;
                                StringTemplate stringTemplate3 = (StringTemplate) obj2;
                                stringBuffer.append("<");
                                stringBuffer.append(stringTemplate3.getName());
                                stringBuffer.append("()@");
                                stringBuffer.append(String.valueOf(stringTemplate3.getTemplateID()));
                                stringBuffer.append(">");
                            }
                        }
                        stringBuffer.append("..]");
                    }
                }
                stringBuffer.append("]");
            }
            if (stringTemplate.referencedAttributes != null) {
                stringBuffer.append(", references=");
                stringBuffer.append(stringTemplate.referencedAttributes);
            }
            stringBuffer.append(">\n");
        }
        return stringBuffer.toString();
    }

    public String getTemplateDeclaratorString() {
        StringBuffer stringBuffer = new StringBuffer("<");
        stringBuffer.append(getName());
        stringBuffer.append("(");
        stringBuffer.append(this.formalArguments.keySet());
        stringBuffer.append(")@");
        stringBuffer.append(String.valueOf(getTemplateID()));
        stringBuffer.append(">");
        return stringBuffer.toString();
    }

    protected String getTemplateHeaderString(boolean z) {
        if (z) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(getName());
            Map map = this.attributes;
            if (map != null) {
                stringBuffer.append(map.keySet());
            }
            return stringBuffer.toString();
        }
        return getName();
    }

    protected void checkNullAttributeAgainstFormalArguments(StringTemplate stringTemplate, String str) {
        if (stringTemplate.getFormalArguments() == FormalArgument.UNKNOWN) {
            StringTemplate stringTemplate2 = stringTemplate.enclosingInstance;
            if (stringTemplate2 != null) {
                checkNullAttributeAgainstFormalArguments(stringTemplate2, str);
                return;
            }
            return;
        }
        if (stringTemplate.lookupFormalArgument(str) == null) {
            throw new NoSuchElementException(new StringBuffer("no such attribute: ").append(str).append(" in template context ").append(getEnclosingInstanceStackString()).toString());
        }
    }

    protected void checkForTrouble() {
        Map map = this.attributes;
        if (map == null) {
            return;
        }
        for (String str : map.keySet()) {
            List list = this.referencedAttributes;
            if (list != null && !list.contains(str)) {
                warning(new StringBuffer().append(getName()).append(": set but not used: ").append(str).toString());
            }
        }
    }

    public String getEnclosingInstanceStackString() {
        LinkedList linkedList = new LinkedList();
        StringTemplate stringTemplate = this;
        while (true) {
            String str = "";
            if (stringTemplate == null) {
                return linkedList.toString().replaceAll(",", "");
            }
            StringBuffer stringBufferAppend = new StringBuffer().append(stringTemplate.getName());
            if (stringTemplate.passThroughAttributes) {
                str = "(...)";
            }
            linkedList.add(0, stringBufferAppend.append(str).toString());
            stringTemplate = stringTemplate.enclosingInstance;
        }
    }

    public boolean isRegion() {
        return this.isRegion;
    }

    public void setIsRegion(boolean z) {
        this.isRegion = z;
    }

    public void addRegionName(String str) {
        if (this.regions == null) {
            this.regions = new HashSet();
        }
        this.regions.add(str);
    }

    public boolean containsRegionName(String str) {
        Set set = this.regions;
        if (set == null) {
            return false;
        }
        return set.contains(str);
    }

    public int getRegionDefType() {
        return this.regionDefType;
    }

    public void setRegionDefType(int i) {
        this.regionDefType = i;
    }

    public String toDebugString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(new StringBuffer("template-").append(getTemplateDeclaratorString()).append(":").toString());
        stringBuffer.append("chunks=");
        List list = this.chunks;
        if (list != null) {
            stringBuffer.append(list.toString());
        }
        stringBuffer.append("attributes=[");
        Map map = this.attributes;
        if (map != null) {
            int i = 0;
            for (String str : map.keySet()) {
                if (i > 0) {
                    stringBuffer.append(',');
                }
                stringBuffer.append(new StringBuffer().append(str).append(ContainerUtils.KEY_VALUE_DELIMITER).toString());
                Object obj = this.attributes.get(str);
                if (obj instanceof StringTemplate) {
                    stringBuffer.append(((StringTemplate) obj).toDebugString());
                } else {
                    stringBuffer.append(obj);
                }
                i++;
            }
            stringBuffer.append("]");
        }
        return stringBuffer.toString();
    }

    public String toStructureString() {
        return toStructureString(0);
    }

    public String toStructureString(int i) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i2 = 1; i2 <= i; i2++) {
            stringBuffer.append("  ");
        }
        stringBuffer.append(getName());
        stringBuffer.append(this.attributes.keySet());
        stringBuffer.append(":\n");
        Map map = this.attributes;
        if (map != null) {
            Iterator it = map.keySet().iterator();
            while (it.hasNext()) {
                Object obj = this.attributes.get((String) it.next());
                if (obj instanceof StringTemplate) {
                    stringBuffer.append(((StringTemplate) obj).toStructureString(i + 1));
                } else if (obj instanceof List) {
                    List list = (List) obj;
                    for (int i3 = 0; i3 < list.size(); i3++) {
                        Object obj2 = list.get(i3);
                        if (obj2 instanceof StringTemplate) {
                            stringBuffer.append(((StringTemplate) obj2).toStructureString(i + 1));
                        }
                    }
                } else if (obj instanceof Map) {
                    for (Object obj3 : ((Map) obj).values()) {
                        if (obj3 instanceof StringTemplate) {
                            stringBuffer.append(((StringTemplate) obj3).toStructureString(i + 1));
                        }
                    }
                }
            }
        }
        return stringBuffer.toString();
    }

    public StringTemplate getDOTForDependencyGraph(boolean z) {
        StringTemplate stringTemplate = new StringTemplate("digraph StringTemplateDependencyGraph {\nnode [shape=$shape$, $if(width)$width=$width$,$endif$      $if(height)$height=$height$,$endif$ fontsize=$fontsize$];\n$edges:{e|\"$e.src$\" -> \"$e.trg$\"\n}$}\n");
        HashMap map = new HashMap();
        getDependencyGraph(map, z);
        for (String str : map.keySet()) {
            Iterator it = ((Set) map.get(str)).iterator();
            while (it.hasNext()) {
                stringTemplate.setAttribute("edges.{src,trg}", str, (String) it.next());
            }
        }
        stringTemplate.setAttribute("shape", "none");
        stringTemplate.setAttribute("fontsize", "11");
        stringTemplate.setAttribute("height", "0");
        return stringTemplate;
    }

    public void getDependencyGraph(Map map, boolean z) {
        String templateHeaderString = getTemplateHeaderString(z);
        Map map2 = this.attributes;
        int i = 0;
        if (map2 != null) {
            Iterator it = map2.keySet().iterator();
            while (it.hasNext()) {
                Object obj = this.attributes.get((String) it.next());
                if (obj instanceof StringTemplate) {
                    StringTemplate stringTemplate = (StringTemplate) obj;
                    putToMultiValuedMap(map, templateHeaderString, stringTemplate.getTemplateHeaderString(z));
                    stringTemplate.getDependencyGraph(map, z);
                } else if (obj instanceof List) {
                    List list = (List) obj;
                    for (int i2 = 0; i2 < list.size(); i2++) {
                        Object obj2 = list.get(i2);
                        if (obj2 instanceof StringTemplate) {
                            StringTemplate stringTemplate2 = (StringTemplate) obj2;
                            putToMultiValuedMap(map, templateHeaderString, stringTemplate2.getTemplateHeaderString(z));
                            stringTemplate2.getDependencyGraph(map, z);
                        }
                    }
                } else if (obj instanceof Map) {
                    for (Object obj3 : ((Map) obj).values()) {
                        if (obj3 instanceof StringTemplate) {
                            StringTemplate stringTemplate3 = (StringTemplate) obj3;
                            putToMultiValuedMap(map, templateHeaderString, stringTemplate3.getTemplateHeaderString(z));
                            stringTemplate3.getDependencyGraph(map, z);
                        }
                    }
                }
            }
        }
        while (true) {
            List list2 = this.chunks;
            if (list2 == null || i >= list2.size()) {
                return;
            }
            Expr expr = (Expr) this.chunks.get(i);
            if (expr instanceof ASTExpr) {
                ASTEnumeration aSTEnumerationFindAllPartial = ((ASTExpr) expr).getAST().findAllPartial(new CommonAST(new CommonToken(7, "include")));
                while (aSTEnumerationFindAllPartial.hasMoreNodes()) {
                    String text = aSTEnumerationFindAllPartial.nextNode().getFirstChild().getText();
                    System.out.println(new StringBuffer("found include ").append(text).toString());
                    putToMultiValuedMap(map, templateHeaderString, text);
                    StringTemplateGroup group = getGroup();
                    if (group != null) {
                        group.getInstanceOf(text).getDependencyGraph(map, z);
                    }
                }
            }
            i++;
        }
    }

    protected void putToMultiValuedMap(Map map, Object obj, Object obj2) {
        HashSet hashSet = (HashSet) map.get(obj);
        if (hashSet == null) {
            hashSet = new HashSet();
            map.put(obj, hashSet);
        }
        hashSet.add(obj2);
    }

    public void printDebugString() {
        System.out.println(new StringBuffer("template-").append(getName()).append(":").toString());
        System.out.print("chunks=");
        System.out.println(this.chunks.toString());
        if (this.attributes == null) {
            return;
        }
        System.out.print("attributes=[");
        int i = 0;
        for (String str : this.attributes.keySet()) {
            if (i > 0) {
                System.out.print(',');
            }
            Object obj = this.attributes.get(str);
            if (obj instanceof StringTemplate) {
                System.out.print(new StringBuffer().append(str).append(ContainerUtils.KEY_VALUE_DELIMITER).toString());
                ((StringTemplate) obj).printDebugString();
            } else if (obj instanceof List) {
                ArrayList arrayList = (ArrayList) obj;
                for (int i2 = 0; i2 < arrayList.size(); i2++) {
                    Object obj2 = arrayList.get(i2);
                    System.out.print(new StringBuffer().append(str).append("[").append(i2).append("] is ").append(obj2.getClass().getName()).append(ContainerUtils.KEY_VALUE_DELIMITER).toString());
                    if (obj2 instanceof StringTemplate) {
                        ((StringTemplate) obj2).printDebugString();
                    } else {
                        System.out.println(obj2);
                    }
                }
            } else {
                System.out.print(new StringBuffer().append(str).append(ContainerUtils.KEY_VALUE_DELIMITER).toString());
                System.out.println(obj);
            }
            i++;
        }
        System.out.print("]\n");
    }

    public String toString() {
        return toString(-1);
    }

    public String toString(int i) throws Throwable {
        StringWriter stringWriter = new StringWriter();
        StringTemplateWriter stringTemplateWriter = this.group.getStringTemplateWriter(stringWriter);
        stringTemplateWriter.setLineWidth(i);
        try {
            write(stringTemplateWriter);
        } catch (IOException unused) {
            error(new StringBuffer("Got IOException writing to writer ").append(stringTemplateWriter.getClass().getName()).toString());
        }
        stringTemplateWriter.setLineWidth(-1);
        return stringWriter.toString();
    }
}
