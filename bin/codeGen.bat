@rem *********************************************
@rem ����Դ���ļ�(.java;.jsp;.sql)
@rem �����ɵ��ļ��ַ�������Ŀ¼��
@rem �������ɵ�.java�ļ�
@rem *********************************************

ant db2src.all cp2generated  >ant.info

@rem ���뱾�ļ�����Ŀ¼
@rem cd F:\wwwsite\exam\build\bin

@rem *********************************************
@rem ����Դ���ļ�(.java;.jsp;.sql)
@rem *********************************************

@rem ant -f conf-build.xml db2src >db2src.info

@rem *********************************************
@rem �����ɵ��ļ��ַ�������Ŀ¼��
@rem *********************************************

@rem  ant -f dopackage.xml cp2generated  >cp2generated.info


@rem *********************************************
@rem �������ɵ�.java�ļ�
@rem *********************************************

@rem ant -f compile-build.xml compile >compile.info

