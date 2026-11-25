package net.bytebuddy.description.modifier;

/* JADX INFO: loaded from: classes12.dex */
public enum MethodStrictness implements ModifierContributor.ForMethod {
    PLAIN(0),
    STRICT(2048);

    private final int mask;

    @Override // net.bytebuddy.description.modifier.ModifierContributor
    public int getRange() {
        return 2048;
    }

    MethodStrictness(int i) {
        this.mask = i;
    }

    @Override // net.bytebuddy.description.modifier.ModifierContributor
    public int getMask() {
        return this.mask;
    }

    @Override // net.bytebuddy.description.modifier.ModifierContributor
    public boolean isDefault() {
        return this == PLAIN;
    }

    public boolean isStrict() {
        return this == STRICT;
    }
}
