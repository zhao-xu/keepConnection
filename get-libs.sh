#!/bin/bash
base='/Applications/SQLDeveloper.app/Contents/Resources/sqldeveloper'
cp -f ${base}/ide/lib/dbapi.jar lib/
cp -f ${base}/ide/lib/javatools.jar lib/
cp -f ${base}/ide/extensions/oracle.ide.jar lib/
cp -f ${base}/sqldeveloper/extensions/oracle.sqldeveloper.jar lib/
cp -f ${base}/sqldeveloper/lib/oracle.sqldeveloper.utils.jar lib/