#!/bin/bash
# Checks that the student opened an Issue on this repository.
#
# NOTE: this requires the autograder job's GITHUB_TOKEN to have
# "issues: read" access. classroom50's documented default token scope
# for autograder jobs is contents:write + statuses:write only, so this
# check may fail with a permission/auth error rather than a real
# "no Issue found" result -- see for-classroom-config-repo/INSTRUCTIONS.md
# for the known risk and how to tell the two failure modes apart.

set -o pipefail

if ! command -v gh >/dev/null 2>&1; then
  echo "FAIL: gh CLI not available in this environment -- cannot check for an issue."
  exit 1
fi

ISSUE_OUTPUT=$(gh issue list --state all --json number,title 2>/tmp/issue_err)
ISSUE_EXIT=$?

if [ $ISSUE_EXIT -ne 0 ]; then
  echo "FAIL: 'gh issue list' errored -- this may be a token permission issue, not a missing Issue."
  echo "$(cat /tmp/issue_err)"
  echo ""
  echo "If this is a permission error (e.g. 'Resource not accessible'), the autograder token"
  echo "likely lacks issues:read access -- flag this to the instructor, it is not"
  echo "necessarily the student's fault."
  exit 1
fi

ISSUE_COUNT=$(echo "$ISSUE_OUTPUT" | grep -c '"number"')

if [ "$ISSUE_COUNT" -gt 0 ]; then
  echo "PASS: Found $ISSUE_COUNT issue(s) on this repository."
  exit 0
else
  echo "FAIL: No Issue found. Open an Issue describing a confusing concept or a question for your team."
  exit 1
fi
