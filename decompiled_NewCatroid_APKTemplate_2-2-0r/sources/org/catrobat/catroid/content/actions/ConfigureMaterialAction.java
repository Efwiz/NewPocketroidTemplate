package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.Attribute;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import com.badlogic.gdx.utils.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import net.mgsx.gltf.scene3d.attributes.PBRColorAttribute;
import net.mgsx.gltf.scene3d.attributes.PBRFloatAttribute;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.raptor.ThreeDManager;
import org.catrobat.catroid.stage.StageActivity;
import org.catrobat.catroid.stage.StageListener;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/ConfigureMaterialAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: ConfigureMaterialAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001bH\u0014R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001c\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u001c\u0010\u0015\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\f\"\u0004\b\u0017\u0010\u000e¨\u0006\u001c"}, d2 = {"Lorg/catrobat/catroid/content/actions/ConfigureMaterialAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", "configTypeSelection", "", "getConfigTypeSelection", "()I", "setConfigTypeSelection", "(I)V", "objectId", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getObjectId", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setObjectId", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", "value", "getValue", "setValue", ConfigConstants.CONFIG_KEY_UPDATE, "", "percent", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ConfigureMaterialAction extends TemporalAction {
    private int configTypeSelection;
    private Formula objectId;
    private Scope scope;
    private Formula value;

    public final Scope getScope() {
        return this.scope;
    }

    public final void setScope(Scope scope) {
        this.scope = scope;
    }

    public final Formula getObjectId() {
        return this.objectId;
    }

    public final void setObjectId(Formula formula) {
        this.objectId = formula;
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
        Formula formula = this.objectId;
        String strInterpretString = formula != null ? formula.interpretString(this.scope) : null;
        if (strInterpretString == null) {
            strInterpretString = "";
        }
        Formula formula2 = this.value;
        String strInterpretString2 = formula2 != null ? formula2.interpretString(this.scope) : null;
        String str = strInterpretString2 != null ? strInterpretString2 : "";
        if (strInterpretString.length() == 0) {
            return;
        }
        StageListener activeStageListener = StageActivity.getActiveStageListener();
        ThreeDManager threeDManager = activeStageListener != null ? activeStageListener.getThreeDManager() : null;
        if (threeDManager == null) {
            return;
        }
        try {
            ModelInstance modelInstance = threeDManager.getModelInstance(strInterpretString);
            if (modelInstance == null) {
                return;
            }
            Array.ArrayIterator<Material> it = modelInstance.materials.iterator();
            while (it.hasNext()) {
                Material next = it.next();
                int i = this.configTypeSelection;
                if (i == 0) {
                    List listSplit$default = StringsKt.split$default((CharSequence) str, new String[]{","}, false, 0, 6, (Object) null);
                    ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listSplit$default, 10));
                    Iterator it2 = listSplit$default.iterator();
                    while (it2.hasNext()) {
                        Float floatOrNull = StringsKt.toFloatOrNull(StringsKt.trim((CharSequence) it2.next()).toString());
                        arrayList.add(Float.valueOf(floatOrNull != null ? floatOrNull.floatValue() : 1.0f));
                    }
                    ArrayList arrayList2 = arrayList;
                    if (arrayList2.size() >= 3) {
                        next.set(PBRColorAttribute.createBaseColorFactor(new Color(((Number) arrayList2.get(0)).floatValue(), ((Number) arrayList2.get(1)).floatValue(), ((Number) arrayList2.get(2)).floatValue(), arrayList2.size() >= 4 ? ((Number) arrayList2.get(3)).floatValue() : 1.0f)));
                    }
                } else if (i == 1) {
                    Float floatOrNull2 = StringsKt.toFloatOrNull(str);
                    next.set(PBRFloatAttribute.createMetallic(RangesKt.coerceIn(floatOrNull2 != null ? floatOrNull2.floatValue() : 0.0f, 0.0f, 1.0f)));
                } else if (i == 2) {
                    Float floatOrNull3 = StringsKt.toFloatOrNull(str);
                    next.set(PBRFloatAttribute.createRoughness(RangesKt.coerceIn(floatOrNull3 != null ? floatOrNull3.floatValue() : 1.0f, 0.0f, 1.0f)));
                } else if (i == 3) {
                    List listSplit$default2 = StringsKt.split$default((CharSequence) str, new String[]{","}, false, 0, 6, (Object) null);
                    ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(listSplit$default2, 10));
                    Iterator it3 = listSplit$default2.iterator();
                    while (it3.hasNext()) {
                        Float floatOrNull4 = StringsKt.toFloatOrNull(StringsKt.trim((CharSequence) it3.next()).toString());
                        arrayList3.add(Float.valueOf(floatOrNull4 != null ? floatOrNull4.floatValue() : 1.0f));
                    }
                    ArrayList arrayList4 = arrayList3;
                    if (arrayList4.size() >= 2) {
                        for (Attribute attribute : next) {
                            if (attribute instanceof TextureAttribute) {
                                ((TextureAttribute) attribute).scaleU = ((Number) arrayList4.get(0)).floatValue();
                                ((TextureAttribute) attribute).scaleV = ((Number) arrayList4.get(1)).floatValue();
                            }
                        }
                    }
                } else if (i == 4) {
                    List listSplit$default3 = StringsKt.split$default((CharSequence) str, new String[]{","}, false, 0, 6, (Object) null);
                    ArrayList arrayList5 = new ArrayList(CollectionsKt.collectionSizeOrDefault(listSplit$default3, 10));
                    Iterator it4 = listSplit$default3.iterator();
                    while (it4.hasNext()) {
                        Float floatOrNull5 = StringsKt.toFloatOrNull(StringsKt.trim((CharSequence) it4.next()).toString());
                        arrayList5.add(Float.valueOf(floatOrNull5 != null ? floatOrNull5.floatValue() : 0.0f));
                    }
                    ArrayList arrayList6 = arrayList5;
                    if (arrayList6.size() >= 3) {
                        next.set(PBRColorAttribute.createEmissive(new Color(((Number) arrayList6.get(0)).floatValue(), ((Number) arrayList6.get(1)).floatValue(), ((Number) arrayList6.get(2)).floatValue(), 1.0f)));
                    }
                } else if (i == 5) {
                    Float floatOrNull6 = StringsKt.toFloatOrNull(str);
                    next.set(PBRFloatAttribute.createEmissiveIntensity(floatOrNull6 != null ? floatOrNull6.floatValue() : 1.0f));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
