@rem define var

SET PROTOCOL=%1
SET PROTOCOL=rnmweb

IF NOT "%PROTOCOL%" == ""  GOTO _SET
SHIFT
ECHO The PROTOCOL environment variable is not defined correctly
ECHO This environment variable is needed to run this program
GOTO _END

SET CURRENT_DIR=%CD%

:_SET
SET SRC_ROOT=..\out\vm
SET PROJECT_ROOT=G:\workspace\%PROTOCOL%
SET DIST_ROOT=%PROJECT_ROOT%\src
SET WEBINF_ROOT=%PROJECT_ROOT%\WebRoot\WEB-INF

@rem mkdir & copy -------BOSS-----START-------

SET MODEL=boss
SET MODEL_ROOT=%DIST_ROOT%\com\lj\web\%MODEL%
SET ACTION_ROOT=%MODEL_ROOT%\action
SET FORM_ROOT=%MODEL_ROOT%\form
SET VALIDATOR_ROOT=%MODEL_ROOT%\validator
SET JSP_ROOT=%WEBINF_ROOT%\view\%MODEL%

IF EXIST "%MODEL_ROOT%" GOTO _EXIST_MODEL_ROOT
MD %MODEL_ROOT%
:_EXIST_MODEL_ROOT

IF EXIST "%ACTION_ROOT%" GOTO _EXIST_ACTION_ROOT
MD %ACTION_ROOT%
:_EXIST_ACTION_ROOT

IF EXIST "%FORM_ROOT%" GOTO _EXIST_FORM_ROOT
MD %FORM_ROOT%
:_EXIST_FORM_ROOT

IF EXIST "%VALIDATOR_ROOT%" GOTO _EXIST_VALIDATOR_ROOT
MD %VALIDATOR_ROOT%
:_EXIST_VALIDATOR_ROOT

IF EXIST "%JSP_ROOT%" GOTO _EXIST_JSP_ROOT
MD %JSP_ROOT%
:_EXIST_JSP_ROOT

@echo rename *.java.%MODEL% to *.java
SET EXT_OLD=java.%MODEL%
SET EXT_NEW=java

copy /Y %SRC_ROOT%\*Action.%EXT_OLD%  %ACTION_ROOT%
del %SRC_ROOT%\*Action.%EXT_OLD%
SET OLD_CD=%CD%
CD %ACTION_ROOT%
FOR /F "usebackq delims=." %%f IN (`dir *.%EXT_OLD% /B /S`) DO move %%f.%EXT_OLD% %%f.%EXT_NEW%
CD %OLD_CD%

copy /Y %SRC_ROOT%\*Form.%EXT_OLD%  %FORM_ROOT%
del %SRC_ROOT%\*Form.%EXT_OLD%
SET OLD_CD=%CD%
CD %FORM_ROOT%
FOR /F "usebackq delims=." %%f IN (`dir *.%EXT_OLD% /B /S`) DO move %%f.%EXT_OLD% %%f.%EXT_NEW%
CD %OLD_CD%

copy /Y %SRC_ROOT%\*Validator.%EXT_OLD%  %VALIDATOR_ROOT%
del %SRC_ROOT%\*Validator.%EXT_OLD%
SET OLD_CD=%CD%
CD %VALIDATOR_ROOT%
FOR /F "usebackq delims=." %%f IN (`dir *.%EXT_OLD% /B /S`) DO move %%f.%EXT_OLD% %%f.%EXT_NEW%
CD %OLD_CD%

@echo rename *.jsp.%MODEL% to *.jsp
SET EXT_OLD=jsp.%MODEL%
SET EXT_NEW=jsp
copy /Y %SRC_ROOT%\*.%EXT_OLD%  %JSP_ROOT%
del %SRC_ROOT%\*.%EXT_OLD%
SET OLD_CD=%CD%
CD %JSP_ROOT%
FOR /F "usebackq delims=." %%f IN (`dir *.%EXT_OLD% /B /S`) DO move %%f.%EXT_OLD% %%f.%EXT_NEW%
CD %OLD_CD%

@rem mkdir & copy -------BOSS-----END-------


@rem mkdir & copy -------BZ-----START-------

SET MODEL=bz
SET MODEL_ROOT=%DIST_ROOT%\com\lj\web\%MODEL%
SET ACTION_ROOT=%MODEL_ROOT%\action
SET FORM_ROOT=%MODEL_ROOT%\form
SET VALIDATOR_ROOT=%MODEL_ROOT%\validator
SET JSP_ROOT=%WEBINF_ROOT%\view\%MODEL%

IF EXIST "%MODEL_ROOT%" GOTO _EXIST_MODEL_ROOT
MD %MODEL_ROOT%
:_EXIST_MODEL_ROOT

IF EXIST "%ACTION_ROOT%" GOTO _EXIST_ACTION_ROOT
MD %ACTION_ROOT%
:_EXIST_ACTION_ROOT

IF EXIST "%FORM_ROOT%" GOTO _EXIST_FORM_ROOT
MD %FORM_ROOT%
:_EXIST_FORM_ROOT

IF EXIST "%VALIDATOR_ROOT%" GOTO _EXIST_VALIDATOR_ROOT
MD %VALIDATOR_ROOT%
:_EXIST_VALIDATOR_ROOT

IF EXIST "%JSP_ROOT%" GOTO _EXIST_JSP_ROOT
MD %JSP_ROOT%
:_EXIST_JSP_ROOT

@echo rename *.java.%MODEL% to *.java
SET EXT_OLD=java.%MODEL%
SET EXT_NEW=java

copy /Y %SRC_ROOT%\*Action.%EXT_OLD%  %ACTION_ROOT%
del %SRC_ROOT%\*Action.%EXT_OLD%
SET OLD_CD=%CD%
CD %ACTION_ROOT%
FOR /F "usebackq delims=." %%f IN (`dir *.%EXT_OLD% /B /S`) DO move %%f.%EXT_OLD% %%f.%EXT_NEW%
CD %OLD_CD%

copy /Y %SRC_ROOT%\*Form.%EXT_OLD%  %FORM_ROOT%
del %SRC_ROOT%\*Form.%EXT_OLD%
SET OLD_CD=%CD%
CD %FORM_ROOT%
FOR /F "usebackq delims=." %%f IN (`dir *.%EXT_OLD% /B /S`) DO move %%f.%EXT_OLD% %%f.%EXT_NEW%
CD %OLD_CD%

copy /Y %SRC_ROOT%\*Validator.%EXT_OLD%  %VALIDATOR_ROOT%
del %SRC_ROOT%\*Validator.%EXT_OLD%
SET OLD_CD=%CD%
CD %VALIDATOR_ROOT%
FOR /F "usebackq delims=." %%f IN (`dir *.%EXT_OLD% /B /S`) DO move %%f.%EXT_OLD% %%f.%EXT_NEW%
CD %OLD_CD%

@echo rename *.jsp.%MODEL% to *.jsp
SET EXT_OLD=jsp.%MODEL%
SET EXT_NEW=jsp
copy /Y %SRC_ROOT%\*.%EXT_OLD%  %JSP_ROOT%
del %SRC_ROOT%\*.%EXT_OLD%
SET OLD_CD=%CD%
CD %JSP_ROOT%
FOR /F "usebackq delims=." %%f IN (`dir *.%EXT_OLD% /B /S`) DO move %%f.%EXT_OLD% %%f.%EXT_NEW%
CD %OLD_CD%

@rem mkdir & copy -------BZ-----END-------


@rem mkdir & copy -------SYSTEM-----START-------

SET MODEL=system
SET MODEL_ROOT=%DIST_ROOT%\com\lj\web\%MODEL%
SET ACTION_ROOT=%MODEL_ROOT%\action
SET FORM_ROOT=%MODEL_ROOT%\form
SET VALIDATOR_ROOT=%MODEL_ROOT%\validator
SET JSP_ROOT=%WEBINF_ROOT%\view\%MODEL%

IF EXIST "%MODEL_ROOT%" GOTO _EXIST_MODEL_ROOT
MD %MODEL_ROOT%
:_EXIST_MODEL_ROOT

IF EXIST "%ACTION_ROOT%" GOTO _EXIST_ACTION_ROOT
MD %ACTION_ROOT%
:_EXIST_ACTION_ROOT

IF EXIST "%FORM_ROOT%" GOTO _EXIST_FORM_ROOT
MD %FORM_ROOT%
:_EXIST_FORM_ROOT

IF EXIST "%VALIDATOR_ROOT%" GOTO _EXIST_VALIDATOR_ROOT
MD %VALIDATOR_ROOT%
:_EXIST_VALIDATOR_ROOT

IF EXIST "%JSP_ROOT%" GOTO _EXIST_JSP_ROOT
MD %JSP_ROOT%
:_EXIST_JSP_ROOT

@echo rename *.java.%MODEL% to *.java
SET EXT_OLD=java.%MODEL%
SET EXT_NEW=java

copy /Y %SRC_ROOT%\*Action.%EXT_OLD%  %ACTION_ROOT%
del %SRC_ROOT%\*Action.%EXT_OLD%
SET OLD_CD=%CD%
CD %ACTION_ROOT%
FOR /F "usebackq delims=." %%f IN (`dir *.%EXT_OLD% /B /S`) DO move %%f.%EXT_OLD% %%f.%EXT_NEW%
CD %OLD_CD%

copy /Y %SRC_ROOT%\*Form.%EXT_OLD%  %FORM_ROOT%
del %SRC_ROOT%\*Form.%EXT_OLD%
SET OLD_CD=%CD%
CD %FORM_ROOT%
FOR /F "usebackq delims=." %%f IN (`dir *.%EXT_OLD% /B /S`) DO move %%f.%EXT_OLD% %%f.%EXT_NEW%
CD %OLD_CD%

copy /Y %SRC_ROOT%\*Validator.%EXT_OLD%  %VALIDATOR_ROOT%
del %SRC_ROOT%\*Validator.%EXT_OLD%
SET OLD_CD=%CD%
CD %VALIDATOR_ROOT%
FOR /F "usebackq delims=." %%f IN (`dir *.%EXT_OLD% /B /S`) DO move %%f.%EXT_OLD% %%f.%EXT_NEW%
CD %OLD_CD%

@echo rename *.jsp.%MODEL% to *.jsp
SET EXT_OLD=jsp.%MODEL%
SET EXT_NEW=jsp
copy /Y %SRC_ROOT%\*.%EXT_OLD%  %JSP_ROOT%
del %SRC_ROOT%\*.%EXT_OLD%
SET OLD_CD=%CD%
CD %JSP_ROOT%
FOR /F "usebackq delims=." %%f IN (`dir *.%EXT_OLD% /B /S`) DO move %%f.%EXT_OLD% %%f.%EXT_NEW%
CD %OLD_CD%

@rem mkdir & copy -------SYSTEM-----END-------

@rem mkdir & copy -------LOG-----START-------

SET MODEL=log
SET MODEL_ROOT=%DIST_ROOT%\com\lj\web\%MODEL%
SET ACTION_ROOT=%MODEL_ROOT%\action
SET FORM_ROOT=%MODEL_ROOT%\form
SET VALIDATOR_ROOT=%MODEL_ROOT%\validator
SET JSP_ROOT=%WEBINF_ROOT%\view\%MODEL%

IF EXIST "%MODEL_ROOT%" GOTO _EXIST_MODEL_ROOT
MD %MODEL_ROOT%
:_EXIST_MODEL_ROOT

IF EXIST "%ACTION_ROOT%" GOTO _EXIST_ACTION_ROOT
MD %ACTION_ROOT%
:_EXIST_ACTION_ROOT

IF EXIST "%FORM_ROOT%" GOTO _EXIST_FORM_ROOT
MD %FORM_ROOT%
:_EXIST_FORM_ROOT

IF EXIST "%VALIDATOR_ROOT%" GOTO _EXIST_VALIDATOR_ROOT
MD %VALIDATOR_ROOT%
:_EXIST_VALIDATOR_ROOT

IF EXIST "%JSP_ROOT%" GOTO _EXIST_JSP_ROOT
MD %JSP_ROOT%
:_EXIST_JSP_ROOT

@echo rename *.java.%MODEL% to *.java
SET EXT_OLD=java.%MODEL%
SET EXT_NEW=java

copy /Y %SRC_ROOT%\*Action.%EXT_OLD%  %ACTION_ROOT%
del %SRC_ROOT%\*Action.%EXT_OLD%
SET OLD_CD=%CD%
CD %ACTION_ROOT%
FOR /F "usebackq delims=." %%f IN (`dir *.%EXT_OLD% /B /S`) DO move %%f.%EXT_OLD% %%f.%EXT_NEW%
CD %OLD_CD%

copy /Y %SRC_ROOT%\*Form.%EXT_OLD%  %FORM_ROOT%
del %SRC_ROOT%\*Form.%EXT_OLD%
SET OLD_CD=%CD%
CD %FORM_ROOT%
FOR /F "usebackq delims=." %%f IN (`dir *.%EXT_OLD% /B /S`) DO move %%f.%EXT_OLD% %%f.%EXT_NEW%
CD %OLD_CD%

copy /Y %SRC_ROOT%\*Validator.%EXT_OLD%  %VALIDATOR_ROOT%
del %SRC_ROOT%\*Validator.%EXT_OLD%
SET OLD_CD=%CD%
CD %VALIDATOR_ROOT%
FOR /F "usebackq delims=." %%f IN (`dir *.%EXT_OLD% /B /S`) DO move %%f.%EXT_OLD% %%f.%EXT_NEW%
CD %OLD_CD%

@echo rename *.jsp.%MODEL% to *.jsp
SET EXT_OLD=jsp.%MODEL%
SET EXT_NEW=jsp
copy /Y %SRC_ROOT%\*.%EXT_OLD%  %JSP_ROOT%
del %SRC_ROOT%\*.%EXT_OLD%
SET OLD_CD=%CD%
CD %JSP_ROOT%
FOR /F "usebackq delims=." %%f IN (`dir *.%EXT_OLD% /B /S`) DO move %%f.%EXT_OLD% %%f.%EXT_NEW%
CD %OLD_CD%

@rem mkdir & copy -------LOG-----END-------

@rem mkdir & copy -------STAT-----START-------

SET MODEL=stat
SET MODEL_ROOT=%DIST_ROOT%\com\lj\web\%MODEL%
SET ACTION_ROOT=%MODEL_ROOT%\action
SET FORM_ROOT=%MODEL_ROOT%\form
SET VALIDATOR_ROOT=%MODEL_ROOT%\validator
SET JSP_ROOT=%WEBINF_ROOT%\view\%MODEL%

IF EXIST "%MODEL_ROOT%" GOTO _EXIST_MODEL_ROOT
MD %MODEL_ROOT%
:_EXIST_MODEL_ROOT

IF EXIST "%ACTION_ROOT%" GOTO _EXIST_ACTION_ROOT
MD %ACTION_ROOT%
:_EXIST_ACTION_ROOT

IF EXIST "%FORM_ROOT%" GOTO _EXIST_FORM_ROOT
MD %FORM_ROOT%
:_EXIST_FORM_ROOT

IF EXIST "%VALIDATOR_ROOT%" GOTO _EXIST_VALIDATOR_ROOT
MD %VALIDATOR_ROOT%
:_EXIST_VALIDATOR_ROOT

IF EXIST "%JSP_ROOT%" GOTO _EXIST_JSP_ROOT
MD %JSP_ROOT%
:_EXIST_JSP_ROOT

@echo rename *.java.%MODEL% to *.java
SET EXT_OLD=java.%MODEL%
SET EXT_NEW=java

copy /Y %SRC_ROOT%\*Action.%EXT_OLD%  %ACTION_ROOT%
del %SRC_ROOT%\*Action.%EXT_OLD%
SET OLD_CD=%CD%
CD %ACTION_ROOT%
FOR /F "usebackq delims=." %%f IN (`dir *.%EXT_OLD% /B /S`) DO move %%f.%EXT_OLD% %%f.%EXT_NEW%
CD %OLD_CD%

copy /Y %SRC_ROOT%\*Form.%EXT_OLD%  %FORM_ROOT%
del %SRC_ROOT%\*Form.%EXT_OLD%
SET OLD_CD=%CD%
CD %FORM_ROOT%
FOR /F "usebackq delims=." %%f IN (`dir *.%EXT_OLD% /B /S`) DO move %%f.%EXT_OLD% %%f.%EXT_NEW%
CD %OLD_CD%

copy /Y %SRC_ROOT%\*Validator.%EXT_OLD%  %VALIDATOR_ROOT%
del %SRC_ROOT%\*Validator.%EXT_OLD%
SET OLD_CD=%CD%
CD %VALIDATOR_ROOT%
FOR /F "usebackq delims=." %%f IN (`dir *.%EXT_OLD% /B /S`) DO move %%f.%EXT_OLD% %%f.%EXT_NEW%
CD %OLD_CD%

@echo rename *.jsp.%MODEL% to *.jsp
SET EXT_OLD=jsp.%MODEL%
SET EXT_NEW=jsp
copy /Y %SRC_ROOT%\*.%EXT_OLD%  %JSP_ROOT%
del %SRC_ROOT%\*.%EXT_OLD%
SET OLD_CD=%CD%
CD %JSP_ROOT%
FOR /F "usebackq delims=." %%f IN (`dir *.%EXT_OLD% /B /S`) DO move %%f.%EXT_OLD% %%f.%EXT_NEW%
CD %OLD_CD%

@rem mkdir & copy -------STAT-----END-------

:_END
CD %CURRENT_DIR%

pause









