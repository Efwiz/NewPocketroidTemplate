package org.jetbrains.kotlin.backend.common.lower;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.declarations.IrAnonymousInitializer;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationBase;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrEnumEntry;
import org.jetbrains.kotlin.ir.declarations.IrErrorDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrExternalPackageFragment;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrLocalDelegatedProperty;
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment;
import org.jetbrains.kotlin.ir.declarations.IrPackageFragment;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrScript;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrTypeAlias;
import org.jetbrains.kotlin.ir.declarations.IrTypeParameter;
import org.jetbrains.kotlin.ir.declarations.IrTypeParametersContainer;
import org.jetbrains.kotlin.ir.declarations.IrValueDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.declarations.IrVariable;
import org.jetbrains.kotlin.ir.expressions.IrBlock;
import org.jetbrains.kotlin.ir.expressions.IrBlockBody;
import org.jetbrains.kotlin.ir.expressions.IrBody;
import org.jetbrains.kotlin.ir.expressions.IrBranch;
import org.jetbrains.kotlin.ir.expressions.IrBreak;
import org.jetbrains.kotlin.ir.expressions.IrBreakContinue;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrCallableReference;
import org.jetbrains.kotlin.ir.expressions.IrCatch;
import org.jetbrains.kotlin.ir.expressions.IrClassReference;
import org.jetbrains.kotlin.ir.expressions.IrComposite;
import org.jetbrains.kotlin.ir.expressions.IrConst;
import org.jetbrains.kotlin.ir.expressions.IrConstantArray;
import org.jetbrains.kotlin.ir.expressions.IrConstantObject;
import org.jetbrains.kotlin.ir.expressions.IrConstantPrimitive;
import org.jetbrains.kotlin.ir.expressions.IrConstantValue;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrContainerExpression;
import org.jetbrains.kotlin.ir.expressions.IrContinue;
import org.jetbrains.kotlin.ir.expressions.IrDeclarationReference;
import org.jetbrains.kotlin.ir.expressions.IrDelegatingConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrDoWhileLoop;
import org.jetbrains.kotlin.ir.expressions.IrDynamicExpression;
import org.jetbrains.kotlin.ir.expressions.IrDynamicMemberExpression;
import org.jetbrains.kotlin.ir.expressions.IrDynamicOperatorExpression;
import org.jetbrains.kotlin.ir.expressions.IrElseBranch;
import org.jetbrains.kotlin.ir.expressions.IrEnumConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrErrorCallExpression;
import org.jetbrains.kotlin.ir.expressions.IrErrorExpression;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrExpressionBody;
import org.jetbrains.kotlin.ir.expressions.IrFieldAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionReference;
import org.jetbrains.kotlin.ir.expressions.IrGetClass;
import org.jetbrains.kotlin.ir.expressions.IrGetEnumValue;
import org.jetbrains.kotlin.ir.expressions.IrGetField;
import org.jetbrains.kotlin.ir.expressions.IrGetObjectValue;
import org.jetbrains.kotlin.ir.expressions.IrGetSingletonValue;
import org.jetbrains.kotlin.ir.expressions.IrGetValue;
import org.jetbrains.kotlin.ir.expressions.IrInstanceInitializerCall;
import org.jetbrains.kotlin.ir.expressions.IrLocalDelegatedPropertyReference;
import org.jetbrains.kotlin.ir.expressions.IrLoop;
import org.jetbrains.kotlin.ir.expressions.IrMemberAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrPropertyReference;
import org.jetbrains.kotlin.ir.expressions.IrRawFunctionReference;
import org.jetbrains.kotlin.ir.expressions.IrReturn;
import org.jetbrains.kotlin.ir.expressions.IrSetField;
import org.jetbrains.kotlin.ir.expressions.IrSetValue;
import org.jetbrains.kotlin.ir.expressions.IrSpreadElement;
import org.jetbrains.kotlin.ir.expressions.IrStringConcatenation;
import org.jetbrains.kotlin.ir.expressions.IrSuspendableExpression;
import org.jetbrains.kotlin.ir.expressions.IrSuspensionPoint;
import org.jetbrains.kotlin.ir.expressions.IrSyntheticBody;
import org.jetbrains.kotlin.ir.expressions.IrThrow;
import org.jetbrains.kotlin.ir.expressions.IrTry;
import org.jetbrains.kotlin.ir.expressions.IrTypeOperatorCall;
import org.jetbrains.kotlin.ir.expressions.IrValueAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrVararg;
import org.jetbrains.kotlin.ir.expressions.IrWhen;
import org.jetbrains.kotlin.ir.expressions.IrWhileLoop;
import org.jetbrains.kotlin.ir.symbols.IrClassifierSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrTypeParameterSymbol;
import org.jetbrains.kotlin.ir.symbols.IrValueSymbol;
import org.jetbrains.kotlin.ir.types.IrSimpleType;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypeAbbreviation;
import org.jetbrains.kotlin.ir.types.IrTypeArgument;
import org.jetbrains.kotlin.ir.types.IrTypeProjection;
import org.jetbrains.kotlin.ir.util.AdditionalIrUtilsKt;
import org.jetbrains.kotlin.ir.util.DumpIrTreeOptions;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.util.RenderIrElementKt;
import org.jetbrains.kotlin.ir.visitors.IrElementVisitor;

/* JADX INFO: compiled from: ClosureAnnotator.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001:\u0002\u001d\u001eB\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0010\u0010\u0015\u001a\u00020\u00162\u0006\u0010\n\u001a\u00020\tH\u0002J\u000e\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0004\u001a\u00020\u000bJ\u0010\u0010\u0019\u001a\u00020\u00182\u0006\u0010\u0004\u001a\u00020\u0005H\u0002J\u000e\u0010\u001a\u001a\u00020\u00182\u0006\u0010\u0004\u001a\u00020\u000eJ\u0010\u0010\u001b\u001a\u00020\u00162\u0006\u0010\u001c\u001a\u00020\tH\u0002R\u001a\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0018\u0010\n\u001a\u00020\t*\u00020\u000b8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\rR\u0018\u0010\n\u001a\u00020\t*\u00020\u000e8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\u000fR\u001a\u0010\u0010\u001a\u0004\u0018\u00010\t*\u00020\u00058BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012R\u001a\u0010\u0013\u001a\u0004\u0018\u00010\t*\u00020\u00058BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0012¨\u0006\u001f"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/ClosureAnnotator;", "", "irElement", "Lorg/jetbrains/kotlin/ir/IrElement;", "declaration", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "(Lorg/jetbrains/kotlin/ir/IrElement;Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;)V", "closureBuilders", "", "Lorg/jetbrains/kotlin/backend/common/lower/ClosureAnnotator$ClosureBuilder;", "closureBuilder", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "getClosureBuilder", "(Lorg/jetbrains/kotlin/ir/declarations/IrClass;)Lorg/jetbrains/kotlin/backend/common/lower/ClosureAnnotator$ClosureBuilder;", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "(Lorg/jetbrains/kotlin/ir/declarations/IrFunction;)Lorg/jetbrains/kotlin/backend/common/lower/ClosureAnnotator$ClosureBuilder;", "closureBuilderOrNull", "getClosureBuilderOrNull", "(Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;)Lorg/jetbrains/kotlin/backend/common/lower/ClosureAnnotator$ClosureBuilder;", "parentClosureBuilder", "getParentClosureBuilder", "collectPotentiallyCapturedTypeParameters", "", "getClassClosure", "Lorg/jetbrains/kotlin/backend/common/lower/Closure;", "getClosure", "getFunctionClosure", "includeInParent", "builder", "ClosureBuilder", "ClosureCollectorVisitor", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ClosureAnnotator {
    private final Map<IrDeclaration, ClosureBuilder> closureBuilders;

    public ClosureAnnotator(IrElement irElement, IrDeclaration declaration) {
        Intrinsics.checkNotNullParameter(irElement, "irElement");
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        this.closureBuilders = new LinkedHashMap();
        ClosureCollectorVisitor closureCollectorVisitor = new ClosureCollectorVisitor();
        ClosureBuilder closureBuilderOrNull = getClosureBuilderOrNull(declaration);
        irElement.accept(closureCollectorVisitor, closureBuilderOrNull == null ? getParentClosureBuilder(declaration) : closureBuilderOrNull);
    }

    public final Closure getFunctionClosure(IrFunction declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        return getClosure(declaration);
    }

    public final Closure getClassClosure(IrClass declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        return getClosure(declaration);
    }

    private final Closure getClosure(IrDeclaration declaration) {
        ClosureBuilder closureBuilder = this.closureBuilders.get(declaration);
        if (closureBuilder != null) {
            return closureBuilder.buildClosure();
        }
        throw new AssertionError("No closure builder for passed declaration " + IrUtilsKt.ir2string(declaration) + '.');
    }

    /* JADX INFO: compiled from: ClosureAnnotator.kt */
    @Metadata(d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010#\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u000e\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0007J\u0006\u0010\u0015\u001a\u00020\u000bJ\u000e\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00000\u0017H\u0002J\u0010\u0010\u0018\u001a\u00020\u00132\b\u0010\u0019\u001a\u0004\u0018\u00010\rJ\u000e\u0010\u001a\u001a\u00020\u00132\u0006\u0010\u001b\u001a\u00020\u0000J\u000e\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u0007J\u000e\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u0019\u001a\u00020\rJ\u000e\u0010\u001f\u001a\u00020\u00132\u0006\u0010 \u001a\u00020!J\u000e\u0010\"\u001a\u00020\u00132\u0006\u0010#\u001a\u00020\tJ\b\u0010$\u001a\u00020\u001dH\u0002R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\f\u001a\b\u0012\u0004\u0012\u00020\r0\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00000\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0014\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006%"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/ClosureAnnotator$ClosureBuilder;", "", "owner", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "(Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;)V", "capturedTypeParameters", "", "Lorg/jetbrains/kotlin/ir/declarations/IrTypeParameter;", "capturedValues", "Lorg/jetbrains/kotlin/ir/symbols/IrValueSymbol;", "closure", "Lorg/jetbrains/kotlin/backend/common/lower/Closure;", "declaredValues", "Lorg/jetbrains/kotlin/ir/declarations/IrValueDeclaration;", "includes", "getOwner", "()Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "potentiallyCapturedTypeParameters", "addPotentiallyCapturedTypeParameter", "", "param", "buildClosure", "collectConnectedClosures", "", "declareVariable", "valueDeclaration", "include", "includingBuilder", "isExternal", "", "typeParameter", "seeType", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/ir/types/IrType;", "seeVariable", "value", "updateFromIncluded", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class ClosureBuilder {
        private final Set<IrTypeParameter> capturedTypeParameters;
        private final Set<IrValueSymbol> capturedValues;
        private Closure closure;
        private final Set<IrValueDeclaration> declaredValues;
        private final Set<ClosureBuilder> includes;
        private final IrDeclaration owner;
        private final Set<IrTypeParameter> potentiallyCapturedTypeParameters;

        public ClosureBuilder(IrDeclaration owner) {
            Intrinsics.checkNotNullParameter(owner, "owner");
            this.owner = owner;
            this.capturedValues = new LinkedHashSet();
            this.declaredValues = new LinkedHashSet();
            this.includes = new LinkedHashSet();
            this.potentiallyCapturedTypeParameters = new LinkedHashSet();
            this.capturedTypeParameters = new LinkedHashSet();
        }

        public final IrDeclaration getOwner() {
            return this.owner;
        }

        public final Closure buildClosure() {
            boolean z;
            Closure closure = this.closure;
            if (closure != null) {
                return closure;
            }
            List<ClosureBuilder> listCollectConnectedClosures = collectConnectedClosures();
            do {
                Iterator<ClosureBuilder> it = listCollectConnectedClosures.iterator();
                z = false;
                while (it.hasNext()) {
                    if (it.next().updateFromIncluded()) {
                        z = true;
                    }
                }
            } while (z);
            for (ClosureBuilder closureBuilder : listCollectConnectedClosures) {
                closureBuilder.closure = new Closure(CollectionsKt.toList(closureBuilder.capturedValues), CollectionsKt.toList(closureBuilder.capturedTypeParameters));
            }
            Closure closure2 = this.closure;
            if (closure2 != null) {
                return closure2;
            }
            throw new AssertionError("Closure should have been built for " + RenderIrElementKt.render$default(this.owner, (DumpIrTreeOptions) null, 1, (Object) null));
        }

        private final List<ClosureBuilder> collectConnectedClosures() {
            LinkedHashSet linkedHashSet = new LinkedHashSet();
            linkedHashSet.add(this);
            collectConnectedClosures$collectRec(linkedHashSet, this);
            return CollectionsKt.asReversed(CollectionsKt.toList(linkedHashSet));
        }

        private static final void collectConnectedClosures$collectRec(LinkedHashSet<ClosureBuilder> linkedHashSet, ClosureBuilder closureBuilder) {
            for (ClosureBuilder closureBuilder2 : closureBuilder.includes) {
                if (closureBuilder2.closure == null && linkedHashSet.add(closureBuilder2)) {
                    collectConnectedClosures$collectRec(linkedHashSet, closureBuilder2);
                }
            }
        }

        private final boolean updateFromIncluded() {
            if (this.closure != null) {
                throw new AssertionError("Closure has already been built for " + RenderIrElementKt.render$default(this.owner, (DumpIrTreeOptions) null, 1, (Object) null));
            }
            int size = this.capturedValues.size();
            int size2 = this.capturedTypeParameters.size();
            for (ClosureBuilder closureBuilder : this.includes) {
                Set<IrValueSymbol> set = closureBuilder.capturedValues;
                Collection collection = this.capturedValues;
                for (Object obj : set) {
                    if (isExternal(((IrValueSymbol) obj).getOwner())) {
                        collection.add(obj);
                    }
                }
                Set<IrTypeParameter> set2 = closureBuilder.capturedTypeParameters;
                Collection collection2 = this.capturedTypeParameters;
                for (Object obj2 : set2) {
                    if (isExternal((IrTypeParameter) obj2)) {
                        collection2.add(obj2);
                    }
                }
            }
            return (this.capturedValues.size() == size && this.capturedTypeParameters.size() == size2) ? false : true;
        }

        public final void include(ClosureBuilder includingBuilder) {
            Intrinsics.checkNotNullParameter(includingBuilder, "includingBuilder");
            this.includes.add(includingBuilder);
        }

        public final void declareVariable(IrValueDeclaration valueDeclaration) {
            if (valueDeclaration != null) {
                this.declaredValues.add(valueDeclaration);
                seeType(valueDeclaration.getType());
            }
        }

        public final void seeVariable(IrValueSymbol value) {
            Intrinsics.checkNotNullParameter(value, "value");
            if (isExternal(value.getOwner())) {
                this.capturedValues.add(value);
            }
        }

        public final boolean isExternal(IrValueDeclaration valueDeclaration) {
            Intrinsics.checkNotNullParameter(valueDeclaration, "valueDeclaration");
            return !this.declaredValues.contains(valueDeclaration);
        }

        public final boolean isExternal(IrTypeParameter typeParameter) {
            Intrinsics.checkNotNullParameter(typeParameter, "typeParameter");
            return this.potentiallyCapturedTypeParameters.contains(typeParameter);
        }

        public final void addPotentiallyCapturedTypeParameter(IrTypeParameter param) {
            Intrinsics.checkNotNullParameter(param, "param");
            this.potentiallyCapturedTypeParameters.add(param);
        }

        public final void seeType(IrType type) {
            List<IrTypeArgument> arguments;
            IrType type2;
            IrType type3;
            Intrinsics.checkNotNullParameter(type, "type");
            if (type instanceof IrSimpleType) {
                IrSimpleType irSimpleType = (IrSimpleType) type;
                IrClassifierSymbol classifier = irSimpleType.getClassifier();
                if (classifier instanceof IrTypeParameterSymbol) {
                    IrTypeParameterSymbol irTypeParameterSymbol = (IrTypeParameterSymbol) classifier;
                    if (isExternal(irTypeParameterSymbol.getOwner()) && this.capturedTypeParameters.add(irTypeParameterSymbol.getOwner())) {
                        Iterator<T> it = irTypeParameterSymbol.getOwner().getSuperTypes().iterator();
                        while (it.hasNext()) {
                            seeType((IrType) it.next());
                        }
                    }
                }
                Iterator<T> it2 = irSimpleType.getArguments().iterator();
                while (true) {
                    if (!it2.hasNext()) {
                        break;
                    }
                    IrTypeArgument irTypeArgument = (IrTypeArgument) it2.next();
                    IrTypeProjection irTypeProjection = irTypeArgument instanceof IrTypeProjection ? (IrTypeProjection) irTypeArgument : null;
                    if (irTypeProjection != null && (type3 = irTypeProjection.getType()) != null) {
                        seeType(type3);
                    }
                }
                IrTypeAbbreviation abbreviation = irSimpleType.getAbbreviation();
                if (abbreviation == null || (arguments = abbreviation.getArguments()) == null) {
                    return;
                }
                for (IrTypeArgument irTypeArgument2 : arguments) {
                    IrTypeProjection irTypeProjection2 = irTypeArgument2 instanceof IrTypeProjection ? (IrTypeProjection) irTypeArgument2 : null;
                    if (irTypeProjection2 != null && (type2 = irTypeProjection2.getType()) != null) {
                        seeType(type2);
                    }
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void includeInParent(ClosureBuilder builder) {
        ClosureBuilder parentClosureBuilder = getParentClosureBuilder(builder.getOwner());
        if (parentClosureBuilder == null || (parentClosureBuilder.getOwner() instanceof IrFunction)) {
            return;
        }
        parentClosureBuilder.include(builder);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final ClosureBuilder getClosureBuilder(IrClass irClass) {
        Object next;
        IrValueDeclaration thisReceiver;
        Map<IrDeclaration, ClosureBuilder> map = this.closureBuilders;
        ClosureBuilder closureBuilder = map.get(irClass);
        if (closureBuilder == null) {
            closureBuilder = new ClosureBuilder(irClass);
            collectPotentiallyCapturedTypeParameters(closureBuilder);
            closureBuilder.declareVariable(irClass.getThisReceiver());
            if (irClass.getIsInner()) {
                IrDeclarationParent parent = irClass.getParent();
                if (parent instanceof IrClass) {
                    thisReceiver = ((IrClass) parent).getThisReceiver();
                } else {
                    if (!(parent instanceof IrScript)) {
                        throw new IllegalStateException(("unexpected parent " + parent).toString());
                    }
                    thisReceiver = ((IrScript) parent).getThisReceiver();
                }
                closureBuilder.declareVariable(thisReceiver);
                includeInParent(closureBuilder);
            }
            Iterator<T> it = irClass.getDeclarations().iterator();
            while (true) {
                if (!it.hasNext()) {
                    next = null;
                    break;
                }
                next = it.next();
                IrDeclaration irDeclaration = (IrDeclaration) next;
                if ((irDeclaration instanceof IrConstructor) && ((IrConstructor) irDeclaration).getIsPrimary()) {
                    break;
                }
            }
            IrDeclaration irDeclaration2 = (IrDeclaration) next;
            if (irDeclaration2 != null) {
                Intrinsics.checkNotNull(irDeclaration2, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrConstructor");
                Iterator<T> it2 = ((IrConstructor) irDeclaration2).getValueParameters().iterator();
                while (it2.hasNext()) {
                    closureBuilder.declareVariable((IrValueParameter) it2.next());
                }
            }
            map.put(irClass, closureBuilder);
        }
        return closureBuilder;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final ClosureBuilder getClosureBuilder(IrFunction irFunction) {
        Map<IrDeclaration, ClosureBuilder> map = this.closureBuilders;
        ClosureBuilder closureBuilder = map.get(irFunction);
        if (closureBuilder == null) {
            closureBuilder = new ClosureBuilder(irFunction);
            collectPotentiallyCapturedTypeParameters(closureBuilder);
            Iterator<T> it = irFunction.getValueParameters().iterator();
            while (it.hasNext()) {
                closureBuilder.declareVariable((IrValueParameter) it.next());
            }
            closureBuilder.declareVariable(irFunction.getDispatchReceiverParameter());
            closureBuilder.declareVariable(irFunction.getExtensionReceiverParameter());
            closureBuilder.seeType(irFunction.getReturnType());
            if (irFunction instanceof IrConstructor) {
                IrDeclarationParent parent = irFunction.getParent();
                Intrinsics.checkNotNull(parent, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrClass");
                IrClass irClass = (IrClass) parent;
                closureBuilder.declareVariable(irClass.getThisReceiver());
                closureBuilder.include(getClosureBuilder(irClass));
            }
            map.put(irFunction, closureBuilder);
        }
        return closureBuilder;
    }

    private final void collectPotentiallyCapturedTypeParameters(ClosureBuilder closureBuilder) {
        ClosureBuilder parentClosureBuilder = getParentClosureBuilder(closureBuilder.getOwner());
        while (parentClosureBuilder != null) {
            IrDeclaration owner = parentClosureBuilder.getOwner();
            if (owner instanceof IrTypeParametersContainer) {
                Iterator<IrTypeParameter> it = ((IrTypeParametersContainer) owner).getTypeParameters().iterator();
                while (it.hasNext()) {
                    closureBuilder.addPotentiallyCapturedTypeParameter(it.next());
                }
            }
            parentClosureBuilder = getParentClosureBuilder(owner);
        }
    }

    private final ClosureBuilder getParentClosureBuilder(IrDeclaration irDeclaration) {
        IrDeclarationParent parent = irDeclaration.getParent();
        if (parent instanceof IrClass) {
            return getClosureBuilder((IrClass) parent);
        }
        if (parent instanceof IrFunction) {
            return getClosureBuilder((IrFunction) parent);
        }
        if (parent instanceof IrDeclaration) {
            return getParentClosureBuilder((IrDeclaration) parent);
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final ClosureBuilder getClosureBuilderOrNull(IrDeclaration irDeclaration) {
        if (irDeclaration instanceof IrClass) {
            return getClosureBuilder((IrClass) irDeclaration);
        }
        if (irDeclaration instanceof IrFunction) {
            return getClosureBuilder((IrFunction) irDeclaration);
        }
        return null;
    }

    /* JADX INFO: compiled from: ClosureAnnotator.kt */
    @Metadata(d1 = {"\u0000d\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0082\u0004\u0018\u00002\u0010\u0012\u0004\u0012\u00020\u0002\u0012\u0006\u0012\u0004\u0018\u00010\u00030\u0001B\u0005¢\u0006\u0002\u0010\u0004J\u001a\u0010\u0005\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u00072\b\u0010\b\u001a\u0004\u0018\u00010\u0003H\u0002J$\u0010\t\u001a\u00020\u00022\b\u0010\n\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\u0006\u001a\u00020\u00072\b\u0010\f\u001a\u0004\u0018\u00010\u0003H\u0002J\u001a\u0010\r\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u000e2\b\u0010\f\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010\u000f\u001a\u00020\u00022\u0006\u0010\u0010\u001a\u00020\u00112\b\u0010\f\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010\u0012\u001a\u00020\u00022\u0006\u0010\u0013\u001a\u00020\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010\u0014\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u00152\b\u0010\f\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010\u0016\u001a\u00020\u00022\u0006\u0010\u0013\u001a\u00020\u00172\b\u0010\f\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010\u0018\u001a\u00020\u00022\u0006\u0010\u0013\u001a\u00020\u00192\b\u0010\f\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010\u001a\u001a\u00020\u00022\u0006\u0010\u0013\u001a\u00020\u001b2\b\u0010\f\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010\u001c\u001a\u00020\u00022\u0006\u0010\u0013\u001a\u00020\u001d2\b\u0010\f\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010\u001e\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u001f2\b\u0010\f\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010 \u001a\u00020\u00022\u0006\u0010\u0013\u001a\u00020!2\b\u0010\f\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010\"\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020#2\b\u0010\f\u001a\u0004\u0018\u00010\u0003H\u0016¨\u0006$"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/ClosureAnnotator$ClosureCollectorVisitor;", "Lorg/jetbrains/kotlin/ir/visitors/IrElementVisitor;", "", "Lorg/jetbrains/kotlin/backend/common/lower/ClosureAnnotator$ClosureBuilder;", "(Lorg/jetbrains/kotlin/backend/common/lower/ClosureAnnotator;)V", "processMemberAccess", "declaration", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "parentClosure", "processScriptCapturing", "receiverExpression", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "data", "visitClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "visitElement", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/ir/IrElement;", "visitExpression", "expression", "visitFunction", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "visitFunctionAccess", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionAccessExpression;", "visitFunctionExpression", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionExpression;", "visitFunctionReference", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionReference;", "visitPropertyReference", "Lorg/jetbrains/kotlin/ir/expressions/IrPropertyReference;", "visitTypeParameter", "Lorg/jetbrains/kotlin/ir/declarations/IrTypeParameter;", "visitValueAccess", "Lorg/jetbrains/kotlin/ir/expressions/IrValueAccessExpression;", "visitVariable", "Lorg/jetbrains/kotlin/ir/declarations/IrVariable;", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private final class ClosureCollectorVisitor implements IrElementVisitor<Unit, ClosureBuilder> {
        public ClosureCollectorVisitor() {
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitAnonymousInitializer */
        public /* bridge */ /* synthetic */ Unit visitAnonymousInitializer2(IrAnonymousInitializer irAnonymousInitializer, ClosureBuilder closureBuilder) {
            visitAnonymousInitializer2(irAnonymousInitializer, closureBuilder);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitAnonymousInitializer, reason: avoid collision after fix types in other method */
        public void visitAnonymousInitializer2(IrAnonymousInitializer irAnonymousInitializer, ClosureBuilder closureBuilder) {
            IrElementVisitor.DefaultImpls.visitAnonymousInitializer(this, irAnonymousInitializer, closureBuilder);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitBlock */
        public /* bridge */ /* synthetic */ Unit visitBlock2(IrBlock irBlock, ClosureBuilder closureBuilder) {
            visitBlock2(irBlock, closureBuilder);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitBlock, reason: avoid collision after fix types in other method */
        public void visitBlock2(IrBlock irBlock, ClosureBuilder closureBuilder) {
            IrElementVisitor.DefaultImpls.visitBlock(this, irBlock, closureBuilder);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitBlockBody */
        public /* bridge */ /* synthetic */ Unit visitBlockBody2(IrBlockBody irBlockBody, ClosureBuilder closureBuilder) {
            visitBlockBody2(irBlockBody, closureBuilder);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitBlockBody, reason: avoid collision after fix types in other method */
        public void visitBlockBody2(IrBlockBody irBlockBody, ClosureBuilder closureBuilder) {
            IrElementVisitor.DefaultImpls.visitBlockBody(this, irBlockBody, closureBuilder);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitBody */
        public /* bridge */ /* synthetic */ Unit visitBody2(IrBody irBody, ClosureBuilder closureBuilder) {
            visitBody2(irBody, closureBuilder);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitBody, reason: avoid collision after fix types in other method */
        public void visitBody2(IrBody irBody, ClosureBuilder closureBuilder) {
            IrElementVisitor.DefaultImpls.visitBody(this, irBody, closureBuilder);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitBranch */
        public /* bridge */ /* synthetic */ Unit visitBranch2(IrBranch irBranch, ClosureBuilder closureBuilder) {
            visitBranch2(irBranch, closureBuilder);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitBranch, reason: avoid collision after fix types in other method */
        public void visitBranch2(IrBranch irBranch, ClosureBuilder closureBuilder) {
            IrElementVisitor.DefaultImpls.visitBranch(this, irBranch, closureBuilder);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitBreak */
        public /* bridge */ /* synthetic */ Unit visitBreak2(IrBreak irBreak, ClosureBuilder closureBuilder) {
            visitBreak2(irBreak, closureBuilder);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitBreak, reason: avoid collision after fix types in other method */
        public void visitBreak2(IrBreak irBreak, ClosureBuilder closureBuilder) {
            IrElementVisitor.DefaultImpls.visitBreak(this, irBreak, closureBuilder);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitBreakContinue */
        public /* bridge */ /* synthetic */ Unit visitBreakContinue2(IrBreakContinue irBreakContinue, ClosureBuilder closureBuilder) {
            visitBreakContinue2(irBreakContinue, closureBuilder);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitBreakContinue, reason: avoid collision after fix types in other method */
        public void visitBreakContinue2(IrBreakContinue irBreakContinue, ClosureBuilder closureBuilder) {
            IrElementVisitor.DefaultImpls.visitBreakContinue(this, irBreakContinue, closureBuilder);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitCall */
        public /* bridge */ /* synthetic */ Unit visitCall2(IrCall irCall, ClosureBuilder closureBuilder) {
            visitCall2(irCall, closureBuilder);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitCall, reason: avoid collision after fix types in other method */
        public void visitCall2(IrCall irCall, ClosureBuilder closureBuilder) {
            IrElementVisitor.DefaultImpls.visitCall(this, irCall, closureBuilder);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public /* bridge */ /* synthetic */ Unit visitCallableReference(IrCallableReference irCallableReference, ClosureBuilder closureBuilder) {
            visitCallableReference2((IrCallableReference<?>) irCallableReference, closureBuilder);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitCallableReference, reason: avoid collision after fix types in other method */
        public void visitCallableReference2(IrCallableReference<?> irCallableReference, ClosureBuilder closureBuilder) {
            IrElementVisitor.DefaultImpls.visitCallableReference(this, irCallableReference, closureBuilder);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitCatch */
        public /* bridge */ /* synthetic */ Unit visitCatch2(IrCatch irCatch, ClosureBuilder closureBuilder) {
            visitCatch2(irCatch, closureBuilder);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitCatch, reason: avoid collision after fix types in other method */
        public void visitCatch2(IrCatch irCatch, ClosureBuilder closureBuilder) {
            IrElementVisitor.DefaultImpls.visitCatch(this, irCatch, closureBuilder);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitClass */
        public /* bridge */ /* synthetic */ Unit visitClass2(IrClass irClass, ClosureBuilder closureBuilder) {
            visitClass2(irClass, closureBuilder);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitClassReference */
        public /* bridge */ /* synthetic */ Unit visitClassReference2(IrClassReference irClassReference, ClosureBuilder closureBuilder) {
            visitClassReference2(irClassReference, closureBuilder);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitClassReference, reason: avoid collision after fix types in other method */
        public void visitClassReference2(IrClassReference irClassReference, ClosureBuilder closureBuilder) {
            IrElementVisitor.DefaultImpls.visitClassReference(this, irClassReference, closureBuilder);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitComposite */
        public /* bridge */ /* synthetic */ Unit visitComposite2(IrComposite irComposite, ClosureBuilder closureBuilder) {
            visitComposite2(irComposite, closureBuilder);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitComposite, reason: avoid collision after fix types in other method */
        public void visitComposite2(IrComposite irComposite, ClosureBuilder closureBuilder) {
            IrElementVisitor.DefaultImpls.visitComposite(this, irComposite, closureBuilder);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public /* bridge */ /* synthetic */ Unit visitConst(IrConst irConst, ClosureBuilder closureBuilder) {
            visitConst2((IrConst<?>) irConst, closureBuilder);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitConst, reason: avoid collision after fix types in other method */
        public void visitConst2(IrConst<?> irConst, ClosureBuilder closureBuilder) {
            IrElementVisitor.DefaultImpls.visitConst(this, irConst, closureBuilder);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitConstantArray */
        public /* bridge */ /* synthetic */ Unit visitConstantArray2(IrConstantArray irConstantArray, ClosureBuilder closureBuilder) {
            visitConstantArray2(irConstantArray, closureBuilder);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitConstantArray, reason: avoid collision after fix types in other method */
        public void visitConstantArray2(IrConstantArray irConstantArray, ClosureBuilder closureBuilder) {
            IrElementVisitor.DefaultImpls.visitConstantArray(this, irConstantArray, closureBuilder);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitConstantObject */
        public /* bridge */ /* synthetic */ Unit visitConstantObject2(IrConstantObject irConstantObject, ClosureBuilder closureBuilder) {
            visitConstantObject2(irConstantObject, closureBuilder);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitConstantObject, reason: avoid collision after fix types in other method */
        public void visitConstantObject2(IrConstantObject irConstantObject, ClosureBuilder closureBuilder) {
            IrElementVisitor.DefaultImpls.visitConstantObject(this, irConstantObject, closureBuilder);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitConstantPrimitive */
        public /* bridge */ /* synthetic */ Unit visitConstantPrimitive2(IrConstantPrimitive irConstantPrimitive, ClosureBuilder closureBuilder) {
            visitConstantPrimitive2(irConstantPrimitive, closureBuilder);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitConstantPrimitive, reason: avoid collision after fix types in other method */
        public void visitConstantPrimitive2(IrConstantPrimitive irConstantPrimitive, ClosureBuilder closureBuilder) {
            IrElementVisitor.DefaultImpls.visitConstantPrimitive(this, irConstantPrimitive, closureBuilder);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitConstantValue */
        public /* bridge */ /* synthetic */ Unit visitConstantValue2(IrConstantValue irConstantValue, ClosureBuilder closureBuilder) {
            visitConstantValue2(irConstantValue, closureBuilder);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitConstantValue, reason: avoid collision after fix types in other method */
        public void visitConstantValue2(IrConstantValue irConstantValue, ClosureBuilder closureBuilder) {
            IrElementVisitor.DefaultImpls.visitConstantValue(this, irConstantValue, closureBuilder);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitConstructor */
        public /* bridge */ /* synthetic */ Unit visitConstructor2(IrConstructor irConstructor, ClosureBuilder closureBuilder) {
            visitConstructor2(irConstructor, closureBuilder);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitConstructor, reason: avoid collision after fix types in other method */
        public void visitConstructor2(IrConstructor irConstructor, ClosureBuilder closureBuilder) {
            IrElementVisitor.DefaultImpls.visitConstructor(this, irConstructor, closureBuilder);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitConstructorCall */
        public /* bridge */ /* synthetic */ Unit visitConstructorCall2(IrConstructorCall irConstructorCall, ClosureBuilder closureBuilder) {
            visitConstructorCall2(irConstructorCall, closureBuilder);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitConstructorCall, reason: avoid collision after fix types in other method */
        public void visitConstructorCall2(IrConstructorCall irConstructorCall, ClosureBuilder closureBuilder) {
            IrElementVisitor.DefaultImpls.visitConstructorCall(this, irConstructorCall, closureBuilder);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitContainerExpression */
        public /* bridge */ /* synthetic */ Unit visitContainerExpression2(IrContainerExpression irContainerExpression, ClosureBuilder closureBuilder) {
            visitContainerExpression2(irContainerExpression, closureBuilder);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitContainerExpression, reason: avoid collision after fix types in other method */
        public void visitContainerExpression2(IrContainerExpression irContainerExpression, ClosureBuilder closureBuilder) {
            IrElementVisitor.DefaultImpls.visitContainerExpression(this, irContainerExpression, closureBuilder);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitContinue */
        public /* bridge */ /* synthetic */ Unit visitContinue2(IrContinue irContinue, ClosureBuilder closureBuilder) {
            visitContinue2(irContinue, closureBuilder);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitContinue, reason: avoid collision after fix types in other method */
        public void visitContinue2(IrContinue irContinue, ClosureBuilder closureBuilder) {
            IrElementVisitor.DefaultImpls.visitContinue(this, irContinue, closureBuilder);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitDeclaration */
        public /* bridge */ /* synthetic */ Unit visitDeclaration2(IrDeclarationBase irDeclarationBase, ClosureBuilder closureBuilder) {
            visitDeclaration2(irDeclarationBase, closureBuilder);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitDeclaration, reason: avoid collision after fix types in other method */
        public void visitDeclaration2(IrDeclarationBase irDeclarationBase, ClosureBuilder closureBuilder) {
            IrElementVisitor.DefaultImpls.visitDeclaration(this, irDeclarationBase, closureBuilder);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitDeclarationReference */
        public /* bridge */ /* synthetic */ Unit visitDeclarationReference2(IrDeclarationReference irDeclarationReference, ClosureBuilder closureBuilder) {
            visitDeclarationReference2(irDeclarationReference, closureBuilder);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitDeclarationReference, reason: avoid collision after fix types in other method */
        public void visitDeclarationReference2(IrDeclarationReference irDeclarationReference, ClosureBuilder closureBuilder) {
            IrElementVisitor.DefaultImpls.visitDeclarationReference(this, irDeclarationReference, closureBuilder);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitDelegatingConstructorCall */
        public /* bridge */ /* synthetic */ Unit visitDelegatingConstructorCall2(IrDelegatingConstructorCall irDelegatingConstructorCall, ClosureBuilder closureBuilder) {
            visitDelegatingConstructorCall2(irDelegatingConstructorCall, closureBuilder);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitDelegatingConstructorCall, reason: avoid collision after fix types in other method */
        public void visitDelegatingConstructorCall2(IrDelegatingConstructorCall irDelegatingConstructorCall, ClosureBuilder closureBuilder) {
            IrElementVisitor.DefaultImpls.visitDelegatingConstructorCall(this, irDelegatingConstructorCall, closureBuilder);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitDoWhileLoop */
        public /* bridge */ /* synthetic */ Unit visitDoWhileLoop2(IrDoWhileLoop irDoWhileLoop, ClosureBuilder closureBuilder) {
            visitDoWhileLoop2(irDoWhileLoop, closureBuilder);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitDoWhileLoop, reason: avoid collision after fix types in other method */
        public void visitDoWhileLoop2(IrDoWhileLoop irDoWhileLoop, ClosureBuilder closureBuilder) {
            IrElementVisitor.DefaultImpls.visitDoWhileLoop(this, irDoWhileLoop, closureBuilder);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitDynamicExpression */
        public /* bridge */ /* synthetic */ Unit visitDynamicExpression2(IrDynamicExpression irDynamicExpression, ClosureBuilder closureBuilder) {
            visitDynamicExpression2(irDynamicExpression, closureBuilder);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitDynamicExpression, reason: avoid collision after fix types in other method */
        public void visitDynamicExpression2(IrDynamicExpression irDynamicExpression, ClosureBuilder closureBuilder) {
            IrElementVisitor.DefaultImpls.visitDynamicExpression(this, irDynamicExpression, closureBuilder);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitDynamicMemberExpression */
        public /* bridge */ /* synthetic */ Unit visitDynamicMemberExpression2(IrDynamicMemberExpression irDynamicMemberExpression, ClosureBuilder closureBuilder) {
            visitDynamicMemberExpression2(irDynamicMemberExpression, closureBuilder);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitDynamicMemberExpression, reason: avoid collision after fix types in other method */
        public void visitDynamicMemberExpression2(IrDynamicMemberExpression irDynamicMemberExpression, ClosureBuilder closureBuilder) {
            IrElementVisitor.DefaultImpls.visitDynamicMemberExpression(this, irDynamicMemberExpression, closureBuilder);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitDynamicOperatorExpression */
        public /* bridge */ /* synthetic */ Unit visitDynamicOperatorExpression2(IrDynamicOperatorExpression irDynamicOperatorExpression, ClosureBuilder closureBuilder) {
            visitDynamicOperatorExpression2(irDynamicOperatorExpression, closureBuilder);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitDynamicOperatorExpression, reason: avoid collision after fix types in other method */
        public void visitDynamicOperatorExpression2(IrDynamicOperatorExpression irDynamicOperatorExpression, ClosureBuilder closureBuilder) {
            IrElementVisitor.DefaultImpls.visitDynamicOperatorExpression(this, irDynamicOperatorExpression, closureBuilder);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitElement */
        public /* bridge */ /* synthetic */ Unit visitElement2(IrElement irElement, ClosureBuilder closureBuilder) {
            visitElement2(irElement, closureBuilder);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitElseBranch */
        public /* bridge */ /* synthetic */ Unit visitElseBranch2(IrElseBranch irElseBranch, ClosureBuilder closureBuilder) {
            visitElseBranch2(irElseBranch, closureBuilder);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitElseBranch, reason: avoid collision after fix types in other method */
        public void visitElseBranch2(IrElseBranch irElseBranch, ClosureBuilder closureBuilder) {
            IrElementVisitor.DefaultImpls.visitElseBranch(this, irElseBranch, closureBuilder);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitEnumConstructorCall */
        public /* bridge */ /* synthetic */ Unit visitEnumConstructorCall2(IrEnumConstructorCall irEnumConstructorCall, ClosureBuilder closureBuilder) {
            visitEnumConstructorCall2(irEnumConstructorCall, closureBuilder);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitEnumConstructorCall, reason: avoid collision after fix types in other method */
        public void visitEnumConstructorCall2(IrEnumConstructorCall irEnumConstructorCall, ClosureBuilder closureBuilder) {
            IrElementVisitor.DefaultImpls.visitEnumConstructorCall(this, irEnumConstructorCall, closureBuilder);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitEnumEntry */
        public /* bridge */ /* synthetic */ Unit visitEnumEntry2(IrEnumEntry irEnumEntry, ClosureBuilder closureBuilder) {
            visitEnumEntry2(irEnumEntry, closureBuilder);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitEnumEntry, reason: avoid collision after fix types in other method */
        public void visitEnumEntry2(IrEnumEntry irEnumEntry, ClosureBuilder closureBuilder) {
            IrElementVisitor.DefaultImpls.visitEnumEntry(this, irEnumEntry, closureBuilder);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitErrorCallExpression */
        public /* bridge */ /* synthetic */ Unit visitErrorCallExpression2(IrErrorCallExpression irErrorCallExpression, ClosureBuilder closureBuilder) {
            visitErrorCallExpression2(irErrorCallExpression, closureBuilder);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitErrorCallExpression, reason: avoid collision after fix types in other method */
        public void visitErrorCallExpression2(IrErrorCallExpression irErrorCallExpression, ClosureBuilder closureBuilder) {
            IrElementVisitor.DefaultImpls.visitErrorCallExpression(this, irErrorCallExpression, closureBuilder);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitErrorDeclaration */
        public /* bridge */ /* synthetic */ Unit visitErrorDeclaration2(IrErrorDeclaration irErrorDeclaration, ClosureBuilder closureBuilder) {
            visitErrorDeclaration2(irErrorDeclaration, closureBuilder);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitErrorDeclaration, reason: avoid collision after fix types in other method */
        public void visitErrorDeclaration2(IrErrorDeclaration irErrorDeclaration, ClosureBuilder closureBuilder) {
            IrElementVisitor.DefaultImpls.visitErrorDeclaration(this, irErrorDeclaration, closureBuilder);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitErrorExpression */
        public /* bridge */ /* synthetic */ Unit visitErrorExpression2(IrErrorExpression irErrorExpression, ClosureBuilder closureBuilder) {
            visitErrorExpression2(irErrorExpression, closureBuilder);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitErrorExpression, reason: avoid collision after fix types in other method */
        public void visitErrorExpression2(IrErrorExpression irErrorExpression, ClosureBuilder closureBuilder) {
            IrElementVisitor.DefaultImpls.visitErrorExpression(this, irErrorExpression, closureBuilder);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitExpression */
        public /* bridge */ /* synthetic */ Unit visitExpression2(IrExpression irExpression, ClosureBuilder closureBuilder) {
            visitExpression2(irExpression, closureBuilder);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitExpressionBody */
        public /* bridge */ /* synthetic */ Unit visitExpressionBody2(IrExpressionBody irExpressionBody, ClosureBuilder closureBuilder) {
            visitExpressionBody2(irExpressionBody, closureBuilder);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitExpressionBody, reason: avoid collision after fix types in other method */
        public void visitExpressionBody2(IrExpressionBody irExpressionBody, ClosureBuilder closureBuilder) {
            IrElementVisitor.DefaultImpls.visitExpressionBody(this, irExpressionBody, closureBuilder);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitExternalPackageFragment */
        public /* bridge */ /* synthetic */ Unit visitExternalPackageFragment2(IrExternalPackageFragment irExternalPackageFragment, ClosureBuilder closureBuilder) {
            visitExternalPackageFragment2(irExternalPackageFragment, closureBuilder);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitExternalPackageFragment, reason: avoid collision after fix types in other method */
        public void visitExternalPackageFragment2(IrExternalPackageFragment irExternalPackageFragment, ClosureBuilder closureBuilder) {
            IrElementVisitor.DefaultImpls.visitExternalPackageFragment(this, irExternalPackageFragment, closureBuilder);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitField */
        public /* bridge */ /* synthetic */ Unit visitField2(IrField irField, ClosureBuilder closureBuilder) {
            visitField2(irField, closureBuilder);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitField, reason: avoid collision after fix types in other method */
        public void visitField2(IrField irField, ClosureBuilder closureBuilder) {
            IrElementVisitor.DefaultImpls.visitField(this, irField, closureBuilder);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitFieldAccess */
        public /* bridge */ /* synthetic */ Unit visitFieldAccess2(IrFieldAccessExpression irFieldAccessExpression, ClosureBuilder closureBuilder) {
            visitFieldAccess2(irFieldAccessExpression, closureBuilder);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitFieldAccess, reason: avoid collision after fix types in other method */
        public void visitFieldAccess2(IrFieldAccessExpression irFieldAccessExpression, ClosureBuilder closureBuilder) {
            IrElementVisitor.DefaultImpls.visitFieldAccess(this, irFieldAccessExpression, closureBuilder);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitFile */
        public /* bridge */ /* synthetic */ Unit visitFile2(IrFile irFile, ClosureBuilder closureBuilder) {
            visitFile2(irFile, closureBuilder);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitFile, reason: avoid collision after fix types in other method */
        public void visitFile2(IrFile irFile, ClosureBuilder closureBuilder) {
            IrElementVisitor.DefaultImpls.visitFile(this, irFile, closureBuilder);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitFunction */
        public /* bridge */ /* synthetic */ Unit visitFunction2(IrFunction irFunction, ClosureBuilder closureBuilder) {
            visitFunction2(irFunction, closureBuilder);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitFunctionAccess */
        public /* bridge */ /* synthetic */ Unit visitFunctionAccess2(IrFunctionAccessExpression irFunctionAccessExpression, ClosureBuilder closureBuilder) {
            visitFunctionAccess2(irFunctionAccessExpression, closureBuilder);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitFunctionExpression */
        public /* bridge */ /* synthetic */ Unit visitFunctionExpression2(IrFunctionExpression irFunctionExpression, ClosureBuilder closureBuilder) {
            visitFunctionExpression2(irFunctionExpression, closureBuilder);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitFunctionReference */
        public /* bridge */ /* synthetic */ Unit visitFunctionReference2(IrFunctionReference irFunctionReference, ClosureBuilder closureBuilder) {
            visitFunctionReference2(irFunctionReference, closureBuilder);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitGetClass */
        public /* bridge */ /* synthetic */ Unit visitGetClass2(IrGetClass irGetClass, ClosureBuilder closureBuilder) {
            visitGetClass2(irGetClass, closureBuilder);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitGetClass, reason: avoid collision after fix types in other method */
        public void visitGetClass2(IrGetClass irGetClass, ClosureBuilder closureBuilder) {
            IrElementVisitor.DefaultImpls.visitGetClass(this, irGetClass, closureBuilder);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitGetEnumValue */
        public /* bridge */ /* synthetic */ Unit visitGetEnumValue2(IrGetEnumValue irGetEnumValue, ClosureBuilder closureBuilder) {
            visitGetEnumValue2(irGetEnumValue, closureBuilder);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitGetEnumValue, reason: avoid collision after fix types in other method */
        public void visitGetEnumValue2(IrGetEnumValue irGetEnumValue, ClosureBuilder closureBuilder) {
            IrElementVisitor.DefaultImpls.visitGetEnumValue(this, irGetEnumValue, closureBuilder);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitGetField */
        public /* bridge */ /* synthetic */ Unit visitGetField2(IrGetField irGetField, ClosureBuilder closureBuilder) {
            visitGetField2(irGetField, closureBuilder);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitGetField, reason: avoid collision after fix types in other method */
        public void visitGetField2(IrGetField irGetField, ClosureBuilder closureBuilder) {
            IrElementVisitor.DefaultImpls.visitGetField(this, irGetField, closureBuilder);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitGetObjectValue */
        public /* bridge */ /* synthetic */ Unit visitGetObjectValue2(IrGetObjectValue irGetObjectValue, ClosureBuilder closureBuilder) {
            visitGetObjectValue2(irGetObjectValue, closureBuilder);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitGetObjectValue, reason: avoid collision after fix types in other method */
        public void visitGetObjectValue2(IrGetObjectValue irGetObjectValue, ClosureBuilder closureBuilder) {
            IrElementVisitor.DefaultImpls.visitGetObjectValue(this, irGetObjectValue, closureBuilder);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitGetValue */
        public /* bridge */ /* synthetic */ Unit visitGetValue2(IrGetValue irGetValue, ClosureBuilder closureBuilder) {
            visitGetValue2(irGetValue, closureBuilder);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitGetValue, reason: avoid collision after fix types in other method */
        public void visitGetValue2(IrGetValue irGetValue, ClosureBuilder closureBuilder) {
            IrElementVisitor.DefaultImpls.visitGetValue(this, irGetValue, closureBuilder);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitInstanceInitializerCall */
        public /* bridge */ /* synthetic */ Unit visitInstanceInitializerCall2(IrInstanceInitializerCall irInstanceInitializerCall, ClosureBuilder closureBuilder) {
            visitInstanceInitializerCall2(irInstanceInitializerCall, closureBuilder);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitInstanceInitializerCall, reason: avoid collision after fix types in other method */
        public void visitInstanceInitializerCall2(IrInstanceInitializerCall irInstanceInitializerCall, ClosureBuilder closureBuilder) {
            IrElementVisitor.DefaultImpls.visitInstanceInitializerCall(this, irInstanceInitializerCall, closureBuilder);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitLocalDelegatedProperty */
        public /* bridge */ /* synthetic */ Unit visitLocalDelegatedProperty2(IrLocalDelegatedProperty irLocalDelegatedProperty, ClosureBuilder closureBuilder) {
            visitLocalDelegatedProperty2(irLocalDelegatedProperty, closureBuilder);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitLocalDelegatedProperty, reason: avoid collision after fix types in other method */
        public void visitLocalDelegatedProperty2(IrLocalDelegatedProperty irLocalDelegatedProperty, ClosureBuilder closureBuilder) {
            IrElementVisitor.DefaultImpls.visitLocalDelegatedProperty(this, irLocalDelegatedProperty, closureBuilder);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitLocalDelegatedPropertyReference */
        public /* bridge */ /* synthetic */ Unit visitLocalDelegatedPropertyReference2(IrLocalDelegatedPropertyReference irLocalDelegatedPropertyReference, ClosureBuilder closureBuilder) {
            visitLocalDelegatedPropertyReference2(irLocalDelegatedPropertyReference, closureBuilder);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitLocalDelegatedPropertyReference, reason: avoid collision after fix types in other method */
        public void visitLocalDelegatedPropertyReference2(IrLocalDelegatedPropertyReference irLocalDelegatedPropertyReference, ClosureBuilder closureBuilder) {
            IrElementVisitor.DefaultImpls.visitLocalDelegatedPropertyReference(this, irLocalDelegatedPropertyReference, closureBuilder);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitLoop */
        public /* bridge */ /* synthetic */ Unit visitLoop2(IrLoop irLoop, ClosureBuilder closureBuilder) {
            visitLoop2(irLoop, closureBuilder);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitLoop, reason: avoid collision after fix types in other method */
        public void visitLoop2(IrLoop irLoop, ClosureBuilder closureBuilder) {
            IrElementVisitor.DefaultImpls.visitLoop(this, irLoop, closureBuilder);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public /* bridge */ /* synthetic */ Unit visitMemberAccess(IrMemberAccessExpression irMemberAccessExpression, ClosureBuilder closureBuilder) {
            visitMemberAccess2((IrMemberAccessExpression<?>) irMemberAccessExpression, closureBuilder);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitMemberAccess, reason: avoid collision after fix types in other method */
        public void visitMemberAccess2(IrMemberAccessExpression<?> irMemberAccessExpression, ClosureBuilder closureBuilder) {
            IrElementVisitor.DefaultImpls.visitMemberAccess(this, irMemberAccessExpression, closureBuilder);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitModuleFragment */
        public /* bridge */ /* synthetic */ Unit visitModuleFragment2(IrModuleFragment irModuleFragment, ClosureBuilder closureBuilder) {
            visitModuleFragment2(irModuleFragment, closureBuilder);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitModuleFragment, reason: avoid collision after fix types in other method */
        public void visitModuleFragment2(IrModuleFragment irModuleFragment, ClosureBuilder closureBuilder) {
            IrElementVisitor.DefaultImpls.visitModuleFragment(this, irModuleFragment, closureBuilder);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitPackageFragment */
        public /* bridge */ /* synthetic */ Unit visitPackageFragment2(IrPackageFragment irPackageFragment, ClosureBuilder closureBuilder) {
            visitPackageFragment2(irPackageFragment, closureBuilder);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitPackageFragment, reason: avoid collision after fix types in other method */
        public void visitPackageFragment2(IrPackageFragment irPackageFragment, ClosureBuilder closureBuilder) {
            IrElementVisitor.DefaultImpls.visitPackageFragment(this, irPackageFragment, closureBuilder);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitProperty */
        public /* bridge */ /* synthetic */ Unit visitProperty2(IrProperty irProperty, ClosureBuilder closureBuilder) {
            visitProperty2(irProperty, closureBuilder);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitProperty, reason: avoid collision after fix types in other method */
        public void visitProperty2(IrProperty irProperty, ClosureBuilder closureBuilder) {
            IrElementVisitor.DefaultImpls.visitProperty(this, irProperty, closureBuilder);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitPropertyReference */
        public /* bridge */ /* synthetic */ Unit visitPropertyReference2(IrPropertyReference irPropertyReference, ClosureBuilder closureBuilder) {
            visitPropertyReference2(irPropertyReference, closureBuilder);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitRawFunctionReference */
        public /* bridge */ /* synthetic */ Unit visitRawFunctionReference2(IrRawFunctionReference irRawFunctionReference, ClosureBuilder closureBuilder) {
            visitRawFunctionReference2(irRawFunctionReference, closureBuilder);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitRawFunctionReference, reason: avoid collision after fix types in other method */
        public void visitRawFunctionReference2(IrRawFunctionReference irRawFunctionReference, ClosureBuilder closureBuilder) {
            IrElementVisitor.DefaultImpls.visitRawFunctionReference(this, irRawFunctionReference, closureBuilder);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitReturn */
        public /* bridge */ /* synthetic */ Unit visitReturn2(IrReturn irReturn, ClosureBuilder closureBuilder) {
            visitReturn2(irReturn, closureBuilder);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitReturn, reason: avoid collision after fix types in other method */
        public void visitReturn2(IrReturn irReturn, ClosureBuilder closureBuilder) {
            IrElementVisitor.DefaultImpls.visitReturn(this, irReturn, closureBuilder);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitScript */
        public /* bridge */ /* synthetic */ Unit visitScript2(IrScript irScript, ClosureBuilder closureBuilder) {
            visitScript2(irScript, closureBuilder);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitScript, reason: avoid collision after fix types in other method */
        public void visitScript2(IrScript irScript, ClosureBuilder closureBuilder) {
            IrElementVisitor.DefaultImpls.visitScript(this, irScript, closureBuilder);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitSetField */
        public /* bridge */ /* synthetic */ Unit visitSetField2(IrSetField irSetField, ClosureBuilder closureBuilder) {
            visitSetField2(irSetField, closureBuilder);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitSetField, reason: avoid collision after fix types in other method */
        public void visitSetField2(IrSetField irSetField, ClosureBuilder closureBuilder) {
            IrElementVisitor.DefaultImpls.visitSetField(this, irSetField, closureBuilder);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitSetValue */
        public /* bridge */ /* synthetic */ Unit visitSetValue2(IrSetValue irSetValue, ClosureBuilder closureBuilder) {
            visitSetValue2(irSetValue, closureBuilder);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitSetValue, reason: avoid collision after fix types in other method */
        public void visitSetValue2(IrSetValue irSetValue, ClosureBuilder closureBuilder) {
            IrElementVisitor.DefaultImpls.visitSetValue(this, irSetValue, closureBuilder);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitSimpleFunction */
        public /* bridge */ /* synthetic */ Unit visitSimpleFunction2(IrSimpleFunction irSimpleFunction, ClosureBuilder closureBuilder) {
            visitSimpleFunction2(irSimpleFunction, closureBuilder);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitSimpleFunction, reason: avoid collision after fix types in other method */
        public void visitSimpleFunction2(IrSimpleFunction irSimpleFunction, ClosureBuilder closureBuilder) {
            IrElementVisitor.DefaultImpls.visitSimpleFunction(this, irSimpleFunction, closureBuilder);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitSingletonReference */
        public /* bridge */ /* synthetic */ Unit visitSingletonReference2(IrGetSingletonValue irGetSingletonValue, ClosureBuilder closureBuilder) {
            visitSingletonReference2(irGetSingletonValue, closureBuilder);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitSingletonReference, reason: avoid collision after fix types in other method */
        public void visitSingletonReference2(IrGetSingletonValue irGetSingletonValue, ClosureBuilder closureBuilder) {
            IrElementVisitor.DefaultImpls.visitSingletonReference(this, irGetSingletonValue, closureBuilder);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitSpreadElement */
        public /* bridge */ /* synthetic */ Unit visitSpreadElement2(IrSpreadElement irSpreadElement, ClosureBuilder closureBuilder) {
            visitSpreadElement2(irSpreadElement, closureBuilder);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitSpreadElement, reason: avoid collision after fix types in other method */
        public void visitSpreadElement2(IrSpreadElement irSpreadElement, ClosureBuilder closureBuilder) {
            IrElementVisitor.DefaultImpls.visitSpreadElement(this, irSpreadElement, closureBuilder);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitStringConcatenation */
        public /* bridge */ /* synthetic */ Unit visitStringConcatenation2(IrStringConcatenation irStringConcatenation, ClosureBuilder closureBuilder) {
            visitStringConcatenation2(irStringConcatenation, closureBuilder);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitStringConcatenation, reason: avoid collision after fix types in other method */
        public void visitStringConcatenation2(IrStringConcatenation irStringConcatenation, ClosureBuilder closureBuilder) {
            IrElementVisitor.DefaultImpls.visitStringConcatenation(this, irStringConcatenation, closureBuilder);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitSuspendableExpression */
        public /* bridge */ /* synthetic */ Unit visitSuspendableExpression2(IrSuspendableExpression irSuspendableExpression, ClosureBuilder closureBuilder) {
            visitSuspendableExpression2(irSuspendableExpression, closureBuilder);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitSuspendableExpression, reason: avoid collision after fix types in other method */
        public void visitSuspendableExpression2(IrSuspendableExpression irSuspendableExpression, ClosureBuilder closureBuilder) {
            IrElementVisitor.DefaultImpls.visitSuspendableExpression(this, irSuspendableExpression, closureBuilder);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitSuspensionPoint */
        public /* bridge */ /* synthetic */ Unit visitSuspensionPoint2(IrSuspensionPoint irSuspensionPoint, ClosureBuilder closureBuilder) {
            visitSuspensionPoint2(irSuspensionPoint, closureBuilder);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitSuspensionPoint, reason: avoid collision after fix types in other method */
        public void visitSuspensionPoint2(IrSuspensionPoint irSuspensionPoint, ClosureBuilder closureBuilder) {
            IrElementVisitor.DefaultImpls.visitSuspensionPoint(this, irSuspensionPoint, closureBuilder);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitSyntheticBody */
        public /* bridge */ /* synthetic */ Unit visitSyntheticBody2(IrSyntheticBody irSyntheticBody, ClosureBuilder closureBuilder) {
            visitSyntheticBody2(irSyntheticBody, closureBuilder);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitSyntheticBody, reason: avoid collision after fix types in other method */
        public void visitSyntheticBody2(IrSyntheticBody irSyntheticBody, ClosureBuilder closureBuilder) {
            IrElementVisitor.DefaultImpls.visitSyntheticBody(this, irSyntheticBody, closureBuilder);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitThrow */
        public /* bridge */ /* synthetic */ Unit visitThrow2(IrThrow irThrow, ClosureBuilder closureBuilder) {
            visitThrow2(irThrow, closureBuilder);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitThrow, reason: avoid collision after fix types in other method */
        public void visitThrow2(IrThrow irThrow, ClosureBuilder closureBuilder) {
            IrElementVisitor.DefaultImpls.visitThrow(this, irThrow, closureBuilder);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitTry */
        public /* bridge */ /* synthetic */ Unit visitTry2(IrTry irTry, ClosureBuilder closureBuilder) {
            visitTry2(irTry, closureBuilder);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitTry, reason: avoid collision after fix types in other method */
        public void visitTry2(IrTry irTry, ClosureBuilder closureBuilder) {
            IrElementVisitor.DefaultImpls.visitTry(this, irTry, closureBuilder);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitTypeAlias */
        public /* bridge */ /* synthetic */ Unit visitTypeAlias2(IrTypeAlias irTypeAlias, ClosureBuilder closureBuilder) {
            visitTypeAlias2(irTypeAlias, closureBuilder);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitTypeAlias, reason: avoid collision after fix types in other method */
        public void visitTypeAlias2(IrTypeAlias irTypeAlias, ClosureBuilder closureBuilder) {
            IrElementVisitor.DefaultImpls.visitTypeAlias(this, irTypeAlias, closureBuilder);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitTypeOperator */
        public /* bridge */ /* synthetic */ Unit visitTypeOperator2(IrTypeOperatorCall irTypeOperatorCall, ClosureBuilder closureBuilder) {
            visitTypeOperator2(irTypeOperatorCall, closureBuilder);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitTypeOperator, reason: avoid collision after fix types in other method */
        public void visitTypeOperator2(IrTypeOperatorCall irTypeOperatorCall, ClosureBuilder closureBuilder) {
            IrElementVisitor.DefaultImpls.visitTypeOperator(this, irTypeOperatorCall, closureBuilder);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitTypeParameter */
        public /* bridge */ /* synthetic */ Unit visitTypeParameter2(IrTypeParameter irTypeParameter, ClosureBuilder closureBuilder) {
            visitTypeParameter2(irTypeParameter, closureBuilder);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitValueAccess */
        public /* bridge */ /* synthetic */ Unit visitValueAccess2(IrValueAccessExpression irValueAccessExpression, ClosureBuilder closureBuilder) {
            visitValueAccess2(irValueAccessExpression, closureBuilder);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitValueParameter */
        public /* bridge */ /* synthetic */ Unit visitValueParameter2(IrValueParameter irValueParameter, ClosureBuilder closureBuilder) {
            visitValueParameter2(irValueParameter, closureBuilder);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitValueParameter, reason: avoid collision after fix types in other method */
        public void visitValueParameter2(IrValueParameter irValueParameter, ClosureBuilder closureBuilder) {
            IrElementVisitor.DefaultImpls.visitValueParameter(this, irValueParameter, closureBuilder);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitVararg */
        public /* bridge */ /* synthetic */ Unit visitVararg2(IrVararg irVararg, ClosureBuilder closureBuilder) {
            visitVararg2(irVararg, closureBuilder);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitVararg, reason: avoid collision after fix types in other method */
        public void visitVararg2(IrVararg irVararg, ClosureBuilder closureBuilder) {
            IrElementVisitor.DefaultImpls.visitVararg(this, irVararg, closureBuilder);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitVariable */
        public /* bridge */ /* synthetic */ Unit visitVariable2(IrVariable irVariable, ClosureBuilder closureBuilder) {
            visitVariable2(irVariable, closureBuilder);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitWhen */
        public /* bridge */ /* synthetic */ Unit visitWhen2(IrWhen irWhen, ClosureBuilder closureBuilder) {
            visitWhen2(irWhen, closureBuilder);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitWhen, reason: avoid collision after fix types in other method */
        public void visitWhen2(IrWhen irWhen, ClosureBuilder closureBuilder) {
            IrElementVisitor.DefaultImpls.visitWhen(this, irWhen, closureBuilder);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitWhileLoop */
        public /* bridge */ /* synthetic */ Unit visitWhileLoop2(IrWhileLoop irWhileLoop, ClosureBuilder closureBuilder) {
            visitWhileLoop2(irWhileLoop, closureBuilder);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitWhileLoop, reason: avoid collision after fix types in other method */
        public void visitWhileLoop2(IrWhileLoop irWhileLoop, ClosureBuilder closureBuilder) {
            IrElementVisitor.DefaultImpls.visitWhileLoop(this, irWhileLoop, closureBuilder);
        }

        /* JADX INFO: renamed from: visitElement, reason: avoid collision after fix types in other method */
        public void visitElement2(IrElement element, ClosureBuilder data) {
            Intrinsics.checkNotNullParameter(element, "element");
            element.acceptChildren(this, data);
        }

        /* JADX INFO: renamed from: visitClass, reason: avoid collision after fix types in other method */
        public void visitClass2(IrClass declaration, ClosureBuilder data) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            declaration.acceptChildren(this, ClosureAnnotator.this.getClosureBuilder(declaration));
        }

        /* JADX INFO: renamed from: visitFunction, reason: avoid collision after fix types in other method */
        public void visitFunction2(IrFunction declaration, ClosureBuilder data) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            ClosureBuilder closureBuilder = ClosureAnnotator.this.getClosureBuilder(declaration);
            declaration.acceptChildren(this, closureBuilder);
            ClosureAnnotator.this.includeInParent(closureBuilder);
        }

        /* JADX INFO: renamed from: visitTypeParameter, reason: avoid collision after fix types in other method */
        public void visitTypeParameter2(IrTypeParameter declaration, ClosureBuilder data) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            for (IrType irType : declaration.getSuperTypes()) {
                if (data != null) {
                    data.seeType(irType);
                }
            }
        }

        /* JADX INFO: renamed from: visitValueAccess, reason: avoid collision after fix types in other method */
        public void visitValueAccess2(IrValueAccessExpression expression, ClosureBuilder data) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            if (data != null) {
                data.seeVariable(expression.getSymbol());
            }
            IrElementVisitor.DefaultImpls.visitValueAccess(this, expression, data);
        }

        /* JADX INFO: renamed from: visitVariable, reason: avoid collision after fix types in other method */
        public void visitVariable2(IrVariable declaration, ClosureBuilder data) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            if (data != null) {
                data.declareVariable(declaration);
            }
            IrElementVisitor.DefaultImpls.visitVariable(this, declaration, data);
        }

        /* JADX INFO: renamed from: visitFunctionAccess, reason: avoid collision after fix types in other method */
        public void visitFunctionAccess2(IrFunctionAccessExpression expression, ClosureBuilder data) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            IrElementVisitor.DefaultImpls.visitFunctionAccess(this, expression, data);
            processScriptCapturing(expression.getDispatchReceiver(), expression.getSymbol().getOwner(), data);
            processMemberAccess(expression.getSymbol().getOwner(), data);
        }

        /* JADX INFO: renamed from: visitFunctionReference, reason: avoid collision after fix types in other method */
        public void visitFunctionReference2(IrFunctionReference expression, ClosureBuilder data) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            IrElementVisitor.DefaultImpls.visitFunctionReference(this, expression, data);
            processMemberAccess(expression.getSymbol().getOwner(), data);
        }

        /* JADX INFO: renamed from: visitFunctionExpression, reason: avoid collision after fix types in other method */
        public void visitFunctionExpression2(IrFunctionExpression expression, ClosureBuilder data) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            IrElementVisitor.DefaultImpls.visitFunctionExpression(this, expression, data);
            processMemberAccess(expression.getFunction(), data);
        }

        /* JADX INFO: renamed from: visitPropertyReference, reason: avoid collision after fix types in other method */
        public void visitPropertyReference2(IrPropertyReference expression, ClosureBuilder data) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            IrElementVisitor.DefaultImpls.visitPropertyReference(this, expression, data);
            IrSimpleFunctionSymbol getter = expression.getGetter();
            if (getter != null) {
                processMemberAccess(getter.getOwner(), data);
            }
            IrSimpleFunctionSymbol setter = expression.getSetter();
            if (setter != null) {
                processMemberAccess(setter.getOwner(), data);
            }
        }

        /* JADX INFO: renamed from: visitExpression, reason: avoid collision after fix types in other method */
        public void visitExpression2(IrExpression expression, ClosureBuilder data) {
            ClosureBuilder closureBuilder;
            Intrinsics.checkNotNullParameter(expression, "expression");
            IrElementVisitor.DefaultImpls.visitExpression(this, expression, data);
            ClosureBuilder closureBuilder2 = null;
            if (data != null) {
                ClosureAnnotator closureAnnotator = ClosureAnnotator.this;
                IrDeclaration owner = data.getOwner();
                IrConstructor irConstructor = owner instanceof IrConstructor ? (IrConstructor) owner : null;
                if (irConstructor != null && (closureBuilder = closureAnnotator.getClosureBuilder(irConstructor)) != null) {
                    data = closureBuilder;
                }
                closureBuilder2 = data;
            }
            if (closureBuilder2 != null) {
                closureBuilder2.seeType(expression.getType());
            }
        }

        private final void processScriptCapturing(IrExpression receiverExpression, IrDeclaration declaration, ClosureBuilder data) {
            if (receiverExpression == null) {
                IrDeclarationParent parent = declaration.getParent();
                if (parent instanceof IrScript) {
                    if (data != null) {
                        IrValueParameter thisReceiver = ((IrScript) parent).getThisReceiver();
                        Intrinsics.checkNotNull(thisReceiver);
                        data.seeVariable(thisReceiver.getSymbol());
                        return;
                    }
                    return;
                }
                if (parent instanceof IrClass) {
                    IrClass irClass = (IrClass) parent;
                    if (!Intrinsics.areEqual(irClass.getOrigin(), IrDeclarationOrigin.SCRIPT_CLASS.INSTANCE) || data == null) {
                        return;
                    }
                    IrValueParameter thisReceiver2 = irClass.getThisReceiver();
                    Intrinsics.checkNotNull(thisReceiver2);
                    data.seeVariable(thisReceiver2.getSymbol());
                }
            }
        }

        private final void processMemberAccess(IrDeclaration declaration, ClosureBuilder parentClosure) {
            ClosureBuilder closureBuilderOrNull;
            if (AdditionalIrUtilsKt.isLocal(declaration)) {
                if (((declaration instanceof IrSimpleFunction) && !Intrinsics.areEqual(((IrSimpleFunction) declaration).getVisibility(), DescriptorVisibilities.LOCAL)) || (closureBuilderOrNull = ClosureAnnotator.this.getClosureBuilderOrNull(declaration)) == null || parentClosure == null) {
                    return;
                }
                parentClosure.include(closureBuilderOrNull);
            }
        }
    }
}
