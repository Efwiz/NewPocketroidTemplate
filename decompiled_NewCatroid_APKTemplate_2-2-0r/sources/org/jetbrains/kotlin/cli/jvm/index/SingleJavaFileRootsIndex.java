package org.jetbrains.kotlin.cli.jvm.index;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;
import kotlin.text.Charsets;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.intellij.lang.java.lexer.JavaLexer;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.pom.java.LanguageLevel;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.jetbrains.kotlin.com.intellij.psi.PsiTreeChangeEvent;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.ElementType;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: SingleJavaFileRootsIndex.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\u0018\u00002\u00020\u0001:\u0001\u0013B\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0005J\u0010\u0010\n\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\f\u001a\u00020\bJ\u0014\u0010\r\u001a\b\u0012\u0004\u0012\u00020\b0\u00032\u0006\u0010\u000e\u001a\u00020\u000fJ\u0016\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\b0\u00032\u0006\u0010\u0011\u001a\u00020\u0012H\u0002R*\u0010\u0006\u001a\u001e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00030\u0007j\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u0003`\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/cli/jvm/index/SingleJavaFileRootsIndex;", "", PsiTreeChangeEvent.PROP_ROOTS, "", "Lorg/jetbrains/kotlin/cli/jvm/index/JavaRoot;", "(Ljava/util/List;)V", "classIdsInRoots", "Ljava/util/ArrayList;", "Lorg/jetbrains/kotlin/name/ClassId;", "Lkotlin/collections/ArrayList;", "findJavaSourceClass", "Lorg/jetbrains/kotlin/com/intellij/openapi/vfs/VirtualFile;", "classId", "findJavaSourceClasses", "packageFqName", "Lorg/jetbrains/kotlin/name/FqName;", "getClassIdsForRootAt", "index", "", "JavaSourceClassIdReader", "cli-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SingleJavaFileRootsIndex {
    private final ArrayList<List<ClassId>> classIdsInRoots;
    private final List<JavaRoot> roots;

    public SingleJavaFileRootsIndex(List<JavaRoot> roots) {
        Intrinsics.checkNotNullParameter(roots, "roots");
        this.roots = roots;
        Iterator<JavaRoot> it = roots.iterator();
        while (it.hasNext()) {
            it.next().getFile().isDirectory();
        }
        this.classIdsInRoots = new ArrayList<>(this.roots.size());
    }

    public final VirtualFile findJavaSourceClass(ClassId classId) {
        Integer next;
        Intrinsics.checkNotNullParameter(classId, "classId");
        Iterator<Integer> it = CollectionsKt.getIndices(this.roots).iterator();
        do {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
        } while (!getClassIdsForRootAt(next.intValue()).contains(classId));
        Integer num = next;
        if (num == null) {
            return null;
        }
        return this.roots.get(num.intValue()).getFile();
    }

    public final List<ClassId> findJavaSourceClasses(FqName packageFqName) {
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        IntRange indices = CollectionsKt.getIndices(this.roots);
        ArrayList arrayList = new ArrayList();
        Iterator<Integer> it = indices.iterator();
        while (it.hasNext()) {
            CollectionsKt.addAll(arrayList, getClassIdsForRootAt(((IntIterator) it).nextInt()));
        }
        ArrayList arrayList2 = new ArrayList();
        for (Object obj : arrayList) {
            if (Intrinsics.areEqual(((ClassId) obj).getPackageFqName(), packageFqName)) {
                arrayList2.add(obj);
            }
        }
        return arrayList2;
    }

    private final List<ClassId> getClassIdsForRootAt(int index) {
        int size = this.classIdsInRoots.size();
        if (size <= index) {
            while (true) {
                this.classIdsInRoots.add(new JavaSourceClassIdReader(this.roots.get(size).getFile()).readClassIds());
                if (size == index) {
                    break;
                }
                size++;
            }
        }
        List<ClassId> list = this.classIdsInRoots.get(index);
        Intrinsics.checkNotNullExpressionValue(list, "get(...)");
        return list;
    }

    /* JADX INFO: compiled from: SingleJavaFileRootsIndex.kt */
    @Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0002\u0018\u0000 \u00182\u00020\u0001:\u0001\u0018B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\n\u001a\u00020\u000bH\u0002J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0002J\b\u0010\u0010\u001a\u00020\rH\u0002J\b\u0010\u0011\u001a\u00020\rH\u0002J\b\u0010\u0012\u001a\u00020\rH\u0002J\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00150\u0014J\b\u0010\u0016\u001a\u00020\u0017H\u0002R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/cli/jvm/index/SingleJavaFileRootsIndex$JavaSourceClassIdReader;", "", "file", "Lorg/jetbrains/kotlin/com/intellij/openapi/vfs/VirtualFile;", "(Lcom/intellij/openapi/vfs/VirtualFile;)V", "braceBalance", "", "lexer", "Lorg/jetbrains/kotlin/com/intellij/lang/java/lexer/JavaLexer;", "parenthesisBalance", "advance", "", "at", "", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/com/intellij/psi/tree/IElementType;", "atClass", "atRecord", "end", "readClassIds", "", "Lorg/jetbrains/kotlin/name/ClassId;", "tokenText", "", "Companion", "cli-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class JavaSourceClassIdReader {
        private int braceBalance;
        private final JavaLexer lexer;
        private int parenthesisBalance;
        private static final Set<IElementType> CLASS_KEYWORDS = SetsKt.setOf((Object[]) new IElementType[]{ElementType.CLASS_KEYWORD, ElementType.INTERFACE_KEYWORD, ElementType.ENUM_KEYWORD});

        public JavaSourceClassIdReader(VirtualFile file) throws IOException {
            Intrinsics.checkNotNullParameter(file, "file");
            JavaLexer javaLexer = new JavaLexer(LanguageLevel.HIGHEST);
            byte[] bArrContentsToByteArray = file.contentsToByteArray();
            Intrinsics.checkNotNullExpressionValue(bArrContentsToByteArray, "contentsToByteArray(...)");
            javaLexer.start(new String(bArrContentsToByteArray, Charsets.UTF_8));
            this.lexer = javaLexer;
        }

        private final boolean at(IElementType type) {
            return Intrinsics.areEqual(this.lexer.getTokenType(), type);
        }

        private final boolean end() {
            return this.lexer.getTokenType() == null;
        }

        private final void advance() {
            IElementType LBRACE = ElementType.LBRACE;
            Intrinsics.checkNotNullExpressionValue(LBRACE, "LBRACE");
            if (at(LBRACE)) {
                this.braceBalance++;
            } else {
                IElementType RBRACE = ElementType.RBRACE;
                Intrinsics.checkNotNullExpressionValue(RBRACE, "RBRACE");
                if (at(RBRACE)) {
                    this.braceBalance--;
                } else {
                    IElementType LPARENTH = ElementType.LPARENTH;
                    Intrinsics.checkNotNullExpressionValue(LPARENTH, "LPARENTH");
                    if (at(LPARENTH)) {
                        this.parenthesisBalance++;
                    } else {
                        IElementType RPARENTH = ElementType.RPARENTH;
                        Intrinsics.checkNotNullExpressionValue(RPARENTH, "RPARENTH");
                        if (at(RPARENTH)) {
                            this.parenthesisBalance--;
                        }
                    }
                }
            }
            this.lexer.advance();
        }

        private final String tokenText() {
            String tokenText = this.lexer.getTokenText();
            Intrinsics.checkNotNullExpressionValue(tokenText, "getTokenText(...)");
            return tokenText;
        }

        private final boolean atClass() {
            return this.braceBalance == 0 && this.parenthesisBalance == 0 && (CLASS_KEYWORDS.contains(this.lexer.getTokenType()) || atRecord());
        }

        private final boolean atRecord() {
            IElementType IDENTIFIER = ElementType.IDENTIFIER;
            Intrinsics.checkNotNullExpressionValue(IDENTIFIER, "IDENTIFIER");
            return at(IDENTIFIER) && Intrinsics.areEqual(tokenText(), PsiKeyword.RECORD);
        }

        /* JADX WARN: Code duplicated, block: B:21:0x005c  */
        public final List<ClassId> readClassIds() {
            FqName fqName = FqName.ROOT;
            while (!end()) {
                IElementType PACKAGE_KEYWORD = ElementType.PACKAGE_KEYWORD;
                Intrinsics.checkNotNullExpressionValue(PACKAGE_KEYWORD, "PACKAGE_KEYWORD");
                if (at(PACKAGE_KEYWORD) || atClass()) {
                    break;
                }
                advance();
            }
            IElementType PACKAGE_KEYWORD2 = ElementType.PACKAGE_KEYWORD;
            Intrinsics.checkNotNullExpressionValue(PACKAGE_KEYWORD2, "PACKAGE_KEYWORD");
            if (at(PACKAGE_KEYWORD2)) {
                StringBuilder sb = new StringBuilder();
                while (!end()) {
                    IElementType SEMICOLON = ElementType.SEMICOLON;
                    Intrinsics.checkNotNullExpressionValue(SEMICOLON, "SEMICOLON");
                    if (at(SEMICOLON)) {
                        break;
                    }
                    IElementType IDENTIFIER = ElementType.IDENTIFIER;
                    Intrinsics.checkNotNullExpressionValue(IDENTIFIER, "IDENTIFIER");
                    if (at(IDENTIFIER)) {
                        sb.append(tokenText());
                    } else {
                        IElementType DOT = ElementType.DOT;
                        Intrinsics.checkNotNullExpressionValue(DOT, "DOT");
                        if (at(DOT)) {
                            sb.append(tokenText());
                        }
                    }
                    advance();
                }
                fqName = new FqName(sb.toString());
            }
            ArrayList arrayList = new ArrayList(1);
            while (true) {
                if (!end() && !atClass()) {
                    advance();
                } else if (!end()) {
                    advance();
                    while (!end()) {
                        IElementType IDENTIFIER2 = ElementType.IDENTIFIER;
                        Intrinsics.checkNotNullExpressionValue(IDENTIFIER2, "IDENTIFIER");
                        if (at(IDENTIFIER2)) {
                            break;
                        }
                        advance();
                    }
                    if (end()) {
                        break;
                    }
                    arrayList.add(new ClassId(fqName, Name.identifier(tokenText())));
                } else {
                    break;
                }
            }
            return arrayList;
        }
    }
}
