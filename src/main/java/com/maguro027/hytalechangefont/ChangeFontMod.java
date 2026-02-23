package com.maguro027.hytalechangefont;

// NOTE: Plugin class is provided by Hyxin framework at runtime
// import com.hypixel.hytale.plugin.Plugin;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * Hytale Change Font MOD - Main Plugin Entry Point
 * 
 * This plugin replaces the default chat font with a custom TTF font file.
 * It integrates with Hyxin (Mixin loader) to inject font rendering hooks.
 * 
 * At runtime, this class extends com.hypixel.hytale.plugin.Plugin
 * (provided by Hyxin framework). During development, it compiles standalone.
 * 
 * CRITICAL EARLY PLUGIN SAFETY NOTES:
 * ⚠️ onEnable() is called DURING server startup, NOT after completion
 * ⚠️ Game state may be uninitialized ("Cannot invoke String.hashCode() because group is null")
 * ⚠️ Use delayed initialization with Thread.sleep() to avoid race conditions
 * ⚠️ Keep onEnable() minimal - schedule heavy operations for later
 * ⚠️ Ensure JAR is placed FIRST in EarlyPlugins directory
 * ⚠️ Launch Hytale with --accept-early-plugins flag
 * 
 * Installation:
 * 1. Place this JAR in your Hytale/{EarlyPlugins} directory (FIRST position, rename if needed)
 * 2. Place custom.ttf in assets/hytalemod/fonts/ (optional, Arial fallback available)
 * 3. Launch: HytaleClient --accept-early-plugins
 * 
 * The plugin will automatically initialize custom font loading after 1.5 seconds delay.
 * If the TTF cannot be loaded, it gracefully falls back to system Arial font.
 * 
 * @author maguro027
 * @version 1.0.1-hotfix (Early Plugin safety)
 */
public class ChangeFontMod /* extends Plugin */ {
    
    private static final Logger LOGGER = Logger.getLogger("HytaleChangeFontMOD");
    private static volatile boolean initialized = false;
    private static volatile boolean initInProgress = false;
    
    public static final String MOD_ID = "hytalechangefont";
    public static final String MOD_NAME = "Hytale Change Font MOD";
    public static final String VERSION = "1.0.1-hotfix";
    
    /**
     * Called when the plugin is loaded and enabled (DURING server initialization).
     * 
     * CRITICAL: This is called BEFORE the server is fully initialized!
     * Avoid heavy operations. Schedule initialization for later instead.
     * 
     * Initialization sequence:
     * 1. Log plugin startup (lightweight)
     * 2. Register delayed initialization handler (do NOT call CustomFont.init() directly)
     * 3. Return immediately
     * 
     * The actual font initialization happens 1-2 seconds later when server is ready.
     */
    // @Override - Overrides Plugin.onEnable() at runtime
    public void onEnable() {
        LOGGER.info("╔════════════════════════════════════════════════════════════╗");
        LOGGER.info("║              Hytale Change Font MOD v" + VERSION + "              ║");
        LOGGER.info("║       Safe Early Plugin with Delayed Initialization         ║");
        LOGGER.info("╚════════════════════════════════════════════════════════════╝");
        
        try {
            LOGGER.info("[EARLY PLUGIN] Server initialization in progress...");
            LOGGER.info("[EARLY PLUGIN] Scheduling delayed font system initialization (1.5s delay)");
            
            // Schedule font initialization in a background thread
            // This avoids interfering with Hyxin's server startup sequence
            if (!initInProgress && !initialized) {
                initInProgress = true;
                
                new Thread(() -> {
                    try {
                        // Wait for server to fully initialize
                        Thread.sleep(1500);
                        
                        LOGGER.info("[DELAYED INIT] Beginning CustomFont initialization...");
                        CustomFont.init();
                        
                        if (CustomFont.INSTANCE != null) {
                            String fontStatus = CustomFont.INSTANCE.isCustomFontLoaded() 
                                ? "Custom TTF loaded" 
                                : "System Arial fallback";
                            LOGGER.info("[DELAYED INIT] ✓ Font system ready: " + fontStatus);
                            LOGGER.info("[DELAYED INIT] ✓ Font name: " + CustomFont.INSTANCE.getFontName());
                            LOGGER.log(Level.INFO, "[DELAYED INIT] ✓ Plugin initialized successfully!");
                            initialized = true;
                        } else {
                            LOGGER.warning("[DELAYED INIT] ⚠ CustomFont.INSTANCE is null after init");
                        }
                    } catch (InterruptedException ie) {
                        LOGGER.warning("[DELAYED INIT] Initialization thread was interrupted");
                        Thread.currentThread().interrupt();
                    } catch (Exception e) {
                        LOGGER.severe("[DELAYED INIT] ✗ Font initialization failed: " + e.getMessage());
                        LOGGER.log(Level.SEVERE, "[DELAYED INIT] Stack trace:", e);
                    } finally {
                        initInProgress = false;
                    }
                }, "HytaleChangeFontMOD-InitThread").start();
                
            } else {
                LOGGER.warning("[EARLY PLUGIN] Initialization already in progress or completed");
            }
            
        } catch (Exception e) {
            LOGGER.severe("✗ CRITICAL ERROR in onEnable: " + e.getMessage());
            LOGGER.log(Level.SEVERE, "Stack trace:", e);
        }
    }
    
    /**
     * Called when the plugin is disabled or Hytale is shutting down.
     * 
     * Cleanup any remaining resources if needed.
     */
    // @Override - Overrides Plugin.onDisable() at runtime
    public void onDisable() {
        try {
            LOGGER.info("═══════════════════════════════════════════════════════════");
            LOGGER.info("[SHUTDOWN] Hytale Change Font MOD disabling...");
            LOGGER.info("[SHUTDOWN] Initialization completed: " + initialized);
            LOGGER.info("[SHUTDOWN] Plugin disabled successfully.");
            LOGGER.info("═══════════════════════════════════════════════════════════");
            
            // Ensure background thread stops
            initialized = false;
            
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "[SHUTDOWN] Error during cleanup: " + e.getMessage(), e);
        }
    }
}
