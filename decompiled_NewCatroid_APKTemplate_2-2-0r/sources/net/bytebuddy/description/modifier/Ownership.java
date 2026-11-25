package net.bytebuddy.description.modifier;

/* JADX INFO: loaded from: classes12.dex */
public enum Ownership implements ModifierContributor.ForField, ModifierContributor.ForMethod, ModifierContributor.ForType {
    MEMBER(0),
    STATIC(8);

    private final int mask;

    @Override // net.bytebuddy.description.modifier.ModifierContributor
    public int getRange() {
        return 8;
    }

    Ownership(int i) {
        this.mask = i;
    }

    @Override // net.bytebuddy.description.modifier.ModifierContributor
    public int getMask() {
        return this.mask;
    }

    @Override // net.bytebuddy.description.modifier.ModifierContributor
    public boolean isDefault() {
        return this == MEMBER;
    }

    public boolean isStatic() {
        return this == STATIC;
    }
}
