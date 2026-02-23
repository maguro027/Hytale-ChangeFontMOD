# 🔤 カスタムフォント設定ガイド

このディレクトリに TrueType フォント (`.ttf`) を配置することで、Hytale のチャット表示フォントをカスタマイズできます。

## ⚡ クイックスタート

1. **お好みの TTF ファイトを入手**
    - [Google Fonts](https://fonts.google.com) から無料ダウンロード
    - または、システムフォント (`C:\Windows\Fonts\`) から探す

2. **ファイル名を `custom.ttf` にリネーム**

3. **このディレクトリに配置**

    ```
    src/main/resources/assets/hytalemod/fonts/
    └── custom.ttf  ← ここに配置
    ```

4. **再ビルド**

    ```bash
    ./gradlew clean build
    ```

5. **JAR を EarlyPlugins フォルダにコピー**

## 📚 おすすめフォント

### 日本語対応

| フォント            | サイズ   | 用途         | 入手先                                                              |
| ------------------- | -------- | ------------ | ------------------------------------------------------------------- |
| **Noto Sans JP**    | 中サイズ | 標準（推奨） | [Google Fonts](https://fonts.google.com/noto/specimen/Noto+Sans+JP) |
| **M PLUS Rounded**  | 小〜中   | フレンドリー | [Google Fonts](https://fonts.google.com/specimen/M+PLUS+Rounded+1c) |
| **Zen Maru Gothic** | 小〜中   | 丸ゴシック   | [Google Fonts](https://fonts.google.com/specimen/Zen+Maru+Gothic)   |

### 英数・汎用

| フォント           | スタイル         | 用途         |
| ------------------ | ---------------- | ------------ |
| **Roboto**         | 現代的           | 標準フォント |
| **Inter**          | スクリーン最適化 | UI用         |
| **JetBrains Mono** | モノスペース     | コード風     |

### ゲーム向け

| フォント           | スタイル   | 用途       |
| ------------------ | ---------- | ---------- |
| **Press Start 2P** | ドット絵風 | レトロ     |
| **Orbitron**       | 未来的     | SF要素     |
| **Kenney Fonts**   | ゲーム向け | カジュアル |

## 🔧 トラブルシューティング

### ❌ フォントが反映されない

**確認項目**:

- [ ] ファイル名は正確に `custom.ttf` か？
- [ ] ファイル形式は TrueType (`.ttf`) か？
- [ ] ビルド時に `build/libs/hytale-changefont-mod-1.0.jar` の中に
      `assets/hytalemod/fonts/custom.ttf` が含まれているか？

**解決方法**:

```bash
# JAR 内のファイル確認
jar tf build/libs/hytale-changefont-mod-1.0.jar | grep custom.ttf
```

### ❌ フォント読み込みエラー

**原因**: TTF ファイルが破損または無効形式

**解決方法**:

1. 別のフォントをダウンロード
2. ファイル検証（Windows ファイルエクスプローラーで TTF を開いてプレビュー）

### ⚠️ フォントが小さい/大きい

`src/main/java/com/maguro027/hytalechangefont/CustomFont.java` を編集:

```java
private static final float FONT_SIZE = 16.0f;  // ← この値を調整
```

- `14.0f` = 小サイズ
- `16.0f` = 標準
- `18.0f` = 大サイズ

## 📋 対応フォーマット

| フォーマット      | 対応状況    | 備考                          |
| ----------------- | ----------- | ----------------------------- |
| TrueType (`.ttf`) | ✅ 完全対応 | 推奨フォーマット              |
| OpenType (`.otf`) | ⚠️ 部分的   | `custom.ttf` にリネームで試す |
| PostScript CFF    | ❌ 未対応   | Java Font API の制限          |

## 🎨 フォント選択のコツ

### 可読性

- **文字の区別**: i/l/1/| が明確に異なるか
- **行間**: 密集しすぎていないか
- **スペース**: 半角スペースが見えるか

### ゲーム向け

- **ヒンティング**: 小さいサイズで鮮明か
- **等幅**: チャット配列を整えたい場合は等幅フォント
- **重み**: Regular より Light だと読みやすい場合もある

### 言語対応

- **日本語チャット**: 日本語フォント（JP/CJK対応）を選択
- **記号**: 絵文字は別途対応が必要（MOD では未実装）

## ✅ フォント検証

**Google Fonts から** `Noto Sans JP` をダウンロードする場合:

1. https://fonts.google.com/noto/specimen/Noto+Sans+JP にアクセス
2. **「Download family」** をクリック
3. ZIP ファイルを展開
4. `NotoSansJP-Regular.ttf` (または任意の `.ttf`) を選択
5. `custom.ttf` にリネーム
6. このディレクトリ (`assets/hytalemod/fonts/`) に配置

## 📄 ライセンス

フォント配布時は必ずライセンスを確認:

| ソース               | ライセンス    | 配布可否          |
| -------------------- | ------------- | ----------------- |
| Google Fonts         | SIL OFL 1.1   | ✅ 可（表記必須） |
| DejaVu Fonts         | Public Domain | ✅ 可             |
| JetBrains Fonts      | OFL 1.1       | ✅ 可             |
| Windows/Mac フォント | 製造元に確認  | ⚠️ 確認必須       |

**配布予定の場合**: Google Fonts の利用をお勧めします（制限なし）

## 🌐 参考リンク

- [Google Fonts](https://fonts.google.com) - 無料オープンソースフォント
- [Font Squirrel](https://www.fontsquirrel.com) - ウェブフォント
- [DaFont](https://www.dafont.com) - 創作フォント
- [DejaVu Fonts](https://dejavu-fonts.github.io) - パブリックドメイン
- [Java Font Tuning](https://docs.oracle.com/javase/tutorial/2d/text/fonts.html) - 技術資料

## 💡 よくある質問

### Q: フォントがない場合は？

**A**: MOD は **Arial フォント**（システムフォント）に自動フォールバックします。常に動作します。

### Q: フォント変更中にゲームが起動できない？

**A**: 以下を確認:

- JAR ファイルが `EarlyPlugins/` フォルダにあるか
- 別の MOD との競合はないか
- Hyxin loader が正しく有効化されているか

### Q: 複数のフォントを切り替えたい？

**A**: 現在のバージョンは 1 つのフォントのみ対応。複数フォント対応は将来の機能としてプランされています。

### Q: 日本語フォントの表示が乱れる

**A**: [Noto Sans JP](https://fonts.google.com/noto/specimen/Noto+Sans+JP) または [Source Han Sans](https://github.com/adobe-fonts/source-han-sans) を試してください。

---

**最終更新**: 2026/02/23  
**MOD バージョン**: 1.0  
**対応フォント形式**: TrueType Font (.ttf)
