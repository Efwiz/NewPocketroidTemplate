package org.jetbrains.kotlin.ir.visitors;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.descriptors.ValueClassRepresentation;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.declarations.IrAnonymousInitializer;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationBase;
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
import org.jetbrains.kotlin.ir.types.IrSimpleType;
import org.jetbrains.kotlin.ir.types.IrType;

/* JADX INFO: compiled from: IrTypeTransformer.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u009c\u0001\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u0000*\u0006\b\u0000\u0010\u0001 \u00002\b\u0012\u0004\u0012\u0002H\u00010\u0002J1\u0010\u0003\u001a\u0002H\u0004\"\n\b\u0001\u0010\u0004*\u0004\u0018\u00010\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u0002H\u00042\u0006\u0010\t\u001a\u00028\u0000H&¢\u0006\u0002\u0010\nJ\u001d\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010\u000fJ\u001d\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010\u0014J\u001d\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0012\u001a\u00020\u00172\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010\u0018J\u001d\u0010\u0019\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00112\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010\u001aJ\u001d\u0010\u001b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u001c2\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010\u001dJ\u001d\u0010\u001e\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u001f2\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010 J\u001d\u0010!\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\"2\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010#J!\u0010$\u001a\u00020\u00072\n\u0010\u0012\u001a\u0006\u0012\u0002\b\u00030%2\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010&J\u001d\u0010'\u001a\u00020\f2\u0006\u0010\r\u001a\u00020(2\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010)J\u001d\u0010*\u001a\u00020\f2\u0006\u0010\r\u001a\u00020+2\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010,J\u001d\u0010-\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020.2\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010/J\u001d\u00100\u001a\u00020\f2\u0006\u0010\r\u001a\u0002012\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0002\u00102J\u001d\u00103\u001a\u00020\f2\u0006\u0010\r\u001a\u0002042\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0002\u00105J\u001d\u00106\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u0002072\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0002\u00108J\u001d\u00109\u001a\u00020\f2\u0006\u0010\r\u001a\u00020:2\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010;¨\u0006<"}, d2 = {"Lorg/jetbrains/kotlin/ir/visitors/IrTypeTransformer;", "D", "Lorg/jetbrains/kotlin/ir/visitors/IrElementTransformer;", "transformType", "Type", "Lorg/jetbrains/kotlin/ir/types/IrType;", "container", "Lorg/jetbrains/kotlin/ir/IrElement;", ModuleXmlParser.TYPE, "data", "(Lorg/jetbrains/kotlin/ir/IrElement;Lorg/jetbrains/kotlin/ir/types/IrType;Ljava/lang/Object;)Lorg/jetbrains/kotlin/ir/types/IrType;", "visitClass", "Lorg/jetbrains/kotlin/ir/IrStatement;", "declaration", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "(Lorg/jetbrains/kotlin/ir/declarations/IrClass;Ljava/lang/Object;)Lorg/jetbrains/kotlin/ir/IrStatement;", "visitClassReference", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrClassReference;", "(Lorg/jetbrains/kotlin/ir/expressions/IrClassReference;Ljava/lang/Object;)Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "visitConstantObject", "Lorg/jetbrains/kotlin/ir/expressions/IrConstantValue;", "Lorg/jetbrains/kotlin/ir/expressions/IrConstantObject;", "(Lorg/jetbrains/kotlin/ir/expressions/IrConstantObject;Ljava/lang/Object;)Lorg/jetbrains/kotlin/ir/expressions/IrConstantValue;", "visitExpression", "(Lorg/jetbrains/kotlin/ir/expressions/IrExpression;Ljava/lang/Object;)Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "visitField", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "(Lorg/jetbrains/kotlin/ir/declarations/IrField;Ljava/lang/Object;)Lorg/jetbrains/kotlin/ir/IrStatement;", "visitFunction", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "(Lorg/jetbrains/kotlin/ir/declarations/IrFunction;Ljava/lang/Object;)Lorg/jetbrains/kotlin/ir/IrStatement;", "visitLocalDelegatedProperty", "Lorg/jetbrains/kotlin/ir/declarations/IrLocalDelegatedProperty;", "(Lorg/jetbrains/kotlin/ir/declarations/IrLocalDelegatedProperty;Ljava/lang/Object;)Lorg/jetbrains/kotlin/ir/IrStatement;", "visitMemberAccess", "Lorg/jetbrains/kotlin/ir/expressions/IrMemberAccessExpression;", "(Lorg/jetbrains/kotlin/ir/expressions/IrMemberAccessExpression;Ljava/lang/Object;)Lorg/jetbrains/kotlin/ir/IrElement;", "visitScript", "Lorg/jetbrains/kotlin/ir/declarations/IrScript;", "(Lorg/jetbrains/kotlin/ir/declarations/IrScript;Ljava/lang/Object;)Lorg/jetbrains/kotlin/ir/IrStatement;", "visitTypeAlias", "Lorg/jetbrains/kotlin/ir/declarations/IrTypeAlias;", "(Lorg/jetbrains/kotlin/ir/declarations/IrTypeAlias;Ljava/lang/Object;)Lorg/jetbrains/kotlin/ir/IrStatement;", "visitTypeOperator", "Lorg/jetbrains/kotlin/ir/expressions/IrTypeOperatorCall;", "(Lorg/jetbrains/kotlin/ir/expressions/IrTypeOperatorCall;Ljava/lang/Object;)Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "visitTypeParameter", "Lorg/jetbrains/kotlin/ir/declarations/IrTypeParameter;", "(Lorg/jetbrains/kotlin/ir/declarations/IrTypeParameter;Ljava/lang/Object;)Lorg/jetbrains/kotlin/ir/IrStatement;", "visitValueParameter", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "(Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;Ljava/lang/Object;)Lorg/jetbrains/kotlin/ir/IrStatement;", "visitVararg", "Lorg/jetbrains/kotlin/ir/expressions/IrVararg;", "(Lorg/jetbrains/kotlin/ir/expressions/IrVararg;Ljava/lang/Object;)Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "visitVariable", "Lorg/jetbrains/kotlin/ir/declarations/IrVariable;", "(Lorg/jetbrains/kotlin/ir/declarations/IrVariable;Ljava/lang/Object;)Lorg/jetbrains/kotlin/ir/IrStatement;", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface IrTypeTransformer<D> extends IrElementTransformer<D> {
    <Type extends IrType> Type transformType(IrElement container, Type type, D data);

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitClass */
    IrElement visitClass2(IrClass declaration, D data);

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitClassReference */
    IrElement visitClassReference2(IrClassReference expression, D data);

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitConstantObject */
    IrElement visitConstantObject2(IrConstantObject expression, D data);

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitExpression */
    IrElement visitExpression2(IrExpression expression, D data);

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitField */
    IrElement visitField2(IrField declaration, D data);

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitFunction */
    IrElement visitFunction2(IrFunction declaration, D data);

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitLocalDelegatedProperty */
    IrElement visitLocalDelegatedProperty2(IrLocalDelegatedProperty declaration, D data);

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    IrElement visitMemberAccess(IrMemberAccessExpression<?> expression, D data);

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitScript */
    IrElement visitScript2(IrScript declaration, D data);

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitTypeAlias */
    IrElement visitTypeAlias2(IrTypeAlias declaration, D data);

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitTypeOperator */
    IrElement visitTypeOperator2(IrTypeOperatorCall expression, D data);

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitTypeParameter */
    IrElement visitTypeParameter2(IrTypeParameter declaration, D data);

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitValueParameter */
    IrElement visitValueParameter2(IrValueParameter declaration, D data);

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitVararg */
    IrElement visitVararg2(IrVararg expression, D data);

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitVariable */
    IrElement visitVariable2(IrVariable declaration, D data);

    /* JADX INFO: compiled from: IrTypeTransformer.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public static final class DefaultImpls {
        public static <D> IrStatement visitAnonymousInitializer(IrTypeTransformer<? super D> irTypeTransformer, IrAnonymousInitializer declaration, D d) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            return IrElementTransformer.DefaultImpls.visitAnonymousInitializer(irTypeTransformer, declaration, d);
        }

        public static <D> IrExpression visitBlock(IrTypeTransformer<? super D> irTypeTransformer, IrBlock expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return IrElementTransformer.DefaultImpls.visitBlock(irTypeTransformer, expression, d);
        }

        public static <D> IrBody visitBlockBody(IrTypeTransformer<? super D> irTypeTransformer, IrBlockBody body, D d) {
            Intrinsics.checkNotNullParameter(body, "body");
            return IrElementTransformer.DefaultImpls.visitBlockBody(irTypeTransformer, body, d);
        }

        public static <D> IrBody visitBody(IrTypeTransformer<? super D> irTypeTransformer, IrBody body, D d) {
            Intrinsics.checkNotNullParameter(body, "body");
            return IrElementTransformer.DefaultImpls.visitBody(irTypeTransformer, body, d);
        }

        public static <D> IrBranch visitBranch(IrTypeTransformer<? super D> irTypeTransformer, IrBranch branch, D d) {
            Intrinsics.checkNotNullParameter(branch, "branch");
            return IrElementTransformer.DefaultImpls.visitBranch(irTypeTransformer, branch, d);
        }

        public static <D> IrExpression visitBreak(IrTypeTransformer<? super D> irTypeTransformer, IrBreak jump, D d) {
            Intrinsics.checkNotNullParameter(jump, "jump");
            return IrElementTransformer.DefaultImpls.visitBreak(irTypeTransformer, jump, d);
        }

        public static <D> IrExpression visitBreakContinue(IrTypeTransformer<? super D> irTypeTransformer, IrBreakContinue jump, D d) {
            Intrinsics.checkNotNullParameter(jump, "jump");
            return IrElementTransformer.DefaultImpls.visitBreakContinue(irTypeTransformer, jump, d);
        }

        public static <D> IrElement visitCall(IrTypeTransformer<? super D> irTypeTransformer, IrCall expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return IrElementTransformer.DefaultImpls.visitCall(irTypeTransformer, expression, d);
        }

        public static <D> IrElement visitCallableReference(IrTypeTransformer<? super D> irTypeTransformer, IrCallableReference<?> expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return IrElementTransformer.DefaultImpls.visitCallableReference(irTypeTransformer, expression, d);
        }

        public static <D> IrCatch visitCatch(IrTypeTransformer<? super D> irTypeTransformer, IrCatch aCatch, D d) {
            Intrinsics.checkNotNullParameter(aCatch, "aCatch");
            return IrElementTransformer.DefaultImpls.visitCatch(irTypeTransformer, aCatch, d);
        }

        public static <D> IrExpression visitComposite(IrTypeTransformer<? super D> irTypeTransformer, IrComposite expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return IrElementTransformer.DefaultImpls.visitComposite(irTypeTransformer, expression, d);
        }

        public static <D> IrExpression visitConst(IrTypeTransformer<? super D> irTypeTransformer, IrConst<?> expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return IrElementTransformer.DefaultImpls.visitConst(irTypeTransformer, expression, d);
        }

        public static <D> IrConstantValue visitConstantArray(IrTypeTransformer<? super D> irTypeTransformer, IrConstantArray expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return IrElementTransformer.DefaultImpls.visitConstantArray(irTypeTransformer, expression, d);
        }

        public static <D> IrConstantValue visitConstantPrimitive(IrTypeTransformer<? super D> irTypeTransformer, IrConstantPrimitive expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return IrElementTransformer.DefaultImpls.visitConstantPrimitive(irTypeTransformer, expression, d);
        }

        public static <D> IrConstantValue visitConstantValue(IrTypeTransformer<? super D> irTypeTransformer, IrConstantValue expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return IrElementTransformer.DefaultImpls.visitConstantValue(irTypeTransformer, expression, d);
        }

        public static <D> IrStatement visitConstructor(IrTypeTransformer<? super D> irTypeTransformer, IrConstructor declaration, D d) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            return IrElementTransformer.DefaultImpls.visitConstructor(irTypeTransformer, declaration, d);
        }

        public static <D> IrElement visitConstructorCall(IrTypeTransformer<? super D> irTypeTransformer, IrConstructorCall expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return IrElementTransformer.DefaultImpls.visitConstructorCall(irTypeTransformer, expression, d);
        }

        public static <D> IrExpression visitContainerExpression(IrTypeTransformer<? super D> irTypeTransformer, IrContainerExpression expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return IrElementTransformer.DefaultImpls.visitContainerExpression(irTypeTransformer, expression, d);
        }

        public static <D> IrExpression visitContinue(IrTypeTransformer<? super D> irTypeTransformer, IrContinue jump, D d) {
            Intrinsics.checkNotNullParameter(jump, "jump");
            return IrElementTransformer.DefaultImpls.visitContinue(irTypeTransformer, jump, d);
        }

        public static <D> IrStatement visitDeclaration(IrTypeTransformer<? super D> irTypeTransformer, IrDeclarationBase declaration, D d) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            return IrElementTransformer.DefaultImpls.visitDeclaration(irTypeTransformer, declaration, d);
        }

        public static <D> IrExpression visitDeclarationReference(IrTypeTransformer<? super D> irTypeTransformer, IrDeclarationReference expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return IrElementTransformer.DefaultImpls.visitDeclarationReference(irTypeTransformer, expression, d);
        }

        public static <D> IrElement visitDelegatingConstructorCall(IrTypeTransformer<? super D> irTypeTransformer, IrDelegatingConstructorCall expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return IrElementTransformer.DefaultImpls.visitDelegatingConstructorCall(irTypeTransformer, expression, d);
        }

        public static <D> IrExpression visitDoWhileLoop(IrTypeTransformer<? super D> irTypeTransformer, IrDoWhileLoop loop, D d) {
            Intrinsics.checkNotNullParameter(loop, "loop");
            return IrElementTransformer.DefaultImpls.visitDoWhileLoop(irTypeTransformer, loop, d);
        }

        public static <D> IrExpression visitDynamicExpression(IrTypeTransformer<? super D> irTypeTransformer, IrDynamicExpression expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return IrElementTransformer.DefaultImpls.visitDynamicExpression(irTypeTransformer, expression, d);
        }

        public static <D> IrExpression visitDynamicMemberExpression(IrTypeTransformer<? super D> irTypeTransformer, IrDynamicMemberExpression expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return IrElementTransformer.DefaultImpls.visitDynamicMemberExpression(irTypeTransformer, expression, d);
        }

        public static <D> IrExpression visitDynamicOperatorExpression(IrTypeTransformer<? super D> irTypeTransformer, IrDynamicOperatorExpression expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return IrElementTransformer.DefaultImpls.visitDynamicOperatorExpression(irTypeTransformer, expression, d);
        }

        public static <D> IrElement visitElement(IrTypeTransformer<? super D> irTypeTransformer, IrElement element, D d) {
            Intrinsics.checkNotNullParameter(element, "element");
            return IrElementTransformer.DefaultImpls.visitElement(irTypeTransformer, element, d);
        }

        public static <D> IrElseBranch visitElseBranch(IrTypeTransformer<? super D> irTypeTransformer, IrElseBranch branch, D d) {
            Intrinsics.checkNotNullParameter(branch, "branch");
            return IrElementTransformer.DefaultImpls.visitElseBranch(irTypeTransformer, branch, d);
        }

        public static <D> IrElement visitEnumConstructorCall(IrTypeTransformer<? super D> irTypeTransformer, IrEnumConstructorCall expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return IrElementTransformer.DefaultImpls.visitEnumConstructorCall(irTypeTransformer, expression, d);
        }

        public static <D> IrStatement visitEnumEntry(IrTypeTransformer<? super D> irTypeTransformer, IrEnumEntry declaration, D d) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            return IrElementTransformer.DefaultImpls.visitEnumEntry(irTypeTransformer, declaration, d);
        }

        public static <D> IrExpression visitErrorCallExpression(IrTypeTransformer<? super D> irTypeTransformer, IrErrorCallExpression expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return IrElementTransformer.DefaultImpls.visitErrorCallExpression(irTypeTransformer, expression, d);
        }

        public static <D> IrStatement visitErrorDeclaration(IrTypeTransformer<? super D> irTypeTransformer, IrErrorDeclaration declaration, D d) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            return IrElementTransformer.DefaultImpls.visitErrorDeclaration(irTypeTransformer, declaration, d);
        }

        public static <D> IrExpression visitErrorExpression(IrTypeTransformer<? super D> irTypeTransformer, IrErrorExpression expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return IrElementTransformer.DefaultImpls.visitErrorExpression(irTypeTransformer, expression, d);
        }

        public static <D> IrBody visitExpressionBody(IrTypeTransformer<? super D> irTypeTransformer, IrExpressionBody body, D d) {
            Intrinsics.checkNotNullParameter(body, "body");
            return IrElementTransformer.DefaultImpls.visitExpressionBody(irTypeTransformer, body, d);
        }

        public static <D> IrExternalPackageFragment visitExternalPackageFragment(IrTypeTransformer<? super D> irTypeTransformer, IrExternalPackageFragment declaration, D d) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            return IrElementTransformer.DefaultImpls.visitExternalPackageFragment(irTypeTransformer, declaration, d);
        }

        public static <D> IrExpression visitFieldAccess(IrTypeTransformer<? super D> irTypeTransformer, IrFieldAccessExpression expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return IrElementTransformer.DefaultImpls.visitFieldAccess(irTypeTransformer, expression, d);
        }

        public static <D> IrFile visitFile(IrTypeTransformer<? super D> irTypeTransformer, IrFile declaration, D d) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            return IrElementTransformer.DefaultImpls.visitFile(irTypeTransformer, declaration, d);
        }

        public static <D> IrElement visitFunctionAccess(IrTypeTransformer<? super D> irTypeTransformer, IrFunctionAccessExpression expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return IrElementTransformer.DefaultImpls.visitFunctionAccess(irTypeTransformer, expression, d);
        }

        public static <D> IrElement visitFunctionExpression(IrTypeTransformer<? super D> irTypeTransformer, IrFunctionExpression expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return IrElementTransformer.DefaultImpls.visitFunctionExpression(irTypeTransformer, expression, d);
        }

        public static <D> IrElement visitFunctionReference(IrTypeTransformer<? super D> irTypeTransformer, IrFunctionReference expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return IrElementTransformer.DefaultImpls.visitFunctionReference(irTypeTransformer, expression, d);
        }

        public static <D> IrExpression visitGetClass(IrTypeTransformer<? super D> irTypeTransformer, IrGetClass expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return IrElementTransformer.DefaultImpls.visitGetClass(irTypeTransformer, expression, d);
        }

        public static <D> IrExpression visitGetEnumValue(IrTypeTransformer<? super D> irTypeTransformer, IrGetEnumValue expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return IrElementTransformer.DefaultImpls.visitGetEnumValue(irTypeTransformer, expression, d);
        }

        public static <D> IrExpression visitGetField(IrTypeTransformer<? super D> irTypeTransformer, IrGetField expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return IrElementTransformer.DefaultImpls.visitGetField(irTypeTransformer, expression, d);
        }

        public static <D> IrExpression visitGetObjectValue(IrTypeTransformer<? super D> irTypeTransformer, IrGetObjectValue expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return IrElementTransformer.DefaultImpls.visitGetObjectValue(irTypeTransformer, expression, d);
        }

        public static <D> IrExpression visitGetValue(IrTypeTransformer<? super D> irTypeTransformer, IrGetValue expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return IrElementTransformer.DefaultImpls.visitGetValue(irTypeTransformer, expression, d);
        }

        public static <D> IrExpression visitInstanceInitializerCall(IrTypeTransformer<? super D> irTypeTransformer, IrInstanceInitializerCall expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return IrElementTransformer.DefaultImpls.visitInstanceInitializerCall(irTypeTransformer, expression, d);
        }

        public static <D> IrElement visitLocalDelegatedPropertyReference(IrTypeTransformer<? super D> irTypeTransformer, IrLocalDelegatedPropertyReference expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return IrElementTransformer.DefaultImpls.visitLocalDelegatedPropertyReference(irTypeTransformer, expression, d);
        }

        public static <D> IrExpression visitLoop(IrTypeTransformer<? super D> irTypeTransformer, IrLoop loop, D d) {
            Intrinsics.checkNotNullParameter(loop, "loop");
            return IrElementTransformer.DefaultImpls.visitLoop(irTypeTransformer, loop, d);
        }

        public static <D> IrModuleFragment visitModuleFragment(IrTypeTransformer<? super D> irTypeTransformer, IrModuleFragment declaration, D d) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            return IrElementTransformer.DefaultImpls.visitModuleFragment(irTypeTransformer, declaration, d);
        }

        public static <D> IrElement visitPackageFragment(IrTypeTransformer<? super D> irTypeTransformer, IrPackageFragment declaration, D d) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            return IrElementTransformer.DefaultImpls.visitPackageFragment(irTypeTransformer, declaration, d);
        }

        public static <D> IrStatement visitProperty(IrTypeTransformer<? super D> irTypeTransformer, IrProperty declaration, D d) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            return IrElementTransformer.DefaultImpls.visitProperty(irTypeTransformer, declaration, d);
        }

        public static <D> IrElement visitPropertyReference(IrTypeTransformer<? super D> irTypeTransformer, IrPropertyReference expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return IrElementTransformer.DefaultImpls.visitPropertyReference(irTypeTransformer, expression, d);
        }

        public static <D> IrExpression visitRawFunctionReference(IrTypeTransformer<? super D> irTypeTransformer, IrRawFunctionReference expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return IrElementTransformer.DefaultImpls.visitRawFunctionReference(irTypeTransformer, expression, d);
        }

        public static <D> IrExpression visitReturn(IrTypeTransformer<? super D> irTypeTransformer, IrReturn expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return IrElementTransformer.DefaultImpls.visitReturn(irTypeTransformer, expression, d);
        }

        public static <D> IrExpression visitSetField(IrTypeTransformer<? super D> irTypeTransformer, IrSetField expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return IrElementTransformer.DefaultImpls.visitSetField(irTypeTransformer, expression, d);
        }

        public static <D> IrExpression visitSetValue(IrTypeTransformer<? super D> irTypeTransformer, IrSetValue expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return IrElementTransformer.DefaultImpls.visitSetValue(irTypeTransformer, expression, d);
        }

        public static <D> IrStatement visitSimpleFunction(IrTypeTransformer<? super D> irTypeTransformer, IrSimpleFunction declaration, D d) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            return IrElementTransformer.DefaultImpls.visitSimpleFunction(irTypeTransformer, declaration, d);
        }

        public static <D> IrExpression visitSingletonReference(IrTypeTransformer<? super D> irTypeTransformer, IrGetSingletonValue expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return IrElementTransformer.DefaultImpls.visitSingletonReference(irTypeTransformer, expression, d);
        }

        public static <D> IrSpreadElement visitSpreadElement(IrTypeTransformer<? super D> irTypeTransformer, IrSpreadElement spread, D d) {
            Intrinsics.checkNotNullParameter(spread, "spread");
            return IrElementTransformer.DefaultImpls.visitSpreadElement(irTypeTransformer, spread, d);
        }

        public static <D> IrExpression visitStringConcatenation(IrTypeTransformer<? super D> irTypeTransformer, IrStringConcatenation expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return IrElementTransformer.DefaultImpls.visitStringConcatenation(irTypeTransformer, expression, d);
        }

        public static <D> IrExpression visitSuspendableExpression(IrTypeTransformer<? super D> irTypeTransformer, IrSuspendableExpression expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return IrElementTransformer.DefaultImpls.visitSuspendableExpression(irTypeTransformer, expression, d);
        }

        public static <D> IrExpression visitSuspensionPoint(IrTypeTransformer<? super D> irTypeTransformer, IrSuspensionPoint expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return IrElementTransformer.DefaultImpls.visitSuspensionPoint(irTypeTransformer, expression, d);
        }

        public static <D> IrBody visitSyntheticBody(IrTypeTransformer<? super D> irTypeTransformer, IrSyntheticBody body, D d) {
            Intrinsics.checkNotNullParameter(body, "body");
            return IrElementTransformer.DefaultImpls.visitSyntheticBody(irTypeTransformer, body, d);
        }

        public static <D> IrExpression visitThrow(IrTypeTransformer<? super D> irTypeTransformer, IrThrow expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return IrElementTransformer.DefaultImpls.visitThrow(irTypeTransformer, expression, d);
        }

        public static <D> IrExpression visitTry(IrTypeTransformer<? super D> irTypeTransformer, IrTry aTry, D d) {
            Intrinsics.checkNotNullParameter(aTry, "aTry");
            return IrElementTransformer.DefaultImpls.visitTry(irTypeTransformer, aTry, d);
        }

        public static <D> IrExpression visitValueAccess(IrTypeTransformer<? super D> irTypeTransformer, IrValueAccessExpression expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return IrElementTransformer.DefaultImpls.visitValueAccess(irTypeTransformer, expression, d);
        }

        public static <D> IrExpression visitWhen(IrTypeTransformer<? super D> irTypeTransformer, IrWhen expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return IrElementTransformer.DefaultImpls.visitWhen(irTypeTransformer, expression, d);
        }

        public static <D> IrExpression visitWhileLoop(IrTypeTransformer<? super D> irTypeTransformer, IrWhileLoop loop, D d) {
            Intrinsics.checkNotNullParameter(loop, "loop");
            return IrElementTransformer.DefaultImpls.visitWhileLoop(irTypeTransformer, loop, d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <D> IrStatement visitValueParameter(IrTypeTransformer<? super D> irTypeTransformer, IrValueParameter declaration, D d) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            IrValueParameter irValueParameter = declaration;
            declaration.setVarargElementType(irTypeTransformer.transformType(irValueParameter, declaration.getVarargElementType(), d));
            declaration.setType(irTypeTransformer.transformType(irValueParameter, declaration.getType(), d));
            return IrElementTransformer.DefaultImpls.visitValueParameter(irTypeTransformer, declaration, d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <D> IrStatement visitClass(final IrTypeTransformer<? super D> irTypeTransformer, final IrClass declaration, final D d) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            ValueClassRepresentation<IrSimpleType> valueClassRepresentation = declaration.getValueClassRepresentation();
            if (valueClassRepresentation != null) {
                valueClassRepresentation.mapUnderlyingType(new Function1<IrSimpleType, IrSimpleType>() { // from class: org.jetbrains.kotlin.ir.visitors.IrTypeTransformer.visitClass.1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    /* JADX WARN: Multi-variable type inference failed */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final IrSimpleType invoke(IrSimpleType it) {
                        Intrinsics.checkNotNullParameter(it, "it");
                        return (IrSimpleType) irTypeTransformer.transformType(declaration, it, d);
                    }
                });
            }
            List<IrType> superTypes = declaration.getSuperTypes();
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(superTypes, 10));
            Iterator<T> it = superTypes.iterator();
            while (it.hasNext()) {
                arrayList.add(irTypeTransformer.transformType(declaration, (IrType) it.next(), d));
            }
            declaration.setSuperTypes(arrayList);
            return IrElementTransformer.DefaultImpls.visitClass(irTypeTransformer, declaration, d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <D> IrStatement visitTypeParameter(IrTypeTransformer<? super D> irTypeTransformer, IrTypeParameter declaration, D d) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            List<IrType> superTypes = declaration.getSuperTypes();
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(superTypes, 10));
            Iterator<T> it = superTypes.iterator();
            while (it.hasNext()) {
                arrayList.add(irTypeTransformer.transformType(declaration, (IrType) it.next(), d));
            }
            declaration.setSuperTypes(arrayList);
            return IrElementTransformer.DefaultImpls.visitTypeParameter(irTypeTransformer, declaration, d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <D> IrStatement visitFunction(IrTypeTransformer<? super D> irTypeTransformer, IrFunction declaration, D d) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            declaration.setReturnType(irTypeTransformer.transformType(declaration, declaration.getReturnType(), d));
            return IrElementTransformer.DefaultImpls.visitFunction(irTypeTransformer, declaration, d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <D> IrStatement visitField(IrTypeTransformer<? super D> irTypeTransformer, IrField declaration, D d) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            declaration.setType(irTypeTransformer.transformType(declaration, declaration.getType(), d));
            return IrElementTransformer.DefaultImpls.visitField(irTypeTransformer, declaration, d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <D> IrStatement visitLocalDelegatedProperty(IrTypeTransformer<? super D> irTypeTransformer, IrLocalDelegatedProperty declaration, D d) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            declaration.setType(irTypeTransformer.transformType(declaration, declaration.getType(), d));
            return IrElementTransformer.DefaultImpls.visitLocalDelegatedProperty(irTypeTransformer, declaration, d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <D> IrStatement visitScript(IrTypeTransformer<? super D> irTypeTransformer, IrScript declaration, D d) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            declaration.setBaseClass(irTypeTransformer.transformType(declaration, declaration.getBaseClass(), d));
            return IrElementTransformer.DefaultImpls.visitScript(irTypeTransformer, declaration, d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <D> IrStatement visitTypeAlias(IrTypeTransformer<? super D> irTypeTransformer, IrTypeAlias declaration, D d) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            declaration.setExpandedType(irTypeTransformer.transformType(declaration, declaration.getExpandedType(), d));
            return IrElementTransformer.DefaultImpls.visitTypeAlias(irTypeTransformer, declaration, d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <D> IrStatement visitVariable(IrTypeTransformer<? super D> irTypeTransformer, IrVariable declaration, D d) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            declaration.setType(irTypeTransformer.transformType(declaration, declaration.getType(), d));
            return IrElementTransformer.DefaultImpls.visitVariable(irTypeTransformer, declaration, d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <D> IrExpression visitExpression(IrTypeTransformer<? super D> irTypeTransformer, IrExpression expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            expression.setType(irTypeTransformer.transformType(expression, expression.getType(), d));
            return IrElementTransformer.DefaultImpls.visitExpression(irTypeTransformer, expression, d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <D> IrElement visitMemberAccess(IrTypeTransformer<? super D> irTypeTransformer, IrMemberAccessExpression<?> expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            Iterator<Integer> it = RangesKt.until(0, expression.getTypeArgumentsCount()).iterator();
            while (it.hasNext()) {
                int iNextInt = ((IntIterator) it).nextInt();
                IrType typeArgument = expression.getTypeArgument(iNextInt);
                if (typeArgument != null) {
                    expression.putTypeArgument(iNextInt, irTypeTransformer.transformType(expression, typeArgument, d));
                }
            }
            return IrElementTransformer.DefaultImpls.visitMemberAccess(irTypeTransformer, expression, d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <D> IrExpression visitClassReference(IrTypeTransformer<? super D> irTypeTransformer, IrClassReference expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            expression.setClassType(irTypeTransformer.transformType(expression, expression.getClassType(), d));
            return IrElementTransformer.DefaultImpls.visitClassReference(irTypeTransformer, expression, d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <D> IrConstantValue visitConstantObject(IrTypeTransformer<? super D> irTypeTransformer, IrConstantObject expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            int size = expression.getTypeArguments().size();
            for (int i = 0; i < size; i++) {
                expression.getTypeArguments().set(i, irTypeTransformer.transformType(expression, expression.getTypeArguments().get(i), d));
            }
            return IrElementTransformer.DefaultImpls.visitConstantObject(irTypeTransformer, expression, d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <D> IrExpression visitTypeOperator(IrTypeTransformer<? super D> irTypeTransformer, IrTypeOperatorCall expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            expression.setTypeOperand(irTypeTransformer.transformType(expression, expression.getTypeOperand(), d));
            return IrElementTransformer.DefaultImpls.visitTypeOperator(irTypeTransformer, expression, d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <D> IrExpression visitVararg(IrTypeTransformer<? super D> irTypeTransformer, IrVararg expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            expression.setVarargElementType(irTypeTransformer.transformType(expression, expression.getVarargElementType(), d));
            return IrElementTransformer.DefaultImpls.visitVararg(irTypeTransformer, expression, d);
        }
    }
}
