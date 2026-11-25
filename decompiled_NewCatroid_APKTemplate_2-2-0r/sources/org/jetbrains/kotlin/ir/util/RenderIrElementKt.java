package org.jetbrains.kotlin.ir.util;

import androidx.exifinterface.media.ExifInterface;
import com.huawei.hms.ml.language.common.utils.Constant;
import com.reandroid.arsc.chunk.OverlayablePolicy;
import io.netty.util.internal.StringUtil;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.UninitializedPropertyAccessException;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.backend.common.serialization.mangle.MangleConstant;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationWithName;
import org.jetbrains.kotlin.ir.declarations.IrEnumEntry;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrLocalDelegatedProperty;
import org.jetbrains.kotlin.ir.declarations.IrPackageFragment;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrScript;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrSymbolOwner;
import org.jetbrains.kotlin.ir.declarations.IrTypeAlias;
import org.jetbrains.kotlin.ir.declarations.IrTypeParameter;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.declarations.IrVariable;
import org.jetbrains.kotlin.ir.expressions.IrConst;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrVararg;
import org.jetbrains.kotlin.ir.expressions.IrVarargElement;
import org.jetbrains.kotlin.ir.symbols.IrClassifierSymbol;
import org.jetbrains.kotlin.ir.symbols.IrConstructorSymbol;
import org.jetbrains.kotlin.ir.symbols.IrTypeAliasSymbol;
import org.jetbrains.kotlin.ir.symbols.IrTypeParameterSymbol;
import org.jetbrains.kotlin.ir.symbols.IrVariableSymbol;
import org.jetbrains.kotlin.ir.types.IrDynamicType;
import org.jetbrains.kotlin.ir.types.IrErrorType;
import org.jetbrains.kotlin.ir.types.IrSimpleType;
import org.jetbrains.kotlin.ir.types.IrStarProjection;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypeAbbreviation;
import org.jetbrains.kotlin.ir.types.IrTypeArgument;
import org.jetbrains.kotlin.ir.types.IrTypePredicatesKt;
import org.jetbrains.kotlin.ir.types.IrTypeProjection;
import org.jetbrains.kotlin.ir.types.SimpleTypeNullability;
import org.jetbrains.kotlin.ir.types.impl.IrTypeBaseKt;
import org.jetbrains.kotlin.ir.types.impl.ReturnTypeIsNotInitializedException;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.renderer.DescriptorRenderer;
import org.jetbrains.kotlin.types.Variance;

/* JADX INFO: compiled from: RenderIrElement.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0088\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001c\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\u001a&\u0010\u0005\u001a\u00020\u00062\u001b\u0010\u0007\u001a\u0017\u0012\b\u0012\u00060\tj\u0002`\n\u0012\u0004\u0012\u00020\u000b0\b¢\u0006\u0002\b\fH\u0082\b\u001a\"\u0010\r\u001a\u00020\u00062\u0006\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0002\u001a\u0018\u0010\u0014\u001a\u00020\u00062\u0006\u0010\u000e\u001a\u00020\u00152\u0006\u0010\u0012\u001a\u00020\u0013H\u0002\u001a\"\u0010\u0016\u001a\u00020\u00062\u0006\u0010\u000e\u001a\u00020\u00172\b\u0010\u0010\u001a\u0004\u0018\u00010\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0002\u001a%\u0010\u0018\u001a\u00020\u00062\u0016\u0010\u0019\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00060\u001a\"\u0004\u0018\u00010\u0006H\u0002¢\u0006\u0002\u0010\u001b\u001a(\u0010\u001c\u001a\u00020\u00062\f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u001f0\u001e2\b\u0010\u0010\u001a\u0004\u0018\u00010\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0002\u001a\"\u0010 \u001a\u00020\u00062\u0006\u0010\u000e\u001a\u00020!2\b\u0010\u0010\u001a\u0004\u0018\u00010\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0002\u001a/\u0010\"\u001a\u00020\u000b*\u00060\tj\u0002`\n2\u0006\u0010\u000e\u001a\u00020#2\u0006\u0010\u0012\u001a\u00020\u00132\f\u0010$\u001a\b\u0012\u0004\u0012\u00020\u000b0%H\u0082\b\u001an\u0010&\u001a\u00020\u000b\"\u0004\b\u0000\u0010'\"\f\b\u0001\u0010(*\u00060)j\u0002`**\u0002H(2\f\u0010+\u001a\b\u0012\u0004\u0012\u0002H'0,2\u0006\u0010-\u001a\u00020\u00062\u0006\u0010.\u001a\u00020\u00062\u0006\u0010/\u001a\u00020\u00062\u001d\u00100\u001a\u0019\u0012\u0004\u0012\u0002H(\u0012\u0004\u0012\u0002H'\u0012\u0004\u0012\u00020\u000b01¢\u0006\u0002\b\fH\u0080\bø\u0001\u0000¢\u0006\u0002\u00102\u001a\u0014\u00103\u001a\u00020\u0006*\u0002042\u0006\u00105\u001a\u000206H\u0002\u001a\u0014\u00107\u001a\u00020\u0006*\u0002082\b\b\u0002\u0010\u0012\u001a\u00020\u0013\u001a\u0014\u00107\u001a\u00020\u0006*\u0002092\b\b\u0002\u0010\u0012\u001a\u00020\u0013\u001a\u0014\u00107\u001a\u00020\u0006*\u00020\u00012\b\b\u0002\u0010\u0012\u001a\u00020\u0013\u001a\u0014\u00107\u001a\u00020\u0006*\u00020:2\b\b\u0002\u0010\u0012\u001a\u00020\u0013\u001a*\u0010;\u001a\u00020\u000b*\u00060\tj\u0002`\n2\u0006\u0010<\u001a\u00020\u001f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0002\u001a,\u0010=\u001a\u00020\u000b*\u00060\tj\u0002`\n2\b\u0010>\u001a\u0004\u0018\u0001082\b\u0010\u0010\u001a\u0004\u0018\u00010\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0002\u001a\f\u0010?\u001a\u00020\u0006*\u00020\u000fH\u0002\u001a\u0014\u0010@\u001a\u00020\u0006*\u00020\u000f2\u0006\u0010\u0012\u001a\u00020\u0013H\u0000\u001a\u0014\u0010A\u001a\u00020\u0006*\u00020B2\u0006\u0010\u0012\u001a\u00020\u0013H\u0000\u001a\f\u0010C\u001a\u00020\u0006*\u00020DH\u0002\u001a \u0010E\u001a\u00020\u000b*\u00020#2\n\u0010F\u001a\u00060\tj\u0002`\n2\u0006\u0010\u0012\u001a\u00020\u0013H\u0002\u001a \u0010G\u001a\u00020\u000b*\u00020#2\n\u0010F\u001a\u00060\tj\u0002`\n2\u0006\u0010\u0012\u001a\u00020\u0013H\u0002\u001a\u0014\u0010H\u001a\u00020\u0006*\u00020I2\u0006\u0010J\u001a\u00020KH\u0000\u001a\f\u0010L\u001a\u00020\u0006*\u00020\u0017H\u0002\u001a\f\u0010M\u001a\u00020\u0006*\u00020NH\u0002\u001a\f\u0010O\u001a\u00020\u0006*\u00020#H\u0000\u001a\f\u0010P\u001a\u00020\u0006*\u00020QH\u0002\u001a\u001e\u0010R\u001a\u00020\u0006*\u00020\u00022\b\u0010\u0010\u001a\u0004\u0018\u00010\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0002\u001a\u0014\u0010S\u001a\u00020\u0006*\u00020T2\u0006\u0010\u0012\u001a\u00020\u0013H\u0000\u001a\u0014\u0010U\u001a\u00020\u0006*\u00020V2\u0006\u0010W\u001a\u00020XH\u0002\u001a\f\u0010Y\u001a\u00020\u0006*\u00020ZH\u0002\u001a\u001e\u0010[\u001a\u00020\u0006*\u00020\\2\b\u0010\u0010\u001a\u0004\u0018\u00010\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0002\u001a\f\u0010]\u001a\u00020\u0006*\u00020^H\u0002\u001a\u0014\u0010_\u001a\u00020\u0006*\u00020`2\u0006\u0010\u0012\u001a\u00020\u0013H\u0000\u001a\u001e\u0010a\u001a\u00020\u0006*\u00020:2\b\u0010\u0010\u001a\u0004\u0018\u00010\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0002\u001a\u001e\u0010b\u001a\u00020\u0006*\u00020\u00012\b\u0010\u0010\u001a\u0004\u0018\u00010\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0002\u001a\u0014\u0010c\u001a\u00020\u0006*\u00020!2\u0006\u0010\u0012\u001a\u00020\u0013H\u0000\u001a\f\u0010d\u001a\u00020\u0006*\u00020\u0002H\u0002\u001a\u001e\u0010e\u001a\u00020\u0006*\u00020\u00012\b\u0010\u0010\u001a\u0004\u0018\u00010\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0002\u001a\f\u0010f\u001a\u00020\u0006*\u00020gH\u0002\u001a\f\u0010h\u001a\u00020\u0006*\u000204H\u0002\u001a1\u0010i\u001a\u00020\u0006\"\u0004\b\u0000\u0010'*\u0002H'2\u0017\u0010\u0007\u001a\u0013\u0012\u0004\u0012\u0002H'\u0012\u0004\u0012\u00020\u00060\b¢\u0006\u0002\b\fH\u0082\b¢\u0006\u0002\u0010j\"\u001a\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00020\u00028BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006k"}, d2 = {"safeReturnType", "Lorg/jetbrains/kotlin/ir/types/IrType;", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "getSafeReturnType", "(Lorg/jetbrains/kotlin/ir/declarations/IrFunction;)Lorg/jetbrains/kotlin/ir/types/IrType;", "buildTrimEnd", "", "fn", "Lkotlin/Function1;", "Ljava/lang/StringBuilder;", "Lkotlin/text/StringBuilder;", "", "Lkotlin/ExtensionFunctionType;", "renderClassWithRenderer", "declaration", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "renderer", "Lorg/jetbrains/kotlin/ir/util/RenderIrElementVisitor;", "options", "Lorg/jetbrains/kotlin/ir/util/DumpIrTreeOptions;", "renderEnumEntry", "Lorg/jetbrains/kotlin/ir/declarations/IrEnumEntry;", "renderField", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "renderFlagsList", OverlayablePolicy.NAME_flags, "", "([Ljava/lang/String;)Ljava/lang/String;", "renderTypeAnnotations", "annotations", "", "Lorg/jetbrains/kotlin/ir/expressions/IrConstructorCall;", "renderTypeParameter", "Lorg/jetbrains/kotlin/ir/declarations/IrTypeParameter;", "appendDeclarationNameToFqName", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "fallback", "Lkotlin/Function0;", "appendIterableWith", ExifInterface.GPS_DIRECTION_TRUE, "Buffer", "Ljava/lang/Appendable;", "Lkotlin/text/Appendable;", "iterable", "", "prefix", "postfix", "separator", "renderItem", "Lkotlin/Function2;", "(Ljava/lang/Appendable;Ljava/lang/Iterable;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lkotlin/jvm/functions/Function2;)V", "normalizedName", "Lorg/jetbrains/kotlin/ir/declarations/IrVariable;", "data", "Lorg/jetbrains/kotlin/ir/util/VariableNameData;", "render", "Lorg/jetbrains/kotlin/ir/IrElement;", "Lorg/jetbrains/kotlin/ir/types/IrSimpleType;", "Lorg/jetbrains/kotlin/ir/types/IrTypeArgument;", "renderAsAnnotation", "irAnnotation", "renderAsAnnotationArgument", "irElement", "renderClassFlags", "renderClassFqn", "renderClassifierFqn", "Lorg/jetbrains/kotlin/ir/symbols/IrClassifierSymbol;", "renderConstructorFlags", "Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "renderDeclarationFqn", "sb", "renderDeclarationParentFqn", "renderDescriptor", "Lorg/jetbrains/kotlin/renderer/DescriptorRenderer;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "renderFieldFlags", "renderLocalDelegatedPropertyFlags", "Lorg/jetbrains/kotlin/ir/declarations/IrLocalDelegatedProperty;", "renderOriginIfNonTrivial", "renderPropertyFlags", "Lorg/jetbrains/kotlin/ir/declarations/IrProperty;", "renderReturnType", "renderScriptFqn", "Lorg/jetbrains/kotlin/ir/declarations/IrScript;", "renderSignatureIfEnabled", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationWithName;", "printSignatures", "", "renderSimpleFunctionFlags", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "renderTypeAbbreviation", "Lorg/jetbrains/kotlin/ir/types/IrTypeAbbreviation;", "renderTypeAliasFlags", "Lorg/jetbrains/kotlin/ir/declarations/IrTypeAlias;", "renderTypeAliasFqn", "Lorg/jetbrains/kotlin/ir/symbols/IrTypeAliasSymbol;", "renderTypeArgument", "renderTypeInner", "renderTypeParameterFqn", "renderTypeParameters", "renderTypeWithRenderer", "renderValueParameterFlags", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "renderVariableFlags", "runTrimEnd", "(Ljava/lang/Object;Lkotlin/jvm/functions/Function1;)Ljava/lang/String;", "ir.tree"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class RenderIrElementKt {
    public static /* synthetic */ String render$default(IrElement irElement, DumpIrTreeOptions dumpIrTreeOptions, int i, Object obj) {
        if ((i & 1) != 0) {
            dumpIrTreeOptions = new DumpIrTreeOptions(false, false, false, false, false, false, 63, null);
        }
        return render(irElement, dumpIrTreeOptions);
    }

    public static final String render(IrElement irElement, DumpIrTreeOptions options) {
        Intrinsics.checkNotNullParameter(irElement, "<this>");
        Intrinsics.checkNotNullParameter(options, "options");
        return (String) irElement.accept(new RenderIrElementVisitor(options), null);
    }

    public static final String renderDescriptor(DescriptorRenderer descriptorRenderer, DeclarationDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptorRenderer, "<this>");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        if (descriptor instanceof ReceiverParameterDescriptor) {
            ReceiverParameterDescriptor receiverParameterDescriptor = (ReceiverParameterDescriptor) descriptor;
            return "this@" + receiverParameterDescriptor.getPackageFragment().getShortName() + ": " + receiverParameterDescriptor.getType();
        }
        return descriptorRenderer.render(descriptor);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String renderSignatureIfEnabled(IrDeclarationWithName irDeclarationWithName, boolean z) {
        if (!z) {
            return "";
        }
        IdSignature signature = irDeclarationWithName.getSymbol().getSignature();
        String str = signature != null ? "signature:" + IdSignatureRendererKt.render$default(signature, null, 1, null) + StringUtil.SPACE : null;
        return str == null ? "" : str;
    }

    public static final String renderOriginIfNonTrivial(IrDeclaration irDeclaration) {
        Intrinsics.checkNotNullParameter(irDeclaration, "<this>");
        return !Intrinsics.areEqual(irDeclaration.getOrigin(), IrDeclarationOrigin.DEFINED.INSTANCE) ? new StringBuilder().append(irDeclaration.getOrigin()).append(StringUtil.SPACE).toString() : "";
    }

    public static final String renderClassifierFqn(IrClassifierSymbol irClassifierSymbol, DumpIrTreeOptions options) {
        Intrinsics.checkNotNullParameter(irClassifierSymbol, "<this>");
        Intrinsics.checkNotNullParameter(options, "options");
        if (irClassifierSymbol.isBound()) {
            IrSymbolOwner owner = irClassifierSymbol.getOwner();
            if (owner instanceof IrClass) {
                return renderClassFqn((IrClass) owner, options);
            }
            if (owner instanceof IrScript) {
                return renderScriptFqn((IrScript) owner, options);
            }
            return owner instanceof IrTypeParameter ? renderTypeParameterFqn((IrTypeParameter) owner, options) : "`unexpected classifier: " + render(owner, options) + '`';
        }
        return "<unbound " + irClassifierSymbol.getClass().getSimpleName() + '>';
    }

    public static final String renderTypeAliasFqn(IrTypeAliasSymbol irTypeAliasSymbol, DumpIrTreeOptions options) {
        Intrinsics.checkNotNullParameter(irTypeAliasSymbol, "<this>");
        Intrinsics.checkNotNullParameter(options, "options");
        if (irTypeAliasSymbol.isBound()) {
            StringBuilder sb = new StringBuilder();
            renderDeclarationFqn(irTypeAliasSymbol.getOwner(), sb, options);
            String string = sb.toString();
            Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
            return string;
        }
        return "<unbound " + irTypeAliasSymbol + '>';
    }

    public static final String renderClassFqn(IrClass irClass, DumpIrTreeOptions options) {
        Intrinsics.checkNotNullParameter(irClass, "<this>");
        Intrinsics.checkNotNullParameter(options, "options");
        StringBuilder sb = new StringBuilder();
        renderDeclarationFqn(irClass, sb, options);
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return string;
    }

    public static final String renderScriptFqn(IrScript irScript, DumpIrTreeOptions options) {
        Intrinsics.checkNotNullParameter(irScript, "<this>");
        Intrinsics.checkNotNullParameter(options, "options");
        StringBuilder sb = new StringBuilder();
        renderDeclarationFqn(irScript, sb, options);
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return string;
    }

    public static final String renderTypeParameterFqn(IrTypeParameter irTypeParameter, DumpIrTreeOptions options) {
        Intrinsics.checkNotNullParameter(irTypeParameter, "<this>");
        Intrinsics.checkNotNullParameter(options, "options");
        StringBuilder sb = new StringBuilder();
        sb.append(irTypeParameter.getName().asString());
        sb.append(" of ");
        renderDeclarationParentFqn(irTypeParameter, sb, options);
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return string;
    }

    private static final void renderDeclarationFqn(IrDeclaration irDeclaration, StringBuilder sb, DumpIrTreeOptions dumpIrTreeOptions) {
        renderDeclarationParentFqn(irDeclaration, sb, dumpIrTreeOptions);
        if (!IrUtilsKt.isFileClass(irDeclaration) || dumpIrTreeOptions.getPrintFacadeClassInFqNames()) {
            sb.append('.');
            if (!(irDeclaration instanceof IrDeclarationWithName)) {
                sb.append(irDeclaration);
            } else {
                sb.append(((IrDeclarationWithName) irDeclaration).getName());
            }
        }
    }

    private static final void renderDeclarationParentFqn(IrDeclaration irDeclaration, StringBuilder sb, DumpIrTreeOptions dumpIrTreeOptions) {
        try {
            IrDeclarationParent parent = irDeclaration.getParent();
            if (parent instanceof IrDeclaration) {
                renderDeclarationFqn((IrDeclaration) parent, sb, dumpIrTreeOptions);
            } else if (parent instanceof IrPackageFragment) {
                sb.append(((IrPackageFragment) parent).getPackageFqName().toString());
            }
        } catch (UninitializedPropertyAccessException unused) {
            sb.append("<uninitialized parent>");
        }
    }

    public static /* synthetic */ String render$default(IrType irType, DumpIrTreeOptions dumpIrTreeOptions, int i, Object obj) {
        if ((i & 1) != 0) {
            dumpIrTreeOptions = new DumpIrTreeOptions(false, false, false, false, false, false, 63, null);
        }
        return render(irType, dumpIrTreeOptions);
    }

    public static final String render(IrType irType, DumpIrTreeOptions options) {
        Intrinsics.checkNotNullParameter(irType, "<this>");
        Intrinsics.checkNotNullParameter(options, "options");
        return renderTypeWithRenderer(irType, new RenderIrElementVisitor(options), options);
    }

    public static final String render(IrSimpleType irSimpleType, DumpIrTreeOptions options) {
        Intrinsics.checkNotNullParameter(irSimpleType, "<this>");
        Intrinsics.checkNotNullParameter(options, "options");
        return render((IrType) irSimpleType, options);
    }

    public static /* synthetic */ String render$default(IrSimpleType irSimpleType, DumpIrTreeOptions dumpIrTreeOptions, int i, Object obj) {
        if ((i & 1) != 0) {
            dumpIrTreeOptions = new DumpIrTreeOptions(false, false, false, false, false, false, 63, null);
        }
        return render(irSimpleType, dumpIrTreeOptions);
    }

    public static /* synthetic */ String render$default(IrTypeArgument irTypeArgument, DumpIrTreeOptions dumpIrTreeOptions, int i, Object obj) {
        if ((i & 1) != 0) {
            dumpIrTreeOptions = new DumpIrTreeOptions(false, false, false, false, false, false, 63, null);
        }
        return render(irTypeArgument, dumpIrTreeOptions);
    }

    public static final String render(IrTypeArgument irTypeArgument, DumpIrTreeOptions options) {
        Intrinsics.checkNotNullParameter(irTypeArgument, "<this>");
        Intrinsics.checkNotNullParameter(options, "options");
        if (irTypeArgument instanceof IrStarProjection) {
            return "*";
        }
        if (!(irTypeArgument instanceof IrTypeProjection)) {
            throw new NoWhenBranchMatchedException();
        }
        IrTypeProjection irTypeProjection = (IrTypeProjection) irTypeArgument;
        return irTypeProjection.getVariance() + StringUtil.SPACE + render(irTypeProjection.getType(), options);
    }

    public static final <T, Buffer extends Appendable> void appendIterableWith(Buffer buffer, Iterable<? extends T> iterable, String prefix, String postfix, String separator, Function2<? super Buffer, ? super T, Unit> renderItem) throws IOException {
        Intrinsics.checkNotNullParameter(buffer, "<this>");
        Intrinsics.checkNotNullParameter(iterable, "iterable");
        Intrinsics.checkNotNullParameter(prefix, "prefix");
        Intrinsics.checkNotNullParameter(postfix, "postfix");
        Intrinsics.checkNotNullParameter(separator, "separator");
        Intrinsics.checkNotNullParameter(renderItem, "renderItem");
        buffer.append(prefix);
        boolean z = true;
        for (T t : iterable) {
            if (!z) {
                buffer.append(separator);
            }
            renderItem.invoke(buffer, t);
            z = false;
        }
        buffer.append(postfix);
    }

    private static final String renderFlagsList(String... strArr) {
        List listFilterNotNull = ArraysKt.filterNotNull(strArr);
        if (!listFilterNotNull.isEmpty()) {
            return CollectionsKt.joinToString$default(listFilterNotNull, ",", "[", "] ", 0, null, null, 56, null);
        }
        return "";
    }

    private static final String renderClassFlags(IrClass irClass) {
        String[] strArr = new String[7];
        strArr[0] = irClass.getIsCompanion() ? "companion" : null;
        strArr[1] = irClass.getIsInner() ? "inner" : null;
        strArr[2] = irClass.getIsData() ? "data" : null;
        strArr[3] = irClass.getIsExternal() ? "external" : null;
        strArr[4] = irClass.getIsValue() ? "value" : null;
        strArr[5] = irClass.getIsExpect() ? "expect" : null;
        strArr[6] = irClass.getIsFun() ? "fun" : null;
        return renderFlagsList(strArr);
    }

    private static final String renderFieldFlags(IrField irField) {
        String[] strArr = new String[3];
        strArr[0] = irField.getIsFinal() ? "final" : null;
        strArr[1] = irField.getIsExternal() ? "external" : null;
        strArr[2] = irField.getIsStatic() ? "static" : null;
        return renderFlagsList(strArr);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String renderSimpleFunctionFlags(IrSimpleFunction irSimpleFunction) {
        String[] strArr = new String[8];
        strArr[0] = irSimpleFunction.getIsTailrec() ? "tailrec" : null;
        strArr[1] = irSimpleFunction.getIsInline() ? "inline" : null;
        strArr[2] = irSimpleFunction.getIsExternal() ? "external" : null;
        strArr[3] = irSimpleFunction.getIsSuspend() ? "suspend" : null;
        strArr[4] = irSimpleFunction.getIsExpect() ? "expect" : null;
        strArr[5] = irSimpleFunction.getIsFakeOverride() ? "fake_override" : null;
        strArr[6] = irSimpleFunction.getIsOperator() ? "operator" : null;
        strArr[7] = irSimpleFunction.getIsInfix() ? "infix" : null;
        return renderFlagsList(strArr);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String renderConstructorFlags(IrConstructor irConstructor) {
        String[] strArr = new String[4];
        strArr[0] = irConstructor.getIsInline() ? "inline" : null;
        strArr[1] = irConstructor.getIsExternal() ? "external" : null;
        strArr[2] = irConstructor.getIsPrimary() ? "primary" : null;
        strArr[3] = irConstructor.getIsExpect() ? "expect" : null;
        return renderFlagsList(strArr);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String renderPropertyFlags(IrProperty irProperty) {
        String[] strArr = new String[7];
        strArr[0] = irProperty.getIsExternal() ? "external" : null;
        strArr[1] = irProperty.getIsConst() ? PsiKeyword.CONST : null;
        strArr[2] = irProperty.getIsLateinit() ? "lateinit" : null;
        strArr[3] = irProperty.getIsDelegated() ? "delegated" : null;
        strArr[4] = irProperty.getIsExpect() ? "expect" : null;
        strArr[5] = irProperty.getIsFakeOverride() ? "fake_override" : null;
        strArr[6] = irProperty.getIsVar() ? PsiKeyword.VAR : "val";
        return renderFlagsList(strArr);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String renderVariableFlags(IrVariable irVariable) {
        String[] strArr = new String[3];
        strArr[0] = irVariable.getIsConst() ? PsiKeyword.CONST : null;
        strArr[1] = irVariable.getIsLateinit() ? "lateinit" : null;
        strArr[2] = irVariable.getIsVar() ? PsiKeyword.VAR : "val";
        return renderFlagsList(strArr);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String renderValueParameterFlags(IrValueParameter irValueParameter) {
        String[] strArr = new String[4];
        strArr[0] = irValueParameter.getVarargElementType() != null ? "vararg" : null;
        strArr[1] = irValueParameter.getIsCrossinline() ? "crossinline" : null;
        strArr[2] = irValueParameter.getIsNoinline() ? "noinline" : null;
        strArr[3] = irValueParameter.getIsAssignable() ? "assignable" : null;
        return renderFlagsList(strArr);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String renderTypeAliasFlags(IrTypeAlias irTypeAlias) {
        String[] strArr = new String[1];
        strArr[0] = irTypeAlias.getIsActual() ? "actual" : null;
        return renderFlagsList(strArr);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String renderTypeParameters(IrFunction irFunction) {
        return CollectionsKt.joinToString$default(irFunction.getTypeParameters(), ", ", "<", ">", 0, null, new Function1<IrTypeParameter, CharSequence>() { // from class: org.jetbrains.kotlin.ir.util.RenderIrElementKt.renderTypeParameters.1
            @Override // kotlin.jvm.functions.Function1
            public final CharSequence invoke(IrTypeParameter it) {
                Intrinsics.checkNotNullParameter(it, "it");
                String string = it.getName().toString();
                Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
                return string;
            }
        }, 24, null);
    }

    private static final IrType getSafeReturnType(IrFunction irFunction) {
        try {
            return irFunction.getReturnType();
        } catch (ReturnTypeIsNotInitializedException unused) {
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String renderLocalDelegatedPropertyFlags(IrLocalDelegatedProperty irLocalDelegatedProperty) {
        return irLocalDelegatedProperty.getIsVar() ? PsiKeyword.VAR : "val";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String normalizedName(IrVariable irVariable, VariableNameData variableNameData) {
        if (variableNameData.getNormalizeNames() && (Intrinsics.areEqual(irVariable.getOrigin(), IrDeclarationOrigin.IR_TEMPORARY_VARIABLE.INSTANCE) || Intrinsics.areEqual(irVariable.getOrigin(), IrDeclarationOrigin.FOR_LOOP_ITERATOR.INSTANCE))) {
            Map<IrVariableSymbol, String> nameMap = variableNameData.getNameMap();
            IrVariableSymbol symbol = irVariable.getSymbol();
            String string = nameMap.get(symbol);
            if (string == null) {
                StringBuilder sb = new StringBuilder("tmp_");
                int temporaryIndex = variableNameData.getTemporaryIndex();
                variableNameData.setTemporaryIndex(temporaryIndex + 1);
                string = sb.append(temporaryIndex).toString();
                nameMap.put(symbol, string);
            }
            return string;
        }
        String strAsString = irVariable.getName().asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        return strAsString;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String renderReturnType(IrFunction irFunction, RenderIrElementVisitor renderIrElementVisitor, DumpIrTreeOptions dumpIrTreeOptions) {
        String strRenderTypeWithRenderer;
        IrType safeReturnType = getSafeReturnType(irFunction);
        return (safeReturnType == null || (strRenderTypeWithRenderer = renderTypeWithRenderer(safeReturnType, renderIrElementVisitor, dumpIrTreeOptions)) == null) ? "<Uninitialized>" : strRenderTypeWithRenderer;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String renderTypeWithRenderer(IrType irType, RenderIrElementVisitor renderIrElementVisitor, DumpIrTreeOptions dumpIrTreeOptions) {
        return renderTypeAnnotations(irType.getAnnotations(), renderIrElementVisitor, dumpIrTreeOptions) + renderTypeInner(irType, renderIrElementVisitor, dumpIrTreeOptions);
    }

    private static final String renderTypeInner(IrType irType, final RenderIrElementVisitor renderIrElementVisitor, final DumpIrTreeOptions dumpIrTreeOptions) {
        if (irType instanceof IrDynamicType) {
            return "dynamic";
        }
        if (irType instanceof IrErrorType) {
            return "IrErrorType(" + (dumpIrTreeOptions.getVerboseErrorTypes() ? IrTypeBaseKt.getOriginalKotlinType(irType) : null) + ')';
        }
        if (!(irType instanceof IrSimpleType)) {
            return "{" + irType.getClass().getSimpleName() + StringUtil.SPACE + irType + '}';
        }
        StringBuilder sb = new StringBuilder();
        IrSimpleType irSimpleType = (IrSimpleType) irType;
        boolean z = (irSimpleType.getClassifier() instanceof IrTypeParameterSymbol) && irSimpleType.getNullability() == SimpleTypeNullability.DEFINITELY_NOT_NULL;
        if (z) {
            sb.append("{");
        }
        sb.append(renderClassifierFqn(irSimpleType.getClassifier(), dumpIrTreeOptions));
        if (!irSimpleType.getArguments().isEmpty()) {
            sb.append(CollectionsKt.joinToString$default(irSimpleType.getArguments(), ", ", "<", ">", 0, null, new Function1<IrTypeArgument, CharSequence>() { // from class: org.jetbrains.kotlin.ir.util.RenderIrElementKt$renderTypeInner$2$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final CharSequence invoke(IrTypeArgument it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return RenderIrElementKt.renderTypeArgument(it, renderIrElementVisitor, dumpIrTreeOptions);
                }
            }, 24, null));
        }
        if (z) {
            sb.append(" & Any}");
        } else if (IrTypePredicatesKt.isMarkedNullable(irSimpleType)) {
            sb.append(MangleConstant.Q_MARK);
        }
        IrTypeAbbreviation abbreviation = irSimpleType.getAbbreviation();
        if (abbreviation != null) {
            sb.append(renderTypeAbbreviation(abbreviation, renderIrElementVisitor, dumpIrTreeOptions));
        }
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return StringsKt.trimEnd((CharSequence) string).toString();
    }

    private static final String renderTypeAbbreviation(IrTypeAbbreviation irTypeAbbreviation, final RenderIrElementVisitor renderIrElementVisitor, final DumpIrTreeOptions dumpIrTreeOptions) {
        StringBuilder sb = new StringBuilder("{ ");
        sb.append(renderTypeAnnotations(irTypeAbbreviation.getAnnotations(), renderIrElementVisitor, dumpIrTreeOptions));
        sb.append(renderTypeAliasFqn(irTypeAbbreviation.getTypeAlias(), dumpIrTreeOptions));
        if (!irTypeAbbreviation.getArguments().isEmpty()) {
            sb.append(CollectionsKt.joinToString$default(irTypeAbbreviation.getArguments(), ", ", "<", ">", 0, null, new Function1<IrTypeArgument, CharSequence>() { // from class: org.jetbrains.kotlin.ir.util.RenderIrElementKt$renderTypeAbbreviation$1$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final CharSequence invoke(IrTypeArgument it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return RenderIrElementKt.renderTypeArgument(it, renderIrElementVisitor, dumpIrTreeOptions);
                }
            }, 24, null));
        }
        if (irTypeAbbreviation.getHasQuestionMark()) {
            sb.append(MangleConstant.Q_MARK);
        }
        sb.append(" }");
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return string;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String renderTypeArgument(IrTypeArgument irTypeArgument, RenderIrElementVisitor renderIrElementVisitor, DumpIrTreeOptions dumpIrTreeOptions) {
        if (irTypeArgument instanceof IrStarProjection) {
            return "*";
        }
        if (!(irTypeArgument instanceof IrTypeProjection)) {
            throw new NoWhenBranchMatchedException();
        }
        StringBuilder sb = new StringBuilder();
        IrTypeProjection irTypeProjection = (IrTypeProjection) irTypeArgument;
        sb.append(irTypeProjection.getVariance().getLabel());
        if (irTypeProjection.getVariance() != Variance.INVARIANT) {
            sb.append(StringUtil.SPACE);
        }
        sb.append(renderTypeWithRenderer(irTypeProjection.getType(), renderIrElementVisitor, dumpIrTreeOptions));
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return StringsKt.trimEnd((CharSequence) string).toString();
    }

    private static final String renderTypeAnnotations(List<? extends IrConstructorCall> list, RenderIrElementVisitor renderIrElementVisitor, DumpIrTreeOptions dumpIrTreeOptions) throws IOException {
        if (list.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        StringBuilder sb2 = sb;
        sb2.append((CharSequence) "");
        boolean z = true;
        for (Object obj : list) {
            if (!z) {
                sb2.append((CharSequence) " ");
            }
            StringBuilder sb3 = sb2;
            sb3.append("@[");
            renderAsAnnotation(sb3, (IrConstructorCall) obj, renderIrElementVisitor, dumpIrTreeOptions);
            sb3.append("]");
            z = false;
        }
        sb2.append((CharSequence) " ");
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return string;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void renderAsAnnotation(StringBuilder sb, final IrConstructorCall irConstructorCall, final RenderIrElementVisitor renderIrElementVisitor, final DumpIrTreeOptions dumpIrTreeOptions) throws IOException {
        String strAsString;
        IrConstructor owner;
        IrClass parentAsClass;
        Name name2;
        IrConstructorSymbol symbol = irConstructorCall.getSymbol();
        if (!symbol.isBound()) {
            symbol = null;
        }
        if (symbol == null || (owner = symbol.getOwner()) == null || (parentAsClass = IrUtilsKt.getParentAsClass(owner)) == null || (name2 = parentAsClass.getName()) == null || (strAsString = name2.asString()) == null) {
            strAsString = "<unbound>";
        }
        sb.append(strAsString);
        if (irConstructorCall.getTypeArgumentsCount() != 0) {
            CollectionsKt.joinTo(RangesKt.until(0, irConstructorCall.getTypeArgumentsCount()), sb, (112 & 2) != 0 ? ", " : ", ", (112 & 4) != 0 ? "" : "<", (112 & 8) != 0 ? "" : ">", (112 & 16) != 0 ? -1 : 0, (112 & 32) != 0 ? "..." : null, (112 & 64) != 0 ? null : new Function1<Integer, CharSequence>() { // from class: org.jetbrains.kotlin.ir.util.RenderIrElementKt.renderAsAnnotation.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ CharSequence invoke(Integer num) {
                    return invoke(num.intValue());
                }

                public final CharSequence invoke(int i) {
                    String strRenderTypeWithRenderer;
                    IrType typeArgument = irConstructorCall.getTypeArgument(i);
                    if (typeArgument == null || (strRenderTypeWithRenderer = RenderIrElementKt.renderTypeWithRenderer(typeArgument, renderIrElementVisitor, dumpIrTreeOptions)) == null) {
                        strRenderTypeWithRenderer = "null";
                    }
                    return strRenderTypeWithRenderer;
                }
            });
        }
        if (irConstructorCall.getValueArgumentsCount() == 0) {
            return;
        }
        List<String> valueParameterNamesForDebug = DumpIrTreeKt.getValueParameterNamesForDebug(irConstructorCall);
        IntRange intRangeUntil = RangesKt.until(0, irConstructorCall.getValueArgumentsCount());
        StringBuilder sb2 = sb;
        sb2.append((CharSequence) "(");
        Iterator<Integer> it = intRangeUntil.iterator();
        boolean z = true;
        while (it.hasNext()) {
            int iNextInt = ((IntIterator) it).nextInt();
            if (!z) {
                sb2.append((CharSequence) ", ");
            }
            StringBuilder sb3 = sb2;
            sb3.append(valueParameterNamesForDebug.get(iNextInt));
            sb3.append(" = ");
            renderAsAnnotationArgument(sb3, irConstructorCall.getValueArgument(iNextInt), renderIrElementVisitor, dumpIrTreeOptions);
            z = false;
        }
        sb2.append((CharSequence) Constant.AFTER_QUTO);
    }

    private static final void renderAsAnnotationArgument(StringBuilder sb, IrElement irElement, RenderIrElementVisitor renderIrElementVisitor, DumpIrTreeOptions dumpIrTreeOptions) throws IOException {
        if (irElement == null) {
            sb.append("<null>");
            return;
        }
        if (irElement instanceof IrConstructorCall) {
            renderAsAnnotation(sb, (IrConstructorCall) irElement, renderIrElementVisitor, dumpIrTreeOptions);
            return;
        }
        if (irElement instanceof IrConst) {
            sb.append('\'');
            sb.append(String.valueOf(((IrConst) irElement).getValue()));
            sb.append('\'');
            return;
        }
        if (!(irElement instanceof IrVararg)) {
            if (renderIrElementVisitor != null) {
                sb.append((String) irElement.accept(renderIrElementVisitor, null));
                return;
            } else {
                sb.append("...");
                return;
            }
        }
        StringBuilder sb2 = sb;
        List<IrVarargElement> elements = ((IrVararg) irElement).getElements();
        sb2.append((CharSequence) "[");
        boolean z = true;
        for (Object obj : elements) {
            if (!z) {
                sb2.append((CharSequence) ", ");
            }
            renderAsAnnotationArgument(sb2, (IrVarargElement) obj, renderIrElementVisitor, dumpIrTreeOptions);
            z = false;
        }
        sb2.append((CharSequence) "]");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String renderClassWithRenderer(IrClass irClass, final RenderIrElementVisitor renderIrElementVisitor, final DumpIrTreeOptions dumpIrTreeOptions) {
        return StringsKt.trimEnd((CharSequence) ("CLASS " + renderOriginIfNonTrivial(irClass) + irClass.getKind() + " name:" + irClass.getName() + StringUtil.SPACE + renderSignatureIfEnabled(irClass, dumpIrTreeOptions.getPrintSignatures()) + "modality:" + irClass.getModality() + " visibility:" + irClass.getVisibility() + StringUtil.SPACE + renderClassFlags(irClass) + "superTypes:[" + CollectionsKt.joinToString$default(irClass.getSuperTypes(), "; ", null, null, 0, null, new Function1<IrType, CharSequence>() { // from class: org.jetbrains.kotlin.ir.util.RenderIrElementKt$renderClassWithRenderer$1$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final CharSequence invoke(IrType it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return RenderIrElementKt.renderTypeWithRenderer(it, renderIrElementVisitor, dumpIrTreeOptions);
            }
        }, 30, null) + ']')).toString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String renderEnumEntry(IrEnumEntry irEnumEntry, DumpIrTreeOptions dumpIrTreeOptions) {
        return StringsKt.trimEnd((CharSequence) ("ENUM_ENTRY " + renderOriginIfNonTrivial(irEnumEntry) + "name:" + irEnumEntry.getName() + StringUtil.SPACE + renderSignatureIfEnabled(irEnumEntry, dumpIrTreeOptions.getPrintSignatures()))).toString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String renderField(IrField irField, RenderIrElementVisitor renderIrElementVisitor, DumpIrTreeOptions dumpIrTreeOptions) {
        return StringsKt.trimEnd((CharSequence) ("FIELD " + renderOriginIfNonTrivial(irField) + "name:" + irField.getName() + StringUtil.SPACE + renderSignatureIfEnabled(irField, dumpIrTreeOptions.getPrintSignatures()) + "type:" + renderTypeWithRenderer(irField.getType(), renderIrElementVisitor, dumpIrTreeOptions) + " visibility:" + irField.getVisibility() + StringUtil.SPACE + renderFieldFlags(irField))).toString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String renderTypeParameter(IrTypeParameter irTypeParameter, final RenderIrElementVisitor renderIrElementVisitor, final DumpIrTreeOptions dumpIrTreeOptions) {
        return StringsKt.trimEnd((CharSequence) ("TYPE_PARAMETER " + renderOriginIfNonTrivial(irTypeParameter) + "name:" + irTypeParameter.getName() + " index:" + irTypeParameter.getIndex() + " variance:" + irTypeParameter.getVariance() + StringUtil.SPACE + renderSignatureIfEnabled(irTypeParameter, dumpIrTreeOptions.getPrintSignatures()) + "superTypes:[" + CollectionsKt.joinToString$default(irTypeParameter.getSuperTypes(), "; ", null, null, 0, null, new Function1<IrType, CharSequence>() { // from class: org.jetbrains.kotlin.ir.util.RenderIrElementKt$renderTypeParameter$1$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final CharSequence invoke(IrType it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return RenderIrElementKt.renderTypeWithRenderer(it, renderIrElementVisitor, dumpIrTreeOptions);
            }
        }, 30, null) + "] reified:" + irTypeParameter.getIsReified())).toString();
    }
}
