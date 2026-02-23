# Hytale Font Modifier

**Hytale チャット & UI フォント自動置き換えツール**

Hytale のゲーム UI やチャットで使用されているフォントを、カスタム TTF フォントに自動的に置き換えます。

## Features

✅ **簡単操作** - BAT ファイルを実行するだけ
✅ **安全** - 自動バックアップ機能付き
✅ **柔軟** - URL からダウンロード、またはローカルファイルを使用可能
✅ **自動復元** - 修正に失敗した場合は自動的にバックアップから復元

## Requirements

- Windows OS (PowerShell 対応)
- Hytale インストール済み
  - プレビュー版: `J:\Hy\install\preview\`
  - リリース版: `J:\Hy\install\release\`
- カスタムフォント (TTF 形式)
  - 推奨: [Noto Sans JP](https://fonts.google.com/noto/specimen/Noto+Sans+JP)
  - その他: 任意の TTF フォント

## Usage

### Step 1: BAT ファイルを実行

```bash
modify-hytale-font.bat
```

### Step 2: 質問に答える

#### 質問 1: バージョン選択
```
[A] プレビュー版 (Preview)
[B] リリース版 (Release)
```

#### 質問 2: フォント取得方法
```
[A] URL を入力してダウンロード
    例: https://github.com/notofonts/noto-cjk/releases/download/Noto-v20240101/NotoSansJP-Regular.ttf

[B] ローカルファイルパスを指定
    例: C:\Fonts\MyFont.ttf
```

### Step 3: 処理完了を待つ

ツールが以下を自動実行します：
1. ✓ Assets.zip をバックアップ
2. ✓ Assets.zip を解凍
3. ✓ フォントを置き換え
4. ✓ Assets.zip を再圧縮

### Step 4: Hytale を再起動

```
1. Hytale アプリを完全に閉じる
2. キャッシュをクリア (オプション):
   削除: J:\Hy\UserData\CachedAssets\
3. Hytale を再起動
4. チャットを開いてフォント確認
```

## Support

### Q: フォントが変わらない

**A**: キャッシュをクリアしてから再起動してください
```bash
rmdir /s /q J:\Hy\UserData\CachedAssets\
```

### Q: 変更を元に戻したい

**A**: バックアップから復元
```bash
copy "J:\Hy\install\release\package\game\latest\Assets.zip.backup" "J:\Hy\install\release\package\game\latest\Assets.zip" /Y
```

### Q: URL からダウンロードに失敗する

**A**: 手動でダウンロードして、オプション B を使用してください

### Q: ローカルファイルが見つからない

**A**: ファイルパスが正確かご確認ください
- 半角スペースが含まれている場合は `""` で囲んでください
- 相対パスではなく絶対パスを使用してください

## Recommended Fonts

以下のフォントが確認されています：

| フォント名 | 用途 | ダウンロード |
|-----------|------|-----------|
| Noto Sans JP | 日本語 + 英字 | [Google Fonts](https://fonts.google.com/noto/specimen/Noto+Sans+JP) |
| Noto Sans | 英字 | [Google Fonts](https://fonts.google.com/noto/specimen/Noto+Sans) |
| DejaVu Sans | 多言語 | [DejaVu](https://dejavu-fonts.github.io/) |
| Liberation Sans | 英字 | [Liberation](https://github.com/liberationfonts) |

## How it Works

```
modify-hytale-font.bat
    ↓ [ユーザー入力: バージョン選択]
    ↓ [ユーザー入力: フォント取得]
    ↓ [バックアップ作成]
    ↓ [Assets.zip 解凍]
    ↓ [フォント置き換え]
    ↓ [Assets.zip 再圧縮]
    ↓ [完了]
Hytale 再起動で反映
```

## File Structure

```
Hytale-ChangeFontMOD/
├── modify-hytale-font.bat      メインツール
├── README.md                   英文説明書
└── README_ja.md                日本語説明書
```

## Backup Location

修正を失敗した場合のバックアップ:
```
J:\Hy\install\preview\package\game\latest\Assets.zip.backup   (プレビュー版)
J:\Hy\install\release\package\game\latest\Assets.zip.backup    (リリース版)
```

## Advanced: Manual Restoration

バックアップから手動で復元する場合：

```powershell
# プレビュー版
copy "J:\Hy\install\preview\package\game\latest\Assets.zip.backup" `
     "J:\Hy\install\preview\package\game\latest\Assets.zip" /Y

# リリース版
copy "J:\Hy\install\release\package\game\latest\Assets.zip.backup" `
     "J:\Hy\install\release\package\game\latest\Assets.zip" /Y
```

## License

MIT License

## Support

問題が発生した場合は、GitHub Issues で報告してください。

---

**Made with ❤️ for Hytale Font Customization**
