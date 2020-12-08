echo  %TEMP%
IF not EXIST logs (
    MKDIR logs
)
FOR /R "%TEMP%" %%G IN (magpie*) DO (
    ECHO COPY %%G %cd%\logs\%%~nxG
    xcopy %%G %cd%\logs\%%~nxG* /Y
)