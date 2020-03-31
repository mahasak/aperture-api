#!/bin/sh

# if container die unexpectedly, play don't delete it pid file
# delete it manually
rm -f /app/RUNNING_PID
exec "$@"

