package org.jetbrains.kotlin.com.intellij.lang;

import java.util.List;
import org.jetbrains.kotlin.com.intellij.util.CharTable;

/* JADX INFO: loaded from: classes3.dex */
public abstract class LighterAST {
    private final CharTable myCharTable;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 1 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 1 ? 3 : 2];
        if (i != 1) {
            objArr[0] = "charTable";
        } else {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/lang/LighterAST";
        }
        if (i != 1) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/lang/LighterAST";
        } else {
            objArr[1] = "getCharTable";
        }
        if (i != 1) {
            objArr[2] = "<init>";
        }
        String str2 = String.format(str, objArr);
        if (i == 1) {
            throw new IllegalStateException(str2);
        }
    }

    public abstract List<LighterASTNode> getChildren(LighterASTNode lighterASTNode);

    public abstract LighterASTNode getParent(LighterASTNode lighterASTNode);

    public abstract LighterASTNode getRoot();

    public LighterAST(CharTable charTable) {
        if (charTable == null) {
            $$$reportNull$$$0(0);
        }
        this.myCharTable = charTable;
    }

    public CharTable getCharTable() {
        CharTable charTable = this.myCharTable;
        if (charTable == null) {
            $$$reportNull$$$0(1);
        }
        return charTable;
    }
}
