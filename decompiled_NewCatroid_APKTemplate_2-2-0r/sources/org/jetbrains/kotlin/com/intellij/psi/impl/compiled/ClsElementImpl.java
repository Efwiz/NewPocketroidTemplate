package org.jetbrains.kotlin.com.intellij.psi.impl.compiled;

import com.google.android.gms.analytics.ecommerce.ProductAction;
import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import io.netty.util.internal.StringUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import name.antonsmirnov.firmata.FormatHelper;
import net.bytebuddy.description.type.TypeDescription;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.core.JavaPsiBundle;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.lang.Language;
import org.jetbrains.kotlin.com.intellij.lang.java.JavaLanguage;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.util.Key;
import org.jetbrains.kotlin.com.intellij.openapi.util.Pair;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiCompiledElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiInvalidElementAccessException;
import org.jetbrains.kotlin.com.intellij.psi.PsiManager;
import org.jetbrains.kotlin.com.intellij.psi.PsiReference;
import org.jetbrains.kotlin.com.intellij.psi.codeStyle.JavaFileCodeStyleFacade;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase;
import org.jetbrains.kotlin.com.intellij.psi.impl.smartPointers.Identikit;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.SourceTreeToPsiMap;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtilCore;
import org.jetbrains.kotlin.com.intellij.util.ArrayUtilRt;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;

/* JADX INFO: loaded from: classes3.dex */
public abstract class ClsElementImpl extends PsiElementBase implements PsiCompiledElement {
    public static final Key<PsiCompiledElement> COMPILED_ELEMENT = Key.create("COMPILED_ELEMENT");
    protected static final String NEXT_LINE = "go_to_next_line_and_indent";
    private volatile Pair<TextRange, Identikit.ByType> myMirror;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        switch (i) {
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
            case 9:
            default:
                str = "@NotNull method %s.%s must not return null";
                break;
        }
        switch (i) {
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
                i2 = 3;
                break;
            case 9:
            default:
                i2 = 2;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 15:
                objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
                break;
            case 8:
                objArr[0] = "newElement";
                break;
            case 9:
            default:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/compiled/ClsElementImpl";
                break;
            case 10:
                objArr[0] = "text";
                break;
            case 11:
            case 12:
            case 13:
                objArr[0] = "buffer";
                break;
            case 14:
                objArr[0] = "separator";
                break;
            case 16:
            case 18:
                objArr[0] = "stubs";
                break;
            case 17:
            case 19:
                objArr[0] = "mirrors";
                break;
        }
        switch (i) {
            case 1:
            case 2:
                objArr[1] = "getChildren";
                break;
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/compiled/ClsElementImpl";
                break;
            case 9:
                objArr[1] = "textToCharArray";
                break;
            default:
                objArr[1] = "getLanguage";
                break;
        }
        switch (i) {
            case 3:
                objArr[2] = "checkAdd";
                break;
            case 4:
                objArr[2] = "cannotModifyException";
                break;
            case 5:
                objArr[2] = ProductAction.ACTION_ADD;
                break;
            case 6:
                objArr[2] = "addBefore";
                break;
            case 7:
                objArr[2] = "addAfter";
                break;
            case 8:
                objArr[2] = SchemaSymbols.ATTVAL_REPLACE;
                break;
            case 10:
                objArr[2] = "textMatches";
                break;
            case 11:
                objArr[2] = "goNextLine";
                break;
            case 12:
            case 13:
            case 14:
                objArr[2] = "appendText";
                break;
            case 15:
                objArr[2] = "setMirrorCheckingType";
                break;
            case 16:
            case 17:
            case 18:
            case 19:
                objArr[2] = "setMirrors";
                break;
        }
        String str2 = String.format(str, objArr);
        switch (i) {
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
                throw new IllegalArgumentException(str2);
            case 9:
            default:
                throw new IllegalStateException(str2);
        }
    }

    public abstract void appendMirrorText(int i, StringBuilder sb);

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement copy() {
        return this;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public ASTNode getNode() {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    /* JADX INFO: renamed from: isPhysical */
    public boolean getIsPhysical() {
        return true;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public final boolean isWritable() {
        return false;
    }

    public abstract void setMirror(TreeElement treeElement) throws InvalidMirrorException;

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public Language getLanguage() {
        JavaLanguage javaLanguage = JavaLanguage.INSTANCE;
        if (javaLanguage == null) {
            $$$reportNull$$$0(0);
        }
        return javaLanguage;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiManager getManager() {
        return getLightParent().getManager();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiFile getContainingFile() {
        PsiElement parent = getLightParent();
        if (parent == null) {
            throw new PsiInvalidElementAccessException(this);
        }
        return parent.getContainingFile();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement, org.jetbrains.kotlin.com.intellij.pom.PomTarget
    public boolean isValid() {
        PsiElement parent = getLightParent();
        return parent != null && parent.isValid();
    }

    protected PsiElement[] getChildren(PsiElement... psiElementArr) {
        if (psiElementArr == null) {
            PsiElement[] psiElementArr2 = PsiElement.EMPTY_ARRAY;
            if (psiElementArr2 == null) {
                $$$reportNull$$$0(1);
            }
            return psiElementArr2;
        }
        ArrayList arrayList = new ArrayList(psiElementArr.length);
        for (PsiElement psiElement : psiElementArr) {
            if (psiElement != null) {
                arrayList.add(psiElement);
            }
        }
        PsiElement[] psiElementArray = PsiUtilCore.toPsiElementArray(arrayList);
        if (psiElementArray == null) {
            $$$reportNull$$$0(2);
        }
        return psiElementArray;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void checkAdd(PsiElement psiElement) throws IncorrectOperationException {
        if (psiElement == null) {
            $$$reportNull$$$0(3);
        }
        throw cannotModifyException(this);
    }

    static IncorrectOperationException cannotModifyException(PsiCompiledElement psiCompiledElement) {
        if (psiCompiledElement == null) {
            $$$reportNull$$$0(4);
        }
        VirtualFile virtualFile = PsiUtilCore.getVirtualFile(psiCompiledElement);
        return new IncorrectOperationException(JavaPsiBundle.message("psi.error.attempt.to.edit.class.file", virtualFile == null ? TypeDescription.Generic.OfWildcardType.SYMBOL : virtualFile.getPresentableUrl()));
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement add(PsiElement psiElement) throws IncorrectOperationException {
        if (psiElement == null) {
            $$$reportNull$$$0(5);
        }
        throw cannotModifyException(this);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement addBefore(PsiElement psiElement, PsiElement psiElement2) throws IncorrectOperationException {
        if (psiElement == null) {
            $$$reportNull$$$0(6);
        }
        throw cannotModifyException(this);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement addAfter(PsiElement psiElement, PsiElement psiElement2) throws IncorrectOperationException {
        if (psiElement == null) {
            $$$reportNull$$$0(7);
        }
        throw cannotModifyException(this);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void delete() throws IncorrectOperationException {
        throw cannotModifyException(this);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void checkDelete() throws IncorrectOperationException {
        throw cannotModifyException(this);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement replace(PsiElement psiElement) throws IncorrectOperationException {
        if (psiElement == null) {
            $$$reportNull$$$0(8);
        }
        throw cannotModifyException(this);
    }

    protected int getIndentSize() {
        return JavaFileCodeStyleFacade.forContext(getContainingFile()).getIndentSize();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiCompiledElement
    public PsiElement getMirror() {
        PsiFile containingFile = ((ClsFileImpl) getContainingFile()).getMirror().getContainingFile();
        Pair<TextRange, Identikit.ByType> pair = this.myMirror;
        if (pair == null) {
            return null;
        }
        return pair.second.findPsiElement(containingFile, pair.first.getStartOffset(), pair.first.getEndOffset());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public final TextRange getTextRange() {
        PsiElement mirror = getMirror();
        return mirror != null ? mirror.getTextRange() : TextRange.EMPTY_RANGE;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public final int getStartOffsetInParent() {
        PsiElement mirror = getMirror();
        if (mirror != null) {
            return mirror.getStartOffsetInParent();
        }
        return -1;
    }

    public int getTextLength() {
        String text = getText();
        if (text == null) {
            return 0;
        }
        return text.length();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement findElementAt(int i) {
        PsiElement mirror = getMirror();
        if (mirror != null) {
            return mirror.findElementAt(i);
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiReference findReferenceAt(int i) {
        PsiElement mirror = getMirror();
        if (mirror != null) {
            return mirror.findReferenceAt(i);
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public final int getTextOffset() {
        PsiElement mirror = getMirror();
        if (mirror != null) {
            return mirror.getTextOffset();
        }
        return -1;
    }

    public String getText() {
        PsiElement mirror = getMirror();
        if (mirror != null) {
            return mirror.getText();
        }
        StringBuilder sb = new StringBuilder();
        appendMirrorText(0, sb);
        Logger.getInstance((Class<?>) ClsElementImpl.class).warn("Mirror wasn't set for " + this + " in " + getContainingFile() + ", expected text '" + ((Object) sb) + FormatHelper.QUOTE);
        return sb.toString();
    }

    public char[] textToCharArray() {
        PsiElement mirror = getMirror();
        char[] cArrTextToCharArray = mirror != null ? mirror.textToCharArray() : ArrayUtilRt.EMPTY_CHAR_ARRAY;
        if (cArrTextToCharArray == null) {
            $$$reportNull$$$0(9);
        }
        return cArrTextToCharArray;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public boolean textMatches(CharSequence charSequence) {
        if (charSequence == null) {
            $$$reportNull$$$0(10);
        }
        return getText().equals(charSequence.toString());
    }

    static void goNextLine(int i, StringBuilder sb) {
        if (sb == null) {
            $$$reportNull$$$0(11);
        }
        sb.append('\n');
        for (int i2 = 0; i2 < i; i2++) {
            sb.append(StringUtil.SPACE);
        }
    }

    protected static void appendText(PsiElement psiElement, int i, StringBuilder sb) {
        if (sb == null) {
            $$$reportNull$$$0(12);
        }
        if (psiElement == null) {
            return;
        }
        ((ClsElementImpl) psiElement).appendMirrorText(i, sb);
    }

    protected static void appendText(PsiElement psiElement, int i, StringBuilder sb, String str) {
        if (sb == null) {
            $$$reportNull$$$0(13);
        }
        if (str == null) {
            $$$reportNull$$$0(14);
        }
        if (psiElement == null) {
            return;
        }
        int length = sb.length();
        ((ClsElementImpl) psiElement).appendMirrorText(i, sb);
        if (sb.length() != length) {
            if (str == NEXT_LINE) {
                goNextLine(i, sb);
            } else {
                sb.append(str);
            }
        }
    }

    protected void setMirrorCheckingType(TreeElement treeElement, IElementType iElementType) throws InvalidMirrorException {
        if (treeElement == null) {
            $$$reportNull$$$0(15);
        }
        if (iElementType != null && treeElement.getElementType() != iElementType) {
            throw new InvalidMirrorException(treeElement.getElementType() + " != " + iElementType);
        }
        PsiElement psi = treeElement.getPsi();
        psi.putUserData(COMPILED_ELEMENT, this);
        this.myMirror = Pair.create(treeElement.getTextRange(), Identikit.fromPsi(psi, JavaLanguage.INSTANCE));
    }

    protected static <T extends PsiElement> void setMirror(T t, T t2) throws InvalidMirrorException {
        if (t == null || t2 == null) {
            throw new InvalidMirrorException(t, t2);
        }
        ((ClsElementImpl) t).setMirror(SourceTreeToPsiMap.psiToTreeNotNull(t2));
    }

    protected static <T extends PsiElement> void setMirrorIfPresent(T t, T t2) throws InvalidMirrorException {
        if ((t == null) != (t2 == null)) {
            throw new InvalidMirrorException(t, t2);
        }
        if (t != null) {
            ((ClsElementImpl) t).setMirror(SourceTreeToPsiMap.psiToTreeNotNull(t2));
        }
    }

    protected static <T extends PsiElement> void setMirrors(T[] tArr, T[] tArr2) throws InvalidMirrorException {
        if (tArr == null) {
            $$$reportNull$$$0(16);
        }
        if (tArr2 == null) {
            $$$reportNull$$$0(17);
        }
        setMirrors(Arrays.asList(tArr), Arrays.asList(tArr2));
    }

    protected static <T extends PsiElement> void setMirrors(List<? extends T> list, List<? extends T> list2) throws InvalidMirrorException {
        if (list == null) {
            $$$reportNull$$$0(18);
        }
        if (list2 == null) {
            $$$reportNull$$$0(19);
        }
        if (list.size() != list2.size()) {
            throw new InvalidMirrorException(list, list2);
        }
        for (int i = 0; i < list.size(); i++) {
            setMirror(list.get(i), list2.get(i));
        }
    }

    protected static class InvalidMirrorException extends RuntimeException {
        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            Object[] objArr = new Object[3];
            if (i == 1) {
                objArr[0] = "stubElements";
            } else if (i != 2) {
                objArr[0] = CrashHianalyticsData.MESSAGE;
            } else {
                objArr[0] = "mirrorElements";
            }
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/compiled/ClsElementImpl$InvalidMirrorException";
            objArr[2] = "<init>";
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public InvalidMirrorException(String str) {
            super(str);
            if (str == null) {
                $$$reportNull$$$0(0);
            }
        }

        public InvalidMirrorException(PsiElement psiElement, PsiElement psiElement2) {
            this("stub:" + psiElement + "; mirror:" + psiElement2);
        }

        /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
        public InvalidMirrorException(List<? extends PsiElement> list, List<? extends PsiElement> list2) {
            this("stub:" + list + "; mirror:" + list2);
            if (list == null) {
                $$$reportNull$$$0(1);
            }
            if (list2 == null) {
                $$$reportNull$$$0(2);
            }
        }
    }
}
