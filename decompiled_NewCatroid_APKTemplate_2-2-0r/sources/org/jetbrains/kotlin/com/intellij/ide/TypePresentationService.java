package org.jetbrains.kotlin.com.intellij.ide;

import javax.swing.Icon;
import org.jetbrains.kotlin.com.intellij.openapi.application.ApplicationManager;

/* JADX INFO: loaded from: classes3.dex */
public abstract class TypePresentationService {
    public abstract Icon getIcon(Object obj);

    public static TypePresentationService getService() {
        return (TypePresentationService) ApplicationManager.getApplication().getService(TypePresentationService.class);
    }
}
