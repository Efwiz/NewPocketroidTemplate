package org.catrobat.catroid.userbrick;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import java.io.Serializable;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/userbrick/UserDefinedBrickLabel.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
@XStreamAlias("userDefinedBrickLabel")
public class UserDefinedBrickLabel extends UserDefinedBrickData implements Serializable {

    @XStreamAlias(CoroutineCodegenUtilKt.COROUTINE_LABEL_FIELD_NAME)
    private String label;

    public UserDefinedBrickLabel(String label) {
        this.label = label;
        this.type = UserDefinedBrickData.UserDefinedBrickDataType.LABEL;
    }

    public UserDefinedBrickLabel(UserDefinedBrickLabel userDefinedBrickLabel) {
        this.label = userDefinedBrickLabel.label;
        this.type = UserDefinedBrickData.UserDefinedBrickDataType.LABEL;
    }

    @Override // org.catrobat.catroid.userbrick.UserDefinedBrickData, org.catrobat.catroid.common.Nameable
    public String getName() {
        return this.label;
    }

    public boolean equals(Object obj) {
        if (obj instanceof UserDefinedBrickLabel) {
            return this.label.equals(((UserDefinedBrickLabel) obj).label);
        }
        return false;
    }

    public int hashCode() {
        return this.label.hashCode();
    }
}
