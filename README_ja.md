# Hytale Change Font MOD

🎮 **Hytale のチャットフォントをカスタム TTF フォントで置き換える Mixin 注入 MOD**

## 📋 概要

これは **Hyxin Early Plugin** で、**Mixin 0.8.5** のバイトコード操作を使用して Hytale のチャット描画をインターセプトし、デフォルトフォントをカスタム TTF フォントに置き換えています。

| コンポーネント       | バージョン                |
| -------------------- | ------------------------- |
| Java                 | 25 LTS                    |
| ビルドツール         | Gradle with Kotlin DSL    |
| プラグインローダー   | Hyxin 0.0.11+             |
| Mixin フレームワーク | 0.8.5                     |
| MOD バージョン       | **1.0.1-hotfix** 安全化版 |
| アーキテクチャ       | クライアント側 MOD        |

---

## 🚀 インストール

### 前提条件

- ✅ Hytale クライアント（プレリリースアクセス必須）
- ✅ Java 25 LTS 以上
- ✅ Hyxin 0.0.11 以上（Hytale 用 Mixin ローダー）

### ステップ 1: Hyxin ローダーをダウンロード

1. [CurseForge Hyxin リリースページ](https://www.curseforge.com/minecraft/mods/hyxin)（または Hytale MOD リポジトリ）にアクセス
2. **Hyxin-0.0.11-all.jar**（または最新版）をダウンロード
3. ダウンロード場所をメモ

### ステップ 2: プラグインディレクトリをセットアップ

1. **Hytale インストールディレクトリ**に移動
2. 存在しない場合は新しいフォルダを作成: **`EarlyPlugins`**
    ```
    C:\Users\YourUser\AppData\Roaming\Hytale\EarlyPlugins\    (Windows)
    ~/Library/Application Support/Hytale/EarlyPlugins/         (macOS)
    ~/.var/app/com.hypixel.hytale/data/Hytale/EarlyPlugins/   (Linux/Flatpak)
    ```

### ステップ 3: JAR をビルドまたはダウンロード

**オプション A: ソースからビルド**

```bash
git clone https://github.com/maguro027/Hytale-ChangeFontMOD.git
cd Hytale-ChangeFontMOD
./gradlew build
# 出力: build/libs/hytale-changefont-mod-1.0.jar
```

**オプション B: 事前ビルド済み JAR をダウンロード**

- [GitHub Releases](https://github.com/maguro027/Hytale-ChangeFontMOD/releases) からダウンロード
- ファイル: `hytale-changefont-mod-1.0.jar`

### ステップ 4: JAR を EarlyPlugins に配置

```bash
cp build/libs/hytale-changefont-mod-1.0.jar ~/Hytale/EarlyPlugins/
```

### ステップ 5: カスタムフォントを追加（オプション）

MOD には Arial フォールバックが含まれています。カスタムフォントを使用する場合:

1. TTF フォントファイルを入手
    - 📌 **推奨**: [Noto Sans JP](https://fonts.google.com/noto/specimen/Noto+Sans+JP)（無料、Google Fonts）
    - 代替: 任意の有効な TTF ファイル（Arial、Segoe UI、DejaVu Sans など）

2. JAR 内を移動:

    ```
    hytale-changefont-mod-1.0.jar
    └─ assets/
       └─ hytalemod/
          └─ fonts/
             └─ custom.ttf  ← ここにフォントを配置
    ```

3. **方法 A: アーカイブマネージャー（GUI）を使用**
    - JAR を右クリック → Archive Manager / 7-Zip / WinRAR で開く
    - `assets/hytalemod/fonts/` に移動
    - `.ttf` ファイルをドラッグ＆ドロップ
    - 保存して閉じる

4. **方法 B: CLI を使用**

    ```bash
    # 抽出
    unzip hytale-changefont-mod-1.0.jar -d mod_extracted/

    # フォントをコピー
    cp NotoSansJP-Regular.ttf mod_extracted/assets/hytalemod/fonts/custom.ttf

    # 再パッケージ化
    cd mod_extracted/
    jar cf hytale-changefont-mod-1.0.jar *
    ```

### ステップ 6: Hyxin で Hytale を起動

1. **Hyxin ローダーを構成**（Hytale ランチャーに応じて異なります）
    - Hyxin JAR がクラスパスにあることを確認
    - JVM 引数を追加: `-javaagent:Hyxin-0.0.11-all.jar` またはランチャー設定で構成

2. **Hytale クライアントを起動**（**重要**: Early Plugin フラグを使用）

    ```bash
    # Windows
    HytaleClient.exe --accept-early-plugins
    
    # Java直接実行
    java -javaagent:Hyxin-0.0.11-all.jar -jar HytaleClient.jar --accept-early-plugins
    ```

3. **コンソールログを確認**:

    ```
    [INFO] ✓ Hytale Change Font MOD v1.0.1-hotfix
    [INFO] [EARLY PLUGIN] Server initialization in progress...
    [INFO] [DELAYED INIT] Beginning CustomFont initialization...
    [INFO] ✓ CustomFont initialized
    [INFO]   Font loaded: [フォント名]
    [INFO] [DELAYED INIT] ✓ Plugin initialized successfully!
    [INFO] ChatFontMixin loaded - font injection hooking system initialized
    ```

---

## 🔧 Mixin 有効化（Hytale インスタンスで）

このバージョンでは、開発ビルド環境での Hytale JAR の非可用性を考慮して、Mixin アノテーションを一時的にコメントアウトしてあります。**Hytale 実行環境で有効化**するには：

### 方法 1: 自分でコンパイル＆有効化

1. [ChatFontMixin.java](src/main/java/com/maguro027/hytalechangefont/mixin/ChatFontMixin.java) を開く
2. 以下のコメントを見つけて有効化:

    ```java
    // @Mixin(targets = "net.hytale.client.render.ChatRenderer")  
    // UNCOMMENT FOR RUNTIME
    ```

    ↓ 修正:

    ```java
    @Mixin(targets = "net.hytale.client.render.ChatRenderer")
    ```

3. @Inject と @Redirect も有効化:

    ```java
    // @Inject(method = "renderChatMessage", ...)
    // UNCOMMENT ABOVE @Inject WHEN DEPLOYED...
    ```

    ↓ 修正（コメント削除）:

    ```java
    @Inject(method = "renderChatMessage", ...)
    ```

4. ビルド:

    ```bash
    ./gradlew clean build
    ```

5. JAR を J:\Hy\UserData\Mods\EarlyPlugins\ に配置

### 方法 2: オンラインで自動有効化レポートを入手

GitHub Issues で Mixin ターゲットの完全な確認を依頼：

- Hytale バージョン
- エラーメッセージ
- ログ出力を貼付

→ 自動生成された有効化版をダウンロード



## 📝 テスト

### インストール確認

1. MOD を読み込んで Hytale を起動
2. チャットウィンドウを開く（デフォルト: `T` キー）
3. フォント描画を確認:
    - ✅ **成功**: チャットテキストがカスタムフォントで表示（デフォルトと異なる）
    - ⚠️ **フォールバック**: Arial で表示（カスタム TTF が見つかりません）
    - ❌ **失敗**: MOD が読み込まれていない（ログを確認）

### デバッグログ

詳細なログを有効にする:

```bash
# Hyxin を起動するときに JVM 引数を追加
-Dmixin.debug=true
-Dlog4j.configurationFile=log4j2.xml
```

コンソール出力で以下を確認:

```
[INFO] ChatFontMixin loaded - font injection hooking system initialized
[INFO] ✓ Font redirection active: Using custom TTF for rendering
```

---

## 🔧 設定

### フォントサイズ

フォントサイズを変更するには、[CustomFont.java](src/main/java/com/maguro027/hytalechangefont/CustomFont.java) を編集:

```java
private static final float FONT_SIZE = 16.0f;  // ここで値を変更
```

それから再ビルド:

```bash
./gradlew build
```

### フォントパス

MOD は以下から読み込みます: `/assets/hytalemod/fonts/custom.ttf`

これは JAR 相対パスなので、TTF が JAR 内のこの正確なパスにあることを確認してください。

### フォールバック動作

カスタム TTF が読み込めない場合:

- ✅ MOD は機能します（クラッシュしません）
- ✅ システム Arial フォントにフォールバック
- ✅ コンソールに警告が表示されます

---

## 📚 開発

### プロジェクト構造

```
Hytale-ChangeFontMOD/
├── src/
│   └── main/
│       ├── java/com/maguro027/hytalechangefont/
│       │   ├── ChangeFontMod.java          (プラグインエントリポイント)
│       │   ├── CustomFont.java             (フォント読み込みロジック)
│       │   └── mixin/ChatFontMixin.java    (Mixin 注入)
│       └── resources/
│           ├── manifest.json               (Hyxin メタデータ)
│           ├── hytalemod.mixins.json       (Mixin 設定)
│           └── assets/hytalemod/fonts/
│               └── custom.ttf              (カスタムフォントファイル)
├── build.gradle.kts                        (ビルド設定)
└── README.md                               (このファイル)
```

### ビルド

```bash
# クリーンビルド
./gradlew clean build

# 出力
build/libs/hytale-changefont-mod-1.0.jar
```

### Mixin 注入の変更

Mixin ターゲットクラスは Hytale クライアントをデコンパイルして確認する必要があります:

```bash
# Hytale JAR をデコンパイル
java -jar cfr.jar HytaleClient.jar
```

その後 [ChatFontMixin.java](src/main/java/com/maguro027/hytalechangefont/mixin/ChatFontMixin.java) を更新:

- `@Mixin(targets = "...")` を正しいクラスに変更
- `@Redirect` メソッド/フィールド名を更新
- バイトコードターゲットパスを確認

詳細な手順については ChatFontMixin.java のコメントを参照してください。

---

## 🐛 トラブルシューティング

### Early Plugin エラー: "Cannot invoke \"String.hashCode()\" because \"this.group\" is null"

**症状**: MOD が読み込まれ、サーバー起動時に NullPointerException が発生してゲームがクラッシュ

**原因**:

- `onEnable()` がサーバー初期化中（初期化完了前）に呼び出されている
- グローバルゲーム状態がまだ初期化されていない
- Hyxin Early Plugin の読み込みタイミングの問題

**解決方法**:

1. **JVM フラグを確認**

    ```
    --accept-early-plugins
    ```

    このフラグを Hytale 起動時に追加してください

2. **EarlyPlugins ディレクトリの配置順を確認**

    複数の MOD がある場合は、このプラグインを **最初の位置** に配置してください：

    ```
    Hytale/EarlyPlugins/
    ├── 00-hytale-changefont-mod-1.0.jar    ← 最初に読み込まれる
    ├── 01-other-mod.jar
    ├── 02-another-mod.jar
    └── ...
    ```

    ファイル名の先頭に数字を付けるでアルファベット順で読み込み順序を制御できます。

3. **遅延初期化が有効か確認**

    v1.0.1-hotfix 以降は、`onEnable()` が遅延初期化を使用しています：

    ```java
    Thread.sleep(1500);  // サーバー初期化完了を待つ
    ```

    コンソールで以下が表示されることを確認:

    ```
    [EARLY PLUGIN] Server initialization in progress...
    [DELAYED INIT] Beginning CustomFont initialization...
    [DELAYED INIT] ✓ Plugin initialized successfully!
    ```

4. **他の MOD との競合をチェック**

    複数の Early Plugin が同時に画面初期化を試みている可能性があります：
    - ログで `FAILED TO START SERVER` の直前に何が読み込まれたか確認
    - 疑わしい MOD を無効化してテスト

5. **Hyxin バージョンを確認**
    ```
    Hyxin-0.0.11-all.jar or higher required
    ```

### 問題: JAR が読み込まれない

**症状**: `[ERROR] Class not found: ChangeFontMod`

**解決方法**:

1. manifest.json の `"Main"` 値が正しいことを確認
2. JAR 構造を確認: `jar tf hytale-changefont-mod-1.0.jar | head`
3. Hyxin バージョン 0.0.11+ であることを確認

### 問題: チャットのフォントが変わらない

**症状**: チャットテキストが引き続きデフォルトフォントを使用

**手順**:

1. ログで以下を確認: `ChatFontMixin loaded`
2. ない場合: Mixin が適用されていない（Hyxin の問題）
3. ある場合: TTF が JAR 内に正しく配置されていることを確認
4. フォールバックを試す: custom.ttf を削除 → Arial を使用すべき

### 問題: フォント形式エラー

**症状**: `[WARNING] Font format error (TTF corrupted?)`

**解決方法**:

1. TTF ファイルの有効性を確認: `file custom.ttf` → "TrueType Font" と表示されるはず
2. 別のフォントを試す: [Noto Sans JP](https://fonts.google.com/noto/specimen/Noto+Sans+JP) を使用
3. 公式ソースからダウンロード（破損していない）

### 問題: "CustomFont not initialized"

**症状**: `[WARNING] ⚠ Font redirection attempted but CustomFont not initialized`

**解決方法**:

1. `ChangeFontMod.onEnable()` が呼び出されたかどうかを確認
2. プラグインがチャットシステムの前に読み込まれることを確認
3. `CustomFont singleton initialized` のログを確認

---

## 🔍 Hytale Decompile ガイド（Mixin ターゲット確認）

Mixin 注入が失敗した場合は、Hytale クライアント JAR をデコンパイルして、正確なクラス名とメソッド名を確認する必要があります。

### ステップ 1: HytaleClient.jar を入手

- Hytale ランチャーの cache/assets ディレクトリから探す
- OR: `java -jar Launcher.jar --dump` でダンプを出力
- 通常: `~/Hytale/assets/` または `~/.cache/hytale/` など

### ステップ 2: デコンパイラをダウンロード

#### 推奨ツール（最も使いやすい）

| ツール      | コマンド                                             | 対応環境 |
| ----------- | ---------------------------------------------------- | -------- |
| **JD-GUI**  | GUI で JAR を開く                                    | Win/Mac  |
| **CFR**     | `java -jar cfr.jar HytaleClient.jar --outputdir src` | All      |
| **Procyon** | `java -jar procyon.jar HytaleClient.jar`             | All      |

#### JD-GUI（最も簡単）推奨

1. [JD-GUI をダウンロード](https://java-decompiler.github.io/)
2. JAR をドラッグ＆ドロップで開く
3. 検索: `Ctrl+F` で "Chat"（大文字小文字を区別）
4. 結果から `ChatRenderer`, `TextRenderer`, `FontRenderer` などを探す

#### CFR（コマンドライン）推奨

```bash
# デコンパイル出力をファイルに保存
java -jar cfr.jar HytaleClient.jar --outputdir decompiled/

# または直接検索
java -jar cfr.jar HytaleClient.jar | grep -i "ChatRenderer\|TextRenderer\|FontRenderer"
```

### ステップ 3: ターゲットクラスを特定

デコンパイル結果で以下を探します：

#### A. チャットレンダーのクラス名

`ChatFontMixin.java` の `@Mixin` アノテーションを見つけて更新：

```java
// 例: デコンパイル結果が以下だった場合
// public class ChatRenderer {
//     private Font font = ...
//     public void renderChatMessage(String text) { ... }

// 修正: @Mixin アノテーションを以下に更新
@Mixin(targets = "net.hytale.client.render.ChatRenderer")
// または
@Mixin(targets = "net.hytale.ui.chat.ChatRenderer")
```

#### B. フォント描画メソッド名

`@Redirect` アノテーションを見つけて更新：

```java
// デコンパイル結果で以下のメソッドを探す:
// - renderChatMessage(String text, int x, int y)
// - drawText(MessageComponent msg, float x, float y)
// - render(String text, Vector3f pos)

// ChatFontMixin.java の @Redirect を編集:
@Redirect(method = "renderChatMessage", at = @At(...))
private Font redirectFont(Font original) { ... }
```

#### C. フォント変数名

`@Redirect` の `target` フィールド指定を更新：

```java
// デコンパイル結果から:
// class ChatRenderer {
//     private Font font;          // ← フォント変数
//     private Font defaultFont;   // ← 静的フォント

// 修正: target パスを更新
@Redirect(
    method = "renderChatMessage",
    at = @At(
        value = "FIELD",
        target = "Lnet/hytale/client/render/ChatRenderer;font:Ljava/awt/Font;"
        //                                             ^^^^^
        //                ここを正しいフィールド名に更新
    )
)
```

### ステップ 4: ChatFontMixin.java を更新

例）デコンパイル結果が以下だった場合：

```java
// 他のクラスの例
public class net.hytale.client.feature.chat.ChatRenderer {
    private Font currentFont;

    public void renderMessage(ChatMessage msg) {
        drawText(msg.getText(), currentFont);
    }
}
```

修正後：

```java
@Mixin(targets = "net.hytale.client.feature.chat.ChatRenderer")
public class ChatFontMixin {

    @Redirect(
        method = "renderMessage",
        at = @At(
            value = "FIELD",
            target = "Lnet/hytale/client/feature/chat/ChatRenderer;currentFont:Ljava/awt/Font;"
        )
    )
    private Font redirectFont(Font original) {
        // ... 既存のログ処理
    }
}
```

### ステップ 5: ビルド＆テスト

```bash
./gradlew clean build

# Mixin annotation processing ログを確認
> Task :compileJava
[OK] hytalemod.refmap.json generated successfully

# EarlyPlugins に配置
cp build/libs/hytale-changefont-mod-1.0.jar ~/Hytale/EarlyPlugins/
```

### トラブルシューティング: Mixin が反応しない

コンソールで以下をチェック：

```
[INFO] [Mixin] ChatFontMixin loaded ← 出ていない = jar に Mixin がない
[INFO] ✓ Font injected into ... ← 出ていない = ターゲット名が間違っている
```

**解決:**

1. `hytalemod.refmap.json` がビルド出力に生成されているか確認
2. ターゲットクラス名をすべて検証（大文字小文字、パッケージ名）
3. 別のデコンパイラを試す（JD-GUI → CFR）

## 📖 参考資料

- [Mixin フレームワーク ドキュメント](https://github.com/SpongePowered/Mixin/wiki)
- [Fabric Mixin チュートリアル](https://fabricmc.net/wiki/tutorial:mixin_introduction)
- [Java Font API](https://docs.oracle.com/en/java/javase/25/docs/api/java.awt/java/awt/Font.html)
- [Gradle Kotlin DSL ガイド](https://docs.gradle.org/current/userguide/kotlin_dsl.html)

---

## 📄 ライセンス

MIT ライセンス - [LICENSE](LICENSE) ファイルを参照

---

## 👤 作者

**maguro027**

- GitHub: [@maguro027](https://github.com/maguro027)
- リポジトリ: [Hytale-ChangeFontMOD](https://github.com/maguro027/Hytale-ChangeFontMOD)

---

## 🤝 貢献

問題やプルリクエストを歓迎します！[貢献ガイドライン](CONTRIBUTING.md) を参照してください

---

## ⚠️ 重要な注意事項

1. **デコンパイラ検証**: Mixin ターゲットクラスは Hytale クライアントをデコンパイルして確認する必要があります。詳細は ChatFontMixin.java を参照してください。

2. **フォント選択**: すべての TTF フォントがゲームで適切に機能するわけではありません。推奨フォント:
    - ✅ Noto Sans シリーズ（Google）
    - ✅ DejaVu Sans
    - ✅ Liberation Sans
    - ✅ Segoe UI

3. **ファイルアクセス権**: EarlyPlugins ディレクトリで JAR に読み取り権限があることを確認

4. **Java バージョン**: Java 25 LTS が必須。確認: `java -version`

---

**最終更新: 2026/02/23**
