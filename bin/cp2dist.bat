@rem *********************************************
@rem 生成源码文件(.java;.jsp;.sql)
@rem 把生成的文件分发到各个目录中
@rem 编译生成的.java文件
@rem *********************************************


set src=G:\workspace\codeBuild\src\conf\out\vm
set dst=G:\workspace\oms\src\com\xiaoran27\db\entity

set pojo=%dst%\pojo
set pojoext=%dst%\pojo\ext
set dao=%dst%\dao

oldcd=%cd%
cd %src%

copy *Ext.java %pojoext%
del *Ext.java

copy %src%\*Dao.java %dao%
del %src%\*Dao.java

copy %src%\*.java %pojo%
del %src%\*.java

cd %oldcd%