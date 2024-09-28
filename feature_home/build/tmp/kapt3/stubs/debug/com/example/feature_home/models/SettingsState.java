package com.example.feature_home.models;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0014\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001Bg\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0003\u0012\b\b\u0002\u0010\b\u001a\u00020\u0003\u0012\b\b\u0002\u0010\t\u001a\u00020\u0003\u0012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u000b\u0012\u000e\b\u0002\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000b0\r\u00a2\u0006\u0002\u0010\u000eJ\t\u0010\'\u001a\u00020\u0003H\u00c6\u0003J\t\u0010(\u001a\u00020\u0003H\u00c6\u0003J\t\u0010)\u001a\u00020\u0003H\u00c6\u0003J\t\u0010*\u001a\u00020\u0003H\u00c6\u0003J\t\u0010+\u001a\u00020\u0003H\u00c6\u0003J\t\u0010,\u001a\u00020\u0003H\u00c6\u0003J\t\u0010-\u001a\u00020\u0003H\u00c6\u0003J\u000b\u0010.\u001a\u0004\u0018\u00010\u000bH\u00c6\u0003J\u000f\u0010/\u001a\b\u0012\u0004\u0012\u00020\u000b0\rH\u00c6\u0003Jk\u00100\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00032\b\b\u0002\u0010\u0007\u001a\u00020\u00032\b\b\u0002\u0010\b\u001a\u00020\u00032\b\b\u0002\u0010\t\u001a\u00020\u00032\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u000b2\u000e\b\u0002\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000b0\rH\u00c6\u0001J\u0013\u00101\u001a\u00020\u00032\b\u00102\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u00103\u001a\u00020\u0014H\u00d6\u0001J\t\u00104\u001a\u000205H\u00d6\u0001R\u001a\u0010\u000f\u001a\u00020\u00108F\u00f8\u0001\u0000\u00f8\u0001\u0001\u00f8\u0001\u0002\u00a2\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\u0013\u001a\u00020\u00148G\u00a2\u0006\u0006\u001a\u0004\b\u0015\u0010\u0016R\u0017\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000b0\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0011\u0010\u0005\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0019R\u0011\u0010\u001a\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0019R\u0011\u0010\u0007\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\u0019R\u0011\u0010\b\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0019R\u0011\u0010\u0006\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0019R\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\u0019R\u0011\u0010\t\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\u0019R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u0019R\u0011\u0010\u001b\u001a\u00020\u001c\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001eR\u0011\u0010\u001f\u001a\u00020 \u00a2\u0006\b\n\u0000\u001a\u0004\b!\u0010\"R\u0011\u0010#\u001a\u00020 \u00a2\u0006\b\n\u0000\u001a\u0004\b$\u0010\"R\u0013\u0010\n\u001a\u0004\u0018\u00010\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b%\u0010&\u0082\u0002\u000f\n\u0002\b\u0019\n\u0005\b\u00a1\u001e0\u0001\n\u0002\b!\u00a8\u00066"}, d2 = {"Lcom/example/feature_home/models/SettingsState;", "", "isVertical", "", "isLoading", "isAutoConnect", "isKeyboardVisible", "isConnected", "isInstrumentsListOpened", "isTryingToConnect", "selectedInstrument", "Lcom/tyom/domain/models/Instrument;", "instruments", "", "(ZZZZZZZLcom/tyom/domain/models/Instrument;Ljava/util/List;)V", "connectBtnColor", "Landroidx/compose/ui/graphics/Color;", "getConnectBtnColor-0d7_KjU", "()J", "connectBtnText", "", "getConnectBtnText", "()I", "getInstruments", "()Ljava/util/List;", "()Z", "isConnectBtnEnabled", "keyboardAlign", "Landroidx/compose/ui/Alignment;", "getKeyboardAlign", "()Landroidx/compose/ui/Alignment;", "keyboardAngle", "", "getKeyboardAngle", "()F", "keyboardScale", "getKeyboardScale", "getSelectedInstrument", "()Lcom/tyom/domain/models/Instrument;", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "other", "hashCode", "toString", "", "feature_home_debug"})
public final class SettingsState {
    private final boolean isVertical = false;
    private final boolean isLoading = false;
    private final boolean isAutoConnect = false;
    private final boolean isKeyboardVisible = false;
    private final boolean isConnected = false;
    private final boolean isInstrumentsListOpened = false;
    private final boolean isTryingToConnect = false;
    @org.jetbrains.annotations.Nullable
    private final com.tyom.domain.models.Instrument selectedInstrument = null;
    @org.jetbrains.annotations.NotNull
    private final java.util.List<com.tyom.domain.models.Instrument> instruments = null;
    private final float keyboardScale = 0.0F;
    private final float keyboardAngle = 0.0F;
    @org.jetbrains.annotations.NotNull
    private final androidx.compose.ui.Alignment keyboardAlign = null;
    private final boolean isConnectBtnEnabled = false;
    
    public SettingsState(boolean isVertical, boolean isLoading, boolean isAutoConnect, boolean isKeyboardVisible, boolean isConnected, boolean isInstrumentsListOpened, boolean isTryingToConnect, @org.jetbrains.annotations.Nullable
    com.tyom.domain.models.Instrument selectedInstrument, @org.jetbrains.annotations.NotNull
    java.util.List<com.tyom.domain.models.Instrument> instruments) {
        super();
    }
    
    public final boolean isVertical() {
        return false;
    }
    
    public final boolean isLoading() {
        return false;
    }
    
    public final boolean isAutoConnect() {
        return false;
    }
    
    public final boolean isKeyboardVisible() {
        return false;
    }
    
    public final boolean isConnected() {
        return false;
    }
    
    public final boolean isInstrumentsListOpened() {
        return false;
    }
    
    public final boolean isTryingToConnect() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable
    public final com.tyom.domain.models.Instrument getSelectedInstrument() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<com.tyom.domain.models.Instrument> getInstruments() {
        return null;
    }
    
    public final float getKeyboardScale() {
        return 0.0F;
    }
    
    public final float getKeyboardAngle() {
        return 0.0F;
    }
    
    @org.jetbrains.annotations.NotNull
    public final androidx.compose.ui.Alignment getKeyboardAlign() {
        return null;
    }
    
    public final boolean isConnectBtnEnabled() {
        return false;
    }
    
    @androidx.annotation.StringRes
    public final int getConnectBtnText() {
        return 0;
    }
    
    public SettingsState() {
        super();
    }
    
    public final boolean component1() {
        return false;
    }
    
    public final boolean component2() {
        return false;
    }
    
    public final boolean component3() {
        return false;
    }
    
    public final boolean component4() {
        return false;
    }
    
    public final boolean component5() {
        return false;
    }
    
    public final boolean component6() {
        return false;
    }
    
    public final boolean component7() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable
    public final com.tyom.domain.models.Instrument component8() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<com.tyom.domain.models.Instrument> component9() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.example.feature_home.models.SettingsState copy(boolean isVertical, boolean isLoading, boolean isAutoConnect, boolean isKeyboardVisible, boolean isConnected, boolean isInstrumentsListOpened, boolean isTryingToConnect, @org.jetbrains.annotations.Nullable
    com.tyom.domain.models.Instrument selectedInstrument, @org.jetbrains.annotations.NotNull
    java.util.List<com.tyom.domain.models.Instrument> instruments) {
        return null;
    }
    
    @java.lang.Override
    public boolean equals(@org.jetbrains.annotations.Nullable
    java.lang.Object other) {
        return false;
    }
    
    @java.lang.Override
    public int hashCode() {
        return 0;
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.NotNull
    public java.lang.String toString() {
        return null;
    }
}