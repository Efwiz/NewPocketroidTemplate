package org.jetbrains.kotlin.com.intellij.psi.impl.smartPointers;

import java.lang.ref.Reference;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.SmartTypePointer;
import org.jetbrains.kotlin.com.intellij.reference.SoftReference;

/* JADX INFO: loaded from: classes3.dex */
public abstract class TypePointerBase<T extends PsiType> implements SmartTypePointer {
    private Reference<T> myTypeRef;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", ModuleXmlParser.TYPE, "org/jetbrains/kotlin/com/intellij/psi/impl/smartPointers/TypePointerBase", "<init>"));
    }

    protected abstract T calcType();

    public TypePointerBase(T t) {
        if (t == null) {
            $$$reportNull$$$0(0);
        }
        this.myTypeRef = new SoftReference(t);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.SmartTypePointer
    public T getType() {
        T t = (T) SoftReference.dereference(this.myTypeRef);
        if (t != null && t.isValid()) {
            return t;
        }
        T t2 = (T) calcType();
        this.myTypeRef = t2 == null ? null : new SoftReference(t2);
        return t2;
    }
}
