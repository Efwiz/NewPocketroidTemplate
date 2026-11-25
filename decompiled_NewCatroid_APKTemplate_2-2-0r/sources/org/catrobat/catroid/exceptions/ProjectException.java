package org.catrobat.catroid.exceptions;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/exceptions/ProjectException.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public abstract class ProjectException extends Exception {
    private static final long serialVersionUID = 1;
    private final String uiErrorMessage;

    public ProjectException() {
        this.uiErrorMessage = "";
    }

    public ProjectException(String message) {
        super(message);
        this.uiErrorMessage = message;
    }

    public String getUiErrorMessage() {
        return this.uiErrorMessage;
    }
}
