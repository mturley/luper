#!/bin/sh
cd /home/mturley/luper/luper-web
echo "Bundling with Meteor(ite) into a tarball..."
mrt bundle ../bundle.tgz
cd ..
echo "Extracting node server files..."
rm -rf bundle/
tar xzvf bundle.tgz
rm bundle.tgz
cd luper-web
