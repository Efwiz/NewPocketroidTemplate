package org.jetbrains.kotlin.com.intellij.psi.tree;

import java.util.function.Supplier;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;

/* JADX INFO: loaded from: classes3.dex */
public class DefaultRoleFinder implements RoleFinder {
    private final Supplier<? extends IElementType[]> myComputable;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "parent", "org/jetbrains/kotlin/com/intellij/psi/tree/DefaultRoleFinder", "findChild"));
    }

    static /* synthetic */ IElementType[] lambda$new$0(IElementType[] iElementTypeArr) {
        return iElementTypeArr;
    }

    public DefaultRoleFinder(final IElementType... iElementTypeArr) {
        this.myComputable = new Supplier() { // from class: org.jetbrains.kotlin.com.intellij.psi.tree.DefaultRoleFinder$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final Object get() {
                return DefaultRoleFinder.lambda$new$0(iElementTypeArr);
            }
        };
    }

    public DefaultRoleFinder(Supplier<? extends IElementType[]> supplier) {
        this.myComputable = supplier;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.tree.RoleFinder
    public ASTNode findChild(ASTNode aSTNode) {
        if (aSTNode == null) {
            $$$reportNull$$$0(0);
        }
        for (ASTNode firstChildNode = aSTNode.getFirstChildNode(); firstChildNode != null; firstChildNode = firstChildNode.getTreeNext()) {
            for (IElementType iElementType : this.myComputable.get()) {
                if (firstChildNode.getElementType() == iElementType) {
                    return firstChildNode;
                }
            }
        }
        return null;
    }
}
