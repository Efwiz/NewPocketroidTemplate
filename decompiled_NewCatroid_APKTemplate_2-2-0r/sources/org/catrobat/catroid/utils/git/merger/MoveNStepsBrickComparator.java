package org.catrobat.catroid.utils.git.merger;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.content.bricks.Brick;
import org.catrobat.catroid.content.bricks.MoveNStepsBrick;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.FormulaElement;
import org.catrobat.catroid.utils.git.Conflict;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/git/merger/MoveNStepsBrickComparator.class
  classes14.dex
 */
/* JADX INFO: compiled from: BrickComparator.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J.\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\u00062\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nH\u0016¨\u0006\f"}, d2 = {"Lorg/catrobat/catroid/utils/git/merger/MoveNStepsBrickComparator;", "Lorg/catrobat/catroid/utils/git/merger/BrickComparator;", "()V", "compare", "", "mergedBrick", "Lorg/catrobat/catroid/content/bricks/Brick;", "baseBrick", "remoteBrick", "conflicts", "", "Lorg/catrobat/catroid/utils/git/Conflict;", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class MoveNStepsBrickComparator implements BrickComparator {
    @Override // org.catrobat.catroid.utils.git.merger.BrickComparator
    public void compare(Brick mergedBrick, Brick baseBrick, Brick remoteBrick, List<Conflict> conflicts) {
        Intrinsics.checkNotNullParameter(mergedBrick, "mergedBrick");
        Intrinsics.checkNotNullParameter(baseBrick, "baseBrick");
        Intrinsics.checkNotNullParameter(remoteBrick, "remoteBrick");
        Intrinsics.checkNotNullParameter(conflicts, "conflicts");
        if ((mergedBrick instanceof MoveNStepsBrick) && (baseBrick instanceof MoveNStepsBrick) && (remoteBrick instanceof MoveNStepsBrick)) {
            String literalValue = BrickComparatorKt.getLiteralValue(((MoveNStepsBrick) baseBrick).getFormulas().get(0));
            MoveNStepsBrick moveNStepsBrick = (MoveNStepsBrick) mergedBrick;
            String literalValue2 = BrickComparatorKt.getLiteralValue(moveNStepsBrick.getFormulas().get(0));
            String literalValue3 = BrickComparatorKt.getLiteralValue(((MoveNStepsBrick) remoteBrick).getFormulas().get(0));
            if (literalValue == null || literalValue2 == null || literalValue3 == null) {
                return;
            }
            if (!Intrinsics.areEqual(literalValue3, literalValue) && Intrinsics.areEqual(literalValue2, literalValue)) {
                Formula formula = moveNStepsBrick.getFormulas().get(0);
                FormulaElement root = formula != null ? formula.getRoot() : null;
                if (root == null) {
                    return;
                }
                root.setValue(literalValue3);
                return;
            }
            if (Intrinsics.areEqual(literalValue3, literalValue) || Intrinsics.areEqual(literalValue2, literalValue) || Intrinsics.areEqual(literalValue3, literalValue2)) {
                return;
            }
            String string = moveNStepsBrick.getBrickID().toString();
            Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
            conflicts.add(new Conflict(string, "MoveNSteps Value", literalValue, literalValue2, literalValue3));
        }
    }
}
