@echo off
chcp 65001 > nul
setlocal enabledelayedexpansion

REM ============================================================================
REM Hytale Font Modifier Tool
REM フォント置き換えツール for Hytale Chat/UI
REM ============================================================================

echo.
echo ╔════════════════════════════════════════════════════════════════╗
echo ║        Hytale Font Modifier - アセット自動置き換え           ║
echo ╚════════════════════════════════════════════════════════════════╝
echo.

REM ============================================================================
REM 質問 1: プレビュー版 or リリース版?
REM ============================================================================

echo [質問 1/2] どのバージョンを修正しますか？
echo.
echo   [A] プレビュー版 (Preview)
echo   [B] リリース版 (Release)
echo.

set /p VERSION_CHOICE="選択してください (A or B): "

if /i "!VERSION_CHOICE!"=="A" (
    set HYTALE_VERSION=preview
    set ASSETS_PATH=J:\Hy\install\preview\package\game\latest\Assets.zip
    echo ✓ プレビュー版を選択しました
) else if /i "!VERSION_CHOICE!"=="B" (
    set HYTALE_VERSION=release
    set ASSETS_PATH=J:\Hy\install\release\package\game\latest\Assets.zip
    echo ✓ リリース版を選択しました
) else (
    echo ✗ 無効な選択です。A または B を入力してください。
    pause
    exit /b 1
)

echo.

REM ============================================================================
REM Assets.zip が存在するか確認
REM ============================================================================

if not exist "!ASSETS_PATH!" (
    echo ✗ エラー: Assets.zip が見つかりません
    echo   パス: !ASSETS_PATH!
    echo.
    echo   Hytale がこの場所にインストールされていない可能性があります。
    pause
    exit /b 1
)

echo [確認] Assets.zip: !ASSETS_PATH!
echo.

REM ============================================================================
REM 質問 2: フォントの入手方法
REM ============================================================================

echo [質問 2/2] カスタムフォント（TTF）をどこから取得しますか？
echo.
echo   [A] URL を入力してダウンロード
echo       （例: https://github.com/.../NotoSansJP.ttf）
echo.
echo   [B] ローカルファイルパスを指定
echo       （例: C:\Fonts\MyFont.ttf）
echo.

set /p FONT_CHOICE="選択してください (A or B): "

if /i "!FONT_CHOICE!"=="A" (
    echo.
    echo フォント URL を入力してください:
    echo （例: https://github.com/notofonts/noto-cjk/releases/download/Noto-v20240101/NotoSansJP-Regular.ttf）
    echo.
    set /p FONT_URL="フォント URL: "
    
    if "!FONT_URL!"=="" (
        echo ✗ URL が入力されていません
        pause
        exit /b 1
    )
    
    REM URL からダウンロード
    echo.
    echo [進行中] フォントをダウンロード中...
    set TEMP_FONT=%TEMP%\hytale_custom_font.ttf
    
    powershell -Command "try { [Net.ServicePointManager]::SecurityProtocol = [Net.SecurityProtocolType]::Tls12; (New-Object System.Net.WebClient).DownloadFile('!FONT_URL!', '!TEMP_FONT!'); Write-Host 'ダウンロード完了'; exit 0 } catch { Write-Host 'ダウンロード失敗: ' $_; exit 1 }"
    
    if errorlevel 1 (
        echo ✗ フォントのダウンロードに失敗しました
        echo   URL: !FONT_URL!
        pause
        exit /b 1
    )
    
    set FONT_FILE=!TEMP_FONT!
    echo ✓ ダウンロード完了: !FONT_FILE!
    
) else if /i "!FONT_CHOICE!"=="B" (
    echo.
    echo ローカルたファイルパスを入力してください:
    echo （例: C:\Fonts\NotoSansJP-Regular.ttf）
    echo.
    set /p FONT_FILE="ファイルパス: "
    
    if not exist "!FONT_FILE!" (
        echo ✗ ファイルが見つかりません: !FONT_FILE!
        pause
        exit /b 1
    )
    
    echo ✓ ファイル確認完了: !FONT_FILE!
    
) else (
    echo ✗ 無効な選択です。A または B を入力してください。
    pause
    exit /b 1
)

echo.

REM ============================================================================
REM Assets.zip をバックアップ
REM ============================================================================

echo [1/4] バックアップを作成中...
set BACKUP_PATH=!ASSETS_PATH!.backup

if exist "!BACKUP_PATH!" (
    echo   ※ バックアップは既に存在します: !BACKUP_PATH!
) else (
    copy "!ASSETS_PATH!" "!BACKUP_PATH!" > nul
    if errorlevel 1 (
        echo ✗ バックアップの作成に失敗しました
        pause
        exit /b 1
    )
    echo ✓ バックアップ作成完了
)

echo.

REM ============================================================================
REM Assets.zip を解凍
REM ============================================================================

echo [2/4] Assets.zip を解凍中...
set EXTRACT_PATH=%TEMP%\hytale_assets_temp
rmdir /s /q "!EXTRACT_PATH!" 2>nul
mkdir "!EXTRACT_PATH!"

powershell -Command "Expand-Archive -Path '!ASSETS_PATH!' -DestinationPath '!EXTRACT_PATH!' -Force" > nul

if errorlevel 1 (
    echo ✗ Assets.zip の解凍に失敗しました
    rmdir /s /q "!EXTRACT_PATH!" 2>nul
    pause
    exit /b 1
)

echo ✓ 解凍完了: !EXTRACT_PATH!

REM fonts フォルダが存在するか確認
if not exist "!EXTRACT_PATH!\fonts" (
    echo ⚠ 警告: fonts フォルダが見つかりません
    echo   パス: !EXTRACT_PATH!\fonts
    echo.
    echo 作成します...
    mkdir "!EXTRACT_PATH!\fonts"
)

echo.

REM ============================================================================
REM フォントをコピー
REM ============================================================================

echo [3/4] フォントを置き換え中...

REM 既存フォントをバックアップ
for %%f in ("!EXTRACT_PATH!\fonts\*.ttf") do (
    if exist "%%f" (
        ren "%%f" "%%~nf.original" > nul
    )
)

REM 新しいフォントをコピー
copy "!FONT_FILE!" "!EXTRACT_PATH!\fonts\chat.ttf" > nul

if errorlevel 1 (
    echo ✗ フォントのコピーに失敗しました
    rmdir /s /q "!EXTRACT_PATH!" 2>nul
    pause
    exit /b 1
)

echo ✓ フォント置き換え完了

REM 複数のフォント名でもコピー（互換性のため）
copy "!EXTRACT_PATH!\fonts\chat.ttf" "!EXTRACT_PATH!\fonts\default.ttf" > nul 2>&1
copy "!EXTRACT_PATH!\fonts\chat.ttf" "!EXTRACT_PATH!\fonts\ui.ttf" > nul 2>&1

echo.

REM ============================================================================
REM 修正された Assets.zip を再圧縮
REM ============================================================================

echo [4/4] Assets.zip を再圧縮中...

REM 元のファイルを削除
del "!ASSETS_PATH!" > nul

REM 新しい ZIP を作成
powershell -Command "Compress-Archive -Path '!EXTRACT_PATH!\*' -DestinationPath '!ASSETS_PATH!' -Force" > nul

if errorlevel 1 (
    echo ✗ Assets.zip の再圧縮に失敗しました
    echo.
    echo バックアップから復元しています...
    copy "!BACKUP_PATH!" "!ASSETS_PATH!" > nul
    rmdir /s /q "!EXTRACT_PATH!" 2>nul
    pause
    exit /b 1
)

echo ✓ 再圧縮完了

REM 一時ファイルを削除
rmdir /s /q "!EXTRACT_PATH!" 2>nul

if /i "!FONT_CHOICE!"=="A" (
    del "!TEMP_FONT!" 2>nul
)

echo.

REM ============================================================================
REM 完了
REM ============================================================================

echo ╔════════════════════════════════════════════════════════════════╗
echo ║                       ✓ 完了しました！                        ║
echo ╚════════════════════════════════════════════════════════════════╝
echo.
echo [情報] Assets.zip を修正しました:
echo   パス: !ASSETS_PATH!
echo.
echo [次のステップ]
echo   1. Hytale アプリを完全に閉じてください
echo   2. もし Assets キャッシュが存在したら削除してください:
echo      J:\Hy\UserData\CachedAssets\
echo   3. Hytale を再起動してください
echo.
echo [復元方法]
echo   フォント変更を元に戻す場合は、以下から復元:
echo   %BACKUP_PATH%
echo.

pause
exit /b 0
