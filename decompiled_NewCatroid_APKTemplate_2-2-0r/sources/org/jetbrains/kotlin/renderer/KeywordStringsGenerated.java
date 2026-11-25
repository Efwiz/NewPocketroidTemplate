package org.jetbrains.kotlin.renderer;

import com.huawei.hms.ml.language.common.utils.LanguageCodeUtil;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;

/* JADX INFO: loaded from: classes6.dex */
public class KeywordStringsGenerated {
    public static final Set<String> KEYWORDS = new HashSet(Arrays.asList("package", "as", "typealias", "class", "this", PsiKeyword.SUPER, "val", PsiKeyword.VAR, "fun", PsiKeyword.FOR, "null", "true", "false", LanguageCodeUtil.IS, "in", PsiKeyword.THROW, "return", PsiKeyword.BREAK, PsiKeyword.CONTINUE, "object", PsiKeyword.IF, PsiKeyword.TRY, PsiKeyword.ELSE, PsiKeyword.WHILE, PsiKeyword.DO, "when", PsiKeyword.INTERFACE, "typeof"));

    private KeywordStringsGenerated() {
    }
}
