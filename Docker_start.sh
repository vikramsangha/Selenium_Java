#!/bin/bash

# Start Selenium server for remote execution
echo "Starting the selenium server for remote execution"
cd /opt/bin
nohup ./start-selenium-standalone.sh &
cd /opt/selenium


echo "Setting remote_selenium_url and headless mode for exection"
sed -i "s/remote_execution.*/remote_execution=true/" "./src/main/java/com/qa/config/config.properties"

if [ ! -z "${remote_selenium_url}" ]; then
	sed -i "s/remote_selenium_url.*/remote_selenium_url=$remote_selenium_url/" "./src/main/java/com/qa/config/config.properties"
fi

if [ ! -z "${headless}" ]; then
	sed -i "s/headless.*/headless=$headless/" "./src/main/java/com/qa/config/config.properties"
fi

if [ ! -z "${record_execution}" ]; then
	echo "Starting XVFB for video recording and setting headleass to false irrespective of user input "
	sed -i "s/headless.*/headless=false/" "./src/main/java/com/qa/config/config.properties"
	nohup /opt/bin/start-xvfb.sh &
	sleep 10
	mkdir -p /opt/selenium/video
	nohup ffmpeg -y -f x11grab -video_size 1360x1020 -r 30 -i ${DISPLAY} -codec:v libx264 -preset ultrafast -pix_fmt yuv420p /opt/selenium/video/abc.mp4 &
	sleep 5
fi

cat ./src/main/java/com/qa/config/config.properties

sleep 20
	

# Run Tests

mvn test --no-transfer-progress -Dtest=${test}

# Stop recording
if [ ! -z "${record_execution}" ]; then
	echo $(pidof ffmpeg)
	kill -s QUIT $(pidof ffmpeg)
fi

# Analyze Results


# Save Artifacts