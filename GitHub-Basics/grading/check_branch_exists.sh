#!/bin/bash
# Checks that the student pushed a branch following the FRC naming convention
# (name/feature, e.g. "firstname-lastname/intro") in addition to main.
#
# Fetches all remote branches first -- the checkout step in the autograde job
# may only have fetched the ref that triggered the run, so we can't rely on
# `git branch -r` already listing every branch.

set -o pipefail

if ! git fetch origin '+refs/heads/*:refs/remotes/origin/*' --quiet 2>/tmp/fetch_err; then
  echo "FAIL: Could not fetch remote branches to check."
  echo "$(cat /tmp/fetch_err)"
  exit 1
fi

PATTERN='^[a-z0-9]+(-[a-z0-9]+)*/[a-z0-9-]+$'

MATCHING_BRANCHES=$(git branch -r --format='%(refname:short)' \
  | sed 's#^origin/##' \
  | grep -vE '^(main|master|HEAD)$' \
  | grep -E "$PATTERN")

if [ -n "$MATCHING_BRANCHES" ]; then
  echo "PASS: Found a branch following the naming convention (name/feature)."
  echo ""
  echo "Matching branches:"
  echo "$MATCHING_BRANCHES"
  exit 0
else
  echo "FAIL: No branch found matching the FRC naming convention (name/feature, e.g. 'firstname-lastname/intro')."
  echo ""
  echo "All remote branches found:"
  git branch -r --format='%(refname:short)'
  exit 1
fi
