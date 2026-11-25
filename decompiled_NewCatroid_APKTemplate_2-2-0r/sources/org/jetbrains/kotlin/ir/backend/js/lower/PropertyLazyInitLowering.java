package org.jetbrains.kotlin.ir.backend.js.lower;

import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.BodyLoweringPass;
import org.jetbrains.kotlin.com.intellij.psi.PsiTreeChangeEvent;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.ir.IrBuiltIns;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.backend.js.JsCommonBackendContext;
import org.jetbrains.kotlin.ir.backend.js.ir.JsIrBuilder;
import org.jetbrains.kotlin.ir.backend.js.utils.MiscKt;
import org.jetbrains.kotlin.ir.builders.declarations.DeclarationBuildersKt;
import org.jetbrains.kotlin.ir.builders.declarations.IrFieldBuilder;
import org.jetbrains.kotlin.ir.builders.declarations.IrFunctionBuilder;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationsKt;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrFactoryHelpersKt;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.expressions.IrBody;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: PropertyLazyInitLowering.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000l\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u0000 )2\u00020\u0001:\u0001)B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J$\u0010\u0016\u001a\u00020\u00172\u0012\u0010\u0018\u001a\u000e\u0012\u0004\u0012\u00020\u001a\u0012\u0004\u0012\u00020\u001b0\u00192\u0006\u0010\u001c\u001a\u00020\u001aH\u0002J\u0012\u0010\u001d\u001a\u0004\u0018\u00010\b2\u0006\u0010\u001e\u001a\u00020\u0007H\u0002J\u0018\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020$H\u0016J(\u0010%\u001a\u00020 *\u00020\b2\u0012\u0010\u0018\u001a\u000e\u0012\u0004\u0012\u00020\u001a\u0012\u0004\u0012\u00020\u001b0\u00192\u0006\u0010\u001c\u001a\u00020\u001aH\u0002J\u0014\u0010&\u001a\u00020\u001a*\u00020\u00132\u0006\u0010'\u001a\u00020(H\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\"\u0010\u0005\u001a\u0010\u0012\u0004\u0012\u00020\u0007\u0012\u0006\u0012\u0004\u0018\u00010\b0\u00068BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\nR \u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\f0\u00068BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\nR\u0014\u0010\u000e\u001a\u00020\u000f8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011R\u0014\u0010\u0012\u001a\u00020\u00138BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015¨\u0006*"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/PropertyLazyInitLowering;", "Lorg/jetbrains/kotlin/backend/common/BodyLoweringPass;", "context", "Lorg/jetbrains/kotlin/ir/backend/js/JsCommonBackendContext;", "(Lorg/jetbrains/kotlin/ir/backend/js/JsCommonBackendContext;)V", "fileToInitializationFuns", "", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "getFileToInitializationFuns", "()Ljava/util/Map;", "fileToInitializerPureness", "", "getFileToInitializerPureness", "irBuiltIns", "Lorg/jetbrains/kotlin/ir/IrBuiltIns;", "getIrBuiltIns", "()Lorg/jetbrains/kotlin/ir/IrBuiltIns;", "irFactory", "Lorg/jetbrains/kotlin/ir/declarations/IrFactory;", "getIrFactory", "()Lorg/jetbrains/kotlin/ir/declarations/IrFactory;", "buildBodyWithIfGuard", "Lorg/jetbrains/kotlin/ir/IrStatement;", "initializers", "", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "initializedField", "createInitializationFunction", "file", "lower", "", "irBody", "Lorg/jetbrains/kotlin/ir/expressions/IrBody;", "container", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "buildPropertiesInitializationBody", "createInitializationField", PsiTreeChangeEvent.PROP_FILE_NAME, "", "Companion", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PropertyLazyInitLowering implements BodyLoweringPass {
    private final JsCommonBackendContext context;

    public PropertyLazyInitLowering(JsCommonBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
    }

    private final IrBuiltIns getIrBuiltIns() {
        return this.context.getIrBuiltIns();
    }

    private final IrFactory getIrFactory() {
        return this.context.getIrFactory();
    }

    private final Map<IrFile, IrSimpleFunction> getFileToInitializationFuns() {
        return this.context.getPropertyLazyInitialization().getFileToInitializationFuns();
    }

    private final Map<IrFile, Boolean> getFileToInitializerPureness() {
        return this.context.getPropertyLazyInitialization().getFileToInitializerPureness();
    }

    @Override // org.jetbrains.kotlin.backend.common.BodyLoweringPass
    public void lower(IrBody irBody, IrDeclaration container) {
        Intrinsics.checkNotNullParameter(irBody, "irBody");
        Intrinsics.checkNotNullParameter(container, "container");
        if (this.context.getPropertyLazyInitialization().getEnabled()) {
            if (((container instanceof IrField) || (container instanceof IrSimpleFunction) || (container instanceof IrProperty)) && PropertyLazyInitLoweringKt.isCompatibleDeclaration(container, this.context)) {
                IrDeclarationParent parent = container.getParent();
                IrSimpleFunction irSimpleFunctionCreateInitializationFunction = null;
                IrFile irFile = parent instanceof IrFile ? (IrFile) parent : null;
                if (irFile == null) {
                    return;
                }
                if (getFileToInitializationFuns().containsKey(irFile)) {
                    irSimpleFunctionCreateInitializationFunction = getFileToInitializationFuns().get(irFile);
                } else if (!Intrinsics.areEqual((Object) getFileToInitializerPureness().get(irFile), (Object) true)) {
                    irSimpleFunctionCreateInitializationFunction = createInitializationFunction(irFile);
                    getFileToInitializationFuns().put(irFile, irSimpleFunctionCreateInitializationFunction);
                }
                if (irSimpleFunctionCreateInitializationFunction == null) {
                    return;
                }
                IrCall irCallBuildCall$default = JsIrBuilder.buildCall$default(JsIrBuilder.INSTANCE, irSimpleFunctionCreateInitializationFunction.getSymbol(), irSimpleFunctionCreateInitializationFunction.getReturnType(), null, Companion.PROPERTY_INIT_FUN_CALL.INSTANCE, null, 20, null);
                if (container instanceof IrSimpleFunction) {
                    MiscKt.prependFunctionCall(irBody, irCallBuildCall$default);
                }
            }
        }
    }

    private final IrSimpleFunction createInitializationFunction(IrFile file) {
        String name2 = IrDeclarationsKt.getName(file);
        Map<IrField, ? extends IrExpression> mapCalculateFieldToExpression = PropertyLazyInitLoweringKt.calculateFieldToExpression(CollectionsKt.toList(file.getDeclarations()), this.context);
        if (mapCalculateFieldToExpression.isEmpty()) {
            return null;
        }
        boolean zAllFieldsInFilePure = PropertyLazyInitLoweringKt.allFieldsInFilePure(mapCalculateFieldToExpression.values());
        getFileToInitializerPureness().put(file, Boolean.valueOf(zAllFieldsInFilePure));
        if (zAllFieldsInFilePure) {
            return null;
        }
        IrField irFieldCreateInitializationField = createInitializationField(getIrFactory(), name2);
        file.getDeclarations().add(irFieldCreateInitializationField);
        irFieldCreateInitializationField.setParent(file);
        IrFactory irFactory = getIrFactory();
        IrFunctionBuilder irFunctionBuilder = new IrFunctionBuilder();
        Name nameSpecial = Name.special("<init properties " + name2 + '>');
        Intrinsics.checkNotNullExpressionValue(nameSpecial, "special(...)");
        irFunctionBuilder.setName(nameSpecial);
        irFunctionBuilder.setStartOffset(-2);
        irFunctionBuilder.setEndOffset(-2);
        irFunctionBuilder.setReturnType(getIrBuiltIns().getUnitType());
        DescriptorVisibility INTERNAL = DescriptorVisibilities.INTERNAL;
        Intrinsics.checkNotNullExpressionValue(INTERNAL, "INTERNAL");
        irFunctionBuilder.setVisibility(INTERNAL);
        irFunctionBuilder.setOrigin(JsIrBuilder.SYNTHESIZED_DECLARATION.INSTANCE);
        IrSimpleFunction irSimpleFunctionBuildFunction = DeclarationBuildersKt.buildFunction(irFactory, irFunctionBuilder);
        IrFile irFile = file;
        irFile.getDeclarations().add(irSimpleFunctionBuildFunction);
        irSimpleFunctionBuildFunction.setParent(irFile);
        buildPropertiesInitializationBody(irSimpleFunctionBuildFunction, mapCalculateFieldToExpression, irFieldCreateInitializationField);
        return irSimpleFunctionBuildFunction;
    }

    private final void buildPropertiesInitializationBody(IrSimpleFunction irSimpleFunction, Map<IrField, ? extends IrExpression> map, IrField irField) {
        irSimpleFunction.setBody(IrFactoryHelpersKt.createBlockBody(getIrFactory(), -1, -1, (List<? extends IrStatement>) CollectionsKt.listOf(buildBodyWithIfGuard(map, irField))));
    }

    private final IrStatement buildBodyWithIfGuard(Map<IrField, ? extends IrExpression> initializers, IrField initializedField) {
        List listCreateListBuilder = CollectionsKt.createListBuilder();
        listCreateListBuilder.add(PropertyLazyInitLoweringKt.createIrSetField(initializedField, JsIrBuilder.INSTANCE.buildBoolean(this.context.getIrBuiltIns().getBooleanType(), true)));
        for (Map.Entry<IrField, ? extends IrExpression> entry : initializers.entrySet()) {
            listCreateListBuilder.add(PropertyLazyInitLoweringKt.createIrSetField(entry.getKey(), entry.getValue()));
        }
        listCreateListBuilder.add(JsIrBuilder.INSTANCE.buildBlock(getIrBuiltIns().getUnitType()));
        return JsIrBuilder.buildIfElse$default(JsIrBuilder.INSTANCE, getIrBuiltIns().getUnitType(), PropertyLazyInitLoweringKt.createIrGetField(initializedField), JsIrBuilder.INSTANCE.buildBlock(getIrBuiltIns().getUnitType()), JsIrBuilder.INSTANCE.buildComposite(getIrBuiltIns().getUnitType(), CollectionsKt.build(listCreateListBuilder)), 0, 0, 0, 0, 240, null);
    }

    private final IrField createInitializationField(IrFactory irFactory, String str) {
        IrFieldBuilder irFieldBuilder = new IrFieldBuilder();
        Name nameIdentifier = Name.identifier("properties initialized " + str);
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        irFieldBuilder.setName(nameIdentifier);
        irFieldBuilder.setType(getIrBuiltIns().getBooleanType());
        irFieldBuilder.setStatic(true);
        irFieldBuilder.setFinal(true);
        irFieldBuilder.setOrigin(JsIrBuilder.SYNTHESIZED_DECLARATION.INSTANCE);
        return DeclarationBuildersKt.buildField(irFactory, irFieldBuilder);
    }
}
