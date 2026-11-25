package org.antlr.stringtemplate.language;

import antlr.RecognitionException;
import antlr.collections.AST;
import com.google.firebase.crashlytics.internal.metadata.UserMetadata;
import com.huawei.hms.ml.language.common.utils.LanguageCodeUtil;
import com.reandroid.arsc.value.CompoundEntry;
import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.antlr.stringtemplate.AttributeRenderer;
import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.StringTemplateWriter;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;

/* JADX INFO: loaded from: classes12.dex */
public class ASTExpr extends Expr {
    public static final String DEFAULT_ATTRIBUTE_NAME = "it";
    public static final String DEFAULT_ATTRIBUTE_NAME_DEPRECATED = "attr";
    public static final String DEFAULT_INDEX0_VARIABLE_NAME = "i0";
    public static final String DEFAULT_INDEX_VARIABLE_NAME = "i";
    public static final String DEFAULT_MAP_KEY_NAME = "key";
    public static final String DEFAULT_MAP_VALUE_NAME = "_default_";
    public static final String EMPTY_OPTION = "empty expr option";
    public static final int MISSING = -1;
    static /* synthetic */ Class class$java$lang$String;
    static /* synthetic */ Class class$org$antlr$stringtemplate$StringTemplate;
    static /* synthetic */ Class class$org$antlr$stringtemplate$StringTemplate$Aggregate;
    AST exprTree;
    String formatString;
    String nullValue;
    Map options;
    String separatorString;
    String wrapString;
    public static final StringTemplate MAP_KEY_VALUE = new StringTemplate();
    public static final Map defaultOptionValues = new HashMap() { // from class: org.antlr.stringtemplate.language.ASTExpr.1
        {
            put("anchor", new StringTemplateAST(34, "true"));
            put("wrap", new StringTemplateAST(34, "\n"));
        }
    };
    public static final Set supportedOptions = new HashSet() { // from class: org.antlr.stringtemplate.language.ASTExpr.2
        {
            add("anchor");
            add(ConfigConstants.CONFIG_KEY_FORMAT);
            add("null");
            add("separator");
            add("wrap");
        }
    };

    public ASTExpr(StringTemplate stringTemplate, AST ast, Map map) {
        super(stringTemplate);
        this.wrapString = null;
        this.nullValue = null;
        this.separatorString = null;
        this.formatString = null;
        this.exprTree = ast;
        this.options = map;
    }

    public AST getAST() {
        return this.exprTree;
    }

    @Override // org.antlr.stringtemplate.language.Expr
    public int write(StringTemplate stringTemplate, StringTemplateWriter stringTemplateWriter) throws Throwable {
        int iAction = 0;
        if (this.exprTree != null && stringTemplate != null && stringTemplateWriter != null) {
            StringTemplateAST stringTemplateAST = (StringTemplateAST) getOption("anchor");
            if (stringTemplateAST != null) {
                stringTemplateWriter.pushAnchorPoint();
            }
            stringTemplateWriter.pushIndentation(getIndentation());
            handleExprOptions(stringTemplate);
            try {
                iAction = new ActionEvaluator(stringTemplate, this, stringTemplateWriter).action(this.exprTree);
            } catch (RecognitionException e) {
                stringTemplate.error(new StringBuffer("can't evaluate tree: ").append(this.exprTree.toStringList()).toString(), e);
            }
            stringTemplateWriter.popIndentation();
            if (stringTemplateAST != null) {
                stringTemplateWriter.popAnchorPoint();
            }
        }
        return iAction;
    }

    protected void handleExprOptions(StringTemplate stringTemplate) {
        this.formatString = null;
        StringTemplateAST stringTemplateAST = (StringTemplateAST) getOption("wrap");
        if (stringTemplateAST != null) {
            this.wrapString = evaluateExpression(stringTemplate, stringTemplateAST);
        }
        StringTemplateAST stringTemplateAST2 = (StringTemplateAST) getOption("null");
        if (stringTemplateAST2 != null) {
            this.nullValue = evaluateExpression(stringTemplate, stringTemplateAST2);
        }
        StringTemplateAST stringTemplateAST3 = (StringTemplateAST) getOption("separator");
        if (stringTemplateAST3 != null) {
            this.separatorString = evaluateExpression(stringTemplate, stringTemplateAST3);
        }
        StringTemplateAST stringTemplateAST4 = (StringTemplateAST) getOption(ConfigConstants.CONFIG_KEY_FORMAT);
        if (stringTemplateAST4 != null) {
            this.formatString = evaluateExpression(stringTemplate, stringTemplateAST4);
        }
        Map map = this.options;
        if (map != null) {
            for (String str : map.keySet()) {
                if (!supportedOptions.contains(str)) {
                    stringTemplate.warning(new StringBuffer("ignoring unsupported option: ").append(str).toString());
                }
            }
        }
    }

    public Object applyTemplateToListOfAttributes(StringTemplate stringTemplate, List list, StringTemplate stringTemplate2) {
        Object[] objArr;
        if (list != null && stringTemplate2 != null && list.size() != 0) {
            StringTemplate.STAttributeList sTAttributeList = new StringTemplate.STAttributeList();
            for (int i = 0; i < list.size(); i++) {
                Object obj = list.get(i);
                if (obj != null) {
                    list.set(i, convertAnythingToIterator(obj));
                }
            }
            int size = list.size();
            Map formalArguments = stringTemplate2.getFormalArguments();
            if (formalArguments == null || formalArguments.size() == 0) {
                stringTemplate.error(new StringBuffer("missing arguments in anonymous template in context ").append(stringTemplate.getEnclosingInstanceStackString()).toString());
            } else {
                Object[] array = formalArguments.keySet().toArray();
                if (array.length != size) {
                    stringTemplate.error(new StringBuffer("number of arguments ").append(formalArguments.keySet()).append(" mismatch between attribute list and anonymous template in context ").append(stringTemplate.getEnclosingInstanceStackString()).toString());
                    size = Math.min(array.length, size);
                    objArr = new Object[size];
                    System.arraycopy(array, 0, objArr, 0, size);
                } else {
                    objArr = array;
                }
                int i2 = 0;
                while (true) {
                    HashMap map = new HashMap();
                    int i3 = 0;
                    for (int i4 = 0; i4 < size; i4++) {
                        Iterator it = (Iterator) list.get(i4);
                        if (it == null || !it.hasNext()) {
                            i3++;
                        } else {
                            map.put((String) objArr[i4], it.next());
                        }
                    }
                    if (i3 == size) {
                        return sTAttributeList;
                    }
                    int i5 = i2 + 1;
                    map.put(DEFAULT_INDEX_VARIABLE_NAME, new Integer(i5));
                    map.put(DEFAULT_INDEX0_VARIABLE_NAME, new Integer(i2));
                    StringTemplate instanceOf = stringTemplate2.getInstanceOf();
                    instanceOf.setEnclosingInstance(stringTemplate);
                    instanceOf.setArgumentContext(map);
                    sTAttributeList.add(instanceOf);
                    i2 = i5;
                }
            }
        }
        return null;
    }

    public Object applyListOfAlternatingTemplates(StringTemplate stringTemplate, Object obj, List list) {
        if (obj == null || list == null || list.size() == 0) {
            return null;
        }
        Object objConvertAnythingIteratableToIterator = convertAnythingIteratableToIterator(convertArrayToList(obj));
        if (objConvertAnythingIteratableToIterator instanceof Iterator) {
            StringTemplate.STAttributeList sTAttributeList = new StringTemplate.STAttributeList();
            Iterator it = (Iterator) objConvertAnythingIteratableToIterator;
            int i = 0;
            while (it.hasNext()) {
                Object next = it.next();
                if (next != null || (next = this.nullValue) != null) {
                    StringTemplate stringTemplate2 = (StringTemplate) list.get(i % list.size());
                    StringTemplateAST argumentsAST = stringTemplate2.getArgumentsAST();
                    StringTemplate instanceOf = stringTemplate2.getInstanceOf();
                    instanceOf.setEnclosingInstance(stringTemplate);
                    instanceOf.setArgumentsAST(argumentsAST);
                    HashMap map = new HashMap();
                    Map formalArguments = instanceOf.getFormalArguments();
                    boolean z = instanceOf.getName() == StringTemplate.ANONYMOUS_ST_NAME;
                    setSoleFormalArgumentToIthValue(instanceOf, map, next);
                    if (!z || formalArguments == null || formalArguments.size() <= 0) {
                        map.put("it", next);
                        map.put(DEFAULT_ATTRIBUTE_NAME_DEPRECATED, next);
                    }
                    int i2 = i + 1;
                    map.put(DEFAULT_INDEX_VARIABLE_NAME, new Integer(i2));
                    map.put(DEFAULT_INDEX0_VARIABLE_NAME, new Integer(i));
                    instanceOf.setArgumentContext(map);
                    evaluateArguments(instanceOf);
                    sTAttributeList.add(instanceOf);
                    i = i2;
                }
            }
            if (sTAttributeList.size() == 0) {
                return null;
            }
            return sTAttributeList;
        }
        StringTemplate stringTemplate3 = (StringTemplate) list.get(0);
        HashMap map2 = new HashMap();
        Map formalArguments2 = stringTemplate3.getFormalArguments();
        stringTemplate3.getArgumentsAST();
        setSoleFormalArgumentToIthValue(stringTemplate3, map2, objConvertAnythingIteratableToIterator);
        if (stringTemplate3.getName() != StringTemplate.ANONYMOUS_ST_NAME || formalArguments2 == null || formalArguments2.size() <= 0) {
            map2.put("it", objConvertAnythingIteratableToIterator);
            map2.put(DEFAULT_ATTRIBUTE_NAME_DEPRECATED, objConvertAnythingIteratableToIterator);
        }
        map2.put(DEFAULT_INDEX_VARIABLE_NAME, new Integer(1));
        map2.put(DEFAULT_INDEX0_VARIABLE_NAME, new Integer(0));
        stringTemplate3.setArgumentContext(map2);
        evaluateArguments(stringTemplate3);
        return stringTemplate3;
    }

    protected void setSoleFormalArgumentToIthValue(StringTemplate stringTemplate, Map map, Object obj) {
        Map formalArguments = stringTemplate.getFormalArguments();
        if (formalArguments != null) {
            boolean z = stringTemplate.getName() == StringTemplate.ANONYMOUS_ST_NAME;
            if (formalArguments.size() == 1 || (z && formalArguments.size() > 0)) {
                if (z && formalArguments.size() > 1) {
                    stringTemplate.error(new StringBuffer("too many arguments on {...} template: ").append(formalArguments).toString());
                }
                map.put((String) formalArguments.keySet().toArray()[0], obj);
            }
        }
    }

    public Object getObjectProperty(StringTemplate stringTemplate, Object obj, Object obj2) {
        if (obj == null || obj2 == null) {
            return null;
        }
        return convertArrayToList(rawGetObjectProperty(stringTemplate, obj, obj2));
    }

    /* JADX WARN: Multi-variable type inference failed */
    protected Object rawGetObjectProperty(StringTemplate stringTemplate, Object obj, Object obj2) throws Throwable {
        Map attributes;
        Class<?> cls = obj.getClass();
        Class<?> clsClass$ = class$org$antlr$stringtemplate$StringTemplate$Aggregate;
        if (clsClass$ == null) {
            clsClass$ = class$("org.antlr.stringtemplate.StringTemplate$Aggregate");
            class$org$antlr$stringtemplate$StringTemplate$Aggregate = clsClass$;
        }
        if (cls == clsClass$) {
            return ((StringTemplate.Aggregate) obj).get((String) obj2);
        }
        Class<?> clsClass$2 = class$org$antlr$stringtemplate$StringTemplate;
        if (clsClass$2 == null) {
            clsClass$2 = class$("org.antlr.stringtemplate.StringTemplate");
            class$org$antlr$stringtemplate$StringTemplate = clsClass$2;
        }
        if (cls == clsClass$2 && (attributes = ((StringTemplate) obj).getAttributes()) != null) {
            return attributes.get((String) obj2);
        }
        Object obj3 = null;
        Object objValues = null;
        obj3 = null;
        obj3 = null;
        obj3 = null;
        if (obj instanceof Map) {
            Map map = (Map) obj;
            if (obj2.equals(UserMetadata.KEYDATA_FILENAME)) {
                objValues = map.keySet();
            } else if (obj2.equals(CompoundEntry.NAME_values)) {
                objValues = map.values();
            } else if (map.containsKey(obj2)) {
                objValues = map.get(obj2);
            } else if (map.containsKey(obj2.toString())) {
                objValues = map.get(obj2.toString());
            } else if (map.containsKey(DEFAULT_MAP_VALUE_NAME)) {
                objValues = map.get(DEFAULT_MAP_VALUE_NAME);
            }
            return objValues == MAP_KEY_VALUE ? obj2 : objValues;
        }
        String str = (String) obj2;
        String string = new StringBuffer().append(Character.toUpperCase(str.charAt(0))).append(str.substring(1, str.length())).toString();
        Method method = getMethod(cls, new StringBuffer("get").append(string).toString());
        if (method == null) {
            method = getMethod(cls, new StringBuffer(LanguageCodeUtil.IS).append(string).toString());
        }
        if (method != null) {
            try {
                return invokeMethod(method, obj, null);
            } catch (Exception e) {
                stringTemplate.error(new StringBuffer("Can't get property ").append(str).append(" using method get/is").append(string).append(" from ").append(cls.getName()).append(" instance").toString(), e);
                return null;
            }
        }
        try {
            try {
                Object objAccessField = accessField(cls.getField(str), obj, null);
                obj3 = objAccessField;
                stringTemplate = objAccessField;
            } catch (IllegalAccessException e2) {
                stringTemplate.error(new StringBuffer("Can't access property ").append(str).append(" using method get/is").append(string).append(" or direct field access from ").append(cls.getName()).append(" instance").toString(), e2);
                stringTemplate = stringTemplate;
            }
            return obj3;
        } catch (NoSuchFieldException e3) {
            stringTemplate.error(new StringBuffer("Class ").append(cls.getName()).append(" has no such attribute: ").append(str).append(" in template context ").append(stringTemplate.getEnclosingInstanceStackString()).toString(), e3);
            return obj3;
        }
    }

    static /* synthetic */ Class class$(String str) throws Throwable {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError().initCause(e);
        }
    }

    protected Object accessField(Field field, Object obj, Object obj2) throws IllegalAccessException {
        try {
            field.setAccessible(true);
        } catch (SecurityException unused) {
        }
        return field.get(obj);
    }

    protected Object invokeMethod(Method method, Object obj, Object obj2) throws IllegalAccessException, InvocationTargetException {
        try {
            method.setAccessible(true);
        } catch (SecurityException unused) {
        }
        return method.invoke(obj, null);
    }

    protected Method getMethod(Class cls, String str) {
        try {
            return cls.getMethod(str, null);
        } catch (NoSuchMethodException unused) {
            return null;
        }
    }

    public boolean testAttributeTrue(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof Boolean) {
            return ((Boolean) obj).booleanValue();
        }
        if (obj instanceof Collection) {
            return ((Collection) obj).size() > 0;
        }
        if (obj instanceof Map) {
            return ((Map) obj).size() > 0;
        }
        if (obj instanceof Iterator) {
            return ((Iterator) obj).hasNext();
        }
        return true;
    }

    public Object add(Object obj, Object obj2) {
        if (obj == null) {
            return obj2;
        }
        return obj2 == null ? obj : new StringBuffer().append(obj.toString()).append(obj2.toString()).toString();
    }

    public StringTemplate getTemplateInclude(StringTemplate stringTemplate, String str, StringTemplateAST stringTemplateAST) {
        StringTemplate embeddedInstanceOf = stringTemplate.getGroup().getEmbeddedInstanceOf(stringTemplate, str);
        if (embeddedInstanceOf == null) {
            stringTemplate.error(new StringBuffer("cannot make embedded instance of ").append(str).append(" in template ").append(stringTemplate.getName()).toString());
            return null;
        }
        embeddedInstanceOf.setArgumentsAST(stringTemplateAST);
        evaluateArguments(embeddedInstanceOf);
        return embeddedInstanceOf;
    }

    public int writeAttribute(StringTemplate stringTemplate, Object obj, StringTemplateWriter stringTemplateWriter) {
        return write(stringTemplate, obj, stringTemplateWriter);
    }

    protected int write(StringTemplate stringTemplate, Object obj, StringTemplateWriter stringTemplateWriter) {
        if (obj == null && (obj = this.nullValue) == null) {
            return -1;
        }
        try {
            if (obj instanceof StringTemplate) {
                return writeTemplate(stringTemplate, obj, stringTemplateWriter);
            }
            Object objConvertAnythingIteratableToIterator = convertAnythingIteratableToIterator(obj);
            return objConvertAnythingIteratableToIterator instanceof Iterator ? writeIterableValue(stringTemplate, objConvertAnythingIteratableToIterator, stringTemplateWriter) : writePOJO(stringTemplate, objConvertAnythingIteratableToIterator, stringTemplateWriter);
        } catch (IOException e) {
            stringTemplate.error(new StringBuffer("problem writing object: ").append(obj).toString(), e);
            return 0;
        }
    }

    protected int writePOJO(StringTemplate stringTemplate, Object obj, StringTemplateWriter stringTemplateWriter) throws IOException {
        String string;
        AttributeRenderer attributeRenderer = stringTemplate.getAttributeRenderer(obj.getClass());
        if (attributeRenderer != null) {
            String str = this.formatString;
            string = str != null ? attributeRenderer.toString(obj, str) : attributeRenderer.toString(obj);
        } else {
            string = obj.toString();
        }
        String str2 = this.wrapString;
        return str2 != null ? stringTemplateWriter.write(string, str2) : stringTemplateWriter.write(string);
    }

    protected int writeTemplate(StringTemplate stringTemplate, Object obj, StringTemplateWriter stringTemplateWriter) throws Throwable {
        StringTemplate stringTemplate2 = (StringTemplate) obj;
        stringTemplate2.setEnclosingInstance(stringTemplate);
        if (StringTemplate.inLintMode() && StringTemplate.isRecursiveEnclosingInstance(stringTemplate2)) {
            throw new IllegalStateException(new StringBuffer("infinite recursion to ").append(stringTemplate2.getTemplateDeclaratorString()).append(" referenced in ").append(stringTemplate2.getEnclosingInstance().getTemplateDeclaratorString()).append("; stack trace:\n").append(stringTemplate2.getEnclosingInstanceStackTrace()).toString());
        }
        String str = this.wrapString;
        if (str != null) {
            stringTemplateWriter.writeWrapSeparator(str);
        }
        if (this.formatString != null) {
            Class clsClass$ = class$java$lang$String;
            if (clsClass$ == null) {
                clsClass$ = class$(CommonClassNames.JAVA_LANG_STRING);
                class$java$lang$String = clsClass$;
            }
            AttributeRenderer attributeRenderer = stringTemplate.getAttributeRenderer(clsClass$);
            if (attributeRenderer != null) {
                StringWriter stringWriter = new StringWriter();
                stringTemplate2.write(stringTemplate.getGroup().getStringTemplateWriter(stringWriter));
                return stringTemplateWriter.write(attributeRenderer.toString(stringWriter.toString(), this.formatString));
            }
        }
        return stringTemplate2.write(stringTemplateWriter);
    }

    /* JADX WARN: Code duplicated, block: B:32:0x0062  */
    /* JADX WARN: Code duplicated, block: B:41:0x007a  */
    /* JADX WARN: Code duplicated, block: B:44:0x008f  */
    /* JADX WARN: Code duplicated, block: B:56:0x008d A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:60:0x0005 A[SYNTHETIC] */
    protected int writeIterableValue(StringTemplate stringTemplate, Object obj, StringTemplateWriter stringTemplateWriter) throws IOException {
        int iWrite;
        String str;
        String str2;
        String str3;
        Iterator it = (Iterator) obj;
        int iWriteSeparator = 0;
        boolean z = false;
        while (it.hasNext()) {
            Object next = it.next();
            if (next == null) {
                next = this.nullValue;
            }
            if (next != null) {
                if (this.separatorString == null) {
                    int iWrite2 = write(stringTemplate, next, stringTemplateWriter);
                    if (iWrite2 != -1) {
                        iWriteSeparator += iWrite2;
                    }
                } else {
                    boolean z2 = next instanceof StringTemplate;
                    if (z2) {
                        StringTemplate stringTemplate2 = (StringTemplate) next;
                        int size = stringTemplate2.getChunks() != null ? stringTemplate2.getChunks().size() : 0;
                        boolean z3 = true;
                        for (int i = 0; i < size; i++) {
                            if (!(((Expr) stringTemplate2.getChunks().get(i)) instanceof ConditionalExpr)) {
                                z3 = false;
                            }
                        }
                        if (!z3) {
                            if (z && (str3 = this.separatorString) != null) {
                                iWriteSeparator += stringTemplateWriter.writeSeparator(str3);
                            }
                            iWrite = write(stringTemplate, next, stringTemplateWriter);
                        } else if (z2 && !(next instanceof Iterator)) {
                            if (z && (str2 = this.separatorString) != null) {
                                iWriteSeparator += stringTemplateWriter.writeSeparator(str2);
                            }
                            iWrite = write(stringTemplate, next, stringTemplateWriter);
                        } else if (write(stringTemplate, next, stringTemplate.getGroup().getStringTemplateWriter(new StringWriter())) != -1) {
                            if (z && (str = this.separatorString) != null) {
                                iWriteSeparator += stringTemplateWriter.writeSeparator(str);
                            }
                            iWrite = write(stringTemplate, next, stringTemplateWriter);
                        }
                        iWriteSeparator += iWrite;
                        z = true;
                    } else if (z2) {
                        if (write(stringTemplate, next, stringTemplate.getGroup().getStringTemplateWriter(new StringWriter())) != -1) {
                            if (z) {
                                iWriteSeparator += stringTemplateWriter.writeSeparator(str);
                            }
                            iWrite = write(stringTemplate, next, stringTemplateWriter);
                            iWriteSeparator += iWrite;
                            z = true;
                        }
                    } else if (write(stringTemplate, next, stringTemplate.getGroup().getStringTemplateWriter(new StringWriter())) != -1) {
                        if (z) {
                            iWriteSeparator += stringTemplateWriter.writeSeparator(str);
                        }
                        iWrite = write(stringTemplate, next, stringTemplateWriter);
                        iWriteSeparator += iWrite;
                        z = true;
                    }
                }
            }
        }
        return iWriteSeparator;
    }

    public String evaluateExpression(StringTemplate stringTemplate, Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof StringTemplateAST) {
            StringTemplateAST stringTemplateAST = (StringTemplateAST) obj;
            StringWriter stringWriter = new StringWriter();
            try {
                new ActionEvaluator(stringTemplate, this, stringTemplate.getGroup().getStringTemplateWriter(stringWriter)).action(stringTemplateAST);
            } catch (RecognitionException e) {
                stringTemplate.error(new StringBuffer("can't evaluate tree: ").append(this.exprTree.toStringList()).toString(), e);
            }
            return stringWriter.toString();
        }
        return obj.toString();
    }

    protected void evaluateArguments(StringTemplate stringTemplate) {
        StringTemplateAST argumentsAST = stringTemplate.getArgumentsAST();
        if (argumentsAST == null || argumentsAST.getFirstChild() == null) {
            return;
        }
        StringTemplate enclosingInstance = stringTemplate.getEnclosingInstance();
        StringTemplate stringTemplate2 = new StringTemplate(stringTemplate.getGroup(), "");
        stringTemplate2.setName(new StringBuffer("<invoke ").append(stringTemplate.getName()).append(" arg context>").toString());
        stringTemplate2.setEnclosingInstance(enclosingInstance);
        stringTemplate2.setArgumentContext(stringTemplate.getArgumentContext());
        try {
            stringTemplate.setArgumentContext(new ActionEvaluator(stringTemplate2, this, null).argList(argumentsAST, stringTemplate, stringTemplate.getArgumentContext()));
        } catch (RecognitionException e) {
            stringTemplate.error(new StringBuffer("can't evaluate tree: ").append(argumentsAST.toStringList()).toString(), e);
        }
    }

    public static Object convertArrayToList(Object obj) {
        if (obj == null) {
            return null;
        }
        if (!obj.getClass().isArray()) {
            return obj;
        }
        if (obj.getClass().getComponentType().isPrimitive()) {
            return new ArrayWrappedInList(obj);
        }
        return Arrays.asList((Object[]) obj);
    }

    protected static Object convertAnythingIteratableToIterator(Object obj) {
        Iterator it;
        if (obj instanceof Collection) {
            it = ((Collection) obj).iterator();
        } else if (obj instanceof Map) {
            it = ((Map) obj).values().iterator();
        } else {
            it = obj instanceof Iterator ? (Iterator) obj : null;
        }
        return it == null ? obj : it;
    }

    protected static Iterator convertAnythingToIterator(Object obj) {
        Iterator it;
        if (obj instanceof Collection) {
            it = ((Collection) obj).iterator();
        } else if (obj instanceof Map) {
            it = ((Map) obj).values().iterator();
        } else {
            it = obj instanceof Iterator ? (Iterator) obj : null;
        }
        if (it != null) {
            return it;
        }
        StringTemplate.STAttributeList sTAttributeList = new StringTemplate.STAttributeList(1);
        sTAttributeList.add(obj);
        return sTAttributeList.iterator();
    }

    public Object first(Object obj) {
        if (obj == null) {
            return null;
        }
        Object objConvertAnythingIteratableToIterator = convertAnythingIteratableToIterator(obj);
        if (!(objConvertAnythingIteratableToIterator instanceof Iterator)) {
            return obj;
        }
        Iterator it = (Iterator) objConvertAnythingIteratableToIterator;
        return it.hasNext() ? it.next() : obj;
    }

    public Object rest(Object obj) {
        if (obj == null) {
            return null;
        }
        Object objConvertAnythingIteratableToIterator = convertAnythingIteratableToIterator(obj);
        if (!(objConvertAnythingIteratableToIterator instanceof Iterator)) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        Iterator it = (Iterator) objConvertAnythingIteratableToIterator;
        if (!it.hasNext()) {
            return null;
        }
        it.next();
        while (it.hasNext()) {
            Object next = it.next();
            if (next != null) {
                arrayList.add(next);
            }
        }
        return arrayList;
    }

    public Object last(Object obj) {
        if (obj == null) {
            return null;
        }
        Object objConvertAnythingIteratableToIterator = convertAnythingIteratableToIterator(obj);
        if (objConvertAnythingIteratableToIterator instanceof Iterator) {
            Iterator it = (Iterator) objConvertAnythingIteratableToIterator;
            while (it.hasNext()) {
                obj = it.next();
            }
        }
        return obj;
    }

    public Object strip(Object obj) {
        if (obj == null) {
            return null;
        }
        Object objConvertAnythingIteratableToIterator = convertAnythingIteratableToIterator(obj);
        if (!(objConvertAnythingIteratableToIterator instanceof Iterator)) {
            return objConvertAnythingIteratableToIterator;
        }
        ArrayList arrayList = new ArrayList();
        Iterator it = (Iterator) objConvertAnythingIteratableToIterator;
        while (it.hasNext()) {
            Object next = it.next();
            if (next != null) {
                arrayList.add(next);
            }
        }
        return arrayList;
    }

    public Object trunc(Object obj) {
        ArrayList arrayList = null;
        if (obj == null) {
            return null;
        }
        Object objConvertAnythingIteratableToIterator = convertAnythingIteratableToIterator(obj);
        if (objConvertAnythingIteratableToIterator instanceof Iterator) {
            arrayList = new ArrayList();
            Iterator it = (Iterator) objConvertAnythingIteratableToIterator;
            while (it.hasNext()) {
                Object next = it.next();
                if (it.hasNext()) {
                    arrayList.add(next);
                }
            }
        }
        return arrayList;
    }

    public Object length(Object obj) {
        int length;
        int i = 0;
        if (obj == null) {
            return new Integer(0);
        }
        if (obj instanceof Map) {
            length = ((Map) obj).size();
        } else if (obj instanceof Collection) {
            length = ((Collection) obj).size();
        } else if (obj instanceof Object[]) {
            length = ((Object[]) obj).length;
        } else if (obj instanceof int[]) {
            length = ((int[]) obj).length;
        } else if (obj instanceof long[]) {
            length = ((long[]) obj).length;
        } else if (obj instanceof float[]) {
            length = ((float[]) obj).length;
        } else if (obj instanceof double[]) {
            length = ((double[]) obj).length;
        } else if (obj instanceof Iterator) {
            Iterator it = (Iterator) obj;
            while (it.hasNext()) {
                it.next();
                i++;
            }
            length = i;
        } else {
            length = 1;
        }
        return new Integer(length);
    }

    public Object getOption(String str) {
        Map map = this.options;
        if (map == null) {
            return null;
        }
        Object obj = map.get(str);
        return obj == EMPTY_OPTION ? defaultOptionValues.get(str) : obj;
    }

    public String toString() {
        return this.exprTree.toStringList();
    }
}
