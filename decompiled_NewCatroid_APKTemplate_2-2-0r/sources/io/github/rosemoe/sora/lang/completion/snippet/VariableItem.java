package io.github.rosemoe.sora.lang.completion.snippet;

/* JADX INFO: loaded from: classes11.dex */
public class VariableItem extends SnippetItem implements PlaceHolderElement {
    private String defaultValue;

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    private String f271name;
    private Transform transform;

    public VariableItem(int i, String str, String str2) {
        this(i, str, str2, null);
    }

    public VariableItem(int i, String str, String str2, Transform transform) {
        super(i);
        this.f271name = str;
        this.defaultValue = str2;
        this.transform = transform;
    }

    public void setTransform(Transform transform) {
        this.transform = transform;
    }

    public Transform getTransform() {
        return this.transform;
    }

    public void setName(String str) {
        this.f271name = str;
    }

    public String getName() {
        return this.f271name;
    }

    public void setDefaultValue(String str) {
        this.defaultValue = str;
    }

    public String getDefaultValue() {
        return this.defaultValue;
    }

    @Override // io.github.rosemoe.sora.lang.completion.snippet.SnippetItem
    /* JADX INFO: renamed from: clone */
    public VariableItem mo2801clone() {
        VariableItem variableItem = new VariableItem(getStartIndex(), this.f271name, this.defaultValue);
        variableItem.setIndex(getStartIndex(), getEndIndex());
        return variableItem;
    }
}
