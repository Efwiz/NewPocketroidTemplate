package org.jetbrains.kotlin.ir.backend.js.lower;

import java.util.List;
import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.MutablePropertyReference2Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import org.jetbrains.kotlin.backend.common.DeclarationTransformer;
import org.jetbrains.kotlin.backend.common.Mapping;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.backend.js.JsCommonBackendContext;
import org.jetbrains.kotlin.ir.backend.js.ir.JsIrBuilder;
import org.jetbrains.kotlin.ir.builders.declarations.DeclarationBuildersKt;
import org.jetbrains.kotlin.ir.builders.declarations.IrFunctionBuilder;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrEnumEntry;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrFactoryHelpersKt;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrPossiblyExternalDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrValueDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.expressions.IrBlockBody;
import org.jetbrains.kotlin.ir.expressions.IrBody;
import org.jetbrains.kotlin.ir.expressions.IrExpressionBody;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.util.PatchDeclarationParentsKt;
import org.jetbrains.kotlin.ir.util.PatchDeclarationParentsVisitor;
import org.jetbrains.kotlin.ir.visitors.IrVisitorsKt;
import org.jetbrains.kotlin.utils.MemoryOptimizedCollectionUtilKt;

/* JADX INFO: compiled from: EnumClassLowering.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0018\u0010%\u001a\u00020\u00162\u0006\u0010&\u001a\u00020\u00162\u0006\u0010'\u001a\u00020\u000fH\u0002J\u0018\u0010(\u001a\n\u0012\u0004\u0012\u00020)\u0018\u00010\u00062\u0006\u0010*\u001a\u00020)H\u0016R \u0010\u0005\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\t0\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR3\u0010\u000e\u001a\u0004\u0018\u00010\r*\u00020\u000f2\b\u0010\f\u001a\u0004\u0018\u00010\r8B@BX\u0082\u008e\u0002¢\u0006\u0012\n\u0004\b\u0014\u0010\u0015\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013R3\u0010\u0017\u001a\u0004\u0018\u00010\u0016*\u00020\u00162\b\u0010\f\u001a\u0004\u0018\u00010\u00168B@BX\u0082\u008e\u0002¢\u0006\u0012\n\u0004\b\u001c\u0010\u0015\u001a\u0004\b\u0018\u0010\u0019\"\u0004\b\u001a\u0010\u001bR3\u0010\u001e\u001a\u0004\u0018\u00010\u001d*\u00020\u001f2\b\u0010\f\u001a\u0004\u0018\u00010\u001d8B@BX\u0082\u008e\u0002¢\u0006\u0012\n\u0004\b$\u0010\u0015\u001a\u0004\b \u0010!\"\u0004\b\"\u0010#¨\u0006+"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/EnumClassConstructorLowering;", "Lorg/jetbrains/kotlin/backend/common/DeclarationTransformer;", "context", "Lorg/jetbrains/kotlin/ir/backend/js/JsCommonBackendContext;", "(Lorg/jetbrains/kotlin/ir/backend/js/JsCommonBackendContext;)V", "additionalParameters", "", "Lkotlin/Pair;", "", "Lorg/jetbrains/kotlin/ir/types/IrType;", "getContext", "()Lorg/jetbrains/kotlin/ir/backend/js/JsCommonBackendContext;", "<set-?>", "Lorg/jetbrains/kotlin/ir/declarations/IrEnumEntry;", "correspondingEntry", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "getCorrespondingEntry", "(Lorg/jetbrains/kotlin/ir/declarations/IrClass;)Lorg/jetbrains/kotlin/ir/declarations/IrEnumEntry;", "setCorrespondingEntry", "(Lorg/jetbrains/kotlin/ir/declarations/IrClass;Lorg/jetbrains/kotlin/ir/declarations/IrEnumEntry;)V", "correspondingEntry$delegate", "Lorg/jetbrains/kotlin/backend/common/Mapping$Delegate;", "Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "newConstructor", "getNewConstructor", "(Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;)Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "setNewConstructor", "(Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;)V", "newConstructor$delegate", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "valueParameter", "Lorg/jetbrains/kotlin/ir/declarations/IrValueDeclaration;", "getValueParameter", "(Lorg/jetbrains/kotlin/ir/declarations/IrValueDeclaration;)Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "setValueParameter", "(Lorg/jetbrains/kotlin/ir/declarations/IrValueDeclaration;Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;)V", "valueParameter$delegate", "transformEnumConstructor", "enumConstructor", "enumClass", "transformFlat", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "declaration", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class EnumClassConstructorLowering implements DeclarationTransformer {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.mutableProperty2(new MutablePropertyReference2Impl(Reflection.getOrCreateKotlinClass(EnumClassConstructorLowering.class), "newConstructor", "getNewConstructor(Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;)Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;")), Reflection.mutableProperty2(new MutablePropertyReference2Impl(Reflection.getOrCreateKotlinClass(EnumClassConstructorLowering.class), "correspondingEntry", "getCorrespondingEntry(Lorg/jetbrains/kotlin/ir/declarations/IrClass;)Lorg/jetbrains/kotlin/ir/declarations/IrEnumEntry;")), Reflection.mutableProperty2(new MutablePropertyReference2Impl(Reflection.getOrCreateKotlinClass(EnumClassConstructorLowering.class), "valueParameter", "getValueParameter(Lorg/jetbrains/kotlin/ir/declarations/IrValueDeclaration;)Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;"))};
    private final List<Pair<String, IrType>> additionalParameters;
    private final JsCommonBackendContext context;

    /* JADX INFO: renamed from: correspondingEntry$delegate, reason: from kotlin metadata */
    private final Mapping.Delegate correspondingEntry;

    /* JADX INFO: renamed from: newConstructor$delegate, reason: from kotlin metadata */
    private final Mapping.Delegate newConstructor;

    /* JADX INFO: renamed from: valueParameter$delegate, reason: from kotlin metadata */
    private final Mapping.Delegate valueParameter;

    public EnumClassConstructorLowering(JsCommonBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.newConstructor = context.getMapping().getEnumConstructorToNewConstructor();
        this.correspondingEntry = context.getMapping().getEnumClassToCorrespondingEnumEntry();
        this.valueParameter = context.getMapping().getEnumConstructorOldToNewValueParameters();
        this.additionalParameters = CollectionsKt.listOf((Object[]) new Pair[]{TuplesKt.to("name", context.getIrBuiltIns().getStringType()), TuplesKt.to("ordinal", context.getIrBuiltIns().getIntType())});
    }

    public final JsCommonBackendContext getContext() {
        return this.context;
    }

    private final void setNewConstructor(IrConstructor irConstructor, IrConstructor irConstructor2) {
        this.newConstructor.setValue(irConstructor, $$delegatedProperties[0], irConstructor2);
    }

    private final void setCorrespondingEntry(IrClass irClass, IrEnumEntry irEnumEntry) {
        this.correspondingEntry.setValue(irClass, $$delegatedProperties[1], irEnumEntry);
    }

    private final void setValueParameter(IrValueDeclaration irValueDeclaration, IrValueParameter irValueParameter) {
        this.valueParameter.setValue(irValueDeclaration, $$delegatedProperties[2], irValueParameter);
    }

    @Override // org.jetbrains.kotlin.backend.common.DeclarationTransformer
    public List<IrDeclaration> transformFlat(IrDeclaration declaration) {
        IrEnumEntry irEnumEntry;
        IrClass correspondingClass;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        IrDeclarationParent parent = declaration.getParent();
        IrClass irClass = parent instanceof IrClass ? (IrClass) parent : null;
        if (irClass != null && IrUtilsKt.isEnumClass(irClass) && !irClass.getIsExpect() && !IrUtilsKt.isEffectivelyExternal((IrPossiblyExternalDeclaration) irClass)) {
            if (declaration instanceof IrConstructor) {
                return CollectionsKt.listOf(transformEnumConstructor((IrConstructor) declaration, irClass));
            }
            if ((declaration instanceof IrEnumEntry) && (correspondingClass = (irEnumEntry = (IrEnumEntry) declaration).getCorrespondingClass()) != null) {
                setCorrespondingEntry(correspondingClass, irEnumEntry);
            }
        }
        return null;
    }

    private final IrConstructor transformEnumConstructor(IrConstructor enumConstructor, final IrClass enumClass) {
        IrFactory irFactory = this.context.getIrFactory();
        IrFunctionBuilder irFunctionBuilder = new IrFunctionBuilder();
        IrConstructor irConstructor = enumConstructor;
        irFunctionBuilder.updateFrom((IrFunction) irConstructor);
        irFunctionBuilder.setReturnType(enumConstructor.getReturnType());
        final IrConstructor irConstructorBuildConstructor = DeclarationBuildersKt.buildConstructor(irFactory, irFunctionBuilder);
        irConstructorBuildConstructor.setParent(enumClass);
        int i = 0;
        for (Object obj : this.additionalParameters) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            Pair pair = (Pair) obj;
            irConstructorBuildConstructor.setValueParameters(MemoryOptimizedCollectionUtilKt.memoryOptimizedPlus(irConstructorBuildConstructor.getValueParameters(), JsIrBuilder.buildValueParameter$default(JsIrBuilder.INSTANCE, irConstructorBuildConstructor, (String) pair.component1(), i, (IrType) pair.component2(), false, null, 48, null)));
            i = i2;
        }
        IrUtilsKt.copyParameterDeclarationsFrom(irConstructorBuildConstructor, irConstructor);
        setNewConstructor(enumConstructor, irConstructorBuildConstructor);
        final IrBody body = enumConstructor.getBody();
        if (body != null) {
            irConstructorBuildConstructor.setBody(IrFactoryHelpersKt.createBlockBody(this.context.getIrFactory(), body.getStartOffset(), body.getEndOffset(), new Function1<IrBlockBody, Unit>() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.EnumClassConstructorLowering$transformEnumConstructor$2$2$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(IrBlockBody irBlockBody) {
                    invoke2(irBlockBody);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(IrBlockBody createBlockBody) {
                    Intrinsics.checkNotNullParameter(createBlockBody, "$this$createBlockBody");
                    List<IrStatement> statements = createBlockBody.getStatements();
                    IrBody irBody = body;
                    Intrinsics.checkNotNull(irBody, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrBlockBody");
                    CollectionsKt.addAll(statements, ((IrBlockBody) irBody).getStatements());
                    IrBlockBody irBlockBody = createBlockBody;
                    EnumClassLoweringKt.fixReferencesToConstructorParameters(this.getContext(), enumClass, irBlockBody);
                    IrVisitorsKt.acceptVoid(createBlockBody, new PatchDeclarationParentsVisitor(enumClass));
                    irConstructorBuildConstructor.setBody(irBlockBody);
                }
            }));
        }
        List<IrValueParameter> valueParameters = enumConstructor.getValueParameters();
        List<IrValueParameter> valueParameters2 = irConstructorBuildConstructor.getValueParameters();
        for (IrValueParameter irValueParameter : valueParameters) {
            Object obj2 = null;
            boolean z = false;
            for (Object obj3 : valueParameters2) {
                if (((IrValueParameter) obj3).getIndex() == irValueParameter.getIndex() + this.additionalParameters.size()) {
                    if (z) {
                        throw new IllegalArgumentException("Collection contains more than one matching element.");
                    }
                    z = true;
                    obj2 = obj3;
                }
            }
            if (z) {
                IrValueParameter irValueParameter2 = (IrValueParameter) obj2;
                setValueParameter(irValueParameter, irValueParameter2);
                IrExpressionBody defaultValue = irValueParameter.getDefaultValue();
                if (defaultValue != null) {
                    IrExpressionBody irExpressionBodyCreateExpressionBody = this.context.getIrFactory().createExpressionBody(defaultValue.getStartOffset(), defaultValue.getEndOffset(), defaultValue.getExpression());
                    PatchDeclarationParentsKt.patchDeclarationParents(irExpressionBodyCreateExpressionBody.getExpression(), irConstructorBuildConstructor);
                    EnumClassLoweringKt.fixReferencesToConstructorParameters(this.context, enumClass, irExpressionBodyCreateExpressionBody);
                    irValueParameter2.setDefaultValue(irExpressionBodyCreateExpressionBody);
                }
            } else {
                throw new NoSuchElementException("Collection contains no element matching the predicate.");
            }
        }
        return irConstructorBuildConstructor;
    }
}
