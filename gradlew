#!/usr/bin/env sh

PRG="$0"

while [-h "$PRG"]; do
    ls=`ls -ld "$PRG"`
    link=`expr "$ls" : '.*-> \(.*\)$'`
    if expr "$link" : '/.*' > /dev/null; then
        PRG="$link"
    else    
        PRG="$link"
    fi
done
SAVED="`pwd`"
cd "`dirname \"$PRG\"`/" > /dev/null
APP_HOME="`pwd -P`"
cd "$SAVED" > /dev/null

APP_NAME="Gradle"
APP_BASE_NAME=`basename "$0"`

DEFAULT_JVM_OPTS=""

MAX_FD="maximum"

warn () {
    echo "$*"
}

die () {
    echo
    echo "$*"
    echo
    exit 1
}
cygwin=false
msys=false
darwin=false
nonstop=false
case "`uname`" in
    CYGWIN* )
        cygwin=true
        ;;
    DarWIN* )
        darwin=true
        ;;
    MINGW* )
        msys=true
        ;;
    NONSTOP* )
        nonstop=true
        ;;
esac

CLASSPATH=$APP_HOME/gradle/wrapper/gradle-wrapper.jar

if [-n "$JAVA_HOME"]; then
    if [-x "$JAVA_HOME/jre/sh/java"];then
        JAVACMD="$JAVA_HOME/jre/sh/java"
    else
        JAVACMD="$JAVA_HOME/bin/java"
    fi
    if[!-x "$JAVACMD"] ; then
        die "ERROR: JAVA_HOME is set to an invalid directory: $JAVA_HOME

Por favor sete o JAVA_HOME disponível no seu environment para bater com a instalação do Java."
    fi
else
    JAVACMD="java"
    which java >/dev/null 2>&1 || die "ERROR: JAVA_HOME is not set and no 'java' command could be found in yout PATH.

Por favor sete o JAVA_HOME no seu environment para bater com a instalação."
fi

if 