#!/bin/bash

jar cf oracle.sqldeveloper.keep.connected.jar -C out/production/keepConnection .
cp -f oracle.sqldeveloper.keep.connected.jar /Applications/SQLDeveloper.app/Contents/Resources/sqldeveloper/sqldeveloper/extensions/
