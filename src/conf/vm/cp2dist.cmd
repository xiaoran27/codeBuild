@rem define var

SET PROTOCOL=%1
SET PROTOCOL=simn
SET MSGFMT=msgc

IF NOT "%PROTOCOL%" == ""  GOTO _SET
SHIFT
ECHO The PROTOCOL environment variable is not defined correctly
ECHO This environment variable is needed to run this program
GOTO _END

:_SET
SET DIST_ROOT=G:\workspace\%PROTOCOL%\src
SET POJO_ROOT=%DIST_ROOT%\com\xiaoran27\db\entity\pojo
SET POJOEXT_ROOT=%POJO_ROOT%\ext
SET DAO_ROOT=%DIST_ROOT%\com\xiaoran27\db\entity\dao
SET DAOTEST_ROOT=%DAO_ROOT%\test
SET MANAGER_ROOT=%DIST_ROOT%\com\xiaoran27\db\entity\manager
SET MANAGERTEST_ROOT=%MANAGER_ROOT%\test
SET DYNAMIC_ROOT=G:\workspace\%PROTOCOL%\dynamic
SET HANDLE_ROOT=%DYNAMIC_ROOT%\com\lj\%PROTOCOL%\%MSGFMT%\handle
SET HANDLETEST_ROOT=%HANDLE_ROOT%\test
SET SOCKET_ROOT=%DIST_ROOT%\com\lj\%PROTOCOL%\\%MSGFMT%\socket
SET SOCKETTEST_ROOT=%SOCKET_ROOT%\test

IF EXIST "%POJO_ROOT%" GOTO _EXIST_POJO_ROOT
MD %POJO_ROOT%
:_EXIST_POJO_ROOT

IF EXIST "%POJOEXT_ROOT%" GOTO _EXIST_POJOEXT_ROOT
MD %POJOEXT_ROOT%
:_EXIST_POJOEXT_ROOT

IF EXIST "%DAO_ROOT%" GOTO _EXIST_DAO_ROOT
MD %DAO_ROOT%
:_EXIST_DAO_ROOT

IF EXIST "%DAOTEST_ROOT%" GOTO _EXIST_DAOTEST_ROOT
MD %DAOTEST_ROOT%
:_EXIST_DAOTEST_ROOT

IF EXIST "%MANAGERTEST_ROOT%" GOTO _EXIST_MANAGERTEST_ROOT
MD %MANAGERTEST_ROOT%
:_EXIST_MANAGERTEST_ROOT

IF EXIST "%HANDLETEST_ROOT%" GOTO _EXIST_HANDLETEST_ROOT
MD %HANDLETEST_ROOT%
:_EXIST_HANDLETEST_ROOT

IF EXIST "%SOCKETTEST_ROOT%" GOTO _EXIST_SOCKETTEST_ROOT
MD %SOCKETTEST_ROOT%
:_EXIST_SOCKETTEST_ROOT

SET CURRENT_DIR=%CD%

CD ..
CD out\vm
SET SRC_ROOT=%CD%

rem copy ... ...

rem copy  *Ext.java ... ...
copy /Y %SRC_ROOT%\*Ext.java  %POJOEXT_ROOT%
del %SRC_ROOT%\*Ext.java

rem copy  *DaoTest.java ... ...
copy /Y %SRC_ROOT%\*DaoTest.java  %DAOTEST_ROOT%
del %SRC_ROOT%\*DaoTest.java
rem copy  *Dao.java ... ...
copy /Y %SRC_ROOT%\*Dao.java  %DAO_ROOT%
del %SRC_ROOT%\*Dao.java

rem copy  *ManagerTest.java ... ...
copy /Y %SRC_ROOT%\*ManagerTest.java  %MANAGERTEST_ROOT%
del %SRC_ROOT%\*ManagerTest.java
rem copy  *Manager.java ... ...
copy /Y %SRC_ROOT%\*Manager.java  %MANAGER_ROOT%
del %SRC_ROOT%\*Manager.java

rem copy  *HandleTest.java ... ...
copy /Y %SRC_ROOT%\*HandleTest.java  %HANDLETEST_ROOT%
del %SRC_ROOT%\*HandleTest.java
rem copy  *Handle.java ... ...
copy /Y %SRC_ROOT%\*Handle.java  %HANDLE_ROOT%
del %SRC_ROOT%\*Handle.java

rem copy  *ClientTest.java ... ...
copy /Y %SRC_ROOT%\*Test.java  %SOCKETTEST_ROOT%
del %SRC_ROOT%\*Test.java
rem copy  *Client.java ... ...
copy /Y %SRC_ROOT%\*Client.java  %SOCKET_ROOT%
del %SRC_ROOT%\*Client.java


rem copy  *.java ... ...
copy /Y %SRC_ROOT%\*.java  %POJO_ROOT%
del %SRC_ROOT%\*.java

:_END
CD %CURRENT_DIR%
pause








