package org.mozilla.javascript;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.mozilla.javascript.ast.FunctionNode;
import org.mozilla.javascript.ast.Jump;
import org.mozilla.javascript.ast.Scope;
import org.mozilla.javascript.ast.ScriptNode;

/* JADX INFO: loaded from: classes4.dex */
public class NodeTransformer {
    private boolean hasFinally;
    private ObjArray loopEnds;
    private ObjArray loops;

    protected void visitCall(Node node, ScriptNode scriptNode) {
    }

    protected void visitNew(Node node, ScriptNode scriptNode) {
    }

    public final void transform(ScriptNode scriptNode, CompilerEnvirons compilerEnvirons) {
        transform(scriptNode, false, compilerEnvirons);
    }

    public final void transform(ScriptNode scriptNode, boolean z, CompilerEnvirons compilerEnvirons) {
        if (compilerEnvirons.getLanguageVersion() >= 200 && scriptNode.isInStrictMode()) {
            z = true;
        }
        transformCompilationUnit(scriptNode, z);
        for (int i = 0; i != scriptNode.getFunctionCount(); i++) {
            transform(scriptNode.getFunctionNode(i), z, compilerEnvirons);
        }
    }

    private void transformCompilationUnit(ScriptNode scriptNode, boolean z) {
        this.loops = new ObjArray();
        this.loopEnds = new ObjArray();
        this.hasFinally = false;
        boolean z2 = scriptNode.getType() != 110 || ((FunctionNode) scriptNode).requiresActivation();
        scriptNode.flattenSymbolTable(!z2);
        transformCompilationUnit_r(scriptNode, scriptNode, scriptNode, z2, z);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code duplicated, block: B:100:0x019e  */
    /* JADX WARN: Code duplicated, block: B:110:0x01ce  */
    /* JADX WARN: Code duplicated, block: B:114:0x01f7  */
    /* JADX WARN: Code duplicated, block: B:150:0x027d  */
    /* JADX WARN: Code duplicated, block: B:152:0x0284 A[LOOP:4: B:152:0x0284->B:154:0x028c, LOOP_START, PHI: r1
      0x0284: PHI (r1v14 org.mozilla.javascript.Node) = (r1v12 org.mozilla.javascript.Node), (r1v17 org.mozilla.javascript.Node) binds: [B:151:0x0282, B:154:0x028c] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:154:0x028c A[LOOP:4: B:152:0x0284->B:154:0x028c, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:159:0x02a1  */
    /* JADX WARN: Code duplicated, block: B:161:0x02b1  */
    /* JADX WARN: Code duplicated, block: B:164:0x02bd  */
    /* JADX WARN: Code duplicated, block: B:166:0x02c3  */
    /* JADX WARN: Code duplicated, block: B:171:0x02d6  */
    /* JADX WARN: Code duplicated, block: B:205:0x037f  */
    /* JADX WARN: Code duplicated, block: B:212:0x039f  */
    /* JADX WARN: Code duplicated, block: B:213:0x03a4  */
    /* JADX WARN: Code duplicated, block: B:218:0x01b4 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:235:0x01b2 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:78:0x0140  */
    /* JADX WARN: Code duplicated, block: B:80:0x014a  */
    /* JADX WARN: Code duplicated, block: B:86:0x015f  */
    /* JADX WARN: Code duplicated, block: B:88:0x0166  */
    /* JADX WARN: Code duplicated, block: B:90:0x0173  */
    /* JADX WARN: Code duplicated, block: B:92:0x017d  */
    /* JADX WARN: Code duplicated, block: B:95:0x0184  */
    /* JADX WARN: Code duplicated, block: B:97:0x0194  */
    /* JADX WARN: Code duplicated, block: B:98:0x0197  */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r18v0, types: [org.mozilla.javascript.NodeTransformer] */
    /* JADX WARN: Type inference failed for: r1v1 */
    /* JADX WARN: Type inference failed for: r1v2, types: [org.mozilla.javascript.Node] */
    /* JADX WARN: Type inference failed for: r1v30, types: [org.mozilla.javascript.Node] */
    /* JADX WARN: Type inference failed for: r1v35, types: [org.mozilla.javascript.Node] */
    /* JADX WARN: Type inference failed for: r1v36 */
    /* JADX WARN: Type inference failed for: r1v37, types: [org.mozilla.javascript.Node] */
    /* JADX WARN: Type inference failed for: r1v40 */
    private void transformCompilationUnit_r(ScriptNode scriptNode, Node node, Scope scope, boolean z, boolean z2) {
        ?? AddBeforeCurrent;
        Node next;
        Node nodeReplaceCurrent;
        Scope scope2;
        int i;
        Node node2;
        Node node3;
        Jump jump;
        Node firstChild;
        Node firstChild2;
        Node lastChild;
        Node firstChild3;
        Scope definingScope;
        boolean z3;
        Node node4;
        Node firstChild4;
        int i2;
        Map<String, org.mozilla.javascript.ast.Symbol> map = null;
        Node node5 = null;
        while (true) {
            if (node5 == null) {
                next = node.getFirstChild();
                AddBeforeCurrent = map;
            } else {
                AddBeforeCurrent = node5;
                next = node5.getNext();
            }
            if (next == null) {
                return;
            }
            int type = next.getType();
            if (z && ((type == 130 || type == 133 || type == 158) && (next instanceof Scope))) {
                Scope scope3 = (Scope) next;
                if (scope3.getSymbolTable() != null) {
                    Node node6 = new Node(type == 158 ? 159 : 154);
                    Node node7 = new Node(154);
                    node6.addChildToBack(node7);
                    Iterator<String> it = scope3.getSymbolTable().keySet().iterator();
                    while (it.hasNext()) {
                        node7.addChildToBack(Node.newString(39, it.next()));
                    }
                    scope3.setSymbolTable(map);
                    Node nodeReplaceCurrent2 = replaceCurrent(node, AddBeforeCurrent, next, node6);
                    int type2 = nodeReplaceCurrent2.getType();
                    node6.addChildToBack(next);
                    next = nodeReplaceCurrent2;
                    type = type2;
                }
            }
            if (type != 3) {
                boolean z4 = false;
                if (type == 4) {
                    if (scriptNode.getType() == 110 && ((FunctionNode) scriptNode).isGenerator()) {
                        z4 = true;
                    }
                    if (z4) {
                        i = 1;
                        next.putIntProp(20, 1);
                    } else {
                        i = 1;
                    }
                    if (this.hasFinally) {
                        int size = this.loops.size() - i;
                        Node node8 = null;
                        while (size >= 0) {
                            Node node9 = (Node) this.loops.get(size);
                            int type3 = node9.getType();
                            if (type3 == 82 || type3 == 124) {
                                if (type3 == 82) {
                                    jump = new Jump(136);
                                    jump.target = ((Jump) node9).getFinally();
                                } else {
                                    node2 = new Node(3);
                                }
                                if (node8 == null) {
                                    node3 = node2;
                                    node3 = jump;
                                    node8 = new Node(130, next.getLineno());
                                } else {
                                    node3 = node2;
                                    node3 = jump;
                                    node8 = node8;
                                }
                                node8.addChildToBack(node3);
                            }
                            size--;
                            node8 = node8;
                        }
                        if (node8 != null) {
                            Node firstChild5 = next.getFirstChild();
                            nodeReplaceCurrent = replaceCurrent(node, AddBeforeCurrent, next, node8);
                            if (firstChild5 == null || z4) {
                                node8.addChildToBack(next);
                            } else {
                                Node node10 = new Node(135, firstChild5);
                                node8.addChildToFront(node10);
                                node8.addChildToBack(new Node(65));
                                transformCompilationUnit_r(scriptNode, node10, scope, z, z2);
                            }
                        }
                    }
                } else if (type == 7) {
                    firstChild = next.getFirstChild();
                    if (type == 7) {
                        while (firstChild.getType() == 26) {
                            firstChild = firstChild.getFirstChild();
                        }
                        if (firstChild.getType() != 12 || firstChild.getType() == 13) {
                            firstChild2 = firstChild.getFirstChild();
                            lastChild = firstChild.getLastChild();
                            if (firstChild2.getType() != 39 && firstChild2.getString().equals("undefined")) {
                                firstChild = lastChild;
                            } else if (lastChild.getType() == 39 && lastChild.getString().equals("undefined")) {
                                firstChild = firstChild2;
                            }
                        }
                    }
                    if (firstChild.getType() == 33) {
                        firstChild.setType(34);
                    }
                } else {
                    if (type != 8) {
                        if (type == 38) {
                            visitCall(next, scriptNode);
                        } else if (type != 39) {
                            if (type != 73) {
                                if (type != 82) {
                                    if (type != 115) {
                                        if (type != 138) {
                                            if (type != 159) {
                                                if (type == 166) {
                                                    ((FunctionNode) scriptNode).addResumptionPoint(next);
                                                } else {
                                                    switch (type) {
                                                        case 30:
                                                            visitNew(next, scriptNode);
                                                            break;
                                                        case 31:
                                                            break;
                                                        case 32:
                                                            firstChild = next.getFirstChild();
                                                            if (type == 7) {
                                                                while (firstChild.getType() == 26) {
                                                                    firstChild = firstChild.getFirstChild();
                                                                }
                                                                if (firstChild.getType() != 12) {
                                                                    firstChild2 = firstChild.getFirstChild();
                                                                    lastChild = firstChild.getLastChild();
                                                                    if (firstChild2.getType() != 39) {
                                                                        if (lastChild.getType() == 39) {
                                                                            firstChild = firstChild2;
                                                                        }
                                                                    } else if (lastChild.getType() == 39) {
                                                                        firstChild = firstChild2;
                                                                    }
                                                                } else {
                                                                    firstChild2 = firstChild.getFirstChild();
                                                                    lastChild = firstChild.getLastChild();
                                                                    if (firstChild2.getType() != 39) {
                                                                        if (lastChild.getType() == 39) {
                                                                            firstChild = firstChild2;
                                                                        }
                                                                    } else if (lastChild.getType() == 39) {
                                                                        firstChild = firstChild2;
                                                                    }
                                                                }
                                                            }
                                                            if (firstChild.getType() == 33) {
                                                                firstChild.setType(34);
                                                            }
                                                            break;
                                                        default:
                                                            switch (type) {
                                                                case 121:
                                                                case 122:
                                                                    Jump jump2 = (Jump) next;
                                                                    Jump jumpStatement = jump2.getJumpStatement();
                                                                    if (jumpStatement == null) {
                                                                        Kit.codeBug();
                                                                    }
                                                                    int size2 = this.loops.size();
                                                                    while (true) {
                                                                        if (size2 == 0) {
                                                                            throw Kit.codeBug();
                                                                        }
                                                                        size2--;
                                                                        Node node11 = (Node) this.loops.get(size2);
                                                                        if (node11 != jumpStatement) {
                                                                            int type4 = node11.getType();
                                                                            if (type4 == 124) {
                                                                                AddBeforeCurrent = addBeforeCurrent(node, AddBeforeCurrent, next, new Node(3));
                                                                            } else if (type4 == 82) {
                                                                                Jump jump3 = new Jump(136);
                                                                                jump3.target = ((Jump) node11).getFinally();
                                                                                AddBeforeCurrent = addBeforeCurrent(node, AddBeforeCurrent, next, jump3);
                                                                            }
                                                                        } else {
                                                                            if (type == 121) {
                                                                                jump2.target = jumpStatement.target;
                                                                            } else {
                                                                                jump2.target = jumpStatement.getContinue();
                                                                            }
                                                                            jump2.setType(5);
                                                                            break;
                                                                        }
                                                                    }
                                                                    break;
                                                                case 123:
                                                                    node4 = new Node(130);
                                                                    firstChild4 = next.getFirstChild();
                                                                    while (firstChild4 != null) {
                                                                        Node next2 = firstChild4.getNext();
                                                                        if (firstChild4.getType() == 39) {
                                                                            if (!firstChild4.hasChildren()) {
                                                                                Node firstChild6 = firstChild4.getFirstChild();
                                                                                firstChild4.removeChild(firstChild6);
                                                                                firstChild4.setType(49);
                                                                                if (type == 155) {
                                                                                    i2 = 156;
                                                                                } else {
                                                                                    i2 = 8;
                                                                                }
                                                                                firstChild4 = new Node(i2, firstChild4, firstChild6);
                                                                            }
                                                                            firstChild4 = next2;
                                                                        } else if (firstChild4.getType() != 159) {
                                                                            throw Kit.codeBug();
                                                                        }
                                                                        node4.addChildToBack(new Node(134, firstChild4, next.getLineno()));
                                                                        firstChild4 = next2;
                                                                    }
                                                                    next = replaceCurrent(node, AddBeforeCurrent, next, node4);
                                                                    break;
                                                                case 124:
                                                                    this.loops.push(next);
                                                                    Node next3 = next.getNext();
                                                                    if (next3.getType() != 3) {
                                                                        Kit.codeBug();
                                                                    }
                                                                    this.loopEnds.push(next3);
                                                                    break;
                                                                default:
                                                                    switch (type) {
                                                                        case 131:
                                                                        case 133:
                                                                            this.loops.push(next);
                                                                            this.loopEnds.push(((Jump) next).target);
                                                                            break;
                                                                        case 132:
                                                                            if (!this.loopEnds.isEmpty() && this.loopEnds.peek() == next) {
                                                                                this.loopEnds.pop();
                                                                                this.loops.pop();
                                                                            }
                                                                            break;
                                                                        default:
                                                                            switch (type) {
                                                                                case 154:
                                                                                    if (next.getFirstChild().getType() == 154) {
                                                                                        if (scriptNode.getType() == 110) {
                                                                                            z3 = true;
                                                                                        } else {
                                                                                            z3 = true;
                                                                                        }
                                                                                        next = visitLet(z3, node, AddBeforeCurrent, next);
                                                                                    } else {
                                                                                        node4 = new Node(130);
                                                                                        firstChild4 = next.getFirstChild();
                                                                                        while (firstChild4 != null) {
                                                                                            Node next4 = firstChild4.getNext();
                                                                                            if (firstChild4.getType() == 39) {
                                                                                                if (!firstChild4.hasChildren()) {
                                                                                                    Node firstChild7 = firstChild4.getFirstChild();
                                                                                                    firstChild4.removeChild(firstChild7);
                                                                                                    firstChild4.setType(49);
                                                                                                    if (type == 155) {
                                                                                                        i2 = 156;
                                                                                                    } else {
                                                                                                        i2 = 8;
                                                                                                    }
                                                                                                    firstChild4 = new Node(i2, firstChild4, firstChild7);
                                                                                                }
                                                                                                firstChild4 = next4;
                                                                                            } else if (firstChild4.getType() != 159) {
                                                                                                throw Kit.codeBug();
                                                                                            }
                                                                                            node4.addChildToBack(new Node(134, firstChild4, next.getLineno()));
                                                                                            firstChild4 = next4;
                                                                                        }
                                                                                        next = replaceCurrent(node, AddBeforeCurrent, next, node4);
                                                                                    }
                                                                                    break;
                                                                                case 155:
                                                                                    node4 = new Node(130);
                                                                                    firstChild4 = next.getFirstChild();
                                                                                    while (firstChild4 != null) {
                                                                                        Node next5 = firstChild4.getNext();
                                                                                        if (firstChild4.getType() == 39) {
                                                                                            if (!firstChild4.hasChildren()) {
                                                                                                Node firstChild8 = firstChild4.getFirstChild();
                                                                                                firstChild4.removeChild(firstChild8);
                                                                                                firstChild4.setType(49);
                                                                                                if (type == 155) {
                                                                                                    i2 = 156;
                                                                                                } else {
                                                                                                    i2 = 8;
                                                                                                }
                                                                                                firstChild4 = new Node(i2, firstChild4, firstChild8);
                                                                                            }
                                                                                            firstChild4 = next5;
                                                                                        } else if (firstChild4.getType() != 159) {
                                                                                            throw Kit.codeBug();
                                                                                        }
                                                                                        node4.addChildToBack(new Node(134, firstChild4, next.getLineno()));
                                                                                        firstChild4 = next5;
                                                                                    }
                                                                                    next = replaceCurrent(node, AddBeforeCurrent, next, node4);
                                                                                    break;
                                                                            }
                                                                            break;
                                                                    }
                                                                    break;
                                                            }
                                                            break;
                                                    }
                                                }
                                            } else if (next.getFirstChild().getType() == 154) {
                                                if (scriptNode.getType() == 110 || ((FunctionNode) scriptNode).requiresActivation()) {
                                                    z3 = true;
                                                } else {
                                                    z3 = false;
                                                }
                                                next = visitLet(z3, node, AddBeforeCurrent, next);
                                            } else {
                                                node4 = new Node(130);
                                                firstChild4 = next.getFirstChild();
                                                while (firstChild4 != null) {
                                                    Node next6 = firstChild4.getNext();
                                                    if (firstChild4.getType() == 39) {
                                                        if (!firstChild4.hasChildren()) {
                                                            Node firstChild9 = firstChild4.getFirstChild();
                                                            firstChild4.removeChild(firstChild9);
                                                            firstChild4.setType(49);
                                                            if (type == 155) {
                                                                i2 = 156;
                                                            } else {
                                                                i2 = 8;
                                                            }
                                                            firstChild4 = new Node(i2, firstChild4, firstChild9);
                                                        }
                                                        firstChild4 = next6;
                                                    } else if (firstChild4.getType() != 159) {
                                                        throw Kit.codeBug();
                                                    }
                                                    node4.addChildToBack(new Node(134, firstChild4, next.getLineno()));
                                                    firstChild4 = next6;
                                                }
                                                next = replaceCurrent(node, AddBeforeCurrent, next, node4);
                                            }
                                        } else {
                                            Scope definingScope2 = scope.getDefiningScope(next.getString());
                                            if (definingScope2 != null) {
                                                next.setScope(definingScope2);
                                            }
                                        }
                                    } else {
                                        this.loops.push(next);
                                        this.loopEnds.push(((Jump) next).target);
                                    }
                                } else {
                                    Node node12 = ((Jump) next).getFinally();
                                    if (node12 != null) {
                                        this.hasFinally = true;
                                        this.loops.push(next);
                                        this.loopEnds.push(node12);
                                    }
                                }
                            } else {
                                ((FunctionNode) scriptNode).addResumptionPoint(next);
                            }
                        }
                    } else if (z2) {
                        next.setType(74);
                    }
                    if (!z) {
                        if (type == 39) {
                            firstChild3 = next;
                        } else {
                            firstChild3 = next.getFirstChild();
                            if (firstChild3.getType() != 49) {
                                if (type != 31) {
                                    throw Kit.codeBug();
                                }
                            }
                        }
                        if (firstChild3.getScope() == null && (definingScope = scope.getDefiningScope(firstChild3.getString())) != null) {
                            firstChild3.setScope(definingScope);
                            if (type == 39) {
                                next.setType(55);
                            } else if (type == 8 || type == 74) {
                                next.setType(56);
                                firstChild3.setType(41);
                            } else if (type == 156) {
                                next.setType(157);
                                firstChild3.setType(41);
                            } else if (type == 31) {
                                next = replaceCurrent(node, AddBeforeCurrent, next, new Node(44));
                            } else {
                                throw Kit.codeBug();
                            }
                        }
                    }
                }
                nodeReplaceCurrent = next;
                if (nodeReplaceCurrent instanceof Scope) {
                    scope2 = (Scope) nodeReplaceCurrent;
                } else {
                    scope2 = scope;
                }
                transformCompilationUnit_r(scriptNode, nodeReplaceCurrent, scope2, z, z2);
            } else {
                if (!this.loopEnds.isEmpty()) {
                    this.loopEnds.pop();
                    this.loops.pop();
                }
                nodeReplaceCurrent = next;
                if (nodeReplaceCurrent instanceof Scope) {
                    scope2 = (Scope) nodeReplaceCurrent;
                } else {
                    scope2 = scope;
                }
                transformCompilationUnit_r(scriptNode, nodeReplaceCurrent, scope2, z, z2);
            }
            node5 = nodeReplaceCurrent;
            map = null;
        }
    }

    protected Node visitLet(boolean z, Node node, Node node2, Node node3) {
        Node nodeReplaceCurrent;
        Node node4;
        Node firstChild;
        Node node5;
        Node firstChild2;
        Node firstChild3 = node3.getFirstChild();
        Node next = firstChild3.getNext();
        node3.removeChild(firstChild3);
        node3.removeChild(next);
        int i = 159;
        boolean z2 = node3.getType() == 159;
        int i2 = 154;
        int i3 = 90;
        if (z) {
            nodeReplaceCurrent = replaceCurrent(node, node2, node3, new Node(z2 ? 160 : 130));
            ArrayList arrayList = new ArrayList();
            Node node6 = new Node(67);
            Node firstChild4 = firstChild3.getFirstChild();
            while (firstChild4 != null) {
                if (firstChild4.getType() == i) {
                    List list = (List) firstChild4.getProp(22);
                    Node firstChild5 = firstChild4.getFirstChild();
                    if (firstChild5.getType() != i2) {
                        throw Kit.codeBug();
                    }
                    if (z2) {
                        node5 = new Node(i3, firstChild5.getNext(), next);
                    } else {
                        node5 = new Node(130, new Node(134, firstChild5.getNext()), next);
                    }
                    if (list != null) {
                        arrayList.addAll(list);
                        for (int i4 = 0; i4 < list.size(); i4++) {
                            node6.addChildToBack(new Node(127, Node.newNumber(0.0d)));
                        }
                    }
                    firstChild2 = firstChild5.getFirstChild();
                } else {
                    node5 = next;
                    firstChild2 = firstChild4;
                }
                if (firstChild2.getType() != 39) {
                    throw Kit.codeBug();
                }
                arrayList.add(ScriptRuntime.getIndexObject(firstChild2.getString()));
                Node firstChild6 = firstChild2.getFirstChild();
                if (firstChild6 == null) {
                    firstChild6 = new Node(127, Node.newNumber(0.0d));
                }
                node6.addChildToBack(firstChild6);
                firstChild4 = firstChild4.getNext();
                next = node5;
                i = 159;
                i2 = 154;
                i3 = 90;
            }
            node6.putProp(12, arrayList.toArray());
            nodeReplaceCurrent.addChildToBack(new Node(2, node6));
            nodeReplaceCurrent.addChildToBack(new Node(124, next));
            nodeReplaceCurrent.addChildToBack(new Node(3));
        } else {
            nodeReplaceCurrent = replaceCurrent(node, node2, node3, new Node(z2 ? 90 : 130));
            Node node7 = new Node(90);
            Node firstChild7 = firstChild3.getFirstChild();
            while (firstChild7 != null) {
                if (firstChild7.getType() == 159) {
                    Node firstChild8 = firstChild7.getFirstChild();
                    if (firstChild8.getType() != 154) {
                        throw Kit.codeBug();
                    }
                    if (z2) {
                        node4 = new Node(90, firstChild8.getNext(), next);
                    } else {
                        node4 = new Node(130, new Node(134, firstChild8.getNext()), next);
                    }
                    Scope.joinScopes((Scope) firstChild7, (Scope) node3);
                    firstChild = firstChild8.getFirstChild();
                } else {
                    node4 = next;
                    firstChild = firstChild7;
                }
                if (firstChild.getType() != 39) {
                    throw Kit.codeBug();
                }
                Node nodeNewString = Node.newString(firstChild.getString());
                nodeNewString.setScope((Scope) node3);
                Node firstChild9 = firstChild.getFirstChild();
                if (firstChild9 == null) {
                    firstChild9 = new Node(127, Node.newNumber(0.0d));
                }
                node7.addChildToBack(new Node(56, nodeNewString, firstChild9));
                firstChild7 = firstChild7.getNext();
                next = node4;
            }
            if (z2) {
                nodeReplaceCurrent.addChildToBack(node7);
                node3.setType(90);
                nodeReplaceCurrent.addChildToBack(node3);
                node3.addChildToBack(next);
                if (next instanceof Scope) {
                    Scope scope = (Scope) next;
                    Scope parentScope = scope.getParentScope();
                    Scope scope2 = (Scope) node3;
                    scope.setParentScope(scope2);
                    scope2.setParentScope(parentScope);
                }
            } else {
                nodeReplaceCurrent.addChildToBack(new Node(134, node7));
                node3.setType(130);
                nodeReplaceCurrent.addChildToBack(node3);
                node3.addChildrenToBack(next);
                if (next instanceof Scope) {
                    Scope scope3 = (Scope) next;
                    Scope parentScope2 = scope3.getParentScope();
                    Scope scope4 = (Scope) node3;
                    scope3.setParentScope(scope4);
                    scope4.setParentScope(parentScope2);
                }
            }
        }
        return nodeReplaceCurrent;
    }

    private static Node addBeforeCurrent(Node node, Node node2, Node node3, Node node4) {
        if (node2 == null) {
            if (node3 != node.getFirstChild()) {
                Kit.codeBug();
            }
            node.addChildToFront(node4);
        } else {
            if (node3 != node2.getNext()) {
                Kit.codeBug();
            }
            node.addChildAfter(node4, node2);
        }
        return node4;
    }

    private static Node replaceCurrent(Node node, Node node2, Node node3, Node node4) {
        if (node2 == null) {
            if (node3 != node.getFirstChild()) {
                Kit.codeBug();
            }
            node.replaceChild(node3, node4);
        } else if (node2.next == node3) {
            node.replaceChildAfter(node2, node4);
        } else {
            node.replaceChild(node3, node4);
        }
        return node4;
    }
}
