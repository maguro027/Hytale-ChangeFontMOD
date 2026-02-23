package com.maguro027.hytalechangefont;

import java.util.logging.Logger;

/**
 * Hytale Change Font MOD - Main Entry Point
 * 
 * This is the main plugin class for the Hytale font modification mod.
 * It initializes the custom font loading system and manages the plugin lifecycle.
 * 
 * Once Hytale SDK is released, this class should extend:
 * {@code extends com.hypixel.hytale.plugin.Plugin}
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
     * Called when the plugin is enabled.
     * Initializes the custom font loading system.
     * 
     * Future activation (once Hytale SDK released):
     * {@code @Override}
     */
    public void onEnable() {
        LOGGER.info("═══════════════════════════════════════════════════════════");
        LOGGER.info("   " + MOD_NAME + " v" + VERSION);
        LOGGER.info("   Initializing custom font system...");
        LOGGER.info("═══════════════════════════════════════════════════════════");
        
        try {
            // Initialize custom font from assets
            CustomFont.init();
            LOGGER.info("✓ Custom font initialized successfully!");
            LOGGER.info("✓ Chat rendering will use custom TTF font");
        } catch (Exception e) {
            LOGGER.severe("✗ Failed to initialize custom font: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Called when the plugin is disabled.
     * Cleans up resources.
     * 
     * Future deactivation (once Hytale SDK released):
     * {@code @Override}
     */
    public void onDisable() {
        LOGGER.info("Hytale Change Font MOD disabled.");
    }
}
