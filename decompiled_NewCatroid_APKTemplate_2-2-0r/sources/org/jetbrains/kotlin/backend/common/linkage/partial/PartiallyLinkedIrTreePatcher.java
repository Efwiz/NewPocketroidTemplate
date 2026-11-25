package org.jetbrains.kotlin.backend.common.linkage.partial;

import androidx.exifinterface.media.ExifInterface;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.MutablePropertyReference0Impl;
import kotlin.jvm.internal.Ref;
import kotlin.jvm.internal.Reflection;
import kotlin.jvm.internal.TypeIntrinsics;
import kotlin.properties.Delegates;
import kotlin.properties.ObservableProperty;
import kotlin.properties.ReadWriteProperty;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt;
import kotlin.reflect.KProperty;
import kotlin.sequences.Sequence;
import kotlin.text.StringsKt;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.psi.PsiTreeChangeEvent;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.ir.IrBuiltIns;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.declarations.IrAnonymousInitializer;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationBase;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationContainer;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationWithVisibility;
import org.jetbrains.kotlin.ir.declarations.IrEnumEntry;
import org.jetbrains.kotlin.ir.declarations.IrErrorDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrExternalPackageFragment;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrLocalDelegatedProperty;
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment;
import org.jetbrains.kotlin.ir.declarations.IrMutableAnnotationContainer;
import org.jetbrains.kotlin.ir.declarations.IrOverridableDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrPackageFragment;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrScript;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrSymbolOwner;
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
import org.jetbrains.kotlin.ir.expressions.IrReturnableBlock;
import org.jetbrains.kotlin.ir.expressions.IrSetField;
import org.jetbrains.kotlin.ir.expressions.IrSetValue;
import org.jetbrains.kotlin.ir.expressions.IrSpreadElement;
import org.jetbrains.kotlin.ir.expressions.IrStatementContainer;
import org.jetbrains.kotlin.ir.expressions.IrStatementOrigin;
import org.jetbrains.kotlin.ir.expressions.IrStringConcatenation;
import org.jetbrains.kotlin.ir.expressions.IrSuspendableExpression;
import org.jetbrains.kotlin.ir.expressions.IrSuspensionPoint;
import org.jetbrains.kotlin.ir.expressions.IrSyntheticBody;
import org.jetbrains.kotlin.ir.expressions.IrThrow;
import org.jetbrains.kotlin.ir.expressions.IrTry;
import org.jetbrains.kotlin.ir.expressions.IrTypeOperator;
import org.jetbrains.kotlin.ir.expressions.IrTypeOperatorCall;
import org.jetbrains.kotlin.ir.expressions.IrValueAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrVararg;
import org.jetbrains.kotlin.ir.expressions.IrWhen;
import org.jetbrains.kotlin.ir.expressions.IrWhileLoop;
import org.jetbrains.kotlin.ir.expressions.impl.IrCompositeImpl;
import org.jetbrains.kotlin.ir.linkage.partial.ExploredClassifier;
import org.jetbrains.kotlin.ir.linkage.partial.PartialLinkageCase;
import org.jetbrains.kotlin.ir.linkage.partial.PartialLinkageLogger;
import org.jetbrains.kotlin.ir.linkage.partial.PartialLinkageSupportForLowerings;
import org.jetbrains.kotlin.ir.linkage.partial.PartialLinkageUtilsKt;
import org.jetbrains.kotlin.ir.linkage.partial.PartiallyLinkedDeclarationOrigin;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrClassifierSymbol;
import org.jetbrains.kotlin.ir.symbols.IrConstructorSymbol;
import org.jetbrains.kotlin.ir.symbols.IrFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrPropertySymbol;
import org.jetbrains.kotlin.ir.symbols.IrReturnTargetSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;
import org.jetbrains.kotlin.ir.symbols.impl.IrAnonymousInitializerSymbolImpl;
import org.jetbrains.kotlin.ir.types.IrSimpleType;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.AdditionalIrUtilsKt;
import org.jetbrains.kotlin.ir.util.IdSignature;
import org.jetbrains.kotlin.ir.util.IrTypeUtilsKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoidKt;
import org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid;
import org.jetbrains.kotlin.ir.visitors.IrVisitorsKt;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.name.FqName;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: PartiallyLinkedIrTreePatcher.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000¦\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u001e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\n\b\u0000\u0018\u0000 =2\u00020\u0001:\t<=>?@ABCDB%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ\u0014\u0010!\u001a\u00020\"2\f\u0010#\u001a\b\u0012\u0004\u0012\u00020\u00180$J\u0014\u0010%\u001a\u00020\"2\f\u0010#\u001a\b\u0012\u0004\u0012\u00020\u001b0&J\u000e\u0010\u001c\u001a\u00020\u001d2\u0006\u0010'\u001a\u00020\u0018J\u000e\u0010(\u001a\u0004\u0018\u00010)*\u00020*H\u0002J\u000e\u0010(\u001a\u0004\u0018\u00010)*\u00020+H\u0002J?\u0010,\u001a\u00020-\"\b\b\u0000\u0010.*\u00020-*\u0002H.2\u0006\u0010/\u001a\u0002002\u0019\u00101\u001a\u0015\u0012\u0004\u0012\u0002H.\u0012\u0006\u0012\u0004\u0018\u00010302¢\u0006\u0002\b4H\u0082\b¢\u0006\u0002\u00105J\u0014\u00106\u001a\u0004\u0018\u000107*\b\u0012\u0004\u0012\u00020+08H\u0002J\u000e\u00106\u001a\u0004\u0018\u000107*\u00020+H\u0002J\u0014\u00109\u001a\u00020\"*\u00020:2\u0006\u0010/\u001a\u00020;H\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u001b\u0010\u000b\u001a\u00020\f8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u000f\u0010\u0010\u001a\u0004\b\r\u0010\u000eR\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u001b\u0010\u0011\u001a\u00020\u00128BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0015\u0010\u0010\u001a\u0004\b\u0013\u0010\u0014R\u001e\u0010\u0016\u001a\u0012\u0012\u0004\u0012\u00020\u00180\u0017j\b\u0012\u0004\u0012\u00020\u0018`\u0019X\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\u001a\u001a\u0012\u0012\u0004\u0012\u00020\u001b0\u0017j\b\u0012\u0004\u0012\u00020\u001b`\u0019X\u0082\u0004¢\u0006\u0002\n\u0000R\u0018\u0010\u001c\u001a\u00020\u001d*\u00020\u001b8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u001e\u0010\u001fR\u0018\u0010\u001c\u001a\u00020\u001d*\u00020\f8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u001e\u0010 ¨\u0006E"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/linkage/partial/PartiallyLinkedIrTreePatcher;", "", "builtIns", "Lorg/jetbrains/kotlin/ir/IrBuiltIns;", "classifierExplorer", "Lorg/jetbrains/kotlin/backend/common/linkage/partial/ClassifierExplorer;", "stubGenerator", "Lorg/jetbrains/kotlin/backend/common/linkage/partial/MissingDeclarationStubGenerator;", "logger", "Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageLogger;", "(Lorg/jetbrains/kotlin/ir/IrBuiltIns;Lorg/jetbrains/kotlin/backend/common/linkage/partial/ClassifierExplorer;Lorg/jetbrains/kotlin/backend/common/linkage/partial/MissingDeclarationStubGenerator;Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageLogger;)V", "stdlibModule", "Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageUtils$Module;", "getStdlibModule", "()Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageUtils$Module;", "stdlibModule$delegate", "Lkotlin/Lazy;", "supportForLowerings", "Lorg/jetbrains/kotlin/backend/common/linkage/partial/PartialLinkageSupportForLoweringsImpl;", "getSupportForLowerings", "()Lorg/jetbrains/kotlin/backend/common/linkage/partial/PartialLinkageSupportForLoweringsImpl;", "supportForLowerings$delegate", "visitedDeclarations", "Ljava/util/HashSet;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "Lkotlin/collections/HashSet;", "visitedModuleFragments", "Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;", "shouldBeSkipped", "", "getShouldBeSkipped", "(Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;)Z", "(Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageUtils$Module;)Z", "patchDeclarations", "", PsiTreeChangeEvent.PROP_ROOTS, "", "patchModuleFragments", "Lkotlin/sequences/Sequence;", "declaration", "explore", "Lorg/jetbrains/kotlin/ir/linkage/partial/ExploredClassifier$Unusable;", "Lorg/jetbrains/kotlin/ir/symbols/IrClassifierSymbol;", "Lorg/jetbrains/kotlin/ir/types/IrType;", "maybeThrowLinkageError", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", ExifInterface.GPS_DIRECTION_TRUE, "transformer", "Lorg/jetbrains/kotlin/backend/common/linkage/partial/FileAwareIrElementTransformerVoid;", "computePartialLinkageCase", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageCase;", "Lkotlin/ExtensionFunctionType;", "(Lorg/jetbrains/kotlin/ir/expressions/IrExpression;Lorg/jetbrains/kotlin/backend/common/linkage/partial/FileAwareIrElementTransformerVoid;Lkotlin/jvm/functions/Function1;)Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "toPartiallyLinkedMarkerTypeOrNull", "Lorg/jetbrains/kotlin/backend/common/linkage/partial/PartiallyLinkedMarkerType;", "", "transformVoid", "Lorg/jetbrains/kotlin/ir/IrElement;", "Lorg/jetbrains/kotlin/ir/visitors/IrElementTransformerVoid;", "AnnotationChecker", "Companion", "DeclarationTransformer", "DeclarationTransformerContext", "DirectChildren", "DirectChildrenStatementsCollector", "ExpressionTransformer", "NonLocalReturnsPatcher", "ReturnTargetContext", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PartiallyLinkedIrTreePatcher {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final FqName REPLACE_WITH_CONSTRUCTOR_EXPRESSION_FIELD_FQN = new FqName("kotlin.ReplaceWith.<init>.expression");
    private final IrBuiltIns builtIns;
    private final ClassifierExplorer classifierExplorer;

    /* JADX INFO: renamed from: stdlibModule$delegate, reason: from kotlin metadata */
    private final Lazy stdlibModule;
    private final MissingDeclarationStubGenerator stubGenerator;

    /* JADX INFO: renamed from: supportForLowerings$delegate, reason: from kotlin metadata */
    private final Lazy supportForLowerings;
    private final HashSet<IrDeclaration> visitedDeclarations;
    private final HashSet<IrModuleFragment> visitedModuleFragments;

    public PartiallyLinkedIrTreePatcher(IrBuiltIns builtIns, ClassifierExplorer classifierExplorer, MissingDeclarationStubGenerator stubGenerator, final PartialLinkageLogger logger) {
        Intrinsics.checkNotNullParameter(builtIns, "builtIns");
        Intrinsics.checkNotNullParameter(classifierExplorer, "classifierExplorer");
        Intrinsics.checkNotNullParameter(stubGenerator, "stubGenerator");
        Intrinsics.checkNotNullParameter(logger, "logger");
        this.builtIns = builtIns;
        this.classifierExplorer = classifierExplorer;
        this.stubGenerator = stubGenerator;
        this.visitedModuleFragments = new HashSet<>();
        this.visitedDeclarations = new HashSet<>();
        this.stdlibModule = LazyKt.lazy(new Function0<org.jetbrains.kotlin.ir.linkage.partial.PartialLinkageUtils.Module>() { // from class: org.jetbrains.kotlin.backend.common.linkage.partial.PartiallyLinkedIrTreePatcher$stdlibModule$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final org.jetbrains.kotlin.ir.linkage.partial.PartialLinkageUtils.Module invoke() {
                return org.jetbrains.kotlin.ir.linkage.partial.PartialLinkageUtils.Module.INSTANCE.determineModuleFor(this.this$0.builtIns.getAnyClass().getOwner());
            }
        });
        this.supportForLowerings = LazyKt.lazy(new Function0<PartialLinkageSupportForLoweringsImpl>() { // from class: org.jetbrains.kotlin.backend.common.linkage.partial.PartiallyLinkedIrTreePatcher$supportForLowerings$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final PartialLinkageSupportForLoweringsImpl invoke() {
                return new PartialLinkageSupportForLoweringsImpl(this.this$0.builtIns, logger);
            }
        });
    }

    private final org.jetbrains.kotlin.ir.linkage.partial.PartialLinkageUtils.Module getStdlibModule() {
        return (org.jetbrains.kotlin.ir.linkage.partial.PartialLinkageUtils.Module) this.stdlibModule.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean getShouldBeSkipped(org.jetbrains.kotlin.ir.linkage.partial.PartialLinkageUtils.Module module) {
        return Intrinsics.areEqual(module, org.jetbrains.kotlin.ir.linkage.partial.PartialLinkageUtils.Module.SyntheticBuiltInFunctions.INSTANCE) || Intrinsics.areEqual(module, getStdlibModule());
    }

    private final boolean getShouldBeSkipped(IrModuleFragment irModuleFragment) {
        return irModuleFragment.getFiles().isEmpty() || Intrinsics.areEqual(irModuleFragment.getName().asString(), getStdlibModule().getName());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final PartialLinkageSupportForLoweringsImpl getSupportForLowerings() {
        return (PartialLinkageSupportForLoweringsImpl) this.supportForLowerings.getValue();
    }

    public final boolean shouldBeSkipped(IrDeclaration declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        return getShouldBeSkipped(org.jetbrains.kotlin.ir.linkage.partial.PartialLinkageUtils.Module.INSTANCE.determineModuleFor(declaration));
    }

    public final void patchDeclarations(Collection<? extends IrDeclaration> roots) {
        Intrinsics.checkNotNullParameter(roots, "roots");
        for (IrDeclaration irDeclaration : roots) {
            org.jetbrains.kotlin.ir.linkage.partial.PartialLinkageUtils.File fileDetermineFileFor = org.jetbrains.kotlin.ir.linkage.partial.PartialLinkageUtils.File.INSTANCE.determineFileFor(irDeclaration);
            if (!getShouldBeSkipped(fileDetermineFileFor.getModule()) && this.visitedDeclarations.add(irDeclaration)) {
                IrDeclaration irDeclaration2 = irDeclaration;
                transformVoid(irDeclaration2, new DeclarationTransformer(fileDetermineFileFor));
                transformVoid(irDeclaration2, new ExpressionTransformer(fileDetermineFileFor));
                transformVoid(irDeclaration2, new NonLocalReturnsPatcher(fileDetermineFileFor));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void transformVoid(IrElement irElement, IrElementTransformerVoid irElementTransformerVoid) {
        irElement.transform(irElementTransformerVoid, null);
    }

    /* JADX INFO: compiled from: PartiallyLinkedIrTreePatcher.kt */
    @Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b2\u0018\u00002\u00020\u0001:\u0002\u000f\u0010B\u0007\b\u0004¢\u0006\u0002\u0010\u0002J\b\u0010\u0006\u001a\u00020\u0007H&J \u0010\u0006\u001a\u00020\u00072\u000e\u0010\b\u001a\n\u0012\u0006\b\u0001\u0012\u00020\n0\t2\u0006\u0010\u000b\u001a\u00020\fH\u0004J\u000e\u0010\r\u001a\u00020\u00072\u0006\u0010\u000e\u001a\u00020\u0005R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000\u0082\u0001\u0002\u0011\u0012¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/linkage/partial/PartiallyLinkedIrTreePatcher$DeclarationTransformerContext;", "", "()V", "scheduledForRemoval", "Ljava/util/HashSet;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "performRemoval", "", "declarations", "", "Lorg/jetbrains/kotlin/ir/IrStatement;", "container", "Lorg/jetbrains/kotlin/ir/IrElement;", "scheduleForRemoval", "declaration", "DeclarationContainer", "StatementContainer", "Lorg/jetbrains/kotlin/backend/common/linkage/partial/PartiallyLinkedIrTreePatcher$DeclarationTransformerContext$DeclarationContainer;", "Lorg/jetbrains/kotlin/backend/common/linkage/partial/PartiallyLinkedIrTreePatcher$DeclarationTransformerContext$StatementContainer;", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static abstract class DeclarationTransformerContext {
        private final HashSet<IrDeclaration> scheduledForRemoval;

        public /* synthetic */ DeclarationTransformerContext(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public abstract void performRemoval();

        private DeclarationTransformerContext() {
            this.scheduledForRemoval = new HashSet<>();
        }

        public final void scheduleForRemoval(IrDeclaration declaration) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            this.scheduledForRemoval.add(declaration);
        }

        protected final void performRemoval(List<? extends IrStatement> declarations, IrElement container) {
            Intrinsics.checkNotNullParameter(declarations, "declarations");
            Intrinsics.checkNotNullParameter(container, "container");
            if (this.scheduledForRemoval.size() == 0) {
                return;
            }
            declarations.size();
            TypeIntrinsics.asMutableCollection(declarations).removeAll(this.scheduledForRemoval);
            declarations.size();
        }

        /* JADX INFO: compiled from: PartiallyLinkedIrTreePatcher.kt */
        @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u0007\u001a\u00020\bH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/linkage/partial/PartiallyLinkedIrTreePatcher$DeclarationTransformerContext$DeclarationContainer;", "Lorg/jetbrains/kotlin/backend/common/linkage/partial/PartiallyLinkedIrTreePatcher$DeclarationTransformerContext;", "declarationContainer", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationContainer;", "(Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationContainer;)V", "getDeclarationContainer", "()Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationContainer;", "performRemoval", "", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class DeclarationContainer extends DeclarationTransformerContext {
            private final IrDeclarationContainer declarationContainer;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public DeclarationContainer(IrDeclarationContainer declarationContainer) {
                super(null);
                Intrinsics.checkNotNullParameter(declarationContainer, "declarationContainer");
                this.declarationContainer = declarationContainer;
            }

            public final IrDeclarationContainer getDeclarationContainer() {
                return this.declarationContainer;
            }

            @Override // org.jetbrains.kotlin.backend.common.linkage.partial.PartiallyLinkedIrTreePatcher.DeclarationTransformerContext
            public void performRemoval() {
                performRemoval(this.declarationContainer.getDeclarations(), this.declarationContainer);
            }
        }

        /* JADX INFO: compiled from: PartiallyLinkedIrTreePatcher.kt */
        @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u0007\u001a\u00020\bH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/linkage/partial/PartiallyLinkedIrTreePatcher$DeclarationTransformerContext$StatementContainer;", "Lorg/jetbrains/kotlin/backend/common/linkage/partial/PartiallyLinkedIrTreePatcher$DeclarationTransformerContext;", "statementContainer", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementContainer;", "(Lorg/jetbrains/kotlin/ir/expressions/IrStatementContainer;)V", "getStatementContainer", "()Lorg/jetbrains/kotlin/ir/expressions/IrStatementContainer;", "performRemoval", "", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class StatementContainer extends DeclarationTransformerContext {
            private final IrStatementContainer statementContainer;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public StatementContainer(IrStatementContainer statementContainer) {
                super(null);
                Intrinsics.checkNotNullParameter(statementContainer, "statementContainer");
                this.statementContainer = statementContainer;
            }

            public final IrStatementContainer getStatementContainer() {
                return this.statementContainer;
            }

            @Override // org.jetbrains.kotlin.backend.common.linkage.partial.PartiallyLinkedIrTreePatcher.DeclarationTransformerContext
            public void performRemoval() {
                performRemoval(this.statementContainer.getStatements(), this.statementContainer);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: compiled from: PartiallyLinkedIrTreePatcher.kt */
    @Metadata(d1 = {"\u0000°\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0082\u0004\u0018\u00002\u00020\u0001B\u000f\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J\u0010\u0010\u0010\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u0011H\u0016J\u0010\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0016J\u0010\u0010\u0016\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u0017H\u0016J\u0010\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u000e\u001a\u00020\u0019H\u0016J\u0010\u0010\u001a\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u001bH\u0016J\u0010\u0010\u001c\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u001dH\u0016J\u0010\u0010\u001e\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u001fH\u0016J\u000e\u0010 \u001a\u0004\u0018\u00010!*\u00020\u0011H\u0002J\u001c\u0010\"\u001a\u00020#\"\b\b\u0000\u0010$*\u00020%*\b\u0012\u0004\u0012\u0002H$0&H\u0002J\u000e\u0010'\u001a\u0004\u0018\u00010(*\u00020)H\u0002J\f\u0010*\u001a\u00020#*\u00020+H\u0002J\u001e\u0010,\u001a\u00020-*\u00020.2\u0006\u0010\u000e\u001a\u00020+2\b\b\u0002\u0010/\u001a\u000200H\u0002J\u001b\u00101\u001a\u0002H2\"\b\b\u0000\u00102*\u00020+*\u0002H2H\u0002¢\u0006\u0002\u00103J\u001b\u00104\u001a\u0002H2\"\b\b\u0000\u00102*\u000205*\u0002H2H\u0002¢\u0006\u0002\u00106J\u001b\u00104\u001a\u0002H2\"\b\b\u0000\u00102*\u000207*\u0002H2H\u0002¢\u0006\u0002\u00108J#\u00104\u001a\u0002H2\"\b\b\u0000\u00102*\u000209*\u0002H22\u0006\u0010:\u001a\u00020\u0007H\u0002¢\u0006\u0002\u0010;R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006<²\u0006\f\u0010=\u001a\u0004\u0018\u00010(X\u008a\u008e\u0002"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/linkage/partial/PartiallyLinkedIrTreePatcher$DeclarationTransformer;", "Lorg/jetbrains/kotlin/backend/common/linkage/partial/FileAwareIrElementTransformerVoid;", "startingFile", "Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageUtils$File;", "(Lorg/jetbrains/kotlin/backend/common/linkage/partial/PartiallyLinkedIrTreePatcher;Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageUtils$File;)V", "stack", "Ljava/util/ArrayDeque;", "Lorg/jetbrains/kotlin/backend/common/linkage/partial/PartiallyLinkedIrTreePatcher$DeclarationTransformerContext;", "visitBlockBody", "Lorg/jetbrains/kotlin/ir/expressions/IrBody;", "body", "Lorg/jetbrains/kotlin/ir/expressions/IrBlockBody;", "visitClass", "Lorg/jetbrains/kotlin/ir/IrStatement;", "declaration", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "visitConstructor", "Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "visitContainerExpression", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrContainerExpression;", "visitField", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "visitPackageFragment", "Lorg/jetbrains/kotlin/ir/declarations/IrPackageFragment;", "visitProperty", "Lorg/jetbrains/kotlin/ir/declarations/IrProperty;", "visitSimpleFunction", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "visitVariable", "Lorg/jetbrains/kotlin/ir/declarations/IrVariable;", "checkConstructorDelegation", "Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageCase$InvalidConstructorDelegation;", "filterOverriddenSymbols", "", ExifInterface.LATITUDE_SOUTH, "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "Lorg/jetbrains/kotlin/ir/declarations/IrOverridableDeclaration;", "rewriteTypesInFunction", "Lorg/jetbrains/kotlin/ir/linkage/partial/ExploredClassifier$Unusable;", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "scheduleForRemoval", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "throwLinkageError", "Lorg/jetbrains/kotlin/ir/expressions/IrCall;", "Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageCase;", "doNotLog", "", "transformChildren", ExifInterface.GPS_DIRECTION_TRUE, "(Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;)Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "transformChildrenWithRemoval", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationContainer;", "(Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationContainer;)Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationContainer;", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementContainer;", "(Lorg/jetbrains/kotlin/ir/expressions/IrStatementContainer;)Lorg/jetbrains/kotlin/ir/expressions/IrStatementContainer;", "Lorg/jetbrains/kotlin/ir/IrElement;", "context", "(Lorg/jetbrains/kotlin/ir/IrElement;Lorg/jetbrains/kotlin/backend/common/linkage/partial/PartiallyLinkedIrTreePatcher$DeclarationTransformerContext;)Lorg/jetbrains/kotlin/ir/IrElement;", "ir.serialization.common", "result"}, k = 1, mv = {1, 9, 0}, xi = 48)
    final class DeclarationTransformer extends FileAwareIrElementTransformerVoid {
        static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.mutableProperty0(new MutablePropertyReference0Impl(Reflection.getOrCreateKotlinClass(DeclarationTransformer.class), "result", "<v#0>"))};
        private final ArrayDeque<DeclarationTransformerContext> stack;

        public DeclarationTransformer(org.jetbrains.kotlin.ir.linkage.partial.PartialLinkageUtils.File file) {
            super(file);
            this.stack = new ArrayDeque<>();
        }

        private final <T extends IrDeclaration> T transformChildren(T t) {
            transformChildrenVoid(t);
            return t;
        }

        private final <T extends IrDeclarationContainer> T transformChildrenWithRemoval(T t) {
            return (T) transformChildrenWithRemoval(t, new DeclarationTransformerContext.DeclarationContainer(t));
        }

        private final <T extends IrStatementContainer> T transformChildrenWithRemoval(T t) {
            return (T) transformChildrenWithRemoval(t, new DeclarationTransformerContext.StatementContainer(t));
        }

        private final <T extends IrElement> T transformChildrenWithRemoval(T t, DeclarationTransformerContext declarationTransformerContext) {
            this.stack.push(declarationTransformerContext);
            transformChildrenVoid(t);
            this.stack.pop();
            declarationTransformerContext.performRemoval();
            return t;
        }

        private final void scheduleForRemoval(IrDeclaration irDeclaration) {
            if (irDeclaration.getOrigin() != PartiallyLinkedDeclarationOrigin.MISSING_DECLARATION) {
                this.stack.peek().scheduleForRemoval(irDeclaration);
            }
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
        public IrPackageFragment visitPackageFragment(IrPackageFragment declaration) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            return (IrPackageFragment) transformChildrenWithRemoval(declaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
        public IrStatement visitClass(IrClass declaration) {
            PartialLinkageCase.DeclarationWithUnusableClassifier declarationWithUnusableClassifier;
            IrAnonymousInitializer irAnonymousInitializerCreateAnonymousInitializer$default;
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            ExploredClassifier.Unusable unusableExplore = PartiallyLinkedIrTreePatcher.this.explore(declaration.getSymbol());
            if (unusableExplore != null) {
                if (unusableExplore instanceof ExploredClassifier.Unusable.CanBeRootCause) {
                    declarationWithUnusableClassifier = new PartialLinkageCase.UnusableClassifier((ExploredClassifier.Unusable.CanBeRootCause) unusableExplore);
                } else if (unusableExplore instanceof ExploredClassifier.Unusable.DueToOtherClassifier) {
                    declarationWithUnusableClassifier = new PartialLinkageCase.DeclarationWithUnusableClassifier(declaration.getSymbol(), ((ExploredClassifier.Unusable.DueToOtherClassifier) unusableExplore).getRootCause());
                } else {
                    throw new NoWhenBranchMatchedException();
                }
                Iterator<T> it = declaration.getDeclarations().iterator();
                do {
                    if (!it.hasNext()) {
                        irAnonymousInitializerCreateAnonymousInitializer$default = null;
                        break;
                    }
                    IrDeclaration irDeclaration = (IrDeclaration) it.next();
                    irAnonymousInitializerCreateAnonymousInitializer$default = irDeclaration instanceof IrAnonymousInitializer ? (IrAnonymousInitializer) irDeclaration : null;
                } while (irAnonymousInitializerCreateAnonymousInitializer$default == null);
                if (irAnonymousInitializerCreateAnonymousInitializer$default == null) {
                    irAnonymousInitializerCreateAnonymousInitializer$default = IrFactory.DefaultImpls.createAnonymousInitializer$default(PartiallyLinkedIrTreePatcher.this.builtIns.getIrFactory(), declaration.getStartOffset(), declaration.getEndOffset(), PartiallyLinkedDeclarationOrigin.AUXILIARY_GENERATED_DECLARATION, new IrAnonymousInitializerSymbolImpl(null, 1, null), false, 16, null);
                    irAnonymousInitializerCreateAnonymousInitializer$default.setBody(PartiallyLinkedIrTreePatcher.this.builtIns.getIrFactory().createBlockBody(declaration.getStartOffset(), declaration.getEndOffset()));
                    irAnonymousInitializerCreateAnonymousInitializer$default.setParent(declaration);
                    declaration.getDeclarations().add(irAnonymousInitializerCreateAnonymousInitializer$default);
                }
                irAnonymousInitializerCreateAnonymousInitializer$default.getBody().getStatements().clear();
                irAnonymousInitializerCreateAnonymousInitializer$default.getBody().getStatements().add(throwLinkageError$default(this, declarationWithUnusableClassifier, irAnonymousInitializerCreateAnonymousInitializer$default, false, 2, null));
                List<IrTypeParameter> typeParameters = declaration.getTypeParameters();
                PartiallyLinkedIrTreePatcher partiallyLinkedIrTreePatcher = PartiallyLinkedIrTreePatcher.this;
                for (IrTypeParameter irTypeParameter : typeParameters) {
                    PartiallyLinkedMarkerType partiallyLinkedMarkerTypeOrNull = partiallyLinkedIrTreePatcher.toPartiallyLinkedMarkerTypeOrNull(irTypeParameter.getSuperTypes());
                    if (partiallyLinkedMarkerTypeOrNull != null) {
                        irTypeParameter.setSuperTypes(CollectionsKt.listOf(partiallyLinkedMarkerTypeOrNull));
                    }
                }
                List<IrType> superTypes = declaration.getSuperTypes();
                PartiallyLinkedIrTreePatcher partiallyLinkedIrTreePatcher2 = PartiallyLinkedIrTreePatcher.this;
                ArrayList arrayList = new ArrayList();
                for (Object obj : superTypes) {
                    if (partiallyLinkedIrTreePatcher2.explore((IrType) obj) == null) {
                        arrayList.add(obj);
                    }
                }
                declaration.setSuperTypes(arrayList);
                IrClass irClass = declaration;
                if (!AdditionalIrUtilsKt.isLocal(irClass) && !declaration.getIsInner()) {
                    List<IrDeclaration> declarations = declaration.getDeclarations();
                    if (!(declarations instanceof Collection) || !declarations.isEmpty()) {
                        for (IrDeclaration irDeclaration2 : declarations) {
                            IrClass irClass2 = irDeclaration2 instanceof IrClass ? (IrClass) irDeclaration2 : null;
                            if (irClass2 == null || irClass2.getIsInner()) {
                            }
                        }
                    }
                }
                scheduleForRemoval(irClass);
                return declaration;
            }
            return (IrStatement) transformChildrenWithRemoval(declaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
        public IrStatement visitConstructor(IrConstructor declaration) {
            PartialLinkageCase.InvalidConstructorDelegation declarationWithUnusableClassifier;
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            ExploredClassifier.Unusable unusableRewriteTypesInFunction = rewriteTypesInFunction(declaration);
            PartialLinkageCase.InvalidConstructorDelegation invalidConstructorDelegationCheckConstructorDelegation = checkConstructorDelegation(declaration);
            if (declaration.getOrigin() == PartiallyLinkedDeclarationOrigin.MISSING_DECLARATION) {
                declarationWithUnusableClassifier = new PartialLinkageCase.MissingDeclaration(declaration.getSymbol());
            } else if (unusableRewriteTypesInFunction != null) {
                declarationWithUnusableClassifier = new PartialLinkageCase.DeclarationWithUnusableClassifier(declaration.getSymbol(), unusableRewriteTypesInFunction);
            } else {
                declarationWithUnusableClassifier = invalidConstructorDelegationCheckConstructorDelegation;
            }
            if (declarationWithUnusableClassifier != null) {
                IrBody body = declaration.getBody();
                IrBlockBody irBlockBodyCreateBlockBody = body instanceof IrBlockBody ? (IrBlockBody) body : null;
                if (irBlockBodyCreateBlockBody == null) {
                    irBlockBodyCreateBlockBody = PartiallyLinkedIrTreePatcher.this.builtIns.getIrFactory().createBlockBody(declaration.getStartOffset(), declaration.getEndOffset());
                    declaration.setBody(irBlockBodyCreateBlockBody);
                }
                if (invalidConstructorDelegationCheckConstructorDelegation != null) {
                    List<IrStatement> statements = irBlockBodyCreateBlockBody.getStatements();
                    final PartiallyLinkedIrTreePatcher$DeclarationTransformer$visitConstructor$1 partiallyLinkedIrTreePatcher$DeclarationTransformer$visitConstructor$1 = new Function1<IrStatement, Boolean>() { // from class: org.jetbrains.kotlin.backend.common.linkage.partial.PartiallyLinkedIrTreePatcher$DeclarationTransformer$visitConstructor$1
                        @Override // kotlin.jvm.functions.Function1
                        public final Boolean invoke(IrStatement it) {
                            Intrinsics.checkNotNullParameter(it, "it");
                            return Boolean.valueOf(it instanceof IrDelegatingConstructorCall);
                        }
                    };
                    statements.removeIf(new Predicate() { // from class: org.jetbrains.kotlin.backend.common.linkage.partial.PartiallyLinkedIrTreePatcher$DeclarationTransformer$$ExternalSyntheticLambda0
                        @Override // java.util.function.Predicate
                        public final boolean test(Object obj) {
                            return PartiallyLinkedIrTreePatcher.DeclarationTransformer.visitConstructor$lambda$8(partiallyLinkedIrTreePatcher$DeclarationTransformer$visitConstructor$1, obj);
                        }
                    });
                }
                List<IrStatement> statements2 = irBlockBodyCreateBlockBody.getStatements();
                IrConstructor irConstructor = declaration;
                statements2.add(0, throwLinkageError(declarationWithUnusableClassifier, irConstructor, PartiallyLinkedIrTreePatcher.INSTANCE.isDirectMemberOf(irConstructor, unusableRewriteTypesInFunction)));
            }
            return transformChildren(declaration);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final boolean visitConstructor$lambda$8(Function1 tmp0, Object obj) {
            Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
            return ((Boolean) tmp0.invoke(obj)).booleanValue();
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
        public IrStatement visitSimpleFunction(IrSimpleFunction declaration) {
            PartialLinkageCase.DeclarationWithUnusableClassifier declarationWithUnusableClassifier;
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            IrSimpleFunction irSimpleFunction = declaration;
            filterOverriddenSymbols(irSimpleFunction);
            ExploredClassifier.Unusable unusableRewriteTypesInFunction = rewriteTypesInFunction(declaration);
            IrDeclarationOrigin origin = declaration.getOrigin();
            if (origin == PartiallyLinkedDeclarationOrigin.UNIMPLEMENTED_ABSTRACT_CALLABLE_MEMBER) {
                declarationWithUnusableClassifier = new PartialLinkageCase.UnimplementedAbstractCallable(irSimpleFunction);
            } else if (origin == PartiallyLinkedDeclarationOrigin.AMBIGUOUS_NON_OVERRIDDEN_CALLABLE_MEMBER) {
                declarationWithUnusableClassifier = new PartialLinkageCase.AmbiguousNonOverriddenCallable(irSimpleFunction);
            } else if (origin == PartiallyLinkedDeclarationOrigin.MISSING_DECLARATION) {
                declarationWithUnusableClassifier = new PartialLinkageCase.MissingDeclaration(declaration.getSymbol());
            } else {
                declarationWithUnusableClassifier = unusableRewriteTypesInFunction != null ? new PartialLinkageCase.DeclarationWithUnusableClassifier(declaration.getSymbol(), unusableRewriteTypesInFunction) : null;
            }
            if (declarationWithUnusableClassifier != null) {
                IrBody body = declaration.getBody();
                IrBlockBody irBlockBodyCreateBlockBody = body instanceof IrBlockBody ? (IrBlockBody) body : null;
                if (irBlockBodyCreateBlockBody == null) {
                    irBlockBodyCreateBlockBody = PartiallyLinkedIrTreePatcher.this.builtIns.getIrFactory().createBlockBody(declaration.getStartOffset(), declaration.getEndOffset());
                    declaration.setBody(irBlockBodyCreateBlockBody);
                }
                irBlockBodyCreateBlockBody.getStatements().clear();
                IrSimpleFunction irSimpleFunction2 = declaration;
                irBlockBodyCreateBlockBody.getStatements().add(throwLinkageError(declarationWithUnusableClassifier, irSimpleFunction2, PartiallyLinkedIrTreePatcher.INSTANCE.isDirectMemberOf(irSimpleFunction2, unusableRewriteTypesInFunction)));
                if (!declaration.getIsInline()) {
                    if (AdditionalIrUtilsKt.isTopLevelDeclaration(irSimpleFunction2)) {
                        scheduleForRemoval(irSimpleFunction2);
                    } else {
                        IrPropertySymbol correspondingPropertySymbol = declaration.getCorrespondingPropertySymbol();
                        IrProperty owner = correspondingPropertySymbol != null ? correspondingPropertySymbol.getOwner() : null;
                        if (owner != null && AdditionalIrUtilsKt.isTopLevelDeclaration(owner)) {
                            scheduleForRemoval(owner);
                        }
                    }
                }
                return declaration;
            }
            return transformChildren(declaration);
        }

        /* JADX WARN: Code duplicated, block: B:52:0x00d8  */
        private final PartialLinkageCase.InvalidConstructorDelegation checkConstructorDelegation(IrConstructor irConstructor) {
            List<IrStatement> statements;
            IrClassSymbol anyClass;
            if (irConstructor.getOrigin() == PartiallyLinkedDeclarationOrigin.MISSING_DECLARATION) {
                return null;
            }
            IrBody body = irConstructor.getBody();
            IrBlockBody irBlockBody = body instanceof IrBlockBody ? (IrBlockBody) body : null;
            if (irBlockBody != null && (statements = irBlockBody.getStatements()) != null) {
                IrClass parentAsClass = IrUtilsKt.getParentAsClass(irConstructor);
                IrClassSymbol symbol = parentAsClass.getSymbol();
                Iterator<T> it = parentAsClass.getSuperTypes().iterator();
                do {
                    if (!it.hasNext()) {
                        anyClass = null;
                        break;
                    }
                    IrType irType = (IrType) it.next();
                    IrSimpleType irSimpleType = irType instanceof IrSimpleType ? (IrSimpleType) irType : null;
                    IrClassifierSymbol classifier = irSimpleType != null ? irSimpleType.getClassifier() : null;
                    anyClass = classifier instanceof IrClassSymbol ? (IrClassSymbol) classifier : null;
                    if (anyClass == null || !IrUtilsKt.isClass(anyClass.getOwner())) {
                        anyClass = null;
                    }
                } while (anyClass == null);
                if (anyClass == null) {
                    anyClass = PartiallyLinkedIrTreePatcher.this.builtIns.getAnyClass();
                }
                IrClass owner = anyClass.getOwner();
                PartiallyLinkedIrTreePatcher partiallyLinkedIrTreePatcher = PartiallyLinkedIrTreePatcher.this;
                for (IrStatement irStatement : statements) {
                    if (irStatement instanceof IrDelegatingConstructorCall) {
                        IrConstructorSymbol symbol2 = ((IrDelegatingConstructorCall) irStatement).getSymbol();
                        IrConstructor owner2 = symbol2.getOwner();
                        boolean zAreEqual = true;
                        if (owner2.getOrigin() != PartiallyLinkedDeclarationOrigin.MISSING_DECLARATION) {
                            IrClassSymbol symbol3 = IrUtilsKt.getParentAsClass(owner2).getSymbol();
                            if (Intrinsics.areEqual(symbol3, symbol) || Intrinsics.areEqual(symbol3, anyClass) || (parentAsClass.getIsExternal() && Intrinsics.areEqual(symbol3, partiallyLinkedIrTreePatcher.builtIns.getAnyClass()))) {
                                zAreEqual = false;
                            }
                        } else {
                            IdSignature signature = symbol2.getSignature();
                            IdSignature.CommonSignature commonSignature = signature instanceof IdSignature.CommonSignature ? (IdSignature.CommonSignature) signature : null;
                            if (commonSignature != null) {
                                zAreEqual = true ^ Intrinsics.areEqual(PartialLinkageUtils.DeclarationId.INSTANCE.getDeclarationId(owner), new PartialLinkageUtils.DeclarationId(commonSignature.getPackageFqName(), StringsKt.substringBeforeLast$default(commonSignature.getDeclarationFqName(), '.', (String) null, 2, (Object) null)));
                            } else {
                                zAreEqual = false;
                            }
                        }
                        if (zAreEqual) {
                            return new PartialLinkageCase.InvalidConstructorDelegation(irConstructor.getSymbol(), anyClass, symbol2);
                        }
                    }
                }
            }
            return null;
        }

        private final ExploredClassifier.Unusable rewriteTypesInFunction(IrFunction irFunction) {
            Delegates delegates = Delegates.INSTANCE;
            final Object obj = null;
            ObservableProperty<ExploredClassifier.Unusable> observableProperty = new ObservableProperty<ExploredClassifier.Unusable>(obj) { // from class: org.jetbrains.kotlin.backend.common.linkage.partial.PartiallyLinkedIrTreePatcher$DeclarationTransformer$rewriteTypesInFunction$$inlined$vetoable$1
                @Override // kotlin.properties.ObservableProperty
                protected boolean beforeChange(KProperty<?> property, ExploredClassifier.Unusable oldValue, ExploredClassifier.Unusable newValue) {
                    Intrinsics.checkNotNullParameter(property, "property");
                    return oldValue == null;
                }
            };
            IrValueParameter dispatchReceiverParameter = irFunction.getDispatchReceiverParameter();
            if (dispatchReceiverParameter != null) {
                rewriteTypesInFunction$fixType(dispatchReceiverParameter, PartiallyLinkedIrTreePatcher.this, observableProperty);
            }
            IrValueParameter extensionReceiverParameter = irFunction.getExtensionReceiverParameter();
            if (extensionReceiverParameter != null) {
                rewriteTypesInFunction$fixType(extensionReceiverParameter, PartiallyLinkedIrTreePatcher.this, observableProperty);
            }
            List<IrValueParameter> valueParameters = irFunction.getValueParameters();
            PartiallyLinkedIrTreePatcher partiallyLinkedIrTreePatcher = PartiallyLinkedIrTreePatcher.this;
            Iterator<T> it = valueParameters.iterator();
            while (it.hasNext()) {
                rewriteTypesInFunction$fixType((IrValueParameter) it.next(), partiallyLinkedIrTreePatcher, observableProperty);
            }
            PartiallyLinkedMarkerType partiallyLinkedMarkerTypeOrNull = PartiallyLinkedIrTreePatcher.this.toPartiallyLinkedMarkerTypeOrNull(irFunction.getReturnType());
            if (partiallyLinkedMarkerTypeOrNull != null) {
                irFunction.setReturnType(partiallyLinkedMarkerTypeOrNull);
                rewriteTypesInFunction$lambda$16(observableProperty, partiallyLinkedMarkerTypeOrNull.getUnusableClassifier());
            }
            List<IrTypeParameter> typeParameters = irFunction.getTypeParameters();
            PartiallyLinkedIrTreePatcher partiallyLinkedIrTreePatcher2 = PartiallyLinkedIrTreePatcher.this;
            for (IrTypeParameter irTypeParameter : typeParameters) {
                PartiallyLinkedMarkerType partiallyLinkedMarkerTypeOrNull2 = partiallyLinkedIrTreePatcher2.toPartiallyLinkedMarkerTypeOrNull(irTypeParameter.getSuperTypes());
                if (partiallyLinkedMarkerTypeOrNull2 != null) {
                    irTypeParameter.setSuperTypes(CollectionsKt.listOf(partiallyLinkedMarkerTypeOrNull2));
                    rewriteTypesInFunction$lambda$16(observableProperty, partiallyLinkedMarkerTypeOrNull2.getUnusableClassifier());
                }
            }
            return rewriteTypesInFunction$lambda$15(observableProperty);
        }

        private static final ExploredClassifier.Unusable rewriteTypesInFunction$lambda$15(ReadWriteProperty<Object, ExploredClassifier.Unusable> readWriteProperty) {
            return readWriteProperty.getValue(null, $$delegatedProperties[0]);
        }

        private static final void rewriteTypesInFunction$lambda$16(ReadWriteProperty<Object, ExploredClassifier.Unusable> readWriteProperty, ExploredClassifier.Unusable unusable) {
            readWriteProperty.setValue(null, $$delegatedProperties[0], unusable);
        }

        private static final void rewriteTypesInFunction$fixType(IrValueParameter irValueParameter, PartiallyLinkedIrTreePatcher partiallyLinkedIrTreePatcher, ReadWriteProperty<Object, ExploredClassifier.Unusable> readWriteProperty) {
            PartiallyLinkedMarkerType partiallyLinkedMarkerTypeOrNull = partiallyLinkedIrTreePatcher.toPartiallyLinkedMarkerTypeOrNull(irValueParameter.getType());
            if (partiallyLinkedMarkerTypeOrNull == null) {
                return;
            }
            PartiallyLinkedMarkerType partiallyLinkedMarkerType = partiallyLinkedMarkerTypeOrNull;
            irValueParameter.setType(partiallyLinkedMarkerType);
            if (irValueParameter.getVarargElementType() != null) {
                irValueParameter.setVarargElementType(partiallyLinkedMarkerType);
            }
            irValueParameter.setDefaultValue(null);
            rewriteTypesInFunction$lambda$16(readWriteProperty, partiallyLinkedMarkerTypeOrNull.getUnusableClassifier());
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
        public IrStatement visitProperty(IrProperty declaration) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            filterOverriddenSymbols(declaration);
            return transformChildren(declaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
        public IrStatement visitField(IrField declaration) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            PartiallyLinkedMarkerType partiallyLinkedMarkerTypeOrNull = PartiallyLinkedIrTreePatcher.this.toPartiallyLinkedMarkerTypeOrNull(declaration.getType());
            if (partiallyLinkedMarkerTypeOrNull != null) {
                IrPropertySymbol correspondingPropertySymbol = declaration.getCorrespondingPropertySymbol();
                IrProperty owner = correspondingPropertySymbol != null ? correspondingPropertySymbol.getOwner() : null;
                if (owner != null && AdditionalIrUtilsKt.isTopLevelDeclaration(owner)) {
                    scheduleForRemoval(owner);
                }
                declaration.setType(partiallyLinkedMarkerTypeOrNull);
                declaration.setInitializer(null);
                return declaration;
            }
            return transformChildren(declaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
        public IrStatement visitVariable(IrVariable declaration) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            PartiallyLinkedMarkerType partiallyLinkedMarkerTypeOrNull = PartiallyLinkedIrTreePatcher.this.toPartiallyLinkedMarkerTypeOrNull(declaration.getType());
            if (partiallyLinkedMarkerTypeOrNull != null) {
                declaration.setType(partiallyLinkedMarkerTypeOrNull);
                declaration.setInitializer(null);
                return declaration;
            }
            return transformChildren(declaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
        public IrBody visitBlockBody(IrBlockBody body) {
            Intrinsics.checkNotNullParameter(body, "body");
            return (IrBody) transformChildrenWithRemoval(body);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
        public IrExpression visitContainerExpression(IrContainerExpression expression) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return (IrExpression) transformChildrenWithRemoval(expression);
        }

        private final <S extends IrSymbol> void filterOverriddenSymbols(IrOverridableDeclaration<S> irOverridableDeclaration) {
            if (irOverridableDeclaration.getOverriddenSymbols().isEmpty()) {
                return;
            }
            List<S> overriddenSymbols = irOverridableDeclaration.getOverriddenSymbols();
            ArrayList arrayList = new ArrayList(irOverridableDeclaration.getOverriddenSymbols().size());
            for (Object obj : overriddenSymbols) {
                IrSymbolOwner owner = ((IrSymbol) obj).getOwner();
                Intrinsics.checkNotNull(owner, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrDeclaration");
                IrDeclaration irDeclaration = (IrDeclaration) owner;
                if (irDeclaration.getOrigin() != PartiallyLinkedDeclarationOrigin.MISSING_DECLARATION) {
                    IrDeclarationWithVisibility irDeclarationWithVisibility = irDeclaration instanceof IrDeclarationWithVisibility ? (IrDeclarationWithVisibility) irDeclaration : null;
                    if (!Intrinsics.areEqual(irDeclarationWithVisibility != null ? irDeclarationWithVisibility.getVisibility() : null, DescriptorVisibilities.PRIVATE)) {
                        arrayList.add(obj);
                    }
                }
            }
            irOverridableDeclaration.setOverriddenSymbols(org.jetbrains.kotlin.utils.CollectionsKt.compact(arrayList));
        }

        static /* synthetic */ IrCall throwLinkageError$default(DeclarationTransformer declarationTransformer, PartialLinkageCase partialLinkageCase, IrDeclaration irDeclaration, boolean z, int i, Object obj) {
            if ((i & 2) != 0) {
                z = false;
            }
            return declarationTransformer.throwLinkageError(partialLinkageCase, irDeclaration, z);
        }

        private final IrCall throwLinkageError(PartialLinkageCase partialLinkageCase, IrDeclaration irDeclaration, boolean z) {
            return PartiallyLinkedIrTreePatcher.this.getSupportForLowerings().throwLinkageError(partialLinkageCase, irDeclaration, getCurrentFile(), z);
        }
    }

    /* JADX INFO: compiled from: PartiallyLinkedIrTreePatcher.kt */
    @Metadata(d1 = {"\u0000\u0082\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\b\u0092\u0004\u0018\u00002\u00020\u0001B\u000f\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0016J\u0010\u0010\r\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u000eH\u0016J\u0010\u0010\u000f\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0010H\u0016J\u0010\u0010\u0011\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0012H\u0016J\u0010\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0016H\u0016J\u0010\u0010\u0017\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0018H\u0016J\u0010\u0010\u0019\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u001aH\u0016J\u0010\u0010\u001b\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u001cH\u0016J\u0010\u0010\u001d\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0006H\u0016J\u0010\u0010\u001e\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u001fH\u0016J\u0010\u0010 \u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020!H\u0016J\u0010\u0010\"\u001a\u00020#2\u0006\u0010\u0007\u001a\u00020#H\u0016J\u0014\u0010$\u001a\u00020\u00062\n\u0010\u0007\u001a\u0006\u0012\u0002\b\u00030%H\u0016J\u0010\u0010&\u001a\u00020'2\u0006\u0010\u0015\u001a\u00020'H\u0016J\u0010\u0010(\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020)H\u0016J\u0010\u0010*\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020+H\u0016J\u0010\u0010,\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020-H\u0016JT\u0010.\u001a\u0004\u0018\u00010/*\b\u0012\u0004\u0012\u0002000%2:\b\u0002\u00101\u001a4\u0012\u0013\u0012\u001103¢\u0006\f\b4\u0012\b\b5\u0012\u0004\b\b(6\u0012\u0015\u0012\u0013\u0018\u000107¢\u0006\f\b4\u0012\b\b5\u0012\u0004\b\b(8\u0012\u0004\u0012\u00020902H\u0084\bø\u0001\u0000J\u0016\u0010:\u001a\u0004\u0018\u00010/*\u00020\u00062\u0006\u0010;\u001a\u00020<H\u0002J\u0012\u0010=\u001a\u0004\u0018\u00010/*\u0006\u0012\u0002\b\u00030%H\u0002J\u000e\u0010>\u001a\u0004\u0018\u00010/*\u00020\u0012H\u0002J\"\u0010?\u001a\u0004\u0018\u00010/*\u00020\u00062\b\u0010@\u001a\u0004\u0018\u00010A2\b\b\u0002\u0010B\u001a\u000209H\u0002JA\u0010C\u001a\u0004\u0018\u00010/\"\b\b\u0000\u0010D*\u00020E*\u00020\u00062\u0006\u0010\u0015\u001a\u0002HD2\u0006\u0010F\u001a\u00020G2\u0012\u0010H\u001a\u000e\u0012\u0004\u0012\u0002HD\u0012\u0004\u0012\u0002090IH\u0004¢\u0006\u0002\u0010JJ\u000e\u0010K\u001a\u0004\u0018\u00010/*\u00020-H\u0002J\u000e\u0010L\u001a\u0004\u0018\u00010/*\u00020\u0012H\u0014J\u001f\u0010M\u001a\u00020N\"\f\b\u0000\u0010O*\u00020P*\u00020Q*\u0002HOH\u0002¢\u0006\u0002\u0010RJ7\u0010S\u001a\u00020\u0006\"\b\b\u0000\u0010O*\u00020\u0006*\u0002HO2\u0019\u0010T\u001a\u0015\u0012\u0004\u0012\u0002HO\u0012\u0006\u0012\u0004\u0018\u00010/0I¢\u0006\u0002\bUH\u0082\b¢\u0006\u0002\u0010VJ\f\u0010W\u001a\u00020N*\u00020\u0006H\u0014J\u0014\u0010X\u001a\u0004\u0018\u00010Y*\b\u0012\u0004\u0012\u00020<0ZH\u0002J\u000e\u0010X\u001a\u0004\u0018\u00010Y*\u00020<H\u0002\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006[²\u0006\u0010\u0010\\\u001a\b\u0012\u0004\u0012\u00020]0ZX\u008a\u0084\u0002"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/linkage/partial/PartiallyLinkedIrTreePatcher$ExpressionTransformer;", "Lorg/jetbrains/kotlin/backend/common/linkage/partial/FileAwareIrElementTransformerVoid;", "startingFile", "Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageUtils$File;", "(Lorg/jetbrains/kotlin/backend/common/linkage/partial/PartiallyLinkedIrTreePatcher;Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageUtils$File;)V", "visitBlock", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrBlock;", "visitBlockBody", "Lorg/jetbrains/kotlin/ir/expressions/IrBody;", "body", "Lorg/jetbrains/kotlin/ir/expressions/IrBlockBody;", "visitCall", "Lorg/jetbrains/kotlin/ir/expressions/IrCall;", "visitClassReference", "Lorg/jetbrains/kotlin/ir/expressions/IrClassReference;", "visitConstructorCall", "Lorg/jetbrains/kotlin/ir/expressions/IrConstructorCall;", "visitDeclaration", "Lorg/jetbrains/kotlin/ir/IrStatement;", "declaration", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationBase;", "visitDeclarationReference", "Lorg/jetbrains/kotlin/ir/expressions/IrDeclarationReference;", "visitDelegatingConstructorCall", "Lorg/jetbrains/kotlin/ir/expressions/IrDelegatingConstructorCall;", "visitEnumConstructorCall", "Lorg/jetbrains/kotlin/ir/expressions/IrEnumConstructorCall;", "visitExpression", "visitFunctionReference", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionReference;", "visitGetObjectValue", "Lorg/jetbrains/kotlin/ir/expressions/IrGetObjectValue;", "visitInstanceInitializerCall", "Lorg/jetbrains/kotlin/ir/expressions/IrInstanceInitializerCall;", "visitMemberAccess", "Lorg/jetbrains/kotlin/ir/expressions/IrMemberAccessExpression;", "visitPackageFragment", "Lorg/jetbrains/kotlin/ir/declarations/IrPackageFragment;", "visitPropertyReference", "Lorg/jetbrains/kotlin/ir/expressions/IrPropertyReference;", "visitReturn", "Lorg/jetbrains/kotlin/ir/expressions/IrReturn;", "visitTypeOperator", "Lorg/jetbrains/kotlin/ir/expressions/IrTypeOperatorCall;", "checkArgumentsAndValueParameters", "Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageCase;", "Lorg/jetbrains/kotlin/ir/symbols/IrFunctionSymbol;", "checkDefaultArgument", "Lkotlin/Function2;", "", "Lkotlin/ParameterName;", "name", "index", "Lorg/jetbrains/kotlin/ir/expressions/IrExpressionBody;", "defaultArgumentExpressionBody", "", "checkExpressionType", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/ir/types/IrType;", "checkExpressionTypeArguments", "checkNotAbstractClass", "checkReferencedDeclaration", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "checkVisibility", "checkReferencedDeclarationType", "D", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "expectedDeclarationDescription", "", "checkDeclarationType", "Lkotlin/Function1;", "(Lorg/jetbrains/kotlin/ir/expressions/IrExpression;Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;Ljava/lang/String;Lkotlin/jvm/functions/Function1;)Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageCase;", "checkSamConversion", "customConstructorCallChecks", "filterUnusableAnnotations", "", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/ir/declarations/IrMutableAnnotationContainer;", "Lorg/jetbrains/kotlin/ir/declarations/IrSymbolOwner;", "(Lorg/jetbrains/kotlin/ir/declarations/IrMutableAnnotationContainer;)V", "maybeThrowLinkageError", "computePartialLinkageCase", "Lkotlin/ExtensionFunctionType;", "(Lorg/jetbrains/kotlin/ir/expressions/IrExpression;Lkotlin/jvm/functions/Function1;)Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "onAfterMaybeThrowLinkageError", "precalculatedUnusableClassifier", "Lorg/jetbrains/kotlin/ir/linkage/partial/ExploredClassifier$Unusable;", "", "ir.serialization.common", "functionsToCheckDefaultValues", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private class ExpressionTransformer extends FileAwareIrElementTransformerVoid {
        protected void onAfterMaybeThrowLinkageError(IrExpression irExpression) {
            Intrinsics.checkNotNullParameter(irExpression, "<this>");
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
        public IrInstanceInitializerCall visitInstanceInitializerCall(IrInstanceInitializerCall expression) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return expression;
        }

        public ExpressionTransformer(org.jetbrains.kotlin.ir.linkage.partial.PartialLinkageUtils.File file) {
            super(file);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
        public IrPackageFragment visitPackageFragment(IrPackageFragment declaration) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            IrFile irFile = declaration instanceof IrFile ? (IrFile) declaration : null;
            if (irFile != null) {
                filterUnusableAnnotations(irFile);
            }
            return super.visitPackageFragment(declaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
        public IrStatement visitDeclaration(IrDeclarationBase declaration) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            if (declaration.getOrigin() instanceof PartiallyLinkedDeclarationOrigin) {
                return declaration;
            }
            filterUnusableAnnotations(declaration);
            return super.visitDeclaration(declaration);
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
        public IrBody visitBlockBody(IrBlockBody body) {
            List<IrStatement> statements;
            Intrinsics.checkNotNullParameter(body, "body");
            IrBody irBodyVisitBlockBody = super.visitBlockBody(body);
            IrStatementContainer irStatementContainer = irBodyVisitBlockBody instanceof IrStatementContainer ? (IrStatementContainer) irBodyVisitBlockBody : null;
            if (irStatementContainer != null && (statements = irStatementContainer.getStatements()) != null) {
                PartiallyLinkedIrTreePatcher.INSTANCE.eliminateDeadCodeStatements(statements);
            }
            return irBodyVisitBlockBody;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
        public IrExpression visitReturn(IrReturn expression) {
            DirectChildren empty;
            IrCall irCall;
            List<IrStatement> statements;
            Intrinsics.checkNotNullParameter(expression, "expression");
            IrReturn irReturn = expression;
            PartiallyLinkedIrTreePatcher partiallyLinkedIrTreePatcher = PartiallyLinkedIrTreePatcher.this;
            IrReturn irReturn2 = irReturn;
            ExpressionTransformer expressionTransformer = this;
            IrElementTransformerVoidKt.transformChildrenVoid(irReturn2, expressionTransformer);
            IrReturn irReturn3 = irReturn;
            PartialLinkageCase partialLinkageCaseCheckReferencedDeclaration = checkReferencedDeclaration(irReturn3, irReturn3.getReturnTargetSymbol(), false);
            if (partialLinkageCaseCheckReferencedDeclaration == null) {
                partialLinkageCaseCheckReferencedDeclaration = checkExpressionType(irReturn, irReturn.getType());
            }
            if (partialLinkageCaseCheckReferencedDeclaration != null) {
                if (PartiallyLinkedIrTreePatcher.INSTANCE.hasBranches(irReturn)) {
                    empty = DirectChildren.INSTANCE.getEMPTY();
                } else {
                    DirectChildrenStatementsCollector directChildrenStatementsCollector = new DirectChildrenStatementsCollector();
                    IrVisitorsKt.acceptChildrenVoid(irReturn2, directChildrenStatementsCollector);
                    empty = directChildrenStatementsCollector.getResult();
                }
                DirectChildren directChildren = empty;
                IrCall irCallThrowLinkageError$default = PartialLinkageSupportForLowerings.DefaultImpls.throwLinkageError$default(partiallyLinkedIrTreePatcher.getSupportForLowerings(), partialLinkageCaseCheckReferencedDeclaration, irReturn2, expressionTransformer.getCurrentFile(), false, 8, null);
                if (!directChildren.getStatements().isEmpty()) {
                    IrCompositeImpl irCompositeImpl = new IrCompositeImpl(irReturn.getStartOffset(), irReturn.getEndOffset(), partiallyLinkedIrTreePatcher.builtIns.getNothingType(), IrStatementOrigin.PARTIAL_LINKAGE_RUNTIME_ERROR.INSTANCE);
                    CollectionsKt.addAll(irCompositeImpl.getStatements(), directChildren.getStatements());
                    if (!directChildren.getHasPartialLinkageRuntimeError()) {
                        irCompositeImpl.getStatements().add(irCallThrowLinkageError$default);
                    }
                    irCall = irCompositeImpl;
                } else {
                    irCall = irCallThrowLinkageError$default;
                }
            } else {
                IrContainerExpression irContainerExpression = irReturn instanceof IrContainerExpression ? (IrContainerExpression) irReturn : null;
                if (irContainerExpression != null && (statements = irContainerExpression.getStatements()) != null) {
                    PartiallyLinkedIrTreePatcher.INSTANCE.eliminateDeadCodeStatements(statements);
                }
                irCall = irReturn;
            }
            onAfterMaybeThrowLinkageError(irReturn);
            return irCall;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
        public IrExpression visitBlock(IrBlock expression) {
            DirectChildren empty;
            IrCall irCall;
            List<IrStatement> statements;
            Intrinsics.checkNotNullParameter(expression, "expression");
            IrBlock irBlock = expression;
            PartiallyLinkedIrTreePatcher partiallyLinkedIrTreePatcher = PartiallyLinkedIrTreePatcher.this;
            IrBlock irBlock2 = irBlock;
            ExpressionTransformer expressionTransformer = this;
            IrElementTransformerVoidKt.transformChildrenVoid(irBlock2, expressionTransformer);
            IrBlock irBlock3 = irBlock;
            PartialLinkageCase partialLinkageCaseCheckReferencedDeclaration = irBlock3 instanceof IrReturnableBlock ? checkReferencedDeclaration(irBlock3, ((IrReturnableBlock) irBlock3).getSymbol(), false) : null;
            if (partialLinkageCaseCheckReferencedDeclaration == null) {
                partialLinkageCaseCheckReferencedDeclaration = checkExpressionType(irBlock, irBlock.getType());
            }
            if (partialLinkageCaseCheckReferencedDeclaration != null) {
                if (PartiallyLinkedIrTreePatcher.INSTANCE.hasBranches(irBlock)) {
                    empty = DirectChildren.INSTANCE.getEMPTY();
                } else {
                    DirectChildrenStatementsCollector directChildrenStatementsCollector = new DirectChildrenStatementsCollector();
                    IrVisitorsKt.acceptChildrenVoid(irBlock2, directChildrenStatementsCollector);
                    empty = directChildrenStatementsCollector.getResult();
                }
                DirectChildren directChildren = empty;
                IrCall irCallThrowLinkageError$default = PartialLinkageSupportForLowerings.DefaultImpls.throwLinkageError$default(partiallyLinkedIrTreePatcher.getSupportForLowerings(), partialLinkageCaseCheckReferencedDeclaration, irBlock2, expressionTransformer.getCurrentFile(), false, 8, null);
                if (!directChildren.getStatements().isEmpty()) {
                    IrCompositeImpl irCompositeImpl = new IrCompositeImpl(irBlock.getStartOffset(), irBlock.getEndOffset(), partiallyLinkedIrTreePatcher.builtIns.getNothingType(), IrStatementOrigin.PARTIAL_LINKAGE_RUNTIME_ERROR.INSTANCE);
                    CollectionsKt.addAll(irCompositeImpl.getStatements(), directChildren.getStatements());
                    if (!directChildren.getHasPartialLinkageRuntimeError()) {
                        irCompositeImpl.getStatements().add(irCallThrowLinkageError$default);
                    }
                    irCall = irCompositeImpl;
                } else {
                    irCall = irCallThrowLinkageError$default;
                }
            } else {
                IrBlock irBlock4 = irBlock instanceof IrContainerExpression ? irBlock : null;
                if (irBlock4 != null && (statements = irBlock4.getStatements()) != null) {
                    PartiallyLinkedIrTreePatcher.INSTANCE.eliminateDeadCodeStatements(statements);
                }
                irCall = irBlock;
            }
            onAfterMaybeThrowLinkageError(irBlock);
            return irCall;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
        public IrExpression visitTypeOperator(IrTypeOperatorCall expression) {
            DirectChildren empty;
            IrCall irCall;
            List<IrStatement> statements;
            Intrinsics.checkNotNullParameter(expression, "expression");
            IrTypeOperatorCall irTypeOperatorCall = expression;
            PartiallyLinkedIrTreePatcher partiallyLinkedIrTreePatcher = PartiallyLinkedIrTreePatcher.this;
            IrTypeOperatorCall irTypeOperatorCall2 = irTypeOperatorCall;
            ExpressionTransformer expressionTransformer = this;
            IrElementTransformerVoidKt.transformChildrenVoid(irTypeOperatorCall2, expressionTransformer);
            IrTypeOperatorCall irTypeOperatorCall3 = irTypeOperatorCall;
            PartialLinkageCase partialLinkageCaseCheckExpressionType = irTypeOperatorCall3.getTypeOperand() != irTypeOperatorCall3.getType() ? checkExpressionType(irTypeOperatorCall3, irTypeOperatorCall3.getTypeOperand()) : null;
            if (partialLinkageCaseCheckExpressionType == null) {
                partialLinkageCaseCheckExpressionType = checkSamConversion(irTypeOperatorCall3);
            }
            PartialLinkageCase partialLinkageCaseCheckExpressionType2 = partialLinkageCaseCheckExpressionType == null ? checkExpressionType(irTypeOperatorCall, irTypeOperatorCall.getType()) : partialLinkageCaseCheckExpressionType;
            if (partialLinkageCaseCheckExpressionType2 != null) {
                if (PartiallyLinkedIrTreePatcher.INSTANCE.hasBranches(irTypeOperatorCall)) {
                    empty = DirectChildren.INSTANCE.getEMPTY();
                } else {
                    DirectChildrenStatementsCollector directChildrenStatementsCollector = new DirectChildrenStatementsCollector();
                    IrVisitorsKt.acceptChildrenVoid(irTypeOperatorCall2, directChildrenStatementsCollector);
                    empty = directChildrenStatementsCollector.getResult();
                }
                DirectChildren directChildren = empty;
                IrCall irCallThrowLinkageError$default = PartialLinkageSupportForLowerings.DefaultImpls.throwLinkageError$default(partiallyLinkedIrTreePatcher.getSupportForLowerings(), partialLinkageCaseCheckExpressionType2, irTypeOperatorCall2, expressionTransformer.getCurrentFile(), false, 8, null);
                if (!directChildren.getStatements().isEmpty()) {
                    IrCompositeImpl irCompositeImpl = new IrCompositeImpl(irTypeOperatorCall.getStartOffset(), irTypeOperatorCall.getEndOffset(), partiallyLinkedIrTreePatcher.builtIns.getNothingType(), IrStatementOrigin.PARTIAL_LINKAGE_RUNTIME_ERROR.INSTANCE);
                    CollectionsKt.addAll(irCompositeImpl.getStatements(), directChildren.getStatements());
                    if (!directChildren.getHasPartialLinkageRuntimeError()) {
                        irCompositeImpl.getStatements().add(irCallThrowLinkageError$default);
                    }
                    irCall = irCompositeImpl;
                } else {
                    irCall = irCallThrowLinkageError$default;
                }
            } else {
                IrContainerExpression irContainerExpression = irTypeOperatorCall instanceof IrContainerExpression ? (IrContainerExpression) irTypeOperatorCall : null;
                if (irContainerExpression != null && (statements = irContainerExpression.getStatements()) != null) {
                    PartiallyLinkedIrTreePatcher.INSTANCE.eliminateDeadCodeStatements(statements);
                }
                irCall = irTypeOperatorCall;
            }
            onAfterMaybeThrowLinkageError(irTypeOperatorCall);
            return irCall;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
        public IrExpression visitDeclarationReference(IrDeclarationReference expression) {
            DirectChildren empty;
            IrCall irCall;
            List<IrStatement> statements;
            Intrinsics.checkNotNullParameter(expression, "expression");
            IrDeclarationReference irDeclarationReference = expression;
            PartiallyLinkedIrTreePatcher partiallyLinkedIrTreePatcher = PartiallyLinkedIrTreePatcher.this;
            IrDeclarationReference irDeclarationReference2 = irDeclarationReference;
            ExpressionTransformer expressionTransformer = this;
            IrElementTransformerVoidKt.transformChildrenVoid(irDeclarationReference2, expressionTransformer);
            IrDeclarationReference irDeclarationReference3 = irDeclarationReference;
            PartialLinkageCase partialLinkageCaseCheckReferencedDeclaration$default = checkReferencedDeclaration$default(this, irDeclarationReference3, irDeclarationReference3.getSymbol(), false, 2, null);
            if (partialLinkageCaseCheckReferencedDeclaration$default == null) {
                partialLinkageCaseCheckReferencedDeclaration$default = checkExpressionType(irDeclarationReference, irDeclarationReference.getType());
            }
            if (partialLinkageCaseCheckReferencedDeclaration$default != null) {
                if (PartiallyLinkedIrTreePatcher.INSTANCE.hasBranches(irDeclarationReference)) {
                    empty = DirectChildren.INSTANCE.getEMPTY();
                } else {
                    DirectChildrenStatementsCollector directChildrenStatementsCollector = new DirectChildrenStatementsCollector();
                    IrVisitorsKt.acceptChildrenVoid(irDeclarationReference2, directChildrenStatementsCollector);
                    empty = directChildrenStatementsCollector.getResult();
                }
                DirectChildren directChildren = empty;
                IrCall irCallThrowLinkageError$default = PartialLinkageSupportForLowerings.DefaultImpls.throwLinkageError$default(partiallyLinkedIrTreePatcher.getSupportForLowerings(), partialLinkageCaseCheckReferencedDeclaration$default, irDeclarationReference2, expressionTransformer.getCurrentFile(), false, 8, null);
                if (!directChildren.getStatements().isEmpty()) {
                    IrCompositeImpl irCompositeImpl = new IrCompositeImpl(irDeclarationReference.getStartOffset(), irDeclarationReference.getEndOffset(), partiallyLinkedIrTreePatcher.builtIns.getNothingType(), IrStatementOrigin.PARTIAL_LINKAGE_RUNTIME_ERROR.INSTANCE);
                    CollectionsKt.addAll(irCompositeImpl.getStatements(), directChildren.getStatements());
                    if (!directChildren.getHasPartialLinkageRuntimeError()) {
                        irCompositeImpl.getStatements().add(irCallThrowLinkageError$default);
                    }
                    irCall = irCompositeImpl;
                } else {
                    irCall = irCallThrowLinkageError$default;
                }
            } else {
                IrContainerExpression irContainerExpression = irDeclarationReference instanceof IrContainerExpression ? (IrContainerExpression) irDeclarationReference : null;
                if (irContainerExpression != null && (statements = irContainerExpression.getStatements()) != null) {
                    PartiallyLinkedIrTreePatcher.INSTANCE.eliminateDeadCodeStatements(statements);
                }
                irCall = irDeclarationReference;
            }
            onAfterMaybeThrowLinkageError(irDeclarationReference);
            return irCall;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
        public IrExpression visitClassReference(IrClassReference expression) {
            DirectChildren empty;
            IrCall irCall;
            List<IrStatement> statements;
            Intrinsics.checkNotNullParameter(expression, "expression");
            IrClassReference irClassReference = expression;
            PartiallyLinkedIrTreePatcher partiallyLinkedIrTreePatcher = PartiallyLinkedIrTreePatcher.this;
            IrClassReference irClassReference2 = irClassReference;
            ExpressionTransformer expressionTransformer = this;
            IrElementTransformerVoidKt.transformChildrenVoid(irClassReference2, expressionTransformer);
            IrClassReference irClassReference3 = irClassReference;
            IrClassReference irClassReference4 = irClassReference3;
            PartialLinkageCase partialLinkageCaseCheckReferencedDeclaration$default = checkReferencedDeclaration$default(this, irClassReference4, irClassReference3.getSymbol(), false, 2, null);
            if (partialLinkageCaseCheckReferencedDeclaration$default == null) {
                partialLinkageCaseCheckReferencedDeclaration$default = checkExpressionType(irClassReference4, irClassReference3.getClassType());
            }
            PartialLinkageCase partialLinkageCaseCheckExpressionType = partialLinkageCaseCheckReferencedDeclaration$default == null ? checkExpressionType(irClassReference, irClassReference.getType()) : partialLinkageCaseCheckReferencedDeclaration$default;
            if (partialLinkageCaseCheckExpressionType != null) {
                if (PartiallyLinkedIrTreePatcher.INSTANCE.hasBranches(irClassReference)) {
                    empty = DirectChildren.INSTANCE.getEMPTY();
                } else {
                    DirectChildrenStatementsCollector directChildrenStatementsCollector = new DirectChildrenStatementsCollector();
                    IrVisitorsKt.acceptChildrenVoid(irClassReference2, directChildrenStatementsCollector);
                    empty = directChildrenStatementsCollector.getResult();
                }
                DirectChildren directChildren = empty;
                IrCall irCallThrowLinkageError$default = PartialLinkageSupportForLowerings.DefaultImpls.throwLinkageError$default(partiallyLinkedIrTreePatcher.getSupportForLowerings(), partialLinkageCaseCheckExpressionType, irClassReference2, expressionTransformer.getCurrentFile(), false, 8, null);
                if (!directChildren.getStatements().isEmpty()) {
                    IrCompositeImpl irCompositeImpl = new IrCompositeImpl(irClassReference.getStartOffset(), irClassReference.getEndOffset(), partiallyLinkedIrTreePatcher.builtIns.getNothingType(), IrStatementOrigin.PARTIAL_LINKAGE_RUNTIME_ERROR.INSTANCE);
                    CollectionsKt.addAll(irCompositeImpl.getStatements(), directChildren.getStatements());
                    if (!directChildren.getHasPartialLinkageRuntimeError()) {
                        irCompositeImpl.getStatements().add(irCallThrowLinkageError$default);
                    }
                    irCall = irCompositeImpl;
                } else {
                    irCall = irCallThrowLinkageError$default;
                }
            } else {
                IrContainerExpression irContainerExpression = irClassReference instanceof IrContainerExpression ? (IrContainerExpression) irClassReference : null;
                if (irContainerExpression != null && (statements = irContainerExpression.getStatements()) != null) {
                    PartiallyLinkedIrTreePatcher.INSTANCE.eliminateDeadCodeStatements(statements);
                }
                irCall = irClassReference;
            }
            onAfterMaybeThrowLinkageError(irClassReference);
            return irCall;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
        public IrExpression visitGetObjectValue(IrGetObjectValue expression) {
            DirectChildren empty;
            IrCall irCall;
            List<IrStatement> statements;
            Intrinsics.checkNotNullParameter(expression, "expression");
            IrGetObjectValue irGetObjectValue = expression;
            PartiallyLinkedIrTreePatcher partiallyLinkedIrTreePatcher = PartiallyLinkedIrTreePatcher.this;
            IrGetObjectValue irGetObjectValue2 = irGetObjectValue;
            ExpressionTransformer expressionTransformer = this;
            IrElementTransformerVoidKt.transformChildrenVoid(irGetObjectValue2, expressionTransformer);
            IrGetObjectValue irGetObjectValue3 = irGetObjectValue;
            IrGetObjectValue irGetObjectValue4 = irGetObjectValue3;
            PartialLinkageCase partialLinkageCaseCheckReferencedDeclaration$default = checkReferencedDeclaration$default(this, irGetObjectValue4, irGetObjectValue3.getSymbol(), false, 2, null);
            if (partialLinkageCaseCheckReferencedDeclaration$default == null) {
                partialLinkageCaseCheckReferencedDeclaration$default = checkReferencedDeclarationType(irGetObjectValue4, irGetObjectValue3.getSymbol().getOwner(), "object", new Function1<IrClass, Boolean>() { // from class: org.jetbrains.kotlin.backend.common.linkage.partial.PartiallyLinkedIrTreePatcher$ExpressionTransformer$visitGetObjectValue$1$1
                    @Override // kotlin.jvm.functions.Function1
                    public final Boolean invoke(IrClass it) {
                        Intrinsics.checkNotNullParameter(it, "it");
                        return Boolean.valueOf(it.getKind() == ClassKind.OBJECT);
                    }
                });
            }
            PartialLinkageCase partialLinkageCaseCheckExpressionType = partialLinkageCaseCheckReferencedDeclaration$default == null ? checkExpressionType(irGetObjectValue, irGetObjectValue.getType()) : partialLinkageCaseCheckReferencedDeclaration$default;
            if (partialLinkageCaseCheckExpressionType != null) {
                if (PartiallyLinkedIrTreePatcher.INSTANCE.hasBranches(irGetObjectValue)) {
                    empty = DirectChildren.INSTANCE.getEMPTY();
                } else {
                    DirectChildrenStatementsCollector directChildrenStatementsCollector = new DirectChildrenStatementsCollector();
                    IrVisitorsKt.acceptChildrenVoid(irGetObjectValue2, directChildrenStatementsCollector);
                    empty = directChildrenStatementsCollector.getResult();
                }
                DirectChildren directChildren = empty;
                IrCall irCallThrowLinkageError$default = PartialLinkageSupportForLowerings.DefaultImpls.throwLinkageError$default(partiallyLinkedIrTreePatcher.getSupportForLowerings(), partialLinkageCaseCheckExpressionType, irGetObjectValue2, expressionTransformer.getCurrentFile(), false, 8, null);
                if (!directChildren.getStatements().isEmpty()) {
                    IrCompositeImpl irCompositeImpl = new IrCompositeImpl(irGetObjectValue.getStartOffset(), irGetObjectValue.getEndOffset(), partiallyLinkedIrTreePatcher.builtIns.getNothingType(), IrStatementOrigin.PARTIAL_LINKAGE_RUNTIME_ERROR.INSTANCE);
                    CollectionsKt.addAll(irCompositeImpl.getStatements(), directChildren.getStatements());
                    if (!directChildren.getHasPartialLinkageRuntimeError()) {
                        irCompositeImpl.getStatements().add(irCallThrowLinkageError$default);
                    }
                    irCall = irCompositeImpl;
                } else {
                    irCall = irCallThrowLinkageError$default;
                }
            } else {
                IrContainerExpression irContainerExpression = irGetObjectValue instanceof IrContainerExpression ? (IrContainerExpression) irGetObjectValue : null;
                if (irContainerExpression != null && (statements = irContainerExpression.getStatements()) != null) {
                    PartiallyLinkedIrTreePatcher.INSTANCE.eliminateDeadCodeStatements(statements);
                }
                irCall = irGetObjectValue;
            }
            onAfterMaybeThrowLinkageError(irGetObjectValue);
            return irCall;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
        public IrExpression visitMemberAccess(IrMemberAccessExpression<?> expression) {
            DirectChildren empty;
            IrCall irCall;
            List<IrStatement> statements;
            Intrinsics.checkNotNullParameter(expression, "expression");
            IrMemberAccessExpression<?> irMemberAccessExpression = expression;
            PartiallyLinkedIrTreePatcher partiallyLinkedIrTreePatcher = PartiallyLinkedIrTreePatcher.this;
            IrMemberAccessExpression<?> irMemberAccessExpression2 = irMemberAccessExpression;
            ExpressionTransformer expressionTransformer = this;
            IrElementTransformerVoidKt.transformChildrenVoid(irMemberAccessExpression2, expressionTransformer);
            IrMemberAccessExpression<?> irMemberAccessExpression3 = irMemberAccessExpression;
            PartialLinkageCase partialLinkageCaseCheckReferencedDeclaration$default = checkReferencedDeclaration$default(this, irMemberAccessExpression3, irMemberAccessExpression3.getSymbol(), false, 2, null);
            if (partialLinkageCaseCheckReferencedDeclaration$default == null) {
                partialLinkageCaseCheckReferencedDeclaration$default = checkExpressionTypeArguments(irMemberAccessExpression3);
            }
            PartialLinkageCase partialLinkageCaseCheckExpressionType = partialLinkageCaseCheckReferencedDeclaration$default == null ? checkExpressionType(irMemberAccessExpression, irMemberAccessExpression.getType()) : partialLinkageCaseCheckReferencedDeclaration$default;
            if (partialLinkageCaseCheckExpressionType != null) {
                if (PartiallyLinkedIrTreePatcher.INSTANCE.hasBranches(irMemberAccessExpression)) {
                    empty = DirectChildren.INSTANCE.getEMPTY();
                } else {
                    DirectChildrenStatementsCollector directChildrenStatementsCollector = new DirectChildrenStatementsCollector();
                    IrVisitorsKt.acceptChildrenVoid(irMemberAccessExpression2, directChildrenStatementsCollector);
                    empty = directChildrenStatementsCollector.getResult();
                }
                DirectChildren directChildren = empty;
                IrCall irCallThrowLinkageError$default = PartialLinkageSupportForLowerings.DefaultImpls.throwLinkageError$default(partiallyLinkedIrTreePatcher.getSupportForLowerings(), partialLinkageCaseCheckExpressionType, irMemberAccessExpression2, expressionTransformer.getCurrentFile(), false, 8, null);
                if (!directChildren.getStatements().isEmpty()) {
                    IrCompositeImpl irCompositeImpl = new IrCompositeImpl(irMemberAccessExpression.getStartOffset(), irMemberAccessExpression.getEndOffset(), partiallyLinkedIrTreePatcher.builtIns.getNothingType(), IrStatementOrigin.PARTIAL_LINKAGE_RUNTIME_ERROR.INSTANCE);
                    CollectionsKt.addAll(irCompositeImpl.getStatements(), directChildren.getStatements());
                    if (!directChildren.getHasPartialLinkageRuntimeError()) {
                        irCompositeImpl.getStatements().add(irCallThrowLinkageError$default);
                    }
                    irCall = irCompositeImpl;
                } else {
                    irCall = irCallThrowLinkageError$default;
                }
            } else {
                IrContainerExpression irContainerExpression = irMemberAccessExpression instanceof IrContainerExpression ? (IrContainerExpression) irMemberAccessExpression : null;
                if (irContainerExpression != null && (statements = irContainerExpression.getStatements()) != null) {
                    PartiallyLinkedIrTreePatcher.INSTANCE.eliminateDeadCodeStatements(statements);
                }
                irCall = irMemberAccessExpression;
            }
            onAfterMaybeThrowLinkageError(irMemberAccessExpression);
            return irCall;
        }

        /* JADX WARN: Code duplicated, block: B:102:0x01a1  */
        /* JADX WARN: Code duplicated, block: B:104:0x01b3  */
        /* JADX WARN: Code duplicated, block: B:105:0x01b8  */
        /* JADX WARN: Code duplicated, block: B:111:0x01c0  */
        /* JADX WARN: Code duplicated, block: B:120:0x01e4  */
        /* JADX WARN: Code duplicated, block: B:147:0x01d2 A[SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:148:0x01d2 A[ADDED_TO_REGION, REMOVE, SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:152:0x01d6 A[SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:156:0x017d A[SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:158:0x01bc A[SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:18:0x0080  */
        /* JADX WARN: Code duplicated, block: B:32:0x00a3  */
        /* JADX WARN: Code duplicated, block: B:61:0x00fb  */
        /* JADX WARN: Code duplicated, block: B:88:0x0156  */
        /* JADX WARN: Code duplicated, block: B:90:0x016f  */
        /* JADX WARN: Code duplicated, block: B:93:0x0179  */
        /* JADX WARN: Code duplicated, block: B:96:0x0183  */
        /* JADX WARN: Code duplicated, block: B:99:0x0191  */
        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
        public IrExpression visitCall(IrCall expression) {
            DirectChildren empty;
            IrCall irCall;
            List<IrStatement> statements;
            IrCall irCall2;
            boolean zIsSubtypeOfClass;
            IrDeclarationParent parent;
            IrClass irClass;
            IrClassSymbol symbol;
            Lazy lazy;
            Iterable iterableUntil;
            Iterator it;
            int iNextInt;
            Iterator it2;
            IrExpressionBody defaultValue;
            IrValueParameter irValueParameter;
            IrValueParameter irValueParameter2;
            int size;
            Intrinsics.checkNotNullParameter(expression, "expression");
            IrCall irCall3 = expression;
            PartiallyLinkedIrTreePatcher partiallyLinkedIrTreePatcher = PartiallyLinkedIrTreePatcher.this;
            IrCall irCall4 = irCall3;
            ExpressionTransformer expressionTransformer = this;
            IrElementTransformerVoidKt.transformChildrenVoid(irCall4, expressionTransformer);
            IrCall irCall5 = irCall3;
            IrCall irCall6 = irCall5;
            PartialLinkageCase.MemberAccessExpressionArgumentsMismatch memberAccessExpressionArgumentsMismatchCheckReferencedDeclaration$default = checkReferencedDeclaration$default(this, irCall6, irCall5.getSymbol(), false, 2, null);
            if (memberAccessExpressionArgumentsMismatchCheckReferencedDeclaration$default == null && (memberAccessExpressionArgumentsMismatchCheckReferencedDeclaration$default = checkReferencedDeclaration$default(this, irCall6, irCall5.getSuperQualifierSymbol(), false, 2, null)) == null && (memberAccessExpressionArgumentsMismatchCheckReferencedDeclaration$default = checkExpressionTypeArguments((irCall2 = irCall5))) == null) {
                IrFunction owner = ((IrFunctionSymbol) irCall2.getSymbol()).getOwner();
                int i = 0;
                if (irCall2.getDispatchReceiver() != null) {
                    zIsSubtypeOfClass = true;
                } else if (irCall2 instanceof IrFunctionReference) {
                    IrFunctionReference irFunctionReference = (IrFunctionReference) irCall2;
                    IrFunctionSymbol symbol2 = irFunctionReference.getSymbol();
                    if (symbol2 instanceof IrSimpleFunctionSymbol) {
                        IrDeclarationParent parent2 = owner.getParent();
                        if (parent2 instanceof IrClass) {
                            irClass = (IrClass) parent2;
                        } else {
                            irClass = null;
                        }
                    } else {
                        if (!(symbol2 instanceof IrConstructorSymbol)) {
                            throw new NoWhenBranchMatchedException();
                        }
                        IrDeclarationParent parent3 = owner.getParent();
                        IrClass irClass2 = parent3 instanceof IrClass ? (IrClass) parent3 : null;
                        if (irClass2 == null) {
                            parent = null;
                        } else {
                            if (!irClass2.getIsInner()) {
                                irClass2 = null;
                            }
                            if (irClass2 != null) {
                                parent = irClass2.getParent();
                            } else {
                                parent = null;
                            }
                        }
                        if (parent instanceof IrClass) {
                            irClass = (IrClass) parent;
                        } else {
                            irClass = null;
                        }
                    }
                    if (irClass == null || (symbol = irClass.getSymbol()) == null) {
                        zIsSubtypeOfClass = false;
                    } else {
                        IrType type = irFunctionReference.getType();
                        IrSimpleType irSimpleType = type instanceof IrSimpleType ? (IrSimpleType) type : null;
                        if (irSimpleType != null && (IrTypeUtilsKt.isKFunction(irSimpleType.getClassifier()) || IrTypeUtilsKt.isKSuspendFunction(irSimpleType.getClassifier()))) {
                            Object objFirstOrNull = CollectionsKt.firstOrNull((List<? extends Object>) irSimpleType.getArguments());
                            IrSimpleType irSimpleType2 = objFirstOrNull instanceof IrSimpleType ? (IrSimpleType) objFirstOrNull : null;
                            IrClassifierSymbol classifier = irSimpleType2 != null ? irSimpleType2.getClassifier() : null;
                            if (classifier != null) {
                                zIsSubtypeOfClass = org.jetbrains.kotlin.ir.types.IrTypeUtilsKt.isSubtypeOfClass(classifier, symbol);
                            } else {
                                zIsSubtypeOfClass = false;
                            }
                        } else {
                            zIsSubtypeOfClass = false;
                        }
                    }
                } else {
                    zIsSubtypeOfClass = false;
                }
                boolean z = owner.getDispatchReceiverParameter() != null;
                if (zIsSubtypeOfClass != z) {
                    memberAccessExpressionArgumentsMismatchCheckReferencedDeclaration$default = new PartialLinkageCase.MemberAccessExpressionArgumentsMismatch(irCall2, zIsSubtypeOfClass, z, 0, 0);
                } else {
                    if (irCall2 instanceof IrFunctionAccessExpression) {
                        if (!owner.getIsExternal()) {
                            if (irCall2 instanceof IrEnumConstructorCall) {
                                IrDeclarationParent parent4 = owner.getParent();
                                IrClass irClass3 = parent4 instanceof IrClass ? (IrClass) parent4 : null;
                                if (!Intrinsics.areEqual(irClass3 != null ? irClass3.getSymbol() : null, PartiallyLinkedIrTreePatcher.this.builtIns.getEnumClass())) {
                                    lazy = LazyKt.lazy(new PartiallyLinkedIrTreePatcher$ExpressionTransformer$checkArgumentsAndValueParameters$functionsToCheckDefaultValues$2(owner));
                                    iterableUntil = RangesKt.until(0, irCall2.getValueArgumentsCount());
                                    if ((iterableUntil instanceof Collection) || !((Collection) iterableUntil).isEmpty()) {
                                        it = iterableUntil.iterator();
                                        while (it.hasNext()) {
                                            iNextInt = ((IntIterator) it).nextInt();
                                            if (irCall2.getValueArgument(iNextInt) == null) {
                                                it2 = m6111checkArgumentsAndValueParameters$lambda28(lazy).iterator();
                                                do {
                                                    if (!it2.hasNext()) {
                                                        defaultValue = null;
                                                        break;
                                                    }
                                                    irValueParameter2 = (IrValueParameter) CollectionsKt.getOrNull(((IrFunction) it2.next()).getValueParameters(), iNextInt);
                                                    if (irValueParameter2 != null) {
                                                        defaultValue = irValueParameter2.getDefaultValue();
                                                    } else {
                                                        defaultValue = null;
                                                    }
                                                } while (defaultValue == null);
                                                if (defaultValue != null && ((irValueParameter = (IrValueParameter) CollectionsKt.getOrNull(owner.getValueParameters(), iNextInt)) == null || !AdditionalIrUtilsKt.isVararg(irValueParameter))) {
                                                }
                                            }
                                            i++;
                                            if (i < 0) {
                                                CollectionsKt.throwCountOverflow();
                                            }
                                        }
                                    }
                                    size = owner.getValueParameters().size();
                                    if (i != size) {
                                        memberAccessExpressionArgumentsMismatchCheckReferencedDeclaration$default = new PartialLinkageCase.MemberAccessExpressionArgumentsMismatch(irCall2, zIsSubtypeOfClass, z, i, size);
                                    }
                                }
                            } else {
                                lazy = LazyKt.lazy(new PartiallyLinkedIrTreePatcher$ExpressionTransformer$checkArgumentsAndValueParameters$functionsToCheckDefaultValues$2(owner));
                                iterableUntil = RangesKt.until(0, irCall2.getValueArgumentsCount());
                                if (iterableUntil instanceof Collection) {
                                    it = iterableUntil.iterator();
                                    while (it.hasNext()) {
                                        iNextInt = ((IntIterator) it).nextInt();
                                        if (irCall2.getValueArgument(iNextInt) == null) {
                                            it2 = m6111checkArgumentsAndValueParameters$lambda28(lazy).iterator();
                                            do {
                                                if (!it2.hasNext()) {
                                                    defaultValue = null;
                                                    break;
                                                }
                                                irValueParameter2 = (IrValueParameter) CollectionsKt.getOrNull(((IrFunction) it2.next()).getValueParameters(), iNextInt);
                                                if (irValueParameter2 != null) {
                                                    defaultValue = irValueParameter2.getDefaultValue();
                                                } else {
                                                    defaultValue = null;
                                                }
                                            } while (defaultValue == null);
                                            if (defaultValue != null) {
                                            }
                                        }
                                        i++;
                                        if (i < 0) {
                                            CollectionsKt.throwCountOverflow();
                                        }
                                    }
                                } else {
                                    it = iterableUntil.iterator();
                                    while (it.hasNext()) {
                                        iNextInt = ((IntIterator) it).nextInt();
                                        if (irCall2.getValueArgument(iNextInt) == null) {
                                            it2 = m6111checkArgumentsAndValueParameters$lambda28(lazy).iterator();
                                            do {
                                                if (!it2.hasNext()) {
                                                    defaultValue = null;
                                                    break;
                                                }
                                                irValueParameter2 = (IrValueParameter) CollectionsKt.getOrNull(((IrFunction) it2.next()).getValueParameters(), iNextInt);
                                                if (irValueParameter2 != null) {
                                                    defaultValue = irValueParameter2.getDefaultValue();
                                                } else {
                                                    defaultValue = null;
                                                }
                                            } while (defaultValue == null);
                                            if (defaultValue != null) {
                                            }
                                        }
                                        i++;
                                        if (i < 0) {
                                            CollectionsKt.throwCountOverflow();
                                        }
                                    }
                                }
                                size = owner.getValueParameters().size();
                                if (i != size) {
                                    memberAccessExpressionArgumentsMismatchCheckReferencedDeclaration$default = new PartialLinkageCase.MemberAccessExpressionArgumentsMismatch(irCall2, zIsSubtypeOfClass, z, i, size);
                                }
                            }
                        }
                    } else if (!(irCall2 instanceof IrFunctionReference)) {
                        lazy = LazyKt.lazy(new PartiallyLinkedIrTreePatcher$ExpressionTransformer$checkArgumentsAndValueParameters$functionsToCheckDefaultValues$2(owner));
                        iterableUntil = RangesKt.until(0, irCall2.getValueArgumentsCount());
                        if (iterableUntil instanceof Collection) {
                            it = iterableUntil.iterator();
                            while (it.hasNext()) {
                                iNextInt = ((IntIterator) it).nextInt();
                                if (irCall2.getValueArgument(iNextInt) == null) {
                                    it2 = m6111checkArgumentsAndValueParameters$lambda28(lazy).iterator();
                                    do {
                                        if (!it2.hasNext()) {
                                            defaultValue = null;
                                            break;
                                        }
                                        irValueParameter2 = (IrValueParameter) CollectionsKt.getOrNull(((IrFunction) it2.next()).getValueParameters(), iNextInt);
                                        if (irValueParameter2 != null) {
                                            defaultValue = irValueParameter2.getDefaultValue();
                                        } else {
                                            defaultValue = null;
                                        }
                                    } while (defaultValue == null);
                                    if (defaultValue != null) {
                                    }
                                }
                                i++;
                                if (i < 0) {
                                    CollectionsKt.throwCountOverflow();
                                }
                            }
                        } else {
                            it = iterableUntil.iterator();
                            while (it.hasNext()) {
                                iNextInt = ((IntIterator) it).nextInt();
                                if (irCall2.getValueArgument(iNextInt) == null) {
                                    it2 = m6111checkArgumentsAndValueParameters$lambda28(lazy).iterator();
                                    do {
                                        if (!it2.hasNext()) {
                                            defaultValue = null;
                                            break;
                                        }
                                        irValueParameter2 = (IrValueParameter) CollectionsKt.getOrNull(((IrFunction) it2.next()).getValueParameters(), iNextInt);
                                        if (irValueParameter2 != null) {
                                            defaultValue = irValueParameter2.getDefaultValue();
                                        } else {
                                            defaultValue = null;
                                        }
                                    } while (defaultValue == null);
                                    if (defaultValue != null) {
                                    }
                                }
                                i++;
                                if (i < 0) {
                                    CollectionsKt.throwCountOverflow();
                                }
                            }
                        }
                        size = owner.getValueParameters().size();
                        if (i != size) {
                            memberAccessExpressionArgumentsMismatchCheckReferencedDeclaration$default = new PartialLinkageCase.MemberAccessExpressionArgumentsMismatch(irCall2, zIsSubtypeOfClass, z, i, size);
                        }
                    }
                    memberAccessExpressionArgumentsMismatchCheckReferencedDeclaration$default = null;
                }
            }
            if (memberAccessExpressionArgumentsMismatchCheckReferencedDeclaration$default == null) {
                memberAccessExpressionArgumentsMismatchCheckReferencedDeclaration$default = checkExpressionType(irCall3, irCall3.getType());
            }
            PartialLinkageCase partialLinkageCase = memberAccessExpressionArgumentsMismatchCheckReferencedDeclaration$default;
            if (partialLinkageCase != null) {
                if (PartiallyLinkedIrTreePatcher.INSTANCE.hasBranches(irCall3)) {
                    empty = DirectChildren.INSTANCE.getEMPTY();
                } else {
                    DirectChildrenStatementsCollector directChildrenStatementsCollector = new DirectChildrenStatementsCollector();
                    IrVisitorsKt.acceptChildrenVoid(irCall4, directChildrenStatementsCollector);
                    empty = directChildrenStatementsCollector.getResult();
                }
                IrCall irCallThrowLinkageError$default = PartialLinkageSupportForLowerings.DefaultImpls.throwLinkageError$default(partiallyLinkedIrTreePatcher.getSupportForLowerings(), partialLinkageCase, irCall4, expressionTransformer.getCurrentFile(), false, 8, null);
                if (!empty.getStatements().isEmpty()) {
                    IrCompositeImpl irCompositeImpl = new IrCompositeImpl(irCall3.getStartOffset(), irCall3.getEndOffset(), partiallyLinkedIrTreePatcher.builtIns.getNothingType(), IrStatementOrigin.PARTIAL_LINKAGE_RUNTIME_ERROR.INSTANCE);
                    CollectionsKt.addAll(irCompositeImpl.getStatements(), empty.getStatements());
                    if (!empty.getHasPartialLinkageRuntimeError()) {
                        irCompositeImpl.getStatements().add(irCallThrowLinkageError$default);
                    }
                    irCall = irCompositeImpl;
                } else {
                    irCall = irCallThrowLinkageError$default;
                }
            } else {
                IrContainerExpression irContainerExpression = irCall3 instanceof IrContainerExpression ? (IrContainerExpression) irCall3 : null;
                if (irContainerExpression != null && (statements = irContainerExpression.getStatements()) != null) {
                    PartiallyLinkedIrTreePatcher.INSTANCE.eliminateDeadCodeStatements(statements);
                }
                irCall = irCall3;
            }
            onAfterMaybeThrowLinkageError(irCall3);
            return irCall;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
        public IrExpression visitConstructorCall(IrConstructorCall expression) {
            DirectChildren empty;
            IrCall irCall;
            List<IrStatement> statements;
            Intrinsics.checkNotNullParameter(expression, "expression");
            IrConstructorCall irConstructorCall = expression;
            PartiallyLinkedIrTreePatcher partiallyLinkedIrTreePatcher = PartiallyLinkedIrTreePatcher.this;
            IrConstructorCall irConstructorCall2 = irConstructorCall;
            ExpressionTransformer expressionTransformer = this;
            IrElementTransformerVoidKt.transformChildrenVoid(irConstructorCall2, expressionTransformer);
            IrConstructorCall irConstructorCall3 = irConstructorCall;
            PartialLinkageCase partialLinkageCaseCheckReferencedDeclaration$default = checkReferencedDeclaration$default(this, irConstructorCall3, irConstructorCall3.getSymbol(), false, 2, null);
            if (partialLinkageCaseCheckReferencedDeclaration$default == null && (partialLinkageCaseCheckReferencedDeclaration$default = checkNotAbstractClass(irConstructorCall3)) == null && (partialLinkageCaseCheckReferencedDeclaration$default = checkExpressionTypeArguments(irConstructorCall3)) == null) {
                partialLinkageCaseCheckReferencedDeclaration$default = customConstructorCallChecks(irConstructorCall3);
            }
            PartialLinkageCase partialLinkageCaseCheckExpressionType = partialLinkageCaseCheckReferencedDeclaration$default == null ? checkExpressionType(irConstructorCall, irConstructorCall.getType()) : partialLinkageCaseCheckReferencedDeclaration$default;
            if (partialLinkageCaseCheckExpressionType != null) {
                if (PartiallyLinkedIrTreePatcher.INSTANCE.hasBranches(irConstructorCall)) {
                    empty = DirectChildren.INSTANCE.getEMPTY();
                } else {
                    DirectChildrenStatementsCollector directChildrenStatementsCollector = new DirectChildrenStatementsCollector();
                    IrVisitorsKt.acceptChildrenVoid(irConstructorCall2, directChildrenStatementsCollector);
                    empty = directChildrenStatementsCollector.getResult();
                }
                DirectChildren directChildren = empty;
                IrCall irCallThrowLinkageError$default = PartialLinkageSupportForLowerings.DefaultImpls.throwLinkageError$default(partiallyLinkedIrTreePatcher.getSupportForLowerings(), partialLinkageCaseCheckExpressionType, irConstructorCall2, expressionTransformer.getCurrentFile(), false, 8, null);
                if (!directChildren.getStatements().isEmpty()) {
                    IrCompositeImpl irCompositeImpl = new IrCompositeImpl(irConstructorCall.getStartOffset(), irConstructorCall.getEndOffset(), partiallyLinkedIrTreePatcher.builtIns.getNothingType(), IrStatementOrigin.PARTIAL_LINKAGE_RUNTIME_ERROR.INSTANCE);
                    CollectionsKt.addAll(irCompositeImpl.getStatements(), directChildren.getStatements());
                    if (!directChildren.getHasPartialLinkageRuntimeError()) {
                        irCompositeImpl.getStatements().add(irCallThrowLinkageError$default);
                    }
                    irCall = irCompositeImpl;
                } else {
                    irCall = irCallThrowLinkageError$default;
                }
            } else {
                IrContainerExpression irContainerExpression = irConstructorCall instanceof IrContainerExpression ? (IrContainerExpression) irConstructorCall : null;
                if (irContainerExpression != null && (statements = irContainerExpression.getStatements()) != null) {
                    PartiallyLinkedIrTreePatcher.INSTANCE.eliminateDeadCodeStatements(statements);
                }
                irCall = irConstructorCall;
            }
            onAfterMaybeThrowLinkageError(irConstructorCall);
            return irCall;
        }

        /* JADX WARN: Code duplicated, block: B:102:0x01aa  */
        /* JADX WARN: Code duplicated, block: B:104:0x01bc  */
        /* JADX WARN: Code duplicated, block: B:105:0x01c1  */
        /* JADX WARN: Code duplicated, block: B:111:0x01c9  */
        /* JADX WARN: Code duplicated, block: B:120:0x01ed  */
        /* JADX WARN: Code duplicated, block: B:147:0x01db A[SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:148:0x01db A[ADDED_TO_REGION, REMOVE, SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:152:0x01df A[SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:156:0x0186 A[SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:158:0x01c5 A[SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:18:0x008c  */
        /* JADX WARN: Code duplicated, block: B:32:0x00af  */
        /* JADX WARN: Code duplicated, block: B:59:0x0101  */
        /* JADX WARN: Code duplicated, block: B:88:0x015f  */
        /* JADX WARN: Code duplicated, block: B:93:0x0182  */
        /* JADX WARN: Code duplicated, block: B:96:0x018c  */
        /* JADX WARN: Code duplicated, block: B:99:0x019a  */
        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
        public IrExpression visitEnumConstructorCall(IrEnumConstructorCall expression) {
            DirectChildren empty;
            IrCall irCall;
            List<IrStatement> statements;
            IrEnumConstructorCall irEnumConstructorCall;
            boolean z;
            IrDeclarationParent parent;
            IrClass irClass;
            boolean zIsSubtypeOfClass;
            IrClassSymbol symbol;
            Lazy lazy;
            Iterable iterableUntil;
            Iterator it;
            int iNextInt;
            Iterator it2;
            IrExpressionBody defaultValue;
            IrValueParameter irValueParameter;
            IrValueParameter irValueParameter2;
            int size;
            Intrinsics.checkNotNullParameter(expression, "expression");
            IrEnumConstructorCall irEnumConstructorCall2 = expression;
            final PartiallyLinkedIrTreePatcher partiallyLinkedIrTreePatcher = PartiallyLinkedIrTreePatcher.this;
            IrEnumConstructorCall irEnumConstructorCall3 = irEnumConstructorCall2;
            ExpressionTransformer expressionTransformer = this;
            IrElementTransformerVoidKt.transformChildrenVoid(irEnumConstructorCall3, expressionTransformer);
            IrEnumConstructorCall irEnumConstructorCall4 = irEnumConstructorCall2;
            IrEnumConstructorCall irEnumConstructorCall5 = irEnumConstructorCall4;
            PartialLinkageCase.MemberAccessExpressionArgumentsMismatch memberAccessExpressionArgumentsMismatchCheckReferencedDeclaration$default = checkReferencedDeclaration$default(this, irEnumConstructorCall5, irEnumConstructorCall4.getSymbol(), false, 2, null);
            if (memberAccessExpressionArgumentsMismatchCheckReferencedDeclaration$default == null && (memberAccessExpressionArgumentsMismatchCheckReferencedDeclaration$default = checkExpressionTypeArguments((irEnumConstructorCall = irEnumConstructorCall4))) == null && (memberAccessExpressionArgumentsMismatchCheckReferencedDeclaration$default = checkReferencedDeclarationType(irEnumConstructorCall5, IrUtilsKt.getParentAsClass(irEnumConstructorCall4.getSymbol().getOwner()), "enum class", new Function1<IrClass, Boolean>() { // from class: org.jetbrains.kotlin.backend.common.linkage.partial.PartiallyLinkedIrTreePatcher$ExpressionTransformer$visitEnumConstructorCall$1$1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(IrClass constructedClass) {
                    Intrinsics.checkNotNullParameter(constructedClass, "constructedClass");
                    return Boolean.valueOf(constructedClass.getKind() == ClassKind.ENUM_CLASS || constructedClass.getKind() == ClassKind.ENUM_ENTRY || Intrinsics.areEqual(constructedClass.getSymbol(), partiallyLinkedIrTreePatcher.builtIns.getEnumClass()));
                }
            })) == null) {
                IrFunction owner = ((IrFunctionSymbol) irEnumConstructorCall.getSymbol()).getOwner();
                int i = 0;
                if (irEnumConstructorCall.getDispatchReceiver() != null) {
                    z = true;
                } else if (irEnumConstructorCall instanceof IrFunctionReference) {
                    IrFunctionReference irFunctionReference = (IrFunctionReference) irEnumConstructorCall;
                    IrFunctionSymbol symbol2 = irFunctionReference.getSymbol();
                    if (symbol2 instanceof IrSimpleFunctionSymbol) {
                        IrDeclarationParent parent2 = owner.getParent();
                        if (parent2 instanceof IrClass) {
                            irClass = (IrClass) parent2;
                        } else {
                            irClass = null;
                        }
                    } else {
                        if (!(symbol2 instanceof IrConstructorSymbol)) {
                            throw new NoWhenBranchMatchedException();
                        }
                        IrDeclarationParent parent3 = owner.getParent();
                        IrClass irClass2 = parent3 instanceof IrClass ? (IrClass) parent3 : null;
                        if (irClass2 == null) {
                            parent = null;
                        } else {
                            if (!irClass2.getIsInner()) {
                                irClass2 = null;
                            }
                            if (irClass2 != null) {
                                parent = irClass2.getParent();
                            } else {
                                parent = null;
                            }
                        }
                        if (parent instanceof IrClass) {
                            irClass = (IrClass) parent;
                        } else {
                            irClass = null;
                        }
                    }
                    if (irClass == null || (symbol = irClass.getSymbol()) == null) {
                        zIsSubtypeOfClass = false;
                    } else {
                        IrType type = irFunctionReference.getType();
                        IrSimpleType irSimpleType = type instanceof IrSimpleType ? (IrSimpleType) type : null;
                        if (irSimpleType != null && (IrTypeUtilsKt.isKFunction(irSimpleType.getClassifier()) || IrTypeUtilsKt.isKSuspendFunction(irSimpleType.getClassifier()))) {
                            Object objFirstOrNull = CollectionsKt.firstOrNull((List<? extends Object>) irSimpleType.getArguments());
                            IrSimpleType irSimpleType2 = objFirstOrNull instanceof IrSimpleType ? (IrSimpleType) objFirstOrNull : null;
                            IrClassifierSymbol classifier = irSimpleType2 != null ? irSimpleType2.getClassifier() : null;
                            if (classifier != null) {
                                zIsSubtypeOfClass = org.jetbrains.kotlin.ir.types.IrTypeUtilsKt.isSubtypeOfClass(classifier, symbol);
                            } else {
                                zIsSubtypeOfClass = false;
                            }
                        } else {
                            zIsSubtypeOfClass = false;
                        }
                    }
                    z = zIsSubtypeOfClass;
                } else {
                    z = false;
                }
                boolean z2 = owner.getDispatchReceiverParameter() != null;
                if (z != z2) {
                    memberAccessExpressionArgumentsMismatchCheckReferencedDeclaration$default = new PartialLinkageCase.MemberAccessExpressionArgumentsMismatch(irEnumConstructorCall, z, z2, 0, 0);
                } else {
                    if (irEnumConstructorCall instanceof IrFunctionAccessExpression) {
                        if (!owner.getIsExternal()) {
                            IrDeclarationParent parent4 = owner.getParent();
                            IrClass irClass3 = parent4 instanceof IrClass ? (IrClass) parent4 : null;
                            if (!Intrinsics.areEqual(irClass3 != null ? irClass3.getSymbol() : null, PartiallyLinkedIrTreePatcher.this.builtIns.getEnumClass())) {
                                lazy = LazyKt.lazy(new PartiallyLinkedIrTreePatcher$ExpressionTransformer$checkArgumentsAndValueParameters$functionsToCheckDefaultValues$2(owner));
                                iterableUntil = RangesKt.until(0, irEnumConstructorCall.getValueArgumentsCount());
                                if ((iterableUntil instanceof Collection) || !((Collection) iterableUntil).isEmpty()) {
                                    it = iterableUntil.iterator();
                                    while (it.hasNext()) {
                                        iNextInt = ((IntIterator) it).nextInt();
                                        if (irEnumConstructorCall.getValueArgument(iNextInt) == null) {
                                            it2 = m6111checkArgumentsAndValueParameters$lambda28(lazy).iterator();
                                            do {
                                                if (!it2.hasNext()) {
                                                    defaultValue = null;
                                                    break;
                                                }
                                                irValueParameter2 = (IrValueParameter) CollectionsKt.getOrNull(((IrFunction) it2.next()).getValueParameters(), iNextInt);
                                                if (irValueParameter2 != null) {
                                                    defaultValue = irValueParameter2.getDefaultValue();
                                                } else {
                                                    defaultValue = null;
                                                }
                                            } while (defaultValue == null);
                                            if (defaultValue != null && ((irValueParameter = (IrValueParameter) CollectionsKt.getOrNull(owner.getValueParameters(), iNextInt)) == null || !AdditionalIrUtilsKt.isVararg(irValueParameter))) {
                                            }
                                        }
                                        i++;
                                        if (i < 0) {
                                            CollectionsKt.throwCountOverflow();
                                        }
                                    }
                                }
                                size = owner.getValueParameters().size();
                                if (i != size) {
                                    memberAccessExpressionArgumentsMismatchCheckReferencedDeclaration$default = new PartialLinkageCase.MemberAccessExpressionArgumentsMismatch(irEnumConstructorCall, z, z2, i, size);
                                }
                            }
                        }
                    } else if (!(irEnumConstructorCall instanceof IrFunctionReference)) {
                        lazy = LazyKt.lazy(new PartiallyLinkedIrTreePatcher$ExpressionTransformer$checkArgumentsAndValueParameters$functionsToCheckDefaultValues$2(owner));
                        iterableUntil = RangesKt.until(0, irEnumConstructorCall.getValueArgumentsCount());
                        if (iterableUntil instanceof Collection) {
                            it = iterableUntil.iterator();
                            while (it.hasNext()) {
                                iNextInt = ((IntIterator) it).nextInt();
                                if (irEnumConstructorCall.getValueArgument(iNextInt) == null) {
                                    it2 = m6111checkArgumentsAndValueParameters$lambda28(lazy).iterator();
                                    do {
                                        if (!it2.hasNext()) {
                                            defaultValue = null;
                                            break;
                                        }
                                        irValueParameter2 = (IrValueParameter) CollectionsKt.getOrNull(((IrFunction) it2.next()).getValueParameters(), iNextInt);
                                        if (irValueParameter2 != null) {
                                            defaultValue = irValueParameter2.getDefaultValue();
                                        } else {
                                            defaultValue = null;
                                        }
                                    } while (defaultValue == null);
                                    if (defaultValue != null) {
                                    }
                                }
                                i++;
                                if (i < 0) {
                                    CollectionsKt.throwCountOverflow();
                                }
                            }
                        } else {
                            it = iterableUntil.iterator();
                            while (it.hasNext()) {
                                iNextInt = ((IntIterator) it).nextInt();
                                if (irEnumConstructorCall.getValueArgument(iNextInt) == null) {
                                    it2 = m6111checkArgumentsAndValueParameters$lambda28(lazy).iterator();
                                    do {
                                        if (!it2.hasNext()) {
                                            defaultValue = null;
                                            break;
                                        }
                                        irValueParameter2 = (IrValueParameter) CollectionsKt.getOrNull(((IrFunction) it2.next()).getValueParameters(), iNextInt);
                                        if (irValueParameter2 != null) {
                                            defaultValue = irValueParameter2.getDefaultValue();
                                        } else {
                                            defaultValue = null;
                                        }
                                    } while (defaultValue == null);
                                    if (defaultValue != null) {
                                    }
                                }
                                i++;
                                if (i < 0) {
                                    CollectionsKt.throwCountOverflow();
                                }
                            }
                        }
                        size = owner.getValueParameters().size();
                        if (i != size) {
                            memberAccessExpressionArgumentsMismatchCheckReferencedDeclaration$default = new PartialLinkageCase.MemberAccessExpressionArgumentsMismatch(irEnumConstructorCall, z, z2, i, size);
                        }
                    }
                    memberAccessExpressionArgumentsMismatchCheckReferencedDeclaration$default = null;
                }
            }
            if (memberAccessExpressionArgumentsMismatchCheckReferencedDeclaration$default == null) {
                memberAccessExpressionArgumentsMismatchCheckReferencedDeclaration$default = checkExpressionType(irEnumConstructorCall2, irEnumConstructorCall2.getType());
            }
            PartialLinkageCase partialLinkageCase = memberAccessExpressionArgumentsMismatchCheckReferencedDeclaration$default;
            if (partialLinkageCase != null) {
                if (PartiallyLinkedIrTreePatcher.INSTANCE.hasBranches(irEnumConstructorCall2)) {
                    empty = DirectChildren.INSTANCE.getEMPTY();
                } else {
                    DirectChildrenStatementsCollector directChildrenStatementsCollector = new DirectChildrenStatementsCollector();
                    IrVisitorsKt.acceptChildrenVoid(irEnumConstructorCall3, directChildrenStatementsCollector);
                    empty = directChildrenStatementsCollector.getResult();
                }
                IrCall irCallThrowLinkageError$default = PartialLinkageSupportForLowerings.DefaultImpls.throwLinkageError$default(partiallyLinkedIrTreePatcher.getSupportForLowerings(), partialLinkageCase, irEnumConstructorCall3, expressionTransformer.getCurrentFile(), false, 8, null);
                if (!empty.getStatements().isEmpty()) {
                    IrCompositeImpl irCompositeImpl = new IrCompositeImpl(irEnumConstructorCall2.getStartOffset(), irEnumConstructorCall2.getEndOffset(), partiallyLinkedIrTreePatcher.builtIns.getNothingType(), IrStatementOrigin.PARTIAL_LINKAGE_RUNTIME_ERROR.INSTANCE);
                    CollectionsKt.addAll(irCompositeImpl.getStatements(), empty.getStatements());
                    if (!empty.getHasPartialLinkageRuntimeError()) {
                        irCompositeImpl.getStatements().add(irCallThrowLinkageError$default);
                    }
                    irCall = irCompositeImpl;
                } else {
                    irCall = irCallThrowLinkageError$default;
                }
            } else {
                IrContainerExpression irContainerExpression = irEnumConstructorCall2 instanceof IrContainerExpression ? (IrContainerExpression) irEnumConstructorCall2 : null;
                if (irContainerExpression != null && (statements = irContainerExpression.getStatements()) != null) {
                    PartiallyLinkedIrTreePatcher.INSTANCE.eliminateDeadCodeStatements(statements);
                }
                irCall = irEnumConstructorCall2;
            }
            onAfterMaybeThrowLinkageError(irEnumConstructorCall2);
            return irCall;
        }

        /* JADX WARN: Code duplicated, block: B:102:0x018c  */
        /* JADX WARN: Code duplicated, block: B:104:0x019e  */
        /* JADX WARN: Code duplicated, block: B:105:0x01a3  */
        /* JADX WARN: Code duplicated, block: B:111:0x01ab  */
        /* JADX WARN: Code duplicated, block: B:120:0x01cf  */
        /* JADX WARN: Code duplicated, block: B:147:0x01bd A[SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:148:0x01bd A[ADDED_TO_REGION, REMOVE, SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:152:0x01c1 A[SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:156:0x0168 A[SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:158:0x01a7 A[SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:16:0x006c  */
        /* JADX WARN: Code duplicated, block: B:30:0x008f  */
        /* JADX WARN: Code duplicated, block: B:57:0x00e1  */
        /* JADX WARN: Code duplicated, block: B:88:0x0141  */
        /* JADX WARN: Code duplicated, block: B:90:0x015a  */
        /* JADX WARN: Code duplicated, block: B:93:0x0164  */
        /* JADX WARN: Code duplicated, block: B:96:0x016e  */
        /* JADX WARN: Code duplicated, block: B:99:0x017c  */
        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
        public IrExpression visitDelegatingConstructorCall(IrDelegatingConstructorCall expression) {
            DirectChildren empty;
            IrCall irCall;
            List<IrStatement> statements;
            IrDelegatingConstructorCall irDelegatingConstructorCall;
            boolean z;
            IrDeclarationParent parent;
            IrClass irClass;
            boolean zIsSubtypeOfClass;
            IrClassSymbol symbol;
            Lazy lazy;
            Iterable iterableUntil;
            Iterator it;
            int iNextInt;
            Iterator it2;
            IrExpressionBody defaultValue;
            IrValueParameter irValueParameter;
            IrValueParameter irValueParameter2;
            int size;
            Intrinsics.checkNotNullParameter(expression, "expression");
            IrDelegatingConstructorCall irDelegatingConstructorCall2 = expression;
            PartiallyLinkedIrTreePatcher partiallyLinkedIrTreePatcher = PartiallyLinkedIrTreePatcher.this;
            IrDelegatingConstructorCall irDelegatingConstructorCall3 = irDelegatingConstructorCall2;
            ExpressionTransformer expressionTransformer = this;
            IrElementTransformerVoidKt.transformChildrenVoid(irDelegatingConstructorCall3, expressionTransformer);
            IrDelegatingConstructorCall irDelegatingConstructorCall4 = irDelegatingConstructorCall2;
            PartialLinkageCase.MemberAccessExpressionArgumentsMismatch memberAccessExpressionArgumentsMismatchCheckReferencedDeclaration$default = checkReferencedDeclaration$default(this, irDelegatingConstructorCall4, irDelegatingConstructorCall4.getSymbol(), false, 2, null);
            if (memberAccessExpressionArgumentsMismatchCheckReferencedDeclaration$default == null && (memberAccessExpressionArgumentsMismatchCheckReferencedDeclaration$default = checkExpressionTypeArguments((irDelegatingConstructorCall = irDelegatingConstructorCall4))) == null) {
                IrFunction owner = ((IrFunctionSymbol) irDelegatingConstructorCall.getSymbol()).getOwner();
                int i = 0;
                if (irDelegatingConstructorCall.getDispatchReceiver() != null) {
                    z = true;
                } else if (irDelegatingConstructorCall instanceof IrFunctionReference) {
                    IrFunctionReference irFunctionReference = (IrFunctionReference) irDelegatingConstructorCall;
                    IrFunctionSymbol symbol2 = irFunctionReference.getSymbol();
                    if (symbol2 instanceof IrSimpleFunctionSymbol) {
                        IrDeclarationParent parent2 = owner.getParent();
                        if (parent2 instanceof IrClass) {
                            irClass = (IrClass) parent2;
                        } else {
                            irClass = null;
                        }
                    } else {
                        if (!(symbol2 instanceof IrConstructorSymbol)) {
                            throw new NoWhenBranchMatchedException();
                        }
                        IrDeclarationParent parent3 = owner.getParent();
                        IrClass irClass2 = parent3 instanceof IrClass ? (IrClass) parent3 : null;
                        if (irClass2 == null) {
                            parent = null;
                        } else {
                            if (!irClass2.getIsInner()) {
                                irClass2 = null;
                            }
                            if (irClass2 != null) {
                                parent = irClass2.getParent();
                            } else {
                                parent = null;
                            }
                        }
                        if (parent instanceof IrClass) {
                            irClass = (IrClass) parent;
                        } else {
                            irClass = null;
                        }
                    }
                    if (irClass == null || (symbol = irClass.getSymbol()) == null) {
                        zIsSubtypeOfClass = false;
                    } else {
                        IrType type = irFunctionReference.getType();
                        IrSimpleType irSimpleType = type instanceof IrSimpleType ? (IrSimpleType) type : null;
                        if (irSimpleType != null && (IrTypeUtilsKt.isKFunction(irSimpleType.getClassifier()) || IrTypeUtilsKt.isKSuspendFunction(irSimpleType.getClassifier()))) {
                            Object objFirstOrNull = CollectionsKt.firstOrNull((List<? extends Object>) irSimpleType.getArguments());
                            IrSimpleType irSimpleType2 = objFirstOrNull instanceof IrSimpleType ? (IrSimpleType) objFirstOrNull : null;
                            IrClassifierSymbol classifier = irSimpleType2 != null ? irSimpleType2.getClassifier() : null;
                            if (classifier != null) {
                                zIsSubtypeOfClass = org.jetbrains.kotlin.ir.types.IrTypeUtilsKt.isSubtypeOfClass(classifier, symbol);
                            } else {
                                zIsSubtypeOfClass = false;
                            }
                        } else {
                            zIsSubtypeOfClass = false;
                        }
                    }
                    z = zIsSubtypeOfClass;
                } else {
                    z = false;
                }
                boolean z2 = owner.getDispatchReceiverParameter() != null;
                if (z != z2) {
                    memberAccessExpressionArgumentsMismatchCheckReferencedDeclaration$default = new PartialLinkageCase.MemberAccessExpressionArgumentsMismatch(irDelegatingConstructorCall, z, z2, 0, 0);
                } else {
                    if (irDelegatingConstructorCall instanceof IrFunctionAccessExpression) {
                        if (!owner.getIsExternal()) {
                            if (irDelegatingConstructorCall instanceof IrEnumConstructorCall) {
                                IrDeclarationParent parent4 = owner.getParent();
                                IrClass irClass3 = parent4 instanceof IrClass ? (IrClass) parent4 : null;
                                if (!Intrinsics.areEqual(irClass3 != null ? irClass3.getSymbol() : null, PartiallyLinkedIrTreePatcher.this.builtIns.getEnumClass())) {
                                    lazy = LazyKt.lazy(new PartiallyLinkedIrTreePatcher$ExpressionTransformer$checkArgumentsAndValueParameters$functionsToCheckDefaultValues$2(owner));
                                    iterableUntil = RangesKt.until(0, irDelegatingConstructorCall.getValueArgumentsCount());
                                    if ((iterableUntil instanceof Collection) || !((Collection) iterableUntil).isEmpty()) {
                                        it = iterableUntil.iterator();
                                        while (it.hasNext()) {
                                            iNextInt = ((IntIterator) it).nextInt();
                                            if (irDelegatingConstructorCall.getValueArgument(iNextInt) == null) {
                                                it2 = m6111checkArgumentsAndValueParameters$lambda28(lazy).iterator();
                                                do {
                                                    if (!it2.hasNext()) {
                                                        defaultValue = null;
                                                        break;
                                                    }
                                                    irValueParameter2 = (IrValueParameter) CollectionsKt.getOrNull(((IrFunction) it2.next()).getValueParameters(), iNextInt);
                                                    if (irValueParameter2 != null) {
                                                        defaultValue = irValueParameter2.getDefaultValue();
                                                    } else {
                                                        defaultValue = null;
                                                    }
                                                } while (defaultValue == null);
                                                if (defaultValue != null && ((irValueParameter = (IrValueParameter) CollectionsKt.getOrNull(owner.getValueParameters(), iNextInt)) == null || !AdditionalIrUtilsKt.isVararg(irValueParameter))) {
                                                }
                                            }
                                            i++;
                                            if (i < 0) {
                                                CollectionsKt.throwCountOverflow();
                                            }
                                        }
                                    }
                                    size = owner.getValueParameters().size();
                                    if (i != size) {
                                        memberAccessExpressionArgumentsMismatchCheckReferencedDeclaration$default = new PartialLinkageCase.MemberAccessExpressionArgumentsMismatch(irDelegatingConstructorCall, z, z2, i, size);
                                    }
                                }
                            } else {
                                lazy = LazyKt.lazy(new PartiallyLinkedIrTreePatcher$ExpressionTransformer$checkArgumentsAndValueParameters$functionsToCheckDefaultValues$2(owner));
                                iterableUntil = RangesKt.until(0, irDelegatingConstructorCall.getValueArgumentsCount());
                                if (iterableUntil instanceof Collection) {
                                    it = iterableUntil.iterator();
                                    while (it.hasNext()) {
                                        iNextInt = ((IntIterator) it).nextInt();
                                        if (irDelegatingConstructorCall.getValueArgument(iNextInt) == null) {
                                            it2 = m6111checkArgumentsAndValueParameters$lambda28(lazy).iterator();
                                            do {
                                                if (!it2.hasNext()) {
                                                    defaultValue = null;
                                                    break;
                                                }
                                                irValueParameter2 = (IrValueParameter) CollectionsKt.getOrNull(((IrFunction) it2.next()).getValueParameters(), iNextInt);
                                                if (irValueParameter2 != null) {
                                                    defaultValue = irValueParameter2.getDefaultValue();
                                                } else {
                                                    defaultValue = null;
                                                }
                                            } while (defaultValue == null);
                                            if (defaultValue != null) {
                                            }
                                        }
                                        i++;
                                        if (i < 0) {
                                            CollectionsKt.throwCountOverflow();
                                        }
                                    }
                                } else {
                                    it = iterableUntil.iterator();
                                    while (it.hasNext()) {
                                        iNextInt = ((IntIterator) it).nextInt();
                                        if (irDelegatingConstructorCall.getValueArgument(iNextInt) == null) {
                                            it2 = m6111checkArgumentsAndValueParameters$lambda28(lazy).iterator();
                                            do {
                                                if (!it2.hasNext()) {
                                                    defaultValue = null;
                                                    break;
                                                }
                                                irValueParameter2 = (IrValueParameter) CollectionsKt.getOrNull(((IrFunction) it2.next()).getValueParameters(), iNextInt);
                                                if (irValueParameter2 != null) {
                                                    defaultValue = irValueParameter2.getDefaultValue();
                                                } else {
                                                    defaultValue = null;
                                                }
                                            } while (defaultValue == null);
                                            if (defaultValue != null) {
                                            }
                                        }
                                        i++;
                                        if (i < 0) {
                                            CollectionsKt.throwCountOverflow();
                                        }
                                    }
                                }
                                size = owner.getValueParameters().size();
                                if (i != size) {
                                    memberAccessExpressionArgumentsMismatchCheckReferencedDeclaration$default = new PartialLinkageCase.MemberAccessExpressionArgumentsMismatch(irDelegatingConstructorCall, z, z2, i, size);
                                }
                            }
                        }
                    } else if (!(irDelegatingConstructorCall instanceof IrFunctionReference)) {
                        lazy = LazyKt.lazy(new PartiallyLinkedIrTreePatcher$ExpressionTransformer$checkArgumentsAndValueParameters$functionsToCheckDefaultValues$2(owner));
                        iterableUntil = RangesKt.until(0, irDelegatingConstructorCall.getValueArgumentsCount());
                        if (iterableUntil instanceof Collection) {
                            it = iterableUntil.iterator();
                            while (it.hasNext()) {
                                iNextInt = ((IntIterator) it).nextInt();
                                if (irDelegatingConstructorCall.getValueArgument(iNextInt) == null) {
                                    it2 = m6111checkArgumentsAndValueParameters$lambda28(lazy).iterator();
                                    do {
                                        if (!it2.hasNext()) {
                                            defaultValue = null;
                                            break;
                                        }
                                        irValueParameter2 = (IrValueParameter) CollectionsKt.getOrNull(((IrFunction) it2.next()).getValueParameters(), iNextInt);
                                        if (irValueParameter2 != null) {
                                            defaultValue = irValueParameter2.getDefaultValue();
                                        } else {
                                            defaultValue = null;
                                        }
                                    } while (defaultValue == null);
                                    if (defaultValue != null) {
                                    }
                                }
                                i++;
                                if (i < 0) {
                                    CollectionsKt.throwCountOverflow();
                                }
                            }
                        } else {
                            it = iterableUntil.iterator();
                            while (it.hasNext()) {
                                iNextInt = ((IntIterator) it).nextInt();
                                if (irDelegatingConstructorCall.getValueArgument(iNextInt) == null) {
                                    it2 = m6111checkArgumentsAndValueParameters$lambda28(lazy).iterator();
                                    do {
                                        if (!it2.hasNext()) {
                                            defaultValue = null;
                                            break;
                                        }
                                        irValueParameter2 = (IrValueParameter) CollectionsKt.getOrNull(((IrFunction) it2.next()).getValueParameters(), iNextInt);
                                        if (irValueParameter2 != null) {
                                            defaultValue = irValueParameter2.getDefaultValue();
                                        } else {
                                            defaultValue = null;
                                        }
                                    } while (defaultValue == null);
                                    if (defaultValue != null) {
                                    }
                                }
                                i++;
                                if (i < 0) {
                                    CollectionsKt.throwCountOverflow();
                                }
                            }
                        }
                        size = owner.getValueParameters().size();
                        if (i != size) {
                            memberAccessExpressionArgumentsMismatchCheckReferencedDeclaration$default = new PartialLinkageCase.MemberAccessExpressionArgumentsMismatch(irDelegatingConstructorCall, z, z2, i, size);
                        }
                    }
                    memberAccessExpressionArgumentsMismatchCheckReferencedDeclaration$default = null;
                }
            }
            if (memberAccessExpressionArgumentsMismatchCheckReferencedDeclaration$default == null) {
                memberAccessExpressionArgumentsMismatchCheckReferencedDeclaration$default = checkExpressionType(irDelegatingConstructorCall2, irDelegatingConstructorCall2.getType());
            }
            PartialLinkageCase partialLinkageCase = memberAccessExpressionArgumentsMismatchCheckReferencedDeclaration$default;
            if (partialLinkageCase != null) {
                if (PartiallyLinkedIrTreePatcher.INSTANCE.hasBranches(irDelegatingConstructorCall2)) {
                    empty = DirectChildren.INSTANCE.getEMPTY();
                } else {
                    DirectChildrenStatementsCollector directChildrenStatementsCollector = new DirectChildrenStatementsCollector();
                    IrVisitorsKt.acceptChildrenVoid(irDelegatingConstructorCall3, directChildrenStatementsCollector);
                    empty = directChildrenStatementsCollector.getResult();
                }
                IrCall irCallThrowLinkageError$default = PartialLinkageSupportForLowerings.DefaultImpls.throwLinkageError$default(partiallyLinkedIrTreePatcher.getSupportForLowerings(), partialLinkageCase, irDelegatingConstructorCall3, expressionTransformer.getCurrentFile(), false, 8, null);
                if (!empty.getStatements().isEmpty()) {
                    IrCompositeImpl irCompositeImpl = new IrCompositeImpl(irDelegatingConstructorCall2.getStartOffset(), irDelegatingConstructorCall2.getEndOffset(), partiallyLinkedIrTreePatcher.builtIns.getNothingType(), IrStatementOrigin.PARTIAL_LINKAGE_RUNTIME_ERROR.INSTANCE);
                    CollectionsKt.addAll(irCompositeImpl.getStatements(), empty.getStatements());
                    if (!empty.getHasPartialLinkageRuntimeError()) {
                        irCompositeImpl.getStatements().add(irCallThrowLinkageError$default);
                    }
                    irCall = irCompositeImpl;
                } else {
                    irCall = irCallThrowLinkageError$default;
                }
            } else {
                IrContainerExpression irContainerExpression = irDelegatingConstructorCall2 instanceof IrContainerExpression ? (IrContainerExpression) irDelegatingConstructorCall2 : null;
                if (irContainerExpression != null && (statements = irContainerExpression.getStatements()) != null) {
                    PartiallyLinkedIrTreePatcher.INSTANCE.eliminateDeadCodeStatements(statements);
                }
                irCall = irDelegatingConstructorCall2;
            }
            onAfterMaybeThrowLinkageError(irDelegatingConstructorCall2);
            return irCall;
        }

        /* JADX WARN: Code duplicated, block: B:104:0x01ae  */
        /* JADX WARN: Code duplicated, block: B:113:0x01d2  */
        /* JADX WARN: Code duplicated, block: B:114:0x01e4  */
        /* JADX WARN: Code duplicated, block: B:143:0x01c0 A[ADDED_TO_REGION, REMOVE, SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:146:0x01c0 A[SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:148:0x01c4 A[SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:152:0x016b A[SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:155:0x01aa A[SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:16:0x0079  */
        /* JADX WARN: Code duplicated, block: B:30:0x009c  */
        /* JADX WARN: Code duplicated, block: B:57:0x00ee  */
        /* JADX WARN: Code duplicated, block: B:81:0x0144  */
        /* JADX WARN: Code duplicated, block: B:86:0x0167  */
        /* JADX WARN: Code duplicated, block: B:89:0x0171  */
        /* JADX WARN: Code duplicated, block: B:92:0x017f  */
        /* JADX WARN: Code duplicated, block: B:95:0x018f  */
        /* JADX WARN: Code duplicated, block: B:97:0x01a1  */
        /* JADX WARN: Code duplicated, block: B:98:0x01a6  */
        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
        public IrExpression visitFunctionReference(IrFunctionReference expression) {
            DirectChildren empty;
            IrCall irCall;
            List<IrStatement> statements;
            IrFunctionReference irFunctionReference;
            IrDeclarationParent parent;
            IrClass irClass;
            boolean zIsSubtypeOfClass;
            IrClassSymbol symbol;
            Lazy lazy;
            Iterable iterableUntil;
            Iterator it;
            int iNextInt;
            Iterator it2;
            IrExpressionBody defaultValue;
            IrValueParameter irValueParameter;
            IrValueParameter irValueParameter2;
            int size;
            Intrinsics.checkNotNullParameter(expression, "expression");
            IrFunctionReference irFunctionReference2 = expression;
            PartiallyLinkedIrTreePatcher partiallyLinkedIrTreePatcher = PartiallyLinkedIrTreePatcher.this;
            IrFunctionReference irFunctionReference3 = irFunctionReference2;
            ExpressionTransformer expressionTransformer = this;
            IrElementTransformerVoidKt.transformChildrenVoid(irFunctionReference3, expressionTransformer);
            IrFunctionReference irFunctionReference4 = irFunctionReference2;
            IrFunctionReference irFunctionReference5 = irFunctionReference4;
            PartialLinkageCase.MemberAccessExpressionArgumentsMismatch memberAccessExpressionArgumentsMismatchCheckReferencedDeclaration$default = checkReferencedDeclaration$default(this, irFunctionReference5, irFunctionReference4.getSymbol(), false, 2, null);
            if (memberAccessExpressionArgumentsMismatchCheckReferencedDeclaration$default == null && (memberAccessExpressionArgumentsMismatchCheckReferencedDeclaration$default = checkReferencedDeclaration$default(this, irFunctionReference5, irFunctionReference4.getReflectionTarget(), false, 2, null)) == null && (memberAccessExpressionArgumentsMismatchCheckReferencedDeclaration$default = checkExpressionTypeArguments((irFunctionReference = irFunctionReference4))) == null) {
                IrFunction owner = ((IrFunctionSymbol) irFunctionReference.getSymbol()).getOwner();
                int i = 0;
                if (irFunctionReference.getDispatchReceiver() != null) {
                    zIsSubtypeOfClass = true;
                } else {
                    IrFunctionReference irFunctionReference6 = irFunctionReference;
                    IrFunctionSymbol symbol2 = irFunctionReference6.getSymbol();
                    if (symbol2 instanceof IrSimpleFunctionSymbol) {
                        IrDeclarationParent parent2 = owner.getParent();
                        if (parent2 instanceof IrClass) {
                            irClass = (IrClass) parent2;
                        } else {
                            irClass = null;
                        }
                    } else {
                        if (!(symbol2 instanceof IrConstructorSymbol)) {
                            throw new NoWhenBranchMatchedException();
                        }
                        IrDeclarationParent parent3 = owner.getParent();
                        IrClass irClass2 = parent3 instanceof IrClass ? (IrClass) parent3 : null;
                        if (irClass2 == null) {
                            parent = null;
                        } else {
                            if (!irClass2.getIsInner()) {
                                irClass2 = null;
                            }
                            if (irClass2 != null) {
                                parent = irClass2.getParent();
                            } else {
                                parent = null;
                            }
                        }
                        if (parent instanceof IrClass) {
                            irClass = (IrClass) parent;
                        } else {
                            irClass = null;
                        }
                    }
                    if (irClass == null || (symbol = irClass.getSymbol()) == null) {
                        zIsSubtypeOfClass = false;
                    } else {
                        IrType type = irFunctionReference6.getType();
                        IrSimpleType irSimpleType = type instanceof IrSimpleType ? (IrSimpleType) type : null;
                        if (irSimpleType != null && (IrTypeUtilsKt.isKFunction(irSimpleType.getClassifier()) || IrTypeUtilsKt.isKSuspendFunction(irSimpleType.getClassifier()))) {
                            Object objFirstOrNull = CollectionsKt.firstOrNull((List<? extends Object>) irSimpleType.getArguments());
                            IrSimpleType irSimpleType2 = objFirstOrNull instanceof IrSimpleType ? (IrSimpleType) objFirstOrNull : null;
                            IrClassifierSymbol classifier = irSimpleType2 != null ? irSimpleType2.getClassifier() : null;
                            if (classifier != null) {
                                zIsSubtypeOfClass = org.jetbrains.kotlin.ir.types.IrTypeUtilsKt.isSubtypeOfClass(classifier, symbol);
                            } else {
                                zIsSubtypeOfClass = false;
                            }
                        } else {
                            zIsSubtypeOfClass = false;
                        }
                    }
                }
                boolean z = owner.getDispatchReceiverParameter() != null;
                if (zIsSubtypeOfClass != z) {
                    memberAccessExpressionArgumentsMismatchCheckReferencedDeclaration$default = new PartialLinkageCase.MemberAccessExpressionArgumentsMismatch(irFunctionReference, zIsSubtypeOfClass, z, 0, 0);
                } else if (!(irFunctionReference instanceof IrFunctionAccessExpression) || owner.getIsExternal()) {
                    memberAccessExpressionArgumentsMismatchCheckReferencedDeclaration$default = null;
                } else if (irFunctionReference instanceof IrEnumConstructorCall) {
                    IrDeclarationParent parent4 = owner.getParent();
                    IrClass irClass3 = parent4 instanceof IrClass ? (IrClass) parent4 : null;
                    if (Intrinsics.areEqual(irClass3 != null ? irClass3.getSymbol() : null, PartiallyLinkedIrTreePatcher.this.builtIns.getEnumClass())) {
                        memberAccessExpressionArgumentsMismatchCheckReferencedDeclaration$default = null;
                    } else {
                        lazy = LazyKt.lazy(new PartiallyLinkedIrTreePatcher$ExpressionTransformer$checkArgumentsAndValueParameters$functionsToCheckDefaultValues$2(owner));
                        iterableUntil = RangesKt.until(0, irFunctionReference.getValueArgumentsCount());
                        if ((iterableUntil instanceof Collection) || !((Collection) iterableUntil).isEmpty()) {
                            it = iterableUntil.iterator();
                            while (it.hasNext()) {
                                iNextInt = ((IntIterator) it).nextInt();
                                if (irFunctionReference.getValueArgument(iNextInt) == null) {
                                    it2 = m6111checkArgumentsAndValueParameters$lambda28(lazy).iterator();
                                    do {
                                        if (!it2.hasNext()) {
                                            defaultValue = null;
                                            break;
                                        }
                                        irValueParameter2 = (IrValueParameter) CollectionsKt.getOrNull(((IrFunction) it2.next()).getValueParameters(), iNextInt);
                                        if (irValueParameter2 != null) {
                                            defaultValue = irValueParameter2.getDefaultValue();
                                        } else {
                                            defaultValue = null;
                                        }
                                    } while (defaultValue == null);
                                    if (defaultValue != null && ((irValueParameter = (IrValueParameter) CollectionsKt.getOrNull(owner.getValueParameters(), iNextInt)) == null || !AdditionalIrUtilsKt.isVararg(irValueParameter))) {
                                    }
                                }
                                i++;
                                if (i < 0) {
                                    CollectionsKt.throwCountOverflow();
                                }
                            }
                        }
                        size = owner.getValueParameters().size();
                        if (i != size) {
                            memberAccessExpressionArgumentsMismatchCheckReferencedDeclaration$default = new PartialLinkageCase.MemberAccessExpressionArgumentsMismatch(irFunctionReference, zIsSubtypeOfClass, z, i, size);
                        } else {
                            memberAccessExpressionArgumentsMismatchCheckReferencedDeclaration$default = null;
                        }
                    }
                } else {
                    lazy = LazyKt.lazy(new PartiallyLinkedIrTreePatcher$ExpressionTransformer$checkArgumentsAndValueParameters$functionsToCheckDefaultValues$2(owner));
                    iterableUntil = RangesKt.until(0, irFunctionReference.getValueArgumentsCount());
                    if (iterableUntil instanceof Collection) {
                        it = iterableUntil.iterator();
                        while (it.hasNext()) {
                            iNextInt = ((IntIterator) it).nextInt();
                            if (irFunctionReference.getValueArgument(iNextInt) == null) {
                                it2 = m6111checkArgumentsAndValueParameters$lambda28(lazy).iterator();
                                do {
                                    if (!it2.hasNext()) {
                                        defaultValue = null;
                                        break;
                                    }
                                    irValueParameter2 = (IrValueParameter) CollectionsKt.getOrNull(((IrFunction) it2.next()).getValueParameters(), iNextInt);
                                    if (irValueParameter2 != null) {
                                        defaultValue = irValueParameter2.getDefaultValue();
                                    } else {
                                        defaultValue = null;
                                    }
                                } while (defaultValue == null);
                                if (defaultValue != null) {
                                }
                            }
                            i++;
                            if (i < 0) {
                                CollectionsKt.throwCountOverflow();
                            }
                        }
                    } else {
                        it = iterableUntil.iterator();
                        while (it.hasNext()) {
                            iNextInt = ((IntIterator) it).nextInt();
                            if (irFunctionReference.getValueArgument(iNextInt) == null) {
                                it2 = m6111checkArgumentsAndValueParameters$lambda28(lazy).iterator();
                                do {
                                    if (!it2.hasNext()) {
                                        defaultValue = null;
                                        break;
                                    }
                                    irValueParameter2 = (IrValueParameter) CollectionsKt.getOrNull(((IrFunction) it2.next()).getValueParameters(), iNextInt);
                                    if (irValueParameter2 != null) {
                                        defaultValue = irValueParameter2.getDefaultValue();
                                    } else {
                                        defaultValue = null;
                                    }
                                } while (defaultValue == null);
                                if (defaultValue != null) {
                                }
                            }
                            i++;
                            if (i < 0) {
                                CollectionsKt.throwCountOverflow();
                            }
                        }
                    }
                    size = owner.getValueParameters().size();
                    if (i != size) {
                        memberAccessExpressionArgumentsMismatchCheckReferencedDeclaration$default = new PartialLinkageCase.MemberAccessExpressionArgumentsMismatch(irFunctionReference, zIsSubtypeOfClass, z, i, size);
                    } else {
                        memberAccessExpressionArgumentsMismatchCheckReferencedDeclaration$default = null;
                    }
                }
            }
            if (memberAccessExpressionArgumentsMismatchCheckReferencedDeclaration$default == null) {
                memberAccessExpressionArgumentsMismatchCheckReferencedDeclaration$default = checkExpressionType(irFunctionReference2, irFunctionReference2.getType());
            }
            PartialLinkageCase partialLinkageCase = memberAccessExpressionArgumentsMismatchCheckReferencedDeclaration$default;
            if (partialLinkageCase != null) {
                if (PartiallyLinkedIrTreePatcher.INSTANCE.hasBranches(irFunctionReference2)) {
                    empty = DirectChildren.INSTANCE.getEMPTY();
                } else {
                    DirectChildrenStatementsCollector directChildrenStatementsCollector = new DirectChildrenStatementsCollector();
                    IrVisitorsKt.acceptChildrenVoid(irFunctionReference3, directChildrenStatementsCollector);
                    empty = directChildrenStatementsCollector.getResult();
                }
                IrCall irCallThrowLinkageError$default = PartialLinkageSupportForLowerings.DefaultImpls.throwLinkageError$default(partiallyLinkedIrTreePatcher.getSupportForLowerings(), partialLinkageCase, irFunctionReference3, expressionTransformer.getCurrentFile(), false, 8, null);
                if (!empty.getStatements().isEmpty()) {
                    IrCompositeImpl irCompositeImpl = new IrCompositeImpl(irFunctionReference2.getStartOffset(), irFunctionReference2.getEndOffset(), partiallyLinkedIrTreePatcher.builtIns.getNothingType(), IrStatementOrigin.PARTIAL_LINKAGE_RUNTIME_ERROR.INSTANCE);
                    CollectionsKt.addAll(irCompositeImpl.getStatements(), empty.getStatements());
                    if (!empty.getHasPartialLinkageRuntimeError()) {
                        irCompositeImpl.getStatements().add(irCallThrowLinkageError$default);
                    }
                    irCall = irCompositeImpl;
                } else {
                    irCall = irCallThrowLinkageError$default;
                }
            } else {
                IrContainerExpression irContainerExpression = irFunctionReference2 instanceof IrContainerExpression ? (IrContainerExpression) irFunctionReference2 : null;
                if (irContainerExpression != null && (statements = irContainerExpression.getStatements()) != null) {
                    PartiallyLinkedIrTreePatcher.INSTANCE.eliminateDeadCodeStatements(statements);
                }
                irCall = irFunctionReference2;
            }
            onAfterMaybeThrowLinkageError(irFunctionReference2);
            return irCall;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
        public IrExpression visitPropertyReference(IrPropertyReference expression) {
            DirectChildren empty;
            IrCall irCall;
            List<IrStatement> statements;
            Intrinsics.checkNotNullParameter(expression, "expression");
            IrPropertyReference irPropertyReference = expression;
            PartiallyLinkedIrTreePatcher partiallyLinkedIrTreePatcher = PartiallyLinkedIrTreePatcher.this;
            IrPropertyReference irPropertyReference2 = irPropertyReference;
            ExpressionTransformer expressionTransformer = this;
            IrElementTransformerVoidKt.transformChildrenVoid(irPropertyReference2, expressionTransformer);
            IrPropertyReference irPropertyReference3 = irPropertyReference;
            IrPropertyReference irPropertyReference4 = irPropertyReference3;
            PartialLinkageCase partialLinkageCaseCheckReferencedDeclaration$default = checkReferencedDeclaration$default(this, irPropertyReference4, irPropertyReference3.getSymbol(), false, 2, null);
            if (partialLinkageCaseCheckReferencedDeclaration$default == null && (partialLinkageCaseCheckReferencedDeclaration$default = checkReferencedDeclaration$default(this, irPropertyReference4, irPropertyReference3.getGetter(), false, 2, null)) == null && (partialLinkageCaseCheckReferencedDeclaration$default = checkReferencedDeclaration$default(this, irPropertyReference4, irPropertyReference3.getSetter(), false, 2, null)) == null && (partialLinkageCaseCheckReferencedDeclaration$default = checkReferencedDeclaration$default(this, irPropertyReference4, irPropertyReference3.getField(), false, 2, null)) == null) {
                partialLinkageCaseCheckReferencedDeclaration$default = checkExpressionTypeArguments(irPropertyReference3);
            }
            PartialLinkageCase partialLinkageCaseCheckExpressionType = partialLinkageCaseCheckReferencedDeclaration$default == null ? checkExpressionType(irPropertyReference, irPropertyReference.getType()) : partialLinkageCaseCheckReferencedDeclaration$default;
            if (partialLinkageCaseCheckExpressionType != null) {
                if (PartiallyLinkedIrTreePatcher.INSTANCE.hasBranches(irPropertyReference)) {
                    empty = DirectChildren.INSTANCE.getEMPTY();
                } else {
                    DirectChildrenStatementsCollector directChildrenStatementsCollector = new DirectChildrenStatementsCollector();
                    IrVisitorsKt.acceptChildrenVoid(irPropertyReference2, directChildrenStatementsCollector);
                    empty = directChildrenStatementsCollector.getResult();
                }
                DirectChildren directChildren = empty;
                IrCall irCallThrowLinkageError$default = PartialLinkageSupportForLowerings.DefaultImpls.throwLinkageError$default(partiallyLinkedIrTreePatcher.getSupportForLowerings(), partialLinkageCaseCheckExpressionType, irPropertyReference2, expressionTransformer.getCurrentFile(), false, 8, null);
                if (!directChildren.getStatements().isEmpty()) {
                    IrCompositeImpl irCompositeImpl = new IrCompositeImpl(irPropertyReference.getStartOffset(), irPropertyReference.getEndOffset(), partiallyLinkedIrTreePatcher.builtIns.getNothingType(), IrStatementOrigin.PARTIAL_LINKAGE_RUNTIME_ERROR.INSTANCE);
                    CollectionsKt.addAll(irCompositeImpl.getStatements(), directChildren.getStatements());
                    if (!directChildren.getHasPartialLinkageRuntimeError()) {
                        irCompositeImpl.getStatements().add(irCallThrowLinkageError$default);
                    }
                    irCall = irCompositeImpl;
                } else {
                    irCall = irCallThrowLinkageError$default;
                }
            } else {
                IrContainerExpression irContainerExpression = irPropertyReference instanceof IrContainerExpression ? (IrContainerExpression) irPropertyReference : null;
                if (irContainerExpression != null && (statements = irContainerExpression.getStatements()) != null) {
                    PartiallyLinkedIrTreePatcher.INSTANCE.eliminateDeadCodeStatements(statements);
                }
                irCall = irPropertyReference;
            }
            onAfterMaybeThrowLinkageError(irPropertyReference);
            return irCall;
        }

        private final PartialLinkageCase checkExpressionType(IrExpression irExpression, IrType irType) {
            ExploredClassifier.Unusable unusableExplore = PartiallyLinkedIrTreePatcher.this.explore(irType);
            if (unusableExplore == null) {
                return null;
            }
            return new PartialLinkageCase.ExpressionWithUnusableClassifier(irExpression, unusableExplore);
        }

        /* JADX WARN: Code duplicated, block: B:13:0x0034 A[RETURN] */
        /* JADX WARN: Code duplicated, block: B:14:0x0035  */
        private final PartialLinkageCase checkExpressionTypeArguments(IrMemberAccessExpression<?> irMemberAccessExpression) {
            ExploredClassifier.Unusable unusableExplore;
            IrMemberAccessExpression<?> irMemberAccessExpression2 = irMemberAccessExpression;
            IntRange intRangeUntil = RangesKt.until(0, irMemberAccessExpression.getTypeArgumentsCount());
            PartiallyLinkedIrTreePatcher partiallyLinkedIrTreePatcher = PartiallyLinkedIrTreePatcher.this;
            Iterator<Integer> it = intRangeUntil.iterator();
            while (it.hasNext()) {
                IrType typeArgument = irMemberAccessExpression.getTypeArgument(((IntIterator) it).nextInt());
                unusableExplore = typeArgument != null ? partiallyLinkedIrTreePatcher.explore(typeArgument) : null;
                if (unusableExplore != null) {
                    if (unusableExplore == null) {
                        return null;
                    }
                    return new PartialLinkageCase.ExpressionWithUnusableClassifier(irMemberAccessExpression2, unusableExplore);
                }
            }
            unusableExplore = null;
            if (unusableExplore == null) {
                return null;
            }
            return new PartialLinkageCase.ExpressionWithUnusableClassifier(irMemberAccessExpression2, unusableExplore);
        }

        static /* synthetic */ PartialLinkageCase checkReferencedDeclaration$default(ExpressionTransformer expressionTransformer, IrExpression irExpression, IrSymbol irSymbol, boolean z, int i, Object obj) {
            if (obj != null) {
                throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: checkReferencedDeclaration");
            }
            if ((i & 2) != 0) {
                z = true;
            }
            return expressionTransformer.checkReferencedDeclaration(irExpression, irSymbol, z);
        }

        /* JADX WARN: Code restructure failed: missing block: B:114:0x01ae, code lost:
        
            if (((r9 != null ? r9.getContainer() : null) instanceof org.jetbrains.kotlin.ir.util.IdSignature.FileSignature) != false) goto L115;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        private final org.jetbrains.kotlin.ir.linkage.partial.PartialLinkageCase checkReferencedDeclaration(org.jetbrains.kotlin.ir.expressions.IrExpression r7, org.jetbrains.kotlin.ir.symbols.IrSymbol r8, boolean r9) {
            /*
                Method dump skipped, instruction units count: 590
                To view this dump add '--comments-level debug' option
            */
            throw new UnsupportedOperationException("Method not decompiled: org.jetbrains.kotlin.backend.common.linkage.partial.PartiallyLinkedIrTreePatcher.ExpressionTransformer.checkReferencedDeclaration(org.jetbrains.kotlin.ir.expressions.IrExpression, org.jetbrains.kotlin.ir.symbols.IrSymbol, boolean):org.jetbrains.kotlin.ir.linkage.partial.PartialLinkageCase");
        }

        private final ExploredClassifier.Unusable precalculatedUnusableClassifier(IrType irType) {
            ExploredClassifier.Unusable unusableClassifier;
            PartiallyLinkedMarkerType partiallyLinkedMarkerType = irType instanceof PartiallyLinkedMarkerType ? (PartiallyLinkedMarkerType) irType : null;
            return (partiallyLinkedMarkerType == null || (unusableClassifier = partiallyLinkedMarkerType.getUnusableClassifier()) == null) ? PartiallyLinkedIrTreePatcher.this.explore(irType) : unusableClassifier;
        }

        private final ExploredClassifier.Unusable precalculatedUnusableClassifier(List<? extends IrType> list) {
            Iterator<T> it = list.iterator();
            while (it.hasNext()) {
                ExploredClassifier.Unusable unusablePrecalculatedUnusableClassifier = precalculatedUnusableClassifier((IrType) it.next());
                if (unusablePrecalculatedUnusableClassifier != null) {
                    return unusablePrecalculatedUnusableClassifier;
                }
            }
            return null;
        }

        protected final <D extends IrDeclaration> PartialLinkageCase checkReferencedDeclarationType(IrExpression irExpression, D declaration, String expectedDeclarationDescription, Function1<? super D, Boolean> checkDeclarationType) {
            Intrinsics.checkNotNullParameter(irExpression, "<this>");
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            Intrinsics.checkNotNullParameter(expectedDeclarationDescription, "expectedDeclarationDescription");
            Intrinsics.checkNotNullParameter(checkDeclarationType, "checkDeclarationType");
            if (checkDeclarationType.invoke(declaration).booleanValue()) {
                return null;
            }
            return new PartialLinkageCase.ExpressionHasWrongTypeOfDeclaration(irExpression, declaration.getSymbol(), expectedDeclarationDescription);
        }

        /* JADX WARN: Code duplicated, block: B:16:0x0045  */
        /* JADX WARN: Code duplicated, block: B:30:0x0068  */
        /* JADX WARN: Code duplicated, block: B:57:0x00ba  */
        public static /* synthetic */ PartialLinkageCase checkArgumentsAndValueParameters$default(ExpressionTransformer expressionTransformer, IrMemberAccessExpression irMemberAccessExpression, Function2 checkDefaultArgument, int i, Object obj) {
            boolean z;
            IrDeclarationParent parent;
            IrClass irClass;
            boolean zIsSubtypeOfClass;
            IrClassSymbol symbol;
            IrExpressionBody defaultValue;
            IrValueParameter irValueParameter;
            if (obj == null) {
                if ((i & 1) != 0) {
                    checkDefaultArgument = new Function2<Integer, IrExpressionBody, Boolean>() { // from class: org.jetbrains.kotlin.backend.common.linkage.partial.PartiallyLinkedIrTreePatcher$ExpressionTransformer$checkArgumentsAndValueParameters$1
                        public final Boolean invoke(int i2, IrExpressionBody irExpressionBody) {
                            return Boolean.valueOf(irExpressionBody != null);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public /* bridge */ /* synthetic */ Boolean invoke(Integer num, IrExpressionBody irExpressionBody) {
                            return invoke(num.intValue(), irExpressionBody);
                        }
                    };
                }
                Intrinsics.checkNotNullParameter(irMemberAccessExpression, "<this>");
                Intrinsics.checkNotNullParameter(checkDefaultArgument, "checkDefaultArgument");
                IrFunction owner = ((IrFunctionSymbol) irMemberAccessExpression.getSymbol()).getOwner();
                int i2 = 0;
                if (irMemberAccessExpression.getDispatchReceiver() != null) {
                    z = true;
                } else if (irMemberAccessExpression instanceof IrFunctionReference) {
                    IrFunctionReference irFunctionReference = (IrFunctionReference) irMemberAccessExpression;
                    IrFunctionSymbol symbol2 = irFunctionReference.getSymbol();
                    if (symbol2 instanceof IrSimpleFunctionSymbol) {
                        IrDeclarationParent parent2 = owner.getParent();
                        if (parent2 instanceof IrClass) {
                            irClass = (IrClass) parent2;
                        } else {
                            irClass = null;
                        }
                    } else {
                        if (!(symbol2 instanceof IrConstructorSymbol)) {
                            throw new NoWhenBranchMatchedException();
                        }
                        IrDeclarationParent parent3 = owner.getParent();
                        IrClass irClass2 = parent3 instanceof IrClass ? (IrClass) parent3 : null;
                        if (irClass2 == null) {
                            parent = null;
                        } else {
                            if (!irClass2.getIsInner()) {
                                irClass2 = null;
                            }
                            if (irClass2 != null) {
                                parent = irClass2.getParent();
                            } else {
                                parent = null;
                            }
                        }
                        if (parent instanceof IrClass) {
                            irClass = (IrClass) parent;
                        } else {
                            irClass = null;
                        }
                    }
                    if (irClass == null || (symbol = irClass.getSymbol()) == null) {
                        zIsSubtypeOfClass = false;
                    } else {
                        IrType type = irFunctionReference.getType();
                        IrSimpleType irSimpleType = type instanceof IrSimpleType ? (IrSimpleType) type : null;
                        if (irSimpleType != null && (IrTypeUtilsKt.isKFunction(irSimpleType.getClassifier()) || IrTypeUtilsKt.isKSuspendFunction(irSimpleType.getClassifier()))) {
                            Object objFirstOrNull = CollectionsKt.firstOrNull((List<? extends Object>) irSimpleType.getArguments());
                            IrSimpleType irSimpleType2 = objFirstOrNull instanceof IrSimpleType ? (IrSimpleType) objFirstOrNull : null;
                            IrClassifierSymbol classifier = irSimpleType2 != null ? irSimpleType2.getClassifier() : null;
                            if (classifier != null) {
                                zIsSubtypeOfClass = org.jetbrains.kotlin.ir.types.IrTypeUtilsKt.isSubtypeOfClass(classifier, symbol);
                            } else {
                                zIsSubtypeOfClass = false;
                            }
                        } else {
                            zIsSubtypeOfClass = false;
                        }
                    }
                    z = zIsSubtypeOfClass;
                } else {
                    z = false;
                }
                boolean z2 = owner.getDispatchReceiverParameter() != null;
                if (z != z2) {
                    return new PartialLinkageCase.MemberAccessExpressionArgumentsMismatch(irMemberAccessExpression, z, z2, 0, 0);
                }
                if (irMemberAccessExpression instanceof IrFunctionAccessExpression) {
                    if (owner.getIsExternal()) {
                        return null;
                    }
                    if (irMemberAccessExpression instanceof IrEnumConstructorCall) {
                        IrDeclarationParent parent4 = owner.getParent();
                        IrClass irClass3 = parent4 instanceof IrClass ? (IrClass) parent4 : null;
                        if (Intrinsics.areEqual(irClass3 != null ? irClass3.getSymbol() : null, PartiallyLinkedIrTreePatcher.this.builtIns.getEnumClass())) {
                            return null;
                        }
                    }
                } else if (irMemberAccessExpression instanceof IrFunctionReference) {
                    return null;
                }
                Lazy lazy = LazyKt.lazy(new PartiallyLinkedIrTreePatcher$ExpressionTransformer$checkArgumentsAndValueParameters$functionsToCheckDefaultValues$2(owner));
                Iterable iterableUntil = RangesKt.until(0, irMemberAccessExpression.getValueArgumentsCount());
                if (!(iterableUntil instanceof Collection) || !((Collection) iterableUntil).isEmpty()) {
                    Iterator it = iterableUntil.iterator();
                    while (it.hasNext()) {
                        int iNextInt = ((IntIterator) it).nextInt();
                        if (irMemberAccessExpression.getValueArgument(iNextInt) == null) {
                            Iterator it2 = m6111checkArgumentsAndValueParameters$lambda28(lazy).iterator();
                            do {
                                if (!it2.hasNext()) {
                                    defaultValue = null;
                                    break;
                                }
                                IrValueParameter irValueParameter2 = (IrValueParameter) CollectionsKt.getOrNull(((IrFunction) it2.next()).getValueParameters(), iNextInt);
                                defaultValue = irValueParameter2 != null ? irValueParameter2.getDefaultValue() : null;
                            } while (defaultValue == null);
                            if (((Boolean) checkDefaultArgument.invoke(Integer.valueOf(iNextInt), defaultValue)).booleanValue() || ((irValueParameter = (IrValueParameter) CollectionsKt.getOrNull(owner.getValueParameters(), iNextInt)) != null && AdditionalIrUtilsKt.isVararg(irValueParameter))) {
                            }
                        }
                        i2++;
                        if (i2 < 0) {
                            CollectionsKt.throwCountOverflow();
                        }
                    }
                }
                int i3 = i2;
                int size = owner.getValueParameters().size();
                if (i3 != size) {
                    return new PartialLinkageCase.MemberAccessExpressionArgumentsMismatch(irMemberAccessExpression, z, z2, i3, size);
                }
                return null;
            }
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: checkArgumentsAndValueParameters");
        }

        /* JADX WARN: Code duplicated, block: B:12:0x003c  */
        /* JADX WARN: Code duplicated, block: B:26:0x005f  */
        /* JADX WARN: Code duplicated, block: B:53:0x00b1  */
        protected final PartialLinkageCase checkArgumentsAndValueParameters(IrMemberAccessExpression<IrFunctionSymbol> irMemberAccessExpression, Function2<? super Integer, ? super IrExpressionBody, Boolean> checkDefaultArgument) {
            boolean z;
            IrDeclarationParent parent;
            IrClass irClass;
            boolean zIsSubtypeOfClass;
            IrClassSymbol symbol;
            IrExpressionBody defaultValue;
            IrValueParameter irValueParameter;
            Intrinsics.checkNotNullParameter(irMemberAccessExpression, "<this>");
            Intrinsics.checkNotNullParameter(checkDefaultArgument, "checkDefaultArgument");
            IrFunction owner = ((IrFunctionSymbol) irMemberAccessExpression.getSymbol()).getOwner();
            int i = 0;
            if (irMemberAccessExpression.getDispatchReceiver() != null) {
                z = true;
            } else if (irMemberAccessExpression instanceof IrFunctionReference) {
                IrFunctionReference irFunctionReference = (IrFunctionReference) irMemberAccessExpression;
                IrFunctionSymbol symbol2 = irFunctionReference.getSymbol();
                if (symbol2 instanceof IrSimpleFunctionSymbol) {
                    IrDeclarationParent parent2 = owner.getParent();
                    if (parent2 instanceof IrClass) {
                        irClass = (IrClass) parent2;
                    } else {
                        irClass = null;
                    }
                } else {
                    if (!(symbol2 instanceof IrConstructorSymbol)) {
                        throw new NoWhenBranchMatchedException();
                    }
                    IrDeclarationParent parent3 = owner.getParent();
                    IrClass irClass2 = parent3 instanceof IrClass ? (IrClass) parent3 : null;
                    if (irClass2 == null) {
                        parent = null;
                    } else {
                        if (!irClass2.getIsInner()) {
                            irClass2 = null;
                        }
                        if (irClass2 != null) {
                            parent = irClass2.getParent();
                        } else {
                            parent = null;
                        }
                    }
                    if (parent instanceof IrClass) {
                        irClass = (IrClass) parent;
                    } else {
                        irClass = null;
                    }
                }
                if (irClass == null || (symbol = irClass.getSymbol()) == null) {
                    zIsSubtypeOfClass = false;
                } else {
                    IrType type = irFunctionReference.getType();
                    IrSimpleType irSimpleType = type instanceof IrSimpleType ? (IrSimpleType) type : null;
                    if (irSimpleType != null && (IrTypeUtilsKt.isKFunction(irSimpleType.getClassifier()) || IrTypeUtilsKt.isKSuspendFunction(irSimpleType.getClassifier()))) {
                        Object objFirstOrNull = CollectionsKt.firstOrNull((List<? extends Object>) irSimpleType.getArguments());
                        IrSimpleType irSimpleType2 = objFirstOrNull instanceof IrSimpleType ? (IrSimpleType) objFirstOrNull : null;
                        IrClassifierSymbol classifier = irSimpleType2 != null ? irSimpleType2.getClassifier() : null;
                        if (classifier != null) {
                            zIsSubtypeOfClass = org.jetbrains.kotlin.ir.types.IrTypeUtilsKt.isSubtypeOfClass(classifier, symbol);
                        } else {
                            zIsSubtypeOfClass = false;
                        }
                    } else {
                        zIsSubtypeOfClass = false;
                    }
                }
                z = zIsSubtypeOfClass;
            } else {
                z = false;
            }
            boolean z2 = owner.getDispatchReceiverParameter() != null;
            if (z != z2) {
                return new PartialLinkageCase.MemberAccessExpressionArgumentsMismatch(irMemberAccessExpression, z, z2, 0, 0);
            }
            if (irMemberAccessExpression instanceof IrFunctionAccessExpression) {
                if (owner.getIsExternal()) {
                    return null;
                }
                if (irMemberAccessExpression instanceof IrEnumConstructorCall) {
                    IrDeclarationParent parent4 = owner.getParent();
                    IrClass irClass3 = parent4 instanceof IrClass ? (IrClass) parent4 : null;
                    if (Intrinsics.areEqual(irClass3 != null ? irClass3.getSymbol() : null, PartiallyLinkedIrTreePatcher.this.builtIns.getEnumClass())) {
                        return null;
                    }
                }
            } else if (irMemberAccessExpression instanceof IrFunctionReference) {
                return null;
            }
            Lazy lazy = LazyKt.lazy(new PartiallyLinkedIrTreePatcher$ExpressionTransformer$checkArgumentsAndValueParameters$functionsToCheckDefaultValues$2(owner));
            Iterable iterableUntil = RangesKt.until(0, irMemberAccessExpression.getValueArgumentsCount());
            if (!(iterableUntil instanceof Collection) || !((Collection) iterableUntil).isEmpty()) {
                Iterator it = iterableUntil.iterator();
                while (it.hasNext()) {
                    int iNextInt = ((IntIterator) it).nextInt();
                    if (irMemberAccessExpression.getValueArgument(iNextInt) == null) {
                        Iterator it2 = m6111checkArgumentsAndValueParameters$lambda28(lazy).iterator();
                        do {
                            if (!it2.hasNext()) {
                                defaultValue = null;
                                break;
                            }
                            IrValueParameter irValueParameter2 = (IrValueParameter) CollectionsKt.getOrNull(((IrFunction) it2.next()).getValueParameters(), iNextInt);
                            defaultValue = irValueParameter2 != null ? irValueParameter2.getDefaultValue() : null;
                        } while (defaultValue == null);
                        if (checkDefaultArgument.invoke(Integer.valueOf(iNextInt), defaultValue).booleanValue() || ((irValueParameter = (IrValueParameter) CollectionsKt.getOrNull(owner.getValueParameters(), iNextInt)) != null && AdditionalIrUtilsKt.isVararg(irValueParameter))) {
                        }
                    }
                    i++;
                    if (i < 0) {
                        CollectionsKt.throwCountOverflow();
                    }
                }
            }
            int i2 = i;
            int size = owner.getValueParameters().size();
            if (i2 != size) {
                return new PartialLinkageCase.MemberAccessExpressionArgumentsMismatch(irMemberAccessExpression, z, z2, i2, size);
            }
            return null;
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX INFO: renamed from: checkArgumentsAndValueParameters$lambda-28, reason: not valid java name */
        public static final List<IrFunction> m6111checkArgumentsAndValueParameters$lambda28(Lazy<? extends List<? extends IrFunction>> lazy) {
            return (List) lazy.getValue();
        }

        private final PartialLinkageCase checkSamConversion(IrTypeOperatorCall irTypeOperatorCall) {
            IrClassSymbol classOrNull;
            IrClass owner;
            if (irTypeOperatorCall.getOperator() != IrTypeOperator.SAM_CONVERSION || (classOrNull = IrTypesKt.getClassOrNull(irTypeOperatorCall.getTypeOperand())) == null || (owner = classOrNull.getOwner()) == null) {
                return null;
            }
            HashSet hashSetNewHashSetWithExpectedSize = org.jetbrains.kotlin.utils.CollectionsKt.newHashSetWithExpectedSize(owner.getDeclarations().size());
            for (IrDeclaration irDeclaration : owner.getDeclarations()) {
                if (irDeclaration instanceof IrSimpleFunction) {
                    IrSimpleFunction irSimpleFunction = (IrSimpleFunction) irDeclaration;
                    if (irSimpleFunction.getModality() == Modality.ABSTRACT) {
                        hashSetNewHashSetWithExpectedSize.add(irSimpleFunction.getSymbol());
                    }
                } else if (irDeclaration instanceof IrProperty) {
                    IrProperty irProperty = (IrProperty) irDeclaration;
                    if (irProperty.getModality() == Modality.ABSTRACT) {
                        return new PartialLinkageCase.InvalidSamConversion(irTypeOperatorCall, SetsKt.emptySet(), irProperty.getSymbol());
                    }
                } else {
                    continue;
                }
            }
            if (hashSetNewHashSetWithExpectedSize.size() != 1) {
                return new PartialLinkageCase.InvalidSamConversion(irTypeOperatorCall, hashSetNewHashSetWithExpectedSize, null);
            }
            return null;
        }

        private final PartialLinkageCase checkNotAbstractClass(IrConstructorCall irConstructorCall) {
            IrClass parentAsClass = IrUtilsKt.getParentAsClass(irConstructorCall.getSymbol().getOwner());
            if (parentAsClass.getModality() == Modality.ABSTRACT || parentAsClass.getModality() == Modality.SEALED) {
                return new PartialLinkageCase.AbstractClassInstantiation(irConstructorCall, parentAsClass.getSymbol());
            }
            return null;
        }

        /* JADX WARN: Code duplicated, block: B:100:0x018c  */
        /* JADX WARN: Code duplicated, block: B:101:0x0191  */
        /* JADX WARN: Code duplicated, block: B:107:0x0199  */
        /* JADX WARN: Code duplicated, block: B:116:0x01bd  */
        /* JADX WARN: Code duplicated, block: B:118:0x01ab A[ADDED_TO_REGION, REMOVE, SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:121:0x01ab A[SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:123:0x01af A[SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:127:0x0156 A[SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:129:0x0195 A[SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:14:0x0059  */
        /* JADX WARN: Code duplicated, block: B:28:0x007c  */
        /* JADX WARN: Code duplicated, block: B:57:0x00d4  */
        /* JADX WARN: Code duplicated, block: B:84:0x012e  */
        /* JADX WARN: Code duplicated, block: B:86:0x0147  */
        /* JADX WARN: Code duplicated, block: B:89:0x0152  */
        /* JADX WARN: Code duplicated, block: B:92:0x015c  */
        /* JADX WARN: Code duplicated, block: B:95:0x016a  */
        /* JADX WARN: Code duplicated, block: B:98:0x017a  */
        protected PartialLinkageCase customConstructorCallChecks(IrConstructorCall irConstructorCall) {
            boolean zIsSubtypeOfClass;
            IrDeclarationParent parent;
            IrClass irClass;
            IrClassSymbol symbol;
            Lazy lazy;
            Iterable iterableUntil;
            Iterator it;
            int iNextInt;
            Iterator it2;
            IrExpressionBody defaultValue;
            IrValueParameter irValueParameter;
            IrValueParameter irValueParameter2;
            int i;
            int size;
            Intrinsics.checkNotNullParameter(irConstructorCall, "<this>");
            PartialLinkageCase partialLinkageCaseCheckReferencedDeclarationType = checkReferencedDeclarationType(irConstructorCall, IrUtilsKt.getParentAsClass(irConstructorCall.getSymbol().getOwner()), "class", new Function1<IrClass, Boolean>() { // from class: org.jetbrains.kotlin.backend.common.linkage.partial.PartiallyLinkedIrTreePatcher$ExpressionTransformer$customConstructorCallChecks$1
                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(IrClass constructedClass) {
                    Intrinsics.checkNotNullParameter(constructedClass, "constructedClass");
                    return Boolean.valueOf(constructedClass.getKind() == ClassKind.CLASS || constructedClass.getKind() == ClassKind.ANNOTATION_CLASS);
                }
            });
            if (partialLinkageCaseCheckReferencedDeclarationType != null) {
                return partialLinkageCaseCheckReferencedDeclarationType;
            }
            IrConstructorCall irConstructorCall2 = irConstructorCall;
            IrFunction owner = ((IrFunctionSymbol) irConstructorCall2.getSymbol()).getOwner();
            int i2 = 0;
            if (irConstructorCall2.getDispatchReceiver() != null) {
                zIsSubtypeOfClass = true;
            } else if (irConstructorCall2 instanceof IrFunctionReference) {
                IrFunctionReference irFunctionReference = (IrFunctionReference) irConstructorCall2;
                IrFunctionSymbol symbol2 = irFunctionReference.getSymbol();
                if (symbol2 instanceof IrSimpleFunctionSymbol) {
                    IrDeclarationParent parent2 = owner.getParent();
                    if (parent2 instanceof IrClass) {
                        irClass = (IrClass) parent2;
                    } else {
                        irClass = null;
                    }
                } else {
                    if (!(symbol2 instanceof IrConstructorSymbol)) {
                        throw new NoWhenBranchMatchedException();
                    }
                    IrDeclarationParent parent3 = owner.getParent();
                    IrClass irClass2 = parent3 instanceof IrClass ? (IrClass) parent3 : null;
                    if (irClass2 == null) {
                        parent = null;
                    } else {
                        if (!irClass2.getIsInner()) {
                            irClass2 = null;
                        }
                        if (irClass2 != null) {
                            parent = irClass2.getParent();
                        } else {
                            parent = null;
                        }
                    }
                    if (parent instanceof IrClass) {
                        irClass = (IrClass) parent;
                    } else {
                        irClass = null;
                    }
                }
                if (irClass == null || (symbol = irClass.getSymbol()) == null) {
                    zIsSubtypeOfClass = false;
                } else {
                    IrType type = irFunctionReference.getType();
                    IrSimpleType irSimpleType = type instanceof IrSimpleType ? (IrSimpleType) type : null;
                    if (irSimpleType != null && (IrTypeUtilsKt.isKFunction(irSimpleType.getClassifier()) || IrTypeUtilsKt.isKSuspendFunction(irSimpleType.getClassifier()))) {
                        Object objFirstOrNull = CollectionsKt.firstOrNull((List<? extends Object>) irSimpleType.getArguments());
                        IrSimpleType irSimpleType2 = objFirstOrNull instanceof IrSimpleType ? (IrSimpleType) objFirstOrNull : null;
                        IrClassifierSymbol classifier = irSimpleType2 != null ? irSimpleType2.getClassifier() : null;
                        if (classifier != null) {
                            zIsSubtypeOfClass = org.jetbrains.kotlin.ir.types.IrTypeUtilsKt.isSubtypeOfClass(classifier, symbol);
                        } else {
                            zIsSubtypeOfClass = false;
                        }
                    } else {
                        zIsSubtypeOfClass = false;
                    }
                }
            } else {
                zIsSubtypeOfClass = false;
            }
            boolean z = owner.getDispatchReceiverParameter() != null;
            if (zIsSubtypeOfClass != z) {
                return new PartialLinkageCase.MemberAccessExpressionArgumentsMismatch(irConstructorCall2, zIsSubtypeOfClass, z, 0, 0);
            }
            if (irConstructorCall2 instanceof IrFunctionAccessExpression) {
                if (!owner.getIsExternal()) {
                    if (irConstructorCall2 instanceof IrEnumConstructorCall) {
                        IrDeclarationParent parent4 = owner.getParent();
                        IrClass irClass3 = parent4 instanceof IrClass ? (IrClass) parent4 : null;
                        if (!Intrinsics.areEqual(irClass3 != null ? irClass3.getSymbol() : null, PartiallyLinkedIrTreePatcher.this.builtIns.getEnumClass())) {
                            lazy = LazyKt.lazy(new PartiallyLinkedIrTreePatcher$ExpressionTransformer$checkArgumentsAndValueParameters$functionsToCheckDefaultValues$2(owner));
                            iterableUntil = RangesKt.until(0, irConstructorCall2.getValueArgumentsCount());
                            if ((iterableUntil instanceof Collection) || !((Collection) iterableUntil).isEmpty()) {
                                it = iterableUntil.iterator();
                                while (it.hasNext()) {
                                    iNextInt = ((IntIterator) it).nextInt();
                                    if (irConstructorCall2.getValueArgument(iNextInt) == null) {
                                        it2 = m6111checkArgumentsAndValueParameters$lambda28(lazy).iterator();
                                        do {
                                            if (!it2.hasNext()) {
                                                defaultValue = null;
                                                break;
                                            }
                                            irValueParameter2 = (IrValueParameter) CollectionsKt.getOrNull(((IrFunction) it2.next()).getValueParameters(), iNextInt);
                                            if (irValueParameter2 != null) {
                                                defaultValue = irValueParameter2.getDefaultValue();
                                            } else {
                                                defaultValue = null;
                                            }
                                        } while (defaultValue == null);
                                        if (defaultValue != null && ((irValueParameter = (IrValueParameter) CollectionsKt.getOrNull(owner.getValueParameters(), iNextInt)) == null || !AdditionalIrUtilsKt.isVararg(irValueParameter))) {
                                        }
                                    }
                                    i2++;
                                    if (i2 < 0) {
                                        CollectionsKt.throwCountOverflow();
                                    }
                                }
                            }
                            i = i2;
                            size = owner.getValueParameters().size();
                            if (i != size) {
                                return new PartialLinkageCase.MemberAccessExpressionArgumentsMismatch(irConstructorCall2, zIsSubtypeOfClass, z, i, size);
                            }
                        }
                    } else {
                        lazy = LazyKt.lazy(new PartiallyLinkedIrTreePatcher$ExpressionTransformer$checkArgumentsAndValueParameters$functionsToCheckDefaultValues$2(owner));
                        iterableUntil = RangesKt.until(0, irConstructorCall2.getValueArgumentsCount());
                        if (iterableUntil instanceof Collection) {
                            it = iterableUntil.iterator();
                            while (it.hasNext()) {
                                iNextInt = ((IntIterator) it).nextInt();
                                if (irConstructorCall2.getValueArgument(iNextInt) == null) {
                                    it2 = m6111checkArgumentsAndValueParameters$lambda28(lazy).iterator();
                                    do {
                                        if (!it2.hasNext()) {
                                            defaultValue = null;
                                            break;
                                        }
                                        irValueParameter2 = (IrValueParameter) CollectionsKt.getOrNull(((IrFunction) it2.next()).getValueParameters(), iNextInt);
                                        if (irValueParameter2 != null) {
                                            defaultValue = irValueParameter2.getDefaultValue();
                                        } else {
                                            defaultValue = null;
                                        }
                                    } while (defaultValue == null);
                                    if (defaultValue != null) {
                                    }
                                }
                                i2++;
                                if (i2 < 0) {
                                    CollectionsKt.throwCountOverflow();
                                }
                            }
                        } else {
                            it = iterableUntil.iterator();
                            while (it.hasNext()) {
                                iNextInt = ((IntIterator) it).nextInt();
                                if (irConstructorCall2.getValueArgument(iNextInt) == null) {
                                    it2 = m6111checkArgumentsAndValueParameters$lambda28(lazy).iterator();
                                    do {
                                        if (!it2.hasNext()) {
                                            defaultValue = null;
                                            break;
                                        }
                                        irValueParameter2 = (IrValueParameter) CollectionsKt.getOrNull(((IrFunction) it2.next()).getValueParameters(), iNextInt);
                                        if (irValueParameter2 != null) {
                                            defaultValue = irValueParameter2.getDefaultValue();
                                        } else {
                                            defaultValue = null;
                                        }
                                    } while (defaultValue == null);
                                    if (defaultValue != null) {
                                    }
                                }
                                i2++;
                                if (i2 < 0) {
                                    CollectionsKt.throwCountOverflow();
                                }
                            }
                        }
                        i = i2;
                        size = owner.getValueParameters().size();
                        if (i != size) {
                            return new PartialLinkageCase.MemberAccessExpressionArgumentsMismatch(irConstructorCall2, zIsSubtypeOfClass, z, i, size);
                        }
                    }
                }
            } else if (!(irConstructorCall2 instanceof IrFunctionReference)) {
                lazy = LazyKt.lazy(new PartiallyLinkedIrTreePatcher$ExpressionTransformer$checkArgumentsAndValueParameters$functionsToCheckDefaultValues$2(owner));
                iterableUntil = RangesKt.until(0, irConstructorCall2.getValueArgumentsCount());
                if (iterableUntil instanceof Collection) {
                    it = iterableUntil.iterator();
                    while (it.hasNext()) {
                        iNextInt = ((IntIterator) it).nextInt();
                        if (irConstructorCall2.getValueArgument(iNextInt) == null) {
                            it2 = m6111checkArgumentsAndValueParameters$lambda28(lazy).iterator();
                            do {
                                if (!it2.hasNext()) {
                                    defaultValue = null;
                                    break;
                                }
                                irValueParameter2 = (IrValueParameter) CollectionsKt.getOrNull(((IrFunction) it2.next()).getValueParameters(), iNextInt);
                                if (irValueParameter2 != null) {
                                    defaultValue = irValueParameter2.getDefaultValue();
                                } else {
                                    defaultValue = null;
                                }
                            } while (defaultValue == null);
                            if (defaultValue != null) {
                            }
                        }
                        i2++;
                        if (i2 < 0) {
                            CollectionsKt.throwCountOverflow();
                        }
                    }
                } else {
                    it = iterableUntil.iterator();
                    while (it.hasNext()) {
                        iNextInt = ((IntIterator) it).nextInt();
                        if (irConstructorCall2.getValueArgument(iNextInt) == null) {
                            it2 = m6111checkArgumentsAndValueParameters$lambda28(lazy).iterator();
                            do {
                                if (!it2.hasNext()) {
                                    defaultValue = null;
                                    break;
                                }
                                irValueParameter2 = (IrValueParameter) CollectionsKt.getOrNull(((IrFunction) it2.next()).getValueParameters(), iNextInt);
                                if (irValueParameter2 != null) {
                                    defaultValue = irValueParameter2.getDefaultValue();
                                } else {
                                    defaultValue = null;
                                }
                            } while (defaultValue == null);
                            if (defaultValue != null) {
                            }
                        }
                        i2++;
                        if (i2 < 0) {
                            CollectionsKt.throwCountOverflow();
                        }
                    }
                }
                i = i2;
                size = owner.getValueParameters().size();
                if (i != size) {
                    return new PartialLinkageCase.MemberAccessExpressionArgumentsMismatch(irConstructorCall2, zIsSubtypeOfClass, z, i, size);
                }
            }
            return null;
        }

        /* JADX WARN: Multi-variable type inference failed */
        private final <T extends IrMutableAnnotationContainer & IrSymbolOwner> void filterUnusableAnnotations(T t) {
            if (t.getAnnotations().isEmpty()) {
                return;
            }
            List<IrConstructorCall> annotations = t.getAnnotations();
            ArrayList arrayList = new ArrayList(t.getAnnotations().size());
            PartiallyLinkedIrTreePatcher partiallyLinkedIrTreePatcher = PartiallyLinkedIrTreePatcher.this;
            for (Object obj : annotations) {
                IrConstructorCall irConstructorCall = (IrConstructorCall) obj;
                AnnotationChecker annotationChecker = new AnnotationChecker(partiallyLinkedIrTreePatcher, getCurrentFile());
                partiallyLinkedIrTreePatcher.transformVoid(irConstructorCall, annotationChecker);
                if (!annotationChecker.getIsUsableAnnotation()) {
                    partiallyLinkedIrTreePatcher.getSupportForLowerings().renderAndLogLinkageError(new PartialLinkageCase.UnusableAnnotation(irConstructorCall.getSymbol(), t.getSymbol()), (IrElement) t, getCurrentFile());
                } else {
                    arrayList.add(obj);
                }
            }
            t.setAnnotations(org.jetbrains.kotlin.utils.CollectionsKt.compact(arrayList));
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
        public IrExpression visitExpression(IrExpression expression) {
            DirectChildren empty;
            IrCall irCall;
            List<IrStatement> statements;
            Intrinsics.checkNotNullParameter(expression, "expression");
            PartiallyLinkedIrTreePatcher partiallyLinkedIrTreePatcher = PartiallyLinkedIrTreePatcher.this;
            IrExpression irExpression = expression;
            ExpressionTransformer expressionTransformer = this;
            IrElementTransformerVoidKt.transformChildrenVoid(irExpression, expressionTransformer);
            PartialLinkageCase partialLinkageCaseCheckExpressionType = checkExpressionType(expression, expression.getType());
            if (partialLinkageCaseCheckExpressionType != null) {
                if (PartiallyLinkedIrTreePatcher.INSTANCE.hasBranches(expression)) {
                    empty = DirectChildren.INSTANCE.getEMPTY();
                } else {
                    DirectChildrenStatementsCollector directChildrenStatementsCollector = new DirectChildrenStatementsCollector();
                    IrVisitorsKt.acceptChildrenVoid(irExpression, directChildrenStatementsCollector);
                    empty = directChildrenStatementsCollector.getResult();
                }
                DirectChildren directChildren = empty;
                IrCall irCallThrowLinkageError$default = PartialLinkageSupportForLowerings.DefaultImpls.throwLinkageError$default(partiallyLinkedIrTreePatcher.getSupportForLowerings(), partialLinkageCaseCheckExpressionType, irExpression, expressionTransformer.getCurrentFile(), false, 8, null);
                if (!directChildren.getStatements().isEmpty()) {
                    IrCompositeImpl irCompositeImpl = new IrCompositeImpl(expression.getStartOffset(), expression.getEndOffset(), partiallyLinkedIrTreePatcher.builtIns.getNothingType(), IrStatementOrigin.PARTIAL_LINKAGE_RUNTIME_ERROR.INSTANCE);
                    CollectionsKt.addAll(irCompositeImpl.getStatements(), directChildren.getStatements());
                    if (!directChildren.getHasPartialLinkageRuntimeError()) {
                        irCompositeImpl.getStatements().add(irCallThrowLinkageError$default);
                    }
                    irCall = irCompositeImpl;
                } else {
                    irCall = irCallThrowLinkageError$default;
                }
            } else {
                IrContainerExpression irContainerExpression = expression instanceof IrContainerExpression ? (IrContainerExpression) expression : null;
                if (irContainerExpression != null && (statements = irContainerExpression.getStatements()) != null) {
                    PartiallyLinkedIrTreePatcher.INSTANCE.eliminateDeadCodeStatements(statements);
                }
                irCall = expression;
            }
            onAfterMaybeThrowLinkageError(expression);
            return irCall;
        }
    }

    /* JADX INFO: compiled from: PartiallyLinkedIrTreePatcher.kt */
    @Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\b\u0082\u0004\u0018\u00002\u00060\u0001R\u00020\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\u0014\u0010\u000f\u001a\u00020\u00102\n\u0010\u0011\u001a\u0006\u0012\u0002\b\u00030\u0012H\u0016J\u000e\u0010\u0013\u001a\u0004\u0018\u00010\u0014*\u00020\u0015H\u0014J\f\u0010\u0016\u001a\u00020\u0017*\u00020\u0010H\u0014R\u0014\u0010\u0006\u001a\u00020\u00078BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u000e\u0010\n\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\r\u001a\u00020\f2\u0006\u0010\u000b\u001a\u00020\f@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u0018²\u0006\n\u0010\u0019\u001a\u00020\u0004X\u008a\u0084\u0002"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/linkage/partial/PartiallyLinkedIrTreePatcher$AnnotationChecker;", "Lorg/jetbrains/kotlin/backend/common/linkage/partial/PartiallyLinkedIrTreePatcher$ExpressionTransformer;", "Lorg/jetbrains/kotlin/backend/common/linkage/partial/PartiallyLinkedIrTreePatcher;", "currentFile", "Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageUtils$File;", "(Lorg/jetbrains/kotlin/backend/common/linkage/partial/PartiallyLinkedIrTreePatcher;Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageUtils$File;)V", "currentErrorMessagesCount", "", "getCurrentErrorMessagesCount", "()I", "initialErrorMessagesCount", "<set-?>", "", "isUsableAnnotation", "()Z", "visitConst", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrConst;", "customConstructorCallChecks", "Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageCase;", "Lorg/jetbrains/kotlin/ir/expressions/IrConstructorCall;", "onAfterMaybeThrowLinkageError", "", "ir.serialization.common", "annotationFile"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private final class AnnotationChecker extends ExpressionTransformer {
        private final int initialErrorMessagesCount;
        private boolean isUsableAnnotation;
        final /* synthetic */ PartiallyLinkedIrTreePatcher this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnnotationChecker(PartiallyLinkedIrTreePatcher partiallyLinkedIrTreePatcher, org.jetbrains.kotlin.ir.linkage.partial.PartialLinkageUtils.File currentFile) {
            super(currentFile);
            Intrinsics.checkNotNullParameter(currentFile, "currentFile");
            this.this$0 = partiallyLinkedIrTreePatcher;
            this.initialErrorMessagesCount = getCurrentErrorMessagesCount();
            this.isUsableAnnotation = true;
        }

        private final int getCurrentErrorMessagesCount() {
            return this.this$0.getSupportForLowerings().getErrorMessagesRendered();
        }

        /* JADX INFO: renamed from: isUsableAnnotation, reason: from getter */
        public final boolean getIsUsableAnnotation() {
            return this.isUsableAnnotation;
        }

        @Override // org.jetbrains.kotlin.backend.common.linkage.partial.PartiallyLinkedIrTreePatcher.ExpressionTransformer
        protected void onAfterMaybeThrowLinkageError(IrExpression irExpression) {
            Intrinsics.checkNotNullParameter(irExpression, "<this>");
            if (this.isUsableAnnotation) {
                this.isUsableAnnotation = this.initialErrorMessagesCount == getCurrentErrorMessagesCount() && !PartialLinkageUtilsKt.isPartialLinkageRuntimeError(irExpression);
            }
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
        public IrExpression visitConst(IrConst<?> expression) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return expression;
        }

        /* JADX WARN: Code duplicated, block: B:14:0x006c  */
        /* JADX WARN: Code duplicated, block: B:28:0x008f  */
        /* JADX WARN: Code duplicated, block: B:57:0x00e7  */
        /* JADX WARN: Code restructure failed: missing block: B:79:0x0134, code lost:
        
            if (kotlin.jvm.internal.Intrinsics.areEqual(r12 != null ? r12.getSymbol() : null, ((org.jetbrains.kotlin.backend.common.linkage.partial.PartiallyLinkedIrTreePatcher.ExpressionTransformer) r4).this$0.builtIns.getEnumClass()) != false) goto L83;
         */
        /* JADX WARN: Code restructure failed: missing block: B:82:0x0139, code lost:
        
            if ((r6 instanceof org.jetbrains.kotlin.ir.expressions.IrFunctionReference) != false) goto L83;
         */
        @Override // org.jetbrains.kotlin.backend.common.linkage.partial.PartiallyLinkedIrTreePatcher.ExpressionTransformer
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        protected org.jetbrains.kotlin.ir.linkage.partial.PartialLinkageCase customConstructorCallChecks(final org.jetbrains.kotlin.ir.expressions.IrConstructorCall r18) {
            /*
                Method dump skipped, instruction units count: 569
                To view this dump add '--comments-level debug' option
            */
            throw new UnsupportedOperationException("Method not decompiled: org.jetbrains.kotlin.backend.common.linkage.partial.PartiallyLinkedIrTreePatcher.AnnotationChecker.customConstructorCallChecks(org.jetbrains.kotlin.ir.expressions.IrConstructorCall):org.jetbrains.kotlin.ir.linkage.partial.PartialLinkageCase");
        }

        private static final org.jetbrains.kotlin.ir.linkage.partial.PartialLinkageUtils.File customConstructorCallChecks$lambda$2$lambda$0(Lazy<? extends org.jetbrains.kotlin.ir.linkage.partial.PartialLinkageUtils.File> lazy) {
            return lazy.getValue();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final ExploredClassifier.Unusable explore(IrClassifierSymbol irClassifierSymbol) {
        return this.classifierExplorer.exploreSymbol(irClassifierSymbol);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final ExploredClassifier.Unusable explore(IrType irType) {
        return this.classifierExplorer.exploreType(irType);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final PartiallyLinkedMarkerType toPartiallyLinkedMarkerTypeOrNull(IrType irType) {
        ExploredClassifier.Unusable unusableExplore = explore(irType);
        if (unusableExplore != null) {
            return new PartiallyLinkedMarkerType(this.builtIns, unusableExplore);
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final PartiallyLinkedMarkerType toPartiallyLinkedMarkerTypeOrNull(List<? extends IrType> list) {
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            PartiallyLinkedMarkerType partiallyLinkedMarkerTypeOrNull = toPartiallyLinkedMarkerTypeOrNull((IrType) it.next());
            if (partiallyLinkedMarkerTypeOrNull != null) {
                return partiallyLinkedMarkerTypeOrNull;
            }
        }
        return null;
    }

    /* JADX INFO: compiled from: PartiallyLinkedIrTreePatcher.kt */
    @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u000b\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0082\b\u0018\u0000 \u00152\u00020\u0001:\u0001\u0015B\u001b\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u000f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003HÆ\u0003J\t\u0010\r\u001a\u00020\u0006HÆ\u0003J#\u0010\u000e\u001a\u00020\u00002\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u0006HÆ\u0001J\u0013\u0010\u000f\u001a\u00020\u00062\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001J\t\u0010\u0013\u001a\u00020\u0014HÖ\u0001R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/linkage/partial/PartiallyLinkedIrTreePatcher$DirectChildren;", "", "statements", "", "Lorg/jetbrains/kotlin/ir/IrStatement;", "hasPartialLinkageRuntimeError", "", "(Ljava/util/List;Z)V", "getHasPartialLinkageRuntimeError", "()Z", "getStatements", "()Ljava/util/List;", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "other", "hashCode", "", "toString", "", "Companion", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final /* data */ class DirectChildren {

        /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
        public static final Companion INSTANCE = new Companion(null);
        private static final DirectChildren EMPTY = new DirectChildren(CollectionsKt.emptyList(), false);
        private final boolean hasPartialLinkageRuntimeError;
        private final List<IrStatement> statements;

        /* JADX WARN: Multi-variable type inference failed */
        public static /* synthetic */ DirectChildren copy$default(DirectChildren directChildren, List list, boolean z, int i, Object obj) {
            if ((i & 1) != 0) {
                list = directChildren.statements;
            }
            if ((i & 2) != 0) {
                z = directChildren.hasPartialLinkageRuntimeError;
            }
            return directChildren.copy(list, z);
        }

        public final List<IrStatement> component1() {
            return this.statements;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final boolean getHasPartialLinkageRuntimeError() {
            return this.hasPartialLinkageRuntimeError;
        }

        public final DirectChildren copy(List<? extends IrStatement> statements, boolean hasPartialLinkageRuntimeError) {
            Intrinsics.checkNotNullParameter(statements, "statements");
            return new DirectChildren(statements, hasPartialLinkageRuntimeError);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof DirectChildren)) {
                return false;
            }
            DirectChildren directChildren = (DirectChildren) other;
            return Intrinsics.areEqual(this.statements, directChildren.statements) && this.hasPartialLinkageRuntimeError == directChildren.hasPartialLinkageRuntimeError;
        }

        public int hashCode() {
            return (this.statements.hashCode() * 31) + Boolean.hashCode(this.hasPartialLinkageRuntimeError);
        }

        public String toString() {
            return "DirectChildren(statements=" + this.statements + ", hasPartialLinkageRuntimeError=" + this.hasPartialLinkageRuntimeError + ')';
        }

        /* JADX INFO: compiled from: PartiallyLinkedIrTreePatcher.kt */
        @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/linkage/partial/PartiallyLinkedIrTreePatcher$DirectChildren$Companion;", "", "()V", "EMPTY", "Lorg/jetbrains/kotlin/backend/common/linkage/partial/PartiallyLinkedIrTreePatcher$DirectChildren;", "getEMPTY", "()Lorg/jetbrains/kotlin/backend/common/linkage/partial/PartiallyLinkedIrTreePatcher$DirectChildren;", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class Companion {
            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            private Companion() {
            }

            public final DirectChildren getEMPTY() {
                return DirectChildren.EMPTY;
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        public DirectChildren(List<? extends IrStatement> statements, boolean z) {
            Intrinsics.checkNotNullParameter(statements, "statements");
            this.statements = statements;
            this.hasPartialLinkageRuntimeError = z;
        }

        public final boolean getHasPartialLinkageRuntimeError() {
            return this.hasPartialLinkageRuntimeError;
        }

        public final List<IrStatement> getStatements() {
            return this.statements;
        }
    }

    /* JADX INFO: compiled from: PartiallyLinkedIrTreePatcher.kt */
    @Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0006\u0010\b\u001a\u00020\tJ\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0016R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/linkage/partial/PartiallyLinkedIrTreePatcher$DirectChildrenStatementsCollector;", "Lorg/jetbrains/kotlin/ir/visitors/IrElementVisitorVoid;", "()V", "children", "", "Lorg/jetbrains/kotlin/ir/IrStatement;", "hasPartialLinkageRuntimeError", "", "getResult", "Lorg/jetbrains/kotlin/backend/common/linkage/partial/PartiallyLinkedIrTreePatcher$DirectChildren;", "visitElement", "", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/ir/IrElement;", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class DirectChildrenStatementsCollector implements IrElementVisitorVoid {
        private final List<IrStatement> children = new ArrayList();
        private boolean hasPartialLinkageRuntimeError;

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitAnonymousInitializer */
        public /* bridge */ /* synthetic */ Object visitAnonymousInitializer2(IrAnonymousInitializer irAnonymousInitializer, Object obj) {
            visitAnonymousInitializer(irAnonymousInitializer, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitAnonymousInitializer(IrAnonymousInitializer irAnonymousInitializer) {
            IrElementVisitorVoid.DefaultImpls.visitAnonymousInitializer(this, irAnonymousInitializer);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitAnonymousInitializer(IrAnonymousInitializer irAnonymousInitializer, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitAnonymousInitializer(this, irAnonymousInitializer, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitBlock */
        public /* bridge */ /* synthetic */ Object visitBlock2(IrBlock irBlock, Object obj) {
            visitBlock(irBlock, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitBlock(IrBlock irBlock) {
            IrElementVisitorVoid.DefaultImpls.visitBlock(this, irBlock);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitBlock(IrBlock irBlock, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitBlock(this, irBlock, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitBlockBody */
        public /* bridge */ /* synthetic */ Object visitBlockBody2(IrBlockBody irBlockBody, Object obj) {
            visitBlockBody(irBlockBody, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitBlockBody(IrBlockBody irBlockBody) {
            IrElementVisitorVoid.DefaultImpls.visitBlockBody(this, irBlockBody);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitBlockBody(IrBlockBody irBlockBody, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitBlockBody(this, irBlockBody, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitBody */
        public /* bridge */ /* synthetic */ Object visitBody2(IrBody irBody, Object obj) {
            visitBody(irBody, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitBody(IrBody irBody) {
            IrElementVisitorVoid.DefaultImpls.visitBody(this, irBody);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitBody(IrBody irBody, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitBody(this, irBody, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitBranch */
        public /* bridge */ /* synthetic */ Object visitBranch2(IrBranch irBranch, Object obj) {
            visitBranch(irBranch, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitBranch(IrBranch irBranch) {
            IrElementVisitorVoid.DefaultImpls.visitBranch(this, irBranch);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitBranch(IrBranch irBranch, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitBranch(this, irBranch, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitBreak */
        public /* bridge */ /* synthetic */ Object visitBreak2(IrBreak irBreak, Object obj) {
            visitBreak(irBreak, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitBreak(IrBreak irBreak) {
            IrElementVisitorVoid.DefaultImpls.visitBreak(this, irBreak);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitBreak(IrBreak irBreak, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitBreak(this, irBreak, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitBreakContinue */
        public /* bridge */ /* synthetic */ Object visitBreakContinue2(IrBreakContinue irBreakContinue, Object obj) {
            visitBreakContinue(irBreakContinue, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitBreakContinue(IrBreakContinue irBreakContinue) {
            IrElementVisitorVoid.DefaultImpls.visitBreakContinue(this, irBreakContinue);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitBreakContinue(IrBreakContinue irBreakContinue, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitBreakContinue(this, irBreakContinue, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitCall */
        public /* bridge */ /* synthetic */ Object visitCall2(IrCall irCall, Object obj) {
            visitCall(irCall, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitCall(IrCall irCall) {
            IrElementVisitorVoid.DefaultImpls.visitCall(this, irCall);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitCall(IrCall irCall, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitCall(this, irCall, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public /* bridge */ /* synthetic */ Object visitCallableReference(IrCallableReference irCallableReference, Object obj) {
            visitCallableReference((IrCallableReference<?>) irCallableReference, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitCallableReference(IrCallableReference<?> irCallableReference) {
            IrElementVisitorVoid.DefaultImpls.visitCallableReference(this, irCallableReference);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitCallableReference(IrCallableReference<?> irCallableReference, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitCallableReference(this, irCallableReference, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitCatch */
        public /* bridge */ /* synthetic */ Object visitCatch2(IrCatch irCatch, Object obj) {
            visitCatch(irCatch, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitCatch(IrCatch irCatch) {
            IrElementVisitorVoid.DefaultImpls.visitCatch(this, irCatch);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitCatch(IrCatch irCatch, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitCatch(this, irCatch, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitClass */
        public /* bridge */ /* synthetic */ Object visitClass2(IrClass irClass, Object obj) {
            visitClass(irClass, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        /* JADX INFO: renamed from: visitClass */
        public void mo6112visitClass(IrClass irClass) {
            IrElementVisitorVoid.DefaultImpls.visitClass(this, irClass);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitClass(IrClass irClass, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitClass(this, irClass, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitClassReference */
        public /* bridge */ /* synthetic */ Object visitClassReference2(IrClassReference irClassReference, Object obj) {
            visitClassReference(irClassReference, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitClassReference(IrClassReference irClassReference) {
            IrElementVisitorVoid.DefaultImpls.visitClassReference(this, irClassReference);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitClassReference(IrClassReference irClassReference, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitClassReference(this, irClassReference, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitComposite */
        public /* bridge */ /* synthetic */ Object visitComposite2(IrComposite irComposite, Object obj) {
            visitComposite(irComposite, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitComposite(IrComposite irComposite) {
            IrElementVisitorVoid.DefaultImpls.visitComposite(this, irComposite);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitComposite(IrComposite irComposite, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitComposite(this, irComposite, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public /* bridge */ /* synthetic */ Object visitConst(IrConst irConst, Object obj) {
            visitConst((IrConst<?>) irConst, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitConst(IrConst<?> irConst) {
            IrElementVisitorVoid.DefaultImpls.visitConst(this, irConst);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitConst(IrConst<?> irConst, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitConst(this, irConst, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitConstantArray */
        public /* bridge */ /* synthetic */ Object visitConstantArray2(IrConstantArray irConstantArray, Object obj) {
            visitConstantArray(irConstantArray, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitConstantArray(IrConstantArray irConstantArray) {
            IrElementVisitorVoid.DefaultImpls.visitConstantArray(this, irConstantArray);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitConstantArray(IrConstantArray irConstantArray, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitConstantArray(this, irConstantArray, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitConstantObject */
        public /* bridge */ /* synthetic */ Object visitConstantObject2(IrConstantObject irConstantObject, Object obj) {
            visitConstantObject(irConstantObject, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitConstantObject(IrConstantObject irConstantObject) {
            IrElementVisitorVoid.DefaultImpls.visitConstantObject(this, irConstantObject);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitConstantObject(IrConstantObject irConstantObject, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitConstantObject(this, irConstantObject, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitConstantPrimitive */
        public /* bridge */ /* synthetic */ Object visitConstantPrimitive2(IrConstantPrimitive irConstantPrimitive, Object obj) {
            visitConstantPrimitive(irConstantPrimitive, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitConstantPrimitive(IrConstantPrimitive irConstantPrimitive) {
            IrElementVisitorVoid.DefaultImpls.visitConstantPrimitive(this, irConstantPrimitive);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitConstantPrimitive(IrConstantPrimitive irConstantPrimitive, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitConstantPrimitive(this, irConstantPrimitive, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitConstantValue */
        public /* bridge */ /* synthetic */ Object visitConstantValue2(IrConstantValue irConstantValue, Object obj) {
            visitConstantValue(irConstantValue, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitConstantValue(IrConstantValue irConstantValue) {
            IrElementVisitorVoid.DefaultImpls.visitConstantValue(this, irConstantValue);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitConstantValue(IrConstantValue irConstantValue, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitConstantValue(this, irConstantValue, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitConstructor */
        public /* bridge */ /* synthetic */ Object visitConstructor2(IrConstructor irConstructor, Object obj) {
            visitConstructor(irConstructor, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitConstructor(IrConstructor irConstructor) {
            IrElementVisitorVoid.DefaultImpls.visitConstructor(this, irConstructor);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitConstructor(IrConstructor irConstructor, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitConstructor(this, irConstructor, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitConstructorCall */
        public /* bridge */ /* synthetic */ Object visitConstructorCall2(IrConstructorCall irConstructorCall, Object obj) {
            visitConstructorCall(irConstructorCall, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitConstructorCall(IrConstructorCall irConstructorCall) {
            IrElementVisitorVoid.DefaultImpls.visitConstructorCall(this, irConstructorCall);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitConstructorCall(IrConstructorCall irConstructorCall, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitConstructorCall(this, irConstructorCall, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitContainerExpression */
        public /* bridge */ /* synthetic */ Object visitContainerExpression2(IrContainerExpression irContainerExpression, Object obj) {
            visitContainerExpression(irContainerExpression, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitContainerExpression(IrContainerExpression irContainerExpression) {
            IrElementVisitorVoid.DefaultImpls.visitContainerExpression(this, irContainerExpression);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitContainerExpression(IrContainerExpression irContainerExpression, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitContainerExpression(this, irContainerExpression, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitContinue */
        public /* bridge */ /* synthetic */ Object visitContinue2(IrContinue irContinue, Object obj) {
            visitContinue(irContinue, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitContinue(IrContinue irContinue) {
            IrElementVisitorVoid.DefaultImpls.visitContinue(this, irContinue);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitContinue(IrContinue irContinue, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitContinue(this, irContinue, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitDeclaration */
        public /* bridge */ /* synthetic */ Object visitDeclaration2(IrDeclarationBase irDeclarationBase, Object obj) {
            visitDeclaration(irDeclarationBase, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitDeclaration(IrDeclarationBase irDeclarationBase) {
            IrElementVisitorVoid.DefaultImpls.visitDeclaration(this, irDeclarationBase);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitDeclaration(IrDeclarationBase irDeclarationBase, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitDeclaration(this, irDeclarationBase, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitDeclarationReference */
        public /* bridge */ /* synthetic */ Object visitDeclarationReference2(IrDeclarationReference irDeclarationReference, Object obj) {
            visitDeclarationReference(irDeclarationReference, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitDeclarationReference(IrDeclarationReference irDeclarationReference) {
            IrElementVisitorVoid.DefaultImpls.visitDeclarationReference(this, irDeclarationReference);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitDeclarationReference(IrDeclarationReference irDeclarationReference, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitDeclarationReference(this, irDeclarationReference, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitDelegatingConstructorCall */
        public /* bridge */ /* synthetic */ Object visitDelegatingConstructorCall2(IrDelegatingConstructorCall irDelegatingConstructorCall, Object obj) {
            visitDelegatingConstructorCall(irDelegatingConstructorCall, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitDelegatingConstructorCall(IrDelegatingConstructorCall irDelegatingConstructorCall) {
            IrElementVisitorVoid.DefaultImpls.visitDelegatingConstructorCall(this, irDelegatingConstructorCall);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitDelegatingConstructorCall(IrDelegatingConstructorCall irDelegatingConstructorCall, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitDelegatingConstructorCall(this, irDelegatingConstructorCall, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitDoWhileLoop */
        public /* bridge */ /* synthetic */ Object visitDoWhileLoop2(IrDoWhileLoop irDoWhileLoop, Object obj) {
            visitDoWhileLoop(irDoWhileLoop, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitDoWhileLoop(IrDoWhileLoop irDoWhileLoop) {
            IrElementVisitorVoid.DefaultImpls.visitDoWhileLoop(this, irDoWhileLoop);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitDoWhileLoop(IrDoWhileLoop irDoWhileLoop, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitDoWhileLoop(this, irDoWhileLoop, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitDynamicExpression */
        public /* bridge */ /* synthetic */ Object visitDynamicExpression2(IrDynamicExpression irDynamicExpression, Object obj) {
            visitDynamicExpression(irDynamicExpression, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitDynamicExpression(IrDynamicExpression irDynamicExpression) {
            IrElementVisitorVoid.DefaultImpls.visitDynamicExpression(this, irDynamicExpression);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitDynamicExpression(IrDynamicExpression irDynamicExpression, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitDynamicExpression(this, irDynamicExpression, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitDynamicMemberExpression */
        public /* bridge */ /* synthetic */ Object visitDynamicMemberExpression2(IrDynamicMemberExpression irDynamicMemberExpression, Object obj) {
            visitDynamicMemberExpression(irDynamicMemberExpression, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitDynamicMemberExpression(IrDynamicMemberExpression irDynamicMemberExpression) {
            IrElementVisitorVoid.DefaultImpls.visitDynamicMemberExpression(this, irDynamicMemberExpression);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitDynamicMemberExpression(IrDynamicMemberExpression irDynamicMemberExpression, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitDynamicMemberExpression(this, irDynamicMemberExpression, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitDynamicOperatorExpression */
        public /* bridge */ /* synthetic */ Object visitDynamicOperatorExpression2(IrDynamicOperatorExpression irDynamicOperatorExpression, Object obj) {
            visitDynamicOperatorExpression(irDynamicOperatorExpression, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitDynamicOperatorExpression(IrDynamicOperatorExpression irDynamicOperatorExpression) {
            IrElementVisitorVoid.DefaultImpls.visitDynamicOperatorExpression(this, irDynamicOperatorExpression);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitDynamicOperatorExpression(IrDynamicOperatorExpression irDynamicOperatorExpression, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitDynamicOperatorExpression(this, irDynamicOperatorExpression, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitElement */
        public /* bridge */ /* synthetic */ Object visitElement2(IrElement irElement, Object obj) {
            visitElement(irElement, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitElement(IrElement irElement, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitElement(this, irElement, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitElseBranch */
        public /* bridge */ /* synthetic */ Object visitElseBranch2(IrElseBranch irElseBranch, Object obj) {
            visitElseBranch(irElseBranch, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitElseBranch(IrElseBranch irElseBranch) {
            IrElementVisitorVoid.DefaultImpls.visitElseBranch(this, irElseBranch);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitElseBranch(IrElseBranch irElseBranch, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitElseBranch(this, irElseBranch, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitEnumConstructorCall */
        public /* bridge */ /* synthetic */ Object visitEnumConstructorCall2(IrEnumConstructorCall irEnumConstructorCall, Object obj) {
            visitEnumConstructorCall(irEnumConstructorCall, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitEnumConstructorCall(IrEnumConstructorCall irEnumConstructorCall) {
            IrElementVisitorVoid.DefaultImpls.visitEnumConstructorCall(this, irEnumConstructorCall);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitEnumConstructorCall(IrEnumConstructorCall irEnumConstructorCall, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitEnumConstructorCall(this, irEnumConstructorCall, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitEnumEntry */
        public /* bridge */ /* synthetic */ Object visitEnumEntry2(IrEnumEntry irEnumEntry, Object obj) {
            visitEnumEntry(irEnumEntry, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitEnumEntry(IrEnumEntry irEnumEntry) {
            IrElementVisitorVoid.DefaultImpls.visitEnumEntry(this, irEnumEntry);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitEnumEntry(IrEnumEntry irEnumEntry, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitEnumEntry(this, irEnumEntry, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitErrorCallExpression */
        public /* bridge */ /* synthetic */ Object visitErrorCallExpression2(IrErrorCallExpression irErrorCallExpression, Object obj) {
            visitErrorCallExpression(irErrorCallExpression, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitErrorCallExpression(IrErrorCallExpression irErrorCallExpression) {
            IrElementVisitorVoid.DefaultImpls.visitErrorCallExpression(this, irErrorCallExpression);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitErrorCallExpression(IrErrorCallExpression irErrorCallExpression, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitErrorCallExpression(this, irErrorCallExpression, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitErrorDeclaration */
        public /* bridge */ /* synthetic */ Object visitErrorDeclaration2(IrErrorDeclaration irErrorDeclaration, Object obj) {
            visitErrorDeclaration(irErrorDeclaration, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitErrorDeclaration(IrErrorDeclaration irErrorDeclaration) {
            IrElementVisitorVoid.DefaultImpls.visitErrorDeclaration(this, irErrorDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitErrorDeclaration(IrErrorDeclaration irErrorDeclaration, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitErrorDeclaration(this, irErrorDeclaration, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitErrorExpression */
        public /* bridge */ /* synthetic */ Object visitErrorExpression2(IrErrorExpression irErrorExpression, Object obj) {
            visitErrorExpression(irErrorExpression, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitErrorExpression(IrErrorExpression irErrorExpression) {
            IrElementVisitorVoid.DefaultImpls.visitErrorExpression(this, irErrorExpression);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitErrorExpression(IrErrorExpression irErrorExpression, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitErrorExpression(this, irErrorExpression, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitExpression */
        public /* bridge */ /* synthetic */ Object visitExpression2(IrExpression irExpression, Object obj) {
            visitExpression(irExpression, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitExpression(IrExpression irExpression) {
            IrElementVisitorVoid.DefaultImpls.visitExpression(this, irExpression);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitExpression(IrExpression irExpression, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitExpression(this, irExpression, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitExpressionBody */
        public /* bridge */ /* synthetic */ Object visitExpressionBody2(IrExpressionBody irExpressionBody, Object obj) {
            visitExpressionBody(irExpressionBody, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitExpressionBody(IrExpressionBody irExpressionBody) {
            IrElementVisitorVoid.DefaultImpls.visitExpressionBody(this, irExpressionBody);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitExpressionBody(IrExpressionBody irExpressionBody, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitExpressionBody(this, irExpressionBody, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitExternalPackageFragment */
        public /* bridge */ /* synthetic */ Object visitExternalPackageFragment2(IrExternalPackageFragment irExternalPackageFragment, Object obj) {
            visitExternalPackageFragment(irExternalPackageFragment, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitExternalPackageFragment(IrExternalPackageFragment irExternalPackageFragment) {
            IrElementVisitorVoid.DefaultImpls.visitExternalPackageFragment(this, irExternalPackageFragment);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitExternalPackageFragment(IrExternalPackageFragment irExternalPackageFragment, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitExternalPackageFragment(this, irExternalPackageFragment, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitField */
        public /* bridge */ /* synthetic */ Object visitField2(IrField irField, Object obj) {
            visitField(irField, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitField(IrField irField) {
            IrElementVisitorVoid.DefaultImpls.visitField(this, irField);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitField(IrField irField, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitField(this, irField, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitFieldAccess */
        public /* bridge */ /* synthetic */ Object visitFieldAccess2(IrFieldAccessExpression irFieldAccessExpression, Object obj) {
            visitFieldAccess(irFieldAccessExpression, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitFieldAccess(IrFieldAccessExpression irFieldAccessExpression) {
            IrElementVisitorVoid.DefaultImpls.visitFieldAccess(this, irFieldAccessExpression);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitFieldAccess(IrFieldAccessExpression irFieldAccessExpression, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitFieldAccess(this, irFieldAccessExpression, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitFile */
        public /* bridge */ /* synthetic */ Object visitFile2(IrFile irFile, Object obj) {
            visitFile(irFile, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitFile(IrFile irFile) {
            IrElementVisitorVoid.DefaultImpls.visitFile(this, irFile);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitFile(IrFile irFile, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitFile(this, irFile, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitFunction */
        public /* bridge */ /* synthetic */ Object visitFunction2(IrFunction irFunction, Object obj) {
            visitFunction(irFunction, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitFunction(IrFunction irFunction) {
            IrElementVisitorVoid.DefaultImpls.visitFunction(this, irFunction);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitFunction(IrFunction irFunction, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitFunction(this, irFunction, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitFunctionAccess */
        public /* bridge */ /* synthetic */ Object visitFunctionAccess2(IrFunctionAccessExpression irFunctionAccessExpression, Object obj) {
            visitFunctionAccess(irFunctionAccessExpression, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitFunctionAccess(IrFunctionAccessExpression irFunctionAccessExpression) {
            IrElementVisitorVoid.DefaultImpls.visitFunctionAccess(this, irFunctionAccessExpression);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitFunctionAccess(IrFunctionAccessExpression irFunctionAccessExpression, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitFunctionAccess(this, irFunctionAccessExpression, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitFunctionExpression */
        public /* bridge */ /* synthetic */ Object visitFunctionExpression2(IrFunctionExpression irFunctionExpression, Object obj) {
            visitFunctionExpression(irFunctionExpression, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitFunctionExpression(IrFunctionExpression irFunctionExpression) {
            IrElementVisitorVoid.DefaultImpls.visitFunctionExpression(this, irFunctionExpression);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitFunctionExpression(IrFunctionExpression irFunctionExpression, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitFunctionExpression(this, irFunctionExpression, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitFunctionReference */
        public /* bridge */ /* synthetic */ Object visitFunctionReference2(IrFunctionReference irFunctionReference, Object obj) {
            visitFunctionReference(irFunctionReference, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitFunctionReference(IrFunctionReference irFunctionReference) {
            IrElementVisitorVoid.DefaultImpls.visitFunctionReference(this, irFunctionReference);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitFunctionReference(IrFunctionReference irFunctionReference, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitFunctionReference(this, irFunctionReference, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitGetClass */
        public /* bridge */ /* synthetic */ Object visitGetClass2(IrGetClass irGetClass, Object obj) {
            visitGetClass(irGetClass, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitGetClass(IrGetClass irGetClass) {
            IrElementVisitorVoid.DefaultImpls.visitGetClass(this, irGetClass);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitGetClass(IrGetClass irGetClass, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitGetClass(this, irGetClass, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitGetEnumValue */
        public /* bridge */ /* synthetic */ Object visitGetEnumValue2(IrGetEnumValue irGetEnumValue, Object obj) {
            visitGetEnumValue(irGetEnumValue, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitGetEnumValue(IrGetEnumValue irGetEnumValue) {
            IrElementVisitorVoid.DefaultImpls.visitGetEnumValue(this, irGetEnumValue);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitGetEnumValue(IrGetEnumValue irGetEnumValue, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitGetEnumValue(this, irGetEnumValue, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitGetField */
        public /* bridge */ /* synthetic */ Object visitGetField2(IrGetField irGetField, Object obj) {
            visitGetField(irGetField, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitGetField(IrGetField irGetField) {
            IrElementVisitorVoid.DefaultImpls.visitGetField(this, irGetField);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitGetField(IrGetField irGetField, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitGetField(this, irGetField, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitGetObjectValue */
        public /* bridge */ /* synthetic */ Object visitGetObjectValue2(IrGetObjectValue irGetObjectValue, Object obj) {
            visitGetObjectValue(irGetObjectValue, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitGetObjectValue(IrGetObjectValue irGetObjectValue) {
            IrElementVisitorVoid.DefaultImpls.visitGetObjectValue(this, irGetObjectValue);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitGetObjectValue(IrGetObjectValue irGetObjectValue, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitGetObjectValue(this, irGetObjectValue, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitGetValue */
        public /* bridge */ /* synthetic */ Object visitGetValue2(IrGetValue irGetValue, Object obj) {
            visitGetValue(irGetValue, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitGetValue(IrGetValue irGetValue) {
            IrElementVisitorVoid.DefaultImpls.visitGetValue(this, irGetValue);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitGetValue(IrGetValue irGetValue, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitGetValue(this, irGetValue, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitInstanceInitializerCall */
        public /* bridge */ /* synthetic */ Object visitInstanceInitializerCall2(IrInstanceInitializerCall irInstanceInitializerCall, Object obj) {
            visitInstanceInitializerCall(irInstanceInitializerCall, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitInstanceInitializerCall(IrInstanceInitializerCall irInstanceInitializerCall) {
            IrElementVisitorVoid.DefaultImpls.visitInstanceInitializerCall(this, irInstanceInitializerCall);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitInstanceInitializerCall(IrInstanceInitializerCall irInstanceInitializerCall, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitInstanceInitializerCall(this, irInstanceInitializerCall, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitLocalDelegatedProperty */
        public /* bridge */ /* synthetic */ Object visitLocalDelegatedProperty2(IrLocalDelegatedProperty irLocalDelegatedProperty, Object obj) {
            visitLocalDelegatedProperty(irLocalDelegatedProperty, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitLocalDelegatedProperty(IrLocalDelegatedProperty irLocalDelegatedProperty) {
            IrElementVisitorVoid.DefaultImpls.visitLocalDelegatedProperty(this, irLocalDelegatedProperty);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitLocalDelegatedProperty(IrLocalDelegatedProperty irLocalDelegatedProperty, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitLocalDelegatedProperty(this, irLocalDelegatedProperty, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitLocalDelegatedPropertyReference */
        public /* bridge */ /* synthetic */ Object visitLocalDelegatedPropertyReference2(IrLocalDelegatedPropertyReference irLocalDelegatedPropertyReference, Object obj) {
            visitLocalDelegatedPropertyReference(irLocalDelegatedPropertyReference, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitLocalDelegatedPropertyReference(IrLocalDelegatedPropertyReference irLocalDelegatedPropertyReference) {
            IrElementVisitorVoid.DefaultImpls.visitLocalDelegatedPropertyReference(this, irLocalDelegatedPropertyReference);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitLocalDelegatedPropertyReference(IrLocalDelegatedPropertyReference irLocalDelegatedPropertyReference, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitLocalDelegatedPropertyReference(this, irLocalDelegatedPropertyReference, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitLoop */
        public /* bridge */ /* synthetic */ Object visitLoop2(IrLoop irLoop, Object obj) {
            visitLoop(irLoop, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitLoop(IrLoop irLoop) {
            IrElementVisitorVoid.DefaultImpls.visitLoop(this, irLoop);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitLoop(IrLoop irLoop, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitLoop(this, irLoop, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public /* bridge */ /* synthetic */ Object visitMemberAccess(IrMemberAccessExpression irMemberAccessExpression, Object obj) {
            visitMemberAccess((IrMemberAccessExpression<?>) irMemberAccessExpression, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitMemberAccess(IrMemberAccessExpression<?> irMemberAccessExpression) {
            IrElementVisitorVoid.DefaultImpls.visitMemberAccess(this, irMemberAccessExpression);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitMemberAccess(IrMemberAccessExpression<?> irMemberAccessExpression, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitMemberAccess(this, irMemberAccessExpression, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitModuleFragment */
        public /* bridge */ /* synthetic */ Object visitModuleFragment2(IrModuleFragment irModuleFragment, Object obj) {
            visitModuleFragment(irModuleFragment, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitModuleFragment(IrModuleFragment irModuleFragment) {
            IrElementVisitorVoid.DefaultImpls.visitModuleFragment(this, irModuleFragment);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitModuleFragment(IrModuleFragment irModuleFragment, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitModuleFragment(this, irModuleFragment, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitPackageFragment */
        public /* bridge */ /* synthetic */ Object visitPackageFragment2(IrPackageFragment irPackageFragment, Object obj) {
            visitPackageFragment(irPackageFragment, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitPackageFragment(IrPackageFragment irPackageFragment) {
            IrElementVisitorVoid.DefaultImpls.visitPackageFragment(this, irPackageFragment);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitPackageFragment(IrPackageFragment irPackageFragment, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitPackageFragment(this, irPackageFragment, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitProperty */
        public /* bridge */ /* synthetic */ Object visitProperty2(IrProperty irProperty, Object obj) {
            visitProperty(irProperty, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitProperty(IrProperty irProperty) {
            IrElementVisitorVoid.DefaultImpls.visitProperty(this, irProperty);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitProperty(IrProperty irProperty, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitProperty(this, irProperty, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitPropertyReference */
        public /* bridge */ /* synthetic */ Object visitPropertyReference2(IrPropertyReference irPropertyReference, Object obj) {
            visitPropertyReference(irPropertyReference, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitPropertyReference(IrPropertyReference irPropertyReference) {
            IrElementVisitorVoid.DefaultImpls.visitPropertyReference(this, irPropertyReference);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitPropertyReference(IrPropertyReference irPropertyReference, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitPropertyReference(this, irPropertyReference, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitRawFunctionReference */
        public /* bridge */ /* synthetic */ Object visitRawFunctionReference2(IrRawFunctionReference irRawFunctionReference, Object obj) {
            visitRawFunctionReference(irRawFunctionReference, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitRawFunctionReference(IrRawFunctionReference irRawFunctionReference) {
            IrElementVisitorVoid.DefaultImpls.visitRawFunctionReference(this, irRawFunctionReference);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitRawFunctionReference(IrRawFunctionReference irRawFunctionReference, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitRawFunctionReference(this, irRawFunctionReference, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitReturn */
        public /* bridge */ /* synthetic */ Object visitReturn2(IrReturn irReturn, Object obj) {
            visitReturn(irReturn, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitReturn(IrReturn irReturn) {
            IrElementVisitorVoid.DefaultImpls.visitReturn(this, irReturn);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitReturn(IrReturn irReturn, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitReturn(this, irReturn, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitScript */
        public /* bridge */ /* synthetic */ Object visitScript2(IrScript irScript, Object obj) {
            visitScript(irScript, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitScript(IrScript irScript) {
            IrElementVisitorVoid.DefaultImpls.visitScript(this, irScript);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitScript(IrScript irScript, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitScript(this, irScript, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitSetField */
        public /* bridge */ /* synthetic */ Object visitSetField2(IrSetField irSetField, Object obj) {
            visitSetField(irSetField, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitSetField(IrSetField irSetField) {
            IrElementVisitorVoid.DefaultImpls.visitSetField(this, irSetField);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitSetField(IrSetField irSetField, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitSetField(this, irSetField, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitSetValue */
        public /* bridge */ /* synthetic */ Object visitSetValue2(IrSetValue irSetValue, Object obj) {
            visitSetValue(irSetValue, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitSetValue(IrSetValue irSetValue) {
            IrElementVisitorVoid.DefaultImpls.visitSetValue(this, irSetValue);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitSetValue(IrSetValue irSetValue, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitSetValue(this, irSetValue, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitSimpleFunction */
        public /* bridge */ /* synthetic */ Object visitSimpleFunction2(IrSimpleFunction irSimpleFunction, Object obj) {
            visitSimpleFunction(irSimpleFunction, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitSimpleFunction(IrSimpleFunction irSimpleFunction) {
            IrElementVisitorVoid.DefaultImpls.visitSimpleFunction(this, irSimpleFunction);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitSimpleFunction(IrSimpleFunction irSimpleFunction, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitSimpleFunction(this, irSimpleFunction, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitSingletonReference */
        public /* bridge */ /* synthetic */ Object visitSingletonReference2(IrGetSingletonValue irGetSingletonValue, Object obj) {
            visitSingletonReference(irGetSingletonValue, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitSingletonReference(IrGetSingletonValue irGetSingletonValue) {
            IrElementVisitorVoid.DefaultImpls.visitSingletonReference(this, irGetSingletonValue);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitSingletonReference(IrGetSingletonValue irGetSingletonValue, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitSingletonReference(this, irGetSingletonValue, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitSpreadElement */
        public /* bridge */ /* synthetic */ Object visitSpreadElement2(IrSpreadElement irSpreadElement, Object obj) {
            visitSpreadElement(irSpreadElement, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitSpreadElement(IrSpreadElement irSpreadElement) {
            IrElementVisitorVoid.DefaultImpls.visitSpreadElement(this, irSpreadElement);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitSpreadElement(IrSpreadElement irSpreadElement, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitSpreadElement(this, irSpreadElement, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitStringConcatenation */
        public /* bridge */ /* synthetic */ Object visitStringConcatenation2(IrStringConcatenation irStringConcatenation, Object obj) {
            visitStringConcatenation(irStringConcatenation, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitStringConcatenation(IrStringConcatenation irStringConcatenation) {
            IrElementVisitorVoid.DefaultImpls.visitStringConcatenation(this, irStringConcatenation);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitStringConcatenation(IrStringConcatenation irStringConcatenation, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitStringConcatenation(this, irStringConcatenation, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitSuspendableExpression */
        public /* bridge */ /* synthetic */ Object visitSuspendableExpression2(IrSuspendableExpression irSuspendableExpression, Object obj) {
            visitSuspendableExpression(irSuspendableExpression, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitSuspendableExpression(IrSuspendableExpression irSuspendableExpression) {
            IrElementVisitorVoid.DefaultImpls.visitSuspendableExpression(this, irSuspendableExpression);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitSuspendableExpression(IrSuspendableExpression irSuspendableExpression, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitSuspendableExpression(this, irSuspendableExpression, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitSuspensionPoint */
        public /* bridge */ /* synthetic */ Object visitSuspensionPoint2(IrSuspensionPoint irSuspensionPoint, Object obj) {
            visitSuspensionPoint(irSuspensionPoint, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitSuspensionPoint(IrSuspensionPoint irSuspensionPoint) {
            IrElementVisitorVoid.DefaultImpls.visitSuspensionPoint(this, irSuspensionPoint);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitSuspensionPoint(IrSuspensionPoint irSuspensionPoint, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitSuspensionPoint(this, irSuspensionPoint, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitSyntheticBody */
        public /* bridge */ /* synthetic */ Object visitSyntheticBody2(IrSyntheticBody irSyntheticBody, Object obj) {
            visitSyntheticBody(irSyntheticBody, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitSyntheticBody(IrSyntheticBody irSyntheticBody) {
            IrElementVisitorVoid.DefaultImpls.visitSyntheticBody(this, irSyntheticBody);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitSyntheticBody(IrSyntheticBody irSyntheticBody, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitSyntheticBody(this, irSyntheticBody, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitThrow */
        public /* bridge */ /* synthetic */ Object visitThrow2(IrThrow irThrow, Object obj) {
            visitThrow(irThrow, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitThrow(IrThrow irThrow) {
            IrElementVisitorVoid.DefaultImpls.visitThrow(this, irThrow);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitThrow(IrThrow irThrow, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitThrow(this, irThrow, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitTry */
        public /* bridge */ /* synthetic */ Object visitTry2(IrTry irTry, Object obj) {
            visitTry(irTry, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitTry(IrTry irTry) {
            IrElementVisitorVoid.DefaultImpls.visitTry(this, irTry);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitTry(IrTry irTry, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitTry(this, irTry, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitTypeAlias */
        public /* bridge */ /* synthetic */ Object visitTypeAlias2(IrTypeAlias irTypeAlias, Object obj) {
            visitTypeAlias(irTypeAlias, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitTypeAlias(IrTypeAlias irTypeAlias) {
            IrElementVisitorVoid.DefaultImpls.visitTypeAlias(this, irTypeAlias);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitTypeAlias(IrTypeAlias irTypeAlias, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitTypeAlias(this, irTypeAlias, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitTypeOperator */
        public /* bridge */ /* synthetic */ Object visitTypeOperator2(IrTypeOperatorCall irTypeOperatorCall, Object obj) {
            visitTypeOperator(irTypeOperatorCall, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitTypeOperator(IrTypeOperatorCall irTypeOperatorCall) {
            IrElementVisitorVoid.DefaultImpls.visitTypeOperator(this, irTypeOperatorCall);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitTypeOperator(IrTypeOperatorCall irTypeOperatorCall, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitTypeOperator(this, irTypeOperatorCall, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitTypeParameter */
        public /* bridge */ /* synthetic */ Object visitTypeParameter2(IrTypeParameter irTypeParameter, Object obj) {
            visitTypeParameter(irTypeParameter, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitTypeParameter(IrTypeParameter irTypeParameter) {
            IrElementVisitorVoid.DefaultImpls.visitTypeParameter(this, irTypeParameter);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitTypeParameter(IrTypeParameter irTypeParameter, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitTypeParameter(this, irTypeParameter, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitValueAccess */
        public /* bridge */ /* synthetic */ Object visitValueAccess2(IrValueAccessExpression irValueAccessExpression, Object obj) {
            visitValueAccess(irValueAccessExpression, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitValueAccess(IrValueAccessExpression irValueAccessExpression) {
            IrElementVisitorVoid.DefaultImpls.visitValueAccess(this, irValueAccessExpression);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitValueAccess(IrValueAccessExpression irValueAccessExpression, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitValueAccess(this, irValueAccessExpression, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitValueParameter */
        public /* bridge */ /* synthetic */ Object visitValueParameter2(IrValueParameter irValueParameter, Object obj) {
            visitValueParameter(irValueParameter, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitValueParameter(IrValueParameter irValueParameter) {
            IrElementVisitorVoid.DefaultImpls.visitValueParameter(this, irValueParameter);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitValueParameter(IrValueParameter irValueParameter, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitValueParameter(this, irValueParameter, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitVararg */
        public /* bridge */ /* synthetic */ Object visitVararg2(IrVararg irVararg, Object obj) {
            visitVararg(irVararg, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitVararg(IrVararg irVararg) {
            IrElementVisitorVoid.DefaultImpls.visitVararg(this, irVararg);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitVararg(IrVararg irVararg, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitVararg(this, irVararg, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitVariable */
        public /* bridge */ /* synthetic */ Object visitVariable2(IrVariable irVariable, Object obj) {
            visitVariable(irVariable, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitVariable(IrVariable irVariable) {
            IrElementVisitorVoid.DefaultImpls.visitVariable(this, irVariable);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitVariable(IrVariable irVariable, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitVariable(this, irVariable, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitWhen */
        public /* bridge */ /* synthetic */ Object visitWhen2(IrWhen irWhen, Object obj) {
            visitWhen(irWhen, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitWhen(IrWhen irWhen) {
            IrElementVisitorVoid.DefaultImpls.visitWhen(this, irWhen);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitWhen(IrWhen irWhen, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitWhen(this, irWhen, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitWhileLoop */
        public /* bridge */ /* synthetic */ Object visitWhileLoop2(IrWhileLoop irWhileLoop, Object obj) {
            visitWhileLoop(irWhileLoop, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitWhileLoop(IrWhileLoop irWhileLoop) {
            IrElementVisitorVoid.DefaultImpls.visitWhileLoop(this, irWhileLoop);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitWhileLoop(IrWhileLoop irWhileLoop, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitWhileLoop(this, irWhileLoop, r2);
        }

        public final DirectChildren getResult() {
            return new DirectChildren(this.children, this.hasPartialLinkageRuntimeError);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        /* JADX INFO: renamed from: visitElement */
        public void mo6335visitElement(IrElement element) {
            Intrinsics.checkNotNullParameter(element, "element");
            if (this.hasPartialLinkageRuntimeError) {
                return;
            }
            IrStatement irStatement = element instanceof IrStatement ? (IrStatement) element : null;
            if (irStatement == null) {
                throw new IllegalStateException(("Not a statement: " + element).toString());
            }
            this.children.add(irStatement);
            this.hasPartialLinkageRuntimeError = PartialLinkageUtilsKt.isPartialLinkageRuntimeError(irStatement);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: compiled from: PartiallyLinkedIrTreePatcher.kt */
    @Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\br\u0018\u00002\u00020\u0001:\u0004\u0007\b\t\nR\u0018\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006\u0082\u0001\u0004\u000b\f\r\u000eø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u000fÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/linkage/partial/PartiallyLinkedIrTreePatcher$ReturnTargetContext;", "", "validReturnTargets", "", "Lorg/jetbrains/kotlin/ir/symbols/IrReturnTargetSymbol;", "getValidReturnTargets", "()Ljava/util/Set;", "Empty", "InFunction", "InFunctionBody", "InInlinedCall", "Lorg/jetbrains/kotlin/backend/common/linkage/partial/PartiallyLinkedIrTreePatcher$ReturnTargetContext$Empty;", "Lorg/jetbrains/kotlin/backend/common/linkage/partial/PartiallyLinkedIrTreePatcher$ReturnTargetContext$InFunction;", "Lorg/jetbrains/kotlin/backend/common/linkage/partial/PartiallyLinkedIrTreePatcher$ReturnTargetContext$InFunctionBody;", "Lorg/jetbrains/kotlin/backend/common/linkage/partial/PartiallyLinkedIrTreePatcher$ReturnTargetContext$InInlinedCall;", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    interface ReturnTargetContext {
        Set<IrReturnTargetSymbol> getValidReturnTargets();

        /* JADX INFO: compiled from: PartiallyLinkedIrTreePatcher.kt */
        @Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\bÆ\n\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0013\u0010\b\u001a\u00020\t2\b\u0010\n\u001a\u0004\u0018\u00010\u000bHÖ\u0003J\t\u0010\f\u001a\u00020\rHÖ\u0001J\t\u0010\u000e\u001a\u00020\u000fHÖ\u0001R\u001a\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/linkage/partial/PartiallyLinkedIrTreePatcher$ReturnTargetContext$Empty;", "Lorg/jetbrains/kotlin/backend/common/linkage/partial/PartiallyLinkedIrTreePatcher$ReturnTargetContext;", "()V", "validReturnTargets", "", "Lorg/jetbrains/kotlin/ir/symbols/IrReturnTargetSymbol;", "getValidReturnTargets", "()Ljava/util/Set;", Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "toString", "", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final /* data */ class Empty implements ReturnTargetContext {
            public static final Empty INSTANCE = new Empty();

            public boolean equals(Object other) {
                if (this == other) {
                    return true;
                }
                if (!(other instanceof Empty)) {
                    return false;
                }
                return true;
            }

            public int hashCode() {
                return 2122373050;
            }

            public String toString() {
                return "Empty";
            }

            private Empty() {
            }

            @Override // org.jetbrains.kotlin.backend.common.linkage.partial.PartiallyLinkedIrTreePatcher.ReturnTargetContext
            public Set<IrReturnTargetSymbol> getValidReturnTargets() {
                return SetsKt.emptySet();
            }
        }

        /* JADX INFO: compiled from: PartiallyLinkedIrTreePatcher.kt */
        @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0007\u0018\u00002\u00020\u0001B#\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tR\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\fR\u001a\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/linkage/partial/PartiallyLinkedIrTreePatcher$ReturnTargetContext$InFunction;", "Lorg/jetbrains/kotlin/backend/common/linkage/partial/PartiallyLinkedIrTreePatcher$ReturnTargetContext;", "validReturnTargets", "", "Lorg/jetbrains/kotlin/ir/symbols/IrReturnTargetSymbol;", "function", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "isInlined", "", "(Ljava/util/Set;Lorg/jetbrains/kotlin/ir/declarations/IrFunction;Z)V", "getFunction", "()Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "()Z", "getValidReturnTargets", "()Ljava/util/Set;", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class InFunction implements ReturnTargetContext {
            private final IrFunction function;
            private final boolean isInlined;
            private final Set<IrReturnTargetSymbol> validReturnTargets;

            /* JADX WARN: Multi-variable type inference failed */
            public InFunction(Set<? extends IrReturnTargetSymbol> validReturnTargets, IrFunction function, boolean z) {
                Intrinsics.checkNotNullParameter(validReturnTargets, "validReturnTargets");
                Intrinsics.checkNotNullParameter(function, "function");
                this.validReturnTargets = validReturnTargets;
                this.function = function;
                this.isInlined = z;
            }

            @Override // org.jetbrains.kotlin.backend.common.linkage.partial.PartiallyLinkedIrTreePatcher.ReturnTargetContext
            public Set<IrReturnTargetSymbol> getValidReturnTargets() {
                return this.validReturnTargets;
            }

            public final IrFunction getFunction() {
                return this.function;
            }

            /* JADX INFO: renamed from: isInlined, reason: from getter */
            public final boolean getIsInlined() {
                return this.isInlined;
            }
        }

        /* JADX INFO: compiled from: PartiallyLinkedIrTreePatcher.kt */
        @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0005R\u001a\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/linkage/partial/PartiallyLinkedIrTreePatcher$ReturnTargetContext$InFunctionBody;", "Lorg/jetbrains/kotlin/backend/common/linkage/partial/PartiallyLinkedIrTreePatcher$ReturnTargetContext;", "validReturnTargets", "", "Lorg/jetbrains/kotlin/ir/symbols/IrReturnTargetSymbol;", "(Ljava/util/Set;)V", "getValidReturnTargets", "()Ljava/util/Set;", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class InFunctionBody implements ReturnTargetContext {
            private final Set<IrReturnTargetSymbol> validReturnTargets;

            /* JADX WARN: Multi-variable type inference failed */
            public InFunctionBody(Set<? extends IrReturnTargetSymbol> validReturnTargets) {
                Intrinsics.checkNotNullParameter(validReturnTargets, "validReturnTargets");
                this.validReturnTargets = validReturnTargets;
            }

            @Override // org.jetbrains.kotlin.backend.common.linkage.partial.PartiallyLinkedIrTreePatcher.ReturnTargetContext
            public Set<IrReturnTargetSymbol> getValidReturnTargets() {
                return this.validReturnTargets;
            }
        }

        /* JADX INFO: compiled from: PartiallyLinkedIrTreePatcher.kt */
        @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B!\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00060\u0003¢\u0006\u0002\u0010\u0007R\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00060\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u001a\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\t¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/linkage/partial/PartiallyLinkedIrTreePatcher$ReturnTargetContext$InInlinedCall;", "Lorg/jetbrains/kotlin/backend/common/linkage/partial/PartiallyLinkedIrTreePatcher$ReturnTargetContext;", "validReturnTargets", "", "Lorg/jetbrains/kotlin/ir/symbols/IrReturnTargetSymbol;", "inlinedLambdaArgumentsWithPermittedNonLocalReturns", "Lorg/jetbrains/kotlin/ir/symbols/IrFunctionSymbol;", "(Ljava/util/Set;Ljava/util/Set;)V", "getInlinedLambdaArgumentsWithPermittedNonLocalReturns", "()Ljava/util/Set;", "getValidReturnTargets", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class InInlinedCall implements ReturnTargetContext {
            private final Set<IrFunctionSymbol> inlinedLambdaArgumentsWithPermittedNonLocalReturns;
            private final Set<IrReturnTargetSymbol> validReturnTargets;

            /* JADX WARN: Multi-variable type inference failed */
            public InInlinedCall(Set<? extends IrReturnTargetSymbol> validReturnTargets, Set<? extends IrFunctionSymbol> inlinedLambdaArgumentsWithPermittedNonLocalReturns) {
                Intrinsics.checkNotNullParameter(validReturnTargets, "validReturnTargets");
                Intrinsics.checkNotNullParameter(inlinedLambdaArgumentsWithPermittedNonLocalReturns, "inlinedLambdaArgumentsWithPermittedNonLocalReturns");
                this.validReturnTargets = validReturnTargets;
                this.inlinedLambdaArgumentsWithPermittedNonLocalReturns = inlinedLambdaArgumentsWithPermittedNonLocalReturns;
            }

            @Override // org.jetbrains.kotlin.backend.common.linkage.partial.PartiallyLinkedIrTreePatcher.ReturnTargetContext
            public Set<IrReturnTargetSymbol> getValidReturnTargets() {
                return this.validReturnTargets;
            }

            public final Set<IrFunctionSymbol> getInlinedLambdaArgumentsWithPermittedNonLocalReturns() {
                return this.inlinedLambdaArgumentsWithPermittedNonLocalReturns;
            }
        }
    }

    /* JADX INFO: compiled from: PartiallyLinkedIrTreePatcher.kt */
    @Metadata(d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0082\u0004\u0018\u00002\u00020\u0001B\u000f\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0010\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0016J\u0010\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0016H\u0016J\u0010\u0010\u0017\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0018H\u0016J\\\u0010\u0019\u001a\u0002H\u001a\"\u0004\b\u0000\u0010\u001a2#\b\u0002\u0010\u001b\u001a\u001d\u0012\u0013\u0012\u00110\u0006¢\u0006\f\b\u001d\u0012\b\b\u001e\u0012\u0004\b\b(\u001f\u0012\u0004\u0012\u00020\u00060\u001c2!\u0010 \u001a\u001d\u0012\u0013\u0012\u00110\u0006¢\u0006\f\b\u001d\u0012\b\b\u001e\u0012\u0004\b\b(!\u0012\u0004\u0012\u0002H\u001a0\u001cH\u0082\b¢\u0006\u0002\u0010\"R\u0014\u0010\u0005\u001a\u00020\u00068BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00060\nX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006#"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/linkage/partial/PartiallyLinkedIrTreePatcher$NonLocalReturnsPatcher;", "Lorg/jetbrains/kotlin/backend/common/linkage/partial/FileAwareIrElementTransformerVoid;", "startingFile", "Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageUtils$File;", "(Lorg/jetbrains/kotlin/backend/common/linkage/partial/PartiallyLinkedIrTreePatcher;Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageUtils$File;)V", "currentContext", "Lorg/jetbrains/kotlin/backend/common/linkage/partial/PartiallyLinkedIrTreePatcher$ReturnTargetContext;", "getCurrentContext", "()Lorg/jetbrains/kotlin/backend/common/linkage/partial/PartiallyLinkedIrTreePatcher$ReturnTargetContext;", "stack", "Ljava/util/ArrayDeque;", "visitBlockBody", "Lorg/jetbrains/kotlin/ir/expressions/IrBody;", "body", "Lorg/jetbrains/kotlin/ir/expressions/IrBlockBody;", "visitFunction", "Lorg/jetbrains/kotlin/ir/IrStatement;", "declaration", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "visitFunctionAccess", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionAccessExpression;", "visitReturn", "Lorg/jetbrains/kotlin/ir/expressions/IrReturn;", "withContext", "R", "getNewContext", "Lkotlin/Function1;", "Lkotlin/ParameterName;", "name", "oldContext", "block", "newContext", "(Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private final class NonLocalReturnsPatcher extends FileAwareIrElementTransformerVoid {
        private final ArrayDeque<ReturnTargetContext> stack;

        public NonLocalReturnsPatcher(org.jetbrains.kotlin.ir.linkage.partial.PartialLinkageUtils.File file) {
            super(file);
            this.stack = new ArrayDeque<>();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final ReturnTargetContext getCurrentContext() {
            ReturnTargetContext returnTargetContextPeek = this.stack.peek();
            return returnTargetContextPeek == null ? ReturnTargetContext.Empty.INSTANCE : returnTargetContextPeek;
        }

        /* JADX WARN: Code duplicated, block: B:26:0x0092  */
        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
        public IrExpression visitFunctionAccess(IrFunctionAccessExpression expression) {
            ReturnTargetContext.InInlinedCall inInlinedCall;
            Intrinsics.checkNotNullParameter(expression, "expression");
            PartiallyLinkedIrTreePatcher partiallyLinkedIrTreePatcher = PartiallyLinkedIrTreePatcher.this;
            ReturnTargetContext currentContext = getCurrentContext();
            IrFunctionSymbol symbol = expression.getSymbol();
            if (symbol.isBound()) {
                IrFunction owner = symbol.getOwner();
                if (owner.getIsInline() || AdditionalIrUtilsKt.isInlineArrayConstructor(owner, partiallyLinkedIrTreePatcher.builtIns)) {
                    ArrayList arrayList = new ArrayList(owner.getValueParameters().size() + 1);
                    if (visitFunctionAccess$lambda$5$canHaveNonLocalReturns(owner.getExtensionReceiverParameter())) {
                        visitFunctionAccess$lambda$5$countInAsInlinedLambdaArgumentWithPermittedNonLocalReturns(expression.getExtensionReceiver(), arrayList);
                    }
                    int i = 0;
                    for (Object obj : owner.getValueParameters()) {
                        int i2 = i + 1;
                        if (i < 0) {
                            CollectionsKt.throwIndexOverflow();
                        }
                        if (visitFunctionAccess$lambda$5$canHaveNonLocalReturns((IrValueParameter) obj)) {
                            visitFunctionAccess$lambda$5$countInAsInlinedLambdaArgumentWithPermittedNonLocalReturns(expression.getValueArgument(i), arrayList);
                        }
                        i = i2;
                    }
                    if (arrayList.isEmpty()) {
                        inInlinedCall = currentContext;
                    } else {
                        inInlinedCall = new ReturnTargetContext.InInlinedCall(currentContext.getValidReturnTargets(), CollectionsKt.toSet(arrayList));
                    }
                } else {
                    inInlinedCall = currentContext;
                }
            } else {
                inInlinedCall = currentContext;
            }
            if (inInlinedCall != currentContext) {
                this.stack.push(inInlinedCall);
            }
            try {
                return super.visitFunctionAccess(expression);
            } finally {
                if (inInlinedCall != currentContext) {
                    Intrinsics.areEqual(this.stack.pop(), inInlinedCall);
                }
            }
        }

        private static final boolean visitFunctionAccess$lambda$5$canHaveNonLocalReturns(IrValueParameter irValueParameter) {
            return (irValueParameter == null || irValueParameter.getIsCrossinline() || irValueParameter.getIsNoinline()) ? false : true;
        }

        private static final void visitFunctionAccess$lambda$5$countInAsInlinedLambdaArgumentWithPermittedNonLocalReturns(IrExpression irExpression, ArrayList<IrFunctionSymbol> arrayList) {
            IrSimpleFunction function;
            ArrayList<IrFunctionSymbol> arrayList2 = arrayList;
            IrSimpleFunctionSymbol symbol = null;
            IrFunctionExpression irFunctionExpression = irExpression instanceof IrFunctionExpression ? (IrFunctionExpression) irExpression : null;
            if (irFunctionExpression != null && (function = irFunctionExpression.getFunction()) != null) {
                symbol = function.getSymbol();
            }
            org.jetbrains.kotlin.utils.CollectionsKt.addIfNotNull(arrayList2, symbol);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
        public IrExpression visitReturn(IrReturn expression) {
            DirectChildren empty;
            List<IrStatement> statements;
            Intrinsics.checkNotNullParameter(expression, "expression");
            PartiallyLinkedIrTreePatcher partiallyLinkedIrTreePatcher = PartiallyLinkedIrTreePatcher.this;
            ReturnTargetContext currentContext = getCurrentContext();
            IrReturn irReturn = expression;
            IrElementTransformerVoidKt.transformChildrenVoid(irReturn, this);
            PartialLinkageCase.IllegalNonLocalReturn illegalNonLocalReturn = !currentContext.getValidReturnTargets().contains(irReturn.getReturnTargetSymbol()) ? new PartialLinkageCase.IllegalNonLocalReturn(expression, currentContext.getValidReturnTargets()) : null;
            if (illegalNonLocalReturn != null) {
                if (PartiallyLinkedIrTreePatcher.INSTANCE.hasBranches(irReturn)) {
                    empty = DirectChildren.INSTANCE.getEMPTY();
                } else {
                    DirectChildrenStatementsCollector directChildrenStatementsCollector = new DirectChildrenStatementsCollector();
                    IrVisitorsKt.acceptChildrenVoid(irReturn, directChildrenStatementsCollector);
                    empty = directChildrenStatementsCollector.getResult();
                }
                IrCall irCallThrowLinkageError$default = PartialLinkageSupportForLowerings.DefaultImpls.throwLinkageError$default(partiallyLinkedIrTreePatcher.getSupportForLowerings(), illegalNonLocalReturn, irReturn, getCurrentFile(), false, 8, null);
                if (!empty.getStatements().isEmpty()) {
                    IrCompositeImpl irCompositeImpl = new IrCompositeImpl(irReturn.getStartOffset(), irReturn.getEndOffset(), partiallyLinkedIrTreePatcher.builtIns.getNothingType(), IrStatementOrigin.PARTIAL_LINKAGE_RUNTIME_ERROR.INSTANCE);
                    CollectionsKt.addAll(irCompositeImpl.getStatements(), empty.getStatements());
                    if (!empty.getHasPartialLinkageRuntimeError()) {
                        irCompositeImpl.getStatements().add(irCallThrowLinkageError$default);
                    }
                    return irCompositeImpl;
                }
                return irCallThrowLinkageError$default;
            }
            IrContainerExpression irContainerExpression = irReturn instanceof IrContainerExpression ? (IrContainerExpression) irReturn : null;
            if (irContainerExpression == null || (statements = irContainerExpression.getStatements()) == null) {
                return irReturn;
            }
            PartiallyLinkedIrTreePatcher.INSTANCE.eliminateDeadCodeStatements(statements);
            return irReturn;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
        public IrStatement visitFunction(IrFunction declaration) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            ReturnTargetContext currentContext = getCurrentContext();
            ReturnTargetContext.InFunction inFunction = new ReturnTargetContext.InFunction(currentContext.getValidReturnTargets(), declaration, (currentContext instanceof ReturnTargetContext.InInlinedCall) && ((ReturnTargetContext.InInlinedCall) currentContext).getInlinedLambdaArgumentsWithPermittedNonLocalReturns().contains(declaration.getSymbol()));
            if (inFunction != currentContext) {
                this.stack.push(inFunction);
            }
            try {
                return super.visitFunction(declaration);
            } finally {
                if (inFunction != currentContext) {
                    Intrinsics.areEqual(this.stack.pop(), inFunction);
                }
            }
        }

        /* JADX WARN: Code duplicated, block: B:12:0x0046  */
        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
        public IrBody visitBlockBody(IrBlockBody body) {
            ReturnTargetContext.InFunctionBody inFunctionBody;
            Set of;
            Intrinsics.checkNotNullParameter(body, "body");
            ReturnTargetContext currentContext = getCurrentContext();
            if (currentContext instanceof ReturnTargetContext.InFunction) {
                ReturnTargetContext.InFunction inFunction = (ReturnTargetContext.InFunction) currentContext;
                if (body != inFunction.getFunction().getBody()) {
                    inFunctionBody = currentContext;
                } else {
                    if (inFunction.getIsInlined()) {
                        of = SetsKt.plus(currentContext.getValidReturnTargets(), inFunction.getFunction().getSymbol());
                    } else {
                        of = SetsKt.setOf(inFunction.getFunction().getSymbol());
                    }
                    inFunctionBody = new ReturnTargetContext.InFunctionBody(of);
                }
            } else {
                inFunctionBody = currentContext;
            }
            if (inFunctionBody != currentContext) {
                this.stack.push(inFunctionBody);
            }
            try {
                return super.visitBlockBody(body);
            } finally {
                if (inFunctionBody != currentContext) {
                    Intrinsics.areEqual(this.stack.pop(), inFunctionBody);
                }
            }
        }
    }

    /* JADX INFO: compiled from: PartiallyLinkedIrTreePatcher.kt */
    @Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0012\u0010\u0005\u001a\u00020\u0006*\b\u0012\u0004\u0012\u00020\b0\u0007H\u0002J\f\u0010\t\u001a\u00020\n*\u00020\u000bH\u0002J\u0016\u0010\f\u001a\u00020\n*\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/linkage/partial/PartiallyLinkedIrTreePatcher$Companion;", "", "()V", "REPLACE_WITH_CONSTRUCTOR_EXPRESSION_FIELD_FQN", "Lorg/jetbrains/kotlin/name/FqName;", "eliminateDeadCodeStatements", "", "", "Lorg/jetbrains/kotlin/ir/IrStatement;", "hasBranches", "", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "isDirectMemberOf", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "unusableClassifier", "Lorg/jetbrains/kotlin/ir/linkage/partial/ExploredClassifier$Unusable;", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final boolean isDirectMemberOf(IrDeclaration irDeclaration, ExploredClassifier.Unusable unusable) {
            IrClassifierSymbol symbol;
            IrClass parentClassOrNull;
            IrClassSymbol symbol2;
            if (unusable == null || (symbol = unusable.getSymbol()) == null || (parentClassOrNull = IrUtilsKt.getParentClassOrNull(irDeclaration)) == null || (symbol2 = parentClassOrNull.getSymbol()) == null) {
                return false;
            }
            return Intrinsics.areEqual(symbol, symbol2);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void eliminateDeadCodeStatements(List<IrStatement> list) {
            final Ref.BooleanRef booleanRef = new Ref.BooleanRef();
            final Function1<IrStatement, Boolean> function1 = new Function1<IrStatement, Boolean>() { // from class: org.jetbrains.kotlin.backend.common.linkage.partial.PartiallyLinkedIrTreePatcher$Companion$eliminateDeadCodeStatements$1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(IrStatement statement) {
                    Intrinsics.checkNotNullParameter(statement, "statement");
                    boolean z = true;
                    boolean z2 = statement instanceof IrInstanceInitializerCall ? true : statement instanceof IrDelegatingConstructorCall ? true : statement instanceof IrEnumConstructorCall ? false : booleanRef.element;
                    Ref.BooleanRef booleanRef2 = booleanRef;
                    if (!booleanRef2.element && !PartialLinkageUtilsKt.isPartialLinkageRuntimeError(statement)) {
                        z = false;
                    }
                    booleanRef2.element = z;
                    return Boolean.valueOf(z2);
                }
            };
            list.removeIf(new Predicate() { // from class: org.jetbrains.kotlin.backend.common.linkage.partial.PartiallyLinkedIrTreePatcher$Companion$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return PartiallyLinkedIrTreePatcher.Companion.eliminateDeadCodeStatements$lambda$0(function1, obj);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final boolean eliminateDeadCodeStatements$lambda$0(Function1 tmp0, Object obj) {
            Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
            return ((Boolean) tmp0.invoke(obj)).booleanValue();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final boolean hasBranches(IrExpression irExpression) {
            if (irExpression instanceof IrWhen ? true : irExpression instanceof IrLoop ? true : irExpression instanceof IrTry ? true : irExpression instanceof IrSuspensionPoint) {
                return true;
            }
            return irExpression instanceof IrSuspendableExpression;
        }
    }

    public final void patchModuleFragments(Sequence<? extends IrModuleFragment> roots) {
        Intrinsics.checkNotNullParameter(roots, "roots");
        for (IrModuleFragment irModuleFragment : roots) {
            if (!getShouldBeSkipped(irModuleFragment) && this.visitedModuleFragments.add(irModuleFragment)) {
                IrModuleFragment irModuleFragment2 = irModuleFragment;
                transformVoid(irModuleFragment2, new DeclarationTransformer(null));
                transformVoid(irModuleFragment2, new ExpressionTransformer(null));
                transformVoid(irModuleFragment2, new NonLocalReturnsPatcher(null));
            }
        }
    }
}
