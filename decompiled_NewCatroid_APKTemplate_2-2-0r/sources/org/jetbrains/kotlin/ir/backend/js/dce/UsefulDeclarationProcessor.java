package org.jetbrains.kotlin.ir.backend.js.dce;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.google.android.gms.cast.MediaTrack;
import com.reandroid.apkeditor.Options;
import java.io.File;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.io.FilesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KAnnotatedElement;
import kotlin.reflect.KFunction;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.backend.js.JsCommonBackendContext;
import org.jetbrains.kotlin.ir.backend.js.utils.AnnotationUtilsKt;
import org.jetbrains.kotlin.ir.declarations.IrAnonymousInitializer;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationBase;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrEnumEntry;
import org.jetbrains.kotlin.ir.declarations.IrErrorDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrExternalPackageFragment;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrLocalDelegatedProperty;
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment;
import org.jetbrains.kotlin.ir.declarations.IrOverridableDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrPackageFragment;
import org.jetbrains.kotlin.ir.declarations.IrPossiblyExternalDeclaration;
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
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrClassifierSymbol;
import org.jetbrains.kotlin.ir.symbols.IrPropertySymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.AdditionalIrUtilsKt;
import org.jetbrains.kotlin.ir.util.IrInlineUtilsKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.visitors.IrElementVisitor;

/* JADX INFO: compiled from: UsefulDeclarationProcessor.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u009a\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0010\u0002\n\u0002\b\u0007\n\u0002\u0010\"\n\u0000\n\u0002\u0010\u001c\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\b&\u0018\u00002\u00020\u0001:\u0001UB!\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\u0002\u0010\u0007J\u0010\u0010.\u001a\u00020/2\u0006\u00100\u001a\u00020\u000eH\u0014J(\u00101\u001a\u00020/2\u0006\u00102\u001a\u00020\u001e2\u0006\u00103\u001a\u00020\u001e2\u0006\u00104\u001a\u00020\u00062\u0006\u00105\u001a\u00020\u0003H\u0002J\"\u00106\u001a\b\u0012\u0004\u0012\u00020\u001e072\f\u00108\u001a\b\u0012\u0004\u0012\u00020\u001e092\u0006\u0010:\u001a\u00020;J\u0010\u0010<\u001a\u00020\u001e2\u0006\u0010=\u001a\u00020\u0006H\u0004J\b\u0010>\u001a\u00020/H\u0014J\u0010\u0010?\u001a\u00020\u00032\u0006\u0010@\u001a\u00020\u001eH$J\u0010\u0010A\u001a\u00020/2\u0006\u00100\u001a\u00020\u000eH\u0014J\u0010\u0010B\u001a\u00020/2\u0006\u0010@\u001a\u00020\u001eH\u0014J\u0010\u0010C\u001a\u00020/2\u0006\u0010D\u001a\u00020EH\u0014J\u0010\u0010F\u001a\u00020/2\u0006\u0010G\u001a\u00020HH\u0014J\u0010\u0010I\u001a\u00020/2\u0006\u0010J\u001a\u00020KH\u0014J\u0010\u0010L\u001a\u00020/2\u0006\u00100\u001a\u00020\u000eH\u0014J\f\u0010M\u001a\u00020/*\u00020\u001eH\u0002J&\u0010N\u001a\u00020/*\u00020\u001e2\u0006\u00102\u001a\u00020\u001e2\u0006\u00104\u001a\u00020\u00062\b\b\u0002\u0010O\u001a\u00020\u0003H\u0004J\f\u0010P\u001a\u00020\u0003*\u00020KH\u0004J\f\u0010Q\u001a\u00020\u0003*\u00020RH\u0004J\f\u0010S\u001a\u00020\u0003*\u00020RH\u0004J\f\u0010T\u001a\u00020\u0003*\u00020\u001eH\u0004R\u0016\u0010\b\u001a\u00060\tR\u00020\u0000X¤\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR$\u0010\f\u001a\u0012\u0012\u0004\u0012\u00020\u000e0\rj\b\u0012\u0004\u0012\u00020\u000e`\u000fX\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R$\u0010\u0012\u001a\u0012\u0012\u0004\u0012\u00020\u000e0\rj\b\u0012\u0004\u0012\u00020\u000e`\u000fX\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0011R&\u0010\u0014\u001a\u001a\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00160\u0015j\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0016`\u0017X\u0082\u0004¢\u0006\u0002\n\u0000R\u0012\u0010\u0018\u001a\u00020\u0019X¦\u0004¢\u0006\u0006\u001a\u0004\b\u001a\u0010\u001bR\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001e0\u001dX\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u001f\u001a\n\u0012\u0004\u0012\u00020!\u0018\u00010 X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0004\u001a\u00020\u0003X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\"\u0010#R$\u0010$\u001a\u0012\u0012\u0004\u0012\u00020\u001e0\u0015j\b\u0012\u0004\u0012\u00020\u001e`\u0017X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b%\u0010&R\u001b\u0010'\u001a\u00020\u001e8DX\u0084\u0084\u0002¢\u0006\f\n\u0004\b*\u0010+\u001a\u0004\b(\u0010)R!\u0010,\u001a\u0012\u0012\u0004\u0012\u00020\u001e0\u0015j\b\u0012\u0004\u0012\u00020\u001e`\u0017¢\u0006\b\n\u0000\u001a\u0004\b-\u0010&¨\u0006V"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/dce/UsefulDeclarationProcessor;", "", "printReachabilityInfo", "", "removeUnusedAssociatedObjects", "dumpReachabilityInfoToFile", "", "(ZZLjava/lang/String;)V", "bodyVisitor", "Lorg/jetbrains/kotlin/ir/backend/js/dce/UsefulDeclarationProcessor$BodyVisitorBase;", "getBodyVisitor", "()Lorg/jetbrains/kotlin/ir/backend/js/dce/UsefulDeclarationProcessor$BodyVisitorBase;", "classesWithObjectAssociations", "Ljava/util/LinkedHashSet;", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "Lkotlin/collections/LinkedHashSet;", "getClassesWithObjectAssociations", "()Ljava/util/LinkedHashSet;", "constructedClasses", "getConstructedClasses", "contagiousReachableDeclarations", "Ljava/util/HashSet;", "Lorg/jetbrains/kotlin/ir/declarations/IrOverridableDeclaration;", "Lkotlin/collections/HashSet;", "context", "Lorg/jetbrains/kotlin/ir/backend/js/JsCommonBackendContext;", "getContext", "()Lorg/jetbrains/kotlin/ir/backend/js/JsCommonBackendContext;", "queue", "Ljava/util/ArrayDeque;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "reachabilityInfos", "", "Lorg/jetbrains/kotlin/ir/backend/js/dce/ReachabilityInfo;", "getRemoveUnusedAssociatedObjects", "()Z", "result", "getResult", "()Ljava/util/HashSet;", "toStringMethod", "getToStringMethod", "()Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "toStringMethod$delegate", "Lkotlin/Lazy;", "usefulPolyfilledDeclarations", "getUsefulPolyfilledDeclarations", "addConstructedClass", "", "irClass", "addReachabilityInfoIfNeeded", TypedValues.TransitionType.S_FROM, "to", MediaTrack.ROLE_DESCRIPTION, "isContagiousOverridableDeclaration", "collectDeclarations", "", "rootDeclarations", "", "dceDumpNameCache", "Lorg/jetbrains/kotlin/ir/backend/js/dce/DceDumpNameCache;", "getMethodOfAny", "name", "handleAssociatedObjects", "isExported", "declaration", "processClass", "processConstructedClassDeclaration", "processConstructor", "irConstructor", "Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "processField", "irField", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "processSimpleFunction", "irFunction", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "processSuperTypes", "addToUsefulPolyfilledDeclarations", "enqueue", "isContagious", "isAccessorForOverriddenExternalField", "isExternalOrOverriddenExternal", "Lorg/jetbrains/kotlin/ir/declarations/IrProperty;", "isOverriddenExternal", "isReachable", "BodyVisitorBase", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class UsefulDeclarationProcessor {
    private final LinkedHashSet<IrClass> classesWithObjectAssociations;
    private final LinkedHashSet<IrClass> constructedClasses;
    private final HashSet<IrOverridableDeclaration<?>> contagiousReachableDeclarations;
    private final String dumpReachabilityInfoToFile;
    private final boolean printReachabilityInfo;
    private final ArrayDeque<IrDeclaration> queue;
    private final List<ReachabilityInfo> reachabilityInfos;
    private final boolean removeUnusedAssociatedObjects;
    private final HashSet<IrDeclaration> result;

    /* JADX INFO: renamed from: toStringMethod$delegate, reason: from kotlin metadata */
    private final Lazy toStringMethod;
    private final HashSet<IrDeclaration> usefulPolyfilledDeclarations;

    protected abstract BodyVisitorBase getBodyVisitor();

    public abstract JsCommonBackendContext getContext();

    protected void handleAssociatedObjects() {
    }

    protected abstract boolean isExported(IrDeclaration declaration);

    protected void processField(IrField irField) {
        Intrinsics.checkNotNullParameter(irField, "irField");
    }

    public UsefulDeclarationProcessor(boolean z, boolean z2, String str) {
        this.printReachabilityInfo = z;
        this.removeUnusedAssociatedObjects = z2;
        this.dumpReachabilityInfoToFile = str;
        this.toStringMethod = LazyKt.lazy(LazyThreadSafetyMode.NONE, (Function0) new Function0<IrDeclaration>() { // from class: org.jetbrains.kotlin.ir.backend.js.dce.UsefulDeclarationProcessor$toStringMethod$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final IrDeclaration invoke() {
                return this.this$0.getMethodOfAny("toString");
            }
        });
        this.contagiousReachableDeclarations = new HashSet<>();
        this.constructedClasses = new LinkedHashSet<>();
        this.reachabilityInfos = (z || str != null) ? new ArrayList() : null;
        this.queue = new ArrayDeque<>();
        this.result = new HashSet<>();
        this.classesWithObjectAssociations = new LinkedHashSet<>();
        this.usefulPolyfilledDeclarations = new HashSet<>();
    }

    public /* synthetic */ UsefulDeclarationProcessor(boolean z, boolean z2, String str, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(z, z2, (i & 4) != 0 ? null : str);
    }

    protected final boolean getRemoveUnusedAssociatedObjects() {
        return this.removeUnusedAssociatedObjects;
    }

    protected final IrDeclaration getMethodOfAny(String name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        List<IrDeclaration> declarations = getContext().getIrBuiltIns().getAnyClass().getOwner().getDeclarations();
        ArrayList arrayList = new ArrayList();
        for (Object obj : declarations) {
            if (obj instanceof IrFunction) {
                arrayList.add(obj);
            }
        }
        Object obj2 = null;
        boolean z = false;
        for (Object obj3 : arrayList) {
            if (Intrinsics.areEqual(((IrFunction) obj3).getName().asString(), name2)) {
                if (z) {
                    throw new IllegalArgumentException("Collection contains more than one matching element.");
                }
                z = true;
                obj2 = obj3;
            }
        }
        if (!z) {
            throw new NoSuchElementException("Collection contains no element matching the predicate.");
        }
        return (IrDeclaration) obj2;
    }

    protected final IrDeclaration getToStringMethod() {
        return (IrDeclaration) this.toStringMethod.getValue();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX INFO: compiled from: UsefulDeclarationProcessor.kt */
    @Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b¤\u0004\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001B\u0005¢\u0006\u0002\u0010\u0004J\u0018\u0010\u0005\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0003H\u0016J\u0018\u0010\t\u001a\u00020\u00022\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\b\u001a\u00020\u0003H\u0016J\u0018\u0010\f\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\r2\u0006\u0010\b\u001a\u00020\u0003H\u0016J\u0018\u0010\u000e\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u000f2\u0006\u0010\b\u001a\u00020\u0003H\u0016J\u0018\u0010\u0010\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u00112\u0006\u0010\b\u001a\u00020\u0003H\u0016J\u0018\u0010\u0012\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u00132\u0006\u0010\b\u001a\u00020\u0003H\u0016J\u0018\u0010\u0014\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u00152\u0006\u0010\b\u001a\u00020\u0003H\u0016¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/dce/UsefulDeclarationProcessor$BodyVisitorBase;", "Lorg/jetbrains/kotlin/ir/visitors/IrElementVisitor;", "", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "(Lorg/jetbrains/kotlin/ir/backend/js/dce/UsefulDeclarationProcessor;)V", "visitBlock", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrBlock;", "data", "visitElement", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/ir/IrElement;", "visitFieldAccess", "Lorg/jetbrains/kotlin/ir/expressions/IrFieldAccessExpression;", "visitFunctionAccess", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionAccessExpression;", "visitRawFunctionReference", "Lorg/jetbrains/kotlin/ir/expressions/IrRawFunctionReference;", "visitStringConcatenation", "Lorg/jetbrains/kotlin/ir/expressions/IrStringConcatenation;", "visitValueAccess", "Lorg/jetbrains/kotlin/ir/expressions/IrValueAccessExpression;", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public abstract class BodyVisitorBase implements IrElementVisitor<Unit, IrDeclaration> {
        public BodyVisitorBase() {
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitAnonymousInitializer */
        public /* bridge */ /* synthetic */ Unit visitAnonymousInitializer2(IrAnonymousInitializer irAnonymousInitializer, IrDeclaration irDeclaration) {
            visitAnonymousInitializer2(irAnonymousInitializer, irDeclaration);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitAnonymousInitializer, reason: avoid collision after fix types in other method */
        public void visitAnonymousInitializer2(IrAnonymousInitializer irAnonymousInitializer, IrDeclaration irDeclaration) {
            IrElementVisitor.DefaultImpls.visitAnonymousInitializer(this, irAnonymousInitializer, irDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitBlock */
        public /* bridge */ /* synthetic */ Unit visitBlock2(IrBlock irBlock, IrDeclaration irDeclaration) {
            visitBlock2(irBlock, irDeclaration);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitBlockBody */
        public /* bridge */ /* synthetic */ Unit visitBlockBody2(IrBlockBody irBlockBody, IrDeclaration irDeclaration) {
            visitBlockBody2(irBlockBody, irDeclaration);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitBlockBody, reason: avoid collision after fix types in other method */
        public void visitBlockBody2(IrBlockBody irBlockBody, IrDeclaration irDeclaration) {
            IrElementVisitor.DefaultImpls.visitBlockBody(this, irBlockBody, irDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitBody */
        public /* bridge */ /* synthetic */ Unit visitBody2(IrBody irBody, IrDeclaration irDeclaration) {
            visitBody2(irBody, irDeclaration);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitBody, reason: avoid collision after fix types in other method */
        public void visitBody2(IrBody irBody, IrDeclaration irDeclaration) {
            IrElementVisitor.DefaultImpls.visitBody(this, irBody, irDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitBranch */
        public /* bridge */ /* synthetic */ Unit visitBranch2(IrBranch irBranch, IrDeclaration irDeclaration) {
            visitBranch2(irBranch, irDeclaration);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitBranch, reason: avoid collision after fix types in other method */
        public void visitBranch2(IrBranch irBranch, IrDeclaration irDeclaration) {
            IrElementVisitor.DefaultImpls.visitBranch(this, irBranch, irDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitBreak */
        public /* bridge */ /* synthetic */ Unit visitBreak2(IrBreak irBreak, IrDeclaration irDeclaration) {
            visitBreak2(irBreak, irDeclaration);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitBreak, reason: avoid collision after fix types in other method */
        public void visitBreak2(IrBreak irBreak, IrDeclaration irDeclaration) {
            IrElementVisitor.DefaultImpls.visitBreak(this, irBreak, irDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitBreakContinue */
        public /* bridge */ /* synthetic */ Unit visitBreakContinue2(IrBreakContinue irBreakContinue, IrDeclaration irDeclaration) {
            visitBreakContinue2(irBreakContinue, irDeclaration);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitBreakContinue, reason: avoid collision after fix types in other method */
        public void visitBreakContinue2(IrBreakContinue irBreakContinue, IrDeclaration irDeclaration) {
            IrElementVisitor.DefaultImpls.visitBreakContinue(this, irBreakContinue, irDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitCall */
        public /* bridge */ /* synthetic */ Unit visitCall2(IrCall irCall, IrDeclaration irDeclaration) {
            visitCall(irCall, irDeclaration);
            return Unit.INSTANCE;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        public void visitCall(IrCall irCall, IrDeclaration irDeclaration) {
            IrElementVisitor.DefaultImpls.visitCall(this, irCall, irDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public /* bridge */ /* synthetic */ Unit visitCallableReference(IrCallableReference irCallableReference, IrDeclaration irDeclaration) {
            visitCallableReference2((IrCallableReference<?>) irCallableReference, irDeclaration);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitCallableReference, reason: avoid collision after fix types in other method */
        public void visitCallableReference2(IrCallableReference<?> irCallableReference, IrDeclaration irDeclaration) {
            IrElementVisitor.DefaultImpls.visitCallableReference(this, irCallableReference, irDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitCatch */
        public /* bridge */ /* synthetic */ Unit visitCatch2(IrCatch irCatch, IrDeclaration irDeclaration) {
            visitCatch2(irCatch, irDeclaration);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitCatch, reason: avoid collision after fix types in other method */
        public void visitCatch2(IrCatch irCatch, IrDeclaration irDeclaration) {
            IrElementVisitor.DefaultImpls.visitCatch(this, irCatch, irDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitClass */
        public /* bridge */ /* synthetic */ Unit visitClass2(IrClass irClass, IrDeclaration irDeclaration) {
            visitClass2(irClass, irDeclaration);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitClass, reason: avoid collision after fix types in other method */
        public void visitClass2(IrClass irClass, IrDeclaration irDeclaration) {
            IrElementVisitor.DefaultImpls.visitClass(this, irClass, irDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitClassReference */
        public /* bridge */ /* synthetic */ Unit visitClassReference2(IrClassReference irClassReference, IrDeclaration irDeclaration) {
            visitClassReference2(irClassReference, irDeclaration);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitClassReference, reason: avoid collision after fix types in other method */
        public void visitClassReference2(IrClassReference irClassReference, IrDeclaration irDeclaration) {
            IrElementVisitor.DefaultImpls.visitClassReference(this, irClassReference, irDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitComposite */
        public /* bridge */ /* synthetic */ Unit visitComposite2(IrComposite irComposite, IrDeclaration irDeclaration) {
            visitComposite2(irComposite, irDeclaration);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitComposite, reason: avoid collision after fix types in other method */
        public void visitComposite2(IrComposite irComposite, IrDeclaration irDeclaration) {
            IrElementVisitor.DefaultImpls.visitComposite(this, irComposite, irDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public /* bridge */ /* synthetic */ Unit visitConst(IrConst irConst, IrDeclaration irDeclaration) {
            visitConst((IrConst<?>) irConst, irDeclaration);
            return Unit.INSTANCE;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        public void visitConst(IrConst<?> irConst, IrDeclaration irDeclaration) {
            IrElementVisitor.DefaultImpls.visitConst(this, irConst, irDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitConstantArray */
        public /* bridge */ /* synthetic */ Unit visitConstantArray2(IrConstantArray irConstantArray, IrDeclaration irDeclaration) {
            visitConstantArray2(irConstantArray, irDeclaration);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitConstantArray, reason: avoid collision after fix types in other method */
        public void visitConstantArray2(IrConstantArray irConstantArray, IrDeclaration irDeclaration) {
            IrElementVisitor.DefaultImpls.visitConstantArray(this, irConstantArray, irDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitConstantObject */
        public /* bridge */ /* synthetic */ Unit visitConstantObject2(IrConstantObject irConstantObject, IrDeclaration irDeclaration) {
            visitConstantObject2(irConstantObject, irDeclaration);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitConstantObject, reason: avoid collision after fix types in other method */
        public void visitConstantObject2(IrConstantObject irConstantObject, IrDeclaration irDeclaration) {
            IrElementVisitor.DefaultImpls.visitConstantObject(this, irConstantObject, irDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitConstantPrimitive */
        public /* bridge */ /* synthetic */ Unit visitConstantPrimitive2(IrConstantPrimitive irConstantPrimitive, IrDeclaration irDeclaration) {
            visitConstantPrimitive2(irConstantPrimitive, irDeclaration);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitConstantPrimitive, reason: avoid collision after fix types in other method */
        public void visitConstantPrimitive2(IrConstantPrimitive irConstantPrimitive, IrDeclaration irDeclaration) {
            IrElementVisitor.DefaultImpls.visitConstantPrimitive(this, irConstantPrimitive, irDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitConstantValue */
        public /* bridge */ /* synthetic */ Unit visitConstantValue2(IrConstantValue irConstantValue, IrDeclaration irDeclaration) {
            visitConstantValue2(irConstantValue, irDeclaration);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitConstantValue, reason: avoid collision after fix types in other method */
        public void visitConstantValue2(IrConstantValue irConstantValue, IrDeclaration irDeclaration) {
            IrElementVisitor.DefaultImpls.visitConstantValue(this, irConstantValue, irDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitConstructor */
        public /* bridge */ /* synthetic */ Unit visitConstructor2(IrConstructor irConstructor, IrDeclaration irDeclaration) {
            visitConstructor2(irConstructor, irDeclaration);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitConstructor, reason: avoid collision after fix types in other method */
        public void visitConstructor2(IrConstructor irConstructor, IrDeclaration irDeclaration) {
            IrElementVisitor.DefaultImpls.visitConstructor(this, irConstructor, irDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitConstructorCall */
        public /* bridge */ /* synthetic */ Unit visitConstructorCall2(IrConstructorCall irConstructorCall, IrDeclaration irDeclaration) {
            visitConstructorCall2(irConstructorCall, irDeclaration);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitConstructorCall, reason: avoid collision after fix types in other method */
        public void visitConstructorCall2(IrConstructorCall irConstructorCall, IrDeclaration irDeclaration) {
            IrElementVisitor.DefaultImpls.visitConstructorCall(this, irConstructorCall, irDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitContainerExpression */
        public /* bridge */ /* synthetic */ Unit visitContainerExpression2(IrContainerExpression irContainerExpression, IrDeclaration irDeclaration) {
            visitContainerExpression2(irContainerExpression, irDeclaration);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitContainerExpression, reason: avoid collision after fix types in other method */
        public void visitContainerExpression2(IrContainerExpression irContainerExpression, IrDeclaration irDeclaration) {
            IrElementVisitor.DefaultImpls.visitContainerExpression(this, irContainerExpression, irDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitContinue */
        public /* bridge */ /* synthetic */ Unit visitContinue2(IrContinue irContinue, IrDeclaration irDeclaration) {
            visitContinue2(irContinue, irDeclaration);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitContinue, reason: avoid collision after fix types in other method */
        public void visitContinue2(IrContinue irContinue, IrDeclaration irDeclaration) {
            IrElementVisitor.DefaultImpls.visitContinue(this, irContinue, irDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitDeclaration */
        public /* bridge */ /* synthetic */ Unit visitDeclaration2(IrDeclarationBase irDeclarationBase, IrDeclaration irDeclaration) {
            visitDeclaration2(irDeclarationBase, irDeclaration);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitDeclaration, reason: avoid collision after fix types in other method */
        public void visitDeclaration2(IrDeclarationBase irDeclarationBase, IrDeclaration irDeclaration) {
            IrElementVisitor.DefaultImpls.visitDeclaration(this, irDeclarationBase, irDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitDeclarationReference */
        public /* bridge */ /* synthetic */ Unit visitDeclarationReference2(IrDeclarationReference irDeclarationReference, IrDeclaration irDeclaration) {
            visitDeclarationReference2(irDeclarationReference, irDeclaration);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitDeclarationReference, reason: avoid collision after fix types in other method */
        public void visitDeclarationReference2(IrDeclarationReference irDeclarationReference, IrDeclaration irDeclaration) {
            IrElementVisitor.DefaultImpls.visitDeclarationReference(this, irDeclarationReference, irDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitDelegatingConstructorCall */
        public /* bridge */ /* synthetic */ Unit visitDelegatingConstructorCall2(IrDelegatingConstructorCall irDelegatingConstructorCall, IrDeclaration irDeclaration) {
            visitDelegatingConstructorCall2(irDelegatingConstructorCall, irDeclaration);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitDelegatingConstructorCall, reason: avoid collision after fix types in other method */
        public void visitDelegatingConstructorCall2(IrDelegatingConstructorCall irDelegatingConstructorCall, IrDeclaration irDeclaration) {
            IrElementVisitor.DefaultImpls.visitDelegatingConstructorCall(this, irDelegatingConstructorCall, irDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitDoWhileLoop */
        public /* bridge */ /* synthetic */ Unit visitDoWhileLoop2(IrDoWhileLoop irDoWhileLoop, IrDeclaration irDeclaration) {
            visitDoWhileLoop2(irDoWhileLoop, irDeclaration);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitDoWhileLoop, reason: avoid collision after fix types in other method */
        public void visitDoWhileLoop2(IrDoWhileLoop irDoWhileLoop, IrDeclaration irDeclaration) {
            IrElementVisitor.DefaultImpls.visitDoWhileLoop(this, irDoWhileLoop, irDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitDynamicExpression */
        public /* bridge */ /* synthetic */ Unit visitDynamicExpression2(IrDynamicExpression irDynamicExpression, IrDeclaration irDeclaration) {
            visitDynamicExpression2(irDynamicExpression, irDeclaration);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitDynamicExpression, reason: avoid collision after fix types in other method */
        public void visitDynamicExpression2(IrDynamicExpression irDynamicExpression, IrDeclaration irDeclaration) {
            IrElementVisitor.DefaultImpls.visitDynamicExpression(this, irDynamicExpression, irDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitDynamicMemberExpression */
        public /* bridge */ /* synthetic */ Unit visitDynamicMemberExpression2(IrDynamicMemberExpression irDynamicMemberExpression, IrDeclaration irDeclaration) {
            visitDynamicMemberExpression2(irDynamicMemberExpression, irDeclaration);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitDynamicMemberExpression, reason: avoid collision after fix types in other method */
        public void visitDynamicMemberExpression2(IrDynamicMemberExpression irDynamicMemberExpression, IrDeclaration irDeclaration) {
            IrElementVisitor.DefaultImpls.visitDynamicMemberExpression(this, irDynamicMemberExpression, irDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitDynamicOperatorExpression */
        public /* bridge */ /* synthetic */ Unit visitDynamicOperatorExpression2(IrDynamicOperatorExpression irDynamicOperatorExpression, IrDeclaration irDeclaration) {
            visitDynamicOperatorExpression2(irDynamicOperatorExpression, irDeclaration);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitDynamicOperatorExpression, reason: avoid collision after fix types in other method */
        public void visitDynamicOperatorExpression2(IrDynamicOperatorExpression irDynamicOperatorExpression, IrDeclaration irDeclaration) {
            IrElementVisitor.DefaultImpls.visitDynamicOperatorExpression(this, irDynamicOperatorExpression, irDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitElement */
        public /* bridge */ /* synthetic */ Unit visitElement2(IrElement irElement, IrDeclaration irDeclaration) {
            visitElement2(irElement, irDeclaration);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitElseBranch */
        public /* bridge */ /* synthetic */ Unit visitElseBranch2(IrElseBranch irElseBranch, IrDeclaration irDeclaration) {
            visitElseBranch2(irElseBranch, irDeclaration);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitElseBranch, reason: avoid collision after fix types in other method */
        public void visitElseBranch2(IrElseBranch irElseBranch, IrDeclaration irDeclaration) {
            IrElementVisitor.DefaultImpls.visitElseBranch(this, irElseBranch, irDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitEnumConstructorCall */
        public /* bridge */ /* synthetic */ Unit visitEnumConstructorCall2(IrEnumConstructorCall irEnumConstructorCall, IrDeclaration irDeclaration) {
            visitEnumConstructorCall2(irEnumConstructorCall, irDeclaration);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitEnumConstructorCall, reason: avoid collision after fix types in other method */
        public void visitEnumConstructorCall2(IrEnumConstructorCall irEnumConstructorCall, IrDeclaration irDeclaration) {
            IrElementVisitor.DefaultImpls.visitEnumConstructorCall(this, irEnumConstructorCall, irDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitEnumEntry */
        public /* bridge */ /* synthetic */ Unit visitEnumEntry2(IrEnumEntry irEnumEntry, IrDeclaration irDeclaration) {
            visitEnumEntry2(irEnumEntry, irDeclaration);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitEnumEntry, reason: avoid collision after fix types in other method */
        public void visitEnumEntry2(IrEnumEntry irEnumEntry, IrDeclaration irDeclaration) {
            IrElementVisitor.DefaultImpls.visitEnumEntry(this, irEnumEntry, irDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitErrorCallExpression */
        public /* bridge */ /* synthetic */ Unit visitErrorCallExpression2(IrErrorCallExpression irErrorCallExpression, IrDeclaration irDeclaration) {
            visitErrorCallExpression2(irErrorCallExpression, irDeclaration);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitErrorCallExpression, reason: avoid collision after fix types in other method */
        public void visitErrorCallExpression2(IrErrorCallExpression irErrorCallExpression, IrDeclaration irDeclaration) {
            IrElementVisitor.DefaultImpls.visitErrorCallExpression(this, irErrorCallExpression, irDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitErrorDeclaration */
        public /* bridge */ /* synthetic */ Unit visitErrorDeclaration2(IrErrorDeclaration irErrorDeclaration, IrDeclaration irDeclaration) {
            visitErrorDeclaration2(irErrorDeclaration, irDeclaration);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitErrorDeclaration, reason: avoid collision after fix types in other method */
        public void visitErrorDeclaration2(IrErrorDeclaration irErrorDeclaration, IrDeclaration irDeclaration) {
            IrElementVisitor.DefaultImpls.visitErrorDeclaration(this, irErrorDeclaration, irDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitErrorExpression */
        public /* bridge */ /* synthetic */ Unit visitErrorExpression2(IrErrorExpression irErrorExpression, IrDeclaration irDeclaration) {
            visitErrorExpression2(irErrorExpression, irDeclaration);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitErrorExpression, reason: avoid collision after fix types in other method */
        public void visitErrorExpression2(IrErrorExpression irErrorExpression, IrDeclaration irDeclaration) {
            IrElementVisitor.DefaultImpls.visitErrorExpression(this, irErrorExpression, irDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitExpression */
        public /* bridge */ /* synthetic */ Unit visitExpression2(IrExpression irExpression, IrDeclaration irDeclaration) {
            visitExpression2(irExpression, irDeclaration);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitExpression, reason: avoid collision after fix types in other method */
        public void visitExpression2(IrExpression irExpression, IrDeclaration irDeclaration) {
            IrElementVisitor.DefaultImpls.visitExpression(this, irExpression, irDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitExpressionBody */
        public /* bridge */ /* synthetic */ Unit visitExpressionBody2(IrExpressionBody irExpressionBody, IrDeclaration irDeclaration) {
            visitExpressionBody2(irExpressionBody, irDeclaration);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitExpressionBody, reason: avoid collision after fix types in other method */
        public void visitExpressionBody2(IrExpressionBody irExpressionBody, IrDeclaration irDeclaration) {
            IrElementVisitor.DefaultImpls.visitExpressionBody(this, irExpressionBody, irDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitExternalPackageFragment */
        public /* bridge */ /* synthetic */ Unit visitExternalPackageFragment2(IrExternalPackageFragment irExternalPackageFragment, IrDeclaration irDeclaration) {
            visitExternalPackageFragment2(irExternalPackageFragment, irDeclaration);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitExternalPackageFragment, reason: avoid collision after fix types in other method */
        public void visitExternalPackageFragment2(IrExternalPackageFragment irExternalPackageFragment, IrDeclaration irDeclaration) {
            IrElementVisitor.DefaultImpls.visitExternalPackageFragment(this, irExternalPackageFragment, irDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitField */
        public /* bridge */ /* synthetic */ Unit visitField2(IrField irField, IrDeclaration irDeclaration) {
            visitField2(irField, irDeclaration);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitField, reason: avoid collision after fix types in other method */
        public void visitField2(IrField irField, IrDeclaration irDeclaration) {
            IrElementVisitor.DefaultImpls.visitField(this, irField, irDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitFieldAccess */
        public /* bridge */ /* synthetic */ Unit visitFieldAccess2(IrFieldAccessExpression irFieldAccessExpression, IrDeclaration irDeclaration) {
            visitFieldAccess2(irFieldAccessExpression, irDeclaration);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitFile */
        public /* bridge */ /* synthetic */ Unit visitFile2(IrFile irFile, IrDeclaration irDeclaration) {
            visitFile2(irFile, irDeclaration);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitFile, reason: avoid collision after fix types in other method */
        public void visitFile2(IrFile irFile, IrDeclaration irDeclaration) {
            IrElementVisitor.DefaultImpls.visitFile(this, irFile, irDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitFunction */
        public /* bridge */ /* synthetic */ Unit visitFunction2(IrFunction irFunction, IrDeclaration irDeclaration) {
            visitFunction2(irFunction, irDeclaration);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitFunction, reason: avoid collision after fix types in other method */
        public void visitFunction2(IrFunction irFunction, IrDeclaration irDeclaration) {
            IrElementVisitor.DefaultImpls.visitFunction(this, irFunction, irDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitFunctionAccess */
        public /* bridge */ /* synthetic */ Unit visitFunctionAccess2(IrFunctionAccessExpression irFunctionAccessExpression, IrDeclaration irDeclaration) {
            visitFunctionAccess2(irFunctionAccessExpression, irDeclaration);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitFunctionExpression */
        public /* bridge */ /* synthetic */ Unit visitFunctionExpression2(IrFunctionExpression irFunctionExpression, IrDeclaration irDeclaration) {
            visitFunctionExpression2(irFunctionExpression, irDeclaration);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitFunctionExpression, reason: avoid collision after fix types in other method */
        public void visitFunctionExpression2(IrFunctionExpression irFunctionExpression, IrDeclaration irDeclaration) {
            IrElementVisitor.DefaultImpls.visitFunctionExpression(this, irFunctionExpression, irDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitFunctionReference */
        public /* bridge */ /* synthetic */ Unit visitFunctionReference2(IrFunctionReference irFunctionReference, IrDeclaration irDeclaration) {
            visitFunctionReference2(irFunctionReference, irDeclaration);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitFunctionReference, reason: avoid collision after fix types in other method */
        public void visitFunctionReference2(IrFunctionReference irFunctionReference, IrDeclaration irDeclaration) {
            IrElementVisitor.DefaultImpls.visitFunctionReference(this, irFunctionReference, irDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitGetClass */
        public /* bridge */ /* synthetic */ Unit visitGetClass2(IrGetClass irGetClass, IrDeclaration irDeclaration) {
            visitGetClass2(irGetClass, irDeclaration);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitGetClass, reason: avoid collision after fix types in other method */
        public void visitGetClass2(IrGetClass irGetClass, IrDeclaration irDeclaration) {
            IrElementVisitor.DefaultImpls.visitGetClass(this, irGetClass, irDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitGetEnumValue */
        public /* bridge */ /* synthetic */ Unit visitGetEnumValue2(IrGetEnumValue irGetEnumValue, IrDeclaration irDeclaration) {
            visitGetEnumValue2(irGetEnumValue, irDeclaration);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitGetEnumValue, reason: avoid collision after fix types in other method */
        public void visitGetEnumValue2(IrGetEnumValue irGetEnumValue, IrDeclaration irDeclaration) {
            IrElementVisitor.DefaultImpls.visitGetEnumValue(this, irGetEnumValue, irDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitGetField */
        public /* bridge */ /* synthetic */ Unit visitGetField2(IrGetField irGetField, IrDeclaration irDeclaration) {
            visitGetField(irGetField, irDeclaration);
            return Unit.INSTANCE;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        public void visitGetField(IrGetField irGetField, IrDeclaration irDeclaration) {
            IrElementVisitor.DefaultImpls.visitGetField(this, irGetField, irDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitGetObjectValue */
        public /* bridge */ /* synthetic */ Unit visitGetObjectValue2(IrGetObjectValue irGetObjectValue, IrDeclaration irDeclaration) {
            visitGetObjectValue2(irGetObjectValue, irDeclaration);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitGetObjectValue, reason: avoid collision after fix types in other method */
        public void visitGetObjectValue2(IrGetObjectValue irGetObjectValue, IrDeclaration irDeclaration) {
            IrElementVisitor.DefaultImpls.visitGetObjectValue(this, irGetObjectValue, irDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitGetValue */
        public /* bridge */ /* synthetic */ Unit visitGetValue2(IrGetValue irGetValue, IrDeclaration irDeclaration) {
            visitGetValue2(irGetValue, irDeclaration);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitGetValue, reason: avoid collision after fix types in other method */
        public void visitGetValue2(IrGetValue irGetValue, IrDeclaration irDeclaration) {
            IrElementVisitor.DefaultImpls.visitGetValue(this, irGetValue, irDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitInstanceInitializerCall */
        public /* bridge */ /* synthetic */ Unit visitInstanceInitializerCall2(IrInstanceInitializerCall irInstanceInitializerCall, IrDeclaration irDeclaration) {
            visitInstanceInitializerCall2(irInstanceInitializerCall, irDeclaration);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitInstanceInitializerCall, reason: avoid collision after fix types in other method */
        public void visitInstanceInitializerCall2(IrInstanceInitializerCall irInstanceInitializerCall, IrDeclaration irDeclaration) {
            IrElementVisitor.DefaultImpls.visitInstanceInitializerCall(this, irInstanceInitializerCall, irDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitLocalDelegatedProperty */
        public /* bridge */ /* synthetic */ Unit visitLocalDelegatedProperty2(IrLocalDelegatedProperty irLocalDelegatedProperty, IrDeclaration irDeclaration) {
            visitLocalDelegatedProperty2(irLocalDelegatedProperty, irDeclaration);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitLocalDelegatedProperty, reason: avoid collision after fix types in other method */
        public void visitLocalDelegatedProperty2(IrLocalDelegatedProperty irLocalDelegatedProperty, IrDeclaration irDeclaration) {
            IrElementVisitor.DefaultImpls.visitLocalDelegatedProperty(this, irLocalDelegatedProperty, irDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitLocalDelegatedPropertyReference */
        public /* bridge */ /* synthetic */ Unit visitLocalDelegatedPropertyReference2(IrLocalDelegatedPropertyReference irLocalDelegatedPropertyReference, IrDeclaration irDeclaration) {
            visitLocalDelegatedPropertyReference2(irLocalDelegatedPropertyReference, irDeclaration);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitLocalDelegatedPropertyReference, reason: avoid collision after fix types in other method */
        public void visitLocalDelegatedPropertyReference2(IrLocalDelegatedPropertyReference irLocalDelegatedPropertyReference, IrDeclaration irDeclaration) {
            IrElementVisitor.DefaultImpls.visitLocalDelegatedPropertyReference(this, irLocalDelegatedPropertyReference, irDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitLoop */
        public /* bridge */ /* synthetic */ Unit visitLoop2(IrLoop irLoop, IrDeclaration irDeclaration) {
            visitLoop2(irLoop, irDeclaration);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitLoop, reason: avoid collision after fix types in other method */
        public void visitLoop2(IrLoop irLoop, IrDeclaration irDeclaration) {
            IrElementVisitor.DefaultImpls.visitLoop(this, irLoop, irDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public /* bridge */ /* synthetic */ Unit visitMemberAccess(IrMemberAccessExpression irMemberAccessExpression, IrDeclaration irDeclaration) {
            visitMemberAccess2((IrMemberAccessExpression<?>) irMemberAccessExpression, irDeclaration);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitMemberAccess, reason: avoid collision after fix types in other method */
        public void visitMemberAccess2(IrMemberAccessExpression<?> irMemberAccessExpression, IrDeclaration irDeclaration) {
            IrElementVisitor.DefaultImpls.visitMemberAccess(this, irMemberAccessExpression, irDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitModuleFragment */
        public /* bridge */ /* synthetic */ Unit visitModuleFragment2(IrModuleFragment irModuleFragment, IrDeclaration irDeclaration) {
            visitModuleFragment2(irModuleFragment, irDeclaration);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitModuleFragment, reason: avoid collision after fix types in other method */
        public void visitModuleFragment2(IrModuleFragment irModuleFragment, IrDeclaration irDeclaration) {
            IrElementVisitor.DefaultImpls.visitModuleFragment(this, irModuleFragment, irDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitPackageFragment */
        public /* bridge */ /* synthetic */ Unit visitPackageFragment2(IrPackageFragment irPackageFragment, IrDeclaration irDeclaration) {
            visitPackageFragment2(irPackageFragment, irDeclaration);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitPackageFragment, reason: avoid collision after fix types in other method */
        public void visitPackageFragment2(IrPackageFragment irPackageFragment, IrDeclaration irDeclaration) {
            IrElementVisitor.DefaultImpls.visitPackageFragment(this, irPackageFragment, irDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitProperty */
        public /* bridge */ /* synthetic */ Unit visitProperty2(IrProperty irProperty, IrDeclaration irDeclaration) {
            visitProperty2(irProperty, irDeclaration);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitProperty, reason: avoid collision after fix types in other method */
        public void visitProperty2(IrProperty irProperty, IrDeclaration irDeclaration) {
            IrElementVisitor.DefaultImpls.visitProperty(this, irProperty, irDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitPropertyReference */
        public /* bridge */ /* synthetic */ Unit visitPropertyReference2(IrPropertyReference irPropertyReference, IrDeclaration irDeclaration) {
            visitPropertyReference2(irPropertyReference, irDeclaration);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitPropertyReference, reason: avoid collision after fix types in other method */
        public void visitPropertyReference2(IrPropertyReference irPropertyReference, IrDeclaration irDeclaration) {
            IrElementVisitor.DefaultImpls.visitPropertyReference(this, irPropertyReference, irDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitRawFunctionReference */
        public /* bridge */ /* synthetic */ Unit visitRawFunctionReference2(IrRawFunctionReference irRawFunctionReference, IrDeclaration irDeclaration) {
            visitRawFunctionReference2(irRawFunctionReference, irDeclaration);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitReturn */
        public /* bridge */ /* synthetic */ Unit visitReturn2(IrReturn irReturn, IrDeclaration irDeclaration) {
            visitReturn2(irReturn, irDeclaration);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitReturn, reason: avoid collision after fix types in other method */
        public void visitReturn2(IrReturn irReturn, IrDeclaration irDeclaration) {
            IrElementVisitor.DefaultImpls.visitReturn(this, irReturn, irDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitScript */
        public /* bridge */ /* synthetic */ Unit visitScript2(IrScript irScript, IrDeclaration irDeclaration) {
            visitScript2(irScript, irDeclaration);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitScript, reason: avoid collision after fix types in other method */
        public void visitScript2(IrScript irScript, IrDeclaration irDeclaration) {
            IrElementVisitor.DefaultImpls.visitScript(this, irScript, irDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitSetField */
        public /* bridge */ /* synthetic */ Unit visitSetField2(IrSetField irSetField, IrDeclaration irDeclaration) {
            visitSetField(irSetField, irDeclaration);
            return Unit.INSTANCE;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        public void visitSetField(IrSetField irSetField, IrDeclaration irDeclaration) {
            IrElementVisitor.DefaultImpls.visitSetField(this, irSetField, irDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitSetValue */
        public /* bridge */ /* synthetic */ Unit visitSetValue2(IrSetValue irSetValue, IrDeclaration irDeclaration) {
            visitSetValue2(irSetValue, irDeclaration);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitSetValue, reason: avoid collision after fix types in other method */
        public void visitSetValue2(IrSetValue irSetValue, IrDeclaration irDeclaration) {
            IrElementVisitor.DefaultImpls.visitSetValue(this, irSetValue, irDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitSimpleFunction */
        public /* bridge */ /* synthetic */ Unit visitSimpleFunction2(IrSimpleFunction irSimpleFunction, IrDeclaration irDeclaration) {
            visitSimpleFunction2(irSimpleFunction, irDeclaration);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitSimpleFunction, reason: avoid collision after fix types in other method */
        public void visitSimpleFunction2(IrSimpleFunction irSimpleFunction, IrDeclaration irDeclaration) {
            IrElementVisitor.DefaultImpls.visitSimpleFunction(this, irSimpleFunction, irDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitSingletonReference */
        public /* bridge */ /* synthetic */ Unit visitSingletonReference2(IrGetSingletonValue irGetSingletonValue, IrDeclaration irDeclaration) {
            visitSingletonReference2(irGetSingletonValue, irDeclaration);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitSingletonReference, reason: avoid collision after fix types in other method */
        public void visitSingletonReference2(IrGetSingletonValue irGetSingletonValue, IrDeclaration irDeclaration) {
            IrElementVisitor.DefaultImpls.visitSingletonReference(this, irGetSingletonValue, irDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitSpreadElement */
        public /* bridge */ /* synthetic */ Unit visitSpreadElement2(IrSpreadElement irSpreadElement, IrDeclaration irDeclaration) {
            visitSpreadElement2(irSpreadElement, irDeclaration);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitSpreadElement, reason: avoid collision after fix types in other method */
        public void visitSpreadElement2(IrSpreadElement irSpreadElement, IrDeclaration irDeclaration) {
            IrElementVisitor.DefaultImpls.visitSpreadElement(this, irSpreadElement, irDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitStringConcatenation */
        public /* bridge */ /* synthetic */ Unit visitStringConcatenation2(IrStringConcatenation irStringConcatenation, IrDeclaration irDeclaration) {
            visitStringConcatenation2(irStringConcatenation, irDeclaration);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitSuspendableExpression */
        public /* bridge */ /* synthetic */ Unit visitSuspendableExpression2(IrSuspendableExpression irSuspendableExpression, IrDeclaration irDeclaration) {
            visitSuspendableExpression2(irSuspendableExpression, irDeclaration);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitSuspendableExpression, reason: avoid collision after fix types in other method */
        public void visitSuspendableExpression2(IrSuspendableExpression irSuspendableExpression, IrDeclaration irDeclaration) {
            IrElementVisitor.DefaultImpls.visitSuspendableExpression(this, irSuspendableExpression, irDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitSuspensionPoint */
        public /* bridge */ /* synthetic */ Unit visitSuspensionPoint2(IrSuspensionPoint irSuspensionPoint, IrDeclaration irDeclaration) {
            visitSuspensionPoint2(irSuspensionPoint, irDeclaration);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitSuspensionPoint, reason: avoid collision after fix types in other method */
        public void visitSuspensionPoint2(IrSuspensionPoint irSuspensionPoint, IrDeclaration irDeclaration) {
            IrElementVisitor.DefaultImpls.visitSuspensionPoint(this, irSuspensionPoint, irDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitSyntheticBody */
        public /* bridge */ /* synthetic */ Unit visitSyntheticBody2(IrSyntheticBody irSyntheticBody, IrDeclaration irDeclaration) {
            visitSyntheticBody2(irSyntheticBody, irDeclaration);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitSyntheticBody, reason: avoid collision after fix types in other method */
        public void visitSyntheticBody2(IrSyntheticBody irSyntheticBody, IrDeclaration irDeclaration) {
            IrElementVisitor.DefaultImpls.visitSyntheticBody(this, irSyntheticBody, irDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitThrow */
        public /* bridge */ /* synthetic */ Unit visitThrow2(IrThrow irThrow, IrDeclaration irDeclaration) {
            visitThrow2(irThrow, irDeclaration);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitThrow, reason: avoid collision after fix types in other method */
        public void visitThrow2(IrThrow irThrow, IrDeclaration irDeclaration) {
            IrElementVisitor.DefaultImpls.visitThrow(this, irThrow, irDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitTry */
        public /* bridge */ /* synthetic */ Unit visitTry2(IrTry irTry, IrDeclaration irDeclaration) {
            visitTry2(irTry, irDeclaration);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitTry, reason: avoid collision after fix types in other method */
        public void visitTry2(IrTry irTry, IrDeclaration irDeclaration) {
            IrElementVisitor.DefaultImpls.visitTry(this, irTry, irDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitTypeAlias */
        public /* bridge */ /* synthetic */ Unit visitTypeAlias2(IrTypeAlias irTypeAlias, IrDeclaration irDeclaration) {
            visitTypeAlias2(irTypeAlias, irDeclaration);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitTypeAlias, reason: avoid collision after fix types in other method */
        public void visitTypeAlias2(IrTypeAlias irTypeAlias, IrDeclaration irDeclaration) {
            IrElementVisitor.DefaultImpls.visitTypeAlias(this, irTypeAlias, irDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitTypeOperator */
        public /* bridge */ /* synthetic */ Unit visitTypeOperator2(IrTypeOperatorCall irTypeOperatorCall, IrDeclaration irDeclaration) {
            visitTypeOperator2(irTypeOperatorCall, irDeclaration);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitTypeOperator, reason: avoid collision after fix types in other method */
        public void visitTypeOperator2(IrTypeOperatorCall irTypeOperatorCall, IrDeclaration irDeclaration) {
            IrElementVisitor.DefaultImpls.visitTypeOperator(this, irTypeOperatorCall, irDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitTypeParameter */
        public /* bridge */ /* synthetic */ Unit visitTypeParameter2(IrTypeParameter irTypeParameter, IrDeclaration irDeclaration) {
            visitTypeParameter2(irTypeParameter, irDeclaration);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitTypeParameter, reason: avoid collision after fix types in other method */
        public void visitTypeParameter2(IrTypeParameter irTypeParameter, IrDeclaration irDeclaration) {
            IrElementVisitor.DefaultImpls.visitTypeParameter(this, irTypeParameter, irDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitValueAccess */
        public /* bridge */ /* synthetic */ Unit visitValueAccess2(IrValueAccessExpression irValueAccessExpression, IrDeclaration irDeclaration) {
            visitValueAccess2(irValueAccessExpression, irDeclaration);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitValueParameter */
        public /* bridge */ /* synthetic */ Unit visitValueParameter2(IrValueParameter irValueParameter, IrDeclaration irDeclaration) {
            visitValueParameter2(irValueParameter, irDeclaration);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitValueParameter, reason: avoid collision after fix types in other method */
        public void visitValueParameter2(IrValueParameter irValueParameter, IrDeclaration irDeclaration) {
            IrElementVisitor.DefaultImpls.visitValueParameter(this, irValueParameter, irDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitVararg */
        public /* bridge */ /* synthetic */ Unit visitVararg2(IrVararg irVararg, IrDeclaration irDeclaration) {
            visitVararg(irVararg, irDeclaration);
            return Unit.INSTANCE;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        public void visitVararg(IrVararg irVararg, IrDeclaration irDeclaration) {
            IrElementVisitor.DefaultImpls.visitVararg(this, irVararg, irDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitVariable */
        public /* bridge */ /* synthetic */ Unit visitVariable2(IrVariable irVariable, IrDeclaration irDeclaration) {
            visitVariable(irVariable, irDeclaration);
            return Unit.INSTANCE;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        public void visitVariable(IrVariable irVariable, IrDeclaration irDeclaration) {
            IrElementVisitor.DefaultImpls.visitVariable(this, irVariable, irDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitWhen */
        public /* bridge */ /* synthetic */ Unit visitWhen2(IrWhen irWhen, IrDeclaration irDeclaration) {
            visitWhen2(irWhen, irDeclaration);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitWhen, reason: avoid collision after fix types in other method */
        public void visitWhen2(IrWhen irWhen, IrDeclaration irDeclaration) {
            IrElementVisitor.DefaultImpls.visitWhen(this, irWhen, irDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitWhileLoop */
        public /* bridge */ /* synthetic */ Unit visitWhileLoop2(IrWhileLoop irWhileLoop, IrDeclaration irDeclaration) {
            visitWhileLoop2(irWhileLoop, irDeclaration);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitWhileLoop, reason: avoid collision after fix types in other method */
        public void visitWhileLoop2(IrWhileLoop irWhileLoop, IrDeclaration irDeclaration) {
            IrElementVisitor.DefaultImpls.visitWhileLoop(this, irWhileLoop, irDeclaration);
        }

        /* JADX INFO: renamed from: visitValueAccess, reason: avoid collision after fix types in other method */
        public void visitValueAccess2(IrValueAccessExpression expression, IrDeclaration data) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            Intrinsics.checkNotNullParameter(data, "data");
            visitDeclarationReference2((IrDeclarationReference) expression, data);
            UsefulDeclarationProcessor.enqueue$default(UsefulDeclarationProcessor.this, expression.getSymbol().getOwner(), data, "variable access", false, 4, null);
        }

        /* JADX INFO: renamed from: visitElement, reason: avoid collision after fix types in other method */
        public void visitElement2(IrElement element, IrDeclaration data) {
            Intrinsics.checkNotNullParameter(element, "element");
            Intrinsics.checkNotNullParameter(data, "data");
            element.acceptChildren(this, data);
        }

        /* JADX INFO: renamed from: visitFunctionAccess, reason: avoid collision after fix types in other method */
        public void visitFunctionAccess2(IrFunctionAccessExpression expression, IrDeclaration data) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            Intrinsics.checkNotNullParameter(data, "data");
            IrElementVisitor.DefaultImpls.visitFunctionAccess(this, expression, data);
            UsefulDeclarationProcessor.enqueue$default(UsefulDeclarationProcessor.this, expression.getSymbol().getOwner(), data, "function access", false, 4, null);
        }

        /* JADX INFO: renamed from: visitRawFunctionReference, reason: avoid collision after fix types in other method */
        public void visitRawFunctionReference2(IrRawFunctionReference expression, IrDeclaration data) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            Intrinsics.checkNotNullParameter(data, "data");
            IrElementVisitor.DefaultImpls.visitRawFunctionReference(this, expression, data);
            UsefulDeclarationProcessor.enqueue$default(UsefulDeclarationProcessor.this, expression.getSymbol().getOwner(), data, "raw function access", false, 4, null);
        }

        /* JADX INFO: renamed from: visitBlock, reason: avoid collision after fix types in other method */
        public void visitBlock2(IrBlock expression, IrDeclaration data) {
            IrFunction inlineFunction;
            Intrinsics.checkNotNullParameter(expression, "expression");
            Intrinsics.checkNotNullParameter(data, "data");
            IrElementVisitor.DefaultImpls.visitBlock(this, expression, data);
            if (!(expression instanceof IrReturnableBlock) || (inlineFunction = IrInlineUtilsKt.getInlineFunction((IrReturnableBlock) expression)) == null) {
                return;
            }
            UsefulDeclarationProcessor.this.addToUsefulPolyfilledDeclarations(inlineFunction);
        }

        /* JADX INFO: renamed from: visitFieldAccess, reason: avoid collision after fix types in other method */
        public void visitFieldAccess2(IrFieldAccessExpression expression, IrDeclaration data) {
            IrProperty owner;
            Intrinsics.checkNotNullParameter(expression, "expression");
            Intrinsics.checkNotNullParameter(data, "data");
            IrElementVisitor.DefaultImpls.visitFieldAccess(this, expression, data);
            IrField owner2 = expression.getSymbol().getOwner();
            IrField irField = owner2;
            UsefulDeclarationProcessor.enqueue$default(UsefulDeclarationProcessor.this, irField, data, "field access", false, 4, null);
            IrPropertySymbol correspondingPropertySymbol = owner2.getCorrespondingPropertySymbol();
            if (correspondingPropertySymbol == null || (owner = correspondingPropertySymbol.getOwner()) == null || !Intrinsics.areEqual(owner2.getOrigin(), IrDeclarationOrigin.PROPERTY_BACKING_FIELD.INSTANCE) || !AnnotationUtilsKt.hasJsPolyfill(owner)) {
                return;
            }
            UsefulDeclarationProcessor.enqueue$default(UsefulDeclarationProcessor.this, owner, irField, "property backing field", false, 4, null);
        }

        /* JADX INFO: renamed from: visitStringConcatenation, reason: avoid collision after fix types in other method */
        public void visitStringConcatenation2(IrStringConcatenation expression, IrDeclaration data) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            Intrinsics.checkNotNullParameter(data, "data");
            IrElementVisitor.DefaultImpls.visitStringConcatenation(this, expression, data);
            UsefulDeclarationProcessor usefulDeclarationProcessor = UsefulDeclarationProcessor.this;
            UsefulDeclarationProcessor.enqueue$default(usefulDeclarationProcessor, usefulDeclarationProcessor.getToStringMethod(), data, "string concatenation", false, 4, null);
        }
    }

    private final void addReachabilityInfoIfNeeded(IrDeclaration from, IrDeclaration to, String description, boolean isContagiousOverridableDeclaration) {
        List<ReachabilityInfo> list = this.reachabilityInfos;
        if (list != null) {
            list.add(new ReachabilityInfo(from, to, description, isContagiousOverridableDeclaration));
        }
    }

    public static /* synthetic */ void enqueue$default(UsefulDeclarationProcessor usefulDeclarationProcessor, IrDeclaration irDeclaration, IrDeclaration irDeclaration2, String str, boolean z, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: enqueue");
        }
        if ((i & 4) != 0) {
            z = true;
        }
        usefulDeclarationProcessor.enqueue(irDeclaration, irDeclaration2, str, z);
    }

    protected final void enqueue(IrDeclaration irDeclaration, IrDeclaration from, String description, boolean z) {
        Intrinsics.checkNotNullParameter(irDeclaration, "<this>");
        Intrinsics.checkNotNullParameter(from, "from");
        Intrinsics.checkNotNullParameter(description, "description");
        if (!(irDeclaration instanceof IrProperty) || ((IrProperty) irDeclaration).getIsExternal()) {
            boolean z2 = z && (irDeclaration instanceof IrOverridableDeclaration) && IrUtilsKt.isMemberOfOpenClass(irDeclaration);
            addReachabilityInfoIfNeeded(from, irDeclaration, description, z2);
            if (z2) {
                this.contagiousReachableDeclarations.add((IrOverridableDeclaration) irDeclaration);
            }
            if (isReachable(irDeclaration)) {
                return;
            }
            this.result.add(irDeclaration);
            this.queue.addLast(irDeclaration);
            addToUsefulPolyfilledDeclarations(irDeclaration);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void addToUsefulPolyfilledDeclarations(IrDeclaration irDeclaration) {
        if (!AnnotationUtilsKt.hasJsPolyfill(irDeclaration) || this.usefulPolyfilledDeclarations.contains(irDeclaration)) {
            return;
        }
        this.usefulPolyfilledDeclarations.add(irDeclaration);
    }

    protected final LinkedHashSet<IrClass> getConstructedClasses() {
        return this.constructedClasses;
    }

    protected final HashSet<IrDeclaration> getResult() {
        return this.result;
    }

    protected final LinkedHashSet<IrClass> getClassesWithObjectAssociations() {
        return this.classesWithObjectAssociations;
    }

    public final HashSet<IrDeclaration> getUsefulPolyfilledDeclarations() {
        return this.usefulPolyfilledDeclarations;
    }

    protected void addConstructedClass(IrClass irClass) {
        Intrinsics.checkNotNullParameter(irClass, "irClass");
        this.constructedClasses.add(irClass);
    }

    protected void processClass(IrClass irClass) {
        IrSimpleFunction irSimpleFunction;
        Intrinsics.checkNotNullParameter(irClass, "irClass");
        processSuperTypes(irClass);
        if (IrUtilsKt.isObject(irClass)) {
            IrClass irClass2 = irClass;
            if (isExported(irClass2) && (irSimpleFunction = getContext().getMapping().getObjectToGetInstanceFunction().get(irClass2)) != null) {
                enqueue$default(this, irSimpleFunction, irClass2, "Exported object getInstance function", false, 4, null);
            }
        }
        Iterator<T> it = irClass.getAnnotations().iterator();
        while (it.hasNext()) {
            IrClass constructedClass = AdditionalIrUtilsKt.getConstructedClass(((IrConstructorCall) it.next()).getSymbol().getOwner());
            if (AnnotationUtilsKt.isAssociatedObjectAnnotatedAnnotation(constructedClass)) {
                this.classesWithObjectAssociations.add(irClass);
                enqueue$default(this, constructedClass, irClass, "@AssociatedObject annotated annotation class", false, 4, null);
            }
        }
    }

    protected void processSuperTypes(IrClass irClass) {
        IrClass owner;
        Intrinsics.checkNotNullParameter(irClass, "irClass");
        Iterator<T> it = irClass.getSuperTypes().iterator();
        while (it.hasNext()) {
            IrClassifierSymbol classifierOrNull = IrTypesKt.getClassifierOrNull((IrType) it.next());
            IrClassSymbol irClassSymbol = classifierOrNull instanceof IrClassSymbol ? (IrClassSymbol) classifierOrNull : null;
            if (irClassSymbol != null && (owner = irClassSymbol.getOwner()) != null) {
                enqueue$default(this, owner, irClass, "superTypes", false, 4, null);
            }
        }
    }

    protected void processSimpleFunction(IrSimpleFunction irFunction) {
        Intrinsics.checkNotNullParameter(irFunction, "irFunction");
        if (irFunction.getIsFakeOverride()) {
            Iterator<T> it = irFunction.getOverriddenSymbols().iterator();
            while (it.hasNext()) {
                enqueue(((IrSimpleFunctionSymbol) it.next()).getOwner(), irFunction, "overridden by a useful fake override", false);
            }
        }
    }

    protected void processConstructor(IrConstructor irConstructor) {
        Intrinsics.checkNotNullParameter(irConstructor, "irConstructor");
        IrClass constructedClass = AdditionalIrUtilsKt.getConstructedClass(irConstructor);
        enqueue$default(this, constructedClass, irConstructor, "constructed class", false, 4, null);
        addConstructedClass(constructedClass);
    }

    protected void processConstructedClassDeclaration(IrDeclaration declaration) {
        IrOverridableDeclaration<?> irOverridableDeclarationProcessConstructedClassDeclaration$findOverriddenContagiousDeclaration;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        if (isReachable(declaration)) {
            return;
        }
        if ((declaration instanceof IrOverridableDeclaration) && (irOverridableDeclarationProcessConstructedClassDeclaration$findOverriddenContagiousDeclaration = processConstructedClassDeclaration$findOverriddenContagiousDeclaration((IrOverridableDeclaration) declaration, this)) != null) {
            enqueue$default(this, declaration, irOverridableDeclarationProcessConstructedClassDeclaration$findOverriddenContagiousDeclaration, "overrides useful declaration", false, 4, null);
        }
        if (declaration instanceof IrSimpleFunction) {
            IrSimpleFunction irSimpleFunction = (IrSimpleFunction) declaration;
            if (isAccessorForOverriddenExternalField(irSimpleFunction)) {
                IrPropertySymbol correspondingPropertySymbol = irSimpleFunction.getCorrespondingPropertySymbol();
                Intrinsics.checkNotNull(correspondingPropertySymbol);
                enqueue$default(this, declaration, correspondingPropertySymbol.getOwner(), "overrides external property", false, 4, null);
            }
        }
        if (declaration instanceof IrProperty) {
            IrProperty irProperty = (IrProperty) declaration;
            IrSimpleFunction getter = irProperty.getGetter();
            if (getter != null && processConstructedClassDeclaration$findOverriddenContagiousDeclaration(getter, this) != null) {
                enqueue$default(this, getter, declaration, "(getter) overrides useful declaration", false, 4, null);
            }
            IrSimpleFunction setter = irProperty.getSetter();
            if (setter == null || processConstructedClassDeclaration$findOverriddenContagiousDeclaration(setter, this) == null) {
                return;
            }
            enqueue$default(this, setter, declaration, "(setter) overrides useful declaration", false, 4, null);
        }
    }

    private static final IrOverridableDeclaration<?> processConstructedClassDeclaration$findOverriddenContagiousDeclaration(IrOverridableDeclaration<?> irOverridableDeclaration, UsefulDeclarationProcessor usefulDeclarationProcessor) {
        Iterator it = irOverridableDeclaration.getOverriddenSymbols().iterator();
        while (true) {
            if (!it.hasNext()) {
                return null;
            }
            IrSymbolOwner owner = ((IrSymbol) it.next()).getOwner();
            IrOverridableDeclaration<?> irOverridableDeclaration2 = owner instanceof IrOverridableDeclaration ? (IrOverridableDeclaration) owner : null;
            if (irOverridableDeclaration2 != null) {
                if (usefulDeclarationProcessor.contagiousReachableDeclarations.contains(irOverridableDeclaration2)) {
                    return irOverridableDeclaration2;
                }
                IrOverridableDeclaration<?> irOverridableDeclarationProcessConstructedClassDeclaration$findOverriddenContagiousDeclaration = processConstructedClassDeclaration$findOverriddenContagiousDeclaration(irOverridableDeclaration2, usefulDeclarationProcessor);
                if (irOverridableDeclarationProcessConstructedClassDeclaration$findOverriddenContagiousDeclaration != null) {
                    return irOverridableDeclarationProcessConstructedClassDeclaration$findOverriddenContagiousDeclaration;
                }
            }
        }
    }

    protected final boolean isAccessorForOverriddenExternalField(IrSimpleFunction irSimpleFunction) {
        IrProperty owner;
        Intrinsics.checkNotNullParameter(irSimpleFunction, "<this>");
        IrPropertySymbol correspondingPropertySymbol = irSimpleFunction.getCorrespondingPropertySymbol();
        if (correspondingPropertySymbol == null || (owner = correspondingPropertySymbol.getOwner()) == null) {
            return false;
        }
        return isExternalOrOverriddenExternal(owner);
    }

    protected final boolean isExternalOrOverriddenExternal(IrProperty irProperty) {
        Intrinsics.checkNotNullParameter(irProperty, "<this>");
        return IrUtilsKt.isEffectivelyExternal((IrPossiblyExternalDeclaration) irProperty) || isOverriddenExternal(irProperty);
    }

    protected final boolean isOverriddenExternal(IrProperty irProperty) {
        Intrinsics.checkNotNullParameter(irProperty, "<this>");
        List<IrPropertySymbol> overriddenSymbols = irProperty.getOverriddenSymbols();
        if ((overriddenSymbols instanceof Collection) && overriddenSymbols.isEmpty()) {
            return false;
        }
        Iterator<T> it = overriddenSymbols.iterator();
        while (it.hasNext()) {
            if (isExternalOrOverriddenExternal(((IrPropertySymbol) it.next()).getOwner())) {
                return true;
            }
        }
        return false;
    }

    protected final boolean isReachable(IrDeclaration irDeclaration) {
        Intrinsics.checkNotNullParameter(irDeclaration, "<this>");
        return this.result.contains(irDeclaration);
    }

    public final Set<IrDeclaration> collectDeclarations(Iterable<? extends IrDeclaration> rootDeclarations, DceDumpNameCache dceDumpNameCache) {
        KAnnotatedElement kAnnotatedElement;
        IrExpression initializer;
        Intrinsics.checkNotNullParameter(rootDeclarations, "rootDeclarations");
        Intrinsics.checkNotNullParameter(dceDumpNameCache, "dceDumpNameCache");
        for (IrDeclaration irDeclaration : rootDeclarations) {
            enqueue$default(this, irDeclaration, irDeclaration, "<ROOT>", false, 4, null);
        }
        while (!this.queue.isEmpty()) {
            while (!this.queue.isEmpty()) {
                IrDeclaration irDeclarationPollFirst = this.queue.pollFirst();
                if (irDeclarationPollFirst instanceof IrClass) {
                    Intrinsics.checkNotNull(irDeclarationPollFirst);
                    processClass((IrClass) irDeclarationPollFirst);
                } else if (irDeclarationPollFirst instanceof IrSimpleFunction) {
                    Intrinsics.checkNotNull(irDeclarationPollFirst);
                    processSimpleFunction((IrSimpleFunction) irDeclarationPollFirst);
                } else if (irDeclarationPollFirst instanceof IrConstructor) {
                    Intrinsics.checkNotNull(irDeclarationPollFirst);
                    processConstructor((IrConstructor) irDeclarationPollFirst);
                } else if (irDeclarationPollFirst instanceof IrField) {
                    Intrinsics.checkNotNull(irDeclarationPollFirst);
                    processField((IrField) irDeclarationPollFirst);
                }
                if (irDeclarationPollFirst instanceof IrFunction) {
                    initializer = ((IrFunction) irDeclarationPollFirst).getBody();
                } else if (irDeclarationPollFirst instanceof IrField) {
                    initializer = ((IrField) irDeclarationPollFirst).getInitializer();
                } else {
                    initializer = irDeclarationPollFirst instanceof IrVariable ? ((IrVariable) irDeclarationPollFirst).getInitializer() : null;
                }
                if (initializer != null) {
                    initializer.accept(getBodyVisitor(), irDeclarationPollFirst);
                }
            }
            handleAssociatedObjects();
            Iterator<IrClass> it = this.constructedClasses.iterator();
            while (it.hasNext()) {
                Iterator<IrDeclaration> it2 = it.next().getDeclarations().iterator();
                while (it2.hasNext()) {
                    processConstructedClassDeclaration(it2.next());
                }
            }
        }
        List<ReachabilityInfo> list = this.reachabilityInfos;
        if (list != null) {
            if (this.printReachabilityInfo) {
                System.out.println((Object) UsefulDeclarationProcessorKt.transformToDotLikeString(list, dceDumpNameCache));
            }
            if (this.dumpReachabilityInfoToFile != null) {
                File file = new File(this.dumpReachabilityInfoToFile);
                String extension = FilesKt.getExtension(file);
                if (Intrinsics.areEqual(extension, Options.TYPE_JSON)) {
                    kAnnotatedElement = (KFunction) UsefulDeclarationProcessor$collectDeclarations$stringify$1.INSTANCE;
                } else {
                    kAnnotatedElement = Intrinsics.areEqual(extension, "js") ? (KFunction) UsefulDeclarationProcessor$collectDeclarations$stringify$2.INSTANCE : (KFunction) UsefulDeclarationProcessor$collectDeclarations$stringify$3.INSTANCE;
                }
                FilesKt.writeText$default(file, (String) ((Function2) kAnnotatedElement).invoke(this.reachabilityInfos, dceDumpNameCache), null, 2, null);
            }
        }
        return this.result;
    }
}
