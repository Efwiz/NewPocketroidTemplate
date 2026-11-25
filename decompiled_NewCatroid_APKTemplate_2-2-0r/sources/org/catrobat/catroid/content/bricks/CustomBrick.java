package org.catrobat.catroid.content.bricks;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.NotImplementedError;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.FormulaElement;
import org.catrobat.catroid.libraries.BrickParameter;
import org.catrobat.catroid.libraries.CustomBrickDefinition;
import org.catrobat.catroid.libraries.CustomBrickManager;
import org.catrobat.catroid.libraries.ParameterType;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/CustomBrick.class
  classes14.dex
 */
/* JADX INFO: compiled from: CustomBrick.kt */
/* JADX INFO: loaded from: classes14.dex */
@XStreamAlias("CustomBrick")
@Metadata(d1 = {"\u0000j\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B\u000f\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0018\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0016J\b\u0010\u0015\u001a\u00020\u0016H\u0016J\u0012\u0010\u0017\u001a\u0004\u0018\u00010\u00182\u0006\u0010\u0019\u001a\u00020\u001aH\u0002J\u0012\u0010\u001b\u001a\u0004\u0018\u00010\u001c2\u0006\u0010\u0019\u001a\u00020\u001aH\u0002J\u0010\u0010\u001d\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u001c0\u001eH\u0002J\u0012\u0010\u001f\u001a\u0004\u0018\u00010 2\u0006\u0010\u0019\u001a\u00020\u001aH\u0002J\u0010\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020$H\u0016J\b\u0010%\u001a\u00020\u001aH\u0016J\b\u0010&\u001a\u00020'H\u0002J\b\u0010(\u001a\u00020\u0010H\u0002R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\u0004R \u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\tX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000e¨\u0006)"}, d2 = {"Lorg/catrobat/catroid/content/bricks/CustomBrick;", "Lorg/catrobat/catroid/content/bricks/FormulaBrick;", "definitionId", "", "(Ljava/lang/String;)V", "getDefinitionId", "()Ljava/lang/String;", "setDefinitionId", "parameterFormulas", "", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getParameterFormulas", "()Ljava/util/List;", "setParameterFormulas", "(Ljava/util/List;)V", "addActionToSequence", "", "sprite", "Lorg/catrobat/catroid/content/Sprite;", "sequence", "Lorg/catrobat/catroid/content/actions/ScriptSequenceAction;", "clone", "Lorg/catrobat/catroid/content/bricks/Brick;", "getBrickFieldForIndex", "Lorg/catrobat/catroid/content/bricks/Brick$BrickField;", "index", "", "getFormulaView", "Landroid/widget/TextView;", "getHeaderTextViews", "", "getSpinnerView", "Landroid/widget/Spinner;", "getView", "Landroid/view/View;", "context", "Landroid/content/Context;", "getViewResource", "readResolve", "", "resetDynamicViews", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CustomBrick extends FormulaBrick {
    private String definitionId;
    private List<Formula> parameterFormulas;

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/CustomBrick$WhenMappings.class
      classes14.dex
     */
    /* JADX INFO: compiled from: CustomBrick.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[ParameterType.values().length];
            try {
                iArr[ParameterType.TEXT_FIELD.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[ParameterType.VARIABLE_DROPDOWN.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public CustomBrick() {
        this(null, 1, 0 == true ? 1 : 0);
    }

    private final Spinner getSpinnerView(int index) {
        return null;
    }

    public /* synthetic */ CustomBrick(String str, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? "" : str);
    }

    public final String getDefinitionId() {
        return this.definitionId;
    }

    public final void setDefinitionId(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.definitionId = str;
    }

    public CustomBrick(String definitionId) {
        Intrinsics.checkNotNullParameter(definitionId, "definitionId");
        this.definitionId = definitionId;
        this.parameterFormulas = new ArrayList();
    }

    public final List<Formula> getParameterFormulas() {
        return this.parameterFormulas;
    }

    public final void setParameterFormulas(List<Formula> list) {
        Intrinsics.checkNotNullParameter(list, "<set-?>");
        this.parameterFormulas = list;
    }

    private final Object readResolve() {
        List<Formula> list = this.parameterFormulas;
        if (list != null && !list.isEmpty()) {
            try {
                Field declaredField = Formula.class.getDeclaredField("formulaTree");
                Intrinsics.checkNotNullExpressionValue(declaredField, "getDeclaredField(...)");
                declaredField.setAccessible(true);
                ArrayList arrayList = new ArrayList();
                Iterator<Formula> it = this.parameterFormulas.iterator();
                while (it.hasNext()) {
                    Object obj = declaredField.get(it.next());
                    FormulaElement formulaElement = obj instanceof FormulaElement ? (FormulaElement) obj : null;
                    if (formulaElement != null) {
                        arrayList.add(new Formula(formulaElement));
                    } else {
                        arrayList.add(new Formula(""));
                    }
                }
                this.parameterFormulas = arrayList;
            } catch (Exception e) {
                Log.e("CustomBrick", "Ошибка восстановления формул через рефлексию", e);
                ArrayList arrayList2 = new ArrayList();
                int size = this.parameterFormulas.size();
                for (int i = 0; i < size; i++) {
                    arrayList2.add(new Formula(""));
                }
                this.parameterFormulas = arrayList2;
            }
        }
        return this;
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        List<BrickParameter> parameters;
        CustomBrickDefinition customBrickDefinitionFindDefinitionById = CustomBrickManager.INSTANCE.findDefinitionById(this.definitionId);
        Integer numValueOf = (customBrickDefinitionFindDefinitionById == null || (parameters = customBrickDefinitionFindDefinitionById.getParameters()) == null) ? null : Integer.valueOf(parameters.size());
        if (numValueOf != null && numValueOf.intValue() == 0) {
            return R.layout.brick_custom_0_param;
        }
        if (numValueOf != null && numValueOf.intValue() == 1) {
            return R.layout.brick_custom_1_param;
        }
        if (numValueOf != null && numValueOf.intValue() == 2) {
            return R.layout.brick_custom_2_param;
        }
        if (numValueOf != null && numValueOf.intValue() == 3) {
            return R.layout.brick_custom_3_param;
        }
        if (numValueOf != null && numValueOf.intValue() == 4) {
            return R.layout.brick_custom_4_param;
        }
        if (numValueOf != null && numValueOf.intValue() == 5) {
            return R.layout.brick_custom_5_param;
        }
        if (numValueOf != null && numValueOf.intValue() == 6) {
            return R.layout.brick_custom_6_param;
        }
        if (numValueOf != null && numValueOf.intValue() == 7) {
            return R.layout.brick_custom_7_param;
        }
        return (numValueOf != null && numValueOf.intValue() == 8) ? R.layout.brick_custom_8_param : R.layout.brick_unknow;
    }

    @Override // org.catrobat.catroid.content.bricks.FormulaBrick, org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public View getView(Context context) {
        TextView textView;
        Intrinsics.checkNotNullParameter(context, "context");
        super.getView(context);
        CustomBrickDefinition customBrickDefinitionFindDefinitionById = CustomBrickManager.INSTANCE.findDefinitionById(this.definitionId);
        if (customBrickDefinitionFindDefinitionById == null) {
            TextView textView2 = (TextView) this.view.findViewById(R.id.custom_brick_header_part_1);
            if (textView2 != null) {
                textView2.setText("Ошибка: блок '" + this.definitionId + "' не найден");
            }
            View view = this.view;
            Intrinsics.checkNotNullExpressionValue(view, "view");
            return view;
        }
        resetDynamicViews();
        List<String> listSplit = new Regex("\\{\\d+\\}").split(customBrickDefinitionFindDefinitionById.getHeaderText(), 0);
        List<TextView> headerTextViews = getHeaderTextViews();
        int i = 0;
        for (Object obj : listSplit) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            String str = (String) obj;
            if (i < headerTextViews.size() && (textView = headerTextViews.get(i)) != null) {
                String str2 = str;
                textView.setText(StringsKt.trim((CharSequence) str2).toString());
                textView.setVisibility(!StringsKt.isBlank(str2) ? 0 : 8);
            }
            i = i2;
        }
        int i3 = 0;
        for (Object obj2 : customBrickDefinitionFindDefinitionById.getParameters()) {
            int i4 = i3 + 1;
            if (i3 < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            BrickParameter brickParameter = (BrickParameter) obj2;
            while (this.parameterFormulas.size() <= i3) {
                this.parameterFormulas.add(new Formula(Double.valueOf(0.0d)));
            }
            Formula formula = this.parameterFormulas.get(i3);
            Brick.BrickField brickFieldForIndex = getBrickFieldForIndex(i3);
            if (brickFieldForIndex == null) {
                Log.e("CustomBrick", "Слишком много параметров для блока " + customBrickDefinitionFindDefinitionById.getId() + ". Параметр " + i4 + " не будет отображен.");
            } else {
                int i5 = WhenMappings.$EnumSwitchMapping$0[brickParameter.getType().ordinal()];
                if (i5 == 1) {
                    TextView formulaView = getFormulaView(i3);
                    if (formulaView != null) {
                        formulaView.setVisibility(0);
                    }
                    Brick.BrickField brickField = brickFieldForIndex;
                    Intrinsics.checkNotNull(formulaView);
                    addAllowedBrickField(brickField, formulaView.getId());
                    setFormulaWithBrickField(brickField, formula);
                } else if (i5 == 2) {
                    Spinner spinnerView = getSpinnerView(i3);
                    if (spinnerView != null) {
                        spinnerView.setVisibility(0);
                    }
                } else {
                    throw new NotImplementedError(null, 1, null);
                }
            }
            i3 = i4;
        }
        if (this instanceof FormulaBrick) {
            setClickListeners();
        }
        View view2 = this.view;
        Intrinsics.checkNotNullExpressionValue(view2, "view");
        return view2;
    }

    private final Brick.BrickField getBrickFieldForIndex(int index) {
        switch (index) {
            case 0:
                return Brick.BrickField.CUSTOM_PARAM_1;
            case 1:
                return Brick.BrickField.CUSTOM_PARAM_2;
            case 2:
                return Brick.BrickField.CUSTOM_PARAM_3;
            case 3:
                return Brick.BrickField.CUSTOM_PARAM_4;
            case 4:
                return Brick.BrickField.CUSTOM_PARAM_5;
            case 5:
                return Brick.BrickField.CUSTOM_PARAM_6;
            case 6:
                return Brick.BrickField.CUSTOM_PARAM_7;
            case 7:
                return Brick.BrickField.CUSTOM_PARAM_8;
            case 8:
                return Brick.BrickField.CUSTOM_PARAM_9;
            case 9:
                return Brick.BrickField.CUSTOM_PARAM_10;
            default:
                return null;
        }
    }

    private final void resetDynamicViews() {
        for (TextView textView : getHeaderTextViews()) {
            if (textView != null) {
                textView.setVisibility(8);
            }
        }
        for (int i = 0; i < 5; i++) {
            TextView formulaView = getFormulaView(i);
            if (formulaView != null) {
                formulaView.setVisibility(8);
            }
            Spinner spinnerView = getSpinnerView(i);
            if (spinnerView != null) {
                spinnerView.setVisibility(8);
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final List<TextView> getHeaderTextViews() {
        return CollectionsKt.listOf((Object[]) new TextView[]{this.view.findViewById(R.id.custom_brick_header_part_1), this.view.findViewById(R.id.custom_brick_header_part_2), this.view.findViewById(R.id.custom_brick_header_part_3), this.view.findViewById(R.id.custom_brick_header_part_4), this.view.findViewById(R.id.custom_brick_header_part_5), this.view.findViewById(R.id.custom_brick_header_part_6), this.view.findViewById(R.id.custom_brick_header_part_7), this.view.findViewById(R.id.custom_brick_header_part_8), this.view.findViewById(R.id.custom_brick_header_part_9)});
    }

    private final TextView getFormulaView(int index) {
        int i;
        switch (index) {
            case 0:
                i = R.id.custom_brick_formula_1;
                break;
            case 1:
                i = R.id.custom_brick_formula_2;
                break;
            case 2:
                i = R.id.custom_brick_formula_3;
                break;
            case 3:
                i = R.id.custom_brick_formula_4;
                break;
            case 4:
                i = R.id.custom_brick_formula_5;
                break;
            case 5:
                i = R.id.custom_brick_formula_6;
                break;
            case 6:
                i = R.id.custom_brick_formula_7;
                break;
            case 7:
                i = R.id.custom_brick_formula_8;
                break;
            case 8:
                i = R.id.custom_brick_formula_9;
                break;
            default:
                i = -1;
                break;
        }
        if (i != -1) {
            return (TextView) this.view.findViewById(i);
        }
        return null;
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        Intrinsics.checkNotNullParameter(sprite, "sprite");
        Intrinsics.checkNotNullParameter(sequence, "sequence");
        CustomBrickDefinition customBrickDefinitionFindDefinitionById = CustomBrickManager.INSTANCE.findDefinitionById(this.definitionId);
        if (customBrickDefinitionFindDefinitionById == null) {
            return;
        }
        sequence.addAction(sprite.getActionFactory().createCustomAction(sprite, sequence, customBrickDefinitionFindDefinitionById, this.parameterFormulas));
    }

    @Override // org.catrobat.catroid.content.bricks.FormulaBrick, org.catrobat.catroid.content.bricks.BrickBaseType
    public Brick clone() {
        CustomBrick customBrick = new CustomBrick(this.definitionId);
        List<Formula> list = this.parameterFormulas;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(((Formula) it.next()).clone());
        }
        customBrick.parameterFormulas = CollectionsKt.toMutableList((Collection) arrayList);
        return customBrick;
    }
}
