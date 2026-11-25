package org.mozilla.javascript.optimizer;

import org.mozilla.javascript.Node;
import org.mozilla.javascript.ObjArray;
import org.mozilla.javascript.ast.ScriptNode;

/* JADX INFO: loaded from: classes4.dex */
class Optimizer {
    static final int AnyType = 3;
    static final int NoType = 0;
    static final int NumberType = 1;
    private boolean inDirectCallFunction;
    private boolean parameterUsedInNumberContext;
    OptFunctionNode theFunction;

    Optimizer() {
    }

    void optimize(ScriptNode scriptNode) {
        int functionCount = scriptNode.getFunctionCount();
        for (int i = 0; i != functionCount; i++) {
            optimizeFunction(OptFunctionNode.get(scriptNode, i));
        }
    }

    private void optimizeFunction(OptFunctionNode optFunctionNode) {
        if (optFunctionNode.fnode.requiresActivation()) {
            return;
        }
        this.inDirectCallFunction = optFunctionNode.isTargetOfDirectCall();
        this.theFunction = optFunctionNode;
        ObjArray objArray = new ObjArray();
        buildStatementList_r(optFunctionNode.fnode, objArray);
        int size = objArray.size();
        Node[] nodeArr = new Node[size];
        objArray.toArray(nodeArr);
        Block.runFlowAnalyzes(optFunctionNode, nodeArr);
        if (optFunctionNode.fnode.requiresActivation()) {
            return;
        }
        this.parameterUsedInNumberContext = false;
        for (int i = 0; i < size; i++) {
            rewriteForNumberVariables(nodeArr[i], 1);
        }
        optFunctionNode.setParameterNumberContext(this.parameterUsedInNumberContext);
    }

    private void markDCPNumberContext(Node node) {
        if (this.inDirectCallFunction && node.getType() == 55) {
            if (this.theFunction.isParameter(this.theFunction.getVarIndex(node))) {
                this.parameterUsedInNumberContext = true;
            }
        }
    }

    private boolean convertParameter(Node node) {
        if (!this.inDirectCallFunction || node.getType() != 55) {
            return false;
        }
        if (!this.theFunction.isParameter(this.theFunction.getVarIndex(node))) {
            return false;
        }
        node.removeProp(8);
        return true;
    }

    /* JADX WARN: Code duplicated, block: B:101:0x0186  */
    /* JADX WARN: Code duplicated, block: B:82:0x0125 A[FALL_THROUGH] */
    /* JADX WARN: Code duplicated, block: B:84:0x013d A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:85:0x013f  */
    /* JADX WARN: Code duplicated, block: B:87:0x0143  */
    /* JADX WARN: Code duplicated, block: B:89:0x0149  */
    /* JADX WARN: Code duplicated, block: B:91:0x0158 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:92:0x015a  */
    /* JADX WARN: Code duplicated, block: B:94:0x0160  */
    /* JADX WARN: Code duplicated, block: B:96:0x016f  */
    /* JADX WARN: Code duplicated, block: B:98:0x0175  */
    private int rewriteForNumberVariables(Node node, int i) {
        Node firstChild;
        Node next;
        int iRewriteForNumberVariables;
        int iRewriteForNumberVariables2;
        int type = node.getType();
        if (type == 40) {
            node.putIntProp(8, 0);
            return 1;
        }
        if (type == 134) {
            if (rewriteForNumberVariables(node.getFirstChild(), 1) == 1) {
                node.putIntProp(8, 0);
            }
            return 0;
        }
        if (type != 141) {
            if (type != 157) {
                if (type == 55) {
                    int varIndex = this.theFunction.getVarIndex(node);
                    if (this.inDirectCallFunction && this.theFunction.isParameter(varIndex) && i == 1) {
                        node.putIntProp(8, 0);
                        return 1;
                    }
                    if (!this.theFunction.isNumberVar(varIndex)) {
                        return 0;
                    }
                    node.putIntProp(8, 0);
                    return 1;
                }
                if (type != 56) {
                    if (type == 107 || type == 108) {
                        Node firstChild2 = node.getFirstChild();
                        int iRewriteForNumberVariables3 = rewriteForNumberVariables(firstChild2, 1);
                        if (firstChild2.getType() == 55) {
                            if (iRewriteForNumberVariables3 != 1 || convertParameter(firstChild2)) {
                                return 0;
                            }
                            node.putIntProp(8, 0);
                            markDCPNumberContext(firstChild2);
                            return 1;
                        }
                        if (firstChild2.getType() == 36 || firstChild2.getType() == 33) {
                            return iRewriteForNumberVariables3;
                        }
                        return 0;
                    }
                    switch (type) {
                        default:
                            switch (type) {
                                case 14:
                                case 15:
                                case 16:
                                case 17:
                                    Node firstChild3 = node.getFirstChild();
                                    Node next2 = firstChild3.getNext();
                                    int iRewriteForNumberVariables4 = rewriteForNumberVariables(firstChild3, 1);
                                    int iRewriteForNumberVariables5 = rewriteForNumberVariables(next2, 1);
                                    markDCPNumberContext(firstChild3);
                                    markDCPNumberContext(next2);
                                    if (convertParameter(firstChild3)) {
                                        if (!convertParameter(next2) && iRewriteForNumberVariables5 == 1) {
                                            node.putIntProp(8, 2);
                                        }
                                    } else if (convertParameter(next2)) {
                                        if (iRewriteForNumberVariables4 == 1) {
                                            node.putIntProp(8, 1);
                                        }
                                    } else if (iRewriteForNumberVariables4 == 1) {
                                        if (iRewriteForNumberVariables5 == 1) {
                                            node.putIntProp(8, 0);
                                        } else {
                                            node.putIntProp(8, 1);
                                        }
                                    } else if (iRewriteForNumberVariables5 == 1) {
                                        node.putIntProp(8, 2);
                                    }
                                    return 0;
                                default:
                                    switch (type) {
                                        case 21:
                                            Node firstChild4 = node.getFirstChild();
                                            Node next3 = firstChild4.getNext();
                                            int iRewriteForNumberVariables6 = rewriteForNumberVariables(firstChild4, 1);
                                            int iRewriteForNumberVariables7 = rewriteForNumberVariables(next3, 1);
                                            if (convertParameter(firstChild4)) {
                                                if (!convertParameter(next3) && iRewriteForNumberVariables7 == 1) {
                                                    node.putIntProp(8, 2);
                                                }
                                            } else if (convertParameter(next3)) {
                                                if (iRewriteForNumberVariables6 == 1) {
                                                    node.putIntProp(8, 1);
                                                }
                                            } else if (iRewriteForNumberVariables6 == 1) {
                                                if (iRewriteForNumberVariables7 == 1) {
                                                    node.putIntProp(8, 0);
                                                    return 1;
                                                }
                                                node.putIntProp(8, 1);
                                            } else if (iRewriteForNumberVariables7 == 1) {
                                                node.putIntProp(8, 2);
                                            }
                                            return 0;
                                        case 22:
                                        case 23:
                                        case 24:
                                        case 25:
                                            break;
                                        default:
                                            switch (type) {
                                                case 36:
                                                    Node firstChild5 = node.getFirstChild();
                                                    Node next4 = firstChild5.getNext();
                                                    if (rewriteForNumberVariables(firstChild5, 1) == 1 && !convertParameter(firstChild5)) {
                                                        node.removeChild(firstChild5);
                                                        node.addChildToFront(new Node(150, firstChild5));
                                                    }
                                                    if (rewriteForNumberVariables(next4, 1) == 1 && !convertParameter(next4)) {
                                                        node.putIntProp(8, 2);
                                                    }
                                                    return 0;
                                                case 37:
                                                    break;
                                                case 38:
                                                    Node firstChild6 = node.getFirstChild();
                                                    rewriteAsObjectChildren(firstChild6, firstChild6.getFirstChild());
                                                    Node next5 = firstChild6.getNext();
                                                    if (((OptFunctionNode) node.getProp(9)) != null) {
                                                        while (next5 != null) {
                                                            if (rewriteForNumberVariables(next5, 1) == 1) {
                                                                markDCPNumberContext(next5);
                                                            }
                                                            next5 = next5.getNext();
                                                        }
                                                    } else {
                                                        rewriteAsObjectChildren(node, next5);
                                                    }
                                                    return 0;
                                                default:
                                                    rewriteAsObjectChildren(node, node.getFirstChild());
                                                    return 0;
                                            }
                                            break;
                                    }
                                case 18:
                                case 19:
                                    firstChild = node.getFirstChild();
                                    next = firstChild.getNext();
                                    iRewriteForNumberVariables = rewriteForNumberVariables(firstChild, 1);
                                    iRewriteForNumberVariables2 = rewriteForNumberVariables(next, 1);
                                    markDCPNumberContext(firstChild);
                                    markDCPNumberContext(next);
                                    if (iRewriteForNumberVariables == 1) {
                                        if (iRewriteForNumberVariables2 == 1) {
                                            node.putIntProp(8, 0);
                                            return 1;
                                        }
                                        if (!convertParameter(next)) {
                                            node.removeChild(next);
                                            node.addChildToBack(new Node(151, next));
                                            node.putIntProp(8, 0);
                                        }
                                        return 1;
                                    }
                                    if (iRewriteForNumberVariables2 == 1) {
                                        if (!convertParameter(firstChild)) {
                                            node.removeChild(firstChild);
                                            node.addChildToFront(new Node(151, firstChild));
                                            node.putIntProp(8, 0);
                                        }
                                        return 1;
                                    }
                                    if (!convertParameter(firstChild)) {
                                        node.removeChild(firstChild);
                                        node.addChildToFront(new Node(151, firstChild));
                                    }
                                    if (!convertParameter(next)) {
                                        node.removeChild(next);
                                        node.addChildToBack(new Node(151, next));
                                    }
                                    node.putIntProp(8, 0);
                                    return 1;
                            }
                        case 9:
                        case 10:
                        case 11:
                            firstChild = node.getFirstChild();
                            next = firstChild.getNext();
                            iRewriteForNumberVariables = rewriteForNumberVariables(firstChild, 1);
                            iRewriteForNumberVariables2 = rewriteForNumberVariables(next, 1);
                            markDCPNumberContext(firstChild);
                            markDCPNumberContext(next);
                            if (iRewriteForNumberVariables == 1) {
                                if (iRewriteForNumberVariables2 == 1) {
                                    node.putIntProp(8, 0);
                                    return 1;
                                }
                                if (!convertParameter(next)) {
                                    node.removeChild(next);
                                    node.addChildToBack(new Node(151, next));
                                    node.putIntProp(8, 0);
                                }
                                return 1;
                            }
                            if (iRewriteForNumberVariables2 == 1) {
                                if (!convertParameter(firstChild)) {
                                    node.removeChild(firstChild);
                                    node.addChildToFront(new Node(151, firstChild));
                                    node.putIntProp(8, 0);
                                }
                                return 1;
                            }
                            if (!convertParameter(firstChild)) {
                                node.removeChild(firstChild);
                                node.addChildToFront(new Node(151, firstChild));
                            }
                            if (!convertParameter(next)) {
                                node.removeChild(next);
                                node.addChildToBack(new Node(151, next));
                            }
                            node.putIntProp(8, 0);
                            return 1;
                    }
                }
            }
            Node next6 = node.getFirstChild().getNext();
            int iRewriteForNumberVariables8 = rewriteForNumberVariables(next6, 1);
            int varIndex2 = this.theFunction.getVarIndex(node);
            if (this.inDirectCallFunction && this.theFunction.isParameter(varIndex2)) {
                if (iRewriteForNumberVariables8 != 1) {
                    return iRewriteForNumberVariables8;
                }
                if (!convertParameter(next6)) {
                    node.putIntProp(8, 0);
                    return 1;
                }
                markDCPNumberContext(next6);
                return 0;
            }
            if (this.theFunction.isNumberVar(varIndex2)) {
                if (iRewriteForNumberVariables8 != 1) {
                    node.removeChild(next6);
                    node.addChildToBack(new Node(151, next6));
                }
                node.putIntProp(8, 0);
                markDCPNumberContext(next6);
                return 1;
            }
            if (iRewriteForNumberVariables8 == 1 && !convertParameter(next6)) {
                node.removeChild(next6);
                node.addChildToBack(new Node(150, next6));
            }
            return 0;
        }
        Node firstChild7 = node.getFirstChild();
        Node next7 = firstChild7.getNext();
        Node next8 = next7.getNext();
        if (rewriteForNumberVariables(firstChild7, 1) == 1 && !convertParameter(firstChild7)) {
            node.removeChild(firstChild7);
            node.addChildToFront(new Node(150, firstChild7));
        }
        if (rewriteForNumberVariables(next7, 1) == 1 && !convertParameter(next7)) {
            node.putIntProp(8, 1);
        }
        if (rewriteForNumberVariables(next8, 1) == 1 && !convertParameter(next8)) {
            node.removeChild(next8);
            node.addChildToBack(new Node(150, next8));
        }
        return 0;
    }

    private void rewriteAsObjectChildren(Node node, Node node2) {
        while (node2 != null) {
            Node next = node2.getNext();
            if (rewriteForNumberVariables(node2, 0) == 1 && !convertParameter(node2)) {
                node.removeChild(node2);
                Node node3 = new Node(150, node2);
                if (next == null) {
                    node.addChildToBack(node3);
                } else {
                    node.addChildBefore(node3, next);
                }
            }
            node2 = next;
        }
    }

    private static void buildStatementList_r(Node node, ObjArray objArray) {
        int type = node.getType();
        if (type == 130 || type == 142 || type == 133 || type == 110) {
            for (Node firstChild = node.getFirstChild(); firstChild != null; firstChild = firstChild.getNext()) {
                buildStatementList_r(firstChild, objArray);
            }
            return;
        }
        objArray.add(node);
    }
}
