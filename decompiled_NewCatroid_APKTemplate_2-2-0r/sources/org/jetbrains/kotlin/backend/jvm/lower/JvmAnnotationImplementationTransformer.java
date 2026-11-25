package org.jetbrains.kotlin.backend.jvm.lower;

import com.gaurav.avnc.vnc.XKeySym;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.sequences.SequencesKt;
import org.antlr.stringtemplate.language.ASTExpr;
import org.jetbrains.kotlin.backend.common.ScopeWithIr;
import org.jetbrains.kotlin.backend.common.ir.BuiltinSymbolsBase;
import org.jetbrains.kotlin.backend.common.lower.AnnotationImplementationMemberGenerator;
import org.jetbrains.kotlin.backend.common.lower.AnnotationImplementationTransformer;
import org.jetbrains.kotlin.backend.common.lower.AnnotationImplementationTransformerKt;
import org.jetbrains.kotlin.backend.common.lower.DeclarationIrBuilder;
import org.jetbrains.kotlin.backend.common.lower.LowerUtilsKt;
import org.jetbrains.kotlin.backend.jvm.InlineClassAbiKt;
import org.jetbrains.kotlin.backend.jvm.JvmBackendContext;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrBuilder;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrBuilderKt;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrInlineUtilsKt;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrUtilsKt;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.ir.DeepCopyIrTreeWithDeclarationsKt;
import org.jetbrains.kotlin.ir.IrBuiltIns;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.builders.ExpressionHelpersKt;
import org.jetbrains.kotlin.ir.builders.IrBlockBodyBuilder;
import org.jetbrains.kotlin.ir.builders.IrBlockBuilder;
import org.jetbrains.kotlin.ir.builders.IrBuilderKt;
import org.jetbrains.kotlin.ir.builders.IrBuilderWithScope;
import org.jetbrains.kotlin.ir.builders.IrStatementsBuilder;
import org.jetbrains.kotlin.ir.builders.PrimitivesKt;
import org.jetbrains.kotlin.ir.builders.declarations.DeclarationBuildersKt;
import org.jetbrains.kotlin.ir.builders.declarations.IrFieldBuilder;
import org.jetbrains.kotlin.ir.builders.declarations.IrFunctionBuilder;
import org.jetbrains.kotlin.ir.builders.declarations.IrPropertyBuilder;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrFactoryHelpersKt;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.declarations.IrVariable;
import org.jetbrains.kotlin.ir.expressions.IrBlockBody;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrErrorExpression;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrExpressionBody;
import org.jetbrains.kotlin.ir.expressions.IrFunctionAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrGetValue;
import org.jetbrains.kotlin.ir.expressions.IrStatementOrigin;
import org.jetbrains.kotlin.ir.expressions.impl.IrDelegatingConstructorCallImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetValueImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrWhileLoopImpl;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrConstructorSymbol;
import org.jetbrains.kotlin.ir.symbols.IrFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.types.IrSimpleType;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypeArgument;
import org.jetbrains.kotlin.ir.types.IrTypePredicatesKt;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.DumpIrTreeOptions;
import org.jetbrains.kotlin.ir.util.IrTypeUtilsKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.util.RenderIrElementKt;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoidKt;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.util.OperatorNameConventions;

/* JADX INFO: compiled from: JvmAnnotationImplementationTransformer.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000v\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010#\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001:\u0001-B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0018\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017H\u0016J8\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u00152\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001c2\u0006\u0010\u001e\u001a\u00020\u001c2\u0006\u0010\u001f\u001a\u00020 H\u0016J\u0010\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020$H\u0016J \u0010%\u001a\u00020\u00192\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u001a\u001a\u00020\u00152\u0006\u0010&\u001a\u00020\u0013H\u0016J\u0018\u0010'\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u00152\u0006\u0010\u0014\u001a\u00020\u0015H\u0016J\u0010\u0010(\u001a\u00020)2\u0006\u0010\u0016\u001a\u00020\u0017H\u0016J\u001c\u0010*\u001a\u00020)*\u00020)2\u0006\u0010#\u001a\u00020$2\u0006\u0010+\u001a\u00020,H\u0016R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\u00020\n8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0014\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00110\u0010X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006."}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/lower/JvmAnnotationImplementationTransformer;", "Lorg/jetbrains/kotlin/backend/common/lower/AnnotationImplementationTransformer;", "jvmContext", "Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "file", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "(Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;Lorg/jetbrains/kotlin/ir/declarations/IrFile;)V", "implementor", "Lorg/jetbrains/kotlin/backend/jvm/lower/JvmAnnotationImplementationTransformer$AnnotationPropertyImplementor;", "inInlineFunctionScope", "", "getInInlineFunctionScope", "()Z", "getJvmContext", "()Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "publicAnnotationImplementationClasses", "", "Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "chooseConstructor", "Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "implClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrConstructorCall;", "generateFunctionBodies", "", "annotationClass", "eqFun", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "hcFun", "toStringFun", "generator", "Lorg/jetbrains/kotlin/backend/common/lower/AnnotationImplementationMemberGenerator;", "getArrayContentEqualsSymbol", "Lorg/jetbrains/kotlin/ir/symbols/IrFunctionSymbol;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/ir/types/IrType;", "implementAnnotationPropertiesAndConstructor", "generatedConstructor", "implementPlatformSpecificParts", "visitConstructorCall", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "transformArrayEqualsArgument", "irBuilder", "Lorg/jetbrains/kotlin/ir/builders/IrBlockBodyBuilder;", "AnnotationPropertyImplementor", "backend.jvm.lower"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JvmAnnotationImplementationTransformer extends AnnotationImplementationTransformer {
    private final AnnotationPropertyImplementor implementor;
    private final JvmBackendContext jvmContext;
    private final Set<IrClassSymbol> publicAnnotationImplementationClasses;

    public final JvmBackendContext getJvmContext() {
        return this.jvmContext;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public JvmAnnotationImplementationTransformer(JvmBackendContext jvmContext, IrFile file) {
        super(jvmContext, file);
        Intrinsics.checkNotNullParameter(jvmContext, "jvmContext");
        Intrinsics.checkNotNullParameter(file, "file");
        this.jvmContext = jvmContext;
        this.publicAnnotationImplementationClasses = new LinkedHashSet();
        this.implementor = new AnnotationPropertyImplementor(jvmContext.getIrFactory(), jvmContext.getIrBuiltIns(), jvmContext.getIr().getSymbols(), jvmContext.getIr().getSymbols().getJavaLangClass(), jvmContext.getIr().getSymbols().getKClassJavaPropertyGetter().getSymbol(), AnnotationImplementationTransformerKt.getANNOTATION_IMPLEMENTATION());
    }

    private final boolean getInInlineFunctionScope() {
        List<ScopeWithIr> allScopes = getAllScopes();
        if ((allScopes instanceof Collection) && allScopes.isEmpty()) {
            return false;
        }
        Iterator<T> it = allScopes.iterator();
        while (it.hasNext()) {
            IrElement irElement = ((ScopeWithIr) it.next()).getIrElement();
            IrDeclaration irDeclaration = irElement instanceof IrDeclaration ? (IrDeclaration) irElement : null;
            if (irDeclaration != null && JvmIrInlineUtilsKt.isInPublicInlineScope(irDeclaration)) {
                return true;
            }
        }
        return false;
    }

    @Override // org.jetbrains.kotlin.backend.common.lower.AnnotationImplementationTransformer
    public IrConstructor chooseConstructor(IrClass implClass, IrConstructorCall expression) {
        Intrinsics.checkNotNullParameter(implClass, "implClass");
        Intrinsics.checkNotNullParameter(expression, "expression");
        return (IrConstructor) SequencesKt.single(IrUtilsKt.getConstructors(implClass));
    }

    @Override // org.jetbrains.kotlin.backend.common.lower.AnnotationImplementationTransformer, org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrExpression visitConstructorCall(IrConstructorCall expression) {
        IrClass owner;
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrClassSymbol classOrNull = IrTypesKt.getClassOrNull(expression.getType());
        if (classOrNull != null && (owner = classOrNull.getOwner()) != null && IrUtilsKt.isAnnotationClass(owner) && getInInlineFunctionScope()) {
            this.publicAnnotationImplementationClasses.add(classOrNull);
        }
        return super.visitConstructorCall(expression);
    }

    @Override // org.jetbrains.kotlin.backend.common.lower.AnnotationImplementationTransformer
    public IrExpression transformArrayEqualsArgument(IrExpression irExpression, IrType type, IrBlockBodyBuilder irBuilder) {
        Intrinsics.checkNotNullParameter(irExpression, "<this>");
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(irBuilder, "irBuilder");
        if (!IrTypeUtilsKt.isUnsignedArray(type)) {
            return irExpression;
        }
        IrCall irCall = ExpressionHelpersKt.irCall((IrBuilderWithScope) irBuilder, this.jvmContext.getIr().getSymbols().getUnsafeCoerceIntrinsic());
        irCall.putTypeArgument(0, type);
        irCall.putTypeArgument(1, InlineClassAbiKt.unboxInlineClass(type));
        irCall.putValueArgument(0, irExpression);
        return irCall;
    }

    @Override // org.jetbrains.kotlin.backend.common.lower.AnnotationImplementationTransformer
    public IrFunctionSymbol getArrayContentEqualsSymbol(IrType type) {
        Object next;
        Intrinsics.checkNotNullParameter(type, "type");
        if (!IrTypeUtilsKt.isPrimitiveArray(type)) {
            type = IrTypeUtilsKt.isUnsignedArray(type) ? InlineClassAbiKt.unboxInlineClass(type) : this.jvmContext.getIr().getSymbols().getArrayOfAnyNType();
        }
        Iterator<T> it = this.jvmContext.getIr().getSymbols().getArraysClass().getOwner().getDeclarations().iterator();
        while (true) {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
            IrDeclaration irDeclaration = (IrDeclaration) next;
            if (irDeclaration instanceof IrFunction) {
                IrFunction irFunction = (IrFunction) irDeclaration;
                if (Intrinsics.areEqual(irFunction.getName().asString(), Namer.EQUALS_METHOD_NAME) && irFunction.getValueParameters().size() == 2 && Intrinsics.areEqual(((IrValueParameter) CollectionsKt.first((List) irFunction.getValueParameters())).getType(), type)) {
                    break;
                }
            }
        }
        if (!(next instanceof IrFunction)) {
            next = null;
        }
        IrFunction irFunction2 = (IrFunction) next;
        if (irFunction2 == null) {
            throw new IllegalArgumentException(("Can't find Arrays.equals method for type " + RenderIrElementKt.render$default(type, (DumpIrTreeOptions) null, 1, (Object) null)).toString());
        }
        return irFunction2.getSymbol();
    }

    @Override // org.jetbrains.kotlin.backend.common.lower.AnnotationImplementationTransformer
    public void implementPlatformSpecificParts(IrClass annotationClass, IrClass implClass) {
        Intrinsics.checkNotNullParameter(annotationClass, "annotationClass");
        Intrinsics.checkNotNullParameter(implClass, "implClass");
        if (this.publicAnnotationImplementationClasses.contains(annotationClass.getSymbol())) {
            this.jvmContext.getPublicAbiSymbols().add(implClass.getSymbol());
        }
        IrSimpleFunction irSimpleFunctionAddFunction$default = DeclarationBuildersKt.addFunction$default(implClass, "annotationType", IrTypesKt.getStarProjectedType(this.jvmContext.getIr().getSymbols().getJavaLangClass()), null, null, false, false, false, false, AnnotationImplementationTransformerKt.getANNOTATION_IMPLEMENTATION(), 0, 0, XKeySym.XK_Cyrillic_EL, null);
        JvmIrBuilder jvmIrBuilderCreateJvmIrBuilder$default = JvmIrBuilderKt.createJvmIrBuilder$default(this.jvmContext, irSimpleFunctionAddFunction$default.getSymbol(), 0, 0, 6, null);
        JvmIrBuilder jvmIrBuilder = jvmIrBuilderCreateJvmIrBuilder$default;
        IrBlockBodyBuilder irBlockBodyBuilder = new IrBlockBodyBuilder(jvmIrBuilder.getContext(), jvmIrBuilder.getScope(), jvmIrBuilder.getStartOffset(), jvmIrBuilder.getEndOffset());
        irBlockBodyBuilder.unaryPlus(ExpressionHelpersKt.irReturn(irBlockBodyBuilder, JvmIrUtilsKt.javaClassReference(jvmIrBuilderCreateJvmIrBuilder$default, IrUtilsKt.getDefaultType(annotationClass))));
        irSimpleFunctionAddFunction$default.setBody(irBlockBodyBuilder.getIrBlockBody());
    }

    @Override // org.jetbrains.kotlin.backend.common.lower.AnnotationImplementationTransformer
    public void implementAnnotationPropertiesAndConstructor(IrClass implClass, IrClass annotationClass, IrConstructor generatedConstructor) {
        Intrinsics.checkNotNullParameter(implClass, "implClass");
        Intrinsics.checkNotNullParameter(annotationClass, "annotationClass");
        Intrinsics.checkNotNullParameter(generatedConstructor, "generatedConstructor");
        this.implementor.implementAnnotationPropertiesAndConstructor(annotationClass, getAnnotationProperties(annotationClass), implClass, generatedConstructor, this);
    }

    @Override // org.jetbrains.kotlin.backend.common.lower.AnnotationImplementationTransformer
    public void generateFunctionBodies(IrClass annotationClass, IrClass implClass, IrSimpleFunction eqFun, IrSimpleFunction hcFun, IrSimpleFunction toStringFun, AnnotationImplementationMemberGenerator generator) {
        Intrinsics.checkNotNullParameter(annotationClass, "annotationClass");
        Intrinsics.checkNotNullParameter(implClass, "implClass");
        Intrinsics.checkNotNullParameter(eqFun, "eqFun");
        Intrinsics.checkNotNullParameter(hcFun, "hcFun");
        Intrinsics.checkNotNullParameter(toStringFun, "toStringFun");
        Intrinsics.checkNotNullParameter(generator, "generator");
        List<IrProperty> annotationProperties = getAnnotationProperties(annotationClass);
        List<IrProperty> annotationProperties2 = getAnnotationProperties(implClass);
        generator.generateEqualsUsingGetters(eqFun, IrUtilsKt.getDefaultType(annotationClass), annotationProperties);
        generator.generateHashCodeMethod(hcFun, annotationProperties2);
        generator.generateToStringMethod(toStringFun, annotationProperties2);
    }

    /* JADX INFO: compiled from: JvmAnnotationImplementationTransformer.kt */
    @Metadata(d1 = {"\u0000j\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B5\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r¢\u0006\u0002\u0010\u000eJ6\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001e2\f\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020!0 2\u0006\u0010\"\u001a\u00020\u001e2\u0006\u0010#\u001a\u00020$2\b\u0010%\u001a\u0004\u0018\u00010&J\f\u0010'\u001a\u00020(*\u00020)H\u0002J\u0014\u0010*\u001a\u00020+*\u00020,2\u0006\u0010-\u001a\u00020+H\u0002J\u0014\u0010.\u001a\u00020+*\u00020,2\u0006\u0010/\u001a\u00020+H\u0002J\f\u00100\u001a\u00020)*\u00020)H\u0002R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0011\u0010\n\u001a\u00020\u000b¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0011\u0010\f\u001a\u00020\r¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001a¨\u00061"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/lower/JvmAnnotationImplementationTransformer$AnnotationPropertyImplementor;", "", "irFactory", "Lorg/jetbrains/kotlin/ir/declarations/IrFactory;", "irBuiltIns", "Lorg/jetbrains/kotlin/ir/IrBuiltIns;", "symbols", "Lorg/jetbrains/kotlin/backend/common/ir/BuiltinSymbolsBase;", "javaLangClassSymbol", "Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "kClassJavaPropertyGetterSymbol", "Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "originForProp", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;", "(Lorg/jetbrains/kotlin/ir/declarations/IrFactory;Lorg/jetbrains/kotlin/ir/IrBuiltIns;Lorg/jetbrains/kotlin/backend/common/ir/BuiltinSymbolsBase;Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;)V", "getIrBuiltIns", "()Lorg/jetbrains/kotlin/ir/IrBuiltIns;", "getIrFactory", "()Lorg/jetbrains/kotlin/ir/declarations/IrFactory;", "getJavaLangClassSymbol", "()Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "getKClassJavaPropertyGetterSymbol", "()Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "getOriginForProp", "()Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;", "getSymbols", "()Lorg/jetbrains/kotlin/backend/common/ir/BuiltinSymbolsBase;", "implementAnnotationPropertiesAndConstructor", "", "annotationClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "annotationProperties", "", "Lorg/jetbrains/kotlin/ir/declarations/IrProperty;", "implClass", "generatedConstructor", "Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "defaultValueTransformer", "Lorg/jetbrains/kotlin/ir/visitors/IrElementTransformerVoid;", "isKClassArray", "", "Lorg/jetbrains/kotlin/ir/types/IrType;", "kClassArrayToJClassArray", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "Lorg/jetbrains/kotlin/ir/builders/IrBuilderWithScope;", "kClassArray", "kClassToJClass", "irExpression", "kClassToJClassIfNeeded", "backend.jvm.lower"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class AnnotationPropertyImplementor {
        private final IrBuiltIns irBuiltIns;
        private final IrFactory irFactory;
        private final IrClassSymbol javaLangClassSymbol;
        private final IrSimpleFunctionSymbol kClassJavaPropertyGetterSymbol;
        private final IrDeclarationOrigin originForProp;
        private final BuiltinSymbolsBase symbols;

        public AnnotationPropertyImplementor(IrFactory irFactory, IrBuiltIns irBuiltIns, BuiltinSymbolsBase symbols, IrClassSymbol javaLangClassSymbol, IrSimpleFunctionSymbol kClassJavaPropertyGetterSymbol, IrDeclarationOrigin originForProp) {
            Intrinsics.checkNotNullParameter(irFactory, "irFactory");
            Intrinsics.checkNotNullParameter(irBuiltIns, "irBuiltIns");
            Intrinsics.checkNotNullParameter(symbols, "symbols");
            Intrinsics.checkNotNullParameter(javaLangClassSymbol, "javaLangClassSymbol");
            Intrinsics.checkNotNullParameter(kClassJavaPropertyGetterSymbol, "kClassJavaPropertyGetterSymbol");
            Intrinsics.checkNotNullParameter(originForProp, "originForProp");
            this.irFactory = irFactory;
            this.irBuiltIns = irBuiltIns;
            this.symbols = symbols;
            this.javaLangClassSymbol = javaLangClassSymbol;
            this.kClassJavaPropertyGetterSymbol = kClassJavaPropertyGetterSymbol;
            this.originForProp = originForProp;
        }

        public final IrFactory getIrFactory() {
            return this.irFactory;
        }

        public final IrBuiltIns getIrBuiltIns() {
            return this.irBuiltIns;
        }

        public final BuiltinSymbolsBase getSymbols() {
            return this.symbols;
        }

        public final IrClassSymbol getJavaLangClassSymbol() {
            return this.javaLangClassSymbol;
        }

        public final IrSimpleFunctionSymbol getKClassJavaPropertyGetterSymbol() {
            return this.kClassJavaPropertyGetterSymbol;
        }

        public final IrDeclarationOrigin getOriginForProp() {
            return this.originForProp;
        }

        private final IrExpression kClassArrayToJClassArray(IrBuilderWithScope irBuilderWithScope, IrExpression irExpression) {
            Object next;
            IrSimpleType starProjectedType = IrTypesKt.getStarProjectedType(this.javaLangClassSymbol);
            IrSimpleType irSimpleTypeTypeWith = IrTypesKt.typeWith(this.symbols.getArray(), starProjectedType);
            IrClass owner = this.symbols.getArray().getOwner();
            IrSimpleFunctionSymbol arrayOfNulls = this.symbols.getArrayOfNulls();
            Iterator<T> it = owner.getDeclarations().iterator();
            while (true) {
                if (!it.hasNext()) {
                    next = null;
                    break;
                }
                next = it.next();
                IrDeclaration irDeclaration = (IrDeclaration) next;
                if ((irDeclaration instanceof IrProperty) && Intrinsics.areEqual(((IrProperty) irDeclaration).getName().asString(), "size")) {
                    break;
                }
            }
            if (!(next instanceof IrProperty)) {
                next = null;
            }
            IrProperty irProperty = (IrProperty) next;
            Intrinsics.checkNotNull(irProperty);
            IrSimpleFunction getter = irProperty.getGetter();
            Intrinsics.checkNotNull(getter);
            IrBlockBuilder irBlockBuilder = new IrBlockBuilder(irBuilderWithScope.getContext(), irBuilderWithScope.getScope(), irBuilderWithScope.getStartOffset(), irBuilderWithScope.getEndOffset(), null, null, false, 64, null);
            IrBlockBuilder irBlockBuilder2 = irBlockBuilder;
            IrVariable irVariableCreateTmpVariable$default = IrBuilderKt.createTmpVariable$default((IrStatementsBuilder) irBlockBuilder2, irExpression, "src", false, (IrDeclarationOrigin) null, (IrType) null, 24, (Object) null);
            IrBlockBuilder irBlockBuilder3 = irBlockBuilder;
            IrVariable irVariableCreateTmpVariable$default2 = IrBuilderKt.createTmpVariable$default((IrStatementsBuilder) irBlockBuilder2, (IrExpression) ExpressionHelpersKt.irInt$default(irBlockBuilder3, 0, null, 2, null), ASTExpr.DEFAULT_INDEX_VARIABLE_NAME, true, (IrDeclarationOrigin) null, (IrType) null, 24, (Object) null);
            IrFunctionAccessExpression irFunctionAccessExpressionIrCall = ExpressionHelpersKt.irCall(irBlockBuilder3, getter);
            IrVariable irVariable = irVariableCreateTmpVariable$default;
            irFunctionAccessExpressionIrCall.setDispatchReceiver(ExpressionHelpersKt.irGet(irBlockBuilder3, irVariable));
            IrVariable irVariableCreateTmpVariable$default3 = IrBuilderKt.createTmpVariable$default((IrStatementsBuilder) irBlockBuilder2, (IrExpression) irFunctionAccessExpressionIrCall, "size", false, (IrDeclarationOrigin) null, (IrType) null, 24, (Object) null);
            IrCall irCallIrCall$default = ExpressionHelpersKt.irCall$default(irBlockBuilder3, arrayOfNulls, irSimpleTypeTypeWith, 0, 0, null, 28, null);
            CollectionsKt.listOf(starProjectedType);
            IrVariable irVariable2 = irVariableCreateTmpVariable$default3;
            irCallIrCall$default.putValueArgument(0, ExpressionHelpersKt.irGet(irBlockBuilder3, irVariable2));
            IrVariable irVariableCreateTmpVariable$default4 = IrBuilderKt.createTmpVariable$default((IrStatementsBuilder) irBlockBuilder2, (IrExpression) irCallIrCall$default, (String) null, false, (IrDeclarationOrigin) null, (IrType) null, 30, (Object) null);
            int startOffset = irBlockBuilder.getStartOffset();
            int endOffset = irBlockBuilder.getEndOffset();
            IrSimpleFunctionSymbol irSimpleFunctionSymbol = irBlockBuilder.getContext().getIrBuiltIns().getLessFunByOperandType().get(IrTypesKt.getClassifierOrFail(irBlockBuilder.getContext().getIrBuiltIns().getIntType()));
            Intrinsics.checkNotNull(irSimpleFunctionSymbol);
            IrVariable irVariable3 = irVariableCreateTmpVariable$default2;
            IrExpression irExpressionPrimitiveOp2 = PrimitivesKt.primitiveOp2(startOffset, endOffset, irSimpleFunctionSymbol, irBlockBuilder.getContext().getIrBuiltIns().getBooleanType(), IrStatementOrigin.LT.INSTANCE, ExpressionHelpersKt.irGet(irBlockBuilder3, irVariable3), ExpressionHelpersKt.irGet(irBlockBuilder3, irVariable2));
            boolean z = false;
            IrSimpleFunction irSimpleFunction = null;
            for (IrSimpleFunction irSimpleFunction2 : IrUtilsKt.getFunctions(owner)) {
                if (Intrinsics.areEqual(irSimpleFunction2.getName(), OperatorNameConventions.SET)) {
                    if (z) {
                        throw new IllegalArgumentException("Sequence contains more than one matching element.");
                    }
                    irSimpleFunction = irSimpleFunction2;
                    z = true;
                }
            }
            if (z) {
                IrSimpleFunction irSimpleFunction3 = irSimpleFunction;
                boolean z2 = false;
                IrSimpleFunction irSimpleFunction4 = null;
                for (IrSimpleFunction irSimpleFunction5 : IrUtilsKt.getFunctions(owner)) {
                    if (Intrinsics.areEqual(irSimpleFunction5.getName(), OperatorNameConventions.GET)) {
                        if (z2) {
                            throw new IllegalArgumentException("Sequence contains more than one matching element.");
                        }
                        irSimpleFunction4 = irSimpleFunction5;
                        z2 = true;
                    }
                }
                if (z2) {
                    IrSimpleFunction irSimpleFunction6 = irSimpleFunction4;
                    IrClass irClass = IrTypesKt.getClass(irBlockBuilder.getContext().getIrBuiltIns().getIntType());
                    Intrinsics.checkNotNull(irClass);
                    IrSimpleFunction irSimpleFunction7 = null;
                    boolean z3 = false;
                    for (IrSimpleFunction irSimpleFunction8 : IrUtilsKt.getFunctions(irClass)) {
                        if (Intrinsics.areEqual(irSimpleFunction8.getName(), OperatorNameConventions.INC)) {
                            if (z3) {
                                throw new IllegalArgumentException("Sequence contains more than one matching element.");
                            }
                            irSimpleFunction7 = irSimpleFunction8;
                            z3 = true;
                        }
                    }
                    if (z3) {
                        IrWhileLoopImpl irWhileLoopImplIrWhile$default = IrBuilderKt.irWhile$default(irBlockBuilder3, null, 1, null);
                        irWhileLoopImplIrWhile$default.setCondition(irExpressionPrimitiveOp2);
                        IrBlockBuilder irBlockBuilder4 = new IrBlockBuilder(irBlockBuilder3.getContext(), irBlockBuilder3.getScope(), irBlockBuilder3.getStartOffset(), irBlockBuilder3.getEndOffset(), null, null, false, 64, null);
                        IrBlockBuilder irBlockBuilder5 = irBlockBuilder4;
                        IrVariable irVariableCreateTmpVariable$default5 = IrBuilderKt.createTmpVariable$default((IrStatementsBuilder) irBlockBuilder4, (IrExpression) ExpressionHelpersKt.irGet(irBlockBuilder5, irVariable3), (String) null, false, (IrDeclarationOrigin) null, (IrType) null, 30, (Object) null);
                        IrFunctionAccessExpression irFunctionAccessExpressionIrCall2 = ExpressionHelpersKt.irCall(irBlockBuilder5, irSimpleFunction6);
                        irFunctionAccessExpressionIrCall2.setDispatchReceiver(ExpressionHelpersKt.irGet(irBlockBuilder5, irVariable));
                        IrVariable irVariable4 = irVariableCreateTmpVariable$default5;
                        irFunctionAccessExpressionIrCall2.putValueArgument(0, ExpressionHelpersKt.irGet(irBlockBuilder5, irVariable4));
                        IrFunctionAccessExpression irFunctionAccessExpressionIrCall3 = ExpressionHelpersKt.irCall(irBlockBuilder5, irSimpleFunction3);
                        IrVariable irVariable5 = irVariableCreateTmpVariable$default4;
                        irFunctionAccessExpressionIrCall3.setDispatchReceiver(ExpressionHelpersKt.irGet(irBlockBuilder5, irVariable5));
                        irFunctionAccessExpressionIrCall3.putValueArgument(0, ExpressionHelpersKt.irGet(irBlockBuilder5, irVariable4));
                        irFunctionAccessExpressionIrCall3.putValueArgument(1, kClassToJClass(irBlockBuilder5, irFunctionAccessExpressionIrCall2));
                        irBlockBuilder4.unaryPlus(irFunctionAccessExpressionIrCall3);
                        irBlockBuilder4.unaryPlus(ExpressionHelpersKt.irSet$default(irBlockBuilder5, irVariableCreateTmpVariable$default2.getSymbol(), ExpressionHelpersKt.irCallOp(irBlockBuilder5, irSimpleFunction7.getSymbol(), irVariableCreateTmpVariable$default2.getType(), ExpressionHelpersKt.irGet(irBlockBuilder5, irVariable3), (24 & 8) != 0 ? null : null, (24 & 16) != 0 ? null : null), (IrStatementOrigin) null, 4, (Object) null));
                        irWhileLoopImplIrWhile$default.setBody(irBlockBuilder4.getIrBlockBody());
                        irBlockBuilder.unaryPlus(irWhileLoopImplIrWhile$default);
                        irBlockBuilder.unaryPlus(ExpressionHelpersKt.irGet(irBlockBuilder3, irVariable5));
                        return irBlockBuilder.getIrBlockBody();
                    }
                    throw new NoSuchElementException("Sequence contains no element matching the predicate.");
                }
                throw new NoSuchElementException("Sequence contains no element matching the predicate.");
            }
            throw new NoSuchElementException("Sequence contains no element matching the predicate.");
        }

        private final IrType kClassToJClassIfNeeded(IrType irType) {
            if (IrTypePredicatesKt.isKClass(irType)) {
                return IrTypesKt.getStarProjectedType(this.javaLangClassSymbol);
            }
            return isKClassArray(irType) ? IrTypesKt.typeWith(this.symbols.getArray(), IrTypesKt.getStarProjectedType(this.javaLangClassSymbol)) : irType;
        }

        private final boolean isKClassArray(IrType irType) {
            IrType typeOrNull;
            return (irType instanceof IrSimpleType) && IrTypePredicatesKt.isArray(irType) && (typeOrNull = IrTypesKt.getTypeOrNull((IrTypeArgument) CollectionsKt.single((List) ((IrSimpleType) irType).getArguments()))) != null && IrTypePredicatesKt.isKClass(typeOrNull);
        }

        private final IrExpression kClassToJClass(IrBuilderWithScope irBuilderWithScope, IrExpression irExpression) {
            IrCall irCallIrGet = ExpressionHelpersKt.irGet(irBuilderWithScope, IrTypesKt.getStarProjectedType(this.javaLangClassSymbol), null, this.kClassJavaPropertyGetterSymbol);
            irCallIrGet.setExtensionReceiver(irExpression);
            return irCallIrGet;
        }

        /* JADX WARN: Code duplicated, block: B:27:0x014d A[DONT_INVERT] */
        /* JADX WARN: Code duplicated, block: B:28:0x014f  */
        /* JADX WARN: Code duplicated, block: B:29:0x015b  */
        /* JADX WARN: Code duplicated, block: B:38:0x0178  */
        public final void implementAnnotationPropertiesAndConstructor(IrClass annotationClass, List<? extends IrProperty> annotationProperties, IrClass implClass, IrConstructor generatedConstructor, IrElementTransformerVoid defaultValueTransformer) {
            LinkedHashMap linkedHashMapEmptyMap;
            IrExpressionBody defaultValue;
            IrValueParameter irValueParameter;
            IrExpressionBody irExpressionBody;
            IrGetValueImpl irGetValueImplKClassArrayToJClassArray;
            IrExpressionBody initializer;
            List<IrValueParameter> valueParameters;
            IrClass implClass2 = implClass;
            Intrinsics.checkNotNullParameter(annotationClass, "annotationClass");
            Intrinsics.checkNotNullParameter(annotationProperties, "annotationProperties");
            Intrinsics.checkNotNullParameter(implClass2, "implClass");
            Intrinsics.checkNotNullParameter(generatedConstructor, "generatedConstructor");
            int i = -2;
            DeclarationIrBuilder declarationIrBuilderCreateIrBuilder = LowerUtilsKt.createIrBuilder(this.irBuiltIns, generatedConstructor.getSymbol(), -2, -2);
            IrBlockBody irBlockBodyCreateBlockBody = IrFactoryHelpersKt.createBlockBody(this.irFactory, -2, -2, (List<? extends IrStatement>) CollectionsKt.listOf(new IrDelegatingConstructorCallImpl(-2, -2, this.irBuiltIns.getUnitType(), (IrConstructorSymbol) SequencesKt.single(IrUtilsKt.getConstructors(this.irBuiltIns.getAnyClass())), 0, 0)));
            generatedConstructor.setBody(irBlockBodyCreateBlockBody);
            IrConstructor primaryConstructor = IrUtilsKt.getPrimaryConstructor(annotationClass);
            if (primaryConstructor == null || (valueParameters = primaryConstructor.getValueParameters()) == null) {
                linkedHashMapEmptyMap = null;
            } else {
                List<IrValueParameter> list = valueParameters;
                linkedHashMapEmptyMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(list, 10)), 16));
                for (Object obj : list) {
                    linkedHashMapEmptyMap.put(((IrValueParameter) obj).getName(), obj);
                }
            }
            if (linkedHashMapEmptyMap == null) {
                linkedHashMapEmptyMap = MapsKt.emptyMap();
            }
            Iterator it = annotationProperties.iterator();
            while (it.hasNext()) {
                IrProperty irProperty = (IrProperty) it.next();
                IrSimpleFunction getter = irProperty.getGetter();
                Intrinsics.checkNotNull(getter);
                IrType returnType = getter.getReturnType();
                IrType irTypeKClassToJClassIfNeeded = kClassToJClassIfNeeded(returnType);
                Name name2 = irProperty.getName();
                IrFactory irFactory = this.irFactory;
                IrFieldBuilder irFieldBuilder = new IrFieldBuilder();
                irFieldBuilder.setStartOffset(i);
                irFieldBuilder.setEndOffset(i);
                irFieldBuilder.setName(name2);
                irFieldBuilder.setType(irTypeKClassToJClassIfNeeded);
                irFieldBuilder.setOrigin(this.originForProp);
                irFieldBuilder.setFinal(true);
                DescriptorVisibility PRIVATE = DescriptorVisibilities.PRIVATE;
                Intrinsics.checkNotNullExpressionValue(PRIVATE, "PRIVATE");
                irFieldBuilder.setVisibility(PRIVATE);
                IrField irFieldBuildField = DeclarationBuildersKt.buildField(irFactory, irFieldBuilder);
                IrClass irClass = implClass2;
                irFieldBuildField.setParent(irClass);
                String strAsString = name2.asString();
                Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
                Iterator it2 = it;
                IrValueParameter irValueParameterAddValueParameter$default = DeclarationBuildersKt.addValueParameter$default(generatedConstructor, strAsString, returnType, (IrDeclarationOrigin) null, 4, (Object) null);
                IrField backingField = irProperty.getBackingField();
                IrExpression expression = (backingField == null || (initializer = backingField.getInitializer()) == null) ? null : initializer.getExpression();
                if (expression instanceof IrGetValue) {
                    IrGetValue irGetValue = (IrGetValue) expression;
                    if (irGetValue.getSymbol().getOwner() instanceof IrValueParameter) {
                        IrValueDeclaration owner = irGetValue.getSymbol().getOwner();
                        Intrinsics.checkNotNull(owner, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrValueParameter");
                        defaultValue = ((IrValueParameter) owner).getDefaultValue();
                    } else if (expression != null) {
                        IrField backingField2 = irProperty.getBackingField();
                        Intrinsics.checkNotNull(backingField2);
                        defaultValue = backingField2.getInitializer();
                    } else if (linkedHashMapEmptyMap.containsKey(name2) || (irValueParameter = (IrValueParameter) linkedHashMapEmptyMap.get(name2)) == null || (defaultValue = irValueParameter.getDefaultValue()) == null || (defaultValue.getExpression() instanceof IrErrorExpression)) {
                        defaultValue = null;
                    }
                } else if (expression != null) {
                    IrField backingField3 = irProperty.getBackingField();
                    Intrinsics.checkNotNull(backingField3);
                    defaultValue = backingField3.getInitializer();
                } else if (linkedHashMapEmptyMap.containsKey(name2)) {
                    defaultValue = null;
                } else {
                    defaultValue = null;
                }
                if (defaultValue == null || (irExpressionBody = (IrExpressionBody) DeepCopyIrTreeWithDeclarationsKt.deepCopyWithVariables(defaultValue)) == null) {
                    irExpressionBody = null;
                } else if (defaultValueTransformer != null) {
                    IrElementTransformerVoidKt.transformChildrenVoid(irExpressionBody, defaultValueTransformer);
                }
                irValueParameterAddValueParameter$default.setDefaultValue(irExpressionBody);
                List<IrStatement> statements = irBlockBodyCreateBlockBody.getStatements();
                DeclarationIrBuilder declarationIrBuilder = declarationIrBuilderCreateIrBuilder;
                IrGetValueImpl irGetValueImplIrGet = ExpressionHelpersKt.irGet(declarationIrBuilder, irValueParameterAddValueParameter$default);
                if (IrTypePredicatesKt.isKClass(returnType)) {
                    irGetValueImplKClassArrayToJClassArray = kClassToJClass(declarationIrBuilder, irGetValueImplIrGet);
                } else {
                    irGetValueImplKClassArrayToJClassArray = isKClassArray(returnType) ? kClassArrayToJClassArray(declarationIrBuilder, irGetValueImplIrGet) : irGetValueImplIrGet;
                }
                IrExpression irExpression = irGetValueImplKClassArrayToJClassArray;
                IrValueParameter thisReceiver = implClass.getThisReceiver();
                Intrinsics.checkNotNull(thisReceiver);
                statements.add(ExpressionHelpersKt.irSetField$default(declarationIrBuilder, ExpressionHelpersKt.irGet(declarationIrBuilder, thisReceiver), irFieldBuildField, irExpression, null, 8, null));
                IrFactory factory = implClass.getFactory();
                IrPropertyBuilder irPropertyBuilder = new IrPropertyBuilder();
                irPropertyBuilder.setStartOffset(-2);
                irPropertyBuilder.setEndOffset(-2);
                irPropertyBuilder.setName(name2);
                irPropertyBuilder.setVar(false);
                irPropertyBuilder.setOrigin(this.originForProp);
                IrProperty irPropertyBuildProperty = DeclarationBuildersKt.buildProperty(factory, irPropertyBuilder);
                implClass.getDeclarations().add(irPropertyBuildProperty);
                irPropertyBuildProperty.setParent(irClass);
                irFieldBuildField.setCorrespondingPropertySymbol(irPropertyBuildProperty.getSymbol());
                irPropertyBuildProperty.setBackingField(irFieldBuildField);
                irPropertyBuildProperty.setParent(irClass);
                irPropertyBuildProperty.setOverriddenSymbols(CollectionsKt.listOf(irProperty.getSymbol()));
                IrFunctionBuilder irFunctionBuilder = new IrFunctionBuilder();
                Name nameSpecial = Name.special("<get-" + irPropertyBuildProperty.getName() + '>');
                Intrinsics.checkNotNullExpressionValue(nameSpecial, "special(...)");
                irFunctionBuilder.setName(nameSpecial);
                irFunctionBuilder.setStartOffset(-2);
                irFunctionBuilder.setEndOffset(-2);
                irFunctionBuilder.setName(name2);
                irFunctionBuilder.setReturnType(kClassToJClassIfNeeded(returnType));
                irFunctionBuilder.setOrigin(this.originForProp);
                DescriptorVisibility PUBLIC = DescriptorVisibilities.PUBLIC;
                Intrinsics.checkNotNullExpressionValue(PUBLIC, "PUBLIC");
                irFunctionBuilder.setVisibility(PUBLIC);
                irFunctionBuilder.setModality(Modality.FINAL);
                IrSimpleFunction irSimpleFunctionBuildFunction = DeclarationBuildersKt.buildFunction(irPropertyBuildProperty.getFactory(), irFunctionBuilder);
                irPropertyBuildProperty.setGetter(irSimpleFunctionBuildFunction);
                irSimpleFunctionBuildFunction.setCorrespondingPropertySymbol(irPropertyBuildProperty.getSymbol());
                irSimpleFunctionBuildFunction.setParent(irPropertyBuildProperty.getParent());
                irSimpleFunctionBuildFunction.setCorrespondingPropertySymbol(irPropertyBuildProperty.getSymbol());
                IrValueParameter thisReceiver2 = implClass.getThisReceiver();
                Intrinsics.checkNotNull(thisReceiver2);
                irSimpleFunctionBuildFunction.setDispatchReceiverParameter(IrUtilsKt.copyTo$default(thisReceiver2, irSimpleFunctionBuildFunction, null, 0, 0, 0, null, null, null, null, null, false, false, false, 8190, null));
                DeclarationIrBuilder declarationIrBuilderCreateIrBuilder2 = LowerUtilsKt.createIrBuilder(this.irBuiltIns, irSimpleFunctionBuildFunction.getSymbol(), -2, -2);
                IrBlockBodyBuilder irBlockBodyBuilder = new IrBlockBodyBuilder(declarationIrBuilderCreateIrBuilder2.getContext(), declarationIrBuilderCreateIrBuilder2.getScope(), declarationIrBuilderCreateIrBuilder2.getStartOffset(), declarationIrBuilderCreateIrBuilder2.getEndOffset());
                IrBlockBodyBuilder irBlockBodyBuilder2 = irBlockBodyBuilder;
                IrValueParameter dispatchReceiverParameter = irSimpleFunctionBuildFunction.getDispatchReceiverParameter();
                Intrinsics.checkNotNull(dispatchReceiverParameter);
                irBlockBodyBuilder.unaryPlus(ExpressionHelpersKt.irReturn(irBlockBodyBuilder2, ExpressionHelpersKt.irGetField$default(irBlockBodyBuilder2, ExpressionHelpersKt.irGet(irBlockBodyBuilder2, dispatchReceiverParameter), irFieldBuildField, null, 4, null)));
                irSimpleFunctionBuildFunction.setBody(irBlockBodyBuilder.getIrBlockBody());
                IrSimpleFunction getter2 = irProperty.getGetter();
                Intrinsics.checkNotNull(getter2);
                irSimpleFunctionBuildFunction.setOverriddenSymbols(CollectionsKt.listOf(getter2.getSymbol()));
                it = it2;
                implClass2 = implClass;
                i = -2;
            }
        }
    }
}
