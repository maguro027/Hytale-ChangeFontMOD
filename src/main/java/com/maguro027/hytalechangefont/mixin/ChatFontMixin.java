package com.maguro027.hytalechangefont.mixin;

// Hytale APIリリース後に有効化
// import com.maguro027.hytalechangefont.CustomFont;
// import net.hypixel.hytale.render.FontRenderer;
// import org.spongepowered.asm.mixin.Mixin;
// import org.spongepowered.asm.mixin.injection.At;
// import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * ChatFontMixin - チャット描画時にカスタムフォントを適用
 * Hytaleリリース後にMixinアノテーションを有効化
 */
// @Mixin(FontRenderer.class)
public class ChatFontMixin {

    // @Redirect(method = "drawString", at = @At(value = "FIELD", target = "Lnet/hypixel/hytale/render/Font;defaultFont"))
    // private FontRenderer useCustomFont(FontRenderer instance) {
    //     return CustomFont.INSTANCE;  // カスタムフォント返却！
    // }
    
    /**
     * このクラスはHytaleリリース後に有効化されます
     * 現在はプレースホルダーとして機能します
     */
    public ChatFontMixin() {
        // Placeholder constructor
    }
}
