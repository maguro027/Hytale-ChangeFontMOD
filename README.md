# Hytale Change Font MOD

🎮 **Replace the chat font with a custom TTF font in Hytale via Mixin injection**

## 📋 Overview

This is a **Hyxin Early Plugin** that uses **Mixin 0.8.5** bytecode manipulation to intercept Hytale's chat rendering and replace the default font with a custom TTF font file.

| Component       | Version                   |
| --------------- | ------------------------- |
| Java            | 25 LTS                    |
| Build Tool      | Gradle with Kotlin DSL    |
| Plugin Loader   | Hyxin 0.0.11+             |
| Mixin Framework | 0.8.5                     |
| MOD Version     | **1.0.1-hotfix** (Safety) |
| Architecture    | Client-side mod           |

---

## 🚀 Installation

### Prerequisites

- ✅ Hytale client (pre-release access required)
- ✅ Java 25 LTS or higher
- ✅ Hyxin 0.0.11 or later (Mixin loader for Hytale)

### Step 1: Download Hyxin Loader

1. Visit [CurseForge Hyxin Release Page](https://www.curseforge.com/minecraft/mods/hyxin) (or appropriate Hytale mod repository)
2. Download **Hyxin-0.0.11-all.jar** (or latest version)
3. Note the download location

### Step 2: Set Up Plugin Directory

1. Navigate to your **Hytale installation directory**
2. Create a new folder if it doesn't exist: **`EarlyPlugins`**
    ```
    C:\Users\YourUser\AppData\Roaming\Hytale\EarlyPlugins\    (Windows)
    ~/Library/Application Support/Hytale/EarlyPlugins/         (macOS)
    ~/.var/app/com.hypixel.hytale/data/Hytale/EarlyPlugins/   (Linux/Flatpak)
    ```

### Step 3: Build or Download JAR

**Option A: Build from Source**

```bash
git clone https://github.com/maguro027/Hytale-ChangeFontMOD.git
cd Hytale-ChangeFontMOD
./gradlew build
# Output: build/libs/hytale-changefont-mod-1.0.jar
```

**Option B: Download Pre-built JAR**

- Download from [GitHub Releases](https://github.com/maguro027/Hytale-ChangeFontMOD/releases)
- File: `hytale-changefont-mod-1.0.jar`

### Step 4: Place JAR in EarlyPlugins

```bash
cp build/libs/hytale-changefont-mod-1.0.jar ~/Hytale/EarlyPlugins/
```

### Step 5: Add Custom Font (Optional)

The mod includes a placeholder Arial fallback. For a custom font:

1. Obtain a TTF font file
    - 📌 **Recommended**: [Noto Sans JP](https://fonts.google.com/noto/specimen/Noto+Sans+JP) (free, Google Fonts)
    - Alternative: Any valid TTF file works (e.g., Arial, Segoe UI, DejaVu Sans)

2. Navigate inside the JAR:

    ```
    hytale-changefont-mod-1.0.jar
    └─ assets/
       └─ hytalemod/
          └─ fonts/
             └─ custom.ttf  ← Place your font here
    ```

3. **Method A: Using Archive Manager (GUI)**
    - Right-click JAR → Open with Archive Manager / 7-Zip / WinRAR
    - Navigate to `assets/hytalemod/fonts/`
    - Drag & drop your `.ttf` file
    - Save and close

4. **Method B: Using CLI**

    ```bash
    # Extract
    unzip hytale-changefont-mod-1.0.jar -d mod_extracted/

    # Copy font
    cp NotoSansJP-Regular.ttf mod_extracted/assets/hytalemod/fonts/custom.ttf

    # Repackage
    cd mod_extracted/
    jar cf hytale-changefont-mod-1.0.jar *
    ```

### Step 6: Launch Hytale with Hyxin

1. **Configure Hyxin Loader** (depending on your Hytale launcher):
    - Ensure Hyxin JAR is in your classpath
    - Add JVM argument: `-javaagent:Hyxin-0.0.11-all.jar` or configure via launcher settings

2. **Start Hytale client normally**

3. **Check Console Logs**:
    ```
    [INFO] ✓ Hytale Change Font MOD v1.0.0
    [INFO] ✓ Font system ready: Custom TTF
    [INFO] ✓ Mixin injection hooks active
    [INFO] ✓ Plugin loaded successfully
    ```

---

## 📝 Testing

### Verify Installation

1. Launch Hytale with the mod loaded
2. Open chat window (default: `T` key)
3. Observe font rendering:
    - ✅ **SUCCESS**: Chat text appears in custom font (different from default)
    - ⚠️ **FALLBACK**: Text in Arial (custom TTF not found)
    - ❌ **FAILED**: Mod didn't load (check logs)

### Debug Logging

To enable verbose logging:

```bash
# Add JVM arguments when launching Hyxin
-Dmixin.debug=true
-Dlog4j.configurationFile=log4j2.xml
```

Check console output for:

```
[INFO] ChatFontMixin loaded - font injection hooking system initialized
[INFO] ✓ Font redirection active: Using custom TTF for rendering
```

---

## 🔧 Configuration

### Font Size

To change font size, edit [CustomFont.java](src/main/java/com/maguro027/hytalechangefont/CustomFont.java):

```java
private static final float FONT_SIZE = 16.0f;  // Change value here
```

Then rebuild:

```bash
./gradlew build
```

### Font Path

The mod loads from: `/assets/hytalemod/fonts/custom.ttf`

This is JAR-relative, so ensure the TTF is at this exact path inside the JAR.

### Fallback Behavior

If the custom TTF cannot be loaded:

- ✅ Mod still works (doesn't crash)
- ✅ Falls back to system Arial font
- ✅ Console will show warning

---

## 📚 Development

### Project Structure

```
Hytale-ChangeFontMOD/
├── src/
│   └── main/
│       ├── java/com/maguro027/hytalechangefont/
│       │   ├── ChangeFontMod.java          (Plugin entry point)
│       │   ├── CustomFont.java             (Font loading logic)
│       │   └── mixin/ChatFontMixin.java    (Mixin injection)
│       └── resources/
│           ├── manifest.json               (Hyxin metadata)
│           ├── hytalemod.mixins.json       (Mixin config)
│           └── assets/hytalemod/fonts/
│               └── custom.ttf              (Custom font file)
├── build.gradle.kts                        (Build configuration)
└── README.md                               (This file)
```

### Building

```bash
# Clean build
./gradlew clean build

# Output
build/libs/hytale-changefont-mod-1.0.jar
```

### Modifying Mixin Injection

The Mixin target class must be verified by decompiling the Hytale client:

```bash
# Decompile Hytale JAR
java -jar cfr.jar HytaleClient.jar
```

Then update [ChatFontMixin.java](src/main/java/com/maguro027/hytalechangefont/mixin/ChatFontMixin.java):

- Change `@Mixin(targets = "...")` to correct class
- Update `@Redirect` method/field names
- Verify bytecode target paths

See comments in ChatFontMixin.java for detailed instructions.

---

## 🐛 Troubleshooting

### Critical: Early Plugin Error: "Cannot invoke \"String.hashCode()\" because \"this.group\" is null"

**Symptom**: MOD loads, then game crashes during server startup with NullPointerException

**Root Cause**:
- `onEnable()` is called DURING server initialization, NOT after completion
- Global game state is still uninitialized at plugin load time
- Hyxin Early Plugin loading timing race condition

**Solution**:

1. **Verify JVM Flag**
   ```
   --accept-early-plugins
   ```
   Add this flag when launching Hytale client

2. **Check EarlyPlugins Directory Load Order**
   
   If you have multiple Early Plugins, place this one **FIRST**:
   ```
   Hytale/EarlyPlugins/
   ├── 00-hytale-changefont-mod-1.0.jar    ← Loads first
   ├── 01-other-mod.jar
   ├── 02-another-mod.jar
   └── ...
   ```
   
   Use numeric prefixes to control load order alphabetically.

3. **Verify Delayed Initialization is Active**
   
   v1.0.1-hotfix and later use delayed init with 1.5 second wait:
   ```java
   Thread.sleep(1500);  // Wait for server to fully initialize
   ```
   
   Check console for these messages:
   ```
   [EARLY PLUGIN] Server initialization in progress...
   [DELAYED INIT] Beginning CustomFont initialization...
   [DELAYED INIT] ✓ Plugin initialized successfully!
   ```

4. **Check for MOD Conflicts**
   
   Multiple Early Plugins trying to initialize screen at same time:
   - Review logs for what loaded before "FAILED TO START SERVER"
   - Disable suspicious plugins one by one
   - Test with this MOD only

5. **Verify Hyxin Version**
   ```
   Hyxin-0.0.11-all.jar (or higher) required
   ```

### Issue: JAR doesn't load

**Symptom**: `[ERROR] Class not found: ChangeFontMod`

**Solution**:

1. Verify manifest.json has correct `"Main"` value
2. Check JAR structure: `jar tf hytale-changefont-mod-1.0.jar | head`
3. Ensure Hyxin version 0.0.11+

### Issue: No font changed in chat

**Symptom**: Chat text still uses default font

**Steps**:

1. Check logs for: `ChatFontMixin loaded`
2. If missing: Mixin didn't apply (Hyxin issue)
3. If present: Verify TTF is correctly placed inside JAR
4. Try with fallback: Remove custom.ttf → should use Arial

### Issue: Font format error

**Symptom**: `[WARNING] Font format error (TTF corrupted?)`

**Solution**:

1. Verify TTF file validity: `file custom.ttf` → should show "TrueType Font"
2. Try different font: Use [Noto Sans JP](https://fonts.google.com/noto/specimen/Noto+Sans+JP)
3. Download from official source (not corrupted)

### Issue: "CustomFont not initialized"

**Symptom**: `[WARNING] ⚠ Font redirection attempted but CustomFont not initialized`

**Solution**:

1. Check if `ChangeFontMod.onEnable()` was called
2. Verify plugin loads before chat system
3. Check logs for `CustomFont singleton initialized`

---

## 📖 References

- [Mixin Framework Documentation](https://github.com/SpongePowered/Mixin/wiki)
- [Fabric Mixin Tutorial](https://fabricmc.net/wiki/tutorial:mixin_introduction)
- [Java Font API](https://docs.oracle.com/en/java/javase/25/docs/api/java.awt/java/awt/Font.html)
- [Gradle Kotlin DSL Guide](https://docs.gradle.org/current/userguide/kotlin_dsl.html)

---

## 📄 License

MIT License - See [LICENSE](LICENSE) file

---

## 👤 Author

**maguro027**

- GitHub: [@maguro027](https://github.com/maguro027)
- Repository: [Hytale-ChangeFontMOD](https://github.com/maguro027/Hytale-ChangeFontMOD)

---

## 🤝 Contributing

Issues and pull requests welcome! Please see [Contributing Guidelines](CONTRIBUTING.md)

---

## ⚠️ Important Notes

1. **Decompiler Verification**: The Mixin target classes MUST be verified by decompiling Hytale client. See ChatFontMixin.java for details.

2. **Font Selection**: Not all TTF fonts work well in games. Recommended fonts:
    - ✅ Noto Sans Series (Google)
    - ✅ DejaVu Sans
    - ✅ Liberation Sans
    - ✅ Segoe UI

3. **File Permissions**: Ensure JAR has read permissions in EarlyPlugins directory

4. **Java Version**: Requires Java 25 LTS. Check: `java -version`

---

**Last Updated: 2026/02/23**
