package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import net.mgsx.gltf.scene3d.lights.PointLightEx;
import net.mgsx.gltf.scene3d.lights.SpotLightEx;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.raptor.ThreeDManager;
import org.catrobat.catroid.stage.StageActivity;
import org.catrobat.catroid.stage.StageListener;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/ConfigureLightAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: ConfigureLightAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001bH\u0014R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001c\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u001c\u0010\u0015\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\f\"\u0004\b\u0017\u0010\u000e¨\u0006\u001c"}, d2 = {"Lorg/catrobat/catroid/content/actions/ConfigureLightAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", "configTypeSelection", "", "getConfigTypeSelection", "()I", "setConfigTypeSelection", "(I)V", "lightId", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getLightId", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setLightId", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", "value", "getValue", "setValue", ConfigConstants.CONFIG_KEY_UPDATE, "", "percent", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ConfigureLightAction extends TemporalAction {
    private int configTypeSelection;
    private Formula lightId;
    private Scope scope;
    private Formula value;

    public final Scope getScope() {
        return this.scope;
    }

    public final void setScope(Scope scope) {
        this.scope = scope;
    }

    public final Formula getLightId() {
        return this.lightId;
    }

    public final void setLightId(Formula formula) {
        this.lightId = formula;
    }

    public final Formula getValue() {
        return this.value;
    }

    public final void setValue(Formula formula) {
        this.value = formula;
    }

    public final int getConfigTypeSelection() {
        return this.configTypeSelection;
    }

    public final void setConfigTypeSelection(int i) {
        this.configTypeSelection = i;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        Color color;
        Color color2;
        Vector3 vector3;
        Vector3 vector4;
        Formula formula = this.lightId;
        String strInterpretString = formula != null ? formula.interpretString(this.scope) : null;
        if (strInterpretString == null) {
            strInterpretString = "";
        }
        Formula formula2 = this.value;
        String strInterpretString2 = formula2 != null ? formula2.interpretString(this.scope) : null;
        String str = strInterpretString2 != null ? strInterpretString2 : "";
        if (strInterpretString.length() == 0) {
        }
        StageListener activeStageListener = StageActivity.getActiveStageListener();
        ThreeDManager threeDManager = activeStageListener != null ? activeStageListener.getThreeDManager() : null;
        if (threeDManager == null) {
            return;
        }
        try {
            PointLightEx pointLightEx = threeDManager.getPointLights().get(strInterpretString);
            SpotLightEx spotLightEx = threeDManager.getSpotLights().get(strInterpretString);
            if (pointLightEx == null && spotLightEx == null) {
                return;
            }
            switch (this.configTypeSelection) {
                case 0:
                    Float floatOrNull = StringsKt.toFloatOrNull(str);
                    float fFloatValue = floatOrNull != null ? floatOrNull.floatValue() : 1.0f;
                    if (pointLightEx != null) {
                        pointLightEx.intensity = fFloatValue;
                    }
                    if (spotLightEx != null) {
                        spotLightEx.intensity = fFloatValue;
                    }
                    break;
                case 1:
                    Float floatOrNull2 = StringsKt.toFloatOrNull(str);
                    float fFloatValue2 = floatOrNull2 != null ? floatOrNull2.floatValue() : 10.0f;
                    if (pointLightEx != null) {
                        pointLightEx.range = fFloatValue2 > 0.0f ? Float.valueOf(fFloatValue2) : null;
                    }
                    if (spotLightEx != null) {
                        spotLightEx.range = fFloatValue2 > 0.0f ? Float.valueOf(fFloatValue2) : null;
                    }
                    break;
                case 2:
                    List listSplit$default = StringsKt.split$default((CharSequence) str, new String[]{","}, false, 0, 6, (Object) null);
                    ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listSplit$default, 10));
                    Iterator it = listSplit$default.iterator();
                    while (it.hasNext()) {
                        Float floatOrNull3 = StringsKt.toFloatOrNull(StringsKt.trim((CharSequence) it.next()).toString());
                        arrayList.add(Float.valueOf(floatOrNull3 != null ? floatOrNull3.floatValue() : 1.0f));
                    }
                    ArrayList arrayList2 = arrayList;
                    if (arrayList2.size() >= 3) {
                        if (pointLightEx != null && (color2 = pointLightEx.color) != null) {
                            color2.set(((Number) arrayList2.get(0)).floatValue(), ((Number) arrayList2.get(1)).floatValue(), ((Number) arrayList2.get(2)).floatValue(), 1.0f);
                        }
                        if (spotLightEx != null && (color = spotLightEx.color) != null) {
                            color.set(((Number) arrayList2.get(0)).floatValue(), ((Number) arrayList2.get(1)).floatValue(), ((Number) arrayList2.get(2)).floatValue(), 1.0f);
                            break;
                        }
                    }
                    break;
                case 3:
                    List listSplit$default2 = StringsKt.split$default((CharSequence) str, new String[]{","}, false, 0, 6, (Object) null);
                    ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(listSplit$default2, 10));
                    Iterator it2 = listSplit$default2.iterator();
                    while (it2.hasNext()) {
                        Float floatOrNull4 = StringsKt.toFloatOrNull(StringsKt.trim((CharSequence) it2.next()).toString());
                        arrayList3.add(Float.valueOf(floatOrNull4 != null ? floatOrNull4.floatValue() : 0.0f));
                    }
                    ArrayList arrayList4 = arrayList3;
                    if (arrayList4.size() >= 3) {
                        if (pointLightEx != null && (vector4 = pointLightEx.position) != null) {
                            vector4.set(((Number) arrayList4.get(0)).floatValue(), ((Number) arrayList4.get(1)).floatValue(), ((Number) arrayList4.get(2)).floatValue());
                        }
                        if (spotLightEx != null && (vector3 = spotLightEx.position) != null) {
                            vector3.set(((Number) arrayList4.get(0)).floatValue(), ((Number) arrayList4.get(1)).floatValue(), ((Number) arrayList4.get(2)).floatValue());
                            break;
                        }
                    }
                    break;
                case 4:
                    List listSplit$default3 = StringsKt.split$default((CharSequence) str, new String[]{","}, false, 0, 6, (Object) null);
                    ArrayList arrayList5 = new ArrayList(CollectionsKt.collectionSizeOrDefault(listSplit$default3, 10));
                    Iterator it3 = listSplit$default3.iterator();
                    while (it3.hasNext()) {
                        Float floatOrNull5 = StringsKt.toFloatOrNull(StringsKt.trim((CharSequence) it3.next()).toString());
                        arrayList5.add(Float.valueOf(floatOrNull5 != null ? floatOrNull5.floatValue() : 0.0f));
                    }
                    ArrayList arrayList6 = arrayList5;
                    if (arrayList6.size() >= 3 && spotLightEx != null) {
                        spotLightEx.direction.set(((Number) arrayList6.get(0)).floatValue(), ((Number) arrayList6.get(1)).floatValue(), ((Number) arrayList6.get(2)).floatValue()).nor();
                        break;
                    }
                    break;
                case 5:
                    Float floatOrNull6 = StringsKt.toFloatOrNull(str);
                    float fFloatValue3 = floatOrNull6 != null ? floatOrNull6.floatValue() : 45.0f;
                    if (spotLightEx != null) {
                        float f = spotLightEx.cutoffAngle;
                        spotLightEx.setConeDeg(fFloatValue3, 0.8f * fFloatValue3);
                    }
                    break;
                case 6:
                    Float floatOrNull7 = StringsKt.toFloatOrNull(str);
                    float fFloatValue4 = floatOrNull7 != null ? floatOrNull7.floatValue() : 0.5f;
                    if (spotLightEx != null) {
                        float f2 = spotLightEx.cutoffAngle;
                        spotLightEx.setConeDeg(f2, (1.0f - RangesKt.coerceIn(fFloatValue4, 0.0f, 1.0f)) * f2);
                    }
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
