package com.maguro027.hytalechangefont;

// Hytale APIリリース後に有効化
// import net.hypixel.hytale.render.Font;
import java.awt.Font;
import java.io.InputStream;

/**
 * カスタムフォント管理クラス
 * Noto Sans JP (Google Fonts) を読み込む
 */
public class CustomFont /* extends HytaleFont */ {
    public static CustomFont INSTANCE;
    private static final String FONT_PATH = "/assets/hytalemod/fonts/custom.ttf";
    private static final int FONT_SIZE = 16;
    private Font awtFont;

    private CustomFont() {
        // super("Noto Sans JP", loadTTF(), FONT_SIZE);  // Hytaleリリース後に有効化
        this.awtFont = loadTTF();
    }

    public static void init() {
        INSTANCE = new CustomFont();
        System.out.println("[CustomFont] Noto Sans JP loaded successfully!");
    }

    /**
     * フォント取得
     * @return java.awt.Font インスタンス
     */
    public Font getFont() {
        return awtFont;
    }
    
    /**
     * Noto Sans JP (Google Fonts) TTFフォントを読み込む
     * @return 読み込んだフォント、失敗時はArialフォールバック
     */
    private static Font loadTTF() {
        try (InputStream is = CustomFont.class.getResourceAsStream(FONT_PATH)) {
            if (is == null) {
                System.err.println("[CustomFont] Font file not found: " + FONT_PATH);
                return getFallbackFont();
            }
            Font font = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont((float)FONT_SIZE);
            System.out.println("[CustomFont] Noto Sans JP loaded successfully from " + FONT_PATH);
            return font;
        } catch (Exception e) {
            System.err.println("[CustomFont] Failed to load font: " + e.getMessage());
            return getFallbackFont();
        }
    }

    private static Font getFallbackFont() {
        System.out.println("[CustomFont] Using fallback font: Arial");
        return new Font("Arial", Font.PLAIN, FONT_SIZE);
    }
}
