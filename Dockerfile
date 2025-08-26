# Enable different versions of the base image
ARG version=4.23.0
FROM selenium/standalone-chrome:${version}

# Work in a predictable directory
WORKDIR /opt/selenium

# Copy project files
COPY . .

USER root

# Install tools; fix CRLF; make script executable
RUN apt-get update -qq && apt-get install -y --no-install-recommends \
      maven \
      ffmpeg \
      dos2unix \
 && dos2unix /opt/selenium/Docker_start.sh \
 && chmod +x /opt/selenium/Docker_start.sh \
 && rm -rf /var/lib/apt/lists/*

# Optional cleanups; fix Linux globs
RUN rm -rf target/* \
 && rm -rf test-output/* \
 && chmod -R o+rwx /opt/selenium/

# (Optional) prefetch deps for faster cold starts
RUN mvn -q -DskipTests dependency:go-offline --no-transfer-progress

# Base image uses a non-root user at runtime
USER seluser

# Healthcheck is fine to keep
HEALTHCHECK --interval=30s CMD /opt/bin/check-grid.sh --host 0.0.0.0 --port 4444

# Always use absolute path for entrypoint
ENTRYPOINT ["/opt/selenium/Docker_start.sh"]
