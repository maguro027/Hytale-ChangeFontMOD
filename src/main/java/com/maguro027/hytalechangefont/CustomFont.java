package com.maguro027.hytalechangefont;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;

/**
 * Custom Font Manager for Hytale Chat
 * 
 * This class is responsible for loading and managing custom TTF fonts
 * that are embedded in the mod's resources. It provides a singleton instance
 * of the loaded font for use throughout the mod.
 * 
 * Font Loading Strategy:
 * 1. Attempt to load custom.ttf from /assets/hytalemod/fonts/custom.ttf
 * 2. If TTF load fails, fallback to system Arial font
 * 3. Always provide a valid Font instance to prevent null pointer exceptions
 * 
 * Future Integration (Hytale SDK):
 * This class should extend or wrap {@code net.hypixel.hytale.render.Font}
 * to properly integrate with Hytale's rendering pipeline:
 * {@code extends HytaleFont implements FontProvider}
 * 
 * @author maguro027
 * @version 1.0.0
 */
public class CustomFont /* extends HytaleFont */ {
    
    private static final Logger LOGGER = Logger.getLogger("HytaleCustomFont");
    
    /** Singleton instance of the custom font */
    public static CustomFont INSTANCE;
    
    /** Path to the custom TTF font in resources */
    private static final String FONT_PATH = "/assets/hytalemod/fonts/custom.ttf";
    
    /** Font size in points */
    private static final float FONT_SIZE = 16.0f;
    
    /** Name of the custom font (used for fallback identification) */
    private static final String CUSTOM_FONT_NAME = "CustomHytaleFont";
    
    /** The loaded AWT Font instance */
    private Font awtFont;
    
    /** Flag indicating whether the font was loaded successfully */
    private boolean isLoaded;

    /**
     * Private constructor - use init() to create instance
     */
    private CustomFont() {
        // Future: super("Custom Font", loadTTF(), FONT_SIZE);
        this.awtFont = loadTTF();
        this.isLoaded = (awtFont != null && !awtFont.getFamily().equals("Arial"));
    }

    /**
     * Initialize the singleton instance of CustomFont
     * Must be called during plugin onEnable()
     */
    public static void init() {
        try {
            INSTANCE = new CustomFont();
            LOGGER.info("✓ CustomFont singleton initialized");
            LOGGER.info("  Font: " + INSTANCE.awtFont.getFontName());
            LOGGER.info("  Size: " + FONT_SIZE + "pt");
            LOGGER.info("  Status: " + (INSTANCE.isLoaded ? "SUCCESS" : "FALLBACK"));
        } catch (Exception e) {
            LOGGER.severe("✗ Failed to initialize CustomFont: " + e.getMessage());
            e.printStackTrace();
            // Still create instance with fallback font
            INSTANCE = new CustomFont();
        }
    }

    /**
     * Get the loaded AWT Font instance
     * 
     * @return java.awt.Font instance (never null - returns fallback if load fails)
     */
    public Font getFont() {
        return awtFont;
    }

    /**
     * Check if the custom font was successfully loaded
     * 
     * @return true if custom TTF was loaded, false if using fallback
     */
    public boolean isCustomFontLoaded() {
        return isLoaded;
    }

    /**
     * Get the font name
     * 
     * @return Font name string
     */
    public String getFontName() {
        return awtFont.getFontName();
    }

    /**
     * Derive a new font with specified size
     * Useful for UI elements requiring different font sizes
     * 
     * @param size Font size in points
     * @return New Font instance with specified size
     */
    public Font deriveFont(float size) {
        return awtFont.deriveFont(size);
    }

    /**
     * Load custom TTF font from resources
     * 
     * This method attempts to load a TTF font from:
     * /assets/hytalemod/fonts/custom.ttf
     * 
     * If the custom font cannot be loaded for any reason,
     * it gracefully falls back to system Arial font to prevent crashes.
     * 
     * Resource Loading Strategy:
     * - Uses ClassLoader.getResourceAsStream() for JAR-safe loading
     * - Properly closes InputStream to prevent resource leaks
     * - Derives font to specified size (FONT_SIZE constant)
     * 
     * @return Loaded Font instance, or Arial fallback if load fails
     */
    private static Font loadTTF() {
        try (InputStream fontStream = CustomFont.class.getResourceAsStream(FONT_PATH)) {
            
            // Check if resource exists
            if (fontStream == null) {
                LOGGER.warning("✗ Font file not found: " + FONT_PATH);
                return getFallbackFont();
            }
            
            // Load TTF from stream
            Font loadedFont = Font.createFont(Font.TRUETYPE_FONT, fontStream);
            
            // Derive to specified size
            Font sizedFont = loadedFont.deriveFont(FONT_SIZE);
            
            LOGGER.info("✓ Custom TTF font loaded successfully");
            LOGGER.info("  Path: " + FONT_PATH);
            LOGGER.info("  Font: " + sizedFont.getFontName());
            
            return sizedFont;
            
        } catch (FontFormatException e) {
            LOGGER.warning("✗ Font format error: " + e.getMessage());
            LOGGER.warning("  The TTF file may be corrupted or invalid");
            return getFallbackFont();
            
        } catch (IOException e) {
            LOGGER.warning("✗ IO error loading font: " + e.getMessage());
            return getFallbackFont();
            
        } catch (Exception e) {
            LOGGER.warning("✗ Unexpected error loading font: " + e.getMessage());
            e.printStackTrace();
            return getFallbackFont();
        }
    }

    /**
     * Get system fallback font
     * 
     * Used when custom TTF loading fails. Returns Arial at specified size,
     * which is widely available on all systems.
     * 
     * @return System Arial font at FONT_SIZE
     */
    private static Font getFallbackFont() {
        LOGGER.info("→ Using fallback system font: Arial at " + FONT_SIZE + "pt");
        return new Font("Arial", Font.PLAIN, (int) FONT_SIZE);
    }
}
