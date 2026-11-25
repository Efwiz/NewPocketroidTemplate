package org.jetbrains.kotlin.backend.common.lower;

import androidx.exifinterface.media.ExifInterface;
import com.beust.jcommander.Parameters;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.NotImplementedError;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import kotlin.text.StringsKt;
import org.antlr.stringtemplate.language.ASTExpr;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.backend.common.BodyLoweringPass;
import org.jetbrains.kotlin.backend.common.CommonBackendContext;
import org.jetbrains.kotlin.backend.common.LowerKt;
import org.jetbrains.kotlin.backend.common.Mapping;
import org.jetbrains.kotlin.backend.common.descriptors.DescriptorUtilsKt;
import org.jetbrains.kotlin.backend.common.lower.inline.FunctionInliningKt;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.AsmUtil;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.IrElementsKt;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.builders.declarations.DeclarationBuildersKt;
import org.jetbrains.kotlin.ir.builders.declarations.IrFunctionBuilder;
import org.jetbrains.kotlin.ir.builders.declarations.IrValueParameterBuilder;
import org.jetbrains.kotlin.ir.declarations.IrAnonymousInitializer;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationBase;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationContainer;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOriginImpl;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationWithName;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationsKt;
import org.jetbrains.kotlin.ir.declarations.IrEnumEntry;
import org.jetbrains.kotlin.ir.declarations.IrErrorDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrExternalPackageFragment;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrLocalDelegatedProperty;
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment;
import org.jetbrains.kotlin.ir.declarations.IrPackageFragment;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrReturnTarget;
import org.jetbrains.kotlin.ir.declarations.IrScript;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrSymbolOwner;
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
import org.jetbrains.kotlin.ir.expressions.IrExpressionsKt;
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
import org.jetbrains.kotlin.ir.expressions.IrInlinedFunctionBlock;
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
import org.jetbrains.kotlin.ir.expressions.impl.IrCallImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrCompositeImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrConstructorCallImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrDelegatingConstructorCallImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrFunctionReferenceImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetFieldImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetValueImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrReturnImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrSetFieldImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrSetValueImpl;
import org.jetbrains.kotlin.ir.symbols.IrFieldSymbol;
import org.jetbrains.kotlin.ir.symbols.IrFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrPropertySymbol;
import org.jetbrains.kotlin.ir.symbols.IrValueParameterSymbol;
import org.jetbrains.kotlin.ir.symbols.IrValueSymbol;
import org.jetbrains.kotlin.ir.symbols.impl.IrFieldSymbolImpl;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.AdditionalIrUtilsKt;
import org.jetbrains.kotlin.ir.util.DumpIrTreeOptions;
import org.jetbrains.kotlin.ir.util.IrInlineUtilsKt;
import org.jetbrains.kotlin.ir.util.IrTypeParameterRemapper;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.util.RemapTypesKt;
import org.jetbrains.kotlin.ir.util.RenderIrElementKt;
import org.jetbrains.kotlin.ir.util.SetDeclarationsParentVisitor;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoidKt;
import org.jetbrains.kotlin.ir.visitors.IrElementVisitor;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.utils.MemoryOptimizedCollectionUtilKt;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: LocalDeclarationsLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0082\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0012\u0018\u00002\u00020\u0001:\u000e9:;<=>?@ABCDEFBc\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0014\b\u0002\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00060\u0005\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b\u0012\b\b\u0002\u0010\t\u001a\u00020\n\u0012\b\b\u0002\u0010\u000b\u001a\u00020\n\u0012\b\b\u0002\u0010\f\u001a\u00020\n\u0012\u0016\b\u0002\u0010\r\u001a\u0010\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000f\u0018\u00010\u0005¢\u0006\u0002\u0010\u0010J$\u0010 \u001a\u00020\u000f2\u0006\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020$2\f\u0010%\u001a\b\u0012\u0004\u0012\u00020'0&J\u0010\u0010 \u001a\u00020\u000f2\u0006\u0010(\u001a\u00020)H\u0016J:\u0010 \u001a\u00020\u000f2\u0006\u0010*\u001a\u00020+2\u0006\u0010#\u001a\u00020$2\u0006\u0010,\u001a\u00020-2\f\u0010%\u001a\b\u0012\u0004\u0012\u00020'0&2\f\u0010.\u001a\b\u0012\u0004\u0012\u00020/0&J\u0018\u0010 \u001a\u00020\u000f2\u0006\u00100\u001a\u0002012\u0006\u0010#\u001a\u00020$H\u0016J\u0016\u00102\u001a\u00020\u000f2\u0006\u00100\u001a\u0002012\u0006\u0010#\u001a\u00020$J\u0014\u00103\u001a\u000204*\u0002052\u0006\u00106\u001a\u000204H\u0002J\u0014\u00107\u001a\u00020\u000f*\u0002052\u0006\u00108\u001a\u000201H\u0002R\u0011\u0010\u000b\u001a\u00020\n¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0011\u0010\f\u001a\u00020\n¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0012R\u001d\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u001c\u0010\r\u001a\u0010\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000f\u0018\u00010\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\t\u001a\u00020\n¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0012R\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u0018\u0010\u001b\u001a\u00020\u001c*\u00020\u001d8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u001e\u0010\u001f¨\u0006G"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/LocalDeclarationsLowering;", "Lorg/jetbrains/kotlin/backend/common/BodyLoweringPass;", "context", "Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;", "localNameSanitizer", "Lkotlin/Function1;", "", "visibilityPolicy", "Lorg/jetbrains/kotlin/backend/common/lower/VisibilityPolicy;", "suggestUniqueNames", "", "compatibilityModeForInlinedLocalDelegatedPropertyAccessors", "forceFieldsForInlineCaptures", "postLocalDeclarationLoweringCallback", "Lorg/jetbrains/kotlin/backend/common/lower/LocalDeclarationsLowering$IntermediateDatastructures;", "", "(Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;Lkotlin/jvm/functions/Function1;Lorg/jetbrains/kotlin/backend/common/lower/VisibilityPolicy;ZZZLkotlin/jvm/functions/Function1;)V", "getCompatibilityModeForInlinedLocalDelegatedPropertyAccessors", "()Z", "getContext", "()Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;", "getForceFieldsForInlineCaptures", "getLocalNameSanitizer", "()Lkotlin/jvm/functions/Function1;", "getSuggestUniqueNames", "getVisibilityPolicy", "()Lorg/jetbrains/kotlin/backend/common/lower/VisibilityPolicy;", "scopeWithCounter", "Lorg/jetbrains/kotlin/backend/common/lower/LocalDeclarationsLowering$ScopeWithCounter;", "Lorg/jetbrains/kotlin/ir/declarations/IrSymbolOwner;", "getScopeWithCounter", "(Lorg/jetbrains/kotlin/ir/declarations/IrSymbolOwner;)Lorg/jetbrains/kotlin/backend/common/lower/LocalDeclarationsLowering$ScopeWithCounter;", "lower", "irElement", "Lorg/jetbrains/kotlin/ir/IrElement;", "container", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "classesToLower", "", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "irFile", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "irBlock", "Lorg/jetbrains/kotlin/ir/expressions/IrBlock;", "closestParent", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;", "functionsToSkip", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "irBody", "Lorg/jetbrains/kotlin/ir/expressions/IrBody;", "lowerWithoutActualChange", "remapType", "Lorg/jetbrains/kotlin/ir/types/IrType;", "Lorg/jetbrains/kotlin/backend/common/lower/LocalDeclarationsLowering$LocalContext;", ModuleXmlParser.TYPE, "remapTypes", "body", "DECLARATION_ORIGIN_FIELD_FOR_CAPTURED_VALUE", "DECLARATION_ORIGIN_FIELD_FOR_CROSSINLINE_CAPTURED_VALUE", "IntermediateDatastructures", "LocalClassConstructorContext", "LocalClassContext", "LocalClassMemberContext", "LocalContext", "LocalContextWithClosureAsParameters", "LocalDeclarationsTransformer", "LocalFunctionContext", "LocalScopeWithCounterMap", "OwnerForLoweredDeclaration", "PotentiallyUnusedField", "ScopeWithCounter", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class LocalDeclarationsLowering implements BodyLoweringPass {
    private final boolean compatibilityModeForInlinedLocalDelegatedPropertyAccessors;
    private final CommonBackendContext context;
    private final boolean forceFieldsForInlineCaptures;
    private final Function1<String, String> localNameSanitizer;
    private final Function1<IntermediateDatastructures, Unit> postLocalDeclarationLoweringCallback;
    private final boolean suggestUniqueNames;
    private final VisibilityPolicy visibilityPolicy;

    /* JADX WARN: Multi-variable type inference failed */
    public LocalDeclarationsLowering(CommonBackendContext context, Function1<? super String, String> localNameSanitizer, VisibilityPolicy visibilityPolicy, boolean z, boolean z2, boolean z3, Function1<? super IntermediateDatastructures, Unit> function1) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(localNameSanitizer, "localNameSanitizer");
        Intrinsics.checkNotNullParameter(visibilityPolicy, "visibilityPolicy");
        this.context = context;
        this.localNameSanitizer = localNameSanitizer;
        this.visibilityPolicy = visibilityPolicy;
        this.suggestUniqueNames = z;
        this.compatibilityModeForInlinedLocalDelegatedPropertyAccessors = z2;
        this.forceFieldsForInlineCaptures = z3;
        this.postLocalDeclarationLoweringCallback = function1;
    }

    public final CommonBackendContext getContext() {
        return this.context;
    }

    public /* synthetic */ LocalDeclarationsLowering(CommonBackendContext commonBackendContext, Function1 function1, VisibilityPolicy visibilityPolicy, boolean z, boolean z2, boolean z3, Function1 function2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(commonBackendContext, (i & 2) != 0 ? new Function1<String, String>() { // from class: org.jetbrains.kotlin.backend.common.lower.LocalDeclarationsLowering.1
            @Override // kotlin.jvm.functions.Function1
            public final String invoke(String it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return it;
            }
        } : function1, (i & 4) != 0 ? VisibilityPolicy.INSTANCE.getDEFAULT() : visibilityPolicy, (i & 8) != 0 ? true : z, (i & 16) != 0 ? false : z2, (i & 32) == 0 ? z3 : false, (i & 64) != 0 ? null : function2);
    }

    public final Function1<String, String> getLocalNameSanitizer() {
        return this.localNameSanitizer;
    }

    public final VisibilityPolicy getVisibilityPolicy() {
        return this.visibilityPolicy;
    }

    public final boolean getSuggestUniqueNames() {
        return this.suggestUniqueNames;
    }

    public final boolean getCompatibilityModeForInlinedLocalDelegatedPropertyAccessors() {
        return this.compatibilityModeForInlinedLocalDelegatedPropertyAccessors;
    }

    public final boolean getForceFieldsForInlineCaptures() {
        return this.forceFieldsForInlineCaptures;
    }

    @Override // org.jetbrains.kotlin.backend.common.BodyLoweringPass, org.jetbrains.kotlin.backend.common.FileLoweringPass
    public void lower(IrFile irFile) {
        Intrinsics.checkNotNullParameter(irFile, "irFile");
        LowerKt.runOnFilePostfix$default(this, irFile, false, 2, null);
    }

    /* JADX INFO: compiled from: LocalDeclarationsLowering.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/LocalDeclarationsLowering$DECLARATION_ORIGIN_FIELD_FOR_CAPTURED_VALUE;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOriginImpl;", "()V", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class DECLARATION_ORIGIN_FIELD_FOR_CAPTURED_VALUE extends IrDeclarationOriginImpl {
        public static final DECLARATION_ORIGIN_FIELD_FOR_CAPTURED_VALUE INSTANCE = new DECLARATION_ORIGIN_FIELD_FOR_CAPTURED_VALUE();

        private DECLARATION_ORIGIN_FIELD_FOR_CAPTURED_VALUE() {
            super("FIELD_FOR_CAPTURED_VALUE", true);
        }
    }

    /* JADX INFO: compiled from: LocalDeclarationsLowering.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/LocalDeclarationsLowering$DECLARATION_ORIGIN_FIELD_FOR_CROSSINLINE_CAPTURED_VALUE;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOriginImpl;", "()V", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class DECLARATION_ORIGIN_FIELD_FOR_CROSSINLINE_CAPTURED_VALUE extends IrDeclarationOriginImpl {
        public static final DECLARATION_ORIGIN_FIELD_FOR_CROSSINLINE_CAPTURED_VALUE INSTANCE = new DECLARATION_ORIGIN_FIELD_FOR_CROSSINLINE_CAPTURED_VALUE();

        private DECLARATION_ORIGIN_FIELD_FOR_CROSSINLINE_CAPTURED_VALUE() {
            super("FIELD_FOR_CROSSINLINE_CAPTURED_VALUE", true);
        }
    }

    @Override // org.jetbrains.kotlin.backend.common.BodyLoweringPass
    public void lower(IrBody irBody, IrDeclaration container) {
        Intrinsics.checkNotNullParameter(irBody, "irBody");
        Intrinsics.checkNotNullParameter(container, "container");
        new LocalDeclarationsTransformer(this, irBody, container, null, null, null, 28, null).lowerLocalDeclarations();
    }

    public final void lower(IrElement irElement, IrDeclaration container, Set<? extends IrClass> classesToLower) {
        Intrinsics.checkNotNullParameter(irElement, "irElement");
        Intrinsics.checkNotNullParameter(container, "container");
        Intrinsics.checkNotNullParameter(classesToLower, "classesToLower");
        new LocalDeclarationsTransformer(this, irElement, container, null, classesToLower, null, 16, null).lowerLocalDeclarations();
    }

    public final void lower(IrBlock irBlock, IrDeclaration container, IrDeclarationParent closestParent, Set<? extends IrClass> classesToLower, Set<? extends IrSimpleFunction> functionsToSkip) {
        Intrinsics.checkNotNullParameter(irBlock, "irBlock");
        Intrinsics.checkNotNullParameter(container, "container");
        Intrinsics.checkNotNullParameter(closestParent, "closestParent");
        Intrinsics.checkNotNullParameter(classesToLower, "classesToLower");
        Intrinsics.checkNotNullParameter(functionsToSkip, "functionsToSkip");
        new LocalDeclarationsTransformer(this, irBlock, container, closestParent, classesToLower, functionsToSkip).lowerLocalDeclarations();
    }

    public final void lowerWithoutActualChange(IrBody irBody, IrDeclaration container) {
        Intrinsics.checkNotNullParameter(irBody, "irBody");
        Intrinsics.checkNotNullParameter(container, "container");
        Iterable keys = this.context.getMapping().getCapturedConstructors().getKeys();
        ArrayList arrayList = new ArrayList();
        Iterator it = keys.iterator();
        while (it.hasNext()) {
            IrConstructor irConstructor = this.context.getMapping().getCapturedConstructors().get((IrConstructor) it.next());
            if (irConstructor != null) {
                arrayList.add(irConstructor);
            }
        }
        new LocalDeclarationsTransformer(this, irBody, container, null, null, null, 28, null).cacheLocalConstructors();
        ArrayList arrayList2 = new ArrayList();
        for (Object obj : arrayList) {
            if (this.context.getMapping().getCapturedConstructors().get((IrConstructor) obj) != null) {
                arrayList2.add(obj);
            }
        }
        Iterator it2 = arrayList2.iterator();
        while (it2.hasNext()) {
            this.context.getMapping().getCapturedConstructors().set((IrConstructor) it2.next(), null);
        }
    }

    /* JADX INFO: compiled from: LocalDeclarationsLowering.kt */
    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0010#\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u001a\u0010\u0005\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0017\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/LocalDeclarationsLowering$ScopeWithCounter;", "", "irElement", "Lorg/jetbrains/kotlin/ir/IrElement;", "(Lorg/jetbrains/kotlin/ir/IrElement;)V", "counter", "", "getCounter", "()I", "setCounter", "(I)V", "getIrElement", "()Lorg/jetbrains/kotlin/ir/IrElement;", "usedLocalFunctionNames", "", "Lorg/jetbrains/kotlin/name/Name;", "getUsedLocalFunctionNames", "()Ljava/util/Set;", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class ScopeWithCounter {
        private int counter;
        private final IrElement irElement;
        private final Set<Name> usedLocalFunctionNames;

        public ScopeWithCounter(IrElement irElement) {
            Intrinsics.checkNotNullParameter(irElement, "irElement");
            this.irElement = irElement;
            this.usedLocalFunctionNames = new HashSet();
        }

        public final IrElement getIrElement() {
            return this.irElement;
        }

        public final int getCounter() {
            return this.counter;
        }

        public final void setCounter(int i) {
            this.counter = i;
        }

        public final Set<Name> getUsedLocalFunctionNames() {
            return this.usedLocalFunctionNames;
        }
    }

    /* JADX INFO: compiled from: LocalDeclarationsLowering.kt */
    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002R\u001d\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/LocalDeclarationsLowering$LocalScopeWithCounterMap;", "", "()V", "scopeMap", "", "Lorg/jetbrains/kotlin/ir/declarations/IrSymbolOwner;", "Lorg/jetbrains/kotlin/backend/common/lower/LocalDeclarationsLowering$ScopeWithCounter;", "getScopeMap", "()Ljava/util/Map;", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class LocalScopeWithCounterMap {
        private final Map<IrSymbolOwner, ScopeWithCounter> scopeMap = new HashMap();

        public final Map<IrSymbolOwner, ScopeWithCounter> getScopeMap() {
            return this.scopeMap;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final ScopeWithCounter getScopeWithCounter(IrSymbolOwner irSymbolOwner) {
        Map<IrSymbolOwner, ScopeWithCounter> scopeMap = this.context.getIr().getLocalScopeWithCounterMap().getScopeMap();
        ScopeWithCounter scopeWithCounter = scopeMap.get(irSymbolOwner);
        if (scopeWithCounter == null) {
            scopeWithCounter = new ScopeWithCounter(irSymbolOwner);
            scopeMap.put(irSymbolOwner, scopeWithCounter);
        }
        return scopeWithCounter;
    }

    /* JADX INFO: compiled from: LocalDeclarationsLowering.kt */
    @Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\"\u0010\f\u001a\u0004\u0018\u00010\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0011\u001a\u00020\u0012H&R\u001d\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/LocalDeclarationsLowering$LocalContext;", "", "()V", "capturedTypeParameterToTypeParameter", "", "Lorg/jetbrains/kotlin/ir/declarations/IrTypeParameter;", "getCapturedTypeParameterToTypeParameter", "()Ljava/util/Map;", "typeRemapper", "Lorg/jetbrains/kotlin/ir/util/IrTypeParameterRemapper;", "getTypeRemapper", "()Lorg/jetbrains/kotlin/ir/util/IrTypeParameterRemapper;", "irGet", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "startOffset", "", "endOffset", "valueDeclaration", "Lorg/jetbrains/kotlin/ir/declarations/IrValueDeclaration;", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static abstract class LocalContext {
        private final Map<IrTypeParameter, IrTypeParameter> capturedTypeParameterToTypeParameter;
        private final IrTypeParameterRemapper typeRemapper;

        public abstract IrExpression irGet(int startOffset, int endOffset, IrValueDeclaration valueDeclaration);

        public LocalContext() {
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            this.capturedTypeParameterToTypeParameter = linkedHashMap;
            this.typeRemapper = new IrTypeParameterRemapper(linkedHashMap);
        }

        public final Map<IrTypeParameter, IrTypeParameter> getCapturedTypeParameterToTypeParameter() {
            return this.capturedTypeParameterToTypeParameter;
        }

        public final IrTypeParameterRemapper getTypeRemapper() {
            return this.typeRemapper;
        }
    }

    /* JADX INFO: compiled from: LocalDeclarationsLowering.kt */
    @Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\"\u0010\u000f\u001a\u0004\u0018\u00010\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00122\u0006\u0010\u0014\u001a\u00020\u0005H\u0016R\u001d\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0012\u0010\t\u001a\u00020\nX¦\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u0012\u0010\r\u001a\u00020\nX¦\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\f¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/LocalDeclarationsLowering$LocalContextWithClosureAsParameters;", "Lorg/jetbrains/kotlin/backend/common/lower/LocalDeclarationsLowering$LocalContext;", "()V", "capturedValueToParameter", "", "Lorg/jetbrains/kotlin/ir/declarations/IrValueDeclaration;", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "getCapturedValueToParameter", "()Ljava/util/Map;", "declaration", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "getDeclaration", "()Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "transformedDeclaration", "getTransformedDeclaration", "irGet", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "startOffset", "", "endOffset", "valueDeclaration", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static abstract class LocalContextWithClosureAsParameters extends LocalContext {
        private final Map<IrValueDeclaration, IrValueParameter> capturedValueToParameter = new LinkedHashMap();

        public abstract IrFunction getDeclaration();

        public abstract IrFunction getTransformedDeclaration();

        public final Map<IrValueDeclaration, IrValueParameter> getCapturedValueToParameter() {
            return this.capturedValueToParameter;
        }

        @Override // org.jetbrains.kotlin.backend.common.lower.LocalDeclarationsLowering.LocalContext
        public IrExpression irGet(int startOffset, int endOffset, IrValueDeclaration valueDeclaration) {
            Intrinsics.checkNotNullParameter(valueDeclaration, "valueDeclaration");
            IrValueParameter irValueParameter = this.capturedValueToParameter.get(valueDeclaration);
            if (irValueParameter == null) {
                return null;
            }
            return new IrGetValueImpl(startOffset, endOffset, irValueParameter.getType(), irValueParameter.getSymbol(), null, 16, null);
        }
    }

    /* JADX INFO: compiled from: LocalDeclarationsLowering.kt */
    @Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0002\f\rB\u000f\b\u0004\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH&J\b\u0010\n\u001a\u00020\u000bH&R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u0005\u0082\u0001\u0002\u000e\u000f¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/LocalDeclarationsLowering$OwnerForLoweredDeclaration;", "", "isLocal", "", "(Z)V", "()Z", "addChild", "", "declaration", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "closestDeclarationParent", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;", "Block", "DeclarationContainer", "Lorg/jetbrains/kotlin/backend/common/lower/LocalDeclarationsLowering$OwnerForLoweredDeclaration$Block;", "Lorg/jetbrains/kotlin/backend/common/lower/LocalDeclarationsLowering$OwnerForLoweredDeclaration$DeclarationContainer;", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static abstract class OwnerForLoweredDeclaration {
        private final boolean isLocal;

        public /* synthetic */ OwnerForLoweredDeclaration(boolean z, DefaultConstructorMarker defaultConstructorMarker) {
            this(z);
        }

        public abstract void addChild(IrDeclaration declaration);

        /* JADX INFO: renamed from: closestDeclarationParent */
        public abstract IrDeclarationParent getIrDeclarationContainer();

        private OwnerForLoweredDeclaration(boolean z) {
            this.isLocal = z;
        }

        /* JADX INFO: renamed from: isLocal, reason: from getter */
        public final boolean getIsLocal() {
            return this.isLocal;
        }

        /* JADX INFO: compiled from: LocalDeclarationsLowering.kt */
        @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016J\b\u0010\t\u001a\u00020\u0003H\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/LocalDeclarationsLowering$OwnerForLoweredDeclaration$DeclarationContainer;", "Lorg/jetbrains/kotlin/backend/common/lower/LocalDeclarationsLowering$OwnerForLoweredDeclaration;", "irDeclarationContainer", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationContainer;", "(Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationContainer;)V", "addChild", "", "declaration", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "closestDeclarationParent", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class DeclarationContainer extends OwnerForLoweredDeclaration {
            private final IrDeclarationContainer irDeclarationContainer;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public DeclarationContainer(IrDeclarationContainer irDeclarationContainer) {
                super(false, null);
                Intrinsics.checkNotNullParameter(irDeclarationContainer, "irDeclarationContainer");
                this.irDeclarationContainer = irDeclarationContainer;
            }

            @Override // org.jetbrains.kotlin.backend.common.lower.LocalDeclarationsLowering.OwnerForLoweredDeclaration
            public void addChild(IrDeclaration declaration) {
                Intrinsics.checkNotNullParameter(declaration, "declaration");
                IrUtilsKt.addChild(this.irDeclarationContainer, declaration);
            }

            @Override // org.jetbrains.kotlin.backend.common.lower.LocalDeclarationsLowering.OwnerForLoweredDeclaration
            /* JADX INFO: renamed from: closestDeclarationParent, reason: from getter */
            public IrDeclarationContainer getIrDeclarationContainer() {
                return this.irDeclarationContainer;
            }
        }

        /* JADX INFO: compiled from: LocalDeclarationsLowering.kt */
        @Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0016J\b\u0010\r\u001a\u00020\u0005H\u0016R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/LocalDeclarationsLowering$OwnerForLoweredDeclaration$Block;", "Lorg/jetbrains/kotlin/backend/common/lower/LocalDeclarationsLowering$OwnerForLoweredDeclaration;", "irBlock", "Lorg/jetbrains/kotlin/ir/expressions/IrBlock;", "irDeclarationParent", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;", "(Lorg/jetbrains/kotlin/ir/expressions/IrBlock;Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;)V", "initialStatementsCount", "", "addChild", "", "declaration", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "closestDeclarationParent", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class Block extends OwnerForLoweredDeclaration {
            private final int initialStatementsCount;
            private final IrBlock irBlock;
            private final IrDeclarationParent irDeclarationParent;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public Block(IrBlock irBlock, IrDeclarationParent irDeclarationParent) {
                super(true, null);
                Intrinsics.checkNotNullParameter(irBlock, "irBlock");
                Intrinsics.checkNotNullParameter(irDeclarationParent, "irDeclarationParent");
                this.irBlock = irBlock;
                this.irDeclarationParent = irDeclarationParent;
                this.initialStatementsCount = irBlock.getStatements().size();
            }

            @Override // org.jetbrains.kotlin.backend.common.lower.LocalDeclarationsLowering.OwnerForLoweredDeclaration
            public void addChild(IrDeclaration declaration) {
                Intrinsics.checkNotNullParameter(declaration, "declaration");
                this.irBlock.getStatements().add(this.irBlock.getStatements().size() - this.initialStatementsCount, declaration);
            }

            @Override // org.jetbrains.kotlin.backend.common.lower.LocalDeclarationsLowering.OwnerForLoweredDeclaration
            /* JADX INFO: renamed from: closestDeclarationParent, reason: from getter */
            public IrDeclarationParent getIrDeclarationContainer() {
                return this.irDeclarationParent;
            }
        }
    }

    /* JADX INFO: compiled from: LocalDeclarationsLowering.kt */
    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000f\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bR\u001a\u0010\t\u001a\u00020\nX\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u001a\u0010\u0015\u001a\u00020\u0003X\u0096.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0010\"\u0004\b\u0017\u0010\u0018¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/LocalDeclarationsLowering$LocalFunctionContext;", "Lorg/jetbrains/kotlin/backend/common/lower/LocalDeclarationsLowering$LocalContextWithClosureAsParameters;", "declaration", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "index", "", "ownerForLoweredDeclaration", "Lorg/jetbrains/kotlin/backend/common/lower/LocalDeclarationsLowering$OwnerForLoweredDeclaration;", "(Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;ILorg/jetbrains/kotlin/backend/common/lower/LocalDeclarationsLowering$OwnerForLoweredDeclaration;)V", "closure", "Lorg/jetbrains/kotlin/backend/common/lower/Closure;", "getClosure", "()Lorg/jetbrains/kotlin/backend/common/lower/Closure;", "setClosure", "(Lorg/jetbrains/kotlin/backend/common/lower/Closure;)V", "getDeclaration", "()Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "getIndex", "()I", "getOwnerForLoweredDeclaration", "()Lorg/jetbrains/kotlin/backend/common/lower/LocalDeclarationsLowering$OwnerForLoweredDeclaration;", "transformedDeclaration", "getTransformedDeclaration", "setTransformedDeclaration", "(Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;)V", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class LocalFunctionContext extends LocalContextWithClosureAsParameters {
        public Closure closure;
        private final IrSimpleFunction declaration;
        private final int index;
        private final OwnerForLoweredDeclaration ownerForLoweredDeclaration;
        public IrSimpleFunction transformedDeclaration;

        @Override // org.jetbrains.kotlin.backend.common.lower.LocalDeclarationsLowering.LocalContextWithClosureAsParameters
        public IrSimpleFunction getDeclaration() {
            return this.declaration;
        }

        public final int getIndex() {
            return this.index;
        }

        public final OwnerForLoweredDeclaration getOwnerForLoweredDeclaration() {
            return this.ownerForLoweredDeclaration;
        }

        public LocalFunctionContext(IrSimpleFunction declaration, int i, OwnerForLoweredDeclaration ownerForLoweredDeclaration) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            Intrinsics.checkNotNullParameter(ownerForLoweredDeclaration, "ownerForLoweredDeclaration");
            this.declaration = declaration;
            this.index = i;
            this.ownerForLoweredDeclaration = ownerForLoweredDeclaration;
        }

        public final Closure getClosure() {
            Closure closure = this.closure;
            if (closure != null) {
                return closure;
            }
            Intrinsics.throwUninitializedPropertyAccessException("closure");
            return null;
        }

        public final void setClosure(Closure closure) {
            Intrinsics.checkNotNullParameter(closure, "<set-?>");
            this.closure = closure;
        }

        @Override // org.jetbrains.kotlin.backend.common.lower.LocalDeclarationsLowering.LocalContextWithClosureAsParameters
        public IrSimpleFunction getTransformedDeclaration() {
            IrSimpleFunction irSimpleFunction = this.transformedDeclaration;
            if (irSimpleFunction != null) {
                return irSimpleFunction;
            }
            Intrinsics.throwUninitializedPropertyAccessException("transformedDeclaration");
            return null;
        }

        public void setTransformedDeclaration(IrSimpleFunction irSimpleFunction) {
            Intrinsics.checkNotNullParameter(irSimpleFunction, "<set-?>");
            this.transformedDeclaration = irSimpleFunction;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: compiled from: LocalDeclarationsLowering.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\n\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u001a\u0010\u000b\u001a\u00020\u0003X\u0096.¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\b\"\u0004\b\r\u0010\u000e¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/LocalDeclarationsLowering$LocalClassConstructorContext;", "Lorg/jetbrains/kotlin/backend/common/lower/LocalDeclarationsLowering$LocalContextWithClosureAsParameters;", "declaration", "Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "inInlineFunctionScope", "", "(Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;Z)V", "getDeclaration", "()Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "getInInlineFunctionScope", "()Z", "transformedDeclaration", "getTransformedDeclaration", "setTransformedDeclaration", "(Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;)V", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    static final class LocalClassConstructorContext extends LocalContextWithClosureAsParameters {
        private final IrConstructor declaration;
        private final boolean inInlineFunctionScope;
        public IrConstructor transformedDeclaration;

        @Override // org.jetbrains.kotlin.backend.common.lower.LocalDeclarationsLowering.LocalContextWithClosureAsParameters
        public IrConstructor getDeclaration() {
            return this.declaration;
        }

        public final boolean getInInlineFunctionScope() {
            return this.inInlineFunctionScope;
        }

        public LocalClassConstructorContext(IrConstructor declaration, boolean z) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            this.declaration = declaration;
            this.inInlineFunctionScope = z;
        }

        @Override // org.jetbrains.kotlin.backend.common.lower.LocalDeclarationsLowering.LocalContextWithClosureAsParameters
        public IrConstructor getTransformedDeclaration() {
            IrConstructor irConstructor = this.transformedDeclaration;
            if (irConstructor != null) {
                return irConstructor;
            }
            Intrinsics.throwUninitializedPropertyAccessException("transformedDeclaration");
            return null;
        }

        public void setTransformedDeclaration(IrConstructor irConstructor) {
            Intrinsics.checkNotNullParameter(irConstructor, "<set-?>");
            this.transformedDeclaration = irConstructor;
        }
    }

    /* JADX INFO: compiled from: LocalDeclarationsLowering.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u00048F¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\"\u0010\b\u001a\u0004\u0018\u00010\u00042\b\u0010\u0007\u001a\u0004\u0018\u00010\u0004@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\u0006¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/LocalDeclarationsLowering$PotentiallyUnusedField;", "", "()V", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/ir/symbols/IrFieldSymbol;", "getSymbol", "()Lorg/jetbrains/kotlin/ir/symbols/IrFieldSymbol;", "<set-?>", "symbolIfUsed", "getSymbolIfUsed", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class PotentiallyUnusedField {
        private IrFieldSymbol symbolIfUsed;

        public final IrFieldSymbol getSymbolIfUsed() {
            return this.symbolIfUsed;
        }

        public final IrFieldSymbol getSymbol() {
            IrFieldSymbol irFieldSymbol = this.symbolIfUsed;
            if (irFieldSymbol != null) {
                return irFieldSymbol;
            }
            IrFieldSymbolImpl irFieldSymbolImpl = new IrFieldSymbolImpl(null, 1, null);
            this.symbolIfUsed = irFieldSymbolImpl;
            return irFieldSymbolImpl;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: compiled from: LocalDeclarationsLowering.kt */
    @Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\b\u0082\u0004\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0001¢\u0006\u0002\u0010\u0007J\"\u0010\u001a\u001a\u0004\u0018\u00010\u001b2\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001d2\u0006\u0010\u001f\u001a\u00020\nH\u0016J\f\u0010 \u001a\u00020\u0005*\u00020\nH\u0002R\u001d\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u000b0\t¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u001a\u0010\u000e\u001a\u00020\u000fX\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013R\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019¨\u0006!"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/LocalDeclarationsLowering$LocalClassContext;", "Lorg/jetbrains/kotlin/backend/common/lower/LocalDeclarationsLowering$LocalContext;", "declaration", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "inInlineFunctionScope", "", "constructorContext", "(Lorg/jetbrains/kotlin/backend/common/lower/LocalDeclarationsLowering;Lorg/jetbrains/kotlin/ir/declarations/IrClass;ZLorg/jetbrains/kotlin/backend/common/lower/LocalDeclarationsLowering$LocalContext;)V", "capturedValueToField", "", "Lorg/jetbrains/kotlin/ir/declarations/IrValueDeclaration;", "Lorg/jetbrains/kotlin/backend/common/lower/LocalDeclarationsLowering$PotentiallyUnusedField;", "getCapturedValueToField", "()Ljava/util/Map;", "closure", "Lorg/jetbrains/kotlin/backend/common/lower/Closure;", "getClosure", "()Lorg/jetbrains/kotlin/backend/common/lower/Closure;", "setClosure", "(Lorg/jetbrains/kotlin/backend/common/lower/Closure;)V", "getConstructorContext", "()Lorg/jetbrains/kotlin/backend/common/lower/LocalDeclarationsLowering$LocalContext;", "getDeclaration", "()Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "getInInlineFunctionScope", "()Z", "irGet", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "startOffset", "", "endOffset", "valueDeclaration", "isInlineDeclaration", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    final class LocalClassContext extends LocalContext {
        private final Map<IrValueDeclaration, PotentiallyUnusedField> capturedValueToField;
        public Closure closure;
        private final LocalContext constructorContext;
        private final IrClass declaration;
        private final boolean inInlineFunctionScope;
        final /* synthetic */ LocalDeclarationsLowering this$0;

        public LocalClassContext(LocalDeclarationsLowering localDeclarationsLowering, IrClass declaration, boolean z, LocalContext localContext) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            this.this$0 = localDeclarationsLowering;
            this.declaration = declaration;
            this.inInlineFunctionScope = z;
            this.constructorContext = localContext;
            this.capturedValueToField = new LinkedHashMap();
        }

        public final IrClass getDeclaration() {
            return this.declaration;
        }

        public final boolean getInInlineFunctionScope() {
            return this.inInlineFunctionScope;
        }

        public final LocalContext getConstructorContext() {
            return this.constructorContext;
        }

        public final Closure getClosure() {
            Closure closure = this.closure;
            if (closure != null) {
                return closure;
            }
            Intrinsics.throwUninitializedPropertyAccessException("closure");
            return null;
        }

        public final void setClosure(Closure closure) {
            Intrinsics.checkNotNullParameter(closure, "<set-?>");
            this.closure = closure;
        }

        public final Map<IrValueDeclaration, PotentiallyUnusedField> getCapturedValueToField() {
            return this.capturedValueToField;
        }

        @Override // org.jetbrains.kotlin.backend.common.lower.LocalDeclarationsLowering.LocalContext
        public IrExpression irGet(int startOffset, int endOffset, IrValueDeclaration valueDeclaration) {
            LocalContext localContext;
            Intrinsics.checkNotNullParameter(valueDeclaration, "valueDeclaration");
            if ((!this.this$0.getForceFieldsForInlineCaptures() || !isInlineDeclaration(valueDeclaration)) && (localContext = this.constructorContext) != null) {
                IrExpression irExpressionIrGet = localContext.irGet(startOffset, endOffset, valueDeclaration);
                if (irExpressionIrGet != null) {
                    return irExpressionIrGet;
                }
            }
            PotentiallyUnusedField potentiallyUnusedField = this.capturedValueToField.get(valueDeclaration);
            if (potentiallyUnusedField == null) {
                return null;
            }
            IrValueParameter thisReceiver = this.declaration.getThisReceiver();
            Intrinsics.checkNotNull(thisReceiver);
            return new IrGetFieldImpl(startOffset, endOffset, potentiallyUnusedField.getSymbol(), valueDeclaration.getType(), new IrGetValueImpl(startOffset, endOffset, thisReceiver.getType(), thisReceiver.getSymbol(), null, 16, null), null, null, 96, null);
        }

        private final boolean isInlineDeclaration(IrValueDeclaration irValueDeclaration) {
            if (irValueDeclaration instanceof IrValueParameter) {
                IrDeclarationParent parent = irValueDeclaration.getParent();
                if ((parent instanceof IrFunction) && ((IrFunction) parent).getIsInline() && FunctionInliningKt.isInlineParameter$default((IrValueParameter) irValueDeclaration, null, 1, null)) {
                    return true;
                }
            }
            return false;
        }
    }

    /* JADX INFO: compiled from: LocalDeclarationsLowering.kt */
    @Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\u0019\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\u0010\u0004\u001a\u00060\u0005R\u00020\u0006¢\u0006\u0002\u0010\u0007J\"\u0010\f\u001a\u0004\u0018\u00010\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0011\u001a\u00020\u0012H\u0016R\u0015\u0010\u0004\u001a\u00060\u0005R\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/LocalDeclarationsLowering$LocalClassMemberContext;", "Lorg/jetbrains/kotlin/backend/common/lower/LocalDeclarationsLowering$LocalContext;", "member", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "classContext", "Lorg/jetbrains/kotlin/backend/common/lower/LocalDeclarationsLowering$LocalClassContext;", "Lorg/jetbrains/kotlin/backend/common/lower/LocalDeclarationsLowering;", "(Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;Lorg/jetbrains/kotlin/backend/common/lower/LocalDeclarationsLowering$LocalClassContext;)V", "getClassContext", "()Lorg/jetbrains/kotlin/backend/common/lower/LocalDeclarationsLowering$LocalClassContext;", "getMember", "()Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "irGet", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "startOffset", "", "endOffset", "valueDeclaration", "Lorg/jetbrains/kotlin/ir/declarations/IrValueDeclaration;", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class LocalClassMemberContext extends LocalContext {
        private final LocalClassContext classContext;
        private final IrDeclaration member;

        public LocalClassMemberContext(IrDeclaration member, LocalClassContext classContext) {
            Intrinsics.checkNotNullParameter(member, "member");
            Intrinsics.checkNotNullParameter(classContext, "classContext");
            this.member = member;
            this.classContext = classContext;
        }

        public final LocalClassContext getClassContext() {
            return this.classContext;
        }

        public final IrDeclaration getMember() {
            return this.member;
        }

        @Override // org.jetbrains.kotlin.backend.common.lower.LocalDeclarationsLowering.LocalContext
        public IrExpression irGet(int startOffset, int endOffset, IrValueDeclaration valueDeclaration) {
            Intrinsics.checkNotNullParameter(valueDeclaration, "valueDeclaration");
            PotentiallyUnusedField potentiallyUnusedField = this.classContext.getCapturedValueToField().get(valueDeclaration);
            if (potentiallyUnusedField == null) {
                return null;
            }
            IrDeclaration irDeclaration = this.member;
            IrValueParameter dispatchReceiverParameter = irDeclaration instanceof IrFunction ? ((IrFunction) irDeclaration).getDispatchReceiverParameter() : this.classContext.getDeclaration().getThisReceiver();
            if (dispatchReceiverParameter == null) {
                throw new IllegalStateException(("No dispatch receiver parameter for " + RenderIrElementKt.render$default(this.member, (DumpIrTreeOptions) null, 1, (Object) null)).toString());
            }
            return new IrGetFieldImpl(startOffset, endOffset, potentiallyUnusedField.getSymbol(), valueDeclaration.getType(), new IrGetValueImpl(startOffset, endOffset, dispatchReceiverParameter.getType(), dispatchReceiverParameter.getSymbol(), null, 16, null), null, null, 96, null);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrType remapType(LocalContext localContext, IrType irType) {
        return localContext.getCapturedTypeParameterToTypeParameter().isEmpty() ? irType : localContext.getTypeRemapper().remapType(irType);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void remapTypes(LocalContext localContext, IrBody irBody) {
        if (localContext.getCapturedTypeParameterToTypeParameter().isEmpty()) {
            return;
        }
        RemapTypesKt.remapTypes(irBody, localContext.getTypeRemapper());
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: compiled from: LocalDeclarationsLowering.kt */
    @Metadata(d1 = {"\u0000ì\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\n\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010#\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\f\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0082\u0004\u0018\u00002\u00020\u0001:\u0001zBE\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\u0010\b\u0002\u0010\b\u001a\n\u0012\u0004\u0012\u00020\n\u0018\u00010\t\u0012\u0010\b\u0002\u0010\u000b\u001a\n\u0012\u0004\u0012\u00020\f\u0018\u00010\t¢\u0006\u0002\u0010\rJ\u0006\u00108\u001a\u000209J\b\u0010:\u001a\u000209H\u0002J\b\u0010;\u001a\u000209H\u0002J\u001a\u0010<\u001a\b\u0012\u0004\u0012\u00020>0=2\n\u0010?\u001a\u00060 R\u00020!H\u0002J\u0010\u0010@\u001a\u0002092\u0006\u0010A\u001a\u00020%H\u0002J\u0018\u0010B\u001a\u00020C2\u0006\u0010D\u001a\u00020E2\u0006\u0010F\u001a\u00020\fH\u0002J\u0018\u0010B\u001a\u00020G2\u0006\u0010D\u001a\u00020H2\u0006\u0010F\u001a\u00020\u001bH\u0002J\u0010\u0010I\u001a\u0002092\u0006\u0010J\u001a\u00020\u001cH\u0002JH\u0010K\u001a\u0012\u0012\u0004\u0012\u00020(0Lj\b\u0012\u0004\u0012\u00020(`M2\f\u0010N\u001a\b\u0012\u0004\u0012\u00020)0=2\u0006\u0010A\u001a\u00020O2\u0006\u0010P\u001a\u00020$2\u0006\u0010Q\u001a\u00020$2\b\b\u0002\u0010R\u001a\u00020SH\u0002J3\u0010T\u001a\u00020U2\u0006\u0010V\u001a\u00020\u000f2\f\u0010W\u001a\b\u0012\u0004\u0012\u00020\u000f0X2\u0012\u0010Y\u001a\u000e\u0012\u0004\u0012\u00020[\u0012\u0004\u0012\u00020\u000f0ZH\u0082\bJ\u0018\u0010\\\u001a\u00020U2\u0006\u0010]\u001a\u00020\u00052\u0006\u0010^\u001a\u00020\u0007H\u0002J\b\u0010_\u001a\u000209H\u0002J\u0006\u0010`\u001a\u000209J\u001c\u0010a\u001a\u0002092\u0006\u0010b\u001a\u00020\n2\n\u0010?\u001a\u00060 R\u00020!H\u0002J\b\u0010c\u001a\u000209H\u0002J\u001a\u0010d\u001a\u0002092\u0006\u0010e\u001a\u00020\u00032\b\u0010f\u001a\u0004\u0018\u00010OH\u0002J\u0010\u0010g\u001a\u00020\u000f2\u0006\u0010]\u001a\u00020hH\u0002J(\u0010i\u001a\u00020U2\u0006\u0010]\u001a\u00020j2\f\u0010W\u001a\b\u0012\u0004\u0012\u00020\u000f0X2\b\b\u0002\u0010R\u001a\u00020SH\u0002J\b\u0010k\u001a\u000209H\u0002J\f\u0010l\u001a\u00020S*\u00020(H\u0002J\f\u0010m\u001a\u00020S*\u00020(H\u0002J\f\u0010n\u001a\u00020S*\u00020(H\u0002J9\u0010o\u001a\u000209\"\u0004\b\u0000\u0010p\"\u0004\b\u0001\u0010q*\u000e\u0012\u0004\u0012\u0002Hp\u0012\u0004\u0012\u0002Hq0\u001a2\u0006\u0010r\u001a\u0002Hp2\u0006\u0010s\u001a\u0002HqH\u0002¢\u0006\u0002\u0010tJ\u0014\u0010u\u001a\u000209*\u00020$2\u0006\u0010f\u001a\u00020vH\u0002J\u0018\u0010w\u001a\u000209*\u0006\u0012\u0002\b\u00030x2\u0006\u0010y\u001a\u00020$H\u0002R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082D¢\u0006\u0002\n\u0000R\u0019\u0010\b\u001a\n\u0012\u0004\u0012\u00020\n\u0018\u00010\t¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0019\u0010\u000b\u001a\n\u0012\u0004\u0012\u00020\f\u0018\u00010\t¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0011R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u001d\u0010\u0019\u001a\u000e\u0012\u0004\u0012\u00020\u001b\u0012\u0004\u0012\u00020\u001c0\u001a¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001eR!\u0010\u001f\u001a\u0012\u0012\u0004\u0012\u00020\n\u0012\b\u0012\u00060 R\u00020!0\u001a¢\u0006\b\n\u0000\u001a\u0004\b\"\u0010\u001eR\u001d\u0010#\u001a\u000e\u0012\u0004\u0012\u00020$\u0012\u0004\u0012\u00020%0\u001a¢\u0006\b\n\u0000\u001a\u0004\b&\u0010\u001eR\u001d\u0010'\u001a\u000e\u0012\u0004\u0012\u00020(\u0012\u0004\u0012\u00020)0\u001a¢\u0006\b\n\u0000\u001a\u0004\b*\u0010\u001eR\u001d\u0010+\u001a\u000e\u0012\u0004\u0012\u00020(\u0012\u0004\u0012\u00020(0\u001a¢\u0006\b\n\u0000\u001a\u0004\b,\u0010\u001eR\u001d\u0010-\u001a\u000e\u0012\u0004\u0012\u00020(\u0012\u0004\u0012\u00020(0\u001a¢\u0006\b\n\u0000\u001a\u0004\b.\u0010\u001eR\u001d\u0010/\u001a\u000e\u0012\u0004\u0012\u000200\u0012\u0004\u0012\u00020\u00050\u001a¢\u0006\b\n\u0000\u001a\u0004\b1\u0010\u001eR\u0018\u00102\u001a\u00020\u000f*\u00020(8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b3\u00104R\u0017\u00105\u001a\u0004\u0018\u00010$*\u00020$8F¢\u0006\u0006\u001a\u0004\b6\u00107¨\u0006{"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/LocalDeclarationsLowering$LocalDeclarationsTransformer;", "", "irElement", "Lorg/jetbrains/kotlin/ir/IrElement;", "container", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "closestParent", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;", "classesToLower", "", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "functionsToSkip", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "(Lorg/jetbrains/kotlin/backend/common/lower/LocalDeclarationsLowering;Lorg/jetbrains/kotlin/ir/IrElement;Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;Ljava/util/Set;Ljava/util/Set;)V", "CAPTURED_RECEIVER_PREFIX", "", "getClassesToLower", "()Ljava/util/Set;", "getClosestParent", "()Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;", "getContainer", "()Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "getFunctionsToSkip", "getIrElement", "()Lorg/jetbrains/kotlin/ir/IrElement;", "localClassConstructors", "", "Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "Lorg/jetbrains/kotlin/backend/common/lower/LocalDeclarationsLowering$LocalClassConstructorContext;", "getLocalClassConstructors", "()Ljava/util/Map;", "localClasses", "Lorg/jetbrains/kotlin/backend/common/lower/LocalDeclarationsLowering$LocalClassContext;", "Lorg/jetbrains/kotlin/backend/common/lower/LocalDeclarationsLowering;", "getLocalClasses", "localFunctions", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "Lorg/jetbrains/kotlin/backend/common/lower/LocalDeclarationsLowering$LocalFunctionContext;", "getLocalFunctions", "newParameterToCaptured", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "Lorg/jetbrains/kotlin/ir/symbols/IrValueSymbol;", "getNewParameterToCaptured", "newParameterToOld", "getNewParameterToOld", "oldParameterToNew", "getOldParameterToNew", "transformedDeclarations", "Lorg/jetbrains/kotlin/ir/declarations/IrSymbolOwner;", "getTransformedDeclarations", "parentNameSuffixForExtensionReceiver", "getParentNameSuffixForExtensionReceiver", "(Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;)Ljava/lang/String;", "transformed", "getTransformed", "(Lorg/jetbrains/kotlin/ir/declarations/IrFunction;)Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "cacheLocalConstructors", "", "collectClosureForLocalDeclarations", "collectLocalDeclarations", "createFieldsForCapturedValues", "", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "localClassContext", "createLiftedDeclaration", "localFunctionContext", "createNewCall", "Lorg/jetbrains/kotlin/ir/expressions/impl/IrCallImpl;", "oldCall", "Lorg/jetbrains/kotlin/ir/expressions/IrCall;", "newCallee", "Lorg/jetbrains/kotlin/ir/expressions/impl/IrConstructorCallImpl;", "Lorg/jetbrains/kotlin/ir/expressions/IrConstructorCall;", "createTransformedConstructorDeclaration", "constructorContext", "createTransformedValueParameters", "Ljava/util/ArrayList;", "Lkotlin/collections/ArrayList;", "capturedValues", "Lorg/jetbrains/kotlin/backend/common/lower/LocalDeclarationsLowering$LocalContext;", "oldDeclaration", "newDeclaration", "isExplicitLocalFunction", "", "findFirstUnusedName", "Lorg/jetbrains/kotlin/name/Name;", "initialName", "usedNames", "", "nextName", "Lkotlin/Function1;", "", "generateNameForLiftedDeclaration", "declaration", "newOwner", "insertLoweredDeclarationForLocalFunctions", "lowerLocalDeclarations", "rewriteClassMembers", "irClass", "rewriteDeclarations", "rewriteFunctionBody", "irDeclaration", "localContext", "suggestLocalName", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationWithName;", "suggestNameForCapturedValue", "Lorg/jetbrains/kotlin/ir/declarations/IrValueDeclaration;", "transformDeclarations", "isCapturedReceiver", "isDispatchReceiver", "isExtensionReceiver", "putAbsentOrSame", "K", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, ASTExpr.DEFAULT_MAP_KEY_NAME, "value", "(Ljava/util/Map;Ljava/lang/Object;Ljava/lang/Object;)V", "recordTransformedValueParameters", "Lorg/jetbrains/kotlin/backend/common/lower/LocalDeclarationsLowering$LocalContextWithClosureAsParameters;", "setLocalTypeArguments", "Lorg/jetbrains/kotlin/ir/expressions/IrMemberAccessExpression;", "callee", "FunctionBodiesRewriter", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    final class LocalDeclarationsTransformer {
        private final String CAPTURED_RECEIVER_PREFIX;
        private final Set<IrClass> classesToLower;
        private final IrDeclarationParent closestParent;
        private final IrDeclaration container;
        private final Set<IrSimpleFunction> functionsToSkip;
        private final IrElement irElement;
        private final Map<IrConstructor, LocalClassConstructorContext> localClassConstructors;
        private final Map<IrClass, LocalClassContext> localClasses;
        private final Map<IrFunction, LocalFunctionContext> localFunctions;
        private final Map<IrValueParameter, IrValueSymbol> newParameterToCaptured;
        private final Map<IrValueParameter, IrValueParameter> newParameterToOld;
        private final Map<IrValueParameter, IrValueParameter> oldParameterToNew;
        final /* synthetic */ LocalDeclarationsLowering this$0;
        private final Map<IrSymbolOwner, IrDeclaration> transformedDeclarations;

        /* JADX WARN: Multi-variable type inference failed */
        public LocalDeclarationsTransformer(LocalDeclarationsLowering localDeclarationsLowering, IrElement irElement, IrDeclaration container, IrDeclarationParent irDeclarationParent, Set<? extends IrClass> set, Set<? extends IrSimpleFunction> set2) {
            Intrinsics.checkNotNullParameter(irElement, "irElement");
            Intrinsics.checkNotNullParameter(container, "container");
            this.this$0 = localDeclarationsLowering;
            this.irElement = irElement;
            this.container = container;
            this.closestParent = irDeclarationParent;
            this.classesToLower = set;
            this.functionsToSkip = set2;
            this.localFunctions = new LinkedHashMap();
            this.localClasses = new LinkedHashMap();
            this.localClassConstructors = new LinkedHashMap();
            this.transformedDeclarations = new LinkedHashMap();
            this.newParameterToOld = new LinkedHashMap();
            this.oldParameterToNew = new LinkedHashMap();
            this.newParameterToCaptured = new LinkedHashMap();
            this.CAPTURED_RECEIVER_PREFIX = AsmUtil.LABELED_THIS_PARAMETER;
        }

        public /* synthetic */ LocalDeclarationsTransformer(LocalDeclarationsLowering localDeclarationsLowering, IrElement irElement, IrDeclaration irDeclaration, IrDeclarationParent irDeclarationParent, Set set, Set set2, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(localDeclarationsLowering, irElement, irDeclaration, (i & 4) != 0 ? null : irDeclarationParent, (i & 8) != 0 ? null : set, (i & 16) != 0 ? null : set2);
        }

        public final IrDeclarationParent getClosestParent() {
            return this.closestParent;
        }

        public final IrDeclaration getContainer() {
            return this.container;
        }

        public final IrElement getIrElement() {
            return this.irElement;
        }

        public final Set<IrClass> getClassesToLower() {
            return this.classesToLower;
        }

        public final Set<IrSimpleFunction> getFunctionsToSkip() {
            return this.functionsToSkip;
        }

        public final Map<IrFunction, LocalFunctionContext> getLocalFunctions() {
            return this.localFunctions;
        }

        public final Map<IrClass, LocalClassContext> getLocalClasses() {
            return this.localClasses;
        }

        public final Map<IrConstructor, LocalClassConstructorContext> getLocalClassConstructors() {
            return this.localClassConstructors;
        }

        public final Map<IrSymbolOwner, IrDeclaration> getTransformedDeclarations() {
            return this.transformedDeclarations;
        }

        public final IrFunction getTransformed(IrFunction irFunction) {
            Intrinsics.checkNotNullParameter(irFunction, "<this>");
            return (IrFunction) this.transformedDeclarations.get(irFunction);
        }

        public final Map<IrValueParameter, IrValueParameter> getNewParameterToOld() {
            return this.newParameterToOld;
        }

        public final Map<IrValueParameter, IrValueParameter> getOldParameterToNew() {
            return this.oldParameterToNew;
        }

        public final Map<IrValueParameter, IrValueSymbol> getNewParameterToCaptured() {
            return this.newParameterToCaptured;
        }

        public final void cacheLocalConstructors() {
            collectLocalDeclarations();
            collectClosureForLocalDeclarations();
            Iterator<T> it = this.localClassConstructors.values().iterator();
            while (it.hasNext()) {
                createTransformedConstructorDeclaration((LocalClassConstructorContext) it.next());
            }
        }

        public final void lowerLocalDeclarations() {
            collectLocalDeclarations();
            if (this.localFunctions.isEmpty() && this.localClasses.isEmpty()) {
                return;
            }
            collectClosureForLocalDeclarations();
            transformDeclarations();
            rewriteDeclarations();
            insertLoweredDeclarationForLocalFunctions();
            Function1 function1 = this.this$0.postLocalDeclarationLoweringCallback;
            if (function1 != null) {
                function1.invoke(new IntermediateDatastructures(this.localFunctions, this.newParameterToOld, this.newParameterToCaptured));
            }
        }

        private final void insertLoweredDeclarationForLocalFunctions() {
            Collection<LocalFunctionContext> collectionValues = this.localFunctions.values();
            LocalDeclarationsLowering localDeclarationsLowering = this.this$0;
            for (LocalFunctionContext localFunctionContext : collectionValues) {
                IrSimpleFunction transformedDeclaration = localFunctionContext.getTransformedDeclaration();
                IrSimpleFunction declaration = localFunctionContext.getDeclaration();
                transformedDeclaration.setBody(declaration.getBody());
                IrBody body = transformedDeclaration.getBody();
                if (body != null) {
                    localDeclarationsLowering.remapTypes(localFunctionContext, body);
                }
                List<IrValueParameter> valueParameters = declaration.getValueParameters();
                ArrayList<IrValueParameter> arrayList = new ArrayList();
                for (Object obj : valueParameters) {
                    if (((IrValueParameter) obj).getDefaultValue() != null) {
                        arrayList.add(obj);
                    }
                }
                for (IrValueParameter irValueParameter : arrayList) {
                    IrExpressionBody defaultValue = irValueParameter.getDefaultValue();
                    Intrinsics.checkNotNull(defaultValue);
                    localDeclarationsLowering.remapTypes(localFunctionContext, defaultValue);
                    IrValueParameter irValueParameter2 = this.oldParameterToNew.get(irValueParameter);
                    Intrinsics.checkNotNull(irValueParameter2);
                    irValueParameter2.setDefaultValue(defaultValue);
                }
                transformedDeclaration.acceptChildren(SetDeclarationsParentVisitor.INSTANCE, transformedDeclaration);
                localFunctionContext.getOwnerForLoweredDeclaration().addChild(localFunctionContext.getTransformedDeclaration());
            }
        }

        /* JADX INFO: compiled from: LocalDeclarationsLowering.kt */
        @Metadata(d1 = {"\u0000\u0088\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0082\u0004\u0018\u00002\u00020\u0001B\u000f\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0010\u0010\u000f\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u0010H\u0016J\u0010\u0010\u0011\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0012H\u0016J\u0010\u0010\u0013\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u0014H\u0016J\u0010\u0010\u0015\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0016H\u0016J\u0010\u0010\u0017\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0018H\u0016J\u0010\u0010\u0019\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u001aH\u0016J\u0010\u0010\u001b\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u001cH\u0016J\u0010\u0010\u001d\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u001eH\u0016J\u0010\u0010\u001f\u001a\u00020\f2\u0006\u0010\r\u001a\u00020 H\u0016J\u0012\u0010!\u001a\u0004\u0018\u00010\f2\u0006\u0010\r\u001a\u00020\"H\u0002J\u0010\u0010#\u001a\u00020\b2\u0006\u0010\t\u001a\u00020$H\u0016J\u0010\u0010%\u001a\u00020\b2\u0006\u0010\t\u001a\u00020&H\u0016J3\u0010'\u001a\u0002H(\"\f\b\u0000\u0010(*\u0006\u0012\u0002\b\u00030)*\u0002H(2\n\u0010*\u001a\u0006\u0012\u0002\b\u00030)2\u0006\u0010+\u001a\u00020\u001aH\u0002¢\u0006\u0002\u0010,JA\u0010-\u001a\u0002H(\"\f\b\u0000\u0010(*\u0006\u0012\u0002\b\u00030)*\u0002H(2\u0006\u0010+\u001a\u00020\u001a2\u0014\u0010.\u001a\u0010\u0012\u0004\u0012\u000200\u0012\u0006\u0012\u0004\u0018\u00010\b0/H\u0086\bø\u0001\u0000¢\u0006\u0002\u00101R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u00062"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/LocalDeclarationsLowering$LocalDeclarationsTransformer$FunctionBodiesRewriter;", "Lorg/jetbrains/kotlin/ir/visitors/IrElementTransformerVoid;", "localContext", "Lorg/jetbrains/kotlin/backend/common/lower/LocalDeclarationsLowering$LocalContext;", "(Lorg/jetbrains/kotlin/backend/common/lower/LocalDeclarationsLowering$LocalDeclarationsTransformer;Lorg/jetbrains/kotlin/backend/common/lower/LocalDeclarationsLowering$LocalContext;)V", "getLocalContext", "()Lorg/jetbrains/kotlin/backend/common/lower/LocalDeclarationsLowering$LocalContext;", "visitCall", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrCall;", "visitClass", "Lorg/jetbrains/kotlin/ir/IrStatement;", "declaration", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "visitConstructor", "Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "visitConstructorCall", "Lorg/jetbrains/kotlin/ir/expressions/IrConstructorCall;", "visitDeclaration", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationBase;", "visitDeclarationReference", "Lorg/jetbrains/kotlin/ir/expressions/IrDeclarationReference;", "visitDelegatingConstructorCall", "Lorg/jetbrains/kotlin/ir/expressions/IrDelegatingConstructorCall;", "visitFunction", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "visitFunctionReference", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionReference;", "visitGetValue", "Lorg/jetbrains/kotlin/ir/expressions/IrGetValue;", "visitLocalDelegatedProperty", "Lorg/jetbrains/kotlin/ir/declarations/IrLocalDelegatedProperty;", "visitMember", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "visitReturn", "Lorg/jetbrains/kotlin/ir/expressions/IrReturn;", "visitSetValue", "Lorg/jetbrains/kotlin/ir/expressions/IrSetValue;", "fillArguments2", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/ir/expressions/IrMemberAccessExpression;", "oldExpression", "newTarget", "(Lorg/jetbrains/kotlin/ir/expressions/IrMemberAccessExpression;Lorg/jetbrains/kotlin/ir/expressions/IrMemberAccessExpression;Lorg/jetbrains/kotlin/ir/declarations/IrFunction;)Lorg/jetbrains/kotlin/ir/expressions/IrMemberAccessExpression;", "mapValueParameters", "transform", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "(Lorg/jetbrains/kotlin/ir/expressions/IrMemberAccessExpression;Lorg/jetbrains/kotlin/ir/declarations/IrFunction;Lkotlin/jvm/functions/Function1;)Lorg/jetbrains/kotlin/ir/expressions/IrMemberAccessExpression;", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
        private final class FunctionBodiesRewriter extends IrElementTransformerVoid {
            private final LocalContext localContext;

            public FunctionBodiesRewriter(LocalContext localContext) {
                this.localContext = localContext;
            }

            public final LocalContext getLocalContext() {
                return this.localContext;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
            public IrStatement visitLocalDelegatedProperty(IrLocalDelegatedProperty declaration) {
                Intrinsics.checkNotNullParameter(declaration, "declaration");
                return IrElementsKt.transformStatement(declaration.getDelegate(), this);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
            public IrStatement visitClass(IrClass declaration) {
                IrClass declaration2;
                Intrinsics.checkNotNullParameter(declaration, "declaration");
                LocalClassContext localClassContext = LocalDeclarationsTransformer.this.getLocalClasses().get(declaration);
                if (localClassContext != null && (declaration2 = localClassContext.getDeclaration()) != null) {
                    return declaration2;
                }
                IrStatement irStatementVisitMember = visitMember(declaration);
                return irStatementVisitMember == null ? super.visitClass(declaration) : irStatementVisitMember;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
            public IrStatement visitFunction(IrFunction declaration) {
                Intrinsics.checkNotNullParameter(declaration, "declaration");
                if (LocalDeclarationsTransformer.this.getLocalFunctions().containsKey(declaration)) {
                    return new IrCompositeImpl(declaration.getStartOffset(), declaration.getEndOffset(), LocalDeclarationsTransformer.this.this$0.getContext().getIrBuiltIns().getUnitType(), null, 8, null);
                }
                IrStatement irStatementVisitMember = visitMember(declaration);
                return irStatementVisitMember == null ? super.visitFunction(declaration) : irStatementVisitMember;
            }

            private final IrStatement visitMember(IrDeclaration declaration) {
                if (!(this.localContext instanceof LocalClassContext) || !Intrinsics.areEqual(declaration.getParent(), ((LocalClassContext) this.localContext).getDeclaration())) {
                    return null;
                }
                IrElementTransformerVoidKt.transformChildrenVoid(declaration, LocalDeclarationsTransformer.this.new FunctionBodiesRewriter(new LocalClassMemberContext(declaration, (LocalClassContext) this.localContext)));
                return declaration;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
            public IrStatement visitConstructor(IrConstructor declaration) {
                Intrinsics.checkNotNullParameter(declaration, "declaration");
                LocalClassConstructorContext localClassConstructorContext = LocalDeclarationsTransformer.this.getLocalClassConstructors().get(declaration);
                if (localClassConstructorContext == null) {
                    return super.visitConstructor(declaration);
                }
                IrConstructor transformedDeclaration = localClassConstructorContext.getTransformedDeclaration();
                LocalDeclarationsTransformer localDeclarationsTransformer = LocalDeclarationsTransformer.this;
                IrBody body = declaration.getBody();
                Intrinsics.checkNotNull(body);
                transformedDeclaration.setBody(body);
                List<IrValueParameter> valueParameters = declaration.getValueParameters();
                ArrayList<IrValueParameter> arrayList = new ArrayList();
                for (Object obj : valueParameters) {
                    if (((IrValueParameter) obj).getDefaultValue() != null) {
                        arrayList.add(obj);
                    }
                }
                for (IrValueParameter irValueParameter : arrayList) {
                    IrValueParameter irValueParameter2 = localDeclarationsTransformer.getOldParameterToNew().get(irValueParameter);
                    Intrinsics.checkNotNull(irValueParameter2);
                    irValueParameter2.setDefaultValue(irValueParameter.getDefaultValue());
                }
                transformedDeclaration.acceptChildren(SetDeclarationsParentVisitor.INSTANCE, transformedDeclaration);
                return transformedDeclaration;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
            public IrExpression visitGetValue(IrGetValue expression) {
                IrExpression irExpressionIrGet;
                Intrinsics.checkNotNullParameter(expression, "expression");
                IrValueDeclaration owner = expression.getSymbol().getOwner();
                LocalContext localContext = this.localContext;
                if (localContext != null && (irExpressionIrGet = localContext.irGet(expression.getStartOffset(), expression.getEndOffset(), owner)) != null) {
                    return irExpressionIrGet;
                }
                IrValueParameter irValueParameter = LocalDeclarationsTransformer.this.getOldParameterToNew().get(owner);
                return irValueParameter != null ? new IrGetValueImpl(expression.getStartOffset(), expression.getEndOffset(), irValueParameter.getType(), irValueParameter.getSymbol(), null, 16, null) : expression;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
            public IrExpression visitSetValue(IrSetValue expression) {
                Intrinsics.checkNotNullParameter(expression, "expression");
                IrElementTransformerVoidKt.transformChildrenVoid(expression, this);
                IrValueParameter irValueParameter = LocalDeclarationsTransformer.this.getOldParameterToNew().get(expression.getSymbol().getOwner());
                if (irValueParameter != null) {
                    return new IrSetValueImpl(expression.getStartOffset(), expression.getEndOffset(), irValueParameter.getType(), irValueParameter.getSymbol(), expression.getValue(), expression.getOrigin());
                }
                return expression;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
            public IrExpression visitCall(IrCall expression) {
                Intrinsics.checkNotNullParameter(expression, "expression");
                IrElementTransformerVoidKt.transformChildrenVoid(expression, this);
                IrFunction transformed = LocalDeclarationsTransformer.this.getTransformed(expression.getSymbol().getOwner());
                if (transformed == null) {
                    return expression;
                }
                IrSimpleFunction irSimpleFunction = (IrSimpleFunction) transformed;
                return fillArguments2(LocalDeclarationsTransformer.this.createNewCall(expression, irSimpleFunction), expression, irSimpleFunction);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
            public IrExpression visitConstructorCall(IrConstructorCall expression) {
                Intrinsics.checkNotNullParameter(expression, "expression");
                IrElementTransformerVoidKt.transformChildrenVoid(expression, this);
                IrFunction transformed = LocalDeclarationsTransformer.this.getTransformed(expression.getSymbol().getOwner());
                if (transformed == null) {
                    return expression;
                }
                IrConstructor irConstructor = (IrConstructor) transformed;
                return fillArguments2(LocalDeclarationsTransformer.this.createNewCall(expression, irConstructor), expression, irConstructor);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
            public IrExpression visitDelegatingConstructorCall(IrDelegatingConstructorCall expression) {
                Intrinsics.checkNotNullParameter(expression, "expression");
                IrElementTransformerVoidKt.transformChildrenVoid(expression, this);
                IrConstructor irConstructor = (IrConstructor) LocalDeclarationsTransformer.this.getTransformedDeclarations().get(expression.getSymbol().getOwner());
                if (irConstructor == null) {
                    return expression;
                }
                IrDelegatingConstructorCallImpl irDelegatingConstructorCallImpl = new IrDelegatingConstructorCallImpl(expression.getStartOffset(), expression.getEndOffset(), LocalDeclarationsTransformer.this.this$0.getContext().getIrBuiltIns().getUnitType(), irConstructor.getSymbol(), expression.getTypeArgumentsCount(), irConstructor.getValueParameters().size());
                IrDelegatingConstructorCallImpl irDelegatingConstructorCallImpl2 = irDelegatingConstructorCallImpl;
                IrDelegatingConstructorCall irDelegatingConstructorCall = expression;
                fillArguments2(irDelegatingConstructorCallImpl2, irDelegatingConstructorCall, irConstructor);
                IrExpressionsKt.copyTypeArgumentsFrom$default(irDelegatingConstructorCallImpl2, irDelegatingConstructorCall, 0, 2, null);
                return irDelegatingConstructorCallImpl;
            }

            public final <T extends IrMemberAccessExpression<?>> T mapValueParameters(T t, IrFunction newTarget, Function1<? super IrValueParameter, ? extends IrExpression> transform) {
                Intrinsics.checkNotNullParameter(t, "<this>");
                Intrinsics.checkNotNullParameter(newTarget, "newTarget");
                Intrinsics.checkNotNullParameter(transform, "transform");
                for (IrValueParameter irValueParameter : newTarget.getValueParameters()) {
                    t.putValueArgument(irValueParameter.getIndex(), transform.invoke(irValueParameter));
                }
                return t;
            }

            private final <T extends IrMemberAccessExpression<?>> T fillArguments2(T t, IrMemberAccessExpression<?> irMemberAccessExpression, IrFunction irFunction) {
                IrGetValueImpl irGetValueImpl;
                IrExpression irExpressionIrGet;
                LocalDeclarationsTransformer localDeclarationsTransformer = LocalDeclarationsTransformer.this;
                for (IrValueParameter irValueParameter : irFunction.getValueParameters()) {
                    int index = irValueParameter.getIndex();
                    IrValueParameter irValueParameter2 = localDeclarationsTransformer.getNewParameterToOld().get(irValueParameter);
                    if (irValueParameter2 != null) {
                        irGetValueImpl = irMemberAccessExpression.getValueArgument(irValueParameter2.getIndex());
                    } else {
                        IrValueSymbol irValueSymbol = localDeclarationsTransformer.getNewParameterToCaptured().get(irValueParameter);
                        if (irValueSymbol == null) {
                            throw new AssertionError("Non-mapped parameter " + irValueParameter);
                        }
                        IrValueParameter owner = irValueSymbol.getOwner();
                        LocalContext localContext = this.localContext;
                        if (localContext == null || (irExpressionIrGet = localContext.irGet(irMemberAccessExpression.getStartOffset(), irMemberAccessExpression.getEndOffset(), owner)) == null) {
                            IrValueParameter irValueParameter3 = localDeclarationsTransformer.getOldParameterToNew().get(owner);
                            if (irValueParameter3 != null) {
                                owner = irValueParameter3;
                            }
                            irGetValueImpl = new IrGetValueImpl(irMemberAccessExpression.getStartOffset(), irMemberAccessExpression.getEndOffset(), owner.getSymbol(), null, 8, null);
                        } else {
                            irGetValueImpl = irExpressionIrGet;
                        }
                    }
                    t.putValueArgument(index, irGetValueImpl);
                }
                t.setDispatchReceiver(irMemberAccessExpression.getDispatchReceiver());
                t.setExtensionReceiver(irMemberAccessExpression.getExtensionReceiver());
                return t;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
            public IrExpression visitFunctionReference(IrFunctionReference expression) {
                IrFunction transformed;
                List<IrTypeParameter> typeParameters;
                Intrinsics.checkNotNullParameter(expression, "expression");
                IrElementTransformerVoidKt.transformChildrenVoid(expression, this);
                IrFunction owner = expression.getSymbol().getOwner();
                IrFunction transformed2 = LocalDeclarationsTransformer.this.getTransformed(owner);
                if (transformed2 == null) {
                    return expression;
                }
                IrFunctionSymbol reflectionTarget = expression.getReflectionTarget();
                if (reflectionTarget != null) {
                    transformed = LocalDeclarationsTransformer.this.getTransformed(reflectionTarget.getOwner());
                    if (transformed == null) {
                        transformed = reflectionTarget.getOwner();
                    }
                } else {
                    transformed = null;
                }
                if (transformed2 instanceof IrConstructor) {
                    typeParameters = IrUtilsKt.getParentAsClass(transformed2).getTypeParameters();
                } else {
                    typeParameters = transformed2.getTypeParameters();
                }
                IrFunctionReferenceImpl irFunctionReferenceImpl = new IrFunctionReferenceImpl(expression.getStartOffset(), expression.getEndOffset(), expression.getType(), transformed2.getSymbol(), typeParameters.size(), transformed2.getValueParameters().size(), transformed != null ? transformed.getSymbol() : null, expression.getOrigin());
                LocalDeclarationsTransformer localDeclarationsTransformer = LocalDeclarationsTransformer.this;
                IrFunctionReferenceImpl irFunctionReferenceImpl2 = irFunctionReferenceImpl;
                IrFunctionReference irFunctionReference = expression;
                fillArguments2(irFunctionReferenceImpl2, irFunctionReference, transformed2);
                localDeclarationsTransformer.setLocalTypeArguments(irFunctionReferenceImpl2, owner);
                IrExpressionsKt.copyTypeArgumentsFrom(irFunctionReferenceImpl2, irFunctionReference, typeParameters.size() - expression.getTypeArgumentsCount());
                IrDeclarationsKt.copyAttributes(irFunctionReferenceImpl, expression);
                return irFunctionReferenceImpl;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
            public IrExpression visitReturn(IrReturn expression) {
                IrFunction transformed;
                Intrinsics.checkNotNullParameter(expression, "expression");
                IrElementTransformerVoidKt.transformChildrenVoid(expression, this);
                IrReturnTarget owner = expression.getReturnTargetSymbol().getOwner();
                IrFunction irFunction = owner instanceof IrFunction ? (IrFunction) owner : null;
                if (irFunction != null && (transformed = LocalDeclarationsTransformer.this.getTransformed(irFunction)) != null) {
                    return new IrReturnImpl(expression.getStartOffset(), expression.getEndOffset(), LocalDeclarationsTransformer.this.this$0.getContext().getIrBuiltIns().getNothingType(), transformed.getSymbol(), expression.getValue());
                }
                return expression;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
            public IrExpression visitDeclarationReference(IrDeclarationReference expression) {
                Intrinsics.checkNotNullParameter(expression, "expression");
                if (LocalDeclarationsTransformer.this.getTransformedDeclarations().containsKey(expression.getSymbol().getOwner())) {
                    throw new NotImplementedError(null, 1, null);
                }
                return super.visitDeclarationReference(expression);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
            public IrStatement visitDeclaration(IrDeclarationBase declaration) {
                Intrinsics.checkNotNullParameter(declaration, "declaration");
                if (LocalDeclarationsTransformer.this.getTransformedDeclarations().containsKey(declaration)) {
                    throw new NotImplementedError(null, 1, null);
                }
                return super.visitDeclaration(declaration);
            }
        }

        private final void rewriteFunctionBody(IrElement irDeclaration, LocalContext localContext) {
            IrElementTransformerVoidKt.transformChildrenVoid(irDeclaration, new FunctionBodiesRewriter(localContext));
        }

        private final void rewriteClassMembers(IrClass irClass, LocalClassContext localClassContext) {
            IrSetFieldImpl irSetFieldImpl;
            List<IrDeclaration> declarations = irClass.getDeclarations();
            ArrayList arrayList = new ArrayList();
            for (Object obj : declarations) {
                if (obj instanceof IrConstructor) {
                    arrayList.add(obj);
                }
            }
            IrElementTransformerVoidKt.transformChildrenVoid(irClass, new FunctionBodiesRewriter(localClassContext));
            Sequence map = SequencesKt.map(CollectionsKt.asSequence(arrayList), new Function1<IrConstructor, LocalClassConstructorContext>() { // from class: org.jetbrains.kotlin.backend.common.lower.LocalDeclarationsLowering$LocalDeclarationsTransformer$rewriteClassMembers$constructorsByDelegationKinds$1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final LocalDeclarationsLowering.LocalClassConstructorContext invoke(IrConstructor it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    LocalDeclarationsLowering.LocalClassConstructorContext localClassConstructorContext = this.this$0.getLocalClassConstructors().get(it);
                    Intrinsics.checkNotNull(localClassConstructorContext);
                    return localClassConstructorContext;
                }
            });
            LocalDeclarationsLowering localDeclarationsLowering = this.this$0;
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            for (Object obj2 : map) {
                ConstructorDelegationKind constructorDelegationKindDelegationKind = LowerUtilsKt.delegationKind(((LocalClassConstructorContext) obj2).getDeclaration(), localDeclarationsLowering.getContext().getIrBuiltIns());
                Object obj3 = linkedHashMap.get(constructorDelegationKindDelegationKind);
                if (obj3 == null) {
                    obj3 = (List) new ArrayList();
                    linkedHashMap.put(constructorDelegationKindDelegationKind, obj3);
                }
                ((List) obj3).add(obj2);
            }
            List<LocalClassConstructorContext> listEmptyList = (List) linkedHashMap.get(ConstructorDelegationKind.CALLS_SUPER);
            if (listEmptyList == null) {
                listEmptyList = CollectionsKt.emptyList();
            }
            if (listEmptyList.isEmpty()) {
                linkedHashMap.get(ConstructorDelegationKind.PARTIAL_LINKAGE_ERROR);
            }
            List<IrField> listCreateFieldsForCapturedValues = createFieldsForCapturedValues(localClassContext);
            CollectionsKt.addAll(irClass.getDeclarations(), listCreateFieldsForCapturedValues);
            Mapping.Delegate<IrClass, Collection<IrField>> capturedFields = this.this$0.getContext().getMapping().getCapturedFields();
            IrClass irClass2 = irClass;
            List listEmptyList2 = this.this$0.getContext().getMapping().getCapturedFields().get(irClass2);
            if (listEmptyList2 == null) {
                listEmptyList2 = CollectionsKt.emptyList();
            }
            capturedFields.set(irClass2, CollectionsKt.plus((Collection) listEmptyList2, (Iterable) listCreateFieldsForCapturedValues));
            for (LocalClassConstructorContext localClassConstructorContext : listEmptyList) {
                IrBody body = localClassConstructorContext.getDeclaration().getBody();
                IrBlockBody irBlockBody = body instanceof IrBlockBody ? (IrBlockBody) body : null;
                if (irBlockBody == null) {
                    throw new AssertionError("Unexpected constructor body: " + localClassConstructorContext.getDeclaration().getBody());
                }
                List<IrStatement> statements = irBlockBody.getStatements();
                Map<IrValueDeclaration, PotentiallyUnusedField> capturedValueToField = localClassContext.getCapturedValueToField();
                LocalDeclarationsLowering localDeclarationsLowering2 = this.this$0;
                ArrayList arrayList2 = new ArrayList();
                for (Map.Entry<IrValueDeclaration, PotentiallyUnusedField> entry : capturedValueToField.entrySet()) {
                    IrValueDeclaration key = entry.getKey();
                    IrFieldSymbol symbolIfUsed = entry.getValue().getSymbolIfUsed();
                    if (symbolIfUsed == null) {
                        irSetFieldImpl = null;
                    } else {
                        IrValueParameter thisReceiver = irClass.getThisReceiver();
                        Intrinsics.checkNotNull(thisReceiver);
                        IrGetValueImpl irGetValueImpl = new IrGetValueImpl(-1, -1, thisReceiver.getSymbol(), null, 8, null);
                        IrExpression irExpressionIrGet = localClassConstructorContext.irGet(-1, -1, key);
                        Intrinsics.checkNotNull(irExpressionIrGet);
                        irSetFieldImpl = new IrSetFieldImpl(-1, -1, symbolIfUsed, irGetValueImpl, irExpressionIrGet, localDeclarationsLowering2.getContext().getIrBuiltIns().getUnitType(), LoweredStatementOrigins.STATEMENT_ORIGIN_INITIALIZER_OF_FIELD_FOR_CAPTURED_VALUE.INSTANCE, null, 128, null);
                    }
                    if (irSetFieldImpl != null) {
                        arrayList2.add(irSetFieldImpl);
                    }
                }
                statements.addAll(0, arrayList2);
            }
        }

        private final void rewriteDeclarations() {
            for (LocalFunctionContext localFunctionContext : this.localFunctions.values()) {
                rewriteFunctionBody(localFunctionContext.getDeclaration(), localFunctionContext);
            }
            for (LocalClassConstructorContext localClassConstructorContext : this.localClassConstructors.values()) {
                LocalClassConstructorContext localClassConstructorContext2 = localClassConstructorContext;
                rewriteFunctionBody(localClassConstructorContext.getDeclaration(), localClassConstructorContext2);
                if (localClassConstructorContext.getDeclaration().getIsPrimary()) {
                    List<IrDeclaration> declarations = AdditionalIrUtilsKt.getConstructedClass(localClassConstructorContext.getDeclaration()).getDeclarations();
                    ArrayList arrayList = new ArrayList();
                    for (Object obj : declarations) {
                        if (obj instanceof IrAnonymousInitializer) {
                            arrayList.add(obj);
                        }
                    }
                    Iterator it = arrayList.iterator();
                    while (it.hasNext()) {
                        rewriteFunctionBody((IrAnonymousInitializer) it.next(), localClassConstructorContext2);
                    }
                }
            }
            for (LocalClassContext localClassContext : this.localClasses.values()) {
                rewriteClassMembers(localClassContext.getDeclaration(), localClassContext);
            }
            rewriteFunctionBody(this.irElement, null);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final IrCallImpl createNewCall(IrCall oldCall, IrSimpleFunction newCallee) {
            IrCallImpl irCallImpl = new IrCallImpl(oldCall.getStartOffset(), oldCall.getEndOffset(), oldCall.getType(), newCallee.getSymbol(), newCallee.getTypeParameters().size(), newCallee.getValueParameters().size(), oldCall.getOrigin(), oldCall.getSuperQualifierSymbol());
            IrCallImpl irCallImpl2 = irCallImpl;
            setLocalTypeArguments(irCallImpl2, oldCall.getSymbol().getOwner());
            IrExpressionsKt.copyTypeArgumentsFrom(irCallImpl2, oldCall, newCallee.getTypeParameters().size() - oldCall.getTypeArgumentsCount());
            return irCallImpl;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final IrConstructorCallImpl createNewCall(IrConstructorCall oldCall, IrConstructor newCallee) {
            IrConstructorCallImpl irConstructorCallImplFromSymbolOwner = IrConstructorCallImpl.INSTANCE.fromSymbolOwner(oldCall.getStartOffset(), oldCall.getEndOffset(), oldCall.getType(), newCallee.getSymbol(), IrUtilsKt.getParentAsClass(newCallee).getTypeParameters().size(), oldCall.getOrigin());
            IrExpressionsKt.copyTypeArgumentsFrom$default(irConstructorCallImplFromSymbolOwner, oldCall, 0, 2, null);
            return irConstructorCallImplFromSymbolOwner;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void setLocalTypeArguments(IrMemberAccessExpression<?> irMemberAccessExpression, IrFunction irFunction) {
            LocalFunctionContext localFunctionContext = this.localFunctions.get(irFunction);
            if (localFunctionContext == null) {
                return;
            }
            for (Map.Entry<IrTypeParameter, IrTypeParameter> entry : localFunctionContext.getCapturedTypeParameterToTypeParameter().entrySet()) {
                irMemberAccessExpression.putTypeArgument(entry.getValue().getIndex(), IrTypesKt.getDefaultType(entry.getKey()));
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        private final void transformDeclarations() {
            Iterator<T> it = this.localFunctions.values().iterator();
            while (it.hasNext()) {
                createLiftedDeclaration((LocalFunctionContext) it.next());
            }
            Collection<LocalClassContext> collectionValues = this.localClasses.values();
            LocalDeclarationsLowering localDeclarationsLowering = this.this$0;
            for (LocalClassContext localClassContext : collectionValues) {
                localClassContext.getDeclaration().setVisibility(localDeclarationsLowering.getVisibilityPolicy().forClass(localClassContext.getDeclaration(), localClassContext.getInInlineFunctionScope()));
                List<IrValueSymbol> capturedValues = localClassContext.getClosure().getCapturedValues();
                Map<IrValueDeclaration, PotentiallyUnusedField> capturedValueToField = localClassContext.getCapturedValueToField();
                Iterator<T> it2 = capturedValues.iterator();
                while (it2.hasNext()) {
                    Pair pair = TuplesKt.to(((IrValueSymbol) it2.next()).getOwner(), new PotentiallyUnusedField());
                    capturedValueToField.put(pair.getFirst(), pair.getSecond());
                }
            }
            Iterator<T> it3 = this.localClassConstructors.values().iterator();
            while (it3.hasNext()) {
                createTransformedConstructorDeclaration((LocalClassConstructorContext) it3.next());
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX WARN: Code duplicated, block: B:22:0x0063  */
        public final String suggestLocalName(IrDeclarationWithName declaration) {
            String str;
            Function1<String, String> localNameSanitizer = this.this$0.getLocalNameSanitizer();
            String strAsString = declaration.getName().asString();
            Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
            String strInvoke = localNameSanitizer.invoke(strAsString);
            LocalFunctionContext localFunctionContext = this.localFunctions.get(declaration);
            if (localFunctionContext != null) {
                LocalDeclarationsLowering localDeclarationsLowering = this.this$0;
                String str2 = declaration.getName().isSpecial() ? "lambda" : strInvoke;
                if (localFunctionContext.getIndex() >= 0) {
                    if (!localDeclarationsLowering.getSuggestUniqueNames()) {
                        return str2;
                    }
                    if (localDeclarationsLowering.getCompatibilityModeForInlinedLocalDelegatedPropertyAccessors() && Intrinsics.areEqual(declaration.getOrigin(), IrDeclarationOrigin.DELEGATED_PROPERTY_ACCESSOR.INSTANCE)) {
                        IrDeclaration irDeclaration = this.container;
                        if ((irDeclaration instanceof IrFunction) && ((IrFunction) irDeclaration).getIsInline()) {
                            str = Parameters.DEFAULT_OPTION_PREFIXES;
                        } else {
                            str = "$";
                        }
                    } else {
                        str = "$";
                    }
                    return str2 + str + localFunctionContext.getIndex();
                }
            }
            return strInvoke;
        }

        private final Name generateNameForLiftedDeclaration(IrDeclaration declaration, final IrDeclarationParent newOwner) {
            List listReversed = CollectionsKt.reversed(SequencesKt.toList(SequencesKt.takeWhile(LocalDeclarationsLoweringKt.getParentsWithSelf(declaration), new Function1<IrDeclarationParent, Boolean>() { // from class: org.jetbrains.kotlin.backend.common.lower.LocalDeclarationsLowering$LocalDeclarationsTransformer$generateNameForLiftedDeclaration$parents$1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(IrDeclarationParent it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return Boolean.valueOf(!Intrinsics.areEqual(it, newOwner));
                }
            })));
            String strJoinToString$default = CollectionsKt.joinToString$default(listReversed, "$", null, null, 0, null, new Function1<IrDeclarationParent, CharSequence>() { // from class: org.jetbrains.kotlin.backend.common.lower.LocalDeclarationsLowering$LocalDeclarationsTransformer$generateNameForLiftedDeclaration$nameFromParents$1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final CharSequence invoke(IrDeclarationParent it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return this.this$0.suggestLocalName((IrDeclarationWithName) it);
                }
            }, 30, null);
            if (listReversed.size() == 1 && (declaration.getParent() instanceof IrClass)) {
                Name nameIdentifier = Name.identifier("_init_$" + strJoinToString$default);
                Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
                return nameIdentifier;
            }
            Name nameIdentifier2 = Name.identifier(strJoinToString$default);
            Intrinsics.checkNotNullExpressionValue(nameIdentifier2, "identifier(...)");
            return nameIdentifier2;
        }

        private final void createLiftedDeclaration(LocalFunctionContext localFunctionContext) {
            DescriptorVisibility descriptorVisibility;
            String str;
            IrSimpleFunction declaration = localFunctionContext.getDeclaration();
            if (declaration.getDispatchReceiverParameter() != null) {
                throw new AssertionError("local functions must not have dispatch receiver");
            }
            OwnerForLoweredDeclaration ownerForLoweredDeclaration = localFunctionContext.getOwnerForLoweredDeclaration();
            IrDeclarationParent irDeclarationContainer = ownerForLoweredDeclaration.getIrDeclarationContainer();
            Name nameGenerateNameForLiftedDeclaration = generateNameForLiftedDeclaration(declaration, irDeclarationContainer);
            Closure closure = localFunctionContext.getClosure();
            List<IrValueSymbol> listComponent1 = closure.component1();
            List<IrTypeParameter> listComponent2 = closure.component2();
            IrFactory irFactory = this.this$0.getContext().getIrFactory();
            IrFunctionBuilder irFunctionBuilder = new IrFunctionBuilder();
            IrSimpleFunction irSimpleFunction = declaration;
            irFunctionBuilder.updateFrom((IrFunction) irSimpleFunction);
            irFunctionBuilder.setName(nameGenerateNameForLiftedDeclaration);
            if (ownerForLoweredDeclaration.getIsLocal()) {
                descriptorVisibility = DescriptorVisibilities.LOCAL;
                str = "LOCAL";
            } else {
                descriptorVisibility = DescriptorVisibilities.PRIVATE;
                str = "PRIVATE";
            }
            Intrinsics.checkNotNullExpressionValue(descriptorVisibility, str);
            irFunctionBuilder.setVisibility(descriptorVisibility);
            irFunctionBuilder.setModality(Modality.FINAL);
            IrSimpleFunction irSimpleFunctionBuildFunction = DeclarationBuildersKt.buildFunction(irFactory, irFunctionBuilder);
            localFunctionContext.setTransformedDeclaration(irSimpleFunctionBuildFunction);
            IrSimpleFunction irSimpleFunction2 = irSimpleFunctionBuildFunction;
            List listCopyTypeParameters$default = IrUtilsKt.copyTypeParameters$default(irSimpleFunction2, listComponent2, null, null, 6, null);
            MapsKt.putAll(localFunctionContext.getCapturedTypeParameterToTypeParameter(), CollectionsKt.zip(listComponent2, listCopyTypeParameters$default));
            IrUtilsKt.copyTypeParametersFrom$default(irSimpleFunction2, declaration, null, localFunctionContext.getCapturedTypeParameterToTypeParameter(), 2, null);
            MapsKt.putAll(localFunctionContext.getCapturedTypeParameterToTypeParameter(), CollectionsKt.zip(declaration.getTypeParameters(), CollectionsKt.drop(irSimpleFunctionBuildFunction.getTypeParameters(), listCopyTypeParameters$default.size())));
            List<IrTypeParameter> listDrop = CollectionsKt.drop(irSimpleFunctionBuildFunction.getTypeParameters(), listCopyTypeParameters$default.size());
            LocalDeclarationsLowering localDeclarationsLowering = this.this$0;
            for (IrTypeParameter irTypeParameter : listDrop) {
                List<IrType> superTypes = irTypeParameter.getSuperTypes();
                ArrayList arrayList = new ArrayList(superTypes.size());
                Iterator<T> it = superTypes.iterator();
                while (it.hasNext()) {
                    arrayList.add(localDeclarationsLowering.remapType(localFunctionContext, (IrType) it.next()));
                }
                irTypeParameter.setSuperTypes(org.jetbrains.kotlin.utils.CollectionsKt.compactIfPossible(arrayList));
            }
            irSimpleFunctionBuildFunction.setParent(irDeclarationContainer);
            LocalFunctionContext localFunctionContext2 = localFunctionContext;
            irSimpleFunctionBuildFunction.setReturnType(this.this$0.remapType(localFunctionContext2, declaration.getReturnType()));
            IrValueParameter irValueParameterCopyTo$default = null;
            irSimpleFunctionBuildFunction.setDispatchReceiverParameter(null);
            IrValueParameter extensionReceiverParameter = declaration.getExtensionReceiverParameter();
            if (extensionReceiverParameter != null) {
                irValueParameterCopyTo$default = IrUtilsKt.copyTo$default(extensionReceiverParameter, irSimpleFunctionBuildFunction, null, 0, 0, 0, null, null, this.this$0.remapType(localFunctionContext2, extensionReceiverParameter.getType()), null, null, false, false, false, 8062, null);
                putAbsentOrSame(this.newParameterToOld, irValueParameterCopyTo$default, extensionReceiverParameter);
            }
            irSimpleFunctionBuildFunction.setExtensionReceiverParameter(irValueParameterCopyTo$default);
            IrDeclarationsKt.copyAttributes(irSimpleFunctionBuildFunction, declaration);
            IrSimpleFunction irSimpleFunction3 = irSimpleFunctionBuildFunction;
            irSimpleFunctionBuildFunction.setValueParameters(MemoryOptimizedCollectionUtilKt.memoryOptimizedPlus((List) irSimpleFunctionBuildFunction.getValueParameters(), (List) createTransformedValueParameters(listComponent1, localFunctionContext2, irSimpleFunction, irSimpleFunction3, Intrinsics.areEqual(declaration.getOrigin(), IrDeclarationOrigin.LOCAL_FUNCTION.INSTANCE))));
            recordTransformedValueParameters(irSimpleFunction3, localFunctionContext);
            Map mapCreateMapBuilder = MapsKt.createMapBuilder();
            IrValueParameter extensionReceiverParameter2 = declaration.getExtensionReceiverParameter();
            if (extensionReceiverParameter2 != null) {
                IrValueParameter extensionReceiverParameter3 = irSimpleFunctionBuildFunction.getExtensionReceiverParameter();
                Intrinsics.checkNotNull(extensionReceiverParameter3);
            }
            MapsKt.putAll(mapCreateMapBuilder, CollectionsKt.zip(declaration.getValueParameters(), CollectionsKt.takeLast(irSimpleFunctionBuildFunction.getValueParameters(), declaration.getValueParameters().size())));
            this.this$0.getContext().remapMultiFieldValueClassStructure(irSimpleFunction, irSimpleFunction3, MapsKt.build(mapCreateMapBuilder));
            irSimpleFunctionBuildFunction.setAnnotations(declaration.getAnnotations());
            this.transformedDeclarations.put(declaration, irSimpleFunctionBuildFunction);
        }

        static /* synthetic */ ArrayList createTransformedValueParameters$default(LocalDeclarationsTransformer localDeclarationsTransformer, List list, LocalContext localContext, IrFunction irFunction, IrFunction irFunction2, boolean z, int i, Object obj) {
            if ((i & 16) != 0) {
                z = false;
            }
            return localDeclarationsTransformer.createTransformedValueParameters(list, localContext, irFunction, irFunction2, z);
        }

        private final ArrayList<IrValueParameter> createTransformedValueParameters(List<? extends IrValueSymbol> capturedValues, LocalContext localFunctionContext, IrFunction oldDeclaration, IrFunction newDeclaration, boolean isExplicitLocalFunction) {
            IrValueParameter owner;
            IrValueParameter owner2;
            ArrayList<IrValueParameter> arrayList = new ArrayList<>(capturedValues.size() + oldDeclaration.getValueParameters().size());
            LocalDeclarationsLowering localDeclarationsLowering = this.this$0;
            LinkedHashSet linkedHashSet = new LinkedHashSet();
            Iterator<T> it = capturedValues.iterator();
            int i = 0;
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                Object next = it.next();
                ArrayList<IrValueParameter> arrayList2 = arrayList;
                int i2 = i + 1;
                if (i < 0) {
                    CollectionsKt.throwIndexOverflow();
                }
                IrValueSymbol irValueSymbol = (IrValueSymbol) next;
                IrValueDeclaration owner3 = irValueSymbol.getOwner();
                IrValueParameterBuilder irValueParameterBuilder = new IrValueParameterBuilder();
                irValueParameterBuilder.setStartOffset(owner3.getStartOffset());
                irValueParameterBuilder.setEndOffset(owner3.getEndOffset());
                irValueParameterBuilder.setOrigin(((owner3 instanceof IrValueParameter) && ((IrValueParameter) owner3).getIndex() < 0 && (newDeclaration instanceof IrConstructor)) ? BOUND_RECEIVER_PARAMETER.INSTANCE : BOUND_VALUE_PARAMETER.INSTANCE);
                irValueParameterBuilder.setName(suggestNameForCapturedValue(owner3, linkedHashSet, isExplicitLocalFunction));
                irValueParameterBuilder.setIndex(i);
                irValueParameterBuilder.setType(localDeclarationsLowering.remapType(localFunctionContext, owner3.getType()));
                boolean z = irValueSymbol instanceof IrValueParameterSymbol;
                IrValueParameterSymbol irValueParameterSymbol = z ? (IrValueParameterSymbol) irValueSymbol : null;
                boolean z2 = true;
                irValueParameterBuilder.setCrossInline((irValueParameterSymbol == null || (owner2 = irValueParameterSymbol.getOwner()) == null || !owner2.getIsCrossinline()) ? false : true);
                IrValueParameterSymbol irValueParameterSymbol2 = z ? (IrValueParameterSymbol) irValueSymbol : null;
                if (irValueParameterSymbol2 == null || (owner = irValueParameterSymbol2.getOwner()) == null || !owner.getIsNoinline()) {
                    z2 = false;
                }
                irValueParameterBuilder.setNoinline(z2);
                IrFunction irFunction = newDeclaration;
                IrValueParameter irValueParameterBuildValueParameter = DeclarationBuildersKt.buildValueParameter(irFunction.getFactory(), irValueParameterBuilder, irFunction);
                this.newParameterToCaptured.put(irValueParameterBuildValueParameter, irValueSymbol);
                arrayList2.add(irValueParameterBuildValueParameter);
                i = i2;
            }
            ArrayList<IrValueParameter> arrayList3 = arrayList;
            for (IrValueParameter irValueParameter : oldDeclaration.getValueParameters()) {
                int index = irValueParameter.getIndex() + capturedValues.size();
                IrType irTypeRemapType = localDeclarationsLowering.remapType(localFunctionContext, irValueParameter.getType());
                IrType varargElementType = irValueParameter.getVarargElementType();
                ArrayList<IrValueParameter> arrayList4 = arrayList3;
                IrValueParameter irValueParameterCopyTo$default = IrUtilsKt.copyTo$default(irValueParameter, newDeclaration, null, index, 0, 0, null, null, irTypeRemapType, varargElementType != null ? localDeclarationsLowering.remapType(localFunctionContext, varargElementType) : null, null, false, false, false, 7802, null);
                putAbsentOrSame(this.newParameterToOld, irValueParameterCopyTo$default, irValueParameter);
                arrayList4.add(irValueParameterCopyTo$default);
                arrayList3 = arrayList4;
                localDeclarationsLowering = localDeclarationsLowering;
                arrayList = arrayList;
            }
            return arrayList;
        }

        private final void recordTransformedValueParameters(IrFunction irFunction, LocalContextWithClosureAsParameters localContextWithClosureAsParameters) {
            for (IrValueParameter irValueParameter : irFunction.getValueParameters()) {
                IrValueSymbol irValueSymbol = this.newParameterToCaptured.get(irValueParameter);
                if (irValueSymbol != null) {
                    localContextWithClosureAsParameters.getCapturedValueToParameter().put(irValueSymbol.getOwner(), irValueParameter);
                }
            }
            for (IrValueParameter irValueParameter2 : CollectionsKt.plus((Collection) CollectionsKt.listOfNotNull((Object[]) new IrValueParameter[]{irFunction.getDispatchReceiverParameter(), irFunction.getExtensionReceiverParameter()}), (Iterable) irFunction.getValueParameters())) {
                IrValueParameter irValueParameter3 = this.newParameterToOld.get(irValueParameter2);
                if (irValueParameter3 != null) {
                    putAbsentOrSame(this.oldParameterToNew, irValueParameter3, irValueParameter2);
                }
            }
        }

        private final void createTransformedConstructorDeclaration(LocalClassConstructorContext constructorContext) {
            IrConstructor declaration = constructorContext.getDeclaration();
            LocalClassContext localClassContext = this.localClasses.get(declaration.getParent());
            Intrinsics.checkNotNull(localClassContext);
            LocalClassContext localClassContext2 = localClassContext;
            List<IrValueSymbol> capturedValues = localClassContext2.getClosure().getCapturedValues();
            IrConstructor irConstructor = declaration;
            IrConstructor irConstructor2 = this.this$0.getContext().getMapping().getCapturedConstructors().get(irConstructor);
            if (irConstructor2 != null) {
                this.transformedDeclarations.put(declaration, irConstructor2);
                constructorContext.setTransformedDeclaration(irConstructor2);
                for (Pair pair : CollectionsKt.zip(irConstructor2.getValueParameters(), capturedValues)) {
                    this.newParameterToCaptured.put((IrValueParameter) pair.component1(), (IrValueSymbol) pair.component2());
                }
                for (Pair pair2 : CollectionsKt.zip(declaration.getValueParameters(), irConstructor2.getValueParameters())) {
                    putAbsentOrSame(this.newParameterToOld, (IrValueParameter) pair2.component2(), (IrValueParameter) pair2.component1());
                }
                recordTransformedValueParameters(irConstructor2, constructorContext);
                return;
            }
            IrFactory irFactory = this.this$0.getContext().getIrFactory();
            LocalDeclarationsLowering localDeclarationsLowering = this.this$0;
            IrFunctionBuilder irFunctionBuilder = new IrFunctionBuilder();
            IrConstructor irConstructor3 = declaration;
            irFunctionBuilder.updateFrom((IrFunction) irConstructor3);
            irFunctionBuilder.setVisibility(localDeclarationsLowering.getVisibilityPolicy().forConstructor(declaration, constructorContext.getInInlineFunctionScope()));
            irFunctionBuilder.setReturnType(declaration.getReturnType());
            IrConstructor irConstructorBuildConstructor = DeclarationBuildersKt.buildConstructor(irFactory, irFunctionBuilder);
            constructorContext.setTransformedDeclaration(irConstructorBuildConstructor);
            irConstructorBuildConstructor.setParent(localClassContext2.getDeclaration());
            IrUtilsKt.copyTypeParametersFrom$default(irConstructorBuildConstructor, declaration, null, null, 6, null);
            if (declaration.getDispatchReceiverParameter() != null) {
                throw new AssertionError("Local class constructor can't have dispatch receiver: " + IrUtilsKt.ir2string(declaration));
            }
            if (declaration.getExtensionReceiverParameter() != null) {
                throw new AssertionError("Local class constructor can't have extension receiver: " + IrUtilsKt.ir2string(declaration));
            }
            IrConstructor irConstructor4 = irConstructorBuildConstructor;
            irConstructorBuildConstructor.setValueParameters(MemoryOptimizedCollectionUtilKt.memoryOptimizedPlus((List) irConstructorBuildConstructor.getValueParameters(), (List) createTransformedValueParameters$default(this, capturedValues, localClassContext2, irConstructor3, irConstructor4, false, 16, null)));
            recordTransformedValueParameters(irConstructor4, constructorContext);
            irConstructorBuildConstructor.setAnnotations(declaration.getAnnotations());
            irConstructorBuildConstructor.setMetadata(declaration.getMetadata());
            this.transformedDeclarations.put(declaration, irConstructorBuildConstructor);
            this.this$0.getContext().getMapping().getCapturedConstructors().set(irConstructor, irConstructorBuildConstructor);
        }

        private final List<IrField> createFieldsForCapturedValues(LocalClassContext localClassContext) {
            DECLARATION_ORIGIN_FIELD_FOR_CAPTURED_VALUE declaration_origin_field_for_captured_value;
            IrField irFieldCreateField;
            IrClass declaration = localClassContext.getDeclaration();
            LinkedHashSet linkedHashSet = new LinkedHashSet();
            Map<IrValueDeclaration, PotentiallyUnusedField> capturedValueToField = localClassContext.getCapturedValueToField();
            LocalDeclarationsLowering localDeclarationsLowering = this.this$0;
            ArrayList arrayList = new ArrayList();
            for (Map.Entry<IrValueDeclaration, PotentiallyUnusedField> entry : capturedValueToField.entrySet()) {
                IrValueDeclaration key = entry.getKey();
                IrFieldSymbol symbolIfUsed = entry.getValue().getSymbolIfUsed();
                if (symbolIfUsed == null) {
                    irFieldCreateField = null;
                } else {
                    if ((key instanceof IrValueParameter) && ((IrValueParameter) key).getIsCrossinline()) {
                        declaration_origin_field_for_captured_value = DECLARATION_ORIGIN_FIELD_FOR_CROSSINLINE_CAPTURED_VALUE.INSTANCE;
                    } else {
                        declaration_origin_field_for_captured_value = DECLARATION_ORIGIN_FIELD_FOR_CAPTURED_VALUE.INSTANCE;
                    }
                    irFieldCreateField = localDeclarationsLowering.getContext().getIrFactory().createField(declaration.getStartOffset(), declaration.getEndOffset(), declaration_origin_field_for_captured_value, suggestNameForCapturedValue$default(this, key, linkedHashSet, false, 4, null), localDeclarationsLowering.getVisibilityPolicy().forCapturedField(key.getSymbol()), symbolIfUsed, key.getType(), true, false, false);
                    irFieldCreateField.setParent(declaration);
                }
                if (irFieldCreateField != null) {
                    arrayList.add(irFieldCreateField);
                }
            }
            return arrayList;
        }

        static /* synthetic */ Name suggestNameForCapturedValue$default(LocalDeclarationsTransformer localDeclarationsTransformer, IrValueDeclaration irValueDeclaration, Set set, boolean z, int i, Object obj) {
            if ((i & 4) != 0) {
                z = false;
            }
            return localDeclarationsTransformer.suggestNameForCapturedValue(irValueDeclaration, set, z);
        }

        private final Name suggestNameForCapturedValue(IrValueDeclaration declaration, Set<String> usedNames, boolean isExplicitLocalFunction) {
            String strAsString;
            int i = 0;
            if (declaration instanceof IrValueParameter) {
                if (!Intrinsics.areEqual(declaration.getName().asString(), "<this>") || !isDispatchReceiver((IrValueParameter) declaration)) {
                    if (Intrinsics.areEqual(declaration.getName().asString(), "<this>")) {
                        IrValueParameter irValueParameter = (IrValueParameter) declaration;
                        if (isExtensionReceiver(irValueParameter)) {
                            String parentNameSuffixForExtensionReceiver = getParentNameSuffixForExtensionReceiver(irValueParameter);
                            String str = AsmUtil.CAPTURED_LABELED_THIS_FIELD + parentNameSuffixForExtensionReceiver;
                            while (!usedNames.add(str)) {
                                i++;
                                str = AsmUtil.CAPTURED_LABELED_THIS_FIELD + parentNameSuffixForExtensionReceiver + '$' + i;
                            }
                            Name nameIdentifier = Name.identifier(str);
                            Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
                            return nameIdentifier;
                        }
                    }
                    if (isCapturedReceiver((IrValueParameter) declaration)) {
                        String strAsString2 = declaration.getName().asString();
                        Intrinsics.checkNotNullExpressionValue(strAsString2, "asString(...)");
                        String strRemovePrefix = StringsKt.removePrefix(strAsString2, (CharSequence) this.CAPTURED_RECEIVER_PREFIX);
                        String str2 = AsmUtil.CAPTURED_LABELED_THIS_FIELD + strRemovePrefix;
                        while (!usedNames.add(str2)) {
                            i++;
                            str2 = AsmUtil.CAPTURED_LABELED_THIS_FIELD + strRemovePrefix + '$' + i;
                        }
                        Name nameIdentifier2 = Name.identifier(str2);
                        Intrinsics.checkNotNullExpressionValue(nameIdentifier2, "identifier(...)");
                        return nameIdentifier2;
                    }
                } else {
                    String str3 = AsmUtil.CAPTURED_THIS_FIELD;
                    while (!usedNames.add(str3)) {
                        i++;
                        str3 = "this$" + i;
                    }
                    Name nameIdentifier3 = Name.identifier(str3);
                    Intrinsics.checkNotNullExpressionValue(nameIdentifier3, "identifier(...)");
                    return nameIdentifier3;
                }
            }
            if (declaration.getName().isSpecial()) {
                strAsString = declaration.getName().asStringStripSpecialMarkers();
            } else {
                strAsString = declaration.getName().asString();
            }
            Intrinsics.checkNotNull(strAsString);
            if (!isExplicitLocalFunction || !(declaration instanceof IrVariable)) {
                String synthesizedString = DescriptorUtilsKt.getSynthesizedString(strAsString);
                while (!usedNames.add(synthesizedString)) {
                    i++;
                    synthesizedString = DescriptorUtilsKt.getSynthesizedString(strAsString + '$' + i);
                }
                Name nameIdentifier4 = Name.identifier(synthesizedString);
                Intrinsics.checkNotNullExpressionValue(nameIdentifier4, "identifier(...)");
                return nameIdentifier4;
            }
            String str4 = strAsString;
            while (!usedNames.add(str4)) {
                i++;
                str4 = strAsString + '$' + i;
            }
            Name nameIdentifier5 = Name.identifier(str4);
            Intrinsics.checkNotNullExpressionValue(nameIdentifier5, "identifier(...)");
            return nameIdentifier5;
        }

        private final boolean isDispatchReceiver(IrValueParameter irValueParameter) {
            IrDeclarationParent parent = irValueParameter.getParent();
            if (parent instanceof IrFunction) {
                return Intrinsics.areEqual(((IrFunction) parent).getDispatchReceiverParameter(), irValueParameter);
            }
            if (parent instanceof IrClass) {
                return Intrinsics.areEqual(((IrClass) parent).getThisReceiver(), irValueParameter);
            }
            return false;
        }

        private final boolean isExtensionReceiver(IrValueParameter irValueParameter) {
            IrDeclarationParent parent = irValueParameter.getParent();
            IrFunction irFunction = parent instanceof IrFunction ? (IrFunction) parent : null;
            if (irFunction == null) {
                return false;
            }
            return Intrinsics.areEqual(irFunction.getExtensionReceiverParameter(), irValueParameter);
        }

        private final boolean isCapturedReceiver(IrValueParameter irValueParameter) {
            String strAsString = irValueParameter.getName().asString();
            Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
            return StringsKt.startsWith$default(strAsString, this.CAPTURED_RECEIVER_PREFIX, false, 2, (Object) null);
        }

        private final String getParentNameSuffixForExtensionReceiver(IrValueParameter irValueParameter) {
            IrDeclarationParent parent = irValueParameter.getParent();
            IrSimpleFunction irSimpleFunction = parent instanceof IrSimpleFunction ? (IrSimpleFunction) parent : null;
            if (irSimpleFunction == null) {
                throw new AssertionError("Extension receiver parent is not a simple function: " + RenderIrElementKt.render$default(irValueParameter.getParent(), (DumpIrTreeOptions) null, 1, (Object) null));
            }
            IrPropertySymbol correspondingPropertySymbol = irSimpleFunction.getCorrespondingPropertySymbol();
            IrProperty owner = correspondingPropertySymbol != null ? correspondingPropertySymbol.getOwner() : null;
            if (owner != null) {
                String strAsStringStripSpecialMarkers = owner.getName().asStringStripSpecialMarkers();
                Intrinsics.checkNotNullExpressionValue(strAsStringStripSpecialMarkers, "asStringStripSpecialMarkers(...)");
                return strAsStringStripSpecialMarkers;
            }
            String strAsStringStripSpecialMarkers2 = irSimpleFunction.getName().asStringStripSpecialMarkers();
            Intrinsics.checkNotNullExpressionValue(strAsStringStripSpecialMarkers2, "asStringStripSpecialMarkers(...)");
            return strAsStringStripSpecialMarkers2;
        }

        private final void collectClosureForLocalDeclarations() {
            ClosureAnnotator closureAnnotator = new ClosureAnnotator(this.irElement, this.container);
            for (Map.Entry<IrFunction, LocalFunctionContext> entry : this.localFunctions.entrySet()) {
                entry.getValue().setClosure(closureAnnotator.getFunctionClosure(entry.getKey()));
            }
            for (Map.Entry<IrClass, LocalClassContext> entry2 : this.localClasses.entrySet()) {
                entry2.getValue().setClosure(closureAnnotator.getClassClosure(entry2.getKey()));
            }
        }

        private final void collectLocalDeclarations() {
            final IrPackageFragment packageFragment = IrUtilsKt.getPackageFragment(this.container);
            IrDeclaration irDeclaration = this.container;
            IrClass irClass = irDeclaration instanceof IrClass ? (IrClass) irDeclaration : null;
            IrClass parent = irClass != null ? irClass : irDeclaration.getParent();
            while ((parent instanceof IrDeclaration) && !(parent instanceof IrClass)) {
                parent = ((IrDeclaration) parent).getParent();
            }
            final IrClass irClass2 = parent instanceof IrClass ? (IrClass) parent : null;
            IrElement irElement = this.irElement;
            final LocalDeclarationsLowering localDeclarationsLowering = this.this$0;
            irElement.accept(new IrElementVisitor<Unit, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data>() { // from class: org.jetbrains.kotlin.backend.common.lower.LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$1
                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitAnonymousInitializer */
                public /* bridge */ /* synthetic */ Unit visitAnonymousInitializer2(IrAnonymousInitializer irAnonymousInitializer, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    visitAnonymousInitializer2(irAnonymousInitializer, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitAnonymousInitializer, reason: avoid collision after fix types in other method */
                public void visitAnonymousInitializer2(IrAnonymousInitializer irAnonymousInitializer, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    IrElementVisitor.DefaultImpls.visitAnonymousInitializer(this, irAnonymousInitializer, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitBlock */
                public /* bridge */ /* synthetic */ Unit visitBlock2(IrBlock irBlock, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    visitBlock2(irBlock, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitBlockBody */
                public /* bridge */ /* synthetic */ Unit visitBlockBody2(IrBlockBody irBlockBody, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    visitBlockBody2(irBlockBody, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitBlockBody, reason: avoid collision after fix types in other method */
                public void visitBlockBody2(IrBlockBody irBlockBody, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    IrElementVisitor.DefaultImpls.visitBlockBody(this, irBlockBody, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitBody */
                public /* bridge */ /* synthetic */ Unit visitBody2(IrBody irBody, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    visitBody2(irBody, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitBody, reason: avoid collision after fix types in other method */
                public void visitBody2(IrBody irBody, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    IrElementVisitor.DefaultImpls.visitBody(this, irBody, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitBranch */
                public /* bridge */ /* synthetic */ Unit visitBranch2(IrBranch irBranch, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    visitBranch2(irBranch, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitBranch, reason: avoid collision after fix types in other method */
                public void visitBranch2(IrBranch irBranch, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    IrElementVisitor.DefaultImpls.visitBranch(this, irBranch, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitBreak */
                public /* bridge */ /* synthetic */ Unit visitBreak2(IrBreak irBreak, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    visitBreak2(irBreak, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitBreak, reason: avoid collision after fix types in other method */
                public void visitBreak2(IrBreak irBreak, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    IrElementVisitor.DefaultImpls.visitBreak(this, irBreak, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitBreakContinue */
                public /* bridge */ /* synthetic */ Unit visitBreakContinue2(IrBreakContinue irBreakContinue, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    visitBreakContinue2(irBreakContinue, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitBreakContinue, reason: avoid collision after fix types in other method */
                public void visitBreakContinue2(IrBreakContinue irBreakContinue, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    IrElementVisitor.DefaultImpls.visitBreakContinue(this, irBreakContinue, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitCall */
                public /* bridge */ /* synthetic */ Unit visitCall2(IrCall irCall, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    visitCall2(irCall, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitCall, reason: avoid collision after fix types in other method */
                public void visitCall2(IrCall irCall, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    IrElementVisitor.DefaultImpls.visitCall(this, irCall, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                public /* bridge */ /* synthetic */ Unit visitCallableReference(IrCallableReference irCallableReference, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    visitCallableReference2((IrCallableReference<?>) irCallableReference, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitCallableReference, reason: avoid collision after fix types in other method */
                public void visitCallableReference2(IrCallableReference<?> irCallableReference, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    IrElementVisitor.DefaultImpls.visitCallableReference(this, irCallableReference, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitCatch */
                public /* bridge */ /* synthetic */ Unit visitCatch2(IrCatch irCatch, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    visitCatch2(irCatch, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitCatch, reason: avoid collision after fix types in other method */
                public void visitCatch2(IrCatch irCatch, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    IrElementVisitor.DefaultImpls.visitCatch(this, irCatch, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitClass */
                public /* bridge */ /* synthetic */ Unit visitClass2(IrClass irClass3, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    visitClass2(irClass3, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitClassReference */
                public /* bridge */ /* synthetic */ Unit visitClassReference2(IrClassReference irClassReference, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    visitClassReference2(irClassReference, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitClassReference, reason: avoid collision after fix types in other method */
                public void visitClassReference2(IrClassReference irClassReference, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    IrElementVisitor.DefaultImpls.visitClassReference(this, irClassReference, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitComposite */
                public /* bridge */ /* synthetic */ Unit visitComposite2(IrComposite irComposite, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    visitComposite2(irComposite, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitComposite, reason: avoid collision after fix types in other method */
                public void visitComposite2(IrComposite irComposite, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    IrElementVisitor.DefaultImpls.visitComposite(this, irComposite, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                public /* bridge */ /* synthetic */ Unit visitConst(IrConst irConst, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    visitConst2((IrConst<?>) irConst, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitConst, reason: avoid collision after fix types in other method */
                public void visitConst2(IrConst<?> irConst, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    IrElementVisitor.DefaultImpls.visitConst(this, irConst, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitConstantArray */
                public /* bridge */ /* synthetic */ Unit visitConstantArray2(IrConstantArray irConstantArray, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    visitConstantArray2(irConstantArray, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitConstantArray, reason: avoid collision after fix types in other method */
                public void visitConstantArray2(IrConstantArray irConstantArray, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    IrElementVisitor.DefaultImpls.visitConstantArray(this, irConstantArray, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitConstantObject */
                public /* bridge */ /* synthetic */ Unit visitConstantObject2(IrConstantObject irConstantObject, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    visitConstantObject2(irConstantObject, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitConstantObject, reason: avoid collision after fix types in other method */
                public void visitConstantObject2(IrConstantObject irConstantObject, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    IrElementVisitor.DefaultImpls.visitConstantObject(this, irConstantObject, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitConstantPrimitive */
                public /* bridge */ /* synthetic */ Unit visitConstantPrimitive2(IrConstantPrimitive irConstantPrimitive, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    visitConstantPrimitive2(irConstantPrimitive, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitConstantPrimitive, reason: avoid collision after fix types in other method */
                public void visitConstantPrimitive2(IrConstantPrimitive irConstantPrimitive, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    IrElementVisitor.DefaultImpls.visitConstantPrimitive(this, irConstantPrimitive, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitConstantValue */
                public /* bridge */ /* synthetic */ Unit visitConstantValue2(IrConstantValue irConstantValue, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    visitConstantValue2(irConstantValue, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitConstantValue, reason: avoid collision after fix types in other method */
                public void visitConstantValue2(IrConstantValue irConstantValue, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    IrElementVisitor.DefaultImpls.visitConstantValue(this, irConstantValue, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitConstructor */
                public /* bridge */ /* synthetic */ Unit visitConstructor2(IrConstructor irConstructor, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    visitConstructor2(irConstructor, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitConstructorCall */
                public /* bridge */ /* synthetic */ Unit visitConstructorCall2(IrConstructorCall irConstructorCall, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    visitConstructorCall2(irConstructorCall, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitConstructorCall, reason: avoid collision after fix types in other method */
                public void visitConstructorCall2(IrConstructorCall irConstructorCall, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    IrElementVisitor.DefaultImpls.visitConstructorCall(this, irConstructorCall, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitContainerExpression */
                public /* bridge */ /* synthetic */ Unit visitContainerExpression2(IrContainerExpression irContainerExpression, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    visitContainerExpression2(irContainerExpression, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitContainerExpression, reason: avoid collision after fix types in other method */
                public void visitContainerExpression2(IrContainerExpression irContainerExpression, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    IrElementVisitor.DefaultImpls.visitContainerExpression(this, irContainerExpression, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitContinue */
                public /* bridge */ /* synthetic */ Unit visitContinue2(IrContinue irContinue, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    visitContinue2(irContinue, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitContinue, reason: avoid collision after fix types in other method */
                public void visitContinue2(IrContinue irContinue, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    IrElementVisitor.DefaultImpls.visitContinue(this, irContinue, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitDeclaration */
                public /* bridge */ /* synthetic */ Unit visitDeclaration2(IrDeclarationBase irDeclarationBase, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    visitDeclaration2(irDeclarationBase, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitDeclaration, reason: avoid collision after fix types in other method */
                public void visitDeclaration2(IrDeclarationBase irDeclarationBase, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    IrElementVisitor.DefaultImpls.visitDeclaration(this, irDeclarationBase, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitDeclarationReference */
                public /* bridge */ /* synthetic */ Unit visitDeclarationReference2(IrDeclarationReference irDeclarationReference, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    visitDeclarationReference2(irDeclarationReference, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitDeclarationReference, reason: avoid collision after fix types in other method */
                public void visitDeclarationReference2(IrDeclarationReference irDeclarationReference, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    IrElementVisitor.DefaultImpls.visitDeclarationReference(this, irDeclarationReference, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitDelegatingConstructorCall */
                public /* bridge */ /* synthetic */ Unit visitDelegatingConstructorCall2(IrDelegatingConstructorCall irDelegatingConstructorCall, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    visitDelegatingConstructorCall2(irDelegatingConstructorCall, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitDelegatingConstructorCall, reason: avoid collision after fix types in other method */
                public void visitDelegatingConstructorCall2(IrDelegatingConstructorCall irDelegatingConstructorCall, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    IrElementVisitor.DefaultImpls.visitDelegatingConstructorCall(this, irDelegatingConstructorCall, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitDoWhileLoop */
                public /* bridge */ /* synthetic */ Unit visitDoWhileLoop2(IrDoWhileLoop irDoWhileLoop, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    visitDoWhileLoop2(irDoWhileLoop, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitDoWhileLoop, reason: avoid collision after fix types in other method */
                public void visitDoWhileLoop2(IrDoWhileLoop irDoWhileLoop, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    IrElementVisitor.DefaultImpls.visitDoWhileLoop(this, irDoWhileLoop, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitDynamicExpression */
                public /* bridge */ /* synthetic */ Unit visitDynamicExpression2(IrDynamicExpression irDynamicExpression, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    visitDynamicExpression2(irDynamicExpression, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitDynamicExpression, reason: avoid collision after fix types in other method */
                public void visitDynamicExpression2(IrDynamicExpression irDynamicExpression, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    IrElementVisitor.DefaultImpls.visitDynamicExpression(this, irDynamicExpression, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitDynamicMemberExpression */
                public /* bridge */ /* synthetic */ Unit visitDynamicMemberExpression2(IrDynamicMemberExpression irDynamicMemberExpression, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    visitDynamicMemberExpression2(irDynamicMemberExpression, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitDynamicMemberExpression, reason: avoid collision after fix types in other method */
                public void visitDynamicMemberExpression2(IrDynamicMemberExpression irDynamicMemberExpression, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    IrElementVisitor.DefaultImpls.visitDynamicMemberExpression(this, irDynamicMemberExpression, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitDynamicOperatorExpression */
                public /* bridge */ /* synthetic */ Unit visitDynamicOperatorExpression2(IrDynamicOperatorExpression irDynamicOperatorExpression, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    visitDynamicOperatorExpression2(irDynamicOperatorExpression, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitDynamicOperatorExpression, reason: avoid collision after fix types in other method */
                public void visitDynamicOperatorExpression2(IrDynamicOperatorExpression irDynamicOperatorExpression, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    IrElementVisitor.DefaultImpls.visitDynamicOperatorExpression(this, irDynamicOperatorExpression, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitElement */
                public /* bridge */ /* synthetic */ Unit visitElement2(IrElement irElement2, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    visitElement2(irElement2, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitElseBranch */
                public /* bridge */ /* synthetic */ Unit visitElseBranch2(IrElseBranch irElseBranch, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    visitElseBranch2(irElseBranch, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitElseBranch, reason: avoid collision after fix types in other method */
                public void visitElseBranch2(IrElseBranch irElseBranch, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    IrElementVisitor.DefaultImpls.visitElseBranch(this, irElseBranch, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitEnumConstructorCall */
                public /* bridge */ /* synthetic */ Unit visitEnumConstructorCall2(IrEnumConstructorCall irEnumConstructorCall, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    visitEnumConstructorCall2(irEnumConstructorCall, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitEnumConstructorCall, reason: avoid collision after fix types in other method */
                public void visitEnumConstructorCall2(IrEnumConstructorCall irEnumConstructorCall, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    IrElementVisitor.DefaultImpls.visitEnumConstructorCall(this, irEnumConstructorCall, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitEnumEntry */
                public /* bridge */ /* synthetic */ Unit visitEnumEntry2(IrEnumEntry irEnumEntry, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    visitEnumEntry2(irEnumEntry, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitEnumEntry, reason: avoid collision after fix types in other method */
                public void visitEnumEntry2(IrEnumEntry irEnumEntry, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    IrElementVisitor.DefaultImpls.visitEnumEntry(this, irEnumEntry, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitErrorCallExpression */
                public /* bridge */ /* synthetic */ Unit visitErrorCallExpression2(IrErrorCallExpression irErrorCallExpression, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    visitErrorCallExpression2(irErrorCallExpression, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitErrorCallExpression, reason: avoid collision after fix types in other method */
                public void visitErrorCallExpression2(IrErrorCallExpression irErrorCallExpression, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    IrElementVisitor.DefaultImpls.visitErrorCallExpression(this, irErrorCallExpression, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitErrorDeclaration */
                public /* bridge */ /* synthetic */ Unit visitErrorDeclaration2(IrErrorDeclaration irErrorDeclaration, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    visitErrorDeclaration2(irErrorDeclaration, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitErrorDeclaration, reason: avoid collision after fix types in other method */
                public void visitErrorDeclaration2(IrErrorDeclaration irErrorDeclaration, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    IrElementVisitor.DefaultImpls.visitErrorDeclaration(this, irErrorDeclaration, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitErrorExpression */
                public /* bridge */ /* synthetic */ Unit visitErrorExpression2(IrErrorExpression irErrorExpression, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    visitErrorExpression2(irErrorExpression, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitErrorExpression, reason: avoid collision after fix types in other method */
                public void visitErrorExpression2(IrErrorExpression irErrorExpression, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    IrElementVisitor.DefaultImpls.visitErrorExpression(this, irErrorExpression, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitExpression */
                public /* bridge */ /* synthetic */ Unit visitExpression2(IrExpression irExpression, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    visitExpression2(irExpression, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitExpression, reason: avoid collision after fix types in other method */
                public void visitExpression2(IrExpression irExpression, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    IrElementVisitor.DefaultImpls.visitExpression(this, irExpression, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitExpressionBody */
                public /* bridge */ /* synthetic */ Unit visitExpressionBody2(IrExpressionBody irExpressionBody, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    visitExpressionBody2(irExpressionBody, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitExpressionBody, reason: avoid collision after fix types in other method */
                public void visitExpressionBody2(IrExpressionBody irExpressionBody, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    IrElementVisitor.DefaultImpls.visitExpressionBody(this, irExpressionBody, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitExternalPackageFragment */
                public /* bridge */ /* synthetic */ Unit visitExternalPackageFragment2(IrExternalPackageFragment irExternalPackageFragment, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    visitExternalPackageFragment2(irExternalPackageFragment, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitExternalPackageFragment, reason: avoid collision after fix types in other method */
                public void visitExternalPackageFragment2(IrExternalPackageFragment irExternalPackageFragment, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    IrElementVisitor.DefaultImpls.visitExternalPackageFragment(this, irExternalPackageFragment, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitField */
                public /* bridge */ /* synthetic */ Unit visitField2(IrField irField, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    visitField2(irField, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitField, reason: avoid collision after fix types in other method */
                public void visitField2(IrField irField, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    IrElementVisitor.DefaultImpls.visitField(this, irField, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitFieldAccess */
                public /* bridge */ /* synthetic */ Unit visitFieldAccess2(IrFieldAccessExpression irFieldAccessExpression, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    visitFieldAccess2(irFieldAccessExpression, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitFieldAccess, reason: avoid collision after fix types in other method */
                public void visitFieldAccess2(IrFieldAccessExpression irFieldAccessExpression, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    IrElementVisitor.DefaultImpls.visitFieldAccess(this, irFieldAccessExpression, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitFile */
                public /* bridge */ /* synthetic */ Unit visitFile2(IrFile irFile, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    visitFile2(irFile, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitFile, reason: avoid collision after fix types in other method */
                public void visitFile2(IrFile irFile, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    IrElementVisitor.DefaultImpls.visitFile(this, irFile, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitFunction */
                public /* bridge */ /* synthetic */ Unit visitFunction2(IrFunction irFunction, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    visitFunction2(irFunction, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitFunction, reason: avoid collision after fix types in other method */
                public void visitFunction2(IrFunction irFunction, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    IrElementVisitor.DefaultImpls.visitFunction(this, irFunction, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitFunctionAccess */
                public /* bridge */ /* synthetic */ Unit visitFunctionAccess2(IrFunctionAccessExpression irFunctionAccessExpression, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    visitFunctionAccess2(irFunctionAccessExpression, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitFunctionAccess, reason: avoid collision after fix types in other method */
                public void visitFunctionAccess2(IrFunctionAccessExpression irFunctionAccessExpression, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    IrElementVisitor.DefaultImpls.visitFunctionAccess(this, irFunctionAccessExpression, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitFunctionExpression */
                public /* bridge */ /* synthetic */ Unit visitFunctionExpression2(IrFunctionExpression irFunctionExpression, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    visitFunctionExpression2(irFunctionExpression, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitFunctionReference */
                public /* bridge */ /* synthetic */ Unit visitFunctionReference2(IrFunctionReference irFunctionReference, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    visitFunctionReference2(irFunctionReference, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitFunctionReference, reason: avoid collision after fix types in other method */
                public void visitFunctionReference2(IrFunctionReference irFunctionReference, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    IrElementVisitor.DefaultImpls.visitFunctionReference(this, irFunctionReference, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitGetClass */
                public /* bridge */ /* synthetic */ Unit visitGetClass2(IrGetClass irGetClass, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    visitGetClass2(irGetClass, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitGetClass, reason: avoid collision after fix types in other method */
                public void visitGetClass2(IrGetClass irGetClass, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    IrElementVisitor.DefaultImpls.visitGetClass(this, irGetClass, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitGetEnumValue */
                public /* bridge */ /* synthetic */ Unit visitGetEnumValue2(IrGetEnumValue irGetEnumValue, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    visitGetEnumValue2(irGetEnumValue, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitGetEnumValue, reason: avoid collision after fix types in other method */
                public void visitGetEnumValue2(IrGetEnumValue irGetEnumValue, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    IrElementVisitor.DefaultImpls.visitGetEnumValue(this, irGetEnumValue, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitGetField */
                public /* bridge */ /* synthetic */ Unit visitGetField2(IrGetField irGetField, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    visitGetField2(irGetField, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitGetField, reason: avoid collision after fix types in other method */
                public void visitGetField2(IrGetField irGetField, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    IrElementVisitor.DefaultImpls.visitGetField(this, irGetField, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitGetObjectValue */
                public /* bridge */ /* synthetic */ Unit visitGetObjectValue2(IrGetObjectValue irGetObjectValue, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    visitGetObjectValue2(irGetObjectValue, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitGetObjectValue, reason: avoid collision after fix types in other method */
                public void visitGetObjectValue2(IrGetObjectValue irGetObjectValue, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    IrElementVisitor.DefaultImpls.visitGetObjectValue(this, irGetObjectValue, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitGetValue */
                public /* bridge */ /* synthetic */ Unit visitGetValue2(IrGetValue irGetValue, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    visitGetValue2(irGetValue, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitGetValue, reason: avoid collision after fix types in other method */
                public void visitGetValue2(IrGetValue irGetValue, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    IrElementVisitor.DefaultImpls.visitGetValue(this, irGetValue, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitInstanceInitializerCall */
                public /* bridge */ /* synthetic */ Unit visitInstanceInitializerCall2(IrInstanceInitializerCall irInstanceInitializerCall, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    visitInstanceInitializerCall2(irInstanceInitializerCall, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitInstanceInitializerCall, reason: avoid collision after fix types in other method */
                public void visitInstanceInitializerCall2(IrInstanceInitializerCall irInstanceInitializerCall, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    IrElementVisitor.DefaultImpls.visitInstanceInitializerCall(this, irInstanceInitializerCall, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitLocalDelegatedProperty */
                public /* bridge */ /* synthetic */ Unit visitLocalDelegatedProperty2(IrLocalDelegatedProperty irLocalDelegatedProperty, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    visitLocalDelegatedProperty2(irLocalDelegatedProperty, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitLocalDelegatedProperty, reason: avoid collision after fix types in other method */
                public void visitLocalDelegatedProperty2(IrLocalDelegatedProperty irLocalDelegatedProperty, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    IrElementVisitor.DefaultImpls.visitLocalDelegatedProperty(this, irLocalDelegatedProperty, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitLocalDelegatedPropertyReference */
                public /* bridge */ /* synthetic */ Unit visitLocalDelegatedPropertyReference2(IrLocalDelegatedPropertyReference irLocalDelegatedPropertyReference, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    visitLocalDelegatedPropertyReference2(irLocalDelegatedPropertyReference, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitLocalDelegatedPropertyReference, reason: avoid collision after fix types in other method */
                public void visitLocalDelegatedPropertyReference2(IrLocalDelegatedPropertyReference irLocalDelegatedPropertyReference, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    IrElementVisitor.DefaultImpls.visitLocalDelegatedPropertyReference(this, irLocalDelegatedPropertyReference, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitLoop */
                public /* bridge */ /* synthetic */ Unit visitLoop2(IrLoop irLoop, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    visitLoop2(irLoop, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitLoop, reason: avoid collision after fix types in other method */
                public void visitLoop2(IrLoop irLoop, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    IrElementVisitor.DefaultImpls.visitLoop(this, irLoop, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                public /* bridge */ /* synthetic */ Unit visitMemberAccess(IrMemberAccessExpression irMemberAccessExpression, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    visitMemberAccess2((IrMemberAccessExpression<?>) irMemberAccessExpression, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitMemberAccess, reason: avoid collision after fix types in other method */
                public void visitMemberAccess2(IrMemberAccessExpression<?> irMemberAccessExpression, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    IrElementVisitor.DefaultImpls.visitMemberAccess(this, irMemberAccessExpression, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitModuleFragment */
                public /* bridge */ /* synthetic */ Unit visitModuleFragment2(IrModuleFragment irModuleFragment, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    visitModuleFragment2(irModuleFragment, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitModuleFragment, reason: avoid collision after fix types in other method */
                public void visitModuleFragment2(IrModuleFragment irModuleFragment, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    IrElementVisitor.DefaultImpls.visitModuleFragment(this, irModuleFragment, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitPackageFragment */
                public /* bridge */ /* synthetic */ Unit visitPackageFragment2(IrPackageFragment irPackageFragment, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    visitPackageFragment2(irPackageFragment, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitPackageFragment, reason: avoid collision after fix types in other method */
                public void visitPackageFragment2(IrPackageFragment irPackageFragment, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    IrElementVisitor.DefaultImpls.visitPackageFragment(this, irPackageFragment, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitProperty */
                public /* bridge */ /* synthetic */ Unit visitProperty2(IrProperty irProperty, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    visitProperty2(irProperty, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitProperty, reason: avoid collision after fix types in other method */
                public void visitProperty2(IrProperty irProperty, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    IrElementVisitor.DefaultImpls.visitProperty(this, irProperty, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitPropertyReference */
                public /* bridge */ /* synthetic */ Unit visitPropertyReference2(IrPropertyReference irPropertyReference, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    visitPropertyReference2(irPropertyReference, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitPropertyReference, reason: avoid collision after fix types in other method */
                public void visitPropertyReference2(IrPropertyReference irPropertyReference, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    IrElementVisitor.DefaultImpls.visitPropertyReference(this, irPropertyReference, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitRawFunctionReference */
                public /* bridge */ /* synthetic */ Unit visitRawFunctionReference2(IrRawFunctionReference irRawFunctionReference, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    visitRawFunctionReference2(irRawFunctionReference, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitRawFunctionReference, reason: avoid collision after fix types in other method */
                public void visitRawFunctionReference2(IrRawFunctionReference irRawFunctionReference, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    IrElementVisitor.DefaultImpls.visitRawFunctionReference(this, irRawFunctionReference, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitReturn */
                public /* bridge */ /* synthetic */ Unit visitReturn2(IrReturn irReturn, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    visitReturn2(irReturn, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitReturn, reason: avoid collision after fix types in other method */
                public void visitReturn2(IrReturn irReturn, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    IrElementVisitor.DefaultImpls.visitReturn(this, irReturn, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitScript */
                public /* bridge */ /* synthetic */ Unit visitScript2(IrScript irScript, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    visitScript2(irScript, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitScript, reason: avoid collision after fix types in other method */
                public void visitScript2(IrScript irScript, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    IrElementVisitor.DefaultImpls.visitScript(this, irScript, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitSetField */
                public /* bridge */ /* synthetic */ Unit visitSetField2(IrSetField irSetField, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    visitSetField2(irSetField, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitSetField, reason: avoid collision after fix types in other method */
                public void visitSetField2(IrSetField irSetField, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    IrElementVisitor.DefaultImpls.visitSetField(this, irSetField, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitSetValue */
                public /* bridge */ /* synthetic */ Unit visitSetValue2(IrSetValue irSetValue, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    visitSetValue2(irSetValue, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitSetValue, reason: avoid collision after fix types in other method */
                public void visitSetValue2(IrSetValue irSetValue, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    IrElementVisitor.DefaultImpls.visitSetValue(this, irSetValue, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitSimpleFunction */
                public /* bridge */ /* synthetic */ Unit visitSimpleFunction2(IrSimpleFunction irSimpleFunction, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    visitSimpleFunction2(irSimpleFunction, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitSingletonReference */
                public /* bridge */ /* synthetic */ Unit visitSingletonReference2(IrGetSingletonValue irGetSingletonValue, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    visitSingletonReference2(irGetSingletonValue, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitSingletonReference, reason: avoid collision after fix types in other method */
                public void visitSingletonReference2(IrGetSingletonValue irGetSingletonValue, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    IrElementVisitor.DefaultImpls.visitSingletonReference(this, irGetSingletonValue, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitSpreadElement */
                public /* bridge */ /* synthetic */ Unit visitSpreadElement2(IrSpreadElement irSpreadElement, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    visitSpreadElement2(irSpreadElement, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitSpreadElement, reason: avoid collision after fix types in other method */
                public void visitSpreadElement2(IrSpreadElement irSpreadElement, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    IrElementVisitor.DefaultImpls.visitSpreadElement(this, irSpreadElement, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitStringConcatenation */
                public /* bridge */ /* synthetic */ Unit visitStringConcatenation2(IrStringConcatenation irStringConcatenation, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    visitStringConcatenation2(irStringConcatenation, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitStringConcatenation, reason: avoid collision after fix types in other method */
                public void visitStringConcatenation2(IrStringConcatenation irStringConcatenation, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    IrElementVisitor.DefaultImpls.visitStringConcatenation(this, irStringConcatenation, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitSuspendableExpression */
                public /* bridge */ /* synthetic */ Unit visitSuspendableExpression2(IrSuspendableExpression irSuspendableExpression, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    visitSuspendableExpression2(irSuspendableExpression, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitSuspendableExpression, reason: avoid collision after fix types in other method */
                public void visitSuspendableExpression2(IrSuspendableExpression irSuspendableExpression, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    IrElementVisitor.DefaultImpls.visitSuspendableExpression(this, irSuspendableExpression, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitSuspensionPoint */
                public /* bridge */ /* synthetic */ Unit visitSuspensionPoint2(IrSuspensionPoint irSuspensionPoint, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    visitSuspensionPoint2(irSuspensionPoint, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitSuspensionPoint, reason: avoid collision after fix types in other method */
                public void visitSuspensionPoint2(IrSuspensionPoint irSuspensionPoint, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    IrElementVisitor.DefaultImpls.visitSuspensionPoint(this, irSuspensionPoint, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitSyntheticBody */
                public /* bridge */ /* synthetic */ Unit visitSyntheticBody2(IrSyntheticBody irSyntheticBody, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    visitSyntheticBody2(irSyntheticBody, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitSyntheticBody, reason: avoid collision after fix types in other method */
                public void visitSyntheticBody2(IrSyntheticBody irSyntheticBody, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    IrElementVisitor.DefaultImpls.visitSyntheticBody(this, irSyntheticBody, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitThrow */
                public /* bridge */ /* synthetic */ Unit visitThrow2(IrThrow irThrow, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    visitThrow2(irThrow, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitThrow, reason: avoid collision after fix types in other method */
                public void visitThrow2(IrThrow irThrow, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    IrElementVisitor.DefaultImpls.visitThrow(this, irThrow, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitTry */
                public /* bridge */ /* synthetic */ Unit visitTry2(IrTry irTry, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    visitTry2(irTry, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitTry, reason: avoid collision after fix types in other method */
                public void visitTry2(IrTry irTry, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    IrElementVisitor.DefaultImpls.visitTry(this, irTry, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitTypeAlias */
                public /* bridge */ /* synthetic */ Unit visitTypeAlias2(IrTypeAlias irTypeAlias, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    visitTypeAlias2(irTypeAlias, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitTypeAlias, reason: avoid collision after fix types in other method */
                public void visitTypeAlias2(IrTypeAlias irTypeAlias, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    IrElementVisitor.DefaultImpls.visitTypeAlias(this, irTypeAlias, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitTypeOperator */
                public /* bridge */ /* synthetic */ Unit visitTypeOperator2(IrTypeOperatorCall irTypeOperatorCall, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    visitTypeOperator2(irTypeOperatorCall, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitTypeOperator, reason: avoid collision after fix types in other method */
                public void visitTypeOperator2(IrTypeOperatorCall irTypeOperatorCall, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    IrElementVisitor.DefaultImpls.visitTypeOperator(this, irTypeOperatorCall, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitTypeParameter */
                public /* bridge */ /* synthetic */ Unit visitTypeParameter2(IrTypeParameter irTypeParameter, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    visitTypeParameter2(irTypeParameter, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitTypeParameter, reason: avoid collision after fix types in other method */
                public void visitTypeParameter2(IrTypeParameter irTypeParameter, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    IrElementVisitor.DefaultImpls.visitTypeParameter(this, irTypeParameter, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitValueAccess */
                public /* bridge */ /* synthetic */ Unit visitValueAccess2(IrValueAccessExpression irValueAccessExpression, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    visitValueAccess2(irValueAccessExpression, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitValueAccess, reason: avoid collision after fix types in other method */
                public void visitValueAccess2(IrValueAccessExpression irValueAccessExpression, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    IrElementVisitor.DefaultImpls.visitValueAccess(this, irValueAccessExpression, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitValueParameter */
                public /* bridge */ /* synthetic */ Unit visitValueParameter2(IrValueParameter irValueParameter, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    visitValueParameter2(irValueParameter, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitValueParameter, reason: avoid collision after fix types in other method */
                public void visitValueParameter2(IrValueParameter irValueParameter, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    IrElementVisitor.DefaultImpls.visitValueParameter(this, irValueParameter, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitVararg */
                public /* bridge */ /* synthetic */ Unit visitVararg2(IrVararg irVararg, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    visitVararg2(irVararg, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitVararg, reason: avoid collision after fix types in other method */
                public void visitVararg2(IrVararg irVararg, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    IrElementVisitor.DefaultImpls.visitVararg(this, irVararg, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitVariable */
                public /* bridge */ /* synthetic */ Unit visitVariable2(IrVariable irVariable, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    visitVariable2(irVariable, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitVariable, reason: avoid collision after fix types in other method */
                public void visitVariable2(IrVariable irVariable, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    IrElementVisitor.DefaultImpls.visitVariable(this, irVariable, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitWhen */
                public /* bridge */ /* synthetic */ Unit visitWhen2(IrWhen irWhen, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    visitWhen2(irWhen, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitWhen, reason: avoid collision after fix types in other method */
                public void visitWhen2(IrWhen irWhen, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    IrElementVisitor.DefaultImpls.visitWhen(this, irWhen, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitWhileLoop */
                public /* bridge */ /* synthetic */ Unit visitWhileLoop2(IrWhileLoop irWhileLoop, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    visitWhileLoop2(irWhileLoop, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitWhileLoop, reason: avoid collision after fix types in other method */
                public void visitWhileLoop2(IrWhileLoop irWhileLoop, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    IrElementVisitor.DefaultImpls.visitWhileLoop(this, irWhileLoop, localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data);
                }

                /* JADX INFO: renamed from: visitElement, reason: avoid collision after fix types in other method */
                public void visitElement2(IrElement element, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data data) {
                    Intrinsics.checkNotNullParameter(element, "element");
                    Intrinsics.checkNotNullParameter(data, "data");
                    element.acceptChildren(this, data);
                }

                /* JADX INFO: renamed from: visitBlock, reason: avoid collision after fix types in other method */
                public void visitBlock2(IrBlock expression, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data data) {
                    Intrinsics.checkNotNullParameter(expression, "expression");
                    Intrinsics.checkNotNullParameter(data, "data");
                    if (expression instanceof IrInlinedFunctionBlock) {
                        IrElementVisitor.DefaultImpls.visitBlock(this, expression, data.withInline(IrInlineUtilsKt.isFunctionInlining((IrInlinedFunctionBlock) expression)));
                    } else {
                        IrElementVisitor.DefaultImpls.visitBlock(this, expression, data);
                    }
                }

                /* JADX INFO: renamed from: visitFunctionExpression, reason: avoid collision after fix types in other method */
                public void visitFunctionExpression2(IrFunctionExpression expression, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data data) {
                    Intrinsics.checkNotNullParameter(expression, "expression");
                    Intrinsics.checkNotNullParameter(data, "data");
                    expression.getFunction().acceptChildren(this, data);
                }

                /* JADX INFO: renamed from: visitSimpleFunction, reason: avoid collision after fix types in other method */
                public void visitSimpleFunction2(IrSimpleFunction declaration, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data data) {
                    int counter;
                    LocalDeclarationsLowering.OwnerForLoweredDeclaration.DeclarationContainer declarationContainer;
                    Intrinsics.checkNotNullParameter(declaration, "declaration");
                    Intrinsics.checkNotNullParameter(data, "data");
                    Set<IrSimpleFunction> functionsToSkip = this.this$0.getFunctionsToSkip();
                    if (functionsToSkip == null || !functionsToSkip.contains(declaration)) {
                        IrElementVisitor.DefaultImpls.visitSimpleFunction(this, declaration, data.withInline(declaration.getIsInline()));
                        if (Intrinsics.areEqual(declaration.getVisibility(), DescriptorVisibilities.LOCAL)) {
                            LocalDeclarationsLowering.ScopeWithCounter currentClass = data.getCurrentClass();
                            if (currentClass == null) {
                                IrClass irClass3 = irClass2;
                                currentClass = irClass3 != null ? localDeclarationsLowering.getScopeWithCounter(irClass3) : null;
                                if (currentClass == null) {
                                    currentClass = localDeclarationsLowering.getScopeWithCounter(packageFragment);
                                }
                            }
                            if (declaration.getName().isSpecial() || currentClass.getUsedLocalFunctionNames().contains(declaration.getName())) {
                                counter = currentClass.getCounter();
                                currentClass.setCounter(counter + 1);
                            } else {
                                counter = -1;
                            }
                            LocalDeclarationsLowering.ScopeWithCounter currentClass2 = data.getCurrentClass();
                            if (currentClass2 != null) {
                                IrElement irElement2 = currentClass2.getIrElement();
                                Intrinsics.checkNotNull(irElement2, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrDeclarationContainer");
                                declarationContainer = new LocalDeclarationsLowering.OwnerForLoweredDeclaration.DeclarationContainer((IrDeclarationContainer) irElement2);
                            } else {
                                IrElement irElement3 = this.this$0.getIrElement();
                                if ((irElement3 instanceof IrBlock ? (IrBlock) irElement3 : null) != null) {
                                    LocalDeclarationsLowering.LocalDeclarationsTransformer localDeclarationsTransformer = this.this$0;
                                    IrBlock irBlock = (IrBlock) localDeclarationsTransformer.getIrElement();
                                    IrDeclarationParent closestParent = localDeclarationsTransformer.getClosestParent();
                                    Intrinsics.checkNotNull(closestParent);
                                    declarationContainer = new LocalDeclarationsLowering.OwnerForLoweredDeclaration.Block(irBlock, closestParent);
                                } else {
                                    IrElement irElement4 = currentClass.getIrElement();
                                    Intrinsics.checkNotNull(irElement4, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrDeclarationContainer");
                                    declarationContainer = new LocalDeclarationsLowering.OwnerForLoweredDeclaration.DeclarationContainer((IrDeclarationContainer) irElement4);
                                }
                            }
                            this.this$0.getLocalFunctions().put(declaration, new LocalDeclarationsLowering.LocalFunctionContext(declaration, counter, declarationContainer));
                            currentClass.getUsedLocalFunctionNames().add(declaration.getName());
                        }
                    }
                }

                /* JADX INFO: renamed from: visitConstructor, reason: avoid collision after fix types in other method */
                public void visitConstructor2(IrConstructor declaration, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data data) {
                    Intrinsics.checkNotNullParameter(declaration, "declaration");
                    Intrinsics.checkNotNullParameter(data, "data");
                    IrElementVisitor.DefaultImpls.visitConstructor(this, declaration, data);
                    if (LocalDeclarationsLoweringKt.isLocalNotInner(AdditionalIrUtilsKt.getConstructedClass(declaration))) {
                        this.this$0.getLocalClassConstructors().put(declaration, new LocalDeclarationsLowering.LocalClassConstructorContext(declaration, getInInlineFunctionScope(data)));
                    }
                }

                /* JADX INFO: renamed from: visitClass, reason: avoid collision after fix types in other method */
                public void visitClass2(IrClass declaration, LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data data) {
                    Intrinsics.checkNotNullParameter(declaration, "declaration");
                    Intrinsics.checkNotNullParameter(data, "data");
                    Set<IrClass> classesToLower = this.this$0.getClassesToLower();
                    if (classesToLower == null || classesToLower.contains(declaration)) {
                        IrElementVisitor.DefaultImpls.visitClass(this, declaration, data.withCurrentClass(declaration));
                        if (LocalDeclarationsLoweringKt.isLocalNotInner(declaration)) {
                            Sequence<IrConstructor> constructors = IrUtilsKt.getConstructors(declaration);
                            final LocalDeclarationsLowering.LocalDeclarationsTransformer localDeclarationsTransformer = this.this$0;
                            Sequence sequenceMapNotNull = SequencesKt.mapNotNull(constructors, new Function1<IrConstructor, LocalDeclarationsLowering.LocalClassConstructorContext>() { // from class: org.jetbrains.kotlin.backend.common.lower.LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$1$visitClass$constructorContext$1
                                {
                                    super(1);
                                }

                                @Override // kotlin.jvm.functions.Function1
                                public final LocalDeclarationsLowering.LocalClassConstructorContext invoke(IrConstructor it) {
                                    Intrinsics.checkNotNullParameter(it, "it");
                                    return localDeclarationsTransformer.getLocalClassConstructors().get(it);
                                }
                            });
                            LocalDeclarationsLowering localDeclarationsLowering2 = localDeclarationsLowering;
                            Object obj = null;
                            boolean z = false;
                            Object obj2 = null;
                            for (Object obj3 : sequenceMapNotNull) {
                                if (LowerUtilsKt.delegationKind(((LocalDeclarationsLowering.LocalClassConstructorContext) obj3).getDeclaration(), localDeclarationsLowering2.getContext().getIrBuiltIns()) == ConstructorDelegationKind.CALLS_SUPER) {
                                    if (z) {
                                        this.this$0.getLocalClasses().put(declaration, new LocalDeclarationsLowering.LocalClassContext(localDeclarationsLowering, declaration, getInInlineFunctionScope(data), (LocalDeclarationsLowering.LocalClassConstructorContext) obj));
                                    } else {
                                        z = true;
                                        obj2 = obj3;
                                    }
                                }
                            }
                            if (z) {
                                obj = obj2;
                            }
                            this.this$0.getLocalClasses().put(declaration, new LocalDeclarationsLowering.LocalClassContext(localDeclarationsLowering, declaration, getInInlineFunctionScope(data), (LocalDeclarationsLowering.LocalClassConstructorContext) obj));
                        }
                    }
                }

                private final boolean getInInlineFunctionScope(LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data) {
                    if (!localDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data.getIsInInlineFunction()) {
                        for (IrDeclaration irDeclaration2 : SequencesKt.generateSequence(this.this$0.getContainer(), new Function1<IrDeclaration, IrDeclaration>() { // from class: org.jetbrains.kotlin.backend.common.lower.LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$1$inInlineFunctionScope$1
                            @Override // kotlin.jvm.functions.Function1
                            public final IrDeclaration invoke(IrDeclaration it) {
                                Intrinsics.checkNotNullParameter(it, "it");
                                IrDeclarationParent parent2 = it.getParent();
                                if (parent2 instanceof IrDeclaration) {
                                    return (IrDeclaration) parent2;
                                }
                                return null;
                            }
                        })) {
                            if (!(irDeclaration2 instanceof IrFunction) || !((IrFunction) irDeclaration2).getIsInline()) {
                            }
                        }
                        return false;
                    }
                    return true;
                }
            }, new LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data(null, false));
        }

        private final <K, V> void putAbsentOrSame(Map<K, V> map, K k, V v) {
            V v2 = map.get(k);
            if (v2 == null) {
                map.put(k, v);
                v2 = v;
            }
            if (!Intrinsics.areEqual(v2, v)) {
                throw new IllegalStateException((v2 + " != " + v).toString());
            }
        }
    }

    /* JADX INFO: compiled from: LocalDeclarationsLowering.kt */
    @Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001BA\u0012\u0012\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003\u0012\u0012\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00070\u0003\u0012\u0012\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\t0\u0003¢\u0006\u0002\u0010\nJ\u0015\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003HÆ\u0003J\u0015\u0010\u0010\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00070\u0003HÆ\u0003J\u0015\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\t0\u0003HÆ\u0003JK\u0010\u0012\u001a\u00020\u00002\u0014\b\u0002\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u00032\u0014\b\u0002\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00070\u00032\u0014\b\u0002\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\t0\u0003HÆ\u0001J\u0013\u0010\u0013\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0016\u001a\u00020\u0017HÖ\u0001J\t\u0010\u0018\u001a\u00020\u0019HÖ\u0001R\u001d\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u001d\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\t0\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\fR\u001d\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00070\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\f¨\u0006\u001a"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/LocalDeclarationsLowering$IntermediateDatastructures;", "", "localFunctions", "", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "Lorg/jetbrains/kotlin/backend/common/lower/LocalDeclarationsLowering$LocalFunctionContext;", "newParameterToOld", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "newParameterToCaptured", "Lorg/jetbrains/kotlin/ir/symbols/IrValueSymbol;", "(Ljava/util/Map;Ljava/util/Map;Ljava/util/Map;)V", "getLocalFunctions", "()Ljava/util/Map;", "getNewParameterToCaptured", "getNewParameterToOld", "component1", "component2", "component3", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class IntermediateDatastructures {
        private final Map<IrFunction, LocalFunctionContext> localFunctions;
        private final Map<IrValueParameter, IrValueSymbol> newParameterToCaptured;
        private final Map<IrValueParameter, IrValueParameter> newParameterToOld;

        /* JADX WARN: Multi-variable type inference failed */
        public static /* synthetic */ IntermediateDatastructures copy$default(IntermediateDatastructures intermediateDatastructures, Map map, Map map2, Map map3, int i, Object obj) {
            if ((i & 1) != 0) {
                map = intermediateDatastructures.localFunctions;
            }
            if ((i & 2) != 0) {
                map2 = intermediateDatastructures.newParameterToOld;
            }
            if ((i & 4) != 0) {
                map3 = intermediateDatastructures.newParameterToCaptured;
            }
            return intermediateDatastructures.copy(map, map2, map3);
        }

        public final Map<IrFunction, LocalFunctionContext> component1() {
            return this.localFunctions;
        }

        public final Map<IrValueParameter, IrValueParameter> component2() {
            return this.newParameterToOld;
        }

        public final Map<IrValueParameter, IrValueSymbol> component3() {
            return this.newParameterToCaptured;
        }

        public final IntermediateDatastructures copy(Map<IrFunction, LocalFunctionContext> localFunctions, Map<IrValueParameter, ? extends IrValueParameter> newParameterToOld, Map<IrValueParameter, ? extends IrValueSymbol> newParameterToCaptured) {
            Intrinsics.checkNotNullParameter(localFunctions, "localFunctions");
            Intrinsics.checkNotNullParameter(newParameterToOld, "newParameterToOld");
            Intrinsics.checkNotNullParameter(newParameterToCaptured, "newParameterToCaptured");
            return new IntermediateDatastructures(localFunctions, newParameterToOld, newParameterToCaptured);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof IntermediateDatastructures)) {
                return false;
            }
            IntermediateDatastructures intermediateDatastructures = (IntermediateDatastructures) other;
            return Intrinsics.areEqual(this.localFunctions, intermediateDatastructures.localFunctions) && Intrinsics.areEqual(this.newParameterToOld, intermediateDatastructures.newParameterToOld) && Intrinsics.areEqual(this.newParameterToCaptured, intermediateDatastructures.newParameterToCaptured);
        }

        public int hashCode() {
            return (((this.localFunctions.hashCode() * 31) + this.newParameterToOld.hashCode()) * 31) + this.newParameterToCaptured.hashCode();
        }

        public String toString() {
            return "IntermediateDatastructures(localFunctions=" + this.localFunctions + ", newParameterToOld=" + this.newParameterToOld + ", newParameterToCaptured=" + this.newParameterToCaptured + ')';
        }

        /* JADX WARN: Multi-variable type inference failed */
        public IntermediateDatastructures(Map<IrFunction, LocalFunctionContext> localFunctions, Map<IrValueParameter, ? extends IrValueParameter> newParameterToOld, Map<IrValueParameter, ? extends IrValueSymbol> newParameterToCaptured) {
            Intrinsics.checkNotNullParameter(localFunctions, "localFunctions");
            Intrinsics.checkNotNullParameter(newParameterToOld, "newParameterToOld");
            Intrinsics.checkNotNullParameter(newParameterToCaptured, "newParameterToCaptured");
            this.localFunctions = localFunctions;
            this.newParameterToOld = newParameterToOld;
            this.newParameterToCaptured = newParameterToCaptured;
        }

        public final Map<IrFunction, LocalFunctionContext> getLocalFunctions() {
            return this.localFunctions;
        }

        public final Map<IrValueParameter, IrValueParameter> getNewParameterToOld() {
            return this.newParameterToOld;
        }

        public final Map<IrValueParameter, IrValueSymbol> getNewParameterToCaptured() {
            return this.newParameterToCaptured;
        }
    }
}
