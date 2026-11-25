package com.reandroid.apkeditor.refactor;

import com.reandroid.identifiers.ResourceIdentifier;
import com.reandroid.identifiers.TypeIdentifier;

/* JADX INFO: loaded from: classes10.dex */
public class EntryRefactor {
    private final TypeIdentifier mTypeIdentifier;

    private boolean refactorByValue(ResourceIdentifier resourceIdentifier) {
        return false;
    }

    public EntryRefactor(TypeIdentifier typeIdentifier) {
        this.mTypeIdentifier = typeIdentifier;
    }

    public int refactorAll() {
        int i = 0;
        for (ResourceIdentifier resourceIdentifier : this.mTypeIdentifier.getItems()) {
            if (resourceIdentifier.isGeneratedName() && refactor(resourceIdentifier)) {
                i++;
            }
        }
        return i;
    }

    private boolean refactor(ResourceIdentifier resourceIdentifier) {
        return refactorByValue(resourceIdentifier);
    }
}
