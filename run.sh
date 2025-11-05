#!/bin/bash

set -e

echo "=== Building project with Maven ==="
cd ~/project/twoPee.net
mvn clean package

echo "=== Copying built JAR to plugins folder ==="
cp -f ~/project/twoPee.net/target/two.pee.plugin-1.0-SNAPSHOT.jar ~/project/devserver/plugins/

echo "=== Starting Spigot server ==="
~/project/devserver/start.sh
