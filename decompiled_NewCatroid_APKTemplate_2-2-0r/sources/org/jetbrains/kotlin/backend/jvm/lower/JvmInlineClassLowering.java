package org.jetbrains.kotlin.backend.jvm.lower;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.function.Predicate;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt;
import org.jetbrains.kotlin.backend.common.ScopeWithIr;
import org.jetbrains.kotlin.backend.common.lower.DeclarationIrBuilder;
import org.jetbrains.kotlin.backend.common.lower.LowerUtilsKt;
import org.jetbrains.kotlin.backend.jvm.InlineClassAbi;
import org.jetbrains.kotlin.backend.jvm.InlineClassAbiKt;
import org.jetbrains.kotlin.backend.jvm.JvmBackendContext;
import org.jetbrains.kotlin.backend.jvm.JvmLoweredDeclarationOrigin;
import org.jetbrains.kotlin.backend.jvm.MemoizedValueClassAbstractReplacements;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrTypeUtilsKt;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.config.ApiVersion;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.descriptors.InlineClassRepresentation;
import org.jetbrains.kotlin.ir.IrElementsKt;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.builders.ExpressionHelpersKt;
import org.jetbrains.kotlin.ir.builders.IrBlockBodyBuilder;
import org.jetbrains.kotlin.ir.builders.IrBlockBuilder;
import org.jetbrains.kotlin.ir.builders.IrBuilderWithScope;
import org.jetbrains.kotlin.ir.builders.declarations.DeclarationBuildersKt;
import org.jetbrains.kotlin.ir.builders.declarations.IrFunctionBuilder;
import org.jetbrains.kotlin.ir.declarations.IrAnonymousInitializer;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationsKt;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.declarations.IrVariable;
import org.jetbrains.kotlin.ir.expressions.IrBody;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrDelegatingConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrExpressionsKt;
import org.jetbrains.kotlin.ir.expressions.IrFunctionAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionReference;
import org.jetbrains.kotlin.ir.expressions.IrGetField;
import org.jetbrains.kotlin.ir.expressions.IrGetValue;
import org.jetbrains.kotlin.ir.expressions.IrMemberAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrReturn;
import org.jetbrains.kotlin.ir.expressions.IrSetValue;
import org.jetbrains.kotlin.ir.expressions.IrStatementOrigin;
import org.jetbrains.kotlin.ir.expressions.IrTypeOperator;
import org.jetbrains.kotlin.ir.expressions.impl.IrCallImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrConstructorCallImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrFunctionReferenceImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetValueImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrIfThenElseImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrSetValueImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrTypeOperatorCallImpl;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrConstructorSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrValueSymbol;
import org.jetbrains.kotlin.ir.types.IrSimpleType;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.AdditionalIrUtilsKt;
import org.jetbrains.kotlin.ir.util.InlineClassesKt;
import org.jetbrains.kotlin.ir.util.IrTypeUtilsKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.util.PatchDeclarationParentsKt;
import org.jetbrains.kotlin.ir.util.TransformKt;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformer;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.InlineClassesUtilsKt;

/* JADX INFO: compiled from: JvmInlineClassLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000´\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0018\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001dH\u0014J\u0010\u0010\u001f\u001a\u00020\u001b2\u0006\u0010 \u001a\u00020\u0013H\u0002J\u0010\u0010!\u001a\u00020\u001b2\u0006\u0010 \u001a\u00020\u0013H\u0002J\u0018\u0010\"\u001a\u00020\u001b2\u0006\u0010 \u001a\u00020\u00132\u0006\u0010#\u001a\u00020$H\u0002J\u0010\u0010%\u001a\u00020\u001b2\u0006\u0010 \u001a\u00020\u0013H\u0002J\u0010\u0010&\u001a\u00020\u001b2\u0006\u0010'\u001a\u00020\u0013H\u0002J*\u0010(\u001a\u00020)2\u0006\u0010*\u001a\u00020)2\u0006\u0010+\u001a\u00020,2\u0006\u0010-\u001a\u00020,2\b\b\u0002\u0010.\u001a\u00020\u0012H\u0002J(\u0010/\u001a\u00020\u001b2\u0006\u00100\u001a\u0002012\u0006\u00102\u001a\u0002012\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u00103\u001a\u00020\u0012H\u0014J \u00104\u001a\u0002012\u0006\u00100\u001a\u0002012\u0006\u0010\u001e\u001a\u0002012\u0006\u00105\u001a\u000206H\u0016J\u0010\u00107\u001a\u00020\u001b2\u0006\u00108\u001a\u00020\u0013H\u0016J\u001e\u00109\u001a\b\u0012\u0004\u0012\u00020;0:2\u0006\u0010<\u001a\u00020$2\u0006\u0010\u001e\u001a\u000201H\u0014J\u0010\u0010=\u001a\u00020)2\u0006\u0010>\u001a\u00020\u0017H\u0016J\u0010\u0010?\u001a\u00020\u00132\u0006\u00108\u001a\u00020\u0013H\u0016J\u0015\u0010@\u001a\u00020\u001b2\u0006\u00108\u001a\u00020;H\u0010¢\u0006\u0002\bAJ\u0010\u0010B\u001a\u00020)2\u0006\u0010>\u001a\u00020CH\u0016J\u0010\u0010D\u001a\u00020)2\u0006\u0010>\u001a\u00020EH\u0016J\u0010\u0010F\u001a\u00020)2\u0006\u0010>\u001a\u00020GH\u0016J\u0010\u0010H\u001a\u00020)2\u0006\u0010>\u001a\u00020IH\u0016J\u0010\u0010J\u001a\u00020)2\u0006\u0010>\u001a\u00020KH\u0016J,\u0010L\u001a\u00020\u001b*\u0006\u0012\u0002\b\u00030M2\u0006\u0010N\u001a\u00020\u001d2\n\u0010\u001c\u001a\u0006\u0012\u0002\b\u00030M2\u0006\u0010\u001e\u001a\u000201H\u0002J\f\u0010O\u001a\u00020)*\u00020)H\u0002J\f\u0010P\u001a\u00020\u0012*\u00020\u0013H\u0016J\u001e\u0010Q\u001a\u0004\u0018\u00010)*\u00020R2\u0006\u0010S\u001a\u00020)2\u0006\u0010T\u001a\u00020)H\u0002R\u0014\u0010\u0005\u001a\u00020\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\t\u001a\u00020\n8TX\u0094\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u001a\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u00100\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u0018\u0010\u0011\u001a\u00020\u0012*\u00020\u00138BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015R\u0018\u0010\u0016\u001a\u00020\u0012*\u00020\u00178BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0018R\u0018\u0010\u0019\u001a\u00020\u0012*\u00020\u00178BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0019\u0010\u0018¨\u0006U"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/lower/JvmInlineClassLowering;", "Lorg/jetbrains/kotlin/backend/jvm/lower/JvmValueClassAbstractLowering;", "context", "Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "(Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;)V", "replacements", "Lorg/jetbrains/kotlin/backend/jvm/MemoizedValueClassAbstractReplacements;", "getReplacements", "()Lorg/jetbrains/kotlin/backend/jvm/MemoizedValueClassAbstractReplacements;", "specificMangle", "Lorg/jetbrains/kotlin/backend/jvm/lower/JvmValueClassAbstractLowering$SpecificMangle;", "getSpecificMangle", "()Lorg/jetbrains/kotlin/backend/jvm/lower/JvmValueClassAbstractLowering$SpecificMangle;", "valueMap", "", "Lorg/jetbrains/kotlin/ir/symbols/IrValueSymbol;", "Lorg/jetbrains/kotlin/ir/declarations/IrValueDeclaration;", "canUseSpecializedEqMethod", "", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "getCanUseSpecializedEqMethod", "(Lorg/jetbrains/kotlin/ir/declarations/IrClass;)Z", "isEqEqCallOnInlineClass", "Lorg/jetbrains/kotlin/ir/expressions/IrCall;", "(Lorg/jetbrains/kotlin/ir/expressions/IrCall;)Z", "isEqualsMethodCallOnInlineClass", "addBindingsFor", "", "original", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "replacement", "addJvmInlineAnnotation", "valueClass", "buildBoxFunction", "buildPrimaryInlineClassConstructor", "irConstructor", "Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "buildSpecializedEqualsMethodIfNeeded", "buildUnboxFunction", "irClass", "coerceInlineClasses", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "argument", TypedValues.TransitionType.S_FROM, "Lorg/jetbrains/kotlin/ir/types/IrType;", "to", "skipCast", "createBridgeBody", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "target", "inverted", "createBridgeDeclaration", "mangledName", "Lorg/jetbrains/kotlin/name/Name;", "handleSpecificNewClass", "declaration", "transformSecondaryConstructorFlat", "", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME, "visitCall", "expression", "visitClassNew", "visitClassNewDeclarationsWhenParallel", "visitClassNewDeclarationsWhenParallel$backend_jvm_lower", "visitFunctionAccess", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionAccessExpression;", "visitFunctionReference", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionReference;", "visitGetField", "Lorg/jetbrains/kotlin/ir/expressions/IrGetField;", "visitGetValue", "Lorg/jetbrains/kotlin/ir/expressions/IrGetValue;", "visitSetValue", "Lorg/jetbrains/kotlin/ir/expressions/IrSetValue;", "buildReplacement", "Lorg/jetbrains/kotlin/ir/expressions/IrMemberAccessExpression;", "originalFunction", "coerceToUnboxed", "isSpecificLoweringLogicApplicable", "specializeEqualsCall", "Lorg/jetbrains/kotlin/ir/builders/IrBuilderWithScope;", "left", "right", "backend.jvm.lower"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JvmInlineClassLowering extends JvmValueClassAbstractLowering {
    private final Map<IrValueSymbol, IrValueDeclaration> valueMap;

    @Override // org.jetbrains.kotlin.backend.jvm.lower.JvmValueClassAbstractLowering
    public void visitClassNewDeclarationsWhenParallel$backend_jvm_lower(IrDeclaration declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public JvmInlineClassLowering(JvmBackendContext context) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
        this.valueMap = new LinkedHashMap();
    }

    @Override // org.jetbrains.kotlin.backend.jvm.lower.JvmValueClassAbstractLowering
    public MemoizedValueClassAbstractReplacements getReplacements() {
        return getContext().getInlineClassReplacements();
    }

    @Override // org.jetbrains.kotlin.backend.jvm.lower.JvmValueClassAbstractLowering
    protected void addBindingsFor(IrFunction original, IrFunction replacement) {
        Intrinsics.checkNotNullParameter(original, "original");
        Intrinsics.checkNotNullParameter(replacement, "replacement");
        for (Pair pair : CollectionsKt.zip(IrUtilsKt.getExplicitParameters(original), IrUtilsKt.getExplicitParameters(replacement))) {
            IrValueParameter irValueParameter = (IrValueParameter) pair.component1();
            this.valueMap.put(irValueParameter.getSymbol(), (IrValueParameter) pair.component2());
        }
    }

    @Override // org.jetbrains.kotlin.backend.jvm.lower.JvmValueClassAbstractLowering
    public IrSimpleFunction createBridgeDeclaration(IrSimpleFunction source, IrSimpleFunction replacement, Name mangledName) {
        Intrinsics.checkNotNullParameter(source, "source");
        Intrinsics.checkNotNullParameter(replacement, "replacement");
        Intrinsics.checkNotNullParameter(mangledName, "mangledName");
        IrFactory irFactory = getContext().getIrFactory();
        IrFunctionBuilder irFunctionBuilder = new IrFunctionBuilder();
        IrSimpleFunction irSimpleFunction = source;
        irFunctionBuilder.updateFrom((IrFunction) irSimpleFunction);
        irFunctionBuilder.setName(mangledName);
        irFunctionBuilder.setReturnType(source.getReturnType());
        IrSimpleFunction irSimpleFunctionBuildFunction = DeclarationBuildersKt.buildFunction(irFactory, irFunctionBuilder);
        IrUtilsKt.copyParameterDeclarationsFrom(irSimpleFunctionBuildFunction, irSimpleFunction);
        irSimpleFunctionBuildFunction.setAnnotations(source.getAnnotations());
        irSimpleFunctionBuildFunction.setParent(source.getParent());
        IrDeclarationsKt.copyAttributes(irSimpleFunctionBuildFunction, source);
        return irSimpleFunctionBuildFunction;
    }

    @Override // org.jetbrains.kotlin.backend.jvm.lower.JvmValueClassAbstractLowering
    public boolean isSpecificLoweringLogicApplicable(IrClass irClass) {
        Intrinsics.checkNotNullParameter(irClass, "<this>");
        return IrDeclarationsKt.isSingleFieldValueClass(irClass);
    }

    @Override // org.jetbrains.kotlin.backend.jvm.lower.JvmValueClassAbstractLowering
    protected JvmValueClassAbstractLowering.SpecificMangle getSpecificMangle() {
        return JvmValueClassAbstractLowering.SpecificMangle.Inline;
    }

    @Override // org.jetbrains.kotlin.backend.common.IrElementTransformerVoidWithContext
    public IrClass visitClassNew(IrClass declaration) {
        IrConstructor irConstructor;
        IrSimpleFunction replacementFunction;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        IrConstructor primaryConstructor = IrUtilsKt.getPrimaryConstructor(declaration);
        if (primaryConstructor != null && (replacementFunction = getReplacements().getReplacementFunction((irConstructor = primaryConstructor))) != null) {
            addBindingsFor(irConstructor, replacementFunction);
        }
        TransformKt.transformDeclarationsFlat(declaration, new Function1<IrDeclaration, List<? extends IrDeclaration>>() { // from class: org.jetbrains.kotlin.backend.jvm.lower.JvmInlineClassLowering.visitClassNew.2
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final List<IrDeclaration> invoke(IrDeclaration memberDeclaration) {
                Intrinsics.checkNotNullParameter(memberDeclaration, "memberDeclaration");
                if (memberDeclaration instanceof IrFunction) {
                    JvmInlineClassLowering jvmInlineClassLowering = JvmInlineClassLowering.this;
                    JvmInlineClassLowering jvmInlineClassLowering2 = jvmInlineClassLowering;
                    jvmInlineClassLowering2.unsafeEnterScope(memberDeclaration);
                    List<IrDeclaration> listTransformFunctionFlat = jvmInlineClassLowering.transformFunctionFlat((IrFunction) memberDeclaration);
                    jvmInlineClassLowering2.unsafeLeaveScope();
                    return listTransformFunctionFlat;
                }
                memberDeclaration.accept(JvmInlineClassLowering.this, null);
                return null;
            }
        });
        if (isSpecificLoweringLogicApplicable(declaration)) {
            handleSpecificNewClass(declaration);
        }
        return declaration;
    }

    @Override // org.jetbrains.kotlin.backend.jvm.lower.JvmValueClassAbstractLowering
    public void handleSpecificNewClass(IrClass declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        final IrConstructor primaryConstructor = IrUtilsKt.getPrimaryConstructor(declaration);
        Intrinsics.checkNotNull(primaryConstructor);
        List<IrDeclaration> declarations = declaration.getDeclarations();
        final Function1<IrDeclaration, Boolean> function1 = new Function1<IrDeclaration, Boolean>() { // from class: org.jetbrains.kotlin.backend.jvm.lower.JvmInlineClassLowering.handleSpecificNewClass.1
            {
                super(1);
            }

            /* JADX WARN: Code duplicated, block: B:12:0x0026  */
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(IrDeclaration it) {
                boolean z;
                Intrinsics.checkNotNullParameter(it, "it");
                if (Intrinsics.areEqual(it, primaryConstructor)) {
                    z = true;
                } else {
                    if (it instanceof IrFunction) {
                        IrFunction irFunction = (IrFunction) it;
                        if (InlineClassAbiKt.isInlineClassFieldGetter(irFunction) && !irFunction.getVisibility().isPublicAPI()) {
                            z = true;
                        }
                    }
                    z = false;
                }
                return Boolean.valueOf(z);
            }
        };
        declarations.removeIf(new Predicate() { // from class: org.jetbrains.kotlin.backend.jvm.lower.JvmInlineClassLowering$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return JvmInlineClassLowering.handleSpecificNewClass$lambda$4(function1, obj);
            }
        });
        buildPrimaryInlineClassConstructor(declaration, primaryConstructor);
        buildBoxFunction(declaration);
        buildUnboxFunction(declaration);
        buildSpecializedEqualsMethodIfNeeded(declaration);
        addJvmInlineAnnotation(declaration);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean handleSpecificNewClass$lambda$4(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        return ((Boolean) tmp0.invoke(obj)).booleanValue();
    }

    private final void addJvmInlineAnnotation(IrClass valueClass) {
        if (IrUtilsKt.hasAnnotation(valueClass, InlineClassesUtilsKt.getJVM_INLINE_ANNOTATION_FQ_NAME())) {
            return;
        }
        IrConstructorSymbol irConstructorSymbol = (IrConstructorSymbol) SequencesKt.first(IrUtilsKt.getConstructors(getContext().getIr().getSymbols().getJvmInlineAnnotation()));
        valueClass.setAnnotations(CollectionsKt.plus((Collection<? extends IrConstructorCallImpl>) valueClass.getAnnotations(), IrConstructorCallImpl.Companion.fromSymbolOwner$default(IrConstructorCallImpl.INSTANCE, irConstructorSymbol.getOwner().getReturnType(), irConstructorSymbol, null, 4, null)));
    }

    @Override // org.jetbrains.kotlin.backend.jvm.lower.JvmValueClassAbstractLowering
    protected void createBridgeBody(IrSimpleFunction source, IrSimpleFunction target, IrFunction original, boolean inverted) {
        Intrinsics.checkNotNullParameter(source, "source");
        Intrinsics.checkNotNullParameter(target, "target");
        Intrinsics.checkNotNullParameter(original, "original");
        DeclarationIrBuilder declarationIrBuilderCreateIrBuilder = LowerUtilsKt.createIrBuilder(getContext(), source.getSymbol(), source.getStartOffset(), source.getEndOffset());
        IrSimpleFunction irSimpleFunction = target;
        IrFunctionAccessExpression irFunctionAccessExpressionIrCall = ExpressionHelpersKt.irCall(declarationIrBuilderCreateIrBuilder, irSimpleFunction);
        IrUtilsKt.passTypeArgumentsFrom$default(irFunctionAccessExpressionIrCall, source, 0, 2, null);
        for (Pair pair : CollectionsKt.zip(IrUtilsKt.getExplicitParameters(source), IrUtilsKt.getExplicitParameters(irSimpleFunction))) {
            IrExpressionsKt.putArgument(irFunctionAccessExpressionIrCall, (IrValueParameter) pair.component2(), ExpressionHelpersKt.irGet(declarationIrBuilderCreateIrBuilder, (IrValueParameter) pair.component1()));
        }
        source.setBody(ExpressionHelpersKt.irExprBody(declarationIrBuilderCreateIrBuilder, irFunctionAccessExpressionIrCall));
    }

    @Override // org.jetbrains.kotlin.backend.jvm.lower.JvmValueClassAbstractLowering
    protected List<IrDeclaration> transformSecondaryConstructorFlat(final IrConstructor constructor, IrSimpleFunction replacement) {
        List<IrStatement> statements;
        Intrinsics.checkNotNullParameter(constructor, "constructor");
        Intrinsics.checkNotNullParameter(replacement, "replacement");
        Iterator<T> it = replacement.getValueParameters().iterator();
        while (it.hasNext()) {
            transformChildrenVoid((IrValueParameter) it.next());
        }
        DeclarationIrBuilder declarationIrBuilderCreateIrBuilder = LowerUtilsKt.createIrBuilder(getContext(), replacement.getSymbol(), replacement.getStartOffset(), replacement.getEndOffset());
        IrSimpleFunction irSimpleFunction = replacement;
        final IrBlockBodyBuilder irBlockBodyBuilder = new IrBlockBodyBuilder(declarationIrBuilderCreateIrBuilder.getContext(), declarationIrBuilderCreateIrBuilder.getScope(), irSimpleFunction.getStartOffset(), irSimpleFunction.getEndOffset());
        final IrVariable irVariableIrTemporary$default = ExpressionHelpersKt.irTemporary$default(irBlockBodyBuilder, null, "$this", replacement.getReturnType(), false, null, 25, null);
        Map<IrValueSymbol, IrValueDeclaration> map = this.valueMap;
        IrValueParameter thisReceiver = AdditionalIrUtilsKt.getConstructedClass(constructor).getThisReceiver();
        Intrinsics.checkNotNull(thisReceiver);
        map.put(thisReceiver.getSymbol(), irVariableIrTemporary$default);
        IrBody body = constructor.getBody();
        if (body != null && (statements = IrUtilsKt.getStatements(body)) != null) {
            Iterator<T> it2 = statements.iterator();
            while (it2.hasNext()) {
                irBlockBodyBuilder.unaryPlus((IrStatement) PatchDeclarationParentsKt.patchDeclarationParents(IrElementsKt.transformStatement(IrElementsKt.transformStatement((IrStatement) it2.next(), new IrElementTransformerVoid() { // from class: org.jetbrains.kotlin.backend.jvm.lower.JvmInlineClassLowering$transformSecondaryConstructorFlat$2$1$1
                    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
                    public IrStatement visitClass(IrClass declaration) {
                        Intrinsics.checkNotNullParameter(declaration, "declaration");
                        return declaration;
                    }

                    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
                    public IrExpression visitDelegatingConstructorCall(IrDelegatingConstructorCall expression) {
                        Intrinsics.checkNotNullParameter(expression, "expression");
                        transformChildrenVoid(expression);
                        return ExpressionHelpersKt.irSet$default(irBlockBodyBuilder, irVariableIrTemporary$default.getSymbol(), expression, (IrStatementOrigin) null, 4, (Object) null);
                    }

                    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
                    public IrExpression visitReturn(IrReturn expression) {
                        Intrinsics.checkNotNullParameter(expression, "expression");
                        transformChildrenVoid(expression);
                        if (!Intrinsics.areEqual(expression.getReturnTargetSymbol(), constructor.getSymbol())) {
                            return expression;
                        }
                        IrBlockBodyBuilder irBlockBodyBuilder2 = irBlockBodyBuilder;
                        IrBlockBodyBuilder irBlockBodyBuilder3 = irBlockBodyBuilder2;
                        IrBlockBodyBuilder irBlockBodyBuilder4 = irBlockBodyBuilder2;
                        int startOffset = expression.getStartOffset();
                        int endOffset = expression.getEndOffset();
                        IrVariable irVariable = irVariableIrTemporary$default;
                        IrBlockBuilder irBlockBuilder = new IrBlockBuilder(irBlockBodyBuilder4.getContext(), irBlockBodyBuilder4.getScope(), startOffset, endOffset, null, null, false, 64, null);
                        irBlockBuilder.unaryPlus(expression.getValue());
                        irBlockBuilder.unaryPlus(ExpressionHelpersKt.irGet(irBlockBuilder, irVariable));
                        return ExpressionHelpersKt.irReturn(irBlockBodyBuilder3, irBlockBuilder.getIrBlockBody());
                    }
                }), this), replacement));
            }
        }
        IrBlockBodyBuilder irBlockBodyBuilder2 = irBlockBodyBuilder;
        irBlockBodyBuilder.unaryPlus(ExpressionHelpersKt.irReturn(irBlockBodyBuilder2, ExpressionHelpersKt.irGet(irBlockBodyBuilder2, irVariableIrTemporary$default)));
        replacement.setBody(irBlockBodyBuilder.getIrBlockBody());
        return CollectionsKt.listOf(replacement);
    }

    private final void buildReplacement(IrMemberAccessExpression<?> irMemberAccessExpression, IrFunction irFunction, IrMemberAccessExpression<?> irMemberAccessExpression2, IrSimpleFunction irSimpleFunction) {
        IrExpressionsKt.copyTypeArgumentsFrom$default(irMemberAccessExpression, irMemberAccessExpression2, 0, 2, null);
        IrSimpleFunction irSimpleFunction2 = irSimpleFunction;
        Map map = MapsKt.toMap(CollectionsKt.zip(IrUtilsKt.getExplicitParameters(irFunction), IrUtilsKt.getExplicitParameters(irSimpleFunction2)));
        for (Pair<IrValueParameter, IrExpression> pair : typedArgumentList(irFunction, irMemberAccessExpression2)) {
            IrValueParameter irValueParameterComponent1 = pair.component1();
            IrExpression irExpressionComponent2 = pair.component2();
            if (irExpressionComponent2 != null) {
                IrExpressionsKt.putArgument(irMemberAccessExpression, irSimpleFunction2, (IrValueParameter) MapsKt.getValue(map, irValueParameterComponent1), irExpressionComponent2.transform((IrElementTransformer<? super Object>) this, (Object) null));
            }
        }
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrExpression visitFunctionReference(IrFunctionReference expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        if (Intrinsics.areEqual(expression.getOrigin(), InlineClassAbi.UNMANGLED_FUNCTION_REFERENCE.INSTANCE)) {
            return super.visitFunctionReference(expression);
        }
        IrFunction owner = expression.getSymbol().getOwner();
        IrSimpleFunction replacementFunction = getContext().getInlineClassReplacements().getReplacementFunction(owner);
        if (replacementFunction == null) {
            return super.visitFunctionReference(expression);
        }
        IrFunctionReferenceImpl irFunctionReferenceImpl = new IrFunctionReferenceImpl(expression.getStartOffset(), expression.getEndOffset(), expression.getType(), replacementFunction.getSymbol(), owner.getTypeParameters().size(), replacementFunction.getValueParameters().size(), expression.getReflectionTarget(), expression.getOrigin());
        buildReplacement(irFunctionReferenceImpl, owner, expression, replacementFunction);
        return (IrExpression) IrDeclarationsKt.copyAttributes(irFunctionReferenceImpl, expression);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrExpression visitFunctionAccess(IrFunctionAccessExpression expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrFunction owner = expression.getSymbol().getOwner();
        IrSimpleFunction replacementFunction = getContext().getInlineClassReplacements().getReplacementFunction(owner);
        if (replacementFunction == null) {
            return super.visitFunctionAccess(expression);
        }
        int startOffset = expression.getStartOffset();
        int endOffset = expression.getEndOffset();
        IrType irTypeSubstitute = IrTypeUtilsKt.substitute(owner.getReturnType(), IrUtilsKt.getTypeSubstitutionMap(expression));
        IrSimpleFunctionSymbol symbol = replacementFunction.getSymbol();
        int size = replacementFunction.getTypeParameters().size();
        int size2 = replacementFunction.getValueParameters().size();
        IrStatementOrigin origin = expression.getOrigin();
        IrCall irCall = expression instanceof IrCall ? (IrCall) expression : null;
        IrCallImpl irCallImpl = new IrCallImpl(startOffset, endOffset, irTypeSubstitute, symbol, size, size2, origin, irCall != null ? irCall.getSuperQualifierSymbol() : null);
        buildReplacement(irCallImpl, owner, expression, replacementFunction);
        return irCallImpl;
    }

    static /* synthetic */ IrExpression coerceInlineClasses$default(JvmInlineClassLowering jvmInlineClassLowering, IrExpression irExpression, IrType irType, IrType irType2, boolean z, int i, Object obj) {
        if ((i & 8) != 0) {
            z = false;
        }
        return jvmInlineClassLowering.coerceInlineClasses(irExpression, irType, irType2, z);
    }

    private final IrExpression coerceInlineClasses(IrExpression argument, IrType from, IrType to, boolean skipCast) {
        IrCallImpl.Companion companion = IrCallImpl.INSTANCE;
        IrSimpleFunctionSymbol unsafeCoerceIntrinsic = getContext().getIr().getSymbols().getUnsafeCoerceIntrinsic();
        IrCallImpl irCallImplFromSymbolOwner = companion.fromSymbolOwner(-1, -1, to, unsafeCoerceIntrinsic, (240 & 16) != 0 ? unsafeCoerceIntrinsic.getOwner().getTypeParameters().size() : 0, (240 & 32) != 0 ? unsafeCoerceIntrinsic.getOwner().getValueParameters().size() : 0, (240 & 64) != 0 ? null : null, (240 & 128) != 0 ? null : null);
        InlineClassRepresentation<IrSimpleType> inlineClassRepresentation = IrDeclarationsKt.getInlineClassRepresentation(JvmIrTypeUtilsKt.getErasedUpperBound(from));
        IrSimpleType irSimpleType = inlineClassRepresentation != null ? (IrSimpleType) inlineClassRepresentation.getUnderlyingType() : null;
        if (irSimpleType != null && IrTypeUtilsKt.isTypeParameter(irSimpleType) && !skipCast) {
            irCallImplFromSymbolOwner.putTypeArgument(0, from);
            IrSimpleType irSimpleType2 = irSimpleType;
            irCallImplFromSymbolOwner.putTypeArgument(1, irSimpleType2);
            irCallImplFromSymbolOwner.putValueArgument(0, new IrTypeOperatorCallImpl(-1, -1, to, IrTypeOperator.IMPLICIT_CAST, irSimpleType2, argument));
        } else {
            irCallImplFromSymbolOwner.putTypeArgument(0, from);
            irCallImplFromSymbolOwner.putTypeArgument(1, to);
            irCallImplFromSymbolOwner.putValueArgument(0, argument);
        }
        return irCallImplFromSymbolOwner;
    }

    private final IrExpression coerceToUnboxed(IrExpression irExpression) {
        return coerceInlineClasses$default(this, irExpression, irExpression.getType(), InlineClassAbiKt.unboxInlineClass(irExpression.getType()), false, 8, null);
    }

    private final IrExpression specializeEqualsCall(IrBuilderWithScope irBuilderWithScope, IrExpression irExpression, IrExpression irExpression2) {
        if (IrUtilsKt.isNullConst(irExpression) || IrUtilsKt.isNullConst(irExpression2)) {
            return null;
        }
        boolean zAreEqual = Intrinsics.areEqual(InlineClassAbiKt.unboxInlineClass(irExpression.getType()), irExpression.getType());
        boolean zAreEqual2 = Intrinsics.areEqual(InlineClassAbiKt.unboxInlineClass(irExpression2.getType()), irExpression2.getType());
        boolean z = !zAreEqual2;
        if (zAreEqual && zAreEqual2) {
            return null;
        }
        boolean zIsNullable = org.jetbrains.kotlin.ir.types.IrTypeUtilsKt.isNullable(irExpression.getType());
        boolean z2 = !zAreEqual2 && org.jetbrains.kotlin.ir.types.IrTypeUtilsKt.isNullable(irExpression2.getType());
        if (!zIsNullable && !z2) {
            return specializeEqualsCall$equals(z, irBuilderWithScope, this, irExpression, irExpression2);
        }
        IrBlockBuilder irBlockBuilder = new IrBlockBuilder(irBuilderWithScope.getContext(), irBuilderWithScope.getScope(), irBuilderWithScope.getStartOffset(), irBuilderWithScope.getEndOffset(), null, null, false, 64, null);
        IrValueDeclaration owner = irExpression instanceof IrGetValue ? ((IrGetValue) irExpression).getSymbol().getOwner() : ExpressionHelpersKt.irTemporary$default(irBlockBuilder, irExpression, null, null, false, null, 30, null);
        IrVariable owner2 = irExpression2 instanceof IrGetValue ? ((IrGetValue) irExpression2).getSymbol().getOwner() : ExpressionHelpersKt.irTemporary$default(irBlockBuilder, irExpression2, null, null, false, null, 30, null);
        IrBlockBuilder irBlockBuilder2 = irBlockBuilder;
        IrTypeOperatorCallImpl irTypeOperatorCallImplIrGet = ExpressionHelpersKt.irGet(irBlockBuilder2, owner);
        if (zIsNullable) {
            irTypeOperatorCallImplIrGet = ExpressionHelpersKt.irImplicitCast(irBlockBuilder2, irTypeOperatorCallImplIrGet, IrTypesKt.makeNotNull(irExpression.getType()));
        }
        IrBlockBuilder irBlockBuilder3 = irBlockBuilder;
        IrTypeOperatorCallImpl irTypeOperatorCallImplIrGet2 = ExpressionHelpersKt.irGet(irBlockBuilder3, owner2);
        if (z2) {
            irTypeOperatorCallImplIrGet2 = ExpressionHelpersKt.irImplicitCast(irBlockBuilder3, irTypeOperatorCallImplIrGet2, IrTypesKt.makeNotNull(irExpression2.getType()));
        }
        IrIfThenElseImpl irIfThenElseImplSpecializeEqualsCall$equals = specializeEqualsCall$equals(z, irBuilderWithScope, this, irTypeOperatorCallImplIrGet, irTypeOperatorCallImplIrGet2);
        if (z2) {
            IrBlockBuilder irBlockBuilder4 = irBlockBuilder;
            irIfThenElseImplSpecializeEqualsCall$equals = ExpressionHelpersKt.irIfNull(irBlockBuilder4, irBlockBuilder.getContext().getIrBuiltIns().getBooleanType(), ExpressionHelpersKt.irGet(irBlockBuilder4, owner2), ExpressionHelpersKt.irFalse(irBlockBuilder4), irIfThenElseImplSpecializeEqualsCall$equals);
        }
        if (zIsNullable) {
            IrBlockBuilder irBlockBuilder5 = irBlockBuilder;
            irBlockBuilder.unaryPlus(ExpressionHelpersKt.irIfNull(irBlockBuilder5, irBlockBuilder.getContext().getIrBuiltIns().getBooleanType(), ExpressionHelpersKt.irGet(irBlockBuilder5, owner), ExpressionHelpersKt.irEqualsNull(irBlockBuilder5, ExpressionHelpersKt.irGet(irBlockBuilder5, owner2)), irIfThenElseImplSpecializeEqualsCall$equals));
        } else {
            irBlockBuilder.unaryPlus(irIfThenElseImplSpecializeEqualsCall$equals);
        }
        return irBlockBuilder.getIrBlockBody();
    }

    /* JADX WARN: Code duplicated, block: B:14:0x0066  */
    /* JADX WARN: Code duplicated, block: B:17:0x0076  */
    /* JADX WARN: Code duplicated, block: B:26:0x00a8  */
    /* JADX WARN: Code duplicated, block: B:29:0x00ca  */
    private static final IrExpression specializeEqualsCall$equals(boolean z, IrBuilderWithScope irBuilderWithScope, JvmInlineClassLowering jvmInlineClassLowering, IrExpression irExpression, IrExpression irExpression2) {
        IrSimpleFunction irSimpleFunction;
        boolean z2;
        IrSimpleFunction replacementFunction;
        IrSimpleFunction irSimpleFunction2;
        if (IrTypeUtilsKt.isUnsigned(irExpression.getType()) && IrTypeUtilsKt.isUnsigned(irExpression2.getType()) && z) {
            return ExpressionHelpersKt.irEquals$default(irBuilderWithScope, jvmInlineClassLowering.coerceToUnboxed(irExpression), jvmInlineClassLowering.coerceToUnboxed(irExpression2), null, 4, null);
        }
        IrClassSymbol classOrNull = IrTypesKt.getClassOrNull(irExpression.getType());
        Intrinsics.checkNotNull(classOrNull);
        IrClass owner = classOrNull.getOwner();
        if (z) {
            IrClassSymbol classOrNull2 = IrTypesKt.getClassOrNull(irExpression2.getType());
            Intrinsics.checkNotNull(classOrNull2);
            if (Intrinsics.areEqual(owner, classOrNull2.getOwner())) {
                replacementFunction = jvmInlineClassLowering.getContext().getInlineClassReplacements().getSpecializedEqualsMethod(owner, irBuilderWithScope.getContext().getIrBuiltIns());
            } else {
                irSimpleFunction = null;
                z2 = false;
                for (IrSimpleFunction irSimpleFunction3 : IrUtilsKt.getFunctions(owner)) {
                    irSimpleFunction2 = irSimpleFunction3;
                    if (!Intrinsics.areEqual(irSimpleFunction2.getName().asString(), Namer.EQUALS_METHOD_NAME) && !irSimpleFunction2.getOverriddenSymbols().isEmpty()) {
                        if (z2) {
                            throw new IllegalArgumentException("Sequence contains more than one matching element.");
                        }
                        z2 = true;
                        irSimpleFunction = irSimpleFunction3;
                    }
                }
                if (z2) {
                    replacementFunction = jvmInlineClassLowering.getContext().getInlineClassReplacements().getReplacementFunction(irSimpleFunction);
                    Intrinsics.checkNotNull(replacementFunction);
                } else {
                    throw new NoSuchElementException("Sequence contains no element matching the predicate.");
                }
            }
        } else {
            irSimpleFunction = null;
            z2 = false;
            while (r8.hasNext()) {
                irSimpleFunction2 = irSimpleFunction3;
                if (!Intrinsics.areEqual(irSimpleFunction2.getName().asString(), Namer.EQUALS_METHOD_NAME)) {
                }
            }
            if (z2) {
                replacementFunction = jvmInlineClassLowering.getContext().getInlineClassReplacements().getReplacementFunction(irSimpleFunction);
                Intrinsics.checkNotNull(replacementFunction);
            } else {
                throw new NoSuchElementException("Sequence contains no element matching the predicate.");
            }
        }
        IrFunctionAccessExpression irFunctionAccessExpressionIrCall = ExpressionHelpersKt.irCall(irBuilderWithScope, replacementFunction);
        irFunctionAccessExpressionIrCall.putValueArgument(0, irExpression);
        irFunctionAccessExpressionIrCall.putValueArgument(1, irExpression2);
        return irFunctionAccessExpressionIrCall;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrExpression visitCall(IrCall expression) {
        IrExpression dispatchReceiver;
        IrExpression valueArgument;
        Intrinsics.checkNotNullParameter(expression, "expression");
        if (InlineClassAbiKt.isInlineClassFieldGetter(expression.getSymbol().getOwner())) {
            IrExpression dispatchReceiver2 = expression.getDispatchReceiver();
            Intrinsics.checkNotNull(dispatchReceiver2);
            IrExpression irExpressionTransform = dispatchReceiver2.transform((IrElementTransformer<? super Object>) this, (Object) null);
            IrValueParameter dispatchReceiverParameter = expression.getSymbol().getOwner().getDispatchReceiverParameter();
            Intrinsics.checkNotNull(dispatchReceiverParameter);
            return coerceInlineClasses$default(this, irExpressionTransform, dispatchReceiverParameter.getType(), expression.getType(), false, 8, null);
        }
        if (isEqEqCallOnInlineClass(expression) || isEqualsMethodCallOnInlineClass(expression)) {
            transformChildrenVoid(expression);
            if (isEqEqCallOnInlineClass(expression)) {
                dispatchReceiver = expression.getValueArgument(0);
                Intrinsics.checkNotNull(dispatchReceiver);
                valueArgument = expression.getValueArgument(1);
                Intrinsics.checkNotNull(valueArgument);
            } else {
                dispatchReceiver = expression.getDispatchReceiver();
                Intrinsics.checkNotNull(dispatchReceiver);
                valueArgument = expression.getValueArgument(0);
                Intrinsics.checkNotNull(valueArgument);
            }
            JvmBackendContext context = getContext();
            ScopeWithIr currentScope = getCurrentScope();
            Intrinsics.checkNotNull(currentScope);
            IrExpression irExpressionSpecializeEqualsCall = specializeEqualsCall(LowerUtilsKt.createIrBuilder(context, currentScope.getScope().getScopeOwnerSymbol(), expression.getStartOffset(), expression.getEndOffset()), dispatchReceiver, valueArgument);
            return irExpressionSpecializeEqualsCall == null ? expression : irExpressionSpecializeEqualsCall;
        }
        return super.visitCall(expression);
    }

    private final boolean isEqualsMethodCallOnInlineClass(IrCall irCall) {
        IrType type;
        IrClassSymbol classOrNull;
        if (!IrUtilsKt.isEquals(irCall.getSymbol().getOwner())) {
            return false;
        }
        IrExpression dispatchReceiver = irCall.getDispatchReceiver();
        IrClass owner = (dispatchReceiver == null || (type = dispatchReceiver.getType()) == null || (classOrNull = IrTypesKt.getClassOrNull(type)) == null) ? null : classOrNull.getOwner();
        if (owner != null) {
            return getCanUseSpecializedEqMethod(owner);
        }
        return false;
    }

    private final boolean isEqEqCallOnInlineClass(IrCall irCall) {
        IrType type;
        IrClassSymbol classOrNull;
        if (!Intrinsics.areEqual(irCall.getSymbol(), getContext().getIrBuiltIns().getEqeqSymbol())) {
            return false;
        }
        IrExpression valueArgument = irCall.getValueArgument(0);
        IrClass owner = (valueArgument == null || (type = valueArgument.getType()) == null || (classOrNull = IrTypesKt.getClassOrNull(type)) == null) ? null : classOrNull.getOwner();
        if (owner != null) {
            return getCanUseSpecializedEqMethod(owner);
        }
        return false;
    }

    private final boolean getCanUseSpecializedEqMethod(IrClass irClass) {
        if (IrDeclarationsKt.isSingleFieldValueClass(irClass)) {
            return !Intrinsics.areEqual(IrUtilsKt.getFqNameWhenAvailable(irClass), StandardNames.RESULT_FQ_NAME) || getContext().getState().getLanguageVersionSettings().getApiVersion().compareTo(ApiVersion.KOTLIN_1_4) >= 0;
        }
        return false;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrExpression visitGetField(IrGetField expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrField owner = expression.getSymbol().getOwner();
        IrDeclarationParent parent = owner.getParent();
        if (Intrinsics.areEqual(owner.getOrigin(), IrDeclarationOrigin.PROPERTY_BACKING_FIELD.INSTANCE) && (parent instanceof IrClass)) {
            IrClass irClass = (IrClass) parent;
            if (IrDeclarationsKt.isSingleFieldValueClass(irClass) && Intrinsics.areEqual(owner.getName(), InlineClassAbiKt.getInlineClassFieldName(irClass))) {
                IrExpression receiver = expression.getReceiver();
                Intrinsics.checkNotNull(receiver);
                IrExpression irExpressionTransform = receiver.transform((IrElementTransformer<? super Object>) this, (Object) null);
                return coerceInlineClasses$default(this, irExpressionTransform, IrTypesKt.makeNotNull(irExpressionTransform.getType()), owner.getType(), false, 8, null);
            }
        }
        return super.visitGetField(expression);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrExpression visitGetValue(IrGetValue expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrValueDeclaration irValueDeclaration = this.valueMap.get(expression.getSymbol());
        if (irValueDeclaration != null) {
            return new IrGetValueImpl(expression.getStartOffset(), expression.getEndOffset(), irValueDeclaration.getType(), irValueDeclaration.getSymbol(), expression.getOrigin());
        }
        return super.visitGetValue(expression);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrExpression visitSetValue(IrSetValue expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrValueDeclaration irValueDeclaration = this.valueMap.get(expression.getSymbol());
        if (irValueDeclaration != null) {
            return new IrSetValueImpl(expression.getStartOffset(), expression.getEndOffset(), irValueDeclaration.getType(), irValueDeclaration.getSymbol(), expression.getValue().transform((IrElementTransformer<? super Object>) this, (Object) null), expression.getOrigin());
        }
        return super.visitSetValue(expression);
    }

    private final void buildBoxFunction(IrClass valueClass) {
        IrSimpleFunction irSimpleFunctionInvoke = getContext().getInlineClassReplacements().getGetBoxFunction().invoke(valueClass);
        DeclarationIrBuilder declarationIrBuilderCreateIrBuilder$default = LowerUtilsKt.createIrBuilder$default(getContext(), irSimpleFunctionInvoke.getSymbol(), 0, 0, 6, (Object) null);
        IrConstructor primaryConstructor = IrUtilsKt.getPrimaryConstructor(valueClass);
        Intrinsics.checkNotNull(primaryConstructor);
        IrConstructorCall irConstructorCallIrCall = ExpressionHelpersKt.irCall((IrBuilderWithScope) declarationIrBuilderCreateIrBuilder$default, primaryConstructor.getSymbol());
        IrUtilsKt.passTypeArgumentsFrom$default(irConstructorCallIrCall, irSimpleFunctionInvoke, 0, 2, null);
        irConstructorCallIrCall.putValueArgument(0, ExpressionHelpersKt.irGet(declarationIrBuilderCreateIrBuilder$default, irSimpleFunctionInvoke.getValueParameters().get(0)));
        Unit unit = Unit.INSTANCE;
        irSimpleFunctionInvoke.setBody(ExpressionHelpersKt.irExprBody(declarationIrBuilderCreateIrBuilder$default, irConstructorCallIrCall));
        valueClass.getDeclarations().add(irSimpleFunctionInvoke);
    }

    private final void buildUnboxFunction(IrClass irClass) {
        IrSimpleFunction irSimpleFunctionInvoke = getContext().getInlineClassReplacements().getGetUnboxFunction().invoke(irClass);
        IrField inlineClassBackingField = InlineClassesKt.getInlineClassBackingField(irClass);
        DeclarationIrBuilder declarationIrBuilderCreateIrBuilder$default = LowerUtilsKt.createIrBuilder$default(getContext(), irSimpleFunctionInvoke.getSymbol(), 0, 0, 6, (Object) null);
        IrBlockBodyBuilder irBlockBodyBuilder = new IrBlockBodyBuilder(declarationIrBuilderCreateIrBuilder$default.getContext(), declarationIrBuilderCreateIrBuilder$default.getScope(), declarationIrBuilderCreateIrBuilder$default.getStartOffset(), declarationIrBuilderCreateIrBuilder$default.getEndOffset());
        IrBlockBodyBuilder irBlockBodyBuilder2 = irBlockBodyBuilder;
        IrValueParameter dispatchReceiverParameter = irSimpleFunctionInvoke.getDispatchReceiverParameter();
        Intrinsics.checkNotNull(dispatchReceiverParameter);
        irBlockBodyBuilder.unaryPlus(ExpressionHelpersKt.irReturn(irBlockBodyBuilder2, ExpressionHelpersKt.irGetField$default(irBlockBodyBuilder2, ExpressionHelpersKt.irGet(irBlockBodyBuilder2, dispatchReceiverParameter), inlineClassBackingField, null, 4, null)));
        irSimpleFunctionInvoke.setBody(irBlockBodyBuilder.getIrBlockBody());
        irClass.getDeclarations().add(irSimpleFunctionInvoke);
    }

    private final void buildSpecializedEqualsMethodIfNeeded(IrClass valueClass) {
        IrFunctionAccessExpression irFunctionAccessExpressionIrEquals$default;
        IrSimpleFunction specializedEqualsMethod = getContext().getInlineClassReplacements().getSpecializedEqualsMethod(valueClass, getContext().getIrBuiltIns());
        if (specializedEqualsMethod.getBody() != null) {
            return;
        }
        IrValueParameter irValueParameter = specializedEqualsMethod.getValueParameters().get(0);
        IrValueParameter irValueParameter2 = specializedEqualsMethod.getValueParameters().get(1);
        IrType irTypeUnboxInlineClass = InlineClassAbiKt.unboxInlineClass(irValueParameter.getType());
        IrSimpleFunction irSimpleFunction = null;
        boolean z = false;
        for (IrSimpleFunction irSimpleFunction2 : IrUtilsKt.getFunctions(valueClass)) {
            if (IrUtilsKt.isEquals(irSimpleFunction2)) {
                if (z) {
                    throw new IllegalArgumentException("Sequence contains more than one matching element.");
                }
                z = true;
                irSimpleFunction = irSimpleFunction2;
            }
        }
        if (z) {
            IrSimpleFunction irSimpleFunction3 = irSimpleFunction;
            DeclarationIrBuilder declarationIrBuilderCreateIrBuilder$default = LowerUtilsKt.createIrBuilder$default(getContext(), valueClass.getSymbol(), 0, 0, 6, (Object) null);
            JvmBackendContext context = getContext();
            IrSimpleType inlineClassUnderlyingType = InlineClassesKt.getInlineClassUnderlyingType(valueClass);
            DeclarationIrBuilder declarationIrBuilder = declarationIrBuilderCreateIrBuilder$default;
            if (Intrinsics.areEqual(irSimpleFunction3.getOrigin(), IrDeclarationOrigin.DEFINED.INSTANCE)) {
                IrSimpleFunction irSimpleFunctionInvoke = context.getInlineClassReplacements().getGetBoxFunction().invoke(valueClass);
                IrFunctionAccessExpression irFunctionAccessExpressionIrCall = ExpressionHelpersKt.irCall(declarationIrBuilder, irSimpleFunction3);
                IrGetValueImpl irGetValueImplIrGet = ExpressionHelpersKt.irGet(declarationIrBuilder, irValueParameter);
                IrType type = irValueParameter.getType();
                IrSimpleType irSimpleType = inlineClassUnderlyingType;
                irFunctionAccessExpressionIrCall.setDispatchReceiver(buildSpecializedEqualsMethodIfNeeded$lambda$32$irBox(declarationIrBuilderCreateIrBuilder$default, irSimpleFunctionInvoke, coerceInlineClasses$default(this, irGetValueImplIrGet, type, irSimpleType, false, 8, null)));
                irFunctionAccessExpressionIrCall.putValueArgument(0, buildSpecializedEqualsMethodIfNeeded$lambda$32$irBox(declarationIrBuilderCreateIrBuilder$default, irSimpleFunctionInvoke, coerceInlineClasses$default(this, ExpressionHelpersKt.irGet(declarationIrBuilder, irValueParameter2), irValueParameter2.getType(), irSimpleType, false, 8, null)));
                irFunctionAccessExpressionIrEquals$default = irFunctionAccessExpressionIrCall;
            } else {
                IrSimpleType irSimpleType2 = inlineClassUnderlyingType;
                IrClass irClass = IrTypesKt.getClass(irSimpleType2);
                if (irClass != null && IrDeclarationsKt.isSingleFieldValueClass(irClass) && !org.jetbrains.kotlin.ir.types.IrTypeUtilsKt.isNullable(irSimpleType2)) {
                    IrFunctionAccessExpression irFunctionAccessExpressionIrCall2 = ExpressionHelpersKt.irCall(declarationIrBuilder, context.getInlineClassReplacements().getSpecializedEqualsMethod(irClass, context.getIrBuiltIns()));
                    irFunctionAccessExpressionIrCall2.putValueArgument(0, coerceInlineClasses$default(this, ExpressionHelpersKt.irGet(declarationIrBuilder, irValueParameter), irValueParameter.getType(), irSimpleType2, false, 8, null));
                    irFunctionAccessExpressionIrCall2.putValueArgument(1, coerceInlineClasses$default(this, ExpressionHelpersKt.irGet(declarationIrBuilder, irValueParameter2), irValueParameter2.getType(), irSimpleType2, false, 8, null));
                    irFunctionAccessExpressionIrEquals$default = irFunctionAccessExpressionIrCall2;
                } else {
                    irFunctionAccessExpressionIrEquals$default = ExpressionHelpersKt.irEquals$default(declarationIrBuilder, coerceInlineClasses$default(this, ExpressionHelpersKt.irGet(declarationIrBuilder, irValueParameter), irValueParameter.getType(), irTypeUnboxInlineClass, false, 8, null), coerceInlineClasses$default(this, ExpressionHelpersKt.irGet(declarationIrBuilder, irValueParameter2), irValueParameter2.getType(), irTypeUnboxInlineClass, false, 8, null), null, 4, null);
                }
                specializedEqualsMethod.setBody(ExpressionHelpersKt.irExprBody(declarationIrBuilder, irFunctionAccessExpressionIrEquals$default));
                valueClass.getDeclarations().add(specializedEqualsMethod);
                return;
            }
            specializedEqualsMethod.setBody(ExpressionHelpersKt.irExprBody(declarationIrBuilder, irFunctionAccessExpressionIrEquals$default));
            valueClass.getDeclarations().add(specializedEqualsMethod);
            return;
        }
        throw new NoSuchElementException("Sequence contains no element matching the predicate.");
    }

    private static final IrFunctionAccessExpression buildSpecializedEqualsMethodIfNeeded$lambda$32$irBox(DeclarationIrBuilder declarationIrBuilder, IrSimpleFunction irSimpleFunction, IrExpression irExpression) {
        IrFunctionAccessExpression irFunctionAccessExpressionIrCall = ExpressionHelpersKt.irCall(declarationIrBuilder, irSimpleFunction);
        irFunctionAccessExpressionIrCall.putValueArgument(0, irExpression);
        return irFunctionAccessExpressionIrCall;
    }

    private final void buildPrimaryInlineClassConstructor(IrClass valueClass, IrConstructor irConstructor) {
        IrFactory factory = valueClass.getFactory();
        IrFunctionBuilder irFunctionBuilder = new IrFunctionBuilder();
        IrConstructor irConstructor2 = irConstructor;
        irFunctionBuilder.updateFrom((IrFunction) irConstructor2);
        DescriptorVisibility PRIVATE = DescriptorVisibilities.PRIVATE;
        Intrinsics.checkNotNullExpressionValue(PRIVATE, "PRIVATE");
        irFunctionBuilder.setVisibility(PRIVATE);
        irFunctionBuilder.setOrigin(JvmLoweredDeclarationOrigin.SYNTHETIC_INLINE_CLASS_MEMBER.INSTANCE);
        irFunctionBuilder.setReturnType(irConstructor.getReturnType());
        irFunctionBuilder.setReturnType(IrUtilsKt.getDefaultType(valueClass));
        IrConstructor irConstructorBuildConstructor = DeclarationBuildersKt.buildConstructor(factory, irFunctionBuilder);
        valueClass.getDeclarations().add(irConstructorBuildConstructor);
        irConstructorBuildConstructor.setParent(valueClass);
        Iterator<T> it = irConstructor.getValueParameters().iterator();
        while (it.hasNext()) {
            ((IrValueParameter) it.next()).setDefaultValue(null);
        }
        IrUtilsKt.copyParameterDeclarationsFrom(irConstructorBuildConstructor, irConstructor2);
        irConstructorBuildConstructor.setAnnotations(irConstructor.getAnnotations());
        DeclarationIrBuilder declarationIrBuilderCreateIrBuilder$default = LowerUtilsKt.createIrBuilder$default(getContext(), irConstructorBuildConstructor.getSymbol(), 0, 0, 6, (Object) null);
        IrConstructor irConstructor3 = irConstructorBuildConstructor;
        IrBlockBodyBuilder irBlockBodyBuilder = new IrBlockBodyBuilder(declarationIrBuilderCreateIrBuilder$default.getContext(), declarationIrBuilderCreateIrBuilder$default.getScope(), irConstructor3.getStartOffset(), irConstructor3.getEndOffset());
        IrBlockBodyBuilder irBlockBodyBuilder2 = irBlockBodyBuilder;
        irBlockBodyBuilder.unaryPlus(ExpressionHelpersKt.irDelegatingConstructorCall(irBlockBodyBuilder2, (IrConstructor) SequencesKt.single(IrUtilsKt.getConstructors(irBlockBodyBuilder.getContext().getIrBuiltIns().getAnyClass().getOwner()))));
        IrValueParameter thisReceiver = valueClass.getThisReceiver();
        Intrinsics.checkNotNull(thisReceiver);
        irBlockBodyBuilder.unaryPlus(ExpressionHelpersKt.irSetField$default(irBlockBodyBuilder2, ExpressionHelpersKt.irGet(irBlockBodyBuilder2, thisReceiver), InlineClassesKt.getInlineClassBackingField(valueClass), ExpressionHelpersKt.irGet(irBlockBodyBuilder2, irConstructorBuildConstructor.getValueParameters().get(0)), null, 8, null));
        irConstructorBuildConstructor.setBody(irBlockBodyBuilder.getIrBlockBody());
        IrSimpleFunction replacementFunction = getContext().getInlineClassReplacements().getReplacementFunction(irConstructor2);
        Intrinsics.checkNotNull(replacementFunction);
        List<IrDeclaration> declarations = valueClass.getDeclarations();
        ArrayList arrayList = new ArrayList();
        for (Object obj : declarations) {
            if (obj instanceof IrAnonymousInitializer) {
                arrayList.add(obj);
            }
        }
        ArrayList arrayList2 = new ArrayList();
        for (Object obj2 : arrayList) {
            if (!((IrAnonymousInitializer) obj2).getIsStatic()) {
                arrayList2.add(obj2);
            }
        }
        ArrayList arrayList3 = arrayList2;
        Iterator<T> it2 = replacementFunction.getValueParameters().iterator();
        while (it2.hasNext()) {
            transformChildrenVoid((IrValueParameter) it2.next());
        }
        DeclarationIrBuilder declarationIrBuilderCreateIrBuilder$default2 = LowerUtilsKt.createIrBuilder$default(getContext(), replacementFunction.getSymbol(), 0, 0, 6, (Object) null);
        IrBlockBodyBuilder irBlockBodyBuilder3 = new IrBlockBodyBuilder(declarationIrBuilderCreateIrBuilder$default2.getContext(), declarationIrBuilderCreateIrBuilder$default2.getScope(), declarationIrBuilderCreateIrBuilder$default2.getStartOffset(), declarationIrBuilderCreateIrBuilder$default2.getEndOffset());
        IrValueParameter irValueParameter = replacementFunction.getValueParameters().get(0);
        IrBlockBodyBuilder irBlockBodyBuilder4 = irBlockBodyBuilder3;
        IrVariable irVariableIrTemporary$default = ExpressionHelpersKt.irTemporary$default(irBlockBodyBuilder3, coerceInlineClasses(ExpressionHelpersKt.irGet(irBlockBodyBuilder4, irValueParameter), irValueParameter.getType(), replacementFunction.getReturnType(), true), null, null, false, null, 30, null);
        Map<IrValueSymbol, IrValueDeclaration> map = this.valueMap;
        IrValueParameter thisReceiver2 = valueClass.getThisReceiver();
        Intrinsics.checkNotNull(thisReceiver2);
        map.put(thisReceiver2.getSymbol(), irVariableIrTemporary$default);
        Iterator it3 = arrayList3.iterator();
        while (it3.hasNext()) {
            Iterator<IrStatement> it4 = ((IrAnonymousInitializer) it3.next()).getBody().getStatements().iterator();
            while (it4.hasNext()) {
                irBlockBodyBuilder3.unaryPlus((IrStatement) PatchDeclarationParentsKt.patchDeclarationParents(IrElementsKt.transformStatement(it4.next(), this), replacementFunction));
            }
        }
        irBlockBodyBuilder3.unaryPlus(ExpressionHelpersKt.irReturn(irBlockBodyBuilder4, ExpressionHelpersKt.irGet(irBlockBodyBuilder4, irVariableIrTemporary$default)));
        replacementFunction.setBody(irBlockBodyBuilder3.getIrBlockBody());
        valueClass.getDeclarations().removeAll(arrayList3);
        valueClass.getDeclarations().add(replacementFunction);
    }
}
