@echo off

for /r E:\zhy.xyz %%a in (*.xyz) do ren "%%a" "%%~na.java"