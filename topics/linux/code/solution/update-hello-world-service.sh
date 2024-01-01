#!/usr/bin/sh

set -e

SCRIPT_DIR=$(dirname $(realpath $0))

hello_world_versions_dir=$(realpath $SCRIPT_DIR/../hello_world_service)
last_file=$(ls -t $hello_world_versions_dir | head -1)
sudo systemctl stop hello-world
sudo cp $hello_world_versions_dir/$last_file /usr/local/bin/hello-world
sudo chmod +x /usr/local/bin/hello-world
sudo systemctl start hello-world
