package org.jf.dexlib2.builder;

import java.util.HashMap;
import org.jf.dexlib2.iface.MethodImplementation;
import org.jf.dexlib2.iface.reference.StringReference;
import org.jf.dexlib2.iface.reference.TypeReference;

/* JADX INFO: loaded from: classes6.dex */
public class MethodImplementationBuilder {
    private MethodLocation currentLocation;
    private final MutableMethodImplementation impl;
    private final HashMap<String, Label> labels = new HashMap<>();

    public MethodImplementationBuilder(int i) {
        MutableMethodImplementation mutableMethodImplementation = new MutableMethodImplementation(i);
        this.impl = mutableMethodImplementation;
        this.currentLocation = mutableMethodImplementation.instructionList.get(0);
    }

    public MethodImplementation getMethodImplementation() {
        return this.impl;
    }

    public Label addLabel(String str) {
        Label label = this.labels.get(str);
        if (label != null) {
            if (label.isPlaced()) {
                throw new IllegalArgumentException("There is already a label with that name.");
            }
            this.currentLocation.getLabels().add(label);
            return label;
        }
        Label labelAddNewLabel = this.currentLocation.addNewLabel();
        this.labels.put(str, labelAddNewLabel);
        return labelAddNewLabel;
    }

    public Label getLabel(String str) {
        Label label = this.labels.get(str);
        if (label != null) {
            return label;
        }
        Label label2 = new Label();
        this.labels.put(str, label2);
        return label2;
    }

    public void addCatch(TypeReference typeReference, Label label, Label label2, Label label3) {
        this.impl.addCatch(typeReference, label, label2, label3);
    }

    public void addCatch(String str, Label label, Label label2, Label label3) {
        this.impl.addCatch(str, label, label2, label3);
    }

    public void addCatch(Label label, Label label2, Label label3) {
        this.impl.addCatch(label, label2, label3);
    }

    public void addLineNumber(int i) {
        this.currentLocation.addLineNumber(i);
    }

    public void addStartLocal(int i, StringReference stringReference, TypeReference typeReference, StringReference stringReference2) {
        this.currentLocation.addStartLocal(i, stringReference, typeReference, stringReference2);
    }

    public void addEndLocal(int i) {
        this.currentLocation.addEndLocal(i);
    }

    public void addRestartLocal(int i) {
        this.currentLocation.addRestartLocal(i);
    }

    public void addPrologue() {
        this.currentLocation.addPrologue();
    }

    public void addEpilogue() {
        this.currentLocation.addEpilogue();
    }

    public void addSetSourceFile(StringReference stringReference) {
        this.currentLocation.addSetSourceFile(stringReference);
    }

    public void addInstruction(BuilderInstruction builderInstruction) {
        this.impl.addInstruction(builderInstruction);
        this.currentLocation = this.impl.instructionList.get(this.impl.instructionList.size() - 1);
    }
}
