package org.antlr.stringtemplate.misc;

import antlr.collections.AST;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.language.ASTExpr;
import org.antlr.stringtemplate.language.ConditionalExpr;
import org.antlr.stringtemplate.language.Expr;
import org.antlr.stringtemplate.language.StringRef;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;

/* JADX INFO: loaded from: classes12.dex */
public class JTreeStringTemplateModel implements TreeModel {
    static /* synthetic */ Class class$java$lang$Object;
    static /* synthetic */ Class class$org$antlr$stringtemplate$misc$JTreeStringTemplateModel$ExprWrapper;
    static /* synthetic */ Class class$org$antlr$stringtemplate$misc$JTreeStringTemplateModel$HashMapWrapper;
    static /* synthetic */ Class class$org$antlr$stringtemplate$misc$JTreeStringTemplateModel$ListWrapper;
    static /* synthetic */ Class class$org$antlr$stringtemplate$misc$JTreeStringTemplateModel$StringTemplateWrapper;
    static Map classNameToWrapperMap;
    Wrapper root;

    public void addTreeModelListener(TreeModelListener treeModelListener) {
    }

    public void removeTreeModelListener(TreeModelListener treeModelListener) {
    }

    static {
        HashMap map = new HashMap();
        classNameToWrapperMap = map;
        Class clsClass$ = class$org$antlr$stringtemplate$misc$JTreeStringTemplateModel$StringTemplateWrapper;
        if (clsClass$ == null) {
            clsClass$ = class$("org.antlr.stringtemplate.misc.JTreeStringTemplateModel$StringTemplateWrapper");
            class$org$antlr$stringtemplate$misc$JTreeStringTemplateModel$StringTemplateWrapper = clsClass$;
        }
        map.put("org.antlr.stringtemplate.StringTemplate", clsClass$);
        Map map2 = classNameToWrapperMap;
        Class clsClass$2 = class$org$antlr$stringtemplate$misc$JTreeStringTemplateModel$ExprWrapper;
        if (clsClass$2 == null) {
            clsClass$2 = class$("org.antlr.stringtemplate.misc.JTreeStringTemplateModel$ExprWrapper");
            class$org$antlr$stringtemplate$misc$JTreeStringTemplateModel$ExprWrapper = clsClass$2;
        }
        map2.put("org.antlr.stringtemplate.language.ASTExpr", clsClass$2);
        Map map3 = classNameToWrapperMap;
        Class clsClass$3 = class$org$antlr$stringtemplate$misc$JTreeStringTemplateModel$HashMapWrapper;
        if (clsClass$3 == null) {
            clsClass$3 = class$("org.antlr.stringtemplate.misc.JTreeStringTemplateModel$HashMapWrapper");
            class$org$antlr$stringtemplate$misc$JTreeStringTemplateModel$HashMapWrapper = clsClass$3;
        }
        map3.put("java.util.Hashtable", clsClass$3);
        Map map4 = classNameToWrapperMap;
        Class clsClass$4 = class$org$antlr$stringtemplate$misc$JTreeStringTemplateModel$ListWrapper;
        if (clsClass$4 == null) {
            clsClass$4 = class$("org.antlr.stringtemplate.misc.JTreeStringTemplateModel$ListWrapper");
            class$org$antlr$stringtemplate$misc$JTreeStringTemplateModel$ListWrapper = clsClass$4;
        }
        map4.put(CommonClassNames.JAVA_UTIL_ARRAY_LIST, clsClass$4);
        Map map5 = classNameToWrapperMap;
        Class clsClass$5 = class$org$antlr$stringtemplate$misc$JTreeStringTemplateModel$ListWrapper;
        if (clsClass$5 == null) {
            clsClass$5 = class$("org.antlr.stringtemplate.misc.JTreeStringTemplateModel$ListWrapper");
            class$org$antlr$stringtemplate$misc$JTreeStringTemplateModel$ListWrapper = clsClass$5;
        }
        map5.put("java.util.Vector", clsClass$5);
    }

    static /* synthetic */ Class class$(String str) throws Throwable {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError().initCause(e);
        }
    }

    static abstract class Wrapper {
        public abstract Object getChild(Object obj, int i);

        public abstract int getChildCount(Object obj);

        public abstract int getIndexOfChild(Object obj, Object obj2);

        public abstract Object getWrappedObject();

        public boolean isLeaf(Object obj) {
            return true;
        }

        Wrapper() {
        }
    }

    static class StringTemplateWrapper extends Wrapper {
        StringTemplate st;

        @Override // org.antlr.stringtemplate.misc.JTreeStringTemplateModel.Wrapper
        public boolean isLeaf(Object obj) {
            return false;
        }

        public StringTemplateWrapper(Object obj) {
            this.st = null;
            this.st = (StringTemplate) obj;
        }

        @Override // org.antlr.stringtemplate.misc.JTreeStringTemplateModel.Wrapper
        public Object getWrappedObject() {
            return getStringTemplate();
        }

        public StringTemplate getStringTemplate() {
            return this.st;
        }

        @Override // org.antlr.stringtemplate.misc.JTreeStringTemplateModel.Wrapper
        public Object getChild(Object obj, int i) {
            StringTemplate stringTemplate = ((StringTemplateWrapper) obj).getStringTemplate();
            if (i == 0) {
                return new HashMapWrapper(stringTemplate.getAttributes());
            }
            Expr expr = (Expr) stringTemplate.getChunks().get(i - 1);
            return expr instanceof StringRef ? expr : new ExprWrapper(expr);
        }

        @Override // org.antlr.stringtemplate.misc.JTreeStringTemplateModel.Wrapper
        public int getChildCount(Object obj) {
            return this.st.getChunks().size() + 1;
        }

        @Override // org.antlr.stringtemplate.misc.JTreeStringTemplateModel.Wrapper
        public int getIndexOfChild(Object obj, Object obj2) {
            if (obj2 instanceof Wrapper) {
                obj2 = ((Wrapper) obj2).getWrappedObject();
            }
            return this.st.getChunks().indexOf(obj2) + 1;
        }

        public String toString() {
            StringTemplate stringTemplate = this.st;
            if (stringTemplate == null) {
                return "<invalid template>";
            }
            return stringTemplate.getName();
        }
    }

    static class ExprWrapper extends Wrapper {
        Expr expr;

        public ExprWrapper(Object obj) {
            this.expr = null;
            this.expr = (Expr) obj;
        }

        public Expr getExpr() {
            return this.expr;
        }

        @Override // org.antlr.stringtemplate.misc.JTreeStringTemplateModel.Wrapper
        public Object getWrappedObject() {
            return this.expr;
        }

        @Override // org.antlr.stringtemplate.misc.JTreeStringTemplateModel.Wrapper
        public Object getChild(Object obj, int i) {
            Expr expr = ((ExprWrapper) obj).getExpr();
            if (expr instanceof ConditionalExpr) {
                return new StringTemplateWrapper(((ConditionalExpr) expr).getSubtemplate());
            }
            if (expr instanceof ASTExpr) {
                AST ast = ((ASTExpr) expr).getAST();
                if (ast.getType() != 7) {
                    return "<invalid>";
                }
                if (i == 0) {
                    return ast.getFirstChild().getNextSibling().toStringList();
                }
                if (i == 1) {
                    String text = ast.getFirstChild().getText();
                    StringTemplate enclosingTemplate = expr.getEnclosingTemplate();
                    return new StringTemplateWrapper(enclosingTemplate.getGroup().getEmbeddedInstanceOf(enclosingTemplate, text));
                }
                return "<invalid>";
            }
            return "<invalid>";
        }

        @Override // org.antlr.stringtemplate.misc.JTreeStringTemplateModel.Wrapper
        public int getChildCount(Object obj) {
            Expr expr = this.expr;
            if (expr instanceof ConditionalExpr) {
                return 1;
            }
            return ((ASTExpr) expr).getAST().getType() == 7 ? 2 : 0;
        }

        @Override // org.antlr.stringtemplate.misc.JTreeStringTemplateModel.Wrapper
        public int getIndexOfChild(Object obj, Object obj2) {
            return this.expr instanceof ConditionalExpr ? 0 : -1;
        }

        @Override // org.antlr.stringtemplate.misc.JTreeStringTemplateModel.Wrapper
        public boolean isLeaf(Object obj) {
            Expr expr = this.expr;
            if (expr instanceof ConditionalExpr) {
                return false;
            }
            return ((expr instanceof ASTExpr) && ((ASTExpr) expr).getAST().getType() == 7) ? false : true;
        }

        public String toString() {
            Expr expr = this.expr;
            if (expr instanceof ASTExpr) {
                if (((ASTExpr) expr).getAST().getType() == 7) {
                    return "$include$";
                }
                return new StringBuffer("$").append(((ASTExpr) this.expr).getAST().toStringList()).append("$").toString();
            }
            if (expr instanceof StringRef) {
                return expr.toString();
            }
            return "<invalid node type>";
        }
    }

    static class ListWrapper extends Wrapper {
        List v;

        @Override // org.antlr.stringtemplate.misc.JTreeStringTemplateModel.Wrapper
        public boolean isLeaf(Object obj) {
            return false;
        }

        public ListWrapper(Object obj) {
            this.v = null;
            this.v = (List) obj;
        }

        @Override // org.antlr.stringtemplate.misc.JTreeStringTemplateModel.Wrapper
        public int getChildCount(Object obj) {
            return this.v.size();
        }

        @Override // org.antlr.stringtemplate.misc.JTreeStringTemplateModel.Wrapper
        public int getIndexOfChild(Object obj, Object obj2) {
            if (obj2 instanceof Wrapper) {
                obj2 = ((Wrapper) obj2).getWrappedObject();
            }
            return this.v.indexOf(obj2);
        }

        @Override // org.antlr.stringtemplate.misc.JTreeStringTemplateModel.Wrapper
        public Object getChild(Object obj, int i) {
            return this.v.get(i);
        }

        @Override // org.antlr.stringtemplate.misc.JTreeStringTemplateModel.Wrapper
        public Object getWrappedObject() {
            return this.v;
        }
    }

    static class MapEntryWrapper extends Wrapper {
        Object key;
        Object value;

        @Override // org.antlr.stringtemplate.misc.JTreeStringTemplateModel.Wrapper
        public boolean isLeaf(Object obj) {
            return false;
        }

        public MapEntryWrapper(Object obj, Object obj2) {
            this.key = obj;
            this.value = obj2;
        }

        @Override // org.antlr.stringtemplate.misc.JTreeStringTemplateModel.Wrapper
        public Object getWrappedObject() {
            return JTreeStringTemplateModel.wrap(this.value);
        }

        @Override // org.antlr.stringtemplate.misc.JTreeStringTemplateModel.Wrapper
        public int getChildCount(Object obj) {
            Object obj2 = this.value;
            if (obj2 instanceof Wrapper) {
                return ((Wrapper) obj2).getChildCount(obj2);
            }
            return 1;
        }

        @Override // org.antlr.stringtemplate.misc.JTreeStringTemplateModel.Wrapper
        public int getIndexOfChild(Object obj, Object obj2) {
            Object obj3 = this.value;
            if (obj3 instanceof Wrapper) {
                return ((Wrapper) obj3).getIndexOfChild(obj3, obj2);
            }
            return 0;
        }

        @Override // org.antlr.stringtemplate.misc.JTreeStringTemplateModel.Wrapper
        public Object getChild(Object obj, int i) {
            Object obj2 = this.value;
            return obj2 instanceof Wrapper ? ((Wrapper) obj2).getChild(obj2, i) : obj2;
        }

        public String toString() {
            return this.key.toString();
        }
    }

    static class HashMapWrapper extends Wrapper {
        HashMap table;

        @Override // org.antlr.stringtemplate.misc.JTreeStringTemplateModel.Wrapper
        public boolean isLeaf(Object obj) {
            return false;
        }

        public HashMapWrapper(Object obj) {
            this.table = (HashMap) obj;
        }

        @Override // org.antlr.stringtemplate.misc.JTreeStringTemplateModel.Wrapper
        public Object getWrappedObject() {
            return this.table;
        }

        @Override // org.antlr.stringtemplate.misc.JTreeStringTemplateModel.Wrapper
        public Object getChild(Object obj, int i) {
            String str = (String) getTableAsListOfKeys().get(i);
            return new MapEntryWrapper(str, JTreeStringTemplateModel.wrap(this.table.get(str)));
        }

        @Override // org.antlr.stringtemplate.misc.JTreeStringTemplateModel.Wrapper
        public int getChildCount(Object obj) {
            return getTableAsListOfKeys().size();
        }

        @Override // org.antlr.stringtemplate.misc.JTreeStringTemplateModel.Wrapper
        public int getIndexOfChild(Object obj, Object obj2) {
            return getTableAsListOfKeys().indexOf(obj2);
        }

        public String toString() {
            return "attributes";
        }

        private List getTableAsListOfKeys() {
            HashMap map = this.table;
            if (map == null) {
                return new LinkedList();
            }
            Set setKeySet = map.keySet();
            LinkedList linkedList = new LinkedList();
            Iterator it = setKeySet.iterator();
            while (it.hasNext()) {
                linkedList.add((String) it.next());
            }
            return linkedList;
        }
    }

    public static Object wrap(Object obj) throws Throwable {
        try {
            Class cls = (Class) classNameToWrapperMap.get(obj.getClass().getName());
            Class<?>[] clsArr = new Class[1];
            Class<?> clsClass$ = class$java$lang$Object;
            if (clsClass$ == null) {
                clsClass$ = class$(CommonClassNames.JAVA_LANG_OBJECT);
                class$java$lang$Object = clsClass$;
            }
            clsArr[0] = clsClass$;
            return cls.getConstructor(clsArr).newInstance(obj);
        } catch (Exception unused) {
            return obj;
        }
    }

    public JTreeStringTemplateModel(StringTemplate stringTemplate) {
        this.root = null;
        if (stringTemplate == null) {
            throw new IllegalArgumentException("root is null");
        }
        this.root = new StringTemplateWrapper(stringTemplate);
    }

    public Object getChild(Object obj, int i) {
        if (obj == null) {
            return null;
        }
        return ((Wrapper) obj).getChild(obj, i);
    }

    public int getChildCount(Object obj) {
        if (obj == null) {
            throw new IllegalArgumentException("root is null");
        }
        return ((Wrapper) obj).getChildCount(obj);
    }

    public int getIndexOfChild(Object obj, Object obj2) {
        if (obj == null || obj2 == null) {
            throw new IllegalArgumentException("root or child is null");
        }
        return ((Wrapper) obj).getIndexOfChild(obj, obj2);
    }

    public Object getRoot() {
        return this.root;
    }

    public boolean isLeaf(Object obj) {
        if (obj == null) {
            throw new IllegalArgumentException("node is null");
        }
        if (obj instanceof Wrapper) {
            return ((Wrapper) obj).isLeaf(obj);
        }
        return true;
    }

    public void valueForPathChanged(TreePath treePath, Object obj) {
        System.out.println("heh, who is calling this mystery method?");
    }
}
