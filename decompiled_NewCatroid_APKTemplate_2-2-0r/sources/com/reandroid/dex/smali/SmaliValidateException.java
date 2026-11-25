package com.reandroid.dex.smali;

import com.reandroid.common.Origin;
import com.reandroid.dex.smali.model.Smali;
import com.reandroid.dex.smali.model.SmaliDef;
import java.io.IOException;

/* JADX INFO: loaded from: classes11.dex */
public class SmaliValidateException extends IOException {
    private final Smali smali;

    public SmaliValidateException(String str, Smali smali) {
        super(str);
        this.smali = smali;
    }

    @Override // java.lang.Throwable
    public String getMessage() {
        String message = super.getMessage();
        Smali parentInstance = this.smali;
        if (parentInstance == null) {
            return message;
        }
        Origin origin = parentInstance.getOrigin();
        if (origin != null) {
            return message + "\n" + origin;
        }
        if (!(parentInstance instanceof SmaliDef)) {
            parentInstance = parentInstance.getParentInstance(SmaliDef.class);
        }
        return parentInstance == null ? message : message + "\n at " + parentInstance.toDebugString();
    }
}
