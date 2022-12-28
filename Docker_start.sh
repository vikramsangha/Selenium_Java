#!/bin/bash

# Start Selenium server for remote execution
cd /opt/bin
nohup ./start-selenium-standalone.sh &
cd /opt/selenium

echo "Starting XVFB for video recording when headless mode is set to false "	
nohup /opt/bin/start-xvfb.sh &
sleep 10
mkdir -p /opt/selenium/video
nohup ffmpeg -y -f x11grab -video_size 1360x1020 -r 30 -i ${DISPLAY} -codec:v libx264 -preset ultrafast -pix_fmt yuv420p /opt/selenium/video/abc.mp4 &
sleep 5

# Run Tests
mvn test -DsuiteXmlFile=src/test/java/com/qa/suites/testsuite.xml -Dorg.slf4j.simpleLogger.defaultLogLevel=INFO

# Stop recording
echo $(pidof ffmpeg)
kill -s QUIT $(pidof ffmpeg)

# Analyze Results


# Save Artifacts