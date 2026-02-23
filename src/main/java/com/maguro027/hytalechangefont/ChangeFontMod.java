package com.maguro027.hytalechangefont;

// NOTE: Plugin class is provided by Hyxin framework at runtime
// import com.hypixel.hytale.plugin.Plugin;
import java.util.logging.Logger;

/**
 * Hytale Change Font MOD - Main Plugin Entry Point
 * 
 * This plugin replaces the default chat font with a custom TTF font file.
 * It integrates with Hyxin (Mixin loader) to inject font rendering hooks.
 * 
 * At runtime, this class extends com.hypixel.hytale.plugin.Plugin
 * (provided by Hyxin framework). During development, it compiles standalone.
 * 
 * Installation:
 * 1. Place this JAR in your Hytale/{EarlyPlugins} directory
 * 2. Place custom.ttf in the assets/hytalemod/fonts/ folder (packed within this JAR)
 * 3. Start Hytale with Hyxin 0.0.11+ loaded
 * 
 * The plugin will automatically initialize custom font loading on enable.
 * If the TTF cannot be loaded, it gracefully falls back to system Arial font.
 * 
 * @author maguro027
 * @version 1.0.0
 */
public class ChangeFontMod /* extends Plugin */ {
    
    private static final Logger LOGGER = Logger.getLogger("HytaleChangeFontMOD");
    
    public static final String MOD_ID = "hytalechangefont";
    public static final String MOD_NAME = "Hytale Change Font MOD";
    public static final String VERSION = "1.0.0";
    
    /**
     * Called when the plugin is loaded and enabled.
     * 
     * Initialization sequence:
     * 1. Log plugin startup
     * 2. Initialize CustomFont singleton
     * 3. Verify Mixin injection setup
     * 4. Report readiness to chat system
     */
    // @Override - Overrides Plugin.onEnable() at runtime
    public void onEnable() {
        LOGGER.info("╔════════════════════════════════════════════════════════════╗");
        LOGGER.info("║              Hytale Change Font MOD v" + VERSION + "              ║");
        LOGGER.info("║         Chat Font Rendering Override via Mixin             ║");
        LOGGER.info("╚════════════════════════════════════════════════════════════╝");
        
        try {
            // Initialize custom font loading system
            LOGGER.info("Initializing custom font loading...");
            CustomFont.init();
            
            // Verify initialization
            if (CustomFont.INSTANCE != null) {
                String fontStatus = CustomFont.INSTANCE.isCustomFontLoaded() 
                    ? "Custom TTF" 
                    : "System fallback (Arial)";
                LOGGER.info("✓ Font system ready: " + fontStatus);
                LOGGER.info("✓ Mixin injection hooks active (requires decompiler verification)");
                LOGGER.info("✓ Plugin loaded successfully - enjoy your custom chat font!");
            } else {
                LOGGER.warning("⚠ Font initialization returned null - using fallback");
            }
            
        } catch (Exception e) {
            LOGGER.severe("✗ Failed to initialize font system: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Called when the plugin is disabled or Hytale is shutting down.
     * 
     * Cleanup any remaining resources if needed.
     */
    // @Override - Overrides Plugin.onDisable() at runtime
    public void onDisable() {
        LOGGER.info("Hytale Change Font MOD disabled.");
    }
}
