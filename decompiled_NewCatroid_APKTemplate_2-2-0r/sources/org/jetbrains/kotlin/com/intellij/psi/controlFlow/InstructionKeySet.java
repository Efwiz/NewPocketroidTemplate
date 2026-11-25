package org.jetbrains.kotlin.com.intellij.psi.controlFlow;

import com.google.android.gms.analytics.ecommerce.ProductAction;
import java.util.Arrays;
import org.antlr.stringtemplate.language.ASTExpr;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;

/* JADX INFO: loaded from: classes3.dex */
final class InstructionKeySet {
    private final Node myRoot;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        objArr[0] = ASTExpr.DEFAULT_MAP_KEY_NAME;
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/controlFlow/InstructionKeySet";
        if (i != 1) {
            objArr[2] = ProductAction.ACTION_ADD;
        } else {
            objArr[2] = "contains";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    InstructionKeySet(int i) {
        this.myRoot = new Node(i);
    }

    void add(InstructionKey instructionKey) {
        if (instructionKey == null) {
            $$$reportNull$$$0(0);
        }
        this.myRoot.add(instructionKey.getOffset(), instructionKey.getCallStack(), 0);
    }

    boolean contains(InstructionKey instructionKey) {
        if (instructionKey == null) {
            $$$reportNull$$$0(1);
        }
        return this.myRoot.contains(instructionKey.getOffset(), instructionKey.getCallStack(), 0);
    }

    public String toString() {
        return this.myRoot.toString();
    }

    private static final class Node extends Int2ObjectOpenHashMap<Node> {
        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            Object[] objArr = new Object[3];
            objArr[0] = "stack";
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/controlFlow/InstructionKeySet$Node";
            if (i != 1) {
                objArr[2] = ProductAction.ACTION_ADD;
            } else {
                objArr[2] = "contains";
            }
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        private Node(int i) {
            super(Math.max(i, 2));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void add(int i, int[] iArr, int i2) {
            if (iArr == null) {
                $$$reportNull$$$0(0);
            }
            if (i2 < iArr.length) {
                Node node = get(i);
                if (node == null) {
                    node = new Node(4);
                    put(i, node);
                }
                node.add(iArr[i2], iArr, i2 + 1);
                return;
            }
            if (containsKey(i)) {
                return;
            }
            put(i, (Object) null);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean contains(int i, int[] iArr, int i2) {
            Node node;
            if (iArr == null) {
                $$$reportNull$$$0(1);
            }
            if (i2 < iArr.length && (node = get(i)) != null) {
                return node.contains(iArr[i2], iArr, i2 + 1);
            }
            return containsKey(i);
        }

        /* JADX WARN: Type inference failed for: r1v1, types: [org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntSet] */
        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.AbstractInt2ObjectMap
        public String toString() {
            StringBuilder sb = new StringBuilder("{");
            int[] intArray = keySet().toIntArray();
            Arrays.sort(intArray);
            for (int i : intArray) {
                if (sb.length() > 1) {
                    sb.append(", ");
                }
                sb.append(i);
                Node node = get(i);
                if (node != null) {
                    sb.append(node);
                }
            }
            return sb.append("}").toString();
        }
    }
}
