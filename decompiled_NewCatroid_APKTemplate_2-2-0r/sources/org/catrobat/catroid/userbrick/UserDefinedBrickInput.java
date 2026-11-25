package org.catrobat.catroid.userbrick;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import java.io.Serializable;
import java.util.UUID;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.UserData;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/userbrick/UserDefinedBrickInput.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
@XStreamAlias("userDefinedBrickInput")
public class UserDefinedBrickInput extends UserDefinedBrickData implements Serializable, UserData<Formula> {
    private int initialIndex = -1;

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    @XStreamAlias("input")
    private InputFormulaField f605name;
    private transient Formula value;

    @Override // org.catrobat.catroid.common.Nameable
    public void setName(String name2) {
    }

    public UserDefinedBrickInput(String input) {
        this.f605name = new InputFormulaField(input);
        this.type = UserDefinedBrickData.UserDefinedBrickDataType.INPUT;
        this.value = new Formula((Integer) 0);
    }

    public UserDefinedBrickInput(UserDefinedBrickInput userDefinedBrickInput) {
        this.f605name = userDefinedBrickInput.f605name;
        this.type = UserDefinedBrickData.UserDefinedBrickDataType.INPUT;
        this.value = userDefinedBrickInput.value;
    }

    @Override // org.catrobat.catroid.userbrick.UserDefinedBrickData, org.catrobat.catroid.common.Nameable
    public String getName() {
        return this.f605name.toString();
    }

    public int getInitialIndex() {
        return this.initialIndex;
    }

    public void setInitialIndex(int initialIndex) {
        this.initialIndex = initialIndex;
    }

    public InputFormulaField getInputFormulaField() {
        return this.f605name;
    }

    @Override // org.catrobat.catroid.formulaeditor.UserData
    public Formula getValue() {
        if (this.value == null) {
            this.value = new Formula((Integer) 0);
        }
        return this.value;
    }

    @Override // org.catrobat.catroid.formulaeditor.UserData
    public void setValue(Formula value) {
        this.value = value;
    }

    @Override // org.catrobat.catroid.formulaeditor.UserData
    public void reset() {
        this.value = new Formula((Integer) 0);
    }

    @Override // org.catrobat.catroid.formulaeditor.UserData
    public UUID getDeviceKey() {
        return UUID.nameUUIDFromBytes(this.f605name.toString().getBytes());
    }
}
