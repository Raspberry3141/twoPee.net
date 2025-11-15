#!/bin/bash
scp -i ~/.ssh/2pProdServer_key.pem ~/project/devserver/plugins/two.pee.plugin-1.0-SNAPSHOT.jar azureuser@20.172.64.185:~/server/plugins/two.pee.plugin-1.0-SNAPSHOT.jar

