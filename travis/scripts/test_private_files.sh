#!/bin/bash

if["$TRAVIS_PULL_REQUEST" != "false" ]; then
    echo "Pulou para pull request"
    exit
fi

test -f twidere/src/google/AndroidManifest.xml
