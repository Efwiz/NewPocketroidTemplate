package org.jetbrains.kotlin.ir.backend.js.lower;

import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.backend.common.DeclarationTransformer;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.ir.backend.js.JsIrBackendContext;
import org.jetbrains.kotlin.ir.backend.js.export.ExportModelGeneratorKt;
import org.jetbrains.kotlin.ir.backend.js.ir.JsIrBuilder;
import org.jetbrains.kotlin.ir.backend.js.utils.IrJsUtilsKt;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.expressions.IrBody;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrSyntheticBody;
import org.jetbrains.kotlin.ir.expressions.IrSyntheticBodyKind;
import org.jetbrains.kotlin.ir.symbols.IrPropertySymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;

/* JADX INFO: compiled from: ExcludeSyntheticDeclarationsFromExportLowering.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u0007\u001a\u00020\bH\u0002J\u0018\u0010\t\u001a\n\u0012\u0004\u0012\u00020\u000b\u0018\u00010\n2\u0006\u0010\f\u001a\u00020\u000bH\u0016J\f\u0010\r\u001a\u00020\u000e*\u00020\u000bH\u0002J\f\u0010\u000f\u001a\u00020\u0010*\u00020\u0010H\u0002J\f\u0010\u0011\u001a\u00020\u0012*\u00020\u000bH\u0002J\f\u0010\u0013\u001a\u00020\u0012*\u00020\u000bH\u0002J\f\u0010\u0014\u001a\u00020\u0012*\u00020\u000bH\u0002R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/ExcludeSyntheticDeclarationsFromExportLowering;", "Lorg/jetbrains/kotlin/backend/common/DeclarationTransformer;", "context", "Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;", "(Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;)V", "getContext", "()Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;", "generateJsExportIgnoreCall", "Lorg/jetbrains/kotlin/ir/expressions/IrConstructorCall;", "transformFlat", "", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "declaration", "excludeFromJsExport", "", "getOriginalFunction", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "isComponentMethodOfDataClass", "", "isExportedSyntheticEnumEntriesProperty", "shouldBeExcludedFromExport", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ExcludeSyntheticDeclarationsFromExportLowering implements DeclarationTransformer {
    private final JsIrBackendContext context;

    public ExcludeSyntheticDeclarationsFromExportLowering(JsIrBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
    }

    public final JsIrBackendContext getContext() {
        return this.context;
    }

    @Override // org.jetbrains.kotlin.backend.common.DeclarationTransformer
    public List<IrDeclaration> transformFlat(IrDeclaration declaration) {
        IrPropertySymbol correspondingPropertySymbol;
        IrProperty owner;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        if (!shouldBeExcludedFromExport(declaration)) {
            return null;
        }
        if ((declaration instanceof IrSimpleFunction) && (correspondingPropertySymbol = ((IrSimpleFunction) declaration).getCorrespondingPropertySymbol()) != null && (owner = correspondingPropertySymbol.getOwner()) != null) {
            excludeFromJsExport(owner);
        }
        excludeFromJsExport(declaration);
        return null;
    }

    private final boolean shouldBeExcludedFromExport(IrDeclaration irDeclaration) {
        return isExportedSyntheticEnumEntriesProperty(irDeclaration) || isComponentMethodOfDataClass(irDeclaration);
    }

    private final boolean isComponentMethodOfDataClass(IrDeclaration irDeclaration) {
        if (!(irDeclaration instanceof IrSimpleFunction)) {
            return false;
        }
        IrSimpleFunction originalFunction = getOriginalFunction((IrSimpleFunction) irDeclaration);
        IrClass parentClassOrNull = IrUtilsKt.getParentClassOrNull(originalFunction);
        if (parentClassOrNull == null || !ExportModelGeneratorKt.isExported(parentClassOrNull, this.context) || !Intrinsics.areEqual(originalFunction.getOrigin(), IrDeclarationOrigin.GENERATED_DATA_CLASS_MEMBER.INSTANCE)) {
            return false;
        }
        String identifier = originalFunction.getName().getIdentifier();
        Intrinsics.checkNotNullExpressionValue(identifier, "getIdentifier(...)");
        return StringsKt.startsWith$default(identifier, StandardNames.DATA_CLASS_COMPONENT_PREFIX, false, 2, (Object) null);
    }

    private final boolean isExportedSyntheticEnumEntriesProperty(IrDeclaration irDeclaration) {
        IrClass parentEnumClassOrNull;
        if ((irDeclaration instanceof IrSimpleFunction) && (parentEnumClassOrNull = IrJsUtilsKt.getParentEnumClassOrNull(irDeclaration)) != null && ExportModelGeneratorKt.isExported(parentEnumClassOrNull, this.context)) {
            IrBody body = ((IrSimpleFunction) irDeclaration).getBody();
            IrSyntheticBody irSyntheticBody = body instanceof IrSyntheticBody ? (IrSyntheticBody) body : null;
            if ((irSyntheticBody != null ? irSyntheticBody.getKind() : null) == IrSyntheticBodyKind.ENUM_ENTRIES) {
                return true;
            }
        }
        return false;
    }

    private final void excludeFromJsExport(IrDeclaration irDeclaration) {
        irDeclaration.setAnnotations(CollectionsKt.plus((Collection<? extends IrConstructorCall>) irDeclaration.getAnnotations(), generateJsExportIgnoreCall()));
    }

    private final IrConstructorCall generateJsExportIgnoreCall() {
        JsIrBuilder jsIrBuilder = JsIrBuilder.INSTANCE;
        IrConstructor primaryConstructor = IrUtilsKt.getPrimaryConstructor(this.context.getIntrinsics().getJsExportIgnoreAnnotationSymbol().getOwner());
        Intrinsics.checkNotNull(primaryConstructor);
        return JsIrBuilder.buildConstructorCall$default(jsIrBuilder, primaryConstructor.getSymbol(), null, null, null, 14, null);
    }

    private final IrSimpleFunction getOriginalFunction(IrSimpleFunction irSimpleFunction) {
        return irSimpleFunction.getOverriddenSymbols().isEmpty() ? irSimpleFunction : getOriginalFunction(((IrSimpleFunctionSymbol) CollectionsKt.first((List) irSimpleFunction.getOverriddenSymbols())).getOwner());
    }
}
