package org.jf.dexlib2.iface;

import java.util.Set;
import org.jf.dexlib2.HiddenApiRestriction;

/* JADX INFO: loaded from: classes6.dex */
public interface Member extends Annotatable {
    int getAccessFlags();

    String getDefiningClass();

    Set<HiddenApiRestriction> getHiddenApiRestrictions();

    String getName();
}
