package org.jf.dexlib2.immutable.instruction;

import java.util.List;
import org.jf.dexlib2.iface.instruction.SwitchElement;
import org.jf.util.ImmutableConverter;

/* JADX INFO: loaded from: classes6.dex */
public class ImmutableSwitchElement implements SwitchElement {
    private static final ImmutableConverter<ImmutableSwitchElement, SwitchElement> CONVERTER = new ImmutableConverter<ImmutableSwitchElement, SwitchElement>() { // from class: org.jf.dexlib2.immutable.instruction.ImmutableSwitchElement.1
        /* JADX INFO: Access modifiers changed from: protected */
        @Override // org.jf.util.ImmutableConverter
        public boolean isImmutable(SwitchElement switchElement) {
            return switchElement instanceof ImmutableSwitchElement;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // org.jf.util.ImmutableConverter
        public ImmutableSwitchElement makeImmutable(SwitchElement switchElement) {
            return ImmutableSwitchElement.of(switchElement);
        }
    };
    protected final int key;
    protected final int offset;

    public ImmutableSwitchElement(int i, int i2) {
        this.key = i;
        this.offset = i2;
    }

    public static ImmutableSwitchElement of(SwitchElement switchElement) {
        if (switchElement instanceof ImmutableSwitchElement) {
            return (ImmutableSwitchElement) switchElement;
        }
        return new ImmutableSwitchElement(switchElement.getKey(), switchElement.getOffset());
    }

    @Override // org.jf.dexlib2.iface.instruction.SwitchElement
    public int getKey() {
        return this.key;
    }

    @Override // org.jf.dexlib2.iface.instruction.SwitchElement
    public int getOffset() {
        return this.offset;
    }

    public static List<ImmutableSwitchElement> immutableListOf(List<? extends SwitchElement> list) {
        return CONVERTER.toList(list);
    }
}
