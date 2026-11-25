package org.jetbrains.kotlin.fir.analysis;

import com.reandroid.arsc.container.SpecTypePair;
import java.util.Set;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.ArraysKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.KtFakeSourceElement;
import org.jetbrains.kotlin.KtFakeSourceElementKind;
import org.jetbrains.kotlin.KtLightSourceElement;
import org.jetbrains.kotlin.KtNodeTypes;
import org.jetbrains.kotlin.KtPsiSourceElement;
import org.jetbrains.kotlin.KtRealPsiSourceElement;
import org.jetbrains.kotlin.KtRealSourceElementKind;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.KtSourceElementKind;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.intellij.lang.LighterASTNode;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet;
import org.jetbrains.kotlin.fir.declarations.FirImport;

/* JADX INFO: compiled from: FirSourceUtils.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000D\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u0014\u0010\u0003\u001a\u00020\u0004*\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0000\u001a4\u0010\u0007\u001a\u0004\u0018\u00010\b*\u00020\u00042\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\r2\u0006\u0010\u000f\u001a\u00020\u0010H\u0002\u001a4\u0010\u0007\u001a\u0004\u0018\u00010\b*\u00020\u00112\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\r2\u0006\u0010\u000f\u001a\u00020\u0010H\u0002\u001a2\u0010\u0007\u001a\u0004\u0018\u00010\b*\u00020\b2\u0006\u0010\u0012\u001a\u00020\u000b2\b\b\u0002\u0010\f\u001a\u00020\r2\b\b\u0002\u0010\u000e\u001a\u00020\r2\b\b\u0002\u0010\u000f\u001a\u00020\u0010\u001a2\u0010\u0007\u001a\u0004\u0018\u00010\b*\u00020\b2\u0006\u0010\t\u001a\u00020\u00012\b\b\u0002\u0010\f\u001a\u00020\r2\b\b\u0002\u0010\u000e\u001a\u00020\r2\b\b\u0002\u0010\u000f\u001a\u00020\u0010\u001a8\u0010\u0007\u001a\u0004\u0018\u00010\b*\u00020\b2\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n2\b\b\u0002\u0010\f\u001a\u00020\r2\b\b\u0002\u0010\u000e\u001a\u00020\r2\b\b\u0002\u0010\u000f\u001a\u00020\u0010\u001a\u0014\u0010\u0013\u001a\u0004\u0018\u00010\b*\u00020\u00142\u0006\u0010\u0015\u001a\u00020\r\"\u0013\u0010\u0000\u001a\u00070\u0001¢\u0006\u0002\b\u0002X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0016"}, d2 = {"IMPORT_PARENT_TOKEN_TYPES", "Lorg/jetbrains/kotlin/com/intellij/psi/tree/TokenSet;", "Lorg/jetbrains/annotations/NotNull;", "buildChildSourceElement", "Lorg/jetbrains/kotlin/KtLightSourceElement;", "childNode", "Lorg/jetbrains/kotlin/com/intellij/lang/LighterASTNode;", "getChild", "Lorg/jetbrains/kotlin/KtSourceElement;", SpecTypePair.NAME_types, "", "Lorg/jetbrains/kotlin/com/intellij/psi/tree/IElementType;", "index", "", ConfigConstants.CONFIG_KEY_DEPTH, "reverse", "", "Lorg/jetbrains/kotlin/KtPsiSourceElement;", ModuleXmlParser.TYPE, "getSourceForImportSegment", "Lorg/jetbrains/kotlin/fir/declarations/FirImport;", "indexFromLast", "checkers"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FirSourceUtilsKt {
    private static final TokenSet IMPORT_PARENT_TOKEN_TYPES;

    public static /* synthetic */ KtSourceElement getChild$default(KtSourceElement ktSourceElement, IElementType iElementType, int i, int i2, boolean z, int i3, Object obj) {
        if ((i3 & 2) != 0) {
            i = 0;
        }
        if ((i3 & 4) != 0) {
            i2 = -1;
        }
        if ((i3 & 8) != 0) {
            z = false;
        }
        return getChild(ktSourceElement, iElementType, i, i2, z);
    }

    public static final KtSourceElement getChild(KtSourceElement ktSourceElement, IElementType type, int i, int i2, boolean z) {
        Intrinsics.checkNotNullParameter(ktSourceElement, "<this>");
        Intrinsics.checkNotNullParameter(type, "type");
        return getChild(ktSourceElement, (Set<? extends IElementType>) SetsKt.setOf(type), i, i2, z);
    }

    public static /* synthetic */ KtSourceElement getChild$default(KtSourceElement ktSourceElement, TokenSet tokenSet, int i, int i2, boolean z, int i3, Object obj) {
        if ((i3 & 2) != 0) {
            i = 0;
        }
        if ((i3 & 4) != 0) {
            i2 = -1;
        }
        if ((i3 & 8) != 0) {
            z = false;
        }
        return getChild(ktSourceElement, tokenSet, i, i2, z);
    }

    public static final KtSourceElement getChild(KtSourceElement ktSourceElement, TokenSet types, int i, int i2, boolean z) {
        Intrinsics.checkNotNullParameter(ktSourceElement, "<this>");
        Intrinsics.checkNotNullParameter(types, "types");
        IElementType[] types2 = types.getTypes();
        Intrinsics.checkNotNullExpressionValue(types2, "getTypes(...)");
        return getChild(ktSourceElement, (Set<? extends IElementType>) ArraysKt.toSet(types2), i, i2, z);
    }

    public static /* synthetic */ KtSourceElement getChild$default(KtSourceElement ktSourceElement, Set set, int i, int i2, boolean z, int i3, Object obj) {
        if ((i3 & 2) != 0) {
            i = 0;
        }
        if ((i3 & 4) != 0) {
            i2 = -1;
        }
        if ((i3 & 8) != 0) {
            z = false;
        }
        return getChild(ktSourceElement, (Set<? extends IElementType>) set, i, i2, z);
    }

    public static final KtSourceElement getChild(KtSourceElement ktSourceElement, Set<? extends IElementType> types, int i, int i2, boolean z) {
        Intrinsics.checkNotNullParameter(ktSourceElement, "<this>");
        Intrinsics.checkNotNullParameter(types, "types");
        if (ktSourceElement instanceof KtPsiSourceElement) {
            return getChild((KtPsiSourceElement) ktSourceElement, types, i, i2, z);
        }
        if (ktSourceElement instanceof KtLightSourceElement) {
            return getChild((KtLightSourceElement) ktSourceElement, types, i, i2, z);
        }
        return null;
    }

    private static final KtSourceElement getChild(KtPsiSourceElement ktPsiSourceElement, Set<? extends IElementType> set, int i, int i2, boolean z) {
        KtFakeSourceElement ktFakeSourceElement;
        PsiElement psiElementFind = new PsiElementFinderByType(set, i, i2, z).find(ktPsiSourceElement.getPsi());
        if (psiElementFind != null) {
            Object obj = (KtSourceElementKind) KtRealSourceElementKind.INSTANCE;
            if (obj instanceof KtRealSourceElementKind) {
                ktFakeSourceElement = new KtRealPsiSourceElement(psiElementFind);
            } else {
                if (!(obj instanceof KtFakeSourceElementKind)) {
                    throw new NoWhenBranchMatchedException();
                }
                ktFakeSourceElement = new KtFakeSourceElement(psiElementFind, (KtFakeSourceElementKind) obj);
            }
        } else {
            ktFakeSourceElement = null;
        }
        return ktFakeSourceElement;
    }

    private static final KtSourceElement getChild(KtLightSourceElement ktLightSourceElement, Set<? extends IElementType> set, int i, int i2, boolean z) {
        LighterASTNode lighterASTNodeFind = new LighterTreeElementFinderByType(ktLightSourceElement.getTreeStructure(), set, i, i2, z).find(ktLightSourceElement.getLighterASTNode());
        if (lighterASTNodeFind == null) {
            return null;
        }
        return buildChildSourceElement(ktLightSourceElement, lighterASTNodeFind);
    }

    public static final KtLightSourceElement buildChildSourceElement(KtLightSourceElement ktLightSourceElement, LighterASTNode childNode) {
        Intrinsics.checkNotNullParameter(ktLightSourceElement, "<this>");
        Intrinsics.checkNotNullParameter(childNode, "childNode");
        int startOffset = ktLightSourceElement.getStartOffset() - ktLightSourceElement.getLighterASTNode().getStartOffset();
        return new KtLightSourceElement(childNode, childNode.getStartOffset() + startOffset, childNode.getEndOffset() + startOffset, ktLightSourceElement.getTreeStructure(), KtRealSourceElementKind.INSTANCE);
    }

    static {
        TokenSet tokenSetCreate = TokenSet.create(KtNodeTypes.DOT_QUALIFIED_EXPRESSION, KtNodeTypes.REFERENCE_EXPRESSION);
        Intrinsics.checkNotNullExpressionValue(tokenSetCreate, "create(...)");
        IMPORT_PARENT_TOKEN_TYPES = tokenSetCreate;
    }

    public static final KtSourceElement getSourceForImportSegment(FirImport firImport, int i) {
        Intrinsics.checkNotNullParameter(firImport, "<this>");
        KtSourceElement source = firImport.getSource();
        if (source == null) {
            return null;
        }
        int i2 = i + 1;
        KtSourceElement child$default = source;
        for (int i3 = 0; i3 < i2; i3++) {
            child$default = getChild$default(child$default, IMPORT_PARENT_TOKEN_TYPES, 0, 1, false, 10, (Object) null);
            if (child$default == null) {
                return null;
            }
        }
        KtSourceElement ktSourceElement = Intrinsics.areEqual(child$default.getElementType(), KtNodeTypes.REFERENCE_EXPRESSION) ? child$default : null;
        if (ktSourceElement != null) {
            return ktSourceElement;
        }
        IElementType REFERENCE_EXPRESSION = KtNodeTypes.REFERENCE_EXPRESSION;
        Intrinsics.checkNotNullExpressionValue(REFERENCE_EXPRESSION, "REFERENCE_EXPRESSION");
        return getChild$default(child$default, REFERENCE_EXPRESSION, 0, 1, true, 2, (Object) null);
    }
}
