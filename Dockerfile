# Enable different versions of the base image
ARG version=4

# Build test workspaces on the same image
FROM selenium/standalone-chrome:$version as builder

# Working directory as /opt/selenium
WORKDIR /opt/selenium
USER root

# Install required tools
RUN apt-get update -qqy \
&& apt-get -qqy install maven ffmpeg \ 
&& rm -rf /var/lib/apt/lists/* /var/cache/apt/*

#============================
# Set configuration options browser launch
#============================
ENV SCREEN_WIDTH 1360
ENV SCREEN_HEIGHT 1020
ENV SCREEN_DEPTH 24
ENV SCREEN_DPI 96
ENV DISPLAY :99.0
ENV START_XVFB true
ENV DBUS_SESSION_BUS_ADDRESS=/dev/null
RUN  mkdir -p /tmp/.X11-unix && sudo chmod 1777 /tmp/.X11-unix \
&& sed -i -e '/^assistive_technologies=/s/^/#/' /etc/java-*-openjdk/accessibility.properties

# Copy framework in image and clean target folders
COPY . .
RUN rm -rf target/* \
&& rm -rf test-output\* \
&& chmod -R o+rwx /opt/selenium/

# Maven clean
RUN mvn clean

# Set user as seluser
USER seluser

# Make sure that container is healthy at the time of running by checking port 4444
HEALTHCHECK --interval=30s CMD /opt/bin/check-grid.sh --host 0.0.0.0 --port 4444 

# Initial set of scripts when container starts
ENTRYPOINT ["./Docker_start.sh"]
