package com.android.tools.r8.shaking;

import com.android.tools.r8.experimental.graphinfo.ClassGraphNode;
import com.android.tools.r8.experimental.graphinfo.FieldGraphNode;
import com.android.tools.r8.experimental.graphinfo.GraphConsumer;
import com.android.tools.r8.experimental.graphinfo.GraphNode;
import com.android.tools.r8.experimental.graphinfo.KeepRuleGraphNode;
import com.android.tools.r8.experimental.graphinfo.MethodGraphNode;
import com.android.tools.r8.internal.C1349Sj;
import com.android.tools.r8.internal.UT$$ExternalSyntheticLambda5;
import com.android.tools.r8.origin.Origin;
import com.android.tools.r8.position.Position;
import com.android.tools.r8.position.TextPosition;
import com.android.tools.r8.position.TextRange;
import com.android.tools.r8.references.ClassReference;
import com.android.tools.r8.references.FieldReference;
import com.android.tools.r8.references.MethodReference;
import com.android.tools.r8.utils.C3970u0;
import com.android.tools.r8.utils.C3979v2;
import java.io.PrintStream;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class D4 extends C3798t {
    static final /* synthetic */ boolean c = true;

    public D4(GraphConsumer graphConsumer) {
        super(graphConsumer);
    }

    private static String d(GraphNode graphNode) {
        if (graphNode instanceof ClassGraphNode) {
            return C3970u0.b(((ClassGraphNode) graphNode).getReference().getDescriptor());
        }
        if (graphNode instanceof MethodGraphNode) {
            MethodReference reference = ((MethodGraphNode) graphNode).getReference();
            return (reference.getReturnType() == null ? PsiKeyword.VOID : reference.getReturnType().getTypeName()) + " " + reference.getHolderClass().getTypeName() + "." + reference.getMethodName() + com.android.tools.r8.utils.Y2.a(",", com.android.tools.r8.utils.V1.a((Collection) reference.getFormalTypes(), (Function) new UT$$ExternalSyntheticLambda5()), com.android.tools.r8.utils.Y2.a.PARENS);
        }
        if (graphNode instanceof FieldGraphNode) {
            FieldReference reference2 = ((FieldGraphNode) graphNode).getReference();
            return reference2.getFieldType().getTypeName() + " " + reference2.getHolderClass().getTypeName() + "." + reference2.getFieldName();
        }
        if (graphNode instanceof KeepRuleGraphNode) {
            KeepRuleGraphNode keepRuleGraphNode = (KeepRuleGraphNode) graphNode;
            if (keepRuleGraphNode.getOrigin() == Origin.unknown()) {
                return keepRuleGraphNode.getContent();
            }
            return keepRuleGraphNode.getOrigin() + ":" + a(keepRuleGraphNode.getPosition());
        }
        if (graphNode == GraphNode.cycle()) {
            return "only cyclic dependencies remain, failed to determine a path from a keep rule";
        }
        if (c) {
            return Objects.toString(graphNode);
        }
        throw new AssertionError("Unexpected graph node type: " + graphNode);
    }

    public void a(ClassReference classReference, PrintStream printStream) {
        ClassGraphNode classGraphNode;
        Iterator<GraphNode> it = a().iterator();
        while (true) {
            if (!it.hasNext()) {
                classGraphNode = null;
                break;
            }
            GraphNode next = it.next();
            if (next instanceof ClassGraphNode) {
                classGraphNode = (ClassGraphNode) next;
                if (classGraphNode.getReference() == classReference) {
                    break;
                }
            }
        }
        if (classGraphNode == null) {
            classGraphNode = new ClassGraphNode(false, classReference);
        }
        a(classGraphNode, printStream);
    }

    public void a(MethodReference methodReference, PrintStream printStream) {
        MethodGraphNode methodGraphNode;
        Iterator<GraphNode> it = a().iterator();
        while (true) {
            if (!it.hasNext()) {
                methodGraphNode = null;
                break;
            }
            GraphNode next = it.next();
            if (next instanceof MethodGraphNode) {
                methodGraphNode = (MethodGraphNode) next;
                if (methodGraphNode.getReference() == methodReference) {
                    break;
                }
            }
        }
        if (methodGraphNode == null) {
            methodGraphNode = new MethodGraphNode(false, methodReference);
        }
        a(methodGraphNode, printStream);
    }

    public void a(FieldReference fieldReference, PrintStream printStream) {
        FieldGraphNode fieldGraphNode;
        Iterator<GraphNode> it = a().iterator();
        while (true) {
            if (!it.hasNext()) {
                fieldGraphNode = null;
                break;
            }
            GraphNode next = it.next();
            if (next instanceof FieldGraphNode) {
                fieldGraphNode = (FieldGraphNode) next;
                if (fieldGraphNode.getReference() == fieldReference) {
                    break;
                }
            }
        }
        if (fieldGraphNode == null) {
            fieldGraphNode = new FieldGraphNode(false, fieldReference);
        }
        a(fieldGraphNode, printStream);
    }

    public final void a(GraphNode graphNode, PrintStream printStream) {
        B4 b4 = new B4(printStream);
        ArrayList arrayListA = null;
        C4 c4 = null;
        if (graphNode != null) {
            IdentityHashMap identityHashMap = new IdentityHashMap();
            ArrayDeque arrayDeque = new ArrayDeque();
            GraphNode graphNode2 = graphNode;
            while (true) {
                Map<GraphNode, Set<C1349Sj>> mapA = a(graphNode2);
                if (mapA == null) {
                    arrayListA = a(graphNode, c4);
                    break;
                }
                if (!c && mapA.isEmpty()) {
                    throw new AssertionError();
                }
                for (GraphNode graphNode3 : mapA.keySet()) {
                    if (!identityHashMap.containsKey(graphNode3)) {
                        identityHashMap.put(graphNode3, graphNode3);
                        arrayDeque.addLast(new C4(graphNode3, c4));
                    }
                }
                if (arrayDeque.isEmpty()) {
                    arrayListA = a(graphNode, new C4(GraphNode.cycle(), c4));
                    break;
                } else {
                    c4 = (C4) arrayDeque.removeFirst();
                    graphNode2 = c4.a;
                }
            }
        }
        if (arrayListA == null) {
            printStream.print("Nothing is keeping ");
            printStream.println(d(graphNode));
            return;
        }
        String strD = d(graphNode);
        b4.b++;
        b4.a();
        b4.a.println(strD);
        for (int size = arrayListA.size() - 1; size >= 0; size--) {
            C3979v2 c3979v2 = (C3979v2) arrayListA.get(size);
            a((GraphNode) c3979v2.a(), (C1349Sj) c3979v2.b(), b4);
        }
        b4.b--;
    }

    private ArrayList a(GraphNode graphNode, C4 c4) {
        C1349Sj c1349SjC;
        if (c4 == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        while (true) {
            int i = 0;
            if (c4.b != null) {
                GraphNode graphNode2 = c4.a;
                if (graphNode2.isCycle()) {
                    arrayList.add(new C3979v2(graphNode2, new C1349Sj(C1349Sj.a.Unknown)));
                } else {
                    Set<C1349Sj> set = a(c4.b.a).get(graphNode2);
                    C1349Sj.a[] aVarArrValues = C1349Sj.a.values();
                    int length = aVarArrValues.length;
                    while (true) {
                        if (i < length) {
                            C1349Sj.a aVar = aVarArrValues[i];
                            Iterator<C1349Sj> it = set.iterator();
                            while (it.hasNext()) {
                                c1349SjC = it.next();
                                if (c1349SjC.a() == aVar) {
                                    break;
                                }
                            }
                            i++;
                        } else {
                            if (c) {
                                c1349SjC = C1349Sj.c();
                                break;
                            }
                            throw new AssertionError("Unexpected empty set of graph edge info");
                        }
                    }
                    arrayList.add(new C3979v2(graphNode2, c1349SjC));
                }
                c4 = c4.b;
            } else {
                Set<C1349Sj> set2 = a(graphNode).get(c4.a);
                GraphNode graphNode3 = c4.a;
                C1349Sj.a[] aVarArrValues2 = C1349Sj.a.values();
                int length2 = aVarArrValues2.length;
                while (i < length2) {
                    C1349Sj.a aVar2 = aVarArrValues2[i];
                    for (C1349Sj c1349SjC2 : set2) {
                        if (c1349SjC2.a() == aVar2) {
                            arrayList.add(new C3979v2(graphNode3, c1349SjC2));
                            return arrayList;
                        }
                    }
                    i++;
                }
                if (c) {
                    c1349SjC2 = C1349Sj.c();
                    arrayList.add(new C3979v2(graphNode3, c1349SjC2));
                    return arrayList;
                }
                throw new AssertionError("Unexpected empty set of graph edge info");
            }
        }
    }

    private static void a(GraphNode graphNode, C1349Sj c1349Sj, B4 b4) {
        b4.a("is " + c1349Sj.b() + ":");
        for (String str : com.android.tools.r8.utils.Y2.f(d(graphNode))) {
            b4.a();
            b4.a.print("|  ");
            b4.a.println(str);
        }
    }

    private static String a(Position position) {
        if (position instanceof TextRange) {
            TextPosition start = ((TextRange) position).getStart();
            return start.getLine() + ":" + start.getColumn();
        }
        return position.getDescription();
    }
}
