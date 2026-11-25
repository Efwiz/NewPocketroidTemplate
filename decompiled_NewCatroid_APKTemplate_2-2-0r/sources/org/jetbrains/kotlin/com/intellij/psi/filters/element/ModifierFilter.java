package org.jetbrains.kotlin.com.intellij.psi.filters.element;

import com.huawei.hms.framework.common.ContainerUtils;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiModifierList;
import org.jetbrains.kotlin.com.intellij.psi.PsiModifierListOwner;
import org.jetbrains.kotlin.com.intellij.psi.filters.ClassFilter;
import org.jetbrains.kotlin.com.intellij.util.Function;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;

/* JADX INFO: loaded from: classes3.dex */
public class ModifierFilter extends ClassFilter {
    public final List<ModifierRestriction> myModifierRestrictions;

    public ModifierFilter(String str, boolean z) {
        this((List<ModifierRestriction>) Collections.singletonList(new ModifierRestriction(str, z)));
    }

    public ModifierFilter(String... strArr) {
        this((List<ModifierRestriction>) ContainerUtil.map(strArr, new Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.filters.element.ModifierFilter$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.com.intellij.util.Function
            public final Object fun(Object obj) {
                return ModifierFilter.lambda$new$0((String) obj);
            }
        }));
    }

    static /* synthetic */ ModifierRestriction lambda$new$0(String str) {
        return new ModifierRestriction(str, true);
    }

    private ModifierFilter(List<ModifierRestriction> list) {
        super(PsiModifierListOwner.class);
        this.myModifierRestrictions = list;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.filters.ClassFilter, org.jetbrains.kotlin.com.intellij.psi.filters.ElementFilter
    public boolean isAcceptable(Object obj, PsiElement psiElement) {
        if (!(obj instanceof PsiModifierListOwner)) {
            return false;
        }
        PsiModifierList psiModifierListMo6095getModifierList = ((PsiModifierListOwner) obj).mo6095getModifierList();
        if (psiModifierListMo6095getModifierList == null) {
            return true;
        }
        for (ModifierRestriction modifierRestriction : this.myModifierRestrictions) {
            if (modifierRestriction.isSet != psiModifierListMo6095getModifierList.hasModifierProperty(modifierRestriction.modifierName)) {
                return false;
            }
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class ModifierRestriction {
        public final boolean isSet;
        public final String modifierName;

        ModifierRestriction(String str, boolean z) {
            this.modifierName = str;
            this.isSet = z;
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.filters.ClassFilter, org.jetbrains.kotlin.com.intellij.psi.filters.ElementFilter
    public String toString() {
        StringBuilder sb = new StringBuilder("modifiers(");
        Iterator<ModifierRestriction> it = this.myModifierRestrictions.iterator();
        while (it.hasNext()) {
            ModifierRestriction next = it.next();
            sb.append(next.modifierName).append(ContainerUtils.KEY_VALUE_DELIMITER).append(next.isSet);
            if (it.hasNext()) {
                sb.append(", ");
            }
        }
        sb.append(Constant.AFTER_QUTO);
        return sb.toString();
    }
}
