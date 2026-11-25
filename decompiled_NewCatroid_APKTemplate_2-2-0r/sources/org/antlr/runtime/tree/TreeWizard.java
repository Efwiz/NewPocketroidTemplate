package org.antlr.runtime.tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.antlr.runtime.Token;

/* JADX INFO: loaded from: classes12.dex */
public class TreeWizard {
    protected TreeAdaptor adaptor;
    protected Map<String, Integer> tokenNameToTypeMap;

    public interface ContextVisitor {
        void visit(Object obj, Object obj2, int i, Map<String, Object> map);
    }

    public Object findFirst(Object obj, int i) {
        return null;
    }

    public Object findFirst(Object obj, String str) {
        return null;
    }

    public static abstract class Visitor implements ContextVisitor {
        public abstract void visit(Object obj);

        @Override // org.antlr.runtime.tree.TreeWizard.ContextVisitor
        public void visit(Object obj, Object obj2, int i, Map<String, Object> map) {
            visit(obj);
        }
    }

    public static class TreePattern extends CommonTree {
        public boolean hasTextArg;
        public String label;

        public TreePattern(Token token) {
            super(token);
        }

        @Override // org.antlr.runtime.tree.CommonTree, org.antlr.runtime.tree.BaseTree, org.antlr.runtime.tree.Tree
        public String toString() {
            if (this.label != null) {
                return "%" + this.label + ":" + super.toString();
            }
            return super.toString();
        }
    }

    public static class WildcardTreePattern extends TreePattern {
        public WildcardTreePattern(Token token) {
            super(token);
        }
    }

    public static class TreePatternTreeAdaptor extends CommonTreeAdaptor {
        @Override // org.antlr.runtime.tree.CommonTreeAdaptor, org.antlr.runtime.tree.TreeAdaptor
        public Object create(Token token) {
            return new TreePattern(token);
        }
    }

    public TreeWizard(TreeAdaptor treeAdaptor) {
        this.adaptor = treeAdaptor;
    }

    public TreeWizard(TreeAdaptor treeAdaptor, Map<String, Integer> map) {
        this.adaptor = treeAdaptor;
        this.tokenNameToTypeMap = map;
    }

    public TreeWizard(TreeAdaptor treeAdaptor, String[] strArr) {
        this.adaptor = treeAdaptor;
        this.tokenNameToTypeMap = computeTokenTypes(strArr);
    }

    public TreeWizard(String[] strArr) {
        this(new CommonTreeAdaptor(), strArr);
    }

    public Map<String, Integer> computeTokenTypes(String[] strArr) {
        HashMap map = new HashMap();
        if (strArr == null) {
            return map;
        }
        for (int i = 4; i < strArr.length; i++) {
            map.put(strArr[i], Integer.valueOf(i));
        }
        return map;
    }

    public int getTokenType(String str) {
        Integer num;
        Map<String, Integer> map = this.tokenNameToTypeMap;
        if (map == null || (num = map.get(str)) == null) {
            return 0;
        }
        return num.intValue();
    }

    public Map<Integer, List<Object>> index(Object obj) {
        HashMap map = new HashMap();
        _index(obj, map);
        return map;
    }

    protected void _index(Object obj, Map<Integer, List<Object>> map) {
        if (obj == null) {
            return;
        }
        int type = this.adaptor.getType(obj);
        List<Object> arrayList = map.get(Integer.valueOf(type));
        if (arrayList == null) {
            arrayList = new ArrayList<>();
            map.put(Integer.valueOf(type), arrayList);
        }
        arrayList.add(obj);
        int childCount = this.adaptor.getChildCount(obj);
        for (int i = 0; i < childCount; i++) {
            _index(this.adaptor.getChild(obj, i), map);
        }
    }

    public List<? extends Object> find(Object obj, int i) {
        final ArrayList arrayList = new ArrayList();
        visit(obj, i, new Visitor() { // from class: org.antlr.runtime.tree.TreeWizard.1
            @Override // org.antlr.runtime.tree.TreeWizard.Visitor
            public void visit(Object obj2) {
                arrayList.add(obj2);
            }
        });
        return arrayList;
    }

    public List<? extends Object> find(Object obj, String str) {
        final ArrayList arrayList = new ArrayList();
        final TreePattern treePattern = (TreePattern) new TreePatternParser(new TreePatternLexer(str), this, new TreePatternTreeAdaptor()).pattern();
        if (treePattern == null || treePattern.isNil() || treePattern.getClass() == WildcardTreePattern.class) {
            return null;
        }
        visit(obj, treePattern.getType(), new ContextVisitor() { // from class: org.antlr.runtime.tree.TreeWizard.2
            @Override // org.antlr.runtime.tree.TreeWizard.ContextVisitor
            public void visit(Object obj2, Object obj3, int i, Map map) {
                if (TreeWizard.this._parse(obj2, treePattern, null)) {
                    arrayList.add(obj2);
                }
            }
        });
        return arrayList;
    }

    public void visit(Object obj, int i, ContextVisitor contextVisitor) {
        _visit(obj, null, 0, i, contextVisitor);
    }

    protected void _visit(Object obj, Object obj2, int i, int i2, ContextVisitor contextVisitor) {
        if (obj == null) {
            return;
        }
        if (this.adaptor.getType(obj) == i2) {
            contextVisitor.visit(obj, obj2, i, null);
        }
        int childCount = this.adaptor.getChildCount(obj);
        for (int i3 = 0; i3 < childCount; i3++) {
            _visit(this.adaptor.getChild(obj, i3), obj, i3, i2, contextVisitor);
        }
    }

    public void visit(Object obj, String str, final ContextVisitor contextVisitor) {
        final TreePattern treePattern = (TreePattern) new TreePatternParser(new TreePatternLexer(str), this, new TreePatternTreeAdaptor()).pattern();
        if (treePattern == null || treePattern.isNil() || treePattern.getClass() == WildcardTreePattern.class) {
            return;
        }
        final HashMap map = new HashMap();
        visit(obj, treePattern.getType(), new ContextVisitor() { // from class: org.antlr.runtime.tree.TreeWizard.3
            @Override // org.antlr.runtime.tree.TreeWizard.ContextVisitor
            public void visit(Object obj2, Object obj3, int i, Map<String, Object> map2) {
                map.clear();
                if (TreeWizard.this._parse(obj2, treePattern, map)) {
                    contextVisitor.visit(obj2, obj3, i, map);
                }
            }
        });
    }

    public boolean parse(Object obj, String str, Map<String, Object> map) {
        return _parse(obj, (TreePattern) new TreePatternParser(new TreePatternLexer(str), this, new TreePatternTreeAdaptor()).pattern(), map);
    }

    public boolean parse(Object obj, String str) {
        return parse(obj, str, null);
    }

    protected boolean _parse(Object obj, TreePattern treePattern, Map<String, Object> map) {
        if (obj == null || treePattern == null) {
            return false;
        }
        if (treePattern.getClass() != WildcardTreePattern.class) {
            if (this.adaptor.getType(obj) != treePattern.getType()) {
                return false;
            }
            if (treePattern.hasTextArg && !this.adaptor.getText(obj).equals(treePattern.getText())) {
                return false;
            }
        }
        if (treePattern.label != null && map != null) {
            map.put(treePattern.label, obj);
        }
        int childCount = this.adaptor.getChildCount(obj);
        if (childCount != treePattern.getChildCount()) {
            return false;
        }
        for (int i = 0; i < childCount; i++) {
            if (!_parse(this.adaptor.getChild(obj, i), (TreePattern) treePattern.getChild(i), map)) {
                return false;
            }
        }
        return true;
    }

    public Object create(String str) {
        return new TreePatternParser(new TreePatternLexer(str), this, this.adaptor).pattern();
    }

    public static boolean equals(Object obj, Object obj2, TreeAdaptor treeAdaptor) {
        return _equals(obj, obj2, treeAdaptor);
    }

    public boolean equals(Object obj, Object obj2) {
        return _equals(obj, obj2, this.adaptor);
    }

    protected static boolean _equals(Object obj, Object obj2, TreeAdaptor treeAdaptor) {
        int childCount;
        if (obj == null || obj2 == null || treeAdaptor.getType(obj) != treeAdaptor.getType(obj2) || !treeAdaptor.getText(obj).equals(treeAdaptor.getText(obj2)) || (childCount = treeAdaptor.getChildCount(obj)) != treeAdaptor.getChildCount(obj2)) {
            return false;
        }
        for (int i = 0; i < childCount; i++) {
            if (!_equals(treeAdaptor.getChild(obj, i), treeAdaptor.getChild(obj2, i), treeAdaptor)) {
                return false;
            }
        }
        return true;
    }
}
