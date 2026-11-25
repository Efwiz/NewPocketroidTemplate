package org.jetbrains.kotlin.fir.lightTree.converter;

import androidx.exifinterface.media.ExifInterface;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.KtFakeSourceElementKind;
import org.jetbrains.kotlin.KtNodeTypes;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.KtSourceElementKt;
import org.jetbrains.kotlin.com.intellij.lang.LighterASTNode;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.Visibilities;
import org.jetbrains.kotlin.fir.FirModuleData;
import org.jetbrains.kotlin.fir.builder.ConversionUtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin;
import org.jetbrains.kotlin.fir.declarations.FirVariable;
import org.jetbrains.kotlin.fir.declarations.builder.FirPropertyBuilder;
import org.jetbrains.kotlin.fir.declarations.impl.FirDeclarationStatusImpl;
import org.jetbrains.kotlin.fir.expressions.FirBlock;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirStatement;
import org.jetbrains.kotlin.fir.expressions.builder.FirArgumentListBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirBlockBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirCallBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirComponentCallBuilder;
import org.jetbrains.kotlin.fir.lightTree.fir.DestructuringDeclaration;
import org.jetbrains.kotlin.fir.symbols.impl.FirPropertySymbol;
import org.jetbrains.kotlin.fir.types.FirQualifierPart;
import org.jetbrains.kotlin.fir.types.FirUserTypeRef;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.name.SpecialNames;
import org.jetbrains.kotlin.psi.KtPsiUtil;

/* JADX INFO: compiled from: ConverterUtil.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000X\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a&\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u0001\u001a/\u0010\r\u001a\u00020\u00012\u0006\u0010\u000e\u001a\u00020\u000f2\u0019\u0010\u0010\u001a\u0015\u0012\u0004\u0012\u00020\u000f\u0012\u0006\u0012\u0004\u0018\u00010\u000f0\u0011¢\u0006\u0002\b\u0012H\u0086\bø\u0001\u0000\u001a'\u0010\u0013\u001a\u0002H\u0014\"\b\b\u0000\u0010\u0014*\u00020\u0015*\u0002H\u00142\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00170\u0016¢\u0006\u0002\u0010\u0018\u001a\n\u0010\u0019\u001a\u00020\u001a*\u00020\u000f\u001a\u0016\u0010\u001b\u001a\u00020\u001c*\u0004\u0018\u00010\u001a2\b\b\u0002\u0010\u001d\u001a\u00020\u001a\"\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0000\u0010\u0003\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\u001e"}, d2 = {"isUnderscored", "", "Lorg/jetbrains/kotlin/fir/types/FirUserTypeRef;", "(Lorg/jetbrains/kotlin/fir/types/FirUserTypeRef;)Z", "generateDestructuringBlock", "Lorg/jetbrains/kotlin/fir/expressions/FirBlock;", "moduleData", "Lorg/jetbrains/kotlin/fir/FirModuleData;", "multiDeclaration", "Lorg/jetbrains/kotlin/fir/lightTree/fir/DestructuringDeclaration;", "container", "Lorg/jetbrains/kotlin/fir/declarations/FirVariable;", "tmpVariable", "isClassLocal", "classNode", "Lorg/jetbrains/kotlin/com/intellij/lang/LighterASTNode;", "getParent", "Lkotlin/Function1;", "Lkotlin/ExtensionFunctionType;", "extractArgumentsFrom", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/fir/expressions/builder/FirCallBuilder;", "", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "(Lorg/jetbrains/kotlin/fir/expressions/builder/FirCallBuilder;Ljava/util/List;)Lorg/jetbrains/kotlin/fir/expressions/builder/FirCallBuilder;", "getAsStringWithoutBacktick", "", "nameAsSafeName", "Lorg/jetbrains/kotlin/name/Name;", "defaultName", "light-tree2fir"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ConverterUtilKt {
    public static /* synthetic */ Name nameAsSafeName$default(String str, String str2, int i, Object obj) {
        if ((i & 1) != 0) {
            str2 = "";
        }
        return nameAsSafeName(str, str2);
    }

    public static final Name nameAsSafeName(String str, String defaultName) {
        Intrinsics.checkNotNullParameter(defaultName, "defaultName");
        if (str != null) {
            Name nameIdentifier = Name.identifier(KtPsiUtil.unquoteIdentifier(str));
            Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
            return nameIdentifier;
        }
        if (defaultName.length() > 0) {
            Name nameIdentifier2 = Name.identifier(defaultName);
            Intrinsics.checkNotNullExpressionValue(nameIdentifier2, "identifier(...)");
            return nameIdentifier2;
        }
        return SpecialNames.NO_NAME_PROVIDED;
    }

    public static final String getAsStringWithoutBacktick(LighterASTNode lighterASTNode) {
        Intrinsics.checkNotNullParameter(lighterASTNode, "<this>");
        return StringsKt.replace$default(lighterASTNode.toString(), "`", "", false, 4, (Object) null);
    }

    public static final boolean isClassLocal(LighterASTNode classNode, Function1<? super LighterASTNode, ? extends LighterASTNode> getParent) {
        LighterASTNode lighterASTNodeInvoke;
        Intrinsics.checkNotNullParameter(classNode, "classNode");
        Intrinsics.checkNotNullParameter(getParent, "getParent");
        while (classNode != null) {
            IElementType tokenType = classNode.getTokenType();
            classNode = getParent.invoke(classNode);
            IElementType tokenType2 = classNode != null ? classNode.getTokenType() : null;
            if (Intrinsics.areEqual(tokenType, KtNodeTypes.PROPERTY) || Intrinsics.areEqual(tokenType, KtNodeTypes.FUN)) {
                LighterASTNode lighterASTNodeInvoke2 = classNode != null ? getParent.invoke(classNode) : null;
                if (Intrinsics.areEqual(tokenType2, KtNodeTypes.KT_FILE)) {
                    return true;
                }
                if (Intrinsics.areEqual(tokenType2, KtNodeTypes.CLASS_BODY)) {
                    if (Intrinsics.areEqual(lighterASTNodeInvoke2 != null ? lighterASTNodeInvoke2.getTokenType() : null, KtNodeTypes.OBJECT_DECLARATION)) {
                        if (!Intrinsics.areEqual((lighterASTNodeInvoke2 == null || (lighterASTNodeInvoke = getParent.invoke(lighterASTNodeInvoke2)) == null) ? null : lighterASTNodeInvoke.getTokenType(), KtNodeTypes.OBJECT_LITERAL)) {
                        }
                    }
                    return true;
                }
                if (Intrinsics.areEqual(tokenType2, KtNodeTypes.BLOCK)) {
                    if (Intrinsics.areEqual(lighterASTNodeInvoke2 != null ? lighterASTNodeInvoke2.getTokenType() : null, KtNodeTypes.SCRIPT)) {
                        return true;
                    }
                }
            }
            if (Intrinsics.areEqual(tokenType2, KtNodeTypes.ENUM_ENTRY) || Intrinsics.areEqual(tokenType, KtNodeTypes.BLOCK)) {
                return true;
            }
        }
        return false;
    }

    public static final boolean isUnderscored(FirUserTypeRef firUserTypeRef) {
        Name name2;
        Intrinsics.checkNotNullParameter(firUserTypeRef, "<this>");
        FirQualifierPart firQualifierPart = (FirQualifierPart) CollectionsKt.lastOrNull((List) firUserTypeRef.getQualifier());
        return Intrinsics.areEqual((firQualifierPart == null || (name2 = firQualifierPart.getName()) == null) ? null : name2.asString(), "_");
    }

    public static final <T extends FirCallBuilder> T extractArgumentsFrom(T t, List<? extends FirExpression> container) {
        Intrinsics.checkNotNullParameter(t, "<this>");
        Intrinsics.checkNotNullParameter(container, "container");
        FirArgumentListBuilder firArgumentListBuilder = new FirArgumentListBuilder();
        CollectionsKt.addAll(firArgumentListBuilder.getArguments(), container);
        t.setArgumentList(firArgumentListBuilder.build());
        return t;
    }

    public static final FirBlock generateDestructuringBlock(FirModuleData moduleData, DestructuringDeclaration multiDeclaration, FirVariable container, boolean z) {
        Intrinsics.checkNotNullParameter(moduleData, "moduleData");
        Intrinsics.checkNotNullParameter(multiDeclaration, "multiDeclaration");
        Intrinsics.checkNotNullParameter(container, "container");
        FirBlockBuilder firBlockBuilder = new FirBlockBuilder();
        if (z) {
            firBlockBuilder.getStatements().add(container);
        }
        boolean zIsVar = multiDeclaration.isVar();
        int i = 0;
        for (FirVariable firVariable : multiDeclaration.getEntries()) {
            i++;
            if (firVariable != null) {
                List<FirStatement> statements = firBlockBuilder.getStatements();
                FirPropertyBuilder firPropertyBuilder = new FirPropertyBuilder();
                firPropertyBuilder.setModuleData(moduleData);
                firPropertyBuilder.setOrigin(FirDeclarationOrigin.Source.INSTANCE);
                firPropertyBuilder.setReturnTypeRef(firVariable.getReturnTypeRef());
                firPropertyBuilder.setName(firVariable.getName());
                FirComponentCallBuilder firComponentCallBuilder = new FirComponentCallBuilder();
                KtSourceElement source = firVariable.getSource();
                KtSourceElement ktSourceElementFakeElement$default = source != null ? KtSourceElementKt.fakeElement$default(source, KtFakeSourceElementKind.DesugaredComponentFunctionCall.INSTANCE, 0, 0, 6, null) : null;
                firComponentCallBuilder.setSource(ktSourceElementFakeElement$default);
                firComponentCallBuilder.setExplicitReceiver(ConversionUtilsKt.generateResolvedAccessExpression(ktSourceElementFakeElement$default, container));
                firComponentCallBuilder.setComponentIndex(i);
                firPropertyBuilder.setInitializer(firComponentCallBuilder.mo6809build());
                firPropertyBuilder.setVar(zIsVar);
                firPropertyBuilder.setSymbol(new FirPropertySymbol(firVariable.getName()));
                firPropertyBuilder.setSource(firVariable.getSource());
                firPropertyBuilder.setLocal(true);
                firPropertyBuilder.setStatus(new FirDeclarationStatusImpl(Visibilities.Local.INSTANCE, Modality.FINAL));
                CollectionsKt.addAll(firPropertyBuilder.getAnnotations(), firVariable.getAnnotations());
                statements.add(firPropertyBuilder.mo6809build());
            }
        }
        return firBlockBuilder.mo6809build();
    }
}
