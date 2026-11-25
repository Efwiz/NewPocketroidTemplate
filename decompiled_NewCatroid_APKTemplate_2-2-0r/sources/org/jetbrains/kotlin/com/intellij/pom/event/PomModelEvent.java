package org.jetbrains.kotlin.com.intellij.pom.event;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.Collections;
import java.util.EventObject;
import java.util.Set;
import org.jetbrains.kotlin.com.intellij.pom.PomModel;
import org.jetbrains.kotlin.com.intellij.pom.PomModelAspect;

/* JADX INFO: loaded from: classes3.dex */
public class PomModelEvent extends EventObject {
    private PomChangeSet myChangeSet;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 2) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 1 || i == 2) ? 2 : 3];
        if (i == 1 || i == 2) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/pom/event/PomModelEvent";
        } else if (i == 3) {
            objArr[0] = "aspect";
        } else if (i != 4) {
            objArr[0] = FirebaseAnalytics.Param.SOURCE;
        } else {
            objArr[0] = "event";
        }
        if (i == 1 || i == 2) {
            objArr[1] = "getChangedAspects";
        } else {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/pom/event/PomModelEvent";
        }
        if (i != 1 && i != 2) {
            if (i == 3) {
                objArr[2] = "getChangeSet";
            } else if (i != 4) {
                objArr[2] = "<init>";
            } else {
                objArr[2] = "merge";
            }
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 2) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PomModelEvent(PomModel pomModel, PomChangeSet pomChangeSet) {
        super(pomModel);
        if (pomModel == null) {
            $$$reportNull$$$0(0);
        }
        this.myChangeSet = pomChangeSet;
    }

    public Set<PomModelAspect> getChangedAspects() {
        PomChangeSet pomChangeSet = this.myChangeSet;
        if (pomChangeSet != null) {
            Set<PomModelAspect> setSingleton = Collections.singleton(pomChangeSet.getAspect());
            if (setSingleton == null) {
                $$$reportNull$$$0(1);
            }
            return setSingleton;
        }
        Set<PomModelAspect> setEmptySet = Collections.emptySet();
        if (setEmptySet == null) {
            $$$reportNull$$$0(2);
        }
        return setEmptySet;
    }

    public PomChangeSet getChangeSet(PomModelAspect pomModelAspect) {
        if (pomModelAspect == null) {
            $$$reportNull$$$0(3);
        }
        PomChangeSet pomChangeSet = this.myChangeSet;
        if (pomChangeSet == null || !pomModelAspect.equals(pomChangeSet.getAspect())) {
            return null;
        }
        return this.myChangeSet;
    }

    public void merge(PomModelEvent pomModelEvent) {
        PomChangeSet pomChangeSet;
        if (pomModelEvent == null) {
            $$$reportNull$$$0(4);
        }
        PomChangeSet pomChangeSet2 = pomModelEvent.myChangeSet;
        if (pomChangeSet2 != null && (pomChangeSet = this.myChangeSet) != null) {
            pomChangeSet.merge(pomChangeSet2);
        } else if (this.myChangeSet == null) {
            this.myChangeSet = pomChangeSet2;
        }
    }

    @Override // java.util.EventObject
    public PomModel getSource() {
        return (PomModel) super.getSource();
    }

    public void beforeNestedTransaction() {
        PomChangeSet pomChangeSet = this.myChangeSet;
        if (pomChangeSet != null) {
            pomChangeSet.beforeNestedTransaction();
        }
    }
}
