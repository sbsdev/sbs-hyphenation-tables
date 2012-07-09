#!/bin/sh
tr A-Z a-z | tr ' ' '\n' | grep -v '^$' |
awk '{printf "%c", "."
for (i=1; i <= length($1); i++){printf "%c8", substr($1, i, 1)}
print "."}' | sed 's/8-8/9/g;s/8\.$/./;'
