#!/bin/bash

if[ "$TRAVIS_PULL_REQUEST" != "false" ]; then
    echo "Pulou para pull request"
    exit 0
fi

tar zxf travis/configs/twidere_private_config.tar.gz
mkdir -p ~/.ssh/
chmod 700 ~/.ssh/
cat private/ssh_config >> ~/.ssh/config
cat private/ssh_known_hosts >> ~/.ssh/known_hosts
ssh-agente bash -c "ssh-add private/ssh_id_rsa; git clone $COMPONENT_GOOGLE_REPO twidere/src/google" > /dev/null 2>&1
git -C twidere/src/google reset --hard `cat twidere/src/.google.commit-id` > /dev/null 2>&1
cat private/dropbox_uploader >> ~/.dropbox_uploader
