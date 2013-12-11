@rem *********************************************
@rem 生成源码文件(.java;.jsp;.sql)
@rem 把生成的文件分发到各个目录中
@rem 编译生成的.java文件
@rem *********************************************

ant db2src.all cp2generated  >ant.info

@rem 进入本文件所在目录
@rem cd F:\wwwsite\exam\build\bin

@rem *********************************************
@rem 生成源码文件(.java;.jsp;.sql)
@rem *********************************************

@rem ant -f conf-build.xml db2src >db2src.info

@rem *********************************************
@rem 把生成的文件分发到各个目录中
@rem *********************************************

@rem  ant -f dopackage.xml cp2generated  >cp2generated.info


@rem *********************************************
@rem 编译生成的.java文件
@rem *********************************************

@rem ant -f compile-build.xml compile >compile.info

