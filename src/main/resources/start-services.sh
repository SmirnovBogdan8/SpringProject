#!/bin/bash

start_service() {
  service_path=$1
  echo "Starting service at $service_path"
  (cd $service_path && ./gradlew bootRun &)
}

start_service "upload-service"
start_service "primary-validation-service"
start_service "secondary-validation-service"
start_service "status-update-service"
start_service "status-check-service"

echo "All services started."
