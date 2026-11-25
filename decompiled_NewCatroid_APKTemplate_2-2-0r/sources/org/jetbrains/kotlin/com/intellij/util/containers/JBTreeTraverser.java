package org.jetbrains.kotlin.com.intellij.util.containers;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import net.bytebuddy.implementation.auxiliary.TypeProxy;
import org.jetbrains.kotlin.com.intellij.util.Function;

/* JADX INFO: loaded from: classes3.dex */
public class JBTreeTraverser<T> extends FilteredTraverserBase<T, JBTreeTraverser<T>> {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 5 || i == 7) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 5 || i == 7) ? 2 : 3];
        switch (i) {
            case 2:
                objArr[0] = "meta";
                break;
            case 3:
            case 6:
                objArr[0] = "function";
                break;
            case 4:
                objArr[0] = "reverse";
                break;
            case 5:
            case 7:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/util/containers/JBTreeTraverser";
                break;
            default:
                objArr[0] = "treeStructure";
                break;
        }
        if (i == 5 || i == 7) {
            objArr[1] = "map";
        } else {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/util/containers/JBTreeTraverser";
        }
        switch (i) {
            case 1:
                objArr[2] = "of";
                break;
            case 2:
                objArr[2] = TypeProxy.SilentConstruction.Appender.NEW_INSTANCE_METHOD_NAME;
                break;
            case 3:
            case 4:
            case 6:
                objArr[2] = "map";
                break;
            case 5:
            case 7:
                break;
            default:
                objArr[2] = TypedValues.TransitionType.S_FROM;
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 5 && i != 7) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    public static <T> JBTreeTraverser<T> from(Function<? super T, ? extends Iterable<? extends T>> function) {
        if (function == null) {
            $$$reportNull$$$0(0);
        }
        return new JBTreeTraverser<>(function);
    }

    public JBTreeTraverser(Function<? super T, ? extends Iterable<? extends T>> function) {
        super(FilteredTraverserBase.Meta.create(function));
    }

    protected JBTreeTraverser(FilteredTraverserBase.Meta<T> meta) {
        super(meta);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.com.intellij.util.containers.FilteredTraverserBase
    public JBTreeTraverser<T> newInstance(FilteredTraverserBase.Meta<T> meta) {
        if (meta == null) {
            $$$reportNull$$$0(2);
        }
        return meta == this.myMeta ? this : new JBTreeTraverser<>(meta);
    }
}
