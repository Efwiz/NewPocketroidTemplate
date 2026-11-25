package org.jetbrains.kotlin.backend.common.serialization.mangle.ir;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.serialization.mangle.KotlinExportChecker;
import org.jetbrains.kotlin.backend.common.serialization.mangle.MangleUtilsKt;
import org.jetbrains.kotlin.backend.common.serialization.mangle.SpecialDeclarationType;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.declarations.IrAnonymousInitializer;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationBase;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationWithVisibility;
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
import org.jetbrains.kotlin.ir.symbols.IrPropertySymbol;
import org.jetbrains.kotlin.ir.util.AdditionalIrUtilsKt;
import org.jetbrains.kotlin.ir.util.DumpIrTreeOptions;
import org.jetbrains.kotlin.ir.util.RenderIrElementKt;
import org.jetbrains.kotlin.ir.visitors.IrElementVisitor;
import org.jetbrains.kotlin.name.SpecialNames;

/* JADX INFO: compiled from: IrExportCheckerVisitor.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\b&\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0002\u000f\u0010B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\u0018\u0010\n\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u00022\u0006\u0010\f\u001a\u00020\rH\u0016J\f\u0010\u000e\u001a\u00020\u0004*\u00020\u0002H&R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0012\u0010\b\u001a\u00060\tR\u00020\u0000X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/serialization/mangle/ir/IrExportCheckerVisitor;", "Lorg/jetbrains/kotlin/backend/common/serialization/mangle/KotlinExportChecker;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "compatibleMode", "", "(Z)V", "checker", "Lorg/jetbrains/kotlin/backend/common/serialization/mangle/ir/IrExportCheckerVisitor$Checker;", "compatibleChecker", "Lorg/jetbrains/kotlin/backend/common/serialization/mangle/ir/IrExportCheckerVisitor$CompatibleChecker;", "check", "declaration", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/backend/common/serialization/mangle/SpecialDeclarationType;", "isPlatformSpecificExported", "Checker", "CompatibleChecker", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class IrExportCheckerVisitor implements KotlinExportChecker<IrDeclaration> {
    private final boolean compatibleMode;
    private final CompatibleChecker compatibleChecker = new CompatibleChecker();
    private final Checker checker = new Checker();

    public abstract boolean isPlatformSpecificExported(IrDeclaration irDeclaration);

    public IrExportCheckerVisitor(boolean z) {
        this.compatibleMode = z;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.mangle.KotlinExportChecker
    public boolean check(IrDeclaration declaration, SpecialDeclarationType type) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(type, "type");
        return ((Boolean) declaration.accept(this.compatibleMode ? this.compatibleChecker : this.checker, null)).booleanValue();
    }

    /* JADX INFO: compiled from: IrExportCheckerVisitor.kt */
    @Metadata(d1 = {"\u0000d\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\u0010\u0001\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0002\u0018\u00002\u0010\u0012\u0004\u0012\u00020\u0002\u0012\u0006\u0012\u0004\u0018\u00010\u00030\u0001B\u0005¢\u0006\u0002\u0010\u0004J\u001f\u0010\u0005\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u00072\b\u0010\b\u001a\u0004\u0018\u00010\u0003H\u0016¢\u0006\u0002\u0010\tJ\u001f\u0010\n\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u000b2\b\u0010\b\u001a\u0004\u0018\u00010\u0003H\u0016¢\u0006\u0002\u0010\fJ\u001f\u0010\r\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u000e2\b\u0010\b\u001a\u0004\u0018\u00010\u0003H\u0016¢\u0006\u0002\u0010\u000fJ\u001f\u0010\u0010\u001a\u00020\u00022\u0006\u0010\u0011\u001a\u00020\u00122\b\u0010\b\u001a\u0004\u0018\u00010\u0003H\u0016¢\u0006\u0002\u0010\u0013J\u001f\u0010\u0014\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u00152\b\u0010\b\u001a\u0004\u0018\u00010\u0003H\u0016¢\u0006\u0002\u0010\u0016J\u001f\u0010\u0017\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u00182\b\u0010\b\u001a\u0004\u0018\u00010\u0003H\u0016¢\u0006\u0002\u0010\u0019J\u001f\u0010\u001a\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u001b2\b\u0010\b\u001a\u0004\u0018\u00010\u0003H\u0016¢\u0006\u0002\u0010\u001cJ\u001f\u0010\u001d\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u001e2\b\u0010\b\u001a\u0004\u0018\u00010\u0003H\u0016¢\u0006\u0002\u0010\u001fJ\u001f\u0010 \u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020!2\b\u0010\b\u001a\u0004\u0018\u00010\u0003H\u0016¢\u0006\u0002\u0010\"J\u001f\u0010#\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020$2\b\u0010\b\u001a\u0004\u0018\u00010\u0003H\u0016¢\u0006\u0002\u0010%¨\u0006&"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/serialization/mangle/ir/IrExportCheckerVisitor$Checker;", "Lorg/jetbrains/kotlin/ir/visitors/IrElementVisitor;", "", "", "()V", "visitAnonymousInitializer", "declaration", "Lorg/jetbrains/kotlin/ir/declarations/IrAnonymousInitializer;", "data", "(Lorg/jetbrains/kotlin/ir/declarations/IrAnonymousInitializer;Ljava/lang/Void;)Ljava/lang/Boolean;", "visitClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "(Lorg/jetbrains/kotlin/ir/declarations/IrClass;Ljava/lang/Void;)Ljava/lang/Boolean;", "visitDeclaration", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationBase;", "(Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationBase;Ljava/lang/Void;)Ljava/lang/Boolean;", "visitElement", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/ir/IrElement;", "(Lorg/jetbrains/kotlin/ir/IrElement;Ljava/lang/Void;)Ljava/lang/Boolean;", "visitErrorDeclaration", "Lorg/jetbrains/kotlin/ir/declarations/IrErrorDeclaration;", "(Lorg/jetbrains/kotlin/ir/declarations/IrErrorDeclaration;Ljava/lang/Void;)Ljava/lang/Boolean;", "visitLocalDelegatedProperty", "Lorg/jetbrains/kotlin/ir/declarations/IrLocalDelegatedProperty;", "(Lorg/jetbrains/kotlin/ir/declarations/IrLocalDelegatedProperty;Ljava/lang/Void;)Ljava/lang/Boolean;", "visitPackageFragment", "Lorg/jetbrains/kotlin/ir/declarations/IrPackageFragment;", "(Lorg/jetbrains/kotlin/ir/declarations/IrPackageFragment;Ljava/lang/Void;)Ljava/lang/Boolean;", "visitSimpleFunction", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "(Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;Ljava/lang/Void;)Ljava/lang/Boolean;", "visitValueParameter", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "(Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;Ljava/lang/Void;)Ljava/lang/Boolean;", "visitVariable", "Lorg/jetbrains/kotlin/ir/declarations/IrVariable;", "(Lorg/jetbrains/kotlin/ir/declarations/IrVariable;Ljava/lang/Void;)Ljava/lang/Boolean;", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class Checker implements IrElementVisitor {
        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitBlock2(IrBlock irBlock, Void r2) {
            return (Boolean) IrElementVisitor.DefaultImpls.visitBlock(this, irBlock, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitBlockBody2(IrBlockBody irBlockBody, Void r2) {
            return (Boolean) IrElementVisitor.DefaultImpls.visitBlockBody(this, irBlockBody, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitBody2(IrBody irBody, Void r2) {
            return (Boolean) IrElementVisitor.DefaultImpls.visitBody(this, irBody, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitBranch2(IrBranch irBranch, Void r2) {
            return (Boolean) IrElementVisitor.DefaultImpls.visitBranch(this, irBranch, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitBreak2(IrBreak irBreak, Void r2) {
            return (Boolean) IrElementVisitor.DefaultImpls.visitBreak(this, irBreak, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitBreakContinue2(IrBreakContinue irBreakContinue, Void r2) {
            return (Boolean) IrElementVisitor.DefaultImpls.visitBreakContinue(this, irBreakContinue, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitCall2(IrCall irCall, Void r2) {
            return (Boolean) IrElementVisitor.DefaultImpls.visitCall(this, irCall, r2);
        }

        public Boolean visitCallableReference(IrCallableReference<?> irCallableReference, Void r2) {
            return (Boolean) IrElementVisitor.DefaultImpls.visitCallableReference(this, irCallableReference, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public /* bridge */ /* synthetic */ Object visitCallableReference(IrCallableReference irCallableReference, Object obj) {
            return visitCallableReference((IrCallableReference<?>) irCallableReference, (Void) obj);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitCatch2(IrCatch irCatch, Void r2) {
            return (Boolean) IrElementVisitor.DefaultImpls.visitCatch(this, irCatch, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitClassReference2(IrClassReference irClassReference, Void r2) {
            return (Boolean) IrElementVisitor.DefaultImpls.visitClassReference(this, irClassReference, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitComposite2(IrComposite irComposite, Void r2) {
            return (Boolean) IrElementVisitor.DefaultImpls.visitComposite(this, irComposite, r2);
        }

        public Boolean visitConst(IrConst<?> irConst, Void r2) {
            return (Boolean) IrElementVisitor.DefaultImpls.visitConst(this, irConst, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public /* bridge */ /* synthetic */ Object visitConst(IrConst irConst, Object obj) {
            return visitConst((IrConst<?>) irConst, (Void) obj);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitConstantArray2(IrConstantArray irConstantArray, Void r2) {
            return (Boolean) IrElementVisitor.DefaultImpls.visitConstantArray(this, irConstantArray, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitConstantObject2(IrConstantObject irConstantObject, Void r2) {
            return (Boolean) IrElementVisitor.DefaultImpls.visitConstantObject(this, irConstantObject, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitConstantPrimitive2(IrConstantPrimitive irConstantPrimitive, Void r2) {
            return (Boolean) IrElementVisitor.DefaultImpls.visitConstantPrimitive(this, irConstantPrimitive, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitConstantValue2(IrConstantValue irConstantValue, Void r2) {
            return (Boolean) IrElementVisitor.DefaultImpls.visitConstantValue(this, irConstantValue, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitConstructor2(IrConstructor irConstructor, Void r2) {
            return (Boolean) IrElementVisitor.DefaultImpls.visitConstructor(this, irConstructor, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitConstructorCall2(IrConstructorCall irConstructorCall, Void r2) {
            return (Boolean) IrElementVisitor.DefaultImpls.visitConstructorCall(this, irConstructorCall, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitContainerExpression2(IrContainerExpression irContainerExpression, Void r2) {
            return (Boolean) IrElementVisitor.DefaultImpls.visitContainerExpression(this, irContainerExpression, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitContinue2(IrContinue irContinue, Void r2) {
            return (Boolean) IrElementVisitor.DefaultImpls.visitContinue(this, irContinue, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitDeclarationReference2(IrDeclarationReference irDeclarationReference, Void r2) {
            return (Boolean) IrElementVisitor.DefaultImpls.visitDeclarationReference(this, irDeclarationReference, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitDelegatingConstructorCall2(IrDelegatingConstructorCall irDelegatingConstructorCall, Void r2) {
            return (Boolean) IrElementVisitor.DefaultImpls.visitDelegatingConstructorCall(this, irDelegatingConstructorCall, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitDoWhileLoop2(IrDoWhileLoop irDoWhileLoop, Void r2) {
            return (Boolean) IrElementVisitor.DefaultImpls.visitDoWhileLoop(this, irDoWhileLoop, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitDynamicExpression2(IrDynamicExpression irDynamicExpression, Void r2) {
            return (Boolean) IrElementVisitor.DefaultImpls.visitDynamicExpression(this, irDynamicExpression, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitDynamicMemberExpression2(IrDynamicMemberExpression irDynamicMemberExpression, Void r2) {
            return (Boolean) IrElementVisitor.DefaultImpls.visitDynamicMemberExpression(this, irDynamicMemberExpression, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitDynamicOperatorExpression2(IrDynamicOperatorExpression irDynamicOperatorExpression, Void r2) {
            return (Boolean) IrElementVisitor.DefaultImpls.visitDynamicOperatorExpression(this, irDynamicOperatorExpression, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitElseBranch2(IrElseBranch irElseBranch, Void r2) {
            return (Boolean) IrElementVisitor.DefaultImpls.visitElseBranch(this, irElseBranch, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitEnumConstructorCall2(IrEnumConstructorCall irEnumConstructorCall, Void r2) {
            return (Boolean) IrElementVisitor.DefaultImpls.visitEnumConstructorCall(this, irEnumConstructorCall, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitEnumEntry2(IrEnumEntry irEnumEntry, Void r2) {
            return (Boolean) IrElementVisitor.DefaultImpls.visitEnumEntry(this, irEnumEntry, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitErrorCallExpression2(IrErrorCallExpression irErrorCallExpression, Void r2) {
            return (Boolean) IrElementVisitor.DefaultImpls.visitErrorCallExpression(this, irErrorCallExpression, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitErrorExpression2(IrErrorExpression irErrorExpression, Void r2) {
            return (Boolean) IrElementVisitor.DefaultImpls.visitErrorExpression(this, irErrorExpression, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitExpression2(IrExpression irExpression, Void r2) {
            return (Boolean) IrElementVisitor.DefaultImpls.visitExpression(this, irExpression, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitExpressionBody2(IrExpressionBody irExpressionBody, Void r2) {
            return (Boolean) IrElementVisitor.DefaultImpls.visitExpressionBody(this, irExpressionBody, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitExternalPackageFragment2(IrExternalPackageFragment irExternalPackageFragment, Void r2) {
            return (Boolean) IrElementVisitor.DefaultImpls.visitExternalPackageFragment(this, irExternalPackageFragment, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitField2(IrField irField, Void r2) {
            return (Boolean) IrElementVisitor.DefaultImpls.visitField(this, irField, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitFieldAccess2(IrFieldAccessExpression irFieldAccessExpression, Void r2) {
            return (Boolean) IrElementVisitor.DefaultImpls.visitFieldAccess(this, irFieldAccessExpression, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitFile2(IrFile irFile, Void r2) {
            return (Boolean) IrElementVisitor.DefaultImpls.visitFile(this, irFile, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitFunction2(IrFunction irFunction, Void r2) {
            return (Boolean) IrElementVisitor.DefaultImpls.visitFunction(this, irFunction, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitFunctionAccess2(IrFunctionAccessExpression irFunctionAccessExpression, Void r2) {
            return (Boolean) IrElementVisitor.DefaultImpls.visitFunctionAccess(this, irFunctionAccessExpression, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitFunctionExpression2(IrFunctionExpression irFunctionExpression, Void r2) {
            return (Boolean) IrElementVisitor.DefaultImpls.visitFunctionExpression(this, irFunctionExpression, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitFunctionReference2(IrFunctionReference irFunctionReference, Void r2) {
            return (Boolean) IrElementVisitor.DefaultImpls.visitFunctionReference(this, irFunctionReference, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitGetClass2(IrGetClass irGetClass, Void r2) {
            return (Boolean) IrElementVisitor.DefaultImpls.visitGetClass(this, irGetClass, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitGetEnumValue2(IrGetEnumValue irGetEnumValue, Void r2) {
            return (Boolean) IrElementVisitor.DefaultImpls.visitGetEnumValue(this, irGetEnumValue, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitGetField2(IrGetField irGetField, Void r2) {
            return (Boolean) IrElementVisitor.DefaultImpls.visitGetField(this, irGetField, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitGetObjectValue2(IrGetObjectValue irGetObjectValue, Void r2) {
            return (Boolean) IrElementVisitor.DefaultImpls.visitGetObjectValue(this, irGetObjectValue, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitGetValue2(IrGetValue irGetValue, Void r2) {
            return (Boolean) IrElementVisitor.DefaultImpls.visitGetValue(this, irGetValue, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitInstanceInitializerCall2(IrInstanceInitializerCall irInstanceInitializerCall, Void r2) {
            return (Boolean) IrElementVisitor.DefaultImpls.visitInstanceInitializerCall(this, irInstanceInitializerCall, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitLocalDelegatedPropertyReference2(IrLocalDelegatedPropertyReference irLocalDelegatedPropertyReference, Void r2) {
            return (Boolean) IrElementVisitor.DefaultImpls.visitLocalDelegatedPropertyReference(this, irLocalDelegatedPropertyReference, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitLoop2(IrLoop irLoop, Void r2) {
            return (Boolean) IrElementVisitor.DefaultImpls.visitLoop(this, irLoop, r2);
        }

        public Boolean visitMemberAccess(IrMemberAccessExpression<?> irMemberAccessExpression, Void r2) {
            return (Boolean) IrElementVisitor.DefaultImpls.visitMemberAccess(this, irMemberAccessExpression, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public /* bridge */ /* synthetic */ Object visitMemberAccess(IrMemberAccessExpression irMemberAccessExpression, Object obj) {
            return visitMemberAccess((IrMemberAccessExpression<?>) irMemberAccessExpression, (Void) obj);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitModuleFragment2(IrModuleFragment irModuleFragment, Void r2) {
            return (Boolean) IrElementVisitor.DefaultImpls.visitModuleFragment(this, irModuleFragment, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitProperty2(IrProperty irProperty, Void r2) {
            return (Boolean) IrElementVisitor.DefaultImpls.visitProperty(this, irProperty, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitPropertyReference2(IrPropertyReference irPropertyReference, Void r2) {
            return (Boolean) IrElementVisitor.DefaultImpls.visitPropertyReference(this, irPropertyReference, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitRawFunctionReference2(IrRawFunctionReference irRawFunctionReference, Void r2) {
            return (Boolean) IrElementVisitor.DefaultImpls.visitRawFunctionReference(this, irRawFunctionReference, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitReturn2(IrReturn irReturn, Void r2) {
            return (Boolean) IrElementVisitor.DefaultImpls.visitReturn(this, irReturn, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitScript2(IrScript irScript, Void r2) {
            return (Boolean) IrElementVisitor.DefaultImpls.visitScript(this, irScript, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitSetField2(IrSetField irSetField, Void r2) {
            return (Boolean) IrElementVisitor.DefaultImpls.visitSetField(this, irSetField, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitSetValue2(IrSetValue irSetValue, Void r2) {
            return (Boolean) IrElementVisitor.DefaultImpls.visitSetValue(this, irSetValue, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitSingletonReference2(IrGetSingletonValue irGetSingletonValue, Void r2) {
            return (Boolean) IrElementVisitor.DefaultImpls.visitSingletonReference(this, irGetSingletonValue, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitSpreadElement2(IrSpreadElement irSpreadElement, Void r2) {
            return (Boolean) IrElementVisitor.DefaultImpls.visitSpreadElement(this, irSpreadElement, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitStringConcatenation2(IrStringConcatenation irStringConcatenation, Void r2) {
            return (Boolean) IrElementVisitor.DefaultImpls.visitStringConcatenation(this, irStringConcatenation, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitSuspendableExpression2(IrSuspendableExpression irSuspendableExpression, Void r2) {
            return (Boolean) IrElementVisitor.DefaultImpls.visitSuspendableExpression(this, irSuspendableExpression, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitSuspensionPoint2(IrSuspensionPoint irSuspensionPoint, Void r2) {
            return (Boolean) IrElementVisitor.DefaultImpls.visitSuspensionPoint(this, irSuspensionPoint, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitSyntheticBody2(IrSyntheticBody irSyntheticBody, Void r2) {
            return (Boolean) IrElementVisitor.DefaultImpls.visitSyntheticBody(this, irSyntheticBody, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitThrow2(IrThrow irThrow, Void r2) {
            return (Boolean) IrElementVisitor.DefaultImpls.visitThrow(this, irThrow, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitTry2(IrTry irTry, Void r2) {
            return (Boolean) IrElementVisitor.DefaultImpls.visitTry(this, irTry, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitTypeAlias2(IrTypeAlias irTypeAlias, Void r2) {
            return (Boolean) IrElementVisitor.DefaultImpls.visitTypeAlias(this, irTypeAlias, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitTypeOperator2(IrTypeOperatorCall irTypeOperatorCall, Void r2) {
            return (Boolean) IrElementVisitor.DefaultImpls.visitTypeOperator(this, irTypeOperatorCall, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitTypeParameter2(IrTypeParameter irTypeParameter, Void r2) {
            return (Boolean) IrElementVisitor.DefaultImpls.visitTypeParameter(this, irTypeParameter, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitValueAccess2(IrValueAccessExpression irValueAccessExpression, Void r2) {
            return (Boolean) IrElementVisitor.DefaultImpls.visitValueAccess(this, irValueAccessExpression, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitVararg2(IrVararg irVararg, Void r2) {
            return (Boolean) IrElementVisitor.DefaultImpls.visitVararg(this, irVararg, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitWhen2(IrWhen irWhen, Void r2) {
            return (Boolean) IrElementVisitor.DefaultImpls.visitWhen(this, irWhen, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitWhileLoop2(IrWhileLoop irWhileLoop, Void r2) {
            return (Boolean) IrElementVisitor.DefaultImpls.visitWhileLoop(this, irWhileLoop, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitElement2(IrElement element, Void data) {
            Intrinsics.checkNotNullParameter(element, "element");
            throw new IllegalStateException(("Should bot reach here " + RenderIrElementKt.render$default(element, (DumpIrTreeOptions) null, 1, (Object) null)).toString());
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitDeclaration2(IrDeclarationBase declaration, Void data) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            IrDeclarationWithVisibility irDeclarationWithVisibility = declaration instanceof IrDeclarationWithVisibility ? (IrDeclarationWithVisibility) declaration : null;
            if (Intrinsics.areEqual(irDeclarationWithVisibility != null ? irDeclarationWithVisibility.getVisibility() : null, DescriptorVisibilities.LOCAL)) {
                return false;
            }
            return (Boolean) declaration.getParent().accept(this, data);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitClass2(IrClass declaration, Void data) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            if (IrExportCheckerVisitorKt.isAnonymous(declaration.getName())) {
                return false;
            }
            return (Boolean) IrElementVisitor.DefaultImpls.visitClass(this, declaration, data);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitSimpleFunction2(IrSimpleFunction declaration, Void data) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            if (IrExportCheckerVisitorKt.isAnonymous(declaration.getName())) {
                return false;
            }
            return (Boolean) IrElementVisitor.DefaultImpls.visitSimpleFunction(this, declaration, data);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitPackageFragment2(IrPackageFragment declaration, Void data) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            return true;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitValueParameter2(IrValueParameter declaration, Void data) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            return false;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitVariable2(IrVariable declaration, Void data) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            return false;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitAnonymousInitializer2(IrAnonymousInitializer declaration, Void data) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            return false;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitLocalDelegatedProperty2(IrLocalDelegatedProperty declaration, Void data) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            return false;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitErrorDeclaration2(IrErrorDeclaration declaration, Void data) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            return false;
        }
    }

    /* JADX INFO: compiled from: IrExportCheckerVisitor.kt */
    @Metadata(d1 = {"\u0000¤\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\u0010\u0001\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0082\u0004\u0018\u00002\u0010\u0012\u0004\u0012\u00020\u0002\u0012\u0006\u0012\u0004\u0018\u00010\u00030\u0001B\u0005¢\u0006\u0002\u0010\u0004J\u001f\u0010\u0005\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u00072\b\u0010\b\u001a\u0004\u0018\u00010\u0003H\u0016¢\u0006\u0002\u0010\tJ\u001f\u0010\n\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u000b2\b\u0010\b\u001a\u0004\u0018\u00010\u0003H\u0016¢\u0006\u0002\u0010\fJ\u001f\u0010\r\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u000e2\b\u0010\b\u001a\u0004\u0018\u00010\u0003H\u0016¢\u0006\u0002\u0010\u000fJ\u001f\u0010\u0010\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u00112\b\u0010\b\u001a\u0004\u0018\u00010\u0003H\u0016¢\u0006\u0002\u0010\u0012J\u001f\u0010\u0013\u001a\u00020\u00022\u0006\u0010\u0014\u001a\u00020\u00152\b\u0010\b\u001a\u0004\u0018\u00010\u0003H\u0016¢\u0006\u0002\u0010\u0016J\u001f\u0010\u0017\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u00182\b\u0010\b\u001a\u0004\u0018\u00010\u0003H\u0016¢\u0006\u0002\u0010\u0019J\u001f\u0010\u001a\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u001b2\b\u0010\b\u001a\u0004\u0018\u00010\u0003H\u0016¢\u0006\u0002\u0010\u001cJ\u001f\u0010\u001d\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u001e2\b\u0010\b\u001a\u0004\u0018\u00010\u0003H\u0016¢\u0006\u0002\u0010\u001fJ\u001f\u0010 \u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020!2\b\u0010\b\u001a\u0004\u0018\u00010\u0003H\u0016¢\u0006\u0002\u0010\"J\u001f\u0010#\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020$2\b\u0010\b\u001a\u0004\u0018\u00010\u0003H\u0016¢\u0006\u0002\u0010%J\u001f\u0010&\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020'2\b\u0010\b\u001a\u0004\u0018\u00010\u0003H\u0016¢\u0006\u0002\u0010(J\u001f\u0010)\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020*2\b\u0010\b\u001a\u0004\u0018\u00010\u0003H\u0016¢\u0006\u0002\u0010+J\u001f\u0010,\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020-2\b\u0010\b\u001a\u0004\u0018\u00010\u0003H\u0016¢\u0006\u0002\u0010.J\u001f\u0010/\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u0002002\b\u0010\b\u001a\u0004\u0018\u00010\u0003H\u0016¢\u0006\u0002\u00101J\u001f\u00102\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u0002032\b\u0010\b\u001a\u0004\u0018\u00010\u0003H\u0016¢\u0006\u0002\u00104J$\u00105\u001a\u00020\u0002*\u0002062\f\u00107\u001a\b\u0012\u0004\u0012\u000209082\b\u0010:\u001a\u0004\u0018\u00010;H\u0002J\f\u0010<\u001a\u00020\u0002*\u00020;H\u0002¨\u0006="}, d2 = {"Lorg/jetbrains/kotlin/backend/common/serialization/mangle/ir/IrExportCheckerVisitor$CompatibleChecker;", "Lorg/jetbrains/kotlin/ir/visitors/IrElementVisitor;", "", "", "(Lorg/jetbrains/kotlin/backend/common/serialization/mangle/ir/IrExportCheckerVisitor;)V", "visitAnonymousInitializer", "declaration", "Lorg/jetbrains/kotlin/ir/declarations/IrAnonymousInitializer;", "data", "(Lorg/jetbrains/kotlin/ir/declarations/IrAnonymousInitializer;Ljava/lang/Void;)Ljava/lang/Boolean;", "visitClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "(Lorg/jetbrains/kotlin/ir/declarations/IrClass;Ljava/lang/Void;)Ljava/lang/Boolean;", "visitConstructor", "Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "(Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;Ljava/lang/Void;)Ljava/lang/Boolean;", "visitDeclaration", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationBase;", "(Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationBase;Ljava/lang/Void;)Ljava/lang/Boolean;", "visitElement", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/ir/IrElement;", "(Lorg/jetbrains/kotlin/ir/IrElement;Ljava/lang/Void;)Ljava/lang/Boolean;", "visitErrorDeclaration", "Lorg/jetbrains/kotlin/ir/declarations/IrErrorDeclaration;", "(Lorg/jetbrains/kotlin/ir/declarations/IrErrorDeclaration;Ljava/lang/Void;)Ljava/lang/Boolean;", "visitField", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "(Lorg/jetbrains/kotlin/ir/declarations/IrField;Ljava/lang/Void;)Ljava/lang/Boolean;", "visitLocalDelegatedProperty", "Lorg/jetbrains/kotlin/ir/declarations/IrLocalDelegatedProperty;", "(Lorg/jetbrains/kotlin/ir/declarations/IrLocalDelegatedProperty;Ljava/lang/Void;)Ljava/lang/Boolean;", "visitPackageFragment", "Lorg/jetbrains/kotlin/ir/declarations/IrPackageFragment;", "(Lorg/jetbrains/kotlin/ir/declarations/IrPackageFragment;Ljava/lang/Void;)Ljava/lang/Boolean;", "visitProperty", "Lorg/jetbrains/kotlin/ir/declarations/IrProperty;", "(Lorg/jetbrains/kotlin/ir/declarations/IrProperty;Ljava/lang/Void;)Ljava/lang/Boolean;", "visitSimpleFunction", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "(Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;Ljava/lang/Void;)Ljava/lang/Boolean;", "visitTypeAlias", "Lorg/jetbrains/kotlin/ir/declarations/IrTypeAlias;", "(Lorg/jetbrains/kotlin/ir/declarations/IrTypeAlias;Ljava/lang/Void;)Ljava/lang/Boolean;", "visitTypeParameter", "Lorg/jetbrains/kotlin/ir/declarations/IrTypeParameter;", "(Lorg/jetbrains/kotlin/ir/declarations/IrTypeParameter;Ljava/lang/Void;)Ljava/lang/Boolean;", "visitValueParameter", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "(Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;Ljava/lang/Void;)Ljava/lang/Boolean;", "visitVariable", "Lorg/jetbrains/kotlin/ir/declarations/IrVariable;", "(Lorg/jetbrains/kotlin/ir/declarations/IrVariable;Ljava/lang/Void;)Ljava/lang/Boolean;", "isExported", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "annotations", "", "Lorg/jetbrains/kotlin/ir/expressions/IrConstructorCall;", "visibility", "Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;", "isPubliclyVisible", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private final class CompatibleChecker implements IrElementVisitor {
        public CompatibleChecker() {
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitBlock2(IrBlock irBlock, Void r2) {
            return (Boolean) IrElementVisitor.DefaultImpls.visitBlock(this, irBlock, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitBlockBody2(IrBlockBody irBlockBody, Void r2) {
            return (Boolean) IrElementVisitor.DefaultImpls.visitBlockBody(this, irBlockBody, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitBody2(IrBody irBody, Void r2) {
            return (Boolean) IrElementVisitor.DefaultImpls.visitBody(this, irBody, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitBranch2(IrBranch irBranch, Void r2) {
            return (Boolean) IrElementVisitor.DefaultImpls.visitBranch(this, irBranch, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitBreak2(IrBreak irBreak, Void r2) {
            return (Boolean) IrElementVisitor.DefaultImpls.visitBreak(this, irBreak, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitBreakContinue2(IrBreakContinue irBreakContinue, Void r2) {
            return (Boolean) IrElementVisitor.DefaultImpls.visitBreakContinue(this, irBreakContinue, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitCall2(IrCall irCall, Void r2) {
            return (Boolean) IrElementVisitor.DefaultImpls.visitCall(this, irCall, r2);
        }

        public Boolean visitCallableReference(IrCallableReference<?> irCallableReference, Void r2) {
            return (Boolean) IrElementVisitor.DefaultImpls.visitCallableReference(this, irCallableReference, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public /* bridge */ /* synthetic */ Object visitCallableReference(IrCallableReference irCallableReference, Object obj) {
            return visitCallableReference((IrCallableReference<?>) irCallableReference, (Void) obj);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitCatch2(IrCatch irCatch, Void r2) {
            return (Boolean) IrElementVisitor.DefaultImpls.visitCatch(this, irCatch, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitClassReference2(IrClassReference irClassReference, Void r2) {
            return (Boolean) IrElementVisitor.DefaultImpls.visitClassReference(this, irClassReference, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitComposite2(IrComposite irComposite, Void r2) {
            return (Boolean) IrElementVisitor.DefaultImpls.visitComposite(this, irComposite, r2);
        }

        public Boolean visitConst(IrConst<?> irConst, Void r2) {
            return (Boolean) IrElementVisitor.DefaultImpls.visitConst(this, irConst, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public /* bridge */ /* synthetic */ Object visitConst(IrConst irConst, Object obj) {
            return visitConst((IrConst<?>) irConst, (Void) obj);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitConstantArray2(IrConstantArray irConstantArray, Void r2) {
            return (Boolean) IrElementVisitor.DefaultImpls.visitConstantArray(this, irConstantArray, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitConstantObject2(IrConstantObject irConstantObject, Void r2) {
            return (Boolean) IrElementVisitor.DefaultImpls.visitConstantObject(this, irConstantObject, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitConstantPrimitive2(IrConstantPrimitive irConstantPrimitive, Void r2) {
            return (Boolean) IrElementVisitor.DefaultImpls.visitConstantPrimitive(this, irConstantPrimitive, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitConstantValue2(IrConstantValue irConstantValue, Void r2) {
            return (Boolean) IrElementVisitor.DefaultImpls.visitConstantValue(this, irConstantValue, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitConstructorCall2(IrConstructorCall irConstructorCall, Void r2) {
            return (Boolean) IrElementVisitor.DefaultImpls.visitConstructorCall(this, irConstructorCall, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitContainerExpression2(IrContainerExpression irContainerExpression, Void r2) {
            return (Boolean) IrElementVisitor.DefaultImpls.visitContainerExpression(this, irContainerExpression, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitContinue2(IrContinue irContinue, Void r2) {
            return (Boolean) IrElementVisitor.DefaultImpls.visitContinue(this, irContinue, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitDeclarationReference2(IrDeclarationReference irDeclarationReference, Void r2) {
            return (Boolean) IrElementVisitor.DefaultImpls.visitDeclarationReference(this, irDeclarationReference, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitDelegatingConstructorCall2(IrDelegatingConstructorCall irDelegatingConstructorCall, Void r2) {
            return (Boolean) IrElementVisitor.DefaultImpls.visitDelegatingConstructorCall(this, irDelegatingConstructorCall, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitDoWhileLoop2(IrDoWhileLoop irDoWhileLoop, Void r2) {
            return (Boolean) IrElementVisitor.DefaultImpls.visitDoWhileLoop(this, irDoWhileLoop, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitDynamicExpression2(IrDynamicExpression irDynamicExpression, Void r2) {
            return (Boolean) IrElementVisitor.DefaultImpls.visitDynamicExpression(this, irDynamicExpression, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitDynamicMemberExpression2(IrDynamicMemberExpression irDynamicMemberExpression, Void r2) {
            return (Boolean) IrElementVisitor.DefaultImpls.visitDynamicMemberExpression(this, irDynamicMemberExpression, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitDynamicOperatorExpression2(IrDynamicOperatorExpression irDynamicOperatorExpression, Void r2) {
            return (Boolean) IrElementVisitor.DefaultImpls.visitDynamicOperatorExpression(this, irDynamicOperatorExpression, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitElseBranch2(IrElseBranch irElseBranch, Void r2) {
            return (Boolean) IrElementVisitor.DefaultImpls.visitElseBranch(this, irElseBranch, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitEnumConstructorCall2(IrEnumConstructorCall irEnumConstructorCall, Void r2) {
            return (Boolean) IrElementVisitor.DefaultImpls.visitEnumConstructorCall(this, irEnumConstructorCall, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitEnumEntry2(IrEnumEntry irEnumEntry, Void r2) {
            return (Boolean) IrElementVisitor.DefaultImpls.visitEnumEntry(this, irEnumEntry, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitErrorCallExpression2(IrErrorCallExpression irErrorCallExpression, Void r2) {
            return (Boolean) IrElementVisitor.DefaultImpls.visitErrorCallExpression(this, irErrorCallExpression, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitErrorExpression2(IrErrorExpression irErrorExpression, Void r2) {
            return (Boolean) IrElementVisitor.DefaultImpls.visitErrorExpression(this, irErrorExpression, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitExpression2(IrExpression irExpression, Void r2) {
            return (Boolean) IrElementVisitor.DefaultImpls.visitExpression(this, irExpression, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitExpressionBody2(IrExpressionBody irExpressionBody, Void r2) {
            return (Boolean) IrElementVisitor.DefaultImpls.visitExpressionBody(this, irExpressionBody, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitExternalPackageFragment2(IrExternalPackageFragment irExternalPackageFragment, Void r2) {
            return (Boolean) IrElementVisitor.DefaultImpls.visitExternalPackageFragment(this, irExternalPackageFragment, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitFieldAccess2(IrFieldAccessExpression irFieldAccessExpression, Void r2) {
            return (Boolean) IrElementVisitor.DefaultImpls.visitFieldAccess(this, irFieldAccessExpression, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitFile2(IrFile irFile, Void r2) {
            return (Boolean) IrElementVisitor.DefaultImpls.visitFile(this, irFile, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitFunction2(IrFunction irFunction, Void r2) {
            return (Boolean) IrElementVisitor.DefaultImpls.visitFunction(this, irFunction, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitFunctionAccess2(IrFunctionAccessExpression irFunctionAccessExpression, Void r2) {
            return (Boolean) IrElementVisitor.DefaultImpls.visitFunctionAccess(this, irFunctionAccessExpression, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitFunctionExpression2(IrFunctionExpression irFunctionExpression, Void r2) {
            return (Boolean) IrElementVisitor.DefaultImpls.visitFunctionExpression(this, irFunctionExpression, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitFunctionReference2(IrFunctionReference irFunctionReference, Void r2) {
            return (Boolean) IrElementVisitor.DefaultImpls.visitFunctionReference(this, irFunctionReference, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitGetClass2(IrGetClass irGetClass, Void r2) {
            return (Boolean) IrElementVisitor.DefaultImpls.visitGetClass(this, irGetClass, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitGetEnumValue2(IrGetEnumValue irGetEnumValue, Void r2) {
            return (Boolean) IrElementVisitor.DefaultImpls.visitGetEnumValue(this, irGetEnumValue, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitGetField2(IrGetField irGetField, Void r2) {
            return (Boolean) IrElementVisitor.DefaultImpls.visitGetField(this, irGetField, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitGetObjectValue2(IrGetObjectValue irGetObjectValue, Void r2) {
            return (Boolean) IrElementVisitor.DefaultImpls.visitGetObjectValue(this, irGetObjectValue, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitGetValue2(IrGetValue irGetValue, Void r2) {
            return (Boolean) IrElementVisitor.DefaultImpls.visitGetValue(this, irGetValue, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitInstanceInitializerCall2(IrInstanceInitializerCall irInstanceInitializerCall, Void r2) {
            return (Boolean) IrElementVisitor.DefaultImpls.visitInstanceInitializerCall(this, irInstanceInitializerCall, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitLocalDelegatedPropertyReference2(IrLocalDelegatedPropertyReference irLocalDelegatedPropertyReference, Void r2) {
            return (Boolean) IrElementVisitor.DefaultImpls.visitLocalDelegatedPropertyReference(this, irLocalDelegatedPropertyReference, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitLoop2(IrLoop irLoop, Void r2) {
            return (Boolean) IrElementVisitor.DefaultImpls.visitLoop(this, irLoop, r2);
        }

        public Boolean visitMemberAccess(IrMemberAccessExpression<?> irMemberAccessExpression, Void r2) {
            return (Boolean) IrElementVisitor.DefaultImpls.visitMemberAccess(this, irMemberAccessExpression, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public /* bridge */ /* synthetic */ Object visitMemberAccess(IrMemberAccessExpression irMemberAccessExpression, Object obj) {
            return visitMemberAccess((IrMemberAccessExpression<?>) irMemberAccessExpression, (Void) obj);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitModuleFragment2(IrModuleFragment irModuleFragment, Void r2) {
            return (Boolean) IrElementVisitor.DefaultImpls.visitModuleFragment(this, irModuleFragment, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitPropertyReference2(IrPropertyReference irPropertyReference, Void r2) {
            return (Boolean) IrElementVisitor.DefaultImpls.visitPropertyReference(this, irPropertyReference, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitRawFunctionReference2(IrRawFunctionReference irRawFunctionReference, Void r2) {
            return (Boolean) IrElementVisitor.DefaultImpls.visitRawFunctionReference(this, irRawFunctionReference, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitReturn2(IrReturn irReturn, Void r2) {
            return (Boolean) IrElementVisitor.DefaultImpls.visitReturn(this, irReturn, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitScript2(IrScript irScript, Void r2) {
            return (Boolean) IrElementVisitor.DefaultImpls.visitScript(this, irScript, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitSetField2(IrSetField irSetField, Void r2) {
            return (Boolean) IrElementVisitor.DefaultImpls.visitSetField(this, irSetField, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitSetValue2(IrSetValue irSetValue, Void r2) {
            return (Boolean) IrElementVisitor.DefaultImpls.visitSetValue(this, irSetValue, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitSingletonReference2(IrGetSingletonValue irGetSingletonValue, Void r2) {
            return (Boolean) IrElementVisitor.DefaultImpls.visitSingletonReference(this, irGetSingletonValue, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitSpreadElement2(IrSpreadElement irSpreadElement, Void r2) {
            return (Boolean) IrElementVisitor.DefaultImpls.visitSpreadElement(this, irSpreadElement, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitStringConcatenation2(IrStringConcatenation irStringConcatenation, Void r2) {
            return (Boolean) IrElementVisitor.DefaultImpls.visitStringConcatenation(this, irStringConcatenation, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitSuspendableExpression2(IrSuspendableExpression irSuspendableExpression, Void r2) {
            return (Boolean) IrElementVisitor.DefaultImpls.visitSuspendableExpression(this, irSuspendableExpression, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitSuspensionPoint2(IrSuspensionPoint irSuspensionPoint, Void r2) {
            return (Boolean) IrElementVisitor.DefaultImpls.visitSuspensionPoint(this, irSuspensionPoint, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitSyntheticBody2(IrSyntheticBody irSyntheticBody, Void r2) {
            return (Boolean) IrElementVisitor.DefaultImpls.visitSyntheticBody(this, irSyntheticBody, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitThrow2(IrThrow irThrow, Void r2) {
            return (Boolean) IrElementVisitor.DefaultImpls.visitThrow(this, irThrow, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitTry2(IrTry irTry, Void r2) {
            return (Boolean) IrElementVisitor.DefaultImpls.visitTry(this, irTry, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitTypeOperator2(IrTypeOperatorCall irTypeOperatorCall, Void r2) {
            return (Boolean) IrElementVisitor.DefaultImpls.visitTypeOperator(this, irTypeOperatorCall, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitValueAccess2(IrValueAccessExpression irValueAccessExpression, Void r2) {
            return (Boolean) IrElementVisitor.DefaultImpls.visitValueAccess(this, irValueAccessExpression, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitVararg2(IrVararg irVararg, Void r2) {
            return (Boolean) IrElementVisitor.DefaultImpls.visitVararg(this, irVararg, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitWhen2(IrWhen irWhen, Void r2) {
            return (Boolean) IrElementVisitor.DefaultImpls.visitWhen(this, irWhen, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitWhileLoop2(IrWhileLoop irWhileLoop, Void r2) {
            return (Boolean) IrElementVisitor.DefaultImpls.visitWhileLoop(this, irWhileLoop, r2);
        }

        private final boolean isExported(IrDeclaration irDeclaration, List<? extends IrConstructorCall> list, DescriptorVisibility descriptorVisibility) {
            return (AdditionalIrUtilsKt.hasAnnotation(list, MangleUtilsKt.getPublishedApiAnnotation()) || IrExportCheckerVisitor.this.isPlatformSpecificExported(irDeclaration) || descriptorVisibility == null || isPubliclyVisible(descriptorVisibility)) && ((Boolean) irDeclaration.getParent().accept(this, null)).booleanValue();
        }

        private final boolean isPubliclyVisible(DescriptorVisibility descriptorVisibility) {
            return descriptorVisibility.isPublicAPI() || descriptorVisibility == DescriptorVisibilities.INTERNAL;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitElement2(IrElement element, Void data) {
            Intrinsics.checkNotNullParameter(element, "element");
            throw new IllegalStateException(("Should bot reach here " + RenderIrElementKt.render$default(element, (DumpIrTreeOptions) null, 1, (Object) null)).toString());
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitDeclaration2(IrDeclarationBase declaration, Void data) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            return Boolean.valueOf(isExported(declaration, declaration.getAnnotations(), null));
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitAnonymousInitializer2(IrAnonymousInitializer declaration, Void data) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            return false;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitValueParameter2(IrValueParameter declaration, Void data) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            return false;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitVariable2(IrVariable declaration, Void data) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            return false;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitLocalDelegatedProperty2(IrLocalDelegatedProperty declaration, Void data) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            return false;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitErrorDeclaration2(IrErrorDeclaration declaration, Void data) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            return false;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitTypeParameter2(IrTypeParameter declaration, Void data) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            return false;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitField2(IrField declaration, Void data) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            return false;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitProperty2(IrProperty declaration, Void data) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            return Boolean.valueOf(isExported(declaration, declaration.getAnnotations(), declaration.getVisibility()));
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitPackageFragment2(IrPackageFragment declaration, Void data) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            return true;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitTypeAlias2(IrTypeAlias declaration, Void data) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            return Boolean.valueOf(declaration.getParent() instanceof IrPackageFragment ? true : isExported(declaration, declaration.getAnnotations(), declaration.getVisibility()));
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitClass2(IrClass declaration, Void data) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            if (Intrinsics.areEqual(declaration.getName(), SpecialNames.NO_NAME_PROVIDED)) {
                return false;
            }
            return Boolean.valueOf(isExported(declaration, declaration.getAnnotations(), declaration.getVisibility()));
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitConstructor2(IrConstructor declaration, Void data) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            IrClass constructedClass = AdditionalIrUtilsKt.getConstructedClass(declaration);
            return Boolean.valueOf(constructedClass.getKind().isSingleton() ? ((Boolean) constructedClass.accept(this, null)).booleanValue() : isExported(declaration, declaration.getAnnotations(), declaration.getVisibility()));
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public Boolean visitSimpleFunction2(IrSimpleFunction declaration, Void data) {
            List<IrConstructorCall> annotations;
            IrProperty owner;
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            IrPropertySymbol correspondingPropertySymbol = declaration.getCorrespondingPropertySymbol();
            if (correspondingPropertySymbol == null || (owner = correspondingPropertySymbol.getOwner()) == null || (annotations = owner.getAnnotations()) == null) {
                annotations = declaration.getAnnotations();
            }
            return Boolean.valueOf(isExported(declaration, annotations, declaration.getVisibility()));
        }
    }
}
