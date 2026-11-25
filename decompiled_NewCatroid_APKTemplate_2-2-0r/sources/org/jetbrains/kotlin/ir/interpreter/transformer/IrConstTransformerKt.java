package org.jetbrains.kotlin.ir.interpreter.transformer;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.constant.EvaluatedConstTracker;
import org.jetbrains.kotlin.incremental.components.InlineConstTracker;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationsKt;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.expressions.IrConst;
import org.jetbrains.kotlin.ir.expressions.IrErrorExpression;
import org.jetbrains.kotlin.ir.interpreter.IrInterpreter;
import org.jetbrains.kotlin.ir.interpreter.checker.EvaluationMode;
import org.jetbrains.kotlin.ir.interpreter.checker.IrInterpreterCommonChecker;
import org.jetbrains.kotlin.ir.interpreter.preprocessor.IrInterpreterConstGetterPreprocessor;
import org.jetbrains.kotlin.ir.interpreter.preprocessor.IrInterpreterKCallableNamePreprocessor;
import org.jetbrains.kotlin.ir.interpreter.preprocessor.IrInterpreterPreprocessor;
import org.jetbrains.kotlin.ir.interpreter.preprocessor.IrInterpreterPreprocessorData;
import org.jetbrains.kotlin.ir.util.AdditionalIrUtilsKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformer;
import org.jetbrains.kotlin.name.ClassId;

/* JADX INFO: compiled from: IrConstTransformer.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000N\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u001a\u0010\u0000\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005\u001a&\u0010\u0006\u001a\u00020\u0007*\u00020\b2\u0006\u0010\t\u001a\u00020\u00012\u0006\u0010\n\u001a\u00020\u000b2\n\u0010\f\u001a\u0006\u0012\u0002\b\u00030\r\u001a<\u0010\u000e\u001a\u00020\u0007*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u00102\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\b2\b\b\u0002\u0010\u0012\u001a\u00020\u0013\u001a\u0080\u0001\u0010\u0014\u001a\u00020\u0007*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u00102\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\b2 \b\u0002\u0010\u0015\u001a\u001a\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u0017\u0012\u0004\u0012\u00020\u0018\u0012\u0004\u0012\u00020\u00070\u00162 \b\u0002\u0010\u0019\u001a\u001a\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u0017\u0012\u0004\u0012\u00020\u0018\u0012\u0004\u0012\u00020\u00070\u00162\b\b\u0002\u0010\u0012\u001a\u00020\u0013¨\u0006\u001a"}, d2 = {"preprocessForConstTransformer", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "interpreter", "Lorg/jetbrains/kotlin/ir/interpreter/IrInterpreter;", "mode", "Lorg/jetbrains/kotlin/ir/interpreter/checker/EvaluationMode;", "reportOnIr", "", "Lorg/jetbrains/kotlin/incremental/components/InlineConstTracker;", "irFile", "field", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "value", "Lorg/jetbrains/kotlin/ir/expressions/IrConst;", "runConstOptimizations", "evaluatedConstTracker", "Lorg/jetbrains/kotlin/constant/EvaluatedConstTracker;", "inlineConstTracker", "suppressExceptions", "", "transformConst", "onWarning", "Lkotlin/Function3;", "Lorg/jetbrains/kotlin/ir/IrElement;", "Lorg/jetbrains/kotlin/ir/expressions/IrErrorExpression;", "onError", "ir.interpreter"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class IrConstTransformerKt {
    public static final void transformConst(IrFile irFile, IrInterpreter interpreter, EvaluationMode mode, EvaluatedConstTracker evaluatedConstTracker, InlineConstTracker inlineConstTracker, Function3<? super IrFile, ? super IrElement, ? super IrErrorExpression, Unit> onWarning, Function3<? super IrFile, ? super IrElement, ? super IrErrorExpression, Unit> onError, boolean z) {
        Intrinsics.checkNotNullParameter(irFile, "<this>");
        Intrinsics.checkNotNullParameter(interpreter, "interpreter");
        Intrinsics.checkNotNullParameter(mode, "mode");
        Intrinsics.checkNotNullParameter(onWarning, "onWarning");
        Intrinsics.checkNotNullParameter(onError, "onError");
        IrFile irFilePreprocessForConstTransformer = preprocessForConstTransformer(irFile, interpreter, mode);
        IrInterpreterCommonChecker irInterpreterCommonChecker = new IrInterpreterCommonChecker();
        boolean z2 = false;
        int i = 1;
        DefaultConstructorMarker defaultConstructorMarker = null;
        irFilePreprocessForConstTransformer.transform((IrElementTransformer<? super IrConstTransformer.Data>) new IrConstOnlyNecessaryTransformer(interpreter, irFilePreprocessForConstTransformer, mode, irInterpreterCommonChecker, evaluatedConstTracker, inlineConstTracker, onWarning, onError, z), new IrConstTransformer.Data(z2, i, defaultConstructorMarker));
        irFilePreprocessForConstTransformer.transform((IrElementTransformer<? super IrConstTransformer.Data>) new IrConstDeclarationAnnotationTransformer(interpreter, irFilePreprocessForConstTransformer, mode, irInterpreterCommonChecker, evaluatedConstTracker, inlineConstTracker, onWarning, onError, z), new IrConstTransformer.Data(z2, i, defaultConstructorMarker));
        irFilePreprocessForConstTransformer.transform((IrElementTransformer<? super IrConstTransformer.Data>) new IrConstTypeAnnotationTransformer(interpreter, irFilePreprocessForConstTransformer, mode, irInterpreterCommonChecker, evaluatedConstTracker, inlineConstTracker, onWarning, onError, z), new IrConstTransformer.Data(z2, i, defaultConstructorMarker));
    }

    public static /* synthetic */ void runConstOptimizations$default(IrFile irFile, IrInterpreter irInterpreter, EvaluationMode evaluationMode, EvaluatedConstTracker evaluatedConstTracker, InlineConstTracker inlineConstTracker, boolean z, int i, Object obj) {
        EvaluatedConstTracker evaluatedConstTracker2 = (i & 4) != 0 ? null : evaluatedConstTracker;
        InlineConstTracker inlineConstTracker2 = (i & 8) != 0 ? null : inlineConstTracker;
        if ((i & 16) != 0) {
            z = false;
        }
        runConstOptimizations(irFile, irInterpreter, evaluationMode, evaluatedConstTracker2, inlineConstTracker2, z);
    }

    public static final void runConstOptimizations(IrFile irFile, IrInterpreter interpreter, EvaluationMode mode, EvaluatedConstTracker evaluatedConstTracker, InlineConstTracker inlineConstTracker, boolean z) {
        Intrinsics.checkNotNullParameter(irFile, "<this>");
        Intrinsics.checkNotNullParameter(interpreter, "interpreter");
        Intrinsics.checkNotNullParameter(mode, "mode");
        irFile.transform((IrElementTransformer<? super IrConstTransformer.Data>) new IrConstAllTransformer(interpreter, irFile, mode, new IrInterpreterCommonChecker(), evaluatedConstTracker, inlineConstTracker, new Function3<IrFile, IrElement, IrErrorExpression, Unit>() { // from class: org.jetbrains.kotlin.ir.interpreter.transformer.IrConstTransformerKt$runConstOptimizations$irConstExpressionTransformer$1
            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(IrFile irFile2, IrElement irElement, IrErrorExpression irErrorExpression) {
                Intrinsics.checkNotNullParameter(irFile2, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(irElement, "<anonymous parameter 1>");
                Intrinsics.checkNotNullParameter(irErrorExpression, "<anonymous parameter 2>");
            }

            @Override // kotlin.jvm.functions.Function3
            public /* bridge */ /* synthetic */ Unit invoke(IrFile irFile2, IrElement irElement, IrErrorExpression irErrorExpression) {
                invoke2(irFile2, irElement, irErrorExpression);
                return Unit.INSTANCE;
            }
        }, new Function3<IrFile, IrElement, IrErrorExpression, Unit>() { // from class: org.jetbrains.kotlin.ir.interpreter.transformer.IrConstTransformerKt$runConstOptimizations$irConstExpressionTransformer$2
            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(IrFile irFile2, IrElement irElement, IrErrorExpression irErrorExpression) {
                Intrinsics.checkNotNullParameter(irFile2, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(irElement, "<anonymous parameter 1>");
                Intrinsics.checkNotNullParameter(irErrorExpression, "<anonymous parameter 2>");
            }

            @Override // kotlin.jvm.functions.Function3
            public /* bridge */ /* synthetic */ Unit invoke(IrFile irFile2, IrElement irElement, IrErrorExpression irErrorExpression) {
                invoke2(irFile2, irElement, irErrorExpression);
                return Unit.INSTANCE;
            }
        }, z), new IrConstTransformer.Data(false, 1, null));
    }

    public static final IrFile preprocessForConstTransformer(IrFile irFile, IrInterpreter interpreter, EvaluationMode mode) {
        Intrinsics.checkNotNullParameter(irFile, "<this>");
        Intrinsics.checkNotNullParameter(interpreter, "interpreter");
        Intrinsics.checkNotNullParameter(mode, "mode");
        Iterator it = SetsKt.setOf((Object[]) new IrInterpreterPreprocessor[]{new IrInterpreterKCallableNamePreprocessor(), new IrInterpreterConstGetterPreprocessor()}).iterator();
        while (it.hasNext()) {
            irFile = ((IrInterpreterPreprocessor) it.next()).preprocess(irFile, new IrInterpreterPreprocessorData(mode, interpreter.getIrBuiltIns()));
        }
        return irFile;
    }

    public static final void reportOnIr(InlineConstTracker inlineConstTracker, IrFile irFile, IrField field, IrConst<?> value) {
        String strAsString;
        String strReplace$default;
        String strReplace$default2;
        Intrinsics.checkNotNullParameter(inlineConstTracker, "<this>");
        Intrinsics.checkNotNullParameter(irFile, "irFile");
        Intrinsics.checkNotNullParameter(field, "field");
        Intrinsics.checkNotNullParameter(value, "value");
        if (Intrinsics.areEqual(field.getOrigin(), IrDeclarationOrigin.IR_EXTERNAL_JAVA_DECLARATION_STUB.INSTANCE)) {
            String path = IrDeclarationsKt.getPath(irFile);
            ClassId classId = AdditionalIrUtilsKt.getClassId(IrUtilsKt.getParentAsClass(field));
            if (classId == null || (strAsString = classId.asString()) == null || (strReplace$default = StringsKt.replace$default(strAsString, ".", "$", false, 4, (Object) null)) == null || (strReplace$default2 = StringsKt.replace$default(strReplace$default, "/", ".", false, 4, (Object) null)) == null) {
                return;
            }
            String strAsString2 = field.getName().asString();
            Intrinsics.checkNotNullExpressionValue(strAsString2, "asString(...)");
            inlineConstTracker.report(path, strReplace$default2, strAsString2, value.getKind().getAsString());
        }
    }
}
