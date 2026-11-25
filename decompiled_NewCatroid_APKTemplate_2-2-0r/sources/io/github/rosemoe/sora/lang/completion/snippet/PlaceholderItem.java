package io.github.rosemoe.sora.lang.completion.snippet;

/* JADX INFO: loaded from: classes11.dex */
public class PlaceholderItem extends SnippetItem {
    private PlaceholderDefinition definition;
    private String text;

    public PlaceholderItem(PlaceholderDefinition placeholderDefinition, int i) {
        setIndex(i, i);
        this.definition = placeholderDefinition;
    }

    private PlaceholderItem(PlaceholderDefinition placeholderDefinition, int i, int i2) {
        setIndex(i, i2);
        this.text = this.text;
        this.definition = placeholderDefinition;
    }

    public void setDefinition(PlaceholderDefinition placeholderDefinition) {
        this.definition = placeholderDefinition;
    }

    public PlaceholderDefinition getDefinition() {
        return this.definition;
    }

    @Override // io.github.rosemoe.sora.lang.completion.snippet.SnippetItem
    /* JADX INFO: renamed from: clone */
    public PlaceholderItem mo2801clone() {
        return new PlaceholderItem(this.definition, getStartIndex(), getEndIndex());
    }
}
