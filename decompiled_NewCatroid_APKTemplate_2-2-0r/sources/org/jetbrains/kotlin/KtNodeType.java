package org.jetbrains.kotlin;

import java.lang.reflect.Constructor;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.idea.KotlinLanguage;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtElementImpl;

/* JADX INFO: loaded from: classes14.dex */
public class KtNodeType extends IElementType {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final Constructor<? extends KtElement> myPsiFactory;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "debugName", "org/jetbrains/kotlin/KtNodeType", "<init>"));
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtNodeType(String str, Class<? extends KtElement> cls) {
        Constructor<? extends KtElement> constructor;
        super(str, KotlinLanguage.INSTANCE);
        if (str == null) {
            $$$reportNull$$$0(0);
        }
        if (cls != null) {
            try {
                constructor = cls.getConstructor(ASTNode.class);
            } catch (NoSuchMethodException unused) {
                throw new RuntimeException("Must have a constructor with ASTNode");
            }
        } else {
            constructor = null;
        }
        this.myPsiFactory = constructor;
    }

    public KtElement createPsi(ASTNode aSTNode) {
        try {
            Constructor<? extends KtElement> constructor = this.myPsiFactory;
            if (constructor == null) {
                return new KtElementImpl(aSTNode);
            }
            return constructor.newInstance(aSTNode);
        } catch (Exception e) {
            throw new RuntimeException("Error creating psi element for node", e);
        }
    }

    public static class KtLeftBoundNodeType extends KtNodeType {
        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "debugName", "org/jetbrains/kotlin/KtNodeType$KtLeftBoundNodeType", "<init>"));
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.tree.IElementType
        public boolean isLeftBound() {
            return true;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public KtLeftBoundNodeType(String str, Class<? extends KtElement> cls) {
            super(str, cls);
            if (str == null) {
                $$$reportNull$$$0(0);
            }
        }
    }
}
