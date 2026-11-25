package org.catrobat.catroid.formulaeditor.function;

import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.camera.mlkitdetectors.ObjectDetectorResults;
import org.catrobat.catroid.formulaeditor.Functions;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/formulaeditor/function/ObjectDetectorFunctionProvider.class
  classes14.dex
 */
/* JADX INFO: compiled from: ObjectDetectorFunctionProvider.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001c\u0010\u0003\u001a\u00020\u00042\u0012\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u0006H\u0016¨\u0006\t"}, d2 = {"Lorg/catrobat/catroid/formulaeditor/function/ObjectDetectorFunctionProvider;", "Lorg/catrobat/catroid/formulaeditor/function/FunctionProvider;", "()V", "addFunctionsToMap", "", "formulaFunctions", "", "Lorg/catrobat/catroid/formulaeditor/Functions;", "Lorg/catrobat/catroid/formulaeditor/function/FormulaFunction;", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ObjectDetectorFunctionProvider implements FunctionProvider {
    @Override // org.catrobat.catroid.formulaeditor.function.FunctionProvider
    public void addFunctionsToMap(Map<Functions, FormulaFunction> formulaFunctions) {
        Intrinsics.checkNotNullParameter(formulaFunctions, "formulaFunctions");
        formulaFunctions.put(Functions.ID_OF_DETECTED_OBJECT, new UnaryFunction(new UnaryFunctionAction() { // from class: org.catrobat.catroid.formulaeditor.function.ObjectDetectorFunctionProvider$$ExternalSyntheticLambda0
            @Override // org.catrobat.catroid.formulaeditor.function.UnaryFunctionAction
            public final Object execute(double d) {
                return ObjectDetectorFunctionProvider.addFunctionsToMap$lambda$0(d);
            }
        }));
        formulaFunctions.put(Functions.OBJECT_WITH_ID_VISIBLE, new UnaryFunction(new UnaryFunctionAction() { // from class: org.catrobat.catroid.formulaeditor.function.ObjectDetectorFunctionProvider$$ExternalSyntheticLambda1
            @Override // org.catrobat.catroid.formulaeditor.function.UnaryFunctionAction
            public final Object execute(double d) {
                return ObjectDetectorFunctionProvider.addFunctionsToMap$lambda$1(d);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Object addFunctionsToMap$lambda$0(double d) {
        Integer num = (Integer) CollectionsKt.getOrNull(CollectionsKt.toList(ObjectDetectorResults.INSTANCE.getResult().keySet()), ((int) d) - 1);
        return Double.valueOf(num != null ? num.intValue() : 0.0d);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Object addFunctionsToMap$lambda$1(double d) {
        return Boolean.valueOf(ObjectDetectorResults.INSTANCE.getResult().get(Integer.valueOf((int) d)) != null);
    }
}
