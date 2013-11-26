#!/bin/sh
LANG=de_DE.UTF-8
cat "$1" \
| recode latin1..UTF-8 \
| tr '[:upper:]' '[:lower:]' \
| grep -v '^$' \
| sed 's/\(.\)/\18/g' \
| sed 's/^\(.*\)$/\.\1\./' \
| sed 's/8-8/9/g;s/8\.$/./;' \
| recode UTF-8..latin1 \
> "$2"
