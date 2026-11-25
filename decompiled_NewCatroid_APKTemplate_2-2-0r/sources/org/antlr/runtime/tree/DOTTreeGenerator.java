package org.antlr.runtime.tree;

import java.util.HashMap;
import org.antlr.stringtemplate.StringTemplate;

/* JADX INFO: loaded from: classes12.dex */
public class DOTTreeGenerator {
    public static StringTemplate _treeST = new StringTemplate("digraph {\n\n\tordering=out;\n\tranksep=.4;\n\tbgcolor=\"lightgrey\"; node [shape=box, fixedsize=false, fontsize=12, fontname=\"Helvetica-bold\", fontcolor=\"blue\"\n\t\twidth=.25, height=.25, color=\"black\", fillcolor=\"white\", style=\"filled, solid, bold\"];\n\tedge [arrowsize=.5, color=\"black\", style=\"bold\"]\n\n  $nodes$\n  $edges$\n}\n");
    public static StringTemplate _nodeST = new StringTemplate("$name$ [label=\"$text$\"];\n");
    public static StringTemplate _edgeST = new StringTemplate("$parent$ -> $child$ // \"$parentText$\" -> \"$childText$\"\n");
    HashMap<Object, Integer> nodeToNumberMap = new HashMap<>();
    int nodeNumber = 0;

    public StringTemplate toDOT(Object obj, TreeAdaptor treeAdaptor, StringTemplate stringTemplate, StringTemplate stringTemplate2) {
        StringTemplate instanceOf = stringTemplate.getInstanceOf();
        this.nodeNumber = 0;
        toDOTDefineNodes(obj, treeAdaptor, instanceOf);
        this.nodeNumber = 0;
        toDOTDefineEdges(obj, treeAdaptor, instanceOf);
        return instanceOf;
    }

    public StringTemplate toDOT(Object obj, TreeAdaptor treeAdaptor) {
        return toDOT(obj, treeAdaptor, _treeST, _edgeST);
    }

    public StringTemplate toDOT(Tree tree) {
        return toDOT(tree, new CommonTreeAdaptor());
    }

    protected void toDOTDefineNodes(Object obj, TreeAdaptor treeAdaptor, StringTemplate stringTemplate) {
        int childCount;
        if (obj == null || (childCount = treeAdaptor.getChildCount(obj)) == 0) {
            return;
        }
        stringTemplate.setAttribute("nodes", getNodeST(treeAdaptor, obj));
        for (int i = 0; i < childCount; i++) {
            Object child = treeAdaptor.getChild(obj, i);
            stringTemplate.setAttribute("nodes", getNodeST(treeAdaptor, child));
            toDOTDefineNodes(child, treeAdaptor, stringTemplate);
        }
    }

    protected void toDOTDefineEdges(Object obj, TreeAdaptor treeAdaptor, StringTemplate stringTemplate) {
        int childCount;
        if (obj == null || (childCount = treeAdaptor.getChildCount(obj)) == 0) {
            return;
        }
        String str = "n" + getNodeNumber(obj);
        String text = treeAdaptor.getText(obj);
        for (int i = 0; i < childCount; i++) {
            Object child = treeAdaptor.getChild(obj, i);
            String text2 = treeAdaptor.getText(child);
            String str2 = "n" + getNodeNumber(child);
            StringTemplate instanceOf = _edgeST.getInstanceOf();
            instanceOf.setAttribute("parent", str);
            instanceOf.setAttribute("child", str2);
            instanceOf.setAttribute("parentText", fixString(text));
            instanceOf.setAttribute("childText", fixString(text2));
            stringTemplate.setAttribute("edges", instanceOf);
            toDOTDefineEdges(child, treeAdaptor, stringTemplate);
        }
    }

    protected StringTemplate getNodeST(TreeAdaptor treeAdaptor, Object obj) {
        String text = treeAdaptor.getText(obj);
        StringTemplate instanceOf = _nodeST.getInstanceOf();
        instanceOf.setAttribute("name", "n" + getNodeNumber(obj));
        instanceOf.setAttribute("text", fixString(text));
        return instanceOf;
    }

    protected int getNodeNumber(Object obj) {
        Integer num = this.nodeToNumberMap.get(obj);
        if (num != null) {
            return num.intValue();
        }
        this.nodeToNumberMap.put(obj, Integer.valueOf(this.nodeNumber));
        int i = this.nodeNumber;
        this.nodeNumber = i + 1;
        return i;
    }

    protected String fixString(String str) {
        if (str == null) {
            return str;
        }
        String strReplaceAll = str.replaceAll("\"", "\\\\\"").replaceAll("\\t", "    ").replaceAll("\\n", "\\\\n").replaceAll("\\r", "\\\\r");
        return strReplaceAll.length() > 20 ? strReplaceAll.substring(0, 8) + "..." + strReplaceAll.substring(strReplaceAll.length() - 8) : strReplaceAll;
    }
}
