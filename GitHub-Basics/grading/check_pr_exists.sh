#!/bin/bash
# Checks that the student opened a pull request on this repository.
#
# NOTE: this requires the autograder job's GITHUB_TOKEN to have
# "pull-requests: read" access. classroom50's documented default token
# scope for autograder jobs is contents:write + statuses:write only, so
# this check may fail with a permission/auth error rather than a real
# "no PR found" result -- see for-classroom-config-repo/INSTRUCTIONS.md
# for the known risk and how to tell the two failure modes apart.

set -o pipefail

if ! command -v gh >/dev/null 2>&1; then
  echo "FAIL: gh CLI not available in this environment -- cannot check for a pull request."
  exit 1
fi

PR_OUTPUT=$(gh pr list --state all --json number,title 2>/tmp/pr_err)
PR_EXIT=$?

if [ $PR_EXIT -ne 0 ]; then
  echo "FAIL: 'gh pr list' errored -- this may be a token permission issue, not a missing PR."
  echo "$(cat /tmp/pr_err)"
  echo ""
  echo "If this is a permission error (e.g. 'Resource not accessible'), the autograder token"
  echo "likely lacks pull-requests:read access -- flag this to the instructor, it is not"
  echo "necessarily the student's fault."
  exit 1
fi

PR_COUNT=$(echo "$PR_OUTPUT" | grep -c '"number"')

if [ "$PR_COUNT" -gt 0 ]; then
  echo "PASS: Found $PR_COUNT pull request(s) on this repository."
  exit 0
else
  echo "FAIL: No pull request found. Open a pull request from your branch into main."
  exit 1
fi
