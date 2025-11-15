#!/bin/bash

# Change to the current directory (just to be sure)
cd "$(dirname "$0")"

docker compose -f compose.yaml up -d --build --force-recreate

echo "Script completed."