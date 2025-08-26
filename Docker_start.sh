#!/bin/bash
set -euo pipefail

# --- Configure your framework properties for remote + headless ---
CONF=./src/main/java/com/qa/config/config.properties
sed -i 's/^remote_execution.*/remote_execution=true/' "$CONF"
sed -i 's#^remote_selenium_url.*#remote_selenium_url=http://127.0.0.1:4444/wd/hub#' "$CONF"
sed -i 's/^headless.*/headless=true/' "$CONF"
echo "Effective config:"
cat "$CONF"

# --- Start Selenium the official way (supervised entrypoint) ---
echo "Starting Selenium Standalone via entry_point.sh ..."
/opt/bin/entry_point.sh &

# --- Wait until the Grid is ready ---
echo -n "Waiting for Selenium at http://127.0.0.1:4444/status"
for i in {1..60}; do
  if curl -fsS http://127.0.0.1:4444/status >/dev/null; then
    echo "  OK"
    break
  fi
  echo -n "."
  sleep 1
done

# --- Run tests (optionally filter via -e test=<Class#method>) ---
echo "Running tests: ${test:-ALL}"
mvn -q -Dtest="${test:-}" test --no-transfer-progress