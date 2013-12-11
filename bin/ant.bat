@echo off

if "%OS%"=="Windows_NT" @setlocal

set LOCALCLASSPATH=%CLASSPATH%
set LOCALCLASSPATH0=

for %%i in ("..\lib\compile.ant.lib\*.jar") do call :cp_jar %%i

set CLASSPATH=%CLASSPATH%;%LOCALCLASSPATH0%

call %ANT_HOME%\bin\ant.bat %*

set CLASSPATH=%LOCALCLASSPATH0%

set LOCALCLASSPATH0=
set LOCALCLASSPATH=

if "%OS%"=="Windows_NT" @endlocal

goto :EOF


rem append classpath
:cp_jar
set _CP_FILE=%~f1
if ""%~s1""=="""" goto gotAllArgs
shift

:argCheck
if ""%~s1""=="""" goto gotAllArgs
set _CP_FILE=%_CP_FILE% %~f1
shift
goto argCheck

:gotAllArgs
set LOCALCLASSPATH0=%_CP_FILE%;%LOCALCLASSPATH0%

goto :EOF