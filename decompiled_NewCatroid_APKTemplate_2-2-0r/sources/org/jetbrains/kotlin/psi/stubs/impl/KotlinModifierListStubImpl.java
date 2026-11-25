package org.jetbrains.kotlin.psi.stubs.impl;

import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;
import org.jetbrains.kotlin.lexer.KtModifierKeywordToken;
import org.jetbrains.kotlin.psi.KtDeclarationModifierList;
import org.jetbrains.kotlin.psi.stubs.KotlinModifierListStub;
import org.jetbrains.kotlin.psi.stubs.elements.KtModifierListElementType;

/* JADX INFO: loaded from: classes6.dex */
public class KotlinModifierListStubImpl extends KotlinStubBaseImpl<KtDeclarationModifierList> implements KotlinModifierListStub {
    private final long mask;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 2 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 2 ? 3 : 2];
        if (i == 1) {
            objArr[0] = "modifierToken";
        } else if (i != 2) {
            objArr[0] = "elementType";
        } else {
            objArr[0] = "org/jetbrains/kotlin/psi/stubs/impl/KotlinModifierListStubImpl";
        }
        if (i != 2) {
            objArr[1] = "org/jetbrains/kotlin/psi/stubs/impl/KotlinModifierListStubImpl";
        } else {
            objArr[1] = "toString";
        }
        if (i == 1) {
            objArr[2] = "hasModifier";
        } else if (i != 2) {
            objArr[2] = "<init>";
        }
        String str2 = String.format(str, objArr);
        if (i == 2) {
            throw new IllegalStateException(str2);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KotlinModifierListStubImpl(StubElement stubElement, long j, KtModifierListElementType<?> ktModifierListElementType) {
        super(stubElement, ktModifierListElementType);
        if (ktModifierListElementType == null) {
            $$$reportNull$$$0(0);
        }
        this.mask = j;
    }

    public long getMask() {
        return this.mask;
    }

    @Override // org.jetbrains.kotlin.psi.stubs.KotlinModifierListStub
    public boolean hasModifier(KtModifierKeywordToken ktModifierKeywordToken) {
        if (ktModifierKeywordToken == null) {
            $$$reportNull$$$0(1);
        }
        return ModifierMaskUtils.maskHasModifier(this.mask, ktModifierKeywordToken);
    }

    @Override // org.jetbrains.kotlin.psi.stubs.impl.KotlinStubBaseImpl, org.jetbrains.kotlin.com.intellij.psi.stubs.StubBase, java.util.concurrent.atomic.AtomicReference
    public String toString() {
        String str = super.toString() + ModifierMaskUtils.maskToString(this.mask);
        if (str == null) {
            $$$reportNull$$$0(2);
        }
        return str;
    }
}
