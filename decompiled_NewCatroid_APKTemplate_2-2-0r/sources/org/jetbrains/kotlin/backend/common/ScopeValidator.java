package org.jetbrains.kotlin.backend.common;

import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import com.reandroid.arsc.value.CompoundEntry;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.declarations.IrAnonymousInitializer;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationBase;
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
import org.jetbrains.kotlin.ir.symbols.IrTypeParameterSymbol;
import org.jetbrains.kotlin.ir.types.IrSimpleType;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypeArgument;
import org.jetbrains.kotlin.ir.types.IrTypeProjection;
import org.jetbrains.kotlin.ir.util.DumpIrTreeOptions;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.util.RenderIrElementKt;
import org.jetbrains.kotlin.ir.visitors.IrElementVisitor;

/* JADX INFO: compiled from: ScopeValidator.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001:\u0002\u000e\u000fBA\u0012:\u0010\u0002\u001a6\u0012\u0013\u0012\u00110\u0004¢\u0006\f\b\u0005\u0012\b\b\u0006\u0012\u0004\b\b(\u0007\u0012\u0013\u0012\u00110\b¢\u0006\f\b\u0005\u0012\b\b\u0006\u0012\u0004\b\b(\t\u0012\u0004\u0012\u00020\n0\u0003j\u0002`\u000b¢\u0006\u0002\u0010\fJ\u000e\u0010\r\u001a\u00020\n2\u0006\u0010\u0007\u001a\u00020\u0004RB\u0010\u0002\u001a6\u0012\u0013\u0012\u00110\u0004¢\u0006\f\b\u0005\u0012\b\b\u0006\u0012\u0004\b\b(\u0007\u0012\u0013\u0012\u00110\b¢\u0006\f\b\u0005\u0012\b\b\u0006\u0012\u0004\b\b(\t\u0012\u0004\u0012\u00020\n0\u0003j\u0002`\u000bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/ScopeValidator;", "", "reportError", "Lkotlin/Function2;", "Lorg/jetbrains/kotlin/ir/IrElement;", "Lkotlin/ParameterName;", "name", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "", CrashHianalyticsData.MESSAGE, "", "Lorg/jetbrains/kotlin/backend/common/ReportError;", "(Lkotlin/jvm/functions/Function2;)V", "check", "Checker", "Visibles", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ScopeValidator {
    private final Function2<IrElement, String, Unit> reportError;

    /* JADX WARN: Multi-variable type inference failed */
    public ScopeValidator(Function2<? super IrElement, ? super String, Unit> reportError) {
        Intrinsics.checkNotNullParameter(reportError, "reportError");
        this.reportError = reportError;
    }

    public final void check(IrElement element) {
        Intrinsics.checkNotNullParameter(element, "element");
        element.accept(new Checker(), new Visibles(this, SetsKt.emptySet(), new LinkedHashSet()));
    }

    /* JADX INFO: compiled from: ScopeValidator.kt */
    @Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010#\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0004\u0018\u00002\u00020\u0001B!\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006¢\u0006\u0002\u0010\bJ&\u0010\f\u001a\u00060\u0000R\u00020\r2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00040\u000f2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00070\u000fJ\u0016\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0016J\u0016\u0010\u0017\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0018\u001a\u00020\u0007R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\n¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/ScopeValidator$Visibles;", "", "typeParameters", "", "Lorg/jetbrains/kotlin/ir/declarations/IrTypeParameter;", CompoundEntry.NAME_values, "", "Lorg/jetbrains/kotlin/ir/declarations/IrValueDeclaration;", "(Lorg/jetbrains/kotlin/backend/common/ScopeValidator;Ljava/util/Set;Ljava/util/Set;)V", "getTypeParameters", "()Ljava/util/Set;", "getValues", "extend", "Lorg/jetbrains/kotlin/backend/common/ScopeValidator;", "newTypeParameters", "", "newValues", "visitTypeAccess", "", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/ir/IrElement;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/ir/types/IrType;", "visitValueAccess", "variable", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public final class Visibles {
        final /* synthetic */ ScopeValidator this$0;
        private final Set<IrTypeParameter> typeParameters;
        private final Set<IrValueDeclaration> values;

        /* JADX WARN: Multi-variable type inference failed */
        public Visibles(ScopeValidator scopeValidator, Set<? extends IrTypeParameter> typeParameters, Set<IrValueDeclaration> values) {
            Intrinsics.checkNotNullParameter(typeParameters, "typeParameters");
            Intrinsics.checkNotNullParameter(values, "values");
            this.this$0 = scopeValidator;
            this.typeParameters = typeParameters;
            this.values = values;
        }

        public final Set<IrTypeParameter> getTypeParameters() {
            return this.typeParameters;
        }

        public final Set<IrValueDeclaration> getValues() {
            return this.values;
        }

        public final void visitTypeAccess(IrElement element, IrType type) {
            Intrinsics.checkNotNullParameter(element, "element");
            Intrinsics.checkNotNullParameter(type, "type");
            if (type instanceof IrSimpleType) {
                IrSimpleType irSimpleType = (IrSimpleType) type;
                if ((irSimpleType.getClassifier() instanceof IrTypeParameterSymbol) && !CollectionsKt.contains(this.typeParameters, irSimpleType.getClassifier().getOwner())) {
                    this.this$0.reportError.invoke(element, "Type parameter " + RenderIrElementKt.render$default(irSimpleType.getClassifier().getOwner(), (DumpIrTreeOptions) null, 1, (Object) null) + " not accessible");
                }
                for (IrTypeArgument irTypeArgument : irSimpleType.getArguments()) {
                    if (irTypeArgument instanceof IrTypeProjection) {
                        visitTypeAccess(element, ((IrTypeProjection) irTypeArgument).getType());
                    }
                }
            }
        }

        public final void visitValueAccess(IrElement element, IrValueDeclaration variable) {
            Intrinsics.checkNotNullParameter(element, "element");
            Intrinsics.checkNotNullParameter(variable, "variable");
            if (this.values.contains(variable)) {
                return;
            }
            this.this$0.reportError.invoke(element, "Value " + RenderIrElementKt.render$default(variable, (DumpIrTreeOptions) null, 1, (Object) null) + " not accessible");
        }

        public final Visibles extend(Collection<? extends IrTypeParameter> newTypeParameters, Collection<? extends IrValueDeclaration> newValues) {
            Intrinsics.checkNotNullParameter(newTypeParameters, "newTypeParameters");
            Intrinsics.checkNotNullParameter(newValues, "newValues");
            return new Visibles(this.this$0, newTypeParameters.isEmpty() ? this.typeParameters : SetsKt.plus((Set) this.typeParameters, (Iterable) newTypeParameters), CollectionsKt.toMutableSet(SetsKt.plus((Set) this.values, (Iterable) newValues)));
        }
    }

    /* JADX INFO: compiled from: ScopeValidator.kt */
    @Metadata(d1 = {"\u0000\u008a\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0004\u0018\u00002\u0012\u0012\u0004\u0012\u00020\u0002\u0012\b\u0012\u00060\u0003R\u00020\u00040\u0001B\u0005¢\u0006\u0002\u0010\u0005J\u001c\u0010\u0006\u001a\u00020\u00022\u0006\u0010\u0007\u001a\u00020\b2\n\u0010\t\u001a\u00060\u0003R\u00020\u0004H\u0016J\u001c\u0010\n\u001a\u00020\u00022\u0006\u0010\u000b\u001a\u00020\f2\n\u0010\t\u001a\u00060\u0003R\u00020\u0004H\u0016J\u001c\u0010\r\u001a\u00020\u00022\u0006\u0010\u000e\u001a\u00020\u000f2\n\u0010\t\u001a\u00060\u0003R\u00020\u0004H\u0016J\u001c\u0010\u0010\u001a\u00020\u00022\u0006\u0010\u0007\u001a\u00020\u00112\n\u0010\t\u001a\u00060\u0003R\u00020\u0004H\u0016J\u001c\u0010\u0012\u001a\u00020\u00022\u0006\u0010\u000b\u001a\u00020\u00132\n\u0010\t\u001a\u00060\u0003R\u00020\u0004H\u0016J\u001c\u0010\u0014\u001a\u00020\u00022\u0006\u0010\u0015\u001a\u00020\u00162\n\u0010\t\u001a\u00060\u0003R\u00020\u0004H\u0016J\u001c\u0010\u0017\u001a\u00020\u00022\u0006\u0010\u000b\u001a\u00020\u00182\n\u0010\t\u001a\u00060\u0003R\u00020\u0004H\u0016J\u001c\u0010\u0019\u001a\u00020\u00022\u0006\u0010\u0007\u001a\u00020\u001a2\n\u0010\t\u001a\u00060\u0003R\u00020\u0004H\u0016J\u001c\u0010\u001b\u001a\u00020\u00022\u0006\u0010\u0007\u001a\u00020\u001c2\n\u0010\t\u001a\u00060\u0003R\u00020\u0004H\u0016J \u0010\u001d\u001a\u00020\u00022\n\u0010\u000b\u001a\u0006\u0012\u0002\b\u00030\u001e2\n\u0010\t\u001a\u00060\u0003R\u00020\u0004H\u0016J\u001c\u0010\u001f\u001a\u00020\u00022\u0006\u0010\u0007\u001a\u00020 2\n\u0010\t\u001a\u00060\u0003R\u00020\u0004H\u0016J\u001c\u0010!\u001a\u00020\u00022\u0006\u0010\u000b\u001a\u00020\"2\n\u0010\t\u001a\u00060\u0003R\u00020\u0004H\u0016J\u001c\u0010#\u001a\u00020\u00022\u0006\u0010\u0007\u001a\u00020$2\n\u0010\t\u001a\u00060\u0003R\u00020\u0004H\u0016J\u001c\u0010%\u001a\u00020\u00022\u0006\u0010\u000b\u001a\u00020&2\n\u0010\t\u001a\u00060\u0003R\u00020\u0004H\u0016J\u001c\u0010'\u001a\u00020\u00022\u0006\u0010\u0007\u001a\u00020(2\n\u0010\t\u001a\u00060\u0003R\u00020\u0004H\u0016J\u001c\u0010)\u001a\u00020\u00022\u0006\u0010\u000b\u001a\u00020*2\n\u0010\t\u001a\u00060\u0003R\u00020\u0004H\u0016J\u001c\u0010+\u001a\u00020\u00022\u0006\u0010\u0007\u001a\u00020,2\n\u0010\t\u001a\u00060\u0003R\u00020\u0004H\u0016J\u000e\u0010-\u001a\u0004\u0018\u00010.*\u00020\u0011H\u0002¨\u0006/"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/ScopeValidator$Checker;", "Lorg/jetbrains/kotlin/ir/visitors/IrElementVisitor;", "", "Lorg/jetbrains/kotlin/backend/common/ScopeValidator$Visibles;", "Lorg/jetbrains/kotlin/backend/common/ScopeValidator;", "(Lorg/jetbrains/kotlin/backend/common/ScopeValidator;)V", "visitAnonymousInitializer", "declaration", "Lorg/jetbrains/kotlin/ir/declarations/IrAnonymousInitializer;", "data", "visitBlock", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrBlock;", "visitCatch", "aCatch", "Lorg/jetbrains/kotlin/ir/expressions/IrCatch;", "visitClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "visitClassReference", "Lorg/jetbrains/kotlin/ir/expressions/IrClassReference;", "visitElement", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/ir/IrElement;", "visitExpression", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "visitField", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "visitFunction", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "visitMemberAccess", "Lorg/jetbrains/kotlin/ir/expressions/IrMemberAccessExpression;", "visitTypeAlias", "Lorg/jetbrains/kotlin/ir/declarations/IrTypeAlias;", "visitTypeOperator", "Lorg/jetbrains/kotlin/ir/expressions/IrTypeOperatorCall;", "visitTypeParameter", "Lorg/jetbrains/kotlin/ir/declarations/IrTypeParameter;", "visitValueAccess", "Lorg/jetbrains/kotlin/ir/expressions/IrValueAccessExpression;", "visitValueParameter", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "visitVararg", "Lorg/jetbrains/kotlin/ir/expressions/IrVararg;", "visitVariable", "Lorg/jetbrains/kotlin/ir/declarations/IrVariable;", "primaryConstructor", "Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public final class Checker implements IrElementVisitor<Unit, Visibles> {
        public Checker() {
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitAnonymousInitializer */
        public /* bridge */ /* synthetic */ Unit visitAnonymousInitializer2(IrAnonymousInitializer irAnonymousInitializer, Visibles visibles) {
            visitAnonymousInitializer2(irAnonymousInitializer, visibles);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitBlock */
        public /* bridge */ /* synthetic */ Unit visitBlock2(IrBlock irBlock, Visibles visibles) {
            visitBlock2(irBlock, visibles);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitBlockBody */
        public /* bridge */ /* synthetic */ Unit visitBlockBody2(IrBlockBody irBlockBody, Visibles visibles) {
            visitBlockBody2(irBlockBody, visibles);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitBlockBody, reason: avoid collision after fix types in other method */
        public void visitBlockBody2(IrBlockBody irBlockBody, Visibles visibles) {
            IrElementVisitor.DefaultImpls.visitBlockBody(this, irBlockBody, visibles);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitBody */
        public /* bridge */ /* synthetic */ Unit visitBody2(IrBody irBody, Visibles visibles) {
            visitBody2(irBody, visibles);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitBody, reason: avoid collision after fix types in other method */
        public void visitBody2(IrBody irBody, Visibles visibles) {
            IrElementVisitor.DefaultImpls.visitBody(this, irBody, visibles);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitBranch */
        public /* bridge */ /* synthetic */ Unit visitBranch2(IrBranch irBranch, Visibles visibles) {
            visitBranch2(irBranch, visibles);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitBranch, reason: avoid collision after fix types in other method */
        public void visitBranch2(IrBranch irBranch, Visibles visibles) {
            IrElementVisitor.DefaultImpls.visitBranch(this, irBranch, visibles);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitBreak */
        public /* bridge */ /* synthetic */ Unit visitBreak2(IrBreak irBreak, Visibles visibles) {
            visitBreak2(irBreak, visibles);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitBreak, reason: avoid collision after fix types in other method */
        public void visitBreak2(IrBreak irBreak, Visibles visibles) {
            IrElementVisitor.DefaultImpls.visitBreak(this, irBreak, visibles);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitBreakContinue */
        public /* bridge */ /* synthetic */ Unit visitBreakContinue2(IrBreakContinue irBreakContinue, Visibles visibles) {
            visitBreakContinue2(irBreakContinue, visibles);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitBreakContinue, reason: avoid collision after fix types in other method */
        public void visitBreakContinue2(IrBreakContinue irBreakContinue, Visibles visibles) {
            IrElementVisitor.DefaultImpls.visitBreakContinue(this, irBreakContinue, visibles);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitCall */
        public /* bridge */ /* synthetic */ Unit visitCall2(IrCall irCall, Visibles visibles) {
            visitCall2(irCall, visibles);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitCall, reason: avoid collision after fix types in other method */
        public void visitCall2(IrCall irCall, Visibles visibles) {
            IrElementVisitor.DefaultImpls.visitCall(this, irCall, visibles);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public /* bridge */ /* synthetic */ Unit visitCallableReference(IrCallableReference irCallableReference, Visibles visibles) {
            visitCallableReference2((IrCallableReference<?>) irCallableReference, visibles);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitCallableReference, reason: avoid collision after fix types in other method */
        public void visitCallableReference2(IrCallableReference<?> irCallableReference, Visibles visibles) {
            IrElementVisitor.DefaultImpls.visitCallableReference(this, irCallableReference, visibles);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitCatch */
        public /* bridge */ /* synthetic */ Unit visitCatch2(IrCatch irCatch, Visibles visibles) {
            visitCatch2(irCatch, visibles);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitClass */
        public /* bridge */ /* synthetic */ Unit visitClass2(IrClass irClass, Visibles visibles) {
            visitClass2(irClass, visibles);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitClassReference */
        public /* bridge */ /* synthetic */ Unit visitClassReference2(IrClassReference irClassReference, Visibles visibles) {
            visitClassReference2(irClassReference, visibles);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitComposite */
        public /* bridge */ /* synthetic */ Unit visitComposite2(IrComposite irComposite, Visibles visibles) {
            visitComposite2(irComposite, visibles);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitComposite, reason: avoid collision after fix types in other method */
        public void visitComposite2(IrComposite irComposite, Visibles visibles) {
            IrElementVisitor.DefaultImpls.visitComposite(this, irComposite, visibles);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public /* bridge */ /* synthetic */ Unit visitConst(IrConst irConst, Visibles visibles) {
            visitConst2((IrConst<?>) irConst, visibles);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitConst, reason: avoid collision after fix types in other method */
        public void visitConst2(IrConst<?> irConst, Visibles visibles) {
            IrElementVisitor.DefaultImpls.visitConst(this, irConst, visibles);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitConstantArray */
        public /* bridge */ /* synthetic */ Unit visitConstantArray2(IrConstantArray irConstantArray, Visibles visibles) {
            visitConstantArray2(irConstantArray, visibles);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitConstantArray, reason: avoid collision after fix types in other method */
        public void visitConstantArray2(IrConstantArray irConstantArray, Visibles visibles) {
            IrElementVisitor.DefaultImpls.visitConstantArray(this, irConstantArray, visibles);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitConstantObject */
        public /* bridge */ /* synthetic */ Unit visitConstantObject2(IrConstantObject irConstantObject, Visibles visibles) {
            visitConstantObject2(irConstantObject, visibles);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitConstantObject, reason: avoid collision after fix types in other method */
        public void visitConstantObject2(IrConstantObject irConstantObject, Visibles visibles) {
            IrElementVisitor.DefaultImpls.visitConstantObject(this, irConstantObject, visibles);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitConstantPrimitive */
        public /* bridge */ /* synthetic */ Unit visitConstantPrimitive2(IrConstantPrimitive irConstantPrimitive, Visibles visibles) {
            visitConstantPrimitive2(irConstantPrimitive, visibles);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitConstantPrimitive, reason: avoid collision after fix types in other method */
        public void visitConstantPrimitive2(IrConstantPrimitive irConstantPrimitive, Visibles visibles) {
            IrElementVisitor.DefaultImpls.visitConstantPrimitive(this, irConstantPrimitive, visibles);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitConstantValue */
        public /* bridge */ /* synthetic */ Unit visitConstantValue2(IrConstantValue irConstantValue, Visibles visibles) {
            visitConstantValue2(irConstantValue, visibles);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitConstantValue, reason: avoid collision after fix types in other method */
        public void visitConstantValue2(IrConstantValue irConstantValue, Visibles visibles) {
            IrElementVisitor.DefaultImpls.visitConstantValue(this, irConstantValue, visibles);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitConstructor */
        public /* bridge */ /* synthetic */ Unit visitConstructor2(IrConstructor irConstructor, Visibles visibles) {
            visitConstructor2(irConstructor, visibles);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitConstructor, reason: avoid collision after fix types in other method */
        public void visitConstructor2(IrConstructor irConstructor, Visibles visibles) {
            IrElementVisitor.DefaultImpls.visitConstructor(this, irConstructor, visibles);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitConstructorCall */
        public /* bridge */ /* synthetic */ Unit visitConstructorCall2(IrConstructorCall irConstructorCall, Visibles visibles) {
            visitConstructorCall2(irConstructorCall, visibles);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitConstructorCall, reason: avoid collision after fix types in other method */
        public void visitConstructorCall2(IrConstructorCall irConstructorCall, Visibles visibles) {
            IrElementVisitor.DefaultImpls.visitConstructorCall(this, irConstructorCall, visibles);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitContainerExpression */
        public /* bridge */ /* synthetic */ Unit visitContainerExpression2(IrContainerExpression irContainerExpression, Visibles visibles) {
            visitContainerExpression2(irContainerExpression, visibles);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitContainerExpression, reason: avoid collision after fix types in other method */
        public void visitContainerExpression2(IrContainerExpression irContainerExpression, Visibles visibles) {
            IrElementVisitor.DefaultImpls.visitContainerExpression(this, irContainerExpression, visibles);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitContinue */
        public /* bridge */ /* synthetic */ Unit visitContinue2(IrContinue irContinue, Visibles visibles) {
            visitContinue2(irContinue, visibles);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitContinue, reason: avoid collision after fix types in other method */
        public void visitContinue2(IrContinue irContinue, Visibles visibles) {
            IrElementVisitor.DefaultImpls.visitContinue(this, irContinue, visibles);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitDeclaration */
        public /* bridge */ /* synthetic */ Unit visitDeclaration2(IrDeclarationBase irDeclarationBase, Visibles visibles) {
            visitDeclaration2(irDeclarationBase, visibles);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitDeclaration, reason: avoid collision after fix types in other method */
        public void visitDeclaration2(IrDeclarationBase irDeclarationBase, Visibles visibles) {
            IrElementVisitor.DefaultImpls.visitDeclaration(this, irDeclarationBase, visibles);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitDeclarationReference */
        public /* bridge */ /* synthetic */ Unit visitDeclarationReference2(IrDeclarationReference irDeclarationReference, Visibles visibles) {
            visitDeclarationReference2(irDeclarationReference, visibles);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitDeclarationReference, reason: avoid collision after fix types in other method */
        public void visitDeclarationReference2(IrDeclarationReference irDeclarationReference, Visibles visibles) {
            IrElementVisitor.DefaultImpls.visitDeclarationReference(this, irDeclarationReference, visibles);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitDelegatingConstructorCall */
        public /* bridge */ /* synthetic */ Unit visitDelegatingConstructorCall2(IrDelegatingConstructorCall irDelegatingConstructorCall, Visibles visibles) {
            visitDelegatingConstructorCall2(irDelegatingConstructorCall, visibles);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitDelegatingConstructorCall, reason: avoid collision after fix types in other method */
        public void visitDelegatingConstructorCall2(IrDelegatingConstructorCall irDelegatingConstructorCall, Visibles visibles) {
            IrElementVisitor.DefaultImpls.visitDelegatingConstructorCall(this, irDelegatingConstructorCall, visibles);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitDoWhileLoop */
        public /* bridge */ /* synthetic */ Unit visitDoWhileLoop2(IrDoWhileLoop irDoWhileLoop, Visibles visibles) {
            visitDoWhileLoop2(irDoWhileLoop, visibles);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitDoWhileLoop, reason: avoid collision after fix types in other method */
        public void visitDoWhileLoop2(IrDoWhileLoop irDoWhileLoop, Visibles visibles) {
            IrElementVisitor.DefaultImpls.visitDoWhileLoop(this, irDoWhileLoop, visibles);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitDynamicExpression */
        public /* bridge */ /* synthetic */ Unit visitDynamicExpression2(IrDynamicExpression irDynamicExpression, Visibles visibles) {
            visitDynamicExpression2(irDynamicExpression, visibles);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitDynamicExpression, reason: avoid collision after fix types in other method */
        public void visitDynamicExpression2(IrDynamicExpression irDynamicExpression, Visibles visibles) {
            IrElementVisitor.DefaultImpls.visitDynamicExpression(this, irDynamicExpression, visibles);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitDynamicMemberExpression */
        public /* bridge */ /* synthetic */ Unit visitDynamicMemberExpression2(IrDynamicMemberExpression irDynamicMemberExpression, Visibles visibles) {
            visitDynamicMemberExpression2(irDynamicMemberExpression, visibles);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitDynamicMemberExpression, reason: avoid collision after fix types in other method */
        public void visitDynamicMemberExpression2(IrDynamicMemberExpression irDynamicMemberExpression, Visibles visibles) {
            IrElementVisitor.DefaultImpls.visitDynamicMemberExpression(this, irDynamicMemberExpression, visibles);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitDynamicOperatorExpression */
        public /* bridge */ /* synthetic */ Unit visitDynamicOperatorExpression2(IrDynamicOperatorExpression irDynamicOperatorExpression, Visibles visibles) {
            visitDynamicOperatorExpression2(irDynamicOperatorExpression, visibles);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitDynamicOperatorExpression, reason: avoid collision after fix types in other method */
        public void visitDynamicOperatorExpression2(IrDynamicOperatorExpression irDynamicOperatorExpression, Visibles visibles) {
            IrElementVisitor.DefaultImpls.visitDynamicOperatorExpression(this, irDynamicOperatorExpression, visibles);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitElement */
        public /* bridge */ /* synthetic */ Unit visitElement2(IrElement irElement, Visibles visibles) {
            visitElement2(irElement, visibles);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitElseBranch */
        public /* bridge */ /* synthetic */ Unit visitElseBranch2(IrElseBranch irElseBranch, Visibles visibles) {
            visitElseBranch2(irElseBranch, visibles);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitElseBranch, reason: avoid collision after fix types in other method */
        public void visitElseBranch2(IrElseBranch irElseBranch, Visibles visibles) {
            IrElementVisitor.DefaultImpls.visitElseBranch(this, irElseBranch, visibles);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitEnumConstructorCall */
        public /* bridge */ /* synthetic */ Unit visitEnumConstructorCall2(IrEnumConstructorCall irEnumConstructorCall, Visibles visibles) {
            visitEnumConstructorCall2(irEnumConstructorCall, visibles);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitEnumConstructorCall, reason: avoid collision after fix types in other method */
        public void visitEnumConstructorCall2(IrEnumConstructorCall irEnumConstructorCall, Visibles visibles) {
            IrElementVisitor.DefaultImpls.visitEnumConstructorCall(this, irEnumConstructorCall, visibles);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitEnumEntry */
        public /* bridge */ /* synthetic */ Unit visitEnumEntry2(IrEnumEntry irEnumEntry, Visibles visibles) {
            visitEnumEntry2(irEnumEntry, visibles);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitEnumEntry, reason: avoid collision after fix types in other method */
        public void visitEnumEntry2(IrEnumEntry irEnumEntry, Visibles visibles) {
            IrElementVisitor.DefaultImpls.visitEnumEntry(this, irEnumEntry, visibles);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitErrorCallExpression */
        public /* bridge */ /* synthetic */ Unit visitErrorCallExpression2(IrErrorCallExpression irErrorCallExpression, Visibles visibles) {
            visitErrorCallExpression2(irErrorCallExpression, visibles);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitErrorCallExpression, reason: avoid collision after fix types in other method */
        public void visitErrorCallExpression2(IrErrorCallExpression irErrorCallExpression, Visibles visibles) {
            IrElementVisitor.DefaultImpls.visitErrorCallExpression(this, irErrorCallExpression, visibles);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitErrorDeclaration */
        public /* bridge */ /* synthetic */ Unit visitErrorDeclaration2(IrErrorDeclaration irErrorDeclaration, Visibles visibles) {
            visitErrorDeclaration2(irErrorDeclaration, visibles);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitErrorDeclaration, reason: avoid collision after fix types in other method */
        public void visitErrorDeclaration2(IrErrorDeclaration irErrorDeclaration, Visibles visibles) {
            IrElementVisitor.DefaultImpls.visitErrorDeclaration(this, irErrorDeclaration, visibles);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitErrorExpression */
        public /* bridge */ /* synthetic */ Unit visitErrorExpression2(IrErrorExpression irErrorExpression, Visibles visibles) {
            visitErrorExpression2(irErrorExpression, visibles);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitErrorExpression, reason: avoid collision after fix types in other method */
        public void visitErrorExpression2(IrErrorExpression irErrorExpression, Visibles visibles) {
            IrElementVisitor.DefaultImpls.visitErrorExpression(this, irErrorExpression, visibles);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitExpression */
        public /* bridge */ /* synthetic */ Unit visitExpression2(IrExpression irExpression, Visibles visibles) {
            visitExpression2(irExpression, visibles);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitExpressionBody */
        public /* bridge */ /* synthetic */ Unit visitExpressionBody2(IrExpressionBody irExpressionBody, Visibles visibles) {
            visitExpressionBody2(irExpressionBody, visibles);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitExpressionBody, reason: avoid collision after fix types in other method */
        public void visitExpressionBody2(IrExpressionBody irExpressionBody, Visibles visibles) {
            IrElementVisitor.DefaultImpls.visitExpressionBody(this, irExpressionBody, visibles);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitExternalPackageFragment */
        public /* bridge */ /* synthetic */ Unit visitExternalPackageFragment2(IrExternalPackageFragment irExternalPackageFragment, Visibles visibles) {
            visitExternalPackageFragment2(irExternalPackageFragment, visibles);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitExternalPackageFragment, reason: avoid collision after fix types in other method */
        public void visitExternalPackageFragment2(IrExternalPackageFragment irExternalPackageFragment, Visibles visibles) {
            IrElementVisitor.DefaultImpls.visitExternalPackageFragment(this, irExternalPackageFragment, visibles);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitField */
        public /* bridge */ /* synthetic */ Unit visitField2(IrField irField, Visibles visibles) {
            visitField2(irField, visibles);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitFieldAccess */
        public /* bridge */ /* synthetic */ Unit visitFieldAccess2(IrFieldAccessExpression irFieldAccessExpression, Visibles visibles) {
            visitFieldAccess2(irFieldAccessExpression, visibles);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitFieldAccess, reason: avoid collision after fix types in other method */
        public void visitFieldAccess2(IrFieldAccessExpression irFieldAccessExpression, Visibles visibles) {
            IrElementVisitor.DefaultImpls.visitFieldAccess(this, irFieldAccessExpression, visibles);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitFile */
        public /* bridge */ /* synthetic */ Unit visitFile2(IrFile irFile, Visibles visibles) {
            visitFile2(irFile, visibles);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitFile, reason: avoid collision after fix types in other method */
        public void visitFile2(IrFile irFile, Visibles visibles) {
            IrElementVisitor.DefaultImpls.visitFile(this, irFile, visibles);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitFunction */
        public /* bridge */ /* synthetic */ Unit visitFunction2(IrFunction irFunction, Visibles visibles) {
            visitFunction2(irFunction, visibles);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitFunctionAccess */
        public /* bridge */ /* synthetic */ Unit visitFunctionAccess2(IrFunctionAccessExpression irFunctionAccessExpression, Visibles visibles) {
            visitFunctionAccess2(irFunctionAccessExpression, visibles);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitFunctionAccess, reason: avoid collision after fix types in other method */
        public void visitFunctionAccess2(IrFunctionAccessExpression irFunctionAccessExpression, Visibles visibles) {
            IrElementVisitor.DefaultImpls.visitFunctionAccess(this, irFunctionAccessExpression, visibles);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitFunctionExpression */
        public /* bridge */ /* synthetic */ Unit visitFunctionExpression2(IrFunctionExpression irFunctionExpression, Visibles visibles) {
            visitFunctionExpression2(irFunctionExpression, visibles);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitFunctionExpression, reason: avoid collision after fix types in other method */
        public void visitFunctionExpression2(IrFunctionExpression irFunctionExpression, Visibles visibles) {
            IrElementVisitor.DefaultImpls.visitFunctionExpression(this, irFunctionExpression, visibles);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitFunctionReference */
        public /* bridge */ /* synthetic */ Unit visitFunctionReference2(IrFunctionReference irFunctionReference, Visibles visibles) {
            visitFunctionReference2(irFunctionReference, visibles);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitFunctionReference, reason: avoid collision after fix types in other method */
        public void visitFunctionReference2(IrFunctionReference irFunctionReference, Visibles visibles) {
            IrElementVisitor.DefaultImpls.visitFunctionReference(this, irFunctionReference, visibles);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitGetClass */
        public /* bridge */ /* synthetic */ Unit visitGetClass2(IrGetClass irGetClass, Visibles visibles) {
            visitGetClass2(irGetClass, visibles);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitGetClass, reason: avoid collision after fix types in other method */
        public void visitGetClass2(IrGetClass irGetClass, Visibles visibles) {
            IrElementVisitor.DefaultImpls.visitGetClass(this, irGetClass, visibles);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitGetEnumValue */
        public /* bridge */ /* synthetic */ Unit visitGetEnumValue2(IrGetEnumValue irGetEnumValue, Visibles visibles) {
            visitGetEnumValue2(irGetEnumValue, visibles);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitGetEnumValue, reason: avoid collision after fix types in other method */
        public void visitGetEnumValue2(IrGetEnumValue irGetEnumValue, Visibles visibles) {
            IrElementVisitor.DefaultImpls.visitGetEnumValue(this, irGetEnumValue, visibles);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitGetField */
        public /* bridge */ /* synthetic */ Unit visitGetField2(IrGetField irGetField, Visibles visibles) {
            visitGetField2(irGetField, visibles);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitGetField, reason: avoid collision after fix types in other method */
        public void visitGetField2(IrGetField irGetField, Visibles visibles) {
            IrElementVisitor.DefaultImpls.visitGetField(this, irGetField, visibles);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitGetObjectValue */
        public /* bridge */ /* synthetic */ Unit visitGetObjectValue2(IrGetObjectValue irGetObjectValue, Visibles visibles) {
            visitGetObjectValue2(irGetObjectValue, visibles);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitGetObjectValue, reason: avoid collision after fix types in other method */
        public void visitGetObjectValue2(IrGetObjectValue irGetObjectValue, Visibles visibles) {
            IrElementVisitor.DefaultImpls.visitGetObjectValue(this, irGetObjectValue, visibles);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitGetValue */
        public /* bridge */ /* synthetic */ Unit visitGetValue2(IrGetValue irGetValue, Visibles visibles) {
            visitGetValue2(irGetValue, visibles);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitGetValue, reason: avoid collision after fix types in other method */
        public void visitGetValue2(IrGetValue irGetValue, Visibles visibles) {
            IrElementVisitor.DefaultImpls.visitGetValue(this, irGetValue, visibles);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitInstanceInitializerCall */
        public /* bridge */ /* synthetic */ Unit visitInstanceInitializerCall2(IrInstanceInitializerCall irInstanceInitializerCall, Visibles visibles) {
            visitInstanceInitializerCall2(irInstanceInitializerCall, visibles);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitInstanceInitializerCall, reason: avoid collision after fix types in other method */
        public void visitInstanceInitializerCall2(IrInstanceInitializerCall irInstanceInitializerCall, Visibles visibles) {
            IrElementVisitor.DefaultImpls.visitInstanceInitializerCall(this, irInstanceInitializerCall, visibles);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitLocalDelegatedProperty */
        public /* bridge */ /* synthetic */ Unit visitLocalDelegatedProperty2(IrLocalDelegatedProperty irLocalDelegatedProperty, Visibles visibles) {
            visitLocalDelegatedProperty2(irLocalDelegatedProperty, visibles);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitLocalDelegatedProperty, reason: avoid collision after fix types in other method */
        public void visitLocalDelegatedProperty2(IrLocalDelegatedProperty irLocalDelegatedProperty, Visibles visibles) {
            IrElementVisitor.DefaultImpls.visitLocalDelegatedProperty(this, irLocalDelegatedProperty, visibles);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitLocalDelegatedPropertyReference */
        public /* bridge */ /* synthetic */ Unit visitLocalDelegatedPropertyReference2(IrLocalDelegatedPropertyReference irLocalDelegatedPropertyReference, Visibles visibles) {
            visitLocalDelegatedPropertyReference2(irLocalDelegatedPropertyReference, visibles);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitLocalDelegatedPropertyReference, reason: avoid collision after fix types in other method */
        public void visitLocalDelegatedPropertyReference2(IrLocalDelegatedPropertyReference irLocalDelegatedPropertyReference, Visibles visibles) {
            IrElementVisitor.DefaultImpls.visitLocalDelegatedPropertyReference(this, irLocalDelegatedPropertyReference, visibles);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitLoop */
        public /* bridge */ /* synthetic */ Unit visitLoop2(IrLoop irLoop, Visibles visibles) {
            visitLoop2(irLoop, visibles);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitLoop, reason: avoid collision after fix types in other method */
        public void visitLoop2(IrLoop irLoop, Visibles visibles) {
            IrElementVisitor.DefaultImpls.visitLoop(this, irLoop, visibles);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public /* bridge */ /* synthetic */ Unit visitMemberAccess(IrMemberAccessExpression irMemberAccessExpression, Visibles visibles) {
            visitMemberAccess2((IrMemberAccessExpression<?>) irMemberAccessExpression, visibles);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitModuleFragment */
        public /* bridge */ /* synthetic */ Unit visitModuleFragment2(IrModuleFragment irModuleFragment, Visibles visibles) {
            visitModuleFragment2(irModuleFragment, visibles);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitModuleFragment, reason: avoid collision after fix types in other method */
        public void visitModuleFragment2(IrModuleFragment irModuleFragment, Visibles visibles) {
            IrElementVisitor.DefaultImpls.visitModuleFragment(this, irModuleFragment, visibles);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitPackageFragment */
        public /* bridge */ /* synthetic */ Unit visitPackageFragment2(IrPackageFragment irPackageFragment, Visibles visibles) {
            visitPackageFragment2(irPackageFragment, visibles);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitPackageFragment, reason: avoid collision after fix types in other method */
        public void visitPackageFragment2(IrPackageFragment irPackageFragment, Visibles visibles) {
            IrElementVisitor.DefaultImpls.visitPackageFragment(this, irPackageFragment, visibles);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitProperty */
        public /* bridge */ /* synthetic */ Unit visitProperty2(IrProperty irProperty, Visibles visibles) {
            visitProperty2(irProperty, visibles);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitProperty, reason: avoid collision after fix types in other method */
        public void visitProperty2(IrProperty irProperty, Visibles visibles) {
            IrElementVisitor.DefaultImpls.visitProperty(this, irProperty, visibles);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitPropertyReference */
        public /* bridge */ /* synthetic */ Unit visitPropertyReference2(IrPropertyReference irPropertyReference, Visibles visibles) {
            visitPropertyReference2(irPropertyReference, visibles);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitPropertyReference, reason: avoid collision after fix types in other method */
        public void visitPropertyReference2(IrPropertyReference irPropertyReference, Visibles visibles) {
            IrElementVisitor.DefaultImpls.visitPropertyReference(this, irPropertyReference, visibles);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitRawFunctionReference */
        public /* bridge */ /* synthetic */ Unit visitRawFunctionReference2(IrRawFunctionReference irRawFunctionReference, Visibles visibles) {
            visitRawFunctionReference2(irRawFunctionReference, visibles);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitRawFunctionReference, reason: avoid collision after fix types in other method */
        public void visitRawFunctionReference2(IrRawFunctionReference irRawFunctionReference, Visibles visibles) {
            IrElementVisitor.DefaultImpls.visitRawFunctionReference(this, irRawFunctionReference, visibles);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitReturn */
        public /* bridge */ /* synthetic */ Unit visitReturn2(IrReturn irReturn, Visibles visibles) {
            visitReturn2(irReturn, visibles);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitReturn, reason: avoid collision after fix types in other method */
        public void visitReturn2(IrReturn irReturn, Visibles visibles) {
            IrElementVisitor.DefaultImpls.visitReturn(this, irReturn, visibles);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitScript */
        public /* bridge */ /* synthetic */ Unit visitScript2(IrScript irScript, Visibles visibles) {
            visitScript2(irScript, visibles);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitScript, reason: avoid collision after fix types in other method */
        public void visitScript2(IrScript irScript, Visibles visibles) {
            IrElementVisitor.DefaultImpls.visitScript(this, irScript, visibles);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitSetField */
        public /* bridge */ /* synthetic */ Unit visitSetField2(IrSetField irSetField, Visibles visibles) {
            visitSetField2(irSetField, visibles);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitSetField, reason: avoid collision after fix types in other method */
        public void visitSetField2(IrSetField irSetField, Visibles visibles) {
            IrElementVisitor.DefaultImpls.visitSetField(this, irSetField, visibles);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitSetValue */
        public /* bridge */ /* synthetic */ Unit visitSetValue2(IrSetValue irSetValue, Visibles visibles) {
            visitSetValue2(irSetValue, visibles);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitSetValue, reason: avoid collision after fix types in other method */
        public void visitSetValue2(IrSetValue irSetValue, Visibles visibles) {
            IrElementVisitor.DefaultImpls.visitSetValue(this, irSetValue, visibles);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitSimpleFunction */
        public /* bridge */ /* synthetic */ Unit visitSimpleFunction2(IrSimpleFunction irSimpleFunction, Visibles visibles) {
            visitSimpleFunction2(irSimpleFunction, visibles);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitSimpleFunction, reason: avoid collision after fix types in other method */
        public void visitSimpleFunction2(IrSimpleFunction irSimpleFunction, Visibles visibles) {
            IrElementVisitor.DefaultImpls.visitSimpleFunction(this, irSimpleFunction, visibles);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitSingletonReference */
        public /* bridge */ /* synthetic */ Unit visitSingletonReference2(IrGetSingletonValue irGetSingletonValue, Visibles visibles) {
            visitSingletonReference2(irGetSingletonValue, visibles);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitSingletonReference, reason: avoid collision after fix types in other method */
        public void visitSingletonReference2(IrGetSingletonValue irGetSingletonValue, Visibles visibles) {
            IrElementVisitor.DefaultImpls.visitSingletonReference(this, irGetSingletonValue, visibles);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitSpreadElement */
        public /* bridge */ /* synthetic */ Unit visitSpreadElement2(IrSpreadElement irSpreadElement, Visibles visibles) {
            visitSpreadElement2(irSpreadElement, visibles);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitSpreadElement, reason: avoid collision after fix types in other method */
        public void visitSpreadElement2(IrSpreadElement irSpreadElement, Visibles visibles) {
            IrElementVisitor.DefaultImpls.visitSpreadElement(this, irSpreadElement, visibles);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitStringConcatenation */
        public /* bridge */ /* synthetic */ Unit visitStringConcatenation2(IrStringConcatenation irStringConcatenation, Visibles visibles) {
            visitStringConcatenation2(irStringConcatenation, visibles);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitStringConcatenation, reason: avoid collision after fix types in other method */
        public void visitStringConcatenation2(IrStringConcatenation irStringConcatenation, Visibles visibles) {
            IrElementVisitor.DefaultImpls.visitStringConcatenation(this, irStringConcatenation, visibles);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitSuspendableExpression */
        public /* bridge */ /* synthetic */ Unit visitSuspendableExpression2(IrSuspendableExpression irSuspendableExpression, Visibles visibles) {
            visitSuspendableExpression2(irSuspendableExpression, visibles);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitSuspendableExpression, reason: avoid collision after fix types in other method */
        public void visitSuspendableExpression2(IrSuspendableExpression irSuspendableExpression, Visibles visibles) {
            IrElementVisitor.DefaultImpls.visitSuspendableExpression(this, irSuspendableExpression, visibles);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitSuspensionPoint */
        public /* bridge */ /* synthetic */ Unit visitSuspensionPoint2(IrSuspensionPoint irSuspensionPoint, Visibles visibles) {
            visitSuspensionPoint2(irSuspensionPoint, visibles);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitSuspensionPoint, reason: avoid collision after fix types in other method */
        public void visitSuspensionPoint2(IrSuspensionPoint irSuspensionPoint, Visibles visibles) {
            IrElementVisitor.DefaultImpls.visitSuspensionPoint(this, irSuspensionPoint, visibles);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitSyntheticBody */
        public /* bridge */ /* synthetic */ Unit visitSyntheticBody2(IrSyntheticBody irSyntheticBody, Visibles visibles) {
            visitSyntheticBody2(irSyntheticBody, visibles);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitSyntheticBody, reason: avoid collision after fix types in other method */
        public void visitSyntheticBody2(IrSyntheticBody irSyntheticBody, Visibles visibles) {
            IrElementVisitor.DefaultImpls.visitSyntheticBody(this, irSyntheticBody, visibles);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitThrow */
        public /* bridge */ /* synthetic */ Unit visitThrow2(IrThrow irThrow, Visibles visibles) {
            visitThrow2(irThrow, visibles);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitThrow, reason: avoid collision after fix types in other method */
        public void visitThrow2(IrThrow irThrow, Visibles visibles) {
            IrElementVisitor.DefaultImpls.visitThrow(this, irThrow, visibles);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitTry */
        public /* bridge */ /* synthetic */ Unit visitTry2(IrTry irTry, Visibles visibles) {
            visitTry2(irTry, visibles);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitTry, reason: avoid collision after fix types in other method */
        public void visitTry2(IrTry irTry, Visibles visibles) {
            IrElementVisitor.DefaultImpls.visitTry(this, irTry, visibles);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitTypeAlias */
        public /* bridge */ /* synthetic */ Unit visitTypeAlias2(IrTypeAlias irTypeAlias, Visibles visibles) {
            visitTypeAlias2(irTypeAlias, visibles);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitTypeOperator */
        public /* bridge */ /* synthetic */ Unit visitTypeOperator2(IrTypeOperatorCall irTypeOperatorCall, Visibles visibles) {
            visitTypeOperator2(irTypeOperatorCall, visibles);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitTypeParameter */
        public /* bridge */ /* synthetic */ Unit visitTypeParameter2(IrTypeParameter irTypeParameter, Visibles visibles) {
            visitTypeParameter2(irTypeParameter, visibles);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitValueAccess */
        public /* bridge */ /* synthetic */ Unit visitValueAccess2(IrValueAccessExpression irValueAccessExpression, Visibles visibles) {
            visitValueAccess2(irValueAccessExpression, visibles);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitValueParameter */
        public /* bridge */ /* synthetic */ Unit visitValueParameter2(IrValueParameter irValueParameter, Visibles visibles) {
            visitValueParameter2(irValueParameter, visibles);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitVararg */
        public /* bridge */ /* synthetic */ Unit visitVararg2(IrVararg irVararg, Visibles visibles) {
            visitVararg2(irVararg, visibles);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitVariable */
        public /* bridge */ /* synthetic */ Unit visitVariable2(IrVariable irVariable, Visibles visibles) {
            visitVariable2(irVariable, visibles);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitWhen */
        public /* bridge */ /* synthetic */ Unit visitWhen2(IrWhen irWhen, Visibles visibles) {
            visitWhen2(irWhen, visibles);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitWhen, reason: avoid collision after fix types in other method */
        public void visitWhen2(IrWhen irWhen, Visibles visibles) {
            IrElementVisitor.DefaultImpls.visitWhen(this, irWhen, visibles);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitWhileLoop */
        public /* bridge */ /* synthetic */ Unit visitWhileLoop2(IrWhileLoop irWhileLoop, Visibles visibles) {
            visitWhileLoop2(irWhileLoop, visibles);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitWhileLoop, reason: avoid collision after fix types in other method */
        public void visitWhileLoop2(IrWhileLoop irWhileLoop, Visibles visibles) {
            IrElementVisitor.DefaultImpls.visitWhileLoop(this, irWhileLoop, visibles);
        }

        /* JADX INFO: renamed from: visitElement, reason: avoid collision after fix types in other method */
        public void visitElement2(IrElement element, Visibles data) {
            Intrinsics.checkNotNullParameter(element, "element");
            Intrinsics.checkNotNullParameter(data, "data");
            element.acceptChildren(this, data);
        }

        /* JADX INFO: renamed from: visitClass, reason: avoid collision after fix types in other method */
        public void visitClass2(IrClass declaration, Visibles data) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            Intrinsics.checkNotNullParameter(data, "data");
            Visibles visiblesExtend = data.extend(declaration.getTypeParameters(), CollectionsKt.listOfNotNull(declaration.getThisReceiver()));
            Iterator<IrType> it = declaration.getSuperTypes().iterator();
            while (it.hasNext()) {
                visiblesExtend.visitTypeAccess(declaration, it.next());
            }
            IrElementVisitor.DefaultImpls.visitClass(this, declaration, visiblesExtend);
        }

        /* JADX INFO: renamed from: visitFunction, reason: avoid collision after fix types in other method */
        public void visitFunction2(IrFunction declaration, Visibles data) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            Intrinsics.checkNotNullParameter(data, "data");
            Visibles visiblesExtend = data.extend(declaration.getTypeParameters(), CollectionsKt.plus((Collection) CollectionsKt.listOfNotNull((Object[]) new IrValueParameter[]{declaration.getDispatchReceiverParameter(), declaration.getExtensionReceiverParameter()}), (Iterable) declaration.getValueParameters()));
            visiblesExtend.visitTypeAccess(declaration, declaration.getReturnType());
            IrElementVisitor.DefaultImpls.visitFunction(this, declaration, visiblesExtend);
        }

        /* JADX INFO: renamed from: visitAnonymousInitializer, reason: avoid collision after fix types in other method */
        public void visitAnonymousInitializer2(IrAnonymousInitializer declaration, Visibles data) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            Intrinsics.checkNotNullParameter(data, "data");
            IrConstructor irConstructorPrimaryConstructor = primaryConstructor(IrUtilsKt.getParentAsClass(declaration));
            if (irConstructorPrimaryConstructor == null) {
                IrElementVisitor.DefaultImpls.visitAnonymousInitializer(this, declaration, data);
            } else {
                IrElementVisitor.DefaultImpls.visitAnonymousInitializer(this, declaration, data.extend(SetsKt.emptySet(), irConstructorPrimaryConstructor.getValueParameters()));
            }
        }

        /* JADX INFO: renamed from: visitField, reason: avoid collision after fix types in other method */
        public void visitField2(IrField declaration, Visibles data) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            Intrinsics.checkNotNullParameter(data, "data");
            data.visitTypeAccess(declaration, declaration.getType());
            if (declaration.getInitializer() == null) {
                IrElementVisitor.DefaultImpls.visitField(this, declaration, data);
                return;
            }
            IrDeclarationParent parent = declaration.getParent();
            IrClass irClass = parent instanceof IrClass ? (IrClass) parent : null;
            IrConstructor irConstructorPrimaryConstructor = irClass != null ? primaryConstructor(irClass) : null;
            if (irConstructorPrimaryConstructor == null) {
                IrElementVisitor.DefaultImpls.visitField(this, declaration, data);
            } else {
                IrElementVisitor.DefaultImpls.visitField(this, declaration, data.extend(SetsKt.emptySet(), irConstructorPrimaryConstructor.getValueParameters()));
            }
        }

        /* JADX INFO: renamed from: visitBlock, reason: avoid collision after fix types in other method */
        public void visitBlock2(IrBlock expression, Visibles data) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            Intrinsics.checkNotNullParameter(data, "data");
            IrElementVisitor.DefaultImpls.visitBlock(this, expression, data.extend(SetsKt.emptySet(), SetsKt.emptySet()));
        }

        /* JADX INFO: renamed from: visitVariable, reason: avoid collision after fix types in other method */
        public void visitVariable2(IrVariable declaration, Visibles data) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            Intrinsics.checkNotNullParameter(data, "data");
            data.visitTypeAccess(declaration, declaration.getType());
            IrElementVisitor.DefaultImpls.visitVariable(this, declaration, data);
            data.getValues().add(declaration);
        }

        /* JADX INFO: renamed from: visitTypeAlias, reason: avoid collision after fix types in other method */
        public void visitTypeAlias2(IrTypeAlias declaration, Visibles data) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            Intrinsics.checkNotNullParameter(data, "data");
            Visibles visiblesExtend = data.extend(declaration.getTypeParameters(), SetsKt.emptySet());
            visiblesExtend.visitTypeAccess(declaration, declaration.getExpandedType());
            IrElementVisitor.DefaultImpls.visitTypeAlias(this, declaration, visiblesExtend);
        }

        /* JADX INFO: renamed from: visitTypeParameter, reason: avoid collision after fix types in other method */
        public void visitTypeParameter2(IrTypeParameter declaration, Visibles data) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            Intrinsics.checkNotNullParameter(data, "data");
            Iterator<IrType> it = declaration.getSuperTypes().iterator();
            while (it.hasNext()) {
                data.visitTypeAccess(declaration, it.next());
            }
            IrElementVisitor.DefaultImpls.visitTypeParameter(this, declaration, data);
        }

        /* JADX INFO: renamed from: visitValueParameter, reason: avoid collision after fix types in other method */
        public void visitValueParameter2(IrValueParameter declaration, Visibles data) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            Intrinsics.checkNotNullParameter(data, "data");
            IrValueParameter irValueParameter = declaration;
            data.visitTypeAccess(irValueParameter, declaration.getType());
            IrType varargElementType = declaration.getVarargElementType();
            if (varargElementType != null) {
                data.visitTypeAccess(irValueParameter, varargElementType);
            }
            IrElementVisitor.DefaultImpls.visitValueParameter(this, declaration, data);
        }

        /* JADX INFO: renamed from: visitValueAccess, reason: avoid collision after fix types in other method */
        public void visitValueAccess2(IrValueAccessExpression expression, Visibles data) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            Intrinsics.checkNotNullParameter(data, "data");
            data.visitValueAccess(expression, expression.getSymbol().getOwner());
            IrElementVisitor.DefaultImpls.visitValueAccess(this, expression, data);
        }

        /* JADX INFO: renamed from: visitMemberAccess, reason: avoid collision after fix types in other method */
        public void visitMemberAccess2(IrMemberAccessExpression<?> expression, Visibles data) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            Intrinsics.checkNotNullParameter(data, "data");
            int typeArgumentsCount = expression.getTypeArgumentsCount();
            for (int i = 0; i < typeArgumentsCount; i++) {
                IrType typeArgument = expression.getTypeArgument(i);
                if (typeArgument != null) {
                    data.visitTypeAccess(expression, typeArgument);
                }
            }
            IrElementVisitor.DefaultImpls.visitMemberAccess(this, expression, data);
        }

        /* JADX INFO: renamed from: visitCatch, reason: avoid collision after fix types in other method */
        public void visitCatch2(IrCatch aCatch, Visibles data) {
            Intrinsics.checkNotNullParameter(aCatch, "aCatch");
            Intrinsics.checkNotNullParameter(data, "data");
            IrElementVisitor.DefaultImpls.visitCatch(this, aCatch, data.extend(SetsKt.emptySet(), SetsKt.emptySet()));
        }

        /* JADX INFO: renamed from: visitTypeOperator, reason: avoid collision after fix types in other method */
        public void visitTypeOperator2(IrTypeOperatorCall expression, Visibles data) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            Intrinsics.checkNotNullParameter(data, "data");
            data.visitTypeAccess(expression, expression.getTypeOperand());
            IrElementVisitor.DefaultImpls.visitTypeOperator(this, expression, data);
        }

        /* JADX INFO: renamed from: visitClassReference, reason: avoid collision after fix types in other method */
        public void visitClassReference2(IrClassReference expression, Visibles data) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            Intrinsics.checkNotNullParameter(data, "data");
            data.visitTypeAccess(expression, expression.getClassType());
            IrElementVisitor.DefaultImpls.visitClassReference(this, expression, data);
        }

        /* JADX INFO: renamed from: visitVararg, reason: avoid collision after fix types in other method */
        public void visitVararg2(IrVararg expression, Visibles data) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            Intrinsics.checkNotNullParameter(data, "data");
            data.visitTypeAccess(expression, expression.getVarargElementType());
            IrElementVisitor.DefaultImpls.visitVararg(this, expression, data);
        }

        /* JADX INFO: renamed from: visitExpression, reason: avoid collision after fix types in other method */
        public void visitExpression2(IrExpression expression, Visibles data) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            Intrinsics.checkNotNullParameter(data, "data");
            data.visitTypeAccess(expression, expression.getType());
            IrElementVisitor.DefaultImpls.visitExpression(this, expression, data);
        }

        private final IrConstructor primaryConstructor(IrClass irClass) {
            IrConstructor irConstructor = null;
            boolean z = false;
            IrConstructor irConstructor2 = null;
            for (IrConstructor irConstructor3 : IrUtilsKt.getConstructors(irClass)) {
                if (irConstructor3.getIsPrimary()) {
                    if (z) {
                        return irConstructor;
                    }
                    z = true;
                    irConstructor2 = irConstructor3;
                }
            }
            if (z) {
                irConstructor = irConstructor2;
            }
            return irConstructor;
        }
    }
}
