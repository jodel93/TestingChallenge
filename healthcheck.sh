#!/usr/bin/env bash
# Environment Variables
# HUB_HOST
# BROWSER
# MODULE

echo "Checking if hub is ready - $HOST"

while [ "$( curl -s http://$HOST:4444/wd/hub/status | jq -r .value.ready )" != "true" ]
do
	sleep 1
done

# start the java command
java -cp angular-strangelist-tests.jar:libs/* \
    -DHOST=$HOST \
    -DBROWSER=$BROWSER \
    org.testng.TestNG $MODULE