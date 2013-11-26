#!/bin/sh

# Misha sends me whitelist files encoded in ISO-8859 text, with CR line terminators.
# This command gets rid of the line terminators.

basedir=$(cd "$(dirname "$0")/.." && pwd)

for file in $basedir/src/main/resources/whitelist/*.txt ; do
	mac2unix "$file"
done
