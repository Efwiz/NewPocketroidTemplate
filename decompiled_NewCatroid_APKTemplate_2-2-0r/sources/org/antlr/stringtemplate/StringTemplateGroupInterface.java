package org.antlr.stringtemplate;

import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.antlr.stringtemplate.language.InterfaceLexer;
import org.antlr.stringtemplate.language.InterfaceParser;

/* JADX INFO: loaded from: classes12.dex */
public class StringTemplateGroupInterface {
    public static StringTemplateErrorListener DEFAULT_ERROR_LISTENER = new StringTemplateErrorListener() { // from class: org.antlr.stringtemplate.StringTemplateGroupInterface.1
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
    protected StringTemplateErrorListener listener;

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    protected String f427name;
    protected StringTemplateGroupInterface superInterface;
    protected Map templates;

    static class TemplateDefinition {
        public LinkedHashMap formalArgs;

        /* JADX INFO: renamed from: name, reason: collision with root package name */
        public String f428name;
        public boolean optional;

        public TemplateDefinition(String str, LinkedHashMap linkedHashMap, boolean z) {
            this.f428name = str;
            this.formalArgs = linkedHashMap;
            this.optional = z;
        }
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public StringTemplateGroupInterface(Reader reader) {
        this(reader, DEFAULT_ERROR_LISTENER, null);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public StringTemplateGroupInterface(Reader reader, StringTemplateErrorListener stringTemplateErrorListener) {
        this(reader, stringTemplateErrorListener, null);
    }

    public StringTemplateGroupInterface(Reader reader, StringTemplateErrorListener stringTemplateErrorListener, StringTemplateGroupInterface stringTemplateGroupInterface) {
        this.templates = new LinkedHashMap();
        this.superInterface = null;
        this.listener = stringTemplateErrorListener;
        setSuperInterface(stringTemplateGroupInterface);
        parseInterface(reader);
    }

    public StringTemplateGroupInterface getSuperInterface() {
        return this.superInterface;
    }

    public void setSuperInterface(StringTemplateGroupInterface stringTemplateGroupInterface) {
        this.superInterface = stringTemplateGroupInterface;
    }

    protected void parseInterface(Reader reader) {
        String name2;
        try {
            new InterfaceParser(new InterfaceLexer(reader)).groupInterface(this);
        } catch (Exception e) {
            if (getName() == null) {
                name2 = "<unknown>";
            } else {
                name2 = getName();
            }
            error(new StringBuffer("problem parsing group ").append(name2).append(": ").append(e).toString(), e);
        }
    }

    public void defineTemplate(String str, LinkedHashMap linkedHashMap, boolean z) {
        TemplateDefinition templateDefinition = new TemplateDefinition(str, linkedHashMap, z);
        this.templates.put(templateDefinition.f428name, templateDefinition);
    }

    public List getMissingTemplates(StringTemplateGroup stringTemplateGroup) {
        ArrayList arrayList = new ArrayList();
        Iterator it = this.templates.keySet().iterator();
        while (it.hasNext()) {
            TemplateDefinition templateDefinition = (TemplateDefinition) this.templates.get((String) it.next());
            if (!templateDefinition.optional && !stringTemplateGroup.isDefined(templateDefinition.f428name)) {
                arrayList.add(templateDefinition.f428name);
            }
        }
        if (arrayList.size() == 0) {
            return null;
        }
        return arrayList;
    }

    public List getMismatchedTemplates(StringTemplateGroup stringTemplateGroup) {
        ArrayList arrayList = new ArrayList();
        Iterator it = this.templates.keySet().iterator();
        while (it.hasNext()) {
            TemplateDefinition templateDefinition = (TemplateDefinition) this.templates.get((String) it.next());
            if (stringTemplateGroup.isDefined(templateDefinition.f428name)) {
                Map formalArguments = stringTemplateGroup.getTemplateDefinition(templateDefinition.f428name).getFormalArguments();
                boolean z = true;
                boolean z2 = (templateDefinition.formalArgs != null && formalArguments == null) || (templateDefinition.formalArgs == null && formalArguments != null) || templateDefinition.formalArgs.size() != formalArguments.size();
                if (!z2) {
                    Iterator it2 = formalArguments.keySet().iterator();
                    do {
                        if (!it2.hasNext()) {
                            z = z2;
                            break;
                        }
                    } while (templateDefinition.formalArgs.get((String) it2.next()) != null);
                } else {
                    z = z2;
                    break;
                }
                if (z) {
                    arrayList.add(getTemplateSignature(templateDefinition));
                }
            }
        }
        if (arrayList.size() == 0) {
            return null;
        }
        return arrayList;
    }

    public String getName() {
        return this.f427name;
    }

    public void setName(String str) {
        this.f427name = str;
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

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer("interface ");
        stringBuffer.append(getName());
        stringBuffer.append(";\n");
        Iterator it = this.templates.keySet().iterator();
        while (it.hasNext()) {
            stringBuffer.append(getTemplateSignature((TemplateDefinition) this.templates.get((String) it.next())));
            stringBuffer.append(";\n");
        }
        return stringBuffer.toString();
    }

    protected String getTemplateSignature(TemplateDefinition templateDefinition) {
        StringBuffer stringBuffer = new StringBuffer();
        if (templateDefinition.optional) {
            stringBuffer.append("optional ");
        }
        stringBuffer.append(templateDefinition.f428name);
        if (templateDefinition.formalArgs != null) {
            StringBuffer stringBuffer2 = new StringBuffer("(");
            int i = 1;
            for (String str : templateDefinition.formalArgs.keySet()) {
                if (i > 1) {
                    stringBuffer2.append(", ");
                }
                stringBuffer2.append(str);
                i++;
            }
            stringBuffer2.append(')');
            stringBuffer.append(stringBuffer2);
        } else {
            stringBuffer.append("()");
        }
        return stringBuffer.toString();
    }
}
