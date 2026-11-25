package org.jetbrains.kotlin.kdoc.psi.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.text.CharsKt;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.TokenType;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.kdoc.lexer.KDocTokens;
import org.jetbrains.kotlin.kdoc.parser.KDocElementTypes;
import org.jetbrains.kotlin.kdoc.parser.KDocKnownTag;

/* JADX INFO: compiled from: KDocTag.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0016\u0018\u0000 \u001b2\u00020\u0001:\u0001\u001bB\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u000e\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00030\nH\u0002J\b\u0010\u000b\u001a\u00020\fH\u0016J\n\u0010\r\u001a\u0004\u0018\u00010\fH\u0016J\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fJ\n\u0010\u0010\u001a\u0004\u0018\u00010\fH\u0016J\u0016\u0010\u0011\u001a\u00020\u00122\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00030\nH\u0002J\u001e\u0010\u0014\u001a\u00020\f2\n\u0010\u0015\u001a\u00060\u0016j\u0002`\u00172\b\b\u0002\u0010\u0018\u001a\u00020\u0012H\u0002J\f\u0010\u0019\u001a\u00020\u001a*\u00020\fH\u0002R\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u00068F¢\u0006\u0006\u001a\u0004\b\u0007\u0010\b¨\u0006\u001c"}, d2 = {"Lorg/jetbrains/kotlin/kdoc/psi/impl/KDocTag;", "Lorg/jetbrains/kotlin/kdoc/psi/impl/KDocElementImpl;", "node", "Lorg/jetbrains/kotlin/com/intellij/lang/ASTNode;", "(Lcom/intellij/lang/ASTNode;)V", "knownTag", "Lorg/jetbrains/kotlin/kdoc/parser/KDocKnownTag;", "getKnownTag", "()Lorg/jetbrains/kotlin/kdoc/parser/KDocKnownTag;", "childrenAfterTagName", "", "getContent", "", "getName", "getSubjectLink", "Lorg/jetbrains/kotlin/kdoc/psi/impl/KDocLink;", "getSubjectName", "hasSubject", "", "contentChildren", "trimCommonIndent", "builder", "Ljava/lang/StringBuilder;", "Lkotlin/text/StringBuilder;", "prepend4WhiteSpaces", "calcIndent", "", "Companion", "psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class KDocTag extends KDocElementImpl {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final String indentationWhiteSpaces = StringsKt.repeat(" ", 4);

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KDocTag(ASTNode node) {
        super(node);
        Intrinsics.checkNotNullParameter(node, "node");
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.navigation.NavigationItem, org.jetbrains.kotlin.com.intellij.pom.PomNamedTarget, org.jetbrains.kotlin.com.intellij.psi.PsiNamedElement, org.jetbrains.kotlin.com.intellij.psi.PsiQualifiedNamedElement
    public String getName() {
        PsiElement psiElementFindChildByType = findChildByType(KDocTokens.TAG_NAME);
        if (psiElementFindChildByType == null) {
            return null;
        }
        String text = psiElementFindChildByType.getText();
        Intrinsics.checkNotNullExpressionValue(text, "getText(...)");
        String strSubstring = text.substring(1);
        Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
        return strSubstring;
    }

    public String getSubjectName() {
        KDocLink subjectLink = getSubjectLink();
        if (subjectLink != null) {
            return subjectLink.getLinkText();
        }
        return null;
    }

    public final KDocLink getSubjectLink() {
        List<ASTNode> listChildrenAfterTagName = childrenAfterTagName();
        if (!hasSubject(listChildrenAfterTagName)) {
            return null;
        }
        ASTNode aSTNode = (ASTNode) CollectionsKt.firstOrNull((List) listChildrenAfterTagName);
        PsiElement psi = aSTNode != null ? aSTNode.getPsi() : null;
        if (psi instanceof KDocLink) {
            return (KDocLink) psi;
        }
        return null;
    }

    public final KDocKnownTag getKnownTag() {
        String name2 = getName();
        if (name2 != null) {
            return KDocKnownTag.INSTANCE.findByTagName(name2);
        }
        return null;
    }

    private final boolean hasSubject(List<? extends ASTNode> contentChildren) {
        KDocKnownTag knownTag = getKnownTag();
        if (!(knownTag != null ? knownTag.getIsReferenceRequired() : false)) {
            return false;
        }
        ASTNode aSTNode = (ASTNode) CollectionsKt.firstOrNull((List) contentChildren);
        return Intrinsics.areEqual(aSTNode != null ? aSTNode.getElementType() : null, KDocTokens.MARKDOWN_LINK);
    }

    private final List<ASTNode> childrenAfterTagName() {
        ASTNode[] children = getNode().getChildren(null);
        Intrinsics.checkNotNullExpressionValue(children, "getChildren(...)");
        ArrayList arrayList = new ArrayList();
        boolean z = false;
        boolean z2 = false;
        for (ASTNode aSTNode : children) {
            if (!z2) {
                if (!Intrinsics.areEqual(aSTNode.getElementType(), KDocTokens.TAG_NAME)) {
                    arrayList.add(aSTNode);
                    z2 = true;
                }
            } else {
                arrayList.add(aSTNode);
            }
        }
        ArrayList arrayList2 = new ArrayList();
        for (Object obj : arrayList) {
            if (!z) {
                if (!Intrinsics.areEqual(((ASTNode) obj).getElementType(), TokenType.WHITE_SPACE)) {
                    arrayList2.add(obj);
                    z = true;
                }
            } else {
                arrayList2.add(obj);
            }
        }
        return arrayList2;
    }

    /* JADX WARN: Code duplicated, block: B:20:0x007c  */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v0, types: [T, java.lang.StringBuilder] */
    public String getContent() {
        String text;
        boolean z;
        ?? sb = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        Ref.ObjectRef objectRef = new Ref.ObjectRef();
        objectRef.element = sb;
        Ref.BooleanRef booleanRef = new Ref.BooleanRef();
        List<ASTNode> listChildrenAfterTagName = childrenAfterTagName();
        if (hasSubject(listChildrenAfterTagName)) {
            listChildrenAfterTagName = CollectionsKt.drop(listChildrenAfterTagName, 1);
        }
        boolean z2 = false;
        boolean z3 = false;
        for (ASTNode aSTNode : listChildrenAfterTagName) {
            IElementType elementType = aSTNode.getElementType();
            Intrinsics.checkNotNullExpressionValue(elementType, "getElementType(...)");
            if (Intrinsics.areEqual(elementType, KDocTokens.CODE_BLOCK_TEXT)) {
                if (!getContent$isCodeBlock(objectRef, sb2)) {
                    if (booleanRef.element) {
                        z = true;
                    } else {
                        String text2 = aSTNode.getText();
                        Intrinsics.checkNotNullExpressionValue(text2, "getText(...)");
                        if (StringsKt.startsWith$default(text2, indentationWhiteSpaces, false, 2, (Object) null)) {
                            z = true;
                        } else {
                            String text3 = aSTNode.getText();
                            Intrinsics.checkNotNullExpressionValue(text3, "getText(...)");
                            if (StringsKt.startsWith$default(text3, "\t", false, 2, (Object) null)) {
                                z = true;
                            } else {
                                z = false;
                            }
                        }
                    }
                    booleanRef.element = z;
                }
                objectRef.element = sb2;
            } else if (KDocTokens.CONTENT_TOKENS.contains(elementType)) {
                getContent$flushCodeBlock(sb, this, sb2, booleanRef, objectRef);
                booleanRef.element = false;
            }
            if (KDocTokens.CONTENT_TOKENS.contains(elementType)) {
                boolean z4 = !(z3 || booleanRef.element) || (z2 && !getContent$isCodeBlock(objectRef, sb2));
                StringBuilder sb3 = (StringBuilder) objectRef.element;
                if (z4) {
                    String text4 = aSTNode.getText();
                    Intrinsics.checkNotNullExpressionValue(text4, "getText(...)");
                    text = StringsKt.trimStart((CharSequence) text4).toString();
                } else {
                    text = aSTNode.getText();
                    Intrinsics.checkNotNullExpressionValue(text, "getText(...)");
                }
                sb3.append(text);
                z2 = false;
                z3 = true;
            }
            if (Intrinsics.areEqual(elementType, KDocTokens.LEADING_ASTERISK)) {
                z2 = true;
            }
            if (Intrinsics.areEqual(elementType, TokenType.WHITE_SPACE) && z3) {
                ((StringBuilder) objectRef.element).append(StringsKt.repeat("\n", StringUtil.countNewLines(aSTNode.getText())));
            }
            if (Intrinsics.areEqual(elementType, KDocElementTypes.KDOC_TAG)) {
                break;
            }
        }
        getContent$flushCodeBlock(sb, this, sb2, booleanRef, objectRef);
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return StringsKt.trimEnd(string, io.netty.util.internal.StringUtil.SPACE, '\t');
    }

    private static final boolean getContent$isCodeBlock(Ref.ObjectRef<StringBuilder> objectRef, StringBuilder sb) {
        return Intrinsics.areEqual(objectRef.element, sb);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static final void getContent$flushCodeBlock(StringBuilder sb, KDocTag kDocTag, StringBuilder sb2, Ref.BooleanRef booleanRef, Ref.ObjectRef<StringBuilder> objectRef) {
        if (getContent$isCodeBlock(objectRef, sb2)) {
            sb.append(kDocTag.trimCommonIndent(sb2, booleanRef.element));
            sb2.setLength(0);
            objectRef.element = sb;
        }
    }

    private final String trimCommonIndent(StringBuilder builder, boolean prepend4WhiteSpaces) {
        Integer num;
        String string = builder.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        List listSplit$default = StringsKt.split$default((CharSequence) string, new char[]{'\n'}, false, 0, 6, (Object) null);
        ArrayList arrayList = new ArrayList();
        for (Object obj : listSplit$default) {
            if (StringsKt.trim((CharSequence) obj).toString().length() > 0) {
                arrayList.add(obj);
            }
        }
        Iterator it = arrayList.iterator();
        if (it.hasNext()) {
            Integer numValueOf = Integer.valueOf(calcIndent((String) it.next()));
            while (it.hasNext()) {
                Integer numValueOf2 = Integer.valueOf(calcIndent((String) it.next()));
                if (numValueOf.compareTo(numValueOf2) > 0) {
                    numValueOf = numValueOf2;
                }
            }
            num = numValueOf;
        } else {
            num = null;
        }
        Integer num2 = num;
        int iIntValue = num2 != null ? num2.intValue() : 0;
        ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(listSplit$default, 10));
        Iterator it2 = listSplit$default.iterator();
        while (it2.hasNext()) {
            arrayList2.add(StringsKt.drop((String) it2.next(), iIntValue));
        }
        ArrayList arrayList3 = arrayList2;
        if (prepend4WhiteSpaces) {
            ArrayList<String> arrayList4 = arrayList3;
            ArrayList arrayList5 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList4, 10));
            for (String strPrependIndent : arrayList4) {
                if (!StringsKt.isBlank(strPrependIndent)) {
                    strPrependIndent = StringsKt.prependIndent(strPrependIndent, indentationWhiteSpaces);
                }
                arrayList5.add(strPrependIndent);
            }
            arrayList3 = arrayList5;
        }
        return CollectionsKt.joinToString$default(arrayList3, "\n", null, null, 0, null, null, 62, null);
    }

    private final int calcIndent(String str) {
        String str2 = str;
        int length = str2.length();
        for (int i = 0; i < length; i++) {
            if (!CharsKt.isWhitespace(str2.charAt(i))) {
                return i;
            }
        }
        return -1;
    }

    /* JADX INFO: compiled from: KDocTag.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/kdoc/psi/impl/KDocTag$Companion;", "", "()V", "indentationWhiteSpaces", "", "getIndentationWhiteSpaces", "()Ljava/lang/String;", "psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final String getIndentationWhiteSpaces() {
            return KDocTag.indentationWhiteSpaces;
        }
    }
}
