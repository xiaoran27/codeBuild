@rem *********************************************
@rem ����Դ���ļ�(.java;.jsp;.sql)
@rem �����ɵ��ļ��ַ�������Ŀ¼��
@rem �������ɵ�.java�ļ�
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