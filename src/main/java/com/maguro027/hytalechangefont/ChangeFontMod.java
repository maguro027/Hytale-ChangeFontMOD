package com.maguro027.hytalechangefont;

// Hytale APIリリース後に有効化
// import com.hypixel.hytale.plugin.Plugin;

/**
 * Hytale Change Font MOD - メインエントリポイント
 * Hytaleリリース後にPluginクラスを継承して有効化
 */
public class ChangeFontMod /* extends Plugin */ {
    
    public static final String MOD_ID = "hytalechangefont";
    public static final String MOD_NAME = "Hytale Change Font MOD";
    public static final String VERSION = "1.0.0";
    
    // @Override
    public void onEnable() {
        log("Hytale Change Font MOD enabled! Custom font loaded.");
        // カスタムフォント初期化 (assetsから)
        CustomFont.init();
    }

    // @Override
    public void onDisable() {
        log("Hytale Change Font MOD disabled.");
    }
    
    private void log(String message) {
        System.out.println("[" + MOD_NAME + "] " + message);
    }
}
