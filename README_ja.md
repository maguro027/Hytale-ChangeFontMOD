# Hytale フォント修正ツール

**Hytale チャット & UI フォント自動置き換えツール**

Hytale のゲーム UI やチャットで使用されているフォントを、カスタム TTF フォントに自動的に置き換えます。

## 📋 概要

- ✅ **簡単操作** - ZIP ファイルを解凍して BAT を実行するだけ
- ✅ **安全** - 自動バックアップ機能付き（修正失敗時は自動復元）
- ✅ **柔軟** - URL からダウンロード、またはローカルファイルを使用可能
- ✅ **全バージョン対応** - プレビュー版/リリース版 両対応

## 🔧 必要なもの

| 項目 | 必須 | 説明 |
|------|------|------|
| OS | ✅ | Windows（PowerShell 対応） |
| Hytale | ✅ | プレビュー版または リリース版 |
| TTF フォント | ✅ | [Noto Sans JP](https://fonts.google.com/noto/specimen/Noto+Sans+JP) 推奨 |
| 管理者権限 | ❌ | なくても動作（推奨） |

### Hytale インストール場所

```
プレビュー版: J:\Hy\install\preview\package\game\latest\
リリース版:   J:\Hy\install\release\package\game\latest\
```

## 🚀 使い方

### Step 1: BAT ファイルを実行

```bash
modify-hytale-font.bat
```

コマンドプロンプトが開きます。

### Step 2: 質問 1 に答える「どのバージョンを修正しますか?」

```
[A] プレビュー版 (Preview)
[B] リリース版 (Release)

選択してください (A or B): A
```

**ご自身の Hytale バージョンに応じて選択してください。**

### Step 3: 質問 2 に答える「フォント取得方法は?」

```
[A] URL を入力してダウンロード
[B] ローカルファイルパスを指定

選択してください (A or B): A
```

#### オプション A: URL からダウンロード

```
フォント URL: https://github.com/notofonts/noto-cjk/releases/download/Noto-v20240101/NotoSansJP-Regular.ttf
```

推奨フォント URL:
- **Noto Sans JP**: https://github.com/notofonts/noto-cjk/releases/download/Noto-v20240101/NotoSansJP-Regular.ttf
- **Noto Sans**: https://github.com/notofonts/noto/releases/download/Noto-v20240101/NotoSans-Regular.ttf

#### オプション B: ローカルファイルパス

```
ファイルパス: C:\Fonts\NotoSansJP-Regular.ttf
```

### Step 4: 処理を待つ

ツールが自動実行します：

```
[1/4] バックアップを作成中...  ✓
[2/4] Assets.zip を解凍中...   ✓
[3/4] フォントを置き換え中...  ✓
[4/4] Assets.zip を再圧縮中... ✓
```

完了メッセージが表示されたら、**Enter キーを押して終了します。**

### Step 5: Hytale を再起動

```
1. Hytale アプリを完全に閉じる（タスクマネージャで確認）
2. キャッシュをクリアしたい場合（オプション）:
   フォルダを削除: J:\Hy\UserData\CachedAssets\
3. Hytale を再起動
4. チャットを開く（T キー）
5. フォントが変わっていることを確認
```

## ⚠️ トラブルシューティング

### Q1: フォントが変わらない

【原因】ゲームがキャッシュを使用している
【解決】
```bash
# キャッシュフォルダを削除
rmdir /s /q J:\Hy\UserData\CachedAssets\

# その後 Hytale を再起動
```

### Q2: URL からダウンロード失敗

【原因1】ネットワーク接続の問題
【解決】オプション B（ローカルファイル）を用いて、手動でダウンロードしたフォントを指定

【原因2】URL が無効
【解決】フォント URL が正確か確認

### Q3: 変更を元に戻したい

【解決】バックアップから復元
```bash
# リリース版の場合
copy "J:\Hy\install\release\package\game\latest\Assets.zip.backup" ^
     "J:\Hy\install\release\package\game\latest\Assets.zip" /Y
```

（`%` を `^` に置き換えて実行してください）

### Q4: "エラー: Assets.zip が見つかりません"

【原因】Hytale がこのパスにインストールされていない
【解決】Hytale のインストール路を確認してください
```bash
dir J:\Hy\install\release\package\game\latest\
```

## 🎨 推奨フォント

以下のフォントが確認されています：

### 日本語対応
| フォント | 特徴 | サイズ |
|---------|------|--------|
| **Noto Sans JP** | Google 公式、高品質 | ~10 MB |
| Noto Serif JP | セリフ（見出し用） | ~10 MB |
| M+ 1p | 日本フォント | ~10 MB |

### 英字
| フォント | 特徴 | 
|---------|------|
| **Noto Sans** | Google 公式 |
| DejaVu Sans | 多言語対応 |
| Liberation Sans | 高い互換性 |

## 📁 ファイル構造

```
Hytale-ChangeFontMOD/
├── modify-hytale-font.bat      ← メインツール（これを実行）
├── README.md                   ← 英文説明書
└── README_ja.md                ← 日本語説明書（このファイル）
```

## 🔄 動作原理

```
modify-hytale-font.bat
  ↓
[質問 1] バージョン選択
  ↓
[質問 2] フォント取得方法
  ↓
[1/4] Assets.zip をバックアップ
  ↓
[2/4] Assets.zip を解凍
  ↓
[3/4] フォント置き換え（chat.ttf, default.ttf など）
  ↓
[4/4] Assets.zip を再圧縮
  ↓
✓ 完了 → Hytale 再起動で反映
```

## 💾 バックアップ場所

修正に失敗した場合のバックアップファイル：

```
プレビュー版: J:\Hy\install\preview\package\game\latest\Assets.zip.backup
リリース版:   J:\Hy\install\release\package\game\latest\Assets.zip.backup
```

これらのファイルから手動で復元できます。

## 🆘 さらに詳しく

### 手動で復元する（コマンドライン）

```cmd
REM リリース版の場合
copy "J:\Hy\install\release\package\game\latest\Assets.zip.backup" ^
     "J:\Hy\install\release\package\game\latest\Assets.zip" /Y

REM プレビュー版の場合
copy "J:\Hy\install\preview\package\game\latest\Assets.zip.backup" ^
     "J:\Hy\install\preview\package\game\latest\Assets.zip" /Y
```

### キャッシュを完全クリア

```cmd
REM CachedAssets フォルダを削除
rmdir /s /q J:\Hy\UserData\CachedAssets\

REM Telemetry キャッシュ（オプション）
rmdir /s /q J:\Hy\UserData\Telemetry\
```

## 📞 問い合わせ

問題が発生した場合：
1. 上記「トラブルシューティング」を確認
2. バックアップから復元して正常状態に戻す
3. GitHub Issues で報告

---

## 📌 注意事項

- ⚠️ BAT 実行時は Hytale を完全に閉じてください
- ⚠️ Assets.zip は自動バックアップされますが、念のため手動バックアップも推奨
- ⚠️ TTF ファイルは有効な TrueType フォントである必要があります
- ⚠️ 大きすぎるフォント（>50 MB）はパフォーマンス低下につながる可能性

---

**Made with ❤️ for Hytale Font Customization**

**バージョン**: 1.0.0  
**最終更新**: 2026-02-23  
**対応 OS**: Windows
