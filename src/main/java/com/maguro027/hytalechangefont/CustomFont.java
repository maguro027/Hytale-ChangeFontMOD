package com.maguro027.hytalechangefont;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;

/**
 * Custom Font Manager for Hytale Chat Rendering
 * 
 * This class manages the loading and caching of custom TTF fonts for use
 * throughout the Hytale chat system. It implements a singleton pattern to
 * ensure only one font instance is active at any given time.
 * 
 * Font Loading Strategy:
 * 1. PRIMARY: Load custom.ttf from /assets/hytalemod/fonts/custom.ttf
 * 2. FALLBACK: If loading fails, use system Arial font (always available)
 * 3. RESULT: Never returns null - guarantees a valid Font object
 * 
 * Integration with Hytale:
 * When the Hytale Font class becomes available, this class should be
 * updated to extend or wrap Hytale's Font type for seamless integration.
 * 
 * Current approach: Uses standard java.awt.Font with Hytale compatibility layer
 * 
 * @author maguro027
 * @version 1.0.0
 */
public class CustomFont {
    
    private static final Logger LOGGER = Logger.getLogger("HytaleCustomFont");
    
    /** Singleton instance of the custom font manager */
    public static CustomFont INSTANCE;
    
    /** Resource path to the custom TTF font */
    private static final String FONT_PATH = "/assets/hytalemod/fonts/custom.ttf";
    
    /** Target font size for chat rendering (16pt typical for UI) */
    private static final float FONT_SIZE = 16.0f;
    
    /** The underlying AWT Font object */
    private Font awtFont;
    
    /** Flag indicating if a custom TTF was successfully loaded */
    private boolean isCustomFontLoaded;

    /**
     * Private constructor - use init() method to create instance
     */
    private CustomFont() {
        this.awtFont = loadTTF();
        this.isCustomFontLoaded = (awtFont != null && !awtFont.getFamily().equals("Arial"));
    }

    /**
     * Initialize the singleton instance.
     * 
     * This must be called once during plugin initialization (in ChangeFontMod.onEnable).
     * Subsequent calls will return the cached INSTANCE.
     * 
     * Thread-safety note: Currently not synchronized. If multi-threaded access is needed,
     * consider adding synchronized block or using AtomicReference.
     */
    public static synchronized void init() {
        if (INSTANCE != null) {
            LOGGER.info("CustomFont already initialized, reusing instance");
            return;
        }
        
        try {
            INSTANCE = new CustomFont();
            
            String status = INSTANCE.isCustomFontLoaded ? "Custom TTF" : "System Arial (fallback)";
            LOGGER.info("╔════════════════════════════════════════════════════════════╗");
            LOGGER.info("║         CustomFont Singleton Initialization Success        ║");
            LOGGER.info("╚════════════════════════════════════════════════════════════╝");
            LOGGER.info("✓ CustomFont initialized");
            LOGGER.info("  Font loaded: " + INSTANCE.awtFont.getFontName());
            LOGGER.info("  Font size: " + FONT_SIZE + "pt");
            LOGGER.info("  Font status: " + status);
            LOGGER.info("  Is custom: " + INSTANCE.isCustomFontLoaded);
            LOGGER.info("✓ Ready for Mixin injection into chat renderer");
            
        } catch (Exception e) {
            LOGGER.severe("✗ Failed to initialize CustomFont: " + e.getMessage());
            e.printStackTrace();
            // Still provide a fallback instance
            INSTANCE = new CustomFont();
        }
    }

    /**
     * Get the underlying AWT Font instance.
     * 
     * This returns the active font (either custom TTF or system fallback).
     * Never returns null - always provides a valid Font object.
     * 
     * @return Font instance for rendering
     */
    public Font getFont() {
        return awtFont;
    }

    /**
     * Check whether the custom TTF was successfully loaded.
     * 
     * @return true if custom TTF is active, false if using fallback
     */
    public boolean isCustomFontLoaded() {
        return isCustomFontLoaded;
    }

    /**
     * Get the font's display name.
     * 
     * @return Font family name (e.g., "Noto Sans JP", "Arial")
     */
    public String getFontName() {
        return awtFont.getFontName();
    }

    /**
     * Create a new font with a different size, maintaining the font style.
     * 
     * Useful for UI elements that need different font sizes:
     * - Large fonts for titles/headers
     * - Small fonts for tooltips/hints
     * 
     * @param size Target size in points
     * @return New Font instance with specified size
     */
    public Font deriveFont(float size) {
        return awtFont.deriveFont(size);
    }

    /**
     * Load custom TTF font from resources.
     * 
     * Resource Path: /assets/hytalemod/fonts/custom.ttf
     * Embedded Location: Inside this JAR (packed by Gradle)
     * 
     * Loading Process:
     * 1. Use ClassLoader.getResourceAsStream() for JAR-safe access
     * 2. Create Font from stream using Font.createFont()
     * 3. Derive to specified FONT_SIZE
     * 4. Close stream via try-with-resources
     * 
     * Exception Handling:
     * - FontFormatException: TTF file corrupted or invalid format
     * - IOException: File access issues
     * - Exception: Unexpected errors
     * - All cases: Fallback to Arial without crashing
     * 
     * @return Loaded Font instance or Arial fallback
     */
    private static Font loadTTF() {
        try (InputStream fontStream = CustomFont.class.getResourceAsStream(FONT_PATH)) {
            
            // Verify resource exists
            if (fontStream == null) {
                LOGGER.warning("✗ Font file not found in resources: " + FONT_PATH);
                LOGGER.info("  Make sure custom.ttf is placed in src/main/resources" + FONT_PATH);
                return getFallbackFont();
            }
            
            // Load TTF from stream
            Font loadedFont = Font.createFont(Font.TRUETYPE_FONT, fontStream);
            
            // Derive to target size
            Font sizedFont = loadedFont.deriveFont(FONT_SIZE);
            
            LOGGER.info("✓ Custom TTF font loaded successfully");
            LOGGER.info("  Path: " + FONT_PATH);
            LOGGER.info("  Font: " + sizedFont.getFontName());
            
            return sizedFont;
            
        } catch (FontFormatException e) {
            LOGGER.warning("✗ Font format error (TTF corrupted?): " + e.getMessage());
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
     * Get system fallback font (Arial).
     * 
     * Arial is chosen because:
     * - Available on virtually all systems
     * - Professional appearance
     * - Good Unicode coverage
     * - Monospace alternative: "Courier New"
     * 
     * @return System Arial font at FONT_SIZE
     */
    private static Font getFallbackFont() {
        LOGGER.info("→ Using fallback system font: Arial at " + FONT_SIZE + "pt");
        return new Font("Arial", Font.PLAIN, (int) FONT_SIZE);
    }
}
