#!/bin/bash

set -o allexport
source $1
rvm default exec fastlane supply run
retcode=$?
set +o allexport

exit $retcode