package org.jf.dexlib2.builder;

import java.util.Set;
import org.jf.dexlib2.builder.debug.BuilderEndLocal;
import org.jf.dexlib2.builder.debug.BuilderEpilogueBegin;
import org.jf.dexlib2.builder.debug.BuilderLineNumber;
import org.jf.dexlib2.builder.debug.BuilderPrologueEnd;
import org.jf.dexlib2.builder.debug.BuilderRestartLocal;
import org.jf.dexlib2.builder.debug.BuilderSetSourceFile;
import org.jf.dexlib2.builder.debug.BuilderStartLocal;
import org.jf.dexlib2.iface.instruction.Instruction;
import org.jf.dexlib2.iface.reference.StringReference;
import org.jf.dexlib2.iface.reference.TypeReference;

/* JADX INFO: loaded from: classes6.dex */
public class MethodLocation {
    int codeAddress;
    int index;
    BuilderInstruction instruction;
    private final LocatedItems<BuilderDebugItem> debugItems = new LocatedDebugItems();
    private final LocatedItems<Label> labels = new LocatedLabels();

    MethodLocation(BuilderInstruction builderInstruction, int i, int i2) {
        this.instruction = builderInstruction;
        this.codeAddress = i;
        this.index = i2;
    }

    public Instruction getInstruction() {
        return this.instruction;
    }

    public int getCodeAddress() {
        return this.codeAddress;
    }

    public int getIndex() {
        return this.index;
    }

    void mergeInto(MethodLocation methodLocation) {
        this.labels.mergeItemsIntoNext(methodLocation, methodLocation.labels);
        this.debugItems.mergeItemsIntoNext(methodLocation, methodLocation.debugItems);
    }

    public Set<Label> getLabels() {
        return this.labels.getModifiableItems(this);
    }

    public Label addNewLabel() {
        Label label = new Label();
        getLabels().add(label);
        return label;
    }

    public Set<BuilderDebugItem> getDebugItems() {
        return this.debugItems.getModifiableItems(this);
    }

    public void addLineNumber(int i) {
        getDebugItems().add(new BuilderLineNumber(i));
    }

    public void addStartLocal(int i, StringReference stringReference, TypeReference typeReference, StringReference stringReference2) {
        getDebugItems().add(new BuilderStartLocal(i, stringReference, typeReference, stringReference2));
    }

    public void addEndLocal(int i) {
        getDebugItems().add(new BuilderEndLocal(i));
    }

    public void addRestartLocal(int i) {
        getDebugItems().add(new BuilderRestartLocal(i));
    }

    public void addPrologue() {
        getDebugItems().add(new BuilderPrologueEnd());
    }

    public void addEpilogue() {
        getDebugItems().add(new BuilderEpilogueBegin());
    }

    public void addSetSourceFile(StringReference stringReference) {
        getDebugItems().add(new BuilderSetSourceFile(stringReference));
    }
}
